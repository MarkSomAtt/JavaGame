package Terkep;

import PajaElemek.*;
import jatek.Game;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A terkepnek az osztalya akol a terkepet "map" egy 2d int tömben tarolok.
 * Tarolom az x,y kordinatakat, a"dzungelbevan" tarolja hogy jelenleg dzungalban vagyunk-e.
 * "terkep" tarolja azt a terkepet amit minden lepes utan medjelenitek a "?" jelekkel.
 * A terkepen 4 falu van ezeket is tarolom."bejarhelyek"-ben tarolom a bejart oltarokat és barlangokat.
 * "felderito" ervenyes-e a fekderítő bonusza."vulkankitores" tortent-e volkan kitöres.
 * "lepesek" a vulkán kitürés ota eltelt lepesek szama.És a mappon lévő 4 falut is letrehozom
 *
 */

public class Map {
    private int[][] map ;

    private int x;
    private int y;
    private boolean dzsungelbevan;
    private int felfedezopos;
    private Scanner scan ;
    private String dir;
    private Object[][] terkep;
    private Dzsungel dzsungel;
    private java.util.Map<Integer,Integer> bejartHelyek;
    private Falu falu1;
    private Falu falu2;
    private Falu falu3;
    private Falu falu4;
    private boolean felderito;
    private boolean vulkankitores;
    private int lepesek;


    public Map() {
        this.lepesek=0;
        this.felderito=false;
        this.vulkankitores=false;
        this.map=new int[][]{
                {1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1},
                {1,1,8,7,7,7,0,1,1,1},
                {1,7,7,7,7,7,7,7,7,7},
                {7,3,3,3,6,7,7,7,7,8},
                {7,2,3,4,6,7,7,5,7,3},
                {7,7,8,9,9,9,6,6,4,3},
                {7,3,4,9,2,9,10,6,7,7},
                {7,3,5,2,2,2,3,4,5,7},
                {7,8,9,9,9,9,9,7,7,7}};
        this.x = 2;
        this.y = 6;
        this.dzsungelbevan = false;
        this.felfedezopos = map[x][y];
        this.scan = new Scanner(System.in);
        this.dir = "";
        this.terkep = new Object[10][10];
        this.dzsungel = new Dzsungel();
        this.bejartHelyek = new HashMap<>();
        this.falu1=new Falu();
        this.falu2=new Falu();
        this.falu3=new Falu();
        this.falu4=new Falu();

    }

    public int getLepesek() {
        return lepesek;
    }

    public void setVulkankitores(boolean vulkankitores) {
        this.vulkankitores = vulkankitores;
    }

    public void setFelderito(boolean felderito) {
        this.felderito = felderito;
    }

    public  void setDzsungelbevan(boolean dzsungelbevan) {
        this.dzsungelbevan = dzsungelbevan;
    }

    public  int[][] getMap() {
        return map;
    }

    public  int getX() {
        return x;
    }

    public  int getY() {
        return y;
    }

