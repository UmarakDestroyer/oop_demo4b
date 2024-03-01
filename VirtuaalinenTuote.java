public class VirtuaalinenTuote extends Tuote {

    public VirtuaalinenTuote(String nimi, double hinta) {
        super(nimi, -1, hinta);
    }

    @Override
    public int getSaldo() {
        // Palauttaa aina luvun 1000, koska tuotetta on 
        // aina varastossa riittävästi mihin tahansa
        // tilaukseen
        return 1000;
    }

    @Override
    public String toString() {
        return super.getNimi() + " (" + super.getHinta() + "), virtuaalinen tuote.";
    }
}