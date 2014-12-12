package nl.esi.metis.testgenerator;

import java.util.Random;

public class SotdmaSetterImpl implements SotdmaSetter {
	
	private Random rn = new Random();
	
	
	public void setSotdma(TestBitVectorConstructor tbv, int from, int to) {
		String name = "getCommunicationState";
		assert (to - from == 18);
		
		//sync state
		int range = 4; 
		int randomNum =  rn.nextInt(range); 
		
		tbv.setBitVector(randomNum, to - 1, to);
		tbv.addExpectedOutput(name+".getSyncState", randomNum);
		
		//slot time-out
		range = 8; 
		randomNum =  rn.nextInt(range);
		
		tbv.setBitVector(randomNum, to - 4, to - 2);
		tbv.addExpectedOutput(name+".getSlotTimeOut", randomNum);
		
		//submessage
		range = 16384; 
		randomNum =  rn.nextInt(range);
		
		tbv.setBitVector(randomNum, from, to - 5);
		tbv.addExpectedOutput(name+".getSubMessage", randomNum);
	}
}	