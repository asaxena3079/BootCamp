package org.demo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParkingClassTest{

    private ParkingLot parkingLot;
    private ParkingLotObserver parkingLotObserver;

    @Before
    public void setup()
    {
        parkingLotObserver = new ParkinLotTestOwner();
    }

    @Test
    public void testParkWhenParkingAvailable() throws Exception {

        parkingLot = new ParkingLot(10, parkingLotObserver);
        int slotNo = parkingLot.park(new Car(1));
        int slotNo2 = parkingLot.park(new Car(2));

        assertEquals(1, slotNo);
        assertEquals(2, slotNo2);
     }

    @Test(expected = ParkingFullException.class)
    public void testParkWhenParkingNotAvailable() throws Exception{

        parkingLot = new ParkingLot(2, parkingLotObserver);
        parkingLot.park(new Car(1));
        parkingLot.park(new Car(2));
        parkingLot.park(new Car(3));
    }

    @Test
    public void testUnparkWhenCarAvailable() throws Exception{

        parkingLot = new ParkingLot(10, parkingLotObserver);
        Car car1 = new Car(1);
        int slotNo = parkingLot.park(car1);

        assertEquals(car1, parkingLot.unpark(slotNo));

    }

    @Test(expected = CarNotFoundException.class)
    public void testUnparkWhenCarNotAvailable() throws Exception{

        parkingLot = new ParkingLot(10, parkingLotObserver);
        Car car1 = new Car(1);
        parkingLot.park(car1);
        int slotNo = 2;
        parkingLot.unpark(slotNo);

    }

    @Test(expected = CarAlreadyPresent.class)
    public void testIsCarAlreadyPresent()
    {
        parkingLot = new ParkingLot(10, parkingLotObserver);
        Car car = new Car(1);
        parkingLot.park(car);
        parkingLot.park(car);
    }

    @Test
    public void ifParkingFullOrVacancyCreatedThenOwnerGetsNotification()
    {
        ParkinLotTestOwner owner = new ParkinLotTestOwner();

        parkingLot = new ParkingLot(2,owner);
        int token = parkingLot.park(new Car(1));
        parkingLot.park(new Car(2));
        assertEquals(true, owner.notifiedFull);

        parkingLot.unpark(token);
        assertEquals(true, owner.notifiedVacancy);

    }


    @Test
    public void ifParkingFullOrVacancyCreatedThenFbiAgentGetsNotification()
    {
        ParkinLotTestOwner owner = new ParkinLotTestOwner();
        parkingLot = new ParkingLot(2,owner);

        FbiAgentTestOwner fbi1 = new FbiAgentTestOwner();
        FbiAgentTestOwner fbi2 = new FbiAgentTestOwner();
        FbiAgentTestOwner fbi3 = new FbiAgentTestOwner();
        parkingLot.registerAgent(fbi1);
        parkingLot.registerAgent(fbi2);
        parkingLot.registerAgent(fbi3);

        int token = parkingLot.park(new Car(1));
        parkingLot.park(new Car(2));
        assertTrue(fbi1.isOnFullCalled());
        assertTrue(fbi2.isOnFullCalled());
        assertTrue(fbi3.isOnFullCalled());

        parkingLot.unpark(token);
        assertTrue(fbi1.isOnVacancyCalled());
        assertTrue(fbi2.isOnVacancyCalled());
        assertTrue(fbi3.isOnVacancyCalled());
    }

    public class ParkinLotTestOwner implements ParkingLotObserver
    {
        public boolean notifiedFull = false;
        public boolean notifiedVacancy = false;

        @Override
        public void onFull() {
            notifiedFull = true;
        }

        @Override
        public void  onVacancy()
        {
            notifiedVacancy = true;
        }
    }

    public class FbiAgentTestOwner implements ParkingLotObserver
    {
        public boolean notifiedFull = false;
        public boolean notifiedVacancy = false;

        @Override
        public void onFull() {
            notifiedFull = true;
        }

        @Override
        public void  onVacancy()
        {
            notifiedVacancy = true;
        }

        public boolean isOnFullCalled()
        {
            return notifiedFull;
        }

        public boolean isOnVacancyCalled()
        {
            return notifiedVacancy;
        }
    }



}