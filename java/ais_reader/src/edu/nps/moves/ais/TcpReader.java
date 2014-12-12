
package edu.nps.moves.ais;

import java.net.*;
import java.io.*;
import java.util.*;


import nl.esi.metis.aisparser.*;
import nl.esi.metis.aisparser.provenance.*;

import org.apache.commons.cli.*;


/**
 *
 * @author DMcG
 */
public class TcpReader 
{
    private InetAddress address = null;
    private int port;
    private Socket socket = null;
    private boolean breakOutOfReadLoop = false;
    AisMessageHandler aisMessageHandler = null;
    AisErrorHandler aisErrorHandler = null;
    AISParser aisParser = null;
    private int maxPositionReports = Defaults.NUMBER_POSREPS;
    
    public TcpReader(InetAddress address, int port, int n)
    {
        this.address = address;
        this.port = port;
        this.maxPositionReports = n;
        aisMessageHandler = new AisMessageHandler();
        aisErrorHandler = new AisErrorHandler();
        
        this.aisParser = new AISParser(aisMessageHandler, aisErrorHandler);
    }
    
    public void establishConnection()
    {
        try
        {
            socket = new Socket(address, port);
            socket.setSoTimeout(30000);
        }
        catch (Exception e)
        {
            System.err.println("Failed to establish TCP connection to AIS server");
            System.err.println(e);
        }
    }
    
    public void readData()
    {
        XMLWriter writer = XMLWriter.getXMLWriter();
        int lineNumber = 0;
        try
        {
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
            System.out.println("<positionReports>");
            
            while(aisMessageHandler.getPositionReportsRead() < maxPositionReports)
            {
                String aisLine = socketReader.readLine();
                Provenance provenance = new FileSource(null, lineNumber,  aisLine, System.currentTimeMillis());
                aisParser.handleSensorData(provenance, aisLine);
                lineNumber++;
            }
        }
        catch(Exception e)
        {
            System.err.println("Error reading Provenance from socket. " + e);
        }
        finally
        {
            System.out.println("</positionReports>");
        }
        
    }
    
    /**
     * Uses Apache commons command line library.
     * 
     * Command line args:
     *  -h, --host: IP of host with the AIS feed
     *  -p, --port: TCP port number
     *  -t, --time: Length of time to run
     *  -n, --numreps: Number of position reports to read
     * @param args 
     */
    public static void main(String[] args)
    {
        String host = Defaults.AIS_HOST;
        int portInt = Defaults.PORT;
        int listenTime = Defaults.LISTEN_TIME_SECONDS;
        int n = Defaults.NUMBER_POSREPS;
        
        InetAddress hostIp = null;
        
        Options options = new Options();
        options.addOption("a", "ais-server", true, "IP of host with TCP AIS service" );
        options.addOption("p", "port", true, "TCP port on AIS host to connect to");
        options.addOption("n", "numreps", true, "Set number of position reports to collect");
        options.addOption("t", "time", true, "Length of time to collect data, in seconds");
        options.addOption("h", "help", false, "Help");
        
        
        
        try
        {
            CommandLineParser parser = new PosixParser();
            CommandLine cmd = parser.parse( options, args);
                        
            if(cmd.hasOption("a"))
            {
                host = cmd.getOptionValue('a');
            }
            
            if(cmd.hasOption('p'))
            {
                portInt = Integer.parseInt(cmd.getOptionValue('p'));
            }
            
            if(cmd.hasOption('t'))
            {
                listenTime = Integer.parseInt(cmd.getOptionValue('t'));
            }
            
            if(cmd.hasOption('n'))
            {
                n = Integer.parseInt(cmd.getOptionValue('n'));
            }
            
            if(cmd.hasOption('h'))
            {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp( "TcpReader", options );
                System.exit(0);
            }
            
        }
        catch(Exception e)
        {
            System.err.println(e);
        }
        
        // NPS default TCP address and port for AIS feed is 172.20.70.143:9010
        System.err.println("Connecting to AIS server at " + host + ", " + portInt);
        
        try
        {
            hostIp = InetAddress.getByName(host);
            System.err.println("Using IP address " + hostIp.toString());
        }
        catch(Exception e)
        {
            System.err.println(e);
        }
        
        try
        { 
            TcpReader reader = new TcpReader(hostIp, portInt, n);
            
//            Timer runLengthTimer = new Timer(true);
//            TimerTask tt = new TimerTask()
//                    {
//                        @Override
//                        public void run()
//                        {
//                            reader.breakOutOfReadLoop();
//                        };
//                    };
//            
//            runLengthTimer.schedule(tt, Defaults.LISTEN_TIME_SECONDS * 1000);
                    
                    
            reader.establishConnection();
            reader.readData();
        }
        catch(Exception e)
        {
            
        }
        
    }
    
    public void breakOutOfReadLoop()
    {
        breakOutOfReadLoop = true;
    }

}
