/**
 * Title:     TTextArea
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      12-mar-2007
 */
package it.algos.base.componente;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import javax.swing.JTextArea;
import javax.swing.text.Document;
import java.awt.Dimension;

/**
 * JTextArea con line wrap attivato di default e alcune
 * funzionalità aggiuntive per il calcolo corretto delle dimensioni.
 * </p>
 * - Permette di regolare correttamente l'altezza in funzione della larghezza
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 12-mar-2007 ore 10.38.37
 */
public class WrapTextArea extends JTextArea {

    public WrapTextArea() {
        super();
        this.inizia();
    }


    public WrapTextArea(Document doc) {
        super(doc);
        this.inizia();
    }


    public WrapTextArea(Document doc, String text, int rows, int columns) {
        super(doc, text, rows, columns);
        this.inizia();
    }


    public WrapTextArea(int rows, int columns) {
        super(rows, columns);
        this.inizia();
    }


    public WrapTextArea(String text) {
        super(text);
        this.inizia();
    }


    public WrapTextArea(String text, int rows, int columns) {
        super(text, rows, columns);
        this.inizia();
    }


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     */
    private void inizia() {
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
    }


    /**
     * Assegna la larghezza all'area di testo.
     * <p/>
     * L'altezza viene calcolata automaticamente in base
     * alla larghezza.<br>
     *
     * @param w la larghezza desiderata
     */
    public void setWidth(int w) {
        try {    // prova ad eseguire il codice
            this.setPreferredSize(new Dimension(w, 0));
            this.setOptimalHeight();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola l'altezza di questa TextArea alla misura ottimale
     * per mostrare tutte le righe.
     * <p/>
     * Funziona anche con word wrap attivo.<br>
     * Usa la larghezza esistente (preferredSize) <br>
     * Regola l'altezza in funzione del contenuto.
     */
    public void setOptimalHeight() {
        try {    // prova ad eseguire il codice
            Lib.Comp.setAreaOptimalHeight(this);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


}// fine della classe
