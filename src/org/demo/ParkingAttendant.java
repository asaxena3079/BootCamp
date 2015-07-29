package org.demo;

import org.demo.ParkingLot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingAttendant implements ParkingLotObserver{

    Map<ParkingLot,Boolean> parkingLotMap = new HashMap<>();

    public void addParkingLot(ParkingLot parkingLot)
    {
        parkingLotMap.put(parkingLot, true);
    }

    public  Token parkCar(Car car)
    {
        ParkingLot parkingLot = getMaxCapacityParkingLot();
        for (Map.Entry<ParkingLot,Boolean> entry : parkingLotMap.entrySet())
        {
            if(entry.getValue()==true && entry.getKey().equals(parkingLot))
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

        for (Map.Entry<ParkingLot,Boolean> entry : parkingLotMap.entrySet())
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
        if(notify==NotificationStatus.FULL)
        {
            for (Map.Entry<ParkingLot,Boolean> entry : parkingLotMap.entrySet())
            {
                if(entry.getKey().getName().equals(name))
                {
                    entry.setValue(false);
                }
            }
        }
        else if(notify==NotificationStatus.AVAILABLE)
        {
            for (Map.Entry<ParkingLot,Boolean> entry : parkingLotMap.entrySet())
            {
                if(entry.getKey().getName().equals(name))
                {
                    entry.setValue(true);
                }
            }
        }



    }

    public ParkingLot getMaxCapacityParkingLot()
    {
        ParkingLot parkingLot= new ParkingLot("Random",0,new ParkingAttendant());

        for (Map.Entry<ParkingLot,Boolean> entry : parkingLotMap.entrySet())
        {
            if(entry.getValue()==true)
            {
                if(entry.getKey().getRemainingCapacity()>parkingLot.getRemainingCapacity())
                {
                    parkingLot = entry.getKey();
                }
            }
        }

        return  parkingLot;
    }

}
