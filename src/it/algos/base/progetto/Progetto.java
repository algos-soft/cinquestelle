/**
 * Title:        Progetto.java
 * Package:      it.algos.base.progetto
 * Description:  Abstract Data Types per la struttura dei dati di un progetto
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 20 giugno 2003 alle 9.18
 */

package it.algos.base.progetto;

import it.algos.base.aggiornamento.Aggiornamento;
import it.algos.base.albero.Albero;
import it.algos.base.albero.AlberoModello;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.azione.Azione;
import it.algos.base.azione.AzioneFactory;
import it.algos.base.backup.Backup;
import it.algos.base.campo.base.Campo;
import it.algos.base.config.Config;
import it.algos.base.contatore.Contatore;
import it.algos.base.contatore.ContatoreModulo;
import it.algos.base.costante.CostanteColore;
import it.algos.base.database.Db;
import it.algos.base.database.DbFactory;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.dialogo.Dialogo;
import it.algos.base.documento.numeratore.NumeratoreDocModulo;
import it.algos.base.errore.Errore;
import it.algos.base.finestra.Finestra;
import it.algos.base.flag.Flag;
import it.algos.base.gestore.Gestore;
import it.algos.base.gestore.GestoreBase;
import it.algos.base.hotkey.HotKey;
import it.algos.base.hotkey.HotKeyDispatcher;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibFont;
import it.algos.base.libreria.Libreria;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.NodoModulo;
import it.algos.base.modulo.NodoModuloOggetto;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.NavigatoreFactory;
import it.algos.base.pref.Pref;
import it.algos.base.pref.PrefDialogo;
import it.algos.base.relazione.Relazione;
import it.algos.base.selezione.Selezione;
import it.algos.base.selezione.SelezioneModulo;
import it.algos.base.semaforo.Semaforo;
import it.algos.base.semaforo.SemaforoModulo;
import it.algos.base.utenti.Utenti;
import it.algos.base.utenti.UtentiModulo;
import it.algos.base.utenti.gruppi.Gruppi;
import it.algos.base.utenti.gruppi.GruppiModulo;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Questa classe concreta e' responsabile di:<br>
 * A - Eseguire le operazioni iniziali del programma <br>
 * B - Implementa il Design Pattern <code>Singleton</code> <br>
 * C - Regola il look-and-feel dell'applicazione <br>
 * D - Regola le preferenze <br>
 * E - Regola i testi <br>
 * F - Regola gli accessi (utente, password, permessi) <br>
 * G - Mantiene un elenco di moduli ordinato secondo la sequenza logica di costruzione <br>
 * H - Mantiene una tabella rappresentante le relazioni tra i moduli <br>
 * I - Connette al database <br>
 * L - Controlla e carica le tabelle fisse (selezionate nella sottoclasse) <br>
 * <p/>                                                  add
 * Tutte le funzionalita' vengono eseguite una ed una sola volta per ogni
 * programma (controllo degli accessi, dimensione del video, ecc) <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  20 giugno 2003 ore 9.18
 */
public final class Progetto {

    /**
     * flag di controllo per la creazione della finestra-grafico dell'albero
     */
    public static final boolean DEBUG = false;

    /**
     * flag di controllo per il cambio delle preferenze
     *
     * @todo da eliminare dopo averle modificate tutte
     * @todo levando anche tutti gli if-else sparsi nel programma
     */
    public static final boolean OLD_PREF = false;

    /**
     * istanza unica di questa classe, creata ad avvio del programma
     */
    private static Progetto ISTANZA = null;

    /**  */
    public static final int LIVELLO_BASE = 0;

    public static final int LIVELLO_MEDIO = 1;

    public static final int LIVELLO_AVANZATO = 2;

    /**
     * nome della cartella icone di base
     */
    public static String DIR_ICONE_BASE = "base/icone";

    /**
     * nome della cartella icone di un modulo specifico
     * (situata allo stesso livello del modulo)
     */
    public static String DIR_ICONE_MODULO = "icone";

    /**
     * estensione dei file di icona
     */
    public static String EXT_ICONE = ".gif";

    /**
     * database di progetto
     */
    private Db db = null;

    /**
     * database contenente i dati di preferenze
     */
    private Db dbPreferenze = null;

    /**
     * database contenente i dati di default
     */
    private Db dbDefault = null;

    /**
     * Connessione al database di progetto
     * Creata e aperta dal primo modulo che necessita di una connessione.
     * Chiusa all'uscita dal programma.
     */
    private Connessione connessione;

    /**
     * parametro del livello di complessita' del programma
     */
    private int livello = LIVELLO_AVANZATO;

    /**
     * Tavola per mantenere tutte le relazioni del progetto
     * contiene oggetti di tipo Relazione
     */
    private ArrayList<Relazione> tavolaRelazioni = null;

    /**
     * collezione chiave-valore ordinata per tutti moduli del progetto
     */
    private LinkedHashMap<String, Modulo> moduli = null;

    /**
     * collezione chiave-valore per le azioni del progetto
     */
    private LinkedHashMap<String, Azione> azioni = null;

    /**
     * collezione delle famiglie di font interne disponibili per il progetto
     */
    private ArrayList famiglieFont = null;

    private Albero alberoModuli = null;

    /**
     * Oggetto formattatore per le date.
     * <p/>
     * Converte data->testo e testo->data
     */
    private SimpleDateFormat dateFormat = null;

    /**
     * Oggetto formattatore per le date.
     * <p/>
     * Converte data->testo e testo->data
     * interpreta l'anno a 2 cifre
     */
    private SimpleDateFormat shortDateFormat = null;

    /**
     * Oggetto formattatore per le ore.
     * <p/>
     * Converte ora->testo e testo->ora
     * utilizza ore minuti e secondi
     */
    private SimpleDateFormat oraFormat = null;

    /**
     * Oggetto formattatore per le ore.
     * <p/>
     * Converte ora->testo e testo->ora
     * non utilizza i secondi
     */
    private SimpleDateFormat shortOraFormat = null;


    /**
     * Istanza del calendario del programma
     */
    private GregorianCalendar calendario;

    /**
     * Data convenzionalmente interpretata come vuota
     */
    private Date dataVuota;

    private static Modulo PRIMO_MODULO = null;

    private static String catUpdate = "";

    /**
     * Flag - indica se il progetto ha eseguito tutte
     * le proprie regolazioni ed eì pronto per avviare
     * il primo modulo utente
     */
    private static boolean pronto = false;

    /**
     * Id dell'utente correntemente collegato
     */
    private static int idUtente = 0;

    /**
     * Collezione delle HotKeys
     */
    private HashMap<Integer, HotKey> hotkeys = null;

    private String nomeProgramma;

    private Gestore gestore;

    /* finestra di monitor avvio del programma */
    private Finestra finestraAvvio;

    /* label di monitor avvio del programma */
    private JLabel labelAvvio;

    /* progressbar di monitor avvio del programma */
    private JProgressBar barAvvio;

    /* monitor di avvio del programma */
    private MonitorAvvio monitorAvvio;


