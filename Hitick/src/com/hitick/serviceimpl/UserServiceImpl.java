package com.hitick.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitick.bean.UserBean;
import com.hitick.dao.UserDao;
import com.hitick.service.UserService;

@Service
public class UserServiceImpl  implements UserService {

	@Autowired UserDao userDao;
	@Override
	public void addUser(UserBean user) {
		 userDao.addUser(user);
		
	}
	@Override
	public int validateUser(String mobileNumber, String password) {
		return userDao.validateUser(mobileNumber,password);
	}
	@Override
	public UserBean findUserById(int userId) {
		return userDao.findUserById(userId);
	}
	@Override
	public int addUserApp(UserBean user) {
		return userDao.addUserApp(user);
	}
	
	@Override
	public void updateGcmRegistrationId(int userId, String gcmRegistrationId) {
		userDao.updateGcmRegistrationId(userId,gcmRegistrationId);
		
	}

}
