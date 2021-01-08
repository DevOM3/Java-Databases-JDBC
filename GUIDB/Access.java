import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

class Access extends JFrame implements ActionListener {
	static JTextField idTextField;
	static JTextField nameTextField;
	static JTextField cityTextField;
	static JTextField contactTextField;
	static ResultSet resultSet;
	static Statement statement;	

	public Access() {
		Container container = this.getContentPane();
		this.setLayout(new FlowLayout());

		JLabel idLabel = new JLabel("Student ID    : ");
		idTextField = new JTextField(21);
		JLabel nameLabel = new JLabel("Student Name: ");
		nameTextField = new JTextField(21);
		JLabel cityLabel = new JLabel("Student City: ");
		cityTextField = new JTextField(21);
		JLabel contactLabel = new JLabel("Student Ph.no.: ");
		contactTextField = new JTextField(21);
		
		JButton previousButton = new JButton("PREVIOUS");
		previousButton.addActionListener(this);
		JButton resetButton = new JButton("RESET");
		resetButton.addActionListener(this);
		JButton refreshButton = new JButton("REFRESH");
		refreshButton.addActionListener(this);
		JButton nextButton = new JButton("NEXT");
		nextButton.addActionListener(this);

		container.add(idLabel);
		container.add(idTextField);
		container.add(nameLabel);
		container.add(nameTextField);
		container.add(cityLabel);
		container.add(cityTextField);
		container.add(contactLabel);
		container.add(contactTextField);

		//container.add(previousButton);
		container.add(resetButton);
		container.add(refreshButton);
		container.add(nextButton);	
	}

	public void actionPerformed(ActionEvent ae) {
		String source = ae.getActionCommand();
		
		if (source == "RESET") {
			idTextField.setText("");
			nameTextField.setText("");
			cityTextField.setText("");
			contactTextField.setText("");
		} else if (source == "PREVIOUS" || source == "NEXT") {
			try {
				handleData(source);
			} catch (Exception e) {
				
			}
		} else {	
			try {
				fetch();
			} catch (Exception e) {
				
			}
		}
	}

	public void handleData(String source) throws Exception {
		if (source == "NEXT") {
			if (resultSet.next()) {
				idTextField.setText(resultSet.getString("id"));
				nameTextField.setText(resultSet.getString("name"));
				cityTextField.setText(resultSet.getString("city"));
				contactTextField.setText(resultSet.getString("contact"));
			} else {
				while(resultSet.previous()) {
					resultSet.previous();
				}
				handleData(source);
			}
		} else if (source == "PREVIOUS") {
			System.out.println("..." + resultSet.previous());
			if (resultSet.previous()) {
				idTextField.setText(resultSet.getString("id"));
				nameTextField.setText(resultSet.getString("name"));
				cityTextField.setText(resultSet.getString("city"));
				contactTextField.setText(resultSet.getString("contact"));
			} else {
				while(resultSet.previous()) {
					resultSet.next();
				}
				handleData(source);
			}
		}
	}

	public static void fetch() throws Exception {
		idTextField.setText("");
		nameTextField.setText("");
		cityTextField.setText("");
		contactTextField.setText("");
		resultSet = statement.executeQuery("SELECT * FROM student");
	}

	public static void main(String []arr) throws Exception {
		Access access = new Access();
		access.setVisible(true);
		access.setSize(400, 400);
		access.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Class.forName("org.sqlite.JDBC");
		Connection connection = DriverManager.getConnection("jdbc:sqlite:db");

		statement = connection.createStatement();
		fetch();
	}
}
