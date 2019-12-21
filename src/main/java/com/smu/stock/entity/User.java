package com.smu.stock.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

	private String uid;

	private String userName;

	private String password;
	/*电话*/
	private String mobile;
	/*角色*/
	private String role;

	//用户状态
	private String status;
}
