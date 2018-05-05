package com.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.dao.UserDao;
import com.test.entity.User;
import com.test.service.UserService;

@Transactional
@Service("userService")
public class UserServiceimpl implements UserService {

	@Autowired
	private UserDao userDao;

	public User getUser(Long id) {
		// TODO Auto-generated method stub
		return userDao.selectByPrimaryKey(id);
	}
}
