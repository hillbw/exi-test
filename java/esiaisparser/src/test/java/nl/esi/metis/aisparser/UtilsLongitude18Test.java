package nl.esi.metis.aisparser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsLongitude18Test {
	private static final double EPSILON = 1e-10;
	@Test 
	public void toDegrees180()
	{
		int value = 180 * 60 * 10;
		double expect = 180.0;
		assertEquals(expect, UtilsLongitude18.toDegrees(value),EPSILON);
	}
	@Test 
	public void toDegrees0()
	{
		int value = 0;
		double expect = 0;
		assertEquals(expect, UtilsLongitude18.toDegrees(value),EPSILON);
	}
}