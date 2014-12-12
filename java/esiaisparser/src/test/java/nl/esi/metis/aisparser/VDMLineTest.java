package nl.esi.metis.aisparser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for VDMLine.
 */
public class VDMLineTest 
{
	//Syntax correctness
	@Test
	public void correctFormat()
	{
		//Setup
		String validString = "!AIVDM,1,1,,A,0,0*5E";
		//Exercise
		boolean retval = VDMLine.isValid(validString);
		//Verify
		assertTrue(retval);
		//Teardown
	}
	@Test
	public void wrongFormat_NoExclamationMark()
	{
		//Setup
		String invalidString = "Wrong";
		//Exercise
		boolean retval = VDMLine.isValid(invalidString);
		//Verify
		assertFalse(retval);
		//Teardown
	}
	@Test
	public void wrongFormat_NoChecksum()
	{
		//Setup
		String invalidString = "!AIVDM,1,1,,A,53P:Rf02<9tlm`C;L01=HUA`E:0ltU840000000l1P@465eh090ESkSCmE200000,0";
		//Exercise
		boolean retval = VDMLine.isValid(invalidString);
		//Verify
		assertFalse(retval);
		//Teardown
	}
	@Test
	public void wrongFormat_IllegalCharacter()
	{
		//Setup
		String invalidString = "!AIVDM,1,1,,A,53P:Rf02<9tlm`CXL01=HUA`E:0ltU840000000l1P@465eh090ESkSCmE200000,0*1F";
		//Exercise
		boolean retval = VDMLine.isValid(invalidString);
		//Verify
		assertFalse(retval);
		//Teardown
	}
	//Accessor NrOfParts
	@Test
	public void singlePart()
	{
		//Setup
		final int nrOfParts = 1;
		String validString = "!AIVDM," +Integer.toString(nrOfParts) + ",1,,A,0,0*5E";
		//Exercise
		assertTrue("Precondition check", VDMLine.isValid(validString));
		VDMLine a = new VDMLine(null,validString);
		//Verify
		assertEquals(nrOfParts,a.getNrOfParts());
		//Teardown
	}
	@Test
	public void doublePart()
	{
		//Setup
		final int nrOfParts = 2;
		String validString = "!AIVDM," +Integer.toString(nrOfParts) + ",1,,A,0,0*5E";
		//Exercise
		assertTrue("Precondition check", VDMLine.isValid(validString));
		VDMLine a = new VDMLine(null,validString);
		//Verify
		assertEquals(nrOfParts,a.getNrOfParts());
		//Teardown
	}
	//Accessor PartId
	@Test
	public void firstPart()
	{
		//Setup
		final int partId = 1;
		String validString = "!AIVDM,1," +Integer.toString(partId) + ",,A,0,0*5E";
		//Exercise
		assertTrue("Precondition check", VDMLine.isValid(validString));
		VDMLine a = new VDMLine(null,validString);
		//Verify
		assertEquals(partId,a.getPartId());
		//Teardown
	}
	@Test
	public void secondPart()
	{
		final int partId = 2;
		String validString = "!AIVDM,1," +Integer.toString(partId) + ",,A,0,0*5E";
		//Exercise
		assertTrue("Precondition check", VDMLine.isValid(validString));
		VDMLine a = new VDMLine(null,validString);
		//Verify
		assertEquals(partId,a.getPartId());
		//Teardown
	}
	//Accessor MessageId
	@Test
	public void acessMessageId()
	{
		final String msgId = Integer.toString(2);
		String validString = "!AIVDM,1,1," +msgId + ",A,0,0*5E";
		//Exercise
		assertTrue("Precondition check", VDMLine.isValid(validString));
		VDMLine a = new VDMLine(null,validString);
		//Verify
		assertEquals(msgId,a.getMessageId());
		//Teardown
	}
	@Test
	public void acessEmptyMessageId()
	{
		String msgId = "";
		String validString = "!AIVDM,1,1," + msgId + ",A,0,0*5E";
		//Exercise
		assertTrue("Precondition check", VDMLine.isValid(validString));
		VDMLine a = new VDMLine(null,validString);
		//Verify
		assertEquals(msgId,a.getMessageId());
		//Teardown
	}

