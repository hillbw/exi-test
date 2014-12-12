package nl.esi.metis.testgenerator;

import java.util.Random;

import nl.esi.metis.aisparser.annotations.AISHypothesisAnnotation;
import nl.esi.metis.aisparser.annotations.AISInconsistentLengthForTypeAnnotation;

public class ErroneousAisMessages01Generator {
	private AisMessages01Generator am1g;
	
	private Random rn = new Random();
	
	public ErroneousAisMessages01Generator (
			RepeatIndicatorSetter repeatIndicatorSetter,
			UserIdSetter userIdSetter,
			NavigationalStatusSetter navigationalStatusSetter,
			RateOfTurnSetter rateOfTurnSetter,
			SpeedOverGroundSetter speedOverGroundSetter,
			PositionAccuracySetter positionAccuracySetter,
			LongitudeSetter longitudeSetter, 
			LatitudeSetter latitudeSetter, 
			CourseOverGroundSetter courseOverGroundSetter,
			TrueHeadingSetter trueHeadingSetter,
			TimeStampSetter timeStampSetter,
			SpecialManoeuvreIndicatorSetter specialManoeuvreIndicatorSetter, 
			SpareSetter spareSetter,
			RAIMSetter raimSetter,
			SotdmaSetter sodmaSetter)
	{
		am1g = new AisMessages01Generator(repeatIndicatorSetter, userIdSetter, navigationalStatusSetter, rateOfTurnSetter, speedOverGroundSetter, positionAccuracySetter, longitudeSetter, latitudeSetter, courseOverGroundSetter, trueHeadingSetter, timeStampSetter, specialManoeuvreIndicatorSetter, spareSetter, raimSetter, sodmaSetter);
	}
	
	
	private static final int MAX_CATEGORY = 2;
	
	public TestBitVectorConstructor getAisMessage()
	{
		TestBitVectorConstructor am1 = am1g.getAisMessage();
		
		switch (rn.nextInt(MAX_CATEGORY))
		{
		case 0:
			int addBits = rn.nextInt(1100) +1;	//must add at least one bit to ensure Annotations
			if (addBits + 168 >= 312)	//violate standard & length == 312 ==> Msg 19
				addBits++;
			if (addBits + 168 >= 424)   //violate standard & length == 424 ==> Msg 05
				addBits++;
			
			am1.addBits(addBits);
			am1.addExpectedAnnotation(new AISInconsistentLengthForTypeAnnotation(1, am1.getBitVector().size()));
			am1.addExpectedAnnotation(new AISHypothesisAnnotation(1, 168));
			break;
		case 1:
			//keep the same length, but change MsgId to outside valid range  {0} + [28..63]
			int msgId = rn.nextInt(64-27);
			if (msgId > 0)
			{
				msgId += 27;
			}
			assert (msgId == 0 || (27 < msgId && msgId < 64));
			am1.setBitVector(msgId, 162, 167);
			am1.addExpectedAnnotation(new AISInconsistentLengthForTypeAnnotation(msgId, 168));
			am1.addExpectedAnnotation(new AISHypothesisAnnotation(1, 168));
			break;
		}	
		return am1;
	}
}