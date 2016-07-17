package com.hitick.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.hitick.bean.GroupBean;
import com.hitick.dao.GroupDao;

@Component
public class GroupDaoImpl  extends BaseDaoImpl implements GroupDao{
	String sqlQuery;
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	

	
	@Override
	public int addGroup(GroupBean group) {
    int groupId=-1;
	try{
			
			
			connection = getConnection();
			
			sqlQuery = "insert into groups values (null, 1, ?, ?, ?)";
			
			preparedStatement = connection.prepareStatement(sqlQuery);	
				preparedStatement.setInt(1, group.getRefAdminId());
				preparedStatement.setString(2, group.getGroupName());
				preparedStatement.setString(3, group.getGroupPassword());	
				preparedStatement.executeUpdate();
			
			 sqlQuery = "select last_insert_id();";
		        preparedStatement = connection.prepareStatement(sqlQuery);
	            resultSet = preparedStatement.executeQuery();
				while(resultSet.next())groupId = resultSet.getInt(1);
					
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return groupId;
		
	}



	@Override
	public int checkForGroup(String groupName, String groupPassword) {
		
		int id=-1;
		try{
			connection = getConnection();
			
			sqlQuery="select * from groups where group_name=? and group_password =? ";
            
			preparedStatement = connection.prepareStatement(sqlQuery);
			
			preparedStatement.setString(1, groupName);
			preparedStatement.setString(2, groupPassword);
			
			resultSet=preparedStatement.executeQuery();
			
			if (resultSet.next()) id = resultSet.getInt("id");
			else id=0;
		
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	return id;
	}



	@Override
	public void alterMemberCount(int groupId,int count) {
		try {
			connection = getConnection();
           if(count==1)
			{sqlQuery = "update groups set member_count=member_count+1 "
					+ "where "
					+ "id=?";
			}else{
				sqlQuery = "update groups set member_count=member_count-1 "
						+ "where "
						+ "id=?";
			}
				preparedStatement = connection.prepareStatement(sqlQuery);

				preparedStatement.setInt(1, groupId);

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
	public GroupBean findGroupById(int refGroupId) {
		
		GroupBean groupBean=new GroupBean();
		try{
			connection = getConnection();

			 sqlQuery = "select * from groups where id=?";

			 preparedStatement = connection.prepareStatement(sqlQuery);
			 preparedStatement.setInt(1, refGroupId);
			 resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				groupBean.setId(refGroupId);
				groupBean.setGroupName(resultSet.getString("group_name"));
				groupBean.setGroupPassword(resultSet.getString("group_password"));
				groupBean.setMemberCount(resultSet.getInt("member_count"));
				groupBean.setRefAdminId(resultSet.getInt("ref_admin_id"));
				
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
		
			return groupBean;
		}

	}


