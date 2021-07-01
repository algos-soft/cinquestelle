/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      2-ago-2006
 */
package it.algos.base.wrapper;

/**
 * Coppia di intero e stringa.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Costruisce un tipo di dati </li>
 * <li> Raggruppa questi dati in un wrapper </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 2-ago-2006 ore 20.52.43
 */
public final class IntStringa {

    /**
     * valore intero
     */
    private int intero;

    /**
     * valore stringa
     */
    private String stringa;


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param intero
     * @param stringa
     */
    public IntStringa(int intero, String stringa) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setIntero(intero);
        this.setStringa(stringa);
    }// fine del metodo costruttore completo


    public int getIntero() {
        return intero;
    }


    private void setIntero(int intero) {
        this.intero = intero;
    }


    public String getStringa() {
        return stringa;
    }


    private void setStringa(String stringa) {
        this.stringa = stringa;
    }
}// fine della classe
