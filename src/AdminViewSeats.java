import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;

import net.proteanit.sql.DbUtils;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AdminViewSeats {

	

	/**
	 * Launch the application.
	 * @wbp.parser.entryPoint
	 */
	public static void main() {
		AdminViewSeats();
		Connect();
		
	}
	
	
		//Use mySQL Connector Variables
		static Connection connection;
		static PreparedStatement prestate;
		static ResultSet resultSet;
		
		
		//Variables
		private static JTextField searchField;
		private static JLabel id_label;
		private static JTable table;
		
		
		//Use mySQL Connector
		public static void Connect()
	    {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/busreservationsystem", "root","");
	            
	        }
	        catch (ClassNotFoundException ex) 
	        {
	          ex.printStackTrace();
	        }
	        catch (SQLException ex) 
	        {
	        	   ex.printStackTrace();
	        }

	    }
		
		
		

	/**
	 * Create the application.
	 */
	public static void AdminViewSeats() {
		JFrame frame = new JFrame("View Seats");
		frame.setBounds(100, 100, 1280, 720);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnBack = new JButton("GO BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				AdminHome adminHome = new AdminHome();
				adminHome.main();
			}
		});
		btnBack.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		btnBack.setBackground(SystemColor.info);
		btnBack.setBounds(10, 10, 121, 47);
		frame.getContentPane().add(btnBack);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(null, "Search Bus", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(287, 569, 757, 63);
		frame.getContentPane().add(panel_1);
		
		JLabel searchLabel = new JLabel("Search Bus ID");
		searchLabel.setFont(new Font("Verdana", Font.BOLD, 17));
		searchLabel.setBounds(32, 21, 156, 28);
		panel_1.add(searchLabel);
		
		searchField = new JTextField();
		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
                try {
                	
                	String searchBusID = searchField.getText();
                	
					prestate = connection.prepareStatement("SELECT * FROM "+ searchBusID);
	                ResultSet resultS = prestate.executeQuery();
	                
	                if(resultS.next()==true)
		            {
		           
		                id_label.setText(searchBusID + " Exist!");      
		         
		   		    	String seatsTable = "SELECT * FROM "+ searchBusID;
		   			    prestate = connection.prepareStatement(seatsTable);
		   			    resultSet = prestate.executeQuery();
		   			    table.setModel(DbUtils.resultSetToTableModel(resultSet));
		            }  
		            else
		            {
		            	id_label.setText("Bus Doesn't Exist!"); 
		            	
		            	DefaultTableModel model = new DefaultTableModel();
						table.setModel(model);
		                
		            }
				} 
                
                catch (SQLException e1) 
                {
					id_label.setText("Bus Doesn't Exist!");
					
					DefaultTableModel model = new DefaultTableModel();
					table.setModel(model);
				}
                
				
				
			}
		});
		searchField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		searchField.setColumns(10);
		searchField.setBackground(SystemColor.menu);
		searchField.setBounds(198, 18, 535, 31);
		panel_1.add(searchField);
		
		JLabel seatsTxt = new JLabel("View Seat Status");
		seatsTxt.setForeground(Color.BLACK);
		seatsTxt.setFont(new Font("Times New Roman", Font.BOLD, 40));
		seatsTxt.setBackground(Color.WHITE);
		seatsTxt.setBounds(487, 27, 302, 69);
		frame.getContentPane().add(seatsTxt);
		
		id_label = new JLabel("Bus ID");
		id_label.setFont(new Font("Verdana", Font.BOLD, 22));
		id_label.setBounds(487, 125, 335, 36);
		frame.getContentPane().add(id_label);
		
		JScrollPane seatsTable = new JScrollPane();
		seatsTable.setBounds(287, 190, 757, 342);
		frame.getContentPane().add(seatsTable);
		
		table = new JTable();
		seatsTable.setViewportView(table);
		
		
		
	}
}
