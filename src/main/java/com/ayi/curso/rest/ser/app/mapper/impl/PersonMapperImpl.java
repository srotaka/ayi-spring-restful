package com.ayi.curso.rest.ser.app.mapper.impl;

import com.ayi.curso.rest.ser.app.dto.request.persons.PersonDTO;
import com.ayi.curso.rest.ser.app.dto.response.person.PersonResponseDTO;
import com.ayi.curso.rest.ser.app.dto.response.person.PersonResponseDTOFull;
import com.ayi.curso.rest.ser.app.entity.PersonEntity;
import com.ayi.curso.rest.ser.app.mapper.IPersonMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class PersonMapperImpl extends Exception implements IPersonMapper {

    private final ModelMapper modelMapper;
    @Override
    public PersonResponseDTO entityToDto(PersonEntity entity) {
        PersonResponseDTO personResponseDTO = new PersonResponseDTO();
        modelMapper.map(entity, personResponseDTO);
        return personResponseDTO;
    }
    @Override
    public PersonEntity dtoToEntity(PersonDTO dto) {
        PersonEntity personEntity = new PersonEntity();
        modelMapper.map(dto, personEntity);
        return personEntity;
    }

    @Override
    public PersonEntity toEntityByRequest(PersonDTO dto) {
        PersonEntity personEntity = new PersonEntity();
        modelMapper.map(dto, personEntity);
        return personEntity;
    }

    @Override
    public PersonResponseDTOFull listPersonDTOs(List<PersonEntity> listPersonEntities) {

        PersonResponseDTOFull listPersonResponseFullDTOs = new PersonResponseDTOFull();
        List<PersonResponseDTO> listPersonResponseDTOs = new ArrayList<>();
        listPersonEntities.forEach((PersonEntity personEntity) -> {
            PersonResponseDTO personResponseDTO = new PersonResponseDTO(
                    personEntity.getIdPerson(),
                    personEntity.getFirstName(),
                    personEntity.getLastName(),
                    personEntity.getTypeDocument(),
                    personEntity.getNumberDocument(),
                    personEntity.getDateBorn(),
                    personEntity.getDateCreated(),
                    personEntity.getDateModified()
            );

            listPersonResponseDTOs.add(personResponseDTO);

        });

        listPersonResponseFullDTOs.setPersonResponseDTO(listPersonResponseDTOs);

        return listPersonResponseFullDTOs;
    }
}