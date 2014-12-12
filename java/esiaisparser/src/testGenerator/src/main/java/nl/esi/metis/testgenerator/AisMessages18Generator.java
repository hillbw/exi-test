package nl.esi.metis.testgenerator;

public class AisMessages18Generator extends
		AisMessagesClassBPositionReportGenerator {

	private static final int length = 168;
	private SpareSetter spare2Setter;
	
	public AisMessages18Generator (
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
			SpareSetter spare2Setter
		)
	{
		super (18, 
				repeatIndicatorSetter,
				userIdSetter,
				spare1Setter,
				speedOverGroundSetter,
				positionAccuracySetter,
				longitudeSetter,
				latitudeSetter,
				courseOverGroundSetter,
				trueHeadingSetter,
				timeStampSetter,
				length);
		this.spare2Setter = spare2Setter;
	}
	
	public TestBitVectorConstructor getAisMessage()
	{
		TestBitVectorConstructor retval =  super.getAisMessage();
		spare2Setter.setSpare(retval, length - 141, length - 140, 2);
		return retval;
	}
}