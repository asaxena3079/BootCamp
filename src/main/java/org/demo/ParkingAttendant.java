package org.demo;

import org.demo.ParkingLot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingAttendant implements ParkingLotObserver{

    Map<ParkingLot,Integer> parkingLotMap = new HashMap<ParkingLot,Integer>();

    public void addParkingLot(ParkingLot parkingLot)
    {
        parkingLotMap.put(parkingLot, (int)parkingLot.getTotalCapacity());
    }

    public  Token parkCar(Car car)
    {
        ParkingLot parkingLot = getMaxCapacityParkingLot();
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

    public ParkingLot getMaxCapacityParkingLot()
    {
        ParkingLot parkingLot= null;
        Integer maxFreeSize = 0;

        for (Map.Entry<ParkingLot,Integer> entry : parkingLotMap.entrySet())
        {
            if(entry.getValue()>maxFreeSize)
            {
                maxFreeSize = entry.getValue();
                parkingLot = entry.getKey();
            }
        }

        if(maxFreeSize==0)
            throw new ParkingFullException("Parking is Full");
        else
            return  parkingLot;
    }

}
