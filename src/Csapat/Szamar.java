package Csapat;

import Felfedezo.Felfedezo;

/**
 * Szamár csapattárs és banuszainak mevalósítása.
 *
 */
public class Szamar extends Csapattars {
    public Szamar(String nev) {
        super(nev);
    }

    /**
     * A szamar bonusza 2-bvel többb inventory slotot add.
     * @param felfedezo akire ez a bonusz érvényes
     */
    public static void bonus(Felfedezo felfedezo){
        felfedezo.bonuszSlotok(2);

    }

    /**
     * Ha a szamar elhadja acsapatot bonusz megszüntetése 2 vel kevesebb inventorí slot.
     * @param felfedezo akire ez a bonusz érvényes
     */
    public static void bonusNincs(Felfedezo felfedezo){
        felfedezo.bonuszSlotok(-2);

    }
}
