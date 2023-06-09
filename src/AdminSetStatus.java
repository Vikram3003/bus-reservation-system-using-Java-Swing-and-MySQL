import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminSetStatus {

	

	/**
	 * Launch the application.
	 * @wbp.parser.entryPoint
	 */
	public static void main() {
		AdminSeatStatus();
		Connect();
		table_load();

	}
	
			//Use mySQL Connector Variables
			static Connection connection;
			static PreparedStatement prestate;
			static ResultSet resultSet;
			
			
			private static JTextField departTimeField;
			private static JTextField reachTimeField;
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
			
			
			//Display the Table
			public static void table_load()
		    {
			     try
			     {
			    	String displayTable = "SELECT * FROM reservation_status";
				    prestate = connection.prepareStatement(displayTable);
				    resultSet = prestate.executeQuery();
				    table.setModel(DbUtils.resultSetToTableModel(resultSet));
			     }
			     catch (SQLException e)
			     {
			    	 e.printStackTrace();
			     }
		    }
			
			

	/**
	 * Create the application.
	 */
	public static void AdminSeatStatus() {
		JFrame frame = new JFrame("Set Status");
		frame.setBounds(100, 100, 1280, 720);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnBackAd = new JButton("GO BACK");
		btnBackAd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				AdminHome adminHome = new AdminHome();
				adminHome.main();
			}
		});
		btnBackAd.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		btnBackAd.setBackground(SystemColor.info);
		btnBackAd.setBounds(10, 10, 121, 47);
		frame.getContentPane().add(btnBackAd);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Set Status", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 98, 486, 389);
		frame.getContentPane().add(panel);
		
		id_label = new JLabel("Bus ID");
		id_label.setFont(new Font("Verdana", Font.BOLD, 22));
		id_label.setBounds(87, 112, 335, 36);
		panel.add(id_label);
		
		JLabel depart_label = new JLabel("Departure Time");
		depart_label.setFont(new Font("Verdana", Font.BOLD, 22));
		depart_label.setBounds(59, 190, 212, 36);
		panel.add(depart_label);
		
		departTimeField = new JTextField();
		departTimeField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		departTimeField.setColumns(10);
		departTimeField.setBackground(SystemColor.menu);
		departTimeField.setBounds(286, 197, 158, 31);
		panel.add(departTimeField);
		
		JLabel reach_label = new JLabel("Reach Time");
		reach_label.setFont(new Font("Verdana", Font.BOLD, 22));
		reach_label.setBounds(59, 280, 158, 36);
		panel.add(reach_label);
		
		reachTimeField = new JTextField();
		reachTimeField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		reachTimeField.setColumns(10);
		reachTimeField.setBackground(SystemColor.menu);
		reachTimeField.setBounds(286, 287, 158, 31);
		panel.add(reachTimeField);
		
		JScrollPane statusTable = new JScrollPane();
		statusTable.setBounds(520, 97, 736, 471);
		frame.getContentPane().add(statusTable);
		
		table = new JTable();
		statusTable.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(null, "Search Bus", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(520, 589, 736, 63);
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
		 
		                prestate = connection.prepareStatement("SELECT bus_id, reserved_seats, time_of_departure, time_of_reach FROM reservation_status where bus_id = ?");
		                prestate.setString(1, searchBusID);
		                ResultSet resultS = prestate.executeQuery();
		 
		            if(resultS.next()==true)
		            {
		              
		                String bID = resultS.getString(1);
		                String timeDepart = resultS.getString(3);
		                String timeReach = resultS.getString(4);
		                
		                
		                id_label.setText(bID + " Exist!"); 
		                departTimeField.setText(timeDepart);
		                reachTimeField.setText(timeReach);
		                
		            }  
		            else
		            {
		            	id_label.setText("Doesn't Exist!"); 
		                departTimeField.setText("");
		                reachTimeField.setText("");
		            }

		        }
		catch (SQLException ex) 
				{
		          
		        }
			}
		});
		
		
		searchField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		searchField.setColumns(10);
		searchField.setBackground(SystemColor.menu);
		searchField.setBounds(183, 22, 526, 31);
		panel_1.add(searchField);
		
		JButton updateLocBtn = new JButton("UPDATE ALL");
		updateLocBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String timeDeparture, timeReach, sID;
				timeDeparture = departTimeField.getText();
				timeReach = reachTimeField.getText();
				sID  = searchField.getText();
				
				try
				{
					prestate = connection.prepareStatement("UPDATE reservation_status SET time_of_departure = ?, time_of_reach = ? where bus_id = ?");
					prestate.setString(1, timeDeparture);
					prestate.setString(2, timeReach);
					prestate.setString(3, sID);
					prestate.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Record " +sID +" Updated Successfully!!!!!");
		            table_load();
					
				}
				catch(Exception e1) 
				{
					e1.printStackTrace();
				}
				
			}
		});
		updateLocBtn.setForeground(Color.WHITE);
		updateLocBtn.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		updateLocBtn.setBackground(SystemColor.textHighlight);
		updateLocBtn.setBounds(10, 521, 486, 47);
		frame.getContentPane().add(updateLocBtn);
		
		JButton clearBtn = new JButton("CLEAR FIELDS");
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				id_label.setText("Bus ID");
				departTimeField.setText("");
                reachTimeField.setText("");
                searchField.setText("");
                searchField.requestFocus();
			}
		});
		clearBtn.setForeground(Color.WHITE);
		clearBtn.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		clearBtn.setBackground(Color.RED);
		clearBtn.setBounds(10, 605, 486, 47);
		frame.getContentPane().add(clearBtn);
	}

}
