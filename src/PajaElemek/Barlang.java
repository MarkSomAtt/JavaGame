package PajaElemek;

import Targyak.Kincs.Kincs;
import jatek.Game;

import java.util.Scanner;

/**
 * Ebben az osztályban a barlongot valósítom meg,ahol egy kincset"kincs" lehet találni.
 * Fáklya is használható a katasztrófa elherülésére egyébként lehet katasztrófa.
 *
 */

public class Barlang {
    private  Scanner scan = new Scanner(System.in);

    private Kincs kincs;

    public Barlang() {
        this.kincs = new Kincs();
    }

    /**
     * Lehetőséget ad fáklya használatára a barlangban.Ha igen nincs hataszrófa,ha nem akkor lehet katasztrófa.<br>
     * Kincset mindig kap a felfedező.
     */
    public void barlang(){

        System.out.println("------------------------------------------------------");
        System.out.println("SZERETNÉL FÁKLYÁT HASZNÁLNI A BARLANGBAN?('igen/'nem')");
        System.out.println("--------(HA NEM AKOR 65% HOGY KATASZTRÓFA LESZ)-------");
        String dontes=scan.nextLine();
        switch (dontes){
            case "igen":
                Game.getEn().invberak(this.kincs);
                int hol=-1;
                for (int i=0;i<Game.getEn().getInv().size();i++){
                    if (Game.getEn().getInv().get(i).getNev().equals("Fáklya")){
                        hol=i;
                    }
                }
                if (hol==-1){
                    System.out.println("!!!!!NINCS FÁKLYÁD!!!!!");
                    if (Math.random()<=0.65){
                        System.out.println("!!!!KATASZRÓFA!!!!!");
                        Game.katasztrofa();
                        Game.getMap().lepes();
                    }else System.out.println("!!!!KATASZRÓFA ELKERÜLVE!!!!!");
                }else {

                    Game.getEn().elhasznál(Game.getEn().getInv().get(hol));
                }
                break;
            case "nem":
                Game.getEn().invberak(this.kincs);
                if (Math.random()<=0.65){
                    System.out.println("!!!!KATASZRÓFA!!!!!");
                    Game.katasztrofa();
                    Game.getMap().lepes();
                }else System.out.println("!!!!KATASZRÓFA ELKERÜLVE!!!!!");
                break;
            default:
                System.out.println("!!!!!ÉRVÉNYTELEN PARANCS!!!!!");
                this.barlang();
                break;
        }

    }

}
