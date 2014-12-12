package nl.esi.metis.testgenerator;

public class AisMessages24AGenerator {
	private RepeatIndicatorSetter repeatIndicatorSetter;
	private UserIdSetter userIdSetter;
	private NameSetter nameSetter;
	
	public AisMessages24AGenerator (
			RepeatIndicatorSetter repeatIndicatorSetter,
			UserIdSetter userIdSetter,
			NameSetter nameSetter
)
	{
		this.repeatIndicatorSetter = repeatIndicatorSetter;
		this.userIdSetter = userIdSetter;
		this.nameSetter = nameSetter;
	}
	
	
	public TestBitVectorConstructor getAisMessage()
	{
		TestBitVectorConstructor retval = new TestBitVectorConstructor(160);
		int msgId = 24;
		retval.setBitVector(msgId, 154, 159);
		retval.addExpectedOutput("getMessageID", msgId);
		
		int partNumber = 0;
		retval.setBitVector(partNumber, 120, 121);
		retval.addExpectedOutput("getPartNumber", partNumber);
		
		repeatIndicatorSetter.setRepeatIndicator(retval, 152, 153, msgId);
		userIdSetter.setUserId(retval, 122, 151);
		nameSetter.setName(retval, 0, 119, 20);
		return retval;
	}
}