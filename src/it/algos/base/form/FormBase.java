/**
 * Title:     FormBase
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      06-ago-2005
 */
package it.algos.base.form;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.dati.CDNavigatore;
import it.algos.base.campo.logica.CLNavigatore;
import it.algos.base.campo.logica.CampoLogica;
import it.algos.base.errore.Errore;
import it.algos.base.evento.BaseListener;
import it.algos.base.evento.Eventi;
import it.algos.base.evento.GestioneEventi;
import it.algos.base.evento.campo.*;
import it.algos.base.evento.form.*;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.libro.Libro;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.portale.Portale;
import it.algos.base.wrapper.Campi;

import javax.swing.border.Border;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.util.*;

/**
 * Superclasse astratta di scheda e dialogo.
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 06-ago-2005 ore 14.44.12
 */
public abstract class FormBase extends PannelloFlusso implements GestioneEventi, Form {

    /**
     * Eventuale modulo di riferimento del form
     */
    private Modulo modulo;

    /**
     * collezione degli oggetti Campo del form
     */
    private LinkedHashMap<String, Campo> campi = null;

    /**
     * Portale proprietario di questo form
     */
    private Portale portale = null;

    /**
     * Libro contenuto nel form
     */
    private Libro libro = null;

    /**
     * Flag - controllo avvenuta inizializzazione
     */
    private boolean inizializzato = false;

    /**
     * flag indicatore dello stato di modifica del form
     */
    private boolean statoModificato;

    /**
     * A list of event listeners for this component.
     */
    private EventListenerList listaListener;

    /**
     * flag - indica che il form è "attivo", cioè sta
     * visualizzando/modificando un record valido.
     * viene acceso al termine dell'avvio e spento alla
     * chiusura (dialogo) o dismissione (scheda)
     */
    private boolean attivo;

    /**
     * Margine interno per tutte le pagine del Form
     */
    private Pagina.Margine margine;

    /**
     * flag di controllo della modificabilità del form
     */
    private boolean modificabile;


