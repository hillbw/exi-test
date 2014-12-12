package nl.esi.metis.testgenerator;

import java.util.Random;

public class RAIMSetterImpl implements RAIMSetter {
	private static final String name = "getRaimFlag";
	
	private Random rn = new Random();
	
	public void setRaimFlag(TestBitVectorConstructor tbv, int from, int to) {
		assert( from == to );
		int randomNum =  rn.nextInt(2);
		
		tbv.addExpectedOutput(name, (randomNum == 1));
		tbv.setBitVector(randomNum, from, to);
	}
}	