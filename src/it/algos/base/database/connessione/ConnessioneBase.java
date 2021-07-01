/**
 * Title:     ConnessioneBase
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      23-set-2004
 */
package it.algos.base.database.connessione;

import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * </p>
 * Questa classe astratta: <ul>
 * <li> Implementa i metodi comuni alle connessioni a una sorgente di dati
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 23-set-2004 ore 12.10.00
 */
public abstract class ConnessioneBase implements Connessione {

    /**
     * database di riferimento per questa connessione
     */
    private Db db = null;

    /**
     * wrapper contenente i dati per effettuare la connessione
     */
    private ConnessioneDati datiConnessione = null;

    /**
     * flag - indica se la connessione e' aperta
     */
    private boolean isOpen = false;

    /**
     * flag - indica se la connessione e' in fase di transazione
     */
    private boolean inTransaction = false;


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param db il database di riferimento
     */
    public ConnessioneBase(Db db) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setDb(db);

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

        /*
         * crea l'oggetto dati per questa connessione
         * usa i dati di connessione di default del database
         */
        this.setDatiConnessione(new ConnessioneDati(this));

    }// fine del metodo inizia


    /**
     * Registra un record esistente.
     * <p/>
     * Sovrascritto nelle sottoclassi.
     *
     * @param codice il codice chiave del record
     * @param campi la lista dei campi da registrare
     *
     * @return true se riuscito
     */
    public boolean registraRecord(int codice, ArrayList campi) {
        return true;
    }


    /**
     * Restituisce il numero totale di records di un modulo.
     * <p/>
     *
     * @param modulo di riferimento
     *
     * @return il numero totale di record del modulo
     */
    public int contaRecords(Modulo modulo) {
        return this.contaRecords(modulo, null);
    }


    /**
     * Avvia una transazione.
     * <p/>
     */
    public void startTransaction() {
        try {    // prova ad eseguire il codice
            if (!this.isInTransaction()) {
                this.getConnection().setAutoCommit(false);
                this.setInTransaction(true);
            } else {
                throw new Exception("Transazione già in corso!");
            }// fine del blocco if-else
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Conferma la transazione corrente.
     * <p/>
     */
    public void commit() {
        try {    // prova ad eseguire il codice
            if (this.isInTransaction()) {
                this.getConnection().commit();
                this.getConnection().setAutoCommit(true);
                this.setInTransaction(false);
            } else {
                throw new Exception("La transazione non è aperta!");
            }// fine del blocco if-else
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Abortisce la transazione corrente.
     * <p/>
     */
    public void rollback() {
        try {    // prova ad eseguire il codice
            if (this.isInTransaction()) {
                this.getConnection().rollback();
                this.getConnection().setAutoCommit(true);
                this.setInTransaction(false);
            } else {
                throw new Exception("La transazione non è aperta!");
            }// fine del blocco if-else
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il database proprietario di questa connessione.
     * <p/>
     *
     * @return il database proprietario di questa connessione
     */
    public Db getDb() {
        return db;
    }


    private void setDb(Db db) {
        this.db = db;
    }


    public ConnessioneDati getDatiConnessione() {
        return datiConnessione;
    }


    public void setDatiConnessione(ConnessioneDati datiConnessione) {
        this.datiConnessione = datiConnessione;
    }


    public boolean isOpen() {
        return isOpen;
    }


    public void setOpen(boolean open) {
        isOpen = open;
    }


    /**
     * Indica se la connessione è correntemente in transazione.
     * <p/>
     *
     * @return true se è in transazione
     */
    public boolean isInTransaction() {
        return inTransaction;
    }


    private void setInTransaction(boolean inTransaction) {
        this.inTransaction = inTransaction;
    }


    /**
     * Attiva o disattiva l'integrità referenziale.
     * <p/>
     *
     * @param flag di attivazione
     *
     * @return true se riuscito
     */
    public boolean setReferentialIntegrity(boolean flag) {
        return false;
    }


    /**
     * Ritorna la connessione.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @return la connessione
     */
    public Connection getConnection() {
        return null;
    }

}// fine della classe
