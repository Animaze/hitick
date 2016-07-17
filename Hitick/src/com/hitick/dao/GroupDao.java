package com.hitick.dao;

import com.hitick.bean.GroupBean;

public interface GroupDao {

	int addGroup(GroupBean group);

	int checkForGroup(String groupName, String groupPassword);

	void alterMemberCount(int groupId, int count);

	GroupBean findGroupById(int refGroupId);

}
