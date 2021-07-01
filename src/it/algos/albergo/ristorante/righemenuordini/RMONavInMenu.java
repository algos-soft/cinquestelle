/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      1-mar-2005
 */
package it.algos.albergo.ristorante.righemenuordini;

import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.piatto.Piatto;
import it.algos.albergo.ristorante.righemenupiatto.RMP;
import it.algos.albergo.ristorante.righemenutavolo.RMT;
import it.algos.albergo.ristorante.righemenutavolo.RMTNavInMenu;
import it.algos.albergo.ristorante.righetavoloordini.RTO;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.util.Operatore;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.NavigatoreLS;
import it.algos.base.navigatore.info.InfoLista;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.tavola.Tavola;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Business logic per RMONavInMenu.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Gestisce la business-logic della GUI </li>
 * <li> Riceve le invocazioni da una classe di tipo <code>Gestore</code> </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 1-mar-2005 ore 11.59.34
 */
public final class RMONavInMenu extends NavigatoreLS {

    /* moduli di uso comune */
    private static Modulo moduloCategoria = null;

    private static Modulo moduloPiatto = null;

    private static Modulo moduloRMO = null;

    private static Modulo moduloRMP = null;

    private static Modulo moduloRMT = null;

    private static Modulo moduloRTO = null;

    /* campi di uso comune */
    private static Campo campoRMOlinkRMP = null;

    private static Campo campoRMOlinkRMT = null;

    private static Campo campoRTOlinkRMO = null;

    private static Campo campoRMOqta = null;

    private static Campo campoRMTOrdinato = null;

    private RMTNavInMenu rmtNavigatore = null;


    /**
     * Costruttore completo con parametri.
     *
     * @param unModulo modulo di riferimento
     */
    public RMONavInMenu(Modulo unModulo) {
        /* rimanda al costruttore della superclasse */
        super(unModulo);

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
        this.setNomeChiave(RMO.NAV_IN_MENU);
        this.setUsaPannelloUnico(true);
        this.setUsaFrecceSpostaOrdineLista(true);
        this.setUsaRicerca(false);
        this.getLista().setOrdinabile(false);
        this.setNomeVista(RMO.VISTA_IN_MENU);
        this.setNomeSet(RMO.SET_EXTRA);
        this.setAggiornamentoTotaliContinuo(true);
    }// fine del metodo inizia


