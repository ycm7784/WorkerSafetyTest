package com.cos.jwt.domain;


import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class WorkerDetails {
	@Id
	private Integer no;
	@ManyToOne()
	@JoinColumn(name = "userCode")
	private Worker  userCode;
	private Double gx;
	private Double gy;
	private Double gz;
	private LocalTime time;
	private Integer heartbeat;
	private Double temp;
	
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public Worker getUserCode() {
		return userCode;
	}
	public void setUserCode(Worker userCode) {
		this.userCode = userCode;
	}
	public Double getGx() {
		return gx;
	}
	public void setGx(Double gx) {
		this.gx = gx;
	}
	public Double getGy() {
		return gy;
	}
	public void setGy(Double gy) {
		this.gy = gy;
	}
	public Double getGz() {
		return gz;
	}
	public void setGz(Double gz) {
		this.gz = gz;
	}
	
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public Integer getHeartbeat() {
		return heartbeat;
	}
	public void setHeartbeat(Integer heartbeat) {
		this.heartbeat = heartbeat;
	}
	public Double getTemp() {
		return temp;
	}
	public void setTemp(Double temp) {
		this.temp = temp;
	}
	
}
