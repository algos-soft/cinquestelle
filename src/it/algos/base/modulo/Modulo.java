/**
 * Title:        Modulo.java
 * Package:      it.algos.base.modulo
 * Description:  Abstract Data Types per la struttura dei dati di un modulo
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 15 dicembre 2002 alle 9.32
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.modulo;

import it.algos.base.albero.AlberoModello;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.azione.modulo.AzAvviaModulo;
import it.algos.base.calcresolver.AlberoCalcolo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.Db;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.util.MetodiQuery;
import it.algos.base.evento.Eventi;
import it.algos.base.evento.GestioneEventi;
import it.algos.base.gestore.Gestore;
import it.algos.base.gestore.gestorestato.GestoreStatoScheda;
import it.algos.base.help.Help;
import it.algos.base.interfaccia.ContenitoreCampi;
import it.algos.base.lista.Lista;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.logica.LogicaModulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.scheda.Scheda;
import it.algos.base.stampa.StampaLista;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.base.wrapper.WrapFiltri;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

//-----------------------------------------------------------------------------
// Classe principale
//-----------------------------------------------------------------------------


/**
 * Questa interfaccia e' responsabile di: <br>
 * A - Definire i metodi usati dalle classi Modulo <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  15 dicembre 2002 ore 9.32
 */
public interface Modulo extends GestioneEventi, ContenitoreCampi {

//    extends CostanteGUI, CostanteModello

    /**
     * nome della vista normale
     */
    public static final String VISTA_BASE_DEFAULT = "vistadefault";

    public static final String VISTA_SIGLA = "vistasigla";

    public static final String VISTA_DESCRIZIONE = "vistadescrizione";

    public static final String NOME_SCHEDA_DEFAULT = Scheda.NOME_CHIAVE_DEFAULT;

    /**
     * nome del set dei campi di default per la scheda
     */
    public static final String SET_BASE_DEFAULT = "setdefault";

    /**
     * nome del set dei campi di default per la pagina Programmatore della scheda
     */
    public static final String SET_PROGRAMMATORE = "setprogrammatore";

    /**
     * codifica per modulo che parte dal metodo main (primo modulo del programma)
     */
    public static final int MAIN = 0;

    /**
     * codifica per modulo normale, lanciato da un altro modulo
     */
    public static final int MODULO = 1;

    /**
     * codifica per modulo che parte da un campo
     */
    public static final int CAMPO = 2;


    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi protetti chiamati dalla sottoclasse subito dopo il costruttore
    //-------------------------------------------------------------------------
    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe Progetto <br>
     * Viene eseguito una sola volta <br>
     */
    public abstract boolean inizializza();


    /**
     * Preparazione del Modulo.
     * <p/>
     * Prepara gli oggetti immediatamente necessari
     * al funzionamento del Modulo.<br>
     * Invocato dal Progetto una sola volta.
     * Invocato dopo il ciclo di creazione e prima
     * del ciclo relaziona.<br>
     *
     * @return true se la preparazione e' riuscita
     */
    public abstract boolean prepara();


    public abstract boolean relaziona();


    /**
     * Regolazioni di avvio, ogni volta che questo oggetto deve 'ripartire', per
     * essere sicuri che sia 'pulito'
     * Metodo chiamato da altre classi
     * Viene eseguito tutte le volte che necessita
     */
    public abstract void avvia();


    /**
     * Ritorna un filtro Popup da usare nelle liste di moduli linkati.
     * <p/>
     *
     * @param nomeCampo il nome del campo per il filtro
     *
     * @return il filtro popup
     */
    public abstract WrapFiltri getFiltroPop(String nomeCampo);


    /**
     * Ritorna un filtro Popup da usare nelle liste di moduli linkati.
     * <p/>
     *
     * @return il filtro popup
     */
    public abstract WrapFiltri getFiltroPop();


    /**
     * Aggiunge un Navigatore con nome chiave propria alla collezione del Modulo.
     * <p/>
     * Se il nome chiave e' mancante solleva una eccezione.
     *
     * @param nav il navigatore da aggiungere, contenete già il nome chiave
     */
    public void addNavigatore(Navigatore nav);


