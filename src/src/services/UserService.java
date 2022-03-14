package src.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.ManagedBean;
import javax.inject.Inject;

import src.config.DbUtil;
import src.models.User;

@ManagedBean
public class UserService {

	Connection connection = null;

	public String findUser(String email, String password) throws SQLException {

		if (email.equals("") || password.equals("")) {
			throw new RuntimeException("Bad Credentials!");
		}

		String query = "SELECT * FROM ist_users where user_email='" + email + "' AND password ='" + password + "'";

		connection = DbUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();

		if (!rs.isBeforeFirst()) {
			System.out.println("No data");
			throw new RuntimeException("User not found!");
		}

		// String usernameString = rs.getString("user_name");
		rs.next();
		return rs.getString("user_email");
	}

	public void SaveUser(User user) throws Exception, SQLException {

		String findIfUserExists = "SELECT COUNT(*) as total FROM ist_users where user_email='" + user.getEmail() + "'";
		String saveUserQuery = "INSERT INTO ist_users (user_email, password) VALUES ('" + user.getEmail() + "', '"
				+ user.getPassword() + "')";

		if (user.getEmail().equals("") || user.getPassword().equals("")) {
			throw new Exception("Email and password must not be empty!");
		}

		connection = DbUtil.getConnection();

		PreparedStatement preparedStatement = connection.prepareStatement(findIfUserExists);
		ResultSet rs = preparedStatement.executeQuery();

		rs.next();

		if (rs.getInt("total") > 0) {
			throw new RuntimeException("User already exists!");
		}

		PreparedStatement preparedStatement1 = connection.prepareStatement(saveUserQuery);
		preparedStatement1.executeUpdate();

	}

}
