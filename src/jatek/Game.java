package jatek;

import Csapat.Bolcs;
import Felfedezo.Felfedezo;
import PajaElemek.Hajo;
import PajaElemek.Kikoto;
import Terkep.Map;
import java.util.*;


/**
 * Maga a játék kezdéséhez futtatható program.
 * Itt hozom létre a térképet("map") a felfedezőt("en") és a hajót is amivel haza lehez jönni.
 * Van egy köröket számoló változó("korok"),egy elenfeleket tároló Map("botok"),
 * és egy változó ami a vulkan/gejzír kitörés helyét tárolja
 * @version 1.0
 */
public class Game {

    private static boolean jatekban=false;
    private static Scanner scan = new Scanner(System.in);
    private static Map map;
    private static Felfedezo en;
    private static java.util.Map<String,Integer> botok;
    private static Hajo hajo;
    private static int korok=0;
    private static int[] kitoresHely;

    public static int[] getkitoresHely() {
        return kitoresHely;
    }

    public static Map getMap() {
        return map;
    }

    public static Felfedezo getEn() {
        return en;
    }

    public static Hajo getHajo() {
        return hajo;
    }

    /**
     * Ez ajáték indításához szükséges futtatható main.Létrehozom a felfedezöt és az ellenfeleket(botok).<br>
     * Ha a játékos igent ellindul ajatek fugvény mond amugy kilép aprogram.
     * @param args argumentumok
     */
    public static void main(String[] args) {
        String dontes;
        System.out.println("Írd azt hogy 'igen' ha szeretnél játszani.Bármi mást a kilépéshez. ");
        dontes = scan.nextLine() ;
        if(dontes.equals("igen")){
            en=new Felfedezo();
            botok=new HashMap<>();
            botok.put("Jancsi",0);
            botok.put("Bela",0);
            botok.put("Kari",0);
            hajo=new Hajo();

            jatekban = true;
            jatek();

        }
        else{
            System.out.println("Kihagytál egy jó játékot");
            System.exit(1);
        }


    }

    /**
     * Itt szomolom aköröket,létrehozom a mappot,meghívodik a csapattag választás.<br>
     * Létrehozok egy kikötőt majd meghívom a kinalatát ahol lehet vásárolni.<br>
     * Pályavalasztásnál el lehet dönteni hogy belvassa a pályát vagy enélkül induljon.<br>
     * Ha van bölcsa csapatban akkor +3 viszony.Majd elindul a lépés.
     */
    public static void jatek(){
        korok++;
        map=new Map();
        reset();
        csapatTagValasztas();
        Kikoto kikoto=new Kikoto();
        kikoto.kinalat();
        map.palyaValasztas();
        map.letrehoz();
        int hol=-1;
        for (int i=0; i<getEn().getCsapat().size();i++){
            if (getEn().getCsapat().get(i).equals("Bölcs")){
                hol=i;
            }
        }
        if (hol!=-1){
            Bolcs.bonus(getEn());
        }
        while(jatekban){
            map.lepes();

        }

    }

    /**
     * Minden kör elején lehet csapattársat választanod.Csak 3-csapattársad lehet és egy fajtábol csak egy.<br>
     * Ha nemet mondasz csapatár nélkül is indulhatsz.
     */
    public static void csapatTagValasztas(){
        if (Game.getEn().getCsapat().size()==3) {
            System.out.println("!!!!TELE VAN A CSAPATOD!!!!");
        }else {
            String dontes = "";
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("VÁLASZTHATSZ MAGADNAK EGY CSAPATTÁRSAT 150 ARANYÉRT.A KÖVETKEZÖK KÖZÜL:");
            System.out.println("KEZDÉSNEK 250 ARANYAD VAN");
            System.out.println("Katona(a Whiskey +20% energia)");
            System.out.println("Kereskedő (mindent kicsivel olcsóbban vehetünk és drágábban adhatunk el)");
            System.out.println("Szamár (+2 inventori slot)");
            System.out.println("ÍRD BE A NEVÉT AKIT VÁLASZTANÁL VAGY AZT HOGY 'nem' HA NEM SZERETNÉL CSAPATTÁRSAT.");
            dontes = scan.nextLine();
            switch (dontes) {
                case "Katona":
                case "Kereskedő":
                case "Szamár":
                    if (Game.getEn().getCsapat().contains(dontes)){
                        System.out.println("MÁR VAN ILYEN CSAPATTÁRSAD");
                        csapatTagValasztas();
                    }else {
                        getEn().getCsapat().add(dontes);
                        getEn().csapatarsatVasarol(150);
                        getEn().bonuszok(dontes);
                    }
                    break;
                case "nem":
                    System.out.println("Sok sikert Csapattárs nélkül!!");
                    break;
                default:
                    System.out.println("!!!!!ÉRVÉNYTELEN PARANCS!!!!");
                    csapatTagValasztas();
                    break;

            }
        }



    }

