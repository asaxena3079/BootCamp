package org.demo;

import org.junit.Before;
import org.junit.Test;

/*import java.util.ArrayList;
import java.util.List;*/

import static org.junit.Assert.*;

public class ParkingClassTest{

    private ParkingLot parkingLot;
    private ParkingLotOwner parkingLotOwner;

    @Before
    public void setup()
    {
        parkingLotOwner = new ParkingLotOwner();
    }

    @Test
    public void testParkWhenParkingAvailable() throws Exception {

        parkingLot = new ParkingLot(10,parkingLotOwner);
        int slotNo = parkingLot.park(new Car(1));
        int slotNo2 = parkingLot.park(new Car(2));

        assertEquals(1, slotNo);
        assertEquals(2, slotNo2);
     }

    @Test(expected = ParkingFullException.class)
    public void testParkWhenParkingNotAvailable() throws Exception{

        parkingLot = new ParkingLot(2,parkingLotOwner);
        parkingLot.park(new Car(1));
        parkingLot.park(new Car(2));
        parkingLot.park(new Car(3));
    }

    @Test
    public void testUnparkWhenCarAvailable() throws Exception{

        parkingLot = new ParkingLot(10,parkingLotOwner);
        Car car1 = new Car(1);
        int slotNo = parkingLot.park(car1);

        assertEquals(car1, parkingLot.unpark(slotNo));

    }

    @Test(expected = CarNotFoundException.class)
    public void testUnparkWhenCarNotAvailable() throws Exception{

        parkingLot = new ParkingLot(10,parkingLotOwner);
        Car car1 = new Car(1);
        parkingLot.park(car1);
        int slotNo = 2;
        parkingLot.unpark(slotNo);

    }

    @Test(expected = CarAlreadyPresent.class)
    public void testIsCarAlreadyPresent()
    {
        parkingLot = new ParkingLot(10,parkingLotOwner);
        Car car = new Car(1);
        parkingLot.park(car);
        parkingLot.park(car);
    }

    @Test
    public void isParkingFull()
    {
        TestOwner owner = new TestOwner();

        /*List<FbiAgent> lst = new ArrayList<FbiAgent>();
        FbiAgent fbi1 = new FbiAgent();
        FbiAgent fbi2 = new FbiAgent();
        lst.add(fbi1);
        lst.add(fbi2);*/


        parkingLot = new ParkingLot(2,owner);
        int token = parkingLot.park(new Car(1));
        parkingLot.park(new Car(2));
        assertEquals(true, owner.notifiedFull);

        parkingLot.unpark(token);
        assertEquals(true, owner.notifiedVacancy);

    }

    public class TestOwner extends ParkingLotOwner
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



}