/**
 * Title:     NavRMP
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      3-feb-2005
 */
package it.algos.albergo.ristorante.righemenupiatto;

import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.menu.MenuModulo;
import it.algos.albergo.ristorante.piatto.Piatto;
import it.algos.albergo.ristorante.piatto.PiattoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;
import it.algos.base.navigatore.info.InfoLista;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;

import java.util.ArrayList;

/**
 * Navigatore specifico per i piatti nel menu.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 3-feb-2005 ore 12.28.15
 */
public final class RMPNavInMenu extends NavigatoreLS {

    private static final int SU = -1;

    private static final int GIU = +1;


    /**
     * Costruttore base senza parametri.
     * <p/>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public RMPNavInMenu() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param modulo il modulo di riferimento
     */
    public RMPNavInMenu(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        /* regola le variabili di istanza coi parametri */

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
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        this.setUsaPannelloUnico(true);
        this.getLista().setOrdinabile(false);
        this.setNomeVista(RMP.VISTA_IN_MENU);
        this.setNomeSet(RMP.SET_PIATTO_CONTORNO);
        this.setUsaFrecceSpostaOrdineLista(true);
        this.setUsaRicerca(false);
        this.addScheda(new RMPScheda(this.getModulo()));

    }// fine del metodo inizia


    /**
     * Inizializzazione dell'oggetto.<br>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche.<br>
     * Viene chiamato dall'inizializzazione del Modulo.<br>
     * Puo' essere chiamato piu' volte.<br>
     * Se l'inizializzazione ha successo imposta il flag inizializzato a true.<br>
     * Il flag puo' essere successivamente modificato dalle sottoclassi se non
     * riescono a portare a termine la propria inizializzazione specifica.<br>
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        Modulo moduloCat;
        Campo campoOrdineCat;
        Modulo moduloRMP;
        Campo campoOrdineRMP;

        super.inizializza();

        try { // prova ad eseguire il codice

            /*
             * regola l'ordine della lista
             * Ordina per categoria e sotto per ordine di riga
             */
            moduloCat = Progetto.getModulo(Ristorante.MODULO_CATEGORIA);
            campoOrdineCat = moduloCat.getCampoOrdine();
            moduloRMP = Progetto.getModulo(Ristorante.MODULO_RIGHE_PIATTO);
            campoOrdineRMP = moduloRMP.getCampoOrdine();
            Ordine ordine = new Ordine();
            ordine.add(campoOrdineCat);
            ordine.add(campoOrdineRMP);
            this.getLista().setOrdine(ordine);

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
     * Sincronizza il portale lista con un pacchetto di informazioni di stato.
     * <p/>
     *
     * @param info il pacchetto di informazioni per la sincronizzazione
     */
    public InfoLista getInfoLista(InfoLista info) {

        /* variabili e costanti locali di lavoro */
        boolean controllaSu = true;
        boolean controllaGiu = true;

        /*
         * Se ci sono righe selezionate, controlla se e' possibile
         * spostare la riga selezionata in su o in giu
         * Modifico il pacchetto info con le nuove informazioni
         */
        if (info.getQuanteRigheSelezionate() == 1) {
            controllaSu = true;
            controllaGiu = true;
            if (info.isPrimaRigaSelezionata()) {
                controllaSu = false;
            }// fine del blocco if
            if (info.isUltimaRigaSelezionata()) {
                controllaGiu = false;
            }// fine del blocco if
            if (controllaSu) {
                info.setPossoSpostareRigaListaSu(this.isPossoSpostareRigaLista(SU));
            }// fine del blocco if
            if (controllaGiu) {
                info.setPossoSpostareRigaListaGiu(this.isPossoSpostareRigaLista(GIU));
            }// fine del blocco if
        }// fine del blocco if

        /* valore di ritorno */
        return info;

    }


    /**
     * Modifica di un popup.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Aggiorna il campo (da GUI a memoria e poi di nuovo a GUI) <br>
     * Invoca il metodo delegato della classe <br>
     * Sincronizza il navigatore <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public void popupModificato(Campo unCampo) {
        /* variabili e costanti locali di lavoro */
        String nomeCampo = "";
        boolean congelato = false;
        int codice = 0;
        Modulo modulo = null;
        String campoFlag = "";
        Campo piattoCongelato = null;
        Campo contornoCongelato = null;

