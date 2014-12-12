/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.nps.moves.ais;

import nl.esi.metis.aisparser.*;

/**
 *
 * @author DMcG
 */
public class AisMessageHandler implements HandleAISMessage
{
    private XMLWriter xmlWriter;
    private int positionReportsRead = 0;
            
    public AisMessageHandler(){
        xmlWriter = XMLWriter.getXMLWriter();
    }
    
    public int getPositionReportsRead()
    {
        return positionReportsRead;
    }
    
    public void handleAISMessage(AISMessage message)
    {   
        switch(message.getMessageID())
        {
            case 3:
                System.err.println("Writing positionReport " + positionReportsRead + ".");
                AISMessagePositionReport positionReport = (AISMessagePositionReport)message;
                
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
                
                String line = "<positionReport " +
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
                              "/>";
                System.out.println(line);
                positionReportsRead++;    
                break;
            
            default:
                break;
        }
    }
}
