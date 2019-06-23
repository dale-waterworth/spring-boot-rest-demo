package com.dale.cv;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dale.cv.dto.CV;
import com.dale.cv.dto.CompanyHistory;
import com.dale.cv.dto.Skill;

/**
 * 
 * @author Dezza2_0
 *
 */

@RestController
@RequestMapping(path = "/cv")
public class CVController {
	
	@Autowired
	private CVService cvService;

	@PostMapping("")
	public @ResponseBody CV saveCV(@Valid @RequestBody CV cv) {
		return cvService.save(cv);
	}
	
	@GetMapping(path = "{id}")
	public @ResponseBody CV getAllCV(@PathVariable int id) {
		return cvService.getCV(id);
	}

	@GetMapping(path = "")
	public @ResponseBody List<CV> getAllCVs() {
		return cvService.getAll();
	}

	@PutMapping(path = "companyHistory")
	public void updateHistory(@RequestBody List<CompanyHistory> companyHistory) {
		cvService.updateCompanyHistory(companyHistory);
	}

	@PostMapping(path = "{id}/companyHistory")
	public void addHistoryItem(@PathVariable int id, @RequestBody CompanyHistory companyHistory) {
		cvService.addCVHistory(id, companyHistory);
	}
	
	@PostMapping(path = "{id}/skill")
	public void addSkillItem(@PathVariable int id, @RequestBody Skill skill) {
		cvService.addCVSkill(id, skill);
	}
}