    /**
     * Aggiunge un Navigatore con un dato nome chiave alla collezione del Modulo.
     * <p/>
     *
     * @param nav    il navigatore da aggiungere
     * @param chiave per la collezione
     */
    public void addNavigatore(Navigatore nav, String chiave);


    /**
     * Aggiunge un Navigatore con nome chiave alla collezione del Modulo.
     * <p/>
     * Imposta il navigatore aggiunto come navigatore corrente.
     * Se il nome chiave e' mancante solleva una eccezione.
     *
     * @param unNavigatore il navigatore da aggiungere
     */
    public abstract void addNavigatoreCorrente(Navigatore unNavigatore);


    /**
     * Assegna il navigatore di default al modulo.
     * <p/>
     * Imposta il navigatore aggiunto come navigatore corrente.
     * Se il nome chiave e' mancante solleva una eccezione.
     *
     * @param nav il navigatore da aggiungere
     */
    public abstract void setNavigatoreDefault(Navigatore nav);


    /**
     * metodo principale di passaggio di informazioni tra i moduli
     */
    public abstract boolean messaggioModulo(InfoModulo unaQueryModulo);


    /**
     * modulo che ha creato questo modulo (alla partenza del programma)
     */
    public abstract void setModuloParente(Modulo unModuloParente);


    /**
     * nome chiave del modulo (viene usato per recuperare il modulo dalla
     * collezione e come default per il nome della tavola, del menu)
     */
    public abstract void setNomeModulo(String unNomeModulo);


    /**
     * flag per usare il gestore come tabella (finestre piu' piccole)
     */
    public abstract void setTabella(boolean isTabella);

//    /**
//     * gestore della logica e dei riferimenti (puntatori) di questo modulo
//     */
//    public abstract void setGestoreOld(GestoreOld unGestore);


    /**
     * modulo della scheda che ha creato questo modulo
     */
    public abstract void setModuloChiamante(Modulo unModuloChiamante);


    public abstract boolean isPrimoModulo();


    /**
     * modello normale dei dati (Abstract Data Types)
     */
    public abstract void setModello(Modello unModelloDati);


    /**
     * regola la lista corrente
     */
    public abstract void setNomeListaCorrente(String nomeListaCorrente);

//    /**
//     * aggiunge la scheda alla collezione
//     */
//    public abstract void addScheda(SchedaOld unaScheda);

//    /**
//     * aggiunge la scheda alla collezione  e regola il puntatore
//     */
//    public abstract void putScheda(SchedaOld unaScheda);


    /**
     * regola la scheda corrente
     */
    public abstract void setNomeSchedaCorrente(String nomeSchedaCorrente);

//    /**
//     * gestore stato lista
//     */
//    public abstract void setGestoreStatoLista(GestoreStatoListaOld unGestoreStatoListaOld);


    /**
     * gestore stato lista
     */
    public abstract void setGestoreStatoScheda(GestoreStatoScheda unGestoreStatoScheda);


    /**
     * gestione dell'aiuto con mappatura dei files HTML
     */
    public abstract void setHelp(Help unHelp);


    /**
     * utilizzo di un solo pannello nel navigatore
     */
    public abstract void setUsaPannelloUnico(boolean unico);


    public abstract void setNodoCorrente(AlberoNodo nodoCorrente);

//    public abstract void setCreatore(ModuloCreatore unCreatore);


    public abstract Gestore getGestore();


    public abstract void setGestore(Gestore gestore);


    /**
     * progetto in esecuzione
     * (il progetto e' unico, il riferimento serve solo a recuperare l'istanza)
     */
    public abstract Progetto getProgetto();


    /**
     * Ritorna un navigatore dalla collezione, data la chiave.
     * <p/>
     *
     * @return il Navigatore richiesto
     */
    public abstract Navigatore getNavigatore(String unaChiave);


    public abstract Navigatore getNavigatoreCorrente();


    /**
     * Ritorna la lista gestita dal navigatore corrente.<br>
     *
     * @return la lista gestita dal Navigatore
     */
    public abstract Lista getLista();


