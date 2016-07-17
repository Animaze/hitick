package com.hitick.service;

import java.util.List;

import com.hitick.bean.GroupMemberMapBean;

public interface GroupMemberMapService {

	void saveMappingDetails(int memberId, int groupId);
	List<GroupMemberMapBean> getListByMemberId(int memberId);

	List<Integer> getMembersFromGroupId(int groupId);
	void deleteMappingDetails(int memberId, int groupId);
	
}
