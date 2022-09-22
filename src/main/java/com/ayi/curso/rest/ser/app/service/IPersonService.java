package com.ayi.curso.rest.ser.app.service;

import com.ayi.curso.rest.ser.app.dto.response.person.PersonResponseDTO;

import java.util.List;

public interface IPersonService {

    List<PersonResponseDTO> findAllPersons();
}
