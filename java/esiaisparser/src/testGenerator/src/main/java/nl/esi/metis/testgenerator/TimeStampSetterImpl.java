package nl.esi.metis.testgenerator;

import java.util.Random;

public class TimeStampSetterImpl implements TimeStampSetter {
	private static final int MAX_CATEGORY = 3;
	
	private static final String name = "getTimeStamp";
	
	private Random rn = new Random();
	
	
	public void setTimeStamp(TestBitVectorConstructor tbv, int from, int to) {
			switch (rn.nextInt(MAX_CATEGORY))
			{
				case 0 :
					setTimeStampMeasurement(tbv, from, to);
					break;
				case 1:
					setTimeStampDefault(tbv, from, to);
					break;
				case 2 :
					setTimeStampPositioningSystem(tbv, from, to);
					break;
				default:
					assert(false);	//impossible category
			}	
	}

	private static final int MAX_VALUE = 59;
	
	private void setTimeStampMeasurement(TestBitVectorConstructor tbv, int from, int to) {
		int range = MAX_VALUE+1; 
		int randomNum =  rn.nextInt(range);
		assert (0 <= randomNum && randomNum <= MAX_VALUE);
		
		tbv.addExpectedOutput(name, randomNum);
		tbv.setBitVector(randomNum, from, to);
	}
	
	private void setTimeStampDefault(TestBitVectorConstructor tbv, int from, int to) {
		int defaultValue = 60;
		
		tbv.addExpectedOutput(name, defaultValue);
		tbv.setBitVector(defaultValue, from, to);
	}

	private static final int MAX_POSITIONING_SYSTEM = 63;
	private static final int MIN_POSITIONING_SYSTEM = 61;
	
	private void setTimeStampPositioningSystem(TestBitVectorConstructor tbv, int from, int to) {
		int range = MAX_POSITIONING_SYSTEM - MIN_POSITIONING_SYSTEM +1; 
		int randomNum =  rn.nextInt(range) + MIN_POSITIONING_SYSTEM;
		assert (MIN_POSITIONING_SYSTEM <= randomNum && randomNum <= MAX_POSITIONING_SYSTEM);
		
		//tbv.addExpectedAnnotation(new AISIllegalValueAnnotation(name, randomNum, "0-60"));  //for UTC second this is an illegal value
		tbv.addExpectedOutput(name, randomNum);
		tbv.setBitVector(randomNum, from, to);
	}
}	