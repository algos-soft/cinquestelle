/**
 * Title:        WrapCompoAccessorio.java
 * Description:
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 26-giu-2009
 */

package it.algos.albergo.camera.compoaccessori;

/**
 * Wrapper di una riga di accessorio per una composizione camera.
 * <p>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  26-giu-2009
 */
public class WrapCompoAccessorio {

    /* codice dell'accessorio */
    private int codAccessorio;

    /* quantità dell'accessorio */
    private int quantita;

    /* numero di giorni di rotazione dell'accessorio */
    private int ggrotazione;

    /**
     * Costruttore completo.
     * <p/>
     * @param codAccessorio codice dell'accessorio
     * @param qta la quantità
     * @param ggrotazione il numero di giorni di rotazione
     */
    public WrapCompoAccessorio(int codAccessorio, int qta, int ggrotazione) {
        this.setCodAccessorio(codAccessorio);
        this.setQtaAccessorio(qta);
        this.setGgrotazione(ggrotazione);
    } /* fine del metodo costruttore completo */


    public int getCodAccessorio() {
        return codAccessorio;
    }


    private void setCodAccessorio(int codAccessorio) {
        this.codAccessorio = codAccessorio;
    }


    public int getQtaAccessorio() {
        return quantita;
    }


    private void setQtaAccessorio(int quantita) {
        this.quantita = quantita;
    }


    public int getGgrotazione() {
        return ggrotazione;
    }


    private void setGgrotazione(int ggrotazione) {
        this.ggrotazione = ggrotazione;
    }
}// fine della classe