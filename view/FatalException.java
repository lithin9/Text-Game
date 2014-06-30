package view;

import java.awt.Toolkit;

/**
 * The exception handler of fatal exceptions which should result in the halting of execution of the program
 */
public class FatalException extends RuntimeException
{
	private static final long serialVersionUID = 5L;
	private final Throwable cause;
	public String message;

	public FatalException(String str, Throwable c)
	{
		message = str;
		cause = c;
		System.out.println(message);
		for(int i=0;i<3;i++)
		{
			try{
				
				//Main.text.changeColors(10);
			}
			catch (NullPointerException ne)
			{
				//Text thing isn't set. Do nothing
			}

			Toolkit.getDefaultToolkit().beep();
			try{Thread.sleep(500);}catch(Exception e){}
		}
	}

	public FatalException(Throwable c)
	{
		message = null;
		cause = c;
		for(int i=0;i<3;i++)
		{
			//Main.text.changeColors(10);
			Toolkit.getDefaultToolkit().beep();
			try{Thread.sleep(500);}catch(Exception e){}
		}
	}

	public FatalException(String string) {
		message = string;
		cause = null;
	}

	public String getMessage()
	{
		if(message != null)
			return message;
		else
			return cause.getMessage();
	}

	public Throwable getCause()
	{
		return cause;
	}
}