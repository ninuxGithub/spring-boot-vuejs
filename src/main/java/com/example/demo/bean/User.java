package com.example.demo.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "user")
public class User implements Serializable {

	
	private static final long serialVersionUID = 8859547318519046581L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ExcelField(title="用户ID", order=1)
	private Long id;

	@NotEmpty
	@ExcelField(title="用户名称", order=2)
	private String username;

	@NotEmpty
	@ExcelField(title="用户密码", order=3)
	private String password;

	@ExcelField(title="用户角色", order=4)
	private String role;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = {
			@JoinColumn(name = "userId", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "roleId", referencedColumnName = "id") })
	private List<Role> roles = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + ", roles="
				+ roles + "]";
	}
	
	

//	@Override
//	public String toString() {
//		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + "]";
//	}

}
