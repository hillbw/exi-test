/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.nps.moves.ais;

import nl.esi.metis.aisparser.*;
import nl.esi.metis.aisparser.provenance.*;
/**
 *
 * @author DMcG
 */
public class AisErrorHandler implements HandleInvalidInput
{

    @Override
    public void handleInvalidVDMMessage(VDMMessage invalidVDMMessage)
    {
        System.err.println("Invalid VDM Message");
    }
    
    @Override
    public void	handleInvalidVDMLine(VDMLine invalidVDMLine) 
    {
        System.err.println("Invalid VDM Line");
    }
    
    @Override
    public void	handleInvalidSensorData(Provenance source, String sensorData) 
    {
        System.err.println("Invalid sensor data");
    }
    
}
