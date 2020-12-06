package com.rezdy.lunch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rezdy.lunch.dto.RecipeDto;
import com.rezdy.lunch.service.LunchService;

@RestController
public class LunchController {

	private LunchService lunchService;

	@Autowired
	public LunchController(LunchService lunchService) {
		this.lunchService = lunchService;
	}

	/**
	 * date parameter is optional, if have no date parameter, current date will be
	 * set to date parameter
	 */
	@GetMapping("/lunch")
	public List<RecipeDto> getRecipes(@RequestParam(value = "date", required = false) String date) {
		return lunchService.getNonExpiredRecipesOnDate(date);
	}
}
