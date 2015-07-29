package org.demo;

public class CarAlreadyPresent extends RuntimeException{

    public CarAlreadyPresent(String msg)
    {
        super(msg);
    }

}
