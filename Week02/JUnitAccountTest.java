package week02;

import static org.junit.Assert.*;

import org.junit.Test;
import test.AbstractJUnitBase;
import week02.app.Account;
import week02.app.User;

public class JUnitAccountTest extends AbstractJUnitBase
{

	@Test
	public void testAccountEqualsOverride()
	{
		// simple user creation
		Account account1 = new Account();						
		trace("Testing default constructor generates identical objects");
		
		Account account2 = new Account();						
		
		// expect to be same
		String msg = String.format("Accounts not equal\n%s\n%s", account1, account2);
		assertTrue(msg, account1.equals(account2));

		trace("Testing setter methods to change one object");
		User newUser = new User(1, "Jim","Bo");
		account2.setName("Test Acouunt");
		account2.setUser(newUser);					
		
		msg = String.format("Accounts should not be equal\n%s\n%s", account1, account2);
		assertFalse(msg, account1.equals(account2));
		
		trace("Testing parameterized constructor with data from existing object");
		Account account3 = new Account(2, newUser, "Test Account 2", 1000.0);	
		String toString = account3.toString();
		trace("Testing toString for new account: " + account3.toString());
		boolean invalidFormat = toString.contains("Account@");
		trace("toString test " + invalidFormat);
		assertFalse("Account doesn't properly override toString\n" + toString, invalidFormat);
	}

}
