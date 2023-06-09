import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PassHome {

	private JFrame frame;
	private static JTextField dropField;
	private static JTextField pickField;

	/**
	 * Launch the application.
	 * @wbp.parser.entryPoint
	 */
	public static void main() {
		PassHome();
		Connect();
	}
	
	
	//Use mySQL Connector Variables
	static Connection connection;
	static PreparedStatement prestate;
	static ResultSet resultSet;
	
	//Variables
	private static JTextField searchField;
	private static JTextField busIDField;
	private static JTextField seatIDField;
	private static JLabel seatAvailable_label;
	private static JTable table, seatTable;
	
	
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
	public static void PassHome() {
		JFrame frame = new JFrame("Passenger Home");
		frame.getContentPane().setBackground(SystemColor.menu);
		frame.setBounds(100, 100, 1280, 720);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnBackAd = new JButton("GO BACK");
		btnBackAd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				FirstMakeSelection MakeSelection = new FirstMakeSelection();
				MakeSelection.firstMakeSelection();
			}
		});
		btnBackAd.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		btnBackAd.setBackground(SystemColor.info);
		btnBackAd.setBounds(10, 10, 121, 47);
		frame.getContentPane().add(btnBackAd);
		
		JLabel checkAvailabilityTxt = new JLabel("Check Bus Availability");
		checkAvailabilityTxt.setForeground(Color.BLACK);
		checkAvailabilityTxt.setFont(new Font("Times New Roman", Font.BOLD, 40));
		checkAvailabilityTxt.setBackground(Color.WHITE);
		checkAvailabilityTxt.setBounds(416, 27, 407, 69);
		frame.getContentPane().add(checkAvailabilityTxt);
		
		JScrollPane displayTable = new JScrollPane();
		displayTable.setBounds(10, 106, 809, 376);
		frame.getContentPane().add(displayTable);
		
		table = new JTable();
		displayTable.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Add Details", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 492, 595, 181);
		frame.getContentPane().add(panel);
		
		JLabel destination_label = new JLabel("Drop Location");
		destination_label.setFont(new Font("Verdana", Font.BOLD, 22));
		destination_label.setBounds(22, 105, 208, 36);
		panel.add(destination_label);
		
		JLabel pickup_label = new JLabel("PickUp Location");
		pickup_label.setFont(new Font("Verdana", Font.BOLD, 22));
		pickup_label.setBounds(22, 42, 208, 36);
		panel.add(pickup_label);
		
		dropField = new JTextField();
		dropField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		dropField.setColumns(10);
		dropField.setBackground(SystemColor.menu);
		dropField.setBounds(255, 112, 158, 31);
		panel.add(dropField);
		
		pickField = new JTextField();
		pickField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		pickField.setColumns(10);
		pickField.setBackground(SystemColor.menu);
		pickField.setBounds(255, 49, 158, 31);
		panel.add(pickField);
		
		JButton searchBack = new JButton("SEARCH");
		searchBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String pickUpLocation, dropLocation;
				
				pickUpLocation = pickField.getText().toLowerCase();
				dropLocation = dropField.getText().toLowerCase();

				if(pickField.getText().isEmpty() && dropField.getText().isEmpty())
				{
					
					JOptionPane.showMessageDialog(null, "Please Enter PickUp And Drop Location to Search.");
				}
				else
				{
					try {
							String displayTable = "SELECT bd.busID, bd.busType, ld.pickup, ld.destination,"
									+ "bd.totalSeats, rs.reserved_seats, rs.time_of_departure, "
									+ "rs.time_of_reach, "
									+ "ld.ticket_price_Rs FROM busdetails bd INNER JOIN location_details ld "
									+ "ON bd.busID = ld.bus_id AND ld.pickup = ? AND ld.destination=? "
									+ "INNER JOIN reservation_status rs "
									+ "ON bd.busID = rs.bus_id";
							
							
			   			    prestate = connection.prepareStatement(displayTable);
			   			    prestate.setString(1, pickUpLocation);
			   			    prestate.setString(2, dropLocation);
			   			    ResultSet resultS = prestate.executeQuery();
			   			    
			   			 if(resultS.next()==true)
				         {
			   				String DisplayTable = "SELECT bd.busID, bd.busType, ld.pickup, ld.destination,"
									+ "bd.totalSeats, rs.reserved_seats, rs.time_of_departure, "
									+ "rs.time_of_reach, "
									+ "ld.ticket_price_Rs FROM busdetails bd INNER JOIN location_details ld "
									+ "ON bd.busID = ld.bus_id AND ld.pickup = ? AND ld.destination=? "
									+ "INNER JOIN reservation_status rs "
									+ "ON bd.busID = rs.bus_id";
			   			    prestate = connection.prepareStatement(DisplayTable);
			   			    prestate.setString(1, pickUpLocation);
			   			    prestate.setString(2, dropLocation);
			   			    resultSet = prestate.executeQuery();
			   			    
			   				table.setModel(DbUtils.resultSetToTableModel(resultSet));
				         }
			   			 else
			   			 {
			   				DefaultTableModel model = new DefaultTableModel();
							table.setModel(model);
							JOptionPane.showMessageDialog(null, "Entered Details doesn't Exist!!!!");
			   			 }
			   			 
			   			    
						
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Entered Details doesn't Exist!!!!");
						e1.printStackTrace();
					}
					
				}
			}
		});
		searchBack.setForeground(SystemColor.text);
		searchBack.setBounds(453, 69, 121, 47);
		panel.add(searchBack);
		searchBack.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		searchBack.setBackground(SystemColor.textHighlight);
		
		JButton cancelBtn = new JButton("CANCEL TICKETS");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				PassCancelTicket passCancelTicket = new PassCancelTicket();
				passCancelTicket.main();	
			}
		});
		cancelBtn.setBounds(615, 492, 204, 47);
		frame.getContentPane().add(cancelBtn);
		cancelBtn.setForeground(Color.WHITE);
		cancelBtn.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		cancelBtn.setBackground(SystemColor.textHighlight);
		
		JScrollPane displayTable_1 = new JScrollPane();
		displayTable_1.setBounds(841, 151, 415, 388);
		frame.getContentPane().add(displayTable_1);
		
		seatTable = new JTable();
		displayTable_1.setViewportView(seatTable);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Check Availability", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(615, 549, 641, 63);
		frame.getContentPane().add(panel_1);
		
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
		           
	                	seatAvailable_label.setText(searchBusID + " Exist!");      
		         
		   		    	String seatsTable = "SELECT * FROM "+ searchBusID;
		   			    prestate = connection.prepareStatement(seatsTable);
		   			    resultSet = prestate.executeQuery();
		   			    seatTable.setModel(DbUtils.resultSetToTableModel(resultSet));
		            }  
		            else
		            {
		            	seatAvailable_label.setText("Bus Doesn't Exist!"); 
		            	
		            	DefaultTableModel model = new DefaultTableModel();
		            	seatTable.setModel(model);
		                
		            }
				} 
                
                catch (SQLException e1) 
                {
                	seatAvailable_label.setText("Bus Doesn't Exist!");
					
					DefaultTableModel model = new DefaultTableModel();
					seatTable.setModel(model);
				}
			}
		});
		searchField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		searchField.setColumns(10);
		searchField.setBackground(SystemColor.menu);
		searchField.setBounds(376, 14, 255, 31);
		panel_1.add(searchField);
		
		JLabel enterLabel = new JLabel("Enter Bus ID To Check");
		enterLabel.setBounds(42, 10, 215, 37);
		panel_1.add(enterLabel);
		enterLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		
		JLabel lblCheckSeatsAvailability = new JLabel("Seats Availability");
		lblCheckSeatsAvailability.setBounds(225, 10, 246, 37);
		panel_1.add(lblCheckSeatsAvailability);
		lblCheckSeatsAvailability.setFont(new Font("Verdana", Font.BOLD, 14));
		
		JLabel busid_label = new JLabel("Bus ID");
		busid_label.setFont(new Font("Verdana", Font.BOLD, 18));
		busid_label.setBounds(625, 622, 96, 36);
		frame.getContentPane().add(busid_label);
		
		JLabel seatid_label = new JLabel("Seat ID");
		seatid_label.setFont(new Font("Verdana", Font.BOLD, 18));
		seatid_label.setBounds(841, 622, 96, 36);
		frame.getContentPane().add(seatid_label);
		
		busIDField = new JTextField();
		busIDField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		busIDField.setColumns(10);
		busIDField.setBackground(SystemColor.menu);
		busIDField.setBounds(711, 628, 96, 31);
		frame.getContentPane().add(busIDField);
		
		seatIDField = new JTextField();
		seatIDField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		seatIDField.setColumns(10);
		seatIDField.setBackground(SystemColor.menu);
		seatIDField.setBounds(931, 628, 89, 31);
		frame.getContentPane().add(seatIDField);
		
		JButton bookBtn = new JButton("BOOK TICKET");
		bookBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String busID, seatID;
				
				busID = busIDField.getText();
				seatID = seatIDField.getText();

				if(busIDField.getText().isEmpty() && seatIDField.getText().isEmpty())
				{
					
					JOptionPane.showMessageDialog(null, "Please Enter Bus ID And Seat ID to Book Ticket.");
				}
				else
				{
					try 
					{
						String displayTable = "SELECT seatID,status FROM " + busID
								+ " WHERE seatID = ? AND status = 'AVAILABLE'";
						
		   			    prestate = connection.prepareStatement(displayTable);
		   			    prestate.setString(1, seatID);
		   			    ResultSet resultS = prestate.executeQuery();
		   			    
		   			 if(resultS.next()==true)
			         {
		   				frame.setVisible(false);
		   				PassBookTicket passBookTicket = new PassBookTicket();
		   				passBookTicket.main(busID, seatID);
		   				
			         }
		   			 else
		   			 {
		   				JOptionPane.showMessageDialog(null, "Please Check the Availability of Seat ID.");
		   			 }
						
						
						
					}
					catch(Exception e1)
					{
						JOptionPane.showMessageDialog(null, "Please Check the Availability of Bus ID.");
					}
				}
			}
		});
		
		
		bookBtn.setForeground(Color.WHITE);
		bookBtn.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		bookBtn.setBackground(SystemColor.textHighlight);
		bookBtn.setBounds(1063, 622, 174, 47);
		frame.getContentPane().add(bookBtn);
		
		seatAvailable_label = new JLabel("Seats");
		seatAvailable_label.setFont(new Font("Verdana", Font.BOLD, 22));
		seatAvailable_label.setBounds(841, 105, 415, 36);
		frame.getContentPane().add(seatAvailable_label);
		
		
	}
}
