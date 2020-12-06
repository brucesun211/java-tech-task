package com.rezdy.lunch.dao.implement;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rezdy.lunch.dao.RecipeDao;
import com.rezdy.lunch.dto.RecipeDto;
import com.rezdy.lunch.entity.Recipe;
import com.rezdy.lunch.utils.PojoUtils;

@Repository
public class RecipeDaoCriteriaQueryImplement implements RecipeDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<RecipeDto> loadRecipes(LocalDate date) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Recipe> criteriaQuery = cb.createQuery(Recipe.class);
		Root<Recipe> recipeRoot = criteriaQuery.from(Recipe.class);

		CriteriaQuery<Recipe> query = criteriaQuery.select(recipeRoot);

		Subquery<Recipe> nonExpiredIngredientSubquery = query.subquery(Recipe.class);
		Root<Recipe> nonExpiredIngredient = nonExpiredIngredientSubquery.from(Recipe.class);
		nonExpiredIngredientSubquery.select(nonExpiredIngredient);

		Predicate matchingRecipe = cb.equal(nonExpiredIngredient.get("title"), recipeRoot.get("title"));
		Predicate expiredIngredient = cb.lessThan(nonExpiredIngredient.join("ingredients").get("useBy"), date);

		Predicate allNonExpiredIngredients = cb
				.exists(nonExpiredIngredientSubquery.where(matchingRecipe, expiredIngredient));

		List<Recipe> expiredRecipeList = entityManager.createQuery(query.where(allNonExpiredIngredients))
				.getResultList();

		List<String> expiredRecipeTitleList = expiredRecipeList.stream().map(recipe -> recipe.getTitle())
				.collect(Collectors.toList());

		CriteriaQuery<Recipe> validRecipeCriteriaQuery = cb.createQuery(Recipe.class);
		Root<Recipe> validRecipeRoot = validRecipeCriteriaQuery.from(Recipe.class);

		validRecipeRoot.fetch("ingredients", JoinType.LEFT);
		validRecipeCriteriaQuery.distinct(true);

		CriteriaQuery<Recipe> validRecipeQuery = validRecipeCriteriaQuery.select(validRecipeRoot);

		List<Recipe> validRecipeList = entityManager
				.createQuery(validRecipeQuery.where(validRecipeRoot.get("title").in(expiredRecipeTitleList).not()))
				.getResultList();

		return PojoUtils.copyRecipeListToRecipeDtoList(validRecipeList);

	}

}
