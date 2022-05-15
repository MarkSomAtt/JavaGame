package Felfedezo;


import Csapat.Kereskedo;

import Csapat.Szamar;
import Targyak.Elelmiszer.*;
import Targyak.Targy;
import jatek.Game;
import java.util.*;
import java.util.Scanner;

/**
 * Ez a felfedezo karakternek az osztalya és minden függösege.Ahol mgevannak valasitva a felfedezo tevekenysegei.
 *A "mozgaskolt" adat tag a mozgasnak jelenlegi költseget tarolja.Az "enegria" ajelenlegi energiat.
 * Az "arany"jelenglegi aranyat,a "slotok" ahazsnálatban levo inventory slotokat.Az "inv" inventory.
 * A "fuggoseg" van e valaki aki függö jelenleg.A "kereskedoB" akereskedo altal adott banusz erteke.
 * A "csapat" -ba tarolom a csapatomban levo emberek nevet.A "voszony"-ba aviszony szintet.
 * A "lepes" a megtet lepesek szama."Whiskey" egymas után megivott Whiskey-k szama.
 * "Kabitoszer" egymás után megevett kabitoszerek szama."katonaBonusz" a katona a cspatban vane.
 * "samanBonusz" saman csapatban vane. "fuggolepes" függokent hany lepest tett meg. "aFuggo" a függo neve.
 * "leserult" levan-e serülve valaki."serult" a serült neve."hirnev" jelenlegi hirnev
 *
 */
public class Felfedezo {
    private  double mozgaskolt;
    private  double energia;
    private  int arany;
    private  int slotok;
    private  List<Targy> inv;
    private boolean fugoseg;
    private int kereskedoB;
    private  List<String> csapat;
    private int viszony;
    private int whiskey;
    private int lepes;
    private int kabitoszer;
    private boolean katonaBonusz;
    private boolean samanBonusz;
    private int fuggoLepes;
    private String[] aFuggo;
    private static Scanner scan = new Scanner(System.in);
    private boolean leSerult;
    private String serult;
    private int hirnev;

    public Felfedezo() {
        this.hirnev=0;
        this.csapat=new ArrayList<>();
        this.leSerult=false;
        this.serult="";
        this.aFuggo=new String[2];
        this.lepes=0;
        this.viszony=3;
        this.mozgaskolt = 1;
        this.energia = 100;
        this.arany = 250;
        this.slotok=0;
        this.inv=new ArrayList<>();
        this.fugoseg=false;
        this.kereskedoB=0;
        this.katonaBonusz=false;
        this.samanBonusz=false;

    }

    public void setFugoseg(boolean fugoseg) {
        this.fugoseg = fugoseg;
    }

    public void setfuggoLepes(int fuggoLepes) {
        this.fuggoLepes = fuggoLepes;
    }

    public void setaFuggo(String[] aFuggo) {
        this.aFuggo = aFuggo;
    }

    public void setleSerult(boolean leSerult) {
        this.leSerult = leSerult;
    }

    public void setSerult(String serult) {
        this.serult = serult;
    }

    public int getViszony() {
        return viszony;
    }

    /**
     * Viszony növelese a samannal és oltarnal hasznalt
     * @param viszony ertek amivel növelem
     */
    public void setViszony(int viszony) {
        this.viszony += viszony;
    }

    public  List<String> getCsapat() {
        return this.csapat;
    }

    public void setkereskedoB(int kereskedoB) {
        this.kereskedoB = kereskedoB;
    }

    /**
     * Mozgasköltseg növelese százalékosan külömbözö esemenyek hatasara.
     * @param mozgaskolt növeles erteke
     */
    public void setMozgaskolt(double mozgaskolt) {
        this.mozgaskolt =this.mozgaskolt* mozgaskolt;
    }

    public int getkereskedoB() {
        return kereskedoB;
    }

    public double getEnergia() {
        return energia;
    }

    public int getArany() {
        return arany;
    }

