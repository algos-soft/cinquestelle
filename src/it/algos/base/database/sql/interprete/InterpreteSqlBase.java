/**
 * Title:     InterpreteSqlBase
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      03-nov-2004
 */
package it.algos.base.database.sql.interprete;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.Db;
import it.algos.base.database.interprete.InterpreteBase;
import it.algos.base.database.sql.DbSql;
import it.algos.base.database.sql.util.FunzioneSql;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.campi.CampoQuery;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.PassaggiSelezione;
import it.algos.base.query.selezione.PassaggioObbligato;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.relazione.Relazione;
import it.algos.base.relazione.RelazioneJoin;
import it.algos.base.relazione.RelazioneManager;
import it.algos.base.relazione.RelazionePercorso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Implementazione astratta di un Interprete Sql.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 03-nov-2004 ore 8.23.25
 */
public abstract class InterpreteSqlBase extends InterpreteBase implements InterpreteSql {

    /**
     * Query di riferimento
     */
    private Query query = null;


    /**
     * Costruttore completo.<br>
     *
     * @param dbSql il database Sql proprietario
     */
    public InterpreteSqlBase(Db dbSql) {
        /* rimanda al costruttore della superclasse */
        super(dbSql);

        try { // prova ad eseguire il codice
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
     * Ritorna una stringa Sql corrispondente a una lista di Campi.
     * <p/>
     * La stringa e' costituita dall'elenco dei nomi Sql dei campi
     * separati dal separatore di campo.
     * L'elenco non e' racchiuso tra parentesi.
     * I nomi dei campi sono qualificati o non qualificati con il nome
     * della tavola a seconda del flag qualificati.
     *
     * @param elementi elenco dei campi della Query (oggetti CampoQuery)
     * @param qualificati - true per usare i nomi qualificati (tavola.campo)
     * - true per usare i nomi non qualificati (campo)
     *
     * @return la stringa corrispondente ai Campi
     */
    protected String stringaCampi(ArrayList elementi, boolean qualificati) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        CampoQuery qc = null;
        Campo campo = null;
        ArrayList funzioni = null;
        String nomeSql = null;
        boolean haFunzioni = false;

        try {    // prova ad eseguire il codice
            for (int k = 0; k < elementi.size(); k++) {

                /* recupera il singolo elemento */
                qc = (CampoQuery)elementi.get(k);

                /* recupera il campo */
                campo = qc.getCampo();

                /* controlla che non sia nullo */
                if (campo == null) {
                    throw new Exception("Campo nullo.");
                }// fine del blocco if

                /* recupera l'elenco delle funzioni sul campo */
                funzioni = qc.getFunzioni();

                /* recupera il nome sql del campo, qualificato o non qualificato */
                if (qualificati) {
                    nomeSql = this.getDatabaseSql().getStringaCampoQualificata(campo);
                } else {
                    nomeSql = this.getDatabaseSql().getStringaCampo(campo);
                }// fine del blocco if-else

                /* aggiunge il separatore di campi della query */
                if (stringa != "") {
                    stringa += this.getDatabaseSql().getSeparatoreCampi();
                }// fine del blocco if

                /* determina se ci sono funzioni da applicare */
                if (funzioni != null) {
                    if (funzioni.size() > 0) {
                        haFunzioni = true;
                    }// fine del blocco if
                }// fine del blocco if

                /* se ci sono funzioni, aggiunge la stringa relativa
                 * al campo con eventuali funzioni, altrimenti aggiunge il campo */
                if (haFunzioni) {
                    stringa += this.stringaFunzioni(funzioni, nomeSql);
                } else {
                    stringa += nomeSql;
                }// fine del blocco if-else

            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Restituisce una stringa corrispondente a un elenco
     * di funzioni che incapsulano un argomento.
     * <p/>
     * Le funzioni vengono applicate in ordine inverso
     * (la prima della lista e' la piu' interna)
     *
     * @param codFunzioni l'elenco dei codici chave delle
     * funzioni (da interfaccia Funzione)
     * @param argomento l'argomento da incapsulare nelle funzioni
     *
     * @return la stringa Sql di funzioni con l'argomento incapsulato
     */
    private String stringaFunzioni(ArrayList codFunzioni, String argomento) {
        /* variabili e costanti locali di lavoro */
        String stringa = null;
        String codFunzione = null;

        try { // prova ad eseguire il codice
            stringa = argomento;
            for (int k = 0; k < codFunzioni.size(); k++) {
                codFunzione = (String)codFunzioni.get(k);
                stringa = stringaFunzione(codFunzione, stringa);
            } // fine del ciclo for
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Restituisce una stringa corrispondente a una funzione
     * che incapsula un argomento.
     * <p/>
     * Le funzioni vengono applicate in ordine inverso
     * (la prima della lista e' la piu' interna)
     *
     * @param codFunzione il codice chave della funzione (da interfaccia Funzione)
     * @param argomento l'argomento da incapsulare nella funzione
     *
     * @return la stringa Sql di funzioni con l'argomento incapsulato
     */
    private String stringaFunzione(String codFunzione, String argomento) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        FunzioneSql fn = null;
        String stringaFn = null;

        try { // prova ad eseguire il codice

            /* recupera la funzione Sql */
            fn = this.getDatabaseSql().getFunzioneSql(codFunzione);

            /* recupera la stringa Sql relativa alla funzione */
            stringaFn = fn.getSimbolo();

            /* costruisce la stringa con funzione e argomento
             * (se la stringa funzione non e' valida ritorna il solo argomento) */
            if (Lib.Testo.isValida(stringaFn)) {
                stringa += stringaFn;
                stringa += this.getDatabase().getParentesiAperta();
                stringa += argomento;
                stringa += this.getDatabase().getParentesiChiusa();
            } else {
                stringa += argomento;
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna la stringa corrispondente a un Filtro.
     * <p/>
     * La clausola WHERE e' compresa nella stringa.
     *
     * @param filtro da elaborare
     *
     * @return la eventuale stringa per il filtro
     */
    protected String stringaFiltro(Filtro filtro) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        InterpreteFiltroSql intFiltro = null;

        try { // prova ad eseguire il codice

            /* recupera l'interprete filtro di questo Db */
            intFiltro = this.getDatabaseSql().getInterpreteFiltroSql();
            /* delega all'interprete la costruzione della stringa Sql */
            stringa += intFiltro.stringaSql(filtro);
            /* aggiunge eventualmente un acapo di separazione */

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Restituisce la stringa dei comandi per la sezione LEFT JOIN.
     * <p/>
     * Per ogni tavola coinvolta nella SELECT diversa
     * dalla tavola base, costruisce un pacchetto di comandi
     * LEFT JOIN che stabiliscono una relazione dalla tavola
     * di partenza (la tavola base) alla tavola
     * destinazione (la tavola da raggiungere).<br>
     *
     * @return un pacchetto con i comandi JOIN
     */
    protected String stringaJoin() {

        /** variabili e costanti locali di lavoro */
        String stringa = "";
        PassaggiSelezione passaggiSelezione = null;
        String tavolaBase = null;
        ArrayList passaggiTavola = null;
        PassaggioObbligato passaggio = null;
        Relazione relazione = null;
        ArrayList<Relazione> relazioni = null;
        ArrayList tavoleEsterne = null;
        String tavolaEsterna = null;
        ArrayList elencoJoin = new ArrayList();
        ArrayList<Relazione> tavolaRelazioni = null;
        RelazionePercorso percorso = null;
        ArrayList elencoJoinPercorso = null;
        RelazioneJoin unaJoin = null;
        DbSql dbSql = null;

        /* recupera l'oggetto passaggi obbligati dalla Query */
        passaggiSelezione = this.getQuery().getPassaggi();

        /* recupera la tavola base */
        tavolaBase = getTavolaBase();

        try {

            /* recupera l'elenco delle tavole esterne */
            tavoleEsterne = getTavoleEsterne();

            /* crea una lista vuota per l'elenco totale delle join */
            elencoJoinPercorso = new ArrayList();

            /*
             * per ogni tavola esterna, determina il percorso
             * di relazione con la tavola base
             */
            for (int k = 0; k < tavoleEsterne.size(); k++) {

                /* recupera la tavola esterna */
                tavolaEsterna = (String)tavoleEsterne.get(k);

                /* recupera i passaggi obbligati che riguardano la tavola */
                passaggiTavola = passaggiSelezione.getPassaggiTavola(tavolaEsterna);

                /* crea un elenco delle corrispondenti relazioni */
                for (int j = 0; k < passaggiTavola.size(); j++) {
                    passaggio = (PassaggioObbligato)passaggiTavola.get(j);
                    relazione = passaggio.getRelazioneObbligata();
                    relazioni.add(relazione);
                } // fine del ciclo for

                /* recupera la tavola relazioni da utilizzare */
                tavolaRelazioni = Progetto.getTavolaRelazioni();

                /* determina il percorso dalla tavola base alla tavola esterna */
                percorso = RelazioneManager.getPercorsoRelazione(tavolaBase,
                        tavolaEsterna,
                        tavolaRelazioni,
                        relazioni);

                /*
                 * In base al percorso, costruisce un elenco di oggetti Join
                 * e lo aggiunge all'elenco totale delle Join.
                 * Vengono aggiunte solo le Join che non esistono gia'.<br>
                 * Se il percorso non e' stato trovato, avvisa il programmatore.
                 */
                if (percorso != null) {
                    elencoJoin = joinPercorso(percorso);
                    aggiungiPacchettoJoin(elencoJoin, elencoJoinPercorso);
                } else {
                    new MessaggioAvviso(
                            "Percorso non trovato. Le clausole JOIN non verranno aggiunte.");
                } /* fine del blocco if-else */

            } /* fine del blocco for */
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /*
        * Spazzola tutte le Join
        * e costruisce il blocco di testo completo
        */
        stringa = "";
        String stringaJoin = "";
        dbSql = this.getDatabaseSql();
        for (int k = 0; k < elencoJoinPercorso.size(); k++) {
            /* estrae la singola Join */
            unaJoin = (RelazioneJoin)elencoJoinPercorso.get(k);
            /* crea la stringa per la Join */
            stringaJoin = "";
            stringaJoin += dbSql.getLeftJoin();
            stringaJoin += unaJoin.getTavolaDestinazione();
            stringaJoin += dbSql.getOn();
            stringaJoin += dbSql.getStringaCampoQualificata(unaJoin.getCampoDestinazione());
            stringaJoin += dbSql.getUguale();
            stringaJoin += dbSql.getStringaCampoQualificata(unaJoin.getCampoPartenza());

            /* la aggiunge al testo JOIN finale */
            stringa += "\n" + stringaJoin;
        } /* fine del blocco for */

        return stringa;
    } /* fine del metodo */


    /**
     * Ritorna la stringa Sql corrispondente alla Tavola.
     * <p/>
     *
     * @return la stringa per la Tavola
     */
    protected String getTavolaBase() {
        return this.getQuery().getModulo().getModello().getTavolaArchivio();
    }


    /**
     * Ritorna un elenco univoco di tutte le tavole esterne alla Select.
     * <p/>
     * Analizza la Query nelle sezioni Campi, Filtri, Ordini
     * e recupera tutte le tavole coinvolte
     * diverse dalla tavola principale.
     *
     * @return l'elenco delle tavole esterne
     */
    protected ArrayList getTavoleEsterne() {
        /** variabili e costanti locali di lavoro */
        ArrayList tavole = null;
        ArrayList campi = null;
        HashMap tavoleEsterne = null;
        HashMap tavoleSezione = null;
        Query query;
        QuerySelezione qs;
        Filtro filtro;
        Ordine ordine;

        try {    // prova ad eseguire il codice

            query = this.getQuery();

            /* crea una HashMap vuota di lavoro */
            tavoleEsterne = new HashMap();

            /* aggiunge le tavole esterne dei campi lista */
            if (query.getListaCampi() != null) {
                campi = query.getListaCampi();
                tavoleSezione = this.tavoleEsterne(campi);
                tavoleEsterne.putAll(tavoleSezione);
            }// fine del blocco if

            /* aggiunge le tavole esterne dei campi filtro */
            filtro = query.getFiltro();
            if (filtro != null) {
                campi = filtro.getCampi();
                tavoleSezione = this.tavoleEsterne(campi);
                tavoleEsterne.putAll(tavoleSezione);
            }// fine del blocco if

            /* se è una QuerySelezione, aggiunge le tavole esterne
             * dei campi ordine */
            if (query instanceof QuerySelezione) {
                qs = (QuerySelezione)query;
                ordine = qs.getOrdine();
                if (ordine != null) {
                    campi = ordine.getCampi();
                    tavoleSezione = this.tavoleEsterne(campi);
                    tavoleEsterne.putAll(tavoleSezione);
                }// fine del blocco if-else
            }// fine del blocco if

            /* trasforma la HashMap finale in ArrayList */
            tavole = Libreria.hashMapToArrayList(tavoleEsterne);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return tavole;
    } /* fine del metodo */


    /**
     * Filtra un elenco di campi e ritorna le tavole esterne.
     * <p/>
     * Ritorna una collezione univoca delle tavole diverse
     * dalla tavola interna della Select.
     *
     * @param campi l'elenco di campi da filtrare
     *
     * @return la collezione univoca delle tavole esterne
     */
    private HashMap tavoleEsterne(ArrayList campi) {
        /* variabili e costanti locali di lavoro */
        HashMap tavole = null;
        Campo campo = null;
        String tavola = null;
        String tavolaInterna = null;

        try {    // prova ad eseguire il codice
            tavolaInterna = getTavolaBase();
            tavole = new HashMap();
            for (int k = 0; k < campi.size(); k++) {
                campo = (Campo)campi.get(k);
                tavola = this.getDatabaseSql().getStringaTavola(campo);
                if (tavola.equalsIgnoreCase(tavolaInterna) == false) {
                    tavole.put(tavola, tavola);
                }// fine del blocco if
            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return tavole;
    }


    /**
     * Ritorna un elenco di oggetti Join costruiti a partire
     * da un percorso.
     * <p/>
     * Dato che il Percorso e' descritto in sequenza ribaltata
     * rispetto alla sequenza necessaria per le Join, ribalta
     * l'elenco delle Join prima di ritornarlo in modo da averle
     * nella sequenza corretta.
     *
     * @param percorso il percorso per il quale costruire le Join
     *
     * @return un elenco di oggetti RelazioneJoin
     */
    private ArrayList joinPercorso(RelazionePercorso percorso) {

        /* variabili e costanti locali di lavoro */
        ArrayList elencoJoin = null;
        Relazione relazione = null;
        ArrayList elencoRelazioni = null;
        RelazioneJoin join = null;

        try { // prova ad eseguire il codice

            /* crea un elenco vuoto da ritornare */
            elencoJoin = new ArrayList();

            /* recupera le relazioni del percorso */
            elencoRelazioni = percorso.getRelazioni();

            /* spazzola le relazioni del percorso
             * e costruisce un elenco di oggetti RelazioneJoin */
            for (int k = 0; k < elencoRelazioni.size(); k++) {

                // estrae la singola relazione
                relazione = (Relazione)elencoRelazioni.get(k);
                // crea la Join
                join = new RelazioneJoin(relazione);
                // la aggiunge all'elenco da ritornare
                elencoJoin.add(join);

            } /* fine del blocco for */

            /** ribalta l'elenco delle Join */
            elencoJoin = Libreria.ribaltaLista(elencoJoin);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return elencoJoin;
    } /* fine del metodo */


    /**
     * Aggiunge un pacchetto di JOIN relativo a un Percorso
     * a un pacchetto di JOIN esistente.
     * <p/>
     * Le JOIN che esistono gia' non vengono aggiunte.<br>
     * Ogni relazione da una tavola a un'altra implica una pacchetto di
     * clausole LEFT JOIN.<br>
     * Dato che una SELECT stabilisce una relazione tra una tavola base
     * e diverse altre tavole, per ognuna di queste relazioni possiamo
     * avere un pacchetto di LEFT JOIN.<br>
     * Da cio' consegue che nell'elenco finale delle LEFT JOIN alcune
     * possono essere ripetute.<br>
     * In una serie di clausole JOIN della SELECT, non devono esistere
     * due righe che puntano alla stessa tavola, nemmeno se sono identiche,
     * quindi il pacchetto finale delle JOIN va ridotto per escludere
     * i doppioni.<br>
     * Nel caso che venga trovata una Join gia' esistente ma su altri campi,
     * il programmatore viene avvisato che non puo' mettere in relazione
     * le stesse due tavole su campi diversi, e la JOIN viene ignorata.<br>
     *
     * @param p1 il pacchetto di JOIN da aggiungere
     * @param p0 il pacchetto al quale aggiungere
     * il pacchetto di Join.
     */
    private void aggiungiPacchettoJoin(ArrayList p1, ArrayList p0) {

        /** variabili e costanti locali di lavoro */
        RelazioneJoin unaJoin = null;

        /** spazzola il pacchetto di Join e lo aggiunge se non esiste */
        for (int k = 0; k < p1.size(); k++) {

            // estrae la singola Join
            unaJoin = (RelazioneJoin)p1.get(k);

            // la aggiunge se non esiste
            if (esisteJoinPariTavola(unaJoin, p0) == false) {
                p0.add(unaJoin);
            } else {
                /** se esiste gia' una Join sulla stessa tavola, e se la Join
                 *  che si sta cercando di aggiungere e' diversa da quella
                 *  esistente (pari tavola ma su altri campi), avvisa
                 *  il programmatore che sta cercando di mettere in relazione la
                 *  stessa tavola su campi diversi.
                 *  In ogni caso la Join non viene aggiunta (non funzionerebbe)
                 *  e per mettere in relazione le tavole vale la Join gia' esistente. */
                if (esisteJoinIdentica(unaJoin, p0) ==
                        false) {  // non e' identica, devo avvisare l'utente prima di tralasciarla
                    String campoPartenza = unaJoin.getChiaveCampoPartenza();
                    String tavolaDestinazione = unaJoin.getTavolaDestinazione();
                    String messaggio = "E' stato rilevato un tentativo di mettere in relazione";
                    messaggio +=
                            "\n" +
                                    "la tavola " +
                                    tavolaDestinazione +
                                    " tramite il campo " +
                                    campoPartenza;
                    messaggio +=
                            "\n" +
                                    "ma esiste gia' una relazione su un altro campo verso la tavola.";
                    messaggio += "\n" + "La relazione su " + campoPartenza + " verra' ignorata.";
                    new MessaggioAvviso(messaggio);
                } /* fine del blocco if */

            } /* fine del blocco if */

        } /* fine del blocco for */

    } /* fine del metodo */


    /**
     * Controlla se una Join esiste gia' in un elenco di Join.
     * <p/>
     * Il controllo viene eseguito sulla tavola destinazione.<br>
     * Se nell'elenco esiste gia' una Join che punta alla stessa
     * tavola destinazione, ritorna true.<br>
     *
     * @param join l'oggetto Join da controllare
     * @param elencoJoin l'elenco di oggetti Join nel quale cercare
     *
     * @return true se l'elenco contiene gia' una Join con la stessa
     *         tavola destinazione
     */
    private boolean esisteJoinPariTavola(RelazioneJoin join, ArrayList elencoJoin) {

        /** variabili e costanti locali di lavoro */
        boolean esiste = false;
        RelazioneJoin unaJoinDaElenco = null;
        String unaTavola = "";
        String altraTavola = "";

        /** ciclo for */
        for (int k = 0; k < elencoJoin.size(); k++) {

            // estrae l'oggetto JOIN e le tavole
            unaJoinDaElenco = (RelazioneJoin)elencoJoin.get(k);
            unaTavola = join.getTavolaDestinazione();
            altraTavola = unaJoinDaElenco.getTavolaDestinazione();

            // confronta le tavole, se sono uguali ritorna true ed esce dal ciclo for
            if (unaTavola.equalsIgnoreCase(altraTavola)) {
                esiste = true;
                break;
            } /* fine del blocco if */

        } /* fine del blocco for */

        return esiste;
    } /* fine del metodo */


    /**
     * Controlla se una Join esiste gia' in un elenco di Join.
     * <p/>
     * Il controllo viene eseguito su tutti i campi usando equals().<br>
     * Se nell'elenco esiste gia' una Join identica, ritorna true.<br>
     *
     * @param join l'oggetto Join da controllare
     * @param elencoJoin l'elenco di oggetti Join nel quale cercare
     *
     * @return true se l'elenco contiene gia' una Join con la stessa
     *         tavola destinazione
     */
    private boolean esisteJoinIdentica(RelazioneJoin join, ArrayList elencoJoin) {

        /** variabili e costanti locali di lavoro */
        boolean esiste = false;
        RelazioneJoin unaJoinDaElenco = null;

        /** ciclo for */
        for (int k = 0; k < elencoJoin.size(); k++) {

            // estrae l'oggetto JOIN e le tavole
            unaJoinDaElenco = (RelazioneJoin)elencoJoin.get(k);

            // confronta le join, se sono identiche ritorna true ed esce dal ciclo for
            if (join.equals(unaJoinDaElenco)) {
                esiste = true;
                break;
            } /* fine del blocco if */

        } /* fine del blocco for */

        return esiste;
    } /* fine del metodo */


    /**
     * Converte un identificatore nel case supportato dal database.
     * <p/>
     *
     * @param identIn l'identificatore da convertire
     *
     * @return l'identificatore convertito nel case appropriato per il database
     */
    protected String fixCase(String identIn) {
        return this.getDatabaseSql().fixCase(identIn);
    }


    /**
     * Ritorna il database Sql proprietario dell'Interprete.
     * <p/>
     *
     * @return il database Sql proprietario
     */
    public DbSql getDatabaseSql() {
        return (DbSql)this.getDatabase();
    }


    protected Query getQuery() {
        return query;
    }


    protected void setQuery(Query query) {
        this.query = query;
    }

}// fine della classe
