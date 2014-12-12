package nl.esi.metis.testgenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
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
import nl.esi.metis.aisparser.provenance.AISMessageProvenance;
import nl.esi.metis.aisparser.provenance.Provenance;

import org.apache.commons.lang3.StringUtils;

public class TestGenerator {
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
		
		FileWriter fstream = new FileWriter("FailedTests.txt");
		BufferedWriter out = new BufferedWriter(fstream);

		for (long i = 0; i < 1000000L; i ++)
		{
			//SetUp
			BlackBoxTest bbt = new BlackBoxTest ();
			//Execute
			AISMessage am = ExecuteTest(bbt.getInputs());
			//Verify
			if (am == null)
			{
				String message = "Test: " + i + " failed:\n"+
						"\tInput: {\"" +  StringUtils.join(bbt.getInputs(), "\";\"") + "\"}\n";
				writeError (out, message);
			} else
			{
				//System.out.println(am.toString());
				
				for (String key : bbt.getExpectedOutputs().keySet())
				{
					try {
						Object actual = am;
						String methods[] = key.split("\\.");
						for (String m : methods)
						{
							Method method = actual.getClass().getMethod(m);
							method.setAccessible(true);
							actual = method.invoke(actual);	//include "this" argument
						}
						Object expected = bbt.getExpectedOutputs().get(key);
						if (! expected.equals(actual))
						{
							String message = "Test: " + i + " failed for " + key + "() :\n"+
									"\tInput: {\"" +  StringUtils.join(bbt.getInputs(), "\";\"") + "\"}\n" +
									"\tExpected : " + expected + "\n" +
									"\tActual   : " + actual + "\n";
				
							writeError(out, message);
							
						}
					} catch (Exception e) {
						String message = "Test: " + i + " invoke "+ key +" failed:\n"+
								"\tInput: {\"" +  StringUtils.join(bbt.getInputs(), "\";\"") + "\"}\n";
						writeError (out, message);
												
						e.printStackTrace();
					} 
				}
				if (am.getProvenance().getAnnotations().size() == bbt.getExpectedAnnotations().size())
				{
					for(Annotation expected : bbt.getExpectedAnnotations())
					{
						if (!hasAnnotation(am, expected))
						{
							StringBuilder message = new StringBuilder ("Test: " + i + " failed for annotation :\n"+
									"\tInput: {\"" +  StringUtils.join(bbt.getInputs(), "\";\"") + "\"}\n" +
									"\tExpected Annotation: " + expected + "\n" +
									"\t Actual Annotations:\n");
							
							for(Annotation a: am.getProvenance().getAnnotations())
							{
								message.append(" * " + a + "\n");
							}
							
							writeError (out, message.toString());
						}
					}
				}
				else
				{
					StringBuilder message = new StringBuilder("Test: " + i + " failed different number of annotations :\n"+
							"\tInput: {\"" +  StringUtils.join(bbt.getInputs(), "\";\"") + "\"}\n" +
							"\tNumber of Expected : " + bbt.getExpectedAnnotations().size() + "\n" +
							"\tNumber of Actual   : " + am.getProvenance().getAnnotations().size() + "\n" +
							"\tExpected :\n");
					
					for(Annotation a: bbt.getExpectedAnnotations())
					{
						message.append(" * " + a + "\n");
					}
					
					message.append("\tActual :\n");
					
					for(Annotation a: am.getProvenance().getAnnotations())
					{
						message.append(" * " + a + "\n");
					}		
		
					writeError(out, message.toString());
			
				}
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
	
	private static boolean hasAnnotation(AISMessage message, Annotation expected)
	{
		boolean retval = false;
		final AISMessageProvenance provenance = message.getProvenance();
		final List<Annotation> annotations = provenance.getAnnotations(); 
		for(Annotation annotation : annotations)
		{
			retval |= annotation.equals(expected);
		}
		return retval;
	}
}