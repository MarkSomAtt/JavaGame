package PajaElemek;


import jatek.Game;

import java.util.Scanner;

/**
 * Az aranypiramisnál lehetőségvan hazamenni vagy folytatni a felfedezást.
 * Érkezéskot biztosan kapsz 1000-hírnevet
 *
 */
public class AranyPiramis {
    public static Scanner scan=new Scanner(System.in);

    /**
     * A felfedező hírnevét növeli 1000-el.Majd lehetöséget ad folytatni a felfedezést.Vagy haza menni.
     */
    public void erkezes(){
        String dontes="";
        Game.getEn().hirnevNoveles(1000);
        System.out.println("-------------------------------------------------------");
        System.out.println("A PIRAMIS MEGTALÁLÁSÁÉRT 1000 HÍRNEVET KAPSZ");
        System.out.println("LEHETÖSÉGED VAN HAZZAMENNI VAGY FOLYTATNI A FELFEDEZÉST");
        System.out.println("HA HAZA SZERETNÉL MENNI ÍRD HOGY 'haza'.");
        System.out.println("BÁRMI MÁSSAL TOVÁBB LÉPHETSZ");
        dontes=scan.nextLine();
        if ("haza".equals(dontes)) {
            Game.korVege();
        }else {
            Game.getMap().lepes();
        }
    }
}
