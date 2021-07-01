/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 14 agosto 2003 alle 9.58
 */
package it.algos.base.campo.video.decorator;

import it.algos.base.campo.base.CampoBase;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.errore.Errore;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Decoratore grafico del campo vido.
 * <p/>
 * Questa classe astratta: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Decorator</b> </li>
 * <li> Disegna un checkbox nel pannello campo del CampoVideo,
 * oltre al pannello componenti </li>
 * <li> Il checkbox pu� essere posizionata solo a destra del pannello componenti <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  14 agosto 2003 ore 9.58
 */
public final class CVDCheck extends CVDecoratoreBase {


    /**
     * check box
     */
    private JCheckBox checkbox = null;

    private boolean selezionatoIniziale;


    /**
     * Costruttore completo con parametri.
     *
     * @param campoVideo da decorare
     */
    public CVDCheck(CampoVideo campoVideo) {
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


    /**
     * Crea gli elementi GUI del pannelloCampo.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Gli elementi possono essere anche pi� di uno <br>
     * Gli elementi vengono aggiunti al pannelloCampo <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see #regolaElementi()
     */
    protected void creaComponentiGUI() {
        /* variabili e costanti locali di lavoro */
        JCheckBox check;
        Pos pos;

        try { // prova ad eseguire il codice

            /* crea il componente */
            this.setCheckbox(new JCheckBox());
            check = this.getCheckbox();

            /* aggiunge un listener che ascolta le modifiche al check */
            check.addItemListener(new CheckAction());

            check.setOpaque(false);
            check.setFocusable(false);
            check.setSelected(this.isSelezionatoIniziale());

            /* recupera la posizione */
            pos = this.getPos();

            /* aggiunge l'etichetta al pannelloCampo */
            this.addComponente(check, pos);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private JCheckBox getCheckbox() {
        return checkbox;
    }


    private void setCheckbox(JCheckBox checkbox) {
        this.checkbox = checkbox;
    }


    public boolean isSelezionato() {
        /* variabili e costanti locali di lavoro */
        boolean selezionato = false;
        JCheckBox check;

        try { // prova ad eseguire il codice
            check = this.getCheckbox();
            if (check != null) {
                selezionato = check.isSelected();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return selezionato;
    }


    public void setSelezionato(boolean selezionato) {
        /* variabili e costanti locali di lavoro */
        JCheckBox check;

        try { // prova ad eseguire il codice
            check = this.getCheckbox();
            if (check != null) {
                check.setSelected(selezionato);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public boolean isSelezionatoIniziale() {
        return selezionatoIniziale;
    }


    public void setSelezionatoIniziale(boolean selezionatoIniziale) {
        this.selezionatoIniziale = selezionatoIniziale;
    }


    private class CheckAction implements ItemListener {

        /**
         * Invocato quando il check viene modificato.
         * Lancia un evento generico per il campo.
         */
        public void itemStateChanged(ItemEvent e) {
            getCampoParente().fire(CampoBase.Evento.generico);
        }

    }

}// fine della classe