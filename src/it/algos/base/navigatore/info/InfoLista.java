/**
 * Title:     InfoLista
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      2-apr-2004
 */
package it.algos.base.navigatore.info;

import it.algos.base.errore.Errore;
import it.algos.base.lista.Lista;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.portale.Portale;
import it.algos.base.portale.PortaleLista;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.scheda.Scheda;
import it.algos.base.tavola.Tavola;

/**
 * Wrapper per le informazioni sullo stato di una Lista.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Costruisce un tipo di dati </li>
 * <li> Raggruppa questi dati in un wrapper </li>
 * <li> </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 2-apr-2004 ore 17.48.15
 */
public final class InfoLista implements Info {

    /**
     * Lista di riferimento
     */
    private Lista lista = null;

    /**
     * flag - controlla se la lista e vuota
     */
    private boolean isVuota = false;

    /**
     * Numero di righe contenute nella lista
     */
    private int quanteRighe = 0;

    /**
     * flag - controlla se ci sono righe selezionate
     */
    private boolean isRigheSelezionate = false;

    /**
     * quante righe selezionate
     */
    private int quanteRigheSelezionate = 0;

    /**
     * flag - controlla se sono selezionate tutte le righe
     */
    private boolean isTutteRigheSelezionate = false;

    /**
     * flag - controlla la riga selezionata e' la prima
     */
    private boolean isPrimaRigaSelezionata = false;

    /**
     * flag - controlla la riga selezionata e' l'ultima
     */
    private boolean isUltimaRigaSelezionata = false;

    /**
     * numero della riga selezionata (0 per la prima, -1 se nessuna selezionata)
     */
    private int numeroRigaSelezionata = 0;

    /**
     * flag per indicare quando si puo' chiudere la finestra
     */
    private boolean isPossoChiudereFinestra = false;

    /**
     * flag per indicare se la lista e' vuota
     */
    private boolean isListaVuota = false;

    /**
     * flag per indicare se e' selezionata piu' di una riga
     */
    private boolean piuRigheSelezionate = false;

    /**
     * flag per indicare quando si possono creare nuovi records
     */
    private boolean isPossoCreareNuoviRecord = false;

    /**
     * flag per indicare che la lista visualizza un estratto
     */
    private boolean isEstratto = false;

    /**
     * flag per indicare quando si possono modificare i records
     */
    private boolean isPossoModificareRecord = false;

    /**
     * flag per indicare quando si possono duplicare i records
     */
    private boolean isPossoDuplicareRecord = false;

    /**
     * flag per indicare quando si possono cancellare i records
     */
    private boolean isPossoCancellareRecord = false;

    /**
     * flag per indicare quando si possono ricaricare tutti i records
     */
    private boolean isPossoCaricareTutti = false;

    /**
     * flag per indicare quando si puo' modificare la selezione
     */
    private boolean isPossoModificareSelezione = false;

    /**
     * flag - controlla se posso spostare in su una data riga
     */
    private boolean isPossoSpostareRigaListaSu = false;

    /**
     * flag - controlla se posso spostare in giu una data riga
     */
    private boolean isPossoSpostareRigaListaGiu = false;


    /**
     * Costruttore completo. <br>
     *
     * @param lista la lista di riferimento
     */
    public InfoLista(Lista lista) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice

