package com.dale.cv.dto;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;


@Entity
@Table(name = "company_history")
public class CompanyHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private int cvId;
	private String name;
	private LocalDateTime fromDate;
	private LocalDateTime toDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Special annotations for storing date:
	 * requires:
	 * app props - spring.jackson.serialization.write-dates-as-timestamps=false
	 * maven: 
	 * <dependency>
			<groupId>com.fasterxml.jackson.datatype</groupId>
			<artifactId>jackson-datatype-jsr310</artifactId>
		</dependency>
	 * 
	 * @return
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	public LocalDateTime getFromDate() {
		return fromDate;
	}

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	public void setFromDate(LocalDateTime fixedDateFrom) {
		this.fromDate = fixedDateFrom;
	}
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	public LocalDateTime getToDate() {
		return toDate;
	}

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	public void setToDate(LocalDateTime fixedDateTo) {
		this.toDate = fixedDateTo;
	}

	public int getCvId() {
		return cvId;
	}

	public void setCvId(int cvId) {
		this.cvId = cvId;
	}
}
