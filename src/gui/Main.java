package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import models.Arc;
import models.Graphe;
import models.Noeud;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import oracle.jdbc.driver.OracleDriver;
import javax.swing.JTextArea;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.lang3.StringUtils;


import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout2;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

import java.awt.Font;
import java.awt.Paint;
import java.awt.Stroke;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JSeparator;

import java.awt.BasicStroke;
import java.awt.Choice;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Main extends JFrame{

	JFrame frame;
	public JPanel panel_4 = new JPanel();
	static LinkedList<Noeud> noeuds = new LinkedList<Noeud>();
	static LinkedList<Arc> arcs = new LinkedList<Arc>();
	static Graphe graphe;
	static boolean orientation;
	public LinkedList<Noeud> NoeudList = new LinkedList<Noeud>();
	public LinkedList<Arc> ArcList = new LinkedList<Arc>();
	JButton button_1 = new JButton("Detection du cycle");
	static JTextArea logField = new JTextArea();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Main window = new Main();
					window.frame.setLocationRelativeTo(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}




	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 0, 1093, 750);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 153));
		panel.setBounds(-11, 0, 437, 711);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		panel_4.setBounds(436, 155, 631, 500);
		frame.getContentPane().add(panel_4);

		logField.setBackground(new Color(102, 102, 102));
		logField.setForeground(new Color(255, 255, 255));
		logField.setBounds(436, 22, 631, 108);
		Font font = new Font("LucidaSans", Font.PLAIN, 16);
		logField.setFont(font);
		frame.getContentPane().add(logField);
		
		JLabel lblGraphtheoryAlgorithm = new JLabel("Detection du plus grand cycle : ");
		lblGraphtheoryAlgorithm.setBounds(21, 13, 393, 33);
		panel.add(lblGraphtheoryAlgorithm);
		lblGraphtheoryAlgorithm.setBackground(Color.WHITE);
		lblGraphtheoryAlgorithm.setForeground(Color.LIGHT_GRAY);
		lblGraphtheoryAlgorithm.setFont(new Font("Segoe UI", Font.BOLD, 24));
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.GRAY);
		separator.setBounds(27, 57, 358, 9);
		panel.add(separator);
		
		JLabel lblNewLabel = new JLabel("Choisir le graphe :");
		lblNewLabel.setBounds(80, 77, 238, 28);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		
		Choice choice = new Choice();
		choice.setBounds(80, 232, 253, 20);
		panel.add(choice);
		
		
		Choice choiceGraphe = new Choice();
		choiceGraphe.setBounds(80, 129, 253, 20);
		panel.add(choiceGraphe);
		
		LinkedList<String> grapheList = new LinkedList<String>();
		try{  
			//step1 load the driver class  
			Class.forName("oracle.jdbc.OracleDriver");  
			DriverManager.registerDriver(new OracleDriver());
			//step2 create  the connection object  
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:otmani","system","Okoiol123");  
			//step3 create the statement object  
			Statement stmt=con.createStatement();  

			//step4 execute query  
			ResultSet rs=stmt.executeQuery("select id from graph");  

			while(rs.next())  
				grapheList.add(rs.getString(1));
			
			//step5 close the connection object  
			con.close();  
			  
		}catch(Exception e){ System.out.println(e);}		  

		JLabel label = new JLabel("Layout :");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Segoe UI", Font.BOLD, 24));
		label.setBounds(80, 179, 238, 28);
		panel.add(label);
		
		JButton button = new JButton("Valider");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NoeudList.clear();
				ArcList.clear();


				try{  
					//step1 load the driver class  
					Class.forName("oracle.jdbc.OracleDriver");  
					System.out.println("validation");
					DriverManager.registerDriver(new OracleDriver());
					//step2 create  the connection object  
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:otmani","system","Okoiol123");  
					//step3 create the statement object  
					Statement stmt=con.createStatement();  
					String graphid = choiceGraphe.getSelectedItem();
					//step4 execute query  
					String sqlcommande = "select noeudid from graph_noeud where graphid="+graphid;
					ResultSet rs=stmt.executeQuery(sqlcommande);  
					LinkedList<Integer> arcid = new LinkedList<Integer>();
					while(rs.next()) {
						NoeudList.add(new Noeud(Integer.parseInt(rs.getString(1)),rs.getString(1)));
					}
					
					String sqlcommande2 = "select arcid from graph_arc where graphid="+graphid;
					ResultSet rs2=stmt.executeQuery(sqlcommande2);  

					while(rs2.next()) {
						//ArcList.add(new Arc(Integer.parseInt(rs.getString(1)),rs.getString(1)));
						arcid.add(rs2.getInt(1));
					}
					
					String sqlcommande3 = "select * from arc where id in ("+StringUtils.join(arcid, ',')+")";
					ResultSet rs3=stmt.executeQuery(sqlcommande3);  

					while(rs3.next()) {
						ArcList.add(new Arc(Integer.parseInt(rs3.getString(1)),Integer.parseInt(rs3.getString(3)),Integer.parseInt(rs3.getString(4))));
					}
					

	
					//step5 close the connection object  
					con.close();  
					  
				}catch(Exception e){ System.out.println(e);}
				panel_4.removeAll();

				
				DrawingGraph g = new DrawingGraph(ArcList,NoeudList,true);
				
				Layout<Integer, String> layout;
				if(choice.getSelectedItem().equals("FRLayout")) {
					layout = new FRLayout<>(g.getGraph());
				}else if(choice.getSelectedItem().equals("CircleLayout")) {
					layout = new CircleLayout<>(g.getGraph());
				}else if(choice.getSelectedItem().equals("SpringLayout")){
					layout = new SpringLayout<>(g.getGraph());
				}else{
					layout = new SpringLayout2<>(g.getGraph());
				}

		        layout.setSize(new Dimension(480, 480));
				BasicVisualizationServer<Integer,String> vv = new BasicVisualizationServer<Integer,String>(layout);
		        vv.setPreferredSize(new Dimension(511, 511));       
		        // Setup up a new vertex to paint transformer...
		        Transformer<Integer,Paint> vertexPaint = new Transformer<Integer,Paint>() {
		            @Override
					public Paint transform(Integer i) {
		            	 return Color.RED;
					}
		        };  
		        // Set up a new stroke Transformer for the edges
		        float dash[] = {10.0f};
		        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
		             BasicStroke.JOIN_MITER, 10.0f, dash, 1.0f);
		        Transformer<String, Stroke> edgeStrokeTransformer = new Transformer<String, Stroke>() {
		            @Override
					public Stroke transform(String s) {
		                return edgeStroke;
		            }
		        };
		        
		        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
		        vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
		        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR); 
		        panel_4.add(vv);
				setVisible(false);
				frame.setVisible(true);
				button_1.setEnabled(true);

			}
			
		});
		button.setBounds(147, 299, 97, 25);
		panel.add(button);
		
		button_1.setEnabled(false);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CycleDetection.run(NoeudList, ArcList);
					List<List<String>> cycle = CycleDetection.run(NoeudList, ArcList);
					List<String> PlusGrandCycle = null;
					LinkedList<String> PlusGrandCycleList = new LinkedList<String>();
					int max = 0;
					for(List<String> list : cycle) {
							if(list.size() > max) {
								max = list.size();
								PlusGrandCycle = list;
								if(PlusGrandCycleList.size() != 0)
									PlusGrandCycleList.clear();
								PlusGrandCycleList.add(list.toString());
								System.out.println(list.toString());

							}else if (max == list.size()) {
								PlusGrandCycleList.add(list.toString());
								System.out.println(list.toString());
							}
					}		
					
					if(cycle.isEmpty()) {
						logField.setText("Ce graphe ne contient aucun cycle");
					}else {
							logField.setText(" Plus Grand Cycle :\n");
							for(String list : PlusGrandCycleList) {
									logField.setText(logField.getText()+"  " +list + "\n"  );
							}
					/** test **/
					
				    /**end test **/
					}
			}
		});
		button_1.setBounds(127, 364, 140, 25);
		panel.add(button_1);
		choice.add("FRLayout");
		choice.add("CircleLayout");
		choice.add("SpringLayout");
		choice.add("SpringLayout2");
		
		for(String s : grapheList) {
			choiceGraphe.add(s);
		}
	}
	


	public LinkedList<Noeud> getNoeuds() {
		return noeuds;
	}

	public void setNoeuds(LinkedList<Noeud> noeuds) {
		Main.noeuds = noeuds;
	}

	public LinkedList<Arc> getArcs() {
		return arcs;
	}

	public void setArcs(LinkedList<Arc> arcs) {
		Main.arcs = arcs;
	}
}
