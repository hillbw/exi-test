package nl.esi.metis.aisparser.impl;

import nl.esi.metis.aisparser.Sixbit;
import nl.esi.metis.aisparser.impl.AISMessage01Impl;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.*;	

public class AISMessage01Test {
	@Test
	public void MessageIdTest()
	{
		Sixbit sb = mock(Sixbit.class);	
		when(sb.getIntFromTo(1, 6)).thenReturn(1);
		when(sb.length()).thenReturn(168);			//pass length assertion

		AISMessage01Impl msg = new AISMessage01Impl(sb, null);
		assertEquals( 1, msg.getMessageID());
	}
}
