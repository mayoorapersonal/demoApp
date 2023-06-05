package com.spring.qburst.demoApp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * References :
 * 	* https://howtodoinjava.com/junit5/junit-5-vs-junit-4/#:~:text=JUnit%204%20has%20everything%20bundled,that%20run%20on%20the%20platform.
 * @author mayoora
 *
 */

@SpringBootTest
class DemoAppApplicationTests {

	@Test
	void contextLoads() {
	}
	
	/**
	 * ==========
	 * Mockito ||
	 * ==========
	 * Mockito is a mocking framework for unit tests written in Java. It is an open source framework available at github. 
	 * You can use Mockito with JUnit to create and use mock objects during unit testing.
	 *When you use it, you will need to initialize the mocks with a call to MockitoAnnotations.initMocks(this).
	 *
	 * 1. Stubbing
	 * -----------
	 *	Stubbing means simulating the behavior of a mock object’s method.  For example, we can stub the getAvailableProducts() method of the ProductDao mock to return a specific value when the method is called.
	 *  
	 *  Eg: 
	 *  	@Test
			public void testBuy() throws InsufficientProductsException {
			    when(productDao.getAvailableProducts(product)).thenReturn(30);
			    assertEquals(30,productDao.getAvailableProducts(product));
			}
	 *  we are stubbing getAvailableProducts(product) of ProductDao to return 30. The when() method represents the trigger to start the stubbing and thenReturn() represents the action of the trigger
	 *   – which in the example code is to return the value 30.
	 *   
	 *  assertEquals(30,productDao.getAvailableProducts(product)); //we confirmed that the stubbing performed as expected.
	 *  
	 *  2. Verifying
	 *  ------------
	 *  We now want to verify the behavior of the buy() method of ProductService. First, we want to verify whether it’s calling the orderProduct() of ProductDao with the required set of parameters.
	 *  We also want to verify:

		2.1. Number of invocations done on a method:	The buy() method invokes getAvailableProduct() at least once.
		2.2 Sequence of Invocation:	The buy() method first invokes getAvailableProduct(), and then orderProduct().
		2.3 Exception verification:	The buy() method fails with InsufficientProductsException if order quantity passed to it is more than the available quantity returned by getAvailableProduct().
		2.4 Behavior during exception:	The buy() method doesn’t invokes orderProduct() when InsufficientProductsException is thrown.
		
		Eg:
			@Test
		    public void testBuy() throws InsufficientProductsException {
		        int availableQuantity = 30;
		        System.out.println("Stubbing getAvailableProducts(product) to return " + availableQuantity);
		        when(productDao.getAvailableProducts(product)).thenReturn(availableQuantity);
		        System.out.println("Calling ProductService.buy(product," + purchaseQuantity + ")");
		        productService.buy(product, purchaseQuantity);
		        System.out.println("Verifying ProductDao(product, " + purchaseQuantity + ") is called");
		        verify(productDao).orderProduct(product, purchaseQuantity); //verified that the orderProduct() method of the Dao mock get’s invoked with the expected set of parameters (that we passed to buy()).
		        System.out.println("Verifying getAvailableProducts(product) is called at least once");
		        verify(productDao, atLeastOnce()).getAvailableProducts(product);
		        System.out.println("Verifying order of method calls on ProductDao: First call getAvailableProducts() followed by orderProduct()");
		        InOrder order = inOrder(productDao); // used the inOrder() method to verify the order of method invocation that the buy() method makes on ProductDao.
		        order.verify(productDao).getAvailableProducts(product);
		        order.verify(productDao).orderProduct(product, purchaseQuantity);
		    }
		    
		    //test method to check whether an InsufficientProductsException gets thrown, as expected, when an order with quantity more than the available quantity is made.
		    @Test(expected = InsufficientProductsException.class)
		    public void purchaseWithInsufficientAvailableQuantity() throws InsufficientProductsException {
		        int availableQuantity = 3;
		        System.out.println("Stubbing getAvailableProducts(product) to return " + availableQuantity);
		        when(productDao.getAvailableProducts(product)).thenReturn(availableQuantity);
		        try {
		            System.out.println("productService.buy(product" + purchaseQuantity + ") should throw InsufficientProductsException");
		            productService.buy(product, purchaseQuantity);
		        } catch (InsufficientProductsException e) {
		            System.out.println("InsufficientProductsException has been thrown");
		            verify(productDao, times(0)).orderProduct(product, purchaseQuantity); //Exception gets thrown, the orderProduct() method is not invoked.
		            System.out.println("Verified orderProduct(product, " + purchaseQuantity + ") is not called");
		            throw e;
		        }
		    }
		    
		    
	 * =========
	 * Junit 5||
	 * =========
	 * 1.  Annotations
			
		1.1 @BeforeEach	->	 the annotated method will be executed before each test method. MUST NOT be a static method otherwise it will throw runtime error.
								Eg: 
								@BeforeEach
								public void initEach(){
								     //test setup code
								}
		1.2 @AfterEach	->	the annotated method will be executed after each test method. MUST NOT be a static method otherwise it will throw runtime error.
								Eg:
								@AfterEach
								public void cleanUpEach(){
									//Test cleanup code
								}
		1.3 @BeforeAll	->	the annotated method will be executed before all test methods in the current class. @BeforeAll annotated method must be called only once. 
								MUST be a static method in the test class.
								Eg: 
								@BeforeAll
								static void setup() {
								    log.info("@BeforeAll - executes once before all test methods in this class");
								}
		1.4 @AfterAll	->	 the annotated method will be executed after all test methods in the current class. 
								 MUST be a static method 
								must have a void return type 
								must not be private. 
								Eg:
								@AfterAll
								public static void cleanUp(){
									System.out.println("After All cleanUp() method called");
								}
		1.5 @Disabled	->	 used to exclude the test methods from the test suite. This annotation can be applied over a test class as well as over individual test methods.
								It accepts only one optional parameter, which indicates the reason this test is disabled.
								Eg:
																
									@Disabled("Do not run in lower environment")
									@Test
								    void testOnDev()
									{
										System.setProperty("ENV", "DEV");
								        Assumptions.assumeFalse("DEV".equals(System.getProperty("ENV")));
								    }
	
		1.6 @Tag	->		 can be used to filter testcases from test suites. 
		1.7 @DisplayName	->	defines a custom display name for a test class or a test method
		
	2. Assumption
			Assumptions are used to run tests only if certain conditions are met. This is typically used for external conditions that are required for the test to run properly,
			 but which aren't directly related to whatever is being tested.
	
		 assumeFalse() , assumingThat​(), assumeTrue()
		 Eg:
		 	@Test
			void trueAssumption() {
			    assumeTrue(5 > 1);
			    assertEquals(5 + 2, 7);
			}
			
			@Test
			void falseAssumption() {
			    assumeFalse(5 < 1);
			    assertEquals(5 + 2, 7);
			}
			
			@Test
			void assumptionThat() {
			    String someString = "Just a string";
			    assumingThat(
			        someString.equals("Just a string"),
			        () -> assertEquals(2 + 2, 4)
			    );
			}
	3. Assertion
			3.1 we can use the method assertThrows:

				@Test
				void assertThrowsException() {
				    String str = null;
				    assertThrows(IllegalArgumentException.class, () -> {
				      Integer.valueOf(str);
				    });
				}
			    
			    @Test
				void lambdaExpressions() {
				    List numbers = Arrays.asList(1, 2, 3);
				    assertTrue(numbers.stream().mapToInt(Integer::intValue).sum() > 5, () -> "Sum should be greater than 5");
				}
	   
	   3.2 the assertTimeout method in JUnit 5:
	   
	   		@Test
			void shouldFailBecauseTimeout() throws InterruptedException {
			    Assertions.assertTimeout(Duration.ofMillis(1), () -> Thread.sleep(10));
			}

	4. Test Suites
			 the concept of aggregating multiple test classes in a test suite, so that we can run those together. JUnit 5 provides two annotations, @SelectPackages and @SelectClasses, to create test suites.


	 */

}
