package org.demo;

public enum NotificationStatus {

    FULL(100),AVAILABLE(101),EIGHTY_FULL(80),SIXTY_FULL(60),PARK(1), UNPARK(0),MAXFREESPACE(102),MAXCAPACITY(103);

    int value;

    private  NotificationStatus(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return  value;
    }

}
