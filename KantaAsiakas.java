public class KantaAsiakas extends Asiakas{
    private int alennusprosentti;

    public KantaAsiakas(String asiakasNumero, String nimi,
                        double ostojaTehty, int alennusprosentti) {
        super(asiakasNumero, nimi, ostojaTehty);
        this.alennusprosentti = alennusprosentti;
    }

    @Override
    public int getAlennusprosentti() {
        return super.getAlennusprosentti() + alennusprosentti;
    }

    public void setAlennusprosentti(int alennusprosentti) {
        this.alennusprosentti = alennusprosentti;
    }

    @Override
    public String toString() {
        return super.toString() + ", alennusprosentti: " + alennusprosentti;
    }
}
