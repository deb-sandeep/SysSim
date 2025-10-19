package com.sandy.syssim.core.ui.mainframe;

import com.sandy.syssim.core.util.SwingUtils;

import javax.swing.*;

public class SSMenuBar extends JMenuBar {

    private final SSFrame frame ;
    
    SSMenuBar( SSFrame frame ) {
        this.frame = frame ;
        setUpUI() ;
    }
    
    private void setUpUI() {
        add( getFileMenu() ) ;
        add( getWindowMenu() ) ;
    }
    
    private JMenu getFileMenu() {
        JMenuItem openMenuItem = createMenuItem( "Open Project", "open-project" ) ;
        JMenuItem closeMenuItem = createMenuItem( "Close Project", "close-project" ) ;
        JMenuItem exitMenuItem = createMenuItem( "Exit", "exit-app" ) ;
        
        openMenuItem.addActionListener( e -> frame.openProject() ) ;
        closeMenuItem.addActionListener( e -> frame.closeProject() ) ;
        exitMenuItem.addActionListener( e -> frame.exitApp() ) ;
        
        JMenu menu = new JMenu( "File" ) ;
        menu.add( openMenuItem ) ;
        menu.add( closeMenuItem ) ;
        menu.addSeparator() ;
        menu.add( exitMenuItem ) ;
        return menu ;
    }
    
    private JMenuItem createMenuItem( String name, String iconName ) {
        JMenuItem menuItem = new JMenuItem( name ) ;
        if( iconName != null ) {
            menuItem.setIcon( SwingUtils.getIcon( iconName ) ) ;
        }
        return menuItem ;
    }
    
    private JMenu getWindowMenu() {
        JMenu menu = new JMenu( "Window" ) ;
        return menu ;
    }
}
