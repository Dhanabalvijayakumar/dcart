package com.dhanabal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dhanabal.entity.UserPreference;

public interface UserPreferenceRepository extends CrudRepository<UserPreference, Long> {

	@Query("select u from UserPreference u where u.user.userId = :userId and u.category.categoryId = :categoryId")
	Optional<UserPreference> findByUserIdAndCategoryId(Long userId, Long categoryId);

	@Query("select u from UserPreference u where u.user.userId = :userId order by u.score desc")
	List<UserPreference> findByUser_UserIdByOrderByScoreDesc(Long userId);

}
