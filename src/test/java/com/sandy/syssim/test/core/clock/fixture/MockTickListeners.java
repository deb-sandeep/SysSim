package com.sandy.syssim.test.core.clock.fixture;

import com.sandy.syssim.core.project.clock.TickEvent;
import com.sandy.syssim.core.project.clock.TickListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MockTickListeners implements TickListener {

    private long lastTickTime = 0 ;

    @Getter
    private int numEventsReceived = 0 ;

    @Override
    public void handleClockTick( TickEvent event ) {
        this.lastTickTime = event.getEventTime() ;
        this.numEventsReceived++ ;
    }
}
