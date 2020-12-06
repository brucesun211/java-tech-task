package com.rezdy.lunch.dto;

import java.time.LocalDate;

public class IngredientDto {

	private String title;

	private LocalDate bestBefore;

	private LocalDate useBy;

	public IngredientDto() {
		super();
	}

	public IngredientDto(String title, LocalDate bestBefore, LocalDate useBy) {
		super();
		this.title = title;
		this.bestBefore = bestBefore;
		this.useBy = useBy;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getBestBefore() {
		return bestBefore;
	}

	public void setBestBefore(LocalDate bestBefore) {
		this.bestBefore = bestBefore;
	}

	public LocalDate getUseBy() {
		return useBy;
	}

	public void setUseBy(LocalDate useBy) {
		this.useBy = useBy;
	}

}
