package it.r27.pizzeria.model;

import java.util.List;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "pizza")
public class Pizza {
   
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @NotNull(message = "Il nome della pizza non è valido.")
    @NotBlank(message = "Il nome della pizza non può essere vuoto.")
    private String name;

    @NotNull(message = "La descrizione della pizza non è valida.")
    @NotBlank(message = "La descrizione della pizza non può essere vuota.")
    private String description;

    @NotNull(message = "Devi inserire il prezzo.")
    @Positive(message = "Il prezzo non può essere 0.")
    private Double price;

    @NotNull(message = "Il link non è valido.")
    @NotBlank(message = "Il link non può essere vuoto.")
    @URL(protocol = "https", message = "Non hai inserito un URL valido (usa solo link con https)")
    private String image;

    @OneToMany(mappedBy = "pizza")
    private List<Deal> deals;

    @ManyToMany
    @JoinTable (name = "pizza_ingredient", joinColumns = @JoinColumn(name = "pizza_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredients;

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Deal> getDeals() {
        return deals;
    }

    public void setDeals(List<Deal> deals) {
        this.deals = deals;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getFormattedPrice() {
        return String.format("%.2f €", price);
    }

    public String getImage() {
        return image;
    }
}
