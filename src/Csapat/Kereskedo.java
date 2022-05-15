package Csapat;

import Felfedezo.Felfedezo;
/**
 * Kereskedő csapattárs és banuszainak mevalósítása.
 *
 */

public class Kereskedo extends Csapattars{
    public Kereskedo(String nev) {
        super(nev);
    }

    /**
     * Kereskedó általi bonusz amivl 5-tel dragabban adhatunk el dolgokat és 5-ttel olcsóbban vehetünk.
     * @param ember akire ez a bonusz érvényes
     */
    public static void bonusz(Felfedezo ember){
        ember.setkereskedoB(5);

    }

    /**
     * A kereskedő bónusz 0 ra álítása amikor elhagya a csapatot.
     * @param ember akire ez a bonusz érvényes
     */
    public static void bonuszNincs(Felfedezo ember){
        ember.setkereskedoB(0);

    }
}
