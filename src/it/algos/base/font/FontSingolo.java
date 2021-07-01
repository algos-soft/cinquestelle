/**
 * Title:     FontSingolo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      20-gen-2004
 */
package it.algos.base.font;

import it.algos.base.errore.Errore;

import java.awt.*;

/**
 * Questa classe definisce un singolo oggetto Font.<br>
 * Gli oggetti FontSingolo contengono le informazioni su Font e Stile<br>
 * Diversi oggetti FontSingolo sono contenuti in una FamigliaFont<br>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 20-gen-2004 ore 18.03.46
 */
public final class FontSingolo extends Object {

    /* Riferimento al Font */
    private Font font = null;

    /* Stile di questo Font (vedi costanti della classe Font)*/
    private int stile = 0;


    /**
     * Costruttore completo.<br>
     *
     * @param unFont il Font
     * @param unoStile lo stile
     */
    public FontSingolo(Font unFont, int unoStile) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia(unFont, unoStile);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @param unFont il Font
     * @param unoStile lo stile
     *
     * @throws Exception unaEccezione
     */
    private void inizia(Font unFont, int unoStile) throws Exception {
        this.setFont(unFont);
        this.setStile(unoStile);
    }// fine del metodo inizia


    public Font getFont() {
        return font;
    }


    private void setFont(Font font) {
        this.font = font;
    }


    public int getStile() {
        return stile;
    }


    private void setStile(int stile) {
        this.stile = stile;
    }

}// fine della classe
