/**
 * Title:     PortaleLista
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      19-mar-2004
 */
package it.algos.base.portale;

import it.algos.base.azione.Azione;
import it.algos.base.errore.Errore;
import it.algos.base.evento.BaseListener;
import it.algos.base.evento.lista.*;
import it.algos.base.lista.Lista;
import it.algos.base.lista.ListaBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.NavigatoreBase;
import it.algos.base.navigatore.info.Info;
import it.algos.base.navigatore.info.InfoLista;
import it.algos.base.navigatore.stato.StatoLista;
import it.algos.base.pannello.Pannello;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.toolbar.ToolBar;
import it.algos.base.toolbar.ToolBarLista;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;

import javax.swing.*;

/**
 * Contenitore della Lista con Eventi, Azioni.
 * </p>
 * Questa classe : <ul>
 * <li> Gestisce un'istanza di <code>Lista</code> </li>
 * <li> Gestisce un'istanza di <code>StatoLista</code> </li>
 * <li> Gestisce un'istanza di <code>ToolBar</code> (nella superclasse) </li>
 * </ul>
 * <p/>
 * Dimensioni:
 * La gestione delle dimensioni e' delegata a un Layout
 * Manager specializzato (LayoutPortaleLista)
 * <p/>
 * - Altezza:
 * - L'altezza del Portale e' determinata dal numero di righe che la
 * lista deve visualizzare.
 * - Se la toolbar e' verticale ed e' piu' alta della Lista, l'altezza
 * e' determinata dall'altezza della Toolbar.
 * - Se non specificato, il numero di righe visualizzato dalla Lista
 * regolato di default in ListaBase.
 * <p/>
 * - Larghezza:
 * La larghezza del Portale e' determinata da:
 * - Se la Toolbar e' verticale, la larghezza della Lista
 * piu' la larghezza della Toolbar.
 * - Se la Toolbar e' orizzontale, la larghezza della Lista o della
 * toolbar (la maggiore delle due)
 * - Se e' stato specificato un valore per la variabile larghezza,
 * usa sempre il valore specificato indipendentemente dalla larghezza
 * della lista o dall'orientamento della toolbar.
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 19-mar-2004 ore 14.36.57
 */
public class PortaleLista extends PortaleBase {

    /**
     * lista gestita dal portale
     */
    private Lista lista = null;

    /**
     * codifica dell'estratto utilizzato in caso di singolo record
     */
    private Estratti codEstratto = null;

    /**
     * Separatore usato dal pannello switch
     */
    private JSeparator separatore;


    /**
     * riferimento all'azione per il clic nella lista
     */
    private AzListaClic azioneClic = null;

    /**
     * riferimento all'azione per il doppio clic nella lista
     */
    private AzListaDoppioClic azioneDoppioClic = null;

    /**
     * riferimento all'azione per il tasto enter nella lista
     */
    private AzListaEnter azioneEnter = null;

    /**
     * riferimento all'azione per il tasto return nella lista
     */
    private AzListaReturn azioneReturn = null;

    /**
     * riferimento all'azione per la modifica di selezione nella lista
     */
    private AzListaSelezione azioneSelezione = null;

    /**
     * riferimento all'azione per gli spostamenti con cursore nella lista
     */
    private AzListaCursore azioneCursore = null;

    /**
     * flag - mostra un estratto in lista se c'è un solo record.
     */
    private boolean usaEstratto = false;

    /**
     * flag - permette di avere un solo record nella tavola collegata.
     * usato in comcomitanza con estratti
     */
    private boolean usaRecordSingolo = false;

    /**
     * flag - forza un comportamento uno a uno per l'estratto.
     */
    private boolean estUnoUno = false;

