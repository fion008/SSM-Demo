package com.test.base.dao.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.test.base.annotation.AutoIncrement;
import com.test.base.annotation.PrimaryKey;
import com.test.base.annotation.Table;
import com.test.base.dao.BaseDao;
import com.test.base.exception.BaseException;
import com.test.base.model.BaseModel;
import com.test.base.tool.ConvertUtil;

public class BaseDaoImpl extends SqlSessionDaoSupport implements BaseDao {

	@Autowired
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}
	
	public int insert(BaseModel t) {
		Class<? extends BaseModel> c = t.getClass();
		String tableName = tableName(c);
		HashMap<String, Object> params = new HashMap<String, Object>();
		ArrayList<String> modelFields = new ArrayList<String>();
		ArrayList<Object> modelValues = new ArrayList<Object>();

		Method[] ms = c.getMethods();
		for (Method m : ms) {// 注意自增字段的验证
			String mName = m.getName();
			if (mName.startsWith("get") && (!mName.equals("getClass"))) {
				Object val;
				try {
					val = m.invoke(t, EMPTY_PARAM);
					if (val == null) {
						continue;
					}
				} catch (Exception e) {
					throw new BaseException("invoke failed", e);
				}
				AutoIncrement ai = m.getAnnotation(AutoIncrement.class);
				if (ai != null) {
					throw new BaseException("AutoIncrement field should be null when insert");
				}
				modelFields.add(methodToField(mName));
				modelValues.add(val);
			}
		}
		if (modelFields.size() == 0) {
			throw new BaseException("Empty model");
		}
		params.put("tableName", tableName);
		params.put("modelFields", modelFields);
		params.put("modelValues", modelValues);
		return getSqlSession().insert("insertBaseModelByKey", params);
	}
	
	private String methodToField(String methodName) {
		methodName = methodName.substring(3);
		if (methodName.length() >= 2) {
			return methodName.substring(0, 1).toLowerCase()
					+ methodName.substring(1);
		}
		return methodName.toLowerCase();
	}
	
	public int update(BaseModel t) {
		Class<? extends BaseModel> c = t.getClass();
		String tableName = tableName(c);
		HashMap<String, Object> params = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> modelEntries = new ArrayList<HashMap<String, Object>>();
		String keyField = null;
		Object keyValue = null;
		Method[] ms = c.getMethods();
		for (Method m : ms) {// 注意自增字段的验证
			String mName = m.getName();
			if (mName.startsWith("get") && (!mName.equals("getClass"))) {
				Object val;
				try {
					val = m.invoke(t, EMPTY_PARAM);
				} catch (Exception e) {
					throw new BaseException("invoke failed", e);
				}
				PrimaryKey ai = m.getAnnotation(PrimaryKey.class);
				if (ai == null) {//普通 字段
					HashMap<String, Object> e = new HashMap<String, Object>();
					e.put("key", methodToField(mName));
					e.put("value", val);
					modelEntries.add(e);
				} else {
					if (keyField != null) {
						throw new BaseException("Duplicated keyField");//主键重复设置，最多只能设一个
					}
					keyField = methodToField(mName);
					keyValue = val;
				}
			}
		}
		if (keyField == null) {
			throw new BaseException("key field not found");
		}
		if (keyValue == null) {
			throw new BaseException("key value should not be null");
		}
		params.put("tableName", tableName);
		params.put("keyField", keyField);
		params.put("keyValue", keyValue);
		params.put("modelEntries", modelEntries);
		return getSqlSession().update("updateBaseModelByKey", params);
	}

	private static final Object[] EMPTY_PARAM = new Object[] {};
	private static final Class<?>[] OBJECT_CLASS = new Class<?>[] { Object.class };

	public BaseModel getByKey(Object kv, Class<? extends BaseModel> c) {
		if (kv == null) {
			throw new BaseException("key value should not be null");
		}
		String tableName = tableName(c);
		Method[] ms = c.getMethods();
		String keyField = null;
		ArrayList<String> modelFields = new ArrayList<String>();
		ArrayList<Class<?>> returnClass = new ArrayList<Class<?>>();
		for (Method m : ms) {// 注意自增字段的验证
			String mName = m.getName();
			if (mName.startsWith("get") && (!mName.equals("getClass"))) {
				String fieldName = methodToField(mName);
				PrimaryKey ai = m.getAnnotation(PrimaryKey.class);
				if (ai != null) {
					if (keyField != null) {
						throw new BaseException("Duplicated keyField");//主键重复设置，最多只能设一个
					}
					keyField = fieldName;
				}
				modelFields.add(fieldName);
				returnClass.add(m.getReturnType());
			}
		}
		if (keyField == null) {
			throw new BaseException("key field not found");
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("tableName", tableName);
		params.put("modelFields", modelFields);
		params.put("keyField", keyField);
		params.put("keyValue", kv);
		HashMap<String, Object> mapResult = getSqlSession().selectOne("selectBaseModelByKey", params);
		if (mapResult == null) {//未找到值
			return null;
		}
		BaseModel bm;
		try {
			bm = c.newInstance();
		} catch (Exception e) {
			throw new BaseException("init model failed", e);
		}
		for (int i = 0; i < modelFields.size(); i++) {
			String field = modelFields.get(i);
			Object val = mapResult.get(field);
			Class<?> rClass = returnClass.get(i);
			String mName = "set" + field.substring(0, 1).toUpperCase() + field.substring(1);
			String convertName = ConvertUtil.TYPE_MAPPING.get(rClass.getName());
			if (convertName == null) {
				throw new BaseException("Mapping not configured, please add in ConvertUtil. " + rClass.getName());
			}
			try {
				Object realObj = ConvertUtil.class.getMethod(convertName, OBJECT_CLASS).invoke(null, new Object[] { val });
				c.getMethod(mName, new Class[] { rClass }).invoke(bm, new Object[] { realObj });
			} catch (Exception e) {
				throw new BaseException("invoke model failed", e);
			}
		}
		return bm;
	}

	public int deleteByKey(Object kv, Class<? extends BaseModel> c) {
		if (kv == null) {
			throw new BaseException("key value should not be null");
		}
		String keyField = null;
		Method[] ms = c.getMethods();
		for (Method m : ms) {// 注意自增字段的验证
			String mName = m.getName();
			if (mName.startsWith("get") && (!mName.equals("getClass"))) {
				String fieldName = methodToField(mName);
				PrimaryKey ai = m.getAnnotation(PrimaryKey.class);
				if (ai != null) {
					if (keyField != null) {
						throw new BaseException("Duplicated keyField");//主键重复设置，最多只能设一个
					}
					keyField = fieldName;
				}
			}
		}
		if (keyField == null) {
			throw new BaseException("key field not found");
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("tableName", tableName(c));
		params.put("keyField", keyField);
		params.put("keyValue", kv);
		
		return getSqlSession().delete("deleteBaseModelByKey", params);
	}

	private String tableName(Class<? extends BaseModel> c) {// 找表名
		Table t = c.getAnnotation(Table.class);
		if (t == null) {
			throw new BaseException("table not specified");
		}
		return t.value();
	}

	public Date findDbTime() {
		return getSqlSession().selectOne("com.test.base.dao.impl.BaseDaoImpl.findDbTime");
	}
}
