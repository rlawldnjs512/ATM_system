package com.min.edu.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Booth {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long boothid;
	
	private String type;
	
	private String color;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,
			   mappedBy = "booth", fetch = FetchType.LAZY)
//	@JsonBackReference
	private List<ATM> atms;
	
	public Booth(String type, String color) {
		super();
		this.type = type;
		this.color = color;
	}
	
	
	
}
