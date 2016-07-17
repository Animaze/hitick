package com.hitick.daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.hitick.dao.BaseDao;




public class BaseDaoImpl implements BaseDao{


	
		private String dbUsername;
		private String dbUrl;
		private String dbPassword;
		private String dbDriver;
		private Connection connection = null;
	

		@Override
		public Connection getConnection() {

			try {

				ResourceBundle rb = ResourceBundle.getBundle("database_config");
				
				dbUsername = rb.getString("userName");
				dbUrl = rb.getString("url");
				dbPassword = rb.getString("password");
				dbDriver = rb.getString("driverName");

				Class.forName(dbDriver);
				connection = DriverManager.getConnection(dbUrl, dbUsername,
						dbPassword);

			} catch (ClassNotFoundException ce) {
				ce.printStackTrace();
			} catch (SQLException se) {
				se.printStackTrace();
			}

			return connection;
		}

}