	//Accessor Channel
	@Test
	public void acessChannelA()
	{
		final char channel = 'A';
		String validString = "!AIVDM,1,1,," + channel + ",0,0*5E";
		//Exercise
		assertTrue("Precondition check", VDMLine.isValid(validString));
		VDMLine a = new VDMLine(null,validString);
		String actual = a.getChannelId();
		//Verify
		assertEquals(1, actual.length());
		assertEquals(channel,actual.charAt(0));
		//Teardown
	}
	@Test
	public void acessChannelEmpty()
	{
		final String channel = "";
		String validString = "!AIVDM,1,1,," + channel + ",0,0*5E";
		//Exercise
		assertTrue("Precondition check", VDMLine.isValid(validString));
		VDMLine a = new VDMLine(null,validString);
		String actual = a.getChannelId();
		//Verify
		assertEquals(0, actual.length());
		assertEquals(channel,actual);
		//Teardown
	}
	//Accessor PayLoad
	@Test
	public void acessPayLoad()
	{
		final String payLoad = "01234567890ABCDEFGHIJKLMNOPQRSTUVW";
		String validString = "!AIVDM,1,1,,A,"+ payLoad +",0*3F";
		//Exercise
		assertTrue("Precondition check", VDMLine.isValid(validString));
		VDMLine a = new VDMLine(null,validString);
		//Verify
		assertEquals(payLoad,a.getPayLoad());
		//Teardown
	}
	//Accessor NrOfFillBits
	@Test
	public void acessNrOfFillBits()
	{
		final int nrOfFillBits = 4;
		String validString = "!AIVDM,1,1,,A,0," +Integer.toString(nrOfFillBits) + "*5E";
		//Exercise
		assertTrue("Precondition check", VDMLine.isValid(validString));
		VDMLine a = new VDMLine(null,validString);
		//Verify
		assertEquals(nrOfFillBits,a.getNrOfFillBits());
		//Teardown
	}
	//Accessor checkSum
	@Test
	public void acessCheckSum()
	{
		//Setup
		final int checksum = 0xF3;
		String validString = "!AIVDM,1,1,,A,0,0*" +Integer.toString(checksum,16).toUpperCase();
		//Exercise
		assertTrue("Precondition check", VDMLine.isValid(validString));
		VDMLine a = new VDMLine(null,validString);
		//Verify
		assertEquals(checksum,a.getCheckSum());
		//Teardown
	}

	//Sematic correctness
	@Test
	public void validChecksum() {
		//Setup
		String validString = "!AIVDM,1,1,,B,19NS7Sp02wo?HETKA3K6mUM20<L=,0*26";
		//Exercise
		assertTrue("Precondition check", VDMLine.isValid(validString));
		VDMLine a = new VDMLine(null,validString);
		//Verify
		assertTrue(a.isCheckSumCorrect());
		//Teardown
	}
	@Test
	public void invalidChecksum() {
		//Setup
		String semanticallyInvalidString = "!AIVDM,1,1,,B,19NS7Sp02wo?HETKANK6mUM20<L=,0*55";
		//Exercise
		assertTrue("Precondition check", VDMLine.isValid(semanticallyInvalidString));
		VDMLine a = new VDMLine(null,semanticallyInvalidString);
		//Verify
		assertFalse(a.isCheckSumCorrect());
		//Teardown
	}
	@Test
	public void invalidPartId() {
		//Setup
		int nrOfParts = 3;
		int partId = 6;
		String semanticallyInvalidString = "!AIVDM,"+Integer.toString(nrOfParts)+","+ Integer.toString(partId)+ ",,B,19NS7Sp02wo?HKAN2K6mUM20<L=,0*55";
		//Exercise
		assertTrue("Precondition check", VDMLine.isValid(semanticallyInvalidString));
		VDMLine a = new VDMLine(null,semanticallyInvalidString);
		//Verify
		assertFalse(a.isPartIdCorrect());
		//Teardown
	}
}