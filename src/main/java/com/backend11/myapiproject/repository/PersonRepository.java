package com.backend11.myapiproject.repository;


import com.backend11.myapiproject.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, String> {
}
