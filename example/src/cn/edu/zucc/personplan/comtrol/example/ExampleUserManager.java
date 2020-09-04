package cn.edu.zucc.personplan.comtrol.example;

import java.sql.Connection;
import java.util.Scanner;

import cn.edu.zucc.personplan.itf.IUserManager;
import cn.edu.zucc.personplan.model.BeanUser;
import cn.edu.zucc.personplan.util.BaseException;
import cn.edu.zucc.personplan.util.DBUtil;

public class ExampleUserManager implements IUserManager {

	@Override
	public BeanUser reg(String userid, String pwd,String pwd2) throws BaseException {
		// TODO Auto-generated method stub
		BeanUser ret=null;
		if(pwd!=pwd2) {
			return null;
		}
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="selece * from tbl_user where user_id=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				throw new BaseException("该用户名已被注册！");
			}
			sql="insert tbl_user values('?', '?', NOW())";
			pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			pst.setString(2, pwd);
			pst.execute();
			ret.setUser_name(userid);
			ret.setUser_pwd(pwd);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		} finally {
			if(conn!=null) {
				try {
					conn.close();
				} catch(java.sql.SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

	
	@Override
	public BeanUser login(String userid, String pwd) throws BaseException {
		// TODO Auto-generated method stub
		java.sql.Connection conn=null;
		BeanUser ret=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select user_pwd from tbl_user where user_id=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			int t=0;
			while(rs.next()) {
				t++;
				if(rs.getString(1)==pwd) {
					ret.setUser_name(userid);
					ret.setUser_pwd(pwd);
				} else throw new BaseException("密码错误！");
			}
			if(t==0) throw new BaseException("用户不存在！");
		} catch(java.sql.SQLException e) {
			e.printStackTrace();
		} finally {
			if(conn!=null) {
				try {
					conn.close();
				} catch(java.sql.SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}


	@Override
	public void changePwd(BeanUser user, String oldPwd, String newPwd,
			String newPwd2) throws BaseException {
		// TODO Auto-generated method stub
		java.sql.Connection conn=null;
		if(newPwd!=newPwd2) {
			return;
		}
		try {
			conn=DBUtil.getConnection();
			String sql="select user_pwd from tbl_user where user_id=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUser_name());
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				if(oldPwd!=user.getUser_pwd()) return;
			}
			sql="update from tbl_user where user_id=? set user_pwd=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUser_name());
			pst.setString(2, newPwd);
			pst.execute();
		} catch(java.sql.SQLException e) {
			e.printStackTrace();
		} finally {
			if(conn!=null) {
				try {
					conn.close();
				} catch(java.sql.SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
