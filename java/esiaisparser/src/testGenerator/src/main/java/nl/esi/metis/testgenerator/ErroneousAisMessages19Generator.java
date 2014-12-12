package nl.esi.metis.testgenerator;


import java.util.Random;

import nl.esi.metis.aisparser.annotations.AISHypothesisAnnotation;
import nl.esi.metis.aisparser.annotations.AISInconsistentLengthForTypeAnnotation;

public class ErroneousAisMessages19Generator {
	private AisMessages19Generator am19g;
	
	private Random rn = new Random();
	
	public ErroneousAisMessages19Generator (
			RepeatIndicatorSetter repeatIndicatorSetter,
			UserIdSetter userIdSetter,
			SpareSetter spare1Setter,
			SpeedOverGroundSetter speedOverGroundSetter,
			PositionAccuracySetter positionAccuracySetter,
			LongitudeSetter longitudeSetter, 
			LatitudeSetter latitudeSetter, 
			CourseOverGroundSetter courseOverGroundSetter,
			TrueHeadingSetter trueHeadingSetter,
			TimeStampSetter timeStampSetter,
			SpareSetter spare2Setter,
			NameSetter nameSetter,
			SpareSetter spare3Setter
)
	{
		am19g = new AisMessages19Generator(repeatIndicatorSetter, 
				userIdSetter,
				spare1Setter,
				speedOverGroundSetter,
				positionAccuracySetter,
				longitudeSetter,
				latitudeSetter,
				courseOverGroundSetter,
				trueHeadingSetter,
				timeStampSetter,
				spare2Setter,
				nameSetter,
				spare3Setter);
	}
	
	

private static final int MAX_CATEGORY = 2;
	
	public TestBitVectorConstructor getAisMessage()
	{
		TestBitVectorConstructor am19 = am19g.getAisMessage();
		
		switch (rn.nextInt(MAX_CATEGORY))
		{
		case 0:
			int addBits = rn.nextInt(800) +1;	//must add at least one bit to ensure Annotations
			if (addBits + 312 >= 424)	 //violate standard & length == 424 ==> Msg 05
				addBits++;
			
			am19.addBits(addBits);
			am19.addExpectedAnnotation(new AISInconsistentLengthForTypeAnnotation(19, am19.getBitVector().size()));
			am19.addExpectedAnnotation(new AISHypothesisAnnotation(19, 312));
			break;
		case 1:
			//keep the same length, but change MsgId to outside valid range  [0..63] \ {6,8,12,14,17,19,21,26}
			int msgId = rn.nextInt(64-8);
			if (msgId >= 6)
				msgId++;
			if (msgId >= 8)
				msgId++;
			if (msgId >= 12)
				msgId++;
			if (msgId >= 14)
				msgId++;
			if (msgId >= 17)
				msgId++;
			if (msgId >= 19)
				msgId++;
			if (msgId >= 21)
				msgId++;
			if (msgId >= 26)
				msgId++; 
			assert(0 <= msgId && msgId <= 63);
			
			am19.setBitVector(msgId, 306, 311);
			am19.addExpectedAnnotation(new AISInconsistentLengthForTypeAnnotation(msgId, 312));
			am19.addExpectedAnnotation(new AISHypothesisAnnotation(19, 312));
			break;
		}	
		return am19;
	}
}