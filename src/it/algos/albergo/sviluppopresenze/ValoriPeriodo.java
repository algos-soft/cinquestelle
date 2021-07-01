/**
 * Title:     ValoriPeriodo
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      13-feb-2009
 */
package it.algos.albergo.sviluppopresenze;

import it.algos.base.errore.Errore;

/**
 * Wrapper per incapsulare i valori di un periodo
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 13-feb-2009 ore 9.14.26
 */
class ValoriPeriodo {

    /* numero di presenze adulti */
    private int presenzeAdulti;

    /* numero di presenze bambini */
    private int presenzeBambini;

    /* valore economico */
    private double valore;


    /**
     * Costruttore completo con parametri.
     * <p>
     *
     * @param adulti numero di presenze adulti
     * @param bambini numero di presenze bambini
     * @param valore valore economico
     */
    public ValoriPeriodo(int adulti, int bambini, double valore) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setPresenzeAdulti(adulti);
        this.setPresenzeBambini(bambini);
        this.setValore(valore);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo

    /**
     * Costruttore senza parametri.
     * <p>
     */
    public ValoriPeriodo() {
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }


    public int getPresenzeAdulti() {
        return presenzeAdulti;
    }


    private void setPresenzeAdulti(int presenzeAdulti) {
        this.presenzeAdulti = presenzeAdulti;
    }


    public int getPresenzeBambini() {
        return presenzeBambini;
    }


    private void setPresenzeBambini(int presenzeBambini) {
        this.presenzeBambini = presenzeBambini;
    }


    public double getValore() {
        return valore;
    }


    private void setValore(double valore) {
        this.valore = valore;
    }
}// fine della classe