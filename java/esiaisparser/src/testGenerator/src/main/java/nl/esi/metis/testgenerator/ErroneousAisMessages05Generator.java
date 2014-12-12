package nl.esi.metis.testgenerator;

import java.util.Random;

import nl.esi.metis.aisparser.annotations.AISHypothesisAnnotation;
import nl.esi.metis.aisparser.annotations.AISInconsistentLengthForTypeAnnotation;

public class ErroneousAisMessages05Generator {
	private AisMessages05Generator am5g;
	
	private Random rn = new Random();
	
	public ErroneousAisMessages05Generator (
			RepeatIndicatorSetter repeatIndicatorSetter,
			UserIdSetter userIdSetter,
			NameSetter nameSetter
)
	{
		am5g = new AisMessages05Generator(repeatIndicatorSetter, userIdSetter, nameSetter);
	}
	
	

private static final int MAX_CATEGORY = 2;
	
	public TestBitVectorConstructor getAisMessage()
	{
		TestBitVectorConstructor am5 = am5g.getAisMessage();
		
		switch (rn.nextInt(MAX_CATEGORY))
		{
		case 0:
			int addBits = rn.nextInt(700) +1;	//must add at least one bit to ensure Annotations
			
			am5.addBits(addBits);
			am5.addExpectedAnnotation(new AISInconsistentLengthForTypeAnnotation(5, am5.getBitVector().size()));
			am5.addExpectedAnnotation(new AISHypothesisAnnotation(5, 424));
			break;
		case 1:
			//keep the same length, but change MsgId to outside valid range  [0..63] \ {5, 6, 8,12,14,17,26}
			int msgId = rn.nextInt(64-7);
			if (msgId >= 5)
				msgId++;
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
			if (msgId >= 26)
				msgId++; 
			assert(0 <= msgId && msgId <= 63);
			
			am5.setBitVector(msgId, 418, 423);
			am5.addExpectedAnnotation(new AISInconsistentLengthForTypeAnnotation(msgId, 424));
			am5.addExpectedAnnotation(new AISHypothesisAnnotation(5, 424));
			break;
		}	
		return am5;
	}
}