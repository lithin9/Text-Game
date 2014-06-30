package view;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import view.WordUtils;

public class Out
{
	public PrintStream out;
	public PrintStream err;
	
	public Out()
	{
		out = new PrintStream(System.out)
		{
			public void println(String x)
			{
				super.println(
			//		new SimpleDateFormat("MM/dd kk:mm:ss ").format(new Date())
			//		+"\n"
					(WordUtils.wrap(x, 75))
				);
			}
			public void printlnColor(String x, String color)
			{
				if(color.equals("red"))
					color = "\\u001B31;1m";
				super.println(
						color
						+(WordUtils.wrap(x, 75))
						);
			}
			
		};
		
		err = new PrintStream(System.err)
		{
			public void println(String x)
			{
				super.println(
					new SimpleDateFormat("MM/dd kk:mm:ss ").format(new Date())
					+"\n"
					+(WordUtils.wrap(x, 75))
				);
			}
		};
	}
}
