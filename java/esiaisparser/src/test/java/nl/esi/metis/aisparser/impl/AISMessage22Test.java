package nl.esi.metis.aisparser.impl;

import nl.esi.metis.aisparser.AISMessage22;
import nl.esi.metis.aisparser.DestinationArea;
import nl.esi.metis.aisparser.DestinationIDs;
import nl.esi.metis.aisparser.Sixbit;
import nl.esi.metis.aisparser.impl.AISMessage22Impl;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.*;	

public class AISMessage22Test {
	@Test
	public void MessageIdTest()
	{
		Sixbit sb = mock(Sixbit.class);	
		when(sb.getIntFromTo(1, 6)).thenReturn(22);
		when(sb.length()).thenReturn(168);			//pass length assertion
		
		AISMessage22Impl msg = new AISMessage22Impl(sb, null);
		assertEquals( 22, msg.getMessageID());
	}
	@Test
	public void DestinationArea()
	{
		Sixbit sb = mock(Sixbit.class);	
		when(sb.getBoolean(anyInt())).thenReturn(false);
		when(sb.getIntFromTo(1, 6)).thenReturn(22);
		when(sb.length()).thenReturn(168);			//pass length assertion
		
		AISMessage22 msg = new AISMessage22Impl(sb, null);
		assertEquals( false, msg.getAddressedMessageIndicator());
		assertEquals( DestinationArea.class, msg.getDestination().getClass());
	}
	@Test
	public void DestinationIDs()
	{
		Sixbit sb = mock(Sixbit.class);	
		when(sb.getBoolean(anyInt())).thenReturn(true);
		when(sb.getIntFromTo(1, 6)).thenReturn(22);
		when(sb.length()).thenReturn(168);			//pass length assertion
		
		AISMessage22 msg = new AISMessage22Impl(sb, null);
		assertEquals( true, msg.getAddressedMessageIndicator());
		assertEquals( DestinationIDs.class, msg.getDestination().getClass());
	}
}