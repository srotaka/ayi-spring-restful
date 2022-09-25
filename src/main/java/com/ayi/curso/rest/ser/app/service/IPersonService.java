package com.ayi.curso.rest.ser.app.service;

import com.ayi.curso.rest.ser.app.dto.request.persons.PersonDTO;
import com.ayi.curso.rest.ser.app.dto.response.person.PersonResponseDTO;

import java.util.List;

public interface IPersonService {
    PersonResponseDTO savePerson(PersonDTO personDTO);
    List<PersonResponseDTO> findAllPersons();
    PersonResponseDTO findPersonById(Long idPerson);
    void delete(Long idPerson);
}
