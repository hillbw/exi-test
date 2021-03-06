package nl.esi.metis.aisparser.impl;

import nl.esi.metis.aisparser.AISMessage;
import nl.esi.metis.aisparser.HandleAISMessage;
import nl.esi.metis.aisparser.HandleInvalidSensorData;
import nl.esi.metis.aisparser.HandleInvalidVDMLine;
import nl.esi.metis.aisparser.HandleInvalidVDMMessage;
import nl.esi.metis.aisparser.HandleSensorData;
import nl.esi.metis.aisparser.HandleVDMLine;
import nl.esi.metis.aisparser.HandleVDMMessage;
import nl.esi.metis.aisparser.ReadPath;
import nl.esi.metis.aisparser.VDMLine;
import nl.esi.metis.aisparser.VDMMessage;
import nl.esi.metis.aisparser.impl.HandleSensorDataImpl;
import nl.esi.metis.aisparser.impl.HandleVDMLineImpl;
import nl.esi.metis.aisparser.impl.HandleVDMMessageImpl;
import nl.esi.metis.aisparser.provenance.Provenance;

public class HandleVDMMessageTestMain {

	/** This is a simple example of how the {@link HandleSensorData} class can be used.
	 * This method just counts the correct and incorrect lines.
	 * For this purpose it uses a simple private handler class.
	 * @param args a file to be parsed, or a directory of files to be parsed
	 */
	public static void main(String[] args) {
		MyHandler myHandler = new MyHandler();
		HandleVDMMessage msgHandler = new HandleVDMMessageImpl(myHandler, myHandler);
		HandleVDMLine lineHandler = new HandleVDMLineImpl(msgHandler, myHandler);
		HandleSensorData sensorDataHandler = new HandleSensorDataImpl(lineHandler, myHandler);
		
		if (args.length ==1)
		{
			String aisPath = args[0];
			ReadPath rp = new ReadPath();
			rp.readPath(aisPath, sensorDataHandler);

			System.out.println("AIS Messages = " + myHandler.getNrOfAISMessages());
			System.out.println("IncorrectSensorData = " + myHandler.getNrOfIncorrectSensorData());
			System.out.println("IncorrectVDMLines = " + myHandler.getNrOfIncorrectVDMLines());
			System.out.println("IncorrectVDMMessages = " + myHandler.getNrOfIncorrectVDMMessages());		
		}
		else
		{
			System.out.println("This test program needs exactly one parameter: the path of either a directory containing ais-nmea files or a single ais-nmea file.");
			System.out.println();
			System.out.println("See for an example the ais-nmea file src/test/resources/example.nmea of this project.");
		}
	}
	
	/** This private class illustrates how an AIS Message, Invalid VDM Message, InvalidVDMLine, and InvalidSensorData handler can be defined.
	 * @author Pierre van de Laar
	 */
	static private class MyHandler implements HandleAISMessage, HandleInvalidVDMMessage, HandleInvalidVDMLine, HandleInvalidSensorData {
		private long nrOfAISMessages  = 0;
		public long getNrOfAISMessages() { return nrOfAISMessages;}

		private long nrOfIncorrectSensorData  = 0;
		public long getNrOfIncorrectSensorData() { return nrOfIncorrectSensorData;}

		private long nrOfIncorrectVDMLines  = 0;
		public long getNrOfIncorrectVDMLines() { return nrOfIncorrectVDMLines;}

		private long nrOfIncorrectVDMMessages  = 0;
		public long getNrOfIncorrectVDMMessages() { return nrOfIncorrectVDMMessages;}

		@Override
		public void handleInvalidSensorData(Provenance p, String line) {
			nrOfIncorrectSensorData ++;
		}

		@Override
		public void handleInvalidVDMLine(VDMLine invalidVDMLine) {
			nrOfIncorrectVDMLines++;
		}

		@Override
		public void handleInvalidVDMMessage(VDMMessage message) {
			nrOfIncorrectVDMMessages++;
		}

		@Override
		public void handleAISMessage(AISMessage message) {
			nrOfAISMessages++;
		}
	}

}
