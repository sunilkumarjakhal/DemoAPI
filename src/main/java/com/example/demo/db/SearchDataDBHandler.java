package com.example.demo.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.FoodModel;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class SearchDataDBHandler {
	
	public List<FoodModel> getDataFromDB(String key, String value) {

		try {
			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(GlobalVariables.DB_IP, GlobalVariables.DB_PORT)) {

				} else {
					System.out.println("DB is not connecting.........");

				}
			}
			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_food_truck_data");

			List<FoodModel> li = new ArrayList<FoodModel>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss a");

			BasicDBObject query = new BasicDBObject();
			if (key.equalsIgnoreCase("Applicant")) {
				query.put("applicant", value);
			} else if (key.equalsIgnoreCase("FacilityType")) {
				query.put("facility_type", value);

			} else if (key.equalsIgnoreCase("FoodItems")) {
				query.put("food_items", value);
			} else if (key.equalsIgnoreCase("Received")) {
				query.put("received", value);
			} else {
				return null;
			}

			DBCursor cursor = table.find(query);
			BasicDBObject oneDetail = null;
			while (cursor.hasNext()) {

				FoodModel fm = new FoodModel();
				oneDetail = (BasicDBObject) cursor.next();

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

				li.add(fm);

			}

			return li;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}


}
