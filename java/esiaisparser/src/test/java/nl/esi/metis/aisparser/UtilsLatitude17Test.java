package nl.esi.metis.aisparser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsLatitude17Test {
	private static final double EPSILON = 1e-10;
	@Test 
	public void toDegrees90()
	{
		int value = 90 * 60 * 10;
		double expect = 90.0;
		assertEquals(expect, UtilsLatitude17.toDegrees(value),EPSILON);
	}
	@Test 
	public void toDegrees0()
	{
		int value = 0;
		double expect = 0;
		assertEquals(expect, UtilsLatitude17.toDegrees(value),EPSILON);
	}
}