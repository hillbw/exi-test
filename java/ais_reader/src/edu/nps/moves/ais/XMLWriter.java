/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.nps.moves.ais;

import java.io.*;
import nl.esi.metis.aisparser.*;

/**
 *
 * @author DMcG
 */
public class XMLWriter 
{
    public static XMLWriter xmlWriter = null;
    private static final String fileName = "/Users/johngalt/Desktop/positionReports.xml";
    private PrintWriter pw = null;
    
    public static XMLWriter getXMLWriter()
    {
        if(xmlWriter == null)
        {
            xmlWriter = new XMLWriter();
        }
        
        return xmlWriter;
    }
    
    private XMLWriter()
    {
        try
        {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
        }
        catch (Exception e)
        {
            System.out.println("Can't create output file " + fileName + " exiting");
            System.exit(0);
        }
        finally
        {
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
            pw.println("<positionReports>");
        }
    }
    
    @Override
    protected void finalize() throws Throwable
    {
        pw.println("</positionReports>");
        super.finalize();
    }
    
    public void writePositionReport(AISMessagePositionReport positionReport)
    {
        int messageId = positionReport.getMessageID();
        int repeatIndicator = positionReport.getRepeatIndicator();
        int userId = positionReport.getUserID(); //MMSI
        int navStatus = positionReport.getNavigationalStatus();
        int rateOfTurn = positionReport.getRateOfTurn();
        int speedOverGround = positionReport.getSpeedOverGround();
        boolean positionAccuracy = positionReport.getPositionAccuracy();
        double longitude = positionReport.getLongitudeInDegrees();
        double latitude = positionReport.getLatitudeInDegrees();
        int courseOverGround = positionReport.getCourseOverGround();
        int trueHeading = positionReport.getTrueHeading();
        int timeStamp = positionReport.getTimeStamp();
        int specialManoeuvre = positionReport.getSpecialManoeuvre();

        pw.println("\t<positionReport " +
                      "messageId=\"" + messageId + "\" " +
                      "repeatIndicator=\"" + repeatIndicator + "\" " +
                      "userId=\"" + userId + "\" " +
                      "navStatus=\"" + navStatus + "\" " +
                      "rateOfTurn=\"" + rateOfTurn + "\" " +
                      "speedOverGround=\"" + speedOverGround + "\" " +
                      "positionAccuracy=\"" + positionAccuracy + "\" " +
                      "longitude=\"" + longitude + "\" " +
                      "latitude=\"" + latitude + "\" " +
                      "courseOverGround=\"" + courseOverGround + "\" " +
                      "trueHeading=\"" + trueHeading + "\" " +
                      "timeStamp=\"" + timeStamp + "\" " +
                      "specialManoeuvre=\"" + specialManoeuvre + "\"" +
                      "/>" );
    }

}
