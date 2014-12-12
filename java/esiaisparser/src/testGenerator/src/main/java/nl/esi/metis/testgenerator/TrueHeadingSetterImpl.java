package nl.esi.metis.testgenerator;

import java.util.Random;

import nl.esi.metis.aisparser.UtilsAngle9;
import nl.esi.metis.aisparser.annotations.AISIllegalValueAnnotation;

public class TrueHeadingSetterImpl implements TrueHeadingSetter {
	private static final int MAX_CATEGORY = 3;
	
	private static final String name = "getTrueHeading";
	
	private Random rn = new Random();
	
	
	public void setTrueHeading(TestBitVectorConstructor tbv, int from, int to) {
			switch (rn.nextInt(MAX_CATEGORY))
			{
				case 0 :
					setCourseOverGroundMeasurement(tbv, from, to);
					break;
				case 1:
					setCourseOverGroundDefault(tbv, from, to);
					break;
				case 2 :
					setCourseOverGroundIllegal(tbv, from, to);
					break;
				default:
					assert(false);	//impossible category
			}	
	}

	private static final int MAX_VALUE = 359;
	
	private void setCourseOverGroundMeasurement(TestBitVectorConstructor tbv, int from, int to) {
		int range = MAX_VALUE+1; 
		int randomNum =  rn.nextInt(range);
		assert (0 <= randomNum && randomNum <= MAX_VALUE);
		
		tbv.addExpectedOutput(name, randomNum);
		tbv.setBitVector(randomNum, from, to);
	}
	
	private void setCourseOverGroundDefault(TestBitVectorConstructor tbv, int from, int to) {
		int defaultValue = 511;
		
		tbv.addExpectedOutput(name, defaultValue);
		tbv.setBitVector(defaultValue, from, to);
	}

	private static final int MAX_ILLEGAL = 510;
	private static final int MIN_ILLEGAL = 360;
	
	private void setCourseOverGroundIllegal(TestBitVectorConstructor tbv, int from, int to) {
		int range = MAX_ILLEGAL - MIN_ILLEGAL +1; 
		int randomNum =  rn.nextInt(range) + MIN_ILLEGAL;
		assert (MIN_ILLEGAL <= randomNum && randomNum <= MAX_ILLEGAL);
		
		tbv.addExpectedAnnotation(new AISIllegalValueAnnotation(name, randomNum, UtilsAngle9.range));
		tbv.addExpectedOutput(name, randomNum);
		tbv.setBitVector(randomNum, from, to);
	}
}	