import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;
import net.proteanit.sql.DbUtils;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import net.proteanit.sql.DbUtils;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.*;
import javax.swing.JToggleButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AdminHome {

	private JFrame frame;
	private static JTextField id_field;
	private static JTextField typeField;
	private static JTextField seatsField;
	private static JTable table;
	private static JTextField sid_field;

	/**
	 * Launch the application.
	 * @wbp.parser.entryPoint
	 */
	public static void main() {
		adminHome();
		Connect();
		table_load();
	}
	
	//Use mySQL Connector
			static Connection connection;
			static PreparedStatement prestate;
			static ResultSet resultSet;
			
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
			    	 String displayTable = "SELECT * FROM busdetails";
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
	public static void adminHome() {
		
		JFrame frame = new JFrame("Admin Home");
		frame.getContentPane().setFont(new Font("Segoe UI Black", Font.BOLD, 24));
		frame.setBounds(100, 100, 1280, 720);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	

		
		
		JButton btnBackAd = new JButton("GO BACK");
		btnBackAd.setBackground(SystemColor.info);
		btnBackAd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				FirstMakeSelection MakeSelection = new FirstMakeSelection();
				MakeSelection.firstMakeSelection();
			}
		});
		btnBackAd.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		btnBackAd.setBounds(10, 10, 121, 47);
		frame.getContentPane().add(btnBackAd);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.controlLtHighlight);
		panel.setBorder(new TitledBorder(null, "Bus Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(39, 107, 532, 371);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel id_label = new JLabel("Bus ID");
		id_label.setFont(new Font("Verdana", Font.BOLD, 22));
		id_label.setBounds(64, 80, 108, 36);
		panel.add(id_label);
		
		JLabel seats_label = new JLabel("Total Seats");
		seats_label.setFont(new Font("Verdana", Font.BOLD, 22));
		seats_label.setBounds(64, 222, 142, 36);
		panel.add(seats_label);
		
		JLabel busType_label = new JLabel("Bus Type");
		busType_label.setFont(new Font("Verdana", Font.BOLD, 22));
		busType_label.setBounds(64, 149, 128, 36);
		panel.add(busType_label);
		
		id_field = new JTextField();
		id_field.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		id_field.setBackground(SystemColor.menu);
		id_field.setBounds(241, 80, 158, 31);
		panel.add(id_field);
		id_field.setColumns(10);
		
		typeField = new JTextField();
		typeField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		typeField.setColumns(10);
		typeField.setBackground(SystemColor.menu);
		typeField.setBounds(241, 156, 158, 31);
		panel.add(typeField);
		
		seatsField = new JTextField();
		seatsField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		seatsField.setColumns(10);
		seatsField.setBackground(SystemColor.menu);
		seatsField.setBounds(241, 229, 158, 31);
		panel.add(seatsField);
		
		JButton saveBtn = new JButton("SAVE");
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				String busID, busType, busTotalSeats;
				
				busID = id_field.getText();
				busType = typeField.getText();
				busTotalSeats = seatsField.getText();
				
				try {
						
						String insertBusDetails = "INSERT INTO busdetails(busID, busType, totalSeats) VALUES (?,?,?)";
						prestate = connection.prepareStatement(insertBusDetails);
						prestate.setString(1, busID);
						prestate.setString(2, busType);
						prestate.setString(3, busTotalSeats);
						prestate.executeUpdate();
						
						String createBusTable = "CREATE TABLE "+ busID + " (seatID VARCHAR(255), status VARCHAR(255), PRIMARY KEY(seatID))";
						prestate = connection.prepareStatement(createBusTable);
						prestate.executeUpdate();
						
						int intBusSeats = Integer.parseInt(busTotalSeats); 
						for (int i = 1; i <= intBusSeats; i++) 
						{
							String str = String.valueOf(i);
							String insertBusTable = "INSERT INTO "+ busID + " VALUES (?, ?)";
							prestate = connection.prepareStatement(insertBusTable);
							prestate.setString(1, str+"seat");
							prestate.setString(2, "AVAILABLE");
							prestate.executeUpdate();
						}
						
						String insertLocDetails = "INSERT INTO location_details(bus_id, pickup, destination, ticket_price_Rs) VALUES (?,?,?,?)";
						prestate = connection.prepareStatement(insertLocDetails);
						prestate.setString(1, busID);
						prestate.setString(2, "");
						prestate.setString(3, "");
						prestate.setInt(4, 0);
						prestate.executeUpdate();
						
						String insertStatusDetails = "INSERT INTO reservation_status(bus_id, reserved_seats, time_of_departure, time_of_reach) VALUES (?,?,?,?)";
						prestate = connection.prepareStatement(insertStatusDetails);
						prestate.setString(1, busID);
						prestate.setInt(2, 0);
						prestate.setString(3, "");
						prestate.setString(4, "");
						prestate.executeUpdate();

						JOptionPane.showMessageDialog(null, "Record Added to the Database Successfully!!!!!");
						table_load();
						          
						id_field.setText("");
						typeField.setText("");
						seatsField.setText("");
						id_field.requestFocus();
					}
					 
					catch (SQLException e1)
					{
						JOptionPane.showMessageDialog(null, "Bus ID Already Exists! Please Enter Unique ID.");
						e1.printStackTrace();
						
					}
				
				
			}
		});
		saveBtn.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		saveBtn.setBackground(Color.GREEN);
		saveBtn.setBounds(39, 516, 250, 47);
		frame.getContentPane().add(saveBtn);
		
		JButton clearBtn = new JButton("CLEAR FIELDS");
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				id_field.setText("");
				typeField.setText("");
				seatsField.setText("");
				sid_field.setText("");
				id_field.requestFocus();
				
			}
		});
		clearBtn.setForeground(Color.WHITE);
		clearBtn.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		clearBtn.setBackground(Color.RED);
		clearBtn.setBounds(299, 516, 272, 47);
		frame.getContentPane().add(clearBtn);
		
		JLabel registerTxt = new JLabel("New Bus Registration");
		registerTxt.setForeground(Color.BLACK);
		registerTxt.setFont(new Font("Times New Roman", Font.BOLD, 40));
		registerTxt.setBackground(Color.WHITE);
		registerTxt.setBounds(461, 10, 388, 69);
		frame.getContentPane().add(registerTxt);
		
		JButton viewSeatsBtn = new JButton("VIEW SEATS");
		viewSeatsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				AdminViewSeats adminViewSeats = new AdminViewSeats();
				adminViewSeats.main();
			}
		});
		viewSeatsBtn.setForeground(SystemColor.text);
		viewSeatsBtn.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		viewSeatsBtn.setBackground(SystemColor.textHighlight);
		viewSeatsBtn.setBounds(39, 585, 165, 47);
		frame.getContentPane().add(viewSeatsBtn);
		
		JScrollPane displayTable = new JScrollPane();
		displayTable.setBounds(598, 107, 644, 376);
		frame.getContentPane().add(displayTable);
		
		table = new JTable();
		displayTable.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.text);
		panel_1.setBorder(new TitledBorder(null, "Search Bus", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(600, 585, 642, 63);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel searchLabel = new JLabel("Search Bus ID");
		searchLabel.setBounds(32, 21, 156, 28);
		searchLabel.setFont(new Font("Verdana", Font.BOLD, 17));
		panel_1.add(searchLabel);
		
		sid_field = new JTextField();
		sid_field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
			          
					String searchBusID = sid_field.getText();
		 
		                prestate = connection.prepareStatement("SELECT busID,busType, totalSeats from busdetails where busID = ?");
		                prestate.setString(1, searchBusID);
		                ResultSet resultS = prestate.executeQuery();
		 
		            if(resultS.next()==true)
		            {
		              
		                String bID = resultS.getString(1);
		                String bType = resultS.getString(2);
		                String tSeats = resultS.getString(3);
		                
		                
		                id_field.setText(bID);
						typeField.setText(bType);
						seatsField.setText(tSeats);
		                
		            }  
		            else
		            {
		            	id_field.setText("");
						typeField.setText("");
						seatsField.setText("");
		            }

		        }
		catch (SQLException ex) 
				{
		          
		        }
		}
			
		});
		
		sid_field.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		sid_field.setColumns(10);
		sid_field.setBackground(SystemColor.menu);
		sid_field.setBounds(198, 22, 420, 31);
		panel_1.add(sid_field);
		
		JButton alterBtn = new JButton("ALTER EXISTING BUS");
		alterBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bID, bType, tSeats, sID;
				
				
				bID = id_field.getText();
				bType = typeField.getText();
				tSeats = seatsField.getText();
				sID  = sid_field.getText();
				
				
				if(sid_field.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Please Enter the Bus ID to Alter Bus.");
					sid_field.requestFocus();
				}
				else
				{
				
				try {
					prestate = connection.prepareStatement("DROP TABLE " + sID);
					prestate.executeUpdate();
					
					
				prestate = connection.prepareStatement("UPDATE busdetails set busID = ?, busType = ?, totalSeats = ? where busID =?");
				prestate.setString(1, bID);
				prestate.setString(2, bType);
				prestate.setString(3, tSeats);
				prestate.setString(4, sID);
				prestate.executeUpdate();
				
				
				String createBusTable = "CREATE TABLE "+ bID + " (seatID VARCHAR(255), status VARCHAR(255), PRIMARY KEY (seatID))";
				prestate = connection.prepareStatement(createBusTable);
				prestate.executeUpdate();
				
				int intBusSeats = Integer.parseInt(tSeats); 
				for (int i = 1; i <= intBusSeats; i++) 
				{
					String str = String.valueOf(i);
					String insertBusTable = "INSERT INTO "+ bID + " VALUES (?, ?)";
					prestate = connection.prepareStatement(insertBusTable);
					prestate.setString(1, str+"seat");
					prestate.setString(2, "AVAILABLE");
					prestate.executeUpdate();
				}
				
				
				prestate = connection.prepareStatement("UPDATE location_details set bus_id = ? where bus_id =?");
				prestate.setString(1, bID);
				prestate.setString(2, sID);
				prestate.executeUpdate();
				
				prestate = connection.prepareStatement("UPDATE reservation_status set bus_id = ? where bus_id =?");
				prestate.setString(1, bID);
				prestate.setString(2, sID);
				prestate.executeUpdate();
				
				String getSeatID = "SELECT seat_id FROM ticket_details WHERE bus_id = ?";
				prestate = connection.prepareStatement(getSeatID);
				prestate.setString(1, sID);
				ResultSet resultSe = prestate.executeQuery();
				
				String getPhone = "SELECT phone_number FROM ticket_details WHERE bus_id = ?";
				prestate = connection.prepareStatement(getPhone);
				prestate.setString(1, sID);
				ResultSet resultS = prestate.executeQuery();
				
				if((resultSe.next() == true) && (resultS.next() == true))
				{
					
					
					prestate = connection.prepareStatement("UPDATE ticket_details SET "
							+ "bus_id = ? where bus_id =?");
					prestate.setString(1, bID);
					prestate.setString(2, sID);
					prestate.executeUpdate();
					
				}

				
				JOptionPane.showMessageDialog(null, "Record " +bID +" Updated Successfully!!!!!");
				table_load();
				          
				            id_field.setText("");
				            typeField.setText("");
				            seatsField.setText("");
				            id_field.requestFocus();
				}
				 
				            catch (SQLException e1) 
				{
				            	JOptionPane.showMessageDialog(null, "Record doesn't exist!!");
				            	 e1.printStackTrace();
				}
				}
			}
		});
		alterBtn.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		alterBtn.setBackground(new Color(255, 218, 185));
		alterBtn.setBounds(600, 516, 327, 47);
		frame.getContentPane().add(alterBtn);
		
		JButton deleteBtn = new JButton("DELETE");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		         String sID;
		         sID  = sid_field.getText();
		         
		         if(sid_field.getText().isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Please Enter the Bus ID to Alter Bus.");
						sid_field.requestFocus();
					}
					else
					{

		         try {
		        	 	prestate = connection.prepareStatement("DROP TABLE " + sID);
						prestate.executeUpdate();
						
		         prestate = connection.prepareStatement("DELETE FROM busdetails where busID =?");
		         prestate.setString(1, sID);
		         prestate.executeUpdate();
		         
		         prestate = connection.prepareStatement("DELETE FROM location_details where bus_id =?");
		         prestate.setString(1, sID);
		         prestate.executeUpdate();
		         
		         prestate = connection.prepareStatement("DELETE FROM reservation_status where bus_id =?");
		         prestate.setString(1, sID);
		         prestate.executeUpdate();
		         
		         prestate = connection.prepareStatement("DELETE FROM ticket_details where bus_id =?");
		         prestate.setString(1, sID);
		         prestate.executeUpdate();
		         
		         
		         JOptionPane.showMessageDialog(null, "Record " + sID +" Deleted Successfully!!!!!");
		         table_load();
		                   
		            id_field.setText("");
		            typeField.setText("");
		            seatsField.setText("");
		            id_field.requestFocus();
		         }
		          
		         catch (SQLException e1) 
		         {
		        	 JOptionPane.showMessageDialog(null, "Record doesn't exist!!");
		         }
	}
			}
		});
		deleteBtn.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		deleteBtn.setBackground(new Color(255, 218, 185));
		deleteBtn.setBounds(937, 516, 305, 47);
		frame.getContentPane().add(deleteBtn);
		
		JButton add_location = new JButton("ADD LOCATION");
		add_location.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				AdminLocation adminLocation = new AdminLocation();
				adminLocation.main();
			}
		});
		add_location.setForeground(Color.WHITE);
		add_location.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		add_location.setBackground(SystemColor.textHighlight);
		add_location.setBounds(215, 585, 181, 47);
		frame.getContentPane().add(add_location);
		
		JButton setStatusBtn = new JButton("SET STATUS");
		setStatusBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				AdminSetStatus adminSeatStatus = new AdminSetStatus();
				adminSeatStatus.main();
			}
		});
		setStatusBtn.setForeground(Color.WHITE);
		setStatusBtn.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		setStatusBtn.setBackground(SystemColor.textHighlight);
		setStatusBtn.setBounds(406, 585, 165, 47);
		frame.getContentPane().add(setStatusBtn);
		
		
		
	}	
	
	
	
	
	
}
