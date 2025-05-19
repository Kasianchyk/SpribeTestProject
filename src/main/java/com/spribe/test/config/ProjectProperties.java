package com.spribe.test.config;

import org.aeonbits.owner.Config;

import static org.aeonbits.owner.Config.*;

@LoadPolicy(Config.LoadType.MERGE)
@Sources({"system:properties", "classpath:config/${environment}.properties"})
public interface ProjectProperties extends Config {

    @Key("base.url")
    String baseUrl();

}
