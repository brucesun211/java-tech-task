package com.rezdy.lunch.service.implement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rezdy.lunch.dao.RecipeDao;
import com.rezdy.lunch.dto.IngredientDto;
import com.rezdy.lunch.dto.RecipeDto;
import com.rezdy.lunch.service.LunchService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class LunchServiceImplTest {

	@Autowired
	private LunchService lunchService;

	@MockBean
	private RecipeDao recipeDao;

	@Test
	@DisplayName("Find Recipes and sorted by BestBefore date, if one recipe's bestbefore date expired, the recipe should be put in the end of list")
	void testGetNonExpiredRecipesOnDate_ShouldFilterByUsedByAndSordedByBestBefore() {

		/*
		 * Given:
		 * 
		 * Recipe(Ham and Cheese Toastie) has some expired bestBefore ingredients
		 * 
		 * Recipe(Hotdog) has some expired bestBefore ingredients
		 * 
		 * Recipe(Salad), all ingredients are before bestBefore date
		 */
		Set<IngredientDto> hctIngredients = new HashSet<>(Set.of(
				new IngredientDto("Cheese", LocalDate.of(1999, Month.JANUARY, 1), LocalDate.of(2031, Month.JANUARY, 1)),
				new IngredientDto("Bread", LocalDate.of(2030, Month.DECEMBER, 31),
						LocalDate.of(2031, Month.JANUARY, 1)),
				new IngredientDto("Butter", LocalDate.of(2030, Month.DECEMBER, 31),
						LocalDate.of(2031, Month.JANUARY, 1)),
				new IngredientDto("Ham", LocalDate.of(2030, Month.DECEMBER, 31),
						LocalDate.of(2031, Month.JANUARY, 1))));

		Set<IngredientDto> hdIngredients = new HashSet<>(Set.of(
				new IngredientDto("Sausage", LocalDate.of(2020, Month.JANUARY, 1),
						LocalDate.of(2031, Month.OCTOBER, 1)),
				new IngredientDto("Ketchup", LocalDate.of(2030, Month.DECEMBER, 31),
						LocalDate.of(2031, Month.JANUARY, 1))));

		Set<IngredientDto> saladIngredients = new HashSet<>(Set.of(
				new IngredientDto("Sausage", LocalDate.of(2030, Month.MARCH, 1), LocalDate.of(2031, Month.OCTOBER, 1)),
				new IngredientDto("Ketchup", LocalDate.of(2030, Month.DECEMBER, 31),
						LocalDate.of(2031, Month.JANUARY, 1))));

		List<RecipeDto> recipeList = new ArrayList<>(List.of(new RecipeDto("Ham and Cheese Toastie", hctIngredients),
				new RecipeDto("Salad", saladIngredients), new RecipeDto("Hotdog", hdIngredients)));

		/*
		 * When: fetch recipes by usedby date
		 */
		doReturn(recipeList).when(recipeDao).loadRecipes(LocalDate.of(2030, Month.FEBRUARY, 1));

		/*
		 * Then: The first element of return recipelist should be salad,
		 * 
		 * Ham and Cheese Toastie and HotDog should be put in the end of the list
		 */
		List<RecipeDto> result = lunchService.getNonExpiredRecipesOnDate("2030-02-01");

		assertNotNull(result);
		assertEquals("Salad", result.get(0).getTitle());
		assertEquals("Ham and Cheese Toastie", result.get(1).getTitle());
		assertEquals("Hotdog", result.get(2).getTitle());
	}

}
