/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 16-5-2007
 */

package it.algos.gestione.task;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.importExport.Esporta;
import it.algos.base.importExport.ExportSettings;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibFile;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.ordine.Ordine;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * Atask - Contenitore dei riferimenti agli oggetti del package.
 * <p/>
 * Questa classe concreta: <ul>
 * <li> Contiene tutti i riferimenti agli oggetti delle classi che servono
 * per gestire una parte del programma </li>
 * <li> Regola il riferimento al Modello specifico (obbligatorio) </li>
 * <li> Regola i titoli di Menu e Finestra del Navigatore </li>
 * <li> Regola eventualmente alcuni aspetti specifici del Navigatore </li>
 * <li> Crea altri eventuali <strong>Moduli</strong> indispensabili per il
 * funzionamento di questo modulo </li>
 * <li> Rende visibili nel Menu gli altri moduli </li>
 * <li> Regola eventuali funzionalit&agrave; specifiche del Navigatore </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 16-5-2007
 */
public class GTaskModulo extends ModuloBase implements GTask {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = GTask.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = GTask.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = GTask.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public GTaskModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(NOME_PROGRAMMA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public GTaskModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(NOME_CHIAVE, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* selezione del modello (obbligatorio) */
        super.setModello(new GTaskModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

    }


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Anche eventuale creazione <br>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Regola il Navigatore di default <br>
     * Crea altri Navigatori (oltre a quello standard) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override
    protected void creaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try { // prova ad eseguire il codice

            super.creaNavigatori();

            this.getNavigatoreDefault().addSchedaCorrente(new GTaskScheda(this));

            /* Navigatore per i Task nella scheda dell'Evento */
            nav = new GTaskNavigatore(this);
            this.addNavigatore(nav, GTask.Nav.eventi.toString());


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Esporta un elenco di task.
     * <p/>
     *
     * @param filtro per selezionare i record da esportare
     * @param campi elenco dei campi da esportare
     * @param conn connessione da utilizzare
     */
    protected void exportTask(ArrayList<Campo> campi, Filtro filtro, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        ExportSettings settings;
        Esporta esporta;
        Ordine ordine;
        File file;

        try {    // prova ad eseguire il codice

            ordine = new Ordine();
            ordine.add(Cam.dataUtile.get());

            settings = new ExportSettings(this);
            settings.setCampi(campi);
            settings.setUsaTitoliColonna(true);
            settings.setModoTitoli(ExportSettings.ModoTitoli.linkSoloModulo);
            settings.setFiltro(filtro);
            settings.setOrdine(ordine);
            settings.setConnessione(conn);

            file = LibFile.creaFile("File di export");
            if (file != null) {
                settings.setPath(file.getPath());
                esporta = new Esporta(settings);
                esporta.run();
                new MessaggioAvviso("Terminato.");
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }

//    /**
//     * Ritorna la data utile per un task.
//     * <p/>
//     * Si basa sulla data di inizio evento e sui giorni specificati
//     * @param codTask il codice del task
//     * @param giorni il numero di giorni prima
//     * @return la data utile
//     */
//    public Date getDataUtile(int codTask, int giorni) {
//        /* variabili e costanti locali di lavoro */
//        Date dataUtile=null;
//        Date dataEvento;
//        Modulo modEvento;
//        int codEvento;
//
//        try {    // prova ad eseguire il codice
//            codEvento = this.query().valoreInt(Cam.evento.get(), codTask);
//            modEvento = AeventoModulo.get();
//            dataEvento = modEvento.query().valoreData(Aevento.Cam.dataInizio.get(), codEvento);
//            dataUtile = this.getDataUtile(dataEvento, giorni);
//        } catch (Exception unErrore) {    // intercetta l'errore
//            new Errore(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return dataUtile;
//    }


    /**
     * Ritorna la data utile per un task.
     * <p/>
     * Si basa sulla data di inizio evento e sui giorni prima specificati
     *
     * @param dataInizioEvento di riferimento (data dell'evento)
     * @param giorniPrima il numero di giorni prima
     *
     * @return la data utile
     */
    public Date getDataUtile(Date dataInizioEvento, int giorniPrima) {
        /* variabili e costanti locali di lavoro */
        Date dataUtile = null;

        try {    // prova ad eseguire il codice
            dataUtile = Lib.Data.add(dataInizioEvento, -giorniPrima);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return dataUtile;
    }


    /**
     * Ritorna la data di inizio suggerita per un task.
     * <p/>
     * Si basa sulla data utile del task e sui giorni di lavorazione specificati
     *
     * @param dataUtileEvento data utile del task
     * @param giorniLavorazione il numero di giorni di lavorazione
     *
     * @return la data di inizio lavorazione
     */
    public Date getDataInizio(Date dataUtileEvento, int giorniLavorazione) {
        /* variabili e costanti locali di lavoro */
        Date data = null;

        try {    // prova ad eseguire il codice
            data = Lib.Data.add(dataUtileEvento, -giorniLavorazione);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }

//    /**
//     * Ricalcola le date di tutti i task di un evento.
//     * <p/>
//     * @param codEvento dell'evento
//     * @param dataEvento la data dell'evento
//     * @param conn la connessione da utilizzare
//     */
//    public void ricalcDateTask(int codEvento, Date dataEvento, Connessione conn) {
//        /* variabili e costanti locali di lavoro */
//        Filtro filtro;
//        Query query;
//        Dati dati;
//        Campo campoChiave;
//        Campo campoggPrima;
//        Campo campoggLavo;
//        int codice;
//        int ggPrima;
//        int ggLavo;
//        Date dataUtile;
//        Date dataInizio;
//        ArrayList<CampoValore> listaCV;
//        CampoValore cv;
//
//        try {    // prova ad eseguire il codice
//
//            campoChiave =  this.getCampoChiave();
//            campoggPrima =  this.getCampo(Cam.giorni.get());
//            campoggLavo =  this.getCampo(Cam.giornilavorazione.get());
//
//            filtro = FiltroFactory.crea(Cam.evento.get(), codEvento);
//            query = new QuerySelezione(this);
//            query.addCampo(campoChiave);
//            query.addCampo(campoggPrima);
//            query.addCampo(campoggLavo);
//            query.setFiltro(filtro);
//            dati = this.query().querySelezione(query, conn);
//
//            for (int k = 0; k < dati.getRowCount(); k++) {
//
//                codice = dati.getIntAt(k, campoChiave);
//                ggPrima = dati.getIntAt(k, campoggPrima);
//                ggLavo = dati.getIntAt(k, campoggLavo);
//
//                dataUtile = this.getDataUtile(dataEvento, ggPrima);
//                dataInizio = this.getDataInizio(dataUtile, ggLavo);
//
//                listaCV = new ArrayList<CampoValore>();
//                cv = new CampoValore(Cam.dataUtile.get(), dataUtile);
//                listaCV.add(cv);
//                cv = new CampoValore(Cam.dataInizio.get(), dataInizio);
//                listaCV.add(cv);
//                this.query().registraRecordValori(codice, listaCV, conn);
//
//            } // fine del ciclo for
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            new Errore(unErrore);
//        } // fine del blocco try-catch
//
//    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static GTaskModulo get() {
        return (GTaskModulo)ModuloBase.get(NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new GTaskModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

} // fine della classe
