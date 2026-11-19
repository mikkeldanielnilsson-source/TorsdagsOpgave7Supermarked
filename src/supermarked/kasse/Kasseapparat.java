package supermarked.kasse;

import supermarked.data.Vare;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class Kasseapparat {
    private static final Locale DK = Locale.forLanguageTag("da-DK");
    private static final double MOMS_SATS = 0.25; // 25 % dansk moms

    private final Map<String, Vare> alleVarer;
    private final Map<String, Vare> tilbudsVarer;

    public Kasseapparat(Map<String, Vare> alleVarer, Map<String, Vare> tilbudsVarer) {
        this.alleVarer = alleVarer;
        this.tilbudsVarer = tilbudsVarer;
    }

    public void behandlKurvOgUdskrivBon(Collection<Vare> kurv) {
        // LinkedHashMap bevarer rækkefølgen af første forekomst og samler ens varer.csv via EAN (id)
        Map<String, KurvLinje> samledeLinjer = new LinkedHashMap<>();
        Set<String> unikkeEanNumre = new HashSet<>();

        for (Vare scannetVare : kurv) {
            String ean = scannetVare.getId();
            unikkeEanNumre.add(ean);

            Vare normalVare = alleVarer.getOrDefault(ean, scannetVare);
            double normalPris = normalVare.getPrice();

            Vare tilbud = tilbudsVarer.get(ean);
            boolean paaTilbud = tilbud != null;
            double aktuelStykPris = paaTilbud ? tilbud.getPrice() : normalPris;

            KurvLinje linje = samledeLinjer.computeIfAbsent(
                    ean,
                    k -> new KurvLinje(normalVare, normalPris, aktuelStykPris, paaTilbud)
            );
            linje.tilfoejAntal(1);
        }

        double totalEfterRabat = 0.0;
        double samletRabat = 0.0;

        System.out.println("==========================================");
        System.out.println("              SUPERMARKED                 ");
        System.out.println("==========================================");

        for (KurvLinje linje : samledeLinjer.values()) {
            double linjeNormalTotal = linje.antal * linje.normalStykPris;
            double linjeAktuelTotal = linje.antal * linje.aktuelStykPris;
            double linjeRabat = linjeAktuelTotal - linjeNormalTotal; // negativt beløb ved rabat

            totalEfterRabat += linjeAktuelTotal;
            samletRabat += linjeRabat;

            System.out.println(linje.vare.getName());
            System.out.printf(
                    DK,
                    "  %d x %.2f kr. = %.2f kr.%n",
                    linje.antal,
                    linje.normalStykPris,
                    linjeNormalTotal
            );

            if (linje.paaTilbud && linjeRabat < -0.0001) {
                System.out.printf(DK, "  Rabat: %.2f kr.%n", linjeRabat);
            }
        }

        // Priser i dansk detailhandel er inkl. 25 % moms: moms = total * (0.25 / 1.25) = total * 0.20
        double momsBeloeb = totalEfterRabat * (MOMS_SATS / (1.0 + MOMS_SATS));

        System.out.println("------------------------------------------");
        System.out.printf(DK, "Antal forskellige varer.csv: %d%n", unikkeEanNumre.size());
        System.out.printf(DK, "Samlet rabat:            %.2f kr.%n", samletRabat);
        System.out.printf(DK, "Total (inkl. moms):      %.2f kr.%n", totalEfterRabat);
        System.out.printf(DK, "Heraf moms (25%%):        %.2f kr.%n", momsBeloeb);
        System.out.println("==========================================");
    }

    private static class KurvLinje {
        private final Vare vare;
        private final double normalStykPris;
        private final double aktuelStykPris;
        private final boolean paaTilbud;
        private int antal;

        private KurvLinje(Vare vare, double normalStykPris, double aktuelStykPris, boolean paaTilbud) {
            this.vare = vare;
            this.normalStykPris = normalStykPris;
            this.aktuelStykPris = aktuelStykPris;
            this.paaTilbud = paaTilbud;
            this.antal = 0;
        }

        private void tilfoejAntal(int ekstra) {
            this.antal += ekstra;
        }
    }
}