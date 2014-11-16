package de.bitbrain.craft.db;

import java.sql.DriverManager;
import java.sql.SQLException;

import de.bitbrain.jpersis.JPersisException;
import de.bitbrain.jpersis.drivers.Query;
import de.bitbrain.jpersis.drivers.jdbc.JDBCDriver;
import de.bitbrain.jpersis.drivers.sqllite.SQLiteQuery;
import de.bitbrain.jpersis.util.Naming;

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
	protected Query createQuery(Class<?> model, Naming naming) {
		return new SQLiteQuery(model, naming, statement);
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