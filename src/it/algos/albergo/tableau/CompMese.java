/**
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-giu-2010
 */
package it.algos.albergo.tableau;

import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.pannello.PannelloFlusso;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;

/**
 * Componente grafico per un mese nella barra dei mesi
 * Usato dal renderer delle celle
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 25-feb-2009 ore 15.09.49
 */
class CompMese extends PanGradientRounded {

    private JLabel labelNomeMese;

//    private static final Color COLORE=new Color(255,255,71);
    private static final Color COLORE=Color.gray;


    /**
     * Costruttore completo senza parametri.<br>
     */
    public CompMese() {
        /* rimanda al costruttore della superclasse */
        super(COLORE, SwingConstants.HORIZONTAL);

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

            /* riempie il pannello con la label */
            label = new JLabel("",SwingConstants.CENTER);
            label.setOpaque(false);
            label.setFont(Tableau.FONT_NOMI_MESI);
            label.setForeground(Color.white);
            this.setLabelNomeMese(label);

            pan.add(Box.createVerticalGlue());
            pan.add(label);
            pan.add(Box.createVerticalGlue());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }



    /**
     * Assegna la data al giorno.
     * <p/>
     * Configura il componente per visualizzare una certa data
     *
     * @param nome del mese
     */
    public void setMese(String nome) {
        this.getLabelNomeMese().setText(nome);
    }


    /**
     * Assegna il colore a tutti i testi.
     * <p/>
     * @param colore da assegnare
     */
    private void setTextColor (Color colore) {
        this.getLabelNomeMese().setForeground(colore);
    }



    private JLabel getLabelNomeMese() {
        return labelNomeMese;
    }


    private void setLabelNomeMese(JLabel labelNomeMese) {
        this.labelNomeMese = labelNomeMese;
    }
}// fine della classe