package com.sandy.syssim.core.ui;

import com.sandy.syssim.core.SysSimConfig;
import com.sandy.syssim.core.ui.uiutil.UITheme;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class SysSimFrame extends JFrame {
    
    private final Container contentPane ;

    public SysSimFrame( UITheme theme, SysSimConfig config ) {
        super() ;

        this.contentPane = super.getContentPane() ;
        setUpUI( theme ) ;
        setVisible( true ) ;
    }
    
    private void setUpUI( UITheme theme ) {
        
        contentPane.setBackground( theme.getBackgroundColor() ) ;
        contentPane.setLayout( new BorderLayout() ) ;
        this.setBounds( 0,0, 1920, 1080 ) ;
    }
}
