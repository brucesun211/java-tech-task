package com.rezdy.lunch.service;

import java.time.LocalDate;
import java.util.List;

import com.rezdy.lunch.dto.RecipeDto;

/**
 * 
 * Code to Interface, not implementation.
 * 
 * @author bruce
 *
 */
public interface LunchService {

	public List<RecipeDto> getNonExpiredRecipesOnDate(String date);

	public List<RecipeDto> loadRecipes(LocalDate date);

}
