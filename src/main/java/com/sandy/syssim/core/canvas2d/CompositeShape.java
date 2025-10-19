package com.sandy.syssim.core.canvas2d ;

import java.awt.* ;
import java.awt.geom.AffineTransform ;
import java.util.ArrayList ;
import java.util.List ;

public class CompositeShape implements Drawable {

    private final List<Drawable> children = new ArrayList<>() ;

    public void add( Drawable shape ) {
        children.add( shape ) ;
    }

    public void remove( Drawable shape ) {
        children.remove( shape ) ;
    }

    public List<Drawable> getChildren( ) {
        return new ArrayList<>( children ) ;
    }

    @Override
    public void draw( Graphics2D g2, AffineTransform virtualToScreen, int canvasHeight ) {
        for ( Drawable child : children ) {
            child.draw( g2, virtualToScreen, canvasHeight ) ;
        }
    }
}
