package com.sandy.syssim;

import com.sandy.syssim.core.SSConfig;
import com.sandy.syssim.core.bus.EventBus;
import com.sandy.syssim.core.ui.mainframe.SSFrame;
import com.sandy.syssim.core.util.DefaultUITheme;
import com.sandy.syssim.core.util.UITheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

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

    @Autowired private UITheme uiTheme ;
    
    private SSFrame  frame = null ;
    private SSConfig cfg = null ;

    public SysSim() {
        APP = this;
    }

    @Override
    public void setApplicationContext( ApplicationContext appCtx )
            throws BeansException {
        APP_CTX = ( ConfigurableApplicationContext )appCtx;
    }

    public void initialize() {

        log.debug( "Initializing SysSim app." ) ;

        log.debug( "Initializing Theme" ) ;
        this.uiTheme = new DefaultUITheme() ;

        log.debug( "- Initializing SysSimFrame" ) ;
        SwingUtilities.invokeLater( ()-> {
            this.frame = APP_CTX.getBean( SSFrame.class ) ;
        } ) ;
        
        log.debug( "SysSim initialization complete" ) ;
    }

    public UITheme getTheme() { return this.uiTheme ; }

    public SSFrame getFrame() { return this.frame; }

    public ApplicationContext getCtx() { return SysSim.APP_CTX ; } ;

    public SSConfig getConfig() {
        if( cfg == null ) {
            if( APP_CTX != null ) {
                cfg = ( SSConfig )APP_CTX.getBean("config");
            }
            else {
                cfg = new SSConfig() ;
            }
        }
        return cfg ;
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
        }
        catch( Exception e ) {
            log.error( "Exception while initializing SysSim.", e ) ;
        }
    }
}
