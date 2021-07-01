/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      10-mag-2006
 */
package it.algos.albergo.pensioni;

import it.algos.albergo.listino.Listino;
import it.algos.albergo.listino.ListinoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.memoria.dati.DatiMemoria;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.WrapListino;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Contenitore per le informazioni relative a
 * un pacchetto di addebiti da listino.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 8-feb-2008 ore 12.18.49
 */
public class WrapAddebiti {

    /**
     * elenco degli elementi di listino
     */
    private ArrayList<Elemento> elementi = null;

    /**
     * Data iniziale generale
     */
    private Date dataInizio;

    /**
     * Data finale generale
     */
    private Date dataFine;

    /**
     * Numero di persone generale
     */
    private int persone;

    /**
     * Codice del record di riferimento in ingresso (periodo o conto)
     */
    private int codice;

    /**
     * Connessione da utilizzare per le letture dal database
     */
    private Connessione connessione;

    /* start -- variabili specifiche dei diversi tipi di wrapper */

    /* modulo di origine della testa */

    private Modulo moduloTesta;

    /* modulo di origine delle righe */
    private Modulo moduloRighe;

    /* campo linkato al codice in esame */
    private Campo campoLink;

    /* campo contenente il codice listino */
    private Campo campoListino;

    /* campo contenente il codice della riga di listino */
    private Campo campoRigaListino;

    /* campo contenente la data di inizio validità */
    private Campo campoInizio;

    /* campo contenente la data di fine validità */
    private Campo campoFine;

    /* campo contenente la quantità */
    private Campo campoQuantita;

    /* campo contenente il prezzo */
    private Campo campoPrezzo;

    /* campo contenente la data di inizio del record di testa */
    private Campo campoInizioTesta;

    /* campo contenente la data di fine del record di testa */
    private Campo campoFineTesta;

    /* campo contenente il numero di persone del record di testa */
    private Campo campoPersoneTesta;

    /* end -- variabili specifiche dei diversi tipi di wrapper */


