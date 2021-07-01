package it.algos.base.wrapper;

/**
 * - @todo Manca la descrizione..
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 4-apr-2008 ore  19:03
 */
public final class StrBool {

    /**
     * variabile di tipo testo
     * una stringa
     */
    private String stringa;

    /**
     * variabile di tipo check
     * un booleano
     */
    private boolean booleano;


    /**
     * Costruttore senza parametri.
     */
    public StrBool() {
        /* rimanda al costruttore della superclasse */
        super();
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     *
     * @param stringa - una stringa
     * @param booleano - un booleano
     */
    public StrBool(String stringa, boolean booleano) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setStringa(stringa);
        this.setBooleano(booleano);
    }// fine del metodo costruttore completo


    /**
     * metodo getter
     *
     * @return stringa
     */
    public String getStringa() {
        return stringa;
    } // fine del metodo getter


    /**
     * metodo setter
     *
     * @param stringa - una stringa
     */
    public void setStringa(String stringa) {
        this.stringa = stringa;
    } // fine del metodo setter


    /**
     * metodo getter
     *
     * @return booleano
     */
    public boolean isBooleano() {
        return booleano;
    } // fine del metodo getter


    /**
     * metodo setter
     *
     * @param booleano - un booleano
     */
    public void setBooleano(boolean booleano) {
        this.booleano = booleano;
    } // fine del metodo setter

}// fine della classe