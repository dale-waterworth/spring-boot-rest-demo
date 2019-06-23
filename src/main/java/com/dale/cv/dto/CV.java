package com.dale.cv.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "cv")
public class CV {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty(message = "Name is required")
	@Column(length = 150)
	private String name;

	@NotEmpty(message = "Email is required")
	@Column(length = 150)
	private String email;

	/**
	 * Mapped - Maybe not the best options for large data sets
	 */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "cvId")
	private List<CompanyHistory> companyHistory;

	/**
	 * Not mapped - may be large or need some extra processing which 
	 * will be done in a service
	 */
	@Transient
	private List<Skill> skills;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "first_name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "email", nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<CompanyHistory> getCompanyHistory() {
		return companyHistory;
	}
	
	public void setCompanyHistory(List<CompanyHistory> companies) {
		this.companyHistory = companies;
	}
	
	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
}
