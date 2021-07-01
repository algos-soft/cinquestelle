/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Valbonesi
 * @version 1.0  /
 * Creato:       il 5 aprile 2006
 */
package it.algos.base.campo.video.decorator;

import it.algos.base.campo.video.CampoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.pannello.PannelloBase;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Decoratore grafico per un campo obbligatorio.
 * <p/>
 * Questa classe astratta: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Decorator</b> </li>
 * <li> Disegna un bordo rosso intorno al campo </li>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  5 aprile 2006 ore 9.58
 */
public final class CVDObbligatorio extends CVDecoratoreBase {

    /**
     * bordo normale del campo
     */
    private Border bordoNormale = null;

    /**
     * bo0rdo del campo se vuoto
     */
    private Border bordoObbligatorio = null;


    /**
     * Costruttore completo con parametri.
     *
     * @param campoVideo da decorare
     */
    public CVDObbligatorio(CampoVideo campoVideo) {
        /* rimanda al costruttore della superclasse */
        super(campoVideo);

        try { // prova ad eseguire il codice

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }


    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        PannelloBase panComponenti;
        Color colore;
        Border bordo;

        try { // prova ad eseguire il codice
            super.inizializza();

            /* recupera il bordo normale dei campi */
            panComponenti = this.getPannelloBaseComponenti();
            bordo = panComponenti.getBorder();
            this.setBordoNormale(bordo);

            /* crea e mette da parte il bordo specifico */
            colore = new Color(180, 80, 80);
            bordo = BorderFactory.createLineBorder(colore);
            this.setBordoObbligatorio(bordo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public void avvia() {
        super.avvia();
        this.regolaComponentiGUI();
    }


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Invoca i metodi delegati <br>
     */
    public void eventoMemoriaModificata() {
        this.regolaComponentiGUI();
    }


    /**
     * Regola gli elementi GUI del pannelloCampo.
     * <p/>
     * Metodo invocato ad ogni modifica del campo <br>
     */
    private void regolaComponentiGUI() {
        /* variabili e costanti locali di lavoro */
        PannelloBase panComponenti;
        boolean vuoto;
        boolean modificabile;
        Border bordo;
        Dimension dim;

        try { // prova ad eseguire il codice

            /* controlla se c'è un valore */
            vuoto = this.getCampoParente().isVuoto();

            /* controlla se il campo è modificabile */
            modificabile = this.getCampoParente().isModificabile();

            /* se il campo è vuoto e modificabile, bordo obbligatorio,
             * altrimenti nessun bordo */
            if (vuoto && modificabile) {
                bordo = this.getBordoObbligatorio();
            } else {
                bordo = this.getBordoNormale();
            }// fine del blocco if-else

            /* recupera e regola il componente */
            panComponenti = this.getPannelloBaseComponenti();
            dim = panComponenti.getPreferredSize();
            panComponenti.setBorder(bordo);

            /* patch per un probabile bug di java sui bordi */
            panComponenti.setPreferredSize(dim);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Campo obbligatorio per registrazione / conferma
     */
    public boolean isObbligatorio() {
        return true;
    }


    private Border getBordoNormale() {
        return bordoNormale;
    }


    private void setBordoNormale(Border bordoNormale) {
        this.bordoNormale = bordoNormale;
    }


    private Border getBordoObbligatorio() {
        return bordoObbligatorio;
    }


    private void setBordoObbligatorio(Border bordoObbligatorio) {
        this.bordoObbligatorio = bordoObbligatorio;
    }
}// fine della classe