    /**
     * A körök elejénvissza álítódnak bizonyos értékek.Serülések megyógyulnak.Energia feltölt.<br>
     * Függőség eltűnik.
     */
    public static void reset(){
        getEn().pihenes();
        getEn().setaFuggo(new String[2]);
        getEn().setfuggoLepes(0);
        getEn().setFugoseg(false);
        getEn().setleSerult(false);
        getEn().setSerult("");
    }

    /**
     * Ha a felfedező valamilyen módon elhadja a csapatot,a jatéknak vége van.
     */
    public static void vesztettel(){
        System.out.println("------------------------------");
        System.out.println("----------VESZTETTÉL----------");
        System.out.println("------------------------------");
        System.exit(1);
    }

    /**
     * Körök végén ha van kincsed eladhatod őker vagy eladaományozhatod őket.
     * Majd meg mutatjuk a jelenlegi állást abotokkal szemben.Ha az 5-ik körben vagyunk akkor akinek a legtöbb
     * hírneve van ő lessz a legjobb felfedező.
     */
    public static void korVege(){
        System.out.println("------------------------------------------------------------------------");
        System.out.println("ELADHATOD A KINCSEIDET(ARANYÉRT) VAGY EL ADOMÁNYOZHATOD ÖKET (HÍRNÉVÉRT)");
        System.out.println("-------------------------'arany/hírnév'--------------------------------'");
        if (getEn().vaneInvben("Kincs")==-1 && getHajo().vaneKincs()==-1){
            System.out.println("NINCS KINCSED");
            if (korok<5){
                ranglista();

            }else{
                ranglista();
                jatekVege();
            }

        }else {
            if (getHajo().vaneKincs()!=-1){
                getEn().invberak(getHajo().getRaktar().get(getHajo().vaneKincs()));
                getHajo().getRaktar().remove(getHajo().vaneKincs());
            }
            String dontes = scan.nextLine();
            switch (dontes) {
                case "arany":
                    getEn().elad(getEn().getInv().get(getEn().vaneInvben("Kincs")));
                    korVege();
                case "hírnév":
                    getEn().elhasznál(getEn().getInv().get(getEn().vaneInvben("Kincs")));
                    getEn().hirnevNoveles(100);
                    korVege();
                default:
                    System.out.println("!!!ERVENYTELEN PARANCS!!!");
                    korVege();
            }
        }

    }

    /**
     * Ki írja a botok pontszámát ami random növekszik körönként 800-1200 ponttal.<br>
     *
     */
    public static void ranglista(){
        Random r=new Random();
        botok.get("Jancsi");
        botok.put("Jancsi",botok.get("Jancsi")+r.nextInt(400+1)  + 800);
        botok.put("Bela",botok.get("Bela")+r.nextInt(400+1)  + 800);
        botok.put("Kari",botok.get("Kari")+r.nextInt(400+1)  + 800);
        System.out.println("Felfedező: "+en.getHirnev());
        System.out.println("Jancsi: "+botok.get("Jancsi"));
        System.out.println("Bela: "+botok.get("Bela"));
        System.out.println("Kari: "+botok.get("Kari"));
        folytatas();


    }

    /**
     * Lehetőség folytatni vagy kilépni a játékból.Ha folytatás akkot meghívodik a jatek ujra.
     */
    public static void folytatas(){
        System.out.println("------------------------------------------------------------------");
        System.out.println("Szeretnél új felfedezést indítani vagy vége a játéknak('igen/nem')");
        String dontes=scan.nextLine();
        switch (dontes){
            case "igen":
                jatek();
                break;
            case "nem":
                System.out.println("KIHAGYTÁL EGY JÓ FELFEDEZÉST");
                System.exit(1);
                break;
            default:
                System.out.println("!!!ERVÉNYTELEN PARACS!!!");

        }
    }

