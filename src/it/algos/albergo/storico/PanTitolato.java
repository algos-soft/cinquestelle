/**
 * Title:     PanNavStorico
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-mar-2009
 */
package it.algos.albergo.storico;

import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.layout.Layout;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pannello.PannelloFlusso;

import javax.swing.JComponent;
import javax.swing.JLabel;
import java.awt.Font;

/**
 * Pannello contenitore di un Navigatore dello Storico
 * </p>
 * Ha un titolo e un Navigatore
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-mar-2009 ore 18.10.49
 */
class PanTitolato extends PannelloFlusso {

    private String titolo;
    private Pannello panComponente;


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param titolo del pannello
     */
    public PanTitolato(String titolo) {
        /* rimanda al costruttore della superclasse */
        super(Layout.ORIENTAMENTO_VERTICALE);

        /* regola le variabili di istanza coi parametri */
        this.setTitolo(titolo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        JLabel labelTitolo;
        Pannello pan;

        try { // prova ad eseguire il codice

            this.setUsaGapFisso(true);
            this.setGapPreferito(1);
            
            /* pannello placeholder per il Navigatore */
            pan = PannelloFactory.orizzontale(null);
            this.setPanComponente(pan);

            /* label con il titolo */
            labelTitolo = new JLabel(this.getTitolo());
            labelTitolo.setFont(FontFactory.creaScreenFont(Font.BOLD, 11f));

            this.add(labelTitolo);
            this.add(this.getPanComponente());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Assegna un componente al pannello.
     * <p/>
     * @param comp componente da inserire
     */
    public void setComponente (JComponent comp) {
        /* variabili e costanti locali di lavoro */
        Pannello pan;

        try {    // prova ad eseguire il codice

            pan = this.getPanComponente();
            pan.removeAll();
            pan.add(comp);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

}

    /**
     * Assegna un navigatore al pannello.
     * <p/>
     * @param nav Navigatore da inserire
     */
    public void setNavigatore (Navigatore nav) {
        /* variabili e costanti locali di lavoro */
        JComponent comp;

        try {    // prova ad eseguire il codice

            comp = nav.getPortaleNavigatore();
            this.setComponente(comp);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

}






    private String getTitolo() {
        return titolo;
    }


    private void setTitolo(String titolo) {
        this.titolo = titolo;
    }


    private Pannello getPanComponente() {
        return panComponente;
    }


    private void setPanComponente(Pannello panComponente) {
        this.panComponente = panComponente;
    }
}// fine della classe
