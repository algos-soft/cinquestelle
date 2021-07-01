/**
 * Title:     CDValuta
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      8-feb-2004
 */
package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.tavola.renderer.RendererPercentuale;
import it.algos.base.validatore.Validatore;
import it.algos.base.validatore.ValidatoreFactory;

/**
 * Campo Dati per gestione Valuta.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Implementa i metodi astratti della superclasse per regolare le
 * conversioni tra i vari tipi di dati </li>
 * <li>  </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 8-feb-2004 ore 11.49.06
 */
public class CDPercentuale extends CDReale {


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public CDPercentuale() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri.
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto <br>
     */
    public CDPercentuale(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

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
        Validatore vld;

        try { // prova ad eseguire il codice

            /* assegna un validatore di default al campo */
            vld = ValidatoreFactory.numDec2();

            /* assegna un validatore di default al campo */
            this.setValidatore(vld);
            this.setRenderer(new RendererPercentuale(this.getCampoParente()));


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Ritorna il double corrispondente al valore in memoria.
     * <p/>
     *
     * @return il numero Double corrispondente alla memoria
     */
    protected double getDoppioMemoria() {
        /* variabili e costanti locali di lavoro */
        double doppio = 0;

        try {    // prova ad eseguire il codice

            doppio = super.getDoppioMemoria();
            doppio *= 100;
            doppio = Lib.Mat.arrotonda(doppio, 2);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return doppio;
    }


    /**
     * Ritorna il double corrispondente alla stringa contenuta nel video.
     * <p/>
     * Metodo invocato da videoMemoria <br>
     */
    @Override protected double getDoppioVideo() {
        double doppio = 0;

        try { // prova ad eseguire il codice

            doppio = super.getDoppioVideo();
            doppio /= 100;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return doppio;
    }


}// fine della classe
