package view;

import java.awt.Toolkit;

/**
 * The exception handler of non-fatal exceptions which should result in some defined clean-up logic
 */
public class CustomException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	private final Throwable cause;
	public String message;

	public CustomException(String str)
	{
		message = str;
		cause = null;
		try
		{
			//Main.text.changeColors(5);	//throws null pointer when GUI is disabled
		}
		catch(NullPointerException npe)
		{
			//Do nothing
		}
		Toolkit.getDefaultToolkit().beep();
	}

	public CustomException(String str, Throwable c)
	{
		message = str;
		cause = c;
		try
		{
			//Main.text.changeColors(5);	//throws null pointer when GUI is disabled
		}
		catch(NullPointerException npe)
		{
			//Do nothing
		}
		Toolkit.getDefaultToolkit().beep();
	}

	public String getMessage()
	{
		return message;
	}

	public Throwable getCause()
	{
		return cause;
	}

	public boolean hasCause()
	{
		return cause!=null;
	}
}