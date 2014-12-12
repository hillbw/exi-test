package nl.esi.metis.testgenerator;

import java.util.Random;

public class RateOfTurnSetterImpl implements RateOfTurnSetter {
	private static final int MAX_CATEGORY = 4;
	
	private Random rn = new Random();
	
	
	public void setRateOfTurn(TestBitVectorConstructor tbv, int from, int to) {
			switch (rn.nextInt(MAX_CATEGORY))
			{
				case 0 :
					setRateOfTurnTIMeasurement(tbv, from, to);
					break;
				case 1 :
					setRateOfTurnTIMax(tbv, from, to);
					break;
				case 2 :
					setRateOfNoTI(tbv, from, to);
					break;
				case 3 :
					setRateOfTurnDEFAULT(tbv, from, to);
					break;
				default:
					assert(false);	//impossible category
			}	
	}

	public void setRateOfTurnTIMeasurement(TestBitVectorConstructor tbv, int from, int to) {
		String name = "getRateOfTurn";
		int range = 251; // == 125 - (-125) + 1
		int randomNum =  rn.nextInt(range) -125;
		
		tbv.addExpectedOutput(name, randomNum);
		tbv.setBitVector(randomNum, from, to);
	}
	
	public void setRateOfTurnTIMax(TestBitVectorConstructor tbv, int from, int to) {
		String name = "getRateOfTurn";
		int randomNum;
		switch (rn.nextInt(2))
		{
			case 0 :
				randomNum = -126;
				break;
			case 1 :
				randomNum = 126;
				break;
			default:
				assert(false);
				randomNum = 0;
				break;
		}
		tbv.addExpectedOutput(name, randomNum);
		tbv.setBitVector(randomNum, from, to);
	}


	private void setRateOfTurnDEFAULT(TestBitVectorConstructor tbv, int from,
			int to) {
		String name = "getRateOfTurn";
		int defaultValue = -128;
		
		tbv.addExpectedOutput(name, defaultValue);
		tbv.setBitVector(defaultValue, from, to);
	}

	private void setRateOfNoTI(TestBitVectorConstructor tbv, int from, int to) {
		String name = "getRateOfTurn";
		int randomNum;
		switch (rn.nextInt(2))
		{
			case 0 :
				randomNum = -127;
				break;
			case 1 :
				randomNum = 127;
				break;
			default:
				assert(false);
				randomNum = 0;
				break;
		}
		tbv.addExpectedOutput(name, randomNum);
		tbv.setBitVector(randomNum, from, to);		
		
	}
}	