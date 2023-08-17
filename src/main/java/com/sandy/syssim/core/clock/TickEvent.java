package com.sandy.syssim.core.clock;

import lombok.Getter;

public record TickEvent( @Getter long tickCount, @Getter long eventTime ) {
}
