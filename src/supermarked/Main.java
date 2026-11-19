package supermarked;

import supermarked.data.DataLoader;
import supermarked.data.Vare;
import supermarked.kasse.Kasseapparat;
import supermarked.robot.Robot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        DataLoader loader = new DataLoader();
        Map<String, Vare> alleVarer = loader.indlaesVarer(Path.of("data/varer.csv"));
        Map<String, Vare> tilbudsVarer = loader.indlaesVarer(Path.of("data/tilbud.csv"));

        Kasseapparat kasseapparat = new Kasseapparat(alleVarer, tilbudsVarer);
        Robot robot = new Robot();

        // 1. Test med Robot
        Collection<Vare> robotKurv = robot.fyldIKurv(alleVarer.values());
        kasseapparat.behandlKurvOgUdskrivBon(robotKurv);

        // 2. Sammenligning med samme varer i samme rækkefølge i ArrayList og LinkedList
        List<Vare> fastSekvens = List.of(
                alleVarer.values().iterator().next(),
                tilbudsVarer.values().iterator().next(),
                alleVarer.values().iterator().next()
        );

        List<Vare> arrayListKurv = new ArrayList<>(fastSekvens);
        List<Vare> linkedListKurv = new LinkedList<>(fastSekvens);

        System.out.println("\n--- Kørsel med ArrayList ---");
        kasseapparat.behandlKurvOgUdskrivBon(arrayListKurv);

        System.out.println("\n--- Kørsel med LinkedList ---");
        kasseapparat.behandlKurvOgUdskrivBon(linkedListKurv);
    }
}