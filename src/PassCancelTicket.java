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
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PassCancelTicket {

	/**
	 * Launch the application.
	 * @wbp.parser.entryPoint
	 */
	public static void main() {
		Connect();
		PassCancelTicket();
	}

	
	//Use mySQL Connector Variables
			static Connection connection;
			static PreparedStatement prestate;
			static ResultSet resultSet;
			
			//Variables
			private static JTextField phoneField;
			private static JTextField ticketField;
			private static JLabel ticket_label;
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
	public static void PassCancelTicket() {
		JFrame frame = new JFrame();
		frame.setBounds(100, 100, 1280, 720);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnBack = new JButton("GO BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				PassHome passHome = new PassHome();
				passHome.main();
			}
		});
		btnBack.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		btnBack.setBackground(SystemColor.info);
		btnBack.setBounds(10, 10, 121, 47);
		frame.getContentPane().add(btnBack);
		
		JLabel cancelTxt = new JLabel("Cancel Tickets");
		cancelTxt.setForeground(Color.BLACK);
		cancelTxt.setFont(new Font("Times New Roman", Font.BOLD, 40));
		cancelTxt.setBackground(Color.WHITE);
		cancelTxt.setBounds(496, 10, 266, 69);
		frame.getContentPane().add(cancelTxt);
		
		ticket_label = new JLabel("Tickets");
		ticket_label.setFont(new Font("Verdana", Font.BOLD, 22));
		ticket_label.setBounds(409, 99, 815, 36);
		frame.getContentPane().add(ticket_label);
		
		JScrollPane ticketsTable = new JScrollPane();
		ticketsTable.setBounds(30, 167, 1211, 342);
		frame.getContentPane().add(ticketsTable);
		
		table = new JTable();
		ticketsTable.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Phone Number", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(30, 519, 1211, 63);
		frame.getContentPane().add(panel_1);
		
		JLabel phoneNumberLabel = new JLabel("Enter Phone Number to Check Tickets Reserved:");
		phoneNumberLabel.setFont(new Font("Verdana", Font.BOLD, 17));
		phoneNumberLabel.setBounds(36, 21, 470, 28);
		panel_1.add(phoneNumberLabel);
		
		phoneField = new JTextField();
		phoneField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				

				String searchPhone = phoneField.getText();
				int phoneInteger = Integer.parseInt(searchPhone);
				
				try {
					prestate = connection.prepareStatement("SELECT * FROM ticket_details WHERE phone_number = ?");
					prestate.setInt(1, phoneInteger);
					ResultSet resultS = prestate.executeQuery();
					
					if(resultS.next() == true)
					{
						ticket_label.setText("Tickets Reserved Using " + searchPhone + " .");
						
						
						String ticketTable = "SELECT * FROM ticket_details WHERE phone_number = ?";
		   			    prestate = connection.prepareStatement(ticketTable);
		   			    prestate.setInt(1, phoneInteger);
		   			    resultSet = prestate.executeQuery();
		   			    table.setModel(DbUtils.resultSetToTableModel(resultSet));
					}
					else
					{
						ticket_label.setText("Tickets not Reserved using the Phone Number You Entered."); 
		            	
		            	DefaultTableModel model = new DefaultTableModel();
						table.setModel(model);
					}
				} 
				catch (SQLException e1) {
					
					e1.printStackTrace();
				}

			}
		});
		phoneField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		phoneField.setColumns(10);
		phoneField.setBackground(SystemColor.menu);
		phoneField.setBounds(545, 18, 621, 31);
		panel_1.add(phoneField);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Cancel Ticket", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1_1.setBackground(Color.WHITE);
		panel_1_1.setBounds(30, 610, 1211, 63);
		frame.getContentPane().add(panel_1_1);
		
		JLabel cancelLabel = new JLabel("Enter Ticket Number to Cancel that Ticket:");
		cancelLabel.setFont(new Font("Verdana", Font.BOLD, 17));
		cancelLabel.setBounds(36, 21, 410, 28);
		panel_1_1.add(cancelLabel);
		
		ticketField = new JTextField();
		ticketField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		ticketField.setColumns(10);
		ticketField.setBackground(SystemColor.menu);
		ticketField.setBounds(469, 18, 535, 31);
		panel_1_1.add(ticketField);
		
		JButton btnCancel = new JButton("CANCEL");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String ticketID = ticketField.getText();
				
				if(ticketField.getText().isEmpty())
				{
					
					JOptionPane.showMessageDialog(null, "Please Enter the Ticket ID to Cancel Ticket.");
				}
				else
				{
				
				try {
				
					 
					String bus_id = "SELECT bus_id FROM  ticket_details WHERE ticket_id = ?";
					prestate = connection.prepareStatement(bus_id);
					prestate.setString(1, ticketID);
					ResultSet bus_string = prestate.executeQuery();
					
					String seat_id = "SELECT seat_id FROM  ticket_details WHERE ticket_id = ?";
					prestate = connection.prepareStatement(seat_id);
					prestate.setString(1, ticketID);
					ResultSet seat_string = prestate.executeQuery();
					
					if((bus_string.next() == true) && (seat_string.next() == true))
					{
						String busID = bus_string.getString(1);
						String seatID = seat_string.getString(1);
						

			         	String updateReserve = "UPDATE "+ busID + " SET status = 'AVAILABLE' WHERE seatID = ?";
						prestate = connection.prepareStatement(updateReserve);
						prestate.setString(1, seatID);
						prestate.executeUpdate();
						
						
						
						String countReserve = "SELECT COUNT(status) FROM " + busID + " WHERE status = ?";
						prestate = connection.prepareStatement(countReserve);
						prestate.setString(1, "RESERVED");
						ResultSet countString = prestate.executeQuery();
						
						if(countString.next() == true)
						{
							String countSeats = countString.getString(1);
							
							String updateRCount = "UPDATE reservation_status SET reserved_seats = ?  WHERE bus_id = ?";
							prestate = connection.prepareStatement(updateRCount);
							prestate.setString(1, countSeats);
							prestate.setString(2, busID);
							prestate.executeUpdate();
							
							 prestate = connection.prepareStatement("DELETE FROM ticket_details where ticket_id =?");
					         prestate.setString(1, ticketID);
					         prestate.executeUpdate();
							
						}
					}
					
			         JOptionPane.showMessageDialog(null, "Ticket Cancelled and Amount Refunded.");
			         
			         ticketField.setText("");
			         phoneField.setText("");
			         phoneField.requestFocus();
			         DefaultTableModel model = new DefaultTableModel();
					 table.setModel(model);
			         
			         
				}
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, "Please Enter the Correct Ticket ID.");
					e1.printStackTrace();
				}
				}
				
			}
		});
		btnCancel.setForeground(SystemColor.text);
		btnCancel.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		btnCancel.setBackground(SystemColor.textHighlight);
		btnCancel.setBounds(1049, 11, 121, 43);
		panel_1_1.add(btnCancel);
	}
}
