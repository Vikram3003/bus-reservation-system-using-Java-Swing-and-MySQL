import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PassBookTicket {


	/**
	 * Launch the application.
	 * @wbp.parser.entryPoint
	 */
	public static void main(String busID, String seatID) {
		Connect();
		PassBookTicket(busID, seatID);
		
	}
	
	//Use mySQL Connector Variables
		static Connection connection;
		static PreparedStatement prestate;
		static ResultSet resultSet;
		
		//Variables
		private static JTextField nameField;
		private static JTextField phoneField;
		
		
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
	public static void PassBookTicket(String busID, String seatID) {
		JFrame frame = new JFrame();
		frame.setBounds(100, 100, 1280, 720);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel reserveBusTxt = new JLabel("Reserve Bus Ticket");
		reserveBusTxt.setForeground(Color.BLACK);
		reserveBusTxt.setFont(new Font("Times New Roman", Font.BOLD, 40));
		reserveBusTxt.setBackground(Color.WHITE);
		reserveBusTxt.setBounds(442, 23, 346, 69);
		frame.getContentPane().add(reserveBusTxt);
		
		JButton btnBackAd = new JButton("GO BACK");
		btnBackAd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				PassHome passHome = new PassHome();
				passHome.main();
			}
		});
		btnBackAd.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		btnBackAd.setBackground(SystemColor.info);
		btnBackAd.setBounds(10, 10, 121, 47);
		frame.getContentPane().add(btnBackAd);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Enter Details", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBackground(Color.WHITE);
		panel.setBounds(25, 170, 460, 213);
		frame.getContentPane().add(panel);
		
		JLabel phone_label = new JLabel("Phone Number");
		phone_label.setFont(new Font("Verdana", Font.BOLD, 22));
		phone_label.setBounds(32, 127, 197, 36);
		panel.add(phone_label);
		
		JLabel name_label = new JLabel("Your Name");
		name_label.setFont(new Font("Verdana", Font.BOLD, 22));
		name_label.setBounds(52, 59, 177, 36);
		panel.add(name_label);
		
		nameField = new JTextField();
		nameField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		nameField.setColumns(10);
		nameField.setBackground(SystemColor.menu);
		nameField.setBounds(241, 66, 158, 31);
		panel.add(nameField);
		
		phoneField = new JTextField();
		phoneField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		phoneField.setColumns(10);
		phoneField.setBackground(SystemColor.menu);
		phoneField.setBounds(241, 134, 158, 31);
		panel.add(phoneField);
		
		JLabel busID_label = new JLabel("BUS ID:");
		busID_label.setFont(new Font("Verdana", Font.BOLD, 22));
		busID_label.setBounds(504, 170, 177, 36);
		frame.getContentPane().add(busID_label);
		
		JLabel bType_label = new JLabel("BUS TYPE:");
		bType_label.setFont(new Font("Verdana", Font.BOLD, 22));
		bType_label.setBounds(751, 170, 177, 36);
		frame.getContentPane().add(bType_label);
		
		JLabel seatNo_label = new JLabel("SEAT ID:");
		seatNo_label.setFont(new Font("Verdana", Font.BOLD, 22));
		seatNo_label.setBounds(1040, 170, 121, 36);
		frame.getContentPane().add(seatNo_label);
		
		JLabel Pickup_label = new JLabel("PICKUP:");
		Pickup_label.setFont(new Font("Verdana", Font.BOLD, 22));
		Pickup_label.setBounds(504, 231, 177, 36);
		frame.getContentPane().add(Pickup_label);
		
		JLabel destination_label = new JLabel("DESTINATION:");
		destination_label.setFont(new Font("Verdana", Font.BOLD, 22));
		destination_label.setBounds(824, 231, 208, 36);
		frame.getContentPane().add(destination_label);
		
		JLabel timeDepart_label = new JLabel("TIME OF DEPARTURE:");
		timeDepart_label.setFont(new Font("Verdana", Font.BOLD, 22));
		timeDepart_label.setBounds(504, 288, 284, 36);
		frame.getContentPane().add(timeDepart_label);
		
		JLabel timeReach_label = new JLabel("TIME OF REACH:");
		timeReach_label.setFont(new Font("Verdana", Font.BOLD, 22));
		timeReach_label.setBounds(915, 288, 221, 36);
		frame.getContentPane().add(timeReach_label);
		
		JLabel ticketPrice_label = new JLabel("TICKET PRICE (Rs.):");
		ticketPrice_label.setFont(new Font("Verdana", Font.BOLD, 22));
		ticketPrice_label.setBounds(624, 347, 285, 36);
		frame.getContentPane().add(ticketPrice_label);
		
		JLabel busIDTxt = new JLabel("BUS ID");
		busIDTxt.setFont(new Font("Verdana", Font.BOLD, 22));
		busIDTxt.setBounds(611, 170, 111, 36);
		frame.getContentPane().add(busIDTxt);
		
		JLabel bTypeTxt = new JLabel("BUS TYPE");
		bTypeTxt.setFont(new Font("Verdana", Font.BOLD, 22));
		bTypeTxt.setBounds(889, 170, 156, 36);
		frame.getContentPane().add(bTypeTxt);
		
		JLabel seatNoTxt = new JLabel("SEATID");
		seatNoTxt.setFont(new Font("Verdana", Font.BOLD, 22));
		seatNoTxt.setBounds(1156, 170, 121, 36);
		frame.getContentPane().add(seatNoTxt);
		
		JLabel PickupTxt = new JLabel("PICKUP");
		PickupTxt.setFont(new Font("Verdana", Font.BOLD, 22));
		PickupTxt.setBounds(622, 231, 177, 36);
		frame.getContentPane().add(PickupTxt);
		
		JLabel destinationTxt = new JLabel("DESTINATION");
		destinationTxt.setFont(new Font("Verdana", Font.BOLD, 22));
		destinationTxt.setBounds(1028, 231, 208, 36);
		frame.getContentPane().add(destinationTxt);
		
		JLabel timeDepartTxt = new JLabel("12AM");
		timeDepartTxt.setFont(new Font("Verdana", Font.BOLD, 22));
		timeDepartTxt.setBounds(786, 288, 82, 36);
		frame.getContentPane().add(timeDepartTxt);
		
		JLabel timeReachTxt = new JLabel("12AM");
		timeReachTxt.setFont(new Font("Verdana", Font.BOLD, 22));
		timeReachTxt.setBounds(1134, 288, 82, 36);
		frame.getContentPane().add(timeReachTxt);
		
		JLabel ticketPriceTxt = new JLabel("1500");
		ticketPriceTxt.setFont(new Font("Verdana", Font.BOLD, 22));
		ticketPriceTxt.setBounds(896, 347, 208, 36);
		frame.getContentPane().add(ticketPriceTxt);
		
		
		
		busIDTxt.setText(busID);
		seatNoTxt.setText(seatID);
		
		JButton btnConfirmReserveTicket = new JButton("CONFIRM RESERVATION");
		btnConfirmReserveTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name, phoneNumber;
				
				name = nameField.getText();
				phoneNumber = phoneField.getText();

				if(nameField.getText().isEmpty() && phoneField.getText().isEmpty())
				{
					
					JOptionPane.showMessageDialog(null, "Please Enter your Name and Phone Number to Reserve Ticket.");
				}
				else 
				{
					
					try
					{
					String ticket = "SELECT ticket_price_Rs FROM location_details WHERE bus_id = ?";
					prestate = connection.prepareStatement(ticket);
					prestate.setString(1, busID);
					ResultSet resultS = prestate.executeQuery();
					
					String busType = "SELECT busType FROM busdetails WHERE busID = ?";
					prestate = connection.prepareStatement(busType);
					prestate.setString(1, busID);
					ResultSet resultSe = prestate.executeQuery();
					
	
					
					if((resultS.next() == true) && (resultSe.next() == true))
					{
						String priceTxt = resultS.getString(1);
						int ticketPrice = Integer.parseInt(priceTxt);
						
						String busTypeTxt = resultSe.getString(1);
						
						int phone = Integer.parseInt(phoneNumber);
						
						try
						{
							Calendar calendar = Calendar.getInstance();
						    long timeMilli = calendar.getTimeInMillis();
						      
							String insertTicketDetails = "INSERT INTO ticket_details(ticket_id, bus_id, bus_type,"
									+ " seat_id, name, phone_number, ticket_price_Rs, time_of_payment) "
									+ "VALUES (?,?,?,?,?,?,?,?)";
							prestate = connection.prepareStatement(insertTicketDetails);
							prestate.setLong(1, timeMilli+phone);
							prestate.setString(2, busID);
							prestate.setString(3, busTypeTxt);
							prestate.setString(4, seatID);
							prestate.setString(5, name);
							prestate.setInt(6, phone);
							prestate.setInt(7, ticketPrice);
							prestate.setTimestamp(8, new java.sql.Timestamp(new java.util.Date().getTime()));
							prestate.executeUpdate();
							
							
							String updateReserve = "UPDATE "+ busID + " SET status = 'RESERVED' WHERE seatID = ?";
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
								
							}
							
							String cusName = nameField.getText();
							
							String insertCustomerDetails = "INSERT INTO customer VALUES (?,?)";
							prestate = connection.prepareStatement(insertCustomerDetails);
							prestate.setInt(1, phone);
							prestate.setString(2, cusName);
							prestate.executeUpdate();
							
							nameField.setText("");
							phoneField.setText("");
							
							
						}
						catch (Exception e1)
						{
							e1.printStackTrace();
						}
						
					}
					
					JOptionPane.showMessageDialog(null, "Payment Successfull!! Ticket Booked Successfully!!");
					
					frame.setVisible(false);
					PassHome passHome = new PassHome();
					passHome.main();
					
					
					}
					catch (Exception e1)
					{
						JOptionPane.showMessageDialog(null, "Please Check the Phone Number you Entered!!");
					}
					
					
				}
				
			}
		});
		btnConfirmReserveTicket.setForeground(SystemColor.text);
		btnConfirmReserveTicket.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		btnConfirmReserveTicket.setBackground(SystemColor.textHighlight);
		btnConfirmReserveTicket.setBounds(25, 432, 1211, 47);
		frame.getContentPane().add(btnConfirmReserveTicket);
		
		try
		{
		String busType = "SELECT busType FROM busdetails WHERE busID = ?";
		prestate = connection.prepareStatement(busType);
		prestate.setString(1, busID);
		ResultSet resultS = prestate.executeQuery();
		
		if(resultS.next() == true)
		{
			String busTypeTxt = resultS.getString(1);
			bTypeTxt.setText(busTypeTxt);
		}
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		
		try
		{
		String pickup = "SELECT pickup FROM location_details WHERE bus_id = ?";
		prestate = connection.prepareStatement(pickup);
		prestate.setString(1, busID);
		ResultSet resultS = prestate.executeQuery();
		
		if(resultS.next() == true)
		{
			String pickupTxt = resultS.getString(1);
			PickupTxt.setText(pickupTxt);
		}
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		
		try
		{
		String dest = "SELECT destination FROM location_details WHERE bus_id = ?";
		prestate = connection.prepareStatement(dest);
		prestate.setString(1, busID);
		ResultSet resultS = prestate.executeQuery();
		
		if(resultS.next() == true)
		{
			String destTxt = resultS.getString(1);
			destinationTxt.setText(destTxt);
		}
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		
		try
		{
		String tDepart = "SELECT time_of_departure FROM reservation_status WHERE bus_id = ?";
		prestate = connection.prepareStatement(tDepart);
		prestate.setString(1, busID);
		ResultSet resultS = prestate.executeQuery();
		
		if(resultS.next() == true)
		{
			String departTxt = resultS.getString(1);
			timeDepartTxt.setText(departTxt);
		}
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		
		
		try
		{
		String tReach = "SELECT time_of_reach FROM reservation_status WHERE bus_id = ?";
		prestate = connection.prepareStatement(tReach);
		prestate.setString(1, busID);
		ResultSet resultS = prestate.executeQuery();
		
		if(resultS.next() == true)
		{
			String reachTxt = resultS.getString(1);
			timeReachTxt.setText(reachTxt);
		}
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		
		
		try
		{
		String ticket = "SELECT ticket_price_Rs FROM location_details WHERE bus_id = ?";
		prestate = connection.prepareStatement(ticket);
		prestate.setString(1, busID);
		ResultSet resultS = prestate.executeQuery();
		
		if(resultS.next() == true)
		{
			String priceTxt = resultS.getString(1);
			ticketPriceTxt.setText(priceTxt);
		}
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		
		
		
	}
}
