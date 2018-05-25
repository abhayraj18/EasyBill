package com.easybill.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.easybill.model.metadata.EnumConstant;
import com.easybill.model.metadata.EnumConstant.Status;
import com.easybill.util.DateUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "USER_TYPE")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false, length = 50)
	private String username;

	@Column(nullable = false, length = 50)
	private String email;
	
	@Column(columnDefinition = "bit(1) default b'0'")
	private boolean emailVerified;
	
	private String emailVerificationToken;

	@Column(nullable = false, length = 100)
	private String password;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date passwordChangedAt;

	private String address;

	@Column(nullable = false, length = 15)
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = EnumConstant.STATUS, nullable = false)
	private EnumConstant.Status status;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date statusChangedAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedAt;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserPassword> userPasswords = new ArrayList<>();

	protected User() {
	}

	public User(String name, String username, String email, String address, String phoneNumber) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.address = address;
		this.phoneNumber = phoneNumber;
		setStatus(Status.ACTIVE);
		setCreatedAt(DateUtil.getCurrentTime());
	}

	public boolean isDistributor() {
		return this instanceof Distributor;
	}

	public boolean isWholesaler() {
		return this instanceof Wholesaler;
	}

	public void setPassword(String password) {
		Date currentTime = DateUtil.getCurrentTime();
		if (Objects.nonNull(getPassword())) {
			setPasswordChangedAt(currentTime);
			setLastModifiedAt(currentTime);
		}
		this.password = password;
		
		UserPassword userPassword = new UserPassword();
		userPassword.setPassword(password);
		userPassword.setCreatedAt(currentTime);
		userPassword.setUser(this);
		getUserPasswords().add(userPassword);
	}

	public void setStatus(EnumConstant.Status status) {
		if (Objects.nonNull(getStatus())) {
			Date currentTime = DateUtil.getCurrentTime();
			setStatusChangedAt(currentTime);
			setLastModifiedAt(currentTime);
		}
		this.status = status;
	}

}
