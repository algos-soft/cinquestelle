/**
 * Title:     CalcResolver
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      5-dic-2006
 */
package it.algos.base.calcresolver;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.wrapper.CampoValore;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Risolutore di campi calcolati per il database.
 * </p>
 * Riceve una lista di oggetti CampoValore da registrare.<br>
 * Crea un albero a partire dalla lista di oggetti CampoValore,
 * rappresentante tutte le dipendenze derivanti dai campi calcolati.<br>
 * Traversa l'albero in Postorder ed assegna i valori ad ogni nodo:<br>
 * - se il nodo non è calcolato:<br>
 *   - se si tratta di nuovo record, usa i valori di default dal campo<br>
 *   - se si tratta di modifica, usa i valori del campo esistenti nel database<br>
 * - se il nodo è calcolato, esegue il calcolo usando i valori dei campi
 *   dipendenti (campi osservati)<br>
 * Al termine produce una lista di oggetti CampoValore
 * contenente tutti i campi interessati (calcolati e non)
 * contenenti i valori sincronizzati.<br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 15-gen-2005 ore 18.14.07
 */
public class CalcResolver extends Object {

    /**
     * Modello di riferimento
     */
    private Modello modello;

    /**
     * Lista di oggetti CampoValore in entrata, da elaborare
     */
    private ArrayList<CampoValore> campiValoreIn;

    /**
     * Lista di oggetti CampoValore in uscita dopo l'elaborazione
     */
    private ArrayList<CampoValore> campiValoreOut;

    /**
     * Lista di campi arrivati in ingresso
     */
    private ArrayList<Campo> campiArrivati;

    /**
     * Lista di campi che devono essere calcolati
     */
    private ArrayList<Campo> campiDaCalcolare;

    /**
     * Lista di campi necessari per il calcolo
     */
    private ArrayList<Campo> campiNecessari;

    /**
     * Lista di CampiValore necessari al calcolo
     */
    private ArrayList<CampoValore> cvNecessari;

    /**
     * Lista di CampiValore calcolati
     */
    private ArrayList<CampoValore> cvCalcolati;

    /**
     * Tipo di operazione, true per nuovo record, false per modifica
     */
    private boolean nuovoRecord;

    /**
     * Codice del record in caso di modifica
     */
    private int codice;

