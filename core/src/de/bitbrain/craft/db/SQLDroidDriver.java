package de.bitbrain.craft.db;

import java.sql.DriverManager;
import java.sql.SQLException;

import de.bitbrain.jpersis.JPersisException;
import de.bitbrain.jpersis.drivers.jdbc.JDBCDriver;

public class SQLDroidDriver extends JDBCDriver {
	  
	  private String file;

	  public SQLDroidDriver(String file) {
	    super("", "", "", "", "");
	    this.file = file;
	  }

	  @Override
	  protected String getURL(String host, String port, String database) {
	    return null;
	  }

	  @Override
	  public void connect() {
	    try {
	      Class.forName("org.sqldroid.SQLDroidDriver");
	      connection = DriverManager.getConnection("jdbc:sqldroid:" + file);
	      statement = connection.createStatement();
	    } catch (ClassNotFoundException e) {
	      throw new JPersisException(e);
	    } catch (SQLException e) {
	      throw new JPersisException(e);
	    }
	  }
	}