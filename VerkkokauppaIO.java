import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Luokkaa käytetään verkkokaupan tietojen tallentamiseen
 * ja lataamiseen tietovarastosta.
 *
 * @author Erkki
 */
public class VerkkokauppaIO {

    public static void main(String[] args) {
        // Tähän voi kirjoittaa koodia, jolla testata
        // kirjoitus- ja lukumetodien toimintaa helposti
    }

    private static final String EROTIN = ";";

    public static void kirjoitaTiedosto(String tiedostonNimi,
                                        String sisalto) {
        try (PrintWriter tiedosto = new PrintWriter(tiedostonNimi)) {
            tiedosto.write(sisalto);
        } catch (FileNotFoundException e) {
            System.out.println("Tapahtui virhe: " + e);
        }
    }

    /**
     * Lukee annetun nimisen tiedoston sisällön ja palauttaa sen listassa.
     * Jokainen listan alkio vastaa yhtä tiedoston riviä
     *
     * @param tiedostonNimi luettavan tiedoston nimi
     * @return tiedoston sisällön listana
     */
    public static ArrayList<String> lueTiedosto(String tiedostonNimi) {
        ArrayList<String> data = new ArrayList<>();
        try (Scanner lukija = new Scanner(new File(tiedostonNimi))) {
            while (lukija.hasNextLine()) {
                data.add(lukija.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Tapahtui virhe: " + e);
        }
        return data;
    }

    /**
     * Kirjoittaa asiakaslistan annetun nimiseen tiedostoon.
     *
     * @param asiakasLista  lista kirjoitettavista asiakkaista.
     * @param tiedostonNimi kirjoitettavan tiedoston nimi
     */
    public static void kirjoitaAsiakkaat(ArrayList<Asiakas> asiakasLista,
                                         String tiedostonNimi) {
        String data = "";
        for (Asiakas asiakas : asiakasLista) {
	    if( asiakas instanceof KantaAsiakas){
		data = String.valueOf(((KantaAsiakas) asiakas).getAlennusProsentti())+VerkkokauppaIO.EROTIN;
	    }
	    else{
		data = "0"+VerkkokauppaIO.EROTIN;
	    }
            data += asiakaskgetData(VerkkokauppaIO.EROTIN);
            data += "\n";
        }
        // Poistetaan viimeinen "turha" rivinvaihto
        if (data.length() > 0) {
            data = data.substring(0, data.length() - 1);
        }
        kirjoitaTiedosto(tiedostonNimi, data);
    }

    /**
     * Palauttaa uuden asiakasolion annetun datarivin perusteella.
     * Rivillä tulee olla asiakasnumero, nimi ja tehdyt ostot tässä
     * järjestyksessä erotettuna merkillä
     * <code>VerkkokauppaIO.EROTIN</code>
     *
     * @param data datarivi, josta tiedot parsitaan
     * @return uuden Asiakas-olion dataan perustuen
     */
    public static Asiakas parsiAsiakas(String data) {
        String[] tiedot = data.split(VerkkokauppaIO.EROTIN);
        // Tässä vaiheessa tulee tietää tietojen järjestys
	String alennus = Double.parseDouble(tiedot[0]);
        String asNro = tiedot[1];
        String nimi = tiedot[2];
        double ostot = Double.parseDouble(tiedot[3]);
	if(alennus == 0){
        	return new Asiakas(asNro, nimi, ostot);
	}
	else{
		return new KantaAsiakas(asNro, nimi, ostot, alennus);
	}
    }

    /**
     * Metodi lukee asiakkaat annetun nimisestä tiedostosta ja
     * palauttaa sisällön mukaisen listan Asiakas-olioita.
     *
     * @param tiedostonNimi luettavan tiedoston nimi
     * @return tiedostosta luetut asiakasoliot listana
     */
    public static ArrayList<Asiakas> lueAsiakkaat(String tiedostonNimi) {
        ArrayList<Asiakas> asiakkaat = new ArrayList<>();
        ArrayList<String> data = lueTiedosto(tiedostonNimi);
        for (String adata : data) {
            Asiakas as = parsiAsiakas(adata);
            asiakkaat.add(as);
        }
        return asiakkaat;
    }

    /**
     * Kirjoittaa tuotelistan annetun nimiseen tiedostoon.
     *
     * @param tuotelista    lista tuotteista
     * @param tiedostonNimi kirjoitettavan tiedoston nimi
     */
    public static void kirjoitaTuotteet(ArrayList<Tuote> tuotelista, String tiedostonNimi) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(
                             new FileOutputStream(tiedostonNimi))) {
            oos.writeObject(tuotelista);
        } catch (IOException e) {
            System.out.println("Tapahtui virhe: " + e);
        }
    }

