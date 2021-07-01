/**
 * Title:     StampaListaCampi
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      2-lug-2007
 */
package it.algos.base.stampa;

import com.wildcrest.j2printerworks.J2Printer;
import com.wildcrest.j2printerworks.J2TablePrinter;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.lista.TavolaModello;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.tavola.Tavola;
import it.algos.base.vista.Vista;

import java.util.ArrayList;

/**
 * Oggetto delegato alla stampa di un elenco di campi.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 25-giu-2007
 */
public class StampaLista implements Runnable {

    /**
     * modulo di riferimento
     */
    private Modulo modulo;

    /**
     * vista contenente i campi da stampare
     */
    private Vista vista;

    /**
     * filtro per selezionare i record da stampare
     */
    private Filtro filtro;

    /**
     * ordine di stampa dei record
     */
    private Ordine ordine;

    /**
     * Eventuale titolo della stampa
     */
    private String titolo;

    /**
     * Dati da stampare
     */
    private Dati dati;

    /**
     * Connessione da utilizzare per il recupero dei dati
     * Se non specificato utilizza quella del Modulo
     */
    private Connessione connessione;

    /**
     * Flag che viene spento prima di presentare il dialogo di stampa
     * e viene acceso se questo viene annullato
     */
    private boolean canceled;

    /**
     * Flag - per usare righe ad altezza variabile
     * todo Attenzione! per ora non funziona bene!!
     */
    private boolean usaAltezzaRigaVariabile;


    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento
     * @param vista contenente i campi da stampare
     * @param filtro per selezionare i record da stampare
     * @param ordine di stampa dei record
     */
    public StampaLista(Modulo modulo, Vista vista, Filtro filtro, Ordine ordine) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setModulo(modulo);
        this.setVista(vista);
        this.setFiltro(filtro);
        this.setOrdine(ordine);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Vista vista;

        try { // prova ad eseguire il codice

            /* usa le righe ad altezza variabile */
            this.setUsaAltezzaRigaVariabile(true);

            /* inizializza la vista, in modo da clonare i campi e da permettere
             * all'utente di regolarli prima di eseguire la stampa  */
            vista = this.getVista();
            if (!vista.isInizializzato()) {
                vista.inizializza();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea e registra il Printer.
     * <p/>
     *
     * @return il Printer creato
     */
    private J2Printer creaPrinter() {
        /* variabili e costanti locali di lavoro */
        J2Printer printer = null;
        Tavola tavola;

        try {    // prova ad eseguire il codice

            /* crea la JTable */
            tavola = this.creaTavola();

            /* crea e regola un TablePrinter */
            printer = tavola.getPrinter(this.getTitolo());

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return printer;
    }


    /**
     * Ritorna il TablePrinter.
     * <p/>
     *
     * @return il TablePrinter creato
     */
    public J2TablePrinter getTablePrinter() {
        /* variabili e costanti locali di lavoro */
        J2TablePrinter printer = null;
        Tavola tavola;

        try {    // prova ad eseguire il codice

            /* crea la JTable */
            tavola = this.creaTavola();

            /* crea e regola un TablePrinter */
            printer = tavola.getTablePrinter();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return printer;
    }


    /**
     * Crea e registra l'oggetto dati da stampare.
     * <p/>
     *
     * @return l'oggetto Dati da stampare
     */
    private Dati creaDati() {
        /* variabili e costanti locali di lavoro */
        Vista vista;
        ArrayList<Campo> campi;
        Filtro filtro;
        Ordine ordine;
        Modulo modulo;
        Query query;
        Dati dati = null;

        try {    // prova ad eseguire il codice

            vista = this.getVista();
            campi = vista.getCampiFisici();
            filtro = this.getFiltro();
            ordine = this.getOrdine();

            modulo = this.getModulo();
            query = new QuerySelezione(modulo);
            query.setCampi(campi);
            query.setFiltro(filtro);
            query.setOrdine(ordine);
            dati = modulo.query().querySelezione(query, this.getConnessione());

            /* registra l'oggetto perché poi lo dovrà chiudere */
            this.setDati(dati);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return dati;

    }


    /**
     * Crea la Tavola da stampare completa di dati.
     * <p/>
     *
     * @return la Tavola creata
     */
    private Tavola creaTavola() {
        /* variabili e costanti locali di lavoro */
        Tavola tavola = null;
        Dati dati;
        TavolaModello mod;

        try {    // prova ad eseguire il codice

            /* crea e registra l'oggetto dati da stampare */
            dati = this.creaDati();

            /* crea la Tavola con i dati */
            mod = new TavolaModello(this.getVista());
            mod.setDati(dati);
            tavola = new Tavola(mod);
            tavola.inizializza();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return tavola;
    }


    /**
     * Esegue la stampa
     * <p/>
     */
    public void run() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        J2Printer printer;
        Dati dati;

        try { // prova ad eseguire il codice

            /* crea il Printer */
            printer = this.creaPrinter();
            continua = (printer != null);

            /* spegne il flag di stampa annullata */
            if (continua) {
                this.setCanceled(false);
            }// fine del blocco if

            /* mostra il dialogo di page setup */
            if (continua) {
                printer.showPageSetupDialog();
                continua = !this.isCanceled();
            }// fine del blocco if

//            /* forza il layout della JTable in modo che chiami il renderer
//            * di ogni riga, il quale regola l'altezza della riga */
//            if (continua) {
//                table = this.getTable();
//                table.doLayout();
//            }// fine del blocco if

            /* esegue la stampa */
            if (continua) {
                printer.print();
            }// fine del blocco if

            /* chiude i dati */
            dati = this.getDati();
            if (dati != null) {
                dati.close();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    private Modulo getModulo() {
        return modulo;
    }


    private void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }


    private Vista getVista() {
        return vista;
    }


    private void setVista(Vista vista) {
        this.vista = vista;
    }


    private Filtro getFiltro() {
        return filtro;
    }


    private void setFiltro(Filtro filtro) {
        this.filtro = filtro;
    }


    private Ordine getOrdine() {
        return ordine;
    }


    private void setOrdine(Ordine ordine) {
        this.ordine = ordine;
    }


    private String getTitolo() {
        return titolo;
    }


    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }


    private Dati getDati() {
        return dati;
    }


    private void setDati(Dati dati) {
        this.dati = dati;
    }


    private boolean isCanceled() {
        return canceled;
    }


    private void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }


    /**
     * Ritorna la connessione da utilizzare.
     * <p/>
     * Se non assegnata, usa quella del modulo
     *
     * @return la connessione
     */
    private Connessione getConnessione() {
        /* variabili e costanti locali di lavoro */
        Connessione conn = null;

        try { // prova ad eseguire il codice
            conn = this.connessione;
            if (conn == null) {
                conn = this.getModulo().getConnessione();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return conn;
    }


    public void setConnessione(Connessione connessione) {
        this.connessione = connessione;
    }


    private boolean isUsaAltezzaRigaVariabile() {
        return usaAltezzaRigaVariabile;
    }


    /**
     * Flag - per usare righe ad altezza variabile
     * <p/>
     * todo Attenzione! per ora non funziona bene!!
     *
     * @param flag per attivare la stampa delle righe ad altezza variabile
     */
    public void setUsaAltezzaRigaVariabile(boolean flag) {
        this.usaAltezzaRigaVariabile = flag;
    }


}
