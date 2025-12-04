package day04;

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

public class Aufgabe_4 {

	private static List<String> inputList = new ArrayList<>();

	public static void main(String[] args) {
	    
		Path path = Path.of("src/day04/Task04.txt");

		try (Stream<String> input = Files.lines(path);) {
			inputList = input.collect(Collectors.toList());
			
			final int slots = 138;
			
			String[][] arr = build2DArray(inputList, slots);
			int result1 = 0;
			
			int i = 0;
			int j = 0;
			int changed = 1;
			
			while (changed > 0) {
			    changed = 0;
			    for (String[] row : arr) {
	                j = 0;
	                for (String sym : row) {
	                    if (sym.equals("@")) {
	                        if (checkAround(arr, i, j) < 4) {
	                            arr[i][j] = ".";
	                            result1++;
	                            changed++;
	                        }
	                    }
	                    j++;
	                }
	                i++;
	            }
			    i = j = 0; 
			}
			
			System.out.println(result1);

		} catch (IOException e) {
			System.out.println("Error");
		}
	}
	
	public static String[][] build2DArray(List<String> inputList, int slots){
	    String[][] arr = new String[slots][slots];
        
        int i = 0;
        int j = 0;
        
        for (String line : inputList) {
            j = 0;
            for (char c : line.toCharArray()) {
                arr[i][j] = "" + c;
                j++;
            }
            i++;
        }
        return arr;
	}
	
	public static int checkAround(String[][] arr, int i, int j) {
	    int amount = 0;
	    int slots = arr[i].length;
	    for (int x = -1; x <= 1; x++) {
	        int column = i + x;
	        if (column > -1 && column < slots) {
	            for (int y = -1; y <= 1; y++) {
	                int row = j + y;
	                if (row > -1 && row < slots) {
	                    if (column == i && row == j) continue; // skip currently checked object
	                    if (arr[column][row].equals("@")) {
	                        amount++;
	                    }
	                }
	            }
	        }
	    }
	    return amount;
	}
	
}
