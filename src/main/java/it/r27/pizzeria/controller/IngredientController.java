package it.r27.pizzeria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import it.r27.pizzeria.model.Ingredient;
import it.r27.pizzeria.model.Pizza;
import it.r27.pizzeria.repository.IngredientRepository;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/ingredienti")
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepo;

    @GetMapping
    public String index(Model model) {

        List<Ingredient> allIngredient = ingredientRepo.findAll();

        model.addAttribute("ingredients", allIngredient);
        model.addAttribute("ing", new Ingredient());

        return "ingredienti/index";
    }
    
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute(name = "ing") Ingredient ingredient, BindingResult result, Model model) {
        
        if (result.hasErrors()) {

            List<Ingredient> allIngredient = ingredientRepo.findAll();

            model.addAttribute("ingredients", allIngredient);
            model.addAttribute("ing", new Ingredient());
            return "ingredienti/index";
        }

        ingredientRepo.save(ingredient);

        return "redirect:/ingredienti";
    }

    @PostMapping("/delete")
    public String delete(@PathVariable Long id, Model model) {

        Ingredient ingredient = ingredientRepo.findById(id).get();

        for (Pizza pizza : ingredient.getPizze()) {

            pizza.getIngredients().remove(ingredient);

        }

        ingredientRepo.deleteById(id);

        return "redirect:/ingredienti";
    }   
    
}
