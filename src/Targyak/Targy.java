package Targyak;

/**
 * Tárgy ősosztály megvalósítása.Minden tárgyban közös adattagok felvétele:"nev","ár","ertek".
 *
 */
public abstract class Targy {
    private String nev;
    private  int ar;
    private int ertek;

    public String getNev() {
        return nev;
    }

    public int getErtek() {
        return ertek;
    }




    public Targy(String nev, int ar, int ertek) {
        this.nev = nev;
        this.ar=ar;
        this.ertek=ertek;
    }

    public int getAr() {
        return ar;
    }
}
