package com.sandy.syssim.test.java3d.poc;

import lombok.extern.slf4j.Slf4j;
import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.geometry.ColorCube;
import org.jogamp.java3d.utils.universe.SimpleUniverse;
import org.jogamp.vecmath.Point3d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Iterator;

@Slf4j
public class Java3DSwingInteraction extends JFrame {
    
    private RotateBehavior rotateBehavior;
    
    public Java3DSwingInteraction() {
        Canvas3D       canvas3D   = createCanvas3D();
        BranchGroup    sceneGraph = createSceneGraph();
        SimpleUniverse universe   = createUniverse( canvas3D );
        
        universe.addBranchGraph( sceneGraph );
        
        initComponents( canvas3D );
    }
    
    private void initComponents( Canvas3D canvas3D ) {
        
        JPanel  btnPanel      = new JPanel();
        JPanel  canvasPanel   = new JPanel();
        
        JButton rotateYButton = new JButton();
        JButton rotateXButton = new JButton();
        JButton rotateZButton = new JButton();
        
        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        setTitle( "Swing Interaction Test" );
        
        btnPanel.setLayout( new FlowLayout( FlowLayout.CENTER ) );
        
        rotateXButton.setText( "Rotate X" );
        rotateYButton.setText( "Rotate Y" );
        rotateZButton.setText( "Rotate Z" );
        
        rotateXButton.addActionListener( this::rotateButtonActionPerformed );
        rotateYButton.addActionListener( this::rotateButtonActionPerformed );
        rotateZButton.addActionListener( this::rotateButtonActionPerformed );
        
        btnPanel.add( rotateXButton ) ;
        btnPanel.add( rotateYButton ) ;
        btnPanel.add( rotateZButton ) ;
        
        getContentPane().add( btnPanel, java.awt.BorderLayout.NORTH );
        
        canvasPanel.setLayout( new java.awt.BorderLayout() );
        canvasPanel.setPreferredSize( new java.awt.Dimension( 500, 500 ) );
        canvasPanel.add( canvas3D, java.awt.BorderLayout.CENTER );
        
        getContentPane().add( canvasPanel, java.awt.BorderLayout.CENTER );
        
        pack();
    }
    
    private Canvas3D createCanvas3D() {
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        return new Canvas3D( config );
    }
    
    private SimpleUniverse createUniverse( Canvas3D canvas3D ) {
        
        SimpleUniverse univ = new SimpleUniverse( canvas3D );
        
        univ.getViewingPlatform().setNominalViewingTransform();
        univ.getViewer().getView().setMinimumFrameCycleTime( 5 );
        
        return univ;
    }
    
    public BranchGroup createSceneGraph() {
        
        // Create the root of the branch graph
        BranchGroup sceneGraph = new BranchGroup();
        
        // Create the transform group node and initialize it to the
        // identity.  Enable the TRANSFORM_WRITE capability so that
        // our behavior code can modify it at runtime.  Add it to the
        // root of the subgraph.
        TransformGroup transformGroup = new TransformGroup();
        transformGroup.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );
        transformGroup.addChild( new ColorCube( 0.4 ) );
        
        // create the RotateBehavior
        BoundingSphere bounds = new BoundingSphere( new Point3d( 0.0, 0.0, 0.0 ), 100.0 );
        rotateBehavior = new RotateBehavior( transformGroup );
        rotateBehavior.setSchedulingBounds( bounds );
        
        sceneGraph.addChild( transformGroup );
        sceneGraph.addChild( rotateBehavior );
        
        return sceneGraph;
    }
    
    private void rotateButtonActionPerformed( ActionEvent evt ) {
        JButton btn      = ( JButton )evt.getSource();
        String  btnLabel = btn.getText();
        
        char axis = btnLabel.charAt( btnLabel.length() - 1 ) ;
        
        rotateBehavior.rotate( axis );
    }
    
    static class RotateBehavior extends Behavior {
        
        private final int ROTATE_X = 1;
        private final int ROTATE_Y = 2;
        private final int ROTATE_Z = 3;
        
        private final TransformGroup  transformGroup;
        private       WakeupOr        wakeupCondition ;
        
        private float angleX = 0.0f;
        private float angleY = 0.0f;
        private float angleZ = 0.0f;
        
        RotateBehavior( TransformGroup tg ) {
            transformGroup = tg;
        }
        
        @Override
        public void initialize() {
            
            wakeupCondition = new WakeupOr( new WakeupCriterion[]{
                new WakeupOnBehaviorPost( this, ROTATE_X ),
                new WakeupOnBehaviorPost( this, ROTATE_Y ),
                new WakeupOnBehaviorPost( this, ROTATE_Z ),
            } ) ;
            wakeupOn( wakeupCondition );
        }
        
        @Override
        public void processStimulus( Iterator<WakeupCriterion> criteria ) {
            
            while( criteria.hasNext() ) {
                WakeupOnBehaviorPost criterion = ( WakeupOnBehaviorPost )criteria.next();
                switch( criterion.getPostId() ) {
                    case ROTATE_X -> angleX += Math.toRadians( 10.0 );
                    case ROTATE_Y -> angleY += Math.toRadians( 10.0 );
                    case ROTATE_Z -> angleZ += Math.toRadians( 10.0 );
                }
            }
            
            transformGroup.setTransform( getRotationTransform( angleX, angleY, angleZ ) );
            wakeupOn( wakeupCondition );
        }
        
        private static Transform3D getRotationTransform( float angleX, float angleY, float angleZ ) {
            
            Transform3D totalTrans = new Transform3D();
            
            Transform3D transRotX = new Transform3D() ;
            Transform3D transRotY = new Transform3D() ;
            Transform3D transRotZ = new Transform3D() ;
            
            transRotX.rotX( angleX );
            transRotY.rotY( angleY );
            transRotZ.rotZ( angleZ );
            
            totalTrans.mul( transRotX, transRotY );
            totalTrans.mul( transRotZ ) ;
            
            return totalTrans ;
        }
        
        void rotate( char axis ) {
            switch( axis ) {
                case 'X' -> postId( ROTATE_X );
                case 'Y' -> postId( ROTATE_Y );
                case 'Z' -> postId( ROTATE_Z );
            }
        }
    }
}
