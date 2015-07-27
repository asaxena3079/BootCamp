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

    Map<Integer,Car> parkingMap = new HashMap<Integer,Car>();

    public int park(Car car)
    {
        if(parkingMap.containsValue(car))
            throw new CarAlreadyPresent("Car Present");

        if(isParkingAvailable())
        {
            countCapacity++;
            parkingMap.put(countCapacity,car);
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


    public Car unpark(int slotNo) {
        if(parkingMap.containsValue(parkingMap.get(slotNo)))
        {
            Car car =  parkingMap.get(slotNo);
            parkingMap.remove(slotNo);
            return car;
        }
        else
            throw new CarNotFoundException("Car Not Found");
    }
}
