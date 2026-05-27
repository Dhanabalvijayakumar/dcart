package com.dhanabal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dhanabal.entity.Users;

public interface UsersRepository extends CrudRepository<Users, Long> {

	@Query("select u from Users u where u.email = :email")
	Optional<Users> findUserByEmail(@Param("email") String email);
	
	@Query("select u from Users u where u.mobile = :mobile")
	Optional<Users> findUserByMobile(@Param("mobile") String mobile);
	
	@Query("select u from Users u where u.mobile = :mobile and u.password = :password")
	Optional<Users> findUserByMobileAndPassword(@Param("mobile") String mobile, @Param("password") String password);
	
}
