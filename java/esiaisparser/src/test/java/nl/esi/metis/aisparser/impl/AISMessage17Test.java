package nl.esi.metis.aisparser.impl;

import nl.esi.metis.aisparser.Sixbit;
import nl.esi.metis.aisparser.impl.AISMessage17Impl;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.*;	

public class AISMessage17Test {
	@Test
	public void MessageIdTest()
	{
		Sixbit sb = mock(Sixbit.class);	
		when(sb.getIntFromTo(1, 6)).thenReturn(17);
		when(sb.length()).thenReturn(80);			//pass length assertion

		AISMessage17Impl msg = new AISMessage17Impl(sb, null);
		assertEquals( 17, msg.getMessageID());
	}
	@Test
	public void ValidLength_80()
	{
		int length = 80;
		assertTrue( AISMessage17Impl.validLength(length));
	}
	@Test
	public void ValidLength_144()
	{
		int length = 144;
		assertTrue( AISMessage17Impl.validLength(length));
	}
	@Test
	public void ValidLength_168()
	{
		int length = 168;
		assertTrue( AISMessage17Impl.validLength(length));
	}
	@Test
	public void ValidLength_816()
	{
		int length = 816;
		assertTrue( AISMessage17Impl.validLength(length));
	}
	@Test
	public void InvalidLength_120()
	{
		int length = 120;
		assertFalse( AISMessage17Impl.validLength(length));
	}
	@Test
	public void InvalidLength_96()
	{
		int length = 96;
		assertFalse( AISMessage17Impl.validLength(length));
	}
	@Test
	public void InvalidLength_167()
	{
		int length = 167;
		assertFalse( AISMessage17Impl.validLength(length));
	}
}