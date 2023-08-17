package com.sandy.syssim;

import com.sandy.syssim.core.SysSimConfig;
import com.sandy.syssim.core.bus.EventBus;
import com.sandy.syssim.core.simbase.InitParams;
import com.sandy.syssim.core.simbase.Simulation;
import com.sandy.syssim.core.ui.SysSimFrame;
import com.sandy.syssim.core.ui.uiutil.DefaultUITheme;
import com.sandy.syssim.core.ui.uiutil.UITheme;
import com.sandy.syssim.sims.projectile2d.Projectile2DSim;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class SysSim
        implements ApplicationContextAware {

    private static final EventBus GLOBAL_EVENT_BUS = new EventBus() ;

    private static ConfigurableApplicationContext APP_CTX = null ;
    private static SysSim APP = null;

    public static SysSim getApp() {
        return APP;
    }
    public static ApplicationContext getAppCtx() {
        return APP_CTX ;
    }
    public static EventBus getBus() {
        return GLOBAL_EVENT_BUS ;
    }

    // ---------------- Instance methods start ---------------------------------

    private UITheme uiTheme = null ;
    private SysSimFrame frame = null ;
    private SysSimConfig cfg = null ;

    public SysSim() {
        APP = this;
    }

    @Override
    public void setApplicationContext( ApplicationContext applicationContext )
            throws BeansException {
        APP_CTX = ( ConfigurableApplicationContext )applicationContext;
    }

    public void initialize() throws Exception {

        log.debug( "## Initializing SysSim app. >" ) ;

        log.debug( "- Initializing Theme" ) ;
        this.uiTheme = new DefaultUITheme() ;

//        log.debug( "- Initializing SysSimFrame" ) ;
//        SwingUtilities.invokeLater( ()->{
//            this.frame = new SysSimFrame( uiTheme, getConfig() ) ;
//        } ) ;
        log.debug( "<< ## SysSim initialization complete" ) ;
    }

    public UITheme getTheme() { return this.uiTheme ; }

    public SysSimFrame getFrame() { return this.frame; }

    public ApplicationContext getCtx() { return SysSim.APP_CTX ; } ;

    public SysSimConfig getConfig() {
        if( cfg == null ) {
            if( APP_CTX != null ) {
                cfg = (SysSimConfig)APP_CTX.getBean("config");
            }
            else {
                cfg = new SysSimConfig() ;
            }
        }
        return cfg ;
    }

    public void runSim() {

        Simulation sim = new Projectile2DSim() ;
        InitParams initParams = sim.getInitParams() ;
    }

    // --------------------- Main method ---------------------------------------
    public static void main( String[] args ) {

        log.debug( "Starting Spring Booot..." ) ;
        log.debug( "Starting SysSim.." ) ;
        try {
            System.setProperty( "java.awt.headless", "false" ) ;

            SpringApplication.run( SysSim.class, args ) ;
            SysSim app = SysSim.getAppCtx().getBean( SysSim.class ) ;
            app.initialize() ;

            app.runSim() ;
        }
        catch( Exception e ) {
            log.error( "Exception while initializing SysSim.", e ) ;
        }
    }
}
