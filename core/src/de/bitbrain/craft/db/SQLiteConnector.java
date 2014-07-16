package de.bitbrain.craft.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import de.myreality.jpersis.db.DatabaseConnector;
import de.myreality.jpersis.db.DatabaseException;

public class SQLiteConnector implements DatabaseConnector {

    // Active connection
    private Connection connection;
    // Active statement
    private Statement statement;
    
    private String file;
    
    public SQLiteConnector(String file) {
    	this.file = file;
    }

	@Override
	public void closeConnection() throws DatabaseException {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public Connection getConnection() {
		return connection;
	}

	@Override
	public Statement getStatement() {
		return statement;
	}

	@Override
	public void openConnection() throws DatabaseException {
		try {
			Class.forName("org.sqlite.JDBC");
	        connection = DriverManager.getConnection("jdbc:sqlite:" + file);
	        statement = connection.createStatement();
		} catch (ClassNotFoundException e) {
			throw new DatabaseException(e);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

   

    
}

