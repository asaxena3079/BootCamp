package org.demo;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParkingAttendantTest {

    @Test
    public void testParkCar() throws Exception
    {
        ParkingAttendant parkingAttendant = new ParkingAttendant();

        ParkingLot parkingLot1 = new ParkingLot("A",2,parkingAttendant);
        ParkingLot parkingLot2 = new ParkingLot("B",5,parkingAttendant);

        parkingAttendant.addParkingLot(parkingLot1);
        parkingAttendant.addParkingLot(parkingLot2);



        Car car = new Car(1);
        Token token = new Token(parkingLot2,1);

        assertEquals(token,parkingAttendant.parkCar(car));
        assertEquals(car, parkingAttendant.unPark(token));
    }

    @Test
    public void testPaarkingWhenOneIsFull() throws Exception
    {
        ParkingAttendant parkingAttendant = new ParkingAttendant();

        ParkingLot parkingLot1 = new ParkingLot("A",5,parkingAttendant);
        ParkingLot parkingLot2 = new ParkingLot("B",2,parkingAttendant);

        parkingAttendant.addParkingLot(parkingLot1);
        parkingAttendant.addParkingLot(parkingLot2);

        Car car1 = new Car(1);
        Car car2 = new Car(2);
        Car car3 = new Car(3);
        Car car4 = new Car(4);
        Car car5 = new Car(5);

        Token token1 = new Token(parkingLot1,1);
        Token token2 = new Token(parkingLot1,2);
        Token token3 = new Token(parkingLot1,3);
        Token token4 = new Token(parkingLot1,4);
        Token token5 = new Token(parkingLot2,1);

        assertEquals(token1, parkingAttendant.parkCar(car1));
        assertEquals(token2, parkingAttendant.parkCar(car2));
        assertEquals(token3, parkingAttendant.parkCar(car3));
        assertEquals(token4, parkingAttendant.parkCar(car4));
        assertEquals(token5, parkingAttendant.parkCar(car5));

        parkingAttendant.unPark(token1);
        Car car6 = new Car(6);
        Token token6 = new Token(parkingLot1,5);
        assertEquals(token6, parkingAttendant.parkCar(car6));
    }
}