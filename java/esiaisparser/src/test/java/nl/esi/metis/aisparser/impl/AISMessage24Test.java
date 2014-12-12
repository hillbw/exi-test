package nl.esi.metis.aisparser.impl;

import nl.esi.metis.aisparser.AISMessage24;
import nl.esi.metis.aisparser.Sixbit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.*;	

public class AISMessage24Test {
	@Test
	public void MessageIdPartATest()
	{
		Sixbit sb = mock(Sixbit.class);	

		when(sb.length()).thenReturn(160);			//pass length assertion
		when(sb.getIntFromTo(1, 6)).thenReturn(24);
		when(sb.getIntFromTo(39, 40)).thenReturn(0);
		
		when(sb.getStringFromTo(anyInt(),anyInt())).thenReturn("name");
		
		AISMessage24 msg = new AISMessage24PartAImpl(sb, null);
		assertEquals( 24, msg.getMessageID());
	}
	
	@Test
	public void MessageIdPartBTest()
	{
		Sixbit sb = mock(Sixbit.class);	
		when(sb.getStringFromTo(anyInt(),anyInt())).thenReturn("name");
		
		when(sb.getIntFromTo(1, 6)).thenReturn(24);
		when(sb.getIntFromTo(39, 40)).thenReturn(1);
		
		
		when(sb.length()).thenReturn(168);			//pass length assertion
		
		AISMessage24 msg = new AISMessage24PartBImpl(sb, null);
		assertEquals( 24, msg.getMessageID());
	}
}