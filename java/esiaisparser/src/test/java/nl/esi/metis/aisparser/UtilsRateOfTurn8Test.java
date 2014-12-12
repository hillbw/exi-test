package nl.esi.metis.aisparser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UtilsRateOfTurn8Test {
	private static final double EPSILON = 1e-10;
	@Test
	public void TurnInformationAvailable()
	{
		int value = -127;
		assertTrue( UtilsRateOfTurn8.isTurnInformationAvailable(value));
	}
	@Test
	public void TurnInformationUnavailable()
	{
		int value = -128;
		assertFalse( UtilsRateOfTurn8.isTurnInformationAvailable(value));
	}
	@Test
	public void TurnIndicatorUnavailable()
	{
		int value = -127;
		assertFalse( UtilsRateOfTurn8.isTurnIndicatorAvailable(value));
	}
	@Test
	public void TurnIndicatorAvailable()
	{
		int value = -126;
		assertTrue( UtilsRateOfTurn8.isTurnInformationAvailable(value));
	}
	@Test
	public void DegreesPerMinute_Minus126()
	{
		int value = -126;
		double expected = - 126 * 126 / (4.733 * 4.733);
		assertEquals(expected, UtilsRateOfTurn8.toDegreesPerMinute(value), EPSILON);
	}
	@Test
	public void DegreesPerMinute_1()
	{
		int value = 1;
		double expected = 1 / (4.733 * 4.733);
		assertEquals(expected, UtilsRateOfTurn8.toDegreesPerMinute(value), EPSILON);
	}
	@Test
	public void toString_0()
	{
		int value = 0;
		assertEquals("not turning", UtilsRateOfTurn8.toString(value));
	}
	@Test
	public void toString_125()
	{
		int value = 125;
		assertEquals("turning right at 697.5045052094995 degrees per min", UtilsRateOfTurn8.toString(value));
	}
	@Test
	public void toString_minus125()
	{
		int value = -125;
		assertEquals("turning left at 697.5045052094995 degrees per min", UtilsRateOfTurn8.toString(value));
	}

}