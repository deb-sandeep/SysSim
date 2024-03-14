package com.sandy.syssim.core.uiutil;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.text.SimpleDateFormat;

public class UIConstant {

    private static final String FONT_NAME = "Courier" ;
    
    public static final Font BASE_FONT = new Font( FONT_NAME, Font.PLAIN, 20 ) ;
    public static final Font SCREENLET_TITLE_FONT = BASE_FONT.deriveFont( Font.BOLD, 60 ) ;

    public static final Color TILE_BORDER_COLOR = Color.DARK_GRAY.darker() ;
    public static final Border TILE_BORDER = new LineBorder( TILE_BORDER_COLOR ) ;
    
    public static final SimpleDateFormat DF_TIME_LG = new SimpleDateFormat( "H:mm:ss" ) ;
    public static final SimpleDateFormat DF_TIME_SM = new SimpleDateFormat( "H:mm" ) ;
}
