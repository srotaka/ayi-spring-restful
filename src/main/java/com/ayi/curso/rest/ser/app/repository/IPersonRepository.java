package com.ayi.curso.rest.ser.app.repository;

import com.ayi.curso.rest.ser.app.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPersonRepository extends JpaRepository<PersonEntity, Long> {

    // PersonEntity findByName(String name);
    //@Query("Select PE from PersonEntity where PE.firstName = :name")
    //List<PersonEntity> getPersonByName(@Param("name") String name);
}
