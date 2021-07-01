/**
 * Title:     CompGiorno
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25-feb-2009
 */
package it.algos.albergo.tableau;

import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.pannello.PannelloFlusso;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.util.Calendar;
import java.util.Date;

/**
 * Componente grafico per un giorno nella barra dei giorni
 * Usato dal renderer delle celle
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 25-feb-2009 ore 15.09.49
 */
class CompGiorno extends PanGradientRounded {

    private JLabel labelNomeGiorno;

    private JLabel labelNumGiorno;

    private JLabel labelNomeMese;

    private static final Color COLORE_FERIALE=Color.lightGray;
    private static final Color COLORE_FESTIVO= Color.red;


    /**
     * Costruttore completo senza parametri.<br>
     */
    public CompGiorno() {
        /* rimanda al costruttore della superclasse */
        super(COLORE_FERIALE, SwingConstants.HORIZONTAL);

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
        PannelloFlusso pan;

        try { // prova ad eseguire il codice

            /* crea un pannello verticale e lo aggiunge */
            pan = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(0);
            pan.setAllineamento(Layout.ALLINEA_CENTRO);
            this.add(pan);

            pan.add(Box.createVerticalGlue());

            /* riempie il pannello con le varie label */
            label = this.creaLabelBase();
            this.setLabelNomeGiorno(label);
            pan.add(label);

            label = this.creaLabelBase();
            this.setLabelNumGiorno(label);
            label.setFont(Tableau.FONT_NUMERI_GIORNO);
            pan.add(label);

            label = this.creaLabelBase();
            this.setLabelNomeMese(label);
//            pan.add(label);

            pan.add(Box.createVerticalGlue());


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea una JLabel di base.
     * <p/>
     *
     * @return una label di base
     */
    private JLabel creaLabelBase() {
        /* variabili e costanti locali di lavoro */
        JLabel label = null;

        try {    // prova ad eseguire il codice
            label = new JLabel("",SwingConstants.CENTER);
            label.setFont(Tableau.FONT_GIORNI);
            label.setOpaque(false);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return label;
    }


    /**
     * Assegna la data al giorno.
     * <p/>
     * Configura il componente per visualizzare una certa data
     *
     * @param data del giorno
     */
    public void setData(Date data) {
        /* variabili e costanti locali di lavoro */
        String strNome;
        String strNumero;
        String strMese;

        try { // prova ad eseguire il codice

            strNome = Lib.Data.getGiorno(data);
            strNome = strNome.substring(0, 2);
            this.getLabelNomeGiorno().setText(strNome);

            int ng = Lib.Data.getNumeroGiorno(data);
            strNumero = Lib.Testo.getStringa(ng);
            this.getLabelNumGiorno().setText(strNumero);

            int numMese = Lib.Data.getNumeroMese(data);
            strMese = Lib.Data.getNomeMese(numMese);
            strMese = strMese.substring(0, 3);
            this.getLabelNomeMese().setText(strMese);

            /* colorazione della domenica */
            int ngs = Lib.Data.getNumeroGiornoSettimana(data);
            if ((ngs== Calendar.SATURDAY) || (ngs== Calendar.SUNDAY)) {
                this.setColor(COLORE_FESTIVO);
                this.setTextColor(Color.white);
            } else {
                this.setColor(COLORE_FERIALE);
                this.setTextColor(Color.black);
            }// fine del blocco if-else



        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Assegna il colore a tutti i testi.
     * <p/>
     * @param colore da assegnare
     */
    private void setTextColor (Color colore) {
        this.getLabelNomeGiorno().setForeground(colore);
        this.getLabelNumGiorno().setForeground(colore);
        this.getLabelNomeMese().setForeground(colore);
    }





    private JLabel getLabelNomeGiorno() {
        return labelNomeGiorno;
    }


    private void setLabelNomeGiorno(JLabel labelNomeGiorno) {
        this.labelNomeGiorno = labelNomeGiorno;
    }


    private JLabel getLabelNumGiorno() {
        return labelNumGiorno;
    }


    private void setLabelNumGiorno(JLabel labelNumGiorno) {
        this.labelNumGiorno = labelNumGiorno;
    }


    private JLabel getLabelNomeMese() {
        return labelNomeMese;
    }


    private void setLabelNomeMese(JLabel labelNomeMese) {
        this.labelNomeMese = labelNomeMese;
    }
}// fine della classe