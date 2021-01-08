import java.sql.*;

class AllDBOperations {
	static Connection connection = null;

	private static void showData() throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM student_info");

		while(resultSet.next()) {
			System.out.println("ID   : " + resultSet.getInt("id"));
			System.out.println("Name : " + resultSet.getString("name"));
			System.out.println("City : " + resultSet.getString("city"));
			System.out.println("Age  : " + resultSet.getInt("age") + "\n\n");
		}
	}

	public static void main(String arr[]) {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:db");

			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO student_info(name,city,age)VALUES(?,?,?);");
			PreparedStatement preparedStatementForUpdation = connection.prepareStatement("UPDATE student_info SET city=? WHERE id=?");
			PreparedStatement preparedStatementForDeletion = connection.prepareStatement("DELETE FROM student_info WHERE id=?");

			preparedStatement.setString(1, "Om Londhe");
			preparedStatement.setString(2, "Aurangabad");
			preparedStatement.setInt(3, 17);
			preparedStatement.executeUpdate();
			showData();

			preparedStatement.setString(1, "Ajay Rathod");
			preparedStatement.setString(2, "Pune");
			preparedStatement.setInt(3, 18);
			preparedStatement.executeUpdate();
			showData();

			preparedStatement.setString(1, "Dhananjay Kuber");
			preparedStatement.setString(2, "Mumbai");
			preparedStatement.setInt(3, 18);
			preparedStatement.executeUpdate();
			showData();


			preparedStatementForUpdation.setString(1, "Delhi");
			preparedStatementForUpdation.setInt(2, 2);
			preparedStatement.executeUpdate();
			showData();


			preparedStatementForDeletion.setInt(1, 1);
			preparedStatement.executeUpdate();
			showData();
	
		} catch (Exception e) {
			System.out.println("An error occurred-- " + e.getMessage());
		}		
	}
}