    /**
     * 70% esély 45 energiát elveszteni.10% esély valaki elhadja a csapatot.Ha a felfedező egyedülvan és eldja a csapatot
     * akkor vége a játéknak.20% eséllyel valaki lesérül.
     */
    public static void katasztrofa(){
        int max=getEn().getCsapat().size();
        Random r=new Random();
        double esely=Math.random();
            if (esely<=0.7){
                getEn().kataszrofaEnergia(45);
            }else if (esely<=0.8 && esely>0.7){
                if (getEn().getCsapat().size()==0){
                    Game.vesztettel();
                }else {
                    int szam= r.nextInt((getEn().getCsapat().size()-1) );
                    getEn().csapatElhagyas(getEn().getCsapat().get(szam));
                }
            }else if (esely<0.8){
                if (getEn().getCsapat().size()==0){
                    getEn().leserul("en");
                    System.out.println("!!!LESÉRÜLT A FELFEDEZŐD");
              }else{
                    int szam= r.nextInt((getEn().getCsapat().size()-1) );
                    getEn().leserul(getEn().getCsapat().get(szam));
                    System.out.println("!!!LESÉRÜLT A "+getEn().getCsapat().get(szam));
            }
        }




    }

    /**
     * Akinek a legtöbb hirneve van az fog nyerni az 5. kör után.
     */
    public static void jatekVege(){
        int max=0;
        String nyertes="";
        for(java.util.Map.Entry<String, Integer> elem : botok.entrySet()) {
            if (max<elem.getValue()){
                nyertes= elem.getKey();
            }
        }
        if (getEn().getHirnev()>botok.get(nyertes)){
            nyertes="Felfedező";
        }
        System.out.println("------------------------------------");
        System.out.println("---A LEGJOBB FELFEDEZŐ: "+nyertes+"---");
        System.out.println("------------------------------------");
        System.exit(1);
    }

    /**
     * 35% hogy volkán fog kitörni és 65% hogy gejzír.A terkepen a vulkankitörést igazra alitom.
     */
    public static void atok(){
        double esely=Math.random();
        kitoresHely=holaKitores();
        Game.getMap().setVulkankitores(true);
        if (esely<=0.35){
            System.out.println("!!!!VULKÁN KITÖRÉS!!!!");
            vulkanKitor(kitoresHely);
            Game.getMap().lepes();
        }else {
            System.out.println("!!!!GEJZÍR KITÖRÉS!!!!");
            gejzirKitores(kitoresHely);
            Game.getMap().lepes();
        }

    }

    /**
     * meg heresi a legközelebbi hegyet és ezzel a pozícioval tér vissza.
     * @return a tömb első érteke a sot masodik oszlop.Ha nem talál min2 -1.
     */
    public static int[] holaKitores(){
        int x=Game.getMap().getX();
        int y=Game.getMap().getY();
        int[] pos=new int[2];
        for (int i=0;i<10;i++) {
            for (int sor = x - i;sor<x+i+1;sor++){
                for (int oszlop = y - i;oszlop<y+i+1;oszlop++){
                    if (Game.getMap().getMap()[sor][oszlop]==3){
                        pos[0]=sor;
                        pos[1]=oszlop;
                        return pos;
                    }
                }
            }
        }
        pos[0]=-1;
        pos[1]=-1;
        return pos;
    }

    /**
     * Lecseréli  a legközelebbi hegyet vulkánra.
     * @param pos legközelebbi hegy kordinátája
     */
    public static void vulkanKitor(int[] pos){
        int x=pos[0];
        int y=pos[1];
        int lepesek=Game.getMap().getLepesek();
        if (lepesek==0){
            Game.getMap().getMap()[x][y]=11;
        }/*else {
            for (int sor = x - lepesek; sor < x + lepesek + 1; sor++) {
                for (int oszlop = y - lepesek; oszlop < y + lepesek + 1; oszlop++) {
                    if (sor>=10){
                        sor=9;
                    }
                    if (oszlop>=10){
                        oszlop=9;
                    }
                    if (Game.getMap().getMap()[sor][oszlop]==3 ||Game.getMap().getMap()[sor][oszlop]==1||Game.getMap().getMap()[sor][oszlop]==2||Game.getMap().getMap()[sor][oszlop]==10){

                    }else {
                        Game.getMap().getMap()[sor][oszlop] = 12;
                    }
                }
            }
        }
        */
    }

    /**
     * Lecseréli a legközelebbi hegyet gejzírre.
     * @param pos legközelebbi hegy kordinátája
     */
    public static void gejzirKitores(int[] pos){
        int x=pos[0];
        int y=pos[1];
        int lepesek=Game.getMap().getLepesek();
        if (lepesek==0){
            Game.getMap().getMap()[x][y]=11;
        }/*else {
            for (int sor = x - lepesek; sor < x + lepesek + 1; sor++) {
                for (int oszlop = y - lepesek; oszlop < y + lepesek + 1; oszlop++) {
                    Game.getMap().getMap()[sor][oszlop]=12;
                }
            }
        }
     */
    }


}
