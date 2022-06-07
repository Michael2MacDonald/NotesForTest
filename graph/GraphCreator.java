/** 
 * Graph Creator With Travelling Salesman
 * Author: Michael MacDonald
 * 05/17/22
 * 
 * Allows you to place nodes with labels. You can also place edges between nodes. Each edge has a cost that you can set.
 * You can set a starting node and press Travelling Salesman find a path that crosses all nodes only once using the least costly path.
 * 
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GraphCreator implements ActionListener, MouseListener {

	JFrame frame = new JFrame(); // frame

	GraphPanel panel = new GraphPanel(); // new panel

	JButton nodeButton = new JButton("Node");
	JButton edgeButton = new JButton("Edge");
	JTextField labelsTF = new JTextField("A");
	JTextField firstNode = new JTextField("FirstNode");
	JTextField secondNode = new JTextField("SecondNode");
	JButton connectedButton = new JButton("Test If Nodes Are Connected");

	Container west = new Container();
	Container east = new Container();
	Container south = new Container();

	JTextField salesmanStartTF = new JTextField("A");

	JButton salesmanB = new JButton("Travelling Salesman Shortest Path");

	final int NODE_CREATE = 0;
	final int EDGE_FIRST = 1;
	final int EDGE_SECOND = 2;
	int state = NODE_CREATE;

	Node first = null; // holds start node

	ArrayList<ArrayList<Node>> complete_paths = new ArrayList<ArrayList<Node>>();
	ArrayList<Integer> complete_paths_costs = new ArrayList<Integer>();
	
	public GraphCreator() {
		frame.setSize(800, 600);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);

		// west
		west.setLayout(new GridLayout(3, 1));
		west.add(nodeButton); // select to place node
		nodeButton.addActionListener(this); // listen for clicky clicky
		nodeButton.setBackground(Color.GREEN); // start as selected
		west.add(edgeButton); // select to place edge
		edgeButton.addActionListener(this); // listen for clicky clicky
		edgeButton.setBackground(Color.LIGHT_GRAY); // start as not selected
		west.add(labelsTF); // labelsTF means "labels the frick"
		frame.add(west, BorderLayout.WEST); // add west things

		// east
		east.setLayout(new GridLayout(3, 1));
		east.add(firstNode);
		east.add(secondNode);
		east.add(connectedButton);
		connectedButton.addActionListener(this);
		frame.add(east, BorderLayout.EAST); // add east things

		// south
		south.setLayout(new GridLayout(1, 2));
		south.add(salesmanStartTF);
		south.add(salesmanB);
		salesmanB.addActionListener(this);
		frame.add(south, BorderLayout.SOUTH); // add south things

		panel.addMouseListener(this); // mouse clicky thing
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true); // LET THERE BE LIGHT! (let there be things on the frame)
	}
	
	public static void main(String[] args) {
		new GraphCreator();
	}

	// java is stupid and yells at you for dumb things
	@Override public void mouseClicked (MouseEvent e) {}
	@Override public void mouseEntered (MouseEvent e) {}
	@Override public void mouseExited  (MouseEvent e) {}
	@Override public void mousePressed (MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (state == NODE_CREATE) {
			panel.addNode(e.getX(), e.getY(), labelsTF.getText());
		}
		else if (state == EDGE_FIRST) {
			Node node = panel.getNode(e.getX(), e.getY());
			if (node != null) {
				first = node;
				state = EDGE_SECOND;
				node.setHighlighted(true);
			}
		}
		else if (state == EDGE_SECOND) {
			Node node = panel.getNode(e.getX(), e.getY());
			if (node != null && !first.equals(node)) {
				String s = labelsTF.getText();
				boolean valid = true;
				for (int i = 0; i < s.length(); i++) {
					if (Character.isDigit(s.charAt(i)) == false) {
						valid = false;
					}
				}
				if (valid == true) {
					first.setHighlighted(false);
					panel.addEdge(first, node, labelsTF.getText());
					first = null;
					state = EDGE_FIRST;
				}
				else {
					JOptionPane.showMessageDialog(frame, "Can only have digits in edge labels.");
				}
			}
		}
		frame.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(nodeButton)) {
			nodeButton.setBackground(Color.GREEN);
			edgeButton.setBackground(Color.LIGHT_GRAY);
			state = NODE_CREATE;
		}
		if (e.getSource().equals(edgeButton)) {
			edgeButton.setBackground(Color.GREEN);
			nodeButton.setBackground(Color.LIGHT_GRAY);
			state = EDGE_FIRST;
			panel.stopHighlighting();
			frame.repaint();
		}
		if (e.getSource().equals(connectedButton)) {
			if (panel.nodeExists(firstNode.getText()) == false) {
				JOptionPane.showMessageDialog(frame, "First node is not in your graph.");
			}
			else if (panel.nodeExists(secondNode.getText()) == false) {
				JOptionPane.showMessageDialog(frame, "Second node is not in your graph.");
			}
			else {
				Queue queue = new Queue();
				ArrayList<String> connectedList = new ArrayList<String>();
				connectedList.add(panel.getNode(firstNode.getText()).getLabel());
				ArrayList<String> edges = panel.getConnectedLabels(firstNode.getText());
				for (int i = 0; i < edges.size(); i++) {
					queue.enqueue(edges.get(i));
				}
				while (queue.isEmpty() == false) {
					String currentNode = queue.dequeue();
					if (connectedList.contains(currentNode) == false) {
						connectedList.add(currentNode);
					}
					edges = panel.getConnectedLabels(currentNode);
					for (int i = 0; i < edges.size(); i++) {
						if (connectedList.contains(edges.get(i)) == false) {
							queue.enqueue(edges.get(i));	
						}
					}
				}
				if (connectedList.contains(secondNode.getText())) {
					JOptionPane.showMessageDialog(frame, "Connected!");
				}
				else {
					JOptionPane.showMessageDialog(frame, "Not Connected.");
				}
			}
		}
		if (e.getSource().equals(salesmanB)) {
			Node start_node = panel.getNode(salesmanStartTF.getText()); // get start node

			if (start_node != null) {
				// create a new list for the path
				ArrayList<Node> start_list = new ArrayList<Node>();
				start_list.add(start_node);

				// clear output lists
				complete_paths.clear();
				complete_paths_costs.clear();

				// call recursive function to find all paths and their costs
				travelling(start_node, start_list, 0);

				// Check for the best path out of the possible paths that were found
				int index = -1; // index of the best path
				int best_cost = Integer.MAX_VALUE;
				for (int i = 0; i < complete_paths_costs.size(); i++) { // check each path
					if (complete_paths_costs.get(i) < best_cost) { // if the cost is less than the current best cost then make it the new best cost
						best_cost = complete_paths_costs.get(i); // record its cost so we can continue to check it against the other posable paths
						index = i; // set it as the best path
					}
				}

				// Tell the user about the path 
				if (index != -1) { // just in case there are no paths found or something idk
					System.out.println("=== Found A Best Path! ==="); // print cost
					System.out.println("Total Cost Of Path: " + complete_paths_costs.get(index)); // print cost
					ArrayList<Node> best = complete_paths.get(index);
					String list = new String();
					for (int i = 0; i < best.size(); i++) {
						System.out.print(best.get(i).getLabel() + (i < best.size() - 1 ? ", " : ""));
						list += best.get(i).getLabel() + (i < best.size() - 1 ? ", " : "");
					}
					System.out.println();

					JOptionPane.showMessageDialog(frame, "Found a best path: \nTotal Cost Of Path: " + complete_paths_costs.get(index) + " \nPath: " + list);
				} else { JOptionPane.showMessageDialog(frame, "No path was found. Check your nodes and edges."); }

			} else { JOptionPane.showMessageDialog(frame, "Not a valid starting node!"); } // ERROR!!!!! add a starting node plz
		}
	}
	
	public void travelling(Node node, ArrayList<Node> cur_path, int total_cost) {

		// if the current path has all the nodes in the graph, add it to the list of complete paths
		// also add the cost of the path to the list of costs
		if ( panel.nodeList.size() <= cur_path.size() ) {
			complete_paths_costs.add(total_cost);
			complete_paths.add(cur_path);
			return; // return up-stream to try other paths
		} else { // continue down the current path

			ArrayList<Edge> edge_list = panel.getConnectedEdges(node);

			for (int i = 0; i < edge_list.size(); i++) { // check all edges
				Edge e = edge_list.get(i);
				// only check edges that contact this node and if the other end of the edge is not a node that is in the current path
				if (e.getOtherEnd(node) != null && cur_path.contains(e.getOtherEnd(node)) == false) {
					// Make a duplicate temp path so we don't mess with the actual path that we are currently on. This way we can try multiple paths that branch from this one
					ArrayList<Node> temp_path = new ArrayList<Node>(cur_path);
					temp_path.add(e.getOtherEnd(node)); // add the next node we are checking to the temp path'
					travelling(e.getOtherEnd(node), temp_path, total_cost + Integer.parseInt(e.getLabel())); // recurse down the path
				}
			}

		}

	} /** END: travelling */
	
	/*
	 * Adjacency Matrix
	 * 
	 *      A   B   C
	 * A    1   1   1
	 * B    1   1   0
	 * C    1   0   1
	 * 
	 * 
	 */

}
