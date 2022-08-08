package com.api.people.manager.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
	@ApiModelProperty(hidden = true, notes = "Id of the Person")
	private Long idPerson;
	@NotNull(message = "Name is mandatory")
	@NotEmpty(message = "Name is mandatory")
	@Size(max = 30, message = "Name must not be more than 30 characters")
	@ApiModelProperty(notes = "Name of the Person", example = "Peter", required = true)
	private String name;
	@NotNull(message = "LastName is mandatory")
	@NotEmpty(message = "LastName is mandatory")
	@Size(max = 30, message = "LastName must not be more than 30 characters")
	@ApiModelProperty(notes = "LastName of the Person", example = "Parker", required = true)
	private String lastName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@NotNull(message = "Age is mandatory")
	@ApiModelProperty(notes = "Age of the Person", example = "1992-05-23", required = true)
	private Date age;
}
