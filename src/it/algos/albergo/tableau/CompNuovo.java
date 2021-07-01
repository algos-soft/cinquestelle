/**
 * Title:     CompCamera
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25-feb-2009
 */
package it.algos.albergo.tableau;

import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFlusso;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.Dimension;

/**
 * Componente grafico per una camera nella barra delle camere
 * Usato dal renderer delle celle
 * <p/>
 * E' composto da 3 subcomponenti:
 * - uno iniziale per l'entrata (arrivo o cambio)
 * - uno centrale per il soggiorno (nome cliente)
 * - uno finale per l'uscita (cambio o partenza)
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 25-feb-2009 ore 15.09.49
 */
class CompNuovo extends PanGradientRounded {

    private JLabel labelCentro;


    /**
     * Costruttore completo senza parametri.<br>
     */
    public CompNuovo() {

        /* rimanda al costruttore della superclasse */
        super(Tableau.COLORE_NUOVO, SwingConstants.VERTICAL);

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

        try { // prova ad eseguire il codice

            /* sfondo arrotondato */
            this.setRoundRadius(10);

            /* pannello generale */
            Pannello pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);

            /* molla */
            pan.add(Box.createHorizontalGlue());

            /* componente centrale */
            pan.add(this.creaCompCentrale());

            /* molla */
            pan.add(Box.createHorizontalGlue());


            /* aggiunge il pannello completo */
            this.add(pan.getPanFisso());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }



    /**
     * Crea il componente centrale.
     * <p/>
     *
     * @return il componente di entrata
     */
    private Component creaCompCentrale() {
        /* variabili e costanti locali di lavoro */
        JLabel label = null;

        try {    // prova ad eseguire il codice
            label = new JLabel();
            this.setLabelCentro(label);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(Tableau.FONT_NOMI_CLIENTI);
            label.setOpaque(false);
            label.setForeground(Tableau.COLORE_NOME_CLIENTE);

            label.setMinimumSize(new Dimension(10, 20));

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return label;
    }


    /**
     * Assegna il testo da visualizzare.
     * <p/>
     * @param testo da visualizzare
     */
    public void setTesto(String testo) {
        this.getLabelCentro().setText(testo);
    }


    private JLabel getLabelCentro() {
        return labelCentro;
    }


    private void setLabelCentro(JLabel labelCentro) {
        this.labelCentro = labelCentro;
    }


}// fine della classe