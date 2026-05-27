package com.dhanabal.repository;

import org.springframework.data.repository.CrudRepository;

import com.dhanabal.entity.TokenBlacklist;

public interface TokenBlacklistRepository extends CrudRepository<TokenBlacklist, Long> {

	boolean existsByToken(String token);
	
}