package com.hitick.dao;

import java.util.List;

import com.hitick.bean.GroupMemberMapBean;

public interface GroupMemberMapDao {

	void saveMappingDetails(int memberId, int groupId);

	List<GroupMemberMapBean> getListByMemberId(int memberId);


	List<Integer> getMembersFromGroupId(int groupId);

	void deleteMappingDetails(int memberId, int groupId);

}
