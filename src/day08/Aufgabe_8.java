package day08;

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

public class Aufgabe_8 {

	private static List<String> inputList = new ArrayList<>();
	
	public static void main(String[] args) {
	    
		Path path = Path.of("src/day08/Task08.txt");
		List<Set<Vector<Long>>> circuits = new ArrayList<>();
		long result1 = 0, result2 = 0;

		try (Stream<String> input = Files.lines(path);) {
			inputList = input.collect(Collectors.toList());
			
			int maxIterations = 5000; // Amount of iterations for connecting the circuits
			List<Vector<Long>> positions = getAllPositions();
			
			Map<Double, Vector<Vector<Long>>> distances = new HashMap<>();
			
			for (Vector<Long> pos : positions) {
			    for (Vector<Long> v : positions) {
			        if (pos == v) continue;
			        double mag = vectorMagnitude(distanceVector(pos, v));
			        Vector<Vector<Long>> pair = new Vector<>();
			        pair.add(pos);
			        pair.add(v);
			        distances.put(mag, pair);
			    }
			}

			Set<Double> keys = distances.keySet();
			List<Double> keyList = new ArrayList<>(keys);
			Collections.sort(keyList);
			boolean shouldBreak = false;
			
			for (int i = 0; i < maxIterations; i++) {
			    if (shouldBreak) break; // Remove this line and change <maxIterations = 1000> for Part 1
                Vector<Vector<Long>> v = distances.get(keyList.get(i));
                result2 = v.get(0).get(0) * v.get(1).get(0);
			    boolean found1 = false, found2 = false;
			    Set<Vector<Long>> set1 = null, set2 = null;

			    for (Set<Vector<Long>> circuit : circuits) {
			        if (circuit.contains(v.get(0))) {
	                    found1 = true;
	                    set1 = circuit;
	                }
	                if (circuit.contains(v.get(1))) {
                        found2 = true;
                        set2 = circuit;
                    }
	            }
	            if (!found1 && !found2) { // Add a new pair of circuits
	                Set<Vector<Long>> s = new HashSet<>();
	                s.add(v.get(0));
	                s.add(v.get(1));
	                circuits.add(s);
	                continue;
	            }
	            if (found1 && !found2) { // Add box 2 to circuit of box 1
	                set1.add(v.get(1));
	                continue;
	            }
	            if (!found1 && found2) { // Add box 1 to circuit of box 2
                    set2.add(v.get(0));
                    continue;
                }
	            if (found1 && found2) { // Merge both circuits
                    if (set1 == set2) continue;
	                set1.addAll(set2);
	                circuits.remove(set2);
	                if (circuits.size() == 1) shouldBreak = true; // break for part 2
                    continue;
                }
			}

			List<Integer> sizes = new ArrayList<>();
			for (Set<Vector<Long>> circuit : circuits) {
			    sizes.add(circuit.size());
			}

			Collections.sort(sizes, Collections.reverseOrder());

			long size1, size2, size3;
			size1 = (sizes.size() >= 1) ? sizes.get(0) : 1;
			size2 = (sizes.size() >= 2) ? sizes.get(1) : 1;
			size3 = (sizes.size() >= 3) ? sizes.get(2) : 1;

			result1 = size1 * size2 * size3;
			System.out.println(result1);
			System.out.println(result2);

		} catch (IOException e) {
			System.out.println("Error");
		}
	}

	public static List<Vector<Long>> getAllPositions() {
	    Pattern p = Pattern.compile("\\d+");
	    List<Vector<Long>> positions = new ArrayList<>();
        
        for (String line : inputList) {
            Matcher m = p.matcher(line);
            Vector<Long> v = new Vector<>();
            while (m.find()) {
                v.add(Long.valueOf(m.group(0)));
            }
            positions.add(v);
        }
        return positions;
	}

	private static Vector<Long> distanceVector(Vector<Long> v1, Vector<Long> v2) {
	    long x1 = v1.get(0), y1 = v1.get(1), z1 = v1.get(2);
	    long x2 = v2.get(0), y2 = v2.get(1), z2 = v2.get(2);
	    Vector<Long> res = new Vector<>();
	    res.add(x2 - x1);
	    res.add(y2 - y1);
	    res.add(z2 - z1);
	    return res;
	    
	}
	
	private static double vectorMagnitude(Vector<Long> v) {
	    long x = v.get(0), y = v.get(1), z = v.get(2);
	    long sum = x * x + y * y + z * z;
	    return Math.sqrt(sum);
	}
}
