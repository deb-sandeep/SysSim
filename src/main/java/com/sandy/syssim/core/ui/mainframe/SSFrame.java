package com.sandy.syssim.core.ui.mainframe;

import com.sandy.syssim.core.SSConfig;
import com.sandy.syssim.core.project.ProjectUtil;
import com.sandy.syssim.core.util.UITheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Slf4j
@Component
public class SSFrame extends JFrame {
    
    private final Container contentPane ;
    
    private final SSConfig config ;
    private final UITheme theme ;
    private final SSMenuBar menuBar ;

    public SSFrame( UITheme theme, SSConfig config ) {
        super() ;
        this.config = config ;
        this.theme = theme ;
        
        this.menuBar = new SSMenuBar( this ) ;

        this.contentPane = super.getContentPane() ;
        
        setUpUI( theme ) ;
        setVisible( true ) ;
    }
    
    private void setUpUI( UITheme theme ) {
        
        contentPane.setBackground( theme.getBackgroundColor() ) ;
        contentPane.setLayout( new BorderLayout() ) ;
        
        setJMenuBar( menuBar ) ;
        
        addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent e ) {
                exitApp() ;
            }
        } );
        
        //SwingUtils.setMaximized( this ) ;
        super.setBounds( 0, 0, 500, 400 );
    }
    
    public void openProject() {
        ProjectUtil.scanSimProjects() ;
    }
    
    public void closeProject() {
    }
    
    public void exitApp() {
        System.exit( 0 ) ;
    }
}
