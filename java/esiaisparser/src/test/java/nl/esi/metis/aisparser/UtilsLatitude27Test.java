package nl.esi.metis.aisparser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsLatitude27Test {
	private static final double EPSILON = 1e-10;
	@Test 
	public void toDegrees90()
	{
		int value = 90 * 60 * 10000;
		double expect = 90.0;
		assertEquals(expect, UtilsLatitude27.toDegrees(value),EPSILON);
	}
	@Test 
	public void toDegrees0()
	{
		int value = 0;
		double expect = 0;
		assertEquals(expect, UtilsLatitude27.toDegrees(value),EPSILON);
	}
	/**
	 * The minimal value in degrees one theoretically can receive based on 27 bits 2 complements 
	 * (it is outside the allowed range of [-90,90] + {91})
	 */
	@Test 
	public void toDegreesTheoreticalMinimum()
	{
		double expect = - 111.8481066666666666667;
		
		int value = -0x4000000;
		assertEquals("precondition", expect, -Math.pow(2, 26) / (60*10000),0);
		assertEquals(expect, UtilsLatitude27.toDegrees(value),EPSILON);
	}
	/**
	 * The maximal value in degrees one theoretically can receive based on 27 bits 2 complements 
	 * (it is outside the allowed range of [-90,90] + {91})
	 */
	@Test 
	public void toDegreesTheoreticalMaximum()
	{
		double expect = 111.848105;
		
		int value = 0x3FFFFFF;
		assertEquals("precondition", expect, (Math.pow(2, 26)-1) / (60*10000),0);
		assertEquals(expect, UtilsLatitude27.toDegrees(value),EPSILON);
	}
}
