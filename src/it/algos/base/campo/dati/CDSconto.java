/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      22-nov-2006
 */
package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.validatore.Validatore;

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
public class CDSconto extends CDPercentuale {


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public CDSconto() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri.
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto <br>
     */
    public CDSconto(Campo unCampoParente) {
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
            vld = super.getValidatore();
            vld.setAccettaDecimali(false);
            vld.setAccettaNegativi(false);
            vld.setLunghezzaMassima(2);
            vld.setValoreMassimo(99);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


}// fine della classe
