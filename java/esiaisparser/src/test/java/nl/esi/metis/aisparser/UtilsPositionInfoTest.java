package nl.esi.metis.aisparser;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UtilsPositionInfoTest {

	@Test
	public void utilsPositionInfo_unavailble()
	{
		int value = 181*60*10000;
		double actual = UtilsLongitude28.toDegrees(value);
		assertTrue(UtilsPositionInfo.isLongitudeSemanticallyCorrect(actual));
	}

	@Test
	public void utilsPositionInfo_max()
	{
		int value = 180*60*10000;
		double actual = UtilsLongitude28.toDegrees(value);
		assertTrue(UtilsPositionInfo.isLongitudeSemanticallyCorrect(actual));
	}
	@Test
	public void utilsPositionInfo_min()
	{
		int value = -180*60*10000;
		double actual = UtilsLongitude28.toDegrees(value);
		assertTrue(UtilsPositionInfo.isLongitudeSemanticallyCorrect(actual));
	}
	@Test
	public void utilsPositionInfo_wrong()
	{
		int value = 361*30*10000; //180.5*60*10000
		double actual = UtilsLongitude28.toDegrees(value);
		assertFalse(UtilsPositionInfo.isLongitudeSemanticallyCorrect(actual));
	}
	
	@Test
	public void utilsPositionInfo_correct()
	{
		int value = 359*30*10000; //179.5*60*10000
		double actual = UtilsLongitude28.toDegrees(value);
		assertTrue(UtilsPositionInfo.isLongitudeSemanticallyCorrect(actual));
	}
}