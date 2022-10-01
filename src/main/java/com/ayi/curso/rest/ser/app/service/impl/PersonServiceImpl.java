package com.ayi.curso.rest.ser.app.service.impl;

import com.ayi.curso.rest.ser.app.dto.request.persons.PersonDTO;
import com.ayi.curso.rest.ser.app.dto.response.person.PersonResponseDTO;
import com.ayi.curso.rest.ser.app.dto.response.person.PersonResponseDTOFull;
import com.ayi.curso.rest.ser.app.entity.PersonEntity;
import com.ayi.curso.rest.ser.app.exceptions.ReadAccessException;
import com.ayi.curso.rest.ser.app.mapper.IPersonMapper;
import com.ayi.curso.rest.ser.app.repository.IPersonRepository;
import com.ayi.curso.rest.ser.app.service.IPersonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
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
    public PersonResponseDTO addPerson(PersonDTO personDTO){
        PersonEntity personEntity = personRepository.save(personMapper.dtoToEntity(personDTO));
        return personMapper.entityToDto(personEntity);
    }

    @Override
    public List<PersonResponseDTO> findAllPersons() throws ReadAccessException {

        List<PersonResponseDTO> personResponseDTOs;
        List<PersonEntity> personEntities = personRepository.findAll();

        if(personEntities == null || personEntities.size() == 0){
            throw new ReadAccessException("No existen registros de personas.");
        }

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
    public PersonResponseDTO findPersonById(Long idPerson) throws ReadAccessException {

        if(idPerson == null || idPerson < 0){
            throw new ReadAccessException("El id es nulo o vacío.");
        }


        PersonResponseDTO personResponseDTO;
        Optional<PersonEntity> entity = personRepository.findById(idPerson);

        if (!entity.isPresent()) {
            throw new ReadAccessException("Error. ID not found.");
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
    @Override
    public PersonResponseDTOFull findAllPersonsForPage(Integer page, Integer size) { // Paginación

        PersonResponseDTOFull personResponseDTOFull;

        Pageable pageable = PageRequest.of(page, size); // Pageable: Interfaz para información de paginación

        //PageRequest es una clase, que implementa la interfaz Pageable
        // 	of(int page, int size) Creates a new unsorted PageRequest

        Page<PersonEntity> personEntityPages = personRepository.findAll(pageable); // Findall está sobrecargado, si no le paso nada me busca tdo, si le paso el pageable me busca la página

        if(personEntityPages != null && !personEntityPages.isEmpty()) { // Que no sea nulo y que contenga páginas
            personResponseDTOFull = personMapper.listPersonDTOs(personEntityPages.getContent()); // El mapper lo agregué yo, y le paso el objeto Page que tiene el contenido de la página
            personResponseDTOFull.setSize(personEntityPages.getSize()); // Tamaño de la página
            personResponseDTOFull.setCurrentPage(personEntityPages.getNumber() + 1); // La página actual, revisar esto (primer página 0 o 1)
            personResponseDTOFull.setTotalPages(personEntityPages.getTotalPages());
            personResponseDTOFull.setTotalElements((int) personEntityPages.getTotalElements()); // Me devuelve Long, así que lo parseo
            return personResponseDTOFull;
        } else {
            throw new RuntimeException("Error en el proceso");
        }
    }

    @Override
    public PersonResponseDTO modifyPersonById(Long id, PersonDTO personDTO) {
        Optional<PersonEntity> entityOptional = personRepository.findById(id);
        PersonEntity entity = entityOptional.get();

        if(entityOptional.isPresent()) {
            entity.setFirstName(personDTO.getFirstName());
            entity.setLastName(personDTO.getLastName());
            entity.setTypeDocument(personDTO.getTypeDocument());
            entity.setNumberDocument(personDTO.getNumberDocument());
            entity.setDateBorn(personDTO.getDateBorn());
            entity.setDateModified(LocalDate.now());

            personRepository.save(entity);

            return personMapper.entityToDto(entity);
        } else {
            throw new RuntimeException("No se encuentra el ID a actualizar");
        }
    }

}
