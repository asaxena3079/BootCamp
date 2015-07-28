package org.demo;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParkingAttendantTest {

    @Test
    public void testParkCar() throws Exception
    {
        ParkingAttendant parkingAttendant = new ParkingAttendant();

        ParkingLot parkingLot1 = new ParkingLot("A",2,parkingAttendant);
        ParkingLot parkingLot2 = new ParkingLot("B",2,parkingAttendant);

        parkingAttendant.addParkingLot(parkingLot1);
        parkingAttendant.addParkingLot(parkingLot2);



        Car car = new Car(1);
        String attendantToken = parkingAttendant.parkCar(car);

        assertEquals("B-1",attendantToken);

        assertEquals(car,parkingAttendant.unPark(attendantToken));
    }

    @Test
    public void testPaarkingWhenOneIsFull() throws Exception
    {
        ParkingAttendant parkingAttendant = new ParkingAttendant();

        ParkingLot parkingLot1 = new ParkingLot("A",2,parkingAttendant);
        ParkingLot parkingLot2 = new ParkingLot("B",2,parkingAttendant);

        parkingAttendant.addParkingLot(parkingLot1);
        parkingAttendant.addParkingLot(parkingLot2);

        Car car1 = new Car(1);
        Car car2 = new Car(2);
        Car car3 = new Car(3);

        assertEquals("B-1", parkingAttendant.parkCar(car1));
        assertEquals("B-2", parkingAttendant.parkCar(car2));
        assertEquals("A-1", parkingAttendant.parkCar(car3));

        parkingAttendant.unPark("B-1");
        assertEquals("B-3", parkingAttendant.parkCar(car1));


    }
}