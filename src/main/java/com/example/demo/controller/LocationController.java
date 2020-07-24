package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.db.LocationDBHandler;
import com.example.demo.model.APIResponseModel;
import com.example.demo.model.FoodModel;

@RestController
public class LocationController {
	

	
	@GetMapping("/getLocationData")
	private ResponseEntity<APIResponseModel> getData(@RequestParam Double latitude , @RequestParam Double longitude) {

		APIResponseModel response = new APIResponseModel();
		response.setDescription("");
		response.setMessage("Failure");
		response.setResult(false);

		try {

			LocationDBHandler locationDBHandler = new LocationDBHandler();
				FoodModel data = locationDBHandler.getDataFromDB(latitude , longitude);
				if (data!=null ) {
					response.setDescription("Data found.");
					response.setMessage("Success");
					response.setResult(true);
					response.setData(data);
					return ResponseEntity.status(HttpStatus.OK).body(response);
				} else {
					response.setDescription("Data not found.");
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
				}


		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);

	}



}