    /**
     * Ritorna il Navigatore di default del Modulo.
     * <p/>
     *
     * @return il navivagore di default
     */
    public abstract Navigatore getNavigatoreDefault();


    /**
     * modulo che ha creato questo modulo (alla partenza del programma)
     */
    public abstract Modulo getModuloParente();


    /**
     * nome chiave del modulo (viene usato per recuperare il modulo dalla
     * collezione e come default per il nome della tavola, del menu)
     */
    public abstract String getNomeChiave();


    /**
     * nome visibile pubblico del modulo.
     */
    public abstract String getNomeModulo();


    /**
     * Ritorna il nome della tavola associata a questo modulo
     * <p/>
     *
     * @return il nome della tavola
     */
    public abstract String getTavola();


    public abstract String getTitoloMenu();


    public abstract String getTitoloFinestra();


    public abstract String getNomeProgramma();


    public abstract ModoAvvio getModoAvvio();


    /**
     * flag per usare il gestore come tabella (finestre piu' piccole)
     */
    public abstract boolean isTabella();


    /**
     * Controlla se l'uso delle transazioni è attivato per il Modulo
     * <p/>
     *
     * @return true se l'uso delle transazioni è attivato
     */
    public abstract boolean isUsaTransazioni();


    /**
     * Ritorna il nome pubblico del modulo, utilizzato nella lista dai
     * campi combo di altri moduli linkati.
     * <p/>
     *
     * @return il nome pubblico
     */
    public abstract String getNomePubblico();


    /**
     * modulo della scheda che ha creato questo modulo
     */
    public abstract Modulo getModuloChiamante();


    /**
     * modello dei dati (Abstract Data Types)
     */
    public abstract Modello getModello();


    /**
     * restituisce il puntatore lista corrente
     */
    public abstract String getNomeLista();

//    /**
//     * restituisce la scheda specifica
//     */
//    public abstract SchedaOld getScheda(String unaScheda);


    /**
     * Ritorna una vista del Modello.
     * <p/>
     *
     * @param nome il nome della Vista
     *
     * @return la Vista richiesta
     */
    public abstract Vista getVista(String nome);


    /**
     * Ritorna la vista di default del Modulo
     */
    public abstract Vista getVistaDefault();


    /**
     * Ritorna il nome della la vista di default del Modulo <p>
     *
     * @return il nome della la vista di default del Modulo
     */
    public abstract String getNomeVistaDefault();


    /**
     * restituisce il puntatore scheda corrente
     */
    public abstract String getNomeScheda();

//    /**
//     * gestore dello stato della lista
//     */
//    public abstract GestoreStatoListaOld getGestoreStatoLista();


    /**
     * gestore dello stato della scheda
     */
    public abstract GestoreStatoScheda getGestoreStatoScheda();


    /**
     * azione che ha lanciato questo modulo
     * <p/>
     *
     * @return l'azione di avvio del modulo
     */
    public abstract AzAvviaModulo getAzioneModulo();


    /**
     * Azione about.
     * <p/>
     * Apre una finestra di informazioni <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    public abstract void apreAbout();


    /**
     * gestione dell'aiuto con mappatura dei files HTML
     */
    public abstract Help getHelp();


    /**
     * Stampa un elenco di campi in forma di lista.
     * <p/>
     *
     * @param vista  contenente i campi da stampare
     * @param filtro per selezionare i record da stampare
     * @param ordine di stampa dei record
     *
     * @return l'oggetto StampaListaCampi creato, pronto da eseguire con run()
     */
    public abstract StampaLista getStampaLista(Vista vista, Filtro filtro, Ordine ordine);


    /**
     * Ritorna la lista dei moduli dai quali questo modulo dipende.
     * <p/>
     *
     * @return la lista dei moduli dai quali questo modulo dipende
     */
    public ArrayList getModuli();

//    /**
//     * restituisce il creatore del modulo
//     */
//    public abstract ModuloCreatore getCreatore();


    public abstract AlberoNodo getNodoCorrente();


    public abstract boolean isRelazionato();


    public abstract void postAscendente();


