/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      16-feb-2005
 */
package it.algos.base.navigatore;

import it.algos.base.errore.Errore;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.Modulo;
import it.algos.base.portale.Portale;

/**
 * Navigatore con due Navigatori, il primo pilota il secondo.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 16-feb-2005 ore 18.05
 */
public class NavigatoreNN extends NavigatoreXN {

    /**
     * Nome chiave del navigatore master da recuperare dalla collezione
     * del modulo master.
     */
    private String chiaveNavMaster = null;

    /**
     * Navigatore Master.
     * <p/>
     * E' il navigatore contenuto nella parte A del PortaleNavigatore<br>
     * Pilota il Navigatore contenuto nella parte B del PortaleNavigatore
     */
    private Navigatore navMaster = null;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param moduloMaster modulo di riferimento
     * @param chiaveNavMaster nome chiave del navigatore Master (nel modulo Master)
     * @param moduloSlave modulo per il navigatore slave
     * @param chiaveNavSlave nome chiave del navigatore Slave (nel modulo Slave)
     */
    public NavigatoreNN(Modulo moduloMaster,
                        String chiaveNavMaster,
                        Modulo moduloSlave,
                        String chiaveNavSlave) {

        /* rimanda al costruttore della superclasse */
        super(moduloMaster, moduloSlave, chiaveNavSlave);

        /* regola le variabili di istanza con i parametri */
        this.setChiaveNavMaster(chiaveNavMaster);

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
        Portale pnMaster = null;

        try {    // prova ad eseguire il codice

            /* recupera e registra il navigatore Master da utilizzare */
            this.registraNavMaster();

            /* recupera il Portale Navigatore del navigatore Master */
            pnMaster = this.getNavMaster().getPortaleNavigatore();

            /* aggiunge il PN del navigatore Master al componente A
             * del Portale navigatore */
            this.getPortaleNavigatore().setPortaleA(pnMaster);

            /* numero righe lista di default per il navigatore Master */
//            this.getNavMaster().setRigheLista(10);

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

            /* inizializzo nella superclasse dopo aver regolato i contenuti del PN */
            super.inizializza();

            /* regola il navigatore Master */
            this.regolaNavMaster();

            /* regola i riferimenti incrociati sui navigatori
             * pilota e pilotato*/
            this.getNavMaster().setNavPilotato(this.getNavSlave());
            this.getNavSlave().setNavPilota(this.getNavMaster());

            /* se questo Navigatore e' pilotato,
             * imposta il navigatore Master come pilotato
             * da questo navigatore. */
            if (this.isPilotato()) {
                this.pilotaNavMaster();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizializza


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Avvia il Navigatore Master
     * Avvia nella superclasse
     */
    public void avvia() {
        this.getNavMaster().avvia();
        super.avvia();
    }


    /**
     * Sincronizza il Navigatore.
     * <p/>
     * Sincronizza il Navigatore Master
     * Sincronizza nella superclasse
     */
    public void sincronizza() {
        this.getNavMaster().sincronizza();
        super.sincronizza();
    }


    /**
     * Recupera il Navigatore Master.
     * <p/>
     * Recupera il navigatore dal modulo Master.
     * Registra il riferimento in questo Navigatore.
     */
    private void registraNavMaster() {
        /* variabili e costanti locali di lavoro */
        String chiave = null;
        Modulo modulo = null;
        Navigatore nav = null;

        try {    // prova ad eseguire il codice

            modulo = this.getModulo();
            chiave = this.getChiaveNavMaster();
            nav = this.getNavigatore(modulo, chiave);
            if (nav != null) {
                this.setNavMaster(nav);
            } else {
                throw new Exception("Navigatore master " + chiave + " non trovato.");
            }// fine del blocco if-else
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola il Navigatore Master.
     * <p/>
     * Imposta il navigatore senza finestra.
     */
    private void regolaNavMaster() {
        /* variabili e costanti locali di lavoro */
        try {    // prova ad eseguire il codice

            this.getNavMaster().setUsaFinestra(false);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Rende questo navigatore pilotato da un dato Modulo.
     * <p/>
     *
     * @param modulo il modulo che deve pilotare questo navigatore.
     */
    public void pilotaNavigatore(Modulo modulo) {

        try {    // prova ad eseguire il codice

            /* rende questo navigatore pilotato dal modulo dato */
            super.pilotaNavigatore(modulo);

            /* rende il navigatore master pilotato da questo navigatore */
            this.pilotaNavMaster();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Rende il navigatore Master pilotato da questo navigatore.
     * <p/>
     */
    private void pilotaNavMaster() {
        /* variabili e costanti locali di lavoro */
        Modulo moduloPilota = null;

        try {    // prova ad eseguire il codice

            /* rende il navigatore master pilotato dallo
             * stesso modulo che pilota questo navigatore */
            moduloPilota = this.getCampoLink().getCampoDB().getModuloLinkato();
            this.getNavMaster().pilotaNavigatore(moduloPilota);

            /* imposta il navigatore master come pilotato
             * da questo navigatore */
            this.setNavPilotato(this.getNavMaster());
            this.getNavMaster().setNavPilota(this);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola l'elenco di valori pilota.
     * <p/>
     * Regola i valori nella superclasse<br>
     * Se cambiati li gira al navigatore Master<br>
     *
     * @param valoriPilota l'elenco dei valori pilota
     *
     * @return true se i valori pilota sono stati modificati.
     */
    public boolean setValoriPilota(int[] valoriPilota) {
        /* variabili e costanti locali di lavoro */
        boolean cambiato = false;
        Navigatore nav = null;

        try { // prova ad eseguire il codice
            cambiato = super.setValoriPilota(valoriPilota);
            if (cambiato) {
                nav = this.getNavMaster();
                if (nav != null) {
                    nav.setValoriPilota(this.getValoriPilota());
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return cambiato;

    }


    /**
     * Regola l'altezza della lista.
     * <p/>
     * Il valore e' espresso in righe <br>
     * Se non regolato, usa l'altezza di default della Lista <br>
     * Il navigatore XN regola la lista del navigatore Master.
     *
     * @param righe l'altezza della lista espressa in righe
     */
    public void setRigheLista(int righe) {
        try { // prova ad eseguire il codice
            this.getNavMaster().setRigheLista(righe);
            super.setRigheLista(righe);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Recupera i valori pilota di questo navigatore.
     * <p/>
     *
     * @return i valori pilota
     */
    public int[] recuperaValoriPilota() {
        return this.getValoriPilota();
    }


    /**
     * Abilita l'uso delle dei bottoni di spostamento di ordine
     * del record su e giu nella lista.
     * <p/>
     * Opera sul navigatore Master.<br>
     * Visualizza i bottoni nella toolbar della lista.<br>
     * La lista e' spostabile solo se ordinata sul campo ordine.<br>
     *
     * @param usaFrecce per usare i bottoni di spostamento
     */
    public void setUsaFrecceSpostaOrdineLista(boolean usaFrecce) {
        this.getNavMaster().setUsaFrecceSpostaOrdineLista(usaFrecce);
    }


    /**
     * Recupera la lista pilota di questo Navigatore.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi.
     *
     * @return la lista pilota di questo navigatore
     */
    protected Lista getListaPilota() {
        return this.getNavMaster().getLista();
    }


    private String getChiaveNavMaster() {
        return chiaveNavMaster;
    }


    private void setChiaveNavMaster(String chiaveNavMaster) {
        this.chiaveNavMaster = chiaveNavMaster;
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
        this.getNavMaster().setTipoIcona(unTipoIcona);
        super.setTipoIcona(unTipoIcona);
    }


    /**
     * flag - usa i bottoni di selezione.
     *
     * @param usaSelezione true per usare i tre bottoni di selezione dei records <br>
     */
    public void setUsaSelezione(boolean usaSelezione) {
        this.getNavMaster().setUsaSelezione(usaSelezione);
        super.setUsaSelezione(usaSelezione);
    }


    /**
     * Abilita l'uso dei caratteri per filtrare la lista.
     *
     * @param usaCarattereFiltro
     */
    public void setUsaCarattereFiltro(boolean usaCarattereFiltro) {
        this.getNavMaster().setUsaCarattereFiltro(usaCarattereFiltro);
        super.setUsaCarattereFiltro(usaCarattereFiltro);
    }


    /**
     * Ritorna il navigatore Master di questo navigatore.
     * <p/>
     *
     * @return il navigatore Master
     */
    public Navigatore getNavMaster() {
        return navMaster;
    }


    /**
     * Regola il navigatore Master di questo navigatore.
     * <p/>
     *
     * @param navMaster il navigatore Master
     */
    private void setNavMaster(Navigatore navMaster) {
        this.navMaster = navMaster;
    }

}// fine della classe
