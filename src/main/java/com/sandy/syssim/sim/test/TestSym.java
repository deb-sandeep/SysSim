package com.sandy.syssim.sim.test;

import com.sandy.syssim.core.project.Simulation;
import com.sandy.syssim.core.project.annots.Sim;
import org.springframework.stereotype.Component;

@Sim(
    name = "TestSym",
    description = "TestSym description"
)
@Component
public class TestSym implements Simulation {
}
