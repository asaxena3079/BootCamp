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
        parkingLotObserver = new ParkinLotTestObserver();
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
        ParkinLotTestObserver owner = new ParkinLotTestObserver();

        parkingLot = new ParkingLot(2,owner);
        int token = parkingLot.park(new Car(1));
        parkingLot.park(new Car(2));
        assertEquals(NotificationStatus.FULL, owner.notify);

        parkingLot.unpark(token);
        assertEquals(NotificationStatus.AVAILABLE, owner.notify);

    }


    @Test
    public void ifParkingFullOrVacancyCreatedThenFbiAgentGetsNotification()
    {
        ParkinLotTestObserver owner = new ParkinLotTestObserver();
        parkingLot = new ParkingLot(2,owner);

        ParkinLotTestObserver fbi1 = new ParkinLotTestObserver();
        ParkinLotTestObserver fbi2 = new ParkinLotTestObserver();
        ParkinLotTestObserver fbi3 = new ParkinLotTestObserver();
        parkingLot.subscribeAgent(fbi1);
        parkingLot.subscribeAgent(fbi2);
        parkingLot.subscribeAgent(fbi3);

        int token = parkingLot.park(new Car(1));
        parkingLot.park(new Car(2));
        assertEquals(NotificationStatus.FULL, fbi1.notify);
        assertEquals(NotificationStatus.FULL, fbi2.notify);
        assertEquals(NotificationStatus.FULL, fbi3.notify);

        parkingLot.unpark(token);
        assertEquals(NotificationStatus.AVAILABLE, fbi1.notify);
        assertEquals(NotificationStatus.AVAILABLE, fbi2.notify);
        assertEquals(NotificationStatus.AVAILABLE, fbi3.notify);
    }

    public class ParkinLotTestObserver implements ParkingLotObserver
    {

        NotificationStatus notify = null;

        @Override
        public void notified(NotificationStatus notify)
        {
            this.notify = notify;
        }
    }

    /*public class FbiAgentTestOwner implements ParkingLotObserver
    {
        NotificationStatus notify = null;

        @Override
        public void notified(NotificationStatus notify)
        {
            this.notify = notify;
        }
    }*/



}