/**
 * Title:     DatiMemoria
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-ott-2004
 */
package it.algos.base.database.sql.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.Db;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.dati.DatiBase;
import it.algos.base.database.dati.InfoColonna;
import it.algos.base.database.sql.DbSql;
import it.algos.base.database.sql.tipodati.TipoDatiSql;
import it.algos.base.database.tipodati.TipoDati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.LibResultSet;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.Query;
import it.algos.base.query.campi.Campi;
import it.algos.base.query.campi.CampoQuery;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Contenitore di dati sql.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Costruisce un tipo di dati </li>
 * <li> Raggruppa questi dati in un wrapper </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 21-ott-2004 ore 16.08.14
 */
public final class DatiSql extends DatiBase {

    /**
     * Database proprietario di questo oggetto dati
     */
    private Db db = null;

    /**
     * Query che ha dato origine a questo oggetto dati
     */
    private Query query = null;

    /**
     * Set di dati selezionati.
     * I dati selezionati sono un subset del Database <br>
     */
    private ResultSet resultSet = null;


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param db il database proprietario
     * @param query la query che ha dato origine a questi dati
     */
    public DatiSql(Db db, Query query) {

        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setDb(db);
        this.setQuery(query);

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
        this.regolaInfoColonne();
    }// fine del metodo inizia


