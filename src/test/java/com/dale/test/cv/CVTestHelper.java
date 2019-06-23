package com.dale.test.cv;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.dale.cv.dto.CV;
import com.dale.cv.dto.CompanyHistory;
import com.dale.cv.dto.Skill;

/**
 * Utility class for building up the test data and params.
 * 
 * NOTE: Test data would ideally be from a file on web service call to suit environments / db's
 * 
 * @author Dale
 *
 */

public class CVTestHelper {

	public static final LocalDateTime fixedDateFrom = LocalDateTime.of(2016, 6, 23, 13, 0);
	public static final LocalDateTime fixedDateTo = LocalDateTime.of(2019, 8, 5, 13, 0);
	public static final String testName = "Joe Bloggs";
	public static final String testEmail = "joe.bloggs@jeans.com";
	public static final String testCompanyName = "Company";
	public static final String testSkillName = "Java";

	public static CV getBasicCV() {
		CV cv = new CV();
		cv.setName(testName);
		cv.setEmail(testEmail);
		return cv;
	}

	public static CompanyHistory getCompanyHistory() {
		CompanyHistory history = new CompanyHistory();
		history.setName(testCompanyName);
		history.setFromDate(fixedDateFrom);
		history.setToDate(fixedDateTo);
		return history;
	}

	public static List<CompanyHistory> getCompanyHistoryList() {
		return Arrays.asList(getCompanyHistory());
	}

	public static Skill getSkill() {
		Skill skill = new Skill();
		skill.setName(testSkillName);
		return skill;
	}

	public static List<Skill> getSkillList() {
		return Arrays.asList(getSkill());
	}
}
