package PajaElemek;

import Targyak.Elelmiszer.*;
import Targyak.Hasznalati_targy.Bozotvago;
import Targyak.Hasznalati_targy.Faklya;
import Targyak.Hasznalati_targy.Kotel;
import Targyak.Hasznalati_targy.Uveggolyo;
import Targyak.Targy;
import jatek.Game;

import java.util.*;

/**
 * Kikotő ahol minden küldetés előtt lehet vásárolni dolgokat kolrátozott mennyiségben.
 * Egy raktára van ahol ezeket a Tárgyakat tárolom.
 *
 */

public class Kikoto {
    private List<Targy> raktar;
    private Scanner scan=new Scanner(System.in);

    public Kikoto() {
        this.raktar = new ArrayList<>();
        feltoltes();
    }

    /**
     * Feltölti a raktárat tárgyakkal.
     */
    public void feltoltes(){
        Kotel kotel=new Kotel();
        Faklya faklya=new Faklya();
        Uveggolyo golyo=new Uveggolyo();
        Whiskey ital=new Whiskey();
        Gyumolcs gyumolcs= new Gyumolcs();
        Hus hus=new Hus();
        Csokolade csoki=new Csokolade();
        Bozotvago vago=new Bozotvago();
        for (int i=0;i<10;i++){
            this.raktar.add(kotel);
        }
        for (int i=0;i<10;i++){
            this.raktar.add(faklya);
        }
        for (int i=0;i<10;i++){
            this.raktar.add(gyumolcs);
        }
        for (int i=0;i<10;i++){
            this.raktar.add(hus);
        }
        for (int i=0;i<10;i++){
            this.raktar.add(ital);
        }
        for (int i=0;i<10;i++){
            this.raktar.add(golyo);
        }
        for (int i=0;i<10;i++){
            this.raktar.add(csoki);
        }
        for (int i=0;i<10;i++){
            this.raktar.add(vago);
        }
    }

    /**
     * Ki iratja a raktárban lévő tárgyakat.Ad lehetőséget venni belőle vagy tovább lépni.
     */
    public void kinalat(){
        System.out.println("---------KIKÖTŐ---------");
        Set<Targy> distinct = new HashSet<>(this.raktar);
        String dontes="";
        for (Targy s : distinct) {
            System.out.println(s.getNev() + ": " + Collections.frequency(this.raktar, s)+" Ár:"+(s.getAr()-Game.getEn().getkereskedoB()));
        }
        System.out.println("HA VENNI SZERETNÉL VALAMIT ÍRD BE A NEVÉT HA NEM,BÁRMI MÁSSAL TOVÁBB LÉPHETSZ");
        dontes=scan.nextLine();
        switch (dontes){
            case "Bozótvágó":
            case"Hús":
            case "Kötél":
            case "Gyümölcs":
            case "Fáklya":
            case "Üveggolyó":
            case "Whiskey":
            case "Csokoládé":
                if (kivalaszt(dontes)==-1){
                    System.out.println("!!!ELFOGYOT!!!!");
                    kinalat();
                }else{
                    Game.getEn().targyatVasarol(this.raktar.get(kivalaszt(dontes)));
                    this.raktar.remove(kivalaszt(dontes));
                    kinalat();
                }
            default:



        }
    }

    /**
     * Megkeresi a keresett tárgyat.
     * @param nev keresett tárgy neve
     * @return a helye havan ha nincs akkor -1
     */
    public int kivalaszt(String nev){
        for (int i=0;i<this.raktar.size();i++){
            if (this.raktar.get(i).getNev().equals(nev)){
                return i;
            }
        }
        return -1;
    }


}
