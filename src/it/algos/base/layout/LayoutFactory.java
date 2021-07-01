/**
 * Title:     LayoutFactory
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      11-gen-2004
 */
package it.algos.base.layout;

import it.algos.base.errore.Errore;
import it.algos.base.pannello.Pannello;

/**
 * Factory method per gli oggetti di tipo layout.
 * </p>
 * Questa classe astratta: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Factory Method</b> <br>
 * <li> Fornisce i metodi statici di creazione degli oggetti di questo
 * package <br>
 * <li> L'uso del <i>design pattern</i> <b>Factory Method</b>, rende la
 * gestione del package trasparente verso l'utilizzatore che, dall'
 * esterno, <b>vede</b> solo due classi: <code><i>Layout</i></code>
 * e <code>LayoutFactory</code> <br>
 * <li> Si possono quindi cambiare le classi di questo package e la loro
 * gerarchia, senza che all'esterno del package si modifichino le
 * chiamate <br>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea ceresa, Alessandro valbonesi
 * @author gac
 * @version 1.0    / 11-gen-2004 ore 18.33.20
 */
public abstract class LayoutFactory {

    /**
     * Crea il layout per disporre gli oggetti.
     * <p/>
     *
     * @param pannello proprietario
     * @param orientamento codifica per l'orientamento (in interfaccia Layout)
     *
     * @return unLayout appena creato
     */
    public static Layout crea(Pannello pannello, int orientamento) {
        /* variabili e costanti locali di lavoro */
        Layout unLayout = null;

        try { // prova ad eseguire il codice

            /* crea un'istanza di layout pannello */
            unLayout = new LayoutFlusso(pannello, orientamento);

            /* Regola i valori di default */
            LayoutFactory.regolaBase(unLayout);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unLayout;
    }


    /**
     * Regola il layout di base coi valori di default.
     * <p/>
     *
     * @param layout da regolare
     */
    private static void regolaBase(Layout layout) {
        try { // prova ad eseguire il codice

            /* regolazioni iniziali di default */
            layout.setUsaGapFisso(Layout.USA_GAP_FISSO);
            layout.setAllineamento(Layout.ALLINEA_SX);
            layout.setRidimensionaParallelo(Layout.RIDIMENSIONA_PARALLELO);
            layout.setRidimensionaPerpendicolare(Layout.RIDIMENSIONA_PERPENDICOLARE);
            layout.setUsaScorrevole(Layout.USA_SCORREVOLE);
            layout.setScorrevoleBordato(Layout.USA_BORDO_SCORREVOLE);
            layout.setGapMinimo(Layout.GAP_MINIMO);
            layout.setGapPreferito(Layout.GAP_PREFERITO);
            layout.setGapMassimo(Layout.GAP_MASSIMO);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * /**
     * Crea il layout per disporre gli oggetti in verticale.
     * <p/>
     *
     * @param pannello proprietario
     *
     * @return unLayout appena creato
     */
    public static Layout verticale(Pannello pannello) {
        /* invoca il metodo delegato della classe */
        return LayoutFactory.crea(pannello, Layout.ORIENTAMENTO_VERTICALE);
    }


    /**
     * Crea il layout per disporre gli oggetti in orizzontale.
     * <p/>
     *
     * @param pannello proprietario
     *
     * @return unLayout appena creato
     */
    public static Layout orizzontale(Pannello pannello) {
        /* invoca il metodo delegato della classe */
        return LayoutFactory.crea(pannello, Layout.ORIENTAMENTO_ORIZZONTALE);
    }

//    /**
//     * Crea il layout per disporre gli oggetti nella pagina.
//     * <p/>
//     * Crea il layout di base <br>
//     * Modifica alcune regolazioni, tipiche della pagina <br>
//     *
//     * @param pagina proprietaria
//     *
//     * @return unLayout appena creato
//     */
//    public static Layout pagina(Pagina pagina) {
//        /* variabili e costanti locali di lavoro */
//        Layout unLayout = null;
//
//        try { // prova ad eseguire il codice
//
//            /* crea un'istanza di layout pannello */
//            unLayout = LayoutFactory.verticale(pagina);
//
//            /* sovrascrive i valori per la pagina */
//
//            /* la pagina tenta di ridimensionare i componenti */
//            unLayout.setRidimensionaComponenti(true);
//
//            /* la pagina usa l'allineamento definito nel componente */
//            unLayout.setAllineamento(Layout.ALLINEA_DA_COMPONENTI);
//
//            /* la pagina usa lo scorrevole */
//            unLayout.setUsaScorrevole(true);
//            unLayout.setScorrevoleBordato(false);
//
//            /* gap tra i componenti nella pagina */
//            unLayout.setGapMinimo(10);
//            unLayout.setGapPreferito(20);
//            unLayout.setGapMassimo(40);
//
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return unLayout;
//    }

//    /**
//     * Crea il layout per disporre le primitive GUI in un <i>pannello</i>. <br>
//     */
//    public static LayoutComponente componente() {
//        /* variabili e costanti locali di lavoro */
//        LayoutComponente unLayoutComponente = null;
//
//        try {	// prova ad eseguire il codice
//            unLayoutComponente = new LayoutComponente();
//        } catch (Exception unErrore) {	// intercetta l'errore
//            /* mostra il messaggio di errore */
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return unLayoutComponente;
//    } // fine del metodo

//    /**
//     * Crea il layout per disporre le primitive GUI in un <i>pannello</i> orizzontale <br>
//     */
//    public static LayoutComponente componenteOrizzontale() {
//        /* variabili e costanti locali di lavoro */
//        LayoutComponente unLayoutComponente = null;
//
//        try {	// prova ad eseguire il codice
//            unLayoutComponente = componente();
//            unLayoutComponente.setPosizionamentoVerticale(false);
//        } catch (Exception unErrore) {	// intercetta l'errore
//            /* mostra il messaggio di errore */
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return unLayoutComponente;
//    } // fine del metodo

}// fine della classe