    public abstract void postDiscendente();


    /**
     * Ritorna un campo dalla collezione campi del Modello.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo
     *
     * @return il campo
     */
    public abstract Campo getCampo(String nomeCampo);


    /**
     * Ritorna un campo dalla collezione campi del Modello.
     * <p/>
     *
     * @param campo l'elemento della Enum Campi dall'interfaccia
     *
     * @return il campo
     */
    public abstract Campo getCampo(Campi campo);


    /**
     * Ritorna il clone di un campo dalla collezione campi del Modello.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo
     *
     * @return il campo clonato
     */
    public abstract Campo getCloneCampo(String nomeCampo);


    /**
     * Ritorna il campo chiave del modulo.
     * <p/>
     *
     * @return il campo chiave
     */
    public abstract Campo getCampoChiave();


    /**
     * Ritorna il campo ordine del modulo.
     * <p/>
     *
     * @return il campo ordine
     */
    public abstract Campo getCampoOrdine();


    /**
     * Ritorna il campo visibile del modulo.
     * <p/>
     *
     * @return il campo visibile
     */
    public abstract Campo getCampoVisibile();


    /**
     * Restituisce il campo Note del Modello.
     *
     * @return il campo Note
     */
    public abstract Campo getCampoNote();


    /**
     * Restituisce il campo "preferito" del Modello.
     *
     * @return il campo "preferito"
     */
    public abstract Campo getCampoPreferito();


    /**
     * Ritorna tutti i campi del modulo.
     * <p/>
     *
     * @return il la lista dei campi fisici del modulo
     */
    public abstract ArrayList getCampi();


    /**
     * Ritorna tutti i campi fisici del modulo.
     * <p/>
     *
     * @return il la lista dei campi fisici del modulo
     */
    public abstract ArrayList<Campo> getCampiFisici();


    /**
     * Ritorna i campi fisici non fissi (non algos) del modulo.
     * <p/>
     *
     * @return il la lista dei campi fisici non fissi
     */
    public abstract ArrayList<Campo> getCampiFisiciNonFissi();


    /**
     * Restituisce i soli campi fissi
     * <p/>
     *
     * @return la lista dei campi fissi
     */
    public abstract ArrayList<Campo> getCampiFissi();


    /**
     * Ritorna l'elenco dei campi definiti come ricercabili.
     * <p/>
     *
     * @return l'elenco dei campi ricercabili
     */
    public abstract ArrayList<Campo> getCampiRicercabili();


    /**
     * Recupera l'albero di campi per questo Modulo.
     * <p/>
     * L'albero e' cosi' strutturato:
     * al primo livello tutti i campi fisici locali
     * sotto a ogni campo linkato da un altro modulo,
     * gli alberi dei moduli linkati (ricorsivo).
     * Ogni nodo
     *
     * @return il modello dati dell'albero
     */
    public abstract AlberoModello getAlberoCampi();


    /**
     * Controlla l'esistenza di un Campo.
     * <br>
     *
     * @param unaChiave nome chiave per recuperare il campo dalla collezione
     *
     * @return vero se il campo esiste nella collezione campi del Modello
     */
    public abstract boolean isEsisteCampo(String unaChiave);


    /**
     * Controlla l'esistenza di una Vista.
     * <br>
     *
     * @param unaChiave nome chiave per recuperare la vista dalla collezione
     *
     * @return vero se la Vista esiste nella collezione viste del Modello
     */
    public abstract boolean isEsisteVista(String unaChiave);


    /**
     * Ritorna un filtro per identificare i dati dei quali eseguire
     * il backup per questo modulo.
     * <p/>
     * Normalmente il filtro è nullo, copia tutti i dati
     *
     * @return il filtro da applicare in fase di backup
     */
    public abstract Filtro getFiltroBackup();


    /**
     * Ritorna un filtro per identificare i dati da eliminare prima di
     * effettuare il restore per questo modulo.
     * <p/>
     * Normalmente il filtro è nullo, elimina su tutti i dati
     *
     * @return il filtro di eliminazione da applicare prima del restore
     */
    public abstract Filtro getFiltroRestore();


