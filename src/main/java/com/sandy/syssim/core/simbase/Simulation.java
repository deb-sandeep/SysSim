package com.sandy.syssim.core.simbase;

import com.sandy.syssim.core.clock.TickListener;

public abstract class Simulation implements TickListener {

    public abstract InitParams getInitParams() ;

    public abstract void initialize() ;
}
