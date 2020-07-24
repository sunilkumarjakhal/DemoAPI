package com.example.demo.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.FoodModel;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class FoodTruckDataDBHandler {

	public boolean addDatainDB(List<FoodModel> foodModel) {

		boolean isInsert = false;
		try {
			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(GlobalVariables.DB_IP, GlobalVariables.DB_PORT)) {

				} else {
					System.out.println("DB is not connecting.........");

				}
			}
			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_food_truck_data");
			BulkWriteOperation bulk = table.initializeOrderedBulkOperation();

			SimpleDateFormat sdfa = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			for (FoodModel fm : foodModel) {

				BasicDBObject document = new BasicDBObject();

				document.put("location_id", fm.getLocatioID());
				document.put("applicant", fm.getApplicant());
				document.put("facility_type", fm.getFacilityType());
				document.put("cnn", fm.getCnn());
				document.put("location_description", fm.getLocationDescription());
				document.put("address", fm.getAddress());
				document.put("block_lot", fm.getBlocklot());
				document.put("block", fm.getBlock());
				document.put("lot", fm.getLot());
				document.put("permit", fm.getPermit());
				document.put("status", fm.getStatus());
				document.put("food_items", fm.getFoodItem());
				document.put("x", fm.getX());
				document.put("y", fm.getY());
				document.put("latitude", fm.getLatitude());
				document.put("longitude", fm.getLongitude());
				document.put("scheduled", fm.getScheduled());
				document.put("daysHour", fm.getDaysHour());
				document.put("received", fm.getReceived());
				document.put("approved", sdfa.parse(fm.getApproved()));
				document.put("prior_permit", fm.getPriorPermit());
				document.put("expiration_date", sdfa.parse(fm.getExpirationDate()));
				document.put("location", fm.getLocation());

				bulk.insert(document);
				isInsert = true;

			}

			if (isInsert) {
				bulk.execute();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<FoodModel> getDataFromDB() {

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

	public boolean updateDatainDB(List<FoodModel> foodModel) {

		boolean isInsert = false;
		try {
			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(GlobalVariables.DB_IP, GlobalVariables.DB_PORT)) {

				} else {
					System.out.println("DB is not connecting.........");

				}
			}
			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_food_truck_data");
			BulkWriteOperation bulk = table.initializeOrderedBulkOperation();
			
			SimpleDateFormat sdfa = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			for (FoodModel fm : foodModel) {

				BasicDBObject query = new BasicDBObject();

				query.put("location_id", fm.getLocatioID());

				BasicDBObject document = new BasicDBObject();

				document.put("location_id", fm.getLocatioID());
				document.put("applicant", fm.getApplicant());
				document.put("facility_type", fm.getFacilityType());
				document.put("cnn", fm.getCnn());
				document.put("location_description", fm.getLocationDescription());
				document.put("address", fm.getAddress());
				document.put("block_lot", fm.getBlocklot());
				document.put("block", fm.getBlock());
				document.put("lot", fm.getLot());
				document.put("permit", fm.getPermit());
				document.put("status", fm.getStatus());
				document.put("food_items", fm.getFoodItem());
				document.put("x", fm.getX());
				document.put("y", fm.getY());
				document.put("latitude", fm.getLatitude());
				document.put("longitude", fm.getLongitude());
				document.put("scheduled", fm.getScheduled());
				document.put("daysHour", fm.getDaysHour());
				document.put("received", fm.getReceived());
				document.put("approved", sdfa.parse(fm.getApproved()));
				document.put("prior_permit", fm.getPriorPermit());
				document.put("expiration_date", sdfa.parse(fm.getExpirationDate()));
				document.put("location", fm.getLocation());

				bulk.find(query).upsert().update(new BasicDBObject("$set", document));
				isInsert = true;

			}

			if (isInsert) {
				bulk.execute();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
