package com.sandy.syssim.core.project.clock;

import lombok.Getter;

public record TickEvent( @Getter long tickCount, @Getter long eventTime ) {
}