    /**
     * A szamar altam meghívott fügvény.A jelenlegi szlotokbol elvesz igy több hely lesz.
     * @param slotok a slotok növelesi erteke
     */
    public void bonuszSlotok(int slotok) {
        this.slotok =this.slotok- slotok;
    }

    public List<Targy> getInv() {
        return inv;
    }

    public int getHirnev() {
        return hirnev;
    }

    /**
     * katasztrofa eseten energia csökkentese.Ha kevesebb mont0 lenne akkor 0 lesz.
     * @param energia ertek amivel csökkentem
     */
    public void kataszrofaEnergia(int energia) {
        if (this.energia-energia<0){
            this.energia=0;
        }else{
            this.energia-=energia;
        }
    }

    /**
     * felfedezo hirnevenek növelese.
     * @param szam ertek amivel növelnem.
     */
    public void hirnevNoveles(int szam){
        this.hirnev+=szam;
    }

    /**
     * Ez a fügveny fogja ki iratni az inventorinkat.<br>
     * Es megkerdzni hogy eszel e valamit, "miteszel" fügveny meghivasaval.<br>
     * Ha nincs semmi az inventoridban akkor "ÜRES AZ INVENTORID" ir ki.
     * Majd a ujjab lepest kerdez be.
     */
    public void inventory(){
        System.out.println("---------INVENTORID---------");
        Set<Targy> distinct = new HashSet<>(this.inv);
        Set<String> volt=new HashSet<>();
        if(this.inv.size()==0) {
            System.out.println("ÜRES AZ INVENTORID");
        }else{
            for (Targy s : distinct) {
                if (volt.contains(s.getNev())){
                }else{
                    volt.add(s.getNev());
                    System.out.println(s.getNev() + ": " + this.inv.stream().filter(t -> t.getNev().equals(s.getNev())).count()+" Érték: "+(s.getErtek()+this.kereskedoB));

                }
            }
            mitEszel();

        }



        Game.getMap().lepes();
    }

