package nl.esi.metis.testgenerator;

import java.util.List;
import java.util.Map;
import java.util.Random;

import nl.esi.metis.aisparser.VDMLine;
import nl.esi.metis.aisparser.annotations.Annotation;

public class BlackBoxTest {	
	//TODO: better implementation???
	// proposal: BitSetter are not intended as one instance to be used over different Messages,
	// but as multiple instances configured for each Message
	// disadvantage: systematic value generation becomes dependent on the Message (Of course for random values it should matter)

	private static AisMessages01Generator am1g = new AisMessages01Generator(
			new RepeatIndicatorSetterImpl(), 
			new UserIdSetterImpl(),
			new NavigationalStatusSetterImpl(),
			new RateOfTurnSetterImpl(),
			new SpeedOverGroundSetterImpl(),
			new PositionAccuracySetterImpl(),
			new LongitudeSetterImpl(), 
			new LatitudeSetterImpl(),
			new CourseOverGroundSetterImpl(),
			new TrueHeadingSetterImpl(),
			new TimeStampSetterImpl(),
			new SpecialManoeuvreIndicatorSetterImpl(), 
			new SpareSetterImpl(),
			new RAIMSetterImpl(),
			new SotdmaSetterImpl());

	private static ErroneousAisMessages01Generator eam1g = new ErroneousAisMessages01Generator(
			new RepeatIndicatorSetterImpl(), 
			new UserIdSetterImpl(),
			new NavigationalStatusSetterImpl(),
			new RateOfTurnSetterImpl(),
			new SpeedOverGroundSetterImpl(),
			new PositionAccuracySetterImpl(),
			new LongitudeSetterImpl(), 
			new LatitudeSetterImpl(),
			new CourseOverGroundSetterImpl(),
			new TrueHeadingSetterImpl(),
			new TimeStampSetterImpl(),
			new SpecialManoeuvreIndicatorSetterImpl(), 
			new SpareSetterImpl(),
			new RAIMSetterImpl(),
			new SotdmaSetterImpl());

	private static AisMessages05Generator am5g = new AisMessages05Generator(
			new RepeatIndicatorSetterImpl(), 
			new UserIdSetterImpl(),
			new NameSetterImpl()
			);
	
	private static ErroneousAisMessages05Generator eam5g = new ErroneousAisMessages05Generator(
			new RepeatIndicatorSetterImpl(), 
			new UserIdSetterImpl(),
			new NameSetterImpl()
			);
	
	private static AisMessages18Generator am18g = new AisMessages18Generator(
			new RepeatIndicatorSetterImpl(), 
			new UserIdSetterImpl(),
			new SpareSetterImpl(),
			new SpeedOverGroundSetterImpl(),
			new PositionAccuracySetterImpl(),
			new LongitudeSetterImpl(), 
			new LatitudeSetterImpl(),
			new CourseOverGroundSetterImpl(),
			new TrueHeadingSetterImpl(),
			new TimeStampSetterImpl(),
			new SpareSetterImpl()
			);

	private static AisMessages19Generator am19g = new AisMessages19Generator(
			new RepeatIndicatorSetterImpl(), 
			new UserIdSetterImpl(),
			new SpareSetterImpl(),
			new SpeedOverGroundSetterImpl(),
			new PositionAccuracySetterImpl(),
			new LongitudeSetterImpl(), 
			new LatitudeSetterImpl(),
			new CourseOverGroundSetterImpl(),
			new TrueHeadingSetterImpl(),
			new TimeStampSetterImpl(),
			new SpareSetterImpl(),
			new NameSetterImpl(),
			new SpareSetterImpl()
			);

	private static ErroneousAisMessages19Generator eam19g = new ErroneousAisMessages19Generator(
			new RepeatIndicatorSetterImpl(), 
			new UserIdSetterImpl(),
			new SpareSetterImpl(),
			new SpeedOverGroundSetterImpl(),
			new PositionAccuracySetterImpl(),
			new LongitudeSetterImpl(), 
			new LatitudeSetterImpl(),
			new CourseOverGroundSetterImpl(),
			new TrueHeadingSetterImpl(),
			new TimeStampSetterImpl(),
			new SpareSetterImpl(),
			new NameSetterImpl(),
			new SpareSetterImpl()
			);

	private static AisMessages24AGenerator am24ag = new AisMessages24AGenerator(
			new RepeatIndicatorSetterImpl(), 
			new UserIdSetterImpl(),
			new NameSetterImpl()
			);

	private static ErroneousAisMessages24AGenerator eam24ag = new ErroneousAisMessages24AGenerator(
			new RepeatIndicatorSetterImpl(), 
			new UserIdSetterImpl(),
			new NameSetterImpl()
			);

	private TestBitVectorConstructor tbc;

	private Random rn = new Random();


	public BlackBoxTest ()
	{
		switch ( rn.nextInt(9) )
		{
		case 0:
			tbc = am1g.getAisMessage();
			break;
		case 1:
			tbc = eam1g.getAisMessage();
			break;	
		case 2:
			tbc = am5g.getAisMessage();
			break;
		case 3:
			tbc = eam5g.getAisMessage();
			break;
		case 4:
			tbc = am18g.getAisMessage();
			break;
		case 5:
			tbc = am19g.getAisMessage();
			break;
		case 6:
			tbc = eam19g.getAisMessage();
			break;
		case 7:
			tbc = am24ag.getAisMessage();
			break;
		case 8:
			tbc = eam24ag.getAisMessage();
			break;
		default:
			assert (false);
			tbc = null;
			break;
		}

	}

	public String[] getInputs() {
		PayLoad pl = new PayLoad(tbc.getBitVector());

		String start = "AIVDM,1,1,,A," + pl.getAsciiString() + "," + pl.getFillBits();
		int checksum = VDMLine.calculateCheckSum(start);

		String checksumString = Integer.toHexString(checksum).toUpperCase();
		if (checksumString.length() == 1)		//Have to add leading '0' since toHexString has no extra leading 0s.
		{
			checksumString = '0' + checksumString;
		}

		return new String[]{"!"+start+"*"+checksumString};
	}

	public Map<String, Object> getExpectedOutputs() {
		return tbc.getExpectedOutputs();
	}

	public List<Annotation> getExpectedAnnotations() {
		return tbc.getExpectedAnnotations();
	}

}
