package com.test.base.dao;

import java.util.Date;

import com.test.base.model.BaseModel;

public interface BaseDao {

	/**
	 * 增加一个对象，自增字段需为空
	 * 
	 * @param model
	 * @return
	 */
	int insert(BaseModel model);

	/**
	 * 根据主键，更新一个对象
	 * 
	 * @param model
	 * @return
	 */
	int update(BaseModel model);

	/**
	 * 根据主键的值，查询一个对象
	 * 
	 * @param valueOfKey
	 *            主键的值
	 * @param clazz
	 *            对象类型
	 * @return
	 */
	BaseModel getByKey(Object valueOfKey, Class<? extends BaseModel> clazz);

	/**
	 * 根据主键的值，删除一个对象
	 * 
	 * 慎用
	 * 
	 * @param valueOfKey
	 *            -- 主键的值
	 * @param clazz
	 *            -- 对象类型
	 * @return
	 */
	int deleteByKey(Object valueOfKey, Class<? extends BaseModel> clazz);

	/**
	 * 查询数据库时间
	 * 
	 * @return
	 */
	Date findDbTime();
}
