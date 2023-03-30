package org.antwalk.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "user")
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username", unique=true)
	@NotNull
	private String userName;

	@Column(name = "password")
	@NotNull
	private String password;

//	@Column(name = "first_name")
//	private String firstName;

//	@Column(name = "last_name")
//	private String lastName;

//	@Column(name = "email")
//	private String email;

	@Column(name = "role")
	@NotNull
	private String role;

	@JsonManagedReference
	@OneToOne(mappedBy = "user")
	private Employee employee;
	
	@JsonManagedReference
	@OneToOne(mappedBy = "user")
	private Driver driver;
	

	@JsonManagedReference
	@OneToOne(mappedBy = "user")
	private Admin admin;
	
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public User() {
	}

	public User(String userName, String password, String firstName, String lastName, String email) {
		this.userName = userName;
		this.password = password;
	}

	public User(String userName, String password, String firstName, String lastName, String email,
			String role) {
		this.userName = userName;
		this.password = password;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public User(String userName, String password, String role, Employee employee, Driver driver, Admin admin) {
		super();
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.employee = employee;
		this.driver = driver;
		this.admin = admin;
	}

	@Override
	public int hashCode() {
		return Objects.hash(admin, driver, employee, id, password, role, userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(admin, other.admin) && Objects.equals(driver, other.driver)
				&& Objects.equals(employee, other.employee) && Objects.equals(id, other.id)
				&& Objects.equals(password, other.password) && Objects.equals(role, other.role)
				&& Objects.equals(userName, other.userName);
	}

	public User(Long id, String userName, String password, String role) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.role = role;
	}

	// public User(Long id, String userName, String password, String role,Employee emp) {
	// 	super();
	// 	this.id = id;
	// 	this.userName = userName;
	// 	this.password = password;
	// 	this.role = role;
	// 	this.employee = emp;
	// }
	public User(Long id, @NotNull String userName, @NotNull String password, @NotNull String role, Employee employee) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.employee = employee;
	}


//	public void setRoles(Collection<Role> roles) {
//		this.roles = roles;
//	}
	
	

	

}