    /**
     * Costruttore completo senza parametri
     */
    protected Progetto() {
        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        GregorianCalendar calendario;
        Date data;
        long millisec;
        KeyboardFocusManager focusManager;

        try { // prova ad eseguire il codice

            /* crea le preferenze base (e legge i valori correnti dal file esterno) */
            new Pref();

            /* Crea la tavola delle Relazioni */
            this.tavolaRelazioni = new ArrayList<Relazione>();

            /* crea la collezione di moduli */
            this.moduli = new LinkedHashMap<String, Modulo>();

            /* crea la collezione di azioni */
            this.azioni = new LinkedHashMap<String, Azione>();

            /* crea la collezione delle famiglie font interne */
            this.famiglieFont = new ArrayList();

            /* crea l'albero dei moduli del Progetto */
            this.setAlberoModuli(new Albero());

            /* aggiunge il nodo root all'albero */
            getAlberoModuli().addNodo(new AlberoNodo(), null);

            /* creaq un gestore di progetto */
            this.setGestore(new GestoreBase());

            /* palette del programma */
//            this.setPalette(new Palette());

            /* crea la collezione delle HotKeys */
            this.setHotKeys(new HashMap<Integer, HotKey>());

            /* aggiunge al KeyboardFocusManager un dispatcher
             * per rispondere alle pressioni delle HotKeys */
            focusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
            focusManager.addKeyEventDispatcher(new HotKeyDispatcher());

            /* crea il calendario */
            calendario = new GregorianCalendar(0, 0, 0, 0, 0, 0);
            this.setCalendario(calendario);

            /* crea la data convenzionalmente vuota */
            millisec = calendario.getTimeInMillis();
            data = new Date(millisec);
            this.setDataVuota(data);

            /**
             * regola il calendario come non-lenient
             * (se la data non è valida non effettua la
             * rotazione automatica dei valori dei campi,
             * es. 32-12-2004 non diventa 01-01-2005)
             */
            calendario.setLenient(false);

            /* crea i formattatori per le date */
            SimpleDateFormat df;
            SimpleDateFormat sdf;
            df = new SimpleDateFormat("dd-MM-yyyy");
            sdf = new SimpleDateFormat("dd-MM-yy");
            df.setCalendar(calendario);
            sdf.setCalendar(calendario);
            this.setDateFormat(df);
            this.setShortDateFormat(sdf);

            /* crea i formattatori per le ore */
            SimpleDateFormat of;
            SimpleDateFormat sof;
            of = new SimpleDateFormat("HH:mm:ss");
            sof = new SimpleDateFormat("HH:mm");
            of.setCalendar(calendario);
            sof.setCalendar(calendario);
            this.setOraFormat(of);
            this.setShortOraFormat(sof);

            /*
             * Regola il colore di sfondo e di primo piano per tutti
             * i combobox disabilitati
             * Opera a basso livello sui defaults della UI
             * In Java per adesso non si riesce a regolare queste proprieta'
             * vedi http://forum.java.sun.com/thread.jspa?threadID=278806&messageID=1089217
             * Alex 18-08-2006
             */
            UIManager.getDefaults().put("ComboBox.disabledBackground",
                    CostanteColore.BIANCO_GRIGIO);
            UIManager.getDefaults().put("ComboBox.disabledForeground", CostanteColore.GRIGIO_SCURO);
            UIManager.getDefaults().put("RadioButton.disabledText", CostanteColore.GRIGIO_SCURO);
            UIManager.getDefaults().put("CheckBox.disabledText", CostanteColore.GRIGIO_SCURO);

//            UIManager.getDefaults().put("TextField.selectionBackground", Color.red);
//            UIManager.getDefaults().put("TextField.selectionForeground", Color.red);
//            UIManager.getDefaults().put("TextField.foreground", Color.red);

            /* Regola le funzioni generali del programma */
            this.funzioniGenerali();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    } /* fine del metodo inizia */


    /**
     * Regolazioni cicliche tra i moduli.
     * <p/>
     * Spazzola tutti i moduli dal basso verso l'alto e viceversa
     * fino a quando le relazioni tra i moduli non sono state instaurate.
     */
    private void pendoloModuli() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Modulo> listaAscendente;
        ArrayList<Modulo> listaDiscendente;
        int loop = 0;
        int maxLoop = 100;

        try { // prova ad eseguire il codice

            /* recupera l'elenco dal basso verso l'alto */
            listaAscendente = getModuliPostorder();

            /* crea una lista discendente inversa a quella ascendente */
            listaDiscendente = new ArrayList<Modulo>();
            for (int k = listaAscendente.size(); k > 0; k--) {
                listaDiscendente.add(listaAscendente.get(k - 1));
            } // fine del ciclo for

            /* elimina dalla lista discendente il primo
             * e l'ultimo elemento per evitare che ai due
             * estremi del pendolo vengano esaminati due
             * volte consecutive *
             * ABCD - DCBA -> ABCD-CB-ABCD-CB...
             * (Se la lista ha meno di tre elementi, e' inutile e la svuoto)
             */
            if (listaDiscendente.size() >= 3) {
                int posUltimo = listaDiscendente.size() - 1;
                listaDiscendente.remove(posUltimo);
                listaDiscendente.remove(0);
            } else {
                listaDiscendente.clear();
            }// fine del blocco if-else

            /**
             * spazzola i moduli oscillando dal basso all'alto e viceversa
             * fintanto che non sono tutti relazionati
             */
            while (!isModuliRelazionati()) {

                /* spazzola dal basso */
                this.relazionaModuli(listaAscendente);

                /* spazzola dall'alto */
                if (!isModuliRelazionati()) {
                    this.relazionaModuli(listaDiscendente);
                }// fine del blocco if

                /* controllo anti-loop */
                if (++loop > maxLoop) {
                    throw new Exception("relazione moduli in loop: " + loop);
                }// fine del blocco if

            }/* fine del blocco while */

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Determina se tutti i moduli nella collezione sono relazionati.
     * <p/>
     *
     * @return true se sono tutti relazionati
     */
    private boolean isModuliRelazionati() {
        /* variabili e costanti locali di lavoro */
        boolean relazionati = true;

        try {    // prova ad eseguire il codice
            for (Modulo mod : getListaModuli()) {
                if (!mod.isRelazionato()) {
                    relazionati = false;
                    break;
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return relazionati;
    }


    /**
     * Prepara tutti i Moduli del progetto.
     * <p/>
     * Spazzola una volta sola tutti i moduli del progetto in ordine discendente <br>
     * Invocato dal progetto <b>dopo</b> il ciclo di inizia e <b>prima</b> del ciclo
     * di <i>relazionaModuli</i> <br>
     *
     * @return true se la preparazione di tutti i moduli e' riuscita.
     */
    private boolean preparaModuli() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        boolean risultato;
        ArrayList listaModuli;
        Modulo unModulo;

        try {    // prova ad eseguire il codice

//            /* recupera l'elenco dei moduli dall'alto verso il basso */
//            listaModuli = getModuliPreorder();

            /**
             * recupera l'elenco dei moduli dal basso verso l'alto
             * Modificato il verso da Alex il 25-03-08
             * ora prepariamo dal basso verso l'alto
             * in prepara() vengono creati i campi
             * in questo modo i campi dei moduli inferiori sono subito
             * disponibili ai moduli superiori
             * pare che funzioni tutto - per ora lo teniamo così
             */
            listaModuli = getModuliPostorder();

            /* spazzola e prepara ogni modulo */
            riuscito = true;

            for (int k = 0; k < listaModuli.size(); k++) {
                unModulo = (Modulo)listaModuli.get(k);
                if (unModulo != null) {
                    risultato = unModulo.prepara();
                    if (risultato) {
                        monitorAvvio.update();
                    } else {
                        riuscito = false;
                    }// fine del blocco if-else
                }// fine del blocco if
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Relaziona tra di loro una lista di Moduli <br>
     * <p/>
     * Spazzola n volte tutti i moduli del progetto in ordine ascendente/discendente <br>
     * Invocato dal progetto <b>dopo</b> il ciclo di preparaModuli e <b>prima</b> del
     * ciclo di <i>inizializza</i> <br>
     *
     * @param listaModuli la lista dei moduli da relazionare
     */
    private void relazionaModuli(ArrayList<Modulo> listaModuli) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito;

        try {    // prova ad eseguire il codice
            for (Modulo mod : listaModuli) {
                if (mod != null) {
                    if (!mod.isRelazionato()) {
                        riuscito = mod.relaziona();
                        if (riuscito) {
                            monitorAvvio.update();
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    } // fine del metodo


    /**
     * Lancio iniziale del programma.
     * </p>
     * Metodo chiamato dal metodo main del modulo iniziale <br>
     * Viene eseguito una sola volta <br>
     * Invoca il metodo delegato dell'istanza Singleton di Progetto <br>
     *
     * @param argomenti eventuali parametri in ingresso
     * @param primoModuloProvvisorio istanza provvisoria del primo modulo
     */
    public static void lancia(String[] argomenti, Modulo primoModuloProvvisorio) {
        /* variabili e costanti locali di lavoro */
        Progetto progetto;

        try { // prova ad eseguire il codice

            /* Regola gli argomenti in ingresso al programma */
            Progetto.regolaArgomenti(argomenti);

            /* regola il primo modulo */
            PRIMO_MODULO = primoModuloProvvisorio;

            /* recupera il progetto */
            progetto = getIstanza();

            /* lancia il progetto con il primo modulo */
            progetto.lanciaIstanza(primoModuloProvvisorio);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } // fine del metodo


    /**
     * Regola gli argomenti in ingresso al programma.
     * </p>
     * Gli argomenti possono essere separati da:<ul>
     * <li> virgola </li>
     * <li> punto e virgola </li>
     * <li> & (e commerciale) </li>
     * <li> - (trattino preceduto da spazio) </li>
     * </ul>
     * Se vengono riconosciuti da Java, sono già separati negli elementi della matrice <br>
     * Se non riconosciuti, arrivano tutti insieme nel primo elemento della matrice
     * e vengono correttamente separati
     * <p/>
     * Ogni argomento è individuato dalla chiave e dal valore, separati dal segno uguale (=) <br>
     * I parametri booleani possono essere privi del valore (e del segno uguale) e si intendono
     * uguali a vero (true) <br>
     * Costruisce la mappa di tutti i valori trovati <br>
     * Associa i valori riconosciuti alle costanti globali di progetto <br>
     *
     * @param argomenti eventuali (quasi mai) parametri in ingresso
     */
    private static void regolaArgomenti(String[] argomenti) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        String chiave;

        try { // prova ad eseguire il codice

            /* Costruisce la mappa di tutti i valori trovati */
            Lib.Clas.setArg(argomenti);

            /* Associa i valori riconosciuti alle costanti globali di progetto */
            /* traverso tutta la collezione */
            for (Pref.Cost cost : Pref.Cost.values()) {

                chiave = cost.getWrap().getChiave();
                if (Lib.Pref.isArg(chiave)) {
                    switch (cost.getWrap().getTipoDati()) {
                        case booleano:
                            valore = Lib.Pref.getBool(chiave);
                            break;
                        case stringa:
                            valore = Lib.Pref.getStr(chiave);
                            break;
                        case intero:
                            valore = Lib.Pref.getInt(chiave);
                            break;
                        default: // caso non definito
                            break;
                    } // fine del blocco switch

                    cost.setValore(valore);
                }// fine del blocco if
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } // fine del metodo


    /**
     * Lancio iniziale del programma.
     * </p>
     * Metodo chiamato dal metodo main del modulo iniziale <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * regola alcuni parametri <br>
     * crea il primo modulo del progetto (e a cascata tutti gli altri) <br>
     * crea la parte statica di tutti i moduli<br>
     * inizializza tutti i moduli <br>
     * lancia il modulo iniziale del programma <br>
     *
     * @param primoModuloProvvisorio istanza usata solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito) <br>
     */
    private void lanciaIstanza(Modulo primoModuloProvvisorio) {
        /* variabili e costanti locali di lavoro */
        String nomePrimoModulo;
        Modulo primoModulo;
        boolean loggato;
        Progetto progetto;
        ProgettoSetup setup = null;
        Aggiornamento agg;

        try {    // prova ad eseguire il codice

            /* regola il nome del programma  */
            this.setNomeProgramma(primoModuloProvvisorio.getNomeProgramma());
            this.regolaNomeProgramma(primoModuloProvvisorio);

            /* apre la finestra di monitor della fase di avvio */
            monitorAvvio = new MonitorAvvio();

            /* recupera il progetto */
            progetto = getIstanza();

            Progetto.regolaCatUpdate();

            loggato = this.loginUtente();

            if (!Pref.Gen.setup.is()) {
                setup = progetto.getSetup();
            }// fine del blocco if

            /**
             * Crea il primo modulo del progetto (e a cascata tutti gli altri)
             * Il primo modulo viene assegnato al nodo root dell'albero moduli.
             */
            this.creaPrimoModulo(primoModuloProvvisorio);

            /* Esegue l'eventuale aggiornamento dei moduli del programma
            * Se i moduli vengono aggiornati, chiude il programma
            * per riavviarlo ed utilizzare i nuovi moduli
            */
            agg = new Aggiornamento();
            agg.esegue();

            /**
             * Crea e aggiunge i moduli fissi al progetto.
             * Questi moduli si aggiungono sempre al nodo Root.
             */
            ModFissi.creaAnte();

            if (DEBUG) {
                getAlberoModuli().mostra();
            }// fine del blocco if

            /* regola il totale moduli nel monitor */
            int quanti = getListaModuli().size();
            monitorAvvio.setTotModuli(quanti);

            /* regola la parte statica di tutti i moduli/modelli del Progetto
             * crea campi, viste, set...  */
            monitorAvvio.setFase("Creazione");
            this.preparaModuli();

            /**
             * Crea ed aggiunge al progetto eventuali altri moduli fissi
             * dopo che i vari moduli sono stati preparati.
             * (i campi sono stati creati)
             */
            ModFissi.creaPost();

            if (DEBUG) {
                getAlberoModuli().mostra();
            }// fine del blocco if

            /**
             * spazzola su e giu tutti i moduli fino a quando
             * non sono tutti relazionati.
             * crea / allinea i campi sul database //@todo falso  /gac 5-5-08
             */
            monitorAvvio.setFase("Controllo struttura dati");
            this.pendoloModuli();

            /* inizializza tutti i moduli dal basso verso l'alto */
            monitorAvvio.setFase("Inizializzazione");
            this.inizializzaModuli();

            /* inizializza la palette */
//            this.inizializzaPalette();

            if (!Pref.Gen.setup.is()) {
                setup.creaDati();
            }// fine del blocco if

            /* effettua il login dell'utente */
//            if (loggato) { //@todo c'è qualcosa che non mi funziona gac/25-2-07
            if (true) {

                /* segnala al progetto che e' terminata la fase
                 * di preparazione di moduli
                 * da questo momento le query usano anche il filtro hard*/
                setPronto(true);

                /* recupera il primo modulo dalla collezione del progetto */
                nomePrimoModulo = primoModuloProvvisorio.getNomeChiave();
                primoModulo = Progetto.getModulo(nomePrimoModulo);

                /* chiude il monitor della fase di avvio */
                monitorAvvio.close();

                /* lancia il modulo iniziale del programma */
                this.avviaModulo(primoModulo);

            } else {

                /* login non riuscito */
                chiudeProgramma();

            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Prima istallazione.
     * <p/>
     * Esegue il metodo iniziale di istallazione per ogni modulo <br>
     */
    private ProgettoSetup getSetup() {
        /* variabili e costanti locali di lavoro */
        ProgettoSetup setup = null;

        try { // prova ad eseguire il codice
            setup = new ProgettoSetup();
            setup.avvia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return setup;
    }


    /**
     * Controlla se è necessario creare il modulo Contatori
     * per il Progetto.
     * <p/>
     *
     * @return true se deve usare i contatori
     */
    private boolean isUsaContatori() {
        /* variabili e costanti locali di lavoro */
        boolean usa = false;
        ArrayList<AlberoNodo> nodi;
        Object oggetto;
        Modulo modulo;
        Modello modello;
        NodoModuloOggetto oggettoNodo;

        try {    // prova ad eseguire il codice

            nodi = this.getAlberoModuli().getNodi();
            for (AlberoNodo nodo : nodi) {
                oggetto = nodo.getUserObject();
                if (oggetto instanceof NodoModuloOggetto) {
                    oggettoNodo = (NodoModuloOggetto)oggetto;
                    modulo = oggettoNodo.getModulo();
                    if (modulo != null) {
                        modello = modulo.getModello();
                        if (modello.isUsaContatori()) {
                            usa = true;
                            break;
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return usa;
    }

//    /**
//     * inizializza la palette.
//     * <p/>
//     * il primo modulo va sempre in palette anche se e' una tabella
//     * viene levato dall'elenco prima del filtro e rimesso
//     * in prima posizione alla fine
//     */
//    public void inizializzaPalette() {
//        /* variabili e costanti locali di lavoro */
//        Palette palette;
//        ArrayList<Modulo> moduli;
//        Modulo primoModulo;
//
//        try { // prova ad eseguire il codice
//            palette = this.getPalette();
//
//            if (palette != null) {
//
//                primoModulo = getPrimoModulo();
//                moduli = getModuliPreorder();
//                moduli.remove(primoModulo);
//                moduli = Progetto.filtraModuliFissi(moduli);
//                moduli = Progetto.filtraModuliTabella(moduli);
//                moduli.add(0, primoModulo);
//                palette.setModuli(moduli);
//                palette.inizializza();
//            }// fine del blocco if
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }


    /**
     * Ritorna il primo modulo.
     * <p/>
     */
    public static Modulo getPrimoModulo() {
        /* variabili e costanti locali di lavoro */
        ArrayList moduli = null;
        Modulo modulo;
        Modulo moduloOut = null;
        String chiave;
        String chiavePrimo;

        try {    // prova ad eseguire il codice
            chiavePrimo = PRIMO_MODULO.getNomeChiave();
            moduli = Progetto.getModuliPreorder();
            for (Object oggetto : moduli) {
                if (oggetto instanceof Modulo) {
                    modulo = (Modulo)oggetto;
                    chiave = modulo.getNomeChiave();
                    if (chiave.equals(chiavePrimo)) {
                        moduloOut = modulo;
                        break;
                    }// fine del blocco if

                }// fine del blocco if

            } // fine del ciclo for-each

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return moduloOut;
    }


    /**
     * Richiede il login all'utente.
     * <p/>
     * Presenta il dialogo di login <br>
     * Regola la variabile id utente corrente in Progetto <br>
     *
     * @return true se il login e' riuscito
     */
    private boolean loginUtente() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua;
        UtentiModulo mod;
        int id = 0;

        // todo provvisorio, dovra' presentare un dialogo
        String nomeUtente;

        try {    // prova ad eseguire il codice
            nomeUtente = Pref.Gen.nomeUtente.str();

            mod = getModuloUtenti();
            continua = (mod != null);

            if (continua) {
                id = mod.getIdUtente(nomeUtente);
                continua = (id > 0);
            }// fine del blocco if

            if (continua) {
                setIdUtenteCorrente(id);
                riuscito = true;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Ritorna il livello dell'utente corrente.
     * <p/>
     *
     * @return il livello dell'utente, null se non trovato
     *
     * @see Utenti
     */
    public static Utenti.TipoUte getLivelloUtenteCorrente() {
        /* variabili e costanti locali di lavoro */
        Utenti.TipoUte tipoLivello = null;
        int id;

        try { // prova ad eseguire il codice
            id = getIdUtenteCorrente();
            if (id > 0) {
                tipoLivello = getModuloUtenti().getLivelloUtente(id);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return tipoLivello;
    }


    /**
     * Regola le funzioni generali del programma, comuni a tutti i moduli.
     * <p/>
     * Invocato dal ciclo Inizia.
     *
     * @return true se riuscito
     */
    private boolean funzioniGenerali() {

        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua = true;

        try { // prova ad eseguire il codice

            /* recupera i dati di configurazione dal file
             * di configurazione del programma */
            if (continua) {
                Config.getIstanza().avvia();
            }// fine del blocco if

            /* regola le impostazioni relative alla lingua,
             * alle convenzioni, al sistema */
            if (continua) {
                this.regolaLocale();
            }// fine del blocco if

            /* Regola i font */
            if (continua) {
                this.regolaFont();
            }// fine del blocco if

            /* Crea le azioni */
            if (continua) {
                this.creaAzioni();
            }// fine del blocco if

            /* regola il look-and-feel della applicazione */
            if (continua) {
                this.setLookAndFeel();
//                this.setLookAndFeel(CostanteBase.LOOK_GAC);
//                this.selezionaLookAndFeel(CostanteBase.LOOK_JAVA);
//                this.selezionaLookAndFeel(CostanteBase.LOOK_METAL);
            }// fine del blocco if

            /* Regola i testi */
            if (continua) {
                this.regolaTesti();
            }// fine del blocco if

            /* regola il nome dell'utente collegato */
            if (continua) {
                this.regolaUtente();
            }// fine del blocco if

            /* Crea il database per il modulo preferenze */
            if (continua) {
//                continua = this.creaDbPreferenze();
            }// fine del blocco if

            /* Regola la partenza del programma (chi, dove, come) */
            if (continua) {
                this.regolaStart();
            }// fine del blocco if

            if (continua) {
                riuscito = true;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;

    } /* fine del metodo funzioniGeneraliProgramma */


    /**
     * Regola le impostazioni Locale
     */
    private void regolaLocale() {
        /** Normalmente prende il Locale dal sistema in uso
         *  se voglio, qui lo posso cambiare...*/

        //Locale.setDefault(...)

    } /* fine del metodo  */


    /**
     * Ritorna il modulo Contatore.
     * <p/>
     *
     * @return il modulo Contatore
     */
    public static ContatoreModulo getModuloContatore() {
        /* variabili e costanti locali di lavoro */
        ContatoreModulo moduloSpecifico = null;
        Modulo modulo;
        try {    // prova ad eseguire il codice
            modulo = getModulo(Contatore.NOME_MODULO);
            if (modulo != null) {
                if (modulo instanceof ContatoreModulo) {
                    moduloSpecifico = (ContatoreModulo)modulo;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return moduloSpecifico;
    }


    /**
     * Ritorna il modulo Utenti.
     * <p/>
     *
     * @return il modulo Utenti
     */
    public static UtentiModulo getModuloUtenti() {
        /* variabili e costanti locali di lavoro */
        UtentiModulo moduloSpecifico = null;
        Modulo modulo;
        try {    // prova ad eseguire il codice
            modulo = getModulo(Utenti.NOME_MODULO);
            if (modulo != null) {
                if (modulo instanceof UtentiModulo) {
                    moduloSpecifico = (UtentiModulo)modulo;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return moduloSpecifico;
    }


    /**
     * Ritorna il modulo Gruppi Utenti.
     * <p/>
     *
     * @return il modulo Gruppi Utenti
     */
    public static GruppiModulo getModuloGruppi() {
        /* variabili e costanti locali di lavoro */
        GruppiModulo moduloSpecifico = null;
        Modulo modulo;
        try {    // prova ad eseguire il codice
            modulo = getModulo(Gruppi.NOME_MODULO);
            if (modulo != null) {
                if (modulo instanceof GruppiModulo) {
                    moduloSpecifico = (GruppiModulo)modulo;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return moduloSpecifico;
    }


    /**
     * Ritorna il modulo Selezione Lista.
     * <p/>
     *
     * @return il modulo Selezione Lista
     */
    public static SelezioneModulo getModuloSelezione() {
        /* variabili e costanti locali di lavoro */
        SelezioneModulo moduloSpecifico = null;
        Modulo modulo = null;
        try {    // prova ad eseguire il codice
            modulo = getModulo(Selezione.NOME_MODULO);
            if (modulo != null) {
                if (modulo instanceof SelezioneModulo) {
                    moduloSpecifico = (SelezioneModulo)modulo;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return moduloSpecifico;
    }


    /**
     * Ritorna il modulo Semaforo.
     * <p/>
     *
     * @return il modulo Semaforo
     */
    public static SemaforoModulo getModuloSemaforo() {
        /* variabili e costanti locali di lavoro */
        SemaforoModulo moduloSpecifico = null;
        Modulo modulo = null;
        try {    // prova ad eseguire il codice
            modulo = getModulo(Semaforo.NOME_MODULO);
            if (modulo != null) {
                if (modulo instanceof SemaforoModulo) {
                    moduloSpecifico = (SemaforoModulo)modulo;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return moduloSpecifico;
    }


    /**
     * Recupera un campo dalla chiave.
     * <p/>
     * La chiave e' una stringa costituita da nomemodulo.nomecampo
     */
    public static Campo getCampo(String chiave) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        boolean continua;
        Modulo modulo = null;
        String nomeModulo = "";
        String nomeCampo = "";
        ArrayList<String> parti;

        try { // prova ad eseguire il codice
            continua = true;

            /* estrae le due parti della chiave*/
            if (continua) {
                parti = Libreria.creaLista(chiave, ".");
                if (parti.size() >= 2) {
                    nomeModulo = parti.get(0);
                    nomeCampo = parti.get(1);
                } else {
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            /* recupera il modulo */
            if (continua) {
                modulo = Progetto.getModulo(nomeModulo);
                if (modulo == null) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* recupera il campo dal modulo */
            if (continua) {
                campo = modulo.getCampo(nomeCampo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Crea i Navigatori dei moduli fissi
     * (oltre a quelli delle preferenze, gia' creati).
     * <p/>
     * Chiamato dopo che le preferenze sono diventate disponibili.<br>
     * (I moduli fissi sono stati inizialmente creati senza navigatori)
     */
    private void creaNavigatoriModuliFissi() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        Navigatore navMaster;
        Navigatore navSlave;
        Modulo moduloUtenti;
        Modulo moduloGruppi;

        try {    // prova ad eseguire il codice

            moduloUtenti = getModuloUtenti();
            moduloGruppi = getModuloGruppi();

            /* crea e aggiunge i navigatori di default dopo che le preferenze
             * sono diventate disponibili
             * (i moduli fissi non creano i navigatori)*/

            /* navigatore LS del modulo Utenti contenuto come slave nel navigatore
             * NN del modulo Gruppi */
            /* crea il navigatore slave da inserire nel navigatore NAV_UG di Gruppi */
            navSlave = NavigatoreFactory.listaScheda(moduloUtenti);
            navSlave.setNomeChiave(Utenti.NAV_UG_SLAVE);
            navSlave.setUsaPannelloUnico(false);
            navSlave.setOrizzontale(false);
            navSlave.setRigheLista(10);
            moduloUtenti.addNavigatoreCorrente(navSlave);
            navSlave.inizializza();

            /* navigatore LS del modulo Gruppi contenuto come master nel navigatore
             * NN del modulo Gruppi */
            navMaster = NavigatoreFactory.listaScheda(moduloGruppi);
            navMaster.setNomeChiave(Gruppi.NAV_UG_MASTER);
            navMaster.setUsaPannelloUnico(true);
            moduloGruppi.addNavigatoreCorrente(navMaster);
            navMaster.inizializza();

            /* navigatore NN del modulo Gruppi */
            nav = NavigatoreFactory.navigatoreNavigatore(moduloGruppi,
                    Gruppi.NAV_UG_MASTER,
                    Utenti.NOME_MODULO,
                    Utenti.NAV_UG_SLAVE);
            nav.setUsaFinestra(true);
            nav.setTitoloFinestra("Utenti e Gruppi");
            nav.setNomeChiave(Gruppi.NAV_UG);
            moduloGruppi.addNavigatoreCorrente(nav);
            nav.inizializza();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea le azioni.
     * <p/>
     * Crea tutte le istanze delle Azioni <br>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Le inizializza tutte <br>
     */
    private void creaAzioni() {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, Azione> azioni;

        try { // prova ad eseguire il codice

            /* crea e inizializza tutte le azioni base del Progetto */
            AzioneFactory.crea(this);
            azioni = this.getAzioni();
            for (Azione azione : azioni.values()) {
                azione.inizializza();
                azione.avvia(this.gestore);
            }

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola i testi
     */
    private void regolaTesti() {
    } /* fine del metodo regolaTesti */


    /**
     * Regola il nome dell'utente collegato
     */
    private void regolaUtente() {
        Flag.setUtente("gac");
    } /* fine del metodo regolaTesti */


    /**
     * Crea il database per il modulo preferenze.
     * <p/>
     * Il database non viene inizializzato.
     * Non vengono aperte connessioni.
     *
     * @return true se riiuscito
     */
    private boolean creaDbPreferenze() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        Db db;
        String indirizzo;
        String nomeDbPref;
        int tipoDbPref;
        int portaDbPref;
        String loginDbPref;
        String passDbPref;
        int modoDbPref;

        try {    // prova ad eseguire il codice

            /* recupera i dati per il collegamento dalle variabili di istanza */
            indirizzo = Config.getValore(Config.INDIRIZZO_DB_PREF_SHARED);
            nomeDbPref = Config.getValore(Config.NOME_DB_PREF_SHARED);
            tipoDbPref = Libreria.getInt(Config.getValore(Config.TIPO_DB_PREF_SHARED));
            portaDbPref = Libreria.getInt(Config.getValore(Config.PORTA_DB_PREF_SHARED));
            loginDbPref = Config.getValore(Config.LOGIN_DB_PREF_SHARED);
            passDbPref = Config.getValore(Config.PASS_DB_PREF_SHARED);

            /* se l'indirizzo e' vuoto usa quello di default */
            if (Lib.Testo.isVuota(indirizzo)) {
                indirizzo = Lib.Sist.getIndirizzoPreferenze();
            }// fine del blocco if

            /* se il nome e' vuoto usa quello di default */
            if (Lib.Testo.isVuota(nomeDbPref)) {
                nomeDbPref = PRIMO_MODULO.getNomeChiave().toLowerCase();
            }// fine del blocco if

            /* se il tipo e' vuoto usa HSQLDB */
            if (tipoDbPref == 0) {
                tipoDbPref = Db.SQL_HSQLDB;
            }// fine del blocco if

            /* nota: se il login, password, porta sono vuoti, lascia vuoti
             * quando questi valori sono vuoti il db usa i
             * propri valori di default */

            /* se e' un indirizzo di filesystem il tipo e' per forza HSQLDB
             * e il modo e' per forza stand-alone
             * se e' un indirizzo IP (o nome) il tipo e' quello specificato
             * nel file di configurazione (o HSQLDB se non specificato)e il modo
             * e' per forza client-server */
            if (this.isFileSystem(indirizzo)) {   // indirizzo di filesystem
                tipoDbPref = Db.SQL_HSQLDB;
                modoDbPref = Db.MODO_STAND_ALONE;
            } else {     // indirizzo IP o nome filesystem
                modoDbPref = Db.MODO_CLIENT_SERVER;
            }// fine del blocco if-else

            /* crea il database */
            db = DbFactory.crea(tipoDbPref);
            db.setNomeDatabase(nomeDbPref);
            db.setHost(indirizzo);  // solo caso multiutente
            db.setPercorsoDati(indirizzo);  // solo caso monoutente
            db.setModoFunzionamento(modoDbPref);
            db.setTipoTavole(Db.TAVOLE_MEMORY); // solo caso monoutente

            if (portaDbPref != 0) {
                db.setPorta(portaDbPref);
            }// fine del blocco if

            if (Lib.Testo.isValida(loginDbPref)) {
                db.setLogin(loginDbPref);
            }// fine del blocco if

            if (Lib.Testo.isValida(passDbPref)) {
                db.setPassword(passDbPref);
            }// fine del blocco if

            /* assegna il database al Progetto */
            this.setDbPreferenze(db);

            /* valore di ritorno */
            riuscito = true;

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Determina se è un indirizzo di filesystem o IP.
     * <p/>
     *
     * @param indirizzo di filesystem o di IP
     *
     * @return vero se filesystem
     *         falso se indirizzo IP (o nome risolvibile da DNS)
     */
    private boolean isFileSystem(String indirizzo) {
        /* variabili e costanti locali di lavoro */
        boolean filesystem = false;
        String sepUnix;
        String sepDos;

        try { // prova ad eseguire il codice
            sepDos = "\\";
            sepUnix = "/";

            if (indirizzo.indexOf(sepDos) != -1) {
                filesystem = true;
            }// fine del blocco if

            if (indirizzo.indexOf(sepUnix) != -1) {
                filesystem = true;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filesystem;
    }


    /**
     * Crea il database con i dati di default.
     * <p/>
     * Il database viene inizializzato.
     * Non vengono aperte connessioni.
     */
    public static Db creaDbDefault() {
        /* variabili e costanti locali di lavoro */
        Db db = null;

        try {    // prova ad eseguire il codice

            if (getDbDefault() == null) {
                /* Crea un database HSQLDB contenente i dati di default */
                db = DbFactory.crea(Db.SQL_HSQLDB);

                db.setNomeDatabase(Pref.DB.archivio.str());
                db.setPercorsoDati(Lib.Sist.getDirDatiStandard());
                db.setHost("localhost");
                db.setLogin("sa");
                db.setPassword("");
                db.setModoFunzionamento(Db.MODO_STAND_ALONE);
                db.setTipoTavole(Db.TAVOLE_MEMORY);

                /* inizializza il database */
                db.inizializza();

                Progetto.getIstanza().setDbDefault(db);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return db;
    }


    /**
     * Carica le famiglie font interne del progetto
     */
    private void regolaFont() {
        this.famiglieFont = LibFont.generaCollezioneFamiglieFontInterne();
    }


    /**
     * Regola la partenza del programma (chi, dove, come)
     */
    private void regolaStart() {
    } /* fine del metodo regolaStart */


    /**
     * Inizializza una lista di Moduli <br>
     */
    private void inizializzaModuli() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Modulo> moduli;
        Modulo mod;

        try {    // prova ad eseguire il codice
            moduli = getModuliPostorder();

            for (int k = 0; k < moduli.size(); k++) {
                mod = moduli.get(k);
                monitorAvvio.update();
//                monitorAvvio.setFase(mod.getNomePubblico());
                mod.inizializza();
            } // fine del ciclo for


        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    } // fine del metodo


    /**
     * Controlla se nella Tavola Relazioni del Progetto esiste
     * una relazione preferita tra due tavole specificate.
     *
     * @param tavolaPartenza la tavola di partenza
     * @param tavolaDestinazione la tavola di destinazione
     *
     * @return true se esiste una relazione preferita tra le due tavole
     */
    private boolean esisteRelazionePreferita(String tavolaPartenza, String tavolaDestinazione) {
        boolean esiste = false;
        String tavolaPartenzaDaRelazione = null;
        String tavolaDestinazioneDaRelazione = null;
        ArrayList<Relazione> unaTavolaRelazioni = getTavolaRelazioni();
        boolean preferita = false;

        for (int k = 0; k < unaTavolaRelazioni.size(); k++) {

            // estrae la relazione dalla Tavola Relazioni
            Relazione unaRelazione = (Relazione)unaTavolaRelazioni.get(k);

            // estrae le tavole dalla relazione
            tavolaPartenzaDaRelazione = unaRelazione.getTavolaPartenza();
            tavolaDestinazioneDaRelazione = unaRelazione.getTavolaArrivo();
            preferita = unaRelazione.isRelazionePreferita();

            // se la relazione e' preferita, controlla anche le tavole
            if (preferita) {
                if (tavolaPartenza.equalsIgnoreCase(tavolaPartenzaDaRelazione)) {
                    if (tavolaDestinazione.equalsIgnoreCase(tavolaDestinazioneDaRelazione)) {
                        esiste = true;
                        break;
                    } /* fine del blocco if */
                } /* fine del blocco if */
            } /* fine del blocco if */

        } /* fine del blocco for */

        return esiste;
    } /* fine del metodo */


    /**
     * Regola il nome del programma.
     */
    public void regolaNomeProgramma(Modulo primoModulo) {
        /* variabili e costanti locali di lavoro */
        String nomeProgramma;

        try { // prova ad eseguire il codice
            nomeProgramma = primoModulo.getNomeProgramma();
//            Flag.setNomeProgramma(nomeProgramma);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Aggiunge un elenco di moduli alla collezione
     *
     * @param unElencoModuli ArrayList di oggetti String con i nomi delle classi dei moduli
     * @param unPercorsoBaseModuli percorso da aggiungere davanti ai nomi
     * dei moduli per identificare pienamente la classe
     */
    public void addModuli(ArrayList unElencoModuli, String unPercorsoBaseModuli) {
        /* ciclo for */
        for (int k = 0; k < unElencoModuli.size(); k++) {
            String unNomeClasse = (String)unElencoModuli.get(k);
            String unPercorsoClasse = unPercorsoBaseModuli + unNomeClasse;
            unPercorsoClasse = Lib.Testo.converteBarreInPunti(unPercorsoClasse);
            /** Crea una istanza della classe dal nome
             * e la aggiunge alla collezion dei moduli */
            try {                                   // prova ad eseguire il codice
                Class unaClasse = Class.forName(unPercorsoClasse);
                Object unOggetto = unaClasse.newInstance();
                Modulo unModulo = (Modulo)unOggetto;
//                this.addModulo(unModulo); //todo ?
            } catch (Exception unErrore) {           // intercetta l'errore
                /** messaggio di errore */
                Errore.crea(unErrore);
            } /* fine del blocco try-catch */

            //Modulo unModulo = Class.forName(unNomeModulo);
        } /* fine del blocco for */

    } /* fine del metodo */


    /**
     * lancia il modulo iniziale del progetto.
     */
    private void avviaModulo(Modulo primoModulo) {
        /* variabili e costanti locali di lavoro */
        Azione azioneAvvia;
        ActionEvent eventoFittizio;

        try { // prova ad eseguire il codice

            /* invia un evento fittizio all'azione per farla partire */
            eventoFittizio = new ActionEvent(this, ActionEvent.ACTION_FIRST, "");
            azioneAvvia = primoModulo.getModuloBase().getAzioneModulo();
            azioneAvvia.getAzione().actionPerformed(eventoFittizio);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola il look-and-feel dell'applicazione
     */
    private void setLookAndFeel(String unLook) {

        /* controllo errori e gestione delle eccezioni */
        try {
            UIManager.setLookAndFeel(unLook);
        } catch (Exception unErrore) {
            try {
                /* se non trova quello richiesto, mette quello standard */
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception unEccezione) {
                /** se non trova neanche questo ! */
                new Errore(unEccezione);
            } /* fine del blocco try-catch */
        } /* fine del blocco try-catch */

    } // fine del metodo


    /**
     * Regola il look-and-feel dell'applicazione
     */
    private void setLookAndFeel() {
        /* variabili e costanti locali di lavoro */
        String laf;

        /* controllo errori e gestione delle eccezioni */
        try {
            /* Usa il Look and Feel di sistema (Aqua su Mac OS X) */
            laf = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(laf);
        } catch (Exception unErrore) {
        } /* fine del blocco try-catch */

    } // fine del metodo


    /**
     * Aggiunge un modulo alla collezione.
     * </p>
     * Usa il nome chiave del modulo come chiave per la collezione
     * Se esiste già una entri con la stessa chiave non fa nulla
     *
     * @param unModulo da aggiungere alla collezione
     *
     * @return la chiave del modulo aggiunto, stringa vuota se non aggiunto
     */
    public String addModuloCollezione(Modulo unModulo) {
        /** variabili e costanti locali di lavoro */
        String unaChiave = "";
        LinkedHashMap<String, Modulo> moduli;

        try {    // prova ad eseguire il codice
            if (unModulo != null) {

                /* recupera la collezione */
                moduli = this.moduli;

                /* recupera il nome chiave del modulo */
                unaChiave = unModulo.getNomeChiave();

                /* aggiunge il modulo se non già esistente */
                if (!moduli.containsKey(unaChiave)) {
                    moduli.put(unaChiave, unModulo);
                }// fine del blocco if-else

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unaChiave;
    } /* fine del metodo */


    /**
     * Crea una nuova istanza definitiva del Modulo.
     * </p>
     *
     * @param moduloProvvisorio l'istanza provvisoria del modulo da creare
     * @param nodo dell'albero moduli sotto il quale attaccare il nuovo modulo
     *
     * @return il modulo definitivo creato
     */
    public Modulo creaIstanzaModulo(Modulo moduloProvvisorio, AlberoNodo nodo) {
        /* variabili e costanti locali di lavoro */
        Modulo modulo = null;
        String classpath;

        try {    // prova ad eseguire il codice

            /* recupera il path completo della classe */
            classpath = moduloProvvisorio.getClass().getName();

            /* Crea una istanza di classe Modulo, col parametro AlberoNodo */
            modulo = Lib.Clas.modulo(classpath, nodo);

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return modulo;

    } /* fine del metodo */


    /**
     * Aggiunge un modulo all'albero strutturato delle dipendenze dei moduli.
     * </p>
     * Chiamato sia dal primo modulo (con unNodo nullo),
     * sia dai successivi <br>
     * Aggiunge un nodo figlio ad un nodo parente <br>
     * Se il nodo parente e' nullo, il modulo figlio diventa il nodo root
     * e tutta la struttura esistente (le preferenze) viene attaccata sotto<br>
     *
     * @param modulo modulo da aggiungere all'albero
     * @param unNodo nodo dell'albero sotto al quale aggiungere unModulo
     * (null per aggiungere come nodo root)
     *
     * @return il nuovo nodo aggiunto
     */
    public AlberoNodo addModuloAlbero(Modulo modulo, AlberoNodo unNodo) {

        /* variabili e costanti locali di lavoro */
        NodoModulo nodo = null;
        AlberoNodo nodoRoot;
        AlberoNodo nodoSotto;
        String percorsoModulo;
        String chiaveModulo;

        try {    // prova ad eseguire il codice

            /* recupera il percorso dal modulo */
            percorsoModulo = modulo.getClass().getName();

            /* recupera la chiave del modulo */
            chiaveModulo = modulo.getNomeChiave();

            /* costruisce il nodo dal Modulo */
            nodo = new NodoModulo(new NodoModuloOggetto(percorsoModulo));
            nodo.setNomeChiave(chiaveModulo);
            nodo.setAllowsChildren(true);

            if (unNodo == null) {  // primo modulo

                /* prende tutta la struttura sotto il nodo root
                 * e la attacca sotto il nuovo nodo, poi
                 * assegna il nodo nuovo all'albero come
                 * nodo root */
                nodoRoot = getAlberoModuli().getNodoRoot();
                if (nodoRoot.getChildCount() > 0) {  // ci sono già altri moduli
                    nodoSotto = (AlberoNodo)nodoRoot.getChildAt(0);
                    nodo.add(nodoSotto);
                }// fine del blocco if-else
                getAlberoModuli().getModelloAlbero().setRoot(nodo);

            } else {  // modulo successivo

                /* aggiunge il nuovo nodo all'albero
                 * sotto al nodo specificato */
                getAlberoModuli().addNodo(nodo, unNodo);

            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nodo;
    } // fine del metodo


    /**
     * Crea il primo Modulo e a cascata tutti i Moduli dipendenti.
     * </p>
     * Agggiunge il modulo all'albero strutturato dei moduli <br>
     * Costruisce una istanza della classe <br>
     * Nel costruttore del Modulo vengono creati tutti i Moduli dipendenti
     * (e questi a loro volta creano tutti i Moduli dipendenti...) <br>
     * Aggiunge il riferimento nella collezione dei Moduli <br>
     *
     * @param primoModuloProvvisorio primo modulo del programma
     */
    private void creaPrimoModulo(Modulo primoModuloProvvisorio) {

        /* crea il primo modulo senza nodo, diventa root */
        this.creaModulo(primoModuloProvvisorio, null);
//        this.getAlberoModuli().mostra();
//        int a = 87;

    } // fine del metodo


    /**
     * Crea un Modulo e tutti i Moduli da esso dipendenti.
     * </p>
     * Agggiunge un nodo all'albero strutturato dei moduli <br>
     * Costruisce una nuova istanza del modulo <br>
     * Nel costruttore del Modulo vengono creati tutti i Moduli dipendenti
     * (e questi a loro volta creano tutti i Moduli dipendenti...) <br>
     * Aggiunge al Nodo il riferimento al Modulo appena creato <br>
     * Aggiunge il modulo alla collezione dei Moduli <br>
     *
     * @param unModuloFiglio modulo provvisorio per costruire l'istanza
     * definitiva, da aggiungere all'albero ed alla collezione dei moduli
     * @param unNodo nodo dell'albero sotto al quale aggiungere unModulo, null per il primo modulo
     *
     * @return modulo definitivo creato per questo modulo nell'albero moduli
     */
    public Modulo creaModulo(Modulo unModuloFiglio, AlberoNodo unNodo) {
        /* variabili e costanti locali di lavoro */
        AlberoNodo unNodoNuovo;
        AlberoNodo unNodoPadre;
        Modulo unModuloDefinitivo = null;
        String nomeChiave;
        boolean moduloEsistente;
        String nomePadre;
        ArrayList<String> moduliPadre = null;

        try { // prova ad eseguire il codice

            /* Agggiunge il modulo all'albero strutturato dei moduli */
            unNodoNuovo = this.addModuloAlbero(unModuloFiglio, unNodo);

            /* Se il nodo è nullo, è il primo modulo */
            if (unNodo != null) {
                moduliPadre = new ArrayList<String>();
                unNodoPadre = unNodo;
                while (unNodoPadre != null) {
                    nomePadre = unNodoPadre.getNomeChiave();
                    moduliPadre.add(nomePadre);
                    unNodoPadre = (AlberoNodo)unNodoPadre.getParent();
                }// fine del blocco while
            }// fine del blocco if

            /*
             * recupera il nome chiave dal modulo provvisorio in arrivo e
             * lo usa per controllare se il modulo esiste già
             */
            nomeChiave = unModuloFiglio.getNomeChiave();

            /* spazzola l'array per cercare se esiste già */
            moduloEsistente = Lib.Array.isEsisteNome(moduliPadre, nomeChiave);

            /* lo crea solo se non esiste già nei padri dell'albero */
            if (!moduloEsistente) {
                unModuloDefinitivo = this.creaIstanzaModulo(unModuloFiglio, unNodoNuovo);

                /*
                 * regola il nome chiave del Modulo definitivo
                 * serve per evitare di usare il nome FISSO inserito di default
                 * nel costruttore del modulo
                 */
                unModuloDefinitivo.setNomeModulo(nomeChiave);

                unNodoNuovo.setModulo(unModuloDefinitivo);

                /* Aggiunge il Modulo definitivo nella collezione dei Moduli */
                this.addModuloCollezione(unModuloDefinitivo);

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore, unModuloFiglio.getNomeChiave());
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unModuloDefinitivo;
    }


    /**
     * Crea un Modulo e tutti i Moduli da esso dipendenti.
     * </p>
     * Agggiunge un nodo all'albero strutturato dei moduli <br>
     * Costruisce una nuova istanza del modulo <br>
     * Nel costruttore del Modulo vengono creati tutti i Moduli dipendenti
     * (e questi a loro volta creano tutti i Moduli dipendenti...) <br>
     * Aggiunge al Nodo il riferimento al Modulo appena creato <br>
     * Aggiunge il modulo alla collezione dei Moduli <br>
     *
     * @param modulo modulo provvisorio per costruire l'istanza
     * definitiva, da aggiungere all'albero ed alla collezione dei moduli
     * @param unNodo nodo dell'albero sotto al quale aggiungere unModulo, null per il primo modulo
     *
     * @return modulo definitivo creato per questo modulo nell'albero moduli
     */
    public Modulo creaModuloNew(Modulo modulo, AlberoNodo unNodo) {
        /* variabili e costanti locali di lavoro */
        AlberoNodo unNodoNuovo;
        AlberoNodo unNodoPadre;
        Modulo unModuloDefinitivo = null;
        String nomeChiave;
        boolean moduloEsistente;
        String nomePadre;
        ArrayList<String> moduliPadre = null;
        boolean esiste;

        try { // prova ad eseguire il codice

            /* Aggiunge il modulo all'albero strutturato dei moduli */
            unNodoNuovo = this.addModuloAlbero(modulo, unNodo);

            /* Controlla se esiste già nella collezione */
            nomeChiave = modulo.getNomeChiave();
            esiste = this.moduli.containsKey(nomeChiave);

            /* Se non esiste crea l'istanza definitiva del modulo
             * e la aggiunge alla collezione */
            if (!esiste) {
                unModuloDefinitivo = this.creaIstanzaModulo(modulo, unNodoNuovo);

                /*
                 * regola il nome chiave del Modulo definitivo
                 * serve per evitare di usare il nome FISSO inserito di default
                 * nel costruttore del modulo
                 */
                unModuloDefinitivo.setNomeModulo(nomeChiave);
                unNodoNuovo.setModulo(unModuloDefinitivo);
                this.addModuloCollezione(unModuloDefinitivo);
            }// fine del blocco if

//            /* Se il nodo è nullo, è il primo modulo */
//            if (unNodo != null) {
//                moduliPadre = new ArrayList<String>();
//                unNodoPadre = unNodo;
//                while (unNodoPadre != null) {
//                    nomePadre = unNodoPadre.getNomeChiave();
//                    moduliPadre.add(nomePadre);
//                    unNodoPadre = (AlberoNodo)unNodoPadre.getParent();
//                }// fine del blocco while
//            }// fine del blocco if
//
//            /*
//             * recupera il nome chiave dal modulo provvisorio in arrivo e
//             * lo usa per controllare se il modulo esiste già
//             */
//            nomeChiave = modulo.getNomeChiave();
//
//            /* spazzola l'array per cercare se esiste già */
//            moduloEsistente = Lib.Array.isEsisteNome(moduliPadre, nomeChiave);
//
//            /* lo crea solo se non esiste già nei padri dell'albero */
//            if (!moduloEsistente) {
//                unModuloDefinitivo = this.creaIstanzaModulo(
//                        modulo,
//                        unNodoNuovo
//                );
//
//                /*
//                 * regola il nome chiave del Modulo definitivo
//                 * serve per evitare di usare il nome FISSO inserito di default
//                 * nel costruttore del modulo
//                 */
//                unModuloDefinitivo.setNomeModulo(nomeChiave);
//
//                unNodoNuovo.setModulo(unModuloDefinitivo);
//
//                /* Aggiunge il Modulo definitivo nella collezione dei Moduli */
//                this.addModuloCollezione(unModuloDefinitivo);
//
//            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore, modulo.getNomeChiave());
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unModuloDefinitivo;
    }


    /**
     * Regola il nome della cartella di update sul server ftp.
     * </p>
     */
    private static void regolaCatUpdate() {
        /* variabili e costanti locali di lavoro */
        Class classe;
        Package pac;
        String path;
        String cart;

        try { // prova ad eseguire il codice
            classe = PRIMO_MODULO.getClass();
            pac = classe.getPackage();
            path = pac.getName();

            cart = path.substring(path.lastIndexOf(".") + 1);
            setCatUpdate(cart);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } // fine del metodo


    /**
     * Crea tutti i moduli contenuti nella directory plugin. <br>
     */
    private void creaModuloPlugin() {
        try {    // prova ad eseguire il codice
            ;//todo da sviluppare
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    } // fine del metodo


    /**
     * Aggiunge una azione alla collezione.
     * <p/>
     * Recupera il nome chiave della azione, dall'azione stessa <br>
     * Aggiunge l'azione alla collezione, usando la chiave <br>
     * Se l'azione esisteva gi&agrave gia', non viene aggiunta <br>
     *
     * @param unAzione azione da aggiungere alla collezione interna
     */
    public void addAzione(Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        String unaChiave;

        try { // prova ad eseguire il codice
            unaChiave = unAzione.getChiave();
            this.getAzioni().put(unaChiave, unAzione);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge una Relazione alla tavola delle Relazioni del Progetto
     *
     * @param unaRelazione la relazione da aggiungere
     */
    public void addRelazione(Relazione unaRelazione) {

        /** Variabili e costanti locali di lavoro */
        String unaTavolaPartenza;
        String unaTavolaDestinazione;
        String unMessaggio;
        boolean aggiungiRelazione = true;
        ArrayList<Relazione> unaTavolaRelazioni = getTavolaRelazioni();

        try {    // prova ad eseguire il codice

            /* La stessa relazione potrebbe essere inserita piu' volte se lo stesso
             * modulo viene inizializzato piu' volte (questo e' normale).
             * Pertanto occorre controllare, prima di inserire una nuova relazione
             * nella tavola, che la stessa relazione non sia gia' esistente. */
            if (aggiungiRelazione) {
                if (unaTavolaRelazioni.contains(unaRelazione)) {    // usa equals()
                    aggiungiRelazione = false;
                } /* fine del blocco if */
            } /* fine del blocco if */

            /* Se si vuole aggiungere una relazione preferita, occorre
        * controllare che non ci sia gia' un'altra relazione preferita
        * tra le stesse due tavole.
        * Nel caso che esista gia', si avvisa il programmatore e non si
        * aggiunge la relazione. */
            if (aggiungiRelazione) {

                if (unaRelazione.isRelazionePreferita()) {

                    // estraggo i nomi delle tavole
                    unaTavolaPartenza = unaRelazione.getTavolaPartenza();
                    unaTavolaDestinazione = unaRelazione.getTavolaArrivo();
                    if (this.esisteRelazionePreferita(unaTavolaPartenza, unaTavolaDestinazione)) {
                        unMessaggio = "Relazione: ";
                        unMessaggio += unaRelazione.toString();
                        unMessaggio += "\n";
                        unMessaggio += "Esiste gia' una relazione preferita tra ";
                        unMessaggio += unaTavolaPartenza;
                        unMessaggio += " e ";
                        unMessaggio += unaTavolaDestinazione;
                        unMessaggio += "\n";
                        unMessaggio += "E' ammessa una sola relazione preferita tra due tavole.\n";
                        unMessaggio += "La relazione non verra' aggiunta.";

                        new MessaggioAvviso(unMessaggio);
                        aggiungiRelazione = false;

                    } /* fine del blocco if */
                } /* fine del blocco if */
            } /* fine del blocco if */

            /* Se ha passato i controlli, aggiunge la relazione */
            if (aggiungiRelazione) {
                unaTavolaRelazioni.add(unaRelazione);
            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Restituisce tutte le relazioni che puntano a un dato modulo.
     * <p/>
     *
     * @param modulo il modulo destinazione
     *
     * @return la lista delle relazioni che puntano al modulo
     */
    public static ArrayList<Relazione> getRelazioniVerso(Modulo modulo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Relazione> relazioni = null;
        ArrayList<Relazione> tavolaRel;
        Relazione rel;
        String nomeModuloDest;
        String nomeModulo;

        try { // prova ad eseguire il codice

            relazioni = new ArrayList<Relazione>();
            tavolaRel = getTavolaRelazioni();
            nomeModuloDest = modulo.getNomeChiave();

            /* spazzola la tavola relazioni e prende solo quelle
             * che puntano al modulo dato */
            for (int k = 0; k < tavolaRel.size(); k++) {
                rel = (Relazione)tavolaRel.get(k);
                nomeModulo = rel.getNomeModuloArrivo();
                if (nomeModulo.equals(nomeModuloDest)) {
                    relazioni.add(rel);
                }// fine del blocco if
            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return relazioni;
    }


    /**
     * metodo getter per ottenere il valore della variabile statica privata
     */
    public static Progetto getIstanza() {

        if (ISTANZA == null) {
            ISTANZA = new Progetto();
        }// fine del blocco if

        return ISTANZA;
    } /* fine del metodo getter */


    /**
     * recupera la tavola delle relazioni del progetto
     */
    public static ArrayList<Relazione> getTavolaRelazioni() {
        return getIstanza().tavolaRelazioni;
    } /* fine del metodo */


    /**
     * controlla l'esistenza di un modulo data la chiave
     *
     * @param unaChiave la chiave del modulo da cercare
     */
    public static boolean isEsisteModulo(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        Progetto unProgetto = null;
        LinkedHashMap unaCollezioneModuli = null;

        unProgetto = Progetto.getIstanza();
        unaCollezioneModuli = unProgetto.moduli;

        return unaCollezioneModuli.containsKey(unaChiave);
        //return this.unaCollezioneModuliProgetto.containsKey(unaChiave);
    } /* fine del metodo */


    /**
     * restituisce un modulo.
     *
     * @return modulo richiesto
     */
    private Modulo getModuloIstanza(String unaChiave) {
        /* valore di ritorno col casting */
        return (Modulo)this.moduli.get(unaChiave);
    } /* fine del metodo */


    /**
     * restituisce un modulo.
     *
     * @return modulo richiesto
     */
    public static Modulo getModulo(String unaChiave) {
        /* valore di ritorno */
        return getIstanza().getModuloIstanza(unaChiave);
    } /* fine del metodo */


    /**
     * Ritorna una lista di tutti i moduli del Progetto.<br>
     *
     * @return la lista di tutti i moduli nella collezione di Progetto.
     */
    public static ArrayList<Modulo> getListaModuli() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Modulo> listaModuli = null;
        LinkedHashMap unaCollezione;
        Iterator unGruppo;
        Modulo unModulo;

        try {    // prova ad eseguire il codice

            listaModuli = new ArrayList<Modulo>();
            unaCollezione = Progetto.getIstanza().moduli;

            /** traversa tutta la collezione */
            unGruppo = unaCollezione.values().iterator();
            while (unGruppo.hasNext()) {

                /* recupera il singolo modulo e lo aggiunge alla lista */
                unModulo = (Modulo)unGruppo.next();
                listaModuli.add(unModulo);

            } /* fine del blocco while */

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaModuli;
    } // fine del metodo


    /**
     * Recupera tutti i moduli dalla collezione in base<br>
     * a un ordine dell'albero.<br>
     * La lista può contenere piu' istanze dello stesso modulo<br>
     *
     * @param ordine il tipo di ordine
     *
     * @return una lista di Moduli
     *
     * @see AlberoModello
     */
    private ArrayList<Modulo> getModuliAlbero(int ordine) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Modulo> listaModuli = null;
        ArrayList listaOggetti = null;
        NodoModuloOggetto unWrapperModulo = null;
        String chiaveModulo = "";
        Modulo unModulo = null;

        try {    // prova ad eseguire il codice

            listaModuli = new ArrayList<Modulo>();

            /* recupera l'elenco dei moduli dall'albero moduli
             * in ordine di dipendenza */
            listaOggetti = getAlberoModuli().getModelloAlbero().getOggetti(ordine);

            /* spazzola la lista ordinata */
            for (int k = 0; k < listaOggetti.size(); k++) {
                /* recupera la chiave del modulo */
                unWrapperModulo = (NodoModuloOggetto)listaOggetti.get(k);
                chiaveModulo = unWrapperModulo.getNomeChiave();

                /* recupera il modulo dalla collezione */
                unModulo = getModulo(chiaveModulo);

                /* aggiunge il modulo alla lista */
                listaModuli.add(unModulo);

            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaModuli;
    } // fine del metodo


    /**
     * Ritorna tutti i moduli nel Progetto in un dato ordine dell'albero.<br>
     * La lista contiene una unica istanza di ogni Modulo.<br>
     *
     * @param ordine il tipo di ordine
     *
     * @return una lista di Moduli
     *
     * @see AlberoModello
     */
    public static ArrayList<Modulo> getModuli(int ordine) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Modulo> listaModuli = null;
        ArrayList<Modulo> listaCompleta = null;

        try {    // prova ad eseguire il codice
            /* recupera la lista completa dei moduli dall'albero */
            listaCompleta = getIstanza().getModuliAlbero(ordine);

            /* elimina i duplicati */
            listaModuli = Libreria.eliminaDuplicatiLista(listaCompleta);
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaModuli;
    } // fine del metodo


    /**
     * Ritorna tutti i moduli nel Progetto nell'ordine Preorder dell'albero.<br>
     * La lista contiene una unica istanza di ogni Modulo.<br>
     *
     * @return una lista di Moduli
     *
     * @see AlberoModello
     */
    public static ArrayList<Modulo> getModuliPreorder() {
        /* valore di ritorno */
        return getModuli(AlberoModello.PREORDER);
    } // fine del metodo


    /**
     * Ritorna tutti i moduli nel Progetto nell'ordine Postorder dell'albero.<br>
     * La lista contiene una unica istanza di ogni Modulo.<br>
     *
     * @return una lista di Moduli
     *
     * @see AlberoModello
     */
    public static ArrayList<Modulo> getModuliPostorder() {
        /* valore di ritorno */
        return getModuli(AlberoModello.POSTORDER);
    } // fine del metodo


    /**
     * Filtra i moduli in ingresso.
     * <p/>
     * Rimuove dalla lista i moduli fissi <br>
     *
     * @param moduliIn moduli in ingressso
     *
     * @return una lista di Moduli filtrata
     *
     * @see AlberoModello
     */
    private static ArrayList<Modulo> filtraModuliFissi(ArrayList<Modulo> moduliIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Modulo> moduliOut = null;

        try {    // prova ad eseguire il codice
            moduliOut = new ArrayList<Modulo>();

            for (Modulo modulo : moduliIn) {

                if (!modulo.isModuloFisso()) {
                    moduliOut.add(modulo);
                }// fine del blocco if
            } // fine del ciclo for-each
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return moduliOut;
    } // fine del metodo


    /**
     * Filtra i moduli in ingresso.
     * <p/>
     * Rimuove dalla lista i moduli tabella <br>
     *
     * @param moduliIn moduli in ingressso
     *
     * @return una lista di Moduli filtrata
     *
     * @see AlberoModello
     */
    private static ArrayList<Modulo> filtraModuliTabella(ArrayList<Modulo> moduliIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Modulo> moduliOut = null;

        try {    // prova ad eseguire il codice
            moduliOut = new ArrayList<Modulo>();

            for (Modulo modulo : moduliIn) {

                if (!modulo.isTabella()) {
                    moduliOut.add(modulo);
                }// fine del blocco if
            } // fine del ciclo for-each
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return moduliOut;
    } // fine del metodo


    /**
     * Ritorna il nome chiave del primo modulo del programma.
     * <p/>
     *
     * @return il nome chiave del primo modulo
     */
    public static String getNomePrimoModulo() {
        /* variabili e costanti locali di lavoro */
        String nomeModulo = "";

        try {    // prova ad eseguire il codice
            if (PRIMO_MODULO != null) {
                nomeModulo = PRIMO_MODULO.getNomeChiave();
            } // fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nomeModulo;
    }


    /**
     * Restituisce un azione.
     *
     * @param unaChiave nome chiave per recuperare l'azione dalla collezione
     *
     * @return azione richiesta
     */
    public static Azione getAzione(String unaChiave) {
        /* valore di ritorno col casting */
        return getIstanza().getAzioneIstanza(unaChiave);
    } /* fine del metodo */


    /**
     * Restituisce un azione.
     *
     * @param unaChiave nome chiave per recuperare l'azione dalla collezione
     *
     * @return azione richiesta
     */
    private Azione getAzioneIstanza(String unaChiave) {
        /* valore di ritorno col casting */
        return (Azione)this.azioni.get(unaChiave);
    } /* fine del metodo */


    /**
     * Restituisce il clone di un'azione.
     *
     * @param unaChiave nome chiave per recuperare l'azione dalla collezione
     *
     * @return il clone dell'azione
     */
    private Azione getAzioneClonataLocale(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        Azione unAzioneOriginale = null;
        Azione unAzioneClone = null;

        try { // prova ad eseguire il codice
            unAzioneOriginale = (Azione)this.azioni.get(unaChiave);
//            unAzioneOriginale = this.getAzione(unaChiave);

            if (unAzioneOriginale != null) {
                unAzioneClone = unAzioneOriginale.clonaAzione();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unAzioneClone;
    }


    /**
     * Restituisce il clone di un'azione.
     *
     * @param unaChiave nome chiave per recuperare l'azione dalla collezione
     *
     * @return il clone dell'azione
     */
    public static Azione getAzioneClonata(String unaChiave) {
        /* valore di ritorno */
        return getIstanza().getAzioneClonataLocale(unaChiave);
    } /* fine del metodo */


    /**
     * restituisce la collezione delle famiglie font del progetto
     */
    public ArrayList getFamiglieFonts() {
        /** invoca il metodo delegato della classe */
        return this.famiglieFont;
    } /* fine del metodo getter */


    /**
     * Ritorna l'albero dei moduli
     * <p/>
     *
     * @return l'albero
     */
    public Albero getAlberoModuli() {
        return alberoModuli;
    }


    private void setAlberoModuli(Albero alberoModuli) {
        this.alberoModuli = alberoModuli;
    }


    public static int getLivello() {
        return getIstanza().livello;
    }


    /**
     * Apre il dialogo delle Preferenze.
     * <p/>
     */
    public static void mostraPreferenze() {
        /* variabili e costanti locali di lavoro */
        Dialogo dialogo;

        try {    // prova ad eseguire il codice
            dialogo = new PrefDialogo();
            dialogo.avvia();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Apre il navigatore dei Contatori.
     * <p/>
     */
    public static void mostraContatori() {
        /* variabili e costanti locali di lavoro */
        Modulo mod;
        Navigatore nav;

        try {    // prova ad eseguire il codice
            mod = getModuloContatore();
            mod.avvia();
            nav = mod.getNavigatoreCorrente();
            nav.apriNavigatore();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Apre il navigatore dei Semafori.
     * <p/>
     */
    public static void mostraSemafori() {
        /* variabili e costanti locali di lavoro */
        Modulo mod;
        Navigatore nav;

        try {    // prova ad eseguire il codice
            mod = getModuloSemaforo();
            mod.avvia();
            nav = mod.getNavigatoreCorrente();
            nav.apriNavigatore();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Tenta di accendere un semaforo per l'utente corrente.
     * <p/>
     * Usa il tempo di attesa di default
     *
     * @param chiave del semaforo da accendere
     * @param ttl tempo di vita del semaforo in secondi
     * @param showMex true per mostrare un messaggio in caso di fallimento
     *
     * @return true se il semaforo è stato acceso
     */
    public static boolean setSemaforo(String chiave, int ttl, boolean showMex) {
        return getModuloSemaforo().setSemaforo(chiave, ttl, showMex);
    }


    /**
     * Tenta di accendere un semaforo per l'utente corrente.
     * <p/>
     *
     * @param chiave del semaforo da accendere
     * @param ttl tempo di vita del semaforo in secondi
     *
     * @return true se il semaforo è stato acceso
     */
    public static boolean setSemaforo(String chiave, int ttl) {
        return getModuloSemaforo().setSemaforo(chiave, ttl);
    }


    /**
     * Tenta di accendere un semaforo per l'utente corrente.
     * <p/>
     * Usa il tempo di attesa di default
     *
     * @param chiave del semaforo da accendere
     * @param showMex true per mostrare un messaggio in caso di fallimento
     *
     * @return true se il semaforo è stato acceso
     */
    public static boolean setSemaforo(String chiave, boolean showMex) {
        return getModuloSemaforo().setSemaforo(chiave, showMex);
    }


    /**
     * Tenta di accendere un semaforo per l'utente corrente.
     * <p/>
     * Il semaforo viene acceso con il ttl di default
     *
     * @param chiave del semaforo da accendere
     *
     * @return true se il semaforo è stato acceso
     */
    public static boolean setSemaforo(String chiave) {
        return getModuloSemaforo().setSemaforo(chiave);
    }


    /**
     * Spegne un semaforo.
     * <p/>
     *
     * @param chiave del semaforo da spegnere
     */
    public static void clearSemaforo(String chiave) {
        getModuloSemaforo().clearSemaforo(chiave);
    }


    /**
     * Determina se l'utente corrente e' un programmatore.
     * <p/>
     */
    public static boolean isProgrammatore() {
        /* variabili e costanti locali di lavoro */
        boolean prog = false;

        try { // prova ad eseguire il codice
            prog = Pref.Cost.prog.is();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return prog;
    }


    /**
     * Determina se l'utente corrente e' un amministratore.
     * <p/>
     */
    public static boolean isAmministratore() {
        /* variabili e costanti locali di lavoro */
        boolean admin = false;
        int numUtente;
        int numAdmin;

        try { // prova ad eseguire il codice
//            numUtente = Libreria.getInt(Pref.Gen.tipoUtente.getWrap().getValore());
//            numAdmin = Libreria.getInt(Pref.Utente.admin.getValore());
//            admin = (numUtente == numAdmin);
            admin = Pref.Cost.admin.is();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return admin;
    }


    /**
     * Ritorna la data corrente.
     * <p/>
     *
     * @return la data corrente
     */
    public static Date getDataCorrente() {
        return Lib.Data.getCorrente();
    }


    /**
     * Ritorna il timestamp corrente.
     * <p/>
     *
     * @return la data corrente
     */
    public static Timestamp getTimestampCorrente() {
        return Lib.Data.getTimestampCorrente();
    }


    /**
     * Procedura di emergenza in caso di impossibilita' di
     * accedere al database dei dati.
     * <p/>
     * Mostra un avviso <br>
     * Apre le preferenze <br>
     * Mostra solo il tema Database <br>
     * Attende la chiusura delle preferenze <br>
     * Esce da programma <br>
     */
    public static void emergenzaDatiMancanti() {

        try {    // prova ad eseguire il codice

            /* mostra un messaggio */
            new MessaggioAvviso("Il database dei dati non è disponibile.\n" +
                    "Verrà aperta la finestra preferenze del programma\n" +
                    "per controllare le impostazioni del database.");

            /* apre le preferenze */
            mostraPreferenze();

            /* esce dal programma */
            System.exit(0);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Apre il navigatore degli Utenti.
     * <p/>
     */
    public static void mostraUtenti() {
        /* variabili e costanti locali di lavoro */
        Modulo moduloGruppi;
        Navigatore nav;

        try {    // prova ad eseguire il codice
            moduloGruppi = Progetto.getModuloGruppi();
            moduloGruppi.avvia();
            nav = moduloGruppi.getNavigatoreCorrente();
            nav.apriNavigatore();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Richiede l'uscita dal progetto.<br>
     * <p/>
     *
     * @return true se uscito
     */
    public static boolean chiudeProgramma() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        Db database;
        Connessione conn;
        Db dbDefault;

        try {    // prova ad eseguire il codice

            /* chiude i moduli */
            riuscito = getIstanza().chiudeModuli();

            /* chiude la connessione di progetto */
            if (riuscito) {
                conn = Progetto.getConnessione();
                if (conn != null) {
                    if (conn.isOpen()) {
                        conn.close();
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* spegne il database di progetto */
            if (riuscito) {
                database = Progetto.getDb();
                if (database != null) {
                    database.shutdown();
                }// fine del blocco if
            }// fine del blocco if

            /* chiude il database di default */
            if (riuscito) {
                dbDefault = Progetto.getDbDefault();
                if (dbDefault != null) {
                    dbDefault.shutdown();
                }// fine del blocco if
            }// fine del blocco if

            /* chiude le preferenze */
            if (riuscito) {
                Pref.close();
            }// fine del blocco if

            /* se riuscito, chiude la JVM */
            if (riuscito) {
                System.exit(0);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Chiude tutti i moduli del programma.<br>
     * <p/>
     *
     * @return true se riuscito a chiudere correttamente
     */
    private boolean chiudeModuli() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito;
        ArrayList lista;
        Modulo modulo;

        riuscito = true;

        try {    // prova ad eseguire il codice

            /* recupera i moduli in Postorder (dal piu' 'piccolo' al piu' 'grande')
             * (forse questo ordine non e' indispensabile...) */
            lista = getModuliPostorder();

            /* prima fase - richiede ad ogni modulo il permesso di chiuderlo */
            for (int k = 0; k < lista.size(); k++) {
                modulo = (Modulo)lista.get(k);
                if (modulo != null) {
                    riuscito = modulo.richiedeChiusura();
                }// fine del blocco if

                if (riuscito == false) {
                    break;
                }// fine del blocco if
            } // fine del ciclo for

            /* seconda fase - chiude effettivamente ogni modulo */
            if (riuscito) {
                for (int k = 0; k < lista.size(); k++) {
                    modulo = (Modulo)lista.get(k);
                    if (modulo != null) {
                        modulo.chiude();
                    }// fine del blocco if
                } // fine del ciclo for

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    public static Db getDb() {
        return getIstanza().db;
    }


    public static void setDb(Db db) {
        getIstanza().db = db;
    }


    public static Db getDbDefault() {
        return getIstanza().dbDefault;
    }


    private void setDbDefault(Db dbDefault) {
        this.dbDefault = dbDefault;
    }


    public static Db getDbPreferenze() {
        return getIstanza().dbPreferenze;
    }


    private void setDbPreferenze(Db dbPreferenze) {
        this.dbPreferenze = dbPreferenze;
    }


    /**
     * Recupera la connessione di progetto
     * <p/>
     * E' la connessione unica al database di progetto
     * mantenuta dal progetto stesso.
     * Se non e' ancora stata creata la crea e la registra ora
     *
     * @return la connessione di progetto
     */
    public static Connessione getConnessione() {
        /* variabili e costanti locali di lavoro */
        Connessione conn = null;
        Db db;

        try { // prova ad eseguire il codice
            conn = getIstanza().connessione;
            if (conn == null) {
                db = Progetto.getDb();
                conn = db.creaConnessione();
                Progetto.setConnessione(conn);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return conn;
    }


    public static void setConnessione(Connessione connessione) {
        getIstanza().connessione = connessione;
    }


    private LinkedHashMap<String, Azione> getAzioni() {
        return azioni;
    }


    private void setAzioni(LinkedHashMap<String, Azione> azioni) {
        this.azioni = azioni;
    }


    public static LinkedHashMap<String, Azione> getAzioniIstanza() {
        return getIstanza().azioni;
    }


    public static boolean isPronto() {
        return pronto;
    }


    private static void setPronto(boolean pronto) {
        Progetto.pronto = pronto;
    }


    public String getNomeProgramma() {
        return nomeProgramma;
    }


    private void setNomeProgramma(String nomeProgramma) {
        this.nomeProgramma = nomeProgramma;
    }


    /**
     * Ritorna l'id dell'utente correntemente conesso.
     * <p/>
     *
     * @return l'id dell'utente
     */
    public static int getIdUtenteCorrente() {
        return Progetto.idUtente;
    }


    /**
     * Regola l'id dell'utente correntemente conesso.
     * <p/>
     *
     * @param idUtente l'id dell'utente
     */
    private static void setIdUtenteCorrente(int idUtente) {
        Progetto.idUtente = idUtente;
    }


    public static SimpleDateFormat getDateFormat() {
        return getIstanza().dateFormat;
    }


    private void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }


    public static SimpleDateFormat getShortDateFormat() {
        return getIstanza().shortDateFormat;
    }


    private void setShortDateFormat(SimpleDateFormat shortDateFormat) {
        this.shortDateFormat = shortDateFormat;
    }


    public static SimpleDateFormat getShortOraFormat() {
        return getIstanza().shortOraFormat;
    }


    private void setShortOraFormat(SimpleDateFormat shortOraFormat) {
        this.shortOraFormat = shortOraFormat;
    }


    public static SimpleDateFormat getOraFormat() {
        return getIstanza().oraFormat;
    }


    private void setOraFormat(SimpleDateFormat oraFormat) {
        this.oraFormat = oraFormat;
    }


    public static GregorianCalendar getCalendario() {
        return getIstanza().calendario;
    }


    private void setCalendario(GregorianCalendar calendario) {
        this.calendario = calendario;
    }


    public static Date getDataVuota() {
        return getIstanza().dataVuota;
    }


    private void setDataVuota(Date dataVuota) {
        this.dataVuota = dataVuota;
    }


    public static String getCatUpdate() {
        return catUpdate;
    }


    public static void setCatUpdate(String catUpdate) {
        Progetto.catUpdate = catUpdate;
    }


    private Finestra getFinestraAvvio() {
        return finestraAvvio;
    }


    private void setFinestraAvvio(Finestra finestraAvvio) {
        this.finestraAvvio = finestraAvvio;
    }


    /**
     * Aggiunge una HotKey alla collezione di progetto
     * <p/>
     *
     * @param chiave per la collezione
     * @param hotkey da aggiungere
     */
    public static void addHotKey(int chiave, HotKey hotkey) {
        Progetto.getHotKeys().put(new Integer(chiave), hotkey);
    }


    /**
     * Aggiunge una HotKey alla collezione di progetto
     * <p/>
     *
     * @param chiave per la collezione
     * @param keycode il codice del tasto
     * @param mod1 il codice del primo modificatore
     * @param mod2 il codice del secondo modificatore
     * @param mod3 il codice del terzo modificatore
     *
     * @see java.awt.event.KeyEvent
     *      usare le costanti che cominciano con VK_
     * @see java.awt.event.InputEvent
     *      per i modificatori usare le costanti con la parola DOWN, es. ALT_DOWN_MASK
     */
    public static void addHotKey(int chiave, int keycode, int mod1, int mod2, int mod3) {
        /* variabili e costanti locali di lavoro */
        HotKey hk;

        try { // prova ad eseguire il codice
            hk = new HotKey(keycode, mod1, mod2, mod3);
            Progetto.addHotKey(chiave, hk);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge una HotKey alla collezione di progetto
     * <p/>
     *
     * @param chiave per la collezione
     * @param keycode il codice del tasto
     * @param mod1 il codice del primo modificatore
     * @param mod2 il codice del secondo modificatore
     *
     * @see java.awt.event.KeyEvent
     *      usare le costanti che cominciano con VK_
     * @see java.awt.event.InputEvent
     *      per i modificatori usare le costanti con la parola DOWN, es. ALT_DOWN_MASK
     */
    public static void addHotKey(int chiave, int keycode, int mod1, int mod2) {
        Progetto.addHotKey(chiave, keycode, mod1, mod2, 0);
    }


    /**
     * Aggiunge una HotKey alla collezione di progetto
     * <p/>
     *
     * @param chiave per la collezione
     * @param keycode il codice del tasto
     * @param mod1 il codice del modificatore
     *
     * @see java.awt.event.KeyEvent
     *      usare le costanti che cominciano con VK_
     * @see java.awt.event.InputEvent
     *      per i modificatori usare le costanti con la parola DOWN, es. ALT_DOWN_MASK
     */
    public static void addHotKey(int chiave, int keycode, int mod1) {
        Progetto.addHotKey(chiave, keycode, mod1, 0);
    }


    /**
     * Aggiunge una HotKey alla collezione di progetto
     * <p/>
     *
     * @param chiave per la collezione
     * @param keycode il codice del tasto
     *
     * @see java.awt.event.KeyEvent
     *      usare le costanti che cominciano con VK_
     */
    public static void addHotKey(int chiave, int keycode) {
        Progetto.addHotKey(chiave, keycode, 0);
    }


    /**
     * Restituisce la collezione delle HotKeys di progetto
     * <p/>
     *
     * @return la collezione delle HotKeys
     */
    public static HashMap<Integer, HotKey> getHotKeys() {
        return Progetto.getIstanza().hotkeys;
    }


    private void setHotKeys(HashMap<Integer, HotKey> hotkeys) {
        this.hotkeys = hotkeys;
    }


    public static Gestore getGestore() {
        return Progetto.getIstanza().gestore;
    }


    private void setGestore(Gestore gestore) {
        this.gestore = gestore;
    }


    /**
     * Effettua un backup di progetto.
     * <p/>
     */
    public static void backup() {
        new Backup().backup();
    }


    /**
     * Ripristina un backup di progetto.
     * <p/>
     *
     * @return eseguito
     */
    public static boolean restore() {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;

        try { // prova ad eseguire il codice
            eseguito = new Backup().restore();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;
    }


    /**
     * Ritorna la licenza di J2PrinterWorks.
     * <p/>
     *
     * @return codice licenza
     */
    public static String getPrintLicense() {
        /* valore di ritorno */
        return "4B3-290-89C-8BC";
    }


    /**
     * Restituisce un'azione che implementa l'interfaccia FocusListener.
     * <p/>
     * Recupera l'azione generica <br>
     * Esegue il casting specifico <br>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    public static FocusListener getAzFocus(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        Azione azione;
        FocusListener listener = null;

        try { // prova ad eseguire il codice

            azione = getAzioneClonata(unaChiave);
            listener = (FocusListener)azione;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listener;
    }


    /**
     * Restituisce un'azione che implementa l'interfaccia ItemListener.
     * <p/>
     * Recupera l'azione generica <br>
     * Esegue il casting specifico <br>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    public static ItemListener getAzItem(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        Azione azione;
        ItemListener listener = null;

        try { // prova ad eseguire il codice

            azione = getAzioneClonata(unaChiave);
            listener = (ItemListener)azione;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listener;
    }


    /**
     * Restituisce un'azione che implementa l'interfaccia KeyListener.
     * <p/>
     * Recupera l'azione generica <br>
     * Esegue il casting specifico <br>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    public static KeyListener getAzKey(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        Azione azione;
        KeyListener listener = null;

        try { // prova ad eseguire il codice

            azione = getAzioneClonata(unaChiave);
            listener = (KeyListener)azione;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listener;
    }


    /**
     * Restituisce un'azione che implementa l'interfaccia PopupMenuListener.
     * <p/>
     * Recupera l'azione generica <br>
     * Esegue il casting specifico <br>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    public static PopupMenuListener getAzPopupMenu(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        Azione azione;
        PopupMenuListener listener = null;

        try { // prova ad eseguire il codice

            azione = getAzioneClonata(unaChiave);
            listener = (PopupMenuListener)azione;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listener;
    }


    /**
     * Restituisce un'azione che implementa l'interfaccia ListSelectionListener.
     * <p/>
     * Recupera l'azione generica <br>
     * Esegue il casting specifico <br>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    public static ListSelectionListener getAzListSelection(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        Azione azione;
        ListSelectionListener listener = null;

        try { // prova ad eseguire il codice

            azione = getAzioneClonata(unaChiave);
            listener = (ListSelectionListener)azione;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listener;
    }


    /**
     * Restituisce un'azione che implementa l'interfaccia MouseListener.
     * <p/>
     * Recupera l'azione generica <br>
     * Esegue il casting specifico <br>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    public static MouseListener getAzMouse(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        Azione azione;
        MouseListener listener = null;

        try { // prova ad eseguire il codice

            azione = getAzioneClonata(unaChiave);
            listener = (MouseListener)azione;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listener;
    }


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Moduli che si trovano in base <br>
     */
    public static enum ModFissi {

        utenti(UtentiModulo.class, false, true, false), // non utilizzato di default
        semafori(SemaforoModulo.class, false, true, false), // non utilizzato di default
        selezione(SelezioneModulo.class, false, true, false), // non utilizzato di default
        contatori(ContatoreModulo.class, false, false, true),  // non utilizzato di default
        numDoc(NumeratoreDocModulo.class, false, true, false),  // non utilizzato di default
        ;

        /**
         * classe del modulo
         */
        private Class classe;

        /**
         * flag - per creare o meno il modulo
         * Valore regolato in fase statica alla creazione della Enum.
         * Modificabile nel Progetto specifico, a meno che non sia
         * bloccato dal flag Modificabile
         */
        private boolean uso;

        /**
         * flag per bloccare la modifica del flag Uso
         * (rimane sempre il valore inserito in fase statica)
         */
        private boolean modificabile;

        /**
         * flag - se la creazione avviene dopo la preparazione dei moduli
         * (creazione dei campi nel Modello)
         * (normalmente avviene prima)
         */
        private boolean post;


        /**
         * Costruttore completo con parametri.
         *
         * @param classe del modulo
         * @param uso di default
         * @param modificabile nel progetto specifico
         * @param post di progetto
         */
        ModFissi(Class classe, boolean uso, boolean modificabile, boolean post) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setClasse(classe);
                this.setUsoInterno(uso);
                this.setModificabile(modificabile);
                this.setPost(post);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Crea e aggiunge i moduli fissi al progetto.
         * Questi moduli si aggiungono sempre al nodo Root.
         */
        public static void creaAnte() {
            /* variabili e costanti locali di lavoro */

            try { // prova ad eseguire il codice

                long prima = System.currentTimeMillis();

                /* traverso tutta la collezione */
                for (ModFissi ele : values()) {
                    if (ele.isUso()) {
                        if (!ele.isPost()) {
                            ele.creaAnteSingolo();
                        }// fine del blocco if
                    }// fine del blocco if
                } // fine del ciclo for-each

                long dopo = System.currentTimeMillis();
                System.out.println((dopo - prima) / 1000);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Creazione del modulo di questo elemento.
         */
        private void creaAnteSingolo() {
            /* variabili e costanti locali di lavoro */
            AlberoNodo nodoRoot;
            Modulo mod;
            Progetto progetto;

            try { // prova ad eseguire il codice

                progetto = Progetto.getIstanza();
                mod = this.crea();
                nodoRoot = progetto.getAlberoModuli().getNodoRoot();
                progetto.creaModulo(mod, nodoRoot);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Crea ed aggiunge al progetto eventuali altri moduli fissi
         * dopo che i vari moduli sono stati preparati.
         * (i campi sono stati creati)
         */
        public static void creaPost() {
            try { // prova ad eseguire il codice

                /* Se il progetto necessita di contatori accende il
                 * flag di uso del relativo modulo*/
                Progetto.ModFissi.contatori.setUsoInterno(Progetto.getIstanza().isUsaContatori());

                /* traverso tutta la collezione */
                for (ModFissi ele : values()) {
                    if (ele.isUso()) {
                        if (ele.isPost()) {
                            ele.creaPostSingolo();
                        }// fine del blocco if
                    }// fine del blocco if
                } // fine del ciclo for-each
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Creazione del modulo di questo elemento.
         */
        private void creaPostSingolo() {
            /* variabili e costanti locali di lavoro */
            Modulo mod;
            ContatoreModulo contMod;

            try { // prova ad eseguire il codice

                mod = this.crea();

                if (mod instanceof ContatoreModulo) {
                    contMod = (ContatoreModulo)mod;
                    contMod.addAlbero();
                }// fine del blocco if

                /**
                 * Aggiunge alla collezione e Prepara il modulo,
                 * che essendo stato creato
                 * dopo la preparazione di Progetto.lanciaIstanza()
                 * non è stato ancora preparato.
                 */
                Progetto.getIstanza().addModuloCollezione(mod);
                mod.prepara();


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Crea un modulo per questo elemento.
         * <p/>
         *
         * @return il modulo creato
         */
        private Modulo crea() {
            /* variabili e costanti locali di lavoro */
            Modulo mod = null;
            Class classe;
            String path;

            try { // prova ad eseguire il codice

                classe = this.getClasse();
                path = classe.getCanonicalName();
                mod = Lib.Clas.modulo(path, null);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return mod;
        }


        private Class getClasse() {
            return classe;
        }


        private void setClasse(Class classe) {
            this.classe = classe;
        }


        public boolean isUso() {
            return uso;
        }


        private void setUsoInterno(boolean uso) {
            this.uso = uso;
        }


        public void setUso(boolean uso) {
            if (isModificabile()) {
                this.uso = uso;
            }// fine del blocco if
        }


        private boolean isModificabile() {
            return modificabile;
        }


        public void setModificabile(boolean modificabile) {
            this.modificabile = modificabile;
        }


        private boolean isPost() {
            return post;
        }


        private void setPost(boolean post) {
            this.post = post;
        }
    }// fine della classe

}// fine della classe