        try { // prova ad eseguire il codice

            /* invoca il metodo sovrascritto della superclasse */
//            super.popupModificato(unCampo);

            /* nome del campo flag congelato sulla tavola Piatto */
            campoFlag = Piatto.CAMPO_CONGELATO;

            /* recupera il nome del campo */
            nomeCampo = unCampo.getNomeInterno();

            /* recupera i riferimenti */
            piattoCongelato = super.getCampoScheda(RMP.CAMPO_PIATTO_CONGELATO);
            contornoCongelato = super.getCampoScheda(RMP.CAMPO_CONTORNO_CONGELATO);
            modulo = unCampo.getCampoDB().getModuloLinkato();

            if (modulo != null) {

                if (nomeCampo.equals(RMP.CAMPO_PIATTO)) {
                    codice = ((Integer)unCampo.getCampoDati().getMemoria()).intValue();
                    congelato = modulo.query().valoreBool(campoFlag, codice);

                    piattoCongelato.setMemoria(new Boolean(congelato));
                    piattoCongelato.getCampoLogica().memoriaGui();
                }// fine del blocco if

                if (nomeCampo.equals(RMP.CAMPO_CONTORNO)) {
                    codice = ((Integer)unCampo.getCampoDati().getMemoria()).intValue();
                    congelato = modulo.query().valoreBool(campoFlag, codice);

                    contornoCongelato.setMemoria(new Boolean(congelato));
                    contornoCongelato.getCampoLogica().memoriaGui();
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Determina se posso spostare la riga selezionata nella lista in su o in giu.
     * <p/>
     *
     * @param direzione -1 per spostamento in su, +1 per spostamento in giu
     *
     * @return posso true se posso spostare la riga nella direzione richiesta
     */
    private boolean isPossoSpostareRigaLista(int direzione) {

        /** variabili e costanti locali di lavoro */
        boolean posso = false;
        int delta = 0;
        int posRigaCorrente = 0;
        int posRigaAdiacente = 0;
        int codCatCorrente = 0;
        int codCatAdiacente = 0;

        try {                                   // prova ad eseguire il codice

            /* controllo parametri in ingresso */
            if (direzione == SU || direzione == GIU) {
                /*  */
                switch (direzione) {
                    case SU:
                        delta = -1;
                        break;
                    case GIU:
                        delta = 1;
                        break;
                } // fine del blocco switch
            } else {
                throw new Exception("Parametro errato.");
            } /* fine del blocco if */

            /* Recupero dalla lista il codice della riga selezionata
     * e il codice della riga adiacente */
            posRigaCorrente = this.getLista().getRigaSelezionata();
            codCatCorrente = this.getCategoriaRiga(posRigaCorrente);
            posRigaAdiacente = posRigaCorrente + delta;
            codCatAdiacente = this.getCategoriaRiga(posRigaAdiacente);

            /* Confronto i due codici categoria per vedere se posso
             * spostare la riga corrente nella direzione richiesta */
            if (codCatCorrente == codCatAdiacente) {
                posso = true;
            }// fine del blocco if

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return posso;
    } /* fine del metodo */


    /**
     * Ritorna il codice categoria relativo a una data riga della lista.
     * <p/>
     *
     * @param posizione la posizione della riga nella lista
     *
     * @return il codice della categoria della riga (0 se non ha categoria)
     */
    private int getCategoriaRiga(int posizione) {
        /* variabili e costanti locali di lavoro */
        int codiceCategoria = 0;
        Modulo moduloCategoria = null;
        Campo campoChiaveCategoria = null;
        Modulo moduloRMP = null;

        Query query = null;
        Filtro filtro = null;
        Dati dati = null;
        Object valore = null;
        Integer intero = null;

        int codRigaCorrente = 0;

        try { // prova ad eseguire il codice

            /* recupera alcuni dati di lavoro */
            moduloCategoria = Progetto.getModulo(Ristorante.MODULO_CATEGORIA);
            campoChiaveCategoria = moduloCategoria.getCampoChiave();
            moduloRMP = this.getModulo();

            /* Recupero dalla lista il codice della riga */
            codRigaCorrente = this.getLista().getChiave(posizione);

            /* Recupero il codice della categoria della riga */
            query = new QuerySelezione(moduloRMP);
            filtro = FiltroFactory.codice(moduloRMP, codRigaCorrente);
            query.addCampo(campoChiaveCategoria);
            query.setFiltro(filtro);
            dati = moduloRMP.query().querySelezione(query);
            valore = dati.getValueAt(0, 0);
            dati.close();
            if (valore != null) {
                if (valore instanceof Integer) {
                    intero = (Integer)valore;
                    codiceCategoria = intero.intValue();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codiceCategoria;
    }


    /**
     * .
     * <p/>
     */
    public int creaRecord() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;

        codice = super.creaRecord();

        try {    // prova ad eseguire il codice
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Registrazione fisica di un record.
     * <p/>
     *
     * @param codice il codice chiave del record
     * @param campi la lista dei campi da registrare
     *
     * @return true se riuscito
     */
    public boolean registraRecord(int codice, ArrayList<Campo> campi) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        Campo campo = null;
        Campo campoLinkPiatto = null;
        String chiaveCampoLinkPiatto = null;
        String chiave = null;
        Object valore = null;
        int codicePiatto = 0;
        boolean cambioCategoria = false;

        try { // prova ad eseguire il codice

            /* determina se la categoria e' cambiata */
            campoLinkPiatto = this.getModulo().getCampo(RMP.CAMPO_PIATTO);
            chiaveCampoLinkPiatto = campoLinkPiatto.getChiaveCampo();
            for (int k = 0; k < campi.size(); k++) {
                campo = (Campo)campi.get(k);
                chiave = campo.getChiaveCampo();
                if (chiave.equals(chiaveCampoLinkPiatto)) {
                    valore = campo.getCampoDati().getMemoria();
                    codicePiatto = Libreria.objToInt(valore);
                    break;
                }// fine del blocco if
            } // fine del ciclo for
            cambioCategoria = this.isCategoriaCambiata(codice, codicePiatto);

            /* registra nella superclasse */
            riuscito = super.registraRecord(codice, campi);

            /* se e' cambiata regola l'ordine della riga nell'ambito del menu */
            if (cambioCategoria) {
                this.getModuloSpecifico().regolaOrdineInMenu(codice);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;

    }


    /**
     * Controlla se il codice categoria di una riga e' diverso da quello
     * attualmente presente nel db.
     * <p/>
     *
     * @return true se e' cambiato
     */
    private boolean isCategoriaCambiata(int codiceRiga, int codicePiatto) {
        /* variabili e costanti locali di lavoro */
        boolean cambiato = false;
        Object valore = null;
        int oldPiatto = 0;
        int oldCategoria = 0;
        int newCategoria = 0;

        try {    // prova ad eseguire il codice
            /* recupera il codice piatto esistente */
            valore = this.getModulo().query().valoreCampo(RMP.CAMPO_PIATTO, codiceRiga);
            oldPiatto = Libreria.objToInt(valore);
            /* recupera il coice categoria esistente */
            valore = this.getModuloPiatto().query().valoreCampo(Piatto.CAMPO_CATEGORIA, oldPiatto);
            oldCategoria = Libreria.objToInt(valore);

            /* recupera il codice categoria da assegnare */
            valore = this.getModuloPiatto().query().valoreCampo(Piatto.CAMPO_CATEGORIA,
                    codicePiatto);
            newCategoria = Libreria.objToInt(valore);

            /* verifica se sono diversi */
            if (newCategoria != oldCategoria) {
                cambiato = true;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cambiato;
    }


    /**
     * Ritorna il modulo specifico di questo Navigatore.
     * <p/>
     *
     * @return il modulo specifico
     */
    private RMPModulo getModuloSpecifico() {
        return (RMPModulo)this.getModulo();
    }


    /**
     * Ritorna il modulo Piatto.
     * <p/>
     *
     * @return il modulo Piatto
     */
    private PiattoModulo getModuloPiatto() {
        return (PiattoModulo)Progetto.getModulo(Ristorante.MODULO_PIATTO);
    }


    /**
     * Ritorna il modulo Menu.
     * <p/>
     *
     * @return il modulo Menu
     */
    private MenuModulo getModuloMenu() {
        return (MenuModulo)Progetto.getModulo(Ristorante.MODULO_MENU);
    }

}// fine della classe
