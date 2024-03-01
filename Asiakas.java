public class Asiakas {
    private String asiakasNumero;
    private String nimi;
    private double ostojaTehty;

    public Asiakas(String asiakasNumero, String nimi,
                   double ostojaTehty) {
        this.asiakasNumero = asiakasNumero;
        this.nimi = nimi;
        this.ostojaTehty = ostojaTehty;
    }

    public String getAsiakasNumero() {
        return asiakasNumero;
    }

    public void setAsiakasNumero(String asiakasNumero) {
        this.asiakasNumero = asiakasNumero;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public double getOstojaTehty() {
        return ostojaTehty;
    }

    public void setOstojaTehty(double ostojaTehty) {
        if (ostojaTehty >= 0) {
            this.ostojaTehty = ostojaTehty;
        }
    }

    public void lisaaOsto(double hinta) {
        if (hinta >= 0) {
            this.ostojaTehty += hinta;
        }
    }

    public int getAlennusprosentti() {
        if (ostojaTehty <= 200) {
            return 0;
        } else if (ostojaTehty <= 1000) {
            return 5;
        } else if (ostojaTehty <= 2500) {
            return 10;
        } else {
            return 15;
        }
    }

    /**
     * Palauttaa kaikki olion tiedot yhtenä merkkijonona.
     * Datapisteet on erotettu toisistaan annetulla erotinmerkillä.
     *
     * @return kaikki olion tiedot yhdessä merkkijonossa
     */
    public String getData(String erotinmerkki) {
        String data = asiakasNumero + erotinmerkki;
        data += nimi + erotinmerkki;
        data += ostojaTehty;

        return data;
    }

    @Override
    public String toString() {
        return nimi + " (" + asiakasNumero + "), ostoja tehty: " + ostojaTehty;
    }
}
