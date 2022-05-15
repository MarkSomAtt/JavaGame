package Targyak.Elelmiszer;

import Targyak.Targy;

/**
 * Élelmioszerek összeségének megvalósítása.
 * Itt a tárgyak hoz képest az új dolog az energia töltés amivel rendelkeznek.Elfogysztáskot mennyi energiát tölt vissza.
 *
 */
public abstract class Elelmiszer extends Targy {
    private double energiatoltes;

    public Elelmiszer(int ar,String nev,double energiatoltes,int ertek) {
        super(nev, ar, ertek);
        this.energiatoltes=energiatoltes;
    }


    public double getEnergiatoltes() {
        return energiatoltes;
    }







}
