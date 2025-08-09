package com.kruthik.entities;

import java.time.LocalDate;
import java.util.List;

import com.kruthik.enums.Roles;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String email;
	private String password;
	@Enumerated(EnumType.STRING)
	private Roles role;
	private String companyName; // for Recruiters
	private String resumePath; // for job seekers
	private LocalDate createDate;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Job> job;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Application> application;

}
