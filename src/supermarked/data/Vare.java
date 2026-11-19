package supermarked.data;

import java.time.LocalDate;
import java.util.Objects;

public class Vare {
    private final String id; // EAN-nummer
    private final String name;
    private final LocalDate priceDate;
    private final double price;
    private final boolean isBudgetBrand;
    private final double quantity;
    private final String unit;
    private final boolean isWeighted;
    private final boolean isBio;
    private final boolean isAvailable;

    public Vare(String id, String name, LocalDate priceDate, double price, boolean isBudgetBrand, double quantity, String unit, boolean isWeighted, boolean isBio, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.priceDate = priceDate;
        this.price = price;
        this.isBudgetBrand = isBudgetBrand;
        this.quantity = quantity;
        this.unit = unit;
        this.isWeighted = isWeighted;
        this.isBio = isBio;
        this.isAvailable = isAvailable;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getPriceDate() {
        return priceDate;
    }

    public double getPrice() {
        return price;
    }

    public boolean isBudgetBrand() {
        return isBudgetBrand;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public boolean isWeighted() {
        return isWeighted;
    }

    public boolean isBio() {
        return isBio;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vare vare)) return false;
        return Objects.equals(id, vare.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name + " (" + id + ") - " + String.format("%.2f", price) + " kr.";
    }
}