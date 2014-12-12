package nl.esi.metis.aisparser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.*;

public class VDMMessageTest {
	//message
	@Test
	public void message ()
	{
		//SetUp
		String msg = "Interesting Content";
		VDMLine mockedAIVDMLine = mock(VDMLine.class);	
		when(mockedAIVDMLine.getPayLoad()).thenReturn(msg);
		when(mockedAIVDMLine.getNrOfParts()).thenReturn(1);	//assert array of length > 0 is created
		when(mockedAIVDMLine.getPartId()).thenReturn(1);
		
		VDMMessage am = new VDMMessage(mockedAIVDMLine);
		//Execute
		String ret = am.getMessage();
		//Verify
		assertEquals(msg,ret);
		//Teardown
	}
	//atEnd
	@Test
	public void atEnd ()
	{
		//SetUp
		int id = 1;
		VDMLine mockedAIVDMLine = mock(VDMLine.class);	
		when(mockedAIVDMLine.getNrOfParts()).thenReturn(id);
		when(mockedAIVDMLine.getPartId()).thenReturn(id);
		when(mockedAIVDMLine.getPayLoad()).thenReturn("dont care");		//StringBuilder doesn't accept null string
		
		VDMMessage am = new VDMMessage(mockedAIVDMLine);
		//Execute
		boolean b = am.atEnd();
		//Verify
		assertTrue("At End test",b);
		//Teardown
	}
	@Test
	public void notAtEnd()
	{
		//SetUp
		VDMLine mockedAIVDMLine = mock(VDMLine.class);	
		when(mockedAIVDMLine.getNrOfParts()).thenReturn(9);
		when(mockedAIVDMLine.getPartId()).thenReturn(1);
		when(mockedAIVDMLine.getPayLoad()).thenReturn("dont care");		//StringBuilder doesn't accept null string
		
		VDMMessage am = new VDMMessage(mockedAIVDMLine);
		//Execute
		boolean b = am.atEnd();
		//Verify
		assertFalse("Not At End test",b);
		//Teardown	
	}
	//isPart
	private VDMLine createAIVDMLine(int nrOfParts, int partId, String channelId, String msgId, String msg) {
		VDMLine mock = mock(VDMLine.class);	
		when(mock.getNrOfParts()).thenReturn(nrOfParts);
		when(mock.getPartId()).thenReturn(partId);
		when(mock.getChannelId()).thenReturn(channelId);
		when(mock.getMessageId()).thenReturn(msgId);
		when(mock.getPayLoad()).thenReturn(msg);
		return mock;
	}
	private VDMMessage createInitialMessage(int nrOfParts, int partId, String channelId, String msgId, String msg) {
		VDMLine mockFirstLine = createAIVDMLine(nrOfParts, partId, channelId, msgId, msg);
		VDMMessage am = new VDMMessage(mockFirstLine);
		return am;
	}
	@Test
	public void correctPart()
	{
		//SetUp
		int nrOfParts = 6;
		String channelId = "A";
		String msgId = Integer.toString(6);
		
		VDMMessage am = createInitialMessage(nrOfParts, 2, channelId, msgId, "dont care");
		VDMLine mockSecondLine = createAIVDMLine(nrOfParts, 3, channelId, msgId, "dont care");
				
		//Execute
		boolean b = am.isPartOfMessage(mockSecondLine);
		//Verify
		assertTrue("correctPart",b);
		//Teardown	
	}
	@Test
	public void incorrectPart_different_nrOfParts()
	{
		//SetUp
		String msgId = Integer.toString(9);
		String channelId = "B";
		
		VDMMessage am = createInitialMessage(8, 2, channelId, msgId, "dont care");
		VDMLine mockSecondLine = createAIVDMLine(3, 3, channelId, msgId, "don't care");
				
		//Execute
		boolean b = am.isPartOfMessage(mockSecondLine);
		//Verify
		assertFalse("incorrectPart",b);
		//Teardown	
	}
	@Test
	public void incorrectPart_not_successor_partId()
	{
		//SetUp
		int nrOfParts = 3;
		String msgId = Integer.toString(7);
		String channelId = "B";
		
		VDMMessage am = createInitialMessage(nrOfParts, 2, channelId, msgId, "dont care");
		VDMLine mockLine = createAIVDMLine(nrOfParts, 1, channelId, msgId, "don't care");
				
		//Execute
		boolean b = am.isPartOfMessage(mockLine);
		//Verify
		assertFalse("incorrectPart",b);
		//Teardown	
	}
	@Test
	public void correctPart_despite_different_channelIds()
	{
		//SetUp
		int nrOfParts = 5;
		String msgId = Integer.toString(3);
		VDMMessage am = createInitialMessage(nrOfParts, 2, "A", msgId, "dont care");
		VDMLine mockSecondLine = createAIVDMLine(nrOfParts, 3, "B", msgId, "don't care");
				
		//Execute
		boolean b = am.isPartOfMessage(mockSecondLine);
		//Verify
		assertTrue("correctPart",b);
		//Teardown	
	}
	@Test
	public void correctPart_despite_different_messageIds()
	{
		//SetUp
		int nrOfParts = 5;
		String channelId = "B";
		VDMMessage am = createInitialMessage(nrOfParts, 2, channelId, "7", "dont care");
		VDMLine mockSecondLine = createAIVDMLine(nrOfParts, 3, channelId, "3", "don't care");
				
		//Execute
		boolean b = am.isPartOfMessage(mockSecondLine);
		//Verify
		assertTrue("correctPart",b);
		//Teardown	
	}
	@Test
	public void incorrectPart_too_much_differences()
	{
		//SetUp
		int nrOfParts = 5;
		VDMMessage am = createInitialMessage(nrOfParts, 2, "A", "2", "dont care");
		VDMLine mockSecondLine = createAIVDMLine(nrOfParts, 3, "B", "3", "don't care");
				
		//Execute
		boolean b = am.isPartOfMessage(mockSecondLine);
		//Verify
		assertFalse("incorrectPart",b);
		//Teardown	
	}
	//Add Line
	@Test
	public void addLine()
	{
		//SetUp
		int nrOfParts = 2;
		String channelId = "A";
		String msgId = Integer.toString(5);
		
		String part1 = "Part1";
		String part2 = "Part2";
		
		VDMMessage am = createInitialMessage(nrOfParts, 1, channelId, msgId, part1);
		VDMLine mockSecondLine = createAIVDMLine(nrOfParts, 2, channelId, msgId, part2);
		assertTrue("Incorrect next line", am.isPartOfMessage(mockSecondLine));		
		//Execute
		am.add (mockSecondLine);
		//Verify
		assertEquals(part1+part2, am.getMessage());
		assertTrue("Message consists of 2 parts", am.atEnd());
		assertTrue("Message is complete", am.isComplete());
		//Teardown	
	}
}