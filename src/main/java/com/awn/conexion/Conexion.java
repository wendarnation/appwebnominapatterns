package com.awn.conexion;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class Conexion {
	private static Conexion instance;
	private BasicDataSource dataSource;

	private Conexion() {
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUsername("root");
		dataSource.setPassword("usuario");
		dataSource.setUrl("jdbc:mysql://localhost:3306/gestionnomina?useTimezone=true&serverTimezone=UTC");
		dataSource.setInitialSize(20);
		dataSource.setMaxIdle(15);
		dataSource.setMaxTotal(20);
		dataSource.setMaxWaitMillis(5000);
	}

	public static synchronized Conexion getInstance() {
		if(instance == null) {
			instance = new Conexion();
		}
		return instance;
	}

	public static Connection getConnection() throws SQLException {
		return getInstance().dataSource.getConnection();
	}
}
