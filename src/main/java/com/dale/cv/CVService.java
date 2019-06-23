package com.dale.cv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dale.cv.dto.CV;
import com.dale.cv.dto.CompanyHistory;
import com.dale.cv.dto.Skill;

/**
 * This class acts like the business layer for getting data linked to the CV
 * 
 * @author Dale
 * 
 */

@Service
public class CVService {

	@Autowired
	private CVRepository cvRepository;

	@Autowired
	private CVCompanyHistoryRepository historyRepository;

	@Autowired
	private CVSkillsRepository skillRepository;

	public CV save(CV cv) {
		CV savedCv = (CV) cvRepository.save(cv);
		saveSkills(savedCv.getId(), cv.getSkills());
		return getCV(savedCv.getId());
	}

	public CV getCV(int id) {
		CV cvFromDb = cvRepository.findById(id).orElse(null);
		return processCV(cvFromDb);
	}

	private CV processCV(CV cv) {
		cv.setSkills(getSkills(cv.getId()));
		return cv;
	}

	private List<Skill> getSkills(Integer cvId) {
		List<Skill> skills = skillRepository.findSkillBycvId(cvId);

		/**
		 * Pretend we need to do other processing with the skills eg. get a list of jobs
		 * that match the skill list.
		 * 
		 * Or we could just add the mapping like what was done for companyHistory in the
		 * CV DTO
		 * 
		 **/
		return skills;
	}

	public void saveSkills(Integer cvId, List<Skill> skills) {
		if(skills == null) {
			return;
		}
		for (Skill skill : skills) {
			addCVSkill(cvId, skill);
		}
	}

	public void addCVSkill(int cvId, Skill skill) {
		skill.setCvId(cvId);
		skillRepository.save(skill);
	}

	public List<CV> getAll() {
		return (List<CV>) cvRepository.findAll();
	}

	public void updateCompanyHistory(List<CompanyHistory> companyHistory) {
		historyRepository.saveAll(companyHistory);
	}

	public void addCVHistory(int cvId, CompanyHistory companyHistory) {
		companyHistory.setCvId(cvId);
		historyRepository.save(companyHistory);
	}

}
