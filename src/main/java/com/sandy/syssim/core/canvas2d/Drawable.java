package com.sandy.syssim.core.canvas2d ;

import java.awt.* ;
import java.awt.geom.AffineTransform ;

public interface Drawable {
    void draw( Graphics2D g2, AffineTransform virtualToScreen, int canvasHeight ) ;
}
