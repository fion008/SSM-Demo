package com.test.dao;

import com.test.entity.User;

public interface UserDao {

	User selectByPrimaryKey(Long id);

}