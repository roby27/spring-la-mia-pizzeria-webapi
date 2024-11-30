package it.r27.pizzeria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.r27.pizzeria.model.Deal;
import it.r27.pizzeria.repository.DealRepository;
import jakarta.validation.Valid;



@Controller
@RequestMapping("/deals")
public class DealController {

    @Autowired
    private DealRepository dealRepo;

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("deals") Deal deal, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        
        if(bindingResult.hasErrors()) {
            return "/deal/create";
        }

        dealRepo.save(deal);

        return "redirect:/pizzeria/show/" + deal.getPizza().getId();
    }
    
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {

        Deal deal = dealRepo.findById(id).get();

        model.addAttribute("deal", deal);
        
        return "/deal/edit";
    }
    
    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("deal") Deal deal, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        

        if(bindingResult.hasErrors()) {
            return "/deal/edit/" + deal.getId();
        }

        dealRepo.save(deal);

        return "redirect:/pizzeria/show/" + deal.getPizza().getId();
    }
    
}