            /* regola le variabili di istanza coi parametri */
            this.setLista(lista);

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     * Disabilita tutte le azioni <br>
     * Determina le Azioni possibili <br>
     * Abilita solo le Azioni congruenti con lo stato attuale <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
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
     *
     * @return vero se viene inizializzato adesso;
     *         falso se era gi&agrave; stato inizializzato
     */
    public boolean inizializza() {

        /* valore di ritorno */
        return true;
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Regola le variabili del pacchetto in funzione dello stato corrente <br>
     */
    public void avvia() {
        /* variabili e costanti locali di lavoro */
        int righeTotali = 0;
        int righeSelezionate = 0;

        /* resetta tutti i flag */
        isVuota = false;
        quanteRighe = 0;
        isRigheSelezionate = false;
        quanteRigheSelezionate = 0;
        isTutteRigheSelezionate = false;
        isPrimaRigaSelezionata = false;
        isUltimaRigaSelezionata = false;
        numeroRigaSelezionata = 0;
        isPossoChiudereFinestra = false;
        isListaVuota = false;
        piuRigheSelezionate = false;
        isPossoCreareNuoviRecord = false;
        isEstratto = false;
        isPossoModificareRecord = false;
        isPossoDuplicareRecord = false;
        isPossoCancellareRecord = false;
        isPossoCaricareTutti = false;
        isPossoModificareSelezione = false;
        isPossoSpostareRigaListaSu = false;
        isPossoSpostareRigaListaGiu = false;

        /* regola i flag in base allo stato */
        try { // prova ad eseguire il codice
            righeTotali = this.getTavola().getRowCount();
            righeSelezionate = this.getTavola().getSelectedRowCount();

            this.setVuota(righeTotali == 0);
            this.setQuanteRighe(righeTotali);
            this.setRigheSelezionate(righeSelezionate != 0);
            this.setQuanteRigheSelezionate(righeSelezionate);
            this.setTutteRigheSelezionate(righeTotali == righeSelezionate);

            /* determina se la prima e solo la prima riga e' selezionata */
            if (this.getTavola().getSelectedRow() == 0) { // e' la prima
                this.isPrimaRigaSelezionata = true;
            }

            /* determina se l'ultima e solo l'ultima riga e' selezionata */
            if (this.getTavola().getSelectedRow() == (getQuanteRighe() - 1)) {
                this.isUltimaRigaSelezionata = true;
            }// fine del blocco if

            /* recupera il numero della riga selezionata */
            this.numeroRigaSelezionata = this.getLista().getRigaSelezionata();

            /* controlla dal Navigatore se si puo' chiudere la finestra lista */
            this.isPossoChiudereFinestra = !this.getPortale().getNavigatore().isNavigatoreMain();

            // Controlla se la lista e' vuota
            this.isListaVuota = this.isVuota();

            // Controlla se ci sono righe selezionate
            if (this.isRigheSelezionate()) {
                this.isRigheSelezionate = true;

                // Controlla se le righe selezionate sono pi&ugrave; di una
                if (this.getQuanteRigheSelezionate() > 1) {
                    this.piuRigheSelezionate = true;

                    // Controlla se le righe sono tutte selezionate
                    if (this.isTutteRigheSelezionate()) {
                        this.isTutteRigheSelezionate = true;
                    } else {
                        this.isTutteRigheSelezionate = false;
                    }// fine del blocco if-else

                } else {
                    this.piuRigheSelezionate = false;
                }// fine del blocco if-else

            } else {
                this.isRigheSelezionate = false;
            }// fine del blocco if-else

            /* controlla se l'ordinamento e' su ordine */

            /* controlla se sono caricati tutti i record */
            Filtro filtro = this.getLista().getFiltroCorrente();
            if (filtro != null) {
                if (filtro.getSize() > 0) {
                    this.isPossoCaricareTutti = true;
                } else {
                    this.isPossoCaricareTutti = false;
                }// fine del blocco if-else
            } else {
                this.isPossoCaricareTutti = false;
            }// fine del blocco if-else

            /* regola la possibilita' teorica di creazione nuovi record */
            this.isPossoCreareNuoviRecord = this.checkCreaNuoviRecord();

            /* controlla se la lista visualizza un estratto */
            this.isEstratto = this.getLista().getPortale().isVisualizzaEstratto();

            this.isPossoModificareRecord = this.getLista().isModificaRecordAbilitata();

            /* regola la possibilita' teorica di duplicazione record */
            this.isPossoDuplicareRecord = this.checkDuplicaRecords();

            this.isPossoModificareSelezione = true;//@todo non mi ricordo dove viene regolato

            /* regola la possibilita' teorica di cancellare record */
            this.isPossoCancellareRecord = this.checkEliminaRecords();

            /*
             * Determina se la riga selezionata puo' essere spostata
             * verso l'alto o verso il basso
             */
            if (this.isPossoSpostareRigheLista()) {
                if (this.isPrimaRigaSelezionata()) { // e' la prima
                    this.setPossoSpostareRigaListaGiu(true);
                } else if (this.isUltimaRigaSelezionata()) { // e' l'ultima
                    this.setPossoSpostareRigaListaSu(true);
                } else {  // e' in mezzo
                    this.setPossoSpostareRigaListaGiu(true);
                    this.setPossoSpostareRigaListaSu(true);
                }// fine del blocco if-else
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo lancia


    /**
     * Determina se in generale e' possibile spostare
     * le righe nella lista.
     * <p/>
     * Perche' le righe siano spostabili, devono essere
     * verificate le seguenti condizioni:
     * - il navigatore deve essere modificabile
     * - la lista deve essere ordinata sul campo ordine
     * - la lista deve contenere piu' di una riga
     * - una e una sola riga deve essere selezionata.
     *
     * @return true se in generale e' possibile spostare le righe nella lista
     */
    private boolean isPossoSpostareRigheLista() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Navigatore nav;

        try { // prova ad eseguire il codice

            nav = this.getNavigatore();

            /* controlla che il navigatore sia modificabile */
            if (continua) {
                continua = nav.isModificabile();
            }// fine del blocco if

            /* controlla che la lista sia ordinata sul campo Ordine */
            if (continua) {
                continua = false;
                if (this.getLista().isOrdinataCampoOrdine()) {
                    if (this.getTavola().getRowCount() > 1) {
                        if (this.getTavola().getSelectedRowCount() == 1) {
                            continua = true;
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Controlla se e' possibile creare nuovi record.
     * <p/>
     * Perche' sia possibile, occorre che:
     * - il navigatore non sia in fase di editing di un nuovo record
     * - se il navigatore e' pilotato, esista uno e un solo valore pilota valido
     * - che la lista dia il suo consenso alla creazione di nuovi record
     *
     * @return true se e' possibile creare nuovi record.
     */
    private boolean checkCreaNuoviRecord() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Navigatore navigatore = null;
        int[] valoriPilota = null;
        Scheda scheda;

        try {    // prova ad eseguire il codice

            navigatore = this.getNavigatore();

            /* controlla che il navigatore sia modificabile */
            if (continua) {
                continua = navigatore.isModificabile();
            }// fine del blocco if

            /* controlla che il navigatore non sia in fase di editing di
             * un nuovo record */
            if (continua) {
                if (navigatore.isNuovoRecord()) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* se il navigatore e' pilotato, posso creare nuovi record
             * solo se ho uno e un solo valore pilota diverso da zero */
            if (continua) {
                if (navigatore.isPilotato()) {
                    continua = false;
                    valoriPilota = navigatore.getValoriPilota();
                    if (valoriPilota != null) {
                        if (valoriPilota.length == 1) {
                            if (valoriPilota[0] != 0) {
                                continua = true;
                            }// fine del blocco if
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* controlla se la Lista da' il proprio assenso
             * alla creazione di nuovi record */
            if (continua) {
                if (!this.getLista().isNuovoRecordAbilitato()) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* controlla che il navigatore non abbia la scheda aperta */
            if (continua) {
                scheda = navigatore.getScheda();
                if (scheda != null) {
                    if (scheda.isAttivo()) {
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* se si usa l'estratto con record singolo, non posso creare più di 1 record */
            if (continua) {
                if (((PortaleLista)this.getPortale()).isUsaRecordSingolo()) {
                    continua = (this.getQuanteRighe() == 0);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Controlla se e' possibile eliminare i record.
     * <p/>
     *
     * @return true se è possibile eliminare i record
     */
    private boolean checkEliminaRecords() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Navigatore navigatore;

        try {    // prova ad eseguire il codice

            navigatore = this.getNavigatore();

            /* controlla che il navigatore sia modificabile */
            if (continua) {
                continua = navigatore.isModificabile();
            }// fine del blocco if

            /* controlla che la lista da' il consenso
             * alla eliminazione di record */
            if (continua) {
                continua = this.getLista().isDeleteAbilitato();
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Controlla se e' possibile duplicare i record.
     * <p/>
     *
     * @return true se è possibile duplicare i record
     */
    private boolean checkDuplicaRecords() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Navigatore navigatore = null;

        try {    // prova ad eseguire il codice

            navigatore = this.getNavigatore();

            /* controlla che il navigatore sia modificabile */
            if (continua) {
                continua = navigatore.isModificabile();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Recupera il portale.
     * <p/>
     */
    private Portale getPortale() {
        return this.getLista().getPortale();
    }


    /**
     * Recupera il Navigatore.
     * <p/>
     */
    private Navigatore getNavigatore() {
        return this.getPortale().getNavigatore();
    }


    /**
     * Recupera la Lista.
     * <p/>
     */
    private Lista getLista() {
        return lista;
    }


    public void setLista(Lista lista) {
        this.lista = lista;
    }


    /**
     * Recupera la Tavola.
     * <p/>
     */
    private Tavola getTavola() {
        /* valore di ritorno */
        return this.getLista().getTavola();
    }


    public boolean isVuota() {
        return isVuota;
    }


    public void setVuota(boolean vuota) {
        isVuota = vuota;
    }


    public int getQuanteRighe() {
        return quanteRighe;
    }


    public void setQuanteRighe(int quanteRighe) {
        this.quanteRighe = quanteRighe;
    }


    public boolean isRigheSelezionate() {
        return isRigheSelezionate;
    }


    public void setRigheSelezionate(boolean righeSelezionate) {
        isRigheSelezionate = righeSelezionate;
    }


    public int getQuanteRigheSelezionate() {
        return quanteRigheSelezionate;
    }


    public void setQuanteRigheSelezionate(int quanteRigheSelezionate) {
        this.quanteRigheSelezionate = quanteRigheSelezionate;
    }


    public boolean isTutteRigheSelezionate() {
        return isTutteRigheSelezionate;
    }


    public void setTutteRigheSelezionate(boolean tutteRigheSelezionate) {
        isTutteRigheSelezionate = tutteRigheSelezionate;
    }


    public boolean isPrimaRigaSelezionata() {
        return isPrimaRigaSelezionata;
    }


    public void setPrimaRigaSelezionata(boolean primaRigaSelezionata) {
        isPrimaRigaSelezionata = primaRigaSelezionata;
    }


    public boolean isUltimaRigaSelezionata() {
        return isUltimaRigaSelezionata;
    }


    public void setUltimaRigaSelezionata(boolean ultimaRigaSelezionata) {
        isUltimaRigaSelezionata = ultimaRigaSelezionata;
    }


    public boolean isPossoSpostareRigaListaSu() {
        return isPossoSpostareRigaListaSu;
    }


    public void setPossoSpostareRigaListaSu(boolean possoSpostareRigaListaSu) {
        isPossoSpostareRigaListaSu = possoSpostareRigaListaSu;
    }


    public boolean isPossoSpostareRigaListaGiu() {
        return isPossoSpostareRigaListaGiu;
    }


    public void setPossoSpostareRigaListaGiu(boolean possoSpostareRigaListaGiu) {
        isPossoSpostareRigaListaGiu = possoSpostareRigaListaGiu;
    }


    public int getNumeroRigaSelezionata() {
        return numeroRigaSelezionata;
    }


    public void setNumeroRigaSelezionata(int numeroRigaSelezionata) {
        this.numeroRigaSelezionata = numeroRigaSelezionata;
    }


    public boolean isPossoChiudereFinestra() {
        return isPossoChiudereFinestra;
    }


    public void setPossoChiudereFinestra(boolean possoChiudereFinestra) {
        isPossoChiudereFinestra = possoChiudereFinestra;
    }


    public boolean isListaVuota() {
        return isListaVuota;
    }


    public void setListaVuota(boolean listaVuota) {
        isListaVuota = listaVuota;
    }


    public boolean isPiuRigheSelezionate() {
        return piuRigheSelezionate;
    }


    public void setPiuRigheSelezionate(boolean piuRigheSelezionate) {
        this.piuRigheSelezionate = piuRigheSelezionate;
    }


    public boolean isPossoCaricareTutti() {
        return isPossoCaricareTutti;
    }


    public void setPossoCaricareTutti(boolean possoCaricareTutti) {
        isPossoCaricareTutti = possoCaricareTutti;
    }


    public boolean isPossoCreareNuoviRecord() {
        return isPossoCreareNuoviRecord;
    }


    public void setPossoCreareNuoviRecord(boolean possoCreareNuoviRecord) {
        isPossoCreareNuoviRecord = possoCreareNuoviRecord;
    }


    public boolean isPossoModificareRecord() {
        return isPossoModificareRecord;
    }


    public void setPossoModificareRecord(boolean possoModificareRecord) {
        isPossoModificareRecord = possoModificareRecord;
    }


    public boolean isPossoDuplicareRecord() {
        return isPossoDuplicareRecord;
    }


    public void setPossoDuplicareRecord(boolean possoDuplicareRecord) {
        isPossoDuplicareRecord = possoDuplicareRecord;
    }


    public boolean isPossoCancellareRecord() {
        return isPossoCancellareRecord;
    }


    public void setPossoCancellareRecord(boolean possoCancellareRecord) {
        isPossoCancellareRecord = possoCancellareRecord;
    }


    public boolean isEstratto() {
        return isEstratto;
    }


    public void setEstratto(boolean estratto) {
        isEstratto = estratto;
    }


    public boolean isPossoModificareSelezione() {
        return isPossoModificareSelezione;
    }


    public void setPossoModificareSelezione(boolean possoModificareSelezione) {
        isPossoModificareSelezione = possoModificareSelezione;
    }

}// fine della classe
