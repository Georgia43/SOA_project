package fr.insa.ms.FeedbackService.controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientConfigResource {
	@Value ("${server.port}")
	private String serverPort;
	
	@Value ("${db.connection}")
	private String typeConnectionDeDB;
	
	@Value ("${db.host}")
	private String dbHost;
	
	@Value ("${db.port}")
	private String dbPort;
	
	@Value("${db.username}") 
    private String dbUsername;

    @Value("${db.password}") 
    private String dbPassword;
	
	@GetMapping("/serverPort")
	public String getServerPort() {
		return serverPort;
	}
	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}
	@GetMapping("/connectionType")
	public String getTypeConnectionDeDB() {
		return typeConnectionDeDB;
	}
	public void setTypeConnectionDeDB(String typeConnectionDeDB) {
		this.typeConnectionDeDB = typeConnectionDeDB;
	}
	@GetMapping("/dbHost")
	public String getDbHost() {
		return dbHost;
	}
	public void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}
	@GetMapping("/dbPort")
	public String getDbPort() {
		return dbPort;
	}
	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}
	
	@Bean
	public DataSource dataSource() {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); 
	    dataSource.setUrl(String.format("jdbc:%s://%s:%s/%s", typeConnectionDeDB, dbHost, dbPort, dbUsername));
	    dataSource.setUsername(dbUsername);
	    dataSource.setPassword(dbPassword);
	    return dataSource;
	}
	
}
