package com.rezdy.lunch.controller;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.rezdy.lunch.dto.IngredientDto;
import com.rezdy.lunch.dto.RecipeDto;
import com.rezdy.lunch.service.LunchService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class LunchControllerTest {

	@MockBean
	private LunchService lunchService;

	@Autowired
	private MockMvc mockMvc;

	private List<RecipeDto> recipeList;

	@BeforeEach
	void setUp() throws Exception {

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

		recipeList = new ArrayList<>(List.of(new RecipeDto("Ham and Cheese Toastie", hctIngredients),
				new RecipeDto("Salad", saladIngredients), new RecipeDto("Hotdog", hdIngredients)));

	}

	@Test
	@DisplayName("GET - /lunch?date=2030-02-01 - Successful")
	void testGetRecipes_success() throws Exception {

		doReturn(recipeList).when(lunchService).getNonExpiredRecipesOnDate("2030-02-01");

		mockMvc.perform(get("/lunch?date=2030-02-01")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(3)));

	}

}
