package PajaElemek;

import Targyak.Elelmiszer.Gyumolcs;
import Targyak.Elelmiszer.Hus;
import Targyak.Elelmiszer.Kabitoszer;
import Targyak.Hasznalati_targy.Faklya;
import Targyak.Hasznalati_targy.Kotel;
import Targyak.Targy;
import jatek.Game;

import java.util.*;

/**
 * Itt a falut és a hozza tartozö elemeket írom le.
 * A falunak van egy raktara "raktar" ahol faluban vehető targyakat tarolom.
 * Itt lehet eladni és venni dolgokat ha van 2-viszonya a felfedezőnek akkor lehet kap csapatars felvétel lehetőséget.
 *
 */
public class Falu {
    private List<Targy> raktar;
    private static Scanner scan = new Scanner(System.in);


    public Falu() {
        this.raktar = new ArrayList<>();
        feltoltes();
    }

    /**
     * Feltölti a falu raktarat targyakkal
     */
    public void feltoltes(){
        Kotel kotel=new Kotel();
        Faklya faklya=new Faklya();

        Gyumolcs gyumolcs= new Gyumolcs();
        Hus hus=new Hus();
        Kabitoszer kabitoszer=new Kabitoszer();
        for (int i=0;i<5;i++){
            this.raktar.add(kotel);
        }
        for (int i=0;i<5;i++){
            this.raktar.add(faklya);
        }
        for (int i=0;i<5;i++){
            this.raktar.add(gyumolcs);
        }
        for (int i=0;i<5;i++){
            this.raktar.add(hus);
        }
        for (int i=0;i<5;i++){
            this.raktar.add(kabitoszer);
        }
    }

    /**
     * Ki iratja a falu raktaraban lévő targyakat.Ad lehetoseget eladni vagy venni targyakat.<br>
     *
     */
    public void kinalat(){
        System.out.println("------------------------------------------");
        Set<Targy> distinct = new HashSet<>(this.raktar);
        String dontes="";
        for (Targy s : distinct) {
            System.out.println(s.getNev() + ": " + Collections.frequency(this.raktar, s) +" Ár:"+(s.getAr()-Game.getEn().getkereskedoB()));
        }
        System.out.println("HA VENNI SZERETNÉL VALAMIT ÍRD BE A NEVÉT HA NEM, ÍRD HOGY 'nem'");
        System.out.println("HA ELADNI SZERETNÉL ÍRD HOGY 'elad'");
        dontes=scan.nextLine();
        switch (dontes){
            case "nem":
                Game.getMap().lepes();
                break;
            case"Hús":
            case "Kötél":
            case "Gyümölcs":
            case "Fáklya":
            case "Kábítószer":
                if (kivalaszt(dontes)==-1){
                    System.out.println("!!!ELFOGYOT!!!!");
                    kinalat();
                }else{
                    Game.getEn().targyatVasarol(this.raktar.get(kivalaszt(dontes)));
                    this.raktar.remove(kivalaszt(dontes));
                    kinalat();
                }
                break;
            case "elad":
                elad();
                break;
            default:
                System.out.println("!!!!ÉRVÉNYTELEN PARANCS!!!!");
                kinalat();

        }
    }

    /**
     * megnezi van -e elyen nevu targy a raktarban.Ha van vissza ter ahelyevel ha nincs -1-el.
     * @param nev keresendő targy neve
     * @return targy helye
     */
    public int kivalaszt(String nev){
        for (int i=0;i<this.raktar.size();i++){
            if (this.raktar.get(i).getNev().equals(nev)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Ha van legalabb 2 viszoja a Felfedezőnek akkor 20% eséllyel ad lehetőséget csapatarsat venni.Egyepkent a kynalatot mutatja.
     * A csapattars ajanlas random 3 csapattars közül.
     */
    public void ajánlás(){
        String dontes="";
        if (Game.getEn().getViszony()>=2 && Math.random()<=0.2){
            Random r=new Random();
            int max=3;
            String csappattars="";
            String leiras="";
            int szam= r.nextInt((max) );
            switch (szam){
                case 0:
                    csappattars="Felderítő";
                    leiras=" (+1 látókör)";
                    break;
                case 1:
                    csappattars="Sámán";
                    leiras=" (a Kábítószer +20% energia)";
                    break;
                case 2:
                    csappattars="Bölcs";
                    leiras=" (+3 alapviszony új térképen)";
                    break;

            }
            System.out.println("------------------------------------");
            System.out.println("LEHETŐSÉGED VAN  FELVENNI EGY CSAPATTÁRSAT(150 ARANY)");
            System.out.println("AKI:"+csappattars+leiras);
            System.out.println("'igen/nem'");
            dontes=scan.nextLine();
            switch (dontes){
                case "igen":
                    if (Game.getEn().getCsapat().size()==3){
                        System.out.println("!!!CSAK 3 CSAPATTÁRSAD LEHET!!!");
                        kinalat();
                    }else if(Game.getEn().getCsapat().contains(csappattars)){
                        System.out.println("MÁR VAN IYLEN CSAPATTÁRSAD");
                        kinalat();
                    }else{
                        Game.getEn().getCsapat().add(csappattars);
                        Game.getEn().csapatarsatVasarol(150);
                        Game.getEn().bonuszok(csappattars);
                        kinalat();
                    }
                    break;
                case "nem":
                    kinalat();
                    break;
                default:
                    System.out.println("!!!!ERVENYTELEN PARANCS!!!!!");
            }
        }else kinalat();
    }

    /**
     * Targyak eladasa ha van ilyen targya a felfedezonek megkapja a az erteket.<br>
     * Ha nem targyat add meg akkor figyelmeztetes.
     */
    public void elad(){
        inventory();
        System.out.println("------ÍRD BE MIT SZERETNÉL ELADNI------");
        System.out.println("------HA VÉGEZTÉL ÍRD HOGY 'vege'------");
        String dontes=scan.nextLine();
        switch (dontes){
            case "Hús":
            case "Fáklya":
            case "Csokoládé":
            case "Kábítószer":
            case "Whiskey":
            case"Gyümölcs":
            case "Bozótvágó":
            case "Kötél":
            case "Üveggolyó":
                if (Game.getEn().vaneInvben(dontes)==-1){
                    System.out.println("!!!NINCS ILYEN TÁRGYAD!!!");
                    elad();
                }else{
                    Game.getEn().elad(Game.getEn().getInv().get(Game.getEn().vaneInvben(dontes)));
                    elad();
                }
                break;
            case "vege":
                kinalat();
                break;
            default:
                System.out.println("!!!ÉRVÉNYTELEN PARANCS!!!");
                elad();
                break;
        }
    }

    /**
     * A felfedezo inventory ki iratasa.
     */
    public void inventory(){
        Set<Targy> distinct = new HashSet<>(Game.getEn().getInv());
        Set<String> volt=new HashSet<>();
        if(Game.getEn().getInv().size()==0) {
            System.out.println("ÜRES AZ INVENTORID");
        }else {
            for (Targy s : distinct) {
                if (volt.contains(s.getNev())) {
                } else {
                    volt.add(s.getNev());
                    System.out.println(s.getNev() + ": " + Game.getEn().getInv().stream().filter(t -> t.getNev().equals(s.getNev())).count() + " Érték: " + (s.getErtek()+Game.getEn().getkereskedoB()));

                }
            }
        }

    }




}
