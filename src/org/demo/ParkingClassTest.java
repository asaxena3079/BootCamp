package org.demo;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParkingClassTest {

    ParkingLot parkingLot;

    @Test
    public void testParkWhenParkingAvailable() throws Exception {

        parkingLot = new ParkingLot(10);
        int slotNo = parkingLot.park(new Car(1));
        int slotNo2 = parkingLot.park(new Car(2));

        assertEquals(1, slotNo);
        assertEquals(2, slotNo2);
     }

    @Test(expected = ParkingFullException.class)
    public void testParkWhenParkingNotAvailable() throws Exception{

        parkingLot = new ParkingLot(2);
        parkingLot.park(new Car(1));
        parkingLot.park(new Car(2));
        parkingLot.park(new Car(3));
    }

    @Test
    public void testUnparkWhenCarAvailable() throws Exception{

        parkingLot = new ParkingLot(10);
        Car car1 = new Car(1);
        Car car2 = new Car(2);
        int slotNo = parkingLot.park(car1);
        int slotNo2 = parkingLot.park(car2);


        assertEquals(car1, parkingLot.unpark(slotNo));

    }

    @Test(expected = CarNotFoundException.class)
    public void testUnparkWhenCarNotAvailable() throws Exception{

        parkingLot = new ParkingLot(10);
        Car car1 = new Car(1);
        int slotNo = parkingLot.park(car1);
        int slotNo2 = 2;
        parkingLot.unpark(slotNo2);

    }

    @Test(expected = CarAlreadyPresent.class)
    public void testIsCarAlreadyPresent()
    {
        parkingLot = new ParkingLot(10);
        Car car = new Car(1);
        parkingLot.park(car);
        parkingLot.park(car);
    }



}