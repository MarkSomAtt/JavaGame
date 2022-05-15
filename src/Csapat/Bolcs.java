package Csapat;

import Felfedezo.Felfedezo;

/**
 * Bölcs csapattars es banuszainak mevalositasa.
 */
public class Bolcs extends Csapattars {

    public Bolcs() {
        super("Bölcs");
    }

    /**
     *A bölcs altal adott bónusz ami uj kör eseten +3 viszony
     * @param felfedezo a felfedezo akire ez a bonusz ervenyes
     */
    public static void bonus(Felfedezo felfedezo){
        felfedezo.setViszony(3);

    }

}
