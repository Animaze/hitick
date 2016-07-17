package com.hitick.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.hitick.bean.UserBean;
import com.hitick.dao.UserDao;

@Component
public class UserDaoImpl  extends BaseDaoImpl implements UserDao{

	String sqlQuery;
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	
	@Override
	public void addUser(UserBean user) {
	
		try{
			
			
			connection = getConnection();
			
			sqlQuery = "insert into users values (null,null, ?, ?, ?, ?, ?, ?)";
			
			preparedStatement = connection.prepareStatement(sqlQuery);	
				preparedStatement.setString(1, user.getFirstName());
				preparedStatement.setString(2, user.getLastName());
				preparedStatement.setString(3, user.getPassword());
				preparedStatement.setString(4, user.getEmail());
				preparedStatement.setString(5, user.getMobileNumber());
				preparedStatement.setString(6, user.getInstitution());
				
				
				preparedStatement.executeUpdate();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	@Override
	public int validateUser(String mobileNumber, String password) {
		int userId=-1;
		try{
			connection = getConnection();
			
			sqlQuery="select * from users where mobile_number=? and password =? ";
            
			preparedStatement = connection.prepareStatement(sqlQuery);
			
			preparedStatement.setString(1, mobileNumber);
			preparedStatement.setString(2, password);
			
			resultSet=preparedStatement.executeQuery();
			
			if (resultSet.next()) userId = resultSet.getInt("id");
			else userId=0;
		
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		return userId;
	}

	@Override
	public UserBean findUserById(int userId) {
	UserBean userBean=new UserBean();
	try{
		connection = getConnection();

		 sqlQuery = "select * from users where id=?";

		 preparedStatement = connection.prepareStatement(sqlQuery);
		 preparedStatement.setInt(1, userId);
		 resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			userBean.setId(userId);
			userBean.setGcmRegistrationId(resultSet.getString("gcm_registration_id"));
			userBean.setFirstName(resultSet.getString("firstname"));
			userBean.setLastName(resultSet.getString("lastname"));
			userBean.setEmail(resultSet.getString("email"));
			userBean.setMobileNumber(resultSet.getString("mobile_number"));
			userBean.setInstitution(resultSet.getString("institution"));
			
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		return userBean;
	}

	@Override
	public int addUserApp(UserBean user) {
		int id=-1;
	
		try{
			
			connection = getConnection();
			
			sqlQuery = "insert into users values (null,null, ?, ?, ?, ?, ?, ?)";
			
			preparedStatement = connection.prepareStatement(sqlQuery);	
				preparedStatement.setString(1, user.getFirstName());
				preparedStatement.setString(2, user.getLastName());
				preparedStatement.setString(3, user.getPassword());
				preparedStatement.setString(4, user.getEmail());
				preparedStatement.setString(5, user.getMobileNumber());
				preparedStatement.setString(6, user.getInstitution());
				
				
				preparedStatement.executeUpdate();
			
				sqlQuery="select LAST_INSERT_ID()";
				
				preparedStatement = connection
						.prepareStatement(sqlQuery);
				
				resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()){
					id = resultSet.getInt(1);
					
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return id;
	}

	@Override
	public void updateGcmRegistrationId(int userId, String gcmRegistrationId) {
		sqlQuery="update users set gcm_registration_id =? where id = ?";
		try {
			connection = getConnection();

				preparedStatement = connection.prepareStatement(sqlQuery);

				preparedStatement.setString(1, gcmRegistrationId);
				preparedStatement.setInt(2, userId);
				
			preparedStatement.executeUpdate();
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
