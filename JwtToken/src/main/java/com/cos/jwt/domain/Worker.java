package com.cos.jwt.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Worker {
	@Id
	@Column(name = "UserCode")
	private Integer UserCode;
	private String name;
	private String gender;
	private Integer age;
	private String role;
	
	
	public Integer getUserCode() {
		return UserCode;
	}
	public void setWorkerDetails(Integer UserCode) {
		this.UserCode = UserCode;
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
	
	
}