    /**
     * Costruttore completo.
     * <p/>
     */
    public WrapAddebiti() {
        /* rimanda al costruttore */
        this(null,
                null,
                0,
                0,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo.
     * <p/>
     * Crea l'oggetto e lo riempie con i dati relativi al codice record fornito
     *
     * @param dataIni data di inizio del periodo (giorno di arrivo)
     * @param dataEnd data di fine del periodo (giorno di partenza)
     * @param persone numero persone previste
     * @param codice del record di testa per riempire il wrapper
     * @param conn connessione da utilizzare per le letture dal database
     * @param moduloTesta modulo di origine della testa
     * @param moduloRighe modulo di origine delle righe
     * @param campoLink campo linkato al codice in esame
     * @param campoListino campo contenente il codice listino
     * @param campoRigaListino campo contenente il codice della riga di listino
     * @param campoInizio campo contenente la data di inizio validità
     * @param campoFine campo contenente la data di fine validità
     * @param campoQuantita campo contenente la quantità
     * @param campoPrezzo campo contenente il prezzo
     * @param campoInizioTesta campo contenente la data di inizio del record di testa
     * @param campoFineTesta campo contenente la data di fine del record di testa
     * @param campoPersoneTesta campo contenente il numero di persone del record di testa
     */
    public WrapAddebiti(Date dataIni,
                        Date dataEnd,
                        int persone,
                        int codice,
                        Connessione conn,
                        Modulo moduloTesta,
                        Modulo moduloRighe,
                        Campo campoLink,
                        Campo campoListino,
                        Campo campoRigaListino,
                        Campo campoInizio,
                        Campo campoFine,
                        Campo campoQuantita,
                        Campo campoPrezzo,
                        Campo campoInizioTesta,
                        Campo campoFineTesta,
                        Campo campoPersoneTesta) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice

            /* regola le variabili di istanza coi parametri */
            this.setDataInizio(dataIni);
            this.setDataFine(dataEnd);
            this.setPersone(persone);
            this.setCodice(codice);
            this.setConnessione(conn);

            this.setModuloTesta(moduloTesta);
            this.setModuloRighe(moduloRighe);
            this.setCampoLink(campoLink);
            this.setCampoListino(campoListino);
            this.setCampoRigaListino(campoRigaListino);
            this.setCampoInizio(campoInizio);
            this.setCampoFine(campoFine);
            this.setCampoQuantita(campoQuantita);
            this.setCampoPrezzo(campoPrezzo);
            this.setCampoInizioTesta(campoInizioTesta);
            this.setCampoFineTesta(campoFineTesta);
            this.setCampoPersoneTesta(campoPersoneTesta);

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
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
        int codice;

        try { // prova ad eseguire il codice

            /* crea lista elementi vuota */
            this.setElementi(new ArrayList<Elemento>());

            /**
             * Riempie il wrapper da codice record se fornito
             */
            codice = this.getCodice();
            if (codice > 0) {
                this.read(this.getConnessione());
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Aggiunge un elemento.
     * <p/>
     *
     * @param codListino codice di listino di riferimento
     *
     * @return l'elemento aggiunto
     */
    public Elemento addElemento(int codListino) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Elemento> elementi;
        Elemento elemento = null;

        try {    // prova ad eseguire il codice
            elementi = this.getElementi();
            elemento = new Elemento(codListino);
            elementi.add(elemento);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return elemento;

    }

//    /**
//     * Riempie l'oggetto da una tavola esterna.
//     * <p/>
//     */
//    protected void readData() {
//    }
//
//
//    /**
//     * Scrive i dati in memoria sul database.
//     * <p/>
//     *
//     * @param codice del record di testa di riferimento
//     *
//     * @return true se riuscito
//     */
//    public boolean writeData(int codice) {
//        return false;
//    }


    /**
     * Riempie i dati di questo oggetto dal database.
     * <p/>
     *
     * @param conn connessione da utilizzare
     */
    private void read(Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Modulo moduloTesta;
        Modulo moduloRighe;
        Campo campoLink;
        Campo campoListino;
        Campo campoRigaListino;
        Campo campoInizio;
        Campo campoFine;
        Campo campoQuantita;
        Campo campoPrezzo;
        Campo campoInizioTesta;
        Campo campoFineTesta;
        Campo campoPersoneTesta;

        int codiceTesta;
        Modulo modListino;
        Query query;
        Filtro filtro;
        Ordine ordine;
        Dati dati;
        int codChiave;
        int codListino;
        int codRigaListino;
        int codListinoCorrente;
        Elemento elemento = null;

        Date data1;
        Date data2;
        int quantita;
        double prezzo;

        Date dataInizioTesta;
        Date dataFineTesta;
        int personeTesta;

        try {    // prova ad eseguire il codice

            /* recupera moduli e campi */
            moduloTesta = this.getModuloTesta();
            moduloRighe = this.getModuloRighe();
            campoLink = this.getCampoLink();
            campoListino = this.getCampoListino();
            campoRigaListino = this.getCampoRigaListino();
            campoInizio = this.getCampoInizio();
            campoFine = this.getCampoFine();
            campoQuantita = this.getCampoQuantita();
            campoPrezzo = this.getCampoPrezzo();
            campoInizioTesta = this.getCampoInizioTesta();
            campoFineTesta = this.getCampoFineTesta();
            campoPersoneTesta = this.getCampoPersoneTesta();

            /* recupera il modulo Listino */
            modListino = ListinoModulo.get();
            codiceTesta = this.getCodice();

            /* costruisce la query con i campi necessari */
            query = new QuerySelezione(moduloRighe);
            query.addCampo(moduloRighe.getCampoChiave());
            query.addCampo(campoListino);
            query.addCampo(campoRigaListino);
            query.addCampo(campoInizio);
            query.addCampo(campoFine);
            query.addCampo(campoQuantita);
            query.addCampo(campoPrezzo);

            /* costruisce il filtro per isolare le righe */
            filtro = FiltroFactory.crea(campoLink, codiceTesta);

            /* ordine per ordine di listino e data inizio */
            ordine = new Ordine();
            ordine.add(modListino.getCampoOrdine());
            ordine.add(campoInizio);

            /* esegue la query */
            query.setFiltro(filtro);
            query.setOrdine(ordine);
            dati = moduloRighe.query().querySelezione(query, conn);

            /* spazzola i dati e li carica nelle strutture interne */
            codListinoCorrente = 0;
            for (int k = 0; k < dati.getRowCount(); k++) {

                codChiave = dati.getIntAt(k, moduloRighe.getCampoChiave());
                codListino = dati.getIntAt(k, campoListino);
                codRigaListino = dati.getIntAt(k, campoRigaListino);

                /* se cambia il codice listino crea un nuovo elemento */
                if (codListino != codListinoCorrente) {
                    elemento = this.addElemento(codListino);
                    codListinoCorrente = codListino;
                }// fine del blocco if-else

                data1 = dati.getDataAt(k, campoInizio);
                data2 = dati.getDataAt(k, campoFine);
                quantita = dati.getIntAt(k, campoQuantita);
                prezzo = dati.getDoubleAt(k, campoPrezzo);

                if (elemento != null) {
                    elemento.addDettaglio(codChiave,
                            data1,
                            data2,
                            quantita,
                            prezzo,
                            codRigaListino);
                }// fine del blocco if

            } // fine del ciclo for

            dati.close();

            /* se non sono stati forniti valori validi,
             * recupera e registra le date di inizio e fine e il numero di persone
             * dal record di testa */
            if (Lib.Data.isVuota(this.getDataInizio())) {
                dataInizioTesta = moduloTesta.query()
                        .valoreData(campoInizioTesta, codiceTesta, conn);
                this.setDataInizio(dataInizioTesta);
            }// fine del blocco if

            if (Lib.Data.isVuota(this.getDataFine())) {
                dataFineTesta = moduloTesta.query().valoreData(campoFineTesta, codiceTesta, conn);
                this.setDataFine(dataFineTesta);
            }// fine del blocco if

            if (this.getPersone() == 0) {
                personeTesta = moduloTesta.query().valoreInt(campoPersoneTesta, codiceTesta, conn);
                this.setPersone(personeTesta);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Scrive i dati di memoria sul database.
     * <p/>
     *
     * @param codice del record di testa di riferimento
     * i dati vengono scritti relativamente a questo record
     * @param conn la connessione da utilizzare
     *
     * @return true se riuscito
     */
    public boolean write(int codice, Connessione conn) {

        /* variabili e costanti locali di lavoro */
        Modulo modulo;
        Campo campoLink;
        Campo campoListino;
        Campo campoRigaListino;
        Campo campoInizio;
        Campo campoFine;
        Campo campoQuantita;
        Campo campoPrezzo;

        boolean riuscito = true;
        ArrayList<Elemento> elementi;
        int codListino;
        ArrayList<Dettaglio> dettagli;
        Date data1, data2;
        int quantita;
        double prezzo;
        int codRigaListino;
        ArrayList<CampoValore> campi;
        CampoValore campoVal;
        int cod;
        Filtro filtro;
        Filtro filtroTot;
        Filtro filtroAltri;

        try {    // prova ad eseguire il codice

            /* recupera moduli e campi */
            modulo = this.getModuloRighe();
            campoLink = this.getCampoLink();
            campoListino = this.getCampoListino();
            campoRigaListino = this.getCampoRigaListino();
            campoInizio = this.getCampoInizio();
            campoFine = this.getCampoFine();
            campoQuantita = this.getCampoQuantita();
            campoPrezzo = this.getCampoPrezzo();

            /* recupera gli elementi del wrapper */
            elementi = this.getElementi();

            /**
             * cancella tutte le eventuali righe di addebito
             * relative a voci di listino non contenute nel wrapper
             */
            filtroAltri = new Filtro();
            for (WrapAddebiti.Elemento elemento : elementi) {
                codListino = elemento.getCodListino();
                filtro = FiltroFactory.crea(campoListino, Filtro.Op.DIVERSO, codListino);
                filtroAltri.add(filtro);
            }
            filtroTot = FiltroFactory.crea(campoLink, codice);
            filtroTot.add(filtroAltri);
            riuscito = modulo.query().eliminaRecords(filtroTot, conn);

            /**
             * Crea o modifica le righe di addebito in base al
             * contenuto del wrapper
             */
            if (riuscito) {

                /* spazzola gli elementi */
                for (WrapAddebiti.Elemento elemento : elementi) {

                    codListino = elemento.getCodListino();
                    dettagli = elemento.getDettagli();

                    /* spazzola i dettagli dell'elemento */
                    for (WrapAddebiti.Dettaglio dettaglio : dettagli) {

                        int codChiave = dettaglio.getCodChiave();
                        data1 = dettaglio.getData1();
                        data2 = dettaglio.getData2();
                        quantita = dettaglio.getQuantita();
                        prezzo = dettaglio.getPrezzo();
                        codRigaListino = dettaglio.getCodRigaListino();

                        /**
                         * crea o modifica il record
                         * in base al codice di origine della riga di dettaglio
                         */
                        campi = new ArrayList<CampoValore>();

                        /* codice riga listino */
                        campoVal = new CampoValore(campoRigaListino, codRigaListino);
                        campi.add(campoVal);

                        /* data di inizio periodo */
                        campoVal = new CampoValore(campoInizio, data1);
                        campi.add(campoVal);

                        /* data di fine periodo */
                        campoVal = new CampoValore(campoFine, data2);
                        campi.add(campoVal);

                        /* quantità */
                        campoVal = new CampoValore(campoQuantita, quantita);
                        campi.add(campoVal);

                        /* prezzo */
                        campoVal = new CampoValore(campoPrezzo, prezzo);
                        campi.add(campoVal);

                        /* valori aggiuntivi in caso di nuovo record */
                        if (codChiave == 0) {

                            /* codice del record di testa */
                            campoVal = new CampoValore(campoLink, codice);
                            campi.add(campoVal);

                            /* codice di listino */
                            campoVal = new CampoValore(campoListino, codListino);
                            campi.add(campoVal);

                        }// fine del blocco if

                        /* creazione o modifica del record */
                        if (codChiave == 0) {   // creazione
                            cod = modulo.query().nuovoRecord(campi, conn);
                            riuscito = (cod > 0);
                        } else {                // modifica
                            riuscito = modulo.query().registraRecordValori(codChiave, campi, conn);
                        }// fine del blocco if-else

                        if (!riuscito) {
                            break;
                        }// fine del blocco if

                    }  // fine del ciclo for sui dettagli

                    if (!riuscito) {
                        break;
                    }// fine del blocco if

                }  // fine del ciclo for sugli elementi

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Rimuove il riferimento alla riga di origine dai dati del wrapper.
     * <p/>
     */
    public void cleanOrigine() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Elemento> elementi;

        try {    // prova ad eseguire il codice
            elementi = this.getElementi();
            if (elementi != null) {
                for (Elemento elemento : elementi) {
                    elemento.cleanOrigine();
                }
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna un oggetto Dati Memoria contenente i dati "piatti".
     * <p/>
     * L'oggetto viene creato ora in base al contenuto del wrapper
     *
     * @return l'oggetto dati così strutturato:
     *         - colonna 0: (int) chiave della riga origine se proveniente da database
     *         - colonna 1: (int) codice listino
     *         - colonna 2: (int) codice riga di listino
     *         - colonna 3: (date) data inizio validità
     *         - colonna 4: (date) data fine validità
     *         - colonna 5: (int) quantità
     *         - colonna 6: (double) prezzo
     */
    public Dati getDati() {
        /* variabili e costanti locali di lavoro */
        Dati dati = null;
        int righe = 0;
        int colonne;
        ArrayList<Elemento> elementi;
        ArrayList<Dettaglio> dettagli;
        Elemento elemento;
        Dettaglio dettaglio;
        int chiaveRigaOrigine;
        int codListino;
        int codRigaListino;
        Date data1;
        Date data2;
        int quantita;
        double prezzo;
        int idx = 0;

        try {    // prova ad eseguire il codice

            /* calcola il numero di righe e colonne */
            elementi = this.getElementi();
            for (Elemento elem : elementi) {
                dettagli = elem.getDettagli();
                righe += dettagli.size();
            }
            colonne = 7;

            /* crea un oggetto DatiMemoria della dimensione opportuna */
            dati = new DatiMemoria(righe, colonne);

            /* riempie l'oggetto Dati */
            for (int row = 0; row < elementi.size(); row++) {
                elemento = elementi.get(row);
                codListino = elemento.getCodListino();
                dettagli = elemento.getDettagli();
                for (int col = 0; col < dettagli.size(); col++) {
                    dettaglio = dettagli.get(col);
                    chiaveRigaOrigine = dettaglio.getCodChiave();
                    data1 = dettaglio.getData1();
                    data2 = dettaglio.getData2();
                    quantita = dettaglio.getQuantita();
                    prezzo = dettaglio.getPrezzo();
                    codRigaListino = dettaglio.getCodRigaListino();
                    dati.setValueAt(chiaveRigaOrigine, idx, 0);
                    dati.setValueAt(codListino, idx, 1);
                    dati.setValueAt(codRigaListino, idx, 2);
                    dati.setValueAt(data1, idx, 3);
                    dati.setValueAt(data2, idx, 4);
                    dati.setValueAt(quantita, idx, 5);
                    dati.setValueAt(prezzo, idx, 6);
                    idx++;
                } // fine del ciclo for
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return dati;
    }


    /**
     * Modifica di date e persone del pacchetto Addebiti Previsti.
     * <p/>
     * Modifica gli addebiti contenuti nel pacchetto.
     * Modifica il periodo coperto o la quantità
     * Per le voci di listino variabili, può aggiungere o rimuovere delle
     * righe in funzione del periodo.
     * Le righe modificate mantengono il vecchio prezzo.
     * Il numero di persone viene applicato solo ai prezzi per persona
     *
     * @param dataIni la nuova data inizio addebiti
     * @param dataFine la nuova data fine addebiti
     * @param persone il nuovo numero di persone
     * @param conn la connessione da utilizzare
     *
     * @return true se riuscito
     */
    public boolean modifica(Date dataIni, Date dataFine, int persone, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        boolean continua;
        ArrayList<Elemento> lista;

        try { // prova ad eseguire il codice

            /* recupera gli Elementi e gira il comando ad ognuno */
            lista = this.getElementi();
            continua = (lista != null);
            if (continua) {
                for (Elemento elemento : lista) {
                    riuscito = elemento.modifica(dataIni, dataFine, persone);
                    if (!riuscito) {
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

            /* scrive i dati sul database */
            if (riuscito) {
                riuscito = this.write(this.getCodice(), conn);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Modifica del numero di persone nel pacchetto Addebiti Previsti.
     * <p/>
     * Modifica gli addebiti contenuti nel pacchetto.
     * Il numero di persone viene applicato solo ai prezzi per persona
     * todo da implementare - deve modificare i record esistenti
     * todo cambiando solo il numero di persone
     *
     * @param persone il nuovo numero di persone
     *
     * @return true se riuscito
     */
    public boolean modifica(int persone) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        boolean continua;
        ArrayList<Elemento> lista;

        try { // prova ad eseguire il codice

            /* recupera gli Elementi e gira il comando ad ognuno */
            lista = this.getElementi();
            continua = (lista != null);
            if (continua) {
                for (Elemento elemento : lista) {
                    riuscito = elemento.modifica(persone);
                    if (!riuscito) {
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Riduce questo wrapper accorpando le righe equivalenti.
     * <p/>
     * Per ogni elemento, accorpa le righe di dettaglio uguali
     * per data inizio, data fine, riga listino sorgente e prezzo.
     *
     * @return true se riuscito
     */
    public boolean riduci() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        boolean continua;
        ArrayList<Elemento> lista;

        try { // prova ad eseguire il codice

            /* recupera gli Elementi e gira il comando ad ognuno */
            lista = this.getElementi();
            continua = (lista != null);
            if (continua) {
                for (Elemento elemento : lista) {
                    riuscito = elemento.riduci();
                    if (!riuscito) {
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Aggiunge un altro WrapAddebiti a questo.
     * <p/>
     * Se mancano degli elementi li aggiunge con i relativi dettagli
     * Per gli elementi che ci sono, aggiunge i dettagli
     *
     * @param wrapper da aggiungere a questo
     */
    public void add(WrapAddebiti wrapper) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Elemento> elementiNew;
        Elemento elemento;
        int codListino;
        ArrayList<Dettaglio> dettagli;
        Dettaglio dettaglio;

        try {    // prova ad eseguire il codice
            elementiNew = wrapper.getElementi();
            for (Elemento unElemento : elementiNew) {

                /* recupera l'elemento con lo stesso codice di listino */
                codListino = unElemento.getCodListino();
                elemento = this.getElemento(codListino);

                /* se non c'è lo aggiunge ora */
                if (elemento == null) {
                    elemento = this.addElemento(codListino);
                }// fine del blocco if

                /* aggiunge i dettagli all'elemento */
                dettagli = unElemento.getDettagli();
                for (Dettaglio unDettaglio : dettagli) {
                    dettaglio = unDettaglio.clona();
                    elemento.addDettaglio(dettaglio);
                }


            }

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Restituisce l'elemento con dato codice di listino..
     * <p/>
     *
     * @param codListino codice di listino
     *
     * @return l'elemento corrispondente, null se non esiste
     */
    private Elemento getElemento(int codListino) {
        /* variabili e costanti locali di lavoro */
        Elemento elemOut = null;
        ArrayList<Elemento> elementi;
        int codice;

        try {    // prova ad eseguire il codice
            elementi = this.getElementi();
            if (elementi != null) {
                for (Elemento e : elementi) {
                    codice = e.getCodListino();
                    if (codice == codListino) {
                        elemOut = e;
                        break;
                    }// fine del blocco if
                }
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return elemOut;
    }


    public ArrayList<Elemento> getElementi() {
        return elementi;
    }


    private void setElementi(ArrayList<Elemento> elementi) {
        this.elementi = elementi;
    }


    /**
     * Ritorna la data di inizio generale dei dati
     * <p/>
     *
     * @return la data di inizio
     */
    public Date getDataInizio() {
        return dataInizio;
    }


    private void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }


    /**
     * Ritorna la data di fine generale dei dati
     * <p/>
     *
     * @return la data di fine
     */
    public Date getDataFine() {
        return dataFine;
    }


    private void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }


    /**
     * Ritorna il numero di persone generale dei dati
     * <p/>
     *
     * @return il numero di persone
     */
    public int getPersone() {
        return persone;
    }


    private void setPersone(int persone) {
        this.persone = persone;
    }


    private int getCodice() {
        return codice;
    }


    private void setCodice(int codice) {
        this.codice = codice;
    }


    private Connessione getConnessione() {
        return connessione;
    }


    private void setConnessione(Connessione connessione) {
        this.connessione = connessione;
    }


    private Modulo getModuloTesta() {
        return moduloTesta;
    }


    private void setModuloTesta(Modulo moduloTesta) {
        this.moduloTesta = moduloTesta;
    }


    private Modulo getModuloRighe() {
        return moduloRighe;
    }


    private void setModuloRighe(Modulo moduloRighe) {
        this.moduloRighe = moduloRighe;
    }


    private Campo getCampoLink() {
        return campoLink;
    }


    private void setCampoLink(Campo campoLink) {
        this.campoLink = campoLink;
    }


    private Campo getCampoListino() {
        return campoListino;
    }


    private void setCampoListino(Campo campoListino) {
        this.campoListino = campoListino;
    }


    private Campo getCampoRigaListino() {
        return campoRigaListino;
    }


    private void setCampoRigaListino(Campo campoRigaListino) {
        this.campoRigaListino = campoRigaListino;
    }


    private Campo getCampoInizio() {
        return campoInizio;
    }


    private void setCampoInizio(Campo campoInizio) {
        this.campoInizio = campoInizio;
    }


    private Campo getCampoFine() {
        return campoFine;
    }


    private void setCampoFine(Campo campoFine) {
        this.campoFine = campoFine;
    }


    private Campo getCampoQuantita() {
        return campoQuantita;
    }


    private void setCampoQuantita(Campo campoQuantita) {
        this.campoQuantita = campoQuantita;
    }


    private Campo getCampoPrezzo() {
        return campoPrezzo;
    }


    private void setCampoPrezzo(Campo campoPrezzo) {
        this.campoPrezzo = campoPrezzo;
    }


    private Campo getCampoInizioTesta() {
        return campoInizioTesta;
    }


    private void setCampoInizioTesta(Campo campoInizioTesta) {
        this.campoInizioTesta = campoInizioTesta;
    }


    private Campo getCampoFineTesta() {
        return campoFineTesta;
    }


    private void setCampoFineTesta(Campo campoFineTesta) {
        this.campoFineTesta = campoFineTesta;
    }


    private Campo getCampoPersoneTesta() {
        return campoPersoneTesta;
    }


    private void setCampoPersoneTesta(Campo campoPersoneTesta) {
        this.campoPersoneTesta = campoPersoneTesta;
    }


    /**
     * Singolo elemento di listino.
     * </p>
     */
    public final class Elemento {

        /**
         * codice di listino
         */
        private int codListino;

        /**
         * elenco delle righe di dettaglio dell'elemento
         */
        private ArrayList<Dettaglio> dettagli = null;


        /**
         * Costruttore completo.
         * <p/>
         *
         * @param codListino codice del listino
         */
        public Elemento(int codListino) {
            /* rimanda al costruttore della superclasse */
            super();

            this.setCodListino(codListino);

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }// fine del metodo costruttore completo


        /**
         * Regolazioni immediate di riferimenti e variabili. <br>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {

            try { // prova ad eseguire il codice

                /* crea lista elementi vuota */
                this.setDettagli(new ArrayList<Dettaglio>());

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Aggiunge una riga di dettaglio a questo elemento.
         * <p/>
         *
         * @param codChiave codice chiave della riga se proveniente da database
         * @param data1 data inizio
         * @param data2 data fine validità
         * @param quantita quantita
         * @param prezzo prezzo unitario
         * @param codRiga codice riga listino di riferimento (per listino variabile)
         *
         * @return la riga di dettaglio creata
         */
        public Dettaglio addDettaglio(int codChiave,
                                      Date data1,
                                      Date data2,
                                      int quantita,
                                      double prezzo,
                                      int codRiga) {
            /* variabili e costanti locali di lavoro */
            Dettaglio dettaglio = null;

            try {    // prova ad eseguire il codice
                dettaglio = new Dettaglio(codChiave, data1, data2, quantita, prezzo, codRiga);
                this.addDettaglio(dettaglio);
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return dettaglio;
        }


        /**
         * Aggiunge una riga di dettaglio a questo elemento.
         * <p/>
         *
         * @param dettaglio da aggiungere
         */
        public void addDettaglio(Dettaglio dettaglio) {
            /* variabili e costanti locali di lavoro */
            ArrayList<Dettaglio> dettagli;

            try {    // prova ad eseguire il codice
                dettagli = this.getDettagli();
                dettagli.add(dettaglio);
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Modifica le date e le persone dell'elemento.
         * <p/>
         * Modifica il periodo coperto o il numero di persone
         * Per le voci di listino variabili, può aggiungere o rimuovere delle
         * righe di dettaglio in funzione del periodo.
         * Le righe modificate mantengono il vecchio prezzo.
         * Il numero di persone viene applicato solo ai prezzi per persona
         *
         * @param dataIni la nuova data inizio addebiti
         * @param dataFine la nuova data fine addebiti
         * @param persone il nuovo numero di persone
         *
         * @return true se riuscito
         */
        public boolean modifica(Date dataIni, Date dataFine, int persone) {
            /* variabili e costanti locali di lavoro */
            boolean riuscito = true;
            ArrayList<Dettaglio> dettagliOld;
            ArrayList<Dettaglio> dettagliNew;
            ArrayList<Dettaglio> dettagliDef;
            int codListino;
            ArrayList<WrapListino> listino;
            Date iniRiga;
            Date endRiga;
            double prezzo;
            int codRigaListino;
            int quantita;

            Dettaglio dettaglio;
            Dettaglio unDettaglioOld;
            boolean esistente;
            int codice;
            Modulo modulo;

            int codChiave;
            Date data1;
            Date data2;
            int indice;

            try { // prova ad eseguire il codice

                /**
                 * crea una nuova lista di righe di dettaglio ottenuta da listino
                 */
                codListino = this.getCodListino();
                listino = ListinoModulo.getPrezzi(codListino, dataIni, dataFine);
                dettagliNew = new ArrayList<Dettaglio>();
                for (WrapListino riga : listino) {
                    iniRiga = riga.getPrimaData();
                    endRiga = riga.getSecondaData();
                    prezzo = riga.getPrezzo();
                    codRigaListino = riga.getCodRiga();

                    /* determina la quantità da utilizzare in funzione del tipo di prezzo */
                    if (Listino.TipoPrezzo.isPerPersona(codListino)) {
                        quantita = persone;
                    } else {
                        quantita = 1;
                    }// fine del blocco if-else

                    dettaglio = new Dettaglio(0,
                            iniRiga,
                            endRiga,
                            quantita,
                            prezzo,
                            codRigaListino);
                    dettagliNew.add(dettaglio);

                } // fine del ciclo for-each

                /**
                 * spazzola le vecchie righe di dettaglio e cancella
                 * dal database quelle in eccesso
                 * (provenienti da righe di listino diverse da quelle nuove)
                 */
                dettagliOld = this.getDettagli();
                for (Dettaglio dettaglioOld : dettagliOld) {

                    /* controlla se esiste nei nuovi */
                    esistente = false;
                    for (Dettaglio dettaglioNew : dettagliNew) {
                        if (dettaglioOld.isStessaOrigine(dettaglioNew)) {
                            esistente = true;
                            break;
                        }// fine del blocco if
                    } // fine del blocco for-each su DettagliNew

                    /* se non esiste cancella la riga dal database */
                    if (!esistente) {
                        codice = dettaglioOld.getCodChiave();
                        modulo = getModuloRighe();
                        modulo.query().eliminaRecord(codice);
                    }// fine del blocco if

                }  // fine del blocco for-each su DettagliOld

                /**
                 * Crea la lista definitiva delle righe di dettaglio:
                 * Spazzola le nuove righe di dettaglio e per ognuna crea una nuova riga.
                 * - se esiste già nelle vechie righe una riga con la stessa origine la usa (modificata)
                 * - se non esiste la aggiunge
                 */
                dettagliDef = new ArrayList<Dettaglio>();
                for (Dettaglio dettaglioNew : dettagliNew) {

                    /* recupera l'indice del nuovo nella lista dei vecchi
                    * se non esiste è -1 */
                    indice = -1;
                    for (int k = 0; k < dettagliOld.size(); k++) {
                        unDettaglioOld = dettagliOld.get(k);
                        if (unDettaglioOld.isStessaOrigine(dettaglioNew)) {
                            indice = k;
                            break;
                        }// fine del blocco if
                    } // fine del ciclo for

                    if (indice >= 0) {    // esiste nei vecchi
                        unDettaglioOld = dettagliOld.get(indice);
                        codChiave = unDettaglioOld.getCodChiave();  // dalla vecchia riga
                        data1 = dettaglioNew.getData1();            // dalla nuova riga
                        data2 = dettaglioNew.getData2();            // dalla nuova riga
                        quantita = dettaglioNew.getQuantita();      // dalla nuova riga
                        prezzo = unDettaglioOld.getPrezzo();        // dalla vecchia riga
                        codRigaListino = dettaglioNew.getCodRigaListino();  // dalla nuova riga
                    } else {           // non esiste nei vecchi
                        codChiave = dettaglioNew.getCodChiave();
                        data1 = dettaglioNew.getData1();
                        data2 = dettaglioNew.getData2();
                        quantita = dettaglioNew.getQuantita();
                        prezzo = dettaglioNew.getPrezzo();
                        codRigaListino = dettaglioNew.getCodRigaListino();
                    }// fine del blocco if-else

                    dettaglio = new Dettaglio(codChiave,
                            data1,
                            data2,
                            quantita,
                            prezzo,
                            codRigaListino);
                    dettagliDef.add(dettaglio);

                }  // fine del blocco for-each su DettagliOld

                /* sostituisce i dettagli dell'elemento */
                this.setDettagli(dettagliDef);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return riuscito;
        }


        /**
         * Modifica persone dell'elemento.
         * <p/>
         *
         * @param persone il nuovo numero di persone
         *
         * @return true se riuscito
         */
        public boolean modifica(int persone) {
            /* variabili e costanti locali di lavoro */
            boolean riuscito = false;

            /* valore di ritorno */
            return riuscito;
        }


        /**
         * Riduce questo elemento accorpando le righe equivalenti.
         * <p/>
         * Accorpa le righe di dettaglio equivalenti
         * per data inizio, data fine, riga listino sorgente e prezzo.
         *
         * @return true se riuscito
         */
        private boolean riduci() {
            /* variabili e costanti locali di lavoro */
            boolean riuscito = true;
            ArrayList<Dettaglio> dettagli;
            ArrayList<Dettaglio> dettagliNew;
            LinkedHashMap<String, ArrayList<Dettaglio>> mappa;
            String firma;
            ArrayList<Dettaglio> listaDettagli;
            Dettaglio unDettaglio;
            Dettaglio dettaglioNew;
            int qtaTot;

            try { // prova ad eseguire il codice

                /* calcola e registra la firma in ogni dettaglio */
                dettagli = this.getDettagli();
                for (Dettaglio dettaglio : dettagli) {
                    dettaglio.calcFirma();
                }

                /**
                 * spazzola i dettagli
                 * crea una mappa
                 * contiene un elemento per ogni firma diversa
                 * la chiave è la firma
                 * il valore è la lista delle righe di dettaglio con quella firma
                 */
                mappa = new LinkedHashMap<String, ArrayList<Dettaglio>>();
                for (Dettaglio dettaglio : dettagli) {

                    /* recupera la firma della riga di dettaglio */
                    firma = dettaglio.getFirma();

                    /* se non esiste nella mappa aggiunge ora l'elemento */
                    if (!mappa.containsKey(firma)) {
                        mappa.put(firma, new ArrayList<Dettaglio>());
                    }// fine del blocco if

                    /* aggiunge la riga di dettaglio ai valori dell'elemento della mappa */
                    listaDettagli = mappa.get(firma);
                    listaDettagli.add(dettaglio);

                }

                /**
                 * Spazzola i valori della mappa e crea una riga di dettaglio per ogni elemento.
                 * La riga è un clone della prima riga nella lista righe dell'elemento.
                 * Viene modificata solo la quantità come somma delle quantità di tutte le righe.
                 */
                dettagliNew = new ArrayList<Dettaglio>();
                for (ArrayList<Dettaglio> lista : mappa.values()) {

                    /* determina la quantità totale (somma delle quantità) */
                    qtaTot = 0;
                    for (Dettaglio dettaglio : lista) {
                        qtaTot += dettaglio.getQuantita();
                    }

                    /**
                     * clona il primo dettaglio, ne modifica la quantità
                     * e lo aggiunge alla nuova lista
                     */
                    unDettaglio = lista.get(0);
                    dettaglioNew = unDettaglio.clona();
                    dettaglioNew.setQuantita(qtaTot);
                    dettagliNew.add(dettaglioNew);
                }

                /**
                 * Sostituisce la lista dei dettagli con la nuova
                 */
                this.setDettagli(dettagliNew);


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return riuscito;

        }


        /**
         * Rimuove il riferimento alla riga di origine dai dati dell'elemento.
         * <p/>
         */
        public void cleanOrigine() {
            /* variabili e costanti locali di lavoro */
            ArrayList<Dettaglio> dettagli;

            try {    // prova ad eseguire il codice
                dettagli = this.getDettagli();
                if (dettagli != null) {
                    for (Dettaglio dettaglio : dettagli) {
                        dettaglio.setCodChiave(0);
                    }
                }// fine del blocco if
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

        }


        public int getCodListino() {
            return codListino;
        }


        private void setCodListino(int codListino) {
            this.codListino = codListino;
        }


        public ArrayList<Dettaglio> getDettagli() {
            return dettagli;
        }


        private void setDettagli(ArrayList<Dettaglio> dettagli) {
            this.dettagli = dettagli;
        }
    } // fine della classe 'interna'


    /**
     * Singola riga di dettaglio di un elemento.
     * </p>
     */
    public final class Dettaglio implements Cloneable {

        /**
         * codice chiave della riga se proveniente da database
         */
        private int codChiave = 0;

        /**
         * data di inizio validità
         */
        private Date data1 = null;

        /**
         * data di fine validità
         */
        private Date data2 = null;

        /**
         * quantità
         */
        private int quantita = 0;

        /**
         * prezzo unitario
         */
        private double prezzo = 0;

        /**
         * codice della riga di listino di riferimento
         */
        private int codRigaListino = 0;

        /**
         * firma riportante date, prezzo, cod. riga listino
         */
        private String firma;


        /**
         * Costruttore completo con parametri.
         * <p/>
         *
         * @param codChiave codice chiave della riga se proveniente da database
         * @param data1 data inizio
         * @param data2 data fine
         * @param quantita quantita
         * @param prezzo prezzo unitario
         * @param codRigaListino codice della riga di listino di riferimento
         */
        public Dettaglio(int codChiave,
                         Date data1,
                         Date data2,
                         int quantita,
                         double prezzo,
                         int codRigaListino) {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili di istanza coi parametri */
            this.setCodChiave(codChiave);
            this.setData1(data1);
            this.setData2(data2);
            this.setQuantita(quantita);
            this.setPrezzo(prezzo);
            this.setCodRigaListino(codRigaListino);

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }// fine del metodo costruttore completo


        /**
         * Regolazioni immediate di riferimenti e variabili. <br>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
        }


        /**
         * Controlla se questa riga di dettaglio è stata originata
         * dalla stessa riga di listino rispetto a una riga di
         * dettaglio fornita.
         * <p/>
         *
         * @param altroDettaglio riga di dettaglio da confrontare con questa
         *
         * @return true se le righe sono state originate dalla stessa riga di listino
         */
        private boolean isStessaOrigine(Dettaglio altroDettaglio) {
            /* variabili e costanti locali di lavoro */
            boolean stessaOrigine = false;
            int rifQuesta;
            int rifAltra;

            try {    // prova ad eseguire il codice
                rifQuesta = this.getCodRigaListino();
                rifAltra = altroDettaglio.getCodRigaListino();
                if (rifQuesta == rifAltra) {
                    stessaOrigine = true;
                }// fine del blocco if
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return stessaOrigine;
        }


        /**
         * Controlla se questa riga di dettaglio copre lo stesso periodo
         * rispetto a una riga di dettaglio fornita.
         * <p/>
         *
         * @param altroDettaglio riga di dettaglio da confrontare con questa
         *
         * @return true se le righe coprono lo stesso periodo
         */
        private boolean isStessoPeriodo(Dettaglio altroDettaglio) {
            /* variabili e costanti locali di lavoro */
            boolean uguali = false;
            Date inizio1, inizio2;
            Date fine1, fine2;

            try {    // prova ad eseguire il codice
                inizio1 = this.getData1();
                fine1 = this.getData2();
                inizio2 = altroDettaglio.getData1();
                fine2 = altroDettaglio.getData2();
                if (inizio1.equals(inizio2)) {
                    if (fine1.equals(fine2)) {
                        uguali = true;
                    }// fine del blocco if
                }// fine del blocco if
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return uguali;
        }


        /**
         * Controlla se questa riga di dettaglio ha lo stesso prezzo
         * rispetto a una riga di dettaglio fornita.
         * <p/>
         *
         * @param altroDettaglio riga di dettaglio da confrontare con questa
         *
         * @return true se le righe hanno lo stesso prezzo
         */
        private boolean isStessoPrezzo(Dettaglio altroDettaglio) {
            /* variabili e costanti locali di lavoro */
            boolean uguali = false;
            double prezzo1, prezzo2;

            try {    // prova ad eseguire il codice
                prezzo1 = this.getPrezzo();
                prezzo2 = altroDettaglio.getPrezzo();
                if (prezzo1 == prezzo2) {
                    uguali = true;
                }// fine del blocco if
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return uguali;
        }


        /**
         * Controlla se questa riga di dettaglio è cumulabile
         * con a una riga di dettaglio fornita.
         * <p/>
         * Si considerano cumulabili se hanno i seguenti campi uguali:
         * - hanno le stesse date di inizio e fine
         * - fanno riferimento alla stessa riga di listino
         * - hanno lo stesso prezzo
         *
         * @param altroDettaglio riga di dettaglio da confrontare con questa
         *
         * @return true se le righe sono cumulabili
         */
        private boolean isCumulabile(Dettaglio altroDettaglio) {
            /* variabili e costanti locali di lavoro */
            boolean cumulabile = false;

            try {    // prova ad eseguire il codice
                if (this.isStessoPeriodo(altroDettaglio)) {
                    if (this.isStessaOrigine(altroDettaglio)) {
                        if (this.isStessoPrezzo(altroDettaglio)) {
                            cumulabile = true;
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return cumulabile;
        }


        /**
         * Calcola la firma di questo elemento e la registra nella variabile di istanza.
         * <p/>
         */
        private void calcFirma() {
            /* variabili e costanti locali di lavoro */
            String firma;

            try {    // prova ad eseguire il codice
                firma = this.getSignature();
                this.setFirma(firma);
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Ritorna una firma di questo elemento costruita su:
         * - data inizio
         * - data fine
         * - prezzo
         * - cod. riga listino
         * <p/>
         *
         * @return la firma dell'elemento
         */
        private String getSignature() {
            /* variabili e costanti locali di lavoro */
            String signature = "";
            Date data;
            String stringa;
            double prezzo;
            int codListino;

            try {    // prova ad eseguire il codice
                data = this.getData1();
                if (data != null) {
                    stringa = Lib.Data.getStringa(data);
                    signature += stringa;
                }// fine del blocco if

                data = this.getData2();
                if (data != null) {
                    stringa = Lib.Data.getStringa(data);
                    signature += stringa;
                }// fine del blocco if

                prezzo = this.getPrezzo();
                signature += prezzo;

                codListino = this.getCodRigaListino();
                signature += codListino;

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return signature;
        }


        /**
         * Ritorna una copia profonda dell'oggetto (deep copy)
         * <p/>
         *
         * @return un clone di questo ogetto
         */
        public Dettaglio clona() {
            /* variabili e costanti locali di lavoro */
            Dettaglio dettaglio;

            try { // prova ad eseguire il codice

                /* invoca il metodo sovrascritto della superclasse Object */
                dettaglio = (Dettaglio)super.clone();

            } catch (CloneNotSupportedException unErrore) { // intercetta l'errore
                throw new InternalError();
            }// fine del blocco try-catch

            /* valore di ritorno */
            return dettaglio;
        }


        private int getCodChiave() {
            return codChiave;
        }


        private void setCodChiave(int codChiave) {
            this.codChiave = codChiave;
        }


        public Date getData1() {
            return data1;
        }


        private void setData1(Date data1) {
            this.data1 = data1;
        }


        public Date getData2() {
            return data2;
        }


        private void setData2(Date data2) {
            this.data2 = data2;
        }


        public int getQuantita() {
            return quantita;
        }


        private void setQuantita(int quantita) {
            this.quantita = quantita;
        }


        public double getPrezzo() {
            return prezzo;
        }


        public void setPrezzo(double prezzo) {
            this.prezzo = prezzo;
        }


        public int getCodRigaListino() {
            return codRigaListino;
        }


        public void setCodRigaListino(int codRigaListino) {
            this.codRigaListino = codRigaListino;
        }


        private String getFirma() {
            return firma;
        }


        private void setFirma(String firma) {
            this.firma = firma;
        }
    } // fine della classe 'interna'


    /**
     * Tipologie di origine dei dati.
     */
    public enum Origine {

        periodo,
        conto;

    }// fine della classe

}// fine della classe