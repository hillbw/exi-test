package nl.esi.metis.testgenerator;


import java.util.Random;

import nl.esi.metis.aisparser.annotations.AISHypothesisAnnotation;
import nl.esi.metis.aisparser.annotations.AISInconsistentLengthForTypeAnnotation;

public class ErroneousAisMessages24AGenerator {
	private AisMessages24AGenerator am24ag;
	
	private Random rn = new Random();
	
	public ErroneousAisMessages24AGenerator (
			RepeatIndicatorSetter repeatIndicatorSetter,
			UserIdSetter userIdSetter,
			NameSetter nameSetter
)
	{
		am24ag = new AisMessages24AGenerator(repeatIndicatorSetter, userIdSetter, nameSetter);
	}
	
	

private static final int MAX_CATEGORY = 2;
	
	public TestBitVectorConstructor getAisMessage()
	{
		TestBitVectorConstructor am24a = am24ag.getAisMessage();
		
		switch (rn.nextInt(MAX_CATEGORY))
		{
		case 0:
			int addBits = rn.nextInt(1000) +1;	//must add at least one bit to ensure Annotations
			if (addBits + 160 >= 168)	//violate standard & msgId = 24 & length == 168 ==> msgId 24 B
				addBits++;
			if (addBits + 160 >= 312)	//violate standard & length == 312 ==> Msg 19
				addBits++;
			if (addBits + 160 >= 424)   //violate standard & length == 424 ==> Msg 05
				addBits++;
			
			am24a.addBits(addBits);
			am24a.addExpectedAnnotation(new AISInconsistentLengthForTypeAnnotation(24, am24a.getBitVector().size()));
			am24a.addExpectedAnnotation(new AISHypothesisAnnotation(24, 160));
			break;
		case 1:
			//keep the same length, but change PartNumber 
			int partNumber = rn.nextInt(3)+1;
			assert (0 < partNumber && partNumber < 4);
			am24a.setBitVector(partNumber, 120, 121);
			am24a.addExpectedAnnotation(new AISInconsistentLengthForTypeAnnotation(24, 160));
			am24a.addExpectedAnnotation(new AISHypothesisAnnotation(24, 160));
			break;
		}	
		return am24a;
	}
}