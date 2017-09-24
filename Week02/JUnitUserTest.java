package week02;

import static org.junit.Assert.*;

//import java.io.PrintStream;

import org.junit.Test;

import test.AbstractJUnitBase;
import week02.app.User;

public class JUnitUserTest extends AbstractJUnitBase
{
	/**
	 * Constructor
	 */
	public JUnitUserTest()
	{
		m_stream = System.out; // Standard out

		trace("########### Initializing JUnitUserTest for week02 ###########");
	}

	@Test
	public void testUserEqualsOverride()
	{
		// simple user creation
		User user = new User();						
		trace("Testing default constructor generates identical objects");
		
		User user2 = new User();						
		
		// expect to be same
		String msg = String.format("Users not equal\n%s\n%s", user, user2);
		assertTrue(msg, user.equals(user2));

		trace("Testing setter methods to change one object");
		user2.setFirstName("Jim");
		user2.setLastName("Bo");
		user2.setUserId(1);					
		
		msg = String.format("Users should not be equal\n%s\n%s", user, user2);
		assertFalse(msg, user.equals(user2));
		
		trace("Testing parameterized constructor with data from existing object");
		User user3 = new User(user2.getUserId(), user2.getFirstName(), user2.getLastName());		
		trace(user3.toString());
		
		msg = String.format("Users should equal\n%s\n%s", user, user2);
		assertTrue(msg, user3.equals(user2));
	}
}
