/**
 * Title:     CVDBottone
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      12-giu-2006
 */
package it.algos.base.campo.video.decorator;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.video.*;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.pref.PrefTipo;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
public class CVDPreferenza extends CVDecoratoreBase {


    /**
     * valore standard (default)
     */
    private Object standard = null;

    /**
     * nota per utente normale (non programmatore)
     */
    private String notaUte = "";

    private final static String ICONA = "Rewind16";


    /**
     * bottone
     */
    private JButton bottone = null;


    /**
     * Costruttore completo con parametri.
     *
     * @param campoVideo da decorare
     */
    public CVDPreferenza(CampoVideo campoVideo) {
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
        try { // prova ad eseguire il codice
            this.creaBottone();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione del bottone freccia.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     */
    private void creaBottone() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        JButton bot;
        Icon icona;

        try { // prova ad eseguire il codice
            /* recupera l'icona con la freccia a sinistra*/
            icona = Lib.Risorse.getIconaBase(ICONA);
            continua = (icona != null);

            /* crea il bottone */
            if (continua) {
                this.setBottone(new JButton(icona));
                bot = this.getBottone();

                /* aggiunge un listener che ascolta le modifiche al check */
                bot.addActionListener(new CVDPreferenza.BottonePremuto());

                bot.setBorderPainted(false);
                bot.setContentAreaFilled(false);
                bot.setMargin(new Insets(0, 0, 0, 0));
            }// fine del blocco if
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
        boolean continua;
        Pos pos;
        JComponent comp = null;
        CampoVideo campoVideo;
        Object valore;
        JCheckBox box;
        Boolean bool;
        ArrayList<PrefTipo> lista;
        PannelloFlusso pan = null;
        int posCombo;

        try { // prova ad eseguire il codice

            /* valore di default da assegnare (non dovrebbe mai essere nullo) */
            valore = this.getStandard();

            /* recupera il campo video originario */
            campoVideo = this.getCampoParente().getCampoVideoNonDecorato();
            continua = (campoVideo != null);

            /* seleziona il tipo di componente da creare */
            if (continua) {
                if (campoVideo instanceof CVCheckBox) {
                    if (valore instanceof Boolean) {
                        bool = (Boolean)valore;
                        if (bool) {
                            box = new JCheckBox("vero");
                        } else {
                            box = new JCheckBox("falso");
                        }// fine del blocco if-else
                        box.setSelected(bool);
                        comp = box;
                    }// fine del blocco if
                    TestoAlgos.setCheckBox(comp);
                }// fine del blocco if
            }// fine del blocco if

            /* seleziona il tipo di componente da creare */
            if (continua) {
                if (campoVideo instanceof CVTestoField) {
                    comp = new JTextField(10);
                    ((JTextField)comp).setText(valore.toString());
                    TestoAlgos.setTesto(comp);
                }// fine del blocco if
            }// fine del blocco if

            /* seleziona il tipo di componente da creare */
            if (continua) {
                if (campoVideo instanceof CVTestoArea) {
                    comp = new JTextArea(3, 20);
                    ((JTextArea)comp).setText(valore.toString());
                    TestoAlgos.setArea(comp);
                }// fine del blocco if
            }// fine del blocco if

            /* seleziona il tipo di componente da creare */
            if (continua) {
                if (campoVideo instanceof CVNumero) {
                    comp = new JTextField(5);
                    ((JTextField)comp).setText(valore.toString());
                    TestoAlgos.setTesto(comp);
                }// fine del blocco if
            }// fine del blocco if

            /* seleziona il tipo di componente da creare */
            if (continua) {
                if (campoVideo instanceof CVData) {
                    comp = new JTextField(5);
                    ((JTextField)comp).setText(valore.toString());
                    TestoAlgos.setTesto(comp);
                }// fine del blocco if
            }// fine del blocco if

            /* seleziona il tipo di componente da creare */
            if (continua) {
                if (campoVideo instanceof CVCombo) {
                    lista = this.getCampoParente().getCampoDati().getValoriInterni();
                    if (lista != null) {
                        comp = new JComboBox(lista.toArray());
                    } else {
                        comp = new JComboBox();
                    }// fine del blocco if-else
                    if (valore instanceof Integer) {
                        posCombo = (Integer)valore;
                        if (posCombo <= ((JComboBox)comp).getItemCount()) {
                            ((JComboBox)comp).setSelectedIndex(posCombo - 1);
                        }// fine del blocco if
                    }// fine del blocco if

                    TestoAlgos.setCombo(comp);
                }// fine del blocco if
            }// fine del blocco if

            /* controlla che sia stato creato un componente */
            if (continua) {
                continua = (comp != null);
            }// fine del blocco if

            /* regola il componente */
            if (continua) {
                comp.setEnabled(false);
            }// fine del blocco if

            /* costruisce un pannellino per contenere sia il bottone-freccia che il componente */
            if (continua) {
                pan = getRiquadro(comp);
            }// fine del blocco if

            /* aggiunge il pannellino alla destra del pannelloComponenti */
            if (continua) {
                pos = this.getPos();
                this.addComponente(pan, pos);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Invoca i metodi delegati <br>
     */
    @Override public void eventoMemoriaModificata() {
        /* variabili e costanti locali di lavoro */
        boolean valoriDiversi;
        Object valCorrente;
        Object valDefault;

        super.eventoMemoriaModificata();

        try { // prova ad eseguire il codice
            valCorrente = this.getCampoParente().getCampoDati().getValore();
            valDefault = this.getStandard();

            valoriDiversi = (valCorrente != valDefault);

            this.getBottone().setEnabled(valoriDiversi);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Costruisce un pannello.
     * <p/>
     * Costruisce un pannello per contenere sia il bottone-freccia che il componente <br>
     * Costruisce un bordo <br>
     * Titola il bordo <br>
     */
    private PannelloFlusso getRiquadro(JComponent comp) {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso pan = null;
        PannelloFlusso pan2;

        try {    // prova ad eseguire il codice
            pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            pan.setAllineamento(Layout.ALLINEA_CENTRO);
            pan2 = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            pan2.setBorder(BorderFactory.createTitledBorder("default"));

            pan.add(this.getBottone());
            pan2.add(comp);
            pan.add(pan2);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Metodo invocato quando il bottone viene premuto.
     * <p/>
     * Sovrascritto dalle sottoclassi
     */
    private void bottonePremuto() {
        /* variabili e costanti locali di lavoro */
        Object valoreDefault;
        Campo campo;

        try {    // prova ad eseguire il codice
            valoreDefault = this.getStandard();
            campo = this.getCampoParente();

            campo.setValore(valoreDefault);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    public JButton getBottone() {
        return bottone;
    }


    private void setBottone(JButton bottone) {
        this.bottone = bottone;
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


    private Object getStandard() {
        return standard;
    }


    public void setStandard(Object standard) {
        this.standard = standard;
    }


    public String getNotaUte() {
        return notaUte;
    }


    public void setNotaUte(String notaUte) {
        this.notaUte = notaUte;
    }
}// fine della classe
