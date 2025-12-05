package day05;

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

public class Aufgabe_5 {

	private static List<String> inputList = new ArrayList<>();

	static long[][] ranges = new long[187][];
    static List<Long> IDs = new ArrayList<>();
	
	public static void main(String[] args) {
	    
		Path path = Path.of("src/day05/Task05.txt");

		try (Stream<String> input = Files.lines(path);) {
			inputList = input.collect(Collectors.toList());
			
			createLists(inputList);
			List<Long[]> res = mergeOverlap(ranges);
			
			long result1, result2;
            result1 = result2 = 0;
			
			for (Long[] range : res) {
			    long start = range[0];
                long end = range[1];
			    
			    result2 += (end - start) + 1;
			    
			    for (long id : IDs) {
			        if ((id >= start) && (id <= end)) {
                        result1++;
                        continue;
                    }
			    }
			}
			
			System.out.println(result1);
			System.out.println(result2);
			
		} catch (IOException e) {
			System.out.println("Error");
		}
	}
	
	static void createLists(List<String> inputList) {
	    boolean idsReached = false;
        int i = 0;
        
        for (String line : inputList) {
            if (line.isEmpty()) {
                idsReached = true;
                continue;
            }
            if (idsReached) {
                IDs.add(Long.valueOf(line));
                continue;
            }
            String[] s = line.split("-");
            long start = Long.valueOf(s[0]);
            long end = Long.valueOf(s[1]);
            long[] arr = {start, end};
            ranges[i] = arr;
            i++;
        }
	}
	
	static ArrayList<Long[]> mergeOverlap(long[][] arr) {
        int n = arr.length;

        Arrays.sort(arr, (a, b) -> Long.compare(a[0], b[0]));
        ArrayList<Long[]> res = new ArrayList<>();

        // Checking for all possible overlaps
        for (int i = 0; i < n; i++) {
            long start = arr[i][0];
            long end = arr[i][1];

            // Skipping already merged intervals
            if (!res.isEmpty() && res.get(res.size() - 1)[1] >= end) {
                continue;
            }

            // Find the end of the merged range
            for (int j = i + 1; j < n; j++) {
                if (arr[j][0] <= end) {
                    end = Math.max(end, arr[j][1]);
                }
            }
            res.add(new Long[]{start, end});
        }
        return res;
    }
	
}
