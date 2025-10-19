package com.sandy.syssim.core.project.util;

import com.sandy.syssim.SysSim;
import com.sandy.syssim.core.project.Simulation;
import com.sandy.syssim.core.project.SimulationMetadata;
import com.sandy.syssim.core.project.annots.Sim;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProjectUtil {

    public static List<SimulationMetadata> scanSimProjects() {
        
        ClassPathScanningCandidateComponentProvider provider =
                new ClassPathScanningCandidateComponentProvider( false ) ;
        
        provider.addIncludeFilter( new AnnotationTypeFilter( Sim.class ) ) ;
        provider.addIncludeFilter( new AssignableTypeFilter( Simulation.class ) ) ;
        
        List<SimulationMetadata> sims = new ArrayList<>();
        for( var beanDef : provider.findCandidateComponents( "com.sandy.syssim.sim" ) ) {
            try {
                Class<? extends Simulation> clz = ( Class<? extends Simulation> )Class.forName( beanDef.getBeanClassName() );
                Sim annotation = clz.getAnnotation( Sim.class ) ;
                
                SimulationMetadata meta = new SimulationMetadata(
                        annotation.name(),
                        annotation.description(),
                        !SysSim.getAppCtx().getBeansOfType( clz ).isEmpty() ,
                        clz ) ;
                
                sims.add( meta ) ;
                log.debug( "Found simulation class: " + clz.getName() ) ;
                log.debug( "   Simulation name : " + meta.getName() ) ;
                log.debug( "   Simulation desc : " + meta.getDescription() ) ;
                log.debug( "   Is container managed : " + meta.isContainerManaged() ) ;
            }
            catch( ClassNotFoundException e ) {
                throw new RuntimeException( e );
            }
        }
        return sims ;
    }
}
