package nl.esi.metis.aisparser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsShipType8Test {
	@Test
	public void Text_2xx()
	{
		int id = 201;
		assertEquals("Reserved for future use", UtilsShipType8.shipTypeToString(id));
	}
	@Test
	public void Text_1xx()
	{
		int id = 101;
		assertEquals("Reserved for regional use", UtilsShipType8.shipTypeToString(id));
	}
	@Test
	public void Text_000()
	{
		int id = 0;
		assertEquals("Not available / no ship", UtilsShipType8.shipTypeToString(id));
	}
	@Test
	public void Text_00x()
	{
		int id = 8;
		assertEquals("Not specified " + id, UtilsShipType8.shipTypeToString(id));
	}
}
