package com.spring.qburst.demoApp;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * https://www.baeldung.com/junit-testinstance-annotation
 * @author mayoora
 *
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Math util test class")
class MathUtilTest {
	
	MathUtil mathUtil;
	
	//Instance of mathutil is created each time before a test method is called in this class.
	//For creating once use @testinstance above the class.
	@BeforeEach
	void init() {
		mathUtil = new MathUtil();
	}
	
	@BeforeAll
	static void beforeAllinit() {
		System.out.println("Hi mayooorraaaaaaaa !!!!!");
	}
	
	@AfterEach
	void Cleanup() {
		System.out.println("Clean up");
	}
	
	@Disabled
	@Test
	@DisplayName("Test sample method.")
	void testSampleMethod() {
		System.out.println("I am sample test.");
	}
	
	//If one of the test case in the nested class fails, then the test group also fails, else success.
	@Nested
	@DisplayName("Group for test addition")
	class testAdd {
		
		@Test
		void testAddPositive() {
			
			int expectedValue = 2;
			int actualValue = mathUtil.add(1, 1);
			assertEquals(expectedValue, actualValue);
		}
		
		@Test
		void testAddNegative() {
			// for lazy alerting the message we can use lambda so that it used only when the it is called.
			assertEquals(-2, mathUtil.add(-1, -1), () ->"Should return positive number");
		}
	}
	
	@Test
	@Tag("Divide")
	void testDivide() {
		
		int a= 2;
		int b= 2;
		assumeTrue(b >0);
		assertThrows(ArithmeticException.class, () -> mathUtil.divide(0,0), "Division by zero error.");
	}

	@RepeatedTest(3)
	void testCircleArea(RepetitionInfo repetitionInfo) {
		repetitionInfo.getCurrentRepetition();
		repetitionInfo.getTotalRepetitions();
		assertEquals(314.1592653589793, mathUtil.computeCircleArea(10),"Should return a positive value for area.");
	}
	
	@Test
	 void groupAssertions() {
	     int[] numbers = {0, 1, 2, 3, 4};
	     assertAll("numbers",
	         () -> assertEquals(numbers[0], 1),
	         () -> assertEquals(numbers[3], 3),
	         () -> assertEquals(numbers[4], 1)
	     );
	 }
	
}
