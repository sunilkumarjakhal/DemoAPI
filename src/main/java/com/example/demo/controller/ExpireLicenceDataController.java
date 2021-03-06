package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.db.ExpiredLicenceDBHandler;
import com.example.demo.model.APIResponseModel;
import com.example.demo.model.FoodModel;

@RestController
public class ExpireLicenceDataController {
	
	@GetMapping("/getExpiredLicenceData")
	private ResponseEntity<APIResponseModel> getData() {

		APIResponseModel response = new APIResponseModel();
		response.setDescription("");
		response.setMessage("Failure");
		response.setResult(false);

		try {

			ExpiredLicenceDBHandler expiredLicenceDBHandler = new ExpiredLicenceDBHandler();
				List<FoodModel> data = expiredLicenceDBHandler.getDataFromDB();
				if (data!=null && data.size()>0) {
					response.setDescription("Data found.");
					response.setMessage("Success");
					response.setResult(true);
					response.setData(data);
					//return response;
					ResponseEntity.status(HttpStatus.OK).body(response);
				} else {
					response.setDescription("Data not found.");
					ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
				}


		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

	}

}
