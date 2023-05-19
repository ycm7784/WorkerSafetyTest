package com.cos.jwt.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Manager {
	@Id
	private String manager_id;
	private String password;
	private String name;
	private String role;
}
