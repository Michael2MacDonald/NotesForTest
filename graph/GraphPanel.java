import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GraphPanel extends JPanel {
	
	ArrayList<Node> nodeList = new ArrayList<Node>();
	ArrayList<Edge> edgeList = new ArrayList<Edge>();
	int circleRadius = 20;
	
	ArrayList<ArrayList<Boolean>> adjacency = new ArrayList<ArrayList<Boolean>>();
	
	public GraphPanel() {
		super();
	}

	public ArrayList<Edge> getConnectedEdges(Node node) { // find all edges that connect to the node
		ArrayList<Edge> result = new ArrayList<Edge>(); // output list

		for (int i = 0; i < edgeList.size(); i++) { // for each edge
			if (edgeList.get(i).first.equals(node) || edgeList.get(i).second.equals(node)) { // look at the first and second nodes of each edge
				result.add(edgeList.get(i)); // add the edge to the output list
			}
		}

		return result;
	}
	
	public ArrayList<String> getConnectedLabels(String label) {
		ArrayList<String> toReturn = new ArrayList<String>();
		int b = getIndex(label);

		for (int i = 0; i < adjacency.size(); i++) {
			if ((adjacency.get(b).get(i) == true) && nodeList.get(i).getLabel().equals(label) == false) {
				toReturn.add(nodeList.get(i).getLabel());
			}
		}

		return toReturn;
	}
	
	public void printAdjacency() {
		System.out.println();

		for (int i = 0; i < adjacency.size(); i++) {
			for (int j = 0; j < adjacency.get(0).size(); j++) {
				System.out.print(adjacency.get(i).get(j)+ "\t");
			}
			System.out.println();
		}
	}
	
	public void addNode(int newx, int newy, String newlabel) {
		nodeList.add(new Node(newx, newy, newlabel));
		adjacency.add(new ArrayList<Boolean>());

		for (int i = 0; i < adjacency.size() - 1; i++) {
			adjacency.get(i).add(false);
		}
		for (int i = 0; i < adjacency.size(); i++) {
			adjacency.get(adjacency.size() - 1).add(false);
		}

		printAdjacency();
	}
	
	public void addEdge(Node first, Node second, String newlabel) {
		edgeList.add(new Edge(first, second, newlabel));
		int firstIndex = 0;
		int secondIndex = 0;

		for (int i = 0; i < nodeList.size(); i++) {
			if (first.equals(nodeList.get(i))) {
				firstIndex = i;
			}
			if (second.equals(nodeList.get(i))) {
				secondIndex = i;
			}
		}
		
		adjacency.get(firstIndex).set(secondIndex, true);
		adjacency.get(secondIndex).set(firstIndex, true);
		printAdjacency();
	}
	
	public Node getNode(int x, int y) {
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.get(i);
			// a^2 + b^2 = c^2 
			double radius = Math.sqrt( Math.pow(x-node.getX(), 2) + Math.pow(y-node.getY(), 2) );
			if (radius < circleRadius) {
				return node;
			}
		}

		return null;
	}
	
	public Node getNode(String s) {
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.get(i);
			if (s.equals(node.getLabel() )) {
				return node;
			}
		}

		return null;
	}
	
	public int getIndex(String s) {
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.get(i);
			if (s.equals(node.getLabel() )) {
				return i;
			}
		}

		return -1;
	}
	
	public boolean nodeExists(String s) {
		for (int i = 0; i < nodeList.size(); i++) {
			if (s.equals(nodeList.get(i).getLabel())) {
				return true;
			}
		}

		return false;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.get(i).getHighlighted() == true) {
				g.setColor(Color.RED);
			}
			else {
				g.setColor(Color.BLACK);
			}
			g.drawOval(nodeList.get(i).getX() - circleRadius, nodeList.get(i).getY() - circleRadius, circleRadius * 2, circleRadius * 2);
			g.drawString(nodeList.get(i).getLabel(), nodeList.get(i).getX(), nodeList.get(i).getY());
		}

		for (int i = 0; i < edgeList.size(); i++) {
			g.setColor(Color.BLACK);
			g.drawLine(edgeList.get(i).getFirst().getX(), edgeList.get(i).getFirst().getY(), edgeList.get(i).getSecond().getX(), edgeList.get(i).getSecond().getY());
			int fx = edgeList.get(i).getFirst().getX();
			int fy = edgeList.get(i).getFirst().getY();
			int sx = edgeList.get(i).getSecond().getX();
			int sy = edgeList.get(i).getSecond().getY();
			
			g.drawString(edgeList.get(i).getLabel(), Math.min(fx, sx) + (Math.abs(sx - fx)/ 2), Math.min(fy, sy) + (Math.abs(sy - fy)/ 2));
		}

	}

	public void stopHighlighting() {
		for (int i = 0; i < nodeList.size(); i++) {
			nodeList.get(i).setHighlighted(false);
		}
	}
	
}
