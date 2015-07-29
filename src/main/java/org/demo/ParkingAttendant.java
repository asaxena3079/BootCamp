package org.demo;

import java.util.HashMap;
import java.util.Map;

public class ParkingAttendant implements ParkingLotObserver{

    Map<ParkingLot,Integer> parkingLotMap = new HashMap<ParkingLot,Integer>();

    ParkingLotSelectionStrategy parkingLotSelectionStrategy;

    public ParkingAttendant(ParkingLotSelectionStrategy parkingLotSelectionStrategy)
    {
        this.parkingLotSelectionStrategy = parkingLotSelectionStrategy;
    }

    public void addParkingLot(ParkingLot parkingLot)
    {
        parkingLotMap.put(parkingLot, (int)parkingLot.getTotalCapacity());
    }

    public  Token parkCar(Car car)
    {
        ParkingLot parkingLot = null;
        if(this.parkingLotSelectionStrategy.apply()==NotificationStatus.MAXFREESPACE)
        {
             parkingLot = getMaxFreeSpaceParkingLot();
        }
        else if(this.parkingLotSelectionStrategy.apply()==NotificationStatus.MAXCAPACITY)
        {
            parkingLot = getMaxCapcityParkingLot();
        }
        return new Token(parkingLot,parkingLot.park(car));
    }

    public Car unPark(Token token)
    {
        return token.getParkingLot().unpark(token.getTokenNo());
    }

    public void notified(NotificationStatus notify, String name)
    {
        if(notify==NotificationStatus.PARK)
        {
            for (Map.Entry<ParkingLot,Integer> entry : parkingLotMap.entrySet())
            {
                if(entry.getKey().getName().equals(name))
                {
                    entry.setValue(entry.getValue()-1);
                }
            }
        }
        else if(notify==NotificationStatus.UNPARK)
        {
            for (Map.Entry<ParkingLot,Integer> entry : parkingLotMap.entrySet())
            {
                if(entry.getKey().getName().equals(name))
                {
                    entry.setValue(entry.getValue()+1);
                }
            }
        }
    }

    public ParkingLot getMaxFreeSpaceParkingLot()
    {
        ParkingLot parkingLotWithMaxFreeSpace= null;
        Integer maxFreeSize = 0;

        for (Map.Entry<ParkingLot,Integer> entry : parkingLotMap.entrySet())
        {
            if(entry.getValue()>maxFreeSize)
            {
                maxFreeSize = entry.getValue();
                parkingLotWithMaxFreeSpace = entry.getKey();
            }
        }

        if(maxFreeSize==0)
            throw new ParkingFullException("Parking is Full");
        else
            return  parkingLotWithMaxFreeSpace;
    }

    public ParkingLot getMaxCapcityParkingLot()
    {
        ParkingLot parkingLotWithMaxCapcity= null;
        Integer maxCapacitySize = 0;

        for (Map.Entry<ParkingLot,Integer> entry : parkingLotMap.entrySet())
        {
            if(entry.getKey().getTotalCapacity()>maxCapacitySize && entry.getValue()>0)
            {
                maxCapacitySize = entry.getValue();
                parkingLotWithMaxCapcity = entry.getKey();
            }
        }

        if(maxCapacitySize==0)
            throw new ParkingFullException("Parking is Full");
        else
            return  parkingLotWithMaxCapcity;
    }
}
