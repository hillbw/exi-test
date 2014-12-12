package nl.esi.metis.testgenerator;

import java.util.Random;

import nl.esi.metis.aisparser.UtilsSpare;
import nl.esi.metis.aisparser.annotations.AISIllegalValueAnnotation;

public class SpareSetterImpl implements SpareSetter {
	private static final int MAX_CATEGORY = 2;
	
	private Random rn = new Random();
	
	public void setSpare(TestBitVectorConstructor tbv, int from, int to, int id) {
		switch (rn.nextInt(MAX_CATEGORY))
		{
			case 0 :
				setSpareCorrect(tbv, from, to, id);
				break;
			case 1 :
				setSpareIllegal(tbv, from, to, id);
				break;
			default:
				assert(false);	//impossible category
		}	
	}
	
	public void setSpareCorrect(TestBitVectorConstructor tbv, int from, int to, int id) {
		String name = "getSpare";
		if (id > 0) {
			name += id;
		}
		tbv.addExpectedOutput(name, 0);
		tbv.setBitVector(0, from, to);
	}
	
	public void setSpareIllegal(TestBitVectorConstructor tbv, int from, int to, int id) {
		int range = (int)Math.pow(2, to-from+1)-1;	//exclude zero + include max value
		int randomNum =  rn.nextInt(range) + 1;
		
		String name = "getSpare";
		if (id!=0)
		{
			name += id;
		}
		tbv.addExpectedAnnotation(new AISIllegalValueAnnotation(name, randomNum, UtilsSpare.range));
		tbv.addExpectedOutput(name, randomNum);
		tbv.setBitVector(randomNum, from, to);
	}
}	