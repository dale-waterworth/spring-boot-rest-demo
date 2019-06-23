package com.dale.test.cv;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.dale.cv.CVService;
import com.dale.cv.dto.CV;
import com.dale.cv.dto.CompanyHistory;
import com.dale.cv.dto.Skill;

/**
 * Integration tests for the CV controller entry point.
 * 
 * Simply check all the end points return the correct data and handle missing 
 * data params - throwing a 400 error
 * 
 * The CVService will have more finely-grained tests for edge conditions
 * 
 * @author Dale
 *
 */

@Transactional
public class CVControllerTest extends AbstractTest {

	@Autowired
	private CVService cvService;

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void expectErrorForCreateEmptyCV() throws Exception {
		String uri = "/cv";
		CV cv = new CV();

		String inputJson = super.mapToJson(cv);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
	}

	@Test
	public void expectErrorForJustName() throws Exception {
		String uri = "/cv";
		CV cv = new CV();
		cv.setName("joe blogs");

		String inputJson = super.mapToJson(cv);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
	}

	@Test
	public void expectErrorForJustEmail() throws Exception {
		String uri = "/cv";
		CV cv = new CV();
		cv.setName("joe blogs");

		String inputJson = super.mapToJson(cv);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
	}

	@Test
	public void createMinimalCV() throws Exception {
		String uri = "/cv";
		CV cv = CVTestHelper.getBasicCV();

		String inputJson = super.mapToJson(cv);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();

		CV savedCV = mapFromJson(content, CV.class);
		assertEquals(savedCV.getName(), CVTestHelper.testName);
		assertEquals(savedCV.getEmail(), CVTestHelper.testEmail);
	}

	@Test
	public void createCVWithHistory() throws Exception {
		String uri = "/cv";
		CV cv = CVTestHelper.getBasicCV();
		List<CompanyHistory> companyHistory = CVTestHelper.getCompanyHistoryList();
		// companyHistory.get(0).setCvId(cv.getId());
		cv.setCompanyHistory(companyHistory);

		String inputJson = super.mapToJson(cv);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();

		CV savedCV = mapFromJson(content, CV.class);

		List<CompanyHistory> savedHistory = savedCV.getCompanyHistory();
		assertEquals(savedHistory.size(), 1);
		assertEquals(savedHistory.get(0).getName(), CVTestHelper.testCompanyName);
	}

	@Test
	public void createCVWithSkills() throws Exception {
		String uri = "/cv";
		CV cv = CVTestHelper.getBasicCV();
		List<Skill> skillList = CVTestHelper.getSkillList();
		cv.setSkills(skillList);

		String inputJson = super.mapToJson(cv);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();

		CV savedCV = mapFromJson(content, CV.class);

		List<Skill> savedSkillList = savedCV.getSkills();
		assertEquals(savedSkillList.size(), 1);
		assertEquals(savedSkillList.get(0).getName(), CVTestHelper.testSkillName);
	}

	
/**
 * This test needed its data pre-populating because we are running in a transaction, meaning if
 * the data was added first it would get rolled back in that transaction. Set the data
 * first then its possible to query and add to that
 * 
 * @throws Exception
 */
	@Sql(scripts = { "classpath:test/import.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	public void createCVAddNewCompanyHistory() throws Exception {
		CV savedCv = cvService.getCV(1);

		StringBuilder sb = new StringBuilder();
		sb.append("/cv/").append(savedCv.getId()).append("/companyHistory");
		String uri = sb.toString();

		String newHistoryName = "new company";
		LocalDateTime newDate = LocalDateTime.now();
		CompanyHistory newHistory = new CompanyHistory();
		newHistory.setName(newHistoryName);
		newHistory.setFromDate(newDate);
		newHistory.setToDate(newDate);
		newHistory.setCvId(savedCv.getId());

		String inputJson = super.mapToJson(newHistory);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		CV updatedCv = cvService.getCV(savedCv.getId());

		assertEquals(4, updatedCv.getCompanyHistory().size());
	}
	
	@Test
	public void CheckSkillNameChanges() throws Exception {
		CV savedCv = cvService.getCV(1);
		Skill existingSkill = savedCv.getSkills().get(0);
		assertEquals(existingSkill.getName(), "Java");

		String newSkillName = "Spring Boot";
		existingSkill.setName(newSkillName);
		
		StringBuilder sb = new StringBuilder();
		sb.append("/cv/").append(savedCv.getId()).append("/skill");
		String uri = sb.toString();

		String inputJson = super.mapToJson(existingSkill);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		CV updatedCv = cvService.getCV(savedCv.getId());

		assertEquals(1, updatedCv.getSkills().size());
		assertEquals(newSkillName, updatedCv.getSkills().get(0).getName());		
		
	}

	
	/**
	 * TODO - add further coverage
	 * 
	 * Could add more tests for updating individual items but would just be more of the same
	 */
}
