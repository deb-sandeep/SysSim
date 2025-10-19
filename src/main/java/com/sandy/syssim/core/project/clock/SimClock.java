package com.sandy.syssim.core.project.clock;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class SimClock extends Thread {

    public static enum State {
        OFF,
        PAUSED,
        RUNNING,
        DESTROYED
    } ;

    private static final int BASE_TICK_INTERVAL_MILLIS = 1000 ;
    private static final int MIN_TICK_INTERVAL_MILLIS  = 100 ;

    @Getter
    private int tickIntervalMillis = 1000 ;

    @Getter
    private long tickCount = 0 ;

    @Getter @Setter
    private State clockState = State.OFF ;

    private List<TickListenerInvoker> invokers = new ArrayList<>() ;

    public SimClock() {
        super( "Simulation System Clock" ) ;
        super.setDaemon( true ) ;
    }

    public void addTickListener( TickListener listener ) {

        log.debug( "Adding tick listener." ) ;

        // Synchronize write to the invokers array. We don't want any concurrent
        // operations while we are adding to the list
        synchronized( this ) {
            if( getTickListenerIndex( listener ) == -1 ) {
                log.debug( "Created, started and registered a new invoker daemon." ) ;
                TickListenerInvoker newInvoker = new TickListenerInvoker( listener ) ;
                newInvoker.start() ;
                invokers.add( newInvoker ) ;
            }
            else {
                log.info( "Tick listener already registered. Skipping add." ) ;
            }
        }
    }

    public void removeTickListener( TickListener listener ) {

        log.debug( "Removing tick listener." ) ;

        synchronized( this ) {
            int listenerIndex = getTickListenerIndex( listener ) ;
            if( listenerIndex != -1 ) {

                log.debug( "Tick listener found. Detaching and shutting down invoker." ) ;
                TickListenerInvoker invoker = invokers.remove( listenerIndex ) ;
                invoker.activateKillSwitch() ;
                invoker.interrupt() ;
                try {
                    invoker.join() ;
                }
                catch( InterruptedException ignore ) {}
                log.debug( "Invoker successfully detached and stopped." ) ;
            }
            else {
                log.info( "Tick listener is not registered." ) ;
            }
        }
    }

    public void clearTickListeners() {

        log.debug( "Removing all tick listeners." ) ;
        synchronized( this ) {
            while( !invokers.isEmpty() ) {
                removeTickListener( invokers.get( 0 ).getUnderlyingTickListener() ) ;
            }
        }
    }

    private synchronized  int getTickListenerIndex(TickListener listener ) {
        for( int i=0; i<invokers.size(); i++ ) {
            TickListenerInvoker invoker = invokers.get( i ) ;
            TickListener existingListener = invoker.getUnderlyingTickListener() ;

            if( listener == existingListener ) {
                return i ;
            }
        }
        return -1 ;
    }

    public void incrementTickInterval( int millis ) {
        setTickInterval( this.tickIntervalMillis + millis ) ;
    }

    public void decrementTickInterval( int millis ) {
        setTickInterval( this.tickIntervalMillis - millis ) ;
    }

    public void setTickInterval( int interval ) {
        if( interval > MIN_TICK_INTERVAL_MILLIS ) {
            this.tickIntervalMillis = interval ;
        }
        else {
            this.tickIntervalMillis = MIN_TICK_INTERVAL_MILLIS ;
        }
    }

    public double getTickIntervalScale() {
        return ((double) this.tickIntervalMillis/BASE_TICK_INTERVAL_MILLIS) ;
    }

    public void run() {

        this.clockState = State.RUNNING ;

        while( clockState != State.DESTROYED ) {
            try {
                if( clockState == State.RUNNING && !invokers.isEmpty() ) {

                    // Why? Because we have already slept for MIN_TICK_INTERVAL, so just need to sleep the rest
                    // This also has the consequence of triggering the first tick after the MIN TICK INTERVAL.
                    // Also, sleep before invoking the listeners so that once we have invoked the listeners, we
                    // sleep only for the minimum tick interval.
                    int sleepTime = this.tickIntervalMillis - MIN_TICK_INTERVAL_MILLIS ;
                    TimeUnit.MILLISECONDS.sleep( sleepTime ) ;

                    this.tickCount++ ;
                    TickEvent tickEvent = new TickEvent( this.tickCount, System.currentTimeMillis() ) ;

                    // Invoke the listeners. It is ok for us to pass the same tick event to the invokers since
                    // tick event is an immutable class.
                    synchronized( this ) {
                        invokers.forEach( i -> i.addTickEvent( tickEvent ) ) ;
                    }
                }
                TimeUnit.MILLISECONDS.sleep( MIN_TICK_INTERVAL_MILLIS ) ;
            }
            catch( InterruptedException ignore ) {}
        }
    }
}
