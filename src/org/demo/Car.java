package org.demo;

public class Car {

    public Car(int carNo) {
        this.carNo = carNo;
    }

    private int carNo;

    public int getCarNo() {
        return carNo;
    }

    public void setCarNo(int carNo) {
        this.carNo = carNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        return carNo == car.carNo;

    }

    @Override
    public int hashCode() {
        return carNo;
    }
}
