/**
 * Title:     PortaleScheda
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-apr-2004
 */
package it.algos.base.portale;

import it.algos.base.azione.Azione;
import it.algos.base.errore.Errore;
import it.algos.base.evento.form.FormModificatoAz;
import it.algos.base.evento.form.FormModificatoEve;
import it.algos.base.evento.form.FormStatoAz;
import it.algos.base.evento.form.FormStatoEve;
import it.algos.base.form.Form;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.OnEditingFinished;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.info.Info;
import it.algos.base.navigatore.info.InfoScheda;
import it.algos.base.navigatore.stato.StatoScheda;
import it.algos.base.scheda.Scheda;
import it.algos.base.scheda.SchedaDefault;
import it.algos.base.toolbar.ToolBar;
import it.algos.base.toolbar.ToolBarScheda;

import javax.swing.*;
import javax.swing.event.EventListenerList;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Contenitore della Scheda.
 * </p>
 * Questa classe :
 * - Mantiene una collezione di schede da visualizzare alternativamente <br>
 * - Mantiene il nome chiave della scheda correntemente visualizzata <br>
 * - Mantiene un elenco di nomi di set in base ai quali costruire
 * automaticemente le corrispondenti schede. <br>
 * - La gestione delle dimensioni e' delegata a un Layout
 * Manager specializzato (LayoutPortaleScheda) <br>
 * - Se non specificato altrimenti, la dimensione di questo portale
 * e' pari alla larghezza della scheda piu' larga e all'altezza
 * della scheda piu' alta, piu' lo spazio per la toolbar.
 * - Se specificata una dimensione (setDimensioneEsterna) viene usata
 * la dimensione specificata.
 * <p/>
 * Uso:
 * - si crea l'istanza dell'oggetto
 * - si aggiungono eventuali oggetti scheda (addScheda)
 * o nomi di set (addSet)
 * - si regola eventualmente la scheda corrente
 * - si inizializza
 * - si avvia ogni volta che e' necessario
 * <p/>
 * Se sono stati aggiunti nomi di set, vengono create le
 * corrispondenti schede
 * La scheda corrente e' sempre l'ultima scheda aggiunta
 * alla collezione
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 21-apr-2004 ore 15.44.10
 */
public class PortaleScheda extends PortaleBase {

    /**
     * usa un colore di sfondo per vedere l'oggetto facilmente
     */
    protected static final boolean DEBUG = false;

    /**
     * nome chiave della scheda correntemente visualizzata
     */
    private String nomeSchedaCorrente = null;

    /**
     * lista di nomi di set
     */
    private ArrayList nomiSet = null;

