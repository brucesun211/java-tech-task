package com.rezdy.lunch.dao;

import java.time.LocalDate;
import java.util.List;

import com.rezdy.lunch.dto.RecipeDto;

/**
 * RecipeDao is interface, we can have different RecipeDao Implements(JPA, JDBC, Mybatis)
 * 
 * @author bruce
 *
 */
public interface RecipeDao {

	public List<RecipeDto> loadRecipes(LocalDate date);

}
