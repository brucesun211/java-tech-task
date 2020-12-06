package com.rezdy.lunch.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
public class Recipe implements Serializable {

	private static final long serialVersionUID = -5285471385923476778L;

	@Id
	private String title;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "recipe_ingredient", joinColumns = @JoinColumn(name = "recipe"), inverseJoinColumns = @JoinColumn(name = "ingredient"))
	private Set<Ingredient> ingredients;

	public String getTitle() {
		return title;
	}

	public Recipe setTitle(String title) {
		this.title = title;
		return this;
	}

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

	public Recipe setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
		return this;
	}
}
