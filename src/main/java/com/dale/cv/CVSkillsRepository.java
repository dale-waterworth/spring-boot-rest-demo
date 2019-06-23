package com.dale.cv;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dale.cv.dto.Skill;

public interface CVSkillsRepository extends CrudRepository<Skill, Integer> {
	List<Skill> findSkillBycvId(Integer cvId);
}
