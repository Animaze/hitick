package com.hitick.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hitick.bean.GroupAdminMapBean;
import com.hitick.bean.GroupMemberMapBean;
import com.hitick.dao.GroupMemberMapDao;
@Component
public class GroupMemberMapDaoImpl extends BaseDaoImpl implements GroupMemberMapDao{

	String sqlQuery;
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	
	@Override
	public void saveMappingDetails(int memberId, int groupId) {
		try{

			connection = getConnection();
		
			sqlQuery="insert into group_member_map values (null, ?, ?)" ;
			preparedStatement = connection.prepareStatement(sqlQuery);
			
			
			preparedStatement.setInt(1, groupId);
			preparedStatement.setInt(2, memberId);
		
			
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
	public List<GroupMemberMapBean> getListByMemberId(int memberId) {
		GroupMemberMapBean groupMemberMapBean =null;
		List<GroupMemberMapBean> listOfGroupMemberMapBean = new ArrayList<GroupMemberMapBean>();
		try {
			connection = getConnection();
		
			sqlQuery="select * from group_member_map where ref_member_id=?";
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, memberId);
			resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next()){
			
				groupMemberMapBean=new GroupMemberMapBean();
				groupMemberMapBean.setId(resultSet.getInt("id"));
				groupMemberMapBean.setRefMemberId(resultSet.getInt("ref_member_id"));
				groupMemberMapBean.setRefGroupId(resultSet.getInt("ref_group_id"));
				listOfGroupMemberMapBean.add(groupMemberMapBean);			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return listOfGroupMemberMapBean;
	}

	

	
	@Override
	public List<Integer> getMembersFromGroupId(int groupId) {
		List<Integer> membersIdsList = new ArrayList<Integer>();
		try{

			connection = getConnection();
		

			sqlQuery="select * from group_member_map where ref_group_id=?";
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, groupId);
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next())membersIdsList.add(resultSet.getInt("ref_member_id"));	
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return membersIdsList;
	}

	@Override
	public void deleteMappingDetails(int memberId, int groupId) {
		try{

			connection = getConnection();
		
			sqlQuery="delete from group_member_map where ref_group_id=? and ref_member_id=?" ;
			preparedStatement = connection.prepareStatement(sqlQuery);
			
			
			preparedStatement.setInt(1, groupId);
			preparedStatement.setInt(2, memberId);
		
			
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
