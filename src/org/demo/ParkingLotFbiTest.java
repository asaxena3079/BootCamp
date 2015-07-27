package org.demo;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

@RunWith(Parameterized.class)
public class ParkingLotFbiTest extends FbiAgent {

    public boolean notifiedFull = false;
    public boolean notifiedVacancy = false;

    @Parameterized.Parameters
    public static  List<FbiAgent> getLst()
    {
        List<FbiAgent> lst = new ArrayList<FbiAgent>();
        FbiAgent fbi1 = new FbiAgent();
        FbiAgent fbi2 = new FbiAgent();
        lst.add(fbi1);
        lst.add(fbi2);
        return  lst;
    }

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