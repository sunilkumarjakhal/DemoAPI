package com.example.demo.db;

import java.text.SimpleDateFormat;

import com.example.demo.model.FoodModel;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class LocationDBHandler {

	public FoodModel getDataFromDB(double latitude, double longitude) {

		try {
			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(GlobalVariables.DB_IP, GlobalVariables.DB_PORT)) {

				} else {
					System.out.println("DB is not connecting.........");

				}
			}
			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_food_truck_data");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss a");

			BasicDBObject query = new BasicDBObject();
			FoodModel fm = null;
			Double mainDis = 99999.0;

			DBCursor cursor = table.find(query);
			BasicDBObject oneDetail = null;
			while (cursor.hasNext()) {
				oneDetail = (BasicDBObject) cursor.next();
				Double dis = getDistance(latitude, longitude, oneDetail.getDouble("latitude"),
						oneDetail.getDouble("longitude"))/1000;

				if (fm == null || dis < mainDis) {
					mainDis = dis;
					fm = new FoodModel();

					fm.setLocatioID(oneDetail.getLong("location_id"));
					fm.setApplicant(oneDetail.getString("applicant"));
					fm.setFacilityType(oneDetail.getString("facility_type"));
					fm.setCnn(oneDetail.getLong("cnn"));
					fm.setLocationDescription(oneDetail.getString("location_description"));
					fm.setAddress(oneDetail.getString("address"));
					fm.setBlocklot(oneDetail.getString("block_lot"));
					fm.setBlock(oneDetail.getString("block"));
					fm.setLot(oneDetail.getString("lot"));
					fm.setPermit(oneDetail.getString("permit"));
					fm.setStatus(oneDetail.getString("status"));
					fm.setFoodItem(oneDetail.getString("food_items"));
					fm.setX(oneDetail.getDouble("x"));
					fm.setY(oneDetail.getDouble("y"));
					fm.setLatitude(oneDetail.getDouble("latitude"));
					fm.setLongitude(oneDetail.getDouble("longitude"));
					fm.setScheduled(oneDetail.getString("scheduled"));
					fm.setDaysHour(oneDetail.getString("daysHour"));
					fm.setReceived(oneDetail.getString("received"));
					fm.setApproved(sdf.format(oneDetail.getDate("approved")));
					fm.setPriorPermit(oneDetail.getInt("prior_permit"));
					fm.setExpirationDate(sdf.format(oneDetail.getDate("expiration_date")));
					fm.setLocation(oneDetail.getString("location"));

				}

			}
			return fm;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public double getDistance(double lat1, double lng1, double lat2, double lng2) {
		double dist = 0;
		try {
			double earthRadius = 6371000; // meters
			double dLat = Math.toRadians(lat2 - lat1);
			double dLng = Math.toRadians(lng2 - lng1);
			double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
					* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
			double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
			dist = (double) (earthRadius * c);
		} catch (Exception e) {
			e.printStackTrace();
			return dist;
		}
		return dist;
	}

}
