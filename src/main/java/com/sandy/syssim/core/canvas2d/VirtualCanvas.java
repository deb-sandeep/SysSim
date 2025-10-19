package com.sandy.syssim.core.canvas2d ;

import javax.swing.* ;
import java.awt.* ;
import java.awt.geom.* ;
import java.util.ArrayList ;
import java.util.List ;
import java.util.Stack ;

public class VirtualCanvas extends JPanel {
    
    private static final int DEFAULT_MIN_X = 0 ;
    private static final int DEFAULT_MIN_Y = 0 ;
    private static final int DEFAULT_MAX_X = 100 ;
    private static final int DEFAULT_MAX_Y = 100 ;
    private static final int DEFAULT_FONT_SIZE = 12 ;
    private static final Font DEFAULT_FONT = new Font( "SansSerif", Font.PLAIN, DEFAULT_FONT_SIZE ) ;
    
    private double vMinX = DEFAULT_MIN_X ;
    private double vMinY = DEFAULT_MIN_Y ;
    private double vMaxX = DEFAULT_MAX_X ;
    private double vMaxY = DEFAULT_MAX_Y ;
    
    private final List<Drawable> shapes = new ArrayList<>() ;
    private final Stack<Color> colorStack = new Stack<>() ;
    
    public VirtualCanvas( ) {
        this( DEFAULT_MIN_X, DEFAULT_MIN_Y, DEFAULT_MAX_X, DEFAULT_MAX_Y ) ;
    }
    
    public VirtualCanvas( double vMinX, double vMinY, double vMaxX, double vMaxY ) {
        setBackground( Color.BLACK ) ;
        setVirtualBounds( vMinX, vMinY, vMaxX, vMaxY ) ;
        colorStack.push( Color.WHITE ) ;
    }
    
    public void pushColor( Color color ) {
        colorStack.push( color ) ;
    }
    
    public void popColor( ) {
        if( colorStack.size() > 1 ) {
            colorStack.pop() ;
        }
    }
    
    public void setVirtualBounds( double vMinX, double vMinY, double vMaxX, double vMaxY ) {
        this.vMinX = vMinX ;
        this.vMinY = vMinY ;
        this.vMaxX = vMaxX ;
        this.vMaxY = vMaxY ;
        repaint() ;
    }
    
    public void drawPoint( double x, double y, double radius ) {
        drawCircle( x, y, radius ) ;
    }
    
    public void drawLine( double x1, double y1, double x2, double y2 ) {
        shapes.add( new PrimitiveShape( new Line2D.Double( x1, y1, x2, y2 ), colorStack.peek() ) ) ;
        repaint() ;
    }
    
    public void drawCircle( double centerX, double centerY, double radius ) {
        double x = centerX - radius ;
        double y = centerY - radius ;
        double size = radius * 2 ;
        shapes.add( new PrimitiveShape( new Ellipse2D.Double( x, y, size, size ), colorStack.peek() ) ) ;
        repaint() ;
    }
    
    public void drawPolygon( double[] xPoints, double[] yPoints ) {
        if( xPoints.length != yPoints.length ) throw new IllegalArgumentException( "Mismatched points" ) ;
        Path2D.Double path = new Path2D.Double() ;
        path.moveTo( xPoints[0], yPoints[0] ) ;
        for( int i = 1 ; i < xPoints.length ; i++ ) {
            path.lineTo( xPoints[i], yPoints[i] ) ;
        }
        path.closePath() ;
        shapes.add( new PrimitiveShape( path, colorStack.peek() ) ) ;
        repaint() ;
    }
    
    public void drawString( String text, double x, double y ) {
        drawString( text, x, y, DEFAULT_FONT ) ;
    }
    
    public void drawString( String text, double x, double y, int fontSize ) {
        drawString( text, x, y, DEFAULT_FONT.deriveFont( (float) fontSize ) ) ;
    }
    
    public void drawString( String text, double x, double y, Font font ) {
        shapes.add( new TextShape( text, x, y, font, colorStack.peek() ) ) ;
        repaint() ;
    }
    
    public void drawCompositeShape( CompositeShape composite ) {
        shapes.add( composite ) ;
        repaint() ;
    }
    
    @Override
    protected void paintComponent( Graphics g ) {
        super.paintComponent( g ) ;
        Graphics2D g2 = ( Graphics2D ) g.create() ;
        
        double w = getWidth() ;
        double h = getHeight() ;
        
        g2.translate( 0, h ) ;
        g2.scale( 1, -1 ) ;
        
        double scaleX = w / ( vMaxX - vMinX ) ;
        double scaleY = h / ( vMaxY - vMinY ) ;
        g2.scale( scaleX, scaleY ) ;
        g2.translate( -vMinX, -vMinY ) ;
        
        AffineTransform virtualToScreen = g2.getTransform() ;
        for( Drawable d : shapes ) {
            d.draw( g2, virtualToScreen, getHeight() ) ;
        }
        
        g2.dispose() ;
    }
}
