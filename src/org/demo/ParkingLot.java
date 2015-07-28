package org.demo;

import java.util.*;

public class ParkingLot {

    private ParkingLotObserver parkingLotObserver;
    private int totalCapacity;
    private int countCapacity=0;
    List<ParkingLotObserver> lst = new ArrayList<ParkingLotObserver>();


    public ParkingLot(int totalCapacity,ParkingLotObserver parkingLotObserver)
    {
        this.totalCapacity = totalCapacity;
        this.parkingLotObserver = parkingLotObserver;
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
                parkingLotObserver.onFull();

                Iterator<ParkingLotObserver> it = lst.iterator();

                while(it.hasNext())
                {
                    it.next().onFull();
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
                parkingLotObserver.onVacancy();

                Iterator<ParkingLotObserver> it = lst.iterator();

                while(it.hasNext())
                {
                    it.next().onVacancy();
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
    public boolean registerAgent(ParkingLotObserver fbiAgent)
    {
        lst.add(fbiAgent);
        return true;
    }

    //Delete an existin agent
    public boolean removeAgent(ParkingLotObserver fbiAgent)
    {
        lst.remove(fbiAgent);
        return true;
    }


}
