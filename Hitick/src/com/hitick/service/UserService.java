package com.hitick.service;

import com.hitick.bean.UserBean;


public interface UserService {

	void addUser(UserBean user);

	int validateUser(String mobileNumber, String password);

	UserBean findUserById(int userId);

	int addUserApp(UserBean user);

	void updateGcmRegistrationId(int userId, String gcmRegistrationId);

 

}
