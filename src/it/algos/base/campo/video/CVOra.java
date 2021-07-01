/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 3 luglio 2003 alle 23.53
 */
package it.algos.base.campo.video;

import it.algos.base.campo.base.Campo;
import it.algos.base.caret.BlockCaret;
import it.algos.base.errore.Errore;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

/**
 * Componente video di tipo ora.
 * </p>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  3 luglio 2003 ore 23.53
 */
public final class CVOra extends CVTestoField {

    /**
     * Costruttore completo con parametri.
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CVOra(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public void creaComponentiInterni() {

        /* rinvia alla superclasse */
        super.creaComponentiInterni();

        /* assegna il cursore a blocchetto */
        this.getComponenteTesto().setCaret(new BlockCaret());

    }


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* larghezza di default dei componenti interni al pannello componenti */
        this.getCampoParente().setLarScheda(65);

    }


    public void inizializza() {
        super.inizializza();

    }


    public void avvia() {
        super.avvia();
    }


    protected void assegnaFormatter() {
        super.assegnaFormatter();
    }


    /**
     * Invocato quando si entra in un campo (prende il fuoco).
     * <p/>
     */
    public void entrataCampo() {

        super.entrataCampo();

        /*
         * Per i campi data toglie la selezione
         * e posiziona il caret all'inizio
         */
        final JTextComponent comp = this.getComponenteTesto();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                comp.select(0, 0);
            }
        });
    }


    public void aggiornaGUI(Object unValore) {
        super.aggiornaGUI(unValore);
    }


    /**
     * Gestione evento gui modificata.
     * <p/>
     * Metodo invocato dal campo logica <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    public void guiModificata() {
        /* variabili e costanti locali di lavoro */
        int pos;
        JTextComponent comp;
        KeyboardFocusManager kfm;
        int selStart, selEnd;

        super.guiModificata();

        try { // prova ad eseguire il codice

            /* se il campo e' pieno, trasferisce il fuoco
             * al prossimo componente */
            comp = this.getComponenteTesto();
            pos = comp.getCaretPosition();
            selStart = comp.getSelectionStart();
            selEnd = comp.getSelectionEnd();

            if (pos >= 5) {
                if (selStart == selEnd) { // no se selezionato + di 1 carattere
                    kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                    kfm.focusNextComponent();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


}// fine della classe