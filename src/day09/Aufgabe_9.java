package day09;

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
			
//			Map<Long, Vector<Vector<Integer>>> areas = getAreasMap(positions);
//			Map<Long, Vector<Vector<Integer>>> validAreas = new HashMap<>();
//			Set<Vector<Integer>> validPoints = new HashSet<>();
//			
//			for (long area : areas.keySet()) {
//			    Vector<Vector<Integer>> pair = areas.get(area);
//			    Vector<Integer> v1 = pair.get(0), v2 = pair.get(1);
//			    int xStart = v1.get(0), xEnd = v2.get(0);
//			    int yStart = v1.get(1), yEnd = v2.get(1);
//			    if (xEnd < xStart) {
//			        int tmp = xStart;
//			        xStart = xEnd;
//			        xEnd = tmp;
//			    }
//			    if (yEnd < yStart) {
//                    int tmp = yStart;
//                    yStart = yEnd;
//                    yEnd = tmp;
//                }
//			    boolean valid = true;
//			    loop:
//			    for (int x = xStart; x <= xEnd; x++) {
//			        boolean leftdown = false, downright = false, rightup = false, upleft = false;
//			        for (int y = yStart; y <= yEnd; y++) {
//			            leftdown = false; downright = false; rightup = false; upleft = false;
//			            Vector<Integer> point = new Vector<>();
//			            point.add(x);
//			            point.add(y);
//			            //if (validPoints.contains(point)) continue;
//			            for (Vector<Integer> pos : positions) {
//			                int x2 = pos.get(0), y2 = pos.get(1);
//			                if ((x2 <= x) && (y2 >= y) && (!leftdown)) leftdown = true;
//			                if ((y2 >= y) && (x2 >= x) && (!downright)) downright = true;
//			                if ((x2 >= x) && (y2 <= y) && (!rightup)) rightup = true;
//			                if ((y2 <= y) && (x2 <= x) && !(upleft)) upleft = true;
//			                //System.out.println(point + " AND " + pos + " is " + leftdown);
//			                if (leftdown && downright && rightup && upleft) break;
//			            }
//			            if (leftdown && downright && rightup && upleft) {
//			                //validPoints.add(point);
//			                continue;
//			            } else {
//			                valid = false;
//			                break loop;
//			            }
//			        }
//			    }
//			    if (valid) {
//			        validAreas.put(area, pair);
//			    }
//			}
//			
//			System.out.println(getBiggestArea(validAreas));
			
			int amount = 0;
			int[] xCoords = new int[positions.size()];
			int[] yCoords = new int[positions.size()];
			
			for (int i = 0; i < positions.size(); i++) {
			    Vector<Integer> pos = positions.get(i);
			    amount++;
			    xCoords[i] = pos.get(0);
			    yCoords[i] = pos.get(1);
			}
			
			Polygon polygon = new Polygon(xCoords, yCoords, amount);
			
			List<Rectangle> recs = new ArrayList<>();
			for (Vector<Integer> pos : positions) {
			    int x1 = pos.get(0), y1 = pos.get(1);
			    for (Vector<Integer> other : positions) {
			        if (pos == other) continue;
			        int x2 = other.get(0), y2 = other.get(1);
			        int width = x2 - x1;
			        int height = y2 - y1;
			        if (width < 0) {
			            width = -width;
			            x1 -= width;
			        }
			        if (height < 0) {
			            height = -height;
			            y1 -= height;
			        }
			        Rectangle r = new Rectangle(x1, y1, width, height);
			        boolean addable = true;
			        for (Rectangle rec : recs) {
			            if (rec.equals(r)) {
			                addable = false;
			                break;
			            }
			        }
			        if (addable) recs.add(r);
			    }
			}
			
			System.out.println(recs);
			
			List<Double> validAreas = new ArrayList<>();
			for (Rectangle rec : recs) {
			    if (polygon.contains(rec)) {
			        validAreas.add(rec.getHeight() * rec.getWidth());
			    }
			}
			Collections.sort(validAreas, Collections.reverseOrder());
			
			System.out.println(validAreas.get(0));
			

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
                areas.put(calculateArea(tile, other), corners);
            }
        }
        return areas;
	}

	private static long calculateArea(Vector<Integer> v1, Vector<Integer> v2) {
	    int x1 = v1.get(0), y1 = v1.get(1);
	    int x2 = v2.get(0), y2 = v2.get(1);
	    long x = Math.abs(x1 - x2) + 1;
	    long y = Math.abs(y1 - y2) + 1;
	    long area = Math.abs(x) * Math.abs(y);
	    
	    return Math.abs(area);
	}
}
