package com.sandy.syssim.core.simbase;

public abstract class Simulation {

    public abstract InitParams getInitParams() ;

    public abstract void initialize() ;

    public abstract void execute( long clockTickCount ) ;
}
