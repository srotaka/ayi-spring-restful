package com.ayi.curso.rest.ser.app.controller;
import com.ayi.curso.rest.ser.app.dto.request.persons.PersonDTO;
import com.ayi.curso.rest.ser.app.dto.response.person.PersonResponseDTO;
import com.ayi.curso.rest.ser.app.dto.response.person.PersonResponseDTOFull;
import com.ayi.curso.rest.ser.app.exceptions.ReadAccessException;
import com.ayi.curso.rest.ser.app.service.IPersonService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@AllArgsConstructor
@Api(value = "Person Api", tags = {"Person Service"})
@RestController
@RequestMapping(value = "/persons", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class PersonController {
    private IPersonService personService;

    @PostMapping(value = "/addPerson ")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value ="Save a person and return it.", httpMethod = "POST",response = PersonResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created. Person was successfully created",response = PersonResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad request/Invalid field")
    })
    public ResponseEntity<PersonResponseDTO> addPerson (@RequestBody PersonDTO request) {

        return  new ResponseEntity<PersonResponseDTO>(personService.addPerson(request), HttpStatus.CREATED);
    }

    @GetMapping(value = "/getAllPersons")
    @ApiOperation(value = "Retrieves List of all Persons",httpMethod = "GET",response = PersonResponseDTO[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Body content with basic information about persons", response = PersonResponseDTO[].class),
            @ApiResponse(code = 404, message = "Person not found"),
            @ApiResponse(code = 400 , message = "Bad request/Invalid field")})
    public ResponseEntity<?> getAllPersons(){

        List<PersonResponseDTO> personResponseDTOs = null;
        Map<String, Object> response = new HashMap<>();

        try {
            personResponseDTOs = personService.findAllPersons();
        } catch (ReadAccessException e) {
            response.put("Mensaje", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }catch (Exception ex) {
            response.put("Código de error", 400);
            response.put("Mensaje", ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(personResponseDTOs);
    }

    @GetMapping(value = "/getPersonById/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Retrieves data associated to Master List by Id",httpMethod = "GET",response = PersonResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Success. Person found by ID."),
            @ApiResponse(code = 404, message = "Person not found"),
            @ApiResponse(code = 400 , message = "Bad request/Invalid field")})
    public ResponseEntity<?> getPersonById(
            @ApiParam(name = "id", required = true, value = "Person Id", example = "1")
            @PathVariable("id") Long id){

        Map<String, Object> response = new HashMap<>();

        try {
            return ResponseEntity.ok(personService.findPersonById(id));
        } catch (ReadAccessException e) {
            response.put("Código de error", 404);
            response.put("Mensaje", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }catch (Exception ex) {
            response.put("Código de error", 400);
            response.put("Mensaje", ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a person by id",httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success. Person deleted by id"),
            @ApiResponse(code = 404, message = "Person not found"),
            @ApiResponse(code = 400 , message = "Bad request/Invalid field")})
    public ResponseEntity<Void> deletePerson(
            @ApiParam(name = "id", required = true, value = "Person Id", example = "1")
            @PathVariable Long id){
        personService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping(value = "/getAllPersons/{page}/{size}")
    @ApiOperation(value = "Retrieves all Lists Persons", httpMethod = "GET", response = PersonResponseDTO[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success. List of persons retrieved.",response = PersonResponseDTO[].class),
            @ApiResponse(code = 400,message = "Bad request/Invalid field")})
    public ResponseEntity<?> getAllPersonsForPage(
                                                   @ApiParam(value = "page to display", required = true, example = "1")
                                                   @PathVariable(name = "page") Integer page,
                                                   @ApiParam(value = "number of items per request", required = true, example = "1")
                                                   @PathVariable(name = "size") Integer size) {

        PersonResponseDTOFull personResponseDTOFull;
        Map<String, Object> response = new HashMap<>();

        personResponseDTOFull = personService.findAllPersonsForPage(page, size);

        if(personResponseDTOFull == null) { // Si no encontró nada
            response.put("Message", "No se encontró información de Personas en el sistema");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        // Si llego acá, entonces encontró algo
        return new ResponseEntity<>(personResponseDTOFull, HttpStatus.OK);

    }

    /*  --------------     REVISAR      -------------- */
    @PutMapping(value = "/updatePersonById/{id}")
    @ApiOperation(value = "Updates person information",httpMethod = "PUT",response = PersonResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Success. Person updated.")})
    public ResponseEntity<?> updatePersonById(
            @ApiParam(value = "person ID", required = true, example = "1")
            @PathVariable(name = "id") Long id,
            @RequestBody PersonDTO personDTO) {

        PersonResponseDTO personResponseDTO = personService.modifyPersonById(id, personDTO);
        Map<String, Object> response = new HashMap<>();

        if(personResponseDTO == null) { // Si no encontró nada
            response.put("Message", "No se encontró el ID a modificar");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); // NOT FOUND (404), no lo encontró.
        }

        return new ResponseEntity<>(personResponseDTO, HttpStatus.CREATED); // CREATED (201)
    }


}
