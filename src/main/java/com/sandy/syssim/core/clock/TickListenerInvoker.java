package com.sandy.syssim.core.clock;

import lombok.Getter;

import java.util.concurrent.LinkedBlockingQueue;

class TickListenerInvoker extends Thread {

    @Getter
    private final TickListener underlyingTickListener ;

    private LinkedBlockingQueue<TickEvent> tickEventQueue = new LinkedBlockingQueue<TickEvent>() ;

    private boolean kilLSwitchSet = false ;

    TickListenerInvoker( TickListener listener ) {
        this.underlyingTickListener = listener ;
    }

    public void activateKillSwitch() {
        this.kilLSwitchSet = true ;
    }

    public void addTickEvent( TickEvent event ) {
        tickEventQueue.offer( event ) ;
    }

    public boolean isKilLSwitchSet() {
        return this.kilLSwitchSet ;
    }

    public void run() {
        while( !kilLSwitchSet ) {
            try {
                TickEvent event = tickEventQueue.take() ;
                this.underlyingTickListener.handleClockTick( event ) ;
            }
            catch( InterruptedException e ) {
                // If a calling thread is interested in destroying this invoker, it should first set the kill switch
                // to on and then interrupt this thread. The interruption is required to ensure that we break out
                // of a take operation (in case this thread is waiting on it).
                //
                // If the interruption is because of any other reason (kill switch not set), we will continue in the loop.
            }
        }
    }
}
