package nl.esi.metis.testgenerator;

public class AisMessages01Generator {
	private RepeatIndicatorSetter repeatIndicatorSetter;
	private UserIdSetter userIdSetter;
	private NavigationalStatusSetter navigationalStatusSetter;
	private RateOfTurnSetter rateOfTurnSetter;
	private SpeedOverGroundSetter speedOverGroundSetter;
	private PositionAccuracySetter positionAccuracySetter;
	private LongitudeSetter longitudeSetter;
	private LatitudeSetter latitudeSetter;
	private CourseOverGroundSetter courseOverGroundSetter;
	private TrueHeadingSetter trueHeadingSetter;
	private TimeStampSetter timeStampSetter;
	private SpecialManoeuvreIndicatorSetter specialManoeuvreIndicatorSetter;
	private SpareSetter spareSetter;
	private RAIMSetter raimSetter;
	private SotdmaSetter sodmaSetter;
	
	public AisMessages01Generator (
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
		this.repeatIndicatorSetter = repeatIndicatorSetter;
		this.userIdSetter = userIdSetter;
		this.navigationalStatusSetter = navigationalStatusSetter;
		this.rateOfTurnSetter = rateOfTurnSetter;
		this.speedOverGroundSetter = speedOverGroundSetter;
		this.positionAccuracySetter = positionAccuracySetter;
		this.longitudeSetter = longitudeSetter;
		this.latitudeSetter = latitudeSetter;
		this.courseOverGroundSetter = courseOverGroundSetter;
		this.trueHeadingSetter = trueHeadingSetter;
		this.timeStampSetter = timeStampSetter;
		this.specialManoeuvreIndicatorSetter = specialManoeuvreIndicatorSetter;
		this.spareSetter = spareSetter;
		this.raimSetter = raimSetter;
		this.sodmaSetter = sodmaSetter;
	}
	
	
	public TestBitVectorConstructor getAisMessage()
	{
		TestBitVectorConstructor retval = new TestBitVectorConstructor(168);
		int msgId = 1;
		retval.setBitVector(msgId, 162, 167);
		retval.addExpectedOutput("getMessageID", msgId);
		
		repeatIndicatorSetter.setRepeatIndicator(retval, 160, 161, msgId);
		userIdSetter.setUserId(retval, 130, 159);
		navigationalStatusSetter.setNavigationalStatus(retval, 126, 129);
		rateOfTurnSetter.setRateOfTurn(retval, 118, 125);
		speedOverGroundSetter.setSpeedOverGround(retval, 108, 117);
		positionAccuracySetter.setPositionAccuracy(retval, 107, 107);
		longitudeSetter.setLongitude(retval, 79, 106);
		latitudeSetter.setLatitude(retval, 52, 78);
		courseOverGroundSetter.setCourseOverGround(retval, 40, 51);
		trueHeadingSetter.setTrueHeading(retval, 31, 39);
		timeStampSetter.setTimeStamp(retval, 25, 30);
		specialManoeuvreIndicatorSetter.setSpecialManoeuvreIndicator(retval, 23, 24);
		spareSetter.setSpare(retval, 20, 22, 0);
		raimSetter.setRaimFlag(retval, 19, 19);
		sodmaSetter.setSotdma(retval, 0, 18);
		return retval;
	}
}