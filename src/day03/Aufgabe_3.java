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

public class Aufgabe_3 {

	private static List<String> inputList = new ArrayList<>();

	public static void main(String[] args) {
	    
	    // THIS CODE IS BIG BULLSHIT AND DOESN'T WORK.
	    // IGNORE IT
	    
		Path path = Path.of("src/day03/Task03.txt");

		try (Stream<String> input = Files.lines(path); Stream<String> input2 = Files.lines(path)) {
			inputList = input.collect(Collectors.toList());
			int result1 = 0;
			long result2 = 0;

			for (String line : inputList) {
			    List<Integer> jolts = new ArrayList<>();
			    List<Integer> jolts2 = new ArrayList<>();
			    List<Integer> jolts3 = new ArrayList<>();
			    int second = 0;
			    for (char c : line.toCharArray()) {
			        jolts.add(Integer.parseInt("" + c));
			    }
			    
			    jolts2 = jolts;
			    jolts3 = jolts;
			    
//			    Integer[] digits = new Integer[12];
//			    digits = version2(digits, jolts3);
//			    String res = "";
//			    for (int i : digits) {
//			        res += i;
//			    }
//			    long ahh = Long.valueOf(res);
//			    System.out.println(ahh);
//			    result2 += ahh;
			    
			    long lol = Long.valueOf(part2(jolts2, 0, 12, false));
			    System.out.println(lol);
			    result2 += lol;
			    
			    int maxPos = findPositionOfMax(jolts);
			    int first = jolts.get(maxPos);
			    if (maxPos == jolts.size() - 1) {
			        jolts.remove(maxPos);
			        second = first;
			        maxPos = findPositionOfMax(jolts);
			        first = jolts.get(maxPos);
			    } else {
			        jolts = jolts.subList(maxPos + 1, jolts.size());
			        maxPos = findPositionOfMax(jolts);
                    second = jolts.get(maxPos);
			    }
			    String number = "" + first + second;
			    result1 += Integer.valueOf(number);
			}
			
			System.out.println(result1);
			System.out.println(result2);
			System.out.println("3121910778619");

		} catch (IOException e) {
			System.out.println("Error");
		}
	}
	
	public static int findPositionOfMax(List<Integer> list) {
        int currMax = 0;
        int currMaxPos = 0;
        int posValue;
        
        for (int i : list) {
            posValue = i;
            if (posValue > currMax) {
                currMax = posValue;
                currMaxPos = list.indexOf(posValue);
            }
        }
        return currMaxPos;
    }
	
	public static String part2(List<Integer> list, int startPos, int length, boolean ignoreLength) {
	    String result = "";
	    if (length == 0) return result;
	    
	    List<Integer> subList = list.subList(startPos, list.size());
	    System.out.println(list);
	    System.out.println(subList);
	    
	    int maxPos = findPositionOfMax(subList);
	    int maxVal = subList.get(maxPos);
	    // System.out.println(maxVal);
//	    if (subList.size() - 1 - maxPos == length - 1 && !ignoreLength) {
//	        // System.out.println("fit");
//	        subList = list.subList(maxPos + 1, list.size());
//	        for (int i : subList) {
//	            result += i;
//	        }
//	        System.out.println("fit: " + result);
//	        return result;
//	    }
	    if (subList.size() - 1 - maxPos >= length - 1 && !ignoreLength) {
	        // System.out.println("more");
	        result += part2(subList, maxPos + 1, length - 1, false);
	    } else {
	        List<Integer> test = subList.subList(0, maxPos);
	        while (!(subList.size() - 1 - maxPos < length - 1)) {
	            maxPos = findPositionOfMax(test);
	            test = test.subList(0, maxPos);
	        }
	        result += part2(subList, maxPos + 1, length - 1, false);
	    }
//	    else if (subList.size() - 1 - maxPos < length - 1 && !ignoreLength) {
//	        List<Integer> tmpList = list.subList(0, maxPos);
//	        System.out.println("less");
//	        String tmp = part2(tmpList, 0, length, true);
//	        maxVal = Integer.parseInt(tmp);
//	        int tmpPos = list.indexOf(maxVal);
//	        result += part2(subList, tmpPos + 1, length - 1, false);
//	    }
	    
	    if (ignoreLength) {
	        return result + maxVal;
	    }

	    return maxVal + result;
	}
	
//	public static Integer[] version2(Integer[] digits, List<Integer> list) {
//	    int space = 0;
//	    int firstEmptyPos = -1;
//	    int lastEmptyPos = 13;
//	    for (Integer i : digits) {
//	        if (i == null) space++;
//	        if (firstEmptyPos == -1 && i == null) firstEmptyPos = Arrays.asList(digits).indexOf(null);
//	        if (i == null)lastEmptyPos = Arrays.asList(digits).lastIndexOf(null);
//	    }
//	    if (space == 0) return digits;
//	    int maxPos = findPositionOfMax(list);
//        int maxVal = list.get(maxPos);
//        int needed = list.size() - 1 - maxPos;
//        System.out.println("needed: " + needed);
//        System.out.println("space: " + space);
//        System.out.println("max at: " + maxPos);
//        System.out.println("first: " + firstEmptyPos);
//        System.out.println("last: " + lastEmptyPos);
//        if (needed <= space) {
//            System.out.println("hit");
//            List<Integer> sub = list.subList(maxPos, list.size());
//            for (int i = 0; i < sub.size(); i++) {
//                System.out.println(lastEmptyPos - needed);
//                int test = lastEmptyPos - needed;
//                digits[test] = sub.get(i);
//                needed--;
//            }
//            list = list.subList(0, maxPos);
//        } else {
//            digits[firstEmptyPos] = list.get(maxPos);
//            list = list.subList(maxPos + 1, list.size());
//        }
//        
//        digits = version2(digits, list);
//        
//	    return digits;
//	}
	
}
