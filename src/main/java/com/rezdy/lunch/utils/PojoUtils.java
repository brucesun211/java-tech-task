package com.rezdy.lunch.utils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.rezdy.lunch.dto.IngredientDto;
import com.rezdy.lunch.dto.RecipeDto;
import com.rezdy.lunch.entity.Recipe;

public class PojoUtils {

	public static final <S, T> T copyBasicBeanProperties(S source, T target) {
		BeanUtils.copyProperties(source, target);
		return target;
	}

	public static List<RecipeDto> copyRecipeListToRecipeDtoList(List<Recipe> recipeList) {

		return recipeList.stream().map(recipe -> {
			RecipeDto recipeDto = new RecipeDto();
			recipeDto.setTitle(recipe.getTitle());
			Set<IngredientDto> ingredientDtoList = recipe.getIngredients().stream().map(ingredient -> {
				return copyBasicBeanProperties(ingredient, new IngredientDto());
			}).collect(Collectors.toSet());
			recipeDto.setIngredients(ingredientDtoList);
			return recipeDto;
		}).collect(Collectors.toList());
	}

}
