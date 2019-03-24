package com.github.denrion.internetprovider.constants;

import com.github.denrion.internetprovider.Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConnectionConstants {
    private static Properties prop;

    static {
        prop = new Properties();
        try {
            prop.load(new FileInputStream("config.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /* DATABASE INFO */
    private static final String SQL_DRIVER = prop.getProperty("SQL_DRIVER");
    private static final String HOST = prop.getProperty("HOST");
    private static final String DB_NAME = prop.getProperty("DB_NAME");

    public static final String USERNAME = prop.getProperty("USERNAME");
    public static final String PASSWORD = prop.getProperty("PASSWORD");
    public static final String CONNECTION_STRING = SQL_DRIVER + "://" + HOST + "/" + DB_NAME;

    private ConnectionConstants() {
    }

}
