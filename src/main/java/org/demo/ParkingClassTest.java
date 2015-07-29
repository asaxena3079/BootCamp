package org.demo;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
    public void testParkForDifferentStrategies() throws Exception{

        parkingLot = new ParkingLot(5, parkingLotObserver);


        ParkinLotTestObserver fbi1 = new ParkinLotTestObserver();
        ParkinLotTestObserver fbi2 = new ParkinLotTestObserver();
        List<ParkingLotObserver> parkingLotObserverList = new ArrayList<ParkingLotObserver>();
        parkingLotObserverList.add(fbi1);
        parkingLotObserverList.add(fbi2);

        ParkinLotTestObserver fbi3 = new ParkinLotTestObserver();
        ParkinLotTestObserver fbi4 = new ParkinLotTestObserver();
        List<ParkingLotObserver> parkingLotObserverList1 = new ArrayList<ParkingLotObserver>();
        parkingLotObserverList1.add(fbi3);
        parkingLotObserverList1.add(fbi4);


        parkingLot.subscribeAgentForParticularStrategies(NotificationStatus.SIXTY_FULL, parkingLotObserverList);
        parkingLot.subscribeAgentForParticularStrategies(NotificationStatus.EIGHTY_FULL, parkingLotObserverList1);

        parkingLot.park(new Car(1));
        parkingLot.park(new Car(2));
        parkingLot.park(new Car(3));
        parkingLot.park(new Car(4));

        assertEquals(NotificationStatus.SIXTY_FULL, fbi1.notify);
        assertEquals(NotificationStatus.SIXTY_FULL, fbi2.notify);

        assertEquals(NotificationStatus.EIGHTY_FULL, fbi3.notify);
        assertEquals(NotificationStatus.EIGHTY_FULL, fbi4.notify);

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

    /*@Test
    public void ifParkingFullOrVacancyCreatedThenOwnerGetsNotification()
    {
        ParkinLotTestObserver owner = new ParkinLotTestObserver();

        parkingLot = new ParkingLot(2,owner);
        int token = parkingLot.park(new Car(1));
        parkingLot.park(new Car(2));
        assertEquals(NotificationStatus.FULL, owner.notify);

        parkingLot.unpark(token);
        assertEquals(NotificationStatus.AVAILABLE, owner.notify);

    }*/


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
        assertEquals(null, fbi1.notify);
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
        public void notified(NotificationStatus notify,String name)
        {
            this.notify = notify;
        }
    }





}