    /**
     * Inizializzazione dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche.<br>
     */
    public void inizializza() {

        super.inizializza();

        try { // prova ad eseguire il codice
            this.regolaComune();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizializza


    /**
     * .
     * <p/>
     */
    public void avvia() {
        super.avvia();
    }


    /**
     * Regola i riferimenti ad oggetti di uso comune..
     * <p/>
     */
    private void regolaComune() {
        /* variabili e costanti locali di lavoro */
        String nome = null;

        try {    // prova ad eseguire il codice

            /* moduli */
            moduloCategoria = Progetto.getModulo(Ristorante.MODULO_CATEGORIA);
            moduloPiatto = Progetto.getModulo(Ristorante.MODULO_PIATTO);
            moduloRMO = Progetto.getModulo(Ristorante.MODULO_RIGHE_MENU_ORDINI);
            moduloRMP = Progetto.getModulo(Ristorante.MODULO_RIGHE_PIATTO);
            moduloRMT = Progetto.getModulo(Ristorante.MODULO_RIGHE_TAVOLO);
            moduloRTO = Progetto.getModulo(Ristorante.MODULO_RIGHE_ORDINI);

            /* navigatore righemenutavolo */
            this.rmtNavigatore = (RMTNavInMenu)moduloRMT.getNavigatore(RMT.NAV_IN_MENU);

            /* campo di link da RMO a RMP */
            nome = RMO.CAMPO_RIGA_MENU_PIATTO;
            campoRMOlinkRMP = moduloRMO.getCampo(nome);

            /* campo link da RMO a RMT */
            nome = RMO.CAMPO_RIGA_MENU_TAVOLO;
            campoRMOlinkRMT = moduloRMO.getCampo(nome);

            /* campo di link da RTO a RMO */
            nome = RTO.CAMPO_RMORDINI;
            campoRTOlinkRMO = moduloRTO.getCampo(nome);

            /* campo quantita' di comande */
            nome = RMO.CAMPO_QUANTITA;
            campoRMOqta = this.getModulo().getCampo(nome);

            /* campo flag tavolo ordinato */
            nome = RMT.Cam.comandato.get();
            campoRMTOrdinato = moduloRMT.getCampo(nome);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Sincronizza lo stato del Navigatore.
     * <p/>
     * Controlla lo stato di tutti i suoi componenti <br>
     * Elabora la sua business logic <br>
     * Regola la GUI di ogni conseguenza (menu, bottoni, ecc) <br>
     * Invoca il metodo sovrascritto della superclasse <br>
     */
    public void sincronizza() {
        try { // prova ad eseguire il codice
            ;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* invoca il metodo sovrascritto della superclasse */
        super.sincronizza();
    }


    /**
     * Registra sul database il valore di un campo per un dato record.
     * <p/>
     * Se si tratta del campo Quantita':
     * - Controlla che il numero non sia superiore a ad un massimo stabilito
     * - Sincronizza il numero di comande dopo aver registrato nella superclasse
     *
     * @param campo il campo da registrare
     * @param codice codice chiave del record da registrare
     *
     * @return true se riuscito
     */
    protected boolean cellaModificata(Campo campo, int codice) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        String chiaveCampoQta;
        int numero;
        int max;


        try { // prova ad eseguire il codice
            super.cellaModificata(campo, codice);

            /* limite massimo stabilito */
            max = 99;

            numero = this.query().valoreInt(campo, codice);
            /* controlla che il numero inserito non sia superiore a max */
            if (numero <= max) {

//                /* registra nella superclasse */
//                riuscito = super.cellaModificata(campo, codice, valoreMemoria);

                /* se si tratta del campo quantita',
                 * sincronizza i record delle comande per la riga */
                chiaveCampoQta = campoRMOqta.getChiaveCampo();
                if (campo.getChiaveCampo().equals(chiaveCampoQta)) {
                    this.sincronizzaComande(codice);
                }// fine del blocco if

            } else {
                new MessaggioAvviso("Il numero massimo e' " + max);
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;

    }


    /**
     * Tasto Enter in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     */
    public void listaEnter() {
        /* variabili e costanti locali di lavoro */
        Tavola tavola = null;
        int riga = 0;
        int colonna = 0;
        int totRighe = 0;
        Rectangle ret = null;

        try { // prova ad eseguire il codice
            /* recupera la tavola */
            tavola = this.getPortaleLista().getLista().getTavola();

            if (tavola != null) {
                /* recupera la riga selezionata e le righe totali */
                riga = tavola.getSelectedRow();
                riga++;
                totRighe = tavola.getRowCount();

                if (riga < totRighe) {
                    tavola.setRowSelectionInterval(riga, riga);
                } else {
                    this.rmtNavigatore.tavoloSuccessivo();
                }// fine del blocco if-else
            }// fine del blocco if

            Component com = tavola.getEditorComponent();
            riga = tavola.getSelectedRow();
            colonna = tavola.getSelectedColumn();
            ret = tavola.getCellRect(riga, colonna, false);
            riga = 318;
            colonna = 52;
            MouseEvent ev = new MouseEvent(tavola, 502, 31097659, 16, riga, colonna, 2, false);
            MouseListener[] orecchio = null;
            orecchio = (MouseListener[])tavola.getListeners(MouseListener.class);

            for (int k = 0; k < orecchio.length; k++) {
                orecchio[k].mouseReleased(ev);
            } // fine del ciclo for
            int a = 87;
//       new MessaggioAvviso(riga+"");
            //todo e qui la festa!
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sincronizza il numero di comande per una riga.
     * <p/>
     *
     * @param codice il codice della riga da sincronizzare
     */
    private void sincronizzaComande(int codice) {
        /* variabili e costanti locali di lavoro */
        int comandeEsistenti = 0;
        int comandePreviste = 0;
        int delta = 0;


        try { // prova ad eseguire il codice

            comandeEsistenti = this.getNumeroComande(codice);

            comandePreviste = this.getModulo().query().valoreInt(campoRMOqta, codice);

            delta = comandeEsistenti - comandePreviste;
            if (delta != 0) {
                if (delta > 0) { // piu' esistenti che previste - da cancellare
                    this.eliminaComande(codice, Math.abs(delta));
                } else {         // piu' previste che esistenti - da creare
                    this.creaComande(Math.abs(delta));
                }// fine del blocco if-else

                /* regola il flag ordinato del tavolo */
                this.regolaFlagOrdinato();

                this.getNavPilota().getNavSlave().aggiornaLista();


            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna il Numero di record registrati per questo menu/tavolo.
     *
     * @param codice il codice di RMT
     *
     * @return valoreMemoria dopo elaborazioni eventuali
     */
    private int getNumeroComande(int codice) {
        /* variabili e costanti locali di lavoro */
        int quante = 0;
        Filtro filtro = null;

        try { // prova ad eseguire il codice
            filtro = FiltroFactory.crea(campoRTOlinkRMO, codice);
            quante = moduloRTO.query().contaRecords(filtro);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return quante;
    }


    /**
     * Elimina fisicamente dalla tavola le comande in eccesso.
     * <p/>
     * Elimina partendo dal numero d'ordine piu' alto (le ultime create)<br>
     *
     * @param codice del menu/tavolo
     * @param quantita numero di record da cancellare
     */
    private void eliminaComande(int codice, int quantita) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Ordine ordine = null;
        int[] chiavi = null;
        int chiave = 0;

        try { // prova ad eseguire il codice
            filtro = FiltroFactory.crea(campoRTOlinkRMO, codice);
            ordine = new Ordine();
            ordine.add(moduloRTO.getCampoOrdine(), Operatore.DISCENDENTE);
            chiavi = moduloRTO.query().valoriChiave(filtro, ordine);
            for (int k = 0; k < quantita; k++) {
                chiave = chiavi[k];
                moduloRTO.query().eliminaRecord(chiave);
            } // fine del ciclo for
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea fisicamente sulla tavola le comande mancanti.
     * <p/>
     * Richiede al navigatore slave la creazione dei record.
     *
     * @param quantita numero di record da creare
     */
    private void creaComande(int quantita) {

        try { // prova ad eseguire il codice
            for (int k = 0; k < quantita; k++) {
                this.getNavPilota().getNavSlave().creaRecord();
            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola il flag Ordinato sul record di testa (RMT).
     * <p/>
     * Recupera la quantita' totale di comande per il tavolo corrente<br>
     * Se maggiore di zero accende il flag ordinato su RMT<br>
     * Se uguale a zero spegne il flag ordinato su RMT<br>
     */
    private void regolaFlagOrdinato() {
        /* variabili e costanti locali di lavoro */
        int quanteComande = 0;
        boolean flag = false;

        try {    // prova ad eseguire il codice
            quanteComande = this.getQtaComande();
            if (quanteComande > 0) {
                flag = true;
            } else {
                flag = false;
            }// fine del blocco if-else

            /* aggiorna il flag ordinato del tavolo e se lo ha modificato
             * rinfresca la lista dei tavoli */
            if (this.setTavoloOrdinato(flag)) {
                this.refreshTavoli();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Recupera la quantita' totale di comande per il tavolo corrente.
     * <p/>
     *
     * @return la quantita' di comande
     */
    private int getQtaComande() {
        /* variabili e costanti locali di lavoro */
        int quantita = 0;
        int codiceRMT = 0;
        Filtro filtro = null;
        Number totale = null;

        try {    // prova ad eseguire il codice
            codiceRMT = this.getCodiceRMTCorrente();
            filtro = FiltroFactory.crea(campoRMOlinkRMT, codiceRMT);
            totale = moduloRMO.query().somma(campoRMOqta, filtro);
            quantita = Libreria.objToInt(totale);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return quantita;
    }


    /**
     * Imposta il flag Ordinato per il tavolo corrente.
     * <p/>
     *
     * @param flag il valore da impostare
     *
     * @return true se ha cambiato la precedente impostazione del flag ordinato
     */
    private boolean setTavoloOrdinato(boolean flag) {
        /* variabili e costanti locali di lavoro */
        boolean cambiato = false;
        int codiceRMT = 0;
        boolean valorePrecedente = false;

        try {    // prova ad eseguire il codice
            codiceRMT = this.getCodiceRMTCorrente();
            valorePrecedente = moduloRMT.query().valoreBool(campoRMTOrdinato, codiceRMT);
            if (valorePrecedente != flag) {
                moduloRMT.query().registraRecordValore(codiceRMT,
                        campoRMTOrdinato,
                        new Boolean(flag));
                cambiato = true;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cambiato;
    }


    /**
     * Rinfresca la lista dei tavoli.
     * <p/>
     */
    private void refreshTavoli() {
        /* variabili e costanti locali di lavoro */
        Navigatore navTavoli = null;

        try {    // prova ad eseguire il codice
            navTavoli = this.getNavPilota().getNavPilota();
            navTavoli.aggiornaLista();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Metodo chiamato ogni volta che i valori pilota sono cambiati.
     * <p/>
     * Intercetta il momento del cambio dei valori pilota.
     * Quando cambiati, se e' stata selezionata una nuova riga
     * sincronizza le RMO (crea/elimina/riordina i records)
     *
     * @param nuoviValori i nuovi valori pilota
     */
    public void valoriPilotaCambiati(int[] nuoviValori) {

        /* rimanda al metodo sovrascritto */
        super.valoriPilotaCambiati(nuoviValori);

        try { // prova ad eseguire il codice
            if (this.getCodiceRMTCorrente() != 0) {
                this.sincroRMOdaRMP();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sincronizza i record delle comande (RMO) in base alle RMP.
     * <p/>
     * Controlla che per ogni RMP ci sia la corrispondente riga<br>
     * Crea le righe mancanti e cancella le righe in eccesso<br>
     * Riordina le righe secondo la sequenza di RMP
     */
    private void sincroRMOdaRMP() {
        try {    // prova ad eseguire il codice

            /* cancella le righe in eccesso */
            /* non necessario, la cancellazione avviene
             * automaticamente grazie alla integrita' referenziale */
//            this.eliminaRigheInEccesso();

            /* aggiunge le righe in difetto */
            this.creaRigheInDifetto();

            /* riordina le righe */
            this.riordinaRMOdaRMP();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea i record delle comande (RMO) in base alle RMP.
     * <p/>
     * Chiamato dalla procedura di recupero<br>
     */
    public void creaRMO() {
        this.sincroRMOdaRMP();
    }

//    /**
//     * Elimina da RMO le righe in eccesso.
//     * <p/>
//     * spazzola i codici da RMO<br>
//     * verifica se esiste in RMP<br>
//     * se non esiste cancella la riga RMO<br>
//     */
//    private void eliminaRigheInEccesso() {
//        /* variabili e costanti locali di lavoro */
//        int[] linksDaRMO = null;
//        int[] codiciDaRMP = null;
//        int codiceLink = 0;
//        int codiceRMT = 0;
//        Filtro filtro = null;
//        Filtro filtro2 = null;
//        int[] chiavi = null;
//        int codChiave = 0;
//
//        try {	// prova ad eseguire il codice
//
//            /* recupera la lista dei link di riga a RMP attualmente presenti su RMO
//             * sono esclusi gli extra */
//            linksDaRMO = this.getLinksRMO();
//
//            /* recupera la lista ordinata delle RMP comandabili del menu */
//            codiciDaRMP = this.getCodiciRMPComandabili();
//
//            for (int k = 0; k < linksDaRMO.length; k++) {
//                codiceLink = linksDaRMO[k];
//                if (LibArray.isEsisteValore(codiceLink, codiciDaRMP) == false) {
//                    /* recupera il codice chiave della riga da eliminare */
//                    filtro = new Filtro();
//                    filtro2 = FiltroFactory.crea(campoRMOlinkRMT, codiceRMT);
//                    filtro.add(filtro2);
//                    filtro2 = FiltroFactory.crea(campoRMOlinkRMP, codiceLink);
//                    filtro.add(filtro2);
//                    chiavi = moduloRMO.query().valoriChiave(filtro);
//                    if (chiavi.length == 1) {
//                        codChiave = chiavi[0];
//                        if (codChiave != 0) {
//                            moduloRMO.query().eliminaRecord(codChiave);
//                        }// fine del blocco if
//                    }// fine del blocco if
//                }// fine del blocco if
//            } // fine del ciclo for
//        } catch (Exception unErrore) {	// intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//    }


    /**
     * Aggiunge a RMO le righe in difetto.
     * <p/>
     * Spazzola i codici da RMP<br>
     * verifica se esiste in RMO<br>
     * se non esiste crea la riga RMO<br>
     * e assegna il link a RMP<br>
     */
    private void creaRigheInDifetto() {
        /* variabili e costanti locali di lavoro */
        int[] codiciDaRMP = null;
        int[] linksDaRMO = null;
        int codiceLink = 0;
        int codChiave = 0;

        try {    // prova ad eseguire il codice

            /* recupera la lista ordinata delle RMP comandabili del menu */
            codiciDaRMP = this.getCodiciRMPComandabili();

            /* recupera la lista dei link di riga a RMP attualmente presenti su RMO
             * sono esclusi gli extra */
            linksDaRMO = this.getLinksRMO();

            /* confronta e crea le righe mancanti */
            for (int k = 0; k < codiciDaRMP.length; k++) {
                codiceLink = codiciDaRMP[k];
                if (!Lib.Array.isEsiste(linksDaRMO, codiceLink)) {
                    codChiave = this.creaRecord(); // regola automaticamente link a RMT
                    if (codChiave != -1) {
                        moduloRMO.query().registraRecordValore(codChiave,
                                campoRMOlinkRMP,
                                new Integer(codiceLink));
                    }// fine del blocco if
                }// fine del blocco if
            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Riordina le RMO in base all'ordine delle RMP.
     * <p/>
     * Recupera l'elenco ordinato delle RMP comandabili
     * Recupera i dati (disordinati) delle RMO (esclusi gli extra)
     * Spazzola le RMP e per ognuna cerca la corrispondente
     * RMO e se la sequenza e' errata assegna quella corretta.
     */
    private void riordinaRMOdaRMP() {
        /* variabili e costanti locali di lavoro */
        Query query = null;
        Dati dati = null;
        int[] codiciRMO = null;
        int[] ordiniRMO = null;
        int[] linksRMOtoRMP = null;
        int quanti = 0;
        Object valore = null;
        int[] codiciDaRMP = null;
        int codiceRMP = 0;
        int pos = 0;
        int codiceRMO = 0;
        int ordineRMO = 0;
        int ordinePrevisto = 0;
        int ultimoOrdine = 0;
        Ordine ordine = null;

        try {    // prova ad eseguire il codice

            /* recupera le righe RMO esclusi gli extra
             * recupera i campi codice, ordine e linkRMP */
            query = new QuerySelezione(moduloRMO);
            query.addCampo(moduloRMO.getCampoChiave());
            query.addCampo(moduloRMO.getCampoOrdine());
            query.addCampo(campoRMOlinkRMP);
            query.setFiltro(this.filtroRMONoExtra());
            dati = moduloRMO.query().querySelezione(query);

            /* crea un array per ogni colonna dei dati */
            quanti = dati.getRowCount();
            codiciRMO = new int[quanti];
            ordiniRMO = new int[quanti];
            linksRMOtoRMP = new int[quanti];
            for (int k = 0; k < quanti; k++) {
                valore = dati.getValueAt(k, 0); // codice
                codiciRMO[k] = Libreria.objToInt(valore);
                valore = dati.getValueAt(k, 1); // ordine
                ordiniRMO[k] = Libreria.objToInt(valore);
                valore = dati.getValueAt(k, 2); // link a RMP
                linksRMOtoRMP[k] = Libreria.objToInt(valore);
            } // fine del ciclo for

            dati.close();

            /* recupera la lista ordinata delle RMP comandabili del menu */
            codiciDaRMP = this.getCodiciRMPComandabili();

            /* spazzola la lista e regola l'ordine dei record RMO */
            for (int k = 0; k < codiciDaRMP.length; k++) {
                codiceRMP = codiciDaRMP[k];
                pos = Lib.Array.getPosizione(linksRMOtoRMP, codiceRMP);
                if (pos != -1) {
                    ordineRMO = ordiniRMO[pos];
                    ordinePrevisto = k + 1;
                    if (ordineRMO != ordinePrevisto) {
                        codiceRMO = codiciRMO[pos];
                        moduloRMO.query().registraRecordValore(codiceRMO,
                                moduloRMO.getCampoOrdine(),
                                new Integer(ordinePrevisto));
                    }// fine del blocco if
                }// fine del blocco if

                /* memorizza l'ultimo numero d'ordine attribuito */
                ultimoOrdine = ordinePrevisto;

            } // fine del ciclo for

            /* recupera le righe extra nell'ordine del campo Ordine
      * recupera i campi codice, ordine */
            query = new QuerySelezione(moduloRMO);
            query.addCampo(moduloRMO.getCampoChiave());
            query.addCampo(moduloRMO.getCampoOrdine());
            query.setFiltro(this.filtroRMOExtra());
            ordine = new Ordine();
            ordine.add(moduloRMO.getCampoOrdine());
            query.setOrdine(ordine);
            dati = moduloRMO.query().querySelezione(query);

            /* crea un array per ogni colonna dei dati */
            quanti = dati.getRowCount();
            codiciRMO = new int[quanti];
            ordiniRMO = new int[quanti];
            for (int k = 0; k < quanti; k++) {
                valore = dati.getValueAt(k, 0); // codice
                codiciRMO[k] = Libreria.objToInt(valore);
                valore = dati.getValueAt(k, 1); // ordine
                ordiniRMO[k] = Libreria.objToInt(valore);
            } // fine del ciclo for

            dati.close();

            /* spazzola la lista e regola l'ordine dei record RMO */
            for (int k = 0; k < quanti; k++) {
                ordineRMO = ordiniRMO[k];
                ordinePrevisto = ultimoOrdine + k + 1;
                if (ordineRMO != ordinePrevisto) {
                    codiceRMO = codiciRMO[k];
                    moduloRMO.query().registraRecordValore(codiceRMO,
                            moduloRMO.getCampoOrdine(),
                            new Integer(ordinePrevisto));
                }// fine del blocco if
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Recupera il codice del menu corrente.
     * <p/>
     *
     * @return il codice del menu corrente
     */
    private int getCodiceMenuCorrente() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        int codiceRMT = 0;
        Object valore = null;

        try {    // prova ad eseguire il codice
            codiceRMT = this.getCodiceRMTCorrente();
            valore = moduloRMT.query().valoreCampo(RMT.Cam.menu.get(), codiceRMT);
            codice = Libreria.objToInt(valore);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Ritorna il codice della RMT correntemente selezionata.
     * <p/>
     *
     * @return il codice della RMT selezionata
     *         (0 se nessuna o piu' di una)
     */
    private int getCodiceRMTCorrente() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        int[] valoriPilota = null;

        try {    // prova ad eseguire il codice
            valoriPilota = this.getValoriPilota();
            if (valoriPilota != null) {
                if (valoriPilota.length == 1) {
                    codice = valoriPilota[0];
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Recupera la lista ordinata dei codici delle RMP comandabili
     * <p/>
     *
     * @return l'array dei codici
     */
    private int[] getCodiciRMPComandabili() {
        /* variabili e costanti locali di lavoro */
        int[] codici = null;
        int codiceMenu = 0;
        Filtro filtro = null;
        Filtro filtro2 = null;
        Ordine ordine = null;
        Campo campoComandabile = null;

        try {    // prova ad eseguire il codice

            codiceMenu = this.getCodiceMenuCorrente();

            /* cea un filtro per le RMP comandabili del menu */
            filtro = new Filtro();
            campoComandabile = moduloPiatto.getCampo(Piatto.CAMPO_COMANDA);
            filtro2 = FiltroFactory.crea(RMP.CAMPO_MENU, codiceMenu);
            filtro.add(filtro2);
            filtro2 = FiltroFactory.crea(campoComandabile, true);
            filtro.add(filtro2);

            /* crea un ordine per categoria.ordine e rmp.ordine */
            ordine = new Ordine();
            ordine.add(moduloCategoria.getCampoOrdine());
            ordine.add(moduloRMP.getCampoOrdine());

            /* recupera i codici */
            codici = moduloRMP.query().valoriChiave(filtro, ordine);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codici;
    }


    /**
     * Recupera da RMO la lista dei link a RMP per un tavolo RMT.
     * <p/>
     *
     * @return la lista dei link a RMP presenti
     */
    private int[] getLinksRMO() {
        /* variabili e costanti locali di lavoro */
        int[] codici = null;
        Filtro filtro = null;
        ArrayList valori = null;

        try {    // prova ad eseguire il codice
            filtro = this.filtroRMONoExtra();
            valori = moduloRMO.query().valoriCampo(campoRMOlinkRMP, filtro);
            codici = Libreria.objToInt(valori);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codici;
    }


    /**
     * Crea un filtro che seleziona le righe di RMO relative a una RMT
     * esclusi i piatti extra.
     * <p/>
     *
     * @return il filtro creato
     */
    private Filtro filtroRMONoExtra() {
        return this.filtroRMO(false);
    }


    /**
     * Crea un filtro che seleziona le righe di RMO relative a una RMT
     * seleziona solo i piatti extra.
     * <p/>
     *
     * @return il filtro creato
     */
    private Filtro filtroRMOExtra() {
        return this.filtroRMO(true);
    }


    /**
     * Crea un filtro che seleziona le righe di RMO relative a una RMT.
     * <p/>
     *
     * @param extra true per selezionare solo i piatti extra,
     * false per selezionare solo i piatti non extra
     *
     * @return il filtro creato
     */
    private Filtro filtroRMO(boolean extra) {
        /* variabili e costanti locali di lavoro */
        int codiceRMT = 0;
        Filtro filtro = null;
        Filtro filtro2 = null;

        try {    // prova ad eseguire il codice
            codiceRMT = this.getCodiceRMTCorrente();
            filtro = new Filtro();
            filtro2 = FiltroFactory.crea(campoRMOlinkRMT, codiceRMT);
            filtro.add(filtro2);
            filtro2 = FiltroFactory.crea(campoRMOlinkRMP, 0);
            if (extra == false) {
                filtro2.setInverso(true);
            }// fine del blocco if
            filtro.add(filtro2);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Intercetta il pacchetto di informazioni del portale Lista.
     * <p/>
     * Abilita lo spostamento, la modifica e la cancellazione solo
     * se si tratta di un extra.
     *
     * @param info il pacchetto di informazioni per la sincronizzazione
     */
    public InfoLista getInfoLista(InfoLista info) {
        /* variabili e costanti locali di lavoro */
        int[] codici;
        int codice;
        boolean extra = false;
        Object valore;
        int valoreLink;

        try { // prova ad eseguire il codice
            codici = this.getLista().getChiaviSelezionate();
            if (codici.length == 1) {
                codice = codici[0];
                valore = moduloRMO.query().valoreCampo(campoRMOlinkRMP, codice);
                valoreLink = Libreria.objToInt(valore);
                if (valoreLink == 0) {
                    extra = true;
                }// fine del blocco if
            }// fine del blocco if

            if (info.isPossoSpostareRigaListaSu()) {
                if (extra == false) {
                    info.setPossoSpostareRigaListaSu(false);
                }// fine del blocco if
            }// fine del blocco if

            if (info.isPossoSpostareRigaListaGiu()) {
                if (extra == false) {
                    info.setPossoSpostareRigaListaGiu(false);
                }// fine del blocco if
            }// fine del blocco if

            if (info.isPossoModificareRecord()) {
                if (extra == false) {
                    info.setPossoModificareRecord(false);
                }// fine del blocco if
            }// fine del blocco if

            if (info.isPossoCancellareRecord()) {
                if (extra == false) {
                    info.setPossoCancellareRecord(false);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return info;
    }


    /**
     * Azione modifica record in una <code>Lista</code>.
     * <p/>
     * Apre la scheda solo se si tratta di un extra.
     * Altrimenti, non fa nulla.
     *
     * @return true se eseguito correttamente
     */
    protected boolean modificaRecord() {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;
        int riga = 0;
        int totRighe = 0;

        try { // prova ad eseguire il codice
            if (this.isExtra()) {
                eseguito = super.modificaRecord();
            }// fine del blocco if

            riga = this.getTavola().getSelectedRow();
            totRighe = this.getTavola().getRowCount();
            riga = (riga < totRighe - 1) ? riga + 1 : riga;

            this.getTavola().setRowSelectionInterval(riga, riga);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;
    }


    /**
     * Controlla se la riga selezionata e' di tipo piatto extra.
     * <p/>
     */
    private boolean isExtra() {
        /* variabili e costanti locali di lavoro */
        boolean extra = false;
        int codiceRiga = 0;
        int codiceLink = 0;

        try {    // prova ad eseguire il codice
            codiceRiga = this.getLista().getRecordSelezionato();

            codiceLink = moduloRMO.query().valoreInt(campoRMOlinkRMP, codiceRiga);

            extra = (codiceLink == 0 ? true : false);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return extra;
    }


    private Tavola getTavola() {
        /* variabili e costanti locali di lavoro */
        Tavola tavola = null;
        Lista lista = null;

        try {    // prova ad eseguire il codice
            lista = this.getLista();
            if (lista != null) {
                tavola = lista.getTavola();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return tavola;
    }

}// fine della classe