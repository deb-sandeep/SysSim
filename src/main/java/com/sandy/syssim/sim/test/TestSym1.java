package com.sandy.syssim.sim.test;

import com.sandy.syssim.core.project.Simulation;
import com.sandy.syssim.core.project.annots.Sim;
import org.springframework.stereotype.Component;

@Sim(
    name = "CircularMotion",
    description = "A simulation to demonstrate vertical circular motion."
)
@Component
public class TestSym1 implements Simulation {
}
