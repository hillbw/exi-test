package nl.esi.metis.testgenerator;

import java.util.Random;

public class SpeedOverGroundSetterImpl implements SpeedOverGroundSetter {
	private static final int MAX_CATEGORY = 3;
	
	private Random rn = new Random();
	
	
	public void setSpeedOverGround(TestBitVectorConstructor tbv, int from, int to) {
			switch (rn.nextInt(MAX_CATEGORY))
			{
				case 0 :
					setSpeedOverGroundMeasurement(tbv, from, to);
					break;
				case 1 :
					setSpeedOverGroundMax(tbv, from, to);
					break;
				case 2 :
					setSpeedOverGroundDEFAULT(tbv, from, to);
					break;
				default:
					assert(false);	//impossible category
			}	
	}

	private void setSpeedOverGroundMeasurement(TestBitVectorConstructor tbv, int from, int to) {
		String name = "getSpeedOverGround";
		int range = 1022; 
		int randomNum =  rn.nextInt(range); // from 0 inclusive to 1022 exclusive
		
		tbv.addExpectedOutput(name, randomNum);
		tbv.setBitVector(randomNum, from, to);
	}
	
	private void setSpeedOverGroundMax(TestBitVectorConstructor tbv, int from, int to) {
		String name = "getSpeedOverGround";
		int maxNum = 1022;
		tbv.addExpectedOutput(name, maxNum);
		tbv.setBitVector(maxNum, from, to);
	}


	private void setSpeedOverGroundDEFAULT(TestBitVectorConstructor tbv, int from,
			int to) {
		String name = "getSpeedOverGround";
		int defaultValue = 1023;
		
		tbv.addExpectedOutput(name, defaultValue);
		tbv.setBitVector(defaultValue, from, to);
	}
}	