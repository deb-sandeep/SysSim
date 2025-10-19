package com.sandy.syssim.core.canvas2d ;

import java.awt.* ;
import java.awt.geom.AffineTransform ;
import java.awt.Shape ;

public class PrimitiveShape implements Drawable {

    private final Shape shape ;
    private final Color color ;

    public PrimitiveShape( Shape shape, Color color ) {
        this.shape = shape ;
        this.color = color ;
    }

    @Override
    public void draw( Graphics2D g2, AffineTransform transform, int canvasHeight ) {
        g2.setTransform( transform ) ;
        g2.setColor( color ) ;
        g2.draw( shape ) ;
    }
}
