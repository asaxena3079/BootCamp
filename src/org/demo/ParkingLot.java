package org.demo;

import java.util.*;

public class ParkingLot {

    private ParkingLotObserver parkingLotObserver;
    private int totalCapacity;
    private int countCapacity=0;
    List<ParkingLotObserver> observers = new ArrayList<ParkingLotObserver>();


    public ParkingLot(int totalCapacity,ParkingLotObserver parkingLotObserver)
    {
        this.totalCapacity = totalCapacity;
        this.parkingLotObserver = parkingLotObserver;
    }

    Map<Integer,Car> parkingMap = new HashMap<>();

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
                parkingLotObserver.notified(NotificationStatus.FULL);

                for (ParkingLotObserver observer : observers) {
                    observer.notified(NotificationStatus.FULL);
                }
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
                parkingLotObserver.notified(NotificationStatus.AVAILABLE);
                for (ParkingLotObserver observer : observers) {
                    observer.notified(NotificationStatus.AVAILABLE);
                }
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

    //Reister new Agent
    public boolean subscribeAgent(ParkingLotObserver fbiAgent)
    {
        observers.add(fbiAgent);
        return true;
    }

    //Delete an existin agent
    public boolean removeAgent(ParkingLotObserver fbiAgent)
    {
        observers.remove(fbiAgent);
        return true;
    }


}
