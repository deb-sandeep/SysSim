package com.sandy.syssim.sims.projectile2d;

import com.sandy.syssim.core.simbase.InitParams;
import com.sandy.syssim.core.simbase.Simulation;
import com.sandy.syssim.core.clock.TickEvent;
import org.springframework.stereotype.Component;

@Component
public class Projectile2DSim extends Simulation {

    private InitParams initParams = null ;

    public InitParams getInitParams() {
        if( initParams == null ) {
            initParams = new Projectile2DSimInitParams() ;
        }
        return initParams;
    }

    public void initialize() {

    }

    public void handleClockTick( TickEvent tick ) {
    }
}
