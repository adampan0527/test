package cn.edu.zucc.personplan.model;

public class BeanUser {
	private String user_name=null;
	private String user_pwd=null;
	public static BeanUser currentLoginUser=null;
	public void setUser_name(String name) {
		this.user_name=name;
	}
	public void setUser_pwd(String pwd) {
		this.user_pwd=pwd;
	}
	public String getUser_name() {
		return user_name;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
}
