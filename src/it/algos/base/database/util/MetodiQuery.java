/**
 * Title:     LibQueryModulo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-gen-2005
 */
package it.algos.base.database.util;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.Db;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.connessione.ConnessioneJDBC;
import it.algos.base.database.connessione.Connettore;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.sql.DbSql;
import it.algos.base.database.sql.tipodati.TipoDatiSql;
import it.algos.base.database.sql.util.OperatoreSql;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibResultSet;
import it.algos.base.libreria.Libreria;
import it.algos.base.matrice.MatriceDoppia;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.QueryFactory;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.modifica.QueryUpdate;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.relazione.Relazione;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.SetValori;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

/**
 * Raccoglitore di tutti i metodi per effettuare le query del modulo
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-gen-2005 ore 15.28.58
 */
public final class MetodiQuery {

    /**
     * Oggetto Connettore di riferimento (Modulo, Navigatore o altro)
     */
    private Connettore connettore = null;


    /**
     * Costruttore completo con parametri.<br>
     *
     * @param connettore l'oggetto Connettore di riferimento per ottenere
     * il Modulo e la connessione
     */
    public MetodiQuery(Connettore connettore) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setConnettore(connettore);

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
    }// fine del metodo inizia


    /**
     * Carica una selezione di record.
     * <p/>
     *
     * @param query informazioni per effettuare la selezione
     *
     * @return un oggetto dati
     */
    public Dati querySelezione(Query query) {
        return this.querySelezione(query, this.getConnessione());
    }


    /**
     * Carica una selezione di record.
     * <p/>
     *
     * @param query informazioni per effettuare la selezione
     * @param conn la connessione da utilizzare
     *
     * @return un oggetto dati
     */
    public Dati querySelezione(Query query, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Dati dati = null;

        try { // prova ad eseguire il codice
            if (conn != null) {
                dati = conn.querySelezione(query);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dati;
    }


    /**
     * Carica una selezione di record.
     * todo metodi analoghi si trovano in FiltroFactory
     * todo questo non mi sembra che sia mai stato usato
     * todo lo possiamo eliminare?
     * todo alex 09/07
     * <p/>
     * todo no/gac 19-10
     * <p/>
     * Carica i records col valore vero nel campo indicato <br>
     *
     * @param campo flag
     *
     * @return un oggetto dati
     */
    public Dati querySelVero(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Dati dati = null;
        Modulo mod;
        Query query;
        Filtro filtro;

        try { // prova ad eseguire il codice
            mod = this.getModulo();
            filtro = FiltroFactory.creaVero(campo.get());
            query = new QuerySelezione(mod);
            query.addFiltro(filtro);
            dati = mod.query().querySelezione(query);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dati;
    }


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
    public int queryModifica(Query query) {
        return this.queryModifica(query, this.getConnessione());
    }


    /**
     * Esegue la modifica di uno o piu' record.
     * <p/>
     * La modifica puo' consistere in inserimento di nuovi record
     * o modifica o cancellazione di record esistenti.
     *
     * @param query informazioni per effettuare la modifica
     * @param conn la connessione da utilizzare
     *
     * @return il numero di record interessati
     */
    public int queryModifica(Query query, Connessione conn) {
        return conn.queryModifica(query);
    }


    /**
     * Esegue la modifica del check indicato in tutti i record.
     * <p/>
     *
     * @param nomeCampo il nome del campo da regolare
     * @param valore booleano
     *
     * @return il numero di record interessati
     */
    public int setAll(String nomeCampo, boolean valore) {
        /* variabili e costanti locali di lavoro */
        int num = 0;
        Query query;
        Modulo mod;

        try { // prova ad eseguire il codice
            mod = this.getModulo();
            query = new QueryUpdate(mod);
            query.addCampo(mod.getCampo(nomeCampo), valore);
            num = this.queryModifica(query);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return num;
    }


    /**
     * Esegue la modifica del check indicato in tutti i record.
     * <p/>
     *
     * @param nomeCampo il nome del campo da regolare
     *
     * @return il numero di record interessati
     */
    public int setAllVero(String nomeCampo) {
        /* invoca il metodo delegato della classe */
        return setAll(nomeCampo, true);
    }


    /**
     * Esegue la modifica del check indicato in tutti i record.
     * <p/>
     *
     * @param campo da regolare
     *
     * @return il numero di record interessati
     */
    public int setAllVero(Campi campo) {
        /* invoca il metodo delegato della classe */
        return setAllVero(campo.get());
    }


    /**
     * Esegue la modifica del check indicato in tutti i record.
     * <p/>
     *
     * @param nomeCampo il nome del campo da regolare
     *
     * @return il numero di record interessati
     */
    public int setAllFalso(String nomeCampo) {
        /* invoca il metodo delegato della classe */
        return setAll(nomeCampo, false);
    }


    /**
     * Esegue la modifica del check indicato in tutti i record.
     * <p/>
     *
     * @param campo da regolare
     *
     * @return il numero di record interessati
     */
    public int setAllFalso(Campi campo) {
        /* invoca il metodo delegato della classe */
        return setAllFalso(campo.get());
    }


    /**
     * Ritorna una lista dei CampiValore contenente i valori di tutti
     * i campi fisici per un dato record di questo modulo.
     * <p/>
     *
     * @param codice del record
     *
     * @return la lista dei valori per tutti i campi fisici.
     */
    public ArrayList<CampoValore> valoriRecord(int codice) {
        return this.valoriRecord(codice, this.getConnessione());
    }


    /**
     * Ritorna una lista dei CampiValore contenente i valori di tutti
     * i campi fisici per un dato record di questo modulo.
     * <p/>
     *
     * @param codice del record
     * @param conn la connessione da utilizzare
     *
     * @return la lista dei valori per tutti i campi fisici.
     */
    public ArrayList<CampoValore> valoriRecord(int codice, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<CampoValore> listaValori = null;
        Dati dati;
        try { // prova ad eseguire il codice
            dati = this.caricaRecord(codice, conn);
            listaValori = dati.getCampiValore(0);
            dati.close();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaValori;
    }


    /**
     * Carica un singolo record del Modulo.
     * <p/>
     * Crea la query per identificare il record <br>
     * Invia la query al db, che esegue e ritorna i Dati <br>
     *
     * @param codice codice chiave del record
     *
     * @return un oggetto dati con tutti i campi fisici del modello
     */
    public Dati caricaRecord(int codice) {
        return this.caricaRecord(codice, this.getConnessione());
    }


    /**
     * Carica un singolo record del Modulo.
     * <p/>
     * Crea la query per identificare il record <br>
     * Invia la query al db, che esegue e ritorna i Dati <br>
     *
     * @param codice codice chiave del record
     * @param conn la connessione da utilizzare
     *
     * @return un oggetto dati con tutti i campi fisici del modello
     */
    public Dati caricaRecord(int codice, Connessione conn) {
        return conn.caricaRecord(this.getModulo(), codice);
    }


    /**
     * Carica un set di records del Modulo.
     * <p/>
     * Crea la query per identificare il record <br>
     * Invia la query al db, che esegue e ritorna i Dati <br>
     *
     * @param filtro per selezionare i records
     *
     * @return un oggetto dati con tutti i campi fisici del modello
     */
    public Dati caricaRecords(Filtro filtro) {
        return this.caricaRecords(filtro, this.getConnessione());
    }


    /**
     * Carica un set di records del Modulo.
     * <p/>
     * Crea la query per identificare il record <br>
     * Invia la query al db, che esegue e ritorna i Dati <br>
     *
     * @param filtro per selezionare i records
     * @param conn la connessione da utilizzare
     *
     * @return un oggetto dati con tutti i campi fisici del modello
     */
    public Dati caricaRecords(Filtro filtro, Connessione conn) {
        return conn.caricaRecords(this.getModulo(), filtro);
    }


    /**
     * Ritorna tutti i valori di un campo di questo modulo.
     * <p/>
     *
     * @param nomeCampo il nome del campo del quale rendere i valori
     * @param nomeCampoFiltro il nome del campo sul quale costruire il filtro
     * @param valore di filtro
     *
     * @return la lista dei valori del campo.
     */
    public ArrayList valoriCampo(String nomeCampo, String nomeCampoFiltro, Object valore) {
        return this.valoriCampo(nomeCampo, nomeCampoFiltro, valore, this.getConnessione());
    }


    /**
     * Ritorna tutti i valori di un campo di questo modulo.
     * <p/>
     *
     * @param nomeCampo il nome del campo del quale rendere i valori
     * @param nomeCampoFiltro il nome del campo sul quale costruire il filtro
     * @param valore di filtro
     * @param conn la connessione da uilizzare
     *
     * @return la lista dei valori del campo.
     */
    public ArrayList valoriCampo(String nomeCampo,
                                 String nomeCampoFiltro,
                                 Object valore,
                                 Connessione conn) {
        /* variabili e costanti locali di lavoro */
        ArrayList lista = null;
        Filtro filtro;

        try { // prova ad eseguire il codice
            filtro = FiltroFactory.crea(nomeCampoFiltro, valore);
            lista = this.valoriCampo(nomeCampo, filtro, conn);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Ritorna tutti i valori di un campo di questo modulo.
     * <p/>
     *
     * @param nomeCampo il nome del campo del quale rendere i valori
     *
     * @return la lista dei valori del campo.
     */
    public ArrayList valoriCampo(String nomeCampo) {
        return this.valoriCampo(nomeCampo, (Filtro)null);
    }


    /**
     * Ritorna tutti i valori di un campo di questo modulo.
     * <p/>
     *
     * @param nomeCampo il nome del campo del quale rendere i valori
     * @param conn la connessione da uilizzare
     *
     * @return la lista dei valori del campo.
     */
    public ArrayList valoriCampo(String nomeCampo, Connessione conn) {
        return this.valoriCampo(nomeCampo, (Filtro)null, conn);
    }


    /**
     * Ritorna tutti i valori di un campo di questo modulo
     * per un dato filtro.
     * <p/>
     *
     * @param nomeCampo il nome del campo del quale rendere i valori
     * @param filtro il filtro da applicare (null per nessun filtro)
     *
     * @return la lista dei valori del campo.
     */
    public ArrayList valoriCampo(String nomeCampo, Filtro filtro) {
        return this.valoriCampo(nomeCampo, filtro, (Ordine)null);
    }


    /**
     * Ritorna tutti i valori di un campo di questo modulo
     * per un dato filtro.
     * <p/>
     *
     * @param nomeCampo il nome del campo del quale rendere i valori
     * @param filtro il filtro da applicare (null per nessun filtro)
     * @param conn la connessione da uilizzare
     *
     * @return la lista dei valori del campo.
     */
    public ArrayList valoriCampo(String nomeCampo, Filtro filtro, Connessione conn) {
        return this.valoriCampo(nomeCampo, filtro, (Ordine)null, conn);
    }


    /**
     * Ritorna tutti i valori di un campo di questo modulo
     * in un dato ordine.
     * <p/>
     *
     * @param nomeCampo il nome del campo del quale rendere i valori
     * @param ordine l'ordine da applicare (null per nessun ordine)
     *
     * @return la lista dei valori del campo.
     */
    public ArrayList valoriCampo(String nomeCampo, Ordine ordine) {
        return this.valoriCampo(nomeCampo, (Filtro)null, ordine);
    }


    /**
     * Ritorna tutti i valori di un campo di questo modulo
     * in un dato ordine.
     * <p/>
     *
     * @param nomeCampo il nome del campo del quale rendere i valori
     * @param ordine l'ordine da applicare (null per nessun ordine)
     * @param conn la connessione da uilizzare
     *
     * @return la lista dei valori del campo.
     */
    public ArrayList valoriCampo(String nomeCampo, Ordine ordine, Connessione conn) {
        return this.valoriCampo(nomeCampo, (Filtro)null, ordine, conn);
    }


    /**
     * Ritorna tutti i valori di un campo di questo modulo
     * per un dato filtro e in un dato ordine.
     * <p/>
     *
     * @param nomeCampo il nome del campo del quale rendere i valori
     * @param filtro il filtro da applicare (null per nessun filtro)
     * @param ordine l'ordine da applicare (null per nessun ordine)
     *
     * @return la lista dei valori del campo.
     */
    public ArrayList valoriCampo(String nomeCampo, Filtro filtro, Ordine ordine) {
        return this.valoriCampo(nomeCampo, filtro, ordine, this.getConnessione());
    }


    /**
     * Ritorna tutti i valori di un campo di questo modulo
     * per un dato filtro e in un dato ordine.
     * <p/>
     *
     * @param nomeCampo il nome del campo del quale rendere i valori
     * @param filtro il filtro da applicare (null per nessun filtro)
     * @param ordine l'ordine da applicare (null per nessun ordine)
     * @param conn la connessione da uilizzare
     *
     * @return la lista dei valori del campo.
     */
    public ArrayList valoriCampo(String nomeCampo, Filtro filtro, Ordine ordine, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        ArrayList valori = null;
        Campo campo;

        try { // prova ad eseguire il codice
            campo = this.getCampo(nomeCampo);
            if (campo != null) {
                valori = this.valoriCampo(campo, filtro, ordine, conn);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valori;
    }


    /**
     * Ritorna tutti i valori di un campo di questo modulo.
     * <p/>
     *
     * @param campo il campo del quale rendere i valori
     *
     * @return la lista dei valori del campo.
     */
    public ArrayList valoriCampo(Campo campo) {
        return this.valoriCampo(campo, (Filtro)null);
    }


    /**
     * Ritorna tutti i valori di un campo di questo modulo.
     * <p/>
     *
     * @param campo il campo del quale rendere i valori
     * @param conn la connessione d utilizzare
     *
     * @return la lista dei valori del campo.
     */
    public ArrayList valoriCampo(Campo campo, Connessione conn) {
        return this.valoriCampo(campo, (Filtro)null, conn);
    }


    /**
     * Ritorna tutti i valori di un campo di questo modulo
     * per un dato filtro.
     * <p/>
     *
     * @param campo il campo del quale rendere i valori
     * @param filtro il filtro da applicare (null per nessun filtro)
     *
     * @return la lista dei valori del campo.
     */
    public ArrayList valoriCampo(Campo campo, Filtro filtro) {
        return this.valoriCampo(campo, filtro, (Ordine)null);
    }


    /**
     * Ritorna tutti i valori di un campo di questo modulo
     * per un dato filtro.
     * <p/>
     *
     * @param campo il campo del quale rendere i valori
     * @param filtro il filtro da applicare (null per nessun filtro)
     * @param conn la connessione da utilizzare
     *
     * @return la lista dei valori del campo.
     */
    public ArrayList valoriCampo(Campo campo, Filtro filtro, Connessione conn) {
        return this.valoriCampo(campo, filtro, (Ordine)null, conn);
    }


    /**
     * Ritorna tutti i valori di un campo di questo modulo.
     * in un dato ordine.
     * <p/>
     *
     * @param campo il campo del quale rendere i valori
     * @param ordine l'ordine da applicare (null per nessun ordine)
     *
     * @return la lista dei valori del campo.
     */
    public ArrayList valoriCampo(Campo campo, Ordine ordine) {
        return this.valoriCampo(campo, (Filtro)null, ordine);
    }


    /**
     * Ritorna tutti i valori di un campo di questo modulo.
     * in un dato ordine.
     * <p/>
     *
     * @param campo il campo del quale rendere i valori
     * @param ordine l'ordine da applicare (null per nessun ordine)
     * @param conn la connessione da utilizzare
     *
     * @return la lista dei valori del campo.
     */
    public ArrayList valoriCampo(Campo campo, Ordine ordine, Connessione conn) {
        return this.valoriCampo(campo, (Filtro)null, ordine, conn);
    }


    /**
     * Ritorna tutti i valori di un campo di questo modulo.
     * per un dato filtro e in un dato ordine.
     * <p/>
     *
     * @param campo il campo del quale rendere i valori
     * @param filtro il filtro da applicare (null per nessun filtro)
     * @param ordine l'ordine da applicare (null per nessun ordine)
     *
     * @return la lista dei valori del campo.
     */
    public ArrayList valoriCampo(Campo campo, Filtro filtro, Ordine ordine) {
        return this.valoriCampo(campo, filtro, ordine, this.getConnessione());
    }


    /**
     * Ritorna tutti i valori di un campo di questo modulo.
     * per un dato filtro e in un dato ordine.
     * <p/>
     *
     * @param campo il campo del quale rendere i valori
     * @param filtro il filtro da applicare (null per nessun filtro)
     * @param ordine l'ordine da applicare (null per nessun ordine)
     * @param conn la connessione da utilizzare
     *
     * @return la lista dei valori del campo.
     */
    public ArrayList valoriCampo(Campo campo, Filtro filtro, Ordine ordine, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Modulo modulo;
        Query q;
        Dati dati;
        ArrayList valori = null;

        try {                                   // prova ad eseguire il codice

            /* recupera il modulo dal campo */
            modulo = campo.getModulo();

            /* crea una QuerySelezione per il modulo,
             * la esegue e recupera il risultato */
            q = new QuerySelezione(modulo);
            q.addCampo(campo);
            if (filtro != null) {
                q.addFiltro(filtro);
            }// fine del blocco if

            /* se manca l'ordine lo crea sul campo
            * altrimenti usa quello passato*/
            if (ordine == null) {
                ordine = new Ordine();
                ordine.add(campo);
            }// fine del blocco if
            q.setOrdine(ordine);

            dati = modulo.query().querySelezione(q, conn);
            if (dati != null) {
                valori = dati.getValoriColonna(campo);
                dati.close();
            }// fine del blocco if

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return valori;

    }


    /**
     * Ritorna il valore di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param nomeCampo il nome del campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     *
     * @return il valore del campo
     */
    public Object valoreCampo(String nomeCampo, int codice) {
        return this.valoreCampo(nomeCampo, codice, this.getConnessione());
    } /* fine del metodo */


    /**
     * Ritorna il valore di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param nomeCampo il nome del campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     * @param conn la connessione da utilizzare
     *
     * @return il valore del campo
     */
    public Object valoreCampo(String nomeCampo, int codice, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        Object valore = null;

        try {                                   // prova ad eseguire il codice
            campo = this.getModulo().getCampo(nomeCampo);
            valore = this.valoreCampo(campo, codice, conn);
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return valore;
    } /* fine del metodo */


    /**
     * Ritorna il valore di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param campo il campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     *
     * @return il valore del campo
     */
    public Object valoreCampo(Campo campo, int codice) {
        return this.valoreCampo(campo, codice, this.getConnessione());
    } /* fine del metodo */


    /**
     * Ritorna il valore di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param campo il campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     * @param conn la connessione da utilizzare
     *
     * @return il valore del campo
     */
    public Object valoreCampo(Campo campo, int codice, Connessione conn) {

        /* variabili e costanti locali di lavoro */
        Campo campoChiave;
        Object valore = null;
        Filtro filtro;
        ArrayList valori;

        try {                                   // prova ad eseguire il codice
            campoChiave = this.getCampoChiave();
            filtro = FiltroFactory.crea(campoChiave, codice);
            valori = this.valoriCampo(campo, filtro, conn);
            if (valori != null) {
                if (valori.size() > 0) {
                    valore = valori.get(0);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return valore;
    } /* fine del metodo */


    /**
     * Ritorna il valore di un campo di questo modulo
     * per un dato valore chiave di un'altro campo.
     * <p/>
     *
     * @param campoChiave il nome del campo su cui effettuare la ricerca
     * @param chiave valore chiave da ricercare
     * @param campoValore il nome del campo del quale rendere il valore
     *
     * @return il valore del campo
     */
    public Object valore(String campoChiave, Object chiave, String campoValore) {
        return this.valore(campoChiave, chiave, campoValore, this.getConnessione());
    }


    /**
     * Ritorna il valore di un campo di questo modulo
     * per un dato valore chiave di un'altro campo.
     * <p/>
     *
     * @param campoChiave il nome del campo su cui effettuare la ricerca
     * @param chiave valore chiave da ricercare
     * @param campoValore il nome del campo del quale rendere il valore
     * @param conn la connessione da utilizzare
     *
     * @return il valore del campo
     */
    public Object valore(String campoChiave, Object chiave, String campoValore, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        int codRec;

        try { // prova ad eseguire il codice
            codRec = this.valoreChiave(campoChiave, chiave, conn);
            valore = this.valoreCampo(campoValore, codRec, conn);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Ritorna il valore intero di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param campo il campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     *
     * @return il valore del campo (0 se non valido)
     */
    public int valoreInt(Campo campo, int codice) {
        return this.valoreInt(campo, codice, this.getConnessione());
    }


    /**
     * Ritorna il valore intero di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param campo il campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     * @param conn la connessione da utilizzare
     *
     * @return il valore del campo (0 se non valido)
     */
    public int valoreInt(Campo campo, int codice, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int intero = 0;
        Object valore;

        try {                                   // prova ad eseguire il codice
            valore = this.valoreCampo(campo, codice, conn);
            intero = Libreria.objToInt(valore);
        } catch (Exception unErrore) {           // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return intero;
    }


    /**
     * Ritorna il valore intero di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param nomeCampo il nome del campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     *
     * @return il valore del campo
     */
    public int valoreInt(String nomeCampo, int codice) {
        /* variabili e costanti locali di lavoro */
        int intero = 0;
        Campo campo;

        try {                                   // prova ad eseguire il codice
            campo = this.getModulo().getCampo(nomeCampo);
            intero = this.valoreInt(campo, codice);
        } catch (Exception unErrore) {           // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return intero;
    }


    /**
     * Ritorna il valore intero di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param nomeCampo il nome del campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     * @param conn la connessione da utilizzare
     *
     * @return il valore del campo
     */
    public int valoreInt(String nomeCampo, int codice, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int intero = 0;
        Campo campo;

        try {                                   // prova ad eseguire il codice
            campo = this.getModulo().getCampo(nomeCampo);
            intero = this.valoreInt(campo, codice, conn);
        } catch (Exception unErrore) {           // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return intero;
    }


    /**
     * Ritorna il valore double di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param campo il campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     *
     * @return il valore del campo (0 se non valido)
     */
    public double valoreDouble(Campo campo, int codice) {
        return this.valoreDouble(campo, codice, this.getConnessione());
    }


    /**
     * Ritorna il valore double di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param campo il campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     * @param conn connessione da utilizzare
     *
     * @return il valore del campo (0 se non valido)
     */
    public double valoreDouble(Campo campo, int codice, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        double doppio = 0;
        Object valore;

        try {                                   // prova ad eseguire il codice
            valore = this.valoreCampo(campo, codice, conn);
            doppio = Libreria.getDouble(valore);
        } catch (Exception unErrore) {           // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return doppio;
    }


    /**
     * Ritorna il valore double di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param nomeCampo il nome del campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     *
     * @return il valore del campo
     */
    public double valoreDouble(String nomeCampo, int codice) {
        return this.valoreDouble(nomeCampo, codice, this.getConnessione());
    }


    /**
     * Ritorna il valore double di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param nomeCampo il nome del campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     * @param conn connessione da utilizzare
     *
     * @return il valore del campo
     */
    public double valoreDouble(String nomeCampo, int codice, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        double doppio = 0;
        Campo campo;

        try {                                   // prova ad eseguire il codice
            campo = this.getModulo().getCampo(nomeCampo);
            doppio = this.valoreDouble(campo, codice, conn);
        } catch (Exception unErrore) {           // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return doppio;
    }


    /**
     * Ritorna il valore Date di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param campo il campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     *
     * @return il valore del campo (data vuota se non valido)
     */
    public Date valoreData(Campo campo, int codice) {
        return this.valoreData(campo, codice, this.getConnessione());
    }


    /**
     * Ritorna il valore Date di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param campo il campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     * @param conn connessione da utilizzare
     *
     * @return il valore del campo (data vuota se non valido)
     */
    public Date valoreData(Campo campo, int codice, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Date data = Lib.Data.getVuota();
        Object valore;

        try {                                   // prova ad eseguire il codice
            valore = this.valoreCampo(campo, codice, conn);
            data = Libreria.getDate(valore);
        } catch (Exception unErrore) {           // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return data;
    }


    /**
     * Ritorna il valore Date di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param nomeCampo il nome del campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     *
     * @return il valore del campo
     */
    public Date valoreData(String nomeCampo, int codice) {
        return this.valoreData(nomeCampo, codice, this.getConnessione());
    }


    /**
     * Ritorna il valore Date di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param nomeCampo il nome del campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     * @param conn connessione da utilizzare
     *
     * @return il valore del campo
     */
    public Date valoreData(String nomeCampo, int codice, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Date data = null;
        Modulo modulo;
        Campo campo;

        try {                                   // prova ad eseguire il codice
            modulo = this.getModulo();
            campo = modulo.getCampo(nomeCampo);
            data = this.valoreData(campo, codice, conn);
        } catch (Exception unErrore) {           // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return data;
    }


    /**
     * Ritorna il valore stringa di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param campo il campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     *
     * @return il valore del campo (stringa vuota se non valido)
     */
    public String valoreStringa(Campo campo, int codice) {
        return this.valoreStringa(campo, codice, this.getConnessione());
    }


    /**
     * Ritorna il valore stringa di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param campo il campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     * @param conn connessione da utilizzare
     *
     * @return il valore del campo (stringa vuota se non valido)
     */
    public String valoreStringa(Campo campo, int codice, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        Object valore;

        try {                                   // prova ad eseguire il codice
            valore = this.valoreCampo(campo, codice, conn);
            stringa = Lib.Testo.getStringa(valore);
        } catch (Exception unErrore) {           // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna il valore stringa di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param nomeCampo il nome del campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     *
     * @return il valore del campo (stringa vuota se non valido)
     */
    public String valoreStringa(String nomeCampo, int codice) {
        return this.valoreStringa(nomeCampo, codice, this.getConnessione());
    }


    /**
     * Ritorna il valore stringa di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param nomeCampo il nome del campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     * @param conn connessione da utilizzare
     *
     * @return il valore del campo (stringa vuota se non valido)
     */
    public String valoreStringa(String nomeCampo, int codice, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        Campo campo;

        try {                                   // prova ad eseguire il codice
            campo = this.getModulo().getCampo(nomeCampo);
            stringa = this.valoreStringa(campo, codice, conn);
        } catch (Exception unErrore) {           // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna il valore booleano di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param campo il campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     *
     * @return il valore booleano del campo
     */
    public boolean valoreBool(Campo campo, int codice) {
        return this.valoreBool(campo, codice, this.getConnessione());
    }


    /**
     * Ritorna il valore booleano di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param campo il campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     * @param conn connessione da utilizzare
     *
     * @return il valore booleano del campo
     */
    public boolean valoreBool(Campo campo, int codice, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean bool = false;
        Object valore = null;

        try {                                   // prova ad eseguire il codice
            valore = this.valoreCampo(campo, codice, conn);
            bool = Libreria.objToBool(valore);
        } catch (Exception unErrore) {           // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return bool;
    }


    /**
     * Ritorna il valore booleano di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param nomeCampo il nome del campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     *
     * @return il valore booleano del campo
     */
    public boolean valoreBool(String nomeCampo, int codice) {
        return this.valoreBool(nomeCampo, codice, this.getConnessione());
    }


    /**
     * Ritorna il valore booleano di un campo di questo modulo
     * per un dato codice chiave.
     * <p/>
     *
     * @param nomeCampo il nome del campo del quale rendere il valore
     * @param codice il codice chiave da ricercare
     * @param conn connessione da utilizzare
     *
     * @return il valore booleano del campo
     */
    public boolean valoreBool(String nomeCampo, int codice, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean bool = false;
        Campo campo = null;

        try {                                   // prova ad eseguire il codice
            campo = this.getModulo().getCampo(nomeCampo);
            bool = this.valoreBool(campo, codice, conn);
        } catch (Exception unErrore) {           // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return bool;
    }


    /**
     * Ritorna una matrice con codici e valori di un campo
     * <p/>
     *
     * @param nomeCampo nome del campo di questo modulo, da cui recuperare i valori
     *
     * @return matriceDoppia matrice contenente i codici e i valori
     */
    public MatriceDoppia valoriDoppi(String nomeCampo) {
        return this.valoriDoppi(nomeCampo, this.getConnessione());
    }


    /**
     * Ritorna una matrice con codici e valori di un campo
     * <p/>
     *
     * @param nomeCampo nome del campo di questo modulo, da cui recuperare i valori
     * @param conn la connessione da utilizzare
     *
     * @return matriceDoppia matrice contenente i codici e i valori
     */
    public MatriceDoppia valoriDoppi(String nomeCampo, Connessione conn) {
        return this.valoriDoppi(nomeCampo, (Filtro)null, conn);
    }


    /**
     * Ritorna una matrice con codici e valori di un campo
     * per un dato filtro.
     * <p/>
     *
     * @param nomeCampo nome del campo di questo modulo, da cui recuperare i valori
     * @param filtro filtro da applicare (null per nessun filtro)
     *
     * @return matriceDoppia matrice contenente i codici e i valori
     */
    public MatriceDoppia valoriDoppi(String nomeCampo, Filtro filtro) {
        return this.valoriDoppi(nomeCampo, filtro, this.getConnessione());
    }


    /**
     * Ritorna una matrice con codici e valori di un campo
     * per un dato filtro.
     * <p/>
     *
     * @param nomeCampo nome del campo di questo modulo, da cui recuperare i valori
     * @param filtro filtro da applicare (null per nessun filtro)
     * @param conn la connessione da utilizzare
     *
     * @return matriceDoppia matrice contenente i codici e i valori
     */
    public MatriceDoppia valoriDoppi(String nomeCampo, Filtro filtro, Connessione conn) {
        return this.valoriDoppi(nomeCampo, filtro, (Ordine)null, conn);
    }


    /**
     * Ritorna una matrice con codici e valori di un campo
     * in un dato ordine.
     * <p/>
     *
     * @param nomeCampo nome del campo di questo modulo, da cui recuperare i valori
     * @param ordine ordine da applicare (null per nessun ordine)
     *
     * @return matriceDoppia matrice contenente i codici e i valori
     */
    public MatriceDoppia valoriDoppi(String nomeCampo, Ordine ordine) {
        return this.valoriDoppi(nomeCampo, ordine, this.getConnessione());
    }


    /**
     * Ritorna una matrice con codici e valori di un campo
     * in un dato ordine.
     * <p/>
     *
     * @param nomeCampo nome del campo di questo modulo, da cui recuperare i valori
     * @param ordine ordine da applicare (null per nessun ordine)
     * @param conn la connessione da utilizzare
     *
     * @return matriceDoppia matrice contenente i codici e i valori
     */
    public MatriceDoppia valoriDoppi(String nomeCampo, Ordine ordine, Connessione conn) {
        return this.valoriDoppi(nomeCampo, (Filtro)null, ordine, conn);
    }


    /**
     * Ritorna una matrice con codici e valori di un campo
     * per un dato filtro e con un dato ordine.
     * <p/>
     *
     * @param nomeCampo nome del campo di questo modulo, da cui recuperare i valori
     * @param filtro filtro da applicare (null per nessun filtro)
     * @param ordine ordine da applicare (null per nessun ordine)
     *
     * @return matriceDoppia matrice contenente i codici e i valori
     */
    public MatriceDoppia valoriDoppi(String nomeCampo, Filtro filtro, Ordine ordine) {
        return this.valoriDoppi(nomeCampo, filtro, ordine, this.getConnessione());
    }


    /**
     * Ritorna una matrice con codici e valori di un campo
     * per un dato filtro e con un dato ordine.
     * <p/>
     *
     * @param nomeCampo nome del campo di questo modulo, da cui recuperare i valori
     * @param filtro filtro da applicare (null per nessun filtro)
     * @param ordine ordine da applicare (null per nessun ordine)
     * @param conn la connessione da utilizzare
     *
     * @return matriceDoppia matrice contenente i codici e i valori
     */
    public MatriceDoppia valoriDoppi(String nomeCampo,
                                     Filtro filtro,
                                     Ordine ordine,
                                     Connessione conn) {
        /* variabili e costanti locali di lavoro */
        MatriceDoppia matrice = null;
        Campo campoValore = null;

        try { // prova ad eseguire il codice
            campoValore = this.getCampo(nomeCampo);
            matrice = this.valoriDoppi(campoValore, filtro, ordine, conn);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return matrice;
    }


    /**
     * Ritorna una matrice con codici e valori di un campo
     * per un dato filtro.
     * <p/>
     *
     * @param campo campo di questo modulo, da cui recuperare i valori
     * @param filtro filtro da applicare (null per nessun filtro)
     *
     * @return matriceDoppia matrice contenente i codici e i valori
     */
    public MatriceDoppia valoriDoppi(Campo campo, Filtro filtro) {
        return this.valoriDoppi(campo, filtro, this.getConnessione());
    }


    /**
     * Ritorna una matrice con codici e valori di un campo
     * per un dato filtro.
     * <p/>
     *
     * @param campo campo di questo modulo, da cui recuperare i valori
     * @param filtro filtro da applicare (null per nessun filtro)
     * @param conn la connessione da utilizzare
     *
     * @return matriceDoppia matrice contenente i codici e i valori
     */
    public MatriceDoppia valoriDoppi(Campo campo, Filtro filtro, Connessione conn) {
        return this.valoriDoppi(campo, filtro, (Ordine)null, conn);
    }


    /**
     * Ritorna una matrice con codici e valori di un campo
     * in un dato ordine.
     * <p/>
     *
     * @param campo campo di questo modulo, da cui recuperare i valori
     * @param ordine ordine da applicare (null per nessun ordine)
     *
     * @return matriceDoppia matrice contenente i codici e i valori
     */
    public MatriceDoppia valoriDoppi(Campo campo, Ordine ordine) {
        return this.valoriDoppi(campo, ordine, this.getConnessione());
    }


    /**
     * Ritorna una matrice con codici e valori di un campo
     * in un dato ordine.
     * <p/>
     *
     * @param campo campo di questo modulo, da cui recuperare i valori
     * @param ordine ordine da applicare (null per nessun ordine)
     * @param conn la connessione da utilizzare
     *
     * @return matriceDoppia matrice contenente i codici e i valori
     */
    public MatriceDoppia valoriDoppi(Campo campo, Ordine ordine, Connessione conn) {
        return this.valoriDoppi(campo, (Filtro)null, ordine, conn);
    }


    /**
     * Ritorna una matrice con codici e valori di un campo
     * per un dato filtro e con un dato ordine.
     * <p/>
     *
     * @param campo campo di questo modulo, da cui recuperare i valori
     * @param filtro filtro da applicare (null per nessun filtro)
     * @param ordine ordine da applicare (null per nessun ordine)
     *
     * @return matriceDoppia matrice contenente i codici e i valori
     */
    public MatriceDoppia valoriDoppi(Campo campo, Filtro filtro, Ordine ordine) {
        return this.valoriDoppi(campo, filtro, ordine, this.getConnessione());
    }


    /**
     * Ritorna una matrice con codici e valori di un campo
     * per un dato filtro e con un dato ordine.
     * <p/>
     *
     * @param campo campo di questo modulo, da cui recuperare i valori
     * @param filtro filtro da applicare (null per nessun filtro)
     * @param ordine ordine da applicare (null per nessun ordine)
     * @param conn la connessione da utilizzare
     *
     * @return matriceDoppia matrice contenente i codici e i valori
     */
    public MatriceDoppia valoriDoppi(Campo campo, Filtro filtro, Ordine ordine, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        MatriceDoppia matrice = null;
        Campo campoChiave;
        Campo campoValore;
        Query query;
        Dati dati;

        try { // prova ad eseguire il codice
            campoChiave = this.getCampoChiave();
            campoValore = campo;
            matrice = new MatriceDoppia();

            query = new QuerySelezione(this.getModulo());
            query.addCampo(campoChiave);
            query.addCampo(campoValore);

            if (filtro != null) {
                query.addFiltro(filtro);
            }// fine del blocco if

            if (ordine != null) {
                query.setOrdine(ordine);
            }// fine del blocco if

            dati = this.querySelezione(query, conn);
            matrice.setCodici(dati.getValoriColonna(campoChiave));
            matrice.setValori(dati.getValoriColonna(campoValore));
            dati.close();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return matrice;
    }


    /**
     * Ritorna il valore chiave del primo record
     * di questo modulo dove un dato campo ha un dato valore
     * <p/>
     *
     * @param nomeCampo su cui effettuare la ricerca
     * @param valore di cui testare l'esistenza nel campo di ricerca
     *
     * @return il valore del campo chiave, 0 se non trovato
     */
    public int valoreChiave(String nomeCampo, Object valore) {
        return this.sqlValoreChiave(nomeCampo, valore);
    } /* fine del metodo */


    /**
     * Ritorna il valore chiave del primo record
     * di questo modulo dove un dato campo ha un dato valore
     * <p/>
     * Se il record manca, lo crea
     *
     * @param nomeCampo su cui effettuare la ricerca
     * @param valore di cui testare l'esistenza nel campo di ricerca
     *
     * @return il valore del campo chiave, 0 se non trovato
     */
    public int creaSeManca(String nomeCampo, Object valore) {
        /* variabili e costanti locali di lavoro */
        int cod = 0;

        try { // prova ad eseguire il codice
            cod = this.valoreChiave(nomeCampo, valore);

            if (cod < 1) {
                cod = this.nuovoRecord();
                this.registraRecordValore(cod, nomeCampo, valore);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return cod;
    } /* fine del metodo */


    /**
     * Ritorna il valore chiave del primo record
     * di questo modulo dove un dato campo ha un dato valore
     * <p/>
     *
     * @param nomeCampo su cui effettuare la ricerca
     * @param valore di cui testare l'esistenza nel campo di ricerca
     * @param conn la connessione da utilizzare
     *
     * @return il valore del campo chiave, 0 se non trovato
     */
    public int valoreChiave(String nomeCampo, Object valore, Connessione conn) {
        return sqlValoreChiave(nomeCampo, valore, conn);
    } /* fine del metodo */


    /**
     * Ritorna il valore chiave del primo record
     * di questo modulo che soddisfa un dato filtro
     * <p/>
     *
     * @param filtro il filtro da applicare (null per nessun filtro)
     *
     * @return il valore del campo chiave, 0 se non trovato
     */
    public int valoreChiave(Filtro filtro) {
        return this.valoreChiave(filtro, this.getConnessione());
    } /* fine del metodo */


    /**
     * Ritorna il valore chiave del primo record
     * di questo modulo che soddisfa un dato filtro
     * <p/>
     *
     * @param filtro il filtro da applicare (null per nessun filtro)
     * @param conn la connessione da utilizzare
     *
     * @return il valore del campo chiave, 0 se non trovato
     */
    public int valoreChiave(Filtro filtro, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int valoreSingolo = 0;
        int[] valori;

        try { // prova ad eseguire il codice
            valori = this.valoriChiave(filtro, (Ordine)null, conn);

            if (valori != null && valori.length >= 1) {
                valoreSingolo = valori[0];
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return valoreSingolo;
    } /* fine del metodo */


    /**
     * Ritorna una lista di valori del campo chiave di questo modulo
     * <p/>
     *
     * @return una lista di valori per il campo chiave
     */
    public int[] valoriChiave() {
        return this.valoriChiave(this.getConnessione());
    } /* fine del metodo */


    /**
     * Ritorna una lista di valori del campo chiave di questo modulo
     * <p/>
     *
     * @param conn la connessione da utilizzare
     *
     * @return una lista di valori per il campo chiave
     */
    public int[] valoriChiave(Connessione conn) {
        return this.valoriChiave((Filtro)null, (Ordine)null, conn);
    } /* fine del metodo */


    /**
     * Ritorna una lista di valori del campo chiave di questo modulo
     * relativa a un dato filtro
     * <p/>
     *
     * @param filtro il filtro da applicare (null per nessun filtro)
     *
     * @return una lista di valori per il campo chiave
     */
    public int[] valoriChiave(Filtro filtro) {
        return this.valoriChiave(filtro, this.getConnessione());
    } /* fine del metodo */


    /**
     * Ritorna una lista di valori del campo chiave di questo modulo
     * relativa a un dato filtro
     * <p/>
     *
     * @param filtro il filtro da applicare (null per nessun filtro)
     * @param conn la connessione da utilizzare
     *
     * @return una lista di valori per il campo chiave
     */
    public int[] valoriChiave(Filtro filtro, Connessione conn) {
        return this.valoriChiave(filtro, (Ordine)null, conn);
    } /* fine del metodo */


    /**
     * Ritorna una lista di valori del campo chiave di questo modulo
     * in un dato ordine
     * <p/>
     *
     * @param ordine l'ordine da applicare (null per nessun ordine)
     *
     * @return una lista di valori per il campo chiave
     */
    public int[] valoriChiave(Ordine ordine) {
        return this.valoriChiave(ordine, this.getConnessione());
    } /* fine del metodo */


    /**
     * Ritorna una lista di valori del campo chiave di questo modulo
     * in un dato ordine
     * <p/>
     *
     * @param ordine l'ordine da applicare (null per nessun ordine)
     * @param conn la connessione da utilizzare
     *
     * @return una lista di valori per il campo chiave
     */
    public int[] valoriChiave(Ordine ordine, Connessione conn) {
        return this.valoriChiave((Filtro)null, ordine, conn);
    } /* fine del metodo */


    /**
     * Ritorna una lista di valori chiave di questo modulo
     * relativa a un dato filtro e in un dato ordine
     * <p/>
     *
     * @param filtro il filtro da applicare (null per nessun filtro)
     * @param ordine l'ordine da applicare (null per nessun ordine)
     *
     * @return una lista di valori per il campo chiave
     */
    public int[] valoriChiave(Filtro filtro, Ordine ordine) {
        return this.valoriChiave(filtro, ordine, this.getConnessione());
    } /* fine del metodo */


    /**
     * Ritorna una lista di valori chiave di questo modulo
     * relativa a un dato filtro e in un dato ordine
     * <p/>
     *
     * @param filtro il filtro da applicare (null per nessun filtro)
     * @param ordine l'ordine da applicare (null per nessun ordine)
     * @param conn la connessione da utilizzare
     *
     * @return una lista di valori per il campo chiave
     */
    public int[] valoriChiave(Filtro filtro, Ordine ordine, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int[] valoriChiave = null;
        Campo campoChiave;
        String nomeCampoChiave;
        ArrayList valori;
        Object valore;
        Integer valoreInteger;
        int valoreInt;

        try { // prova ad eseguire il codice
            campoChiave = this.getCampoChiave();
            continua = (campoChiave != null);

            if (continua) {
                nomeCampoChiave = campoChiave.getNomeInterno();
                valori = this.valoriCampo(nomeCampoChiave, filtro, ordine, conn);
                valoriChiave = new int[valori.size()];
                for (int k = 0; k < valori.size(); k++) {
                    valore = valori.get(k);
                    valoreInteger = (Integer)valore;
                    valoreInt = valoreInteger;
                    valoriChiave[k] = valoreInt;
                } // fine del ciclo for
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valoriChiave;
    } /* fine del metodo */


    /**
     * Ritorna una lista di valori chiave di questo modulo
     * relativa a un dato campo e un dato valore
     * <p/>
     *
     * @param campo - nome del campo
     * @param valore - del campo che devono avere i records per essere inclusi
     *
     * @return una lista di valori per il campo chiave
     */
    public int[] valoriChiave(String campo, Object valore) {
        return valoriChiave(campo, valore, this.getConnessione());
    } /* fine del metodo */

    /**
     * Ritorna una lista di valori chiave di questo modulo
     * relativa a un dato campo e un dato valore
     * <p/>
     *
     * @param campo - nome del campo
     * @param valore - del campo che devono avere i records per essere inclusi
     * @param conn - la connessione da utilizzare
     *
     * @return una lista di valori per il campo chiave
     */
    public int[] valoriChiave(String campo, Object valore, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int[] chiavi = null;
        Filtro filtro;

        try { // prova ad eseguire il codice
            filtro = FiltroFactory.crea(campo, valore);

            if (filtro != null) {
                chiavi = this.valoriChiave(filtro, conn);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return chiavi;
    } /* fine del metodo */



    /**
     * Ritorna il numero di records di questo modulo.
     * <p/>
     * Conta i records sul campo chiave che non e' mai nullo.
     *
     * @return il numero di records non nulli sul campo
     *         corrispondenti al filtro dato
     */
    public int contaRecords() {
        return this.contaRecords(this.getConnessione());
    }


    /**
     * Ritorna il numero di records di questo modulo.
     * <p/>
     * Conta i records sul campo chiave che non e' mai nullo.
     *
     * @param conn la connessione da utilizzare
     *
     * @return il numero di records non nulli sul campo
     *         corrispondenti al filtro dato
     */
    public int contaRecords(Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Campo campoChiave;
        String nomeCampoChiave;
        int quanti = 0;

        try { // prova ad eseguire il codice
            campoChiave = this.getCampoChiave();
            nomeCampoChiave = campoChiave.getNomeInterno();
            quanti = this.contaRecords(nomeCampoChiave, (Filtro)null, conn);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return quanti;
    }


    /**
     * Ritorna il numero di records non nulli in un campo di questo modulo.
     * <p/>
     *
     * @param nomeCampo il nome del campo da contare
     * @param filtro il filtro da applicare (null per nessun filtro)
     *
     * @return il numero di records non nulli sul campo
     *         corrispondenti al filtro dato
     */
    public int contaRecords(String nomeCampo, Filtro filtro) {
        return this.contaRecords(nomeCampo, filtro, this.getConnessione());
    }


    /**
     * Ritorna il numero di records non nulli in un campo di questo modulo.
     * <p/>
     *
     * @param nomeCampo il nome del campo da contare
     * @param filtro il filtro da applicare (null per nessun filtro)
     * @param conn la connessione da utilizzare
     *
     * @return il numero di records non nulli sul campo
     *         corrispondenti al filtro dato
     */
    public int contaRecords(String nomeCampo, Filtro filtro, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int quanti = 0;
        Campo campo;

        try { // prova ad eseguire il codice
            campo = this.getCampo(nomeCampo);
            quanti = this.contaRecords(campo, filtro, conn);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return quanti;
    }


    /**
     * Ritorna il numero di records non nulli in un campo di questo modulo.
     * <p/>
     *
     * @param campo il campo da contare
     * @param filtro il filtro da applicare (null per nessun filtro)
     *
     * @return il numero di records non nulli sul campo
     *         corrispondenti al filtro dato
     */
    public int contaRecords(Campo campo, Filtro filtro) {
        return this.contaRecords(campo, filtro, this.getConnessione());
    }


    /**
     * Ritorna il numero di records non nulli in un campo di questo modulo.
     * <p/>
     *
     * @param campo il campo da contare
     * @param filtro il filtro da applicare (null per nessun filtro)
     * @param conn la connessione da utilizzare
     *
     * @return il numero di records non nulli sul campo
     *         corrispondenti al filtro dato
     */
    public int contaRecords(Campo campo, Filtro filtro, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int quanti = 0;
        Query query;
        Dati dati;
        Object valore;
        Number numero;

        try { // prova ad eseguire il codice

            query = QueryFactory.conta(campo, filtro);

            /* se la connessione non è specificata usa quella del modulo */
            if (conn == null) {
                conn = this.getModulo().getConnessione();
            }// fine del blocco if

            dati = this.querySelezione(query, conn);
            valore = dati.getValueAt(0, 0);
            if (valore instanceof Number) {
                numero = (Number)valore;
                quanti = numero.intValue();
            }// fine del blocco if
            dati.close();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return quanti;
    }


    /**
     * Ritorna il numero di records di questo modulo.
     * <p/>
     * Conta sul campo chiave che non e' mai nullo.
     *
     * @param filtro il filtro da applicare (null per nessun filtro)
     *
     * @return il numero di records corrispondenti al filtro dato
     */
    public int contaRecords(Filtro filtro) {
        return this.contaRecords(filtro, this.getConnessione());
    }


    /**
     * Ritorna il numero di records di questo modulo.
     * <p/>
     * Conta sul campo chiave che non e' mai nullo.
     *
     * @param filtro il filtro da applicare (null per nessun filtro)
     * @param conn la connessione da utilizzare
     *
     * @return il numero di records corrispondenti al filtro dato
     */
    public int contaRecords(Filtro filtro, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int quanti = 0;

        try { // prova ad eseguire il codice
            quanti = this.contaRecords(this.getCampoChiave(), filtro, conn);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return quanti;
    }


    /**
     * Elimina un record esistente.
     * <p/>
     *
     * @param codice il codice chiave del record
     *
     * @return true se riuscito
     */
    public boolean eliminaRecord(int codice) {
        return this.eliminaRecord(codice, this.getConnessione());
    } /* fine del metodo */


    /**
     * Elimina un record esistente.
     * <p/>
     *
     * @param codice il codice chiave del record
     * @param conn connessione da utilizzare
     *
     * @return true se riuscito
     */
    public boolean eliminaRecord(int codice, Connessione conn) {
        return conn.eliminaRecord(this.getModulo(), codice);
    } /* fine del metodo */


    /**
     * Elimina tutti i records della tavola.
     * <p/>
     *
     * @return true se riuscito
     */
    public boolean eliminaRecords() {
        return this.getConnessione().eliminaRecords(this.getModulo());
    }


    /**
     * Elimina tutti i records corrispondenti a un dato filtro.
     * <p/>
     * ATTENZIONE! QUESTO METODO NON INVOCA I TRIGGER NEL MODELLO!
     *
     * @param filtro il filtro da applicare
     *
     * @return true se riuscito
     */
    public boolean eliminaRecords(Filtro filtro) {
        return this.eliminaRecords(filtro, this.getConnessione());
    }


    /**
     * Elimina tutti i records corrispondenti a un dato filtro.
     * <p/>
     * ATTENZIONE! QUESTO METODO NON INVOCA I TRIGGER NEL MODELLO!
     *
     * @param filtro il filtro da applicare
     * @param conn la connessione da utilizzare
     *
     * @return true se riuscito
     */
    public boolean eliminaRecords(Filtro filtro, Connessione conn) {
        return conn.eliminaRecords(this.getModulo(), filtro);
    }


    /**
     * Crea un nuovo record per questo modulo con valori forniti.
     * <p/>
     * Usa il valore specificato per tutti i campi forniti.
     * I campi non forniti vengono riempiti con i valori di default.
     *
     * @param valori da registrare
     *
     * @return il codice del record creato, -1 se non riuscito
     */
    public int nuovoRecord(ArrayList<CampoValore> valori) {
        return this.nuovoRecord(valori, this.getConnessione());
    }

    /**
     * Crea un nuovo record per questo modulo con valori forniti.
     * <p/>
     * Usa il valore specificato per tutti i campi forniti.
     * I campi non forniti vengono riempiti con i valori di default.
     *
     * @param valori da registrare
     *
     * @return il codice del record creato, -1 se non riuscito
     */
    public int nuovoRecord(SetValori valori) {
        return this.nuovoRecord(valori.getListaValori());
    }



    /**
     * Crea un nuovo record per questo modulo con valori forniti.
     * <p/>
     * Usa il valore specificato per tutti i campi forniti.
     * I campi non forniti vengono riempiti con i valori di default.
     *
     * @param valori da registrare
     * @param conn connessione da utilizzare
     *
     * @return il codice del record creato, -1 se non riuscito
     */
    public int nuovoRecord(ArrayList<CampoValore> valori, Connessione conn) {
        return conn.nuovoRecord(this.getModulo(), valori);
    }


    /**
     * Crea un nuovo record per questo modulo con valori forniti.
     * <p/>
     * Usa il valore specificato per tutti i campi forniti.
     * I campi non forniti vengono riempiti con i valori di default.
     *
     * @param valori da registrare
     * @param conn connessione da utilizzare
     *
     * @return il codice del record creato, -1 se non riuscito
     */
    public int nuovoRecord(SetValori valori, Connessione conn) {
        return this.nuovoRecord(valori.getListaValori(), conn);
    }



    /**
     * Crea un nuovo record per questo modulo col valore fornito.
     * <p/>
     * Usa il valore specificato per il campo specificato.
     * Gli altri campi vengono riempiti con i valori di default.
     *
     * @param nomeCampo da registrare
     * @param valore da registrare
     *
     * @return il codice del record creato, -1 se non riuscito
     */
    public int nuovoRecord(String nomeCampo, Object valore) {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        ArrayList<CampoValore> valori;
        CampoValore campoValore;

        try { // prova ad eseguire il codice
            valori = new ArrayList<CampoValore>();
            campoValore = new CampoValore(nomeCampo, valore);
            valori.add(campoValore);
            codice = this.nuovoRecord(valori);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Crea un nuovo record per questo modulo.
     * <p/>
     * Tutti i campi vengono riempiti con i valori di default.
     *
     * @return il codice del record creato, -1 se fallito
     */
    public int nuovoRecord() {
        return this.getConnessione().nuovoRecord(this.getModulo());
    }


    /**
     * Crea un nuovo record per questo modulo.
     * <p/>
     * Tutti i campi vengono riempiti con i valori di default.
     *
     * @param conn connessione da utilizzare
     *
     * @return il codice del record creato, -1 se fallito
     */
    public int nuovoRecord(Connessione conn) {
        return conn.nuovoRecord(this.getModulo());
    }


    /**
     * Registra un record esistente.
     * <p/>
     * Usa il valore Archivio dei campi
     *
     * @param codice il codice chiave del record
     * @param campi la lista dei campi da registrare
     *
     * @return true se riuscito
     */
    public boolean registraRecord(int codice, ArrayList<Campo> campi) {
        return this.registraRecord(codice, campi, this.getConnessione());
    } /* fine del metodo */


    /**
     * Registra un record esistente.
     * <p/>
     * Usa il valore Archivio dei campi
     *
     * @param codice il codice chiave del record
     * @param campi la lista dei campi da registrare
     * @param conn connessione da utilizzare
     *
     * @return true se riuscito
     */
    public boolean registraRecord(int codice, ArrayList<Campo> campi, Connessione conn) {
        return conn.registraRecord(this.getModulo(), codice, campi);
    } /* fine del metodo */


    /**
     * Registra un record esistente.
     * <p/>
     * Usa i campi e i valori forniti
     *
     * @param codice il codice chiave del record
     * @param campiValore la lista dei campi con valori (oggetti CampoValore)
     *
     * @return true se riuscito
     */
    public boolean registraRecordValori(int codice, ArrayList<CampoValore> campiValore) {
        return this.registraRecordValori(codice, campiValore, this.getConnessione());
    } /* fine del metodo */


    /**
     * Registra un record esistente.
     * <p/>
     * Usa i campi e i valori forniti
     *
     * @param codice il codice chiave del record
     * @param campiValore la lista dei campi con valori (oggetti CampoValore)
     * @param conn connessione da utilizzare
     *
     * @return true se riuscito
     */
    public boolean registraRecordValori(int codice,
                                        ArrayList<CampoValore> campiValore,
                                        Connessione conn) {
        return conn.registraRecordValori(this.getModulo(), codice, campiValore);
    } /* fine del metodo */


    /**
     * Registra un record esistente.
     * <p/>
     * Usa il campo ed il valore forniti
     *
     * @param codice il codice chiave del record
     * @param campo il campo da registrare
     * @param valore il valore da assegnare
     *
     * @return true se riuscito
     */
    public boolean registraRecordValore(int codice, Campo campo, Object valore) {
        return this.registraRecordValore(codice, campo, valore, this.getConnessione());
    }


    /**
     * Registra un record esistente.
     * <p/>
     * Usa il campo ed il valore forniti
     *
     * @param codice il codice chiave del record
     * @param campo il campo da registrare
     * @param valore il valore da assegnare
     * @param conn connessione da utilizzare
     *
     * @return true se riuscito
     */
    public boolean registraRecordValore(int codice, Campo campo, Object valore, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        ArrayList<CampoValore> campi;
        CampoValore cv;

        try { // prova ad eseguire il codice
            campi = new ArrayList<CampoValore>();
            cv = new CampoValore(campo, valore);
            campi.add(cv);
            riuscito = this.registraRecordValori(codice, campi, conn);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Registra un record esistente.
     * <p/>
     * Usa il nome del campo ed il valore forniti
     *
     * @param codice il codice chiave del record
     * @param nomeCampo il campo da registrare
     * @param valore il valore da assegnare
     *
     * @return true se riuscito
     */
    public boolean registraRecordValore(int codice, String nomeCampo, Object valore) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        Campo campo;

        try { // prova ad eseguire il codice
            campo = this.getCampo(nomeCampo);

            if (campo != null) {
                riuscito = this.registraRecordValore(codice, campo, valore);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Registra un record esistente.
     * <p/>
     * Usa il nome del campo ed il valore forniti
     *
     * @param codice il codice chiave del record
     * @param nomeCampo il campo da registrare
     * @param valore il valore da assegnare
     *
     * @return true se riuscito
     */
    public boolean registra(int codice, String nomeCampo, Object valore, Connessione conn) {
        /* invoca il metodo delegato della classe */
        return this.registraRecordValore(codice, nomeCampo, valore, conn);
    }


    /**
     * Registra un record esistente.
     * <p/>
     * Usa il nome del campo ed il valore forniti
     *
     * @param codice il codice chiave del record
     * @param nomeCampo il campo da registrare
     * @param valore il valore da assegnare
     *
     * @return true se riuscito
     */
    public boolean registra(int codice, String nomeCampo, Object valore) {
        /* invoca il metodo delegato della classe */
        return this.registraRecordValore(codice, nomeCampo, valore, this.getConnessione());
    }


    /**
     * Registra un record esistente.
     * <p/>
     * Usa il nome del campo ed il valore forniti
     *
     * @param codice il codice chiave del record
     * @param campo della Enumeration Cam
     * @param valore il valore da assegnare
     *
     * @return true se riuscito
     */
    public boolean registra(int codice, Campi campo, Object valore) {
        /* invoca il metodo delegato della classe */
        return this.registra(codice, campo.get(), valore, this.getConnessione());
    }


    /**
     * Registra un record esistente.
     * <p/>
     * Usa il nome del campo ed il valore forniti
     *
     * @param codice il codice chiave del record
     * @param nomeCampo il campo da registrare
     * @param valore il valore da assegnare
     * @param conn connessione da utilizzare
     *
     * @return true se riuscito
     */
    public boolean registraRecordValore(int codice,
                                        String nomeCampo,
                                        Object valore,
                                        Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        Campo campo;

        try { // prova ad eseguire il codice
            campo = this.getCampo(nomeCampo);

            if (campo != null) {
                riuscito = this.registraRecordValore(codice, campo, valore, conn);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Duplica un record di questo modulo ed eventualmente anche i subrecord.
     * <p/>
     *
     * @param codice il codice chiave del record da duplicare
     * @param duplicaSub true per duplicare anche i subrecord
     *
     * @return il codice del record duplicato, -1 se non riuscito
     */
    public int duplicaRecord(int codice, boolean duplicaSub) {
        return this.duplicaRecord(codice, duplicaSub, this.getConnessione());
    } /* fine del metodo */


    /**
     * Duplica un record di questo modulo ed eventualmente anche i subrecord.
     * <p/>
     *
     * @param codice il codice chiave del record da duplicare
     * @param duplicaSub true per duplicare anche i subrecord
     * @param conn la connessione da utilizzare
     *
     * @return il codice del record duplicato, -1 se non riuscito
     */
    public int duplicaRecord(int codice, boolean duplicaSub, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int codiceDup = -1;
        boolean riuscito;

        try { // prova ad eseguire il codice

            codiceDup = this.duplica(codice, conn);
            if (duplicaSub) {
                if (codiceDup != -1) {
                    riuscito = this.duplicaSub(codice, codiceDup, conn);
                    if (!riuscito) {
                        codiceDup = -1;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codiceDup;
    } /* fine del metodo */


    /**
     * Duplica un record di questo modulo.
     * <p/>
     * Duplica solo il record di questo modulo. <br>
     * Non duplica i subrecord. <br>
     *
     * @param codice il codice chiave del record da duplicare
     *
     * @return il codice del record duplicato, -1 se non riuscito
     */
    public int duplicaRecord(int codice) {
        return this.duplicaRecord(codice, false);
    } /* fine del metodo */


    /**
     * Duplica tutti i subrecord di questo modulo.
     * <p/>
     *
     * @param codice il codice chiave del record del quale duplicare i sub
     * @param codiceDup il codice chiave del nuovo record di testa al quale associare i sub duplicati
     *
     * @return true se riuscito
     */
    private boolean duplicaSub(int codice, int codiceDup) {
        return this.duplicaSub(codice, codiceDup, this.getConnessione());
    }


    /**
     * Duplica tutti i subrecord di questo modulo.
     * <p/>
     *
     * @param codice il codice chiave del record del quale duplicare i sub
     * @param codiceDup il codice chiave del nuovo record di testa al quale associare i sub duplicati
     * @param conn la connessione da utilizzare
     *
     * @return true se riuscito
     */
    private boolean duplicaSub(int codice, int codiceDup, Connessione conn) {
        boolean riuscito = true;
        ArrayList<Relazione> relazioni = null;
        Relazione rel = null;
        String nomeModuloSub = null;
        Modulo moduloSub = null;
        int[] chiaviSub = null;
        int chiaveSub = 0;
        int chiaveSubDup = 0;
        int ordineSub = 0;
        Filtro filtro = null;
        String nomeCampoLink = null;
        Campo campoLink = null;
        ArrayList<CampoValore> campiValore = null;
        CampoValore cv = null;
        Campo campoSubOrdine = null;

        try { // prova ad eseguire il codice

            /* recupera l'elenco delle relazioni che puntano a questo modulo */
            relazioni = Progetto.getRelazioniVerso(this.getModulo());

            /* spazzola le relazioni e per ognuna duplica i record */
            for (int k = 0; k < relazioni.size(); k++) {
                rel = (Relazione)relazioni.get(k);
                nomeModuloSub = rel.getNomeModuloPartenza();
                moduloSub = Progetto.getModulo(nomeModuloSub);
                nomeCampoLink = rel.getNomeCampoPartenza();
                campoLink = moduloSub.getCampo(nomeCampoLink);
                campoSubOrdine = moduloSub.getCampoOrdine();

                /* identifica i record da duplicare */
                filtro = FiltroFactory.crea(campoLink, new Integer(codice));
                chiaviSub = moduloSub.query().valoriChiave(filtro);

                /* duplica i record del modulo sub */
                for (int j = 0; j < chiaviSub.length; j++) {

                    /* recupera la chiave del subrecord da duplicare */
                    chiaveSub = chiaviSub[j];

                    /* recupera il valore del campo Ordine del subrecord da duplicare */
                    ordineSub = moduloSub.query().valoreInt(campoSubOrdine, chiaveSub, conn);

                    /* duplica il subrecord */
                    chiaveSubDup = moduloSub.query().duplicaRecord(chiaveSub, true, conn);

                    if (chiaveSubDup != -1) {

                        /* regola il campo di link e il campo ordine
                         * del record duplicato */
                        campiValore = new ArrayList<CampoValore>();
                        cv = new CampoValore(campoLink, new Integer(codiceDup));
                        campiValore.add(cv);
                        cv = new CampoValore(campoSubOrdine, new Integer(ordineSub));
                        campiValore.add(cv);
                        moduloSub.query().registraRecordValori(chiaveSubDup, campiValore, conn);

                    } else {
                        riuscito = false;
                        break;
                    }// fine del blocco if-else

                } // fine del ciclo for

            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;

    }


    /**
     * Duplica un record di questo modulo.
     * <p/>
     * Crea un nuovo record <br>
     * Copia i valori di tutti i campi esclusi i campi fissi <br>
     *
     * @param codice il codice chiave del record da duplicare
     * @param conn la connessione da utilizzare
     *
     * @return il codice del record duplicato, -1 se non riuscito
     */
    private int duplica(int codice, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int codiceDup = 0;
        ArrayList<Campo> campiModulo;
        ArrayList<Campo> campi;
        Campo unCampo;
        Query query;
        Filtro filtro;
        CampoValore cv;
        ArrayList<CampoValore> valori;
        Dati dati;
        Modulo modulo;

        try { // prova ad eseguire il codice

            /* crea una lista di tutti i campi fisici non-fissi del Modulo */
            modulo = this.getModulo();
            campi = new ArrayList<Campo>();
            campiModulo = modulo.getCampiFisici();
            for (int k = 0; k < campiModulo.size(); k++) {
                unCampo = (Campo)campiModulo.get(k);
                if (!(unCampo.getCampoDB().isFissoAlgos())) {
                    campi.add(unCampo);
                }// fine del blocco if
            } // fine del ciclo for

            /* crea una query con tutti i campi e il filtro sul codice */
            query = new QuerySelezione(modulo);
            for (int k = 0; k < campi.size(); k++) {
                unCampo = (Campo)campi.get(k);
                query.addCampo(unCampo);
            } // fine del ciclo for
            filtro = FiltroFactory.codice(modulo, codice);
            query.setFiltro(filtro);

            /* recupera i dati */
            dati = this.querySelezione(query, conn);

            /* crea una lista di oggetti CampoValore per i dati da trasportare */
            valori = new ArrayList<CampoValore>();
            for (int k = 0; k < campi.size(); k++) {
                unCampo = (Campo)campi.get(k);
                cv = new CampoValore(unCampo, dati.getValueAt(0, unCampo));
                valori.add(cv);
            } // fine del ciclo for

            /* chiude i dati */
            dati.close();

            /* crea un nuovo record con i valori */
            codiceDup = this.nuovoRecord(conn);
            this.registraRecordValori(codiceDup, valori, conn);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codiceDup;
    } /* fine del metodo */


    /**
     * Inverte i valori del campo ordine di due record.
     * <p/>
     *
     * @param codA codice chiave del primo record
     * @param codB codice chiave del secondo record
     *
     * @return true se riuscito
     */
    public boolean swapOrdine(int codA, int codB) {
        return this.swapOrdine(codA, codB, this.getConnessione());
    }


    /**
     * Inverte i valori del campo ordine di due record.
     * <p/>
     *
     * @param codA codice chiave del primo record
     * @param codB codice chiave del secondo record
     * @param conn la connessione da utilizzare
     *
     * @return true se riuscito
     */
    public boolean swapOrdine(int codA, int codB, Connessione conn) {
        return conn.swapOrdine(this.getModulo(), codA, codB);
    }


    /**
     * Ritorna un campo di questo modulo dal nome.
     * <p/>
     *
     * @param nomeCampo il nome del campo
     *
     * @return il campo
     */
    private Campo getCampo(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try {    // prova ad eseguire il codice
            campo = this.getModulo().getCampo(nomeCampo);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Ritorna il campo chiave di questo modulo.
     * <p/>
     *
     * @return il campo chiave
     */
    private Campo getCampoChiave() {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try {    // prova ad eseguire il codice
            campo = this.getModulo().getCampoChiave();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Recupera il massimo valore presente in un dato campo.
     *
     * @param campo il campo per il quale recuperare il massimo valore
     *
     * @return il massimo valore per il campo,
     *         (zero se non ci sono records)
     */
    public int valoreMassimo(Campo campo) {
        return this.getConnessione().valoreMassimo(campo, (Filtro)null);
    }


    /**
     * Recupera il massimo valore presente in un dato campo per un dato filtro.
     *
     * @param campo il campo per il quale recuperare il massimo valore
     * @param filtro il filtro da applicare, null per non specificato
     *
     * @return il massimo valore per il campo,
     *         (zero se non ci sono records corrispondenti al filtro)
     */
    public int valoreMassimo(Campo campo, Filtro filtro) {
        return this.getConnessione().valoreMassimo(campo, filtro);
    }


    /**
     * Somma i valori di un campo.
     *
     * @param campo il campo da sommare
     * @param filtro il filtro da applicare, null per non specificato
     *
     * @return la somma dei valori nel campo
     */
    public Number somma(Campo campo, Filtro filtro) {
        return this.somma(campo, filtro, this.getConnessione());
    }


    /**
     * Somma i valori di un campo.
     *
     * @param campo il campo da sommare
     * @param filtro il filtro da applicare, null per non specificato
     * @param conn la connessione da utilizzare
     *
     * @return la somma dei valori nel campo
     */
    public Number somma(Campo campo, Filtro filtro, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Query query;
        Dati dati;
        Object valore;
        Number totale = null;
        int numDec;
        double doppio;

        try { // prova ad eseguire il codice


            query = QueryFactory.somma(campo, filtro);
            dati = this.querySelezione(query, conn);
            if (dati.getRowCount() > 0) {
                valore = dati.getValueAt(0, 0);
                if (valore != null) {

                    if (valore instanceof Number) {
                        totale = (Number)valore;
                    }// fine del blocco if

                    /* se è un double, arrotonda al numero di decimali del campo */
                    if (valore instanceof Double) {
                        numDec = campo.getCampoDati().getNumDecimali();
                        doppio = (Double)totale;
                        doppio = Lib.Mat.arrotonda(doppio, numDec);
                        totale = doppio;
                    }// fine del blocco if

                }// fine del blocco if
            }// fine del blocco if
            dati.close();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return totale;
    }


    /**
     * Somma i valori di un campo.
     *
     * @param nomeCampo il nome del campo da sommare
     * @param filtro il filtro da applicare, null per non specificato
     *
     * @return la somma dei valori nel campo
     */
    public Number somma(String nomeCampo, Filtro filtro) {
        return somma(this.getCampo(nomeCampo), filtro);
    }


    /**
     * Somma i valori di un campo.
     *
     * @param nomeCampo il nome del campo da sommare
     * @param filtro il filtro da applicare, null per non specificato
     * @param conn la connessione da utilizzare
     *
     * @return la somma dei valori nel campo
     */
    public Number somma(String nomeCampo, Filtro filtro, Connessione conn) {
        return somma(this.getCampo(nomeCampo), filtro, conn);
    }


    /**
     * Somma i valori di un campo linkato.
     *
     * @param campo il campo da sommare
     * @param nomeCampoLink nome del campo link
     * @param codice da filtrare
     *
     * @return la somma dei valori nel campo
     */
    public Number somma(Campo campo, String nomeCampoLink, int codice) {
        return this.somma(campo, nomeCampoLink, codice, this.getConnessione());
    }


    /**
     * Somma i valori di un campo linkato.
     *
     * @param campo il campo da sommare
     * @param nomeCampoLink nome del campo link
     * @param codice da filtrare
     * @param conn la connessione da utilizzare
     *
     * @return la somma dei valori nel campo
     */
    public Number somma(Campo campo, String nomeCampoLink, int codice, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;
        Number totale = null;

        try { // prova ad eseguire il codice

            filtro = FiltroFactory.crea(nomeCampoLink, codice);
            totale = this.somma(campo, filtro, conn);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return totale;
    }


    /**
     * Somma i valori di un campo linkato.
     *
     * @param nomeCampo da sommare
     * @param nomeCampoLink nome del campo link
     * @param codice da filtrare
     *
     * @return la somma dei valori nel campo
     */
    public Number somma(String nomeCampo, String nomeCampoLink, int codice) {
        return this.somma(this.getCampo(nomeCampo), nomeCampoLink, codice);
    }


    /**
     * Somma i valori di un campo linkato.
     *
     * @param nomeCampo da sommare
     * @param nomeCampoLink nome del campo link
     * @param codice da filtrare
     * @param conn la connessione da utilizzare
     *
     * @return la somma dei valori nel campo
     */
    public Number somma(String nomeCampo, String nomeCampoLink, int codice, Connessione conn) {
        return this.somma(this.getCampo(nomeCampo), nomeCampoLink, codice, conn);
    }


    /**
     * Controlla se esiste un determinato record.
     * <p/>
     * Il record deve soddisfare la condizione di avere
     * tutti i campi indicati uguale ai valori indicati <br>
     * Ritorna vero se esiste almeno un record che soddisfa le richieste <br>
     * (potrebbe essercene più di uno) <br>
     *
     * @param campiValore su cui operare il filtro
     *
     * @return true se esiste il record richiesto
     */
    public boolean isEsisteRecord(ArrayList<CampoValore> campiValore) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        Campo campo;
        String nome;
        Object valore;
        Filtro filtroCorr;
        Filtro filtro;
        int quanti;

        try {    // prova ad eseguire il codice
            /* filtro di accumulo */
            filtro = new Filtro();

            /* crea un filtro per ogni condizione da rispettare */
            for (CampoValore campVal : campiValore) {
                campo = campVal.getCampo();
                valore = campVal.getValore();
                if (campo != null) {
                    filtroCorr = FiltroFactory.crea(campo, valore);
//                    nome = campo.getNomeInternoCampo();
                } else {
                    nome = campVal.getNomeCampo();
                    filtroCorr = FiltroFactory.crea(nome, valore);
                }// fine del blocco if-else
                filtro.add(filtroCorr);
            } // fine del ciclo for-each

            quanti = this.contaRecords(filtro);
            if (quanti > 0) {
                esiste = true;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Controlla se esiste un determinato record.
     * <p/>
     * Il record deve soddisfare le condizioni del filtro
     * Ritorna vero se esiste almeno un record che soddisfa le richieste <br>
     * (potrebbe essercene più di uno) <br>
     *
     * @param filtro di controllo per l'esistenza del record
     *
     * @return true se esiste il record richiesto
     */
    public boolean isEsisteRecord(Filtro filtro) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        int quanti;

        try {    // prova ad eseguire il codice

            quanti = this.contaRecords(filtro);
            if (quanti > 0) {
                esiste = true;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Controlla se non esiste un determinato record.
     * <p/>
     * Il record deve soddisfare le condizioni del filtro
     * Ritorna vero se non esiste nemmeno un record che soddisfa le richieste <br>
     * (potrebbe essercene più di uno) <br>
     *
     * @param filtro di controllo per l'esistenza del record
     *
     * @return true se non esiste il record richiesto
     */
    public boolean nessunRecord(Filtro filtro) {
        return !isEsisteRecord(filtro);
    }


    /**
     * Controlla se esiste un determinato record.
     * <p/>
     * Usa la connessione del modulo del campo
     * Il record deve soddisfare la condizione di avere
     * il campo indicato uguale al valore indicato <br>
     * Ritorna vero se esiste almeno un record che soddisfa la richiesta <br>
     * (potrebbe essercene più di uno) <br>
     *
     * @param nomeCampo su cui effettuare la ricerca
     * @param valore di cui testare l'esistenza
     * @param conn connessione da utilizzare
     *
     * @return true se esiste almeno un record richiesto
     */
    public boolean isEsisteRecord(String nomeCampo, Object valore, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        Filtro filtro;
        int quanti;

        try {    // prova ad eseguire il codice
            filtro = FiltroFactory.crea(nomeCampo, valore);
            quanti = this.contaRecords(filtro, conn);
            if (quanti > 0) {
                esiste = true;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Controlla se esiste un determinato record.
     * <p/>
     * Usa la connessione del modulo del campo
     * Il record deve soddisfare la condizione di avere
     * il campo indicato uguale al valore indicato <br>
     * Ritorna vero se esiste almeno un record che soddisfa la richiesta <br>
     * (potrebbe essercene più di uno) <br>
     *
     * @param nomeCampo su cui effettuare la ricerca
     * @param valore di cui testare l'esistenza
     *
     * @return true se esiste almeno un record richiesto
     */
    public boolean isEsisteRecord(String nomeCampo, Object valore) {
        return this.isEsisteRecord(nomeCampo, valore, this.getConnessione());
    }


    /**
     * Controlla se esiste un record con un dato codice.
     * <p/>
     * Il record deve soddisfare la condizione di avere
     * il campo chiave uguale al valore indicato <br>
     * Ritorna vero se esiste almeno un record col codice richiesto <br>
     * (non dovrebbe essercene più di uno) <br>
     *
     * @param codice il codice da cercare
     * @param conn connessione da utilizzare
     *
     * @return true se esiste un record con questo codice
     */
    public boolean isEsisteRecord(int codice, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        Filtro filtro;
        int quanti;

        try {    // prova ad eseguire il codice
            filtro = FiltroFactory.codice(this.getModulo(), codice);
            quanti = this.contaRecords(filtro, conn);
            if (quanti > 0) {
                esiste = true;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Controlla se esiste un record con un dato codice.
     * <p/>
     * Usa la connessione del modulo
     * Il record deve soddisfare la condizione di avere
     * il campo chiave uguale al valore indicato <br>
     * Ritorna vero se esiste almeno un record col codice richiesto <br>
     * (non dovrebbe essercene più di uno) <br>
     *
     * @param codice il codice da cercare
     *
     * @return true se esiste un record con questo codice
     */
    public boolean isEsisteRecord(int codice) {
        return this.isEsisteRecord(codice, (Connessione)null);
    }


    /**
     * Ritorna il valore del record (primo o ultimo) di un campo, dati filtro e ordine.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo del quale si desidera il valore
     * @param filtro il filtro da applicare
     * @param ordine l'ordine da applicare
     * @param primo flag tra primo ed ultimo
     * @param conn la connessione da utilizzare
     *
     * @return il valore dell'ultimo record per il campo richiesto.
     */
    private Object valorePrimoUltimoRecord(String nomeCampo,
                                           Filtro filtro,
                                           Ordine ordine,
                                           boolean primo,
                                           Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        Dati dati;
        Query query;
        Campo campo;

        try {    // prova ad eseguire il codice

            /* recupera l'ordine del campo */
            if (ordine == null) {
                campo = this.getModulo().getCampo(nomeCampo);
                ordine = campo.getCampoLista().getOrdinePrivato();
            }// fine del blocco if

            query = new QuerySelezione(this.getModulo());
            query.addCampo(nomeCampo);
            query.setFiltro(filtro);
            query.setOrdine(ordine);
            dati = this.querySelezione(query, conn);
            if (primo) {
                valore = dati.getValueAt(0, 0);
            } else {
                valore = dati.getValueAt(dati.getRowCount() - 1, 0);
            }// fine del blocco if-else
            dati.close();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Ritorna il valore del primo record di un campo, dati filtro e ordine.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo del quale si desidera il valore
     * @param filtro il filtro da applicare
     * @param ordine l'ordine da applicare
     *
     * @return il valore del primo record per il campo richiesto.
     */
    public Object valorePrimoRecord(String nomeCampo, Filtro filtro, Ordine ordine) {
        /* variabili e costanti locali di lavoro */
        Object valore=null;
        Connessione conn;

        try { // prova ad eseguire il codice
            conn = this.getConnessione();
            valore = this.valorePrimoUltimoRecord(nomeCampo, filtro, ordine, true, conn);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Ritorna il valore del primo record di un campo, dato un ordine.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo del quale si desidera il valore
     * @param ordine l'ordine da applicare
     *
     * @return il valore del primo record per il campo richiesto.
     */
    public Object valorePrimoRecord(String nomeCampo, Ordine ordine) {
        return this.valorePrimoRecord(nomeCampo, null, ordine);
    }


    /**
     * Ritorna il valore del primo record di un campo, dato un campo ordine.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo del quale si desidera il valore
     * @param campoOrdine il campo di questo od altro modulo
     * dal quale recuperare l'ordine privato
     *
     * @return il valore del primo record per il campo richiesto.
     */
    public Object valorePrimoRecord(String nomeCampo, Campo campoOrdine) {
        /* variabili e costanti locali di lavoro */
        Ordine ordine;
        Object valore = null;

        try { // prova ad eseguire il codice
            ordine = campoOrdine.getCampoLista().getOrdinePrivato();
            valore = this.valorePrimoRecord(nomeCampo, null, ordine);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Ritorna il valore del primo record di un campo, dato un
     * campo ordine di questo modulo.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo del quale si desidera il valore
     * @param nomeCampoOrdine il nome interno del campo di questo modulo
     * dal quale recuperare l'ordine privato
     *
     * @return il valore del primo record per il campo richiesto.
     */
    public Object valorePrimoRecord(String nomeCampo, String nomeCampoOrdine) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        Campo campo;

        try { // prova ad eseguire il codice
            campo = this.getModulo().getCampo(nomeCampoOrdine);
            valore = this.valorePrimoRecord(nomeCampo, campo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Ritorna il valore del primo record di un campo,
     * secondo il proprio ordine.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo del quale si desidera il valore
     *
     * @return il valore del primo record per il campo richiesto.
     */
    public Object valPrimoRecord(String nomeCampo) {
        /* valore di ritorno */
        return valorePrimoRecord(nomeCampo, nomeCampo);
    }


    /**
     * Ritorna il valore dell'ultimo record di un campo, dati filtro e ordine.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo del quale si desidera il valore
     * @param filtro il filtro da applicare
     * @param ordine l'ordine da applicare
     *
     * @return il valore dell'ultimo record per il campo richiesto.
     */
    public Object valoreUltimoRecord(String nomeCampo, Filtro filtro, Ordine ordine) {
        /* variabili e costanti locali di lavoro */
        Object valore=null;
        Connessione conn;

        try { // prova ad eseguire il codice
            conn = this.getConnessione();
            valore = this.valorePrimoUltimoRecord(nomeCampo, filtro, ordine, false, conn);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }

    /**
     * Ritorna il valore dell'ultimo record di un campo, dati filtro e ordine.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo del quale si desidera il valore
     * @param filtro il filtro da applicare
     * @param ordine l'ordine da applicare
     * @param conn la connessione da utilizzare
     *
     * @return il valore dell'ultimo record per il campo richiesto.
     */
    public Object valoreUltimoRecord(String nomeCampo, Filtro filtro, Ordine ordine, Connessione conn) {
        return this.valorePrimoUltimoRecord(nomeCampo, filtro, ordine, false, conn);
    }



    /**
     * Ritorna il valore dell'ultimo record di un campo, dato un ordine.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo del quale si desidera il valore
     * @param ordine l'ordine da applicare
     *
     * @return il valore dell'ultimo record per il campo richiesto.
     */
    public Object valoreUltimoRecord(String nomeCampo, Ordine ordine) {
        return this.valoreUltimoRecord(nomeCampo, null, ordine);
    }


    /**
     * Ritorna il valore dell'ultimo record di un campo, dati filtro e ordine.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo del quale si desidera il valore
     * @param filtro il filtro da applicare
     *
     * @return il valore dell'ultimo record per il campo richiesto.
     */
    public Object valUltimoRecord(String nomeCampo, Filtro filtro) {
        return this.valoreUltimoRecord(nomeCampo, filtro, null);
    }


    /**
     * Ritorna il valore dell'ultimo record di un campo, dato un campo ordine.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo del quale si desidera il valore
     * @param campoOrdine il campo di questo od altro modulo
     * dal quale recuperare l'ordine privato
     *
     * @return il valore dell'ultimo record per il campo richiesto.
     */
    public Object valoreUltimoRecord(String nomeCampo, Campo campoOrdine) {
        /* variabili e costanti locali di lavoro */
        Ordine ordine;
        Object valore = null;

        try { // prova ad eseguire il codice
            ordine = campoOrdine.getCampoLista().getOrdinePrivato();
            valore = this.valoreUltimoRecord(nomeCampo, null, ordine);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }

    /**
     * Ritorna il valore dell'ultimo record di un campo, dato un campo ordine.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo del quale si desidera il valore
     * @param campoOrdine il campo di questo od altro modulo
     * @param conn la connessione da utilizzare
     * dal quale recuperare l'ordine privato
     *
     * @return il valore dell'ultimo record per il campo richiesto.
     */
    public Object valoreUltimoRecord(String nomeCampo, Campo campoOrdine, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Ordine ordine;
        Object valore = null;

        try { // prova ad eseguire il codice
            ordine = campoOrdine.getCampoLista().getOrdinePrivato();
            valore = this.valoreUltimoRecord(nomeCampo, null, ordine, conn);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }



    /**
     * Ritorna il valore dell'ultimo record di un campo, dato un
     * campo ordine di questo modulo.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo del quale si desidera il valore
     * @param nomeCampoOrdine il nome interno del campo di questo modulo
     * dal quale recuperare l'ordine privato
     *
     * @return il valore dell'ultimo record per il campo richiesto.
     */
    public Object valoreUltimoRecord(String nomeCampo, String nomeCampoOrdine) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        Campo campo;

        try { // prova ad eseguire il codice
            campo = this.getModulo().getCampo(nomeCampoOrdine);
            valore = this.valoreUltimoRecord(nomeCampo, campo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }

    /**
     * Ritorna il valore dell'ultimo record di un campo, dato un
     * campo ordine di questo modulo.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo del quale si desidera il valore
     * @param nomeCampoOrdine il nome interno del campo di questo modulo
     * @param conn la connessione da utilizzare
     * dal quale recuperare l'ordine privato
     *
     * @return il valore dell'ultimo record per il campo richiesto.
     */
    public Object valoreUltimoRecord(String nomeCampo, String nomeCampoOrdine, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        Campo campo;

        try { // prova ad eseguire il codice
            campo = this.getModulo().getCampo(nomeCampoOrdine);
            valore = this.valoreUltimoRecord(nomeCampo, campo, conn);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }



    /**
     * Ritorna il valore dell'ultimo record di un campo,
     * secondo il proprio ordine.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo del quale si desidera il valore
     *
     * @return il valore dell'ultimo record per il campo richiesto.
     */
    public Object valoreUltimoRecord(String nomeCampo) {
        return valoreUltimoRecord(nomeCampo, nomeCampo);
    }

    /**
     * Ritorna il valore dell'ultimo record di un campo,
     * secondo il proprio ordine.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo del quale si desidera il valore
     * @param conn la connessione da utilizzare
     *
     * @return il valore dell'ultimo record per il campo richiesto.
     */
    public Object valoreUltimoRecord(String nomeCampo, Connessione conn) {
        return valoreUltimoRecord(nomeCampo, nomeCampo, conn);
    }



    /**
     * Ritorna i valori interi distinti per un campo di questo modulo
     * <p/>
     * Usa la connessione del modulo
     *
     * @param nomeCampo nome del campo di questo modulo del quale rendere i valori distinti
     *
     * @return array dei valori interi distinti del campo.
     */
    public ArrayList<Integer> valoriDistintiInt(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Integer> valori = null;
        ArrayList lista;
        int intero;

        try { // prova ad eseguire il codice
            lista = this.valoriDistinti(nomeCampo);

            valori = new ArrayList<Integer>();

            /* traverso tutta la collezione */
            for (Object ogg : lista) {
                intero = Libreria.getInt(ogg);
                valori.add(intero);
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valori;
    }


    /**
     * Ritorna i valori distinti per un campo di questo modulo
     * <p/>
     * Usa la connessione del modulo
     *
     * @param nomeCampo nome del campo di questo modulo del quale rendere i valori distinti
     *
     * @return la lista dei valori distinti del campo.
     */
    public ArrayList valoriDistinti(String nomeCampo) {
        return this.valoriDistinti(nomeCampo, (Filtro)null);
    }


    /**
     * Ritorna i valori distinti per un campo di questo modulo
     * <p/>
     * Usa la connessione del modulo
     *
     * @param nomeCampo nome del campo di questo modulo del quale rendere i valori distinti
     * @param filtro il filtro da applicare (null per nessun filtro)
     *
     * @return la lista dei valori distinti del campo.
     */
    public ArrayList valoriDistinti(String nomeCampo, Filtro filtro) {
        /* variabili e costanti locali di lavoro */
        ArrayList valori = null;
        Campo campo;

        try { // prova ad eseguire il codice
            campo = this.getModulo().getCampo(nomeCampo);

            if (campo != null) {
                valori = this.valoriDistinti(campo, filtro);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valori;

    }


    /**
     * Ritorna i valori distinti per un campo
     * <p/>
     * Usa la connessione del modulo
     *
     * @param campo il campo del quale rendere i valori distinti
     *
     * @return la lista dei valori distinti del campo.
     */
    public ArrayList valoriDistinti(Campo campo) {
        return this.valoriDistinti(campo, (Filtro)null);
    }


    /**
     * Ritorna i valori distinti per un campo
     * <p/>
     * Usa la connessione del modulo
     *
     * @param campo il campo del quale rendere i valori distinti
     * @param filtro il filtro da applicare (null per nessun filtro)
     *
     * @return la lista dei valori distinti del campo.
     */
    public ArrayList valoriDistinti(Campo campo, Filtro filtro) {
        return this.valoriDistinti(campo, filtro, this.getConnessione());
    }


    /**
     * Ritorna i valori distinti per un campo
     * <p/>
     *
     * @param campo il campo del quale rendere i valori distinti
     * @param filtro il filtro da applicare (null per nessun filtro)
     * @param conn la connessione da utilizzare
     *
     * @return la lista dei valori distinti del campo.
     */
    public ArrayList valoriDistinti(Campo campo, Filtro filtro, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Modulo modulo;
        Query q;
        Dati dati;
        ArrayList valori = null;
        Ordine ordine;

        try {

            /* recupera il modulo dal campo */
            modulo = campo.getModulo();

            /* crea una QuerySelezione per il modulo,
             * la esegue e recupera il risultato */
            q = new QuerySelezione(modulo);
            q.addCampo(campo);
            q.setValoriDistinti(true);
            if (filtro != null) {
                q.addFiltro(filtro);
            }// fine del blocco if

            /* ordine fisso sul campo */
            ordine = new Ordine();
            ordine.add(campo);
            q.setOrdine(ordine);

            dati = modulo.query().querySelezione(q, conn);
            valori = dati.getValoriColonna(campo);
            dati.close();

        } catch (Exception unErrore) {
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return valori;

    }


    /**
     * Ritorna il valore chiave del primo record
     * di questo modulo dove un dato campo ha un dato valore
     * <p/>
     * Usa l'accesso diretto SQL per prestazioni
     *
     * @param nomeCampo su cui effettuare la ricerca
     * @param valore di cui testare l'esistenza nel campo di ricerca
     * @param conn connessione da utilizzare
     *
     * @return il valore del campo chiave, 0 se non trovato
     */
    private int sqlValoreChiave(String nomeCampo, Object valore, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int chiave = 0;
        boolean continua;
        Modulo mod = null;
        ConnessioneJDBC connJDBC = null;
        String stringaSql = "";
        String nomeTavola = "";
        ResultSet rs = null;
        Object ogg;
        String str = "";
        Db db = null;
        DbSql dbSql = null;
        Campo campo;
        TipoDatiSql tipoDatiSql;
        OperatoreSql operatore;

        try {    // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Testo.isValida(nomeCampo) && valore != null && conn != null);

            if (continua) {
                mod = this.getModulo();
                continua = mod != null;
            }// fine del blocco if

            if (continua) {
                if (conn instanceof ConnessioneJDBC) {
                    connJDBC = (ConnessioneJDBC)conn;
                }// fine del blocco if
                continua = conn != null;
            }// fine del blocco if

            if (continua) {
                db = conn.getDb();
                continua = db != null;
            }// fine del blocco if

            if (continua) {
                if (db instanceof DbSql) {
                    dbSql = (DbSql)db;
                }// fine del blocco if
                continua = dbSql != null;
            }// fine del blocco if

            if (continua) {
                nomeTavola = mod.getModello().getTavolaArchivio();
                continua = Lib.Testo.isValida(nomeTavola);
            }// fine del blocco if

            if (continua) {
                nomeTavola = dbSql.fixCase(nomeTavola);
                nomeCampo = dbSql.fixCase(nomeCampo);
            }// fine del blocco if

            /* conversione del valore in stringa tramite il tipo dati del campo */
            if (continua) {

                campo = this.getCampo(nomeCampo);
                tipoDatiSql = (TipoDatiSql)dbSql.getTipoDati(campo);
                operatore = dbSql.getOperatoreFiltro(Filtro.Op.UGUALE);
                str = tipoDatiSql.stringaConfronto(valore, operatore);

            }// fine del blocco if

            if (continua) {
                stringaSql = "SELECT CODICE FROM ";
                stringaSql += nomeTavola;
                stringaSql += " WHERE ";
                stringaSql += nomeCampo;
                stringaSql += " = ";
                stringaSql += str;
            }// fine del blocco if

            if (continua) {
                rs = connJDBC.esegueSelect(stringaSql);
                continua = rs != null;
            }// fine del blocco if

            if (continua) {
                ogg = LibResultSet.getValoreCella(rs, 1, 1);
                chiave = Libreria.getInt(ogg);
                rs.getStatement().close();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return chiave;
    }


    /**
     * Ritorna il valore chiave del primo record
     * di questo modulo dove un dato campo ha un dato valore
     * <p/>
     * Usa l'accesso diretto SQL
     *
     * @param nomeCampo su cui effettuare la ricerca
     * @param valore di cui testare l'esistenza nel campo di ricerca
     *
     * @return il valore del campo chiave, 0 se non trovato
     */
    private int sqlValoreChiave(String nomeCampo, Object valore) {
        return this.sqlValoreChiave(nomeCampo, valore, this.getConnessione());
    }


    private Modulo getModulo() {
        return this.getConnettore().getModulo();
    }


    private Connessione getConnessione() {
        return this.getConnettore().getConnessione();
    }


    private Connettore getConnettore() {
        return connettore;
    }


    private void setConnettore(Connettore connettore) {
        this.connettore = connettore;
    }
}// fine della classe