    /**
     * Fajlbol valo palya beolvasása.A játékostol bekert mappaba keresi a fajlt.
     */
    public void beolvasottPalya()  {
        boolean letezik=true;
        System.out.println("Add meg az elérési utvonalat a mappához ahol a plya.txt-van");
        System.out.println("PL.:G:\\java game");
        String dontes=scan.nextLine();
        Scanner sc= null;
        String[] szavak;
        try {
            sc = new Scanner(new File(dontes+"\\palya.txt"));
            for (int i = 0; i < 10; i++) {
                if (sc.hasNextLine()) {
                    String sor = sc.nextLine();
                    szavak = sor.split(",");
                    for (int j = 0; j < szavak.length; j++) {
                        this.map[i][j] = Integer.parseInt(szavak[j]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("!!!NEM TALALTA MEG A FÁJLT");
            letezik=false;
            palyaValasztas();
        }





    }

    /**
     * meg kerdezi ajatekost melyik palyat szeretne hasznalni.
     */
    public void palyaValasztas()  {
        System.out.println("Fájlból beolvasott pályát szeretnél vagy simát('sima/beolvasott')");
        String dontes=scan.nextLine();
        switch (dontes){
            case "sima":
                break;
            case "beolvasott":
                beolvasottPalya();
                break;
            default:
                System.out.println("ÉRVÉNYTELEN PARANCS");
                palyaValasztas();
                break;
        }
    }

    /**
     * Lepes iranyanak bekerdezese + vulkankitörés ota eltel lepesek szamolasa.<br>
     * inventori megnezesenek lehetosege.Jelenlegi allaspont le ellenorzese.
     */
    public void lepes(){
        System.out.println("A, 'fel', 'le', 'balra', 'jobbra', beírásával tudsz mozogni.");
        dir = scan.nextLine();

        switch (dir){
            case "le":
                Game.getEn().mozdul();
                le();
                break;
            case "fel":
                Game.getEn().mozdul();
                fel();
                break;
            case "balra":
                Game.getEn().mozdul();
                balra();
                break;
            case "jobbra":
                Game.getEn().mozdul();
                jobbra();
                break;
            case "inv":
                Game.getEn().inventory();
                break;
            default:
                System.out.println("!!!!!ÉRVÉNYTELEN PARANCS!!!!!");
                break;
        }
        if (this.vulkankitores){
            this.lepesek++;
            Game.vulkanKitor(Game.getkitoresHely());
            if (this.lepesek==3){
                this.vulkankitores=false;
            }
        }
        allaspont();

    }

    /**
     * A felderito altal adott + latokör.
     */
    public void bonuszLatokor(){

        for(int sor=x-2;sor<x+2+1;sor++) {
            for (int oszlop = y - 2; oszlop < y + 2 + 1; oszlop++) {
                terkep[sor][oszlop] = map[sor][oszlop];

            }
        }
        map();
    }

    /**
     * Terkép megjelenitese mozgasok utan.
     */
    public void map(){

        for(int i=0;i<this.map.length;i++) {
            for (int k =0;k<map.length;k++) {
                if (((i+1==x || i-1==x)&& k==y) || ((k+1==y || k-1==y) && i==x) ||(k==y && i==x) ||(i-1==x && k-1==y) || (i+1==x && k-1==y) ||(i-1==x && k+1==y) || (i+1==x && k+1==y) ) {
                    terkep[i][k]=map[i][k];
                    if (i==x && k==y) {
                        System.out.print("x  ");
                    }else{
                        System.out.print(terkep[i][k] + "  ");
                    }
                }else{
                    System.out.print(terkep[i][k]+"  ");
                }
            }
            System.out.println();
        }



    }

    /**
     * A terkep letre hozasa.Es egy kicsi magyarazata.
     */
    public   void letrehoz(){

        System.out.println("--------------EZ A TÉRKÉPED------------------");
        System.out.println("----------'X' JELÖLI AHOL ÁLLSZ--------------");
        System.out.println("-'inv' BEÍRÁSÁVAL MEGNÉZHETED AZ INVENTORIDAT-");
        for(int i=0;i<map.length;i++) {
            for (int k =0;k<map.length;k++) {
                if (((i+1==x || i-1==x)&& k==y) || ((k+1==y || k-1==y) && i==x) ||(k==y && i==x) ||(i-1==x && k-1==y) || (i+1==x && k-1==y) ||(i-1==x && k+1==y) || (i+1==x && k+1==y) ) {
                    terkep[i][k]=map[i][k];
                    if (i==x && k==y){
                        System.out.print("x  ");
                    }else {
                        System.out.print(terkep[i][k] + "  ");
                    }

                }else {
                    terkep[i][k]="?";
                    System.out.print(terkep[i][k]+"  ");
                }

            }
            System.out.println();
        }
    }

    /**
     * Lefele mozgas megvalositasa.Ervenyes helyre lehet csak lepni.<br>
     * felfedezopos frissítese.Felfedezo helye.Energiaja és aranyanak jelenlegi ertek ki irasa.
     */
    public  void le(){
        switch (map[x+1][y]){
            case 1:
            case 2:
            case 3:
            case 11:
            case 12:
            case 13:
            case 14:
                System.out.println("ERRE A HELYRE NEM LÉPHETSZ.MENJ MÁSIK IRÁNYBA");
                break;
            default:
                x = x + 1;
                felfedezopos = map[x][y];
                if (felderito) {
                    bonuszLatokor();
                }else map();
                System.out.println("Jelenleg " + felfedezopos + "-on álsz.Energiad: "+Game.getEn().getEnergia()+" Aranyad: "+Game.getEn().getArany());
                System.out.println("Csapat:"+Game.getEn().getCsapat());
        }
    }

    /**
     * Felfele mozgas megvalositasa.Ervenyes helyre lehet csak lepni.<br>
     * felfedezopos frissitese.Felfedezo helye.energiaja és aranyanak jelenlegi ertek ki irasa.
     */
    public  void fel(){
        switch (map[x-1][y]){
            case 1:
            case 2:
            case 3:
            case 11:
            case 12:
            case 13:
            case 14:
                System.out.println("ERRE A HELYRE NEM LÉPHETSZ.MENJ MÁSIK IRÁNYBA");
                break;
            default:
                x = x - 1;
                felfedezopos = map[x][y];
                if (felderito) {
                    bonuszLatokor();
                }else map();
                System.out.println("Jelenleg " + felfedezopos + "-on álsz.Energiad: "+Game.getEn().getEnergia()+" Aranyad: "+Game.getEn().getArany());
                System.out.println("Csapat:"+Game.getEn().getCsapat());
        }

    }

    /**
     * Balra mozgas megvalositasa.Ervenyes helyre lehet csak lepni.<br>
     * felfedezopos frissitese.Felfedezo helye.energiaja és aranyanak jelenlegi ertek ki irasa.
     *
     */
    public void balra(){
        switch (map[x][y-1]){
            case 1:
            case 2:
            case 3:
            case 11:
            case 12:
            case 13:
            case 14:
                System.out.println("ERRE A HELYRE NEM LÉPHETSZ.MENJ MÁSIK IRÁNYBA");
                break;
            default:
                y = y - 1;
                felfedezopos = map[x][y];
                if (felderito) {
                    bonuszLatokor();
                }else map();
                System.out.println("Jelenleg " + felfedezopos + "-on álsz.Energiad: "+Game.getEn().getEnergia()+" Aranyad: "+Game.getEn().getArany());
                System.out.println("Csapat:"+Game.getEn().getCsapat());
        }
    }

    /**
     * Jobbra mozgas megvalositasa.Ervenyes helyre lehet csak lepni.<br>
     * felfedezopos frissitese.Felfedezo helye.energiaja és aranyanak jelenlegi ertek ki irasa.
     */
    public void jobbra(){
        switch (map[x][y+1]){
            case 1:
            case 2:
            case 3:
            case 11:
            case 12:
            case 13:
            case 14:
                System.out.println("ERRE A HELYRE NEM LÉPHETSZ.MENJ MÁSIK IRÁNYBA");
                break;
            default:
                y = y +1;
                felfedezopos = map[x][y];
                if (felderito) {
                    bonuszLatokor();
                }else map();
                System.out.println("Jelenleg " + felfedezopos + "-on álsz.Energiad: "+Game.getEn().getEnergia()+" Aranyad: "+Game.getEn().getArany());
                System.out.println("Csapat:"+Game.getEn().getCsapat());
        }
    }

    /**
     * Megnezi jelenlegi mezöt amin all és ennek megfeleloen esemenyek törtenhetnek.Attol fügöel milyen elemen all.
     */
    public void allaspont(){
        switch (felfedezopos){
            case 0:
                Game.getHajo().hazautazas();
                dzsungelBolJon();
                break;
            case 6:
                dzsungel.dzsungel();
                break;
            case 5:
                oltarhozEr();
                break;
            case 4:
                dzsungelBolJon();
                barlanghozEr();
                break;
            case 8:
                dzsungelBolJon();
                faluValasztas();
                break;
            case 10:
                dzsungelBolJon();
                AranyPiramis piramis=new AranyPiramis();
                piramis.erkezes();
                break;
            default:
                dzsungelBolJon();
                break;
        }
    }

    /**
     * Ha barlanghoz er akkor megnezi hogy jart itt mar vagy meg nem.<br>
     * Ha igen akkor üres lesz a barlang.Ha nem akkor hozzaadja a bejarthelyekhez.<br>
     * Majd lertehot egy barlangot és meghívja a barlang fuggvenyet.
     */
    public void barlanghozEr(){
        boolean bennevan=false;
        if (bejartHelyek.size()==0){
            Barlang barlang=new Barlang();
            bejartHelyek.put(x,y);
            barlang.barlang();
        }else {
            for (java.util.Map.Entry<Integer, Integer> elem : bejartHelyek.entrySet()) {
                if (elem.getKey() == x && elem.getValue() == y) {
                    bennevan = true;
                    break;
                }
            }
            if (bennevan){
                System.out.println("-------------------");
                System.out.println("MÁR ÜRES A BARLANG");
                this.lepes();
            }else{
                Barlang barlang = new Barlang();
                bejartHelyek.put(x, y);
                barlang.barlang();
            }
        }
    }

    /**
     * Ha oltárhoz er akkor megnézi hogy jart itt mar vagy meg nem.<br>
     * Ha igen akkor üres lesz a oltar.Ha nem akkor hozzáadja a bejarthelyekhez.<br>
     * Majd lertehot egy oltart és meghívja az oltar fuggvenyet.
     */
    public void oltarhozEr() {
        boolean bennevan=false;
        if (bejartHelyek.size() == 0) {
            Oltar oltar=new Oltar();
            bejartHelyek.put(x, y);
            oltar.oltar();
        } else {
            for (java.util.Map.Entry<Integer, Integer> elem : bejartHelyek.entrySet()) {
                if (elem.getKey() == x && elem.getValue() == y) {
                    bennevan=true;
                    break;
                }
            }
            if (bennevan){
                System.out.println("-------------------");
                System.out.println("MÁR ÜRES A AZ OLTÁR");
                lepes();
            }else{
                Oltar oltar=new Oltar();
                bejartHelyek.put(x, y);
                oltar.oltar();
            }
        }
    }

    /**
     * Megnezi melyk falura leptünk ra es azt hivja meg.
     */
    public void faluValasztas(){
        if (x==2 && y==2){
            this.falu1.ajánlás();
        }
        if (x==4 &&y==9){
            this.falu2.ajánlás();
        }
        if (x==6 &&y==2){
            this.falu3.ajánlás();
        }
        if (x==9&&y==1){
            this.falu4.ajánlás();
        }
    }

    /**
     * Ha valaki dzsungelboll jönne ki akkor a mozgasköltsege vissza all eredetire és a junglebanvan hamis lessz.
     */
    public void dzsungelBolJon(){
        dzsungel.setOsszefugDzsungel(0);
        if (dzsungelbevan){
            Game.getEn().setMozgaskolt(0.5);
            dzsungelbevan=false;
        }
    }


}
