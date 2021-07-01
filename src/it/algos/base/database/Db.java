/**
 * Title:     Db
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      8-ott-2004
 */
package it.algos.base.database;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.tipodati.TipoDati;
import it.algos.base.database.util.Funzione;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.Query;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Database generico
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 8-ott-2004 ore 17.11.30
 */
public interface Db {

    /**
     * codifica del tipo di database
     */
    public static final int SQL_STANDARD = 1;

    /**
     * codifica del tipo di database
     */
    public static final int SQL_MYSQL = 2;

    /**
     * codifica del tipo di database
     */
    public static final int SQL_POSTGRES = 3;

    /**
     * codifica del tipo di database
     */
    public static final int SQL_ORACLE = 4;

    /**
     * codifica del tipo di database
     */
    public static final int SQL_SYBASE = 5;

    /**
     * codifica del tipo di database
     */
    public static final int SQL_HSQLDB = 6;

    /**
     * codifica del tipo di database
     */
    public static final int MEMORIA = 7;

    /**
     * codifica del tipo di database
     */
    public static final int XML = 8;

    /**
     * modalita' di funzionamento del database - Client-Server
     */
    public static final int MODO_CLIENT_SERVER = 1;

    /**
     * modalita' di funzionamento del database - Stand alone
     */
    public static final int MODO_STAND_ALONE = 2;

    /**
     * Accesso ai dati su file
     * significativo solo per modalita' Stand Alone
     */
    public static final int ACCESSO_DATI_FILE = 1;

    /**
     * Accesso ai dati su risorsa
     * significativo solo per modalita' Stand Alone
     */
    public static final int ACCESSO_DATI_RISORSA = 2;

    /**
     * Accesso ai dati su memoria
     * significativo solo per modalita' Stand Alone
     */
    public static final int ACCESSO_DATI_MEMORIA = 3;


    /**
     * tavole di tipo MEMORY - lavora in memoria, scrive alla
     * chiusura del database, rilegge alla riapertura
     */
    public static final int TAVOLE_MEMORY = 1;

    /**
     * tavole di tipo CACHED - lavora con una cache su disco,
     * non deve portare tutto in memoria alla riapertura
     */
    public static final int TAVOLE_CACHED = 2;

    /**
     * tavole di tipo TEXT - come cached, ma usa qualsiasi
     * file CSV o delimitato
     */
    public static final int TAVOLE_TEXT = 3;

    /**
     * tavole di tipo TEMP - lavora esclusivamente in memoria,
     * i dati sopravvivono fino alla chiusura della connessione
     */
    public static final int TAVOLE_TEMP = 4;


    /**
     * stringa generica per l'unione di tipo AND
     */
    public static final String AND = " AND GENERICO ";

    /**
     * stringa generica per l'unione di tipo OR
     */
    public static final String OR = " OR GENERICO ";

    /**
     * stringa generica per l'unione di tipo AND NOT
     */
    public static final String AND_NOT = " AND NOT GENERICO ";

    /**
     * stringa generica per la parentesi aperta
     */
    public static final String PARENTESI_APERTA = "(";

    /**
     * stringa generica per la parentesi aperta
     */
    public static final String PARENTESI_CHIUSA = ")";

//-----------------------------------------------------------------------------
// Azioni di integrita' referenziale
//-----------------------------------------------------------------------------
    /* azioni da intraprendere sulle imported keys in caso di modifica
     * o cancellazione della foreign key per salvaguardare
     * l'integrita' referenziale */

//    /**
//     * non fa nulla sulle imported keys
//     */
//    int AZIONE_NO_ACTION = DatabaseMetaData.importedKeyNoAction;
//
//    /**
//     * elimina o modifica le imported keys
//     */
//    int AZIONE_CASCADE = DatabaseMetaData.importedKeyCascade;
//
//    /**
//     * assegna valore nullo alle imported keys
//     */
//    int AZIONE_SET_NULL = DatabaseMetaData.importedKeySetNull;
//
//    /**
//     * assegna il valore di default alle imported keys
//     */
//    int AZIONE_SET_DEFAULT = DatabaseMetaData.importedKeySetDefault;


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche. <br>
     * Puo' essere chiamato piu' volte.<br>
     * Se l'inizializzazione ha successo imposta il flag inizializzato a true.<br>
     * Il flag puo' essere successivamente modificato dalle sottoclassi se
     * non riescono a portare a termine la propria inizializzazione specifica.<br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @return true se l'inizializzazione ha avuto successo <br>
     */
    public abstract boolean inizializza();


