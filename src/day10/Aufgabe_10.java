package day10;

import java.awt.Dimension;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aufgabe_10 {

	private static List<String> inputList = new ArrayList<>();
	
	public static void main(String[] args) {
	    
		Path path = Path.of("src/day10/Task10.txt");

		try (Stream<String> input = Files.lines(path);) {
			inputList = input.collect(Collectors.toList());
			
			long result1 = 0;
			
			List<Machine> machines = new ArrayList<>();
			
			for (String line : inputList) {
			    machines.add(new Machine(line));
			}
			for (Machine m : machines) {
			    System.out.println(m.getFewestPressesJolts());
			    result1 += m.getFewestPressesJolts();
			}
			System.out.println(result1);
			
		} catch (IOException e) {
			System.out.println("Error");
		}
	}
	
	private static class Machine {
	    
	    private boolean[] correctLights;
	    private List<int[]> buttons;
	    private int[] joltages;
	    int fewestPressesLights = 999;
	    int fewestPressesJolts = 9999999;
	    
	    public Machine(String line) {
	        correctLights = parseLights(line);
	        buttons = parseButtons(line);
	        joltages = parseJoltages(line);
	        //getFewestPressesLights(1, getTurnedOffLights());
	        getFewestPressesJolts(1, getTurnedOffJolts());
	        System.out.println("done");
	    }
	    
	    public int getFewestPressesLights() {
	        return fewestPressesLights;
	    }
	    
	    public int getFewestPressesJolts() {
            return fewestPressesJolts;
        }
	    
	    private boolean getFewestPressesLights(int amount, boolean[] current) {
	        if (amount > buttons.size()) return false;
	        boolean[] next = current.clone();
	        for (int i = 0; i < buttons.size(); i++) {
	            if (amount > fewestPressesLights) return false;
	            next = pressButtonLights(i, current);
	            getFewestPressesLights(amount + 1, next);
	            if (Arrays.equals(correctLights, next)) {
	                fewestPressesLights = amount;
	                break;
	            }
	        }
	        return true;
	    }

	    private boolean getFewestPressesJolts(int amount, int[] current) {
            if (amount > buttons.size()) return false;
            int[] next = current.clone();
            while (!Arrays.equals(joltages, next)) {
                for (int i = 0; i < buttons.size(); i++) {
                    if (amount > fewestPressesJolts) return false;
                    next = pressButtonJolts(i, current);
                    getFewestPressesJolts(amount + 1, next);
                    if (Arrays.equals(joltages, next)) {
                        fewestPressesJolts = amount;
                        break;
                    }
                }
            }
            return true;
        }

	    private boolean[] pressButtonLights(int index, boolean[] current) {
	        boolean[] next = current.clone();
	        for (int i = 0; i < buttons.get(index).length; i++) {
	            int light = buttons.get(index)[i];
	            next[light] = !next[light];
	        }
	        return next;
	    }
	    
	    private int[] pressButtonJolts(int index, int[] current) {
	        int[] next = current.clone();
            for (int i = 0; i < buttons.get(index).length; i++) {
                int jolt = buttons.get(index)[i];
                next[jolt]++;
            }
            return next;
	    }
	    
	    private boolean[] parseLights(String line) {
	        Pattern p = Pattern.compile("\\[(.+)\\]");
	        Matcher m = p.matcher(line);
	        m.find();
	        char[] chars = m.group(1).toCharArray();
	        boolean[] lights = new boolean[chars.length];
	        for (int i = 0; i < chars.length; i++) {
	            lights[i] = (chars[i] == '.') ? false : true;
	        }
	        return lights;
	    }
	    
	    private List<int[]> parseButtons(String line) {
	        List<int[]> buttons = new ArrayList<>();
	        Pattern p = Pattern.compile("\\([^\\(\\)]+\\)");
	        Matcher m = p.matcher(line);
	        while (m.find()) {
	            String found = m.group(0);
	            found = found.replace("(", "").replace(")", "");
	            String[] arr = found.split(",");
	            int[] nums = new int[arr.length];
	            for (int i = 0; i < arr.length; i++) {
	                String num = arr[i];
	                nums[i] = Integer.valueOf(num);
	            }
	            buttons.add(nums);
	        }
	        return buttons;
	    }
	    
	    private int[] parseJoltages(String line) {
	        Pattern p = Pattern.compile("\\{(.+)\\}");
            Matcher m = p.matcher(line);
            m.find();
            String[] nums = m.group(1).split(",");
            int[] joltages = new int[nums.length];
            for (int i = 0; i < nums.length; i++) {
                joltages[i] = Integer.valueOf(nums[i]);
            }
            return joltages;
	    }
	    
	    private boolean[] getTurnedOffLights() {
	        boolean[] arr = new boolean[correctLights.length];
	        for (int i = 0; i < arr.length; i++) {
	            arr[i] = false;
	        }
	        return arr;
	    }
	    
	    private int[] getTurnedOffJolts() {
            int[] arr = new int[joltages.length];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = 0;
            }
            return arr;
        }
	    
	}
}
