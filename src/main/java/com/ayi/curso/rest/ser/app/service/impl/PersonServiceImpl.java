package com.ayi.curso.rest.ser.app.service.impl;

import com.ayi.curso.rest.ser.app.dto.request.persons.PersonDTO;
import com.ayi.curso.rest.ser.app.dto.response.person.PersonResponseDTO;
import com.ayi.curso.rest.ser.app.entity.PersonEntity;
import com.ayi.curso.rest.ser.app.mapper.IPersonMapper;
import com.ayi.curso.rest.ser.app.repository.IPersonRepository;
import com.ayi.curso.rest.ser.app.service.IPersonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
@Transactional
public class PersonServiceImpl extends Exception implements IPersonService {

    @Autowired
    private IPersonRepository personRepository;
    @Autowired
    private IPersonMapper personMapper;
    @Override
    public PersonResponseDTO savePerson(PersonDTO personDTO){
        PersonEntity personEntity = personRepository.save(personMapper.dtoToEntity(personDTO));
        return personMapper.entityToDto(personEntity);
    }

    @Override
    public List<PersonResponseDTO> findAllPersons() {

        List<PersonResponseDTO> personResponseDTOs;
        List<PersonEntity> personEntities = personRepository.findAll();

        /* Incorporar validaciones y excepciones */
        /*if (listMasterEntities.size() == 0) {
            errorResponse.throwError(HttpStatus.BAD_REQUEST, ErrorConstants.LIST_MASTER_CRITERIA_NOT_FOUND);
        }*/

        personResponseDTOs = personEntities.stream()
                .map(lt -> new PersonResponseDTO(
                        lt.getIdPerson(),
                        lt.getFirstName(),
                        lt.getLastName(),
                        lt.getTypeDocument(),
                        lt.getNumberDocument(),
                        lt.getDateBorn(),
                        lt.getDateCreated(),
                        lt.getDateModified()
                ))
                .collect(Collectors.toList());
        return personResponseDTOs;
    }

    @Override
    public PersonResponseDTO findPersonById(Long idPerson) {

        PersonResponseDTO personResponseDTO;
        Optional<PersonEntity> entity = personRepository.findById(idPerson);

        if (!entity.isPresent()) {
            throw new RuntimeException("Error. ID not found.");
        }

        personResponseDTO = personMapper.entityToDto(entity.get());
        return personResponseDTO;
    }
    @Override
    public void delete(Long idPerson){
        Optional<PersonEntity> person = personRepository.findById(idPerson);

        if (person.isPresent()) {
            personRepository.deleteById(idPerson);
        } else {
            throw new RuntimeException("Error. ID not found.");
        }
    }


}
