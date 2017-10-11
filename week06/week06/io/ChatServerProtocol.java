package week06.io;

import week06.util.*;

import java.util.Hashtable;

public class ChatServerProtocol
{
	//public String nick;
	//public ClientConn conn;

	public ChatServerProtocol(ClientConn c)
	{
		m_account = null;
		m_conn = c;
	}

	
	public String getAccount()
	{
		return m_account;
	}
	
	public void setAccount(String account)
	{
		m_account = account;
	}
	
	public String getPin()
	{
		return m_pin;
	}
	
	public void setPin(String pin)
	{
		m_pin = pin;
	}
	
	public ClientConn getClientConnection()
	{
		return m_conn;
	}
	
	public void setClientConnection(ClientConn conn)
	{
		m_conn = conn;
	}


	private void log(String msg)
	{
		System.err.println(msg);
	}

	public boolean isAuthenticated()
	{
		return !(getAccount() == null);
	}
	
	/**
	 * Implements the authentication protocol. This consists of checking that
	 * the message starts with the NICK command and that the nick following it
	 * is not already in use. returns: msg_OK if authenticated msg_NICK_IN_USE
	 * if the specified nick is already in use msg_SPECIFY_NICK if the message
	 * does not start with the NICK command
	 */
	private String authenticate(String msg)
	{
		if(msg.startsWith("LOGIN"))
		{
			String tryAccount = msg.substring(6);
			if(addAccount(tryAccount, m_conn))
			{
				log("Account: " + tryAccount + " joined.");
				makeLoginRequest(tryAccount);
				//this.data.nick = tryNick;
				setAccount(tryAccount);
				return msg_OK;
			}
			else
			{
				return msg_ACCOUNT_IN_USE;
			}
		}
		if(msg.startsWith("PIN"))
		{
			String tryPin = msg.substring(4);
			if(tryPin.length() == 4)
			{
				log("Pin: " + tryPin + " accepted");
				//makeLoginRequest(tryPin);
				//this.data.nick = tryNick;
				setPin(tryPin);
				return msg_OK;
			}
			else
			{
				return "Pin not valid";
			}
		}
		else
		{
			return "Specify Pin Account";
		}
	}
	
	private LoginRequest makeLoginRequest(String message){
		
		log(message);
		
		
		LoginRequest loginRequest = new LoginRequest(1234, 1_000_000);
		
		return loginRequest;
	}
	
	/**
	 * Send a message to another user.
	 * 
	 * @recipient contains the recepient's nick
	 * @msg contains the message to send return true if the nick is registered
	 *      in the hash, false otherwise
	 */
	private boolean sendMsg(String recipient, String msg)
	{
		if(accounts.containsKey(recipient))
		{
			ClientConn c = accounts.get(recipient);
			String response = getAccount() + ": " + msg;
			log("Server sending: " + response);
			c.sendMsg(response);
			return true;
		}
		else
		{
			log("returning false from sendMsg; no account");
			return false;
		}
	}

	/**
	 * Process a message coming from the client
	 */
	public String process(String msg)
	{
		if(!isAuthenticated())
		{
			return authenticate(msg);
		}

		log("Server: processing");
		String[] msg_parts = msg.split(" ", 3);
		String msg_type = msg_parts[0];

		if(msg_type.equals("MSG"))
		{
			if(msg_parts.length < 3)
			{
				log("Server: Invalid message - incorrect length");
				return msg_INVALID;
			}
			if(sendMsg(msg_parts[1], msg_parts[2]))
			{
				return msg_OK;
			}
			else
			{
				return msg_SEND_FAILED;
			}
		}
		else
		{
			log("Invalid message: " + msg);
			return msg_INVALID;
		}
	}
	
	/**
	 * Adds a nick to the hash table returns false if the nick is already in the
	 * table, true otherwise
	 */
	private static boolean addAccount(String account, ClientConn c)
	{
		if(accounts.containsKey(account))
		{
			return false;
		}
		else
		{
			accounts.put(account, c);
			return true;
		}
	}
	
	private String m_account;
	private String m_pin;
	private ClientConn m_conn;
	//private ChatServerProtocol data = new ChatServerProtocol();
	/* a hash table from user nicks to the corresponding connections */
	private static Hashtable<String, ClientConn> accounts = new Hashtable<String, ClientConn>();

	private static final String msg_OK = "OK";
	private static final String msg_ACCOUNT_IN_USE = "ACCOUNT IN USE";
	private static final String msg_SPECIFY_ACCOUNT = "SPECIFY ACCOUNT";
	private static final String msg_INVALID = "INVALID COMMAND";
	private static final String msg_SEND_FAILED = "FAILED TO SEND";

}