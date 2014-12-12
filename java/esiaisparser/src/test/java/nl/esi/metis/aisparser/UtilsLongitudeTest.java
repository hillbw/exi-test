package nl.esi.metis.aisparser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** These tests prove that we can generalize Longitude28 and Longitude18 to a single Longitude with a single check function for unavailable. 
 * @author Pierre van de Laar
 */
public class UtilsLongitudeTest {
	@Test
	public void unavailable28_181()
	{
		int value = 181*60*10000;
		assertEquals(0x6791AC0, value);
		double actual = UtilsLongitude28.toDegrees(value);
		assertEquals(181, actual,0); 
	}

	@Test
	public void unavailable18_181()
	{
		int value = 181*60*10;
		double actual = UtilsLongitude18.toDegrees(value);
		assertEquals(181, actual,0); 
	}
}