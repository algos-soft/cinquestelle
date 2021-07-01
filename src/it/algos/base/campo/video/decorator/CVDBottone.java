/**
 * Title:     CVDBottone
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      12-giu-2006
 */
package it.algos.base.campo.video.decorator;

import it.algos.base.campo.base.CampoBase;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.errore.Errore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Decoratore grafico del campo video.
 * <p/>
 * Questa classe astratta: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Decorator</b> </li>
 * <li> Disegna un bottone nel pannello campo del CampoVideo,
 * oltre al pannello componenti </li>
 * <li> Il bottone viene posizionato a destra del pannello componenti <br>
 * Quando il bottone viene premuto, viene lanciato un evento
 * generico per il campo.
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  12-giu-2006 ore 9.58
 */
public class CVDBottone extends CVDecoratoreBase {


    /**
     * bottone
     */
    private JButton bottone = null;


    /**
     * Costruttore completo con parametri.
     *
     * @param campoVideo da decorare
     * @param bottone da utilizzare
     */
    public CVDBottone(CampoVideo campoVideo, JButton bottone) {
        /* rimanda al costruttore della superclasse */
        super(campoVideo);

        try { // prova ad eseguire il codice

            this.setBottone(bottone);

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Costruttore completo con parametri.
     *
     * @param campoVideo da decorare
     */
    public CVDBottone(CampoVideo campoVideo) {
        /* rimanda al costruttore */
        this(campoVideo, null);
    }


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        JButton bot;

        try { // prova ad eseguire il codice
            /* crea il bottone se manca */
            bot = this.getBottone();
            if (bot == null) {
                this.setBottone(new JButton());
                bot = this.getBottone();
                bot.setBorderPainted(false);
                bot.setContentAreaFilled(false);
                bot.setMargin(new Insets(0, 0, 0, 0));
            }// fine del blocco if


            this.setPos(Pos.DESTRA);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea gli elementi GUI del pannelloCampo.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Gli elementi possono essere anche più di uno <br>
     * Gli elementi vengono aggiunti al pannelloCampo <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see #regolaElementi()
     */
    protected void creaComponentiGUI() {
        /* variabili e costanti locali di lavoro */
        JButton bottone;
        Pos pos;

        try { // prova ad eseguire il codice

            bottone = this.getBottone();

            /* aggiunge un listener che ascolta le modifiche al check */
            bottone.addActionListener(new CVDBottone.BottonePremuto());

            bottone.setOpaque(false);
            bottone.setFocusable(false);

            /* recupera la posizione */
            pos = this.getPos();

            /* aggiunge il bottone al pannelloCampo */
            this.addComponente(bottone, pos);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola l'abilitazione del bottone parallelamente
     * al flag modificabile
     */
    public void setModificabile(boolean modificabile) {
        /* variabili e costanti locali di lavoro */
        JButton bottone;

        try { // prova ad eseguire il codice
            super.setModificabile(modificabile);
            bottone = this.getBottone();
            if (bottone != null) {
                bottone.setEnabled(modificabile);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    public JButton getBottone() {
        return bottone;
    }


    private void setBottone(JButton bottone) {
        this.bottone = bottone;
    }


    /**
     * Metodo invocato quando il bottone viene premuto.
     * <p/>
     * Sovrascritto dalle sottoclassi
     */
    protected void bottonePremuto() {
        try {    // prova ad eseguire il codice
            getCampoParente().fire(CampoBase.Evento.bottonePremuto);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    private class BottonePremuto implements ActionListener {

        /**
         * Invocato quando il bottone viene premuto.
         * Invoca un metodo delegato di questa classe.
         */
        public void actionPerformed(ActionEvent e) {
            bottonePremuto();
        }

    }

}// fine della classe
