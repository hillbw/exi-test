/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.nps.moves.ais;

/**
 *
 * @author DMcG
 */
public class Defaults 
{
    /** Default port for AIS TCP feed on campus */
    public static int PORT = 9010;
    
    /** Host for AIS feed on campus */
    public static String AIS_HOST = "172.20.70.143";

    /** Default amount of time to collect data, in seconds, before exiting */
    public static int LISTEN_TIME_SECONDS = 30;
    
    /** Default number of position reports to read, before exiting */
    public static int NUMBER_POSREPS = 20;
}
