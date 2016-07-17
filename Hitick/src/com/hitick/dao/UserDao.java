package com.hitick.dao;

import com.hitick.bean.UserBean;

public interface UserDao {

	void addUser(UserBean user);

	int validateUser(String username, String password);

	UserBean findUserById(int userId);

	int addUserApp(UserBean user);

	void updateGcmRegistrationId(int userId, String gcmRegistrationId);

}
