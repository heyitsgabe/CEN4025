package week02;

import test.AbstractTestHarness;


/**
 * File: TestHarness.java
 */
class TestHarness extends AbstractTestHarness
{
	public static void main(String[] args)
	{
		new TestHarness().runTests();

	}
	
	/**
	 * Implements the base class abstract method
	 */
	protected void runTests()
	{
		try
		{
			boolean javadocTest = executeTest(JUnitJavadocValidation.class);
			boolean userTest = executeTest(JUnitUserTest.class);
			boolean accountTest = executeTest(JUnitAccountTest.class);
			boolean result = javadocTest && userTest && accountTest;

			trace(result ? "PASSED" : "FAILED");
		}
		catch(Exception ex)
		{
			trace(ex.getMessage());
		}
	}
}