    /**
     * Presenta un record esistente in una scheda modale.
     * <p/>
     * Legge i dati dal database
     * Carica i dati nella scheda
     * Presenta la scheda in modifica all'utente
     *
     * @param codice     del record
     * @param nomeScheda nome chiave della scheda nella collezione schede del modulo
     * @param conn       connessione da utilizzare per il recupero dei dati
     *
     * @return true se confermato, con o senza modifiche
     */
    public abstract boolean presentaRecord(int codice, String nomeScheda, Connessione conn);


    /**
     * Presenta un record esistente in una scheda modale.
     * Usa la scheda pop del Modulo.
     * <p/>
     *
     * @param codice del record
     * @param conn   connessione da utilizzare per il recupero dei dati
     *
     * @return true se confermato, con o senza modifiche
     */
    public abstract boolean presentaRecord(int codice, Connessione conn);


    /**
     * Presenta un record esistente in una scheda modale.
     * Usa la connessione di default del Modulo.
     * <p/>
     *
     * @param codice     del record
     * @param nomeScheda nome chiave della scheda nella collezione schede del modulo
     *
     * @return true se confermato, con o senza modifiche
     */
    public abstract boolean presentaRecord(int codice, String nomeScheda);


    /**
     * Presenta un record esistente in una scheda modale.
     * <p/>
     * Usa la scheda di default per presentazione esterna (schedaPop) del modulo
     * Usa la connessione del modulo
     *
     * @param codice del record
     *
     * @return true se confermato, con o senza modifiche
     */
    public abstract boolean presentaRecord(int codice);


    /**
     * Presenta un record esistente in una scheda modale.
     * <p/>
     * Consente l'editing all'utente e permette di confermare o abbandonare.
     *
     * @param codice del record
     * @param scheda da utilizzare
     * @param conn   connessione da utilizzare per il recupero dei dati
     *
     * @return vero se confermato, con o senza modifiche
     */
    public abstract boolean presentaRecord(int codice, Scheda scheda, Connessione conn);


    /**
     * Presenta un record in una scheda modale.
     * <p/>
     * Consente l'editing all'utente e permette di confermare o abbandonare.
     *
     * @param codice      del record
     * @param scheda      da utilizzare
     * @param nuovoRecord true se il record presentato è un nuovo record
     * @param conn        connessione da utilizzare per il recupero dei dati
     *
     * @return vero se confermato, con o senza modifiche
     */
    public abstract boolean presentaRecord(int codice,
                                           Scheda scheda,
                                           boolean nuovoRecord,
                                           Connessione conn);


    /**
     * Restituisce un estratto.
     * </p>
     * Restituisce un estratto conforme al tipo ed al record richiesto <br>
     *
     * @param tipo   codifica dell'estratto desiderato
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito dal modello
     */
    public abstract EstrattoBase getEstratto(Estratti tipo, Object chiave);


    /**
     * Restituisce un estratto sotto forma di stringa.
     * </p>
     *
     * @param estratto stringa dell'estratto desiderato
     * @param chiave   con cui effettuare la ricerca
     * @param conn     connessione da utilizzare
     *
     * @return la stringa costruita dal modello
     */
    public abstract String getEstStr(Estratti estratto, int chiave, Connessione conn);


    /**
     * Restituisce un estratto.
     * </p>
     * Restituisce un estratto conforme al tipo ed al record richiesto <br>
     * Utilizza la connessione fornita per effettuare le query <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param estratto tipo di estratto desiderato
     * @param chiave   con cui effettuare la ricerca
     * @param conn     la connessione da utilizzare per le query
     *
     * @return l'estratto costruito
     */
    public abstract EstrattoBase getEstratto(Estratti estratto, Object chiave, Connessione conn);


    /**
     * Restituisce il codice dell'eventuale Record Preferito.
     * <p/>
     * Utilizza la connessione del Modulo.
     *
     * @return il codice del Record Preferito, 0 se non trovato
     */
    public abstract int getRecordPreferito();


