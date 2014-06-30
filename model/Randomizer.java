package model;

import java.text.DecimalFormat;

public class Randomizer
{
	public static double randomizer(double max)
	{
		double random = Math.random();
		DecimalFormat df = new DecimalFormat("#.##");
		
		return Double.valueOf(df.format(max*random));
	}
	
	public static double randomizer(double max, double modifier)
	{
		double random = Math.random();

		DecimalFormat df = new DecimalFormat("#.##");
		return Double.valueOf(df.format((max*random)+modifier));
	}
	
	public static double[] randomizer(double max, int rolls)
	{
		double random;
		
		DecimalFormat df = new DecimalFormat("#.##");
		double[] results = new double[rolls];
		for(int i=0; i<rolls; i++)
		{
			random = Math.random();
			results[i] = Double.valueOf(df.format(max*random));
		}
		return results;
	}
	
	public static double[] randomizer(double max, int rolls, double modifier)
	{
		double random;
		
		double[] results = new double[rolls];
		for(int i=0; i<rolls; i++)
		{
			random = Math.random();

			DecimalFormat df = new DecimalFormat("#.##");
			results[i] = Double.valueOf(df.format((max*random)+modifier));
		}
		return results;
	}
}
