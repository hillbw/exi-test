package nl.esi.metis.testgenerator;

import java.util.Random;

import nl.esi.metis.aisparser.UtilsNavStatus;
import nl.esi.metis.aisparser.annotations.AISIllegalValueAnnotation;

public class NavigationalStatusSetterImpl implements NavigationalStatusSetter {
	
	private static final int MAX_CATEGORY = 2;
	
	private Random rn = new Random();
	
	
	public void setNavigationalStatus(TestBitVectorConstructor tbv, int from, int to) {
		switch (rn.nextInt(MAX_CATEGORY))
		{
			case 0 :
				setNavigationalStatusCorrect(tbv, from, to);
				break;
			case 1 :
				setNavigationalStatusIllegal(tbv, from, to);
				break;
			default:
				assert(false);	//impossible category
		}
		
	}

	private final int RESERVED_LOW = 9;
	private final int RESERVED_HIGH = 13;
	
	private void setNavigationalStatusIllegal(TestBitVectorConstructor tbv,
			int from, int to) {
		int range = RESERVED_HIGH - RESERVED_LOW + 1;
		int randomNum =  rn.nextInt(range) + RESERVED_LOW;
		
		tbv.addExpectedAnnotation(new AISIllegalValueAnnotation("getNavigationalStatus", randomNum, UtilsNavStatus.range));
		tbv.addExpectedOutput("getNavigationalStatus", randomNum);
		tbv.setBitVector(randomNum, from, to);
		
	}


	private void setNavigationalStatusCorrect(TestBitVectorConstructor tbv,
			int from, int to) {
		int range = 16 - (RESERVED_HIGH - RESERVED_LOW + 1);
		int randomNum =  rn.nextInt(range);  // starting at 0
		
		if (randomNum >= RESERVED_LOW)
		{
			randomNum += (RESERVED_HIGH - RESERVED_LOW + 1);
		}
		
		tbv.addExpectedOutput("getNavigationalStatus", randomNum);
		tbv.setBitVector(randomNum, from, to);
		
		
	}
	

}	