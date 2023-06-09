import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AdminLocation {



	/**
	 * Launch the application.
	 * @wbp.parser.entryPoint
	 */
	public static void main() {
		AdminLocation();
		Connect();
		table_load();
		
	}
	
	//Use mySQL Connector Variables
	static Connection connection;
	static PreparedStatement prestate;
	static ResultSet resultSet;
	
	//Variable
	private static JTextField searchField;
	private static JTextField pickupField;
	private static JTextField destField;
	private static JTextField ticketField;
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
	    	 String displayTable = "SELECT * FROM location_details";
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
	public static void AdminLocation() {
		JFrame frame = new JFrame("Add Location");
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
		
		JLabel locTxt = new JLabel("Add Location / Price");
		locTxt.setForeground(Color.BLACK);
		locTxt.setFont(new Font("Times New Roman", Font.BOLD, 40));
		locTxt.setBackground(Color.WHITE);
		locTxt.setBounds(422, 22, 388, 69);
		frame.getContentPane().add(locTxt);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(null, "Search Bus", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(483, 577, 757, 63);
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
		 
		                prestate = connection.prepareStatement("SELECT bus_id, pickup, destination, ticket_price_Rs from location_details where bus_id = ?");
		                prestate.setString(1, searchBusID);
		                ResultSet resultS = prestate.executeQuery();
		 
		            if(resultS.next()==true)
		            {
		              
		                String bID = resultS.getString(1);
		                String pick = resultS.getString(2);
		                String desti = resultS.getString(3);
		                String tPrice = resultS.getString(4);
		                
		                
		                id_label.setText(bID + " Exist!"); 
		                pickupField.setText(pick);
		                destField.setText(desti);
		                ticketField.setText(tPrice);
		                
		            }  
		            else
		            {
		            	id_label.setText("Doesn't Exist!"); 
		                pickupField.setText("");
		                destField.setText("");
		                ticketField.setText("");
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
		searchField.setBounds(198, 22, 535, 31);
		panel_1.add(searchField);
		
		JButton updateLocBtn = new JButton("UPDATE ALL");
		updateLocBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String pickUp, destination, ticketPrice, sID;
				
				
				
				pickUp = pickupField.getText().toLowerCase();
				destination = destField.getText().toLowerCase();
				ticketPrice = ticketField.getText();
				sID  = searchField.getText();
				
				try
				{
					prestate = connection.prepareStatement("UPDATE location_details set pickup = ?, destination = ?, ticket_price_Rs = ? where bus_id = ?");
					prestate.setString(1, pickUp);
					prestate.setString(2, destination);
					prestate.setString(3, ticketPrice);
					prestate.setString(4, sID);
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
		updateLocBtn.setBounds(10, 508, 451, 47);
		frame.getContentPane().add(updateLocBtn);
		
		JScrollPane locationTable = new JScrollPane();
		locationTable.setBounds(483, 108, 757, 447);
		frame.getContentPane().add(locationTable);
		
		table = new JTable();
		locationTable.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Add Location", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 108, 451, 371);
		frame.getContentPane().add(panel);
		
		id_label = new JLabel("Bus ID");
		id_label.setFont(new Font("Verdana", Font.BOLD, 22));
		id_label.setBounds(64, 76, 335, 36);
		panel.add(id_label);
		
		JLabel destination_label = new JLabel("Destination");
		destination_label.setFont(new Font("Verdana", Font.BOLD, 22));
		destination_label.setBounds(64, 221, 158, 36);
		panel.add(destination_label);
		
		JLabel pickup_label = new JLabel("PickUp");
		pickup_label.setFont(new Font("Verdana", Font.BOLD, 22));
		pickup_label.setBounds(64, 154, 128, 36);
		panel.add(pickup_label);
		
		pickupField = new JTextField();
		pickupField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		pickupField.setColumns(10);
		pickupField.setBackground(SystemColor.menu);
		pickupField.setBounds(241, 159, 158, 31);
		panel.add(pickupField);
		
		destField = new JTextField();
		destField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		destField.setColumns(10);
		destField.setBackground(SystemColor.menu);
		destField.setBounds(241, 226, 158, 31);
		panel.add(destField);
		
		JLabel ticket_label = new JLabel("Ticket Price");
		ticket_label.setFont(new Font("Verdana", Font.BOLD, 22));
		ticket_label.setBounds(64, 295, 158, 36);
		panel.add(ticket_label);
		
		ticketField = new JTextField();
		ticketField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		ticketField.setColumns(10);
		ticketField.setBackground(SystemColor.menu);
		ticketField.setBounds(241, 300, 158, 31);
		panel.add(ticketField);
		
		JButton clearBtn = new JButton("CLEAR FIELDS");
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				id_label.setText("Bus ID");
				pickupField.setText("");
                destField.setText("");
                ticketField.setText("");
                searchField.setText("");
                searchField.setText("");
                searchField.requestFocus();
			}
		});
		clearBtn.setForeground(Color.WHITE);
		clearBtn.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		clearBtn.setBackground(Color.RED);
		clearBtn.setBounds(10, 593, 451, 47);
		frame.getContentPane().add(clearBtn);
	}
}
