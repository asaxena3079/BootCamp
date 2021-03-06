package org.demo;

import org.demo.ParkingLot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingAttendant implements ParkingLotObserver{

    Map<ParkingLot,Integer> parkingLotMap = new HashMap<>();

    public void addParkingLot(ParkingLot parkingLot)
    {
        parkingLotMap.put(parkingLot, (int)parkingLot.getTotalCapacity());
    }

    public  Token parkCar(Car car)
    {
        ParkingLot parkingLot = getMaxCapacityParkingLot();
        for (Map.Entry<ParkingLot,Integer> entry : parkingLotMap.entrySet())
        {
            if(entry.getValue()>0 && entry.getKey().equals(parkingLot))
            {
                int token = entry.getKey().park(car);
                return new Token(entry.getKey(),token);
            }
        }
        throw new ParkingFullException("No Space in any parking lot");
    }

    public Car unPark(Token token)
    {
        ParkingLot parkingLot = token.getParkingLot();
        int tokenNo = token.getTokenNo();

        for (Map.Entry<ParkingLot,Integer> entry : parkingLotMap.entrySet())
        {
            if(parkingLot.equals(entry.getKey()))
            {
                return entry.getKey().unpark(tokenNo);
            }
        }
        throw new CarNotFoundException("Car Not Found Exception");
    }

    @Override
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