    /**
     * Regola le informazioni sulle colonne in base alla query.
     * <p/>
     * Crea un oggetto InfoColonna per ogni campo e lo aggiunge alla collezione
     * Aggiunge una entry alla mappa campi
     * Identifica la posizione della colonna chiave
     */
    protected void regolaInfoColonne() {
        /* variabili e costanti locali di lavoro */
        Query q;
        ArrayList elementi;
        CampoQuery cq;
        InfoColonna ic;
        Campo campo;
        TipoDati td;
        String chiaveCampoChiave;
        String chiave;

        try {    // prova ad eseguire il codice
            q = this.getQuery();
            if (q != null) {
                elementi = q.getListaElementi();
                chiaveCampoChiave = q.getModulo().getCampoChiave().getChiaveCampo();
                for (int k = 0; k < elementi.size(); k++) {

                    cq = (CampoQuery)elementi.get(k);
                    campo = cq.getCampo();

                    /* determina il tipo di dati per la colonna<br>
                    /* usa il tipo dati del campo<br>
                     * potrebbe essere ancora cambiato quando si assegnano i dati
                     * (vedi per esempio DatiSql.setResultSet())*/
                    td = this.getDb().getTipoDati(campo);

                    /* crea un oggetto di informazioni sulla colonna,
                     * lo regola e lo aggiunge alla lista */
                    ic = new InfoColonna();
                    ic.setPosizione(k);
                    ic.setCampo(campo);
                    ic.setTipoDati(td);
                    this.getInfoColonne().add(ic);

                    /* recupera la chiave del campo */
                    chiave = campo.getChiaveCampo();

                    /* aggiunge la entry per la mappa campi */
                    this.getMappaCampi().put(chiave, ic);

                    /* se il campo e' il campo chiave, regola la posizione
                     * della colonna chiave */
                    if (chiave.equals(chiaveCampoChiave)) {
                        this.setColonnaChiavi(k);
                    }// fine del blocco if

                } // fine del ciclo for
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il numero di righe dei Dati.
     * <p/>
     * Se esiste il ResultSet, lo recupera dal ResultSet
     * Altrimenti, ritorna zero.
     *
     * @return il numero di righe dei dati
     */
    public int getRowCount() {
        /* variabili e costanti locali di lavoro */
        ResultSet unResultSet;
        int righe = 0;

        try { // prova ad eseguire il codice
            unResultSet = this.getResultSet();
            if (unResultSet != null) {
                righe = LibResultSet.quanteRighe(unResultSet);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
        /* valore di ritorno */
        return righe;
    } /* fine del metodo */


    /**
     * Ritorna il numero di colonne dei Dati.
     * <p/>
     *
     * @return il numero di colonne dei dati
     */
    public int getColumnCount() {
        /* variabili e costanti locali di lavoro */
        ResultSet unResultSet;
        int righe = 0;

        try { // prova ad eseguire il codice
            unResultSet = this.getResultSet();
            if (unResultSet != null) {
                righe = LibResultSet.quanteColonne(unResultSet);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
        /* valore di ritorno */
        return righe;
    } /* fine del metodo */


    /**
     * Ritorna il valore Memoria di una cella.
     * <p/>
     * Se la cella rappresenta il valore di un campo, ritorna il valore
     * memoria per il tipo di campo.<br>
     * Se la cella e' il risultato di una funzione, ritorna il valore
     * fornito dal database.<br>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param colonna indice di colonna della cella richiesta (0 per la prima)
     *
     * @return il valore Memoria dell'oggetto nella cella specificata
     */
    public Object getValueAt(int riga, int colonna) {
        /* variabili e costanti locali di lavoro */
        Object valoreOut = null;
        boolean continua;
        Object valoreBl;
        Object valoreDb;
        Campo campo;
        int chiaveTdCampo;
        TipoDati tdCampo;
        TipoDati tdColonna;

        try { // prova ad eseguire il codice

            /* controlla che la riga richiesta sia nel range */
            continua = (riga >= 0) && (riga < this.getRowCount());

            /* controlla che la colonna richiesta sia nel range */
            if (continua) {
                continua = (colonna >= 0) && (colonna < this.getColumnCount());
            }// fine del blocco if

            if (continua) {

                /* Recupera il valore DB nella sottoclasse */
                valoreDb = this.getDbValueAt(riga, colonna);

                /* recupera il campo corrispondente a questa colonna */
                campo = this.getCampoColonna(colonna);

                /* recupera il tipo dati del campo */
                chiaveTdCampo = campo.getCampoDati().getTipoArchivio().getChiaveTipoDatiDb();
                tdCampo = this.getDb().getTipoDati(chiaveTdCampo);

                /* recupera il tipo dati dalla colonna*/
                tdColonna = this.getInfoColonna(colonna).getTipoDati();

                /*
                * Se il tipo dati e' lo stesso, delega al campo
                * la conversione da DB a BL e poi da Archivio a Memoria.
                * Se e' diverso, significa che sulla colonna c'era una
                * funzione che rende un tipo diverso da quello
                * della colonna. In tal caso rende il valore
                * cosi' come arriva dal database.
                */
                if (tdCampo.equals(tdColonna)) {
                    valoreBl = campo.db2bl(valoreDb, this.getDb());
                    valoreOut = campo.getCampoDati().getMemoriaDaArchivio(valoreBl);
                } else {
                    valoreOut = valoreDb;
                }// fine del blocco if-else
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valoreOut;
    }


    /**
     * Assegna un valore a una cella.
     * <p/>
     * Nota bene: per adesso, dopo questa operazione
     * il ResultSet non è aggiornato.
     * Per vedere le modifiche occorre ricaricarlo.
     *
     * @param valore da assegnare
     * @param riga l'indice della riga, 0 per la prima
     * @param colonna l'indice della colonna, 0 per la prima
     */
    public void setValueAt(Object valore, int riga, int colonna) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int colonnaChiavi;
        int codice = 0;
        Campo campo = null;
        Modulo modulo;

        try { // prova ad eseguire il codice

            /* recupera la colonna delle chiavi */
            colonnaChiavi = this.getColonnaChiavi();
            continua = (colonnaChiavi >= 0);

            /* recupera il codice del record */
            if (continua) {
                codice = this.getIntAt(riga, colonnaChiavi);
                continua = (codice > 0);
            }// fine del blocco if

            /* recupera il campo da scrivere */
            if (continua) {
                campo = this.getCampoColonna(colonna);
                continua = (campo != null);
            }// fine del blocco if

            /* esegue la query */
            if (continua) {
                modulo = this.getQuery().getModulo();
                modulo.query().registraRecordValore(codice, campo, valore);
            }// fine del blocco if

            if (continua) {
//                ResultSet rs;
//                rs = this.getResultSet();
//                rs.absolute(riga+1);
//                Object val = this.getValueAt(riga, colonna);
//                int a = 87;
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Resituisce la posizione di una chiave all'interno dei dati.
     * <p/>
     * Se e' il primo record, ritorna POSIZIONE_PRIMO.
     * Se il record ha dei record precedenti e successivi, ritorna POSIZIONE_INTERMEDIO.
     * Se il record e' l'ultimo, ritorna POSIZIONE_ULTIMO.
     * Nota: se e' l'unico record nei dati, ritorna sempre POSIZIONE_PRIMO.
     *
     * @param codice il codice del record del quale individuare la posizione
     *
     * @return la posizione della chiave (vedi codifica in Dati)
     */
    public int posizioneRelativa(int codice) {
        /* variabili e costanti locali di lavoro */
        int pRel;
        int pAss;
        int righe;

        pRel = Dati.POSIZIONE_ASSENTE;

        try { // prova ad eseguire il codice
            righe = this.getRowCount();

            pAss = this.getRigaChiave(codice);

            if (pAss != -1) {
                if (pAss == 0) {
                    pRel = Dati.POSIZIONE_PRIMO;
                } else {
                    if (pAss < righe - 1) {
                        pRel = Dati.POSIZIONE_INTERMEDIO;
                    } else {
                        pRel = Dati.POSIZIONE_ULTIMO;
                    }// fine del blocco if-else
                }// fine del blocco if-else
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pRel;
    }


    /**
     * Restituisce la codifica di posizione del record corrente nell'ambito del result set.
     *
     * @return la posizione (vedi codifica in Dati)
     */
    public int posizione() {
        /* variabili e costanti locali di lavoro */
        int pRel;
        int pAss;
        int righe;

        pRel = Dati.POSIZIONE_ASSENTE;

        try { // prova ad eseguire il codice

            righe = this.getRowCount();

            pAss = this.getResultSet().getRow();

            if (pAss > 0) {
                if (pAss == 1) {
                    pRel = Dati.POSIZIONE_PRIMO;
                } else {
                    if (pAss < righe) {
                        pRel = Dati.POSIZIONE_INTERMEDIO;
                    } else {
                        pRel = Dati.POSIZIONE_ULTIMO;
                    }// fine del blocco if-else
                }// fine del blocco if-else
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pRel;
    }


    /**
     * Restituisce il codice-chiave di un record relativo ad un altro record.
     *
     * @param codice record di riferimento
     * @param spostamento codifica della posizione di spostamento
     * (vedi codifica in Dati)
     *
     * @return codice del record nella posizione relativa richiesta
     */
    public int getCodiceRelativo(int codice, Dati.Spostamento spostamento) {
        /* variabili e costanti locali di lavoro */
        int codiceRelativo = 0;
        int righe;
        int cod;
        int colonnaChiavi;

        try { // prova ad eseguire il codice
            righe = this.getRowCount();
            colonnaChiavi = this.getColonnaChiavi();

            switch (spostamento) {
                case primo:
                    codiceRelativo = getIntAt(0, colonnaChiavi);
                    break;
                case precedente:
                    for (int k = 0; k < righe; k++) {
                        cod = this.getIntAt(k, colonnaChiavi);
                        if (cod == codice) {
                            if (k > 0) {
                                codiceRelativo = getIntAt(k - 1, colonnaChiavi);
                            }// fine del blocco if
                            break;
                        }// fine del blocco if
                    } // fine del ciclo for
                    break;
                case successivo:
                    for (int k = 0; k < righe; k++) {
                        cod = this.getIntAt(k, colonnaChiavi);
                        if (cod == codice) {
                            if (k < righe - 1) {
                                codiceRelativo = getIntAt(k + 1, colonnaChiavi);
                            }// fine del blocco if
                            break;
                        }// fine del blocco if
                    } // fine del ciclo for
                    break;
                case ultimo:
                    codiceRelativo = getIntAt(righe - 1, colonnaChiavi);
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codiceRelativo;
    }


    /**
     * Resituisce la posizione assoluta di una chiave all'interno dei dati.
     * <p/>
     *
     * @param codice il codice del record del quale individuare la posizione
     *
     * @return la posizione assoluta della chiave (0 per la prima, -1 se non trovato)
     */
    public int getRigaChiave(int codice) {
        /* variabili e costanti locali di lavoro */
        int posizione;
        int righe;
        int intero;
        int colonnaChiavi;

        posizione = -1;

        try { // prova ad eseguire il codice

            colonnaChiavi = this.getColonnaChiavi();
            righe = this.getRowCount();

            for (int k = 0; k < righe; k++) {
                intero = this.getIntAt(k, colonnaChiavi);
                if (intero == codice) {
                    posizione = k;
                    break;
                }// fine del blocco if

            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return posizione;
    }


    /**
     * Ritorna il valore di una cella.<p>
     * Il valore e' rappresentato a livello DB.
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param colonna indice di colonna della cella richiesta (0 per la prima)
     *
     * @return il valore dell'oggetto nella cella specificata
     */
    public Object getDbValueAt(int riga, int colonna) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        ResultSet unResultSet;

        try { // prova ad eseguire il codice
            unResultSet = this.getResultSet();
            if (unResultSet != null) {
                try { // prova ad eseguire il codice
                    unResultSet.absolute(riga + 1);
                    if (colonna < LibResultSet.quanteColonne(unResultSet)) {
                        valore = unResultSet.getObject(colonna + 1);
                    }// fine del blocco if
                } catch (Exception unErrore) { // intercetta l'errore
                    // nessun errore, può succedere che il ResultSet esista
                    // ma la connessione sia chiusa
                }// fine del blocco try-catch

            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Regola i tipi delle colonne in base al ResultSet.
     * <p/>
     * Normalmente i tipi dati delle colonne sono regolati
     * alla creazione dell'oggetto dati usando la Query
     * che lo ha generato, e il tipo dati di ogni colonna
     * corrisponde al tipo dati del campo originale.
     * Se pero' nella query sono state utilizzate delle funzioni
     * sui campi (SUM, COUNT ecc...) il tipo delle colonne risultanti
     * puo' essere diverso dal tipo dati del campo.
     * Quindi, controllo che i tipi siano corretti e cambio
     * quelli che eventualmente sono diversi.
     */
    private void checkColonneDaRS() {
        ResultSet rs;
        ResultSetMetaData rsmd;
        int quanteColonne;
        int tipoColonnaRS;
        int tipoColonnaDati;
        TipoDatiSql tdsql;
        InfoColonna ic;
        TipoDati tdNuovo;
        String stringa;

        try { // prova ad eseguire il codice
            rs = this.getResultSet();

            if (rs != null) {    // per sicurezza

                rsmd = rs.getMetaData();
                quanteColonne = rsmd.getColumnCount();
                for (int k = 0; k < quanteColonne; k++) {
                    tipoColonnaRS = rsmd.getColumnType(k + 1);
                    ic = this.getInfoColonna(k);

                    tdsql = (TipoDatiSql)ic.getTipoDati();
                    tipoColonnaDati = tdsql.getTipoJdbc();

                    if (tipoColonnaRS != tipoColonnaDati) {

                        Campi campiQuery = this.getQuery().getCampi();
                        ArrayList elementi = campiQuery.getListaElementi();
                        CampoQuery campoQuery = (CampoQuery)elementi.get(k);
                        ArrayList funzioni = campoQuery.getFunzioni();

                        if (funzioni.size() > 0) { // ok, ci sono funzioni, e' normale
                            tdNuovo = this.getDbSql().getTipoDatiDaJdbc(tipoColonnaRS);
                            if (tdNuovo != null) {
                                ic.setTipoDati(tdNuovo);
                            } else {
                                throw new Exception("Non trovato un tipo dati " +
                                        "in grado di gestire " +
                                        "il tipo Jdbc " +
                                        tipoColonnaRS);
                            }// fine del blocco if-else

                        } else { // non ci sono funzioni e il tipo e' diverso, qualcosa non va

                            stringa = "Tipo Jdbc inatteso in assenza di funzioni.\n";
                            stringa += "Colonna: " + ic.getCampo().getChiaveCampo() + "\n";
                            stringa += "Tipo Jdbc atteso: " + tipoColonnaDati + "\n";
                            stringa += "Tipo Jdbc ricevuto: " + tipoColonnaRS + "\n";

                            throw new Exception(stringa);

                        }// fine del blocco if-else

                    }// fine del blocco if
                } // fine del ciclo for
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna il database Sql di riferimento per questo oggetto dati.
     * <p/>
     *
     * @return il database Sql
     */
    private DbSql getDbSql() {
        /* valore di ritorno */
        return (DbSql)this.getDb();
    }


    protected Db getDb() {
        return db;
    }


    private void setDb(Db db) {
        this.db = db;
    }


    protected Query getQuery() {
        return query;
    }


    private void setQuery(Query query) {
        this.query = query;
    }


    /**
     * Ritorna il modulo di riferimento della query che ha originato questi dati.
     * <p/>
     *
     * @return il modulo della query
     */
    protected Modulo getModulo() {
        /* variabili e costanti locali di lavoro */
        Modulo modulo = null;
        Query query;

        try { // prova ad eseguire il codice
            query = this.getQuery();
            if (query != null) {
                modulo = query.getModulo();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return modulo;
    }


    public ResultSet getResultSet() {
        return resultSet;
    }


    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
        this.checkColonneDaRS();
    }


    /**
     * Chiude l'oggetto dati e libera le risorse Jdbc allocate.<p>
     * <p/>
     * Chiude lo statement il quale chiude automaticamente il resultset.<br>
     * Dovrebbe essere eseguito automaticamente alla finalizzazione
     * dell'oggetto, quando il garbage collector determina che non ha
     * piu' riferimenti.<br>
     * Però il funzionamento non e' affidabile per tutti i driver Jdbc.
     * Quindi e' meglio chiamare esplicitamente close() quando l'oggetto
     * dati non serve piu'.<br>
     */
    public void close() {
        /* variabili e costanti locali di lavoro */
        Statement stat;
        ResultSet rs;

        try { // prova ad eseguire il codice
            rs = this.getResultSet();
            if (rs != null) {
                stat = rs.getStatement();
                if (stat != null) {
                    stat.close();   // chiude statement e resultset
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch
    }


}// fine della classe
