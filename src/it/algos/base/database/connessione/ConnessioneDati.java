/**
 * Title:     ConnessioneDati
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      23-set-2004
 */
package it.algos.base.database.connessione;

import it.algos.base.database.Db;
import it.algos.base.errore.Errore;

/**
 * </p>
 * Questa classe: <ul>
 * Implementa un wrapper che mantiene i dati per effettuare
 * una connessione a una sorgente di dati.<br>
 * Mantiene i dati utili per ogni tipo di connessione, la implementazione
 * specifica della Connessione utilizza solo i dati che le servono.
 * </ul>
 *
 * @author Alberto Colombo, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 23-set-2004 ore 12.22.16
 */
public final class ConnessioneDati extends Object implements Cloneable {

    /**
     * Riferimento alla connessione proprietaria di questi dati
     */
    private Connessione conn = null;

    /**
     * Riferimento al database proprietario di questi dati
     */
    private Db db = null;

    /**
     * indirizzo dell'host con il quale stabilire la connessione <br>
     * es. "192.168.55.2" o "localhost"
     */
    private String host = null;

    /**
     * porta del database <br>
     * es. 5432
     */
    private int porta = 0;

    /**
     * nome del database <br>
     * es. "algos"
     */
    private String nomeDatabase = null;

    /**
     * login per accedere al database
     */
    private String login = null;

    /**
     * password per accedere al database
     */
    private String password = null;


    /**
     * Costruttore completo con connessione.
     * <p/>
     *
     * @param conn la connessione di riferimento
     */
    public ConnessioneDati(Connessione conn) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza con i parametri */
        this.setConnessione(conn);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo con database.
     * <p/>
     * Non ha una connessione di riferimento.<br>
     * Usato per costruire un oggetto allo scopo immagazzinare i dati
     * da usare come default per tutte le nuove connessioni create da
     * un database.<br>
     *
     * @param db il database di riferimento
     */
    public ConnessioneDati(Db db) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza con i parametri */
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

        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Ritorna l'indirizzo dell'host con il quale stabilire la connessione
     * <p/>
     *
     * @return l'indirizzo dell'host
     */
    public String getHost() {
        return host;
    }


    /**
     * Assegna l'indirizzo dell'host con il quale stabilire la connessione
     * <p/>
     *
     * @param host l'indirizzo dell'host
     * es. "192.168.168.1" o "localhost" o "mydbserver@mydomain.net"
     */
    public void setHost(String host) {
        this.host = host;
    }


    /**
     * Ritorna la porta di collegamento al database.
     * <p/>
     *
     * @return la porta di collegamento al database
     */
    public int getPorta() {
        return porta;
    }


    /**
     * Assegna la porta di collegamento al database.
     * <p/>
     *
     * @param porta la porta di collegamento al database
     */
    public void setPorta(int porta) {
        this.porta = porta;
    }


    /**
     * Ritorna il nome del database.
     * <p/>
     *
     * @return il nome del database
     */
    public String getNomeDatabase() {
        return nomeDatabase;
    }


    /**
     * Assegna il nome del database.
     * <p/>
     *
     * @param nomeDatabase il nome del database
     */
    public void setNomeDatabase(String nomeDatabase) {
        this.nomeDatabase = nomeDatabase;
    }


    /**
     * Ritorna il login con il quale effettuare la connessione.
     * <p/>
     *
     * @return il login per la connessione
     */
    public String getLogin() {
        return login;
    }


    /**
     * Assegna il login con il quale effettuare la connessione.
     * <p/>
     *
     * @param login il login per la connessione
     */
    public void setLogin(String login) {
        this.login = login;
    }


    /**
     * Ritorna la password con la quale effettuare la connessione.
     * <p/>
     *
     * @return la password per la connessione
     */
    public String getPassword() {
        return password;
    }


    /**
     * Assegna la password con la quale effettuare la connessione.
     * <p/>
     *
     * @param password la password per la connessione
     */
    public void setPassword(String password) {
        this.password = password;
    }


    private Connessione getConnessione() {
        return conn;
    }


    private void setConnessione(Connessione conn) {
        this.conn = conn;
    }


    /**
     * Ritorna il database di riferimento di questo oggetto dati.
     * <p/>
     * Se ha una Connessione, ritorna il db della connessione.<br>
     * Se non ha una Connessione, ritorna il db proprietario.
     */
    private Db getDb() {
        /* variabili e costanti locali di lavoro */
        Db database = null;
        Connessione conn = null;

        try { // prova ad eseguire il codice
            conn = this.getConnessione();
            if (conn != null) {
                database = conn.getDb();
            } else {
                database = this.db;
            }// fine del blocco if-else
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return database;
    }


    private void setDb(Db db) {
        this.db = db;
    }


    /**
     * Ritorna una copia profonda dell'oggetto (deep copy) col casting
     * Per fare una copia completa di questo oggetto occorre:
     * Prima copiare l'oggetto nel suo insieme, richiamando il metodo
     * sovrascritto che copia e regola tutte le variabili dell'oggetto con
     * gli stessi valori delle variabili originarie
     * Secondo copiare tutte le variabili che sono puntatori ad altri
     * oggetti, per evitare che nella copia ci sia il puntatore all'oggetto
     * originale (in genere tutti gli oggetti che vengono creati nella
     * classe col comando new)
     * Terzo in ogni sottoclasse occorre fare le copie dei puntatori
     * esistenti nelle sottoclassi stesse
     */
    public ConnessioneDati clona() {

        /* variabili e costanti locali di lavoro */
        ConnessioneDati conn = null;

        try {    // prova ad eseguire il codice

            conn = (ConnessioneDati)super.clone();

        } catch (CloneNotSupportedException unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
            throw new InternalError();
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return conn;

    } /* fine del metodo */

}// fine della classe
