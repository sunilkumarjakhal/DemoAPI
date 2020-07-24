package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.db.SearchDataDBHandler;
import com.example.demo.model.APIResponseModel;
import com.example.demo.model.FoodModel;

@RestController
public class SearchDataByKeyOrderController {
	
	@GetMapping("/getSearchData")
	private ResponseEntity<APIResponseModel> getData(@RequestParam String key , @RequestParam String value ) {

		APIResponseModel response = new APIResponseModel();
		response.setDescription("");
		response.setMessage("Failure");
		response.setResult(false);

		try {

			SearchDataDBHandler searchDataDBHandler = new SearchDataDBHandler();
				List<FoodModel> data = searchDataDBHandler.getDataFromDB(key , value);
				if (data!=null && data.size()>0 ) {
					response.setDescription("Data found.");
					response.setMessage("Success");
					response.setResult(true);
					response.setData(data);
					ResponseEntity.status(HttpStatus.OK).body(response);
				} else {
					response.setDescription("Data not found.");
					ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
				}


		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

}
