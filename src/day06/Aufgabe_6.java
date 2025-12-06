package day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aufgabe_6 {

	private static List<String> inputList = new ArrayList<>();
	
	public static void main(String[] args) {
	    
		Path path = Path.of("src/day06/Task06.txt");
		
		long result1, result2;
		result1 = result2 = 0;
		List<List<String>> tasks = new ArrayList<>();

		try (Stream<String> input = Files.lines(path);) {
			inputList = input.collect(Collectors.toList());
			
			tasks = listPart1();
			result1 = calculate(tasks);
			tasks = listPart2();
			result2 = calculate(tasks);
			
			System.out.println("1 = " + result1 + "; 2 = " + result2);
			
		} catch (IOException e) {
			System.out.println("Error");
		}
	}
	
	public static List<List<String>> listPart1() {
		List<List<String>> tasks = prepareList();
		Pattern pattern = Pattern.compile("\\d+|\\+|\\*");

		for (String line : inputList) {
			Matcher m = pattern.matcher(line);
			int i = 0;
			
			while (m.find()) { // Get numbers and operations and add to tasks
				tasks.get(i).add(m.group(0));
				i++;
			}
		}
		
		return tasks;
	}
	
	public static List<List<String>> listPart2() {
		List<List<String>> tasks = prepareList();
		String[][] sheet;
		
		sheet = new String[5][inputList.get(0).length()]; // Get input as String[][]
		
		for (int i = 0; i < inputList.size(); i++) {
			char[] line = inputList.get(i).toCharArray();
			for (int j = 0; j < line.length; j++) {
				sheet[i][j] = "" + line[j]; // Fill the sheet
			}
		}

		int taskNumber = 0;
		for (int n = sheet[0].length - 1; n >= 0; n--) {
			String num = "";
			for (int m = 0; m < sheet.length; m++) {
				num += sheet[m][n]; // Build numbers from columns
			}
			num = num.replace(" ", "");
			if (num.isEmpty()) { // Skip the empty columns
				taskNumber++;
				continue;
			}
			if (num.contains("+") || num.contains("*")) { // Get operation, add num and op to task
				String operation = num.substring(num.length() - 1);
				num = num.substring(0, num.length() - 1);
				tasks.get(taskNumber).add(num);
				tasks.get(taskNumber).add(operation);
				continue;
			}
			tasks.get(taskNumber).add(num); // No operation, add number to task
		}
		return tasks;
	}
	
	private static List<List<String>> prepareList() {
		List<List<String>> tasks = new ArrayList<>();
		Pattern pattern = Pattern.compile("\\d+");
		Matcher mm = pattern.matcher(inputList.get(0));

		while (mm.find()) tasks.add(new ArrayList<String>()); // Add List for each task
		return tasks;
	}
	
	private static long calculate(List<List<String>> tasks) {
		long result = 0;
		for (List<String> task : tasks) {
			String operation = task.getLast(); // Get type of operation
			long taskResult = 1;
			for (String s : task) {
				if (s.equals("+") || s.equals("*")) continue; // Skip the operation
				int num = Integer.valueOf(s);
				if (operation.equals("+")) taskResult += num; // Addition
				else if (operation.equals("*")) taskResult = taskResult * num; // Multiplication
			}
			if (operation.equals("+")) taskResult--; // -1 because taskResult starts at 1
			result += taskResult;
		}
		return result;
	}
}
