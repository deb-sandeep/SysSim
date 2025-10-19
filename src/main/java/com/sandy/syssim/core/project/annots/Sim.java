package com.sandy.syssim.core.project.annots;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention( RetentionPolicy.RUNTIME )
public @interface Sim {
    String name() ;
    String description() ;
}
