package nl.esi.metis.testgenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nl.esi.metis.aisparser.AISMessage;
import nl.esi.metis.aisparser.HandleAISMessage;
import nl.esi.metis.aisparser.HandleInvalidInput;
import nl.esi.metis.aisparser.HandleSensorData;
import nl.esi.metis.aisparser.HandleVDMLine;
import nl.esi.metis.aisparser.HandleVDMMessage;
import nl.esi.metis.aisparser.VDMLine;
import nl.esi.metis.aisparser.VDMMessage;
import nl.esi.metis.aisparser.annotations.Annotation;
import nl.esi.metis.aisparser.impl.HandleSensorDataImpl;
import nl.esi.metis.aisparser.impl.HandleVDMLineImpl;
import nl.esi.metis.aisparser.impl.HandleVDMMessageImpl;
import nl.esi.metis.aisparser.provenance.Provenance;

import org.apache.commons.lang3.StringUtils;

public class CompleteRandomTestGenerator {
	private static int count = 0;
	private static AISMessage am;
	public static AISMessage ExecuteTest(String[] lines) {
		am = null;
		
		for (String line : lines) {
			System.out.println(count/1000.0 + " " + line);
			count ++;
		}
		
		HandleInvalidInput invalidInputHandler = new HandleInvalidInput() {
			public void handleInvalidVDMMessage(VDMMessage invalidVDMMessage) {
				System.err.println("Error VDM Message : "+ invalidVDMMessage.getProvenance().getProvenanceTree(""));
			}
			public void handleInvalidVDMLine(VDMLine invalidVDMLine) {
				System.err.println("Error VDM Line : "+ invalidVDMLine.getProvenance().getProvenanceTree(""));
			}
			public void handleInvalidSensorData(Provenance source, String sensorData) {
				System.err.println("Error sensor data : "+ sensorData);
			}
		};
		
		HandleAISMessage aisMessageHandler = new HandleAISMessage() {
			public void handleAISMessage(AISMessage message) {
				am = message;
			}
		};
		HandleVDMMessage aivdmMessage = new HandleVDMMessageImpl(aisMessageHandler, invalidInputHandler);
		HandleVDMLine aivdmLine = new HandleVDMLineImpl(aivdmMessage, invalidInputHandler);
		HandleSensorData sensorData = new HandleSensorDataImpl(aivdmLine, invalidInputHandler);
		Provenance provenance = new Provenance() {
			public double getTime() {
				return 0;
			}
			public String getProvenanceTree(String layout) {
				return layout + "root";
			}
			public List<Annotation> getAnnotations() {
				return new ArrayList<Annotation>();
			}
		};
		for (String line : lines) {
			sensorData.handleSensorData(provenance, line);
		}
		return am;
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		FileWriter fstream = new FileWriter("FailedTestsRandom.txt");
		BufferedWriter out = new BufferedWriter(fstream);

		for (long i = 0; i < 1000000L; i ++)
		{
			//SetUp
			CompleteRandomTest bbt = new CompleteRandomTest();
			//Execute
			AISMessage am = ExecuteTest(bbt.getInputs());
			//Verify
			if (am == null)
			{
				String message = "Test: " + i + " failed:\n"+
						"\tInput: {\"" +  StringUtils.join(bbt.getInputs(), "\";\"") + "\"}\n";
				writeError (out, message);
			} 
			//TearDown
		}
		out.close();
	}

	private static void writeError(BufferedWriter out, String message)
			throws IOException {
		out.write(message);
		System.err.println(message);
	}	
}