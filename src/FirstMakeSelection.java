import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;

public class FirstMakeSelection {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		firstMakeSelection();
		Connect();
	}

	/**
	 * Create the application.
	 */
	public static void firstMakeSelection() {
		JFrame frame = new JFrame("Make Your Choice");
		frame.setBounds(100, 100, 1280, 720);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel MSelectTxt = new JLabel("Make Selection");
		MSelectTxt.setFont(new Font("Times New Roman", Font.BOLD, 40));
		MSelectTxt.setForeground(new Color(0, 0, 0));
		MSelectTxt.setBackground(Color.WHITE);
		MSelectTxt.setBounds(493, 37, 278, 69);
		frame.getContentPane().add(MSelectTxt);
		
		JLabel lblAdmincreateBus = new JLabel("Admin (Create Bus) or Passenger (Reserve Tickets)");
		lblAdmincreateBus.setForeground(Color.BLACK);
		lblAdmincreateBus.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblAdmincreateBus.setBackground(Color.WHITE);
		lblAdmincreateBus.setBounds(358, 142, 591, 69);
		frame.getContentPane().add(lblAdmincreateBus);
		
		
		//Host Button Action
		JButton btnAdmin = new JButton("Admin");
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				AdminHome AdminHome = new AdminHome();
				AdminHome.main();
			}
		});
		
		
		btnAdmin.setBackground(Color.ORANGE);
		btnAdmin.setFont(new Font("Calibri", Font.BOLD, 22));
		btnAdmin.setBounds(543, 284, 189, 79);
		frame.getContentPane().add(btnAdmin);
		
		JButton btnPassenger = new JButton("Passenger");
		btnPassenger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				PassHome PassHome = new PassHome();
				PassHome.main();
			}
		});
		btnPassenger.setForeground(Color.BLACK);
		btnPassenger.setBackground(Color.ORANGE);
		btnPassenger.setFont(new Font("Calibri", Font.BOLD, 22));
		btnPassenger.setBounds(543, 434, 189, 79);
		frame.getContentPane().add(btnPassenger);
	}
	
	static Connection connection;
	PreparedStatement prestate;
	ResultSet resultSet;

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
}


