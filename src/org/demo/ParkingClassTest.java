package org.demo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ankit_22 on 7/27/2015.
 */
public class ParkingClassTest {

    @Test
    public void testParkWhenParkingAvailable() throws Exception {

        ParkingLot pc = new ParkingLot(10);
        int slotNo = pc.park(new Car(1));
        int slotNo2 = pc.park(new Car(2));

        assertEquals(1, slotNo);
        assertEquals(2, slotNo2);
     }

    @Test(expected = ParkingFullException.class)
    public void testParkWhenParkingNotAvailable() throws Exception{

        ParkingLot pc1 = new ParkingLot(2);
        pc1.park(new Car(1));
        pc1.park(new Car(2));
        pc1.park(new Car(3));

    }

}