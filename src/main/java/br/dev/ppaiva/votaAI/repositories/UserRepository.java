package br.dev.ppaiva.votaAI.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.dev.ppaiva.votaAI.entities.User;

public class UserRepository extends AbstractRepository {

	public User save(User user) {
		String sql = "INSERT INTO user (username, private_key) VALUES (?, ?)";
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPrivateKey());

			int affectedRows = stmt.executeUpdate();

			if (affectedRows > 0) {
				try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						user.setId(generatedKeys.getLong(1));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error saving user", e);
		}
		return user;
	}

	public User update(User user) {
		if (user.getId() == null) {
			return save(user);
		}

		String sql = "UPDATE user SET username = ?, private_key = ? WHERE user_id = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPrivateKey());
			stmt.setLong(3, user.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error updating user", e);
		}
		return user;
	}

	public User findById(Long id) {
		String sql = "SELECT user_id, username, private_key FROM user WHERE user_id = ?";
		User user = null;

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setLong(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					user = new User();
					user.setId(rs.getLong("user_id"));
					user.setUsername(rs.getString("username"));
					user.setPrivateKey(rs.getString("private_key"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error finding user by ID", e);
		}
		return user;
	}

	public User findByUsername(String username) {
		String sql = "SELECT user_id, username, private_key FROM user WHERE username = ?";
		User user = null;

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, username);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					user = new User();
					user.setId(rs.getLong("user_id"));
					user.setUsername(rs.getString("username"));
					user.setPrivateKey(rs.getString("private_key"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error finding user by username", e);
		}
		return user;
	}

	public User findByPrivateKey(String privateKey) {
		String sql = "SELECT user_id, username, private_key FROM user WHERE private_key = ?";
		User user = null;

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, privateKey);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					user = new User();
					user.setId(rs.getLong("user_id"));
					user.setUsername(rs.getString("username"));
					user.setPrivateKey(rs.getString("private_key"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error finding user by private key", e);
		}
		return user;
	}
}
