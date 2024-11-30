package it.r27.pizzeria.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.r27.pizzeria.model.Deal;
import it.r27.pizzeria.model.Pizza;
import it.r27.pizzeria.repository.IngredientRepository;
import it.r27.pizzeria.repository.PizzaRepository;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/pizzeria")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepo;

	@Autowired
    private IngredientRepository ingredientRepo;

    @GetMapping
	public String index(Authentication authentication, Model model, @RequestParam(name = "keyword", required = false) String keyword) {
		
		List<Pizza> allPizza;
		
		if(keyword != null && !keyword.isBlank()) {
			allPizza = pizzaRepo.findByNameContaining(keyword);
			model.addAttribute("keyword", keyword);
		} else {
			allPizza = pizzaRepo.findAll();
		}

		model.addAttribute("pizza", allPizza);

		return "pizzeria/index";
	}

	@GetMapping("/show/{id}")
	public String show(@PathVariable(name = "id") Long id, @RequestParam(name = "keyword", required = false) String keyword,
			Model model) {

		Optional<Pizza> pizzaOptional = pizzaRepo.findById(id);

		if (pizzaOptional.isPresent()) {
			model.addAttribute("pizza", pizzaOptional.get());
		}
		
        model.addAttribute("keyword", keyword);
		if(keyword == null || keyword.isBlank() || keyword.equals("null")) {
			model.addAttribute("pizzaUrl", "/pizzeria");
		} else {			
			model.addAttribute("pizzaUrl", "/pizzeria?keyword=" + keyword);
		}

		return "pizzeria/show";
	}

	@GetMapping("/create")
	public String create (Model model) {
		model.addAttribute("pizza", new Pizza());
		model.addAttribute("allIngredients", ingredientRepo.findAll());
		return "pizzeria/create";
	}
	
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("allIngredients", ingredientRepo.findAll());
			return "/pizzeria/create";
		}

		pizzaRepo.save(formPizza);

		redirectAttributes.addFlashAttribute("successMessage", "Pizza aggiunta correttamente.");
		
		return "redirect:/pizzeria";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		model.addAttribute("pizza", pizzaRepo.findById(id).get());
		model.addAttribute("allIngredients", ingredientRepo.findAll());
		return "pizzeria/edit";
	}
	
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("allIngredients", ingredientRepo.findAll());
			return "/pizzeria/edit";
		}

		pizzaRepo.save(formPizza);
		
		return "redirect:/pizzeria";
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {

		pizzaRepo.deleteById(id);

		redirectAttributes.addFlashAttribute("deleteMessage", "Pizza eliminata correttamente.");

		return "redirect:/pizzeria";
	}
	
	@GetMapping("/{id}/deals")
	public String deal(@PathVariable Long id, Model model) {

		Pizza pizza = pizzaRepo.findById(id).get();

		Deal deal = new Deal();
		deal.setPizza(pizza);

		model.addAttribute("deals", deal);

		return "deal/create";
	}
}
