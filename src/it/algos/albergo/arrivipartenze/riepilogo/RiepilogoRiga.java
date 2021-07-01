/**
 * Title:     RiepilogoRiga
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      3-lug-2009
 */
package it.algos.albergo.arrivipartenze.riepilogo;

import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.pannello.PannelloFlusso;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Una riga del riepilogo Presenze
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 3-lug-2009 ore 21.56.36
 */
class RiepilogoRiga extends PannelloFlusso {

    /* riepilogo di riferimento */
    private RiepilogoNew riepilogo;

    /* listener da registrare nel bottone */
    private ActionListener listener;

    /* bottone con testo e azione */
    private JButton bottone;

    /* jlabel per il titolo della riga */
    private JLabel labelTitolo;

    /* jlabel per il numero totale */
    private JLabel labelTotale;

    /* jlabel per il numero in dettaglio (A/B) */
    private JLabel labelDettaglio;


    /* larghezza della prima colonna (titoli) */
    private int larTitoli = 140;

    /* larghezza della colonna totali */
    private int larTotali = 30;

    /* larghezza della colonna dettaglio */
    private int larDettagli = 55;

    /* larghezza della colonna bottoni */
    private int larBottoni = 28;



    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param riepilogo riepilogo di riferimento
     * @param listener da registrare nel bottone
     */
    public RiepilogoRiga(
            RiepilogoNew riepilogo, ActionListener listener) {
        /* rimanda al costruttore della superclasse */
        super(Layout.ORIENTAMENTO_ORIZZONTALE);

        /* regola le variabili di istanza coi parametri */
        this.setListener(listener);
        this.setRiepilogo(riepilogo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        JLabel label;


        try { // prova ad eseguire il codice

            this.setAllineamento(Layout.ALLINEA_CENTRO);
            this.setGapMinimo(2);
            this.setGapPreferito(10);

            /* crea registra e aggiunge la label del titolo */
            label = new JLabel(" ");   // se non metti lo spazio nasce ad altezza zero!
            label.setFont(FontFactory.creaScreenFont(Font.PLAIN, 12f));
            this.setLabelTitolo(label);
            Lib.Comp.setPreferredWidth(label, larTitoli);
            Lib.Comp.bloccaLarghezza(label);
            this.add(label);

            /* crea registra e aggiunge label per il totale */
            label = new JLabel(" ");    // se non metti lo spazio nasce ad altezza zero!
            label.setFont(FontFactory.creaScreenFont(Font.BOLD, 14f));
            label.setHorizontalAlignment(SwingConstants.TRAILING);
            Lib.Comp.setPreferredWidth(label, larTotali);
            Lib.Comp.bloccaLarghezza(label);
            this.setLabelTotale(label);
            this.add(label);

            /* crea registra e aggiunge label per il dettaglio */
            label = new JLabel(" ");    // se non metti lo spazio nasce ad altezza zero!
            label.setFont(FontFactory.creaScreenFont(Font.PLAIN, 12f));
            label.setHorizontalAlignment(SwingConstants.TRAILING);
            Lib.Comp.setPreferredWidth(label, larDettagli);
            Lib.Comp.bloccaLarghezza(label);
            this.setLabelDettaglio(label);
            this.add(label);

            /* crea il bottone e lo aggiunge */
            JButton bot = new JButton("...");
            this.setBottone(bot);
            bot.addActionListener(this.getListener());
            bot.setFocusable(false);
            Lib.Comp.setPreferredWidth(bot, larBottoni);
            Lib.Comp.setPreferredHeigth(bot, 16);
            Lib.Comp.bloccaDim(bot);
            this.add(bot);



        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Aggiorna la riga.
     * <p/>
     * Assegna i valori
     * Regola il titolo del bottone
     *
     * @param testo da visualizzare
     * @param adulti numero di adulti
     * @param bambini numero di bambini
     * @param abilitato true se il bottone che rimanda al listener deve essere abilitato
     */
    public void aggiorna(String testo, int adulti, int bambini, boolean abilitato) {
        this.setValore(adulti, bambini);
        this.getLabelTitolo().setText(testo);
        this.getBottone().setEnabled(abilitato);
    }


    /**
     * Assegna i valori alla riga.
     * <p/>
     * Assegna i valori
     * Regola il titolo del bottone
     *
     * @param adulti numero di adulti
     * @param bambini numero di bambini
     */
    private void setValore(int adulti, int bambini) {
        /* variabili e costanti locali di lavoro */
        int totale;
        String dettaglio = "";

        try {    // prova ad eseguire il codice

            totale = adulti + bambini;

            if ((adulti > 0) | (bambini > 0)) {
                dettaglio = "(" + adulti + "+" + bambini + ")";
            }// fine del blocco if

            this.getLabelTotale().setText("" + totale);
            this.getLabelDettaglio().setText("" + dettaglio);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna la data di riferimento per questo tipo di riga.
     * <p/>
     * Sovrascritto dalle sottoclassi interessate
     *
     * @return la data di riferimento
     */
    protected Date getDataRiferimento () {
        return null;
    }





    protected RiepilogoNew getRiepilogo() {
        return riepilogo;
    }


    private void setRiepilogo(RiepilogoNew riepilogo) {
        this.riepilogo = riepilogo;
    }


    private ActionListener getListener() {
        return listener;
    }


    private void setListener(ActionListener listener) {
        this.listener = listener;
    }


    private JButton getBottone() {
        return bottone;
    }


    private void setBottone(JButton bottone) {
        this.bottone = bottone;
    }


    private JLabel getLabelTitolo() {
        return labelTitolo;
    }


    private void setLabelTitolo(JLabel labelTitolo) {
        this.labelTitolo = labelTitolo;
    }


    private JLabel getLabelTotale() {
        return labelTotale;
    }


    private void setLabelTotale(JLabel labelTotale) {
        this.labelTotale = labelTotale;
    }


    private JLabel getLabelDettaglio() {
        return labelDettaglio;
    }


    private void setLabelDettaglio(JLabel labelDettaglio) {
        this.labelDettaglio = labelDettaglio;
    }

}// fine della classe
