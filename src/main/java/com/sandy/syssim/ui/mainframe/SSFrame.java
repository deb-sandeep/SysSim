package com.sandy.syssim.ui.mainframe;

import com.sandy.syssim.core.SSConfig;
import com.sandy.syssim.core.uiutil.UITheme;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class SSFrame extends JFrame {
    
    private final Container contentPane ;

    public SSFrame( UITheme theme, SSConfig config ) {
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