    /**
     * Costruttore senza parametri.
     * <p/>
     * Costruisce un form senza modulo e con orientamento
     * di default (verticale)
     */
    public FormBase() {
        /* rimanda al costruttore della superclasse */
        this(null);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo.
     * <p/>
     * Costruisce un form senza modulo e con l'orientamento specificato.
     *
     * @param orientamento del layout
     */
    public FormBase(int orientamento) {

        this(null, orientamento);

    }// fine del metodo costruttore completo


    /**
     * Costruttore completo con parametri.
     * <p/>
     * Costruisce un form con modulo e con l'orientamento di default.
     *
     * @param modulo di riferimento
     */
    public FormBase(Modulo modulo) {

        this(modulo, Layout.ORIENTAMENTO_VERTICALE);

    }// fine del metodo costruttore completo


    /**
     * Costruttore completo con parametri.
     * <p/>
     * Costruisce un form con modulo e l'orientamento specificato.
     *
     * @param modulo di riferimento
     * @param orientamento codice dell'orientamento del layout
     * Layout.ORIENTAMENTO_ORIZZONTALE o Layout.ORIENTAMENTO_VERTICALE
     */
    public FormBase(Modulo modulo, int orientamento) {

        /* rimanda al costruttore della superclasse */
        super(orientamento);

        /* regola le variabili di istanza coi parametri */
        this.setModulo(modulo);

        try { // prova ad eseguire il codice

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice

            /* crea lista degli ascoltatori dei propri eventi */
            this.setListaListener(new EventListenerList());

            /* crea la collezione di campi */
            this.setCampi(new LinkedHashMap<String, Campo>());

            /* crea un libro per i contenuti
             * e lo aggiunge al form */
            this.setLibro(new Libro());
            this.getLibro().setForm(this);
            this.add(this.getLibro());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);

        }// fine del blocco try-catch

    }


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {

//        super.inizializza();

        try { // prova ad eseguire il codice

            /* creazione delle pagine */
            this.creaPagine();

            /* inizializza i campi */
            this.inizializzaCampi();

            /* regola il margine delle pagine, che ora esistono */
            this.regolaMarginePagine();

            /* registra questo form come listener presso i propri campi */
            this.aggiungeListenerForm();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea le pagine della scheda.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaPagine() {
    } /* fine del metodo */


    /**
     * Assegno i tasti ai bottoni standard.
     * <p/>
     * Escape <br>
     * Enter <br>
     */
    protected void regolaAzioni() {
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     */
    public void avvia() {


        try { // prova ad eseguire il codice

            /* avvia i campi */
            this.avviaCampi();

            /* avvia il Libro */
            this.getLibro().avvia();

            /*
             * segna inizialmente lo stato del form come non modificato
             * rappresenta lo stato di uguaglianza backup - memoria
             * per tutti i campi del form
             */
            this.setStatoModificato(false);

            /* prima sincronizzazione */
            this.sincronizza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Regola il margine di tutte le pagine.
     * <p/>
     * Usa il margine solo se è stato assegnato
     * Se non è stato assegnato un margine specifico, lascia
     * quello già esistente nelle Pagine
     */
    protected void regolaMarginePagine() {
        /* variabili e costanti locali di lavoro */
        Pagina.Margine margine;

        try {    // prova ad eseguire il codice

            margine = this.getMargine();
            if (margine != null) {
                this.setMargine(margine);
            }// fine del blocco if

        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Inizializza tutti i campi del form.
     * <p/>
     */
    protected void inizializzaCampi() {

        LinkedHashMap<String, Campo> campi;

        try {    // prova ad eseguire il codice
            campi = this.getCampi();
            for (Campo campo : campi.values()) {

                if (!campo.isInizializzato()) {
                    campo.inizializza();
                }// fine del blocco if

                /* regola il riferimento al form nel campo */
                campo.setForm(this);

            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Avvia tutti i campi della collezione.
     * <p/>
     */
    protected void avviaCampi() {

        try {    // prova ad eseguire il codice

            /* avvia tutti i campi della collezione */
            LinkedHashMap<String, Campo> campi;
            campi = this.getCampi();
            for (Campo campo : campi.values()) {
                if (!campo.isInizializzato()) {
                    campo.inizializza();
                }// fine del blocco if
                campo.avvia();
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Sincronizzazione della scheda/dialogo.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     * Sincronizza il portale del form
     */
    public void sincronizza() {
        Portale portale;

        try { // prova ad eseguire il codice
            /* sincronizza il portale */
            portale = this.getPortale();
            if (portale != null) {
                portale.sincronizza();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Registra questo form come ascoltatore di alcuni eventi dei campi.
     * <p/>
     */
    protected void aggiungeListenerForm() {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, Campo> campi;

        try { // prova ad eseguire il codice
            campi = this.getCampi();
            for (Campo campo : campi.values()) {
                this.aggiungeListenerForm(campo);
            }

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Registra questo form come ascoltatore di alcuni eventi di un campo.
     * <p/>
     *
     * @param campo al quale aggiungere i listeners
     */
    private void aggiungeListenerForm(Campo campo) {
        try {    // prova ad eseguire il codice

            /* generico (?)*/
            campo.addListener(new AzioneCampo());
            /* entro nel campo */
            campo.addListener(new AzionePrendeFuocoCampo());
            /* esco dal campo */
            campo.addListener(new AzionePerdeFuocoCampo());
            /* esco dal campo dopo una modifica */
            campo.addListener(new AzioneModificaCampo());
            /* memoria del campo modificata */
            campo.addListener(new AzioneMemoriaCampo());
            /* stato di uguaglianza backup/memoria del campo */
            campo.addListener(new AzioneStatoCampo());
            /* bottone associato al campo premuto */
            campo.addListener(new AzioneBottoneCampo());
            /* ritorno dopo la presentazione di un record */
            campo.addListener(new AzionePresentatoRecord());

            /* associa i campi osservati al campo osservatore calcolato */
            campo.getCampoLogica().regolaAzione();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Resetta i valori di tutti i campi.
     * <p/>
     * Resetta i valori memoria e backup di ogni campo <br>
     */
    public void resetCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;
        Iterator unGruppo;

        try {    // prova ad eseguire il codice

            unGruppo = this.getCampi().values().iterator();

            /* traversa tutta la collezione */
            while (unGruppo.hasNext()) {
                unCampo = (Campo)unGruppo.next();
                unCampo.reset();
            } /* fine del blocco while */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * Aggiunge un componente alla prima pagina del form.
     * <p/>
     *
     * @param comp il componente da aggiungere
     */
    public void addComponente(Component comp) {
        /* variabili e costanti locali di lavoro */
        Pagina pagina;

        try { // prova ad eseguire il codice
            pagina = this.getLibro().getPagina(0);
            if (pagina != null) {
                pagina.add(comp);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Aggiunge un componente ad una specifica pagina del form.
     * <p/>
     *
     * @param comp il componente da aggiungere
     * @param numPagina il numero della pagina (0 per la prima)
     */
    public void addComponente(Component comp, int numPagina) {
        /* variabili e costanti locali di lavoro */
        Pagina pagina;

        try { // prova ad eseguire il codice
            pagina = this.getLibro().getPagina(numPagina);
            if (pagina != null) {
                pagina.add(comp);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Rimuove un componente dalla prima pagina del form.
     * <p/>
     *
     * @param comp il componente da rimuovere
     */
    public void removeComponente(Component comp) {
        /* variabili e costanti locali di lavoro */
        Pagina pagina;

        try { // prova ad eseguire il codice
            pagina = this.getLibro().getPagina(0);
            if (pagina != null) {
                pagina.remove(comp);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Rimuove un componente da una specifica pagina del form.
     * <p/>
     *
     * @param comp il componente da rimuovere
     * @param numPagina il numero della pagina (0 per la prima)
     */
    public void removeComponente(Component comp, int numPagina) {
        /* variabili e costanti locali di lavoro */
        Pagina pagina;

        try { // prova ad eseguire il codice
            pagina = this.getLibro().getPagina(numPagina);
            if (pagina != null) {
                pagina.remove(comp);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge un pannello.
     * <p/>
     * Aggiunge il pannello alla parte grafica <br>
     * Aggunge i campi eventualmente contenuti nel pannello alla collezione <br>
     *
     * @param pan pannello da aggiungere
     */
    public void addPannello(Pannello pan) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campi;

        try { // prova ad eseguire il codice
            campi = pan.getCampiPannello();

            for (Campo campo : campi) {
                this.addCampoCollezione(campo);
            } // fine del ciclo for-each

            this.addComponente(pan.getPanFisso());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Rimuove un pannello.
     * <p/>
     * Rimuove il pannello alla parte grafica <br>
     * Rimuove i campi eventualmente contenuti nel pannello dalla collezione <br>
     *
     * @param pan pannello da rimuovere
     */
    public void removePannello(Pannello pan) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campi;

        try { // prova ad eseguire il codice
            campi = pan.getCampiPannello();

            for (Campo campo : campi) {
                this.removeCampoCollezione(campo);
            } // fine del ciclo for-each

            this.removeComponente(pan.getPanFisso());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge un campo alla collezione.
     * <p/>
     * Usa il nome del campo come chiave.<br>
     * Non aggiunge graficamente il campo al contenitore.<br>
     *
     * @param campo il campo da aggiungere
     */
    public void addCampoCollezione(Campo campo) {
        /* variabili e costanti locali di lavoro */
        String chiave;

        try { // prova ad eseguire il codice
            chiave = campo.getNomeInterno();
            this.addCampoCollezione(campo, chiave);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge un campo alla collezione con una data chiave.
     * <p/>
     * Non aggiunge graficamente il campo al contenitore.<br>
     *
     * @param campo il campo da aggiungere
     * @param chiave per la collezione
     */
    public void addCampoCollezione(Campo campo, String chiave) {
        /* variabili e costanti locali di lavoro */
        HashMap<String, Campo> campi;

        try { // prova ad eseguire il codice
            campi = this.getCampi();
            if (campi != null) {

                /* se il form e' gia' stato inizializzato, aggiunge
                 * i listener al form nel momento in cui
                 * il campo viene aggiunto */
                if (this.isInizializzato()) {
                    this.aggiungeListenerForm(campo);
                }// fine del blocco if

                campi.put(chiave, campo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Rimuove un campo dalla collezione.
     * <p/>
     * Usa il nome del campo come chiave.<br>
     * Non rimuove graficamente il campo dal contenitore.<br>
     *
     * @param campo il campo da rimuovere
     */
    public void removeCampoCollezione(Campo campo) {
        /* variabili e costanti locali di lavoro */
        String chiave;

        try { // prova ad eseguire il codice
            chiave = campo.getNomeInterno();
            this.removeCampoCollezione(chiave);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Rimuove un campo con una data chiave dalla collezione.
     * <p/>
     * Non rimuove graficamente il campo dal contenitore.<br>
     *
     * @param chiave per la ricerca nella collezione
     */
    public void removeCampoCollezione(String chiave) {
        /* variabili e costanti locali di lavoro */
        Map campi;

        try { // prova ad eseguire il codice
            campi = this.getCampi();
            if (campi != null) {
                campi.remove(chiave);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Controlla l'esistenza di un campo.
     * <p/>
     *
     * @param chiave chiave per recuperare il campo dalla collezione
     *
     * @return vero se il campo esiste nella collezione campi
     */
    public boolean isEsisteCampo(String chiave) {
        return this.getCampi().containsKey(chiave);
    }


    /**
     * Restituisce il valore di un campo.
     * <p/>
     * Sincronizza la memoria col valore video <br>
     * Recupera il valore della variabile memoria del Campodati <br>
     *
     * @param unCampo da interrogare
     *
     * @return valore memoria del campo (oggetto indifferenziato)
     *
     * @see it.algos.base.campo.logica.CLBase#guiMemoria()
     */
    public Object getValore(Campo unCampo) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;

        try { // prova ad eseguire il codice
            /* Sincronizza la memoria col valore video */
//            unCampo.getCampoLogica().guiMemoria();

            /* Recupera il valore della variabile memoria del Campodati */
            valore = unCampo.getCampoDati().getMemoria();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Restituisce il valore di un campo.
     * <p/>
     * Recupera il campo dalla collezione interna del Dialogo
     * Sincronizza la memoria col valore video <br>
     * Recupera il valore della variabile memoria del Campodati <br>
     *
     * @param nomeCampo da interrogare
     *
     * @return valore memoria del campo (oggetto indifferenziato)
     *
     * @see it.algos.base.campo.logica.CLBase#guiMemoria()
     */
    public Object getValore(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        Object valore = null;

        try { // prova ad eseguire il codice
            /* Recupera il campo dalla collezione interna del Dialogo */
            campo = this.getCampo(nomeCampo);

            /* Invoca il metodo sovrascritto della classe */
            if (campo != null) {
                valore = this.getValore(campo);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Restituisce il valore stringa di un campo.
     * <p/>
     * Recupera il campo dalla collezione interna
     * Sincronizza la memoria col valore video <br>
     * Recupera il valore della variabile memoria del Campodati <br>
     * Effettua il casting al tipo stringa <br>
     *
     * @param nomeCampo da interrogare
     *
     * @return valore memoria del campo col casting al tipo stringa
     */
    public String getString(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        String valore = null;

        try { // prova ad eseguire il codice
            /* Invoca il metodo sovrascritto della classe e casting */
            valore = Lib.Testo.getStringa(this.getValore(nomeCampo));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Restituisce il valore intero di un campo.
     * <p/>
     * Recupera il campo dalla collezione interna del Dialogo
     * Sincronizza la memoria col valore video <br>
     * Recupera il valore della variabile memoria del Campodati <br>
     * Effettua il casting al tipo intero <br>
     *
     * @param nomeCampo da interrogare
     *
     * @return valore memoria del campo col casting al tipo intero
     */
    public int getInt(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        int valore = 0;

        try { // prova ad eseguire il codice
            /* Invoca il metodo sovrascritto della classe e casting */
            valore = Libreria.getInt(this.getValore(nomeCampo));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Restituisce il valore double di un campo.
     * <p/>
     * Recupera il campo dalla collezione interna del Dialogo
     * Sincronizza la memoria col valore video <br>
     * Recupera il valore della variabile memoria del Campodati <br>
     * Effettua il casting al tipo intero <br>
     *
     * @param nomeCampo da interrogare
     *
     * @return valore memoria del campo col casting al tipo intero
     */
    public double getDouble(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        double valore = 0;

        try { // prova ad eseguire il codice
            /* Invoca il metodo sovrascritto della classe e casting */
            valore = Libreria.getDouble(this.getValore(nomeCampo));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Restituisce il valore booleano di un campo.
     * <p/>
     * Recupera il campo dalla collezione interna del Dialogo
     * Sincronizza la memoria col valore video <br>
     * Recupera il valore della variabile memoria del Campodati <br>
     * Effettua il casting al tipo booleano <br>
     *
     * @param nomeCampo da interrogare
     *
     * @return valore memoria del campo col casting al tipo booleano
     */
    public boolean getBool(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        boolean valore = false;

        try { // prova ad eseguire il codice
            /* Invoca il metodo sovrascritto della classe e casting */
            valore = Libreria.getBool(this.getValore(nomeCampo));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Restituisce il valore data di un campo.
     * <p/>
     *
     * @param nomeCampo da interrogare
     *
     * @return valore memoria del campo col casting al tipo data
     */
    public Date getData(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Date valore = null;

        try { // prova ad eseguire il codice
            /* Invoca il metodo sovrascritto della classe e casting */
            valore = Libreria.getDate(this.getValore(nomeCampo));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Assegna un valore a un campo.
     * <p/>
     *
     * @param campo al quale asssegnare il valore
     * @param valore da assegnare
     */
    public void setValore(Campo campo, Object valore) {
        try { // prova ad eseguire il codice
            campo.setValore(valore);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Assegna un valore a un campo.
     * <p/>
     *
     * @param nome del campo al quale asssegnare il valore
     * @param valore da assegnare
     */
    public void setValore(String nome, Object valore) {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice
            campo = this.getCampo(nome);
            if (campo != null) {
                this.setValore(campo, valore);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }



    /**
     * Rende il form abilitato o meno.
     * <p/>
     * Gira la chiamata a tutti i campi
     *
     * @param flag per rendere il form abilitato
     */
    public void setAbilitato(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Map<String, Campo> mappaCampi;

        try {    // prova ad eseguire il codice

            /* abilita tutti i campi */
            mappaCampi = this.getCampi();
            if (mappaCampi != null) {

                /*
                 * rende modificabili i campi abilitati
                 * o rende non modificabili tutti i campi
                 */
                for (Campo campo : mappaCampi.values()) {
                    campo.setAbilitato(flag);
                }
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }



    /**
     * Controlla se il form è modificabile
     * <p/>
     *
     * @return true se è modificabile
     */
    public boolean isModificabile() {
        return modificabile;
    }


    /**
     * Rende il form modificabile o meno.
     * <p/>
     *
     * @param flag per rendere modificabile
     */
    public void setModificabile(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Map<String, Campo> mappaCampi;

        try {    // prova ad eseguire il codice

            /* abilita tutti i campi */
            mappaCampi = this.getCampi();
            if (mappaCampi != null) {

                /*
                 * rende modificabili i campi abilitati
                 * o rende non modificabili tutti i campi
                 */
                for (Campo campo : mappaCampi.values()) {
                    campo.setModificabile(flag);
                }
            }// fine del blocco if

            this.modificabile = flag;

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }





    /**
     * Aggiorna le liste dei campi Navigatore
     * contenuti nel form
     * <p/>
     */
    public void aggiornaListeNavigatori() {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, Campo> campi;
        CampoLogica cl;
        Navigatore nav;

        try {    // prova ad eseguire il codice
            campi = this.getCampi();
            for (Campo campo : campi.values()) {
                cl = campo.getCampoLogica();
                if (cl instanceof CLNavigatore) {
                    nav = campo.getNavigatore();
                    if (nav != null) {
                        nav.aggiornaLista();
                    }// fine del blocco if
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Recupera un campo dalla collezione.
     * <p/>
     *
     * @param chiave per recuperare il campo
     *
     * @return il campo recuperato
     */
    public Campo getCampo(String chiave) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        HashMap campi;
        String chiaveMinuscola;

        try { // prova ad eseguire il codice
            chiaveMinuscola = chiave.toLowerCase();
            campi = this.getCampi();
            if (campi != null) {
                campo = (Campo)campi.get(chiaveMinuscola);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
        /* valore di ritorno */
        return campo;
    }


    /**
     * Recupera un campo dalla collezione.
     * <p/>
     *
     * @param campo Enumeration dell'interfaccia
     *
     * @return il campo recuperato
     */
    public Campo getCampo(Campi campo) {
        return getCampo(campo.get());
    }


    /**
     * Allinea le variabili di tutti i campi: da GUI verso Memoria.
     * <p/>
     * Partendo dalla componente GUI di CampoVideo, regola di conseguenza le
     * variabili Video e Memoria di CampoDati <br>
     * Recupera il valore attuale del componente GUI in CampoVideo <br>
     * Aggiorna il valore del video e della memoria in CampoDati <br>
     * La variabile memoria e' allineata per calcoli varii <br>
     */
    protected void guiMemoria() {
        /* traversa tutta la collezione */

        try { // prova ad eseguire il codice
            for (Campo unCampo : this.getCampi().values()) {
                unCampo.getCampoLogica().guiMemoria();
            }
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce le pagine del form.
     * <p/>
     * Recupera le pagine dal libro.
     *
     * @return le pagine del form
     */
    public ArrayList<Pagina> getPagine() {
        /* variabili e costanti locali di lavoro */
        Libro libro;
        ArrayList<Pagina> pagine = null;
        Pagina[] pagLibro;

        try {    // prova ad eseguire il codice

            libro = this.getLibro();
            if (libro != null) {
                pagine = new ArrayList<Pagina>();
                pagLibro = libro.getPagineLibro();
                for (Pagina pag : pagLibro) {
                    pagine.add(pag);
                }
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pagine;
    }


    /**
     * Ritorna la prima pagina del form
     * <p/>
     *
     * @return la prima pagina
     */
    protected Pagina getPrimaPagina() {
        return this.getLibro().getPagina(0);
    }


    /**
     * Regola il bordo di tutte le pagine del form.
     * <p/>
     *
     * @param bordo da applicare a tutte le pagine
     */
    public void setBordoPagine(Border bordo) {
        /* variabili e costanti locali di lavoro */
        Libro libro;

        try {    // prova ad eseguire il codice

            libro = this.getLibro();
            if (libro != null) {
                libro.setBordoPagine(bordo);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Attiva o disattiva lo scorrevole sul contenuto
     * <p/>
     * Sovrascrive per agire su tutte le pagine del Libro
     */
    public void setUsaScorrevole(boolean flag) {
        for (Pagina pag : this.getPagine()) {
            pag.setUsaScorrevole(flag);
        }
    }


    /**
     * Attiva o disattiva l'uso del gap fisso
     * <p/>
     * Sovrascrive per agire su tutte le pagine del Libro
     */
    public void setUsaGapFisso(boolean usaGapFisso) {
        for (Pagina pag : this.getPagine()) {
            pag.setUsaGapFisso(usaGapFisso);
        }
    }


    /**
     * Regola il gap minimo
     * <p/>
     * Sovrascrive per agire su tutte le pagine del Libro
     */
    public void setGapMinimo(int gapMinimo) {
        for (Pagina pag : this.getPagine()) {
            pag.setGapMinimo(gapMinimo);
        }
    }


    /**
     * Regola il gap preferito
     * <p/>
     * Sovrascrive per agire su tutte le pagine del Libro
     */
    public void setGapPreferito(int gapPreferito) {
        for (Pagina pag : this.getPagine()) {
            pag.setGapPreferito(gapPreferito);
        }
    }


    /**
     * Regola il gap massimo
     * <p/>
     * Sovrascrive per agire su tutte le pagine del Libro
     */
    public void setGapMassimo(int gapMassimo) {
        for (Pagina pag : this.getPagine()) {
            pag.setGapMassimo(gapMassimo);
        }
    }


    /**
     * Attiva / disattiva il ridimensionamento dei componenti
     * <p/>
     * Sovrascrive per agire su tutte le pagine del Libro
     */
    public void setRidimensionaComponenti(boolean flag) {
        for (Pagina pag : this.getPagine()) {
            pag.setRidimensionaComponenti(flag);
        }
    }


    /**
     * Attiva / disattiva il ridimensionamento parallelo
     * <p/>
     * Sovrascrive per agire su tutte le pagine del Libro
     */
    public void setRidimensionaParallelo(boolean flag) {
        for (Pagina pag : this.getPagine()) {
            pag.setRidimensionaParallelo(flag);
        }
    }


    /**
     * Attiva / disattiva il ridimensionamento perpendicolare
     * <p/>
     * Sovrascrive per agire su tutte le pagine del Libro
     */
    public void setRidimensionaPerpendicolare(boolean flag) {
        for (Pagina pag : this.getPagine()) {
            pag.setRidimensionaPerpendicolare(flag);
        }
    }


    /**
     * Attiva / disattiva la considerazione dei componenti invisibili
     * <p/>
     * Sovrascrive per agire su tutte le pagine del Libro
     */
    public void setConsideraComponentiInvisibili(boolean flag) {
        for (Pagina pag : this.getPagine()) {
            pag.setConsideraComponentiInvisibili(flag);
        }
    }


    private Pagina.Margine getMargine() {
        return margine;
    }


    /**
     * Regola il margine di tutte le pagine.
     * <p/>
     *
     * @param margine da utilizzare
     */
    public void setMargine(Pagina.Margine margine) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Pagina> pagine;

        try { // prova ad eseguire il codice

            /* regola la variabile */
            this.margine = margine;

            /* se le pagine sono disponibili, regola tutte le pagine */
            pagine = this.getPagine();
            if (pagine != null) {
                for (Pagina pag : pagine) {
                    if (pag != null) {
                        pag.setMargine(margine);
                    }// fine del blocco if
                }
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Avanza ad un campo specifico.
     * <p/>
     * Regola il fuoco <br>
     *
     * @param nomeCampo interessato
     */
    public void vaiCampo(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice
            campo = this.getCampo(nomeCampo);
            this.vaiCampo(campo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Avanza ad un campo specifico.
     * <p/>
     * Regola il fuoco <br>
     *
     * @param campo interessato
     */
    public void vaiCampo(Campo campo) {

        try { // prova ad eseguire il codice
            if (campo != null) {
                campo.grabFocus();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * posiziona il fuoco al primo campo del form.
     * <p/>
     */
    public void vaiCampoPrimo() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        ArrayList<Campo> listaCampi;

        try {    // prova ad eseguire il codice
            listaCampi = this.getCampiPannello();
            if (listaCampi.size() > 0) {
                campo = listaCampi.get(0);
                this.vaiCampo(campo);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Avanza al primo campo successivo vuoto dopo quello specificato.
     * <p/>
     *
     * @param campo dopo il quale andare al successivo vuoto
     */
    protected void vaiCampoSuccessivoVuoto(Campo campo) {
        /* variabili e costanti locali di lavoro */
        boolean trovato = false;

        try { // prova ad eseguire il codice

            for (Campo unCampo : this.getCampi().values()) {
                if (unCampo.equals(campo)) {
                    trovato = true;
                }// fine del blocco if

                if (trovato) {
                    if (!unCampo.equals(campo)) {

                        if (unCampo.isVuoto()) {
                            vaiCampo(unCampo.getNomeInterno());
                            break;
                        }// fine del blocco if
                    }// fine del blocco if

                }// fine del blocco if

            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Controlla se i campi del form sono stati modificati.
     * </p>
     * Aggiorna la memoria coi valori provenienti dai componenti GUI <br>
     * Aggiorna i componenti GUI coi valori provenienti dalla memoria <br>
     * Questo doppio passaggio in avanti ed indietro dei valori si rende
     * necessario perche': <ul>
     * <li> alcuni campi basano il loro valore sui valori della memoria di
     * altri campi (campi calcolati) </li>
     * <li> alcuni campi basano il valore di alcune componenti GUI accessorie
     * sul proprio valore in memoria (decoratori Legenda e Estratto) </li>
     * </ul>
     *
     * @return modificati true se anche uno solo campo e' stato modificato <br>
     */
    public boolean isModificata() {
        /* variabili e costanti locali di lavoro */
        boolean modificati = false;
        LinkedHashMap<String, Campo> campi;

        try { // prova ad eseguire il codice
            /* traversa tutta la collezione */
            campi = this.getCampi();
            for (Campo campo : campi.values()) {
                if (campo.getCampoDati().isModificato()) {
                    modificati = true;
                    break;
                } /* fine del blocco if */
            }
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return modificati;
    }


    /**
     * Determina se il form e' registrabile.
     * <p/>
     * Sovrascritto dalle sottoclassi.
     * Nel caso di Scheda, se la scheda e' registrabile
     * Nel caso di dialogo, se il dialogo e' confermabile o registrabile
     *
     * @return true se registrabile / confermabile
     */
    public boolean isRegistrabile() {
        return false;
    }


    /**
     * Determina se il form e' cancellabile.
     * <p/>
     *
     * @return true se cancellabile
     */
    public boolean isCancellabile() {
        return this.isModificata();
    }


    /**
     * Notifies all listeners that have registered interest
     * for notification on this event type.
     * The event instance is lazily created.
     *
     * @see FormEve
     * @deprecated
     */
    public void fire() {
    }


    /**
     * Recupera il modulo di riferimento di questo contenitore di campi.
     * <br>
     *
     * @return il modulo di riferimento
     */
    public Modulo getModulo() {
        return modulo;
    }


    protected void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }


    /**
     * Ritorna la collezione di campi del form.
     * <p/>
     *
     * @return la collezione di campi del form
     */
    public LinkedHashMap<String, Campo> getCampi() {
        return campi;
    }


    /**
     * Ritorna una lista dei campi fisici della scheda.
     * <p/>
     *
     * @return la lista dei campi fisici contenuti nella scheda
     */
    public ArrayList<Campo> getCampiFisici() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campiFisici = null;

        try { // prova ad eseguire il codice
            campiFisici = new ArrayList<Campo>();
            for (Campo campo : this.getCampi().values()) {
                if (campo.getCampoDB().isCampoFisico()) {
                    campiFisici.add(campo);
                }// fine del blocco if
            }
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campiFisici;
    }


    /**
     * Ritorna l'elenco dei campi modificati.
     * <p/>
     *
     * @return l'elenco dei campi modificati
     */
    public ArrayList<Campo> getCampiModificati() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campiMod = new ArrayList<Campo>();
        LinkedHashMap<String, Campo> campi;

        try {    // prova ad eseguire il codice
            /* traversa tutta la collezione */
            campi = this.getCampi();
            for (Campo campo : campi.values()) {
                if (campo.getCampoDati().isModificato()) {
                    campiMod.add(campo);
                } /* fine del blocco if */
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campiMod;
    }


    /**
     * Ritorna l'elenco dei campi Navigatore del form.
     * <p/>
     *
     * @return l'elenco dei campi Navigatore
     */
    public ArrayList<Campo> getCampiNavigatore() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campiNav = new ArrayList<Campo>();
        Collection<Campo> campi;

        try {    // prova ad eseguire il codice
            campi = this.getCampi().values();
            for (Campo campo : campi) {
                if (campo.getCampoDati() instanceof CDNavigatore) {
                    campiNav.add(campo);
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campiNav;
    }


    protected void setCampi(LinkedHashMap<String, Campo> campi) {
        this.campi = campi;
    }


    public Portale getPortale() {
        return portale;
    }


    public void setPortale(Portale portale) {
        this.portale = portale;
    }


    /**
     * Ritorna il Libro contenuto nel form
     * <p/>
     *
     * @return il Libro contenuto nel form
     */
    public Libro getLibro() {
        return libro;
    }


    protected void setLibro(Libro libro) {
        this.libro = libro;
    }


    protected boolean isInizializzato() {
        return inizializzato;
    }


    protected void setInizializzato(boolean inizializzato) {
        this.inizializzato = inizializzato;
    }


    public EventListenerList getListaListener() {
        return listaListener;
    }


    public void setListaListener(EventListenerList listaListener) {
        this.listaListener = listaListener;
    }


    private boolean isStatoModificato() {
        return statoModificato;
    }


    private void setStatoModificato(boolean statoModificato) {
        this.statoModificato = statoModificato;
    }


    /**
     * flag - indica che il form è "attivo", cioè sta
     * visualizzando/modificando un record valido.
     * viene acceso al termine dell'avvio e spento alla
     * chiusura (dialogo) o dismissione (scheda)
     * <p/>
     *
     * @return true se il form è attivo
     */
    public boolean isAttivo() {
        return attivo;
    }


    protected void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }


    /**
     * Metodo eseguito per qualunque evento generato nel campo.
     * <p/>
     *
     * @param campo che ha generato l'evento
     */
    protected void eventoCampo(Campo campo) {
    }


    /**
     * Metodo eseguito quando lo stato backup/memoria di un campo cambia.
     * <p/>
     * Stato inteso come uguaglianza/differenza tra backup e memoria <br>
     * Sovrascritto dalle sottoclassi
     *
     * @param campo che ha generato l'evento
     */
    protected void eventoStatoModificato(Campo campo) {
    }


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @param campo che ha generato l'evento
     */
    protected void eventoMemoriaModificata(Campo campo) {
    }


    /**
     * Metodo eseguito quando un campo riceve il fuoco.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param campo interessato
     */
    protected void eventoEntrataCampo(Campo campo) {
    }


    /**
     * Metodo eseguito quando un campo perde il fuoco.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param campo interessato
     */
    protected void eventoUscitaCampo(Campo campo) {
    }


    /**
     * Metodo eseguito quando un campo modificato perde il fuoco.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param campo interessato
     */
    protected void eventoUscitaCampoModificato(Campo campo) {
    }


    /**
     * Metodo eseguito quando il bottone associato al campo viene premuto.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param campo interessato
     */
    protected void eventoCampoBottonePremuto(Campo campo) {
    }


    /**
     * Metodo eseguito dopo che il campo ha presentato un record.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param campo interessato
     */
    protected void eventoCampoPresentatoRecord(Campo campo) {
    }


    /**
     * Controlla se un campo appartiene ad una lista di campi.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param campoBase di riferimento
     * @param campi array di campi da controllare
     *
     * @return vero se il campo base è uguale
     *         ad uno dei campi dell'array
     */
    protected boolean isCampo(Campo campoBase, Campi... campi) {
        /* variabili e costanti locali di lavoro */
        boolean appartiene = false;
        int num;
        Campo campo;
        String nome;

        try { // prova ad eseguire il codice
//            /* numero di campi ricevuti da controllare */
//            num = campi.length;
//
//            for (int k = 0; k < num; k++) {
//                campo = this.getCampo(campi[k].get());
//                if (campoBase.equals(campo)) {
//                    appartiene = true;
//                    break;
//                }// fine del blocco if
//            } // fine del ciclo for

            String[] lista = new String[campi.length];
            for (int k = 0; k < campi.length; k++) {
                nome = campi[k].get();
                lista[k] = nome;
            } // fine del ciclo for

            appartiene = this.isCampo(campoBase, lista);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return appartiene;
    }


    /**
     * Controlla se un campo appartiene ad una lista di campi.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param campoBase di riferimento
     * @param nomi lista di campi da controllare
     *
     * @return vero se il campo base è uguale
     *         ad uno dei campi dell'array
     */
    protected boolean isCampo(Campo campoBase, String... nomi) {
        /* variabili e costanti locali di lavoro */
        boolean appartiene = false;
        int num;
        Campo campo;

        try { // prova ad eseguire il codice

            /* numero di campi ricevuti da controllare */
            num = nomi.length;

            for (int k = 0; k < num; k++) {
                campo = this.getCampo(nomi[k]);
                if (campoBase.equals(campo)) {
                    appartiene = true;
                    break;
                }// fine del blocco if
            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return appartiene;
    }


    /**
     * Azione generica per ogni evento del campo
     */
    private class AzioneCampo extends CampoBaseAz {

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
        public void campoAz(CampoBaseEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                if (isAttivo()) {
                    eventoCampo(unEvento.getCampo());
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * azione di cambio stato (backup-memoria) del campo
     */
    private class AzioneStatoCampo extends CampoStatoAz {

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
        public void campoStatoAz(CampoStatoEve unEvento) {
            /* variabili e costanti locali di lavoro */
            boolean prima;
            boolean adesso;

            try { // prova ad eseguire il codice
                if (isAttivo()) {
                    /* invoca il metodo delegato della classe gestione eventi */
                    eventoStatoModificato(unEvento.getCampo());

                    /*
                     * regola lo stato generale del form
                     * se cambia rilancia l'evento di stato modificato per il form
                     */
                    prima = isStatoModificato();
                    adesso = isModificata();
                    if (prima != adesso) {
                        setStatoModificato(adesso);
                        fire(FormBase.Evento.formStato);
                    }// fine del blocco if
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Azione bottone del campo premuto
     */
    private class AzioneBottoneCampo extends BottoneAz {

        /**
         * bottoneAz, da BottoneLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void bottoneAz(BottoneEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                if (isAttivo()) {
                    eventoCampoBottonePremuto(unEvento.getCampo());
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Azione bottone del campo premuto
     */
    private class AzionePresentatoRecord extends CampoPresentatoRecordAz {

        /**
         * bottoneAz, da BottoneLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void presentatoRecordAz(CampoPresentatoRecordEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                if (isAttivo()) {

                    /* invoca il metodo delegato */
                    eventoCampoPresentatoRecord(unEvento.getCampo());

                    /* sincronizza il form */
                    sincronizza();

                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Aggiunge un listener.
     * <p/>
     * Aggiunge uno specifico listener <br>
     * L'oggetto mantiene una propria collezione di listener <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    public void addListener(BaseListener listener) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            Evento.addLocale(listaListener, listener);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge un listener.
     * <p/>
     * Aggiunge uno specifico listener <br>
     * L'oggetto mantiene una propria collezione di listener <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    public void addListener(Eventi evento, BaseListener listener) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            evento.add(listaListener, listener);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Rimuove un listener.
     * <p/>
     * Rimuove lo specifico listener <br>
     * L'oggetto mantiene una propria collezione di listener <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    public void removeListener(BaseListener listener) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            Evento.removeLocale(listaListener, listener);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Avvisa tutti i listener.
     * <p/>
     * Avvisa tutti i listener che si sono registrati
     * per questo tipo di evento <br>
     * L'evento viene creato al momento <br>
     * E' responsabilità della classe invocare questo metodo quando
     * si creano le condizioni per generare l'evento <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    public void fire(Eventi unEvento) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            Lib.Eventi.fire(listaListener, unEvento, FormBase.class, this);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Azione di cambio valore memoria del campo.
     */
    private class AzioneMemoriaCampo extends CampoMemoriaAz {

        /**
         * CampoMemoriaAz, da CampoListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void campoMemoriaAz(CampoMemoriaEve unEvento) {
            try { // prova ad eseguire il codice

                /* l'azione viene eseguita solo se il form è attivo */
                if (isAttivo()) {

                    /* invoca il metodo delegato della classe gestione eventi */
                    eventoMemoriaModificata(unEvento.getCampo());

                    /* sincronizza il form */
                    sincronizza();

                    /* lancia un evento di form modificato */
                    fire(FormBase.Evento.formModificato);

                }// fine del blocco if


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Azione di fuoco ricevuto dal campo.
     */
    private class AzionePrendeFuocoCampo extends CampoPrendeFuocoAz {

        /**
         * eseguito quando il campo prende il fuoco
         * </p>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void prendeFuocoAz(CampoPrendeFuocoEve unEvento) {
            if (isAttivo()) {
                eventoEntrataCampo(unEvento.getCampo());
            }// fine del blocco if
        }
    } // fine della classe interna


    /**
     * Azione di fuoco perso dal campo.
     */
    private class AzionePerdeFuocoCampo extends CampoPerdeFuocoAz {

        /**
         * eseguito quando il campo perde il fuoco
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void perdeFuocoAz(CampoPerdeFuocoEve unEvento) {
            if (isAttivo()) {
                eventoUscitaCampo(unEvento.getCampo());
            }// fine del blocco if
        }
    } // fine della classe interna


    /**
     * Azione di fuoco perso dal campo modificato.
     */
    private class AzioneModificaCampo extends CampoModificatoAz {

        /**
         * eseguito quando il campo modificato perde il fuoco
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void campoModificatoAz(CampoModificatoEve unEvento) {
            if (isAttivo()) {
                eventoUscitaCampoModificato(unEvento.getCampo());
            }// fine del blocco if
        }
    } // fine della classe interna


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Eventi che vengono lanciati dal modulo <br>
     * Per ogni evento: <ul>
     * <li> classe interfaccia </li>
     * <li> classe evento </li>
     * <li> classe azione </li>
     * <li> metodo azione </li>
     * </ul>
     */
    public enum Evento implements Eventi {

        form(FormLis.class, FormEve.class, FormAz.class, "formAz"),
        formModificato(FormModificatoLis.class,
                FormModificatoEve.class,
                FormModificatoAz.class,
                "formModificatoAz"),
        formStato(FormStatoLis.class, FormStatoEve.class, FormStatoAz.class, "formStatoAz");


        /**
         * interfaccia listener per l'evento
         */
        private Class listener;

        /**
         * classe evento
         */
        private Class evento;

        /**
         * classe azione
         */
        private Class azione;

        /**
         * metodo
         */
        private String metodo;


        /**
         * Costruttore completo con parametri.
         *
         * @param listener interfaccia
         * @param evento classe
         * @param azione classe
         * @param metodo nome metodo azione
         */
        Evento(Class listener, Class evento, Class azione, String metodo) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setListener(listener);
                this.setEvento(evento);
                this.setAzione(azione);
                this.setMetodo(metodo);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Aggiunge un listener alla lista.
         * <p/>
         * Metodo statico <br>
         * Serve per utilizzare questa Enumeration <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista degli eventi a cui aggiungersi
         * @param listener dell'evento da lanciare
         */
        public static void addLocale(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.add(Evento.values(), lista, listener);
        }


        /**
         * Rimuove un listener dalla lista.
         * <p/>
         * Metodo statico <br>
         * Serve per utilizzare questa Enumeration <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista degli eventi da cui rimuoverlo
         * @param listener dell'evento da non lanciare
         */
        public static void removeLocale(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.remove(Evento.values(), lista, listener);
        }


        /**
         * Aggiunge un listener alla lista.
         * <p/>
         * Serve per utilizzare la Enumeration della sottoclasse <br>
         * Metodo (sovra)scritto nelle Enumeration specifiche
         * (le Enumeration delle sottoclassi della classe dove
         * e' questa Enumeration) <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista degli eventi a cui aggiungersi
         * @param listener dell'evento da lanciare
         */
        public void add(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.add(Evento.values(), lista, listener);
        }


        /**
         * Rimuove un listener dalla lista.
         * <p/>
         * Serve per utilizzare la Enumeration della sottoclasse <br>
         * Metodo (sovra)scritto nelle Enumeration specifiche
         * (le Enumeration delle sottoclassi della classe dove
         * e' questa Enumeration) <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista degli eventi da cui rimuoverlo
         * @param listener dell'evento da non lanciare
         */
        public void remove(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.remove(Evento.values(), lista, listener);
        }


        public Class getListener() {
            return listener;
        }


        private void setListener(Class listener) {
            this.listener = listener;
        }


        public Class getEvento() {
            return evento;
        }


        private void setEvento(Class evento) {
            this.evento = evento;
        }


        public Class getAzione() {
            return azione;
        }


        private void setAzione(Class azione) {
            this.azione = azione;
        }


        public String getMetodo() {
            return metodo;
        }


        private void setMetodo(String metodo) {
            this.metodo = metodo;
        }


    }// fine della classe

}// fine della classe
