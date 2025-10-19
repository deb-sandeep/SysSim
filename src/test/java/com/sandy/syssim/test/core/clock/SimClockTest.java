package com.sandy.syssim.test.core.clock ;

import com.sandy.syssim.core.project.clock.SimClock;
import com.sandy.syssim.test.core.clock.fixture.MockTickListeners;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class SimClockTest {

    @Test
    public void testSimClock() {

        SimClock clock = new SimClock() ;
        MockTickListeners tl = new MockTickListeners() ;
        clock.addTickListener( tl ) ;
        clock.setTickInterval( 100 ) ;
        clock.start() ;

        try {
            TimeUnit.SECONDS.sleep( 5 ) ;
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        MatcherAssert.assertThat( tl.getNumEventsReceived(), Matchers.equalTo( 49 ) ) ;
    }
}
