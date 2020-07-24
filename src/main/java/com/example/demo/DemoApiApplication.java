package com.example.demo;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.db.GlobalVariables;
import com.example.demo.db.MongoDBConnection;

@SpringBootApplication
public class DemoApiApplication {

	public static void main(String[] args) {
		try {
			Properties prop = new Properties();
			InputStream input = null;

			input = new FileInputStream("application.properties");

			prop.load(input);
			GlobalVariables.DB_IP = prop.getProperty("DB_IP").toString();
			GlobalVariables.DB_PORT = Integer.parseInt(prop.getProperty("DB_PORT"));
			GlobalVariables.IS_DB_CREDENTIAL_ENABLE = Boolean.parseBoolean(prop.getProperty("IS_DB_CREDENTIAL_ENABLE"));
			
			MongoDBConnection.db_name = prop.getProperty("DB_NAME");
			MongoDBConnection.db_user = prop.getProperty("DB_USER");
			MongoDBConnection.db_password = prop.getProperty("DB_PASSWORD");
			

			if (MongoDBConnection.makeDBConnection(GlobalVariables.DB_IP, GlobalVariables.DB_PORT)) {
			}

			SpringApplication.run(DemoApiApplication.class, args);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
