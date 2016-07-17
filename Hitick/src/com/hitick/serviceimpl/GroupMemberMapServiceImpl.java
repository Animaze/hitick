package com.hitick.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitick.bean.GroupMemberMapBean;
import com.hitick.dao.GroupMemberMapDao;
import com.hitick.service.GroupMemberMapService;
@Service
public class GroupMemberMapServiceImpl implements GroupMemberMapService{

	@Autowired GroupMemberMapDao groupMemberMapDao;
	
	@Override
	public void saveMappingDetails(int memberId, int groupId) {
		groupMemberMapDao.saveMappingDetails(memberId,groupId);
		
	}

	@Override
	public List<GroupMemberMapBean> getListByMemberId(int memberId) {
		return groupMemberMapDao.getListByMemberId(memberId);
	}

	@Override
	public List<Integer> getMembersFromGroupId(int groupId) {
		return groupMemberMapDao.getMembersFromGroupId(groupId);
	}

	@Override
	public void deleteMappingDetails(int memberId, int groupId) {
		groupMemberMapDao.deleteMappingDetails(memberId,groupId);
		
	}

}
