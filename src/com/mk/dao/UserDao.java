package com.mk.dao;

import java.sql.ResultSet;

import com.mk.util.DBUtil;

public class UserDao {
	public void registerUser(String firstname,String lastname,String username,String password) throws Exception {
		String sql="insert into users(firstname,lastname,username,password) "
				+ "values('"+firstname+"','"+lastname+"','"+username+"','"+password+"')";
		DBUtil.executeUpdate(sql);
		
	}
	
	public boolean authenticate(String username,String password) throws Exception {
		boolean authenticated=false;
		String sql="select username,password,count(username) as count from users "
				+ "where username='"+username+"' and password='"+password+"'";
		ResultSet rs=DBUtil.executeQuery(sql);
		while(rs.next()) {
			int count=rs.getInt("count");
			if(count>0) {
				String user=rs.getString("username");
				String pass=rs.getString("password");
				if(user.equals(username) && pass.equals(password)) {
					authenticated=true;
					
				}else {
					authenticated=false;
				}
			}else {
				authenticated=false;
			}
			
		}
		
		return authenticated;
		
		
	}
	
	public boolean isExist(String username) throws Exception {
		boolean exist=false;
		String sql="select count(username) as count from users where username='"+username+"'";
		ResultSet rs=DBUtil.executeQuery(sql);
		while(rs.next()) {
			int count=rs.getInt("count");
			if(count > 0) {
				exist=true;
			}
		}
		return exist;
		
	}

}
