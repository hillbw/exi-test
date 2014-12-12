package nl.esi.metis.aisparser;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UtilsNavStatusTest {
	@Test
	public void Valid_0()
	{
		int value = 0;
		assertTrue( UtilsNavStatus.isNavStatusSemanticallyCorrect(value));
	}
	@Test
	public void Invalid_min1()
	{
		int value = -1;
		assertFalse( UtilsNavStatus.isNavStatusSemanticallyCorrect(value));
	}
	@Test
	public void Valid_8()
	{
		int value = 8;
		assertTrue( UtilsNavStatus.isNavStatusSemanticallyCorrect(value));
	}
	@Test
	public void Invalid_9()
	{
		int value = 9;
		assertFalse( UtilsNavStatus.isNavStatusSemanticallyCorrect(value));
	}
	@Test
	public void Invalid_13()
	{
		int value = 13;
		assertFalse( UtilsNavStatus.isNavStatusSemanticallyCorrect(value));
	}
	@Test
	public void Valid_14()
	{
		int value = 14;
		assertTrue( UtilsNavStatus.isNavStatusSemanticallyCorrect(value));
	}
	@Test
	public void Valid_15()
	{
		int value = 15;
		assertTrue( UtilsNavStatus.isNavStatusSemanticallyCorrect(value));
	}
	@Test
	public void Invalid_16()
	{
		int value = 16;
		assertFalse( UtilsNavStatus.isNavStatusSemanticallyCorrect(value));
	}
}