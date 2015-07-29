package org.demo;

public class Token {

    private ParkingLot parkingLot;
    private int tokenNo;

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public int getTokenNo() {
        return tokenNo;
    }

    public Token(ParkingLot parkingLot, int tokenNo) {
        this.parkingLot = parkingLot;
        this.tokenNo = tokenNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (tokenNo != token.tokenNo) return false;
        return !(parkingLot != null ? !parkingLot.equals(token.parkingLot) : token.parkingLot != null);

    }

    @Override
    public int hashCode() {
        int result = parkingLot != null ? parkingLot.hashCode() : 0;
        result = 31 * result + tokenNo;
        return result;
    }
}
