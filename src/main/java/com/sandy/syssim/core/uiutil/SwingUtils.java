package com.sandy.syssim.core.uiutil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class SwingUtils {

    public static void setMaximized( JFrame frame ) {
        Dimension screenSz = Toolkit.getDefaultToolkit().getScreenSize() ; 
        frame.setBounds( 0, 0, screenSz.width, screenSz.height ) ;
    }
    
    public static void centerOnScreen( Component component, int width, int height ) {
        
        int x = getScreenWidth()/2 - width/2 ;
        int y = getScreenHeight()/2 - height/2 ;
        
        component.setBounds( x, y, width, height ) ;
    }
    
    public static int getScreenWidth() {
        Dimension screenSz = Toolkit.getDefaultToolkit().getScreenSize() ; 
        return screenSz.width ;
    }
    
    public static int getScreenHeight() {
        Dimension screenSz = Toolkit.getDefaultToolkit().getScreenSize() ; 
        return screenSz.height ;
    }
    
    public static ImageIcon getIcon( String iconName ) {
        URL url = SwingUtils.class.getResource( "/icons/" + iconName + ".png" ) ;
        Image image = Toolkit.getDefaultToolkit().getImage( url ) ;
        return new ImageIcon( image ) ;
    }
    
    public static BufferedImage getIconImage( String iconName ) {
        BufferedImage img ;
        try {
            URL url = SwingUtils.class.getResource( "/icons/" + iconName + ".png" ) ;
            assert url != null;
            img = ImageIO.read( url ) ;
        }
        catch( IOException e ) {
            throw new RuntimeException( e ) ;
        }
        return img ;
    }
    
    // The logic has been copied from a stack exchange post.
    // https://stackoverflow.com/questions/1984071/how-to-hide-cursor-in-a-swing-application
    public static void hideCursor( Container container ) {
        // Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit()
                .createCustomCursor( cursorImg,
                        new Point(0, 0),
                        "blank cursor" ) ;

        // Set the blank cursor to the JFrame.
        container.setCursor( blankCursor ) ;
    }

    public static Color getRandomColor() {

        Random rand = new Random() ;
        float r = ( float ) (rand.nextFloat() / 2f + 0.5);
        float g = ( float ) (rand.nextFloat() / 2f + 0.5);
        float b = ( float ) (rand.nextFloat() / 2f + 0.5);
        return new Color(r, g, b) ;
    }

    public static String getHexColorString( Color color ) {
        return "#"+Integer.toHexString( color.getRGB() ).substring(2 ) ;
    }
}
