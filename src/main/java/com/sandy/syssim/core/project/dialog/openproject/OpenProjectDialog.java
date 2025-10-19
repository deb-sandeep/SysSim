package com.sandy.syssim.core.project.dialog.openproject;

import com.sandy.syssim.SysSim;
import com.sandy.syssim.core.project.Simulation;
import com.sandy.syssim.core.project.SimulationMetadata;
import com.sandy.syssim.core.project.annots.Sim;
import com.sandy.syssim.core.ui.mainframe.SSFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OpenProjectDialog extends OpenProjectDialogUI {
    
    private final SSFrame mainFrame ;
    private final List<SimulationMetadata> sims ;
    private final DefaultListModel<SimulationMetadata> simListModel = new DefaultListModel<>() ;
    
    public OpenProjectDialog( SSFrame mainFrame ) {
        super( mainFrame ) ;
        this.mainFrame = mainFrame ;
        this.sims = scanSimProjects() ;
        setUpUI() ;
    }
    
    private List<SimulationMetadata> scanSimProjects() {
        
        ClassPathScanningCandidateComponentProvider provider =
                new ClassPathScanningCandidateComponentProvider( false ) ;
        
        provider.addIncludeFilter( new AnnotationTypeFilter( Sim.class ) ) ;
        provider.addIncludeFilter( new AssignableTypeFilter( Simulation.class ) ) ;
        
        List<SimulationMetadata> sims = new ArrayList<>();
        for( var beanDef : provider.findCandidateComponents( "com.sandy.syssim.sim" ) ) {
            try {
                Class<? extends Simulation> clz = ( Class<? extends Simulation> )Class.forName( beanDef.getBeanClassName() );
                Sim annotation = clz.getAnnotation( Sim.class ) ;
                
                assert annotation != null;
                SimulationMetadata meta = new SimulationMetadata(
                        annotation.name(),
                        annotation.description(),
                        !SysSim.getAppCtx().getBeansOfType( clz ).isEmpty() ,
                        clz ) ;
                
                sims.add( meta ) ;
                log.debug( "Found simulation class: {}", clz.getName() );
                log.debug( "   Simulation name : {}", meta.getName() );
                log.debug( "   Simulation desc : {}", meta.getDescription() );
                log.debug( "   Is container managed : {}", meta.isContainerManaged() );
            }
            catch( ClassNotFoundException e ) {
                throw new RuntimeException( e );
            }
        }
        return sims ;
    }
    
    private void setUpUI() {
        
        setUpSimulationList() ;
        setUpSearchField() ;

        super.cancelButton.addActionListener( (e) -> {
            setVisible( false ) ;
        } ) ;
        super.okButton.addActionListener( (e) -> {
            setVisible( false ) ;
            SimulationMetadata selectedSim = super.projectList.getSelectedValue() ;
            if( selectedSim != null ) {
                this.mainFrame.openProject( selectedSim ) ;
            }
        }) ;
    }
    
    private void setUpSimulationList() {
        this.simListModel.addAll( this.sims ) ;
        super.projectList.setModel( this.simListModel ) ;
        super.projectList.addListSelectionListener( (e) -> {
            if( !e.getValueIsAdjusting() ) {
                SimulationMetadata selectedSim = super.projectList.getSelectedValue() ;
                okButton.setEnabled( selectedSim != null ) ;
                super.descrTextArea.setText( "" ) ;
                if( selectedSim != null ) {
                    super.descrTextArea.setText( selectedSim.getDescription() ) ;
                }
            }
        } ) ;
        super.projectList.setSelectedIndex( 0 ) ;
    }
    
    private void setUpSearchField() {
        super.searchTextField.getDocument().addDocumentListener( new DocumentListener() {
            private void update() {
                String filter = OpenProjectDialog.super.searchTextField.getText().trim().toLowerCase() ;
                simListModel.clear();
                sims.stream()
                        .filter(item -> item.getName().toLowerCase().contains( filter ) )
                        .forEach( simListModel::addElement ) ;
            }
            @Override public void insertUpdate(DocumentEvent e) { update(); }
            @Override public void removeUpdate(DocumentEvent e) { update(); }
            @Override public void changedUpdate(DocumentEvent e) { update(); }
        } ) ;
    }
}
