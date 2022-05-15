package PajaElemek;


import jatek.Game;

/**
 * A Dzsungelnél számolom háhy osszefüggő dzsungel volt egymás után "osszefugDzsungel".
 * Ha van Bozótvágód az elhasználódik és levágod a dzsungelt
 *
 */

public class Dzsungel {
    private int osszefugDzsungel;

    public void setOsszefugDzsungel(int osszefugDzsungel) {
        this.osszefugDzsungel = osszefugDzsungel;
    }

    public Dzsungel() {
        this.osszefugDzsungel = 0;
    }

    /**
     * Ha van bozótvágó a felfedezőnél akkor elhasználja és sima területté teszi a dzungelt.<br>
     * Ha nincs növeli a mozgásköltséget.Növeli azz összefüggő dszungel számát mindig.
     */
    public void dzsungel(){
        int x=Game.getMap().getX();
        int y=Game.getMap().getY();
        this.osszefugDzsungel+=1;
        boolean van=false;
        int hol=0;
        for (int i =0;i<Game.getEn().getInv().size();i++){
            if (Game.getEn().getInv().get(i).getNev().equals("Bozótvágó")){
                hol=i;
                van=true;
            }
        }
        if ((!van && this.osszefugDzsungel==1)){
            Game.getEn().setMozgaskolt(2);
            Game.getMap().setDzsungelbevan(true);

        }else if (van){
            Game.getEn().elhasznál(Game.getEn().getInv().get(hol));
            Game.getMap().getMap()[x][y]=7;
        }
    }
}
