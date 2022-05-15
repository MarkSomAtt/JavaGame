package Csapat;

/**
 * A csapattagok os osztalya.Ahol a közös adattagot hozom letre a nevet.
 */
public abstract class Csapattars {
    private String nev;

    public String getNev() {
        return nev;
    }

    public Csapattars(String nev) {
        this.nev = nev;
    }
}
