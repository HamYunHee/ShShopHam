package com.shshop.service;

import org.apache.ibatis.session.SqlSession;

import com.shshop.domain.Product;
import com.shshop.domain.User;
import com.shshop.mapper.ProductMapper;
import com.shshop.mapper.UserMapper;
import com.shshop.util.MyBatisUtil;

public class AuthenticatorService {
	SqlSession sqlSession = null;

	public AuthenticatorService() {
		
	}
	
	public boolean canLogin(String email, String password) {
		sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		
		boolean loginResult = false;

		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			if (userMapper.getUserByEmail(email, password) != null) {
				loginResult = true;
			}
			
		} finally {
			sqlSession.close();
		}

		return loginResult;
	}

	public User getExistsUser(String email, String password) {
		sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		
		User user = null;

		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			user = userMapper.getUserByEmail(email, password);

		} finally {
			sqlSession.close();
		}

		return user;
	}
	
	public void insertProduct(Product product) {
		sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		
		try {
			ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
			productMapper.insertProduct(product);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
	
}
