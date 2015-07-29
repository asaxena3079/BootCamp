package org.demo;

public class CarNotFoundException extends RuntimeException{

    public CarNotFoundException(String msg)
    {
        super(msg);
    }

}
