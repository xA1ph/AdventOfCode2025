package day02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aufgabe_2 {

	private static List<String> inputList = new ArrayList<>();

	public static void main(String[] args) {
		Path path = Path.of("src/day02/Task02.txt");

		try (Stream<String> input = Files.lines(path); Stream<String> input2 = Files.lines(path)) {
			inputList = input.collect(Collectors.toList());

			String[] Ids = inputList.get(0).split(",");
			Long result1 = (long) 0;
			Long result2 = (long) 0;
			
			for (String ID : Ids) {
			    List<Long> values = new ArrayList<>();
			    String[] nums = ID.split("-");
			    for (Long i = Long.valueOf(nums[0]); i <= Long.valueOf(nums[1]); i++) { // Create Long Array from Number Range
			        values.add(i);
			    }
			    for (Long v : values) {
			        if (v <= 9) continue; // skip single digit numbers
			        String s = v.toString();
			        if (s.length() % 2 == 0) {
			            String left = s.substring(0, s.length()/2);
			            String right = s.substring(s.length()/2);
			            if (left.equals(right)) {
			                result1 += v; // Found invalid ID for Part 1
			                result2 += v; // Means also invalid ID for Part 2
			                continue; // Skip Regex search for Part 2
			            }
			        }
			        if (s.matches("([0-9]+)(\\1+)")) result2 += v; // Check IDs missed in Part 1
			    }
			}
			
			System.out.println(result1);
			System.out.println(result2);

		} catch (IOException e) {
			System.out.println("Error");
		}
	}
}
