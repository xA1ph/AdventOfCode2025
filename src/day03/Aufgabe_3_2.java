package day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aufgabe_3_2 {

	private static List<String> inputList = new ArrayList<>();

	public static void main(String[] args) {
	    
		Path path = Path.of("src/day03/Task03.txt");

		try (Stream<String> input = Files.lines(path);) {
			inputList = input.collect(Collectors.toList());
			
			long result = 0;
			
			for (String line : inputList) {
			    
                List<Integer> jolts = new ArrayList<>();
                for (char c : line.toCharArray()) {
                    jolts.add(Integer.parseInt("" + c));
                }
                
                int[] digits = new int[12];
                
                for (int i = 0; i < digits.length; i++) {
                    int pos = findFittingMax(jolts, -i + digits.length - 1);
                    int value = jolts.get(pos);
                    digits[i] = value;
                    jolts = jolts.subList(pos + 1, jolts.size());
                }
                
                String num = "";
                for (int i : digits) {
                    num += i;
                }
                
                result += Long.valueOf(num);
            }
			
			System.out.println(result);
			

		} catch (IOException e) {
			System.out.println("Error");
		}
	}
	
	public static int findFittingMax(List<Integer> list, int digit) {
        int currMax = 0;
        int currMaxPos = 0;
        int posValue;
        
        for (int i = 0; i < list.size(); i++) {
            if (!(list.size() - 1 - i >= digit)) {
                continue;
            }
            posValue = list.get(i);
            if (posValue > currMax) {
                currMax = posValue;
                currMaxPos = list.indexOf(posValue);
            }
        }
        return currMaxPos;
    }
	
}
