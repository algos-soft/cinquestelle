/**
 * Title:     Connessione
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      23-set-2004
 */
package it.algos.base.database.connessione;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.Db;
import it.algos.base.database.dati.Dati;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.wrapper.CampoValore;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;

/**
 * Connessione a un database.
 * </p>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un interfaccia unificata per la gestione di una
 * connessione con una sorgente di dati</li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 23-set-2004 ore 12.09.18
 */
public interface Connessione {


    /**
     * Ritorna il database proprietario di questa connessione.
     * <p/>
     *
     * @return il database proprietario di questa connessione
     */
    public abstract Db getDb();


    /**
     * Apre la connessione con la sorgente di dati. <br>
     *
     * @return true se riuscito
     */
    public abstract boolean open() throws Exception;


    /**
     * Chiude la connessione con la sorgente di dati, se aperta.<br>
     * Altrimenti non fa nulla.
     */
    public abstract void close();


    /**
     * Avvia una transazione.
     * <p/>
     */
    public abstract void startTransaction();


    /**
     * Conferma la transazione corrente.
     * <p/>
     */
    public abstract void commit();


    /**
     * Abortisce la transazione corrente.
     * <p/>
     */
    public abstract void rollback();


    /**
     * Controlla se la connessione è attiva e funzionante.
     * <p/>
     * Tenta di aprire la connessione e poi la richiude<br>
     *
     * @return true sel la connessione e' attiva e funzionante.
     */
    public abstract boolean test();


    /**
     * Recupera l'oggetto DatabaseMetaData di questa connessione.
     * <p/>
     *
     * @return l'oggetto DatabaseMetaData della connessione
     */
    public abstract DatabaseMetaData getMetaData();


    /**
     * Restituisce l'oggetto dati (configurazione della Connessione). <br>
     *
     * @return l'oggetto dati della connessione.
     */
    public abstract ConnessioneDati getDatiConnessione();


    /**
     * Assegna un oggetto Dati alla connessione.
     *
     * @param datiConnessione l'oggetto Dati da assegnare
     */
    public abstract void setDatiConnessione(ConnessioneDati datiConnessione);


    /**
     * Restituisce true se la connessione e' aperta.
     *
     * @return true se la connessione e' aperta
     */
    public abstract boolean isOpen();


    /**
     * Indica se la connessione è correntemente in transazione.
     * <p/>
     *
     * @return true se è in transazione
     */
    public abstract boolean isInTransaction();


    /**
     * Carica una selezione di record.
     * <p/>
     *
     * @param query informazioni per effettuare la selezione
     *
     * @return un oggetto dati
     */
    public abstract Dati querySelezione(Query query);


    /**
     * Esegue la modifica di uno o piu' record.
     * <p/>
     * La modifica puo' consistere in inserimento di nuovi record
     * o modifica o cancellazione di record esistenti.
     *
     * @param query informazioni per effettuare la modifica
     *
     * @return il numero di record interessati
     */
    public abstract int queryModifica(Query query);


    /**
     * Carica un singolo record del Modulo.
     * <p/>
     *
     * @param modulo di riferimento
     * @param codice codice chiave del record
     *
     * @return un oggetto dati con tutti i campi del modello
     */
    public abstract Dati caricaRecord(Modulo modulo, int codice);


    /**
     * Carica una selezione di records del Modulo.
     * <p/>
     *
     * @param modulo di riferimento
     * @param filtro il filtro per selezionare i records (null = tutti)
     * @param ordine l'ordine del risultato (null = nessun ordine)
     *
     * @return un oggetto dati con tutti i campi del modello
     */
    public abstract Dati caricaRecords(Modulo modulo, Filtro filtro, Ordine ordine);


    /**
     * Carica una selezione di records del Modulo.
     * <p/>
     * I records vengono ordinati sul campo Ordine
     *
     * @param modulo di riferimento
     * @param filtro il filtro per selezionare i records (null = tutti)
     *
     * @return un oggetto dati con tutti i campi del modello
     */
    public abstract Dati caricaRecords(Modulo modulo, Filtro filtro);