    /**
     * collezione di oggetti scheda
     */
    private LinkedHashMap<String, Scheda> schede = null;

    
    // lista di listened di tipo onEditingFinished
    ArrayList<OnEditingFinished> finishListeners = new ArrayList<OnEditingFinished>();

//    /**
//     * tool bar alternativa
//     */
//    private ToolBar toolBarLista;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public PortaleScheda() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param unNavigatore navigatore che gestisce questo pannello
     */
    public PortaleScheda(Navigatore unNavigatore) {
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

        try { // prova ad eseguire il codice

            /* crea le collezioni */
            this.setNomiSet(new ArrayList());
            this.setSchede(new LinkedHashMap());

            /* crea la lista dei listeners */
            this.setListaListener(new EventListenerList());

            /* Regola il layout manager */
            this.setUsaGapFisso(true);
            this.setGapPreferito(0);

            /* il portale scheda e' trasparente */
            this.setOpaque(false);

            /* Assegna il gestore dello stato */
            super.setStato(new StatoScheda(this));

            /* posizione di default della ToolBar per i portali Scheda */
            this.setPosToolbar(ToolBar.Pos.nord);

            /* eventuale debug */
            if (DEBUG) {
                this.setOpaque(true);
                this.setBackground(Color.pink);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Invoca il metodo sovrascritto della superclasse <br>
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        Scheda scheda;

        try { // prova ad eseguire il codice

            /* inizializza nella superclasse */
            super.inizializza();

            /* crea le schede e le aggiunge
             * alla collezione delle schede del portale */
            this.creaSchede();

            /* inizializza tutte le schede */
            this.inizializzaSchede();

            /* usa la scheda corrente */
            scheda = this.getSchedaCorrente();
            this.usaScheda(scheda);

            /* marca come inizializzato */
            this.setInizializzato(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Sovrascritto nelle sottoclassi
     *
     * @param codice del record da caricare
     * @param nuovoRecord true se si sta presentando un nuovo record
     */
    public void avvia(int codice, boolean nuovoRecord) {
        /* variabili e costanti locali di lavoro */
        Scheda scheda;
        Navigatore nav;

        try { // prova ad eseguire il codice

            /* avvia nella superclasse */
            super.avvia(codice);

            /* recupera la scheda corrente */
            scheda = this.getSchedaCorrente();

            if (scheda != null) {

                /* aggiunge la scheda inizializzata al portale */
                this.usaScheda(scheda);

                /* regola il flag nuovo record della scheda */
                scheda.setNuovoRecord(nuovoRecord);

                /* avvia la scheda */
                scheda.avvia(codice);

                /* se il Portale è contenuto in un Navigatore, sincronizza il Navigatore */
                nav = this.getNavigatore();
                if (nav != null) {
                    nav.sincronizza();
                }// fine del blocco if

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

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
        /* variabili e costanti locali di lavoro */
        Scheda scheda;

        try { // prova ad eseguire il codice

            /* avvia la scheda */
            scheda = this.getSchedaCorrente();
            if (scheda != null) {
                scheda.avvia();
            }// fine del blocco if

            /* invoca il metodo sovrascritto nella superclasse */
            super.avvia();

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
        Scheda scheda;
        InfoScheda infoScheda;
        Object obj;

        try { // prova ad eseguire il codice

            /* recupera le informazioni sulla scheda corrente
             * e sincronizza il portale */
            scheda = this.getSchedaCorrente();
            if (scheda != null) {
                obj = this.getInfoStato();
                if ((obj != null) && (obj instanceof InfoScheda)) {
                    infoScheda = (InfoScheda)obj;
                    infoScheda.avvia();
                    this.setInfoStato(infoScheda);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea le schede e le aggiunge alla collezione.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     */
    protected void creaSchede() {
        /* variabili e costanti locali di lavoro */
        ArrayList lista = null;
        SchedaDefault scheda = null;
        String nome = "";
        String nomeCorrente = "";
        Navigatore nav;
        Modulo modulo;

        try { // prova ad eseguire il codice

            /* memorizza il nome della scheda corrente */
            nomeCorrente = this.getNomeSchedaCorrente();

            /* aggiunge al portale le schede costruite dai nomi di set */
            if (this.getSchede().size() == 0) {
                modulo = this.getModulo();
                if (modulo != null) {
                    lista = this.getNomiSet();
                    for (int k = 0; k < lista.size(); k++) {
                        nome = Lib.Testo.getStringa(lista.get(k));
                        scheda = new SchedaDefault(modulo);
                        scheda.setNomeSet(nome);
                        this.addScheda(nome, scheda);
                    } // fine del ciclo for
                }// fine del blocco if
            }// fine del blocco if

            /** se il portale non ha ancora schede, aggiunge una scheda dal nome
             di set del Navigatore */
            if (this.getSchede().size() == 0) {
                nav = this.getNavigatore();
                if (nav != null) {
                    nome = nav.getNomeSet();
                    if (Lib.Testo.isValida(nome)) {
                        modulo = this.getModulo();
                        if (modulo != null) {
                            scheda = new SchedaDefault(modulo);
                            scheda.setNomeSet(nome);
                            this.addScheda(nome, scheda);
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /** se il portale non ha ancora schede, aggiunge una scheda
             dal nome di set di default del modulo */
            if (this.getSchede().size() == 0) {
                modulo = this.getModulo();
                if (modulo != null) {
                    nome = Modulo.SET_BASE_DEFAULT;
                    scheda = new SchedaDefault(modulo);
                    scheda.setNomeSet(nome);
                    this.addScheda(nome, scheda);
                }// fine del blocco if
            }// fine del blocco if

            /** se c'era un nome di scheda corrente valido, ed esiste una
             scheda con tale nome, lo ripristina */
            if (Lib.Testo.isValida(nomeCorrente)) {
                if (this.getScheda(nomeCorrente) != null) {
                    this.setNomeSchedaCorrente(nomeCorrente);
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Inizializza tutte le schede della collezione.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     */
    protected void inizializzaSchede() {
        try { // prova ad eseguire il codice

            /**
             * Per tutte le schede:
             * - Assegna il riferimento al portale
             * - Inizializza la scheda
             * - Registra il portale presso ogni scheda come
             *   interessato alle modifiche che avvengono nei campi
             */
            for (Scheda scheda : this.getSchede().values()) {
                scheda.setPortale(this);
                scheda.inizializza();
                scheda.addListener(new AzioneModificaScheda());
            }
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Assegna una scheda al Portale.
     * <p/>
     * Sostituisce la scheda nel pannello schede con quella fornita
     *
     * @param scheda la scheda da utilizzare
     */
    protected void usaScheda(Scheda scheda) {
        /* variabili e costanti locali di lavoro */
        Component compNuovo;
        JComponent compMain;

        try {    // prova ad eseguire il codice

            if (scheda != null) {

                /* assegna alla scheda il riferimento a questo portale
                 * che la sta usando */
                scheda.setPortale(this);

                /* recupera il componente principale */
                compMain = this.getCompMain();

                /* svuota il componente principale */
                compMain.removeAll();

                /* inserisce la scheda nel componente principale */
                compMain.add(scheda.getScheda());

                /* crea il pacchetto informazioni per la scheda
                 * e lo assegna al portale */
                super.setPacchettoInfo(new InfoScheda(scheda));

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge una scheda alla collezione.
     * <p/>
     * Regola la scheda appena aggiunta come corrente <br>
     *
     * @param unaScheda da aggiungere
     *
     * @return il nome definitivo assegnato alla scheda  �
     */
    @Override
    public String addScheda(Scheda unaScheda) {
        /* variabili e costanti locali di lavoro */
        String chiave = "";

        try { // prova ad eseguire il codice
            chiave = unaScheda.getNomeChiave();

            chiave = this.addScheda(chiave, unaScheda);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return chiave;
    }


    /**
     * Aggiunge una scheda alla collezione.
     * <p/>
     * Regola la scheda appena aggiunta come corrente <br>
     * Se il nomeChiave è nullo o vuoto, usa come chiave un numero progressivo <br>
     *
     * @param nomeChiave della scheda
     * @param unaScheda da aggiungere
     *
     * @return il nome definitivo assegnato alla scheda
     */
    public String addScheda(String nomeChiave, Scheda unaScheda) {
        /* variabili e costanti locali di lavoro */
        String chiave = "";

        try { // prova ad eseguire il codice
            chiave = nomeChiave;

            if (Lib.Testo.isVuota(nomeChiave)) {
                chiave = "" + this.getSchede().size();
            }// fine del blocco if

            this.getSchede().put(chiave, unaScheda);
            this.setNomeSchedaCorrente(chiave);

            unaScheda.setPortale(this);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return chiave;
    }


    /**
     * Aggiunge un set alla collezione interna.
     * <p/>
     *
     * @param nomeSet da recuperare dal modello
     *
     * @return lo stesso nome del set passato come parametro
     */
    public String addSet(String nomeSet) {
        try { // prova ad eseguire il codice
            this.getNomiSet().add(nomeSet);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nomeSet;
    }


    /**
     * Aggiunge il set di default alla collezione interna.
     * <p/>
     * Recupera il set dal Modello <br>
     */
    private void addSetDefault() {
        /* invoca il metodo delegato della classe */
        this.addSet(Modulo.SET_BASE_DEFAULT);
    }


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

            /* gruppo di azioni di archivio */
            this.addAzione(Azione.CHIUDE_SCHEDA);
            this.addAzione(Azione.REGISTRA_SCHEDA);
            this.addAzione(Azione.ANNULLA_MODIFICHE);

            /* gruppo di azioni di spostamento */
            this.addAzione(Azione.PRIMO_RECORD);
            this.addAzione(Azione.RECORD_PRECEDENTE);
            this.addAzione(Azione.RECORD_SUCCESSIVO);
            this.addAzione(Azione.ULTIMO_RECORD);

            /* gruppo di azioni di lista (navigatore record unico) */
            this.addAzione(Azione.NUOVO_RECORD);
            this.addAzione(Azione.ELIMINA_RECORD);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea la toolbar.<br>
     * Sovrascrive il metodo della superclasse.<br>
     * La Toolbar viene creata orizzontale o verticale
     * in funzione dell'orientamento del Navigatore.<br>
     */
    protected void creaToolbar() {
        /* variabili e costanti locali di lavoro */
        ToolBar toolbar;
//        boolean orientamento;
//        Azione az;

        try {    // prova ad eseguire il codice

            /* tool bar normale della scheda */
            toolbar = new ToolBarScheda(this);
//            orientamento = toolbar.isVerticale();
            this.setToolBar(toolbar);

//            /* tool bar alternativa per la scheda di un navigatore a record unico */
//            toolbar = new ToolBarLista(this);
//            toolbar.setVerticale(orientamento);
//            toolbar.setTipoIcona(ToolBar.ICONA_PICCOLA);
//            toolbar.setUsaNuovo(false);
//            toolbar.setUsaModifica(false);
//            toolbar.setUsaElimina(true);
//
//            az = this.getAzione(Azione.NUOVO_RECORD);
//            toolbar.addBottone(az);
//            az = this.getAzione(Azione.ELIMINA_RECORD);
//            toolbar.addBottone(az);
//
//            az = this.getAzione(Azione.CHIUDE_SCHEDA);
//            toolbar.addBottone(az);
//            az = this.getAzione(Azione.REGISTRA_SCHEDA);
//            toolbar.addBottone(az);
//            az = this.getAzione(Azione.ANNULLA_MODIFICHE);
//            toolbar.addBottone(az);
//            this.setToolBarLista(toolbar);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    } // fine del metodo


    /**
     * Regola lo stato del portale Scheda.
     * <p/>
     * Sincronizza la GUI del Portale <br>
     * Regola lo stato della Scheda (azioni ed altro) <br>
     *
     * @param info il pacchetto di informazioni sullo stato della scheda
     */
    public void setInfoStato(Info info) {
        try { // prova ad eseguire il codice
            this.getStato().regola(info);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna la scheda corrente.
     * <p/>
     * E' la scheda attualmente visualizzata da questo Portale.
     *
     * @return la scheda corrente
     */
    public Scheda getSchedaCorrente() {
        /* variabili e costanti locali di lavoro */
        Scheda scheda = null;
        String nome = "";

        try { // prova ad eseguire il codice
            nome = this.getNomeSchedaCorrente();
            scheda = this.getScheda(nome);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return scheda;
    }


    /**
     * Ritorna il form contenuto nel Portale.
     * <p/>
     *
     * @return il form contenuto nel Portale, null se il portale
     *         non contiene un form
     */
    public Form getForm() {
        return this.getSchedaCorrente();
    }
    
    public void addEditFinishedListener(OnEditingFinished listener){
    	this.finishListeners.add(listener);
    }


    /** 
     * lancia un evento di editing completato 
     * @param codice - il codice del record editato nella scheda
     * @param registrato - true se è stato registrato, false se abbandonato
     */
    public void fireEditingFinished(int codice, boolean registrato){
    	for (OnEditingFinished listener : finishListeners ) {
    		listener.onEditingFinished(getModulo(), codice, registrato);
		}
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
//        Scheda scheda = null;
//
//        try { // prova ad eseguire il codice
//            scheda = this.getSchedaCorrente();
//            if (scheda != null) {
//                comp = (JComponent)scheda;
//            }// fine del blocco if
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//        /* valore di ritorno */
//        return comp;
//    }


    public ArrayList getNomiSet() {
        return nomiSet;
    }


    private void setNomiSet(ArrayList nomiSet) {
        this.nomiSet = nomiSet;
    }


    public LinkedHashMap<String, Scheda> getSchede() {
        return schede;
    }


    private void setSchede(LinkedHashMap schede) {
        this.schede = schede;
    }


    public Scheda getScheda(String chiave) {
        return (Scheda)this.getSchede().get(chiave);
    }

//    private ToolBar getToolBarLista() {
//        return toolBarLista;
//    }
//
//
//    private void setToolBarLista(ToolBar toolBarLista) {
//        this.toolBarLista = toolBarLista;
//    }


    /**
     * Ritorna il nome chiave della scheda corrente.
     * <p/>
     * E' il nome chiave della scheda nella collezione schede di
     * questo Portale
     *
     * @return il nome chiave della scheda corrente nella collezione
     */
    public String getNomeSchedaCorrente() {
        return nomeSchedaCorrente;
    }


    public void setNomeSchedaCorrente(String nomeSchedaCorrente) {
        this.nomeSchedaCorrente = nomeSchedaCorrente;
    }


    /**
     *
     */
    private void eseguiScheda() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice
            this.sincroInterno();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Chiamata quando una scheda cambia di stato backup / memoria.
     */
    private class AzioneStatoScheda extends FormStatoAz {

        /**
         * campoModificato, da CampoListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void formStatoAz(FormStatoEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                eseguiScheda();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Chiamata ogni volta che un campo di una scheda viene modificato.
     */
    private class AzioneModificaScheda extends FormModificatoAz {

        /**
         * campoModificato, da CampoListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void formModificatoAz(FormModificatoEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                eseguiScheda();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


}// fine della classe
