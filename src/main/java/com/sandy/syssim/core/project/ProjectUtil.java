package com.sandy.syssim.core.project ;

import com.sandy.syssim.core.project.annots.Simulation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

@Slf4j
public class ProjectUtil {

    public static void scanSimProjects() {
        
        ClassPathScanningCandidateComponentProvider provider =
                new ClassPathScanningCandidateComponentProvider( false ) ;
        
        provider.addIncludeFilter( new AnnotationTypeFilter( Simulation.class ) );
        
        for( var beanDef : provider.findCandidateComponents( "com.sandy.syssim.sim" ) ) {
            try {
                log.debug( beanDef.getBeanClassName() ) ;
                Class<?>   clz      = Class.forName( beanDef.getBeanClassName() ) ;
                Simulation simAnnot = clz.getAnnotation( Simulation.class ) ;
                log.debug( simAnnot.name() ) ;
                log.debug( simAnnot.description() ) ;
            }
            catch( ClassNotFoundException e ) {
                throw new RuntimeException( e );
            }
        }
    }
}
