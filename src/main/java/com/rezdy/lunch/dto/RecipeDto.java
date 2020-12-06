package com.rezdy.lunch.dto;

import java.util.Set;

public class RecipeDto {

	private String title;
	private Set<IngredientDto> ingredients;

	public RecipeDto() {
		super();
	}

	public RecipeDto(String title, Set<IngredientDto> ingredients) {
		super();
		this.title = title;
		this.ingredients = ingredients;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<IngredientDto> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<IngredientDto> ingredients) {
		this.ingredients = ingredients;
	}

}
