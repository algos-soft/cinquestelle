package it.algos.base.wrapper;

/**
 * Wrapper per il singolo elemento dati di un combo interno..
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 4-mar-2008 ore  15:51
 */
public final class ComboWrap {

    /**
     * variabile di tipo intero
     */
    private int codice;

    /**
     * variabile di tipo testo
     * da visualizzare (usa toString())
     */
    private Object oggetto;


    /**
     * Costruttore senza parametri.
     */
    public ComboWrap() {
        /* rimanda al costruttore della superclasse */
        super();
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     *
     * @param codice -
     * @param oggetto - da visualizzare (usa toString())
     */
    public ComboWrap(int codice, Object oggetto) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setCodice(codice);
        this.setOggetto(oggetto);
    }// fine del metodo costruttore completo


    /**
     * metodo getter
     *
     * @return codice
     */
    public int getCodice() {
        return codice;
    } // fine del metodo getter


    /**
     * metodo setter
     *
     * @param codice -
     */
    private void setCodice(int codice) {
        this.codice = codice;
    } // fine del metodo setter


    /**
     * metodo getter
     *
     * @return oggetto
     */
    public Object getOggetto() {
        return oggetto;
    } // fine del metodo getter


    /**
     * metodo setter
     *
     * @param oggetto - da visualizzare (usa toString())
     */
    private void setOggetto(Object oggetto) {
        this.oggetto = oggetto;
    } // fine del metodo setter
}// fine della classe