    /**
     * Ritorna la funzione del Db corrispondente a una
     * data funzione generica.
     * <p/>
     *
     * @param codice il codice della funzione generica
     * (da interfaccia Funzione)
     *
     * @return l'oggetto Funzione
     */
    public abstract Funzione getFunzione(String codice);


    /**
     * Crea una nuova connessione a questo database.
     * <p/>
     *
     * @return la connessione appena creata
     */
    public Connessione creaConnessione();


    /**
     * Carica una selezione di record.
     * <p/>
     *
     * @param query informazioni per effettuare la selezione
     * @param conn la Connessione sulla quale effettuare la Query
     *
     * @return un oggetto dati
     */
    public abstract Dati querySelezione(Query query, Connessione conn);


    /**
     * Esegue la modifica di uno o piu' record.
     * <p/>
     * La modifica puo' consistere in inserimento di nuovi record
     * o modifica o cancellazione di record esistenti.
     *
     * @param query informazioni per effettuare la modifica
     * @param conn la Connessione sulla quale effettuare la Query
     *
     * @return il numero di record interessati, -1 se fallito
     */
    public abstract int queryModifica(Query query, Connessione conn);


    /**
     * Determina se un dato campo eisste sul database.
     * <p/>
     *
     * @param campo il campo da controllare
     * @param conn la connessione da utilizzare per il controllo
     *
     * @return true se il campo esiste
     */
    public abstract boolean isEsisteCampo(Campo campo, Connessione conn);


    public abstract int getTipoDb();


    public abstract void setTipoDb(int tipo);


    /**
     * Ritorna lo stato di accensione del motore di database
     * <p/>
     * Significativo solo per database stand-alone
     *
     * @return true se il motore di database e' acceso
     */
    public abstract boolean isMotoreAcceso();


    /**
     * Ritorna il nome della classe del driver
     * <p/>
     *
     * @return il nome della classe Driver
     */
    public abstract String getNomeClasseDriver();


    /**
     * Assegna una connessione interna di lavoro al database.
     * <p/>
     *
     * @param conn la Connessione da assegnare
     */
    public abstract void setConnessione(Connessione conn);


    /**
     * Recupera la connessione interna di lavoro del database.
     * <p/>
     *
     * @return la Connessione interna di lavoro
     */
    public abstract Connessione getConnessione();


    /**
     * Ritorna la collezione dei tipi dati gestiti da questo db
     * <p/>
     *
     * @return la collezione dei tipi dati
     *         chiave: da interfaccia TipoDati
     *         valore: oggetto TipoDati
     */
    public abstract HashMap getTipiDati();


    public abstract String getUnioneAnd();


    public abstract String getUnioneOr();


    public abstract String getUnioneAndNot();


    public abstract String getParentesiAperta();


    public abstract String getParentesiChiusa();


    /**
     * Ritorna una stringa che identifica un campo per il database.
     * <p/>
     *
     * @param campo il campo
     *
     * @return una stringa che identifica il campo per il Db
     */
    public abstract String getStringaCampo(Campo campo);


    /**
     * Ritorna una stringa che identifica un campo per il database.
     * <p/>
     * La stringa e' qualificata con il nome della tavola (tavola.campo)
     *
     * @param campo il campo
     *
     * @return una stringa qualificata che identifica il campo per il Db
     */
    public abstract String getStringaCampoQualificata(Campo campo);


    /**
     * Ritorna il tipo dati del Db in grado di gestire i valori
     * di un dato campo.
     * <p/>
     *
     * @param campo il campo per il quale cercare il corrispondente tipo dati
     *
     * @return il TipoDati del db che gestisce i valori del campo.
     */
    public abstract TipoDati getTipoDati(Campo campo);


    /**
     * Ritorna il tipo dati del Db in grado di gestire una
     * data classe di business logic.
     * <p/>
     *
     * @param classe la classe di business logic da gestire
     *
     * @return il TipoDati del db che gestisce la classe.
     */
    public abstract TipoDati getTipoDati(Class classe);


    /**
     * Ritorna il tipo dati del Db corrispondente a una data chiave.
     * <p/>
     *
     * @param chiave la chiave da cercare
     *
     * @return l'oggetto TipoDati del Db corrispondente
     */
    public abstract TipoDati getTipoDati(int chiave);


