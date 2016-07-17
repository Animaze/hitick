package com.hitick.service;

import java.util.List;

import com.hitick.bean.GroupAdminMapBean;

public interface GroupAdminMapService {

	void saveMappingDetails(int adminId, int groupId);

	List<GroupAdminMapBean> getListByAdminId(int adminId);

}
