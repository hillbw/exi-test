package nl.esi.metis.aisparser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsLongitude28Test {
	private static final double EPSILON = 1e-10;
	@Test 
	public void toDegrees180()
	{
		int value = 180 * 60 * 10000;
		double expect = 180.0;
		assertEquals(expect, UtilsLongitude28.toDegrees(value),EPSILON);
	}
	
	@Test 
	public void toDegreesMin180()
	{
		int value = -180 * 60 * 10000;
		double expect = -180.0;
		assertEquals(expect, UtilsLongitude28.toDegrees(value),EPSILON);
	}
	
	@Test 
	public void toDegrees0()
	{
		int value = 0;
		double expect = 0;
		assertEquals(expect, UtilsLongitude28.toDegrees(value),EPSILON);
	}
	/**
	 * The minimal value in degrees one theoretically can receive based on 28 bits 2 complements 
	 * (it is outside the allowed range of [-180,180] + {181})
	 */
	@Test 
	public void toDegreesTheoreticalMinimum()
	{
		double expect = -223.6962133333333333;
		
		int value = -0x8000000; 
		assertEquals("precondition", expect, -Math.pow(2, 27) / (60* 10000), 0);
		assertEquals(expect, UtilsLongitude28.toDegrees(value),EPSILON);
	}
	/**
	 * The maximal value in degrees one theoretically can receive based on 28 bits 2 complements 
	 * (it is outside the allowed range of [-180,180] + {181})
	 */
	@Test 
	public void toDegreesTheoreticalMaximum()
	{
		double expect = 223.69621166666667;
		
		int value = 0x7FFFFFF;
		assertEquals("precondition", expect, (Math.pow(2, 27)-1) / (60*10000), 0);
		assertEquals(expect, UtilsLongitude28.toDegrees(value),EPSILON);
	}
}