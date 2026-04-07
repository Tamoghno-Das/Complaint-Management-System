package com.example.config;
import oracle.jdbc.datasource.impl.OracleDataSource;

import java.sql.Connection;
import java.sql.SQLException;


public class DBUtil {

    public static Connection getConnection() throws SQLException {

        var oracleDataSource = new OracleDataSource();
        oracleDataSource.setURL("jdbc:oracle:thin:@localhost:1521:ORCL");
        oracleDataSource.setUser("c##scott");
        oracleDataSource.setPassword("tiger");
        return oracleDataSource.getConnection();
    }

    public static String nullvalue(String value)
    {
        return value == null ?"N/A":value;
    }
}
