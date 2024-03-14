package com.sandy.syssim.test.java3d;

import org.jogamp.vecmath.Matrix4f;

public class TMatrix extends Matrix4f {
    
    public static Matrix4f xRot( float angle ) {
        Matrix4f mat = new Matrix4f() ;
        mat.rotX( angle ) ;
        return mat ;
    }

    public static Matrix4f xRot( float angle, Matrix4f input ) {
        Matrix4f mat = new Matrix4f() ;
        mat.rotX( angle ) ;
        mat.mul( input );
        return mat ;
    }

    public static Matrix4f yRot( float angle ) {
        Matrix4f mat = new Matrix4f() ;
        mat.rotY( angle ) ;
        return mat ;
    }

    public static Matrix4f yRot( float angle, Matrix4f input ) {
        Matrix4f mat = new Matrix4f() ;
        mat.rotY( angle ) ;
        mat.mul( input ) ;
        return mat ;
    }

    public static Matrix4f zRot( float angle ) {
        Matrix4f mat = new Matrix4f() ;
        mat.rotZ( angle ) ;
        return mat ;
    }
    
    public static Matrix4f zRot( float angle, Matrix4f input ) {
        Matrix4f mat = new Matrix4f() ;
        mat.rotZ( angle ) ;
        mat.mul( input ) ;
        return mat ;
    }
    
    public static Matrix4f identity() {
        Matrix4f mat = new Matrix4f() ;
        mat.setIdentity() ;
        return mat ;
    }
}
