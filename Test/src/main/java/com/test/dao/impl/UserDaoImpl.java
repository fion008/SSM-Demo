package com.test.dao.impl;

import org.springframework.stereotype.Service;

import com.test.base.dao.impl.BaseDaoImpl;
import com.test.dao.UserDao;
import com.test.entity.User;

@Service("userDao")
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	public User selectByPrimaryKey(Long id) {

		return (User) getByKey(id, User.class);
	}
}
