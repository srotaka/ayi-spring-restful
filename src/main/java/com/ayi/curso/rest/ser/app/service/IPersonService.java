package com.ayi.curso.rest.ser.app.service;

import com.ayi.curso.rest.ser.app.dto.request.persons.PersonDTO;
import com.ayi.curso.rest.ser.app.dto.response.person.PersonResponseDTO;
import com.ayi.curso.rest.ser.app.dto.response.person.PersonResponseDTOFull;
import com.ayi.curso.rest.ser.app.exceptions.ReadAccessException;

import java.util.List;

public interface IPersonService {
    PersonResponseDTO addPerson(PersonDTO personDTO);
    List<PersonResponseDTO> findAllPersons() throws ReadAccessException;
    PersonResponseDTO findPersonById(Long idPerson) throws ReadAccessException;
    void delete(Long idPerson);

    PersonResponseDTOFull findAllPersonsForPage(Integer page, Integer size);

    PersonResponseDTO modifyPersonById(Long id, PersonDTO personDTO);
}
