/**
 * Title:     ProgressBar
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      19-giu-2006
 */
package it.algos.base.progressbar;

import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.pannello.PannelloFlusso;

import javax.swing.*;
import java.awt.*;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA CLASSE .
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 19-giu-2006 ore 0.10.21
 */
public final class ProgressBar extends PannelloFlusso {

    /**
     * Riferimento alla JProgressBar
     */
    JProgressBar bar;

    /**
     * Bottone di interruzione
     */
    JButton bottoneBreak;


    /**
     * Costruttore completo con parametri. <br>
     */
    public ProgressBar() {
        /* rimanda al costruttore della superclasse */
        super(Layout.ORIENTAMENTO_ORIZZONTALE);

        /* regola le variabili di istanza coi parametri */
//        this.set(un);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
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
        JProgressBar bar;
        Icon icona;
        JButton bottone;

        try { // prova ad eseguire il codice

            /* regola il layout */
            this.setGapMinimo(1);
            this.setGapPreferito(2);
            this.setGapMassimo(2);
            this.setAllineamento(Layout.ALLINEA_CENTRO);

            /* crea e registra la JProgressBar */
            bar = new JProgressBar();
            bar.setStringPainted(true);
            this.setBar(bar);

            /* crea e registra il bottone di interruzione */
            icona = Lib.Risorse.getIconaBase("Interrompi16");
            bottone = new JButton(icona);
            this.setBottoneBreak(bottone);
            bottone.setOpaque(false);
            bottone.setFocusable(false);
            bottone.setBorderPainted(false);
            bottone.setRolloverEnabled(true);
            bottone.setContentAreaFilled(false);
            bottone.setMargin(new Insets(0, 0, 0, 0));

//            bottone.addActionListener(new AzioneBreak());

            this.add(bar);
            this.add(bottone);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Assegna il valore corrente alla progress bar.
     * <p/>
     */
    public void setValue(int val) {
        this.getBar().setValue(val);
    }


    /**
     * Assegna il valore di fondo scala alla progress bar.
     * <p/>
     */
    public void setMaximum(int max) {
        this.getBar().setMaximum(max);
    }


    /**
     * Regola la stringa da visualizzare nella progress bar.
     * <p/>
     */
    public void setString(String stringa) {
        this.getBar().setString(stringa);
    }


    /**
     * Abilita o disabilita il bottone di interruzione.
     * <p/>
     */
    public void setBreakAbilitato(boolean flag) {
        this.getBottoneBreak().setEnabled(flag);
        this.getBottoneBreak().setVisible(flag);
    }


    private JProgressBar getBar() {
        return bar;
    }


    private void setBar(JProgressBar bar) {
        this.bar = bar;
    }


    public JButton getBottoneBreak() {
        return bottoneBreak;
    }


    private void setBottoneBreak(JButton bottoneBreak) {
        this.bottoneBreak = bottoneBreak;
    }


}// fine della classe
