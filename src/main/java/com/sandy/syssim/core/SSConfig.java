package com.sandy.syssim.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.File;

@Configuration( "config" )
@PropertySource( "classpath:syssim.properties" )
@ConfigurationProperties( "syssim" )
@Data
public class SSConfig {
    private File workspacePath = null ;
}
