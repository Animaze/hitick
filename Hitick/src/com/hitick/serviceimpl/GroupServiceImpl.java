package com.hitick.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitick.bean.GroupBean;
import com.hitick.dao.GroupDao;
import com.hitick.service.GroupService;


@Service
public class GroupServiceImpl implements GroupService {

	@Autowired GroupDao groupDao;
	
	@Override
	public int addGroup(GroupBean group) {
		return groupDao.addGroup(group);
		
	}

	@Override
	public int checkForGroup(String groupName, String groupPassword) {

		return groupDao.checkForGroup(groupName,groupPassword);
	}

	@Override
	public void alterMemberCount(int groupId,int count) {
		groupDao.alterMemberCount(groupId,count);
		
	}

	@Override
	public GroupBean findGroupById(int refGroupId) {
		
		return groupDao.findGroupById(refGroupId);
	}

}
