package it.r27.pizzeria.restcontroller;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.r27.pizzeria.model.Payload;
import it.r27.pizzeria.model.Pizza;
import it.r27.pizzeria.repository.PizzaRepository;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@CrossOrigin
@RequestMapping("/api/pizzeria")
public class PizzaRestController {

    @Autowired
    private PizzaRepository pizzaRepo;

    @GetMapping
    public ResponseEntity<Payload<List<Pizza>>> index(@RequestParam(name = "keyword", required = false) String keyword) {

        
        try {
            
            if (keyword != null && !keyword.isBlank()) {
    
                Payload<List<Pizza>>response = new Payload<>("Lista delle pizze filtrata per keyword", "200", pizzaRepo.findByNameContaining(keyword));
                
                return new ResponseEntity<Payload<List<Pizza>>>(response, HttpStatus.OK);
    
            } else {
    
                Payload<List<Pizza>> response = new Payload<>("Lista delle pizze", "200", pizzaRepo.findAll());
                
                return ResponseEntity.ok(response);
    
            }

        } catch (Exception e) {

            Payload<List<Pizza>> error = new Payload<>("Errore nella ricerca per: " + e.getMessage(), "400", null);
            return new ResponseEntity<Payload<List<Pizza>>>(error, HttpStatus.BAD_REQUEST);
            
        }
    }

   @PutMapping("/{id}")
	public ResponseEntity<Payload<Pizza>> update(@PathVariable Long id, @RequestBody Pizza pizza) {
		
		try {
			
			Optional<Pizza> byId = pizzaRepo.findById(id);
			
			Pizza pizzaUpdate = byId.get();

            pizzaUpdate.setName(pizza.getName());
            pizzaUpdate.setDescription(pizza.getDescription());
            pizzaUpdate.setPrice(pizza.getPrice());
			
			pizzaRepo.save(pizzaUpdate);
			
            Payload<Pizza> payload = new Payload<Pizza>("Ok", "200", pizzaUpdate);

			return ResponseEntity.ok(payload);

		} catch(Exception e) {
            
            Payload<Pizza> payload = new Payload<Pizza>("Errore", "400", null);
			return new ResponseEntity<Payload<Pizza>>(payload, HttpStatus.NOT_FOUND);

		}
		
	}

    @DeleteMapping("/{id}")
    public ResponseEntity<Payload<Pizza>> delete(@PathVariable Long id) {
        try {
            
            pizzaRepo.deleteById(id);
            Payload<Pizza> payload = new Payload<Pizza>("Pizza eliminata", "200", pizzaRepo.findById(id).get());

            return ResponseEntity.ok(payload);

        } catch (Exception e) {

            Payload<Pizza> payload = new Payload<Pizza>("Errore", "400", null);
			return new ResponseEntity<Payload<Pizza>>(payload, HttpStatus.NOT_FOUND);

        }
    }

    @PostMapping
    public ResponseEntity<Payload<Pizza>> create(@Valid @RequestBody Pizza pizza) {

        
        try {
            pizzaRepo.save(pizza);
            Payload<Pizza> payload = new Payload<Pizza>("Pizza creata", "200", pizza);

            return ResponseEntity.ok(payload);
            
        } catch (Exception e) {

            Payload<Pizza> payload = new Payload<Pizza>("Errore nella creazione", "400", null);
			return new ResponseEntity<Payload<Pizza>>(payload, HttpStatus.NOT_FOUND);
        }

    }
}
