package nl.esi.metis.testgenerator;

import java.util.Random;

public class UserIdSetterImpl implements UserIdSetter {
	private Random rn = new Random();
	
	public void setUserId(TestBitVectorConstructor tbv, int from, int to) {
		int range = (int)Math.pow(2, to-from+1);
		int randomNum =  rn.nextInt(range);

		tbv.addExpectedOutput("getUserID", randomNum);
		tbv.setBitVector(randomNum, from, to);
	}
}	