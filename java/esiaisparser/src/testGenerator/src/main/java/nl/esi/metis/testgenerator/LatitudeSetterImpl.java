package nl.esi.metis.testgenerator;

import java.util.Random;

import nl.esi.metis.aisparser.UtilsPositionInfo;
import nl.esi.metis.aisparser.annotations.AISIllegalValueAnnotation;

public class LatitudeSetterImpl implements LatitudeSetter {
	private static final int MAX_CATEGORY = 3;
	
	private static final int MIN_90 = -90*60*10000;
	private static final int MAX_90 = 90*60*10000;
	private static final int UNAVAILABLE = 0x3412140;
	private static final int MIN = - 0x4000000;
	private static final int MAX = 0x4000000-1;
	
	private Random rn = new Random();
	
	public void setLatitude(TestBitVectorConstructor tbv, int from, int to) {	
		switch (rn.nextInt(MAX_CATEGORY))
		{
			case 0 :
				setLatitudeMeasurement(tbv, from, to);
				break;
			case 1 :
				setLatitudeUnavailable(tbv, from, to);
				break;
			case 2:
				setLatitudeIllegal (tbv, from, to);
				break;
			default:
				assert(false);	//impossible category
		}
	}
	
	final int measurementRange = (MAX_90-MIN_90+1);
	public void setLatitudeMeasurement(TestBitVectorConstructor tbv, int from, int to) {
		int randomNum =  rn.nextInt(measurementRange) + MIN_90;
		assert ( MIN_90 <= randomNum &&  randomNum <= MAX_90 );
		
		final double degrees = toDegrees(randomNum);
		tbv.addExpectedOutput("getLatitudeInDegrees", degrees);
		tbv.setBitVector(randomNum, from, to);
	}
	
	public void setLatitudeUnavailable(TestBitVectorConstructor tbv, int from, int to) {
		int value = UNAVAILABLE;		//not available = default
		
		tbv.addExpectedOutput("getLatitudeInDegrees", 91.0);
		tbv.setBitVector(value, from, to);
	}

	final int illegalRange = (MIN_90 - MIN) + (MAX-MAX_90-1);
	public void setLatitudeIllegal(TestBitVectorConstructor tbv, int from, int to) {
		int randomNum =  rn.nextInt(illegalRange) + MIN;
		if (randomNum >= MIN_90)
		{
			randomNum += MAX_90+1 -MIN_90;
		}
		if (randomNum >= UNAVAILABLE)
		{
			randomNum ++;
		}
		assert (MIN <= randomNum && randomNum < MIN_90) || (MAX_90 < randomNum  && randomNum <= MAX);
		
		final double degrees = toDegrees(randomNum);
		tbv.addExpectedOutput("getLatitudeInDegrees", degrees);
		tbv.addExpectedAnnotation(new AISIllegalValueAnnotation("getLatitudeInDegrees", degrees, UtilsPositionInfo.rangeLatitude)); 
		tbv.setBitVector(randomNum, from, to);
	}

	private static final double factor = 1.0/(60*10000);
	private static double toDegrees(int randomNum) {
		return randomNum * factor;
	}
}	