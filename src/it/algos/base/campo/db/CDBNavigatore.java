/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      25-gen-2005
 */
package it.algos.base.campo.db;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.logica.CampoLogica;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;

/**
 * Campo DB per la gestione di un Navigatore pilotato.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-gen-2005 ore 13.47.16
 */
public final class CDBNavigatore extends CDBBase {

    /**
     * Costruttore base senza parametri.
     * <p/>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public CDBNavigatore() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDBNavigatore(Campo unCampoParente) {
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
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setCampoFisico(false);
    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        Modulo moduloPadre;
        CampoLogica campoLogica;
        Navigatore navCampo;

        try { // prova ad eseguire il codice

            /* inizializzo nella superclasse e controllo se riuscito */
            super.inizializza();

            campoLogica = this.getCampoParente().getCampoLogica();
            navCampo = campoLogica.getNavigatore();
            moduloPadre = this.getCampoParente().getModulo();

            if (moduloPadre != null) {
                /* rende il navigatore pilotato dal modulo di questo campo */
                navCampo.pilotaNavigatore(moduloPadre);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }

}// fine della classe
