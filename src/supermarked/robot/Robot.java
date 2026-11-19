package supermarked.robot;

import supermarked.data.Vare;

import java.util.*;

public class Robot {

    private int min = 5; // minimum antal (slags) varer.csv i kurven
    private int max = 25; // maximum antal (slags) varer.csv i kurven
    private int sandsynlighedForKun1Vare = 70; // procents sandsynlighed for at der kun er et styk af hver slags vare
    private int maxStyk = 5; // maximalt antal af samme slags vare

    public Robot() {

    }

    public Collection<Vare> fyldIKurv(Collection<Vare> alleVarer) {
        Random random = new Random();
        List<Vare> kurv = new ArrayList<>();

        int antalVarer = random.nextInt(min, max);
        for (int i = 0; i < antalVarer; i++) {
            int styk = (random.nextInt(100) > sandsynlighedForKun1Vare) ? random.nextInt(2, maxStyk) : 1;
            Vare vare = alleVarer.stream().skip(random.nextInt(alleVarer.size())).findFirst().get();
            while (styk > 0) {
                kurv.add(vare);
                styk--;
            }
        }

        Collections.shuffle(kurv);
        return kurv;
    }
}