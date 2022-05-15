package PajaElemek;

import Targyak.Targy;
import jatek.Game;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Ebben az osztályban valásul meg a hajó(terkepen "0") amiben egy raktár van ahol a kincsinket tudjuk elraktározni.
 * Vagy haza lehet vele utazni.Pohenésre is van lehetőség.
 *
 */

public class Hajo {
    private List<Targy> raktar;
    private static Scanner scan = new Scanner(System.in);


    public List<Targy> getRaktar() {
        return raktar;
    }

    public Hajo() {
        this.raktar = new ArrayList<>();
    }

    /**
     * Add lehetőséget Haza utazni vagy elrakni a kincseket a hajón vagy pihenni.
     */
    public void hazautazas(){
        String dontes;
        System.out.println("------------------------------------------------------");
        System.out.println("---LEHETŐSÉGED VAN HAZA UTAZNI A KINCSEIDDEL('haza')--");
        System.out.println("----------------------VAGY----------------------------");
        System.out.println("------------ELRAKTÁROZNI ŐKET ('raktar')--------------");
        System.out.println("---------------PIHENHETSZ IS('pihen')-----------------");
        System.out.println("------------BÁRMI MÁSSAL TOVÁBB LÉPHETSZ--------------");
        System.out.println("------------------------------------------------------");
        dontes=scan.nextLine();
        switch (dontes){
            case "haza":
                Game.korVege();
                break;
            case "pihen":
                Game.getEn().pihenes();
                break;
            case "raktar":
               elrak();
               break;
            default:
                Game.getMap().lepes();
        }
    }

    /**
     * Ha van a felfedezőnél kincs akkor bereakja a hajó raktárába ha nincs ki yrja hogy nincs.
     */
    public void elrak(){

        if (Game.getEn().vaneInvben("Kincs")==-1){
            System.out.println("!!!!NINCS KINCSED!!!!");
        }else{
            Targy kincs=Game.getEn().getInv().get(Game.getEn().vaneInvben("Kincs"));
            this.raktar.add(kincs);
            Game.getEn().elhasznál(kincs);
        }


    }

    /**
     * Megnézi van-e kincs a hajó raktárában
     * @return vagy a kincs helye vagy ha nincs -1
     */
    public int vaneKincs(){
        for (int i=0;i<this.raktar.size();i++){
            if (this.raktar.get(i).getNev().equals("Kincs")){
                return i;
            }
        }
        return -1;
    }

}
