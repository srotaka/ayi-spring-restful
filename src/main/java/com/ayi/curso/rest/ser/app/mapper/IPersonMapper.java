package com.ayi.curso.rest.ser.app.mapper;


import com.ayi.curso.rest.ser.app.dto.request.persons.PersonDTO;
import com.ayi.curso.rest.ser.app.dto.response.person.PersonResponseDTO;
import com.ayi.curso.rest.ser.app.dto.response.person.PersonResponseDTOFull;
import com.ayi.curso.rest.ser.app.entity.PersonEntity;

import java.util.List;

public interface IPersonMapper {

    PersonResponseDTO entityToDto(PersonEntity entity);
    PersonEntity dtoToEntity(PersonDTO dto);
    PersonEntity toEntityByRequest(PersonDTO dto);

    PersonResponseDTOFull listPersonDTOs(List<PersonEntity> listPersonEntities);
}