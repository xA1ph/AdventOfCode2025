package day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aufgabe_11 {

	private static List<String> inputList = new ArrayList<>();
	private static List<Node> nodes = new ArrayList<>();
	private static Map<String, Long> cache = new HashMap<>();

	public static void main(String[] args) {

		Path path = Path.of("src/day11/Task11.txt");

		try (Stream<String> input = Files.lines(path);) {
			inputList = input.collect(Collectors.toList());

			boolean part1 = false;
			nodes.add(new Node("out"));

			while (nodes.size() < inputList.size() + 1) {
			    for (String line : inputList) {
			        String[] lineSplit = line.split(": ");
			        String name = lineSplit[0];
			        String[] childNames= lineSplit[1].split(" ");
			        Node n = new Node(name);
			        if (!nodes.contains(n)) {
			            boolean valid = true;
			            for (String cN : childNames) {
			                Node tmp = new Node(cN);
			                if (!nodes.contains(tmp)) {
			                    valid = false;
			                    break;
			                }
			            }
			            if (valid) {
			                n.addChildren(childNames);
	                        nodes.add(n);
			            }
			        }
			    }
			}

			if (part1) {
			    int i = nodes.indexOf(new Node("you"));
			    Tree tree1 = new Tree(nodes.get(i));
			    System.out.println(tree1.countPathsEnds("out"));
			} else {
	            System.out.println(part2());
			}

		} catch (IOException e) {
			System.out.println("Error");
		}
	}

	public static long part2() {
	    long result = 1;
	    int s = nodes.indexOf(new Node("svr")), f = nodes.indexOf(new Node("fft")), d = nodes.indexOf(new Node("dac"));
        Tree tree = new Tree(nodes.get(s));
        result *= tree.countPathsEnds("fft");
        System.out.println("first done: " + result);
        cache.clear();
        tree = new Tree(nodes.get(f));
        result *= tree.countPathsEnds("dac");
        System.out.println("second done: " + result);
        cache.clear();
        tree = new Tree(nodes.get(d));
        result *= tree.countPathsEnds("out");

	    return result;
	}

	public static class Tree {
	    private Node root;

	    public Tree(Node rootNode) {
	        root = rootNode;
	    }

	    public long countPathsEnds(String end) {
	        return root.countPathsEnds(end);
	    }
	}

	public static class Node {
        private String data;
        private List<Node> children;
        
        public Node(String data) {
            this.data = data;
        }

        public void addChildren(String[] childNames) {
            List<String> list = new ArrayList<>();
            for (String name : childNames) {
                list.add(name);
            }
            children = findChildren(list);
        }

        private List<Node> findChildren(List<String> childNames){
            List<Node> children = new ArrayList<>();
            for (String name : childNames) {
                for (Node node : nodes) {
                    if (node.getData().equals(name)) {
                        children.add(node);
                        break;
                    }
                }
            }
            return children;
        }

        public String getData() {
            return data;
        }

        public List<Node> getChildren() {
            return children;
        }

        public long countPathsEnds(String end) {
            if (cache.containsKey(data)) return cache.get(data);
            long amount = 0;
            for (int i = 0; i < children.size(); i++) {
                Node child = children.get(i);
                if (child.getData().equals(end)) amount++;
                else if (child.getData().equals("out")) continue;
                else amount += child.countPathsEnds(end);
            }
            cache.put(data, amount);
            return amount;
        }

        @Override
        public boolean equals(Object o) {
            Node n = (Node) o;
            return  n.getData().equals(this.data);
        }
    }
}
