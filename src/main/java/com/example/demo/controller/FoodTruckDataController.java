package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.db.FoodTruckDataDBHandler;
import com.example.demo.model.APIResponseModel;
import com.example.demo.model.FoodModel;

@RestController
public class FoodTruckDataController {

	FoodTruckDataDBHandler foodTruckDataDBHandler = new FoodTruckDataDBHandler();

	@PostMapping("/addData")
	private ResponseEntity<APIResponseModel> addData(@RequestBody List<FoodModel> foodModel) {

		APIResponseModel response = new APIResponseModel();
		response.setDescription("");
		response.setMessage("Failure");
		response.setResult(false);

		try {

			if (foodModel != null && foodModel.size() > 0) {

				boolean res = foodTruckDataDBHandler.addDatainDB(foodModel);
				if (res) {
					response.setDescription("Data added successfully.");
					response.setMessage("Success");
					response.setResult(true);
					return ResponseEntity.status(HttpStatus.OK).body(response);
				} else {
					response.setDescription("Data insertion failed.");
					return ResponseEntity.status(HttpStatus.METHOD_FAILURE).body(response);
				}

			} else {
				response.setDescription("Empty data in the request.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);

	}
	
	@PutMapping("/updateData")
	private ResponseEntity<APIResponseModel> updateData(@RequestBody List<FoodModel> foodModel) {

		APIResponseModel response = new APIResponseModel();
		response.setDescription("");
		response.setMessage("Failure");
		response.setResult(false);

		try {

			if (foodModel != null && foodModel.size() > 0) {

				boolean res = foodTruckDataDBHandler.updateDatainDB(foodModel);
				if (res) {
					response.setDescription("Data updated successfully.");
					response.setMessage("Success");
					response.setResult(true);
					return ResponseEntity.status(HttpStatus.OK).body(response);
				} else {
					response.setDescription("Data updation failed.");
					return ResponseEntity.status(HttpStatus.METHOD_FAILURE).body(response);
				}

			} else {
				response.setDescription("Empty data in the request.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);

	}
	
	@GetMapping("/getData")
	private ResponseEntity<APIResponseModel> getData() {

		APIResponseModel response = new APIResponseModel();
		response.setDescription("");
		response.setMessage("Failure");
		response.setResult(false);

		try {


				List<FoodModel> data = foodTruckDataDBHandler.getDataFromDB();
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
