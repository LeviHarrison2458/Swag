package com.tts.usersapi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class APIUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ApiModelProperty(
			  value = "first name of the user",
			  name = "firstName",
			  dataType = "String",
			  example = "Kobe")
	private String firstName;
	@ApiModelProperty(
			  value = "last name of the user",
			  name = "lastName",
			  dataType = "String",
			  example = "Bryant")
	private String lastName;
	
	@ApiModelProperty(
			  value = "state of residence of the user",
			  name = "state",
			  dataType = "String",
			  example = "California")
	private String state;

}
