package com.dale.test.cv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import com.dale.cv.CVService;
import com.dale.cv.dto.CV;

/**
 * Unit tests for the CV Service.
 * 
 * Add more fine grained conditions like max length, params existing etc.
 * 
 * @author Dale
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CVRepositoryTest {

	@Autowired
	private CVService cvService;

	@Test(expected = ConstraintViolationException.class)
	public void testEmptyCV() {
		CV cv = new CV();
		cvService.save(cv);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testErrorNameOnly() {
		CV cv = new CV();
		cv.setName("some name");

		cvService.save(cv);

		fail("Email is required");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testErrorEmailOnly() {
		CV cv = new CV();
		cv.setEmail("some name");

		cvService.save(cv);

		fail("Name is required");
	}

	@Test
	public void testSaveCV() {
		CV cv = new CV();
		cv.setEmail("email");
		cv.setName("name");

		CV cvSaved = cvService.save(cv);

		CV dbCv = cvService.getCV(cvSaved.getId());

		assertEquals(dbCv.getName(), cvSaved.getName());
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testErrorForLongNme() {
		CV cv = new CV();
		cv.setEmail("email");
		cv.setName(StringUtils.repeat("*", 151));

		CV cvSaved = cvService.save(cv);

		fail("Should have throw error as string is too big");
	}
}
