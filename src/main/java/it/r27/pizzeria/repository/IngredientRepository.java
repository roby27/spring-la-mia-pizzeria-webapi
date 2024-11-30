package it.r27.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.r27.pizzeria.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
