package de.thluebeck.pizza.controllers;

import de.thluebeck.pizza.entities.Pizza;
import de.thluebeck.pizza.repositories.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class PizzaController {

    private final PizzaRepository pizzaRepository;

    @Autowired
    public PizzaController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }


    @GetMapping("/createpizza")
    public String showCreatePizzaForm(Pizza pizza) {
        return "add-pizza";
    }

    @GetMapping("/pizzas")
    public String showPizzaList(Model model) {
        model.addAttribute("pizzas", pizzaRepository.findAll());
        return "pizzas";
    }

    @PostMapping("/pizzas/addpizza")
    public String addPizza(@Valid Pizza pizza, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-pizza";
        }

        pizzaRepository.save(pizza);
        return "redirect:/pizzas";
    }

    @GetMapping("/pizzas/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Pizza pizza = pizzaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid pizza Id:" + id));
        model.addAttribute("pizza", pizza);

        return "update-pizza";
    }

    @PostMapping("/pizzas/update/{id}")
    public String updatePizza(@PathVariable("id") long id, @Valid Pizza pizza, BindingResult result, Model model) {
        if (result.hasErrors()) {
            pizza.setId(id);
            return "update-pizza";
        }

        pizzaRepository.save(pizza);

        return "redirect:/pizzas";
    }

    @GetMapping("/pizzas/delete/{id}")
    public String deletePizza(@PathVariable("id") long id, Model model) {
        Pizza pizza = pizzaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid pizza Id:" + id));
        pizzaRepository.delete(pizza);

        return "redirect:/pizzas";
    }
}
