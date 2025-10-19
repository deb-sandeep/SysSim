package com.sandy.syssim.core.project;

import lombok.Getter;

public class SimulationMetadata {
    
    @Getter private String name ;
    @Getter private String description ;
    @Getter private boolean containerManaged ;
    @Getter private Class<? extends Simulation> simulationClass ;
    
    public SimulationMetadata( String name, String description, boolean containerManaged, Class<? extends Simulation> simulationClass ) {
        this.name = name ;
        this.description = description ;
        this.containerManaged = containerManaged ;
        this.simulationClass = simulationClass ;
    }
}
