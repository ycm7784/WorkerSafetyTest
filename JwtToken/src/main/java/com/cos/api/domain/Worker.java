package com.cos.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Worker {
	@Id
	@Column(name = "userCode")
	private Integer userCode;
	private String name;
	private String gender;
	private Integer age;
	private String role;
	
	

	public Integer getUserCode() {
		return userCode;
	}
	public void setUserCode(Integer userCode) {
		this.userCode = userCode;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "Worker [userCode=" + userCode + ", name=" + name + ", gender=" + gender + ", age=" + age + ", role="
				+ role + "]";
	}
	
	
}
