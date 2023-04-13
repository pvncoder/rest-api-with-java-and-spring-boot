/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.pedro.model.repository;

import br.com.pedro.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Modifying
	@Query("UPDATE Person p SET p.enabled = FALSE WHERE p.id = (:id)")
	void disablePerson(@Param("id") Long id);
}
