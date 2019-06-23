package com.dale.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.dale.test.cv.CVControllerTest;
import com.dale.test.cv.CVRepositoryTest;
 
@RunWith(Suite.class)
@SuiteClasses({
	CVControllerTest.class, 
	CVRepositoryTest.class})
public class TestSuite {

}
