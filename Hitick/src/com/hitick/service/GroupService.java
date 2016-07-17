package com.hitick.service;

import com.hitick.bean.GroupBean;

public interface GroupService {

	int addGroup(GroupBean group);

	int checkForGroup(String groupName, String groupPassword);

	void alterMemberCount(int groupId, int count);

	GroupBean findGroupById(int refGroupId);

}
