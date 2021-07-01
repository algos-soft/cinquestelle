package it.algos.base.wrapper;

/**
 * Wrapper di due stringhe..
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 4-mar-2008 ore  21:09
 */
public final class TreStringhe {

    /**
     * variabile di tipo DueStringhe
     * le prime due stringhe
     */
    private DueStringhe primeDue;

    /**
     * variabile di tipo testo
     * terza stringa
     */
    private String terza;


    /**
     * Costruttore senza parametri.
     */
    public TreStringhe() {
        /* rimanda al costruttore della superclasse */
        super();
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con una stringa <br>
     *
     * @param precedente stringa
     */
    public TreStringhe(String precedente) {
        /* invoca il metodo sovrascritto della classe */
        this(precedente, "", "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con due stringhe <br>
     *
     * @param precedenti - l'istanza con le precedenti stringhe
     */
    public TreStringhe(DueStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(), precedenti.getSeconda(), "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     *
     * @param prima - prima stringa
     * @param seconda - seconda stringa
     * @param terza - terza stringa
     */
    public TreStringhe(String prima, String seconda, String terza) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        DueStringhe primeDue;
        primeDue = new DueStringhe(prima, seconda);

        this.setPrecedenti(primeDue);
        this.setTerza(terza);
    }// fine del metodo costruttore completo


    /**
     * metodo getter
     *
     * @return primeDue
     */
    private DueStringhe getPrecedenti() {
        return primeDue;
    } // fine del metodo getter


    /**
     * metodo setter
     *
     * @param primeDue stringhe
     */
    private void setPrecedenti(DueStringhe primeDue) {
        this.primeDue = primeDue;
    } // fine del metodo setter


    /**
     * metodo getter
     *
     * @return due stringhe
     */
    public DueStringhe getDue() {
        return this.getPrecedenti();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return prima
     */
    public String getPrima() {
        return this.getPrecedenti().getPrima();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return seconda
     */
    public String getSeconda() {
        return this.getPrecedenti().getSeconda();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return terza
     */
    public String getTerza() {
        return this.terza;
    } // fine del metodo getter


    /**
     * metodo setter
     *
     * @param prima - prima stringa
     */
    public void setPrima(String prima) {
        this.getPrecedenti().setPrima(prima);
    } // fine del metodo setter


    /**
     * metodo setter
     *
     * @param seconda - seconda stringa
     */
    public void setSeconda(String seconda) {
        this.getPrecedenti().setSeconda(seconda);
    } // fine del metodo setter


    /**
     * metodo setter
     *
     * @param terza - terza stringa
     */
    public void setTerza(String terza) {
        this.terza = terza;
    } // fine del metodo setter


}// fine della classe