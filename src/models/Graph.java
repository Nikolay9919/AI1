package models;

import java.util.ArrayList;
import java.util.List;

public class Graph {
	private List<Node> nodes = new ArrayList<>();
	private List<Node> path = new ArrayList<>();

	public Graph() {}
	
	public Graph(List<Node> nodes) {
		super();
		this.nodes = nodes;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	public boolean searchA (Node startNode, Node endNode) {
		List<Node> closed = new ArrayList<>();
		List<Node> open = new ArrayList<Node>();
		this.cleanGraph();
		
		open.add(startNode);
		startNode.setG(0);
		startNode.setH(endNode);
		
		while (!open.isEmpty()) {
			Node currentNode = open.get(0);
			System.out.println(currentNode.getName());
			double minF = currentNode.getF();
			boolean tentative_is_better = false;
			// choose node open with minimum F
			if (open.size() > 1) {
				for (int i = 1; i < open.size(); i++) {
					if (open.get(i).getF() < minF) {
						currentNode = open.get(i);
						minF = open.get(i).getF();
					}
				}
			}
			// build path
			if (currentNode.getName() == endNode.getName()) {
				Node prevStep = endNode;
				while (prevStep != null) {
					//for paint path
//					prevStep.setInPath(true);
					path.add(prevStep);
					Node nodeWithLink = prevStep.getCameFrom();
//					//for paint path
//					for (Link pathLink : nodeWithLink.getLinks()) {
//						if (pathLink.getNodeEnd() == prevStep) {
//							pathLink.setInPath(true);
//						}
//					}
					prevStep = nodeWithLink;
				}
//				for (int i=path.size(); i > 0; i--) {
//					System.out.println(path.get(i).getName());
//				}
				return true;
			}
			
			open.remove(currentNode);
			closed.add(currentNode);
			linkLoop: for (Link link : currentNode.getLinks()) {
				//if next node in closed list
				for (Node n : closed) {
					if (link.getNodeEnd().getName() == n.getName()) {
						continue linkLoop;
					}
				}
				
				double tentative_g_score = currentNode.getG() + link.getLength();
				//if next node in open list
				boolean inOpenList = false;
				for (Node n : open) {
					if (link.getNodeEnd().getName() == n.getName()) {
						inOpenList = true;
						if (tentative_g_score < link.getNodeEnd().getG()) {
							tentative_is_better = true;
						} else {
							tentative_is_better = false;
						}
					}
				}
				if (!inOpenList) {
					open.add(link.getNodeEnd());
					tentative_is_better = true;
				}
				if (tentative_is_better) {
					int index = open.size() - 1;
					open.get(index).setCameFrom(currentNode);
					open.get(index).setG(tentative_g_score);
					open.get(index).setH(endNode);
				}
			}			
		}
		return false;
	}
	
	public boolean searchGreed(Node startNode, Node endNode) {
		//TODO Implementation
		return false;
	}

	public List<Node> getPath() {
		return path;	
	}
	
	public int getNodeIdByName(String name) {
		for (int i=0;i<nodes.size();i++) {
			if (nodes.get(i).getName() == name) {
				return i;
			}
		}
		return -1;
	}
	
	private void cleanGraph() {
		for (Node n: nodes) {
			n.setCameFrom(null);
			n.setG(0.0);
			n.setH(null);
		}
		path = new ArrayList<>();
	}
	
	public void makeTestGraph() {
		nodes = new ArrayList<>();
		path = new ArrayList<>();
		
		nodes.add(new Node("A", 60, 3, 10)); //0
		nodes.add(new Node("B", 12, 2, 2)); //1
		nodes.add(new Node("C", 50, 0, 0)); //2
		nodes.add(new Node("D", 45, 4, 6)); //3
		nodes.add(new Node("E", 10, 2, 0)); //4
		nodes.add(new Node("F", 30, 4, 0)); //5
		nodes.add(new Node("J", 35, 8, 12)); //6
		nodes.get(0).getLinks().add(new Link(6, nodes.get(1)));
		nodes.get(0).getLinks().add(new Link(5, nodes.get(3)));
		nodes.get(0).getLinks().add(new Link(15, nodes.get(6)));
		nodes.get(1).getLinks().add(new Link(6, nodes.get(0)));
		nodes.get(3).getLinks().add(new Link(5, nodes.get(0)));
		nodes.get(6).getLinks().add(new Link(15, nodes.get(0)));
		nodes.get(1).getLinks().add(new Link(2, nodes.get(2)));
		nodes.get(1).getLinks().add(new Link(3, nodes.get(4)));
		nodes.get(1).getLinks().add(new Link(4, nodes.get(3)));
		nodes.get(2).getLinks().add(new Link(2, nodes.get(1)));
		nodes.get(4).getLinks().add(new Link(3, nodes.get(1)));
		nodes.get(3).getLinks().add(new Link(4, nodes.get(1)));
		nodes.get(3).getLinks().add(new Link(3, nodes.get(5)));
		nodes.get(3).getLinks().add(new Link(10, nodes.get(6)));
		nodes.get(5).getLinks().add(new Link(3, nodes.get(3)));
		nodes.get(6).getLinks().add(new Link(10, nodes.get(3)));
		nodes.get(2).getLinks().add(new Link(1, nodes.get(4)));
		nodes.get(4).getLinks().add(new Link(1, nodes.get(2)));
		nodes.get(4).getLinks().add(new Link(5, nodes.get(5)));
		nodes.get(5).getLinks().add(new Link(5, nodes.get(4)));
	}
}
