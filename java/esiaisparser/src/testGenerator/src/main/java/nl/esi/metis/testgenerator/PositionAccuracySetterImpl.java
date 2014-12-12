package nl.esi.metis.testgenerator;

import java.util.Random;

public class PositionAccuracySetterImpl implements PositionAccuracySetter {
	private static final String name = "getPositionAccuracy";
	
	private Random rn = new Random();
	
	public void setPositionAccuracy(TestBitVectorConstructor tbv, int from, int to) {
		assert( from == to );
		int randomNum =  rn.nextInt(2);
		
		tbv.addExpectedOutput(name, (randomNum == 1));
		tbv.setBitVector(randomNum, from, to);
	}
}	