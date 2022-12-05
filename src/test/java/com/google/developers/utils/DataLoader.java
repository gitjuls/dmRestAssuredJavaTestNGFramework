package com.google.developers.utils;

import java.util.Properties;

public class DataLoader {
    private final Properties properties;
    private static DataLoader dataLoader;

    private DataLoader(){
        String userDir = System.getProperty("user.dir");
        String pathToFile = userDir.concat("/src/test/resources/data.properties");
        properties = PropertyUtils.propertyLoader(pathToFile);
    }

    public static DataLoader getInstance(){
        if(dataLoader == null){
            dataLoader = new DataLoader();
        }
        return dataLoader;
    }

    public String getUserId(){
        String prop = properties.getProperty("userId");
        if(prop != null) return prop;
        else throw new RuntimeException("property userId is not specified in the config.properties file");
    }

}
