package screen;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import models.Graph;
import models.Link;
import models.Node;

import javax.swing.JTable;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;

public class LinkScreen extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final int ASTAR = 1;
	private static final int GREED = 2;
	
	private JPanel contentPane;
	private JTable tableFrom;
	private JTable tableTo;
	private JButton btnMakeOneWay;
	private JButton btnMakeTwoWay;

	private NodeTableTM ntm;
	private JTextField tfLength;
	private JLabel lblWeight;

	private JButton btnAddNewNode;
	
	private Graph graph;
	private NodeAddScreen nodeAddScreen;
	private JButton btnFindPath;
	
	private JTextPane tpLinks;
	private JTextPane tpResult;
	private JTextField tfFirstMarker;
	private JTextField tfSecondMarker;
	
	private int searchAlgorithm = ASTAR;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LinkScreen frame = new LinkScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public LinkScreen() {
		setTitle("Graph");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 686, 533);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tableFrom = new JTable();
		tableFrom.setBounds(10, 56, 260, 190);
		contentPane.add(tableFrom);
		
		tableTo = new JTable();
		tableTo.setBounds(400, 56, 260, 190);
		contentPane.add(tableTo);
		
		btnMakeOneWay = new JButton("Make One Way Link");
		btnMakeOneWay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graph.getNodes().get(tableFrom.getSelectedRow()).getLinks().add(new Link(Integer.parseInt(tfLength.getText()), graph.getNodes().get(tableTo.getSelectedRow())));
				tpLinks.setText(tpLinks.getText() + "\n" + graph.getNodes().get(tableFrom.getSelectedRow()).getName() + " => " + graph.getNodes().get(tableTo.getSelectedRow()).getName());
			}
		});
		btnMakeOneWay.setBounds(243, 413, 183, 23);
		contentPane.add(btnMakeOneWay);
		
		btnMakeTwoWay = new JButton("Make Two Way Link");
		btnMakeTwoWay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graph.getNodes().get(tableFrom.getSelectedRow()).getLinks().add(new Link(Integer.parseInt(tfLength.getText()), graph.getNodes().get(tableTo.getSelectedRow())));
				graph.getNodes().get(tableTo.getSelectedRow()).getLinks().add(new Link(Integer.parseInt(tfLength.getText()), graph.getNodes().get(tableFrom.getSelectedRow())));
				tpLinks.setText(tpLinks.getText() + "\n" + graph.getNodes().get(tableFrom.getSelectedRow()).getName() + " <=> " + graph.getNodes().get(tableTo.getSelectedRow()).getName());
			}
		});
		btnMakeTwoWay.setBounds(243, 447, 183, 23);
		contentPane.add(btnMakeTwoWay);
		
		tfLength = new JTextField();
		tfLength.setBounds(300, 77, 73, 20);
		contentPane.add(tfLength);
		tfLength.setColumns(10);
		
		lblWeight = new JLabel("Length");
		lblWeight.setBounds(313, 56, 46, 14);
		contentPane.add(lblWeight);
		
		btnAddNewNode = new JButton("Add new Node");
		btnAddNewNode.setBounds(46, 413, 171, 23);
		contentPane.add(btnAddNewNode);
		
		btnFindPath = new JButton("Find Path");
		btnFindPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean result = false;
				tpResult.setText("");
				tpResult.setText(tpResult.getText() + "\nPath:\n"); 
				Node startNode = graph.getNodes().get(tableFrom.getSelectedRow());
				Node endNode = graph.getNodes().get(tableTo.getSelectedRow());
				Node firstMarkerNode = null;
				Node secondMarkerNode = null;
				if(!tfFirstMarker.getText().isEmpty()) {
					int id1 = graph.getNodeIdByName(tfFirstMarker.getText());
					if (id1 >= 0) {
						firstMarkerNode = graph.getNodes().get(id1);
					}
				}
				if(!tfSecondMarker.getText().isEmpty()) {
					int id2 = graph.getNodeIdByName(tfSecondMarker.getText());
					if (id2 >= 0) {
						secondMarkerNode = graph.getNodes().get(id2);
					}
				}
				if (firstMarkerNode != null && secondMarkerNode != null) {
					if (searchAlgorithm == ASTAR) {
						result = graph.searchA(startNode, firstMarkerNode);
					}
					if (searchAlgorithm == GREED) {
						result = graph.searchGreed(startNode, firstMarkerNode);
					}
					if (result) {
						for (int i=graph.getPath().size() - 1; i > 0; i--) {
							tpResult.setText(tpResult.getText() + graph.getPath().get(i).getName() + " -> "); 
						}
						tpResult.setText(tpResult.getText() + graph.getPath().get(0).getName());
					}

					if (searchAlgorithm == ASTAR) {
						result = graph.searchA(firstMarkerNode, secondMarkerNode);
					}
					if (searchAlgorithm == GREED) {
						result = graph.searchGreed(firstMarkerNode, secondMarkerNode);
					}
					if (result) {
						for (int i=graph.getPath().size() - 1; i > 0; i--) {
							tpResult.setText(tpResult.getText() + graph.getPath().get(i).getName() + " -> "); 
						}
						tpResult.setText(tpResult.getText() + graph.getPath().get(0).getName());
					}

					if (searchAlgorithm == ASTAR) {
						result = graph.searchA(secondMarkerNode, endNode);
					}
					if (searchAlgorithm == GREED) {
						result = graph.searchGreed(secondMarkerNode, endNode);
					}
					if (result) {
						for (int i=graph.getPath().size() - 1; i > 0; i--) {
							tpResult.setText(tpResult.getText() + graph.getPath().get(i).getName() + " -> "); 
						}
						tpResult.setText(tpResult.getText() + graph.getPath().get(0).getName());
					}
					
				} else {
					if (searchAlgorithm == ASTAR) {
						result = graph.searchA(startNode, endNode);
					}
					if (searchAlgorithm == GREED) {
						result = graph.searchGreed(startNode, endNode);
					}
					if (result) {
						for (int i=graph.getPath().size() - 1; i > 0; i--) {
							tpResult.setText(tpResult.getText() + graph.getPath().get(i).getName() + " -> "); 
						}
						tpResult.setText(tpResult.getText() + graph.getPath().get(0).getName());
					}
				}

			}
		});
		btnFindPath.setBounds(457, 413, 171, 23);
		contentPane.add(btnFindPath);
		
		tpLinks = new JTextPane();
		tpLinks.setBounds(291, 101, 91, 144);
		contentPane.add(tpLinks);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				graph.makeTestGraph();
				updateNodes();
				//initGraph();
			}
		});
		btnClear.setBounds(10, 7, 89, 23);
		contentPane.add(btnClear);
		
		tfFirstMarker = new JTextField();
		tfFirstMarker.setBounds(222, 8, 86, 20);
		contentPane.add(tfFirstMarker);
		tfFirstMarker.setColumns(10);
		
		tfSecondMarker = new JTextField();
		tfSecondMarker.setBounds(351, 8, 86, 20);
		contentPane.add(tfSecondMarker);
		tfSecondMarker.setColumns(10);
		
		JLabel lblThrough = new JLabel("Through");
		lblThrough.setBounds(141, 11, 46, 14);
		contentPane.add(lblThrough);
		
		JRadioButton rbGreed = new JRadioButton("Greedy search");
		rbGreed.setBounds(457, 447, 109, 23);
		rbGreed.setSelected(false);
		rbGreed.setActionCommand("greed");
		rbGreed.addActionListener(this);
		contentPane.add(rbGreed);
		
		JRadioButton rbAStar = new JRadioButton("A*");
		rbAStar.setBounds(566, 447, 62, 23);
		rbAStar.setSelected(true);
		rbAStar.setActionCommand("astar");
		rbAStar.addActionListener(this);
		contentPane.add(rbAStar);
		
		ButtonGroup bgSearch = new ButtonGroup();
		bgSearch.add(rbGreed);
		bgSearch.add(rbAStar);

		initGraph();

		btnAddNewNode.setActionCommand("add_node");
		
		tpResult = new JTextPane();
		tpResult.setBounds(20, 263, 629, 116);
		contentPane.add(tpResult);;
		btnAddNewNode.addActionListener(this);
		
	}

	private void initGraph() {
		graph = new Graph();
		updateNodes();
	}
	
	public void updateNodes() {
		List<String[]> values = new ArrayList<>();

		for (Node n : graph.getNodes()) {
			values.add(new String[] {n.getName(), String.valueOf(n.getX()), String.valueOf(n.getY()), String.valueOf(n.getWeight())});
		}
		ntm = new NodeTableTM(values.toArray(new Object[][] {}));
		tableFrom.setModel(ntm);
		tableTo.setModel(ntm);
	}

	public Graph getGraph() {
		return graph;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "add_node":
			nodeAddScreen = new NodeAddScreen(this);
			nodeAddScreen.setVisible(true);
			break;
		case "greed":
			searchAlgorithm = GREED;
			break;
		case "astar":
			searchAlgorithm = ASTAR;
			break;
		default:
			break;
		}

		
	}
}
