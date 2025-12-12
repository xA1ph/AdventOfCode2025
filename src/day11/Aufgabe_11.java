package day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aufgabe_11 {

	private static List<String> inputList = new ArrayList<>();
	private static List<Node<String>> nodes = new ArrayList<>();
	
	public static void main(String[] args) {
	    
		Path path = Path.of("src/day11/Task11.txt");

		try (Stream<String> input = Files.lines(path);) {
			inputList = input.collect(Collectors.toList());
			
			boolean part1 = false;
			nodes.add(new Node<String>("out"));
			
			while (nodes.size() < inputList.size() + 1) {
			    for (String line : inputList) {
			        String[] lineSplit = line.split(": ");
			        String name = lineSplit[0];
			        String[] childNames= lineSplit[1].split(" ");
			        Node<String> n = new Node<>(name);
			        if (!nodes.contains(n)) {
			            boolean valid = true;
			            for (String cN : childNames) {
			                Node<String> tmp = new Node<>(cN);
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
			    int i = nodes.indexOf(new Node<String>("you"));
			    Tree<String> tree1 = new Tree<>(nodes.get(i));
			    System.out.println(tree1.countPathsEnds("out"));
			} else {
			    // int j = nodes.indexOf(new Node<String>("svr"));
	            // Tree<String> tree2 = new Tree<>(nodes.get(j));
	            System.out.println(part2());
			}
			
		} catch (IOException e) {
			System.out.println("Error");
		}
	}
	
	public static long part2() {
	    long result = 1;
	    int s = nodes.indexOf(new Node<String>("svr"));
	    int f = nodes.indexOf(new Node<String>("fft"));
	    int d = nodes.indexOf(new Node<String>("dac"));
        Tree<String> tree = new Tree<>(nodes.get(s));
        result *= tree.countPathsEnds("dac");
        System.out.println("first done");
        tree = new Tree<>(nodes.get(d));
        result *= tree.countPathsEnds("fft");
        System.out.println("second done");
        tree = new Tree<>(nodes.get(f));
        result *= tree.countPathsEnds("out");
        
	    return result;
	}
	
	public static class Tree<T> {
	    private Node<String> root;

	    public Tree(Node<String> rootNode) {
	        root = rootNode;
	    }
	    
	    public long countPathsEnds(String end) {
	        return root.countPathsEnds(end);
	    }
	    
	    public long countOutPathsPart2() {
            return root.countOutPathsPart2(false, false);
        }
	}
	
	public static class Node<T> {
        private String data;
        private List<Node<String>> children;
        
        public Node(String data) {
            this.data = data;
        }
        
        public Node(String data, List<String> childNames) {
            this.data = data;
            children = findChildren(childNames);
        }
        
        public void addChildren(String[] childNames) {
            List<String> list = new ArrayList<>();
            for (String name : childNames) {
                list.add(name);
            }
            children = findChildren(list);
        }
        
        private List<Node<String>> findChildren(List<String> childNames){
            List<Node<String>> children = new ArrayList<>();
            for (String name : childNames) {
                for (Node<String> node : nodes) {
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
        
        public List<Node<String>> getChildren() {
            return children;
        }
        
        public long countPathsEnds(String end) {
            long amount = 0;
            for (Node<String> child : children) {
                if (child.getData().equals(end)) amount++;
                else if (child.getData().equals("out")) continue;
                else amount += child.countPathsEnds(end);
            }
            return amount;
        }
        
        public long countOutPathsPart2(boolean fftFound, boolean dacFound) {
            long amount = 0;
            boolean foundFFT = fftFound;
            boolean foundDAC = dacFound;
            if (foundFFT && foundDAC && children.size() == 1) {
                if (children.get(0).getData().equals("out")) {
                    amount++;
                    return amount;
                }
            }
            for (Node<String> child : children) {
                if (!foundFFT || !foundDAC) {
                    for (Node<String> c : children) {
                        if (!foundFFT && c.getData().equals("fft")) foundFFT = true;
                        if (!foundDAC && c.getData().equals("dac")) foundDAC = true;
                    }
                }
                if (!child.getData().equals("out")) amount += child.countOutPathsPart2(foundFFT, foundDAC);
            }
            return amount;
        }
        
        @Override
        public boolean equals(Object o) {
            Node<String> n = (Node<String>) o;
            return  n.getData().equals(this.data);
        }
    }
}
