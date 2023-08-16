package com.sandy.syssim.core.behavior;

import com.sandy.syssim.SysSim;

/**
 * Spring beans implementing this interface are called upon during the
 * shutdown of SConsole.
 */
public interface ComponentFinalizer {

    default boolean isInvocable() { return true ; }

    void destroy( SysSim app ) throws Exception ;
}
