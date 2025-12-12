package day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aufgabe_9 {

	private static List<String> inputList = new ArrayList<>();
	
	public static void main(String[] args) {
	    
		Path path = Path.of("src/day09/Task09.txt");

		try (Stream<String> input = Files.lines(path);) {
			inputList = input.collect(Collectors.toList());
			
			boolean redAndGreenOnly = true;
			List<Vector<Integer>> positions = getPositionsList();

			if (!redAndGreenOnly) {
			    System.out.println("Largest Area of any rectangle: " + getPart1Result(positions) + " tiles");
			    return;
			}
			
			// Could be optimized by sorting the edges and rectangles by size (Longest/Biggest first) --> less comparisons
			List<Vector<Vector<Integer>>> rectangles = getRecList(positions);
			List<Vector<Vector<Integer>>> edges = getEdges(positions);
			List<Vector<Vector<Integer>>> validRecs = new ArrayList<>();
			
			for (Vector<Vector<Integer>> rec : rectangles) {
			    boolean valid = true;
			    Vector<Integer> v1 = rec.get(0), v2 = rec.get(1);
			    int xStart = Math.min(v1.get(0), v2.get(0)), xEnd = Math.max(v1.get(0), v2.get(0));
                int yStart = Math.min(v1.get(1), v2.get(1)), yEnd = Math.max(v1.get(1), v2.get(1));
                for (Vector<Vector<Integer>> edge : edges) {
                    int x3 = Math.min(edge.get(0).get(0), edge.get(1).get(0)), x4 = Math.max(edge.get(0).get(0), edge.get(1).get(0));
                    int y3 = Math.min(edge.get(0).get(1), edge.get(1).get(1)), y4 = Math.max(edge.get(0).get(1), edge.get(1).get(1));
                    if (x4 > xStart && x3 < xEnd && y4 > yStart && y3 < yEnd) {
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    validRecs.add(rec);
                }
			}

			long result2 = 0;
			for (Vector<Vector<Integer>> rec : validRecs) {
			    long area = calculateArea(rec.get(0), rec.get(1));
			    if (area > result2) result2 = area;
			}
			System.out.println(result2);

		} catch (IOException e) {
			System.out.println("Error");
		}
	}

	public static List<Vector<Integer>> getPositionsList() {
	    List<Vector<Integer>> positions = new ArrayList<>();
	    for (String line : inputList) {
	        String[] arr = line.split(",");
	        Vector<Integer> v = new Vector<>();
	        v.add(Integer.valueOf(arr[0]));
	        v.add(Integer.valueOf(arr[1]));
	        positions.add(v);
	    }
	    return positions;
	}

	public static long getPart1Result(List<Vector<Integer>> positions) {
	    Map<Long, Vector<Vector<Integer>>> areas = getAreasMap(positions);
        return getBiggestArea(areas);
	}
	
	private static long getBiggestArea(Map<Long, Vector<Vector<Integer>>> areas) {
	    List<Long> keyList = new ArrayList<>();
        keyList.addAll(areas.keySet());
        Collections.sort(keyList, Collections.reverseOrder());
        return keyList.get(0);
	}

	private static Map<Long, Vector<Vector<Integer>>> getAreasMap(List<Vector<Integer>> positions) {
	    Map<Long, Vector<Vector<Integer>>> areas = new HashMap<>();
        for (Vector<Integer> tile : positions) {
            for (Vector<Integer> other : positions) {
                if (tile == other) continue;
                Vector<Vector<Integer>> corners = new Vector<>();
                corners.add(tile);
                corners.add(other);
                Long area = calculateArea(tile, other);
                areas.put(area, corners);
            }
        }
        return areas;
	}
	
	private static List<Vector<Vector<Integer>>> getRecList(List<Vector<Integer>> positions) {
        List<Vector<Vector<Integer>>> recs = new ArrayList<>();
        for (Vector<Integer> tile : positions) {
            for (Vector<Integer> other : positions) {
                if (tile == other) continue;
                Vector<Vector<Integer>> corners = new Vector<>();
                corners.add(tile);
                corners.add(other);
                recs.add(corners);
            }
        }
        return recs;
    }
	
	private static List<Vector<Vector<Integer>>> getEdges(List<Vector<Integer>> positions) {
	    List<Vector<Vector<Integer>>> edges = new ArrayList<>();
	    for (int i = 0; i < positions.size(); i++) {
	        int prevIndex = (i == 0) ? positions.size() - 1 : i - 1;
	        Vector<Vector<Integer>> v = new Vector<>();
	        v.add(positions.get(i));
	        v.add(positions.get(prevIndex));
	        edges.add(v);
	    }
	    return edges;
	}

	private static long calculateArea(Vector<Integer> v1, Vector<Integer> v2) {
	    int x1 = v1.get(0), y1 = v1.get(1);
	    int x2 = v2.get(0), y2 = v2.get(1);
	    long x = Math.abs(x1 - x2) + 1, y = Math.abs(y1 - y2) + 1;
	    long area = x * y;
	    
	    return area;
	}
}
