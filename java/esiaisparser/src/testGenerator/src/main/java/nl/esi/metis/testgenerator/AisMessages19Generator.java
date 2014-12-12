package nl.esi.metis.testgenerator;

public class AisMessages19Generator extends
		AisMessagesClassBPositionReportGenerator {

	private static final int length = 312;
	private SpareSetter spare2Setter;
	private NameSetter nameSetter;
	private SpareSetter spare3Setter;
	
	public AisMessages19Generator (
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
		super (19, 
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
		this.nameSetter = nameSetter;
		this.spare3Setter = spare3Setter;
	}
	
	public TestBitVectorConstructor getAisMessage()
	{
		TestBitVectorConstructor retval =  super.getAisMessage();
		spare2Setter.setSpare(retval, length - 143, length - 140, 2);
		nameSetter.setName(retval, length - 263, length - 144, 20);
		spare3Setter.setSpare(retval, length - 312, length - 309, 3);
		return retval;
	}
}