    /**
     * Recupera una icona specifica del modulo.
     * <p/>
     *
     * @param chiave dell'icona da recuperare
     *
     * @return l'icona richiesta, null se non trovata
     */
    public abstract Icon getIcona(String chiave);


    public abstract ModuloBase getModuloBase();


    public abstract Db getDb();


    public abstract void setDb(Db db);


    /**
     * Recupera la connessione del Modulo
     * <p/>
     *
     * @return la connessione del Modulo
     */
    public abstract Connessione getConnessione();


    /**
     * Assegna una connessione al Modulo
     * <p/>
     *
     * @param connessione la connessione da assegnare
     */
    public abstract void setConnessione(Connessione connessione);


    /**
     * Ritorna l'oggetto contenente i metodi Query del modulo.
     * <p/>
     *
     * @return l'oggetto contenente i metodi Query
     */
    public abstract MetodiQuery query();


    /**
     * Ritorna l'oggetto contenente i metodi Messaggio del modulo.
     * <p/>
     *
     * @return l'oggetto contenente i metodi Messaggio
     */
    public abstract MetodiMessaggioModulo messaggio();


    /**
     * Chiede al modulo il permesso di chiuderlo.
     * <p/>
     * Presenta eventuali messaggi all'utente per risolvere situazioni aperte <br>
     *
     * @return true se riuscito.
     */
    public abstract boolean richiedeChiusura();


    /**
     * Chiude effettivamente il modulo.
     * <p/>
     */
    public abstract void chiude();


    /**
     * Chiusura della scheda tramite il bottone Annulla.
     * <p/>
     * Metodo invocato dal gestore eventi <br>
     */
    public abstract void chiudeScheda();


    /**
     * Rilascia un numero di ID per il prossimo record di questo modulo.
     * <p/>
     * Aggiorna il contatore prima del rilascio.
     *
     * @return il prossimo ID per il nuovo record del Modulo, 0 se non riuscito
     */
    public abstract int releaseID();


    /**
     * Recupera la scheda di default.
     * <p/>
     *
     * @return la scheda richiesta
     */
    public abstract Scheda getSchedaDefault();


    /**
     * recupera una scheda dalla collezione
     * <p/>
     *
     * @param chiave della scheda
     *
     * @return la scheda richiesta
     */
    public abstract Scheda getScheda(String chiave);


    /**
     * Recupera la collezione di navigatori del modulo
     * <p/>
     *
     * @return la collezione di navigatori
     */
    public abstract LinkedHashMap<String, Navigatore> getNavigatori();


    /**
     * Controlla se questo modulo fa parte del sistema di moduli
     * fissi del programma.
     * <p/>
     *
     * @return true se e' un modulo fisso
     */
    public abstract boolean isModuloFisso();


    /**
     * Regola il flag  che indica se questo modulo fa parte
     * del sistema di moduli fissi del programma.
     * <p/>
     *
     * @param moduloFisso true se e' un modulo fisso
     */
    public abstract void setModuloFisso(boolean moduloFisso);


    /**
     * Ritorna l'oggetto che gestisce la logica specifica del modulo.
     * <p/>
     *
     * @return la logica specifica del modulo
     */
    public abstract LogicaModulo getLogicaSpecifica();

    public boolean isInizializzato();

    /**
     * Ritorna true se il modulo è stato avviato una prima volta
     * <p/>
     *
     * @return true se il modulo è stato avviato una prima volta
     */
    public abstract boolean isAvviato();


    /**
     * Ritorna l'elenco dei moduli dipendenti da questo modulo
     * <p/>
     *
     * @return l'elenco dei moduli dipendenti
     */
    public abstract ArrayList<Modulo> getModuliDipendenti();


    /**
     * Metodo invocato quando viene premuta una HotKey.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param chiave della hotkey premuta
     */
    public abstract void hotkey(int chiave);


    /**
     * Ritorna la scheda da utilizzare per l'editing dall'esterno
     * <p/>
     *
     * @return la scheda per l'editing esterno
     */
    public abstract Scheda getSchedaPop();


    /**
     * Ritorna l'albero dei campi calcolati
     * <p/>
     *
     * @return l'albero dei campi calcolati
     */
    public abstract AlberoCalcolo getAlberoCalcolo();


