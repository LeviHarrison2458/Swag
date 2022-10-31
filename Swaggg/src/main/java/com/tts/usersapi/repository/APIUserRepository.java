package com.tts.usersapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.usersapi.models.APIUser;

@Repository
public interface APIUserRepository extends CrudRepository<APIUser, Long> {
	List<APIUser> findByState(String state);

	Optional<APIUser> findById(Long id);

	@Override
	List<APIUser> findAll();
}
