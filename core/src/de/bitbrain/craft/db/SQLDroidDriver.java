/*
 * Craft - Crafting game for Android, PC and Browser.
 * Copyright (C) 2014 Miguel Gonzalez
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package de.bitbrain.craft.db;

import java.sql.DriverManager;
import java.sql.SQLException;

import de.bitbrain.jpersis.JPersisException;
import de.bitbrain.jpersis.drivers.Query;
import de.bitbrain.jpersis.drivers.jdbc.JDBCDriver;
import de.bitbrain.jpersis.drivers.sqllite.SQLiteQuery;
import de.bitbrain.jpersis.util.Naming;

/**
 * Driver implementation for SQLDroid
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
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