package supermarked.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DataLoader {

    public Map<String, Vare> indlaesVarer(Path csvFil) throws IOException {
        Map<String, Vare> varer = new HashMap<>();

        try (BufferedReader reader = Files.newBufferedReader(csvFil)) {
            String linje = reader.readLine(); // spring header over
            if (linje == null) {
                return varer;
            }

            while ((linje = reader.readLine()) != null) {
                if (linje.isBlank()) {
                    continue;
                }
                Vare vare = parseLinje(linje);
                varer.put(vare.getId(), vare);
            }
        }

        return varer;
    }

    private Vare parseLinje(String linje) {
        // Semikolon-separeret CSV med 10 kolonner
        String[] felter = linje.split(";", -1);

        String id = felter[0].trim();
        String name = felter[1].trim();
        LocalDate priceDate = LocalDate.parse(felter[2].trim());
        double price = Double.parseDouble(felter[3].trim());
        boolean isBudgetBrand = Boolean.parseBoolean(felter[4].trim());
        double quantity = felter[5].isBlank() ? 0.0 : Double.parseDouble(felter[5].trim());
        String unit = felter[6].trim();
        boolean isWeighted = Boolean.parseBoolean(felter[7].trim());
        boolean isBio = Boolean.parseBoolean(felter[8].trim());
        boolean isAvailable = Boolean.parseBoolean(felter[9].trim());

        return new Vare(id, name, priceDate, price, isBudgetBrand, quantity, unit, isWeighted, isBio, isAvailable
        );
    }
}