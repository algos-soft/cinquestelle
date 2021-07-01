/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      30-apr-2005
 */
package it.algos.base.property;

import it.algos.base.errore.Errore;

/**
 * Wrapper di una Property generica.
 * </p>
 * Oggetti mantenuti:
 * - chiave identificativa della Property
 * - valore corrente della Property
 * - testo di commento
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 30-apr-2005 ore 15.50.41
 */
public final class Property extends Object {

    /**
     * chiave identificativa della Property
     */
    private String chiave = "";

    /**
     * valore corrente della Property
     */
    private String valore = null;

    /**
     * testo di commento (righe separate da \n)
     */
    private String commento = "";


    /**
     * Costruttore senza parametri.
     */
    public Property() {
        /* rimanda al costruttore di questa classe */
        this(null, null, null);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo con parametri.
     *
     * @param chiave chiave identificativa della Property
     * @param valore valore della Property
     * @param commento testo di commento (righe separate da \n)
     */
    public Property(String chiave, String valore, String commento) {

        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setChiave(chiave);
        this.setValore(valore);
        this.setCommento(commento);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    public String getChiave() {
        return chiave;
    }


    public void setChiave(String chiave) {
        this.chiave = chiave;
    }


    public String getValore() {
        return valore;
    }


    public void setValore(String valore) {
        this.valore = valore;
    }


    public String getCommento() {
        return commento;
    }


    public void setCommento(String commento) {
        this.commento = commento;
    }
}// fine della classe
