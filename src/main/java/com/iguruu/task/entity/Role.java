package com.iguruu.task.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
@Table(name = "Roles")


public class Role {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long  id ;
	 
	 @Column(nullable = false)
	 private String rolename;
	

	
}
