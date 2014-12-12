package nl.esi.metis.aisparser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** These tests prove that we can generalize Latitude27 and Latitude17 to a single Latitude with a single check function for unavailable. 
 * @author Pierre van de Laar
 */
public class UtilsLatitudeTest {
	@Test
	public void unavailable27_91()
	{
		int value = 91*60*10000;
		assertEquals(0x3412140, value);
		double actual = UtilsLatitude27.toDegrees(value);
		assertEquals(91, actual,0); 
	}

	@Test
	public void unavailable17_91()
	{
		int value = 91*60*10;
		double actual = UtilsLatitude17.toDegrees(value);
		assertEquals(91, actual,0); 
	}

}