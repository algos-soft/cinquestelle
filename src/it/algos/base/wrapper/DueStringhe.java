package it.algos.base.wrapper;

/**
 * Wrapper di due stringhe.
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 4-mar-2008 ore  21:08
 */
public final class DueStringhe {

    /**
     * variabile di tipo testo
     */
    private String prima;

    /**
     * variabile di tipo testo
     * seconda stringa
     */
    private String seconda;


    /**
     * Costruttore senza parametri.
     */
    public DueStringhe() {
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
    public DueStringhe(String precedente) {
        /* invoca il metodo sovrascritto della classe */
        this(precedente, "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     *
     * @param prima -
     * @param seconda - seconda stringa
     */
    public DueStringhe(String prima, String seconda) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setPrima(prima);
        this.setSeconda(seconda);
    }// fine del metodo costruttore completo


    /**
     * metodo getter
     *
     * @return prima
     */
    public String getPrima() {
        return prima;
    } // fine del metodo getter


    /**
     * metodo setter
     *
     * @param prima -
     */
    public void setPrima(String prima) {
        this.prima = prima;
    } // fine del metodo setter


    /**
     * metodo getter
     *
     * @return seconda
     */
    public String getSeconda() {
        return seconda;
    } // fine del metodo getter


    /**
     * metodo setter
     *
     * @param seconda - seconda stringa
     */
    public void setSeconda(String seconda) {
        this.seconda = seconda;
    } // fine del metodo setter
}// fine della classe