    /**
     * Ritorna true se un tipo dati e' di tipo testo.
     * <p/>
     * E' di tipo Testo se la classe di business logic e' String.
     *
     * @param chiave la chiave del tipo dati da cercare
     *
     * @return true se e' di tipo testo
     */
    public boolean isTipoTesto(int chiave);


    /**
     * Crea una tavola sul database.
     * <p/>
     * La tavola viene creata solo se non gia' esistente.
     *
     * @param modulo il modulo per il quale creare la tavola
     * @param conn la connessione da utilizzare
     *
     * @return true se esistente o creata correttamente
     *         false se non creata
     */
    public abstract boolean creaTavola(Modulo modulo, Connessione conn);


    /**
     * Allinea una colonna del Db in base al corrispondente
     * campo del Modello.
     * <p/>
     * Se la colonna non esiste, la crea e la allinea
     * Se la colonna esiste, la allinea soltanto
     *
     * @param campo il campo per il quale allineare la colonna
     * @param conn la connessione da utilizzare
     *
     * @return true se ha eseguito correttamente
     */
    public abstract boolean allineaCampo(Campo campo, Connessione conn);


    /**
     * Controlla il database e' disponibile all'apertura di connessioni.
     * <p/>
     * Prova ad aprire e chiudere una connessione.
     *
     * @return true se il database e' disponibile all'apertura di connessioni.
     */
    public abstract boolean isDisponibileConnessioni();


    /**
     * Chiude il database.
     * <p/>
     * Sovrascritto dalle sottoclassi.
     * Consolida i dati su disco, libera i lock sui file, esegue
     * altre eventuali finalizzazioni.
     *
     * @return true se riuscito
     */
    public abstract boolean shutdown();


    /**
     * Ritorna il modo di funzionamento del database
     * <p/>
     * Opzioni possibili: MODO_SERVER o MODO_STAND_ALONE
     *
     * @return il modo di funzionamento
     *
     * @see Db
     */
    public abstract int getModoFunzionamento();


    /**
     * Regola il modo di funzionamento del database
     * <p/>
     * Sovrascritto dalle sottoclassi
     * Opzioni possibili: MODO_SERVER o MODO_STAND_ALONE
     * Vedi costanti in Db
     */
    public abstract void setModoFunzionamento(int modoFunzionamento);


    /**
     * Regola il tipo di accesso ai dati
     * <p/>
     * Opzioni possibili: ACCESSO_DATI_FILE o ACCESSO_DATI_RISORSA<br>
     * Vedi costanti in Db<br>
     * Significativo solo per database stand-alone.<br>
     */
    public abstract void setTipoAccessoDati(int tipoAccessoDati);


    /**
     * Ritorna il tipo di tavole del database
     */
    public abstract int getTipoTavole();


    /**
     * Regola il tipo di tavole utilizzate dal database.
     * <p/>
     * Sovrascritto dalle sottoclassi. <br>
     * Opzioni possibili: TAVOLE_MEMORY, TAVOLE_CACHED o TAVOLE_TEXT. <br>
     * Significativo solo per database HSQLDB. <br>
     *
     * @see Db
     */
    public abstract void setTipoTavole(int tipoTavole);


    /**
     * Ritorna il separatore di campo per le tavole TEXT
     * <p/>
     *
     * @return la stringa del separatore
     */
    public abstract String getSepText();


    /**
     * Regola il separatore di campo per le tavole TEXT
     * <p/>
     *
     * @param sepText la stringa del separatore
     */
    public abstract void setSepText(String sepText);


    /**
     * Regola il percorso dove il database registra i dati.
     * <p/>
     */
    public abstract void setPercorsoDati(String percorsoDati);


    public abstract void setHost(String host);


    public abstract void setPorta(int porta);


    /**
     * Assegna il nome del database.
     * <p/>
     *
     * @param nome il nome del database
     */
    public abstract void setNomeDatabase(String nome);


    /**
     * Recupera il nome del database.
     * <p/>
     *
     * @return il nome del database
     */
    public abstract String getNomeDatabase();


    /**
     * Assegna la login per l'accesso al db
     * <p/>
     */
    public abstract void setLogin(String login);


    /**
     * Assegna la password per l'accesso al db
     * <p/>
     */
    public abstract void setPassword(String password);


