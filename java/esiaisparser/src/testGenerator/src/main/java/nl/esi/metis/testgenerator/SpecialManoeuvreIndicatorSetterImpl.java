package nl.esi.metis.testgenerator;

import java.util.Random;

import nl.esi.metis.aisparser.UtilsSpecialManoeuvreIndicator2;
import nl.esi.metis.aisparser.annotations.AISIllegalValueAnnotation;

public class SpecialManoeuvreIndicatorSetterImpl implements SpecialManoeuvreIndicatorSetter {
	private static final int MAX_CATEGORY = 3;
	
	private Random rn = new Random();
	
	public void setSpecialManoeuvreIndicator(TestBitVectorConstructor tbv, int from, int to) {	
		switch (rn.nextInt(MAX_CATEGORY))
		{
			case 0 :
				setSpecialManoeuvreIndicatorMeasurement(tbv, from, to);
				break;
			case 1 :
				setSpecialManoeuvreIndicatorUnavailable(tbv, from, to);
				break;
			case 2:
				setSpecialManoeuvreIndicatorIllegal (tbv, from, to);
				break;
			default:
				assert(false);	//impossible category
		}
	}
	
	public void setSpecialManoeuvreIndicatorMeasurement(TestBitVectorConstructor tbv, int from, int to) {
		int randomNum =  rn.nextInt(2)+1;
		assert (1 <= randomNum && randomNum <= 2);
		
		tbv.addExpectedOutput("getSpecialManoeuvre", randomNum);
		tbv.setBitVector(randomNum, from, to);
	}
	
	public void setSpecialManoeuvreIndicatorUnavailable(TestBitVectorConstructor tbv, int from, int to) {
		int value = 0;		//not available = default
		
		tbv.addExpectedOutput("getSpecialManoeuvre", value);
		tbv.setBitVector(value, from, to);
	}

	public void setSpecialManoeuvreIndicatorIllegal(TestBitVectorConstructor tbv, int from, int to) {
		int value = 3;		//not available = default
		
		tbv.addExpectedOutput("getSpecialManoeuvre", value);
		tbv.addExpectedAnnotation(new AISIllegalValueAnnotation("getSpecialManoeuvre", value, UtilsSpecialManoeuvreIndicator2.range));
		tbv.setBitVector(value, from, to);
	}
}	