    /**
     * Connessione da utilizzare per l'eventuale accesso al database
     */
    private Connessione connessione;


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param modello di riferimento
     * @param lista da elaborare
     * @param nuovo true per nuovo record, false per modifica
     * @param codice del record in caso di modifica
     * @param conn la connessione da utilizzare per accedere al database
     */
    public CalcResolver(Modello modello,
                        ArrayList<CampoValore> lista,
                        boolean nuovo,
                        int codice,
                        Connessione conn) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setModello(modello);
        this.setCampiValoreIn(lista);
        this.setNuovoRecord(nuovo);
        this.setCodice(codice);
        this.setConnessione(conn);

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
    }


    /**
     * Avvia l'oggetto.
     * <p/>
     * Esegue l'elaborazione
     */
    public void avvia() {

        try {    // prova ad eseguire il codice

            /* rimuove dalla lista dei CampiValore arrivati
             * eventuali campi calcolati.*/
            this.removeCalcolati();

            /* crea la lista dei campi arrivati */
            this.regolaCampiArrivati();

            /* crea la lista dei campi da calcolare */
            this.regolaCampiDaCalcolare();

            /* crea la lista dei campi necessari per il calcolo */
            this.regolaCampiNecessari();

            /* crea la lista dei CampiValore necessari completa di valori */
            this.regolaCVNecessari();

            /* crea la lista dei CampiValore calcolati completa di valori */
            this.regolaCVCalcolati();

            /* crea la lista dei campiValore in uscita */
            this.regolaCampiValoreOut();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Rimuove dalla lista dei CampiValore arrivati
     * eventuali campi calcolati.
     * <p/>
     * registra la nuova lista dei CampiValore arrivati
     */
    private void removeCalcolati() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        ArrayList<CampoValore> cvArrivati;
        ArrayList<CampoValore> cvPuliti;

        try {    // prova ad eseguire il codice
            cvArrivati = this.getCampiValoreIn();
            continua = (cvArrivati != null);

            if (continua) {
                cvPuliti = new ArrayList<CampoValore>();
                for (CampoValore cv : cvArrivati) {
                    if (!cv.isCalcolato()) {
                        cvPuliti.add(cv);
                    }// fine del blocco if
                }
                /* registra la lista ripulita */
                this.setCampiValoreIn(cvPuliti);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Determina la lista dei campi arrivati
     * e la registra.
     * <p/>
     * Rimuove dai campi arrivati eventuali campi calcolati
     */
    private void regolaCampiArrivati() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> arrivati = null;
        ArrayList<CampoValore> listaCV = null;
        Campo campo;

        try {    // prova ad eseguire il codice

            arrivati = new ArrayList<Campo>();
            listaCV = this.getCampiValoreIn();
            for (CampoValore cv : listaCV) {
                campo = cv.getCampo();
                arrivati.add(campo);
            }
            this.setCampiArrivati(arrivati);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Determina la lista univoca dei campi da calcolare
     * e la registra.
     * <p/>
     */
    private void regolaCampiDaCalcolare() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> calcolati = null;
        ArrayList<Campo> campiLista = null;
        ArrayList<Campo> necessari = null;
        ArrayList<Campo> dipendenti = null;
        AlberoCalcolo albero;

        try {    // prova ad eseguire il codice

            albero = this.getAlberoCalcolo();
//            albero.mostra();

            /* campi calcolati dipendenti dai campi arrivati */
            campiLista = this.getCampiArrivati();
            calcolati = albero.getCampiDipendenti(campiLista);

            /* se nuovo, aggiunge ai campi calcolati
             * eventuali altri campi calcolati presenti
             * nella lista dei campi necessari al calcolo,
             * assieme ad eventuali campi da essi dipendenti */
            if (this.isNuovoRecord()) {
                necessari = this.getCampiNecessari(calcolati);
                for (Campo campo : necessari) {
                    if (campo.isCalcolato()) {

                        /* aggiunge il campo alla lista dei calcolati */
                        if (!calcolati.contains(campo)) {
                            calcolati.add(campo);
                        }// fine del blocco if

                        /* aggiunge eventuali campi dipendenti alla lista dei calcolati */
                        dipendenti = albero.getCampiDipendenti(campo);
                        for (Campo campoDip : dipendenti) {
                            if (!calcolati.contains(campoDip)) {
                                calcolati.add(campoDip);
                            }// fine del blocco if
                        }

                    }// fine del blocco if
                }
            }// fine del blocco if

            this.setCampiDaCalcolare(calcolati);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Determina la lista univoca dei campi necessari per il calcolo
     * e la registra.
     * <p/>
     * Per ogni campo da calcolare, recupera l'elenco dei campi osservati<br>
     * Crea un elenco univoco.<br>
     */
    private void regolaCampiNecessari() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> necessari = null;
        ArrayList<Campo> osservati = null;
        ArrayList<Campo> daCalcolare = null;

        try {    // prova ad eseguire il codice

            necessari = new ArrayList<Campo>();
            daCalcolare = this.getCampiDaCalcolare();
            osservati = this.getCampiNecessari(daCalcolare);

            /* aggiunge i campi osservati alla lista dei campi necessari
             * a meno che il campo non sia già contenuto nella lista
             * dei campi da calcolare. */
            for (Campo campo : osservati) {
                if (!daCalcolare.contains(campo)) {
                    necessari.add(campo);
                }// fine del blocco if

            }

            /* registra la lista */
            this.setCampiNecessari(necessari);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea la lista completa dei campiValore in uscita e la registra
     * <p/>
     * L'insieme è sempre composto da:
     * - tutti i campi arrivati
     * - tutti i campi da calcolare
     * Se nuovo, all'insieme si aggiungono anche tutti
     * i campi necessari al calcolo.
     */
    private void regolaCampiValoreOut() {
        /* variabili e costanti locali di lavoro */
        ArrayList<CampoValore> cvOut = null;

        try {    // prova ad eseguire il codice

            cvOut = new ArrayList<CampoValore>();

            /* aggiunge tutti i campi arrivati */
            cvOut.addAll(this.getCampiValoreIn());

            /* aggiunge tutti i campi calcolati */
            cvOut.addAll(this.getCvCalcolati());

            /* se nuovo record, aggiunge anche i necessari */
            if (this.isNuovoRecord()) {
                for (CampoValore cv : this.getCvNecessari()) {
                    if (!cvOut.contains(cv)) {
                        cvOut.add(cv);
                    }// fine del blocco if
                }
            }// fine del blocco if

            /* registra la lista */
            this.setCampiValoreOut(cvOut);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea la lista dei campiValore necessari al calcolo,
     * assegna i valori e la registra.
     * <p/>
     * Spazzola la lista dei campi necessari.
     * - Se il campo è nella lista in entrata, usa il valore fornito.
     * - Se non è nella lista in entrata:
     * - se si tratta di nuovo, usa il valore vuoto di default
     * - se si tratta di modifica, recupera il valore dal database
     */
    private void regolaCVNecessari() {
        /* variabili e costanti locali di lavoro */
        ArrayList<CampoValore> listaCV = null;
        ArrayList<Campo> listaNecessari = null;
        ArrayList<CampoValore> listaForniti = null;
        ArrayList<CampoValore> listaNonForniti = null;
        CampoValore cv;
        CampoValore cvIn;
        Campo unCampo;
        Object valore = null;
        Modulo mod;
        Query query;
        Filtro filtro;
        Dati dati;
        int codice;

        try {    // prova ad eseguire il codice

            listaCV = new ArrayList<CampoValore>();
            listaNecessari = this.getCampiNecessari();

            /* crea le due liste dei CampiValore necessari
             * forniti e non forniti nella lista in ingresso */
            listaForniti = new ArrayList<CampoValore>();
            listaNonForniti = new ArrayList<CampoValore>();
            for (Campo campo : listaNecessari) {
                cvIn = this.getCampoValoreIn(campo);
                if (cvIn != null) {
                    listaForniti.add(cvIn);
                } else {
                    /* crea un nuovo CampoValore con il solo campo */
                    cv = new CampoValore(campo, null);
                    listaNonForniti.add(cv);
                }// fine del blocco if-else
            }

            /* per i campi non forniti:
             * - se nuovo, usa i valori di default per nuovo record
             * - se modifica, recupera i valori dal database */
            if (this.isNuovoRecord()) { // nuovo record, valore vuoto di default
                for (CampoValore unCv : listaNonForniti) {
                    unCampo = unCv.getCampo();
                    valore = unCampo.getCampoDati().getValoreNuovoRecord(this.getConnessione());
                    unCv.setValore(valore);
                }
            } else {  // modifica, legge valori dal database

                if (listaNonForniti.size() > 0) {
                    /* recupera i dati con una query */
                    mod = this.getModello().getModulo();
                    query = new QuerySelezione(mod);
                    for (CampoValore unCv : listaNonForniti) {
                        unCampo = unCv.getCampo();
                        query.addCampo(unCampo);
                    }
                    codice = this.getCodice();
                    filtro = FiltroFactory.codice(mod, codice);
                    query.setFiltro(filtro);
                    dati = mod.query().querySelezione(query, this.getConnessione());

                    /* inserisce i valori nei CampiValore */
                    for (CampoValore unCv : listaNonForniti) {
                        unCampo = unCv.getCampo();
                        valore = dati.getValueAt(0, unCampo);
                        unCv.setValore(valore);
                    }

                    /* chiude la connessione con l'oggetto dati */
                    dati.close();

                }// fine del blocco if

            }// fine del blocco if-else

            /* crea e registra la lista completa */
            listaCV.addAll(listaForniti);
            listaCV.addAll(listaNonForniti);
            this.setCvNecessari(listaCV);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea la lista dei campiValore calcolati.
     * <p/>
     * Elabora i campi calcolati nell'ordine Postorder dell'albero per
     * rispettare la gerarchia di calcolo.
     * Spazzola i campi calcolati, li calcola e crea il rispettivo
     * CampoValore calcolato.
     * Appena ha creato il CampoValore calcolato, lo aggiunge alla
     * lista dei CampiValore calcolati in modo che sia disponibile
     * per i calcoli successivi.
     */
    private void regolaCVCalcolati() {
        /* variabili e costanti locali di lavoro */
        ArrayList<CampoValore> listaCV = null;
        ArrayList<Campo> campiCalcAlbero = null;
        AlberoCalcolo albero;
        Campo unCampo;
        CampoValore cv;
        Object valore;

        try {    // prova ad eseguire il codice

            /* crea la lista vuota dei CampiValore calcolati */
            listaCV = new ArrayList<CampoValore>();
            this.setCvCalcolati(listaCV);

            albero = this.getAlberoCalcolo();

            /* recupera la lista di tutti i campi calcolati
             * dall'albero in postorder */
            campiCalcAlbero = albero.getCampiCalcolatiPostorder();

            for (Campo campo : campiCalcAlbero) {

                /* recupera il campo dalla lista dei campi da calcolare
                 * se lo trova lo calcola e lo aggiunge alla lista
                 * dei CampiValore calcolati */
                unCampo = this.getCampoDaCalcolare(campo);
                if (unCampo != null) {
                    valore = this.calcola(unCampo);
                    cv = new CampoValore(campo, valore);
                    this.getCvCalcolati().add(cv);
                }// fine del blocco if

            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il valore calcolato di un campo.
     * <p/>
     */
    private Object calcola(Campo campo) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        ArrayList<String> nomiOss;
        ArrayList<Object> valoriOss;
        Campo unCampo;
        CampoValore cv;
        Object unValore;

        try {    // prova ad eseguire il codice

            /* recupera la lista dei valori dei campi osservati */
            valoriOss = new ArrayList<Object>();
            nomiOss = campo.getCampoLogica().getCampiOsservati();
            for (String nome : nomiOss) {
                unCampo = this.getCampo(nome);
                cv = this.recuperaCVOsservato(unCampo);
                if (cv != null) {
                    unValore = cv.getValore();
                    valoriOss.add(unValore);
                } else {
                    throw new Exception("Campo osservato " + nome + " non trovato.");
                }// fine del blocco if-else
            }

            /* delega al campo il calcolo del valore */
            valore = campo.esegueOperazione(valoriOss);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Recupera un CampoValore osservato.
     * <p/>
     * Recupera il CampoValore dai campiValore necessari.
     * o dai campiValore calcolati.
     * Prima lo cerca nei campiValore necessari.
     * Se non lo trova, lo cerca nei campiValore già calcolati.
     *
     * @return il CampoValore richiesto, null se non trovato
     */
    private CampoValore recuperaCVOsservato(Campo campo) {
        /* variabili e costanti locali di lavoro */
        CampoValore cv = null;

        try {    // prova ad eseguire il codice
            cv = this.getCVNecessario(campo);
            if (cv == null) {
                cv = this.getCVCalcolato(campo);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cv;
    }


    /**
     * Ritorna il CampoValore che contiene un dato campo
     * da una lista di CampiValore
     * <p/>
     *
     * @param listaCV lista nella quale cercare
     * @param campo da cercare
     *
     * @return il CampoValore trovato, null se non trovato
     */
    private CampoValore getCampoValore(ArrayList<CampoValore> listaCV, Campo campo) {
        return Lib.Camp.getCampoValore(listaCV, campo);
    }


    /**
     * Ritorna il CampoValore della lista CampiValore in ingresso
     * che contiene un dato campo.
     * <p/>
     *
     * @param campo da cercare
     *
     * @return il CampoValore trovato, null se non trovato
     */
    private CampoValore getCampoValoreIn(Campo campo) {
        /* variabili e costanti locali di lavoro */
        CampoValore cvOut = null;
        ArrayList<CampoValore> lista;
        try {    // prova ad eseguire il codice
            lista = this.getCampiValoreIn();
            cvOut = this.getCampoValore(lista, campo);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cvOut;
    }


    /**
     * Ritorna un CampoValore della lista CampiValore necessari
     * che contiene un dato campo.
     * <p/>
     *
     * @param campo da cercare
     *
     * @return il CampoValore trovato, null se non trovato
     */
    private CampoValore getCVNecessario(Campo campo) {
        /* variabili e costanti locali di lavoro */
        CampoValore cvOut = null;
        ArrayList<CampoValore> lista;
        try {    // prova ad eseguire il codice
            lista = this.getCvNecessari();
            cvOut = this.getCampoValore(lista, campo);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cvOut;
    }


    /**
     * Ritorna un CampoValore della lista CampiValore calcolati
     * che contiene un dato campo.
     * <p/>
     *
     * @param campo da cercare
     *
     * @return il CampoValore trovato, null se non trovato
     */
    private CampoValore getCVCalcolato(Campo campo) {
        /* variabili e costanti locali di lavoro */
        CampoValore cvOut = null;
        ArrayList<CampoValore> lista;
        try {    // prova ad eseguire il codice
            lista = this.getCvCalcolati();
            cvOut = this.getCampoValore(lista, campo);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cvOut;
    }


    /**
     * Ritorna un Campo da una lista di campi
     * <p/>
     *
     * @param listaCampi lista nella quale cercare
     * @param campo da cercare
     *
     * @return il Campo trovato, null se non trovato
     */
    private Campo getCampoLista(ArrayList<Campo> listaCampi, Campo campo) {
        /* variabili e costanti locali di lavoro */
        Campo campoOut = null;

        try {    // prova ad eseguire il codice
            for (Campo unCampo : listaCampi) {
                if (unCampo.equals(campo)) {
                    campoOut = unCampo;
                    break;
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campoOut;
    }


    /**
     * Recupera un campo dalla lista dei campi da calcolare.
     * <p/>
     *
     * @param campo da recuperare
     *
     * @return il campo recuperato, null se non trovato
     */
    private Campo getCampoDaCalcolare(Campo campo) {
        return this.getCampoLista(this.getCampiDaCalcolare(), campo);
    }


    /**
     * Recupera un campo dalla lista dei campi necessari.
     * <p/>
     *
     * @param campo da recuperare
     *
     * @return il campo recuperato, null se non trovato
     */
    private Campo getCampoNecessario(Campo campo) {
        return this.getCampoLista(this.getCampiNecessari(), campo);
    }


    /**
     * Recupera un campo dalla lista dei campi necessari.
     * <p/>
     *
     * @param nome interno del da recuperare
     *
     * @return il campo recuperato, null se non trovato
     */
    private Campo getCampoNecessario(String nome) {
        Campo campo;
        Campo campoOut = null;

        try { // prova ad eseguire il codice
            campo = this.getCampo(nome);
            if (campo != null) {
                campoOut = this.getCampoNecessario(campo);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campoOut;
    }


    /**
     * Ritorna l'elenco univoco dei campi necessari
     * per il calcolo di un dato elenco di campi calcolati
     * <p/>
     *
     * @param campi calcolati per i quali determinare i campi necessari
     *
     * @return l'elenco dei campi necessari al calcolo
     */
    private ArrayList<Campo> getCampiNecessari(ArrayList<Campo> campi) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> necessari = null;
        AlberoCalcolo albero;

        try {    // prova ad eseguire il codice
            albero = this.getAlberoCalcolo();
            necessari = albero.getCampiOsservati(campi);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return necessari;
    }


    /**
     * Ritorna un campo dal modello dato il nome
     * <p/>
     *
     * @param nome del campo
     *
     * @return il campo recuperato dal Modello
     */
    private Campo getCampo(String nome) {
        return this.getModello().getCampo(nome);
    }


    /**
     * Ritorna l'albero di calcolo del modello.
     * <p/>
     *
     * @return l'albero di calcolo
     */
    private AlberoCalcolo getAlberoCalcolo() {
        return this.getModello().getAlberoCalcolo();
    }


    /**
     * Ritorna la lista completa dei CampiValore da scrivere.
     * <p/>
     * L'ordine dei CampiValore nella lista è quello dei campi nel Modello.
     *
     * @return la lista completa dei CampiValore da scrivere
     */
    public ArrayList<CampoValore> getListaElaborata() {
        /* variabili e costanti locali di lavoro */
        ArrayList<CampoValore> listaCVOut = null;
        ArrayList<CampoValore> listaCV = null;
        LinkedHashMap<String, Campo> campiModello = null;
        CampoValore cv;

        try {    // prova ad eseguire il codice
            listaCVOut = new ArrayList<CampoValore>();
            campiModello = this.getModello().getCampiFisici();
            listaCV = this.getCampiValoreOut();
            for (Campo campo : campiModello.values()) {
                cv = this.getCampoValore(listaCV, campo);
                if (cv != null) {
                    listaCVOut.add(cv);
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaCVOut;
    }


    private Modello getModello() {
        return modello;
    }


    private void setModello(Modello modello) {
        this.modello = modello;
    }


    private ArrayList<CampoValore> getCampiValoreIn() {
        return campiValoreIn;
    }


    private void setCampiValoreIn(ArrayList<CampoValore> campiValoreIn) {
        this.campiValoreIn = campiValoreIn;
    }


    private ArrayList<CampoValore> getCampiValoreOut() {
        return campiValoreOut;
    }


    private void setCampiValoreOut(ArrayList<CampoValore> campiValoreOut) {
        this.campiValoreOut = campiValoreOut;
    }


    private ArrayList<Campo> getCampiArrivati() {
        return campiArrivati;
    }


    private void setCampiArrivati(ArrayList<Campo> campiArrivati) {
        this.campiArrivati = campiArrivati;
    }


    private ArrayList<Campo> getCampiDaCalcolare() {
        return campiDaCalcolare;
    }


    private void setCampiDaCalcolare(ArrayList<Campo> campiDaCalcolare) {
        this.campiDaCalcolare = campiDaCalcolare;
    }


    private ArrayList<Campo> getCampiNecessari() {
        return campiNecessari;
    }


    private void setCampiNecessari(ArrayList<Campo> campiNecessari) {
        this.campiNecessari = campiNecessari;
    }


    private ArrayList<CampoValore> getCvNecessari() {
        return cvNecessari;
    }


    private void setCvNecessari(ArrayList<CampoValore> cvNecessari) {
        this.cvNecessari = cvNecessari;
    }


    private ArrayList<CampoValore> getCvCalcolati() {
        return cvCalcolati;
    }


    private void setCvCalcolati(ArrayList<CampoValore> cvCalcolati) {
        this.cvCalcolati = cvCalcolati;
    }


    private boolean isNuovoRecord() {
        return nuovoRecord;
    }


    private void setNuovoRecord(boolean nuovoRecord) {
        this.nuovoRecord = nuovoRecord;
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

}// fine della classe
