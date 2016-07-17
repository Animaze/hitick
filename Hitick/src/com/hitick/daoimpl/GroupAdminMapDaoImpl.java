package com.hitick.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hitick.bean.GroupAdminMapBean;
import com.hitick.dao.GroupAdminMapDao;

@Component
public class GroupAdminMapDaoImpl extends BaseDaoImpl implements
		GroupAdminMapDao {

	String sqlQuery;
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;

	@Override
	public void saveMappingDetails(int adminId, int groupId) {

		try {

			connection = getConnection();

			sqlQuery = "insert into group_admin_map values (null, ?, ?)";
			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setInt(1, adminId);
			preparedStatement.setInt(2, groupId);

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
	public List<GroupAdminMapBean> getListByAdminId(int adminId) {
		GroupAdminMapBean groupAdminMapBean =null;
		List<GroupAdminMapBean> listOfGroupAdminMapBean = new ArrayList<GroupAdminMapBean>();
		try {
			connection = getConnection();
		
			sqlQuery="select * from group_admin_map where ref_admin_id=?";
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, adminId);
			resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next()){
			
				groupAdminMapBean=new GroupAdminMapBean();
				groupAdminMapBean.setId(resultSet.getInt("id"));
				groupAdminMapBean.setRefAdminId(resultSet.getInt("ref_admin_id"));
				groupAdminMapBean.setRefGroupId(resultSet.getInt("ref_group_id"));
				listOfGroupAdminMapBean.add(groupAdminMapBean);			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listOfGroupAdminMapBean;
	}

}
