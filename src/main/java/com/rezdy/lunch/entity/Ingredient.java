package com.rezdy.lunch.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ingredient implements Serializable {

	private static final long serialVersionUID = 4148026602122073251L;

	@Id
	private String title;

	private LocalDate bestBefore;

	private LocalDate useBy;

	public String getTitle() {
		return title;
	}

	public Ingredient setTitle(String title) {
		this.title = title;
		return this;
	}

	public LocalDate getBestBefore() {
		return bestBefore;
	}

	public Ingredient setBestBefore(LocalDate bestBefore) {
		this.bestBefore = bestBefore;
		return this;
	}

	public LocalDate getUseBy() {
		return useBy;
	}

	public Ingredient setUseBy(LocalDate useBy) {
		this.useBy = useBy;
		return this;
	}
}
