package nl.esi.metis.aisparser.impl;

import nl.esi.metis.aisparser.HandleInvalidSensorData;
import nl.esi.metis.aisparser.HandleSensorData;
import nl.esi.metis.aisparser.HandleVDMLine;
import nl.esi.metis.aisparser.ReadPath;
import nl.esi.metis.aisparser.VDMLine;
import nl.esi.metis.aisparser.impl.HandleSensorDataImpl;
import nl.esi.metis.aisparser.provenance.Provenance;

public class HandleSensorDataTestMain {

	/** This is a simple example of how the {@link HandleSensorData} class can be used.
	 * This method just counts the correct and incorrect lines.
	 * For this purpose it uses a simple private handler class.
	 * @param args a file to be parsed, or a directory of files to be parsed
	 */
	public static void main(String[] args) {
		MyHandler myHandler = new MyHandler();
		HandleSensorData lineHandler = new HandleSensorDataImpl(myHandler, myHandler);
		
		if (args.length ==1)
		{
			String aisPath = args[0];
			ReadPath rp = new ReadPath();
			rp.readPath(aisPath, lineHandler);

			System.out.println("CorrectSensorData = " + myHandler.getNrOfCorrectSensorData());
			System.out.println("SensorData According To Standard = " + myHandler.getNrOfStandardSensorData());
			System.out.println("IncorrectSensorData = " + myHandler.getNrOfIncorrectSensorData());
		}
		else
		{
			System.out.println("This test program needs exactly one parameter: the path of either a directory containing ais-nmea files or a single ais-nmea file.");
			System.out.println();
			System.out.println("See for an example the ais-nmea file src/test/resources/example.nmea of this project.");
		}
		

	}
	
	/** This private class illustrates how an VDMLine handler and InvalidSensorData handler can be defined.
	 * @author Pierre van de Laar
	 */
	static private class MyHandler implements HandleVDMLine, HandleInvalidSensorData {
		private long nrOfCorrectSensorData  = 0;
		public long getNrOfCorrectSensorData() { return nrOfCorrectSensorData;}

		private long nrOfStandardSensorData  = 0;
		public long getNrOfStandardSensorData() { return nrOfStandardSensorData;}

		private long nrOfIncorrectSensorData  = 0;
		public long getNrOfIncorrectSensorData() { return nrOfIncorrectSensorData;}
		
		@Override
		public void handleInvalidSensorData(Provenance p, String line) {
			nrOfIncorrectSensorData ++;
		}

		@Override
		public void handleVDMLine(VDMLine line) {
			nrOfCorrectSensorData ++;
			if (line.getProvenance().getAnnotations().size() == 0)
			{
				nrOfStandardSensorData++;
			}
		}

		@Override
		public void finished() {
		}
	}

}
