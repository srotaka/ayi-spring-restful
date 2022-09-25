package com.ayi.curso.rest.ser.app.dto.response.person;

import com.fasterxml.jackson.annotation.JsonFormat;
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
        value = "PersonResponseDTO",
        description = "Represents the data needed to created Persons"
)
public class PersonResponseDTO implements Serializable {

    @ApiModelProperty(position = 1, notes = "Non negative value. ID is required.")
    private Long idPerson;

    @ApiModelProperty(position = 2, notes = "Non negative value. First name is required.")
    private String firstName;

    @ApiModelProperty(position = 3, notes = "Non negative value. Last name is required.")
    private String lastName;

    @ApiModelProperty(position = 4, notes = "Non negative value. Document type is required.")
    private String typeDocument;

    @ApiModelProperty(position = 5, notes = "Non negative value. Document number is required.")
    private Integer numberDocument;

    @ApiModelProperty(position = 6, notes = "Non negative value. Date of birth is required.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateBorn;

    @ApiModelProperty(position = 7, notes = "Creation date is required.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateCreated;

    @ApiModelProperty(position = 8, notes = "Modification date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateModified;

}