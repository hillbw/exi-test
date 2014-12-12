package nl.esi.metis.testgenerator;

import java.util.List;
import java.util.Map;
import java.util.Random;

import nl.esi.metis.aisparser.VDMLine;
import nl.esi.metis.aisparser.annotations.Annotation;

public class CompleteRandomTest {	
	

	private TestBitVectorConstructor tbc;

	private Random rn = new Random();


	public CompleteRandomTest ()
	{
		
		tbc =  new TestBitVectorConstructor(0);
		final int length = rn.nextInt(1099)+1;
		tbc.addBits(length);
	}

	public String[] getInputs() {
		PayLoad pl = new PayLoad(tbc.getBitVector());

		String start = "AIVDM,1,1,,A," + pl.getAsciiString() + "," + pl.getFillBits();
		int checksum = VDMLine.calculateCheckSum(start);

		String checksumString = Integer.toHexString(checksum).toUpperCase();
		if (checksumString.length() == 1)		//Have to add leading '0' since toHexString has no extra leading 0s.
		{
			checksumString = '0' + checksumString;
		}

		return new String[]{"!"+start+"*"+checksumString};
	}

	public Map<String, Object> getExpectedOutputs() {
		return tbc.getExpectedOutputs();
	}

	public List<Annotation> getExpectedAnnotations() {
		return tbc.getExpectedAnnotations();
	}

}
