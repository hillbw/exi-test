package nl.esi.metis.aisparser;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UtilsUserID30Test {
	@Test
	public void origin_Europe()
	{
		long mmsi = 235083314;
		assertEquals( "Europe", UtilsUserId30.getOrigin(mmsi));
	}
	@Test
	public void origin_IllegalMMSI()
	{
		long mmsi = 1000000000;	//more than 9 digits
		assertEquals( "Invalid MMSI number", UtilsUserId30.getOrigin(mmsi));
	}

}
