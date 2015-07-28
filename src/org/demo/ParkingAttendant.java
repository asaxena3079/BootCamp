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

    public  String parkCar(Car car)
    {
        String attendantToken = null;
        for (Map.Entry<ParkingLot,Boolean> entry : parkingLotMap.entrySet())
        {
            if(entry.getValue()==true)
            {
                int token = entry.getKey().park(car);
                attendantToken = entry.getKey().getName() + "-" + token;
                return attendantToken;
            }
        }
        throw new ParkingFullException("No Space in any parking lot");
    }

    public Car unPark(String attendantToken)
    {
        String[] name_token = attendantToken.split("-");
        String nameParkingLot = name_token[0];
        int token = Integer.parseInt(name_token[1]);

        for (Map.Entry<ParkingLot,Boolean> entry : parkingLotMap.entrySet())
        {
            if(entry.getKey().getName().equals(nameParkingLot))
            {
                return entry.getKey().unpark(token);
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

}
