package com.hitick.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitick.bean.GroupAdminMapBean;
import com.hitick.dao.GroupAdminMapDao;
import com.hitick.service.GroupAdminMapService;

@Service
public class GroupAdminMapServiceImpl  implements GroupAdminMapService{

	@Autowired GroupAdminMapDao groupAdminMapDao;
	
	@Override
	public void saveMappingDetails(int adminId, int groupId) {
		groupAdminMapDao.saveMappingDetails(adminId,groupId);
		
	}

	@Override
	public List<GroupAdminMapBean> getListByAdminId(int adminId) {
		
		return groupAdminMapDao.getListByAdminId(adminId);
	}

}
