/**
 * Title:     InterpreteStructSql
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      8-nov-2004
 */
package it.algos.base.database.sql.interprete;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoBase;
import it.algos.base.database.Db;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.connessione.ConnessioneJDBC;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.sql.DbSql;
import it.algos.base.database.sql.tipodati.TipoDatiSql;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibResultSet;
import it.algos.base.libreria.Libreria;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.pref.Pref;
import it.algos.base.query.Query;
import it.algos.base.query.QueryFactory;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.selezione.QuerySelezione;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Interprete per la manipolazione della struttura di un
 * database Sql generico.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 8-nov-2004 ore 8.45.03
 */
public abstract class InterpreteStructSqlBase extends InterpreteSqlBase implements
        InterpreteStructSql {

    /**
     * Costruttore completo.<br>
     *
     * @param dbSql il database Sql proprietario
     */
    public InterpreteStructSqlBase(DbSql dbSql) {
        /** rimanda al costruttore della superclasse */
        super(dbSql);
    } /* fine del metodo costruttore completo */

//    /**
//     * Crea una tavola di un modulo sul database.
//     * <p/>
//     * La tavola viene creata solo se non e' gia' esistente.
//     *
//     * @param modulo il modulo per il quale creare la tavola
//     *
//     * @return true se gia' esistente o creata correttamente
//     *         false se ha tentato di crearla ma non e' riuscito.
//     */
//    public boolean creaTavola(Modulo modulo) {
//        /* variabili e costanti locali di lavoro */
//        boolean eseguito = true;
//        String tavola = null;
//        Connessione conn = null;
//        Campo campoChiave = null;
//
//        try {	// prova ad eseguire il codice
//
//            tavola = this.getDatabaseSql().getStringaTavola(modulo);
//            conn = modulo.getConnessione();
//
//            /* assegna la connessione al db */
//            this.getDatabase().setConnessione(conn);
//
//            /* controlla se la tavola e' gia' esistente
//             * se non esiste, la crea con il campo chiave non regolato */
//            if (!this.isEsisteTavola(tavola, conn)) {
//                eseguito = this.creaTavola(modulo, conn);
//
//                /* regola il campo chiave creato con la tavola */
//                if (eseguito) {
//                    campoChiave = modulo.getCampoChiave();
//                    eseguito = this.regolaColonna(campoChiave);
//                }// fine del blocco if
//
//            }// fine del blocco if
//
//        } catch (Exception unErrore) {	// intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return eseguito;
//    }


    /**
     * Crea una tavola sul database.
     * <p/>
     * Viene aggiunto il campo chiave, per ora non regolato.
     *
     * @param modulo di riferimento
     * @param conn la connessione da utilizzare
     *
     * @return true se gia' esistente o creata correttamente
     *         false se ha tentato di crearla ma non e' riuscito.
     */
    public boolean creaTavola(Modulo modulo, Connessione conn) {

        /* variabili e costanti locali di lavoro */
        boolean eseguito = true;
        boolean continua;
        int risultato;
        String tavola;
        String stringa;
        Campo campoChiave;


        try { // prova ad eseguire il codice

            tavola = this.getDatabaseSql().getStringaTavola(modulo);
            continua = !this.isEsisteTavola(tavola, conn);

            if (continua) {

                /*
                * costruisce la stringa per creare la tavola
                * usando il campo chiave
                * (CREATE TABLE richiede obbligatoriamente almeno un campo)
                * Le ulteriori caratteristiche del campo chiave
                * verranno regolate successivamente, assieme agli altri campi.
                */
                stringa = this.stringaCreaTavolaIniziale(modulo);

                /* invia il comando al database e recupera il risultato */
                risultato = this.getDatabaseSql().esegueUpdate(stringa, conn);

                /* contolla il risultato */
                eseguito = false;
                if (risultato != -1) {
                    /* regola il valore di ritorno */
                    eseguito = true;
                } /* fine del blocco if */

                /* regola il campo chiave creato con la tavola */
                if (eseguito) {
                    campoChiave = modulo.getCampoChiave();
                    eseguito = this.regolaColonna(campoChiave);
                }// fine del blocco if

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return (eseguito);
    } /* fine del metodo */


    /**
     * Restituisce la stringa per la creazione iniziale della tavola
     * di un modulo, con il campo chiave.
     * <p/>
     * La tavola comprende il campo chiave, del tipo corretto.
     * Le caratteristiche del campo chiave non sono regolate.
     * Sovrascritto dalle sottoclassi.
     *
     * @param modulo il modulo per il quale creare la stringa
     *
     * @return la stringa Sql per la creazione della tavola
     */
    protected String stringaCreaTavolaIniziale(Modulo modulo) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        Modello modello;
        Campo campoChiave;
        String tavola;
        String nomeCampo;
        TipoDatiSql tipoDati;
        String keyword;
        boolean temporanea;

        try {    // prova ad eseguire il codice

            /*
             * recupera il campo chiave del Modello
             * recupera nome tavola dal Db
             * recupera il tipo dati Archivio del campo chiave
             * recupera la keyword sql per il tipo
             */
            modello = modulo.getModello();
            campoChiave = modello.getCampoChiave();
            tavola = this.getDatabaseSql().getStringaTavola(modulo);
            nomeCampo = this.getDatabaseSql().getStringaCampo(campoChiave);
            tipoDati = (TipoDatiSql)this.getDatabaseSql().getTipoDati(campoChiave);
            keyword = tipoDati.getKeyword();

            /* costruisce la stringa */
            temporanea = modello.isUsaTavolaTemporanea();
            if (temporanea) {
                stringa += this.getDatabaseSql().getCreateTempTable();
            } else {
                stringa += this.getDatabaseSql().getCreateTable();
            }// fine del blocco if-else

            stringa += tavola;
            stringa += " ";
            stringa += this.getDatabaseSql().getParentesiAperta();
            stringa += nomeCampo;
            stringa += keyword;
            stringa += this.getDatabaseSql().getParentesiChiusa();

            /* aggiunge le opzioni tavola */
            stringa += " ";
            stringa += this.getStringaOpzioniTavola(modulo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna la stringa di opzioni creazine tavola.
     * <p/>
     *
     * @param modulo proprietario della tavola
     *
     * @return la stringa di opzioni
     */
    protected String getStringaOpzioniTavola(Modulo modulo) {
        return "";
    }


    /**
     * Elimina una tavola dal database.
     * <p/>
     *
     * @param tavola il nome della tavola
     * @param conn la connessione da utilizzare
     *
     * @return true se riuscito
     */
    public boolean eliminaTavola(String tavola, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;
        String stringa = null;
        int risultato = 0;

        try {    // prova ad eseguire il codice

            /* costruisce la stringa per eliminare la tavola */
            stringa = this.stringaEliminaTavola(tavola);

            /* invia il comando al database e recupera il risultato */
            risultato = this.getDatabaseSql().esegueUpdate(stringa, conn);

            /* contolla il risultato e regola il ritorno */
            eseguito = false;
            if (risultato != -1) {
                eseguito = true;
            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;
    }


    /**
     * Ritorna la stringa sql per eliminare una tavola
     * per questo database.
     * <p/>
     *
     * @param tavola il nome della tavola
     *
     * @return la stringa sql per eliminare la tavola
     */
    protected String stringaEliminaTavola(String tavola) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";

        try {    // prova ad eseguire il codice
            stringa += this.getDatabaseSql().getDropTable();
            stringa += fixCase(tavola);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Allinea una colonna del Db in base al corrispondente
     * campo del Modello.
     * <p/>
     * - se la colonna non esiste, la crea e la regola
     * - se esiste, la regola soltanto
     *
     * @param campo il campo per il quale allineare la colonna
     * @param conn la connessione da utilizzare
     *
     * @return true se ha eseguito correttamente
     */
    public boolean allineaCampo(Campo campo, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = true;

        try {    // prova ad eseguire il codice

            /* assegna al db la connessione da utilizzare */
            this.getDatabase().setConnessione(conn);

            if (!this.isEsisteCampo(campo, conn)) {

                /* il campo non esiste, lo crea e lo regola */
                eseguito = this.creaColonna(campo, conn);  // crea e regola
                if (eseguito) {
                    eseguito = this.regolaColonna(campo);
                }// fine del blocco if

            } else {

                /* Il campo esiste. Se la preferenza lo prevede, lo regola */
                if (Pref.DB.allinea.is()) {
                    eseguito = regolaColonna(campo);  // regola
                }// fine del blocco if

            }// fine del blocco if-else
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;
    } // fine del metodo


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
    public ArrayList getCampiTavola(String tavola, Connessione conn) {

        /* variabili e costanti locali di lavoro */
        ArrayList<String> listaCampi = null;
        ArrayList infoColonne;
        ArrayList infoColonna;
        String nomeColonna;

        try { // prova ad eseguire il codice

            /* assegna al db la connessione da utilizzare */
            this.getDatabase().setConnessione(conn);

            /* recupera le informazioni sulle colonne */
            infoColonne = this.getInfoColonne(tavola);

            listaCampi = new ArrayList<String>();

            /* spazzola le informazioni sulle colonne e costuisce
             * la lista dei nomi di colonna */
            for (int k = 0; k < infoColonne.size(); k++) {
                infoColonna = (ArrayList)infoColonne.get(k);
                nomeColonna = (String)this.getCaratteristica(infoColonna, DbSql.INFO_NOME_CAMPO);
                listaCampi.add(nomeColonna.toLowerCase());
            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaCampi;
    }


    /**
     * Crea un oggetto Campo da una colonna di una tavola.
     * todo bisogna assegnare un campo dati corretto!
     * todo di default crea un campo dati di tipo testo!!
     * <p/>
     * Il nome interno del campo e' uguale al nome della colonna
     * Il tipo dati del campo corrisponde al tipo JDBC della colonna
     *
     * @param modulo del campo da creare
     * @param tavola il nome della tavola
     * @param colonna il nome della colonna
     * @param conn la connessione da utilizzare per l'interrogazione
     *
     * @return il campo creato.
     */
    public Campo creaCampoColonna(Modulo modulo, String tavola, String colonna, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        ArrayList infoColonna = null;
        Object valore = null;
        int tipoJDBC = 0;
        int chiaveTd = 0;
        String t = null;

        try { // prova ad eseguire il codice

            /* assegna al db la connessione da utilizzare */
            this.getDatabase().setConnessione(conn);

            /* crea un nuovo oggetto Campo di default */
            campo = new CampoBase(colonna);
            campo.setModulo(modulo);

            /* recupera le informazioni sulla colonna */
            infoColonna = this.getInfoColonna(tavola, colonna);

            /* recupera il tipo JDBC della colonna */
            valore = this.getCaratteristica(infoColonna, DbSql.INFO_TIPO_JDBC);
            tipoJDBC = Libreria.getInt(valore);

            /* recupera dal database la chiave per il tipo dati corrispondente */
            chiaveTd = this.getDatabaseSql().getChiaveTipoDatiSql(tipoJDBC);

            /* recupera il tipo dati del db corrispondente al tipo JDBC */
//            tipoDati = this.getDatabaseSql().getTipoDati(tipoJDBC);

            /* controlla se ha trovato un tipo dati corrispondente */
            if (chiaveTd == 0) {
                t = "Non trovato un tipo dati in grado di gestire il tipo JDBC.\n";
                t += "Campo: " + tavola + "." + colonna + "\n";
                t += "Tipo Jdbc: " + tipoJDBC;
                throw new Exception(t);
            }// fine del blocco if

            /* recupera la chiave del tipo dati */
//            int chiaveTipoDati = tipoDati.get
            // todo - scrivere un metodo in dbBase che mi restituisca la chiave di un tipo dati
            // todo - dato il tipo JDBC
            // todo - poi nel campo registrare la chiave
            // todo - il tipo memoria non serve piu'

            /* recupera la classe di business logic gestita dal tipo dati */
//            classe = tipoDati.getClasseBl();

            /* recupera il tipo memoria in grado di gestire questa classe */
//            tm = (TipoMemoria)Progetto.getIstanza().getTipiMemoria().get(classe);

            // todo usare un tipo archivio che contenga il tipo dati corretto!
            /* assegna la chiave del tipo dati al campo */
//            campo.getCampoDati().setTipoDatiDb(chiaveTd);

            /* inizializza il campo */
            campo.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;

    }


    /**
     * Elimina una colonna dal database.
     * <p/>
     *
     * @param campo il campo corrispondente alla colonna da eliminare
     * @param conn la connessione da utilizzare per l'operazione
     *
     * @return true se riuscito.
     */
    public boolean eliminaColonna(Campo campo, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;
        String tavola = null;
        String colonna = null;

        try { // prova ad eseguire il codice

            /* assegna al db la connessione da utilizzare */
            this.getDatabase().setConnessione(conn);

            /* recupera tavola e colonna */
            tavola = this.getDatabaseSql().getStringaTavola(campo.getModulo());
            colonna = this.getDatabase().getStringaCampo(campo);

            /* invoca il metodo delegato */
            eseguito = this.eliminaColonna(tavola, colonna);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;
    }


    /**
     * Attiva o disattiva l'integrità referenziale.
     * <p/>
     *
     * @param flag di attivazione
     * @param conn connessione da utilizzare
     *
     * @return true se riuscito
     */
    public boolean setReferentialIntegrity(boolean flag, Connessione conn) {
        return false;
    }


    /**
     * Controlla se una tavola esiste sul database.
     * <p/>
     * La ricerca della tavola e' case-sensitive.
     *
     * @param nomeTavola il nome della tavola da cercare
     * @param conn la connessione da utilizzare
     *
     * @return true se la tavola esiste, altrimenti false
     */
    public boolean isEsisteTavola(String nomeTavola, Connessione conn) {

        /** variabili locale di lavoro */
        boolean esiste = false;
        ResultSet rs = null;
        String[] tipoOggetti = {"TABLE"};   // considera solo oggetti di tipo table
        DatabaseMetaData md;

        try {                                   // prova ad eseguire il codice

            /* converte nel case appropriato */
            nomeTavola = fixCase(nomeTavola);

            /* recupera un resultset contenente la tavola richiesta */
            md = this.getMetaData(conn);
            rs = md.getTables(null, null, nomeTavola, tipoOggetti);

            /* se il resultset contiene una riga, allora la tavola esiste */
            if (LibResultSet.quanteRighe(rs) == 1) {
                esiste = true;
            } /* fine del blocco if */

        } catch (Exception unErrore) {           // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return esiste;
    } /* fine del metodo */

//    /**
//     * Determina se un dato campo esiste sul database.
//     * <p/>
//     *
//     * @param campo il campo da controllare
//     * @param conn  la connessione da utilizzare per il controllo
//     *
//     * @return true se il campo esiste
//     */
//    public boolean isEsisteCampo(Campo campo, Connessione conn) {
//
//        /* assegna al db la connessione da utilizzare */
//        this.getDatabase().setConnessione(conn);
//
//        /* valore di ritorno */
//        return this.esisteCampo(campo);
//
//    } // fine del metodo


    /**
     * Determina se un campo esiste sul database.
     * <p/>
     *
     * @param campo il campo da controllare
     * @param conn la connessione da utilizzare
     *
     * @return true se il campo esiste
     */
    public boolean isEsisteCampo(Campo campo, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        String nomeSqlTavola = null;
        String nomeSqlCampo = null;

        try {    // prova ad eseguire il codice
            /* recupera nomi sql di tavola e campo */
            nomeSqlTavola = this.getDatabaseSql().getStringaTavola(campo);
            nomeSqlCampo = this.getDatabaseSql().getStringaCampo(campo);
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return this.isEsisteColonna(nomeSqlTavola, nomeSqlCampo, conn);
    } // fine del metodo


    /**
     * Determina se una colonna esiste sul database.
     * <p/>
     * la ricerca della tavola e del campo sono case-sensitive
     *
     * @param tavola il nome della tavola
     * @param colonna il nome della colonna
     * @param conn la connessione da utilizzare
     *
     * @return true se la colonna esiste
     */
    public boolean isEsisteColonna(String tavola, String colonna, Connessione conn) {

        /** variabili locali di lavoro */
        boolean esiste = false;
        ResultSet rs = null;
        DatabaseMetaData md = null;

        try {   // prova ad eseguire il codice

            /* converte gli identificatori nel case appropriato */
            tavola = fixCase(tavola);
            colonna = fixCase(colonna);

            /* se manca la connessione usa quella di default del db */
            if (conn == null) {
                conn = this.getDatabaseSql().getConnessioneJDBC();
            }// fine del blocco if

            /* recupera i metadati */
            md = this.getMetaData(conn);

            /*
             * recupera un resultset contenente la colonna richiesta
             * (se esiste)
             */
            rs = md.getColumns(null, null, tavola, colonna);

            /* se il resultset contiene una riga, allora la colonna esiste */
            if (LibResultSet.quanteRighe(rs) == 1) {
                esiste = true;
            } /* fine del blocco if */

        } catch (Exception unErrore) {           // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return esiste;
    } /* fine del metodo */


    /**
     * Crea una colonna del database in base a un campo.
     * <p/>
     * Crea la colonna del tipo appropriato al campo.
     * Non effettua altre regolazioni (caratteristiche, indici ecc...)
     *
     * @param campo il campo per il quale creare la colonna
     * @param conn la connessione da utilizzare
     *
     * @return true se la colonna e' stata creata correttamente
     */
    public boolean creaColonna(Campo campo, Connessione conn) {

        /* variabili e costanti locali di lavoro */
        String nomeTavola = null;
        String nomeCampo = null;
        TipoDatiSql tipoDati = null;
        String stringaTipo = null;
        boolean eseguito = true;

        try {    // prova ad eseguire il codice

            /* recupera i dati dal campo */
            nomeTavola = this.getDatabaseSql().getStringaTavola(campo);
            nomeCampo = this.getDatabaseSql().getStringaCampo(campo);
            tipoDati = (TipoDatiSql)this.getDatabaseSql().getTipoDati(campo);
            stringaTipo = tipoDati.stringaSqlTipo();

            /* crea la colonna */
            eseguito = this.creaColonna(nomeTavola, nomeCampo, stringaTipo);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /** valore di ritorno */
        return eseguito;
    } /* fine del metodo creaCampiArchivio */


    /**
     * Crea una colonna sul database.
     * <p/>
     *
     * @param tavola il nome sql della tavola
     * @param colonna il nome sql della colonna da creare
     * @param keyword la keyword Sql per il tipo di colonna
     *
     * @return true se creata correttamente
     */
    private boolean creaColonna(String tavola, String colonna, String keyword) {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;
        String stringa = "";
        Connessione conn = null;
        int risultato = 0;

        try {    // prova ad eseguire il codice

            /* recupera la connessione */
            conn = this.getDatabaseSql().getConnessioneJDBC();

            /* crea la colonna */
            stringa += this.getDatabaseSql().getAlterTable();
            stringa += fixCase(tavola);
            stringa += this.getDatabaseSql().getAddColumn();
            stringa += fixCase(colonna);
            stringa += keyword;

            /* invia il comando al database e recupera il risultato */
            risultato = this.getDatabaseSql().esegueUpdate(stringa, conn);

            /* contolla il risultato */
            if (risultato != -1) {
                /* regola il valore di ritorno */
                eseguito = true;
            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;
    }


    /**
     * Elimina una colonna dal database.
     * <p/>
     * Elimina anche tutti i riferimenti
     * alla colonna esistenti all'esterno della tavola.<br>
     * (indici, constraints, views etc.)
     *
     * @param nomeTavola il nome della tavola
     * @param nomeColonna il nome della colonna da eliminare
     *
     * @return true se la colonna e' stata eliminata correttamente
     */
    private boolean eliminaColonna(String nomeTavola, String nomeColonna) {
        /** variabili locali di lavoro*/
        boolean eseguito = true;
        Connessione conn = null;
        String stringa = "";
        int risultato = 0;
        ArrayList lista;
        String nomeFKey;

        try { // prova ad eseguire il codice

            /* prima di tutto elimina l'eventuale foreign key sulla colonna */
            lista = this.getInfoFKey(nomeTavola, nomeColonna);
            if (lista != null) {
                if (lista.size() > 0) {
                    nomeFKey = (String)this.getCaratteristica(lista, DbSql.INFO_FKEY_NOME_FKEY);
                    this.eliminaForeignKey(nomeTavola, nomeFKey);
                }// fine del blocco if
            }// fine del blocco if

            /* recupera la connessione */
            conn = this.getDatabaseSql().getConnessioneJDBC();

            /* costruisce la stringa Sql */
            stringa = this.stringaEliminaColonna(nomeTavola, nomeColonna);

            /* invia il comando al database e recupera il risultato */
            risultato = this.getDatabaseSql().esegueUpdate(stringa, conn);

            /** controlla il risultato */
            if (risultato == -1) {
                eseguito = false;
            } /* fine del blocco if */

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return eseguito;
    } /* fine del metodo */


    /**
     * Restituisce il comando Sql per eliminare una colonna.
     * <p/>
     *
     * @param tavola il nome della tavola
     * @param colonna il nome della colonna da eliminare
     *
     * @return il comando Sql per l'eliminazione della colonna
     */
    protected String stringaEliminaColonna(String tavola, String colonna) {
        /* variabili e costanti locali di lavoro */
        String cmd = "";

        try {    // prova ad eseguire il codice

            tavola = fixCase(tavola);
            colonna = fixCase(colonna);

            cmd = this.getDatabaseSql().getAlterTable();
            cmd += tavola;
            cmd += this.getDatabaseSql().getDrop();
            cmd += colonna;

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cmd;
    }


    /**
     * Regola le caratteristiche di una colonna del DB.
     * <p/>
     * Le caratteristiche vengono regolate in base al Campo.<br>
     * Prima di effettuare ogni regolazione controlla se
     * la colonna e' gia' allineata, intal caso non effettua
     * la regolazione.<br>
     *
     * @param campo il campo in base al quale regolare la colonna
     *
     * @return true se la colonna risulta gia' allineata
     *         o e' stata regolata correttamente.
     */
    protected boolean regolaColonna(Campo campo) {

        /* variabili e costanti locali di lavoro */
        boolean eseguito = true;
        Modello modello;
        boolean usaIntegrita;

        try { // prova ad eseguire il codice

//            /* determina modello usa l'integrità referenziale;
//             * se non la usa non crea le foreign keys. */
//            modello = campo.getModulo().getModello();
//            usaIntegrita = modello.isUsaIntegritaReferenziale();

            /* Regola la caratteristica Tipo */
            eseguito = this.regolaTipo(campo);

            /* Regola la caratteristica Not Null */
            if (eseguito) {
                eseguito = this.regolaNotNull(campo);
            } /* fine del blocco if */

            /* Regola la caratteristica Primary Key */
            if (eseguito) {
                eseguito = this.regolaPrimaryKey(campo);
            } /* fine del blocco if */

            /* Regola la caratteristica Indice */
            if (eseguito) {
                eseguito = this.regolaIndice(campo);
            } /* fine del blocco if */

            /* Regola la caratteristica Foreign Key */
            if (eseguito) {
                eseguito = this.regolaForeignKey(campo);
            } /* fine del blocco if */


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /** valore di ritorno */
        return eseguito;
    } /* fine del metodo */


    /**
     * Regola la caratteristica Tipo.
     * <p/>
     * Sovrascrive il metodo della superclasse.
     *
     * @param campo il campo di riferimento
     *
     * @return true se la caratteristica risulta gia' allineata
     *         o e' stata regolata correttamente
     */
    private boolean regolaTipo(Campo campo) {
        /* variabili locali di lavoro*/
        boolean eseguito = true;
        String tavola = "";
        String colonna = "";
        String keyword = "";
        Object o = null;
        int tipoJdbcCampo = 0;
        int tipoJdbcDb = 0;

        try { // prova ad eseguire il codice

            /* recupera il tipo Jdbc dal campo */
            tipoJdbcCampo = this.getDatabaseSql().getTipoJdbc(campo);

            /* recupera il tipo Jdbc dalla colonna */
            o = this.getInfoColonna(campo, DbSql.INFO_TIPO_JDBC);
            tipoJdbcDb = Libreria.getInt(o);

            /* se sono diversi, cambia il tipo sul Db */
            if (tipoJdbcCampo != tipoJdbcDb) {

                /* recupera i dati dal campo */
                tavola = this.getDatabaseSql().getStringaTavola(campo);
                colonna = this.getDatabaseSql().getStringaCampo(campo);
                keyword = this.getDatabaseSql().getKeywordSql(campo);

                /* esegue la modifica */
                eseguito = this.modificaTipoColonna(tavola, colonna, keyword);

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;
    } /* fine del metodo */


    /**
     * Regola la caratteristica Not Null.
     * <p/>
     *
     * @param campo il campo di riferimento
     *
     * @return true se la caratteristica risulta gia' allineata
     *         o e' stata regolata correttamente
     */
    private boolean regolaNotNull(Campo campo) {

        /* variabili e costanti locali di lavoro */
        boolean eseguito = true;
        boolean accettaNullCampo;
        boolean accettaNullDB = false;
        boolean allineato = false;
        String risposta;
        boolean assegna;

        /* recupera la caratteristica dal campo */
        accettaNullCampo = campo.getCampoDB().isAccettaValoreNullo();

        /* recupera la caratteristica dal campo del DB */
        risposta = getInfoColonna(campo, DbSql.INFO_NULLABLE).toString();

        /* interpreta la risposta */
        if (risposta.equalsIgnoreCase("YES")) {
            accettaNullDB = true;
        } /* fine del blocco if */

        /* verifica se le caratteristiche sono allineate */
        if (accettaNullDB == accettaNullCampo) {
            allineato = true;
        } /* fine del blocco if */

        /* se le carateristiche del campo non sono allineate con la colonna,
         * allinea la colonna campo sul DB */
        if (!allineato) {

            /* determina se deve assegnare o rimuovere la caratteristica */
            if (!accettaNullCampo) {
                assegna = true;
            } else {
                assegna = false;
            } /* fine del blocco if */

            /* esegue la regolazione
             * opera nella classe specifica */
            eseguito = this.regolaNotNull(campo, assegna);

        } /* fine del blocco if */

        /** valore di ritorno */
        return eseguito;
    } /* fine del metodo */


    /**
     * Regola la caratteristica Primary Key
     * <p/>
     *
     * @param campo il campo per il quale regolare la caratteristica
     *
     * @return true se la caratteristica risulta gia' allineata
     *         o e' stata regolata correttamente
     */
    private boolean regolaPrimaryKey(Campo campo) {

        /** variabili e costanti locali di lavoro */
        boolean eseguito = true;
        boolean isPkeyCampo;
        ArrayList risposta;
        String tavola;
        boolean esistePkeyDb = false;
        String campoPkeyDB;
        ArrayList unaInfoPkey;
        String nomeCampo;
        boolean allineato = false;
        String t;

        /* recupera la caratteristica dal campo */
        isPkeyCampo = campo.getCampoDB().isChiavePrimaria();

        /* recupera la caratteristica dal DB */
        tavola = this.getDatabaseSql().getStringaTavola(campo);
        risposta = this.getInfoPrimaryKey(tavola);

        /* controllo se esiste una pkey per la tavola sul DB */
        if (risposta.size() > 0) {
            esistePkeyDb = true;
        } /* fine del blocco if */

        /* determino se la sitazione e' alllineata tra Modello e DB */
        if (esistePkeyDb) {

            /* recupera i dati del primo campo della pkey
             * (attualmente non supportiamo primary keys basate su piu' di un campo)*/
            unaInfoPkey = (ArrayList)risposta.get(0);
            campoPkeyDB = this.getCaratteristica(unaInfoPkey, DbSql.INFO_PKEY_CAMPO)
                    .toString();

            /* Sul DB esiste una pkey.
             * Se questo campo e' chiave primaria, deve essere uguale al
             * campo della pkey, se no va allineato.
             * Se questo campo non e' chiave primaria, deve essere diverso
             * dal campo della pkey, se no va allineato.*/
            nomeCampo = this.getDatabaseSql().getStringaCampo(campo);
            if (isPkeyCampo) {
                if (campoPkeyDB.equalsIgnoreCase(nomeCampo)) {
                    allineato = true;
                } /* fine del blocco if */
            } else {
                if (!campoPkeyDB.equalsIgnoreCase(nomeCampo)) {
                    allineato = true;
                } /* fine del blocco if */
            } /* fine del blocco if-else */

        } else {    // non esiste una pkey sul DB
            /* se non esiste una pkey sul DB e questo campo non e' chiave,
             * allora la situazione e' allineata */
            if (!isPkeyCampo) {
                allineato = true;
            } /* fine del blocco if */
        } /* fine del blocco if */

        /* se non sono allineate, allinea il campo sul DB */
        if (!allineato) {

            /*
             * elimina la eventuale pkey della tavola.
             * se non riesce, avvisa ma procede comunque.
             */
            if (esistePkeyDb) {
                if (!this.eliminaPrimaryKey(tavola)) {
                    t = "Impossibile eliminare la primary key dalla tavola ";
                    t += tavola;
                    new MessaggioAvviso(t);
                } /* fine del blocco if */
            } /* fine del blocco if */

            /*
             * se il campo lo prevede,
             * crea una pkey basata sul campo
             */
            if (isPkeyCampo) {
                if (!this.creaPrimaryKey(campo)) {
                    eseguito = false;
                } /* fine del blocco if */
            } /* fine del blocco if */

        } /* fine del blocco if */

        /** valore di ritorno */
        return eseguito;
    } /* fine del metodo */


    /**
     * Crea una constraint di tipo Primary Key su una tavola
     *
     * @param campo il campo sul quale creare la primary key
     *
     * @return true se la primary key e' stata creata correttamente
     */
    protected boolean creaPrimaryKey(Campo campo) {

        /** variabili locali di lavoro*/
        boolean eseguito = false;
        String nomeTavola = null;
        String nomeCampo = null;
        String nomeConstraint = null;
        String cmd = null;

        /* estrae i dati dal campo */
        nomeTavola = this.getDatabaseSql().getStringaTavola(campo);
        nomeCampo = this.getDatabaseSql().getStringaCampo(campo);

        /* costruisce il nome per la constraint (unica sulla tavola)*/
        nomeConstraint = nomeTavola + DbSql.SUFFISSO_DEFAULT_PKEY;

        /* costruisce il comando per creare la constraint */
        cmd = this.getDatabaseSql().getPrimaryKey();
        cmd += this.getDatabaseSql().getParentesiAperta();
        cmd += nomeCampo;
        cmd += this.getDatabaseSql().getParentesiChiusa();

        /* crea la constraint */
        if (this.creaConstraint(nomeTavola, nomeConstraint, cmd)) {
            eseguito = true;
        } /* fine del blocco if */

        return eseguito;
    } /* fine del metodo */


    /**
     * Regola la caratteristica Indice.
     * <p/>
     *
     * @param campo il campo per il quale regolare l'indice
     *
     * @return true se la caratteristica risulta gia' allineata
     *         o e' stata regolata correttamente
     */
    private boolean regolaIndice(Campo campo) {

        /** variabili e costanti locali di lavoro */
        boolean eseguito = true;
        String tavola;
        boolean isIndicizzatoCampo;
        boolean isUnicoCampo;
        ArrayList listaIndici;
        String nomeIndicePkey;
        String nomeIndiceFkey;
        ArrayList indiciNoKey;
        boolean isIndicizzatoDB = false;
        boolean isUnicoDB = false;
        Object unaCaratteristica;
        boolean allineato = false;
        ArrayList infoIndice;
        String nomeIndice;

        /* regolazione iniziale delle variabili */
        tavola = this.getDatabaseSql().getStringaTavola(campo);

        /* == recupera le caratteristiche dell'indice dal campo == */
        isIndicizzatoCampo = campo.getCampoDB().isIndicizzato();
        isUnicoCampo = campo.getCampoDB().isUnico();

        /* == recupera le caratteristica dell'indice dal Db == */
        /* recupera l'elenco di tutti gli indici sul campo
         * (compresi quelli relativi a primary key e foreign key) */
        listaIndici = this.getInfoIndici(campo, false);

        /* recupera il nome dell'indice della eventuale primary key */
        nomeIndicePkey = this.getNomeIndicePrimaryKey(tavola);

        /* recupera il nome dell'indice della eventuale foreign key */
        nomeIndiceFkey = this.getNomeIndiceForeignKey(campo);

        /* elimina dalla lista gli eventuali indici relativi alla
    * primary key e alla foreign key, che vengono gestiti
    * dalla regolazioni specifiche */
        indiciNoKey = new ArrayList();
        for (int k = 0; k < listaIndici.size(); k++) {
            infoIndice = (ArrayList)listaIndici.get(k);
            nomeIndice = getCaratteristica(infoIndice, DbSql.INFO_IDX_NOME_INDICE)
                    .toString();

            /* Se non si tratta di pKey o fKey,
             * aggiunge l'indice alla lista */
            if (!nomeIndice.equalsIgnoreCase(nomeIndicePkey)) {
                if (!nomeIndice.equalsIgnoreCase(nomeIndiceFkey)) {
                    indiciNoKey.add(infoIndice);
                }// fine del blocco if
            } /* fine del blocco if */

        } /* fine del blocco for */

        /* controlla il risultato */
        if (indiciNoKey.size() > 0) {
            isIndicizzatoDB = true;
            /* controlla se l'indice esistente e' unico
             * (opera solo sul primo indice sul campo, attualmente
             * non supportiamo piu' di un indice sullo stesso campo) */
            unaCaratteristica = this.getCaratteristica((ArrayList)indiciNoKey.get(0),
                    DbSql.INFO_IDX_NON_UNICO);

            /* Interpreto il valore in Libreria perche' alcuni driver
             * (es. mysql) ritornano una stringa "true/false" anziche'
             * un Boolean */
            boolean bool = Libreria.getBool(unaCaratteristica);
            if (!bool) {
                isUnicoDB = true;
            } /* fine del blocco if */
        } /* fine del blocco if */

        /* == verifica se le caratteristiche sono allineate == */
        if (isIndicizzatoDB == isIndicizzatoCampo) {
            if (isUnicoDB == isUnicoCampo) {
                allineato = true;
            } /* fine del blocco if */
        } /* fine del blocco if */

        /* == se non sono allineate, allinea il campo sul DB == */
        if (!allineato) {

            /* elimina tutti gli eventuali indici non pkey sul campo
             * (normalmente uno solo)*/
            for (int i = 0; i < indiciNoKey.size(); i++) {

                /* recupera la lista caratteristiche dell'indice*/
                infoIndice = (ArrayList)indiciNoKey.get(i);
                /* recupera il nome dell'indice */
                nomeIndice = getCaratteristica(infoIndice, DbSql.INFO_IDX_NOME_INDICE)
                        .toString();
                /* elimina l'indice */
                if (!this.eliminaIndice(tavola, nomeIndice)) {
                    eseguito = false;
                } /* fine del blocco if */
            } /* fine del blocco for */

            /* se il campo lo prevede,
             * crea un indice per il campo */
            if (isIndicizzatoCampo) {
                if (!this.creaIndice(campo, isUnicoCampo)) {
                    eseguito = false;
                } /* fine del blocco if */
            } /* fine del blocco if */

        } /* fine del blocco if */

        /** valore di ritorno */
        return eseguito;
    } /* fine del metodo */


    /**
     * Crea una constraint per una tavola.
     * <p/>
     *
     * @param tavola il nome della tavola
     * @param nomeConstraint il nome della constraint da creare
     * @param cmdConstraint il comando completo di argomenti per la constraint
     * (es. "PRIMARY KEY (codice)" )
     *
     * @return true se la constraint e' stata creata correttamente
     */
    private boolean creaConstraint(String tavola, String nomeConstraint, String cmdConstraint) {

        /* variabili locali di lavoro */
        boolean eseguito = true;
        Connessione conn;
        String stringa = "";
        int risultato;

        try {    // prova ad eseguire il codice

            /* recupera la connessione */
            conn = this.getDatabaseSql().getConnessioneJDBC();

            /* costruisce la stringa Sql */
            stringa += this.getDatabaseSql().getAlterTable();
            stringa += fixCase(tavola);
            stringa += this.getDatabaseSql().getAdd();
            stringa += this.getDatabaseSql().getConstraint();
            stringa += fixCase(nomeConstraint);
            stringa += " ";
            stringa += fixCase(cmdConstraint);

            /* invia il comando al Db e controlla il risultato */
            risultato = this.getDatabaseSql().esegueUpdate(stringa, conn);
            if (risultato == -1) {
                eseguito = false;
                throw new Exception(stringa);
            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return eseguito;
    } /* fine del metodo */


    /**
     * Crea un indice su un campo.
     * <p/>
     * Sovrascritto dalle sottoclassi.
     *
     * @param campo il campo sul quale creare l'indice
     * @param unico true se l'indice deve accettare solo valori unici
     *
     * @return true se l'indice e' stato creato correttamente
     */
    protected boolean creaIndice(Campo campo, boolean unico) {

        /* variabili locali di lavoro*/
        boolean eseguito = true;
        Connessione conn;
        String tavola;
        String nomeCampo;
        String nomeIndice;
        String cmd;
        int risultato;

        try {    // prova ad eseguire il codice

            /* recupera la connessione */
            conn = this.getDatabaseSql().getConnessioneJDBC();

            /* estrae i dati dal campo */
            tavola = this.getDatabaseSql().getStringaTavola(campo);
            nomeCampo = this.getDatabaseSql().getStringaCampo(campo);

            /* costruisce il nome per l'indice da creare */
            nomeIndice = tavola + DbSql.UNDERSCORE + nomeCampo;

            /* costruisce il comando */
            cmd = this.getDatabaseSql().getCreate();
            /* aggiunge eventualmente la clausola Unique */
            if (unico) {
                cmd += this.getDatabaseSql().getUnique();
            } /* fine del blocco if */
            cmd += this.getDatabaseSql().getIndex();
            cmd += nomeIndice;
            cmd += this.getDatabaseSql().getOn();
            cmd += tavola;
            cmd += " ";
            cmd += this.getDatabaseSql().getParentesiAperta();
            cmd += nomeCampo;
            cmd += this.getDatabaseSql().getParentesiChiusa();

            /* invia il comando al Db e controlla il risultato */
            risultato = this.getDatabaseSql().esegueUpdate(cmd, conn);
            if (risultato == -1) {
                eseguito = false;
            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return eseguito;
    } /* fine del metodo */


    /**
     * Regola la caratteristica Foreign Key.
     * <p/>
     *
     * @param campo il campo per il quale regolare la caratteristica.
     *
     * @return true se la caratteristica risulta gia' allineata
     *         o e' stata regolata correttamente
     */
    private boolean regolaForeignKey(Campo campo) {

        /** variabili e costanti locali di lavoro */
        boolean eseguito = true;
        String tavola;
        boolean isFkeyCampo;
        String tavolaLinkataCampo = "";
        Campo nomeCampo;
        String campoLinkatoCampo = "";
        int azioneUpdateCampo = -1;   // no zero, e' un valore significativo
        int azioneDeleteCampo = -1;   // no zero, e' un valore significativo
        boolean isFkeyDB = false;
        String tavolaLinkataDB = "";
        String campoLinkatoDB = "";
        int azioneUpdateDB = -1;   // no zero, e' un valore significativo
        int azioneDeleteDB = -1;   // no zero, e' un valore significativo
        ArrayList unaInfoFkeyDb;
        Object unValore;
        boolean allineato = false;
        String unNomeFkey;
        Modello modello;

        /* regolazione iniziale delle variabili */
        tavola = this.getDatabaseSql().getStringaTavola(campo);

        /* == recupera la caratteristica del campo dal modello ==
         * Questi dati rappresentano il parere del campo e non riflettono
         * necessariamente la situazione sul Db.
         * determina modello usa l'integrità referenziale;
         * se non la usa non crea le foreign keys. */
        modello = campo.getModulo().getModello();

        if (modello.isUsaIntegritaReferenziale()) {
            isFkeyCampo = campo.getCampoDB().isLinkato();
        } else {
            isFkeyCampo = false;
        }// fine del blocco if-else


        if (isFkeyCampo) {
            /* la tavola linkata */
            tavolaLinkataCampo = campo.getCampoDB().getTavolaLinkata().toLowerCase();

            /* campo del modulo linkato che funge da chiave */
            nomeCampo = campo.getCampoDB().getCampoLinkChiave();
            campoLinkatoCampo = this.getDatabaseSql().getStringaCampo(nomeCampo);

            /* azioni di integrita' referenziale */
            azioneUpdateCampo = campo.getCampoDB().getAzioneUpdate().getCodice();
            azioneDeleteCampo = campo.getCampoDB().getAzioneDelete().getCodice();
        } /* fine del blocco if */

        /* == recupera la caratteristica della colonna dal DB ==
         * Questi dati rappresentano la attuale situazione sul Db */

        /* recupera le informazioni sulla foreign key della colonna */
        unaInfoFkeyDb = this.getInfoFKey(campo);

        /* se la colonna ha una Fkey, ne recupera i dettagli */
        if (unaInfoFkeyDb != null) {
            isFkeyDB = true;
            tavolaLinkataDB = getCaratteristica(unaInfoFkeyDb, DbSql.INFO_FKEY_TAVOLA_PKEY)
                    .toString();
            campoLinkatoDB = getCaratteristica(unaInfoFkeyDb, DbSql.INFO_FKEY_CAMPO_PKEY)
                    .toString();
            unValore = getCaratteristica(unaInfoFkeyDb, DbSql.INFO_FKEY_AZIONE_UPDATE);

            /* uso estraeInt perche' alcuni driver ritornano Short, altri Integer */
            azioneUpdateDB = Libreria.getInt(unValore);

            unValore = getCaratteristica(unaInfoFkeyDb, DbSql.INFO_FKEY_AZIONE_DELETE);

            /* uso estraeInt perche' alcuni driver ritornano Short, altri Integer */
            azioneDeleteDB = Libreria.getInt(unValore);

        } /* fine del blocco if */

        /* == verifica se tutte le caratteristiche sono allineate == */
        if (isFkeyDB == isFkeyCampo) {
            if (tavolaLinkataDB.equalsIgnoreCase(tavolaLinkataCampo)) {
                if (campoLinkatoDB.equalsIgnoreCase(campoLinkatoCampo)) {
                    if (azioneUpdateDB == azioneUpdateCampo) {
                        if (azioneDeleteDB == azioneDeleteCampo) {
                            allineato = true;
                        } /* fine del blocco if */
                    } /* fine del blocco if */
                } /* fine del blocco if */
            } /* fine del blocco if */
        } /* fine del blocco if */

        /* == se non sono allineate, allinea il campo sul DB == */
        if (!allineato) {

            /* elimina la eventuale fkey sul campo */
            if (isFkeyDB) {

                /* recupera il nome della fKey */
                unNomeFkey = this.getCaratteristica(unaInfoFkeyDb, DbSql.INFO_FKEY_NOME_FKEY)
                        .toString();

                /** PATCH JDBC!
                 *  il driver jdbc di pgsql 7.3 ha un bug:
                 *  oltre al nome della fkey riporta una serie di caratteri
                 *  senza significato apparente. La sequenza sconosciuta comincia
                 *  con un carattere non-word, pertanto tengo solo la stringa
                 *  fino alla primo carattere non-word escluso.
                 *  Questa patch non dovrebbe interferire con altri diver corretti,
                 *  perche' un nome di constraint dovrebbe essere sempre costituito da
                 *  caratteri di tipo word (a-z, A-Z, _, 0-9).*/

                /* PARE CHE SIA SUPERATO - DISABILITATO ALEX 11/11/2004 */

//                String regex = "\\W";   // caratteri non-word
//                String[] unArrayStringhe = unNomeFkey.split(regex);
//                unNomeFkey = unArrayStringhe[0];

                /* elimina la fkey */
                this.eliminaForeignKey(tavola, unNomeFkey);
            } /* fine del blocco if */

            /* se il campo lo prevede,
             * crea una Fkey per il campo */
            if (isFkeyCampo) {
                if (!this.creaForeignKey(campo)) {
                    eseguito = false;
                } /* fine del blocco if */
            } /* fine del blocco if */

        } /* fine del blocco if */

        /** valore di ritorno */
        return eseguito;
    } /* fine del metodo */


    /**
     * Crea una constraint di tipo Foreign Key su una tavola.
     * <p/>
     *
     * @param campo il campo sul quale creare la foreign key
     *
     * @return true se la foreign key e' stata creata correttamente
     */
    private boolean creaForeignKey(Campo campo) {

        /* variabili locali di lavoro*/
        boolean eseguito = false;
        boolean continua = true;
        String tavolaPartenza = null;
        String nomeCampoPartenza = null;
        String nomeQualificatoCampoPartenza;
        String messaggioBaseErrore = null;
        Campo campoLinkChiave;
        String nomeTavolaArrivo = null;
        String nomeCampoArrivo = null;
        String nomeQualificatoCampoArrivo = null;
        boolean isIndicizzatoCampoArrivo = false;
        boolean isUnicoCampoArrivo = false;
        boolean isChiavePrimariaCampoArrivo = false;
        int azioneDelete = -1;
        int azioneUpdate = -1;
        String ref = "";

        /* estrae i dati dal campo di partenza */
        if (continua) {
            tavolaPartenza = this.getDatabaseSql().getStringaTavola(campo);
            nomeCampoPartenza = this.getDatabaseSql().getStringaCampo(campo);
            nomeQualificatoCampoPartenza = this.getDatabaseSql().getStringaCampoQualificata(campo);
            messaggioBaseErrore =
                    "Impossibile creare una foreign key su " +
                            nomeQualificatoCampoPartenza +
                            "\nperche' ";
        } /* fine del blocco if */

        /* verifica se il campo partenza ha il flag setLinkato = true */
        if (continua) {
            if (!campo.getCampoDB().isLinkato()) {
                new MessaggioAvviso(messaggioBaseErrore + "il campo non e' di tipo linkato.");
                continua = false;
            } /* fine del blocco if */
        } /* fine del blocco if */

        /* estrae i dati relativi al legame dal campo partenza */
        if (continua) {
            campoLinkChiave = campo.getCampoDB().getCampoLinkChiave();
            azioneDelete = campo.getCampoDB().getAzioneDelete().getCodice();
            azioneUpdate = campo.getCampoDB().getAzioneUpdate().getCodice();
            if (campoLinkChiave != null) {
                nomeTavolaArrivo = this.getDatabaseSql().getStringaTavola(campoLinkChiave);
                nomeCampoArrivo = this.getDatabaseSql().getStringaCampo(campoLinkChiave);
                nomeQualificatoCampoArrivo = this.getDatabaseSql().getStringaCampoQualificata(
                        campoLinkChiave);
                isIndicizzatoCampoArrivo = campoLinkChiave.getCampoDB().isIndicizzato();
                isUnicoCampoArrivo = campoLinkChiave.getCampoDB().isUnico();
                isChiavePrimariaCampoArrivo = campoLinkChiave.getCampoDB().isChiavePrimaria();
            } else {
                new MessaggioAvviso(messaggioBaseErrore + "il campo link chiave e' nullo.");
                continua = false;
            } /* fine del blocco if-else */
        } /* fine del blocco if */

        /* Per poter stabilire un legame, il campo di arrivo
         * deve essere indicizzato con indice unico.
         * Verifica se il campo arrivo e' chiave primaria
         * se e' chiave primaria, va bene cosi' perche' ha gia'
         * un indice unico.
         * Se non e' chiave primaria, verifica che
         * il campo arrivo sia indicizzato con indice unico */
        if (continua) {
            if (!isChiavePrimariaCampoArrivo) {
                /* verifica se il campo arrivo e' indicizzato */
                if (continua) {
                    if (!isIndicizzatoCampoArrivo) {
                        new MessaggioAvviso(messaggioBaseErrore +
                                "il campo linkato " +
                                nomeQualificatoCampoArrivo +
                                " non e' indicizzato.");
                        continua = false;
                    } /* fine del blocco if */
                } /* fine del blocco if */

                /* verifica se l'indice sul campo di arrivo e' di tipo unique */
                if (continua) {
                    if (!isUnicoCampoArrivo) {
                        new MessaggioAvviso(messaggioBaseErrore +
                                "il campo linkato " +
                                nomeQualificatoCampoArrivo +
                                " non ha indice unico.");
                        continua = false;
                    } /* fine del blocco if */
                } /* fine del blocco if */
            } /* fine del blocco if */
        } /* fine del blocco if */

        /* solo se attivata la apposita preferenza, verifica
         * che i valori gia' esistenti sulla foreign table
         * siano congrui, ed eventualmente li regola */
        if (continua) {
            if (Pref.DB.congruita.is()) {
                if (!this.checkDatiCongrui(campo)) {
                    continua = false;
                } /* fine del blocco if */
            } /* fine del blocco if */
        } /* fine del blocco if */

        /* costruisce il nome e il la reference per la fKey */
        if (continua) {
            ref += nomeTavolaArrivo;
            ref += this.getDatabaseSql().getParentesiAperta();
            ref += nomeCampoArrivo;
            ref += this.getDatabaseSql().getParentesiChiusa();
            ref += this.getDatabaseSql().getOnDelete();
            ref += this.stringaAzioneFkey(azioneDelete);
            ref += this.getDatabaseSql().getOnUpdate();
            ref += this.stringaAzioneFkey(azioneUpdate);
        } /* fine del blocco if */

        /* crea la foreign key sul DB */
        if (continua) {
            if (this.creaForeignKey(tavolaPartenza, nomeCampoPartenza, ref)) {
                eseguito = true;
            } /* fine del blocco if */
        } /* fine del blocco if */

        /* valore di ritorno */
        return eseguito;
    } /* fine del metodo */


    /**
     * Crea una Foreign Key.
     * <p/>
     *
     * @param tavola il nome della tavola
     * @param colonna il nome della colonna
     * @param riferimento il comando per la definizione del riferimento
     * (cioe' il contenuto della clausola REFERENCES, clausola esclusa)
     *
     * @return true se riuscito
     */
    protected boolean creaForeignKey(String tavola, String colonna, String riferimento) {

        /* variabili locali di lavoro*/
        boolean eseguito = true;
        Connessione conn = null;
        String cmd = "";
        int risultato = 0;

        try { // prova ad eseguire il codice

            /* recupera la connessione */
            conn = this.getDatabaseSql().getConnessioneJDBC();

            /* costruisce il comando */
            cmd += this.getDatabaseSql().getAlterTable();
            cmd += fixCase(tavola);
            cmd += this.getDatabaseSql().getAdd();
            cmd += this.getDatabaseSql().getForeignKey();
            cmd += this.getDatabaseSql().getParentesiAperta();
            cmd += fixCase(colonna);
            cmd += this.getDatabaseSql().getParentesiChiusa();
            cmd += this.getDatabaseSql().getReferences();
            cmd += fixCase(riferimento);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* invia il comando al Db e controlla il risultato */
        risultato = this.getDatabaseSql().esegueUpdate(cmd, conn);
        if (risultato == -1) {
            eseguito = false;
        } /* fine del blocco if */

        /* valore di ritorno */
        return eseguito;
    }


    /**
     * Ritorna la stringa Sql corrispondente ad una azione
     * di integrita' referenziale.
     * <p/>
     *
     * @param codice il codice della azione
     *
     * @return la stringa Sql
     */
    private String stringaAzioneFkey(int codice) {
        /* variabili locali di lavoro */
        String stringa = null;
        Db.Azione azione;

        try {    // prova ad eseguire il codice
            azione = Db.Azione.get(codice);

            /* seleziona la azione corrispondente al codice */
            switch (azione) {
                case noAction:
                    stringa = this.getDatabaseSql().getNoAction();
                    break;
                case cascade:
                    stringa = this.getDatabaseSql().getCascade();
                    break;
                case setNull:
                    stringa = this.getDatabaseSql().getSetNull();
                    break;
                case setDefault:
                    stringa = this.getDatabaseSql().getSetDefault();
                    break;
                default:
                    throw new Exception("Codice azione " +
                            new Integer(codice).toString() +
                            " non riconosciuto.");
            } // fine del blocco switch
        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return stringa;
    } /* fine del metodo */


    /**
     * Controllo congruita' referenziale dati.
     * <p/>
     * Controlla ed eventualmente regola i valori di un campo linkato in modo
     * da poter stabilire un legame di integrita' referenziale con un altro campo.
     * Aggiusta i dati sul campo MOLTI, regolando tutti i valori
     * che non hanno un corrispondente record sul campo UNO
     * La regolazione viene eseguita a seconda del tipo di
     * integrita' referenziale desiderato per il campo, e puo' consistere in:<br>
     * - nessuna azione
     * - cancellazione del record
     * - assegnazione del valore null
     * - assegnazione del valore di default
     * <p/>
     *
     * @param campo il campo da regolare (foreign key)
     *
     * @return true se i dati sono congrui o se sono stati regolati correttamente
     */
    private boolean checkDatiCongrui(Campo campo) {
        /** variabili e costanti locali di lavoro */
        final int NESSUNA = 0;
        final int CANCELLA = 1;
        final int IMPOSTA_NULLO = 2;
        final int IMPOSTA_DEFAULT = 3;
        boolean eseguito = true;
        Connessione conn = null;
        Query q = null;
        Dati dati = null;
        Modulo moduloFkey = null;
        Modulo moduloPkey;
        Modello modelloFkey = null;
        Campo campoChiaveFkey = null;
        Campo campoLinkPkey = null;
        Filtro filtro = null;
        int quantiRecords = 0;
        Object unValoreLink = null;
        Object valoreDefaultFkey = null;
        int unaChiaveFkey = 0;
        int numeroRecordsInPkey = 0;
        boolean incongruo = false;
        boolean regolato = false;
        int azioneIntegrita = 0;
        int azione = 0;
        Db.Azione azDb;

        try {    // prova ad eseguire il codice

            /* recupera la connessione */
            conn = this.getDatabaseSql().getConnessioneJDBC();

            /* regolazione delle variabili */
            moduloFkey = campo.getModulo();
            modelloFkey = moduloFkey.getModello();
            campoChiaveFkey = modelloFkey.getCampoChiave();
            campoLinkPkey = campo.getCampoDB().getCampoLinkChiave();
            moduloPkey = campo.getCampoDB().getModuloLinkato();
            azioneIntegrita = campo.getCampoDB().getAzioneDelete().getCodice();
            valoreDefaultFkey = campo.getInit().getValore();

            /* recupera tutti i record della tavola Molti
             * li ordina sul campo di link */
            q = new QuerySelezione(moduloFkey);
            q.addCampo(campo);
            q.addCampo(campoChiaveFkey);
            q.addOrdine(campo);
            dati = this.getDatabaseSql().querySelezione(q, conn);
            quantiRecords = dati.getRowCount();
            dati.close();

            /* Scorre il risultato e per ogni record verifica ed eventualmente
             * regola la congruita' */
            for (int k = 0; k < quantiRecords; k++) {

                /* recupera il valore e il codice chiave del campo linkato */
                unValoreLink = dati.getValueAt(k, 0);
                unaChiaveFkey = ((Integer)dati.getValueAt(k, 1)).intValue();

                /*
                * Se il valore del campo linkato e' nullo:
                *  - se la azione delete e' CASCADE, cancella il record
                *  - negli altri casi e' ok e non fa nulla
                * Se il valore del campo linkato non e' nullo:
                *  - cerca il valore chiave nella tavola di testa
                *    - se lo trova, e' ok e non fa nulla
                *    - se non lo trova, cancella il record o gli assegna
                *      un valore in funzione della impostazione della
                *      clausola delete nel modello
                */
                if (unValoreLink == null) { // e' nullo

                    if (azioneIntegrita == Db.Azione.cascade.getCodice()) {  // nullo, CASCADE
                        incongruo = true;
                        azione = CANCELLA;
                    } else {   // nullo, non CASCADE
                        incongruo = false;
                        azione = NESSUNA;
                    } /* fine del blocco if-else */

                } else {   // non e' nullo

                    /*
                     * controlla che esista la chiave nella tavola di testa
                     * (dato che prima di arrivare qui ho gia' controllato
                     * che il campo chiave sia unico, non prevedo il caso
                     * di trovare piu' di un record con il valore chiave)
                     */
                    filtro = FiltroFactory.crea(campoLinkPkey, unValoreLink);
                    numeroRecordsInPkey = conn.contaRecords(moduloPkey, filtro);
                    if (numeroRecordsInPkey == 0) { // non nullo, valore chiave non trovato
                        incongruo = true;   // e' sempre incongruo


                        azDb = Db.Azione.get(azioneIntegrita);
                        switch (azDb) {
                            case noAction:   //non fa nulla, ma resta incongruo
                                azione = NESSUNA;
                                break;
                            case cascade:     // cancella il record
                                azione = CANCELLA;
                                break;
                            case setNull:    // imposta il valore a null
                                azione = IMPOSTA_NULLO;
                                break;
                            case setDefault: // imposta al valore di default
                                azione = IMPOSTA_DEFAULT;
                                break;
                            default:   // azione non definita
                                azione = NESSUNA;
                                break;
                        } // fine del blocco switch

                    } else {   // non nullo, valore chiave trovato

                        incongruo = false;
                        azione = NESSUNA;

                    } /* fine del blocco if-else */

                } /* fine del blocco if-else */

                /* se incongruo, esegue l'azione determinata in precedenza */
                if (incongruo) {
                    regolato = false;
                    switch (azione) {
                        case NESSUNA:   //non fa nulla, ma il record resta non regolato
                            break;
                        case CANCELLA:     // cancella il record
                            q = QueryFactory.elimina(moduloFkey, unaChiaveFkey);
                            if (conn.queryModifica(q) == 1) {
                                regolato = true;
                            }// fine del blocco if
                            break;
                        case IMPOSTA_NULLO:    // imposta il valore a null
                            q = QueryFactory.modifica(campo, unaChiaveFkey, null);
                            if (conn.queryModifica(q) == 1) {
                                regolato = true;
                            }// fine del blocco if
                            break;
                        case IMPOSTA_DEFAULT:    // imposta al valore di default
                            q = QueryFactory.modifica(campo, unaChiaveFkey, valoreDefaultFkey);
                            if (conn.queryModifica(q) == 1) {
                                regolato = true;
                            }// fine del blocco if
                            break;
                        default:   // azione non definita, il record resta non regolato
                            break;
                    } // fine del blocco switch

                    /*
                     * se non ha potuto regolare correttamente, esce
                     * dal ciclo e ritorna false
                     */
                    if (!regolato) {
                        eseguito = false;
                        break;
                    } /* fine del blocco if */

                } /* fine del blocco if */

            } /* fine del blocco for */

        } catch (Exception unErrore) { // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return eseguito;
    } /* fine del metodo */


    /**
     * Acquisisce le informazioni su una caratteristica di una
     * colonna del DB dal driver JDBC.
     * <p/>
     *
     * @param campo il campo
     * @param codCaratteristica il codice della caratteristica richiesta
     * (come da documentazione di DataBaseMetaData.getColumns())
     *
     * @return oggetto contenente il valore della caratteristica richiesta
     */
    private Object getInfoColonna(Campo campo, int codCaratteristica) {

        /* variabili e costanti locali di lavoro */
        ArrayList unaLista = null;

        try { // prova ad eseguire il codice
            /* recupera le informazioni sulla colonna */
            unaLista = getInfoColonna(campo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return getCaratteristica(unaLista, codCaratteristica);
    } /* fine del metodo */


    /**
     * Acquisisce le informazioni su una colonna del DB dal driver JDBC
     * <p/>
     *
     * @param campo il campo
     *
     * @return un elenco di informazioni come ritornato dal driver
     */
    private ArrayList getInfoColonna(Campo campo) {

        /* variabili e costanti locali di lavoro */
        String t = null;
        String c = null;

        /* recupera i dati identificativi dal campo */
        t = this.getDatabaseSql().getStringaTavola(campo);
        c = this.getDatabaseSql().getStringaCampo(campo);

        /* valore di ritorno */
        return this.getInfoColonna(t, c);
    } /* fine del metodo */


    /**
     * Acquisisce le informazioni su una colonna del DB dal driver JDBC.
     * <p/>
     *
     * @param t il nome della tavola sul DB
     * @param c il nome della colonna sul DB
     *
     * @return un elenco di informazioni sulla colonna
     *         come ritornato dal driver
     */
    protected ArrayList getInfoColonna(String t, String c) {

        /* variabili e costanti locali di lavoro */
        ArrayList info = null;
        ResultSet rs = null;

        try { // prova ad eseguire il codice

            t = fixCase(t);
            c = fixCase(c);

            /* recupera un resultset contenente le informazioni
             * sul campo richiesto */
            rs = getMetaData().getColumns(null, null, t, c);

            /* estrae una lista di caratteristiche dal ResultSet */
            if (LibResultSet.quanteRighe(rs) == 1) {
                info = LibResultSet.getValoriRiga(rs, 1);
            } /* fine del blocco if */

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return info;
    } /* fine del metodo */


    /**
     * Acquisisce le informazioni su tutte le colonne di una tavola
     * <p/>
     *
     * @param tavola il nome della tavola
     *
     * @return un elenco di informazioni sulle colonne
     *         come ritornato dal driver
     */
    protected ArrayList getInfoColonne(String tavola) {

        /* variabili e costanti locali di lavoro */
        ArrayList info = null;
        ResultSet rs = null;

        try { // prova ad eseguire il codice

            tavola = fixCase(tavola);

            /* recupera un resultset contenente le informazioni
             * sulle colonne della tavola richiesta */
            rs = getMetaData().getColumns(null, null, tavola, null);

            /* estrae una lista di caratteristiche dal ResultSet */
            info = LibResultSet.getValoriRighe(rs);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return info;
    } /* fine del metodo */


    /**
     * Acquisisce le informazioni sulla chiave primaria di una tavola
     * dal driver JDBC.
     * <p/>
     *
     * @param tavola il nome della tavola
     *
     * @return un elenco di informazioni come ritornato dal driver
     *         (una riga per ogni campo che fa parte della chiave primaria,
     *         normalmente una sola riga)
     */
    protected ArrayList getInfoPrimaryKey(String tavola) {

        /** variabili e costanti locali di lavoro */
        ArrayList caratteristiche = null;
        ResultSet rs = null;

        try {    // prova ad eseguire il codice

            tavola = fixCase(tavola);

            /* recupera un resultset contenente le informazioni
             * sugli indici della tavola (se esistono) */
            rs = getMetaData().getPrimaryKeys(null, null, tavola);

            /** estrae una lista dal ResultSet */
            caratteristiche = LibResultSet.getValoriRighe(rs);

        } catch (Exception unErrore) { // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return caratteristiche;
    } /* fine del metodo */


    /**
     * Acquisisce le informazioni sugli indici su un dato campo
     * dal driver JDBC.
     * <p/>
     *
     * @param campo il campo
     * @param soloUnici flag true per ritornare solo gli
     * indici Unique, false per tutti
     *
     * @return un elenco di informazioni come ritornato dal driver
     *         (una riga per ogni indice del campo)
     *         Lista vuota se non ha indici
     */
    private ArrayList getInfoIndici(Campo campo, boolean soloUnici) {

        /** variabili e costanti locali di lavoro */
        ArrayList infoIndici = null;
        ArrayList indiciTavola = null;
        ArrayList infoIndice = null;
        String tavola = null;
        String nomeCampo = null;
        String nomeCampoIndice = null;

        try {    // prova ad eseguire il codice

            /* recupera il nome della tavola e del campo */
            tavola = this.getDatabaseSql().getStringaTavola(campo);
            nomeCampo = this.getDatabaseSql().getStringaCampo(campo);

            /* recupera tutti gli indici della tavola */
            indiciTavola = this.getInfoIndici(tavola, soloUnici);

            /* crea una nuova lista per le informazioni da ritornare */
            infoIndici = new ArrayList();

            /* filtra solo gli indici sul campo richiesto */
            for (int i = 0; i < indiciTavola.size(); i++) {

                /* recupera la lista caratteristiche dell'indice*/
                infoIndice = (ArrayList)indiciTavola.get(i);

                /* recupera il nome del campo dalle caratteristiche dell'indice */
                nomeCampoIndice = getCaratteristica(infoIndice, DbSql.INFO_IDX_NOME_CAMPO)
                        .toString();

                /* verifica se l'indice e' sul campo richiesto */
                if (nomeCampo.equalsIgnoreCase(nomeCampoIndice)) {
                    infoIndici.add(infoIndice);
                } /* fine del blocco if */
            } /* fine del blocco for */

        } catch (Exception unErrore) { // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return infoIndici;
    } /* fine del metodo */


    /**
     * Acquisisce le informazioni sugli indici di una tavola
     * dal driver JDBC.
     * <p/>
     *
     * @param tavola il nome della tavola
     * @param soloUnici flag true per ritornare solo gli indici Unique, false per tutti
     *
     * @return un elenco di informazioni come ritornato dal driver
     *         (una riga per ogni indice)
     */
    protected ArrayList getInfoIndici(String tavola, boolean soloUnici) {

        /* variabili e costanti locali di lavoro */
        ArrayList elencoCaratteristiche = null;
        ResultSet rs = null;

        try {    // prova ad eseguire il codice

            tavola = fixCase(tavola);

            /* recupera un resultset contenente le informazioni
             * sugli indici della tavola (se esistono) */
            rs = this.getMetaData().getIndexInfo(null, null, tavola, soloUnici, false);

            /* estrae una lista dal ResultSet */
            elencoCaratteristiche = LibResultSet.getValoriRighe(rs);

        } catch (Exception unErrore) { // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return elencoCaratteristiche;
    } /* fine del metodo */


    /**
     * Ritorna il nome dell'indice della primary key di una tavola.
     * <p/>
     *
     * @param tavola il nome della tavola
     *
     * @return il nome dell'indice della pkey
     *         (stringa vuota se non esiste la pkey)
     */
    private String getNomeIndicePrimaryKey(String tavola) {
        /** variabili e costanti locali di lavoro */
        ArrayList risposta = null;
        ArrayList infoPkey = null;
        String nomeIndicePkey = "";

        try {                                   // prova ad eseguire il codice

            /* recupera il nome del primo campo della pkey
             * (attualmente non supportiamo primary keys
             * basate su piu' di un campo) */
            risposta = this.getInfoPrimaryKey(tavola);
            if (risposta.size() > 0) {
                infoPkey = (ArrayList)risposta.get(0);
                /* estrae il nome dell'indice */
                nomeIndicePkey = this.getCaratteristica(infoPkey, DbSql.INFO_PKEY_NOME)
                        .toString();
            }// fine del blocco if

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return nomeIndicePkey;
    } /* fine del metodo */


    /**
     * Ritorna il nome dell'eventuale indice collegato
     * alla foreign key di un campo.
     * <p/>
     * Significativo solo per i Db che usano un indice
     * anziche' una constraint per gestire le fKeys.
     * (Postgres usa una constraint, MySql usa un indice...)
     *
     * @param campo il campo da controllare
     *
     * @return il nome dell'indice relativo alla fkey
     *         (stringa vuota se non esiste la fkey o l'indice)
     */
    private String getNomeIndiceForeignKey(Campo campo) {
        /** variabili e costanti locali di lavoro */
        String nomeIndiceFkey = "";
        String nomeCampoFkey = "";
        ArrayList infoFkey = null;
        ArrayList infoIndici = null;
        ArrayList infoIndice = null;

        try {                                   // prova ad eseguire il codice

            /*
             * recupera il nome del campo della fKey.
             */
            infoFkey = this.getInfoFKey(campo);

            if (infoFkey != null) {
                nomeCampoFkey = this.getCaratteristica(infoFkey, DbSql.INFO_FKEY_CAMPO_FKEY)
                        .toString();
            }// fine del blocco if

            /*
             * recupera il nome dell'eventuale indice del campo
             */
            if (Lib.Testo.isValida(nomeCampoFkey)) {
                infoIndici = this.getInfoIndici(campo, false);
                if (infoIndici.size() > 0) {
                    infoIndice = (ArrayList)infoIndici.get(0);
                    nomeIndiceFkey = (String)this.getCaratteristica(infoIndice,
                            DbSql.INFO_IDX_NOME_INDICE);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return nomeIndiceFkey;
    } /* fine del metodo */


    /**
     * Acquisisce le informazioni sulle foreign keys di una tavola
     * dal driver JDBC.
     * <p/>
     *
     * @param tavola il nome della tavola
     *
     * @return un elenco di informazioni come ritornato dal driver
     *         (una riga per ogni campo che fa parte della chiave primaria,
     *         normalmente una sola riga)
     */
    protected ArrayList getInfoFKeys(String tavola) {

        /* variabili e costanti locali di lavoro */
        ArrayList caratteristiche = null;
        ResultSet rs = null;

        try {    // prova ad eseguire il codice

            tavola = fixCase(tavola);

            /* recupera un resultset contenente le informazioni
             * sulle imported keys tavola (se esistono) */
            rs = getMetaData().getImportedKeys(null, null, tavola);

            /* estrae una lista dal ResultSet */
            caratteristiche = LibResultSet.getValoriRighe(rs);

        } catch (Exception unErrore) { // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return caratteristiche;
    } /* fine del metodo */


    /**
     * Ritorna le informazioni sulla foreign key di un campo.
     * <p/>
     *
     * @param nomeTavola il nome della tavola
     * @param nomeCampo il nome del campo
     *
     * @return le informazioni sulla foreign key del campo
     *         null vuota se il campo non ha fkey
     */
    private ArrayList getInfoFKey(String nomeTavola, String nomeCampo) {

        /* variabili e costanti locali di lavoro */
        ArrayList risposta = null;
        ArrayList infoFkeyTavola = null;
        ArrayList infoFkey = null;
        String nomeCampoPartenza = null;
        ArrayList infoFkeyCampo = null;
        risposta = null;

        try {                                   // prova ad eseguire il codice

            /* recupera le informazioni su tutte le foreign keys della tavola */
            infoFkeyTavola = this.getInfoFKeys(nomeTavola);

            /* filtra solo le informazioni relative al campo */
            infoFkeyCampo = new ArrayList();
            for (int i = 0; i < infoFkeyTavola.size(); i++) {
                /* recupera la lista caratteristiche della singola fkey */
                infoFkey = (ArrayList)infoFkeyTavola.get(i);
                /* recupera il nome del campo di partenza */
                nomeCampoPartenza = getCaratteristica(infoFkey, DbSql.INFO_FKEY_CAMPO_FKEY)
                        .toString();
                /* verifica se la foreign key e' sul campo richiesto */
                if (nomeCampo.equalsIgnoreCase(nomeCampoPartenza)) {
                    infoFkeyCampo.add(infoFkey);
                } /* fine del blocco if */
            } /* fine del blocco for */

            /* recupera le informazioni sulla prima foreign key del campo
             * (attualmente non supportiamo piu' foreign keys su un campo)*/
            if (infoFkeyCampo.size() > 0) {
                risposta = (ArrayList)infoFkeyCampo.get(0);
            } /* fine del blocco if */

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return risposta;
    } /* fine del metodo */


    /**
     * Ritorna le informazioni sulla foreign key di un campo.
     * <p/>
     *
     * @param campo il campo
     *
     * @return le informazioni sulla foreign key del campo
     *         null vuota se il campo non ha fkey
     */
    private ArrayList getInfoFKey(Campo campo) {

        /* variabili e costanti locali di lavoro */
        ArrayList risposta = null;
        String tavola = null;
        String nomeCampo = null;
        risposta = null;

        try {

            tavola = this.getDatabaseSql().getStringaTavola(campo);
            nomeCampo = this.getDatabaseSql().getStringaCampo(campo);
            risposta = this.getInfoFKey(tavola, nomeCampo);

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return risposta;
    } /* fine del metodo */


    /**
     * Acquisisce una specifica caratteristica da una lista di caratteristiche.
     * <p/>
     *
     * @param lista la lista di caratteristiche
     * @param codCaratteristica il codice della caratteristica richiesta
     * (come da documentazione in DataBaseMetaData)
     *
     * @return oggetto contenente il valore della caratteristica richiesta
     */
    protected Object getCaratteristica(ArrayList lista, int codCaratteristica) {
        int codShiftato = codCaratteristica - 1;
        return lista.get(codShiftato);
    } /* fine del metodo */


    /**
     * Ritorna il MetaData relativo al database.
     * <p/>
     *
     * @return l'oggetto DatabaseMetaData
     */
    protected DatabaseMetaData getMetaData() {

        /* variabili locali di lavoro */
        DatabaseMetaData md = null;
        ConnessioneJDBC conn;

        try {                                   // prova ad eseguire il codice

            /* recupera la connessione con il database */
            conn = this.getDatabaseSql().getConnessioneJDBC();

            /* recupera i metadati dal database */
            md = this.getMetaData(conn);

        } catch (Exception unErrore) {           // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return md;
    } /* fine del metodo */


    /**
     * Ritorna il MetaData relativo al database.
     * <p/>
     *
     * @param conn connessione da utilizzare
     *
     * @return l'oggetto DatabaseMetaData
     */
    protected DatabaseMetaData getMetaData(Connessione conn) {

        /* variabili locali di lavoro */
        DatabaseMetaData md = null;

        try {                                   // prova ad eseguire il codice

            /* recupera i metadati dal database */
            md = conn.getConnection().getMetaData();

        } catch (Exception unErrore) {           // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return md;
    } /* fine del metodo */


}// fine della classe



