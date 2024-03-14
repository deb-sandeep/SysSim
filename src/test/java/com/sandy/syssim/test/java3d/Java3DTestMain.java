package com.sandy.syssim.test.java3d;

import com.sandy.syssim.test.java3d.poc.Java3DViewPlatform;

import java.awt.*;

public class Java3DTestMain {
    public static void main( String[] args ) {
        System.setProperty( "sun.awt.noerasebackground", "true" );
        EventQueue.invokeLater( () ->
                //new Java3DTimerTrigger().setVisible( true )
                //new Java3DSwingInteraction().setVisible( true )
                new Java3DViewPlatform().setVisible( true )
        );
    }
}
