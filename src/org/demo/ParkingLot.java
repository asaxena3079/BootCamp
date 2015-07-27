package org.demo;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {

    private ParkingLotOwner parkingLotOwner;
    private int totalCapacity;
    private int countCapacity=0;

    public ParkingLot(int totalCapacity,ParkingLotOwner parkingLotOwner)
    {
        this.totalCapacity = totalCapacity;
        this.parkingLotOwner = parkingLotOwner;
    }

    Map<Integer,Car> parkingMap = new HashMap<Integer,Car>();

    //Park the car along with the check of parking full and notify Owner on full parking
    public int park(Car car)
    {
        if(parkingMap.containsValue(car))
            throw new CarAlreadyPresent("Car Present");

        if(isParkingAvailable())
        {
            countCapacity++;
            parkingMap.put(countCapacity,car);
            if(isParkingFull())
            {
                parkingLotOwner.onFull();
            }
            return countCapacity;
        }
        else
        {
            throw new ParkingFullException("Parking Full");
        }
    }

    //Check whether parking is available or not
    public boolean isParkingAvailable()
    {
        return parkingMap.size() < totalCapacity;
    }

    //Check whether the parking is full
    public boolean isParkingFull()
    {
        return parkingMap.size() == totalCapacity;
    }

    //Unpark the car and notify Owner after vacancy creation
    public Car unpark(int token)
    {
        if(parkingMap.containsValue(parkingMap.get(token)))
        {
            if(isParkingFull())
            {
                parkingLotOwner.onVacancy();
            }
            Car car =  parkingMap.get(token);
            parkingMap.remove(token);
            return car;
        }
        else
        {
            throw new CarNotFoundException("Car Not Found");
        }
    }

}
