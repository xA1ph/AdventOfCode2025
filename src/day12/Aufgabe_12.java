package day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aufgabe_12 {

	private static String input;
	private static List<Integer> presentSizes = new ArrayList<>();
	private static List<Long> spaces = new ArrayList<>();
	private static List<List<Integer>> amounts = new ArrayList<>();

	public static void main(String[] args) {
	    
	    // Crazy solution. Just check if the present sizes fit in the overall space.
	    
		Path path = Path.of("src/day12/Task12.txt");
		int result1 = 0;

		try {
            input = Files.readString(path);
            
            parse();
            
            for (int i = 0; i < spaces.size(); i++) {
                long space = spaces.get(i), neededSpace = 0;
                List<Integer> amount = amounts.get(i);
                for (int j = 0; j < amount.size(); j++) {
                    neededSpace += amount.get(j) * presentSizes.get(j);
                }
                if (neededSpace <= space) {
                    result1++;
                }
            }
            
            System.out.println(result1);
            
        } catch (IOException e) {
            System.out.println("Error");
        }	
	}
	
	public static void parse() {
	    String[] arr = input.split("\n\n");
	    Pattern p1 = Pattern.compile("#");
	    for (int i = 0; i < arr.length - 1; i++) {
	        Matcher m = p1.matcher(arr[i]);
	        int size = 0;
	        while(m.find()) {
	            size++;
	        }
	        presentSizes.add(size);
	    }
	    String trees = arr[arr.length - 1];
	    Pattern p2 = Pattern.compile("\\d\\dx\\d\\d"), p3 = Pattern.compile("[0-9]+");
	    Matcher m2 = p2.matcher(trees);
	    while(m2.find()) {
	        String found = m2.group(0);
	        String[] arr2 = found.split("x");
	        trees = trees.replaceFirst(found, "");
	        long left = Integer.valueOf(arr2[0]), right =Integer.valueOf(arr2[1]);
	        spaces.add(left * right);
	    }
	    Matcher m3 = p3.matcher(trees);
	    int i = 0;
	    List<Integer> slots = new ArrayList<>();
	    while (m3.find()) {
	       int curr = Integer.valueOf(m3.group(0));
	       if (i == 0) slots = new ArrayList<>();
	       slots.add(curr);
	       if (i == 5) {
	           amounts.add(slots);
	           i = -1;
	       }
	       i++;
	    }
	}
	
}
