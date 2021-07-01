/**
 * Title:     PortaleFactory
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      19-mar-2004
 */
package it.algos.base.portale;

import it.algos.base.errore.Errore;
import it.algos.base.lista.Lista;
import it.algos.base.lista.ListaDefault;
import it.algos.base.navigatore.Navigatore;

/**
 * Factory per la creazione dei Portali.
 * </p>
 * Questa classe astratta: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Factory Method</b> </li>
 * <li> Fornisce i metodi statici di creazione degli oggetti di questo
 * package </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 19-mar-2004 ore 14.29.22
 */
public abstract class PortaleFactory extends Object {

    /**
     * Costruttore semplice senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public PortaleFactory() {
        /* rimanda al costruttore della superclasse */
        super();
    }// fine del metodo costruttore semplice


    /**
     * Crea un PortaleLista per un Navigatore.
     * <p/>
     * Crea il Portale e la sua Lista contenuta <br>
     * Regola la variabile d'istanza Lista del Portale <br>
     *
     * @param unNavigatore il navigatore di riferimento
     *
     * @return il PortaleLista creato
     */
    public static PortaleLista lista(Navigatore unNavigatore) {
        /* variabili e costanti locali di lavoro */
        PortaleLista unPortale = null;
        Lista unaLista = null;

        try { // prova ad eseguire il codice
            /* crea un'istanza della classe da ritornare */
            unPortale = new PortaleLista(unNavigatore);

            /* crea un'istanza della Lista e gli assegna il Portale */
            unaLista = new ListaDefault(unPortale);

            /* regola la variabile d'istanza del portale */
            unPortale.setLista(unaLista);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unPortale;
    }


    /**
     * Crea un PortaleScheda per un Navigatore.
     * <p/>
     * Crea il Portale e la sua Scheda contenuta. <br>
     *
     * @param unNavigatore il navigatore di riferimento
     *
     * @return il PortaleScheda creato
     */
    public static PortaleScheda scheda(Navigatore unNavigatore) {
        /* variabili e costanti locali di lavoro */
        PortaleScheda unPortale = null;

        try {    // prova ad eseguire il codice

            /* crea un'istanza della classe da ritornare */
            unPortale = new PortaleScheda(unNavigatore);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unPortale;
    }

//    /**
//     * Crea un Portale Navigatore Interno per un Navigatore.
//     * <p/>
//     * Crea il Portale e il suo Navigatore contenuto. <br>
//     *
//     * @param unNavigatore il navigatore di riferimento
//     * @param moduloNavInterno il modulo del navigatore interno
//     *
//     * @return il PortaleNavigatore creato
//     */
//    public static PortaleNavigatore navigatoreInterno(Navigatore unNavigatore, Modulo moduloNavInterno) {
//        /* variabili e costanti locali di lavoro */
//        PortaleNavigatore unPortale = null;
//        Navigatore navInterno = null;
//        Modulo moduloNavPrincipale = null;
//
//        try {	// prova ad eseguire il codice
//
//            moduloNavPrincipale = unNavigatore.getModulo();
//
//            /* crea un'istanza della classe da ritornare */
//            unPortale = new PortaleNavigatore(unNavigatore);
//
//            /* crea il navigatore interno */
//            navInterno = NavigatoreFactory.listaNavigatore(moduloNavPrincipale, moduloNavInterno);
//
//            /* assegna il navigatore interno al Portale */
//            unPortale.setNavigatore(navInterno);
//
//        } catch (Exception unErrore) {	// intercetta l'errore
//            Errore.crea(unErrore);
//        } /* fine del blocco try-catch */
//
//        /* valore di ritorno */
//        return unPortale;
//    }


}// fine della classe
