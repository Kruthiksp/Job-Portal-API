package com.kruthik.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = {"applications"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "jobs")
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String title;
	private String description;
	private String location;
	private double salary;
	private LocalDate createDate;
	private LocalDate lastDateToApply;
	@Builder.Default
	private boolean isExpired = false;

	@ManyToOne
	@JoinColumn(name = "recruiter_id")
	private User user;
	@OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
	private List<Application> applications;

}