    /**
     * Carica una selezione di records del Modulo.
     * <p/>
     *
     * @param modulo di riferimento
     * @param ordine l'ordine del risultato
     *
     * @return un oggetto dati con tutti i campi del modello
     */
    public abstract Dati caricaRecords(Modulo modulo, Ordine ordine);


    /**
     * Carica tutti i records del Modulo.
     * <p/>
     *
     * @param modulo di riferimento
     *
     * @return un oggetto dati con tutti i record e tutti i campi del modello
     */
    public abstract Dati caricaRecords(Modulo modulo);


    /**
     * Crea un nuovo record con valori.
     * <p/>
     * Usa il valore specificato per tutti i campi forniti.
     * I campi non forniti vengono riempiti con i valori di default.
     *
     * @param modulo di riferimento
     * @param valori da registrare
     *
     * @return il codice del record creato, -1 se non riuscito
     */
    public abstract int nuovoRecord(Modulo modulo, ArrayList<CampoValore> valori);


    /**
     * Crea un nuovo record.
     * <p/>
     * I campi vengono riempiti con i valori di default.
     *
     * @param modulo di riferimento
     *
     * @return il codice del record creato, -1 se non riuscito
     */
    public abstract int nuovoRecord(Modulo modulo);


    /**
     * Registra un record esistente.
     * <p/>
     * Usa il valore Archivio dei campi
     *
     * @param modulo di riferimento
     * @param codice il codice chiave del record
     * @param campi la lista dei campi da registrare
     *
     * @return true se riuscito
     */
    public abstract boolean registraRecord(Modulo modulo, int codice, ArrayList<Campo> campi);


    /**
     * Registra un record esistente.
     * <p/>
     * Usa i campi e i valori forniti
     *
     * @param modulo di riferimento
     * @param codice il codice chiave del record
     * @param campiValore la lista dei campi con valori (oggetti CampoValore)
     *
     * @return true se riuscito
     */
    public abstract boolean registraRecordValori(Modulo modulo,
                                                 int codice,
                                                 ArrayList<CampoValore> campiValore);


    /**
     * Elimina un record esistente.
     * <p/>
     *
     * @param modulo di riferimento
     * @param codice il codice chiave del record
     *
     * @return true se riuscito
     */
    public abstract boolean eliminaRecord(Modulo modulo, int codice);


    /**
     * Elimina tutti i records corrispondenti a un dato filtro.
     * <p/>
     * ATTENZIONE! QUESTO METODO NON INVOCA I TRIGGER NEL MODELLO!
     *
     * @param modulo di riferimento
     * @param filtro il filtro da applicare (null per nessun filtro)
     *
     * @return true se riuscito
     */
    public abstract boolean eliminaRecords(Modulo modulo, Filtro filtro);


    /**
     * Elimina tutti i records della tavola.
     * <p/>
     * ATTENZIONE! QUESTO METODO NON INVOCA I TRIGGER NEL MODELLO!
     *
     * @param modulo di riferimento
     *
     * @return true se riuscito
     */
    public abstract boolean eliminaRecords(Modulo modulo);


    /**
     * Restituisce il numero di records corrispondenti
     * a un Filtro dato.
     * <p/>
     *
     * @param modulo di riferimento
     * @param filtro il filtro da applicare (null per tutti i record)
     *
     * @return il numero di record selezionati dal filtro
     */
    public abstract int contaRecords(Modulo modulo, Filtro filtro);


    /**
     * Restituisce il numero totale di records di un modulo.
     * <p/>
     *
     * @param modulo di riferimento
     *
     * @return il numero totale di record del modulo
     */
    public abstract int contaRecords(Modulo modulo);


