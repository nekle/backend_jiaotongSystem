package com.bjtu.websystem.common;

import com.bjtu.websystem.common.utils.GPS;
import com.bjtu.websystem.common.utils.GPSConverterUtils;

public class testConvertion {
    public static void main(String[] args){
         GPS myGPS = new GPS(23.8312,117.4879);
         GPS newGPS =  GPSConverterUtils.gps84_To_Gcj02(myGPS.getLat(),myGPS.getLon());
         System.out.println(
                "oldGPS" + myGPS.toString() + "newGPS "+newGPS.toString()
                 +"float GPS" + "lat:" + (float)newGPS.getLat() + "lon:" + (float)newGPS.getLon()
         );
    }
}
