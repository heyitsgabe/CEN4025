package week02;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import test.TestResult;
import test.TestResultDetail;
import test.javadoc.ConstructorTestData;
import test.javadoc.FileTestData;
import test.javadoc.FileTestData2;
import test.javadoc.JUnitJavadocValidationUtility;
import test.javadoc.JUnitJavadocValidationUtility2;
import test.javadoc.MethodTestData;
import test.javadoc.MethodTestData2;

/**
 * Tests the Javadoc in the source file.
 * 
 * @author Scott
 *
 */
public class JUnitJavadocValidation
{
	public JUnitJavadocValidation()
	{
		m_stream = System.out; // Standard out
		
		trace("########### Initializing JUnitJavadocValidation for week02 ###########");
		
		List<FileTestData2> testFiles = new ArrayList<FileTestData2>();
		List<MethodTestData2> methods = new ArrayList<MethodTestData2>();
		List<ConstructorTestData> constructors = new ArrayList<ConstructorTestData>();
				
		constructors.add(new ConstructorTestData("User", 0, "public"));
		constructors.add(new ConstructorTestData("User",3, "public"));
		
		methods.add(new MethodTestData2("equals", "Object", "boolean","public"));
		methods.add(new MethodTestData2("getFirstName", "", "String","public"));
		methods.add(new MethodTestData2("getLastName", "", "String","public"));
		methods.add(new MethodTestData2("getUserId", "", "long","public"));
		methods.add(new MethodTestData2("setFirstName", "String", "void","public"));
		methods.add(new MethodTestData2("setLastName", "String", "void","public"));
		methods.add(new MethodTestData2("setUserId", "long", "void","public"));
		methods.add(new MethodTestData2("toString", "", "String","public"));
		
		testFiles.add(new FileTestData2("week02.app", "User.java", methods,constructors));
		

		List<MethodTestData2> accountMethods = new ArrayList<MethodTestData2>();
		List<ConstructorTestData> accountConstructors = new ArrayList<ConstructorTestData>();
		accountConstructors.add(new ConstructorTestData("Account", 0, "public"));
		accountConstructors.add(new ConstructorTestData("Account",4, "public"));
		
		accountMethods.add(new MethodTestData2("equals", "Object", "boolean","public"));
		accountMethods.add(new MethodTestData2("getAccountId", "", "long","public"));
		accountMethods.add(new MethodTestData2("getBalance", "", "double","public"));
		accountMethods.add(new MethodTestData2("getName", "", "String","public"));
		accountMethods.add(new MethodTestData2("getUser", "", "User","public"));
		accountMethods.add(new MethodTestData2("getUserId", "", "long","public"));
		accountMethods.add(new MethodTestData2("setName", "String", "void","public"));
		accountMethods.add(new MethodTestData2("setUser", "User", "void","public"));
		accountMethods.add(new MethodTestData2("toString", "", "String","public"));
		
		testFiles.add(new FileTestData2("week02.app", "Account.java", accountMethods,accountConstructors));
		
		m_validator = new JUnitJavadocValidationUtility2("Week02 Javadoc Test",
				testFiles);
		
		m_validator.suppressParserTrace(true);
	}

	@Test
	public void testJavadoc()
	{
		trace("** Validating Javadoc **");

		// Update these values for each assignment
		// m_packageName = "week02";
		TestResult result = m_validator.runTest();
		StringBuilder details = new StringBuilder();
		if(!result.passed())
		{			
			List<TestResultDetail> detailList = result.getTestResultDetails();
			for(TestResultDetail detail : detailList)
			{
				trace(detail.testDetails());
				details.append(detail.testDetails());
				details.append(CRLF);
			}
		}
		trace(String.format("** Test result: %s **", result.passed() ? "Passed" : "Failed"));
		assertTrue(details.toString(), result.passed());
	}
	
	/**
	 * Trace the msg to a PrintStream Provides the method for tests to report
	 * status
	 * 
	 * @param msg
	 */
	private void trace(String msg)
	{
		m_stream.println(msg);
	}
	
	private JUnitJavadocValidationUtility2 m_validator;
	protected PrintStream m_stream;
	private static String CRLF = System.getProperty("line.separator");
}
