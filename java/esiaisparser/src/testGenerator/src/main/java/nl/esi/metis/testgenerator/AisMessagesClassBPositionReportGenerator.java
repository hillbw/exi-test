package nl.esi.metis.testgenerator;

public class AisMessagesClassBPositionReportGenerator {
		
	private int msgId;
	private RepeatIndicatorSetter repeatIndicatorSetter;
	private UserIdSetter userIdSetter;
	private SpareSetter spare1Setter;
	private SpeedOverGroundSetter speedOverGroundSetter;
	private PositionAccuracySetter positionAccuracySetter;
	private LongitudeSetter longitudeSetter;
	private LatitudeSetter latitudeSetter;
	private CourseOverGroundSetter courseOverGroundSetter;
	private TrueHeadingSetter trueHeadingSetter;
	private TimeStampSetter timeStampSetter;
	private int length;
	
	public AisMessagesClassBPositionReportGenerator (
				int msgId,
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
				int length
			)
	{
		this.msgId = msgId;
		this.repeatIndicatorSetter = repeatIndicatorSetter;
		this.userIdSetter = userIdSetter;
		this.spare1Setter = spare1Setter;
		this.speedOverGroundSetter = speedOverGroundSetter;
		this.positionAccuracySetter = positionAccuracySetter;
		this.longitudeSetter = longitudeSetter;
		this.latitudeSetter = latitudeSetter;
		this.courseOverGroundSetter = courseOverGroundSetter;
		this.trueHeadingSetter = trueHeadingSetter;
		this.timeStampSetter = timeStampSetter;
		this.length = length;
	}
	
	
	public TestBitVectorConstructor getAisMessage()
	{
		TestBitVectorConstructor retval = new TestBitVectorConstructor(length);
		retval.setBitVector(msgId, length - 6, length - 1);
		retval.addExpectedOutput("getMessageID", msgId);
		
		repeatIndicatorSetter.setRepeatIndicator(retval, length - 8, length - 7, msgId);
		userIdSetter.setUserId(retval, length - 38, length - 9);
		
		spare1Setter.setSpare(retval, length - 46, length - 39, 1);
		speedOverGroundSetter.setSpeedOverGround(retval, length - 56, length - 47);
		positionAccuracySetter.setPositionAccuracy(retval, length - 57, length - 57);
		longitudeSetter.setLongitude(retval, length - 85, length - 58);
		latitudeSetter.setLatitude(retval, length - 112, length - 86);
		courseOverGroundSetter.setCourseOverGround(retval, length - 124, length - 113);
		trueHeadingSetter.setTrueHeading(retval, length - 133, length - 125);
		timeStampSetter.setTimeStamp(retval, length - 139, length - 134);
		return retval;
	}
}