    /**
     * Lukee tuotelistan tiedostosta
     *
     * @param tiedostonNimi tiedoston nimi
     * @return listan tuotteita
     */
    public static ArrayList<Tuote> lueTuotteet(String tiedostonNimi) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(tiedostonNimi))) {
            ArrayList<Tuote> tlista = (ArrayList<Tuote>) ois.readObject();
            return tlista;
        } catch (IOException e) {
            System.out.println("Tapahtui virhe: " + e);
        } catch (ClassNotFoundException e) {
            // Tämä virhe tulee, jos luettu tieto ei ole yhteensopiva
            // sen luokan kanssa, jonka tyyppiseksi se yritetään muuntaa
            System.out.println("Tapahtui virhe: " + e);
        }
        return null;
    }
    /**
     * Kirjoitaa myyjat tiedostoon
     *
     * @param myyjalista lista myyjistä
     * @param tiedostonNimi tiedoston nimi
     */
    public static void kirjoitaMyyjat(ArrayList<Myyja> myyjalista, String tiedostonNimi){
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(
                             new FileOutputStream(tiedostonNimi))) {
            oos.writeObject(myyjalista);
        } catch (IOException e) {
            System.out.println("Tapahtui virhe: " + e);
        }

    }
    /**
     * Lukee myyjalistan tiedostosta
     *
     * @param tiedostonNimi tiedoston nimi
     * @return myyjalista lista myyjistä
     */
    public static ArrayList<Myyja> lueMyyjat(String tiedostonNimi){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(tiedostonNimi))) {
            ArrayList<Myyja> myyjalista = (ArrayList<Myyja>) ois.readObject();
            return myyjalista;
        } catch (IOException e) {
            System.out.println("Tapahtui virhe: " + e);
        } catch (ClassNotFoundException e) {
            System.out.println("Tapahtui virhe: " + e);
        }
        return null;

    }
    
    /**
     * Kirjoittaa ostotapahtumat tiedostoon 
     *
     * @param ostotapahtumalista lista ostotapahtumista
     * @param tiedostonNimi tiedoston nimi
     */
    public static void kirjoitaOstotapahtumat(ArrayList<Ostotapahtuma> ostotapahtumalista, String tiedostonNimi){
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(
                             new FileOutputStream(tiedostonNimi))) {
            oos.writeObject(ostotapahtumalista);
        } catch (IOException e) {
            System.out.println("Tapahtui virhe: " + e);
        }

    }

    /**
     * Kirjoitaa ostotapahtumat tiedostoon
     *
     * @param tiedostonNimi tiedoston nimi
     * @return ostotapahtumalista lista ostotapahtumista
     */
    public static ArrayList<Ostotapahtuma> lueOstotapahtumat(String tiedostonNimi){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(tiedostonNimi))) {
            ArrayList<Ostotapahtuma> ostotapahtumalista = (ArrayList<Ostotapahtuma>) ois.readObject();
            return ostotapahtumalista;
        } catch (IOException e) {
            System.out.println("Tapahtui virhe: " + e);
        } catch (ClassNotFoundException e) {
            System.out.println("Tapahtui virhe: " + e);
        }
        return null;
    }
}