    /**
     * Recupera il massimo valore presente in un dato campo per un dato filtro.
     *
     * @param unCampo il campo per il quale recuperare il massimo valore
     * @param unFiltro il filtro da applicare, null per non specificato
     *
     * @return il massimo valore per il campo,
     *         (null se non ci sono records corrispondenti al filtro)
     */
    public abstract int valoreMassimo(Campo unCampo, Filtro unFiltro);


    /**
     * Determina se un campo e' vuoto in tutti i records della sua tavola.
     * <p/>
     * Un campo e' considerato vuoto quando contiene solo valori
     * nulli o valori considerati vuoti per lo specifico tipo di campo.
     *
     * @param unCampo il campo da controllare
     *
     * @return true se tutti i records sono vuoti
     */
    public abstract boolean isCampoVuoto(Campo unCampo);


    /**
     * Inverte i valori di un campo di due record.
     * <p/>
     *
     * @param campo il campo del quale invertire i valori
     * @param codA codice chiave del primo record
     * @param codB codice chiave del secondo record
     *
     * @return true se riuscito
     */
    public abstract boolean swapCampo(Campo campo, int codA, int codB);


    /**
     * Inverte i valori del campo ordine di due record.
     * <p/>
     *
     * @param modulo di riferimento
     * @param codA codice chiave del primo record
     * @param codB codice chiave del secondo record
     *
     * @return true se riuscito
     */
    public abstract boolean swapOrdine(Modulo modulo, int codA, int codB);


    /**
     * Controlla l'esistenza di una tavola.
     * <p/>
     *
     * @param tavola il nome della tavola
     *
     * @return true se esiste
     */
    public abstract boolean isEsisteTavola(String tavola);


    /**
     * Determina se una data colonna esiste sul database.
     * <p/>
     *
     * @param nomeTavola nome della tavola
     * @param nomeColonna nome della colonna
     *
     * @return true se la colonna esiste
     */
    public abstract boolean isEsisteColonna(String nomeTavola, String nomeColonna);


    /**
     * Determina se un dato campo esiste sul database.
     * <p/>
     *
     * @param campo il campo da controllare
     *
     * @return true se il campo esiste
     */
    public abstract boolean isEsisteCampo(Campo campo);


    /**
     * Crea la tavola del modulo sul database.
     * <p/>
     * La tavola viene creata solo se non gia' esistente.
     *
     * @param modulo di riferimento
     *
     * @return true se esistente o creata correttamente
     *         false se non creata
     */
    public abstract boolean creaTavola(Modulo modulo);


    /**
     * Elimina una tavola dal database.
     * <p/>
     *
     * @param tavola il nome della tavola
     *
     * @return true se riuscito
     */
    public abstract boolean eliminaTavola(String tavola);


    /**
     * Crea una colonna del database in base a un campo.
     * <p/>
     * Crea la colonna del tipo appropriato al campo.
     * Non effettua altre regolazioni (caratteristiche, indici ecc...)
     *
     * @param campo il campo per il quale creare la colonna
     *
     * @return true se la colonna e' stata creata correttamente
     */
    public abstract boolean creaColonna(Campo campo);


    /**
     * Allinea una colonna del Db in base al corrispondente
     * campo del Modello.
     * <p/>
     * Se la colonna non esiste, la crea e la allinea
     * Se la colonna esiste, la allinea soltanto
     *
     * @param campo il campo per il quale allineare la colonna
     *
     * @return true se ha eseguito correttamente
     */
    public abstract boolean allineaCampo(Campo campo);


    /**
     * Elimina una colonna dal database.
     * <p/>
     *
     * @param campo il campo corrispondente alla colonna da eliminare
     *
     * @return true se riuscito.
     */
    public abstract boolean eliminaColonna(Campo campo);


    /**
     * Attiva o disattiva l'integrità referenziale.
     * <p/>
     *
     * @param flag di attivazione
     *
     * @return true se riuscito
     */
    public abstract boolean setReferentialIntegrity(boolean flag);


    /**
     * Ritorna la connessione Java.
     * <p/>
     *
     * @return la connessione
     */
    public abstract Connection getConnection();


}// fine della interfaccia