    /**
     * Ritorna il percorso completo del file.
     * <p/>
     * Necessario perché ho oscurato il metodo toString <br>
     */
    public abstract String getPath();


    /**
     * Presenta un dialogo per selezionare un record.
     * <p/>
     *
     * @param nomeCampo   da esaminare
     * @param filtro      dei records da selezionare
     * @param messaggio   del dialogo
     * @param valIniziale del selettore
     * @param usaNuovo    permette di creare nuovi record (default=true)
     * @param est         Eventuale estratto posizionato sotto
     * @param altriCampi  eventuali da visualizzare nel combo
     *
     * @return codice del record selezionato
     *         zero se non selezionato
     */
    public abstract int selezionaRecord(String nomeCampo,
                                        Filtro filtro,
                                        String messaggio,
                                        String valIniziale,
                                        boolean usaNuovo,
                                        Estratti est,
                                        String... altriCampi);


    /**
     * Presenta un dialogo per selezionare un record.
     * <p/>
     *
     * @param nomeCampo   da esaminare
     * @param filtro      dei records da selezionare
     * @param messaggio   del dialogo
     * @param valIniziale del selettore
     * @param usaNuovo    permette di creare nuovi record (default=true)
     * @param est         Eventuale estratto posizionato sotto
     *
     * @return codice del record selezionato
     *         zero se non selezionato
     */
    public abstract String selezionaTesto(String nomeCampo,
                                          Filtro filtro,
                                          String messaggio,
                                          String valIniziale,
                                          boolean usaNuovo,
                                          Estratti est);


    /**
     * Presenta un dialogo per selezionare un record.
     * <p/>
     *
     * @param nomeCampo da esaminare
     *
     * @return codice del record selezionato
     *         zero se non selezionato
     */
    public abstract int selezionaRecord(String nomeCampo);


    /**
     * Seleziona, con un dialogo, un valore da un campo testo con un selettore.
     * <p/>
     *
     * @param nomeCampo da esaminare
     *
     * @return valore del testo selezionato
     */
    public abstract String selezionaTesto(String nomeCampo);


    /**
     * Bottone carica tutti in una <code>Lista</code>.
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     * Sincronizza lo stato della GUI <br>
     * Invoca il metodo delegato <br>
     */
    public abstract void caricaTutti();


    /**
     * Chiude la scheda corrente registrando il record.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @return true se riuscito
     */
    public abstract boolean registraScheda();


    /**
     * Prima istallazione.
     * <p/>
     * Metodo invocato da Progetto.lancia() <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * <p/>
     * Esegue il metodo iniziale di istallazione per ogni modulo <br>
     */
    public abstract void setup();


    /**
     * Avvisa tutti i listener.
     * <p/>
     * Avvisa tutti i listener che si sono registrati
     * per questo tipo di evento <br>
     * L'evento viene creato al momento <br>
     * È responsabilità della classe invocare questo metodo quando
     * si creano le condizioni per generare l'evento <br>
     *
     * @see javax.swing.event.EventListenerList
     * @deprecated
     */
    public void fire();


    /**
     * Avvisa tutti i listener.
     * <p/>
     * Avvisa tutti i listener che si sono registrati
     * per questo tipo di evento <br>
     * L'evento viene creato al momento <br>
     * È responsabilità della classe invocare questo metodo quando
     * si creano le condizioni per generare l'evento <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    public void fire(Eventi tipoEvento);


    /**
     * Classe interna Enumerazione.
     */
    public enum ModoAvvio {

        normale(), // navigatore - default
        paletta(),
        splash(),
        dialogo(),
        senzaGui(),;

    }// fine della classe

//    /**
//     * Classe interna Enumerazione.
//     */
//    public enum AvvioProgramma {
//
//        palette(), // solo palette
//        navigatore(), // solo navigatore
//        paletteNavigatore(), // palette + navigatore
//        splash(), // splash screen
//        paletteSplash();  // palette + splash screen
//
//
//    }// fine della classe

}// fine della classe