package br.dev.ppaiva.votaAI.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractRepository {
	private static final String URL = "jdbc:mysql://localhost:3306/votaAI";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	protected Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
}
