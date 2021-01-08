import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

class Register extends JFrame implements ActionListener {
	JTextField idTextField;
	JTextField nameTextField;
	JTextField cityTextField;
	JTextField contactTextField;
	static PreparedStatement preparedStatement;	

	public Register() {
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
		
		JButton addButton = new JButton("ADD");
		addButton.addActionListener(this);
		JButton resetButton = new JButton("RESET");
		resetButton.addActionListener(this);

		container.add(idLabel);
		container.add(idTextField);
		container.add(nameLabel);
		container.add(nameTextField);
		container.add(cityLabel);
		container.add(cityTextField);
		container.add(contactLabel);
		container.add(contactTextField);

		container.add(addButton);
		container.add(resetButton);	
	}

	public void actionPerformed(ActionEvent ae) {
		String source = ae.getActionCommand();
		
		if (source == "RESET") {
			idTextField.setText("");
			nameTextField.setText("");
			cityTextField.setText("");
			contactTextField.setText("");
		} else {
			try {
				handleDatabase();
			} catch (Exception e) {
				
			}
		}
	}

	public void handleDatabase() throws Exception {
		preparedStatement.setInt(1, Integer.parseInt(idTextField.getText()));
		preparedStatement.setString(2, nameTextField.getText());
		preparedStatement.setString(3, cityTextField.getText());
		preparedStatement.setInt(4, Integer.parseInt(contactTextField.getText()));

		preparedStatement.executeUpdate();
	}

	public static void main(String []arr) throws Exception {
		Register guiDB = new Register();
		guiDB.setVisible(true);
		guiDB.setSize(400, 400);
		guiDB.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Class.forName("org.sqlite.JDBC");
		Connection connection = DriverManager.getConnection("jdbc:sqlite:db");

		// Statement statement = connection.createStatement();
		// statement.executeQuery("CREATE TABLE student(id integer, name text, city text, contact integer); ");

		preparedStatement = connection.prepareStatement("INSERT INTO student VALUES(?,?,?,?)");
	}
}