    /**
     * Abilita l'uso della funzionalità record preferito nella lista.
     */
    private boolean usaPreferito;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public PortaleLista() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param unNavigatore navigatore che gestisce questo pannello
     */
    public PortaleLista(Navigatore unNavigatore) {
        /* rimanda al costruttore della superclasse */
        super(unNavigatore);

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
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            /* Assegna il gestore dello stato */
            super.setStato(new StatoLista(this));

            /* separatore */
            this.setSeparatore(new JSeparator(JSeparator.VERTICAL));

            /* posizione di default della ToolBar per i portali Lista */
            this.setPosToolbar(ToolBar.Pos.ovest);

            this.setUsaEstratto(false);
            this.setUsaRecordSingolo(false);
            this.setEstUnoUno(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * effettua l'inizializzazione del portale <br>
     * sovrascrive il metodo della superclasse <br>
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        Lista lista;
        AzListaClic az = null;

        try {    // prova ad eseguire il codice
            /* inizializza nella superclasse */
            super.inizializza();

            /* crea le azioni da associare alla lista */
            this.creaAzioniLista();

            /* recupera alcune informazioni */
            lista = this.getLista();

            /* inizializza la lista */
            lista.inizializza();

            /* crea il pacchetto informazioni */
            super.setPacchettoInfo(new InfoLista(lista));

            /* regola l'azione */
            this.aggiungeListeners();

            /* marca come inizializzato */
            this.setInizializzato(true);


        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }// fine del metodo inizializza


    /**
     * crea le azioni da associare alla lista
     * <p/>
     */
    private void creaAzioniLista() {
        try {    // prova ad eseguire il codice
            /* crea le azioni da associare alla lista */
            this.setAzioneClic(new AzListaClic());
            this.setAzioneDoppioClic(new AzListaDoppioClic());
            this.setAzioneEnter(new AzListaEnter());
            this.setAzioneReturn(new AzListaReturn());
            this.setAzioneSelezione(new AzListaSelezione());
            this.setAzioneCursore(new AzListaCursore());
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Sovrascritto nelle sottoclassi
     */
    public void avvia() {

        try { // prova ad eseguire il codice

            /* invoca il metodo sovrascritto nella superclasse */
            super.avvia();

            /* se il navigatore e' pilotato, regola il filtro base
             * della lista in base ai valori pilota */
            if (this.getNavigatore().isPilotato()) {
                this.regolaFiltroBaseLista();
            }// fine del blocco if

            /* avvia la lista */
            this.getLista().avvia();

            /* regola il contenuto del componente principale */
            this.regolaMain();

            this.sincronizza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo avvia


    /**
     * Sincronizza la GUI del portale.
     * <p/>
     * Recupera le informazioni sullo stato del portale <br>
     * Regola lo stato del portale <br>
     * Invoca i metodi delegati nelle sottoclassi <br>
     */
    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        Object obj;
        InfoLista infoLista;

        try { // prova ad eseguire il codice

            obj = this.getInfoStato();
            if ((obj != null) && (obj instanceof InfoLista)) {
                infoLista = (InfoLista)obj;
                infoLista.avvia();
                infoLista = ((NavigatoreBase)this.getNavigatore()).getInfoLista(infoLista);
                this.setInfoStato(infoLista);
            }// fine del blocco if

            /* sincronizza la lista */
            this.getLista().sincronizza();

            /* sincronizza nella superclasse DOPO aver effettuato
             * le eventuali operazioni sul portale */
            super.sincronizza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ricarica la selezione della Lista.
     * <p/>
     * Usa il filtro base piu' quello corrente<br>
     * Invoca il metodo delegato <br>
     */
    public void aggiornaLista() {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice

            lista = this.getLista();
            if (lista != null) {
                lista.caricaSelezione();
            }// fine del blocco if

            /* visualizza la lista o l'estratto a seconda del numero dei records */
            this.regolaMain();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }

//    /**
//     * Costruisce graficamente il Portale.
//     * <p/>
//     * - svuota il Portale
//     * - assegna il Layout
//     * - aggiunge i componenti
//     */
//    protected void impagina() {
//
//        super.impagina();
//
//        try {    // prova ad eseguire il codice
//
//
//            /* aggiunge i componenti al portale */
//            switch (this.getPosizioneToolBar()) {
//                /* aggiunge prima la toolbar e poi la lista */
//                case Portale.TOOLBAR_PRIMA:
//                    super.aggiungiToolBar();
//                    this.add(this.getPannelloSwitch());
//                    break;
//                    /* aggiunge prima la lista e poi la toolbar */
//                case Portale.TOOLBAR_DOPO:
//                    this.add(this.getPannelloSwitch());
//                    super.aggiungiToolBar();
//                    break;
//                    /* posizione non definita, prima toolbar poi lista */
//                default:
//                    super.aggiungiToolBar();
//                    this.add(this.getPannelloSwitch());
//            } /* fine del blocco switch */
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//    }


    /**
     * Restituisce il codice chiave del record selezionato nella lista
     * o visualizzato nell'estratto.
     * <p/>
     *
     * @return codice chiave
     */
    public int getRecordSelezionato() {
        /* variabili e costanti locali di lavoro */
        Lista lista;
        int chiave = 0;

        try { // prova ad eseguire il codice

            lista = this.getLista();

            if (this.isEstrattoRecordSingolo()) {
                chiave = lista.getChiave(0);
            } else {
                chiave = lista.getRecordSelezionato();
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return chiave;
    }


    /**
     * Restituisce i codici chiave delle righe selezionate nella lista
     * o del record visualizzato nell'estratto.
     *
     * @return int[] un array delle chiavi selezionate
     */
    public int[] getChiaviSelezionate() {
        /* variabili e costanti locali di lavoro */
        Lista lista;
        int[] chiavi = null;

        try { // prova ad eseguire il codice

            lista = this.getLista();

            if (this.isEstrattoRecordSingolo()) {
                chiavi = new int[1];
                chiavi[0] = lista.getChiave(0);
            } else {
                chiavi = lista.getChiaviSelezionate();
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return chiavi;
    }


    /**
     * Aggiunge i listeners.
     * <p/>
     * Aggiunge i propri ascoltatori alla lista<br>
     * Metodo invocato dal ciclo Inizializza <br>
     */
    private void aggiungeListeners() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Lista lista;
        BaseListener listener;

        try {    // prova ad eseguire il codice
            /* recupera la lista */
            lista = this.getLista();
            continua = (lista != null);

            if (continua) {

                /* recupera ed aggiunge l'azione clic semplice */
                listener = this.getAzioneClic();
                lista.addListener(listener);

                /* recupera ed aggiunge l'azione doppio clic */
                listener = this.getAzioneDoppioClic();
                lista.addListener(listener);

                /* recupera ed aggiunge l'azione enter */
                listener = this.getAzioneEnter();
                lista.addListener(listener);

                /* recupera ed aggiunge l'azione return */
                listener = this.getAzioneReturn();
                lista.addListener(listener);

                /* recupera ed aggiunge l'azione selezione */
                listener = this.getAzioneSelezione();
                lista.addListener(listener);

                /* recupera ed aggiunge l'azione cursore */
                listener = this.getAzioneCursore();
                lista.addListener(listener);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Decide se usare l'estratto e lo recupera.
     * <p/>
     */
    private EstrattoBase getEstratto() {
        /* variabili e costanti locali di lavoro */
        EstrattoBase estratto = null;
        Estratti codEstratto;
        int codRec;

        try { // prova ad eseguire il codice

            codEstratto = this.getCodEstratto();
            if (codEstratto != null) {
                codRec = this.getLista().getChiave(0);
                estratto = this.getModulo().getEstratto(codEstratto, codRec);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return estratto;
    }


    /**
     * Regola il contenuto del componente principale.
     * <p/>
     */
    private void regolaMain() {
        /* variabili e costanti locali di lavoro */
        JComponent compMain;
        Pannello panEst;
        ListaBase lista;

        try { // prova ad eseguire il codice

            /* recupera il componente principale  */
            compMain = this.getCompMain();

            /* recupera l'estratto */
            panEst = this.getPannelloEstratto();

            /* recupera la lista  */
            lista = this.getLista().getListaBase();

            /* svuota il componente principale */
            compMain.removeAll();

            /* aggiunge il contenuto */
            if (this.isVisualizzaEstratto()) {
                compMain.add(panEst.getPanFisso());
            } else {
                compMain.add(lista);
            }// fine del blocco if-else
            compMain.validate();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola il filtro base della Lista
     * in base ai valori pilota del Navigatore.
     * <p/>
     */
    private void regolaFiltroBaseLista() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;

        try { // prova ad eseguire il codice
            filtro = this.getNavigatore().creaFiltroPilota();
            this.getLista().setFiltroBase(filtro);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola lo stato del portale Lista.
     * <p/>
     * Sincronizza la GUI del Portale <br>
     * Regola lo stato della Lista (azioni ed altro) <br>
     *
     * @param info il pacchetto di informazioni sullo stato della lista
     */
    public void setInfoStato(Info info) {
        try { // prova ad eseguire il codice
            this.getStato().regola(info);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea la toolbar.
     * <p/>
     * Sovrascrive il metodo della superclasse.<br>
     * La Toolbar viene creata orizzontale o verticale
     * in funzione dell'orientamento (invertito) del Navigatore.<br>
     */
    protected void creaToolbar() {
        /* variabili e costanti locali di lavoro */
        ToolBar toolbar = null;

        try {    // prova ad eseguire il codice

            toolbar = new ToolBarLista(this);
            this.setToolBar(toolbar);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    } // fine del metodo


    /**
     * Aggiunge le azioni al portale.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Invoca il metodo sovrascritto della superclasse <br>
     * Aggiunge ogni singola azione a questo portale (anche se non viene usata) <br>
     * La singola azione viene creata dal metodo delegato della superclasse, che
     * la clona dal Progetto <br>
     */
    protected void addAzioni() {
        try {    // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.addAzioni();

            /* gruppo di azioni per la gestione del singolo record */
            super.addAzione(Azione.NUOVO_RECORD);
            super.addAzione(Azione.AGGIUNGI_RECORD);
            super.addAzione(Azione.MODIFICA_RECORD);
            super.addAzione(Azione.DUPLICA_RECORD);
            super.addAzione(Azione.ELIMINA_RECORD);
            super.addAzione(Azione.RIMUOVI_RECORD);
            super.addAzione(Azione.PREFERITO);

            /* gruppo di azioni di controllo della Lista*/
            super.addAzione(Azione.SELEZIONE_MODIFICATA);

            /* gruppo di azioni del mouse nella Lista*/
            super.addAzione(Azione.LISTA_CLICK);
            super.addAzione(Azione.LISTA_DOPPIO_CLICK);
            super.addAzione(Azione.TITOLO);

            /* gruppo di azioni varie nella Lista*/
            super.addAzione(Azione.LISTA_CARATTERE);
            super.addAzione(Azione.LISTA_ENTER);
            super.addAzione(Azione.LISTA_RETURN);
            super.addAzione(Azione.ENTRATA_CELLA);
            super.addAzione(Azione.USCITA_CELLA);

            /* gruppo di azioni di selezione della lista */
            super.addAzione(Azione.RICERCA);
            super.addAzione(Azione.PROIETTA);
            super.addAzione(Azione.CARICA_TUTTI);
            super.addAzione(Azione.SOLO_SELEZIONATI);
            super.addAzione(Azione.NASCONDE_SELEZIONATI);

            /* gruppo di azioni di spostamento della lista */
            super.addAzione(Azione.COLONNA);
            super.addAzione(Azione.RIGA_SU);
            super.addAzione(Azione.RIGA_GIU);
            super.addAzione(Azione.FRECCE);
            super.addAzione(Azione.PAGINE);
            super.addAzione(Azione.HOME);
            super.addAzione(Azione.END);

            /* gruppo di azioni di stampa della Lista*/
            super.addAzione(Azione.STAMPA);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    public Lista getLista() {
        return lista;
    }


    /**
     * Assegna una Lista al Portale <p>
     * Regola il riferimento al Portale nella Lista
     *
     * @param lista la lista da assegnare
     */
    public void setLista(Lista lista) {
        lista.setPortale(this);
        this.lista = lista;
    }

//    /**
//     * Ritorna il componente grafico contenuto nel portale
//     * oltre alla toolbar
//     * <p/>
//     *
//     * @return il componente grafico
//     */
//    public JComponent getContenuto() {
//        /* variabili e costanti locali di lavoro */
//        JComponent comp = null;
//        Lista lista = null;
//
//        try { // prova ad eseguire il codice
//            lista = this.getLista();
//            if (lista != null) {
//                comp = (JComponent)lista;
//            }// fine del blocco if
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//        /* valore di ritorno */
//        return comp;
//    }


    public void setNomeVista(String nomeVista) {
        this.getLista().setNomeVista(nomeVista);
    }


    /**
     * Abilita o disabilita  l'uso della status bar nella Lista.
     * <p/>
     *
     * @param usaStatusBar true per usare la status bar, false per non usarla
     */
    public void setUsaStatusBar(boolean usaStatusBar) {
        this.getLista().setUsaStatusBar(usaStatusBar);
    }


    /**
     * Controlla l'uso del pulsante Preferito nella lista
     * <p/>
     *
     * @return true se usa il pulsante Preferito
     */
    public boolean isUsaPreferito() {
        return this.usaPreferito;
    }


    /**
     * Abilita l'uso del pulsante Preferito nella lista.
     * <p/>
     *
     * @param flag per usare il pulsante
     */
    public void setUsaPreferito(boolean flag) {
        /* variabili e costanti locali di lavoro */
        ToolBar tb;

        try { // prova ad eseguire il codice
            this.usaPreferito = flag;

            tb = this.getToolBar();
            if (tb != null) {
                tb.setUsaPreferito(flag);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    private Pannello getPannelloEstratto() {
        /* variabili e costanti locali di lavoro */
        Pannello pannello = null;
        EstrattoBase estratto;

        try { // prova ad eseguire il codice
            estratto = this.getEstratto();
            if (estratto != null) {
                pannello = estratto.getPannello();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pannello;
    }


    /**
     * Verifica se l'estratto deve essere visualizzato.
     * <p/>
     *
     * @return true se deve essere visualizzato
     */
    public boolean isVisualizzaEstratto() {
        /* variabili e costanti locali di lavoro */
        boolean usa = false;
        boolean continua;
        Pannello panEst;
        Lista lista = null;
        int quantiRec;


        try { // prova ad eseguire il codice

            continua = isUsaEstratto();

            /* recupera l'estratto */
            if (continua) {
                panEst = this.getPannelloEstratto();
                continua = (panEst != null);
            }// fine del blocco if

            /* recupera la lista */
            if (continua) {
                lista = this.getLista();
                continua = (lista != null);
            }// fine del blocco if

            if (continua) {
                quantiRec = lista.getNumRecordsVisualizzati();
                usa = (quantiRec == 0 || quantiRec == 1);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return usa;
    }


    /**
     * Verifica se la lista sottostante all'estratto
     * contiene uno e un solo record.
     * <p/>
     *
     * @return true se il record è uno
     */
    public boolean isEstrattoRecordSingolo() {
        /* variabili e costanti locali di lavoro */
        boolean usa = false;
        boolean continua;
        Pannello panEst;
        Lista lista = null;
        int quantiRec;


        try { // prova ad eseguire il codice

            continua = isUsaEstratto();

            /* recupera l'estratto */
            if (continua) {
                panEst = this.getPannelloEstratto();
                continua = (panEst != null);
            }// fine del blocco if

            /* recupera la lista */
            if (continua) {
                lista = this.getLista();
                continua = (lista != null);
            }// fine del blocco if

            if (continua) {
                quantiRec = lista.getNumRecordsVisualizzati();
                usa = quantiRec == 1;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return usa;
    }


    /**
     * Sincronizza gli elementi.
     * <p/>
     * Metodo invocato dal metodo fire() <br>
     * Può essere invocato anche da altri metodi interni <br>
     * (in salita) <b>
     */
    public void sincroInterno() {
    }


    /**
     * Sincronizza gli elementi.
     * <p/>
     * Metodo invocato da sincroEsterno del contenitore superiore <br>
     * (in discesa) <b>
     */
    public void sincroEsterno() {
    }


    /**
     * Esegue l'azione generata dall'evento.
     * <p/>
     * Metodo invocato dalla classe interna <br>
     */
    protected void esegui(ListaBase.Evento evento) {
        try { // prova ad eseguire il codice
            switch (evento) {
                case click:
                    this.sincronizza();
                    break;
                case doppioClick:
                    super.fire(PortaleBase.Evento.modificaRecord);
                    break;
                case enter:
                    super.fire(PortaleBase.Evento.modificaRecord);
                    break;
                case ritorno:
                    super.fire(PortaleBase.Evento.modificaRecord);
                    break;
                case selezione:
                    super.fire(PortaleBase.Evento.listaSelezione);
                    break;
                case frecciaAlto:
                    super.fire(PortaleBase.Evento.frecciaAlto);
//                        this.getLista().frecciaAlto();
                    break;
                case frecciaBasso:
                    super.fire(PortaleBase.Evento.frecciaBasso);
//                        this.getLista().frecciaBasso();
                    break;
                case paginaSu:
                    super.fire(PortaleBase.Evento.paginaSu);
//                        this.getNavigatore().pagine(evento.getEvento());
                    break;
                case paginaGiu:
                    super.fire(PortaleBase.Evento.paginaGiu);
//                        this.getNavigatore().pagine(evento.getEvento());
                    break;
                case home:
                    super.fire(PortaleBase.Evento.home);
//                        this.getNavigatore().home();
                    break;
                case end:
                    super.fire(PortaleBase.Evento.end);
//                        this.getNavigatore().end();
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Attiva l'uso dell'estratto per la lista con record singolo.
     * <p/>
     *
     * @param estratto da utilizzare
     * Per disattivare l'uso dell'estratto passare null
     */
    public void setEstratto(Estratti estratto) {
        try {    // prova ad eseguire il codice
            if (estratto != null) {
                this.setUsaEstratto(true);
                this.setEstUnoUno(false);
                this.setCodEstratto(estratto);
            } else {
                this.setUsaEstratto(false);
            }// fine del blocco if-else
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Attiva l'uso dell'estratto per la lista con record singolo.
     * <p/>
     * Blocca l'inserimento a un solo record <br>
     *
     * @param estratto da utilizzare
     * Per disattivare l'uso dell'estratto passare null
     */
    public void setEstrattoRecordSingolo(Estratti estratto) {
        try {    // prova ad eseguire il codice
            this.setEstratto(estratto);
            this.setUsaRecordSingolo(true);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Attiva l'uso dell'estratto Uno a Uno per la lista.
     * <p/>
     *
     * @param estratto da utilizzare
     * Per disattivare l'uso dell'estratto passare null
     */
    public void setEstrattoUnoUno(Estratti estratto) {
        try {    // prova ad eseguire il codice
            if (estratto != null) {
                this.setUsaEstratto(true);
                this.setEstUnoUno(true);
                this.setCodEstratto(estratto);
            } else {
                this.setUsaEstratto(false);
            }// fine del blocco if-else
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    private Estratti getCodEstratto() {
        return codEstratto;
    }


    private void setCodEstratto(Estratti codEstratto) {
        this.codEstratto = codEstratto;
    }


    private JSeparator getSeparatore() {
        return separatore;
    }


    private void setSeparatore(JSeparator separatore) {
        this.separatore = separatore;
    }


    private AzListaClic getAzioneClic() {
        return azioneClic;
    }


    private void setAzioneClic(AzListaClic azioneClic) {
        this.azioneClic = azioneClic;
    }


    private AzListaDoppioClic getAzioneDoppioClic() {
        return azioneDoppioClic;
    }


    private void setAzioneDoppioClic(AzListaDoppioClic azioneDoppioClic) {
        this.azioneDoppioClic = azioneDoppioClic;
    }


    private AzListaEnter getAzioneEnter() {
        return azioneEnter;
    }


    private void setAzioneEnter(AzListaEnter azioneEnter) {
        this.azioneEnter = azioneEnter;
    }


    private AzListaReturn getAzioneReturn() {
        return azioneReturn;
    }


    private void setAzioneReturn(AzListaReturn azioneReturn) {
        this.azioneReturn = azioneReturn;
    }


    private AzListaSelezione getAzioneSelezione() {
        return azioneSelezione;
    }


    private void setAzioneSelezione(AzListaSelezione azioneSelezione) {
        this.azioneSelezione = azioneSelezione;
    }


    private AzListaCursore getAzioneCursore() {
        return azioneCursore;
    }


    private void setAzioneCursore(AzListaCursore azioneCursore) {
        this.azioneCursore = azioneCursore;
    }


    private boolean isUsaEstratto() {
        return usaEstratto;
    }


    private void setUsaEstratto(boolean usaEstratto) {
        this.usaEstratto = usaEstratto;
    }


    public boolean isUsaRecordSingolo() {
        return usaRecordSingolo;
    }


    private void setUsaRecordSingolo(boolean usaRecordSingolo) {
        this.usaRecordSingolo = usaRecordSingolo;
    }


    private boolean isEstUnoUno() {
        return estUnoUno;
    }


    private void setEstUnoUno(boolean estUnoUno) {
        this.estUnoUno = estUnoUno;
    }


    /**
     * Inner class per gestire l'azione.
     */
    private class AzListaClic extends ListaClicAz {

        /**
         * listaClicAz, da ListaClicLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void listaClicAz(ListaClicEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                esegui(ListaBase.Evento.click);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Inner class per gestire l'azione.
     */
    private class AzListaDoppioClic extends ListaDoppioClicAz {

        /**
         * listaDoppioClicAz, da ListaDoppioClicLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void listaDoppioClicAz(ListaDoppioClicEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                esegui(ListaBase.Evento.doppioClick);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Inner class per gestire l'azione.
     */
    private class AzListaEnter extends ListaEnterAz {

        /**
         * listaEnterAz, da ListaEnterLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void listaEnterAz(ListaEnterEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                esegui(ListaBase.Evento.enter);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Inner class per gestire l'azione.
     */
    private class AzListaReturn extends ListaReturnAz {

        /**
         * listaReturnAz, da ListaReturnLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void listaReturnAz(ListaReturnEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                esegui(ListaBase.Evento.ritorno);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Inner class per gestire l'azione.
     */
    private class AzListaSelezione extends ListaSelAz {

        /**
         * listaSelAz, da ListaSelLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void listaSelAz(ListaSelEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                esegui(ListaBase.Evento.selezione);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Inner class per gestire l'azione.
     */
    private class AzListaCursore extends ListaCursoreAz {

        /**
         * listaFrecciaAltoAz, da ListaCursoreLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void listaFrecciaAltoAz(ListaCursoreEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                esegui(ListaBase.Evento.frecciaAlto);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * listaFrecciaBassoAz, da ListaCursoreLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void listaFrecciaBassoAz(ListaCursoreEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                esegui(ListaBase.Evento.frecciaBasso);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * listaPagSuAz, da ListaCursoreLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void listaPagSuAz(ListaCursoreEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                esegui(ListaBase.Evento.paginaSu);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * listaPagGiuAz, da ListaCursoreLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void listaPagGiuAz(ListaCursoreEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                esegui(ListaBase.Evento.paginaGiu);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * listaHomeAz, da ListaCursoreLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void listaHomeAz(ListaCursoreEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                esegui(ListaBase.Evento.home);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * listaEndAz, da ListaCursoreLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void listaEndAz(ListaCursoreEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                esegui(ListaBase.Evento.end);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    } // fine della classe interna

}// fine della classe
