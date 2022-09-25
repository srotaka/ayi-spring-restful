package com.ayi.curso.rest.ser.app.dto.request.persons;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(
        value = "PersonDTO",
        description = "Represents the data needed to created Persons"
)

public class PersonDTO implements Serializable {

    @ApiModelProperty(position = 1, required = true, notes = "Non negative value. First name is required.")
    private String firstName;

    @ApiModelProperty(position = 2, required = true, notes = "Non negative value. Last name is required.")
    private String lastName;

    @ApiModelProperty(position = 3, required = true, notes = "Non negative value. Document type is required.")
    private String typeDocument;

    @ApiModelProperty(position = 4, required = true, notes = "Non negative value. Document number is required.")
    private Integer numberDocument;

    @ApiModelProperty(position = 5, required = true, notes = "Non negative value. Date of birth is required.")
    private LocalDate dateBorn;

    @ApiModelProperty(position = 6, required = true, notes = "Non negative value. Creation date is required.")
    private LocalDate dateCreated;

    @ApiModelProperty(position = 7, notes = "Non negative value. Modification date is required.")
    private LocalDate dateModified;

}