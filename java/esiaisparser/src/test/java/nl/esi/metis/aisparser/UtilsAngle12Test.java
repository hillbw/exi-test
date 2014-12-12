package nl.esi.metis.aisparser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UtilsAngle12Test {
	private static final double EPSILON = 1e-10;
	@Test
	public void semanticallyCorrect_Default()
	{
		int value = 3600;
		assertTrue( UtilsAngle12.isAngleSemanticallyCorrect(value));
	}
	@Test
	public void semanticallyCorrect_0()
	{
		int value = 0;
		assertTrue( UtilsAngle12.isAngleSemanticallyCorrect(value));
	}
	@Test
	public void semanticallyCorrect_3599()
	{
		int value = 3599;
		assertTrue( UtilsAngle12.isAngleSemanticallyCorrect(value));
	}
	@Test
	public void semanticallyCorrect_1234()
	{
		int value = 1234;
		assertTrue( UtilsAngle12.isAngleSemanticallyCorrect(value));
	}
	@Test
	public void semanticallyIncorrect_4095()
	{
		int value = 4095;
		assertFalse( UtilsAngle12.isAngleSemanticallyCorrect(value));
	}
	@Test
	public void semanticallyIncorrect_3601()
	{
		int value = 3601;
		assertFalse( UtilsAngle12.isAngleSemanticallyCorrect(value));
	}
	@Test
	public void Available()
	{
		int value = 1234;
		assertTrue( UtilsAngle12.isAvailable(value));
	}
	@Test
	public void Unavailable()
	{
		int value = 3600;
		assertFalse( UtilsAngle12.isAvailable(value));
	}
	
	@Test 
	public void toDegrees_3599()
	{
		int value = 3599;
		double expect = value/10.0;
		assertEquals(expect, UtilsAngle12.toDegrees(value),EPSILON);
	}
	@Test 
	public void toDegrees_0()
	{
		int value = 0;
		double expect = 0;
		assertEquals(expect, UtilsAngle12.toDegrees(value),EPSILON);
	}
}
