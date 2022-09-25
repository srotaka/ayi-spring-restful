package com.ayi.curso.rest.ser.app.controller;
import com.ayi.curso.rest.ser.app.dto.request.persons.PersonDTO;
import com.ayi.curso.rest.ser.app.dto.response.person.PersonResponseDTO;
import com.ayi.curso.rest.ser.app.service.IPersonService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@Api(value = "Person Api", tags = {"Person Service"})
@RestController
@RequestMapping(value = "/persons", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class PersonController {
    private IPersonService personService;

    @PostMapping(value = "/savePerson")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(
            value ="Save a person and return it.",
            httpMethod = "POST",
            response = PersonResponseDTO.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201,
                    message = "CREATED - Person was successfully created",
                    response = PersonResponseDTO.class),
            @ApiResponse(code = 400,
                    message = "INVALID_ARGUMENT - Certain arguments "
                            + "cannot be empty or null.")
    })

    public ResponseEntity<PersonResponseDTO> savePerson(@RequestBody PersonDTO request) {
        //PersonResponseDTO personResponseDTO = personService.savePerson(request);
        return  new ResponseEntity<PersonResponseDTO>(personService.savePerson(request), HttpStatus.CREATED);
    }

    @GetMapping(value = "/getAllPersons")
    @ApiOperation(
            value = "Retrieves List of all Persons",
            httpMethod = "GET",
            response = PersonResponseDTO[].class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Body content with basic information about persons",
                    response = PersonResponseDTO[].class),
            @ApiResponse(
                    code = 400,
                    message = "Describes errors on invalid payload received, e.g: missing fields, invalid data formats, etc.")
    })
    public ResponseEntity<List<PersonResponseDTO>> getAllPersons() {

        List<PersonResponseDTO> personResponseDTOs = personService.findAllPersons();
        return ResponseEntity.ok(personResponseDTOs);
    }

    @GetMapping(
            value = "/getPersonById/{id}", produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiOperation(
            value = "Retrieves data associated to Master List by Id",
            httpMethod = "GET",
            response = PersonResponseDTO.class
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Body content with basic information for Master List  by Id"
            ),
            @ApiResponse(
                    code = 400,
                    message = "Describes errors on invalid payload received, e.g: missing fields, invalid data formats, etc.")
    })
    public ResponseEntity<PersonResponseDTO> getPersonById(
            @ApiParam(name = "id", required = true, value = "Person Id", example = "1")
            @PathVariable("id") Long id){

        return ResponseEntity.ok(personService.findPersonById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a person by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Person deleted by id"),
            @ApiResponse(code = 404, message = "Person not found"),
            @ApiResponse(code = 400 , message = "Bad request/Invalid field")})
    public ResponseEntity<Void> deletePerson(
            @ApiParam(name = "id", required = true, value = "Person Id", example = "1")
            @PathVariable Long id){
        personService.delete(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }




}
