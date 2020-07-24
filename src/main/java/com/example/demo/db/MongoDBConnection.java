package com.example.demo.db;

import java.util.Arrays;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;

public class MongoDBConnection {

	public static MongoClient mongoDB;
	public static String db_name = "demo_database";
	public static String db_user = "admin";
	public static String db_password = "admin";
	public static DB db = null;

	public static boolean makeDBConnection(String ip, int port) {
		boolean isOk = false;

		try {
			if (GlobalVariables.IS_DB_CREDENTIAL_ENABLE) {
				MongoCredential credential = MongoCredential.createCredential(db_user, db_name,
						db_password.toCharArray());
				mongoDB = new MongoClient(new ServerAddress(ip, port), Arrays.asList(credential));
			} else {
				mongoDB = new MongoClient(ip, MongoClientOptions.builder().socketTimeout(100000).connectTimeout(100000)
						.connectionsPerHost(5000).maxConnectionIdleTime(300000).maxWaitTime(300000).build());
			}

			db = mongoDB.getDB(db_name);
			isOk = !mongoDB.getConnectPoint().isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isOk;
	}

	public static boolean isConnectionAlive() {
		DBObject ping = new BasicDBObject("ping", "1");
		try {
			MongoDBConnection.mongoDB.getDB("dbname").command(ping);
			return true;
		} catch (MongoException e) {
			return false;
		}
	}

	public static MongoClient getMongoClient() {

		return mongoDB;

	}

	public static DB getDB() {
		return db;
	}

	public static boolean stopMongoClient() {
		if (mongoDB != null)
			mongoDB.close();
		return true;
	}
}