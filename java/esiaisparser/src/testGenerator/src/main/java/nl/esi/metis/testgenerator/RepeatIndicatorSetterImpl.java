package nl.esi.metis.testgenerator;

import java.util.Random;

import nl.esi.metis.aisparser.annotations.AISIllegalValueAnnotation;

public class RepeatIndicatorSetterImpl implements RepeatIndicatorSetter {
	private static final int MAX_CATEGORY = 2;
	
	private Random rn = new Random();
	
	public void setRepeatIndicator(TestBitVectorConstructor tbv, int from, int to, int msgId) {
		assert(to-from+1 == 2);
		
		if (msgId == 27)
		{
			switch (rn.nextInt(MAX_CATEGORY))
			{
				case 0 :
					setRepeatIndicatorCorrect(tbv, from, to);
					break;
				case 1 :
					setRepeatIndicatorIllegal(tbv, from, to);
					break;
				default:
					assert(false);	//impossible category
			}	
		}
		else
		{
			int range = (int)Math.pow(2, to-from+1);	//range from 0 (inclusive) till 2^(to-from+1) (exclusive)
			int randomNum =  rn.nextInt(range);
			setRepeatIndicatorValue(tbv, from, to, randomNum);
		}
	}

	public void setRepeatIndicatorValue(TestBitVectorConstructor tbv, int from, int to, int num) {
		String name = "getRepeatIndicator";
		tbv.addExpectedOutput(name, num);
		tbv.setBitVector(num, from, to);
	}

	public void setRepeatIndicatorCorrect(TestBitVectorConstructor tbv, int from, int to) {
		setRepeatIndicatorValue(tbv, from, to, 3);
	}
	
	public void setRepeatIndicatorIllegal(TestBitVectorConstructor tbv, int from, int to) {
		int randomNum =  rn.nextInt(3); //range from 0 (inclusive) till 3 (exclusive)

		setRepeatIndicatorValue(tbv, from, to, randomNum);
		
		tbv.addExpectedAnnotation(new AISIllegalValueAnnotation("getRepeatIndicator", randomNum, "{3}"));
	}
}	