    /**
     * Meg nezi egy tergy neve alapjan hogy van e ilyen az inventoriban ha nincs -1 ell ter vissza.<br>
     * Ha van azzal a hellyel ter vissza hol van az inventoriba.
     * @param nev a keresendő targy neve
     * @return ha nincs -1 havan a helye.
     */
    public int vaneInvben(String nev){
        for (int i=0;i<this.inv.size();i++){
            if (this.inv.get(i).getNev().equals(nev)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Megkerdezi hogy mit szeretnel enni majd ha ez helyes akkor megeszi az "eszik fügvény"-el.<br>
     * Ha nincs ilyen eteled akkor "NINCS ILYEN ELELMISZERED"-et ir ki.<br>
     * Ha nem elmiszer nevet adod meg akkor helytelen a parancs és ujra probalkozhatsz.A "nem"-el tovabb mehetsz.
     */
    public void mitEszel(){
        String etel;
        System.out.println("HA ENNI SZERETNÉL IRD BE HOGY MIT PL.:'Gyümölcs'");
        System.out.println("HA NEM, IRD BE HOGY 'nem'");
        etel= scan.nextLine();
        switch (etel){
            case "Gyümölcs":
                if (vaneInvben("Gyümölcs")!=-1){
                    eszik((Elelmiszer) this.inv.get(vaneInvben("Gyümölcs")));
                }else System.out.println("NINCS ILYEN ELELMISZERED");
                break;
            case "Csokoládé":
                if (vaneInvben("Csokoládé")!=-1){
                    eszik((Elelmiszer) this.inv.get(vaneInvben("Csokoládé")));
                }else System.out.println("NINCS ILYEN ELELMISZERED");
                break;
            case "Hús":
                if (vaneInvben("Hús")!=-1){
                    eszik((Elelmiszer) this.inv.get(vaneInvben("Hús")));
                }else System.out.println("NINCS ILYEN ELELMISZERED");
                break;
            case "Kábitoszer":
                if (vaneInvben("Kábitoszer")!=-1){
                    eszik((Elelmiszer) this.inv.get(vaneInvben("Kábitoszer")));
                }else System.out.println("NINCS ILYEN ELELMISZERED");
                break;
            case "Whiskey":
                if (vaneInvben("Whiskey")!=-1){
                    eszik((Elelmiszer) this.inv.get(vaneInvben("Whiskey")));
                }else System.out.println("NINCS ILYEN ELELMISZERED");
                break;
            case "nem":
                Game.getMap().lepes();
                break;
            default:
                System.out.println("!!!!!ÉRVÉNYTELEN PARANCS!!!!");
                break;
        }
    }

    /**
     * Minden mozgasnal megivodik ez a fügveny.Itt tesztelödik le a függoseg is.
     * Meg törtenik itt az energia levetele is.Meg ha serüles van annak a lepesei és hatása is itt folyik.
     * Ha a függo fogyasz a kivalto anyagbol akkor a függéese megmarad csak a fuggolepes lesz 0.
     */
    public void mozdul(){
        if (this.fuggoLepes>30 &&Math.random()<=0.1){
            if (aFuggo[0].equals("felfedező")){
                Game.vesztettel();
            }else {
                csapatElhagyas(aFuggo[0]);
                this.fugoseg=false;
            }
        }
        if (this.energia==0 ){
            nullaEnergia();
        }
        this.lepes+=1;
        if (this.energia-mozgaskolt<0){
            this.energia=0;
        }else {
            this.energia -= this.mozgaskolt;
        }
        if (this.fugoseg){
            this.fuggoLepes+=1;
        }
        if (this.leSerult && Math.random()<=0.05){
            int ki=0;
                if (this.serult.equals("en")){
                    Game.vesztettel();
                }else {
                    for (int i=0;i<this.csapat.size();i++){
                        if(this.csapat.get(i).equals(this.serult)){
                            ki=i;
                        }
                    }
                    csapatElhagyas(this.csapat.get(ki));
                }
        }
    }

    /**
     *Ez a fügveny hivódik meg ha valaki elhadja a csapatot.Kikapcsolja a bunuszat a csapattársnak aki elment.
     * Es csökkenti a mozgasköltseget.
     * @param nev aki elhagyja a csapatot
     */
    public  void csapatElhagyas(String nev){
        if(nev.equals("Katona")){
           this.katonaBonusz=false;
            this.csapat.remove("Katona");
        }
        if(nev.equals("Kereskedő")){
            Kereskedo.bonuszNincs(this);
            this.csapat.remove("Kereskedő");
        }
        if(nev.equals("Szamár")){
            Szamar.bonusNincs(this);
            this.csapat.remove("Szamár");
        }
        if(nev.equals("Sámán")){
            this.samanBonusz=false;
            this.csapat.remove("Sámán");
        }
        if (nev.equals("Felderítő")){

            Game.getMap().setFelderito(false);
            this.csapat.remove("Felderítő");
        }
        if (nev.equals("Bölcs")){
            this.csapat.remove("Bölcs");
        }
        this.mozgaskolt=this.mozgaskolt/1.15;
        System.out.println(nev+"Elhhagyta a csapatot!!!!");
    }

    /**
     * Katasztofa eseten leserülhet valaki.Eltarolja a nevet és igazra alítja a leSerult valtozot.
     * Ki irja hogy ki serült le.
     * @param nev a serült neve
     */
    public void leserul(String nev){
        this.leSerult=true;
        this.serult=nev;
        System.out.println("!!!"+this.leSerult+"Lesérült!!");

    }

    /**
     * Inventoriba berakast segiti.Megszamolja hany van benne.Ez alapjan növeli a szlotokat ha kell.<br>
     * Kivéve ha kincs akkor mindig növeli.Ha nincs ilyen targyunk akkor is növeli.
     * @param targy a targy amit beszeretnenk rakni
     */
    public  void invberak(Targy targy){
        int szamlalo=0;

        if (vaneInvben(targy.getNev())!=-1) {
            for (int i = 0; i < this.inv.size(); i++) {
                if (this.inv.get(i).getNev().equals(targy.getNev())){
                    szamlalo++;
                }

            }
            if (szamlalo%7==0){
                this.slotok=this.slotok+1;
                slotell();
            }
        }else {
            this.slotok+=1;
            slotell();
        }
        if (targy.getNev().equals("Kincs")){
            this.slotok+=1;
            slotell();
        }
        this.inv.add(targy);


    }

    /**
     * Itt kapja meg a bonuszokat ha van.Ellenőrzi a whiskey és kábítószer fogyasztások számát<br>
     * és ha kell függő vé tesz valakit.<br>
     * Majd az energiat maximu 100-ra feltölti.Elhasznál fügvény segitségével kitorlé az ételt.
     * @param etel amit enni szeretnénk
     */
    public void eszik(Elelmiszer etel){
        double energiaTolt=0;
        if (etel.getNev().equals("Kábítószer")&&this.samanBonusz){
            energiaTolt= etel.getEnergiatoltes()*1.2;
        }else if (etel.getNev().equals("Whiskey")&& this.katonaBonusz){
            energiaTolt=etel.getEnergiatoltes()*1.2;
        }else energiaTolt=etel.getEnergiatoltes();

        if (fugoseg && aFuggo[1].equals(etel.getNev())){
            this.fuggoLepes=0;
        }

        if (this.energia+energiaTolt>100){
            this.energia=100;
        }else{
            this.energia+=energiaTolt;
        }
        if (etel.getNev().equals("Kábítószer")){
            this.kabitoszer+=1;
        }else this.kabitoszer=0;

        if (etel.getNev().equals("Whiskey")){
            this.whiskey+=1;
        }else this.whiskey=0;
        this.elhasznál(etel);
        if (this.whiskey==2 &&etel.getNev().equals("Whiskey") ){
            kiaFuggo("Whiskey");
        }
        if (this.kabitoszer==2 &&etel.getNev().equals("Kánítószer") ){
            kiaFuggo("Kábítószer");
        }

    }

    /**
     * A inventoryba berakásnáll ellenőrzi hogy 8 nál többb slotot használunk-e.<br>
     * Ha igen akkor növeli a mozgaskötséget.
     */
    public  void slotell(){
        if (this.slotok>8){
                this.mozgaskolt=this.mozgaskolt*1.2;
        }
    }

    /**
     * A cspatarsat megvesszük leveszi az aranyat ha van elég és növeli a mozgáasköltséget
     * @param ar csapattars ara.
     */
    public void csapatarsatVasarol(int ar){
        if (this.arany-ar<0){
            System.out.println("!!!!!!!!!!!!!");
            System.out.println("NINCS ELÉG ARANYAD");
            System.out.println("!!!!!!!!!!!!!");

        }else{
            this.arany-=ar;
            csapatMozgasNovel();
        }

    }

    /**
     * Csapattar felvételkor a megfelelő bonusz aktiválja név szerint.
     * @param nev csapattárs neve
     */
    public void bonuszok(String nev){
        if(nev.equals("Katona")){
            this.katonaBonusz=true;
        }
        if(nev.equals("Kereskedő")){
            Kereskedo.bonusz(this);
        }
        if(nev.equals("Szamár")){
            Szamar.bonus(this);
        }
        if(nev.equals("Sámán")){
            this.samanBonusz=true;
        }
        if (nev.equals("Felderítő")){
            Game.getMap().setFelderito(true);
        }

    }

    /**
     * Ha van elég aranyad leveszi ha nincs figelmeztet.Invberak -kal elrakja atárgyat ha van.
     * @param targy megvenni kivant targy
     */
    public void targyatVasarol(Targy targy){
        if (this.arany-(targy.getAr()-kereskedoB)<0){
            System.out.println("!!!!!!!!!!!!!");
            System.out.println("NINCS ELÉG ARANYAD");
            System.out.println("!!!!!!!!!!!!!");

        }else{
            this.arany-=(targy.getAr()-kereskedoB);
            invberak(targy);
        }

    }

    /**
     * Tárgyak elhasználása és a szlotok igazítása ehez plusz a muzgás költség aslotok után ha kell.
     * @param targy elhasználandó tárgy
     */
    public void elhasznál(Targy targy){
        int szamlalo=0;
        for (int i = 0; i < this.inv.size(); i++) {
            if (this.inv.get(i)==targy){
                szamlalo++;
            }
        }
        if (targy.getNev().equals("Kincs")){
            if (this.slotok>8) {
                this.mozgaskolt = this.mozgaskolt / 1.2;
            }
            this.slotok-=1;
            this.inv.remove(targy);
        }else if (szamlalo%7==1){
            if (this.slotok>8) {
                this.mozgaskolt = this.mozgaskolt / 1.2;
            }
            this.slotok-=1;
            this.inv.remove(targy);
        }else this.inv.remove(targy);



    }

    /**
     * Megnézi hogy van ilyen tárgyad ha van megkapod az értékét.A slotokat+mozgasköltséget igazítja.
     * @param targy eladandó tárgy
     */
    public void elad(Targy targy){
        int szamlalo=0;
        if (!this.inv.contains(targy)){
            System.out.println("!!!!!!!NINCS ILYEN TÁRGYAD!!!!!!!!");
        }else{
            this.arany+=(targy.getErtek()+kereskedoB);
            for (int i = 0; i < this.inv.size(); i++) {
                if (this.inv.get(i)==targy){
                    szamlalo++;
                }
            }
            if (szamlalo%7==1){
                this.slotok-=1;
                if (this.slotok>8) {
                    this.mozgaskolt = this.mozgaskolt / 1.2;
                }
                this.inv.remove(targy);
            }else this.inv.remove(targy);
        }
    }

    /**
     * Csapattag felvétel esetén hívodik meg.Növeli a mozgásköltséget.
     */
    public void csapatMozgasNovel(){
        this.mozgaskolt=this.mozgaskolt*1.15;
    }

    /**
     * Ha nulla az energia akkor hívódik meg a mozgásban.Van esély rá hogy valaki elhadja a csapatot.<br>
     * ha üres a csapat akkor felfedező is elhagyhatja acsapatot ekkor játé vége.
     */
    public void nullaEnergia(){
        if (this.csapat.size()==0) {
           if (Math.random()<=0.08){
               Game.vesztettel();
           }

        }else{
            for (int i = 0; i < this.csapat.size(); i++) {
                if (Math.random() <= 0.08) {
                    switch (i) {
                        case 0:
                            csapatElhagyas(this.csapat.get(i));
                            break;
                        case 1:
                            csapatElhagyas(this.csapat.get(i));
                            break;
                        case 2:
                            csapatElhagyas(this.csapat.get(i));
                            break;

                    }
                }
            }
        }

    }

    /**
     * Föggés esetén megadja ki a föggő.
     * @param oka a függést kiváltó szer
     */
    public void kiaFuggo(String oka){
        int meret=this.csapat.size();
        if (meret!=0) {
            for (int i = 0; i < meret; i++) {
                if (Math.random() <= 0.15) {
                    switch (i) {
                        case 0:
                            this.fugoseg = true;
                            aFuggo[0] = this.csapat.get(i);
                            aFuggo[1] = oka;
                            break;
                        case 1:
                            this.fugoseg = true;
                            aFuggo[0] = this.csapat.get(i);
                            aFuggo[1] = oka;
                            break;
                        case 2:
                            this.fugoseg = true;
                            aFuggo[0] = this.csapat.get(i);
                            aFuggo[1] = oka;
                            break;

                    }

                }


            }
        }else{
                aFuggo[0] = "felfedező";
                aFuggo[1] = oka;
                this.fugoseg = true;
        }
        System.out.println(aFuggo[0]+" "+aFuggo[1] +" FÜGGŐLETT");

    }
    /**
     * pihenés 100-ra álítja az enegiát.
     */
    public void pihenes(){
        this.energia=100;
    }








}
