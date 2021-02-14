package com.ecsite.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCon {
	private static String driverName = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost/Ecsite";

	private static String user = "root";
	private static String password = "";

	public Connection getConnection() {
		Connection con = null;

		try {
			Class.forName(driverName);
			con = (Connection) DriverManager.getConnection(url,user,password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

}
