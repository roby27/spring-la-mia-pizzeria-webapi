package it.r27.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.r27.pizzeria.model.Deal;

public interface DealRepository extends JpaRepository<Deal, Long> {

}
