package org.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {

    private int totalCapacity;
    private int countCapacity=0;

    public ParkingLot(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public int getCountCapacity() {
        return countCapacity;
    }

    Map<Car,Integer> parkingMap = new HashMap<Car,Integer>();

    public int park(Car car)
    {
        if(isParkingAvailable())
        {
            countCapacity++;
            parkingMap.put(car,countCapacity);
            return countCapacity;
        }
        else
            throw new ParkingFullException("Parking Full");
    }

    public boolean isParkingAvailable()
    {
        if(parkingMap.size()<totalCapacity)
            return true;
        else
            return false;

    }

}
