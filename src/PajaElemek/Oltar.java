package PajaElemek;

import Targyak.Kincs.Kincs;
import jatek.Game;

/**
 * Oltár mevalósítás ahol egy kincs van "kincs" és átok következhet be.
 *
 */

public class Oltar {
    private Kincs kincs;

    public Oltar() {
        this.kincs = new Kincs();
    }

    /**
     * A felfedező viszonyát 2-vel csökkenti.Kincset kap a felfedező.<br>
     * 80% eséllyel átok következik be.
     */
    public void oltar(){
        Game.getEn().setViszony(-2);
        Game.getEn().invberak(this.kincs);
        System.out.println("-------------------");
        System.out.println("!!KINCSET TALÁLTÁL!!");
        if (Math.random()<=0.8){
            System.out.println("-----------------");
            System.out.println("!!!!!!!ÁTOK!!!!!!!");
            Game.atok();
        }
    }
}
