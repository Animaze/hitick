package com.hitick.dao;

import java.util.List;

import com.hitick.bean.GroupAdminMapBean;

public interface GroupAdminMapDao {

	void saveMappingDetails(int adminId, int groupId);

	List<GroupAdminMapBean> getListByAdminId(int adminId);

}