    /**
     * Ritorna il percorso dati di default del database.
     * <p/>
     *
     * @return il percorso dati di default del database
     */
    public abstract String getPercorsoDatiDefault();


    /**
     * Ritorna la porta di default del database.
     * <p/>
     *
     * @return la porta di default del database
     */
    public abstract int getPortaDefault();


    /**
     * Ritorna il nome di default del database.
     * <p/>
     *
     * @return il nome di default del database
     */
    public abstract String getNomeDbDefault();


    /**
     * Ritorna il login di default del database.
     * <p/>
     *
     * @return il login di default del database
     */
    public abstract String getLoginDefault();


    /**
     * Ritorna la password di default del database.
     * <p/>
     *
     * @return la password di default del database
     */
    public abstract String getPasswordDefault();


    /**
     * Ritorna l'elenco dei nomi di tutti i campi di una tavola del database.
     * <p/>
     *
     * @param tavola il nome della tavola
     * @param conn la connessione da utilizzare per l'interrogazione
     *
     * @return una lista di oggetti String contenente i nomi non qualificati
     *         di tutti i campi della tavola.
     */
    public abstract ArrayList<String> getCampiTavola(String tavola, Connessione conn);


    /**
     * Crea un oggetto Campo da una colonna di una tavola.
     * <p/>
     * Il nome interno del campo e' uguale al nome della colonna
     * Il tipo Memoria del campo corrisponde al tipo Archivio della colonna
     *
     * @param modulo del campo da creare
     * @param tavola il nome della tavola
     * @param colonna il nome della colonna
     * @param conn la connessione da utilizzare per l'interrogazione
     *
     * @return il campo creato.
     */
    public abstract Campo creaCampoColonna(Modulo modulo,
                                           String tavola,
                                           String colonna,
                                           Connessione conn);


    /**
     * Elimina una colonna dal database.
     * <p/>
     *
     * @param campo il campo corrispondente alla colonna da eliminare
     * @param conn la connessione da utilizzare per l'operazione
     *
     * @return true se riuscito.
     */
    public abstract boolean eliminaColonna(Campo campo, Connessione conn);


    /**
     * Attiva o disattiva l'integrità referenziale.
     * <p/>
     *
     * @param flag di attivazione
     *
     * @return true se riuscito
     */
    public abstract boolean setReferentialIntegrity(boolean flag, Connessione conn);


    /**
     * Controlla se l'oggetto e' inizializzato.
     * <p/>
     *
     * @return true se inizializzato
     */
    public abstract boolean isInizializzato();


    /**
     * Tipi di operazioni sui record.
     */
    public enum TipoOp {

        nuovo(),
        modifica(),
        elimina()

    }// fine della classe


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Azioni di controllo integrità referenziale <br>
     */
    public enum Azione {

        /**
         * non fa nulla sulle imported keys
         */
        noAction(DatabaseMetaData.importedKeyNoAction),
        /**
         * elimina o modifica le imported keys
         */
        cascade(DatabaseMetaData.importedKeyCascade),
        /**
         * assegna valore nullo alle imported keys
         */
        setNull(DatabaseMetaData.importedKeySetNull),
        /**
         * assegna il valore di default alle imported keys
         */
        setDefault(DatabaseMetaData.importedKeySetDefault),;


        /**
         * codice interno java
         */
        private int codice;


        /**
         * Costruttore completo con parametri.
         *
         * @param codice interno java
         */
        Azione(int codice) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setCodice(codice);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public static Azione get(int cod) {
            /* variabili e costanti locali di lavoro */
            Azione azione = null;

            try { // prova ad eseguire il codice
                /* traverso tutta la collezione */
                for (Azione az : Azione.values()) {
                    if (az.getCodice() == cod) {
                        azione = az;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return azione;
        }


        /**
         * Lista di nomi per creazione popup.
         */
        public static String getLista() {
            /* variabili e costanti locali di lavoro */
            String lista = "";
            String tag = ",";

            try { // prova ad eseguire il codice
                /* traverso tutta la collezione */
                for (Azione az : Azione.values()) {
                    lista += az;
                    lista += tag;
                } // fine del ciclo for-each

                lista = Lib.Testo.levaCoda(lista, tag);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        public int getCodice() {
            return codice;
        }


        public void setCodice(int codice) {
            this.codice = codice;
        }
    }// fine della classe

}// fine della interfaccia
