package com.rezdy.lunch.service.implement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rezdy.lunch.dao.RecipeDao;
import com.rezdy.lunch.dto.RecipeDto;
import com.rezdy.lunch.service.LunchService;

@Service
@Transactional
public class LunchServiceImpl implements LunchService {

	@Autowired
	private RecipeDao recipeDao;

	private List<RecipeDto> recipesSorted;

	public List<RecipeDto> getNonExpiredRecipesOnDate(String date) {
		LocalDate dueDate;
		if (date == null) {
			dueDate = LocalDate.now();
		} else {
			dueDate = LocalDate.parse(date);
		}
		List<RecipeDto> recipes = loadRecipes(dueDate);
		sortRecipes(recipes, dueDate);

		return recipesSorted;
	}

	private void sortRecipes(List<RecipeDto> recipes, LocalDate date) {

		final List<RecipeDto> afterBestList = new ArrayList<>();

		this.recipesSorted = recipes.stream().filter(recipe -> {
			System.out.println("date" + date);
			boolean hasAfterBeforeIngredient = false;
			hasAfterBeforeIngredient = recipe.getIngredients().stream()
					.anyMatch(ingredient -> ingredient.getBestBefore().isBefore(date));

			if (hasAfterBeforeIngredient)
				afterBestList.add(recipe);
			return !hasAfterBeforeIngredient;
		}).collect(Collectors.toList());

		this.recipesSorted.addAll(afterBestList);

	}

	@Override
	public List<RecipeDto> loadRecipes(LocalDate date) {
		return this.recipeDao.loadRecipes(date);
	}

}
