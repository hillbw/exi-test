package nl.esi.metis.testgenerator;

import java.util.Random;

import nl.esi.metis.aisparser.UtilsPositionInfo;
import nl.esi.metis.aisparser.annotations.AISIllegalValueAnnotation;

public class LongitudeSetterImpl implements LongitudeSetter {
	private static final int MAX_CATEGORY = 3;
	
	private static final int MIN_180 = -0x66FF300;
	private static final int MAX_180 = 0x66FF300;
	private static final int UNAVAILABLE = 0x6791AC0;
	private static final int MIN = - 0x8000000;
	private static final int MAX = 0x8000000-1;
	
	private Random rn = new Random();
	
	public void setLongitude(TestBitVectorConstructor tbv, int from, int to) {	
		switch (rn.nextInt(MAX_CATEGORY))
		{
			case 0 :
				setLongitudeMeasurement(tbv, from, to);
				break;
			case 1 :
				setLongitudeUnavailable(tbv, from, to);
				break;
			case 2:
				setLongitudeIllegal (tbv, from, to);
				break;
			default:
				assert(false);	//impossible category
		}
	}
	
	final int measurementRange = (MAX_180-MIN_180+1);
	public void setLongitudeMeasurement(TestBitVectorConstructor tbv, int from, int to) {
		int randomNum =  rn.nextInt(measurementRange) + MIN_180;
		assert ( MIN_180 <= randomNum &&  randomNum <= MAX_180 );
		
		final double degrees = toDegrees(randomNum);
		tbv.addExpectedOutput("getLongitudeInDegrees", degrees);
		tbv.setBitVector(randomNum, from, to);
	}
	
	public void setLongitudeUnavailable(TestBitVectorConstructor tbv, int from, int to) {
		int value = UNAVAILABLE;		//not available = default
		
		tbv.addExpectedOutput("getLongitudeInDegrees", 181.0);
		tbv.setBitVector(value, from, to);
	}

	final int illegalRange = (MIN_180 - MIN) + (MAX-MAX_180-1);
	public void setLongitudeIllegal(TestBitVectorConstructor tbv, int from, int to) {
		int randomNum =  rn.nextInt(illegalRange) + MIN;
		if (randomNum >= MIN_180)
		{
			randomNum += MAX_180+1 -MIN_180;
		}
		if (randomNum >= UNAVAILABLE)
		{
			randomNum ++;
		}
		assert (MIN <= randomNum && randomNum < MIN_180) || (MAX_180 < randomNum  && randomNum <= MAX);
		
		final double degrees = toDegrees(randomNum);
		tbv.addExpectedOutput("getLongitudeInDegrees", degrees);
		tbv.addExpectedAnnotation(new AISIllegalValueAnnotation("getLongitudeInDegrees", degrees, UtilsPositionInfo.rangeLongitude)); 
		tbv.setBitVector(randomNum, from, to);
	}

	private static final double factor = 1.0/(60*10000);
	private static double toDegrees(int randomNum) {
		return randomNum * factor;
	}
}	