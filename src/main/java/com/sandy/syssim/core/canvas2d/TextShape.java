package com.sandy.syssim.core.canvas2d ;

import java.awt.* ;
import java.awt.geom.* ;

public class TextShape implements Drawable {

    private final String text ;
    private final double x ;
    private final double y ;
    private final Font font ;
    private final Color color ;

    public TextShape( String text, double x, double y, Font font, Color color ) {
        this.text = text ;
        this.x = x ;
        this.y = y ;
        this.font = font ;
        this.color = color ;
    }

    @Override
    public void draw( Graphics2D g2, AffineTransform virtualToScreen, int canvasHeight ) {
        Point2D virtualPoint = new Point2D.Double( x, y ) ;
        Point2D screenPoint = virtualToScreen.transform( virtualPoint, null ) ;

        g2.setTransform( new AffineTransform() ) ;
        g2.setFont( font ) ;
        g2.setColor( color ) ;
        g2.drawString( text, (float) screenPoint.getX(), (float) ( canvasHeight - screenPoint.getY() ) ) ;
    }
}
