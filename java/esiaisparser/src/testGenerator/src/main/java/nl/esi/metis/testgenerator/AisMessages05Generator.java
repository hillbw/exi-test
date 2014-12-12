package nl.esi.metis.testgenerator;

public class AisMessages05Generator {
	private RepeatIndicatorSetter repeatIndicatorSetter;
	private UserIdSetter userIdSetter;
	private NameSetter nameSetter;
	
	public AisMessages05Generator (
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
		TestBitVectorConstructor retval = new TestBitVectorConstructor(424);
		int msgId = 5;
		retval.setBitVector(msgId, 418, 423);
		retval.addExpectedOutput("getMessageID", msgId);
		
		repeatIndicatorSetter.setRepeatIndicator(retval, 416, 417, msgId);
		userIdSetter.setUserId(retval, 386, 415);
		nameSetter.setName(retval, 192, 311, 20);
		return retval;
	}
}