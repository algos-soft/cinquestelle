/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      16-feb-2005
 */
package it.algos.base.navigatore;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.Modulo;
import it.algos.base.portale.Portale;

/**
 * Superclasse astratta per Navigatore con un Navigatore Interno
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 16-feb-2005 ore 18.05
 */
public abstract class NavigatoreXN extends NavigatoreBase {

    /**
     * Modulo del navigatore slave.
     */
    private Modulo moduloSlave = null;

    /**
     * Nome chiave del navigatore slave da recuperare dalla
     * collezione del modulo slave.
     */
    private String chiaveNavSlave = null;

    /**
     * Navigatore slave.
     * <p/>
     * E' il navigatore contenuto nella parte B del PortaleNavigatore<br>
     * E' pilotato dall'oggetto contenuto nella parte A del PortaleNavigatore
     * (una Lista o un Navigatore)
     */
    private Navigatore navSlave = null;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param moduloMaster modulo di riferimento
     * @param moduloSlave modulo del navigatore slave
     * @param chiaveNavSlave nome chiave del navigatore slave nel modulo slave
     */
    public NavigatoreXN(Modulo moduloMaster, Modulo moduloSlave, String chiaveNavSlave) {

        /* rimanda al costruttore della superclasse */
        super(moduloMaster);

        /* regola le variabili di istanza con i parametri */
        this.setModuloSlave(moduloSlave);
        this.setChiaveNavSlave(chiaveNavSlave);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Portale portaleNavSlave = null;

        try {    // prova ad eseguire il codice


            this.setUsaPannelloUnico(false);

            /* registra il navigatore slave da utilizzare */
            this.registraNavSlave();

            /* recupera il Portale del Navigatore slave */
            portaleNavSlave = this.getNavSlave().getPortaleNavigatore();

            /* aggiunge il portale del Navigatore slave al componente B
             * del Portale navigatore */
            this.getPortaleNavigatore().setPortaleB(portaleNavSlave);

            /* numero righe lista di default per il navigatore Slave */
//            this.getNavSlave().setRigheLista(10);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Inizializzazione dell'oggetto.<br>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche.<br>
     * Viene chiamato dall'inizializzazione del Modulo.<br>
     */
    public void inizializza() {

        try { // prova ad eseguire il codice

            /* inizializza nella superclasse */
            super.inizializza();

            /* regola il navigatore slave */
            this.regolaNavSlave();

            /* regola i riferimenti incrociati sui navigatori
         * pilota e pilotato*/
            this.getNavSlave().setNavPilota(this);
            this.setNavPilotato(this.getNavSlave());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizializza


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Avvia il Navigatore slave
     * Avvia nella superclasse
     */
    public void avvia() {
        this.getNavSlave().avvia();
        super.avvia();
    }// fine del metodo lancia


    /**
     * Recupera la lista pilota di questo Navigatore.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi.
     *
     * @return la lista pilota di questo navigatore
     */
    protected Lista getListaPilota() {
        return null;
    }


    /**
     * Recupera un navigatore da un modulo.
     * <p/>
     * Se non trova il navigatore richiesto visualizza un errore.
     *
     * @param modulo il modulo dal quale recuperare il navigatore
     * @param chiave la chiave del navigatore da recuperare
     *
     * @return il navigatore recuperato
     */
    protected Navigatore getNavigatore(Modulo modulo, String chiave) {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;

        try {    // prova ad eseguire il codice

            if (Lib.Testo.isVuota(chiave)) {
                throw new Exception("Chiave navigatore non specificata.");
            }// fine del blocco if

            if (modulo == null) {
                throw new Exception("Modulo nullo.");
            }// fine del blocco if

            nav = modulo.getNavigatore(chiave);

            if (nav == null) {
                throw new Exception("Navigatore con chiave " +
                        chiave +
                        " non trovato nel modulo " +
                        modulo.getNomeChiave());
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


    /**
     * Recupera il Navigatore slave.
     * <p/>
     * Recupera il navigatore dal modulo slave.<br>
     * Registra il riferimento in questo Navigatore.<br>
     */
    private void registraNavSlave() {
        /* variabili e costanti locali di lavoro */
        String chiave = null;
        Modulo modulo = null;
        Navigatore nav = null;

        try {    // prova ad eseguire il codice

            modulo = this.getModuloSlave();
            chiave = this.getChiaveNavSlave();
            nav = this.getNavigatore(modulo, chiave);
            if (nav != null) {
                this.setNavSlave(nav);
            } else {
                throw new Exception("Navigatore slave " + chiave + " non trovato.");
            }// fine del blocco if-else
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola il Navigatore slave.
     * <p/>
     * Registra il campo di link nel Navigatore slave.
     * Imposta il navigatore slave senza finestra.
     */
    private void regolaNavSlave() {

        try {    // prova ad eseguire il codice

            /* rende il navigatore slave pilotato dal
             * modulo di questo navigatore */
            this.getNavSlave().pilotaNavigatore(this.getModulo());

            /* toglie la eventuale finestra al navigatore slave */
            this.getNavSlave().setUsaFinestra(false);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Regola l'altezza della lista.
     * <p/>
     * Il valore e' espresso in righe <br>
     * Se non regolato, usa l'altezza di default della Lista <br>
     * Il navigatore XN regola la lista del navigatore Slave.
     *
     * @param righe l'altezza della lista espressa in righe
     */
    public void setRigheLista(int righe) {
        try { // prova ad eseguire il codice
            this.getNavSlave().setRigheLista(righe);
            super.setRigheLista(righe);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Chiede al navigatore il permesso di chiuderlo.
     * <p/>
     * Presenta eventuali messaggi all'utente per risolvere situazioni aperte <br>
     * Tenta la chiusura del navigatore slave.<br>
     * Se riuscito, rimanda alla superclasse, altrimenti ritorna false.
     *
     * @return true se riuscito.
     */
    public boolean richiediChiusura() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        Navigatore navSlave = null;

        try {    // prova ad eseguire il codice

            navSlave = this.getNavSlave();
            riuscito = navSlave.richiediChiusura();
            if (riuscito) {
                riuscito = super.richiediChiusura();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Abilita l'uso dei caratteri per filtrare la lista.
     *
     * @param usaCarattereFiltro
     */
    public void setUsaCarattereFiltro(boolean usaCarattereFiltro) {
        this.getNavSlave().setUsaCarattereFiltro(usaCarattereFiltro);
        super.setUsaCarattereFiltro(usaCarattereFiltro);
    }


    /**
     * Ritorna il modulo del Navigatore interno.
     * <p/>
     *
     * @return il modulo del Navigatore interno
     */
    private Modulo getModuloSlave() {
        return moduloSlave;
    }


    /**
     * Regola il modulo del Navigatore interno.
     * <p/>
     *
     * @param moduloSlave il modulo del Navigatore interno
     */
    private void setModuloSlave(Modulo moduloSlave) {
        this.moduloSlave = moduloSlave;
    }


    private String getChiaveNavSlave() {
        return chiaveNavSlave;
    }


    private void setChiaveNavSlave(String chiaveNavSlave) {
        this.chiaveNavSlave = chiaveNavSlave;
    }


    /**
     * Flag - seleziona l'icona piccola, media o grande.
     * <p/>
     *
     * @param unTipoIcona codice tipo icona (Codifica in ToolBar)
     *
     * @see it.algos.base.toolbar.ToolBar
     */
    public void setTipoIcona(int unTipoIcona) {
        this.getNavSlave().setTipoIcona(unTipoIcona);
    }


    /**
     * flag - usa i bottoni di selezione.
     *
     * @param usaSelezione true per usare i tre bottoni di selezione dei records <br>
     */
    public void setUsaSelezione(boolean usaSelezione) {
        this.getNavSlave().setUsaSelezione(usaSelezione);
    }


    /**
     * Ritorna il navigatore Slave di questo navigatore.
     * <p/>
     *
     * @return il navigatore Slave
     */
    public Navigatore getNavSlave() {
        return navSlave;
    }


    /**
     * Regola il navigatore Slave di questo navigatore.
     * <p/>
     *
     * @param navSlave il navigatore Slave
     */
    private void setNavSlave(Navigatore navSlave) {
        this.navSlave = navSlave;
    }

}// fine della classe
