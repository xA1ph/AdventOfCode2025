package day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aufgabe_1 {

	private static List<String> inputList = new ArrayList<>();

	public static void main(String[] args) {
		Path path = Path.of("src/day01/Task01.txt");

		try (Stream<String> input = Files.lines(path); Stream<String> input2 = Files.lines(path)) {
			inputList = input.collect(Collectors.toList());

		int counter = 50;
		
		int amountOfZeros1 = 0;
		int amountOfZeros2 = 0;
		
		for (String line : inputList) {
			String direction = line.substring(0, 1);
			int steps = Integer.valueOf(line.substring(1, line.length()));
			
			for (int i = 0; i < steps; i++) {
				if (direction.equals("R")) {
					int next = counter + 1;
					counter = (next > 99) ? 0 : next;
				} else {
					int next = counter - 1;
					counter = (next < 0) ? 99 : next;
				}
				if (counter == 0) amountOfZeros2++;
			}
			if (counter == 0) amountOfZeros1++;
		}
		
		System.out.println(amountOfZeros1);
		System.out.println(amountOfZeros2);

		} catch (IOException e) {
			System.out.println("Error");
		}
	}
}
