package day07;

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

public class Aufgabe_7 {

	private static List<String> inputList = new ArrayList<>();

	public static void main(String[] args) {

		Path path = Path.of("src/day07/Task07.txt");

		try (Stream<String> input = Files.lines(path);) {
			inputList = input.collect(Collectors.toList());

			long[][] diagram = diagramAsNumArray();
			long hits, timelines;
			hits = timelines = 0;

			for (int i = 0; i < diagram.length - 1; i++) {
				long[] line = diagram[i];
				for (int j = 0; j < line.length; j++) {
					long sym = diagram[i][j];
					if (sym > 0) { // if current slot is no splitter
						long next = diagram[i + 1][j];
						if (next >= 0) { // Add current timelines to next row
							diagram[i + 1][j] += sym;
							continue;
						} if (next == -1) { // Hit a splitter
							hits++;
							diagram[i + 1][j + 1] += sym;
							diagram[i + 1][j - 1] += sym;
							continue;
						}
					}
				}
			}
			for (long particle : diagram[diagram.length - 1]) {
					timelines += particle;
			}

			System.out.println(hits);
			System.out.println(timelines);

		} catch (IOException e) {
			System.out.println("Error");
		}
	}


	private static long[][] diagramAsNumArray() {
		long[][] diagram = new long[inputList.size()][];
		for (int i = 0; i < inputList.size(); i++) {
			char[] line = inputList.get(i).toCharArray();
			diagram[i] = new long[line.length];
			for (int j = 0; j < line.length; j++) {
				switch (line[j]) {
				case '.':
					diagram[i][j] = 0;
					break;
				case '^':
					diagram[i][j] = -1;
					break;
				case 'S':
					diagram[i][j] = 1;
					break;
				}
			}
		}
		return diagram;
	}
}
