/**
 * Title:     DialogoStatistiche
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      27-lug-2007
 */
package it.algos.albergo.ristorante.menu;

import com.wildcrest.j2printerworks.J2ComponentPrinter;
import com.wildcrest.j2printerworks.J2FlowPrinter;
import com.wildcrest.j2printerworks.J2Printer;
import com.wildcrest.j2printerworks.J2TablePrinter;
import com.wildcrest.j2printerworks.VerticalGap;
import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.categoria.Categoria;
import it.algos.albergo.ristorante.categoria.CategoriaModulo;
import it.algos.albergo.ristorante.piatto.Piatto;
import it.algos.albergo.ristorante.piatto.PiattoModulo;
import it.algos.albergo.ristorante.righemenuordini.RMO;
import it.algos.albergo.ristorante.righemenuordini.RMOModulo;
import it.algos.albergo.ristorante.righemenupiatto.RMP;
import it.algos.albergo.ristorante.righemenupiatto.RMPModulo;
import it.algos.albergo.ristorante.righemenutavolo.RMT;
import it.algos.albergo.ristorante.righemenutavolo.RMTModulo;
import it.algos.albergo.ristorante.righetavoloordini.RTOModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.dati.Dati;
import it.algos.base.dialogo.DialogoBase;
import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloMemoria;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.NavigatoreL;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.portale.Portale;
import it.algos.base.progetto.Progetto;
import it.algos.base.progressbar.OperazioneMonitorabile;
import it.algos.base.progressbar.ProgressBar;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;
import it.algos.base.wrapper.CampoValore;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

/**
 * Dialogo statistiche gradimento piatti.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 27-lug-2007 ore 16.16.06
 */
public final class DialogoStatistiche extends DialogoBase {

    /* nomi dei campi del dialogo */
    private static final String nomeDataIni = "dal";

    private static final String nomeDataFine = "al";

    /* Chiave per recuperare il Navigatore Risultati dal modulo Risultati */
    private static final String CHIAVE_NAV = "navRisultati";

    /* Titolo del dialogo e della stampa */
    private static final String TITOLO_DIALOGO = "Analisi ordinazioni e gradimento piatti";

    /* bottone esegui calcolo */
    private JButton bottoneEsegui;

    /* modulo con db in memoria per l'immagazzinamento dei risultati */
    private static ModuloRisultati moduloRisultati;

    /* nome della vista del modulo Risultati da utilizzare nel Navigatore Risultati */
    public static final String VISTA_RISULTATI = "vistaRisultati";


    /* Pannello contenente il Navigatore del Modulo Memoria */
    private Pannello panNavigatore;

    /* Progress bar della operazione */
    private ProgressBar progressBar;

    /* Pannello per la visualizzazione dei coperti */
    private PanCoperti panCoperti;


    /**
     * Costruttore completo con parametri
     * <p/>
     */
    public DialogoStatistiche() {
        /* rimanda al costruttore della superclasse */
        super();

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

            /* questo dialogo non e' modale */
            this.getDialogo().setModal(false);

            this.setTitolo(TITOLO_DIALOGO);
            this.addBottoneStampa();
            this.addBottoneChiudi();

            /* crea il modulo con db in memoria */
            this.creaModuloMem();

            /* crea i pannelli del dialogo */
            this.creaPannelli();

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
        Navigatore nav;
        Portale portale;

        super.inizializza();

        try { // prova ad eseguire il codice

            Modulo modulo = getModuloRisultati();
            modulo.inizializza();
//            Campo campo = modulo.getCampoChiave();
//            campo.setVisibileVistaDefault(false);
//            campo.setPresenteScheda(false);
//            Vista vista = modulo.getVistaDefault();
//            vista.getElement
//            Campo campo = vista.getCampo(modulo.getCampoChiave());



            /* aggiunge il Portale Navigatore al pannello placeholder */
            nav = this.getNavigatore();
            portale = nav.getPortaleNavigatore();
            this.getPanNavigatore().add(portale);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     */
    public void avvia() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice
            getModuloRisultati().avvia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        super.avvia();

    }// fine del metodo avvia


    /**
     * Crea, registra i pannelli principali e li aggiunge al dialogo.
     * <p/>
     */
    private void creaPannelli() {
        /* variabili e costanti locali di lavoro */
        Pannello pan;

        try {    // prova ad eseguire il codice

            /* pannello date */
            pan = this.creaPanDate();
            this.addPannello(pan);

            /* pannello coperti */
            PanCoperti panCoperti = new PanCoperti();
            this.setPanCoperti(panCoperti);
            this.addPannello(panCoperti);


            /* pannello placeholder per il Navigatore risultati */
            /* il navigatore vi viene inserito in fase di inizializzazione */
            pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            this.setPanNavigatore(pan);
            this.addPannello(pan);


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Pannello di impostazione delle date di analisi.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanDate() {
        /* variabili e costanti locali di lavoro */
        Pannello panDate = null;
        Campo campoDataInizio;
        Campo campoDataFine;
        JButton bottone;
        ProgressBar pb;

        try {    // prova ad eseguire il codice

            /* pannello date */
            campoDataInizio = CampoFactory.data(DialogoStatistiche.nomeDataIni);
            campoDataInizio.decora().eliminaEtichetta();
            campoDataInizio.decora().etichettaSinistra("dal");
            campoDataInizio.decora().obbligatorio();
            campoDataFine = CampoFactory.data(DialogoStatistiche.nomeDataFine);
            campoDataFine.decora().eliminaEtichetta();
            campoDataFine.decora().etichettaSinistra("al");
            campoDataFine.decora().obbligatorio();

            /* bottone esegui */
            bottone = new JButton("Esegui");
            bottone.setOpaque(false);
            this.setBottoneEsegui(bottone);
            bottone.addActionListener(new DialogoStatistiche.AzioneEsegui());
            bottone.setFocusPainted(false);

            /* progress bar */
            pb = new ProgressBar();
            this.setProgressBar(pb);

            panDate = PannelloFactory.orizzontale(this.getModulo());
            panDate.setAllineamento(Layout.ALLINEA_CENTRO);
            panDate.creaBordo("Periodo di analisi");
            panDate.add(campoDataInizio);
            panDate.add(campoDataFine);
            panDate.add(bottone);
            panDate.add(Box.createHorizontalGlue());
            panDate.add(pb);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return panDate;
    }





    /**
     * Crea e registra il modulo Memoria per i risultati.
     * <p/>
     */
    private void creaModuloMem() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campi = new ArrayList<Campo>();
        ModuloRisultati modulo;
        Campo campo;

        try {    // prova ad eseguire il codice

            campo = CampoFactory.intero(Campi.Ris.codicePiatto.getNome());
            campi.add(campo);

            campo = CampoFactory.testo(Campi.Ris.nomePiatto.getNome());
            campo.setVisibileVistaDefault();
            campo.setTitoloColonna("piatto");
            campo.setToolTipLista("nome del piatto");
            campo.decora()
                    .etichetta("nome del piatto");  // le etichette servono per il dialogo ricerca
            campo.setLarghezza(250);
            campi.add(campo);

            campo = CampoFactory.testo(Campi.Ris.categoria.getNome());
            campo.setVisibileVistaDefault();
            campo.setTitoloColonna("categoria");
            campo.setToolTipLista("categoria del piatto");
            campo.setLarghezza(80);
            campi.add(campo);

            campo = CampoFactory.intero(Campi.Ris.quanteVolte.getNome());
            campo.setVisibileVistaDefault();
            campo.setLarghezza(80);
            campo.setTitoloColonna("quante volte");
            campo.setToolTipLista(
                    "quante volte questo piatto è stato offerto nel periodo analizzato");
            campo.decora().etichetta("quante volte");
            campi.add(campo);

            campo = CampoFactory.intero(Campi.Ris.quantiCoperti.getNome());
            campo.setVisibileVistaDefault();
            campo.setTitoloColonna("coperti");
            campo.setToolTipLista("numero di coperti che avrebbero potuto ordinare");
            campo.decora().etichetta("n. coperti");
            campo.setLarghezza(80);
            campi.add(campo);

            campo = CampoFactory.intero(Campi.Ris.quanteComande.getNome());
            campo.setVisibileVistaDefault();
            campo.setTitoloColonna("comande");
            campo.setToolTipLista("numero di comande effettive");
            campo.decora().etichetta("n. comande");
            campo.setLarghezza(80);
            campo.setTotalizzabile(true);
            campi.add(campo);

            campo = CampoFactory.percentuale(Campi.Ris.gradimento.getNome());
            campo.setVisibileVistaDefault();
            campo.setTitoloColonna("gradimento");
            campo.setToolTipLista(
                    "percentuale di gradimento (è il 100% se tutti i coperti potenziali lo hanno ordinato)");
            campo.decora().etichetta("% gradimento");
            campo.setLarghezza(80);
            campi.add(campo);

            modulo = new ModuloRisultati(campi);
            setModuloRisultati(modulo);


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna il Navigatore risultati.
     * <p/>
     *
     * @return il Navigatore risultati
     */
    private Navigatore getNavigatore() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;
        Modulo modulo;

        try {    // prova ad eseguire il codice
            modulo = getModuloRisultati();
            if (modulo != null) {
                nav = modulo.getNavigatore(CHIAVE_NAV);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


    /**
     * Ritorna un filtro per selezionare tutti i menu nel periodo di analisi.
     * <p/>
     *
     * @return il filtro
     */
    private Filtro getFiltroMenu() {
        /* variabili e costanti locali di lavoro */
        Filtro fdata1;
        Filtro fdata2;
        Filtro filtroData = null;
        Modulo modMenu;

        try {    // prova ad eseguire il codice

            modMenu = MenuModulo.get();
            fdata1 = FiltroFactory.crea(modMenu.getCampo(Menu.Cam.data.get()),
                    Filtro.Op.MAGGIORE_UGUALE,
                    getDataInizio());

            fdata2 = FiltroFactory.crea(modMenu.getCampo(Menu.Cam.data.get()),
                    Filtro.Op.MINORE_UGUALE,
                    getDataFine());
            filtroData = new Filtro();
            filtroData.add(fdata1);
            filtroData.add(fdata2);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtroData;
    }


    /**
     * Ritorna un filtro per selezionare i piatti comandabili.
     * <p/>
     *
     * @return il filtro
     */
    private Filtro getFiltroPiattiComandabili() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Modulo modPiatto = PiattoModulo.get();

        try {    // prova ad eseguire il codice

            filtro = FiltroFactory.crea(modPiatto.getCampo(Piatto.CAMPO_COMANDA), true);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Cerca di chiudere il dialogo.
     * <p/>
     *
     * @return true se il dialogo è stato effettivamente chiuso
     */
    protected boolean chiudiDialogo() {
        /* variabili e costanti locali di lavoro */
        Modulo modulo;

        try { // prova ad eseguire il codice

            /* alla chiusura del dialogo chiude il modulo memoria */
            /* (chiude la connessione e spegne il database) */
            modulo = getModuloRisultati();
            if (modulo != null) {
                modulo.chiude();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return super.chiudiDialogo();

    }


    /**
     * Metodo invocato dal pulsante Esegui.
     * <p/>
     * Lancia un processo con progress bar che effettua l'analisi dei dati
     */
    private void esegui() {
        /* variabili e costanti locali di lavoro */
        OperazioneMonitorabile operazione;

        try {    // prova ad eseguire il codice

            /**
             * Lancia l'operazione di analisi dei dati in un thread asincrono
             * al termine il thread aggiorna i dati del dialogo
             */
            operazione = new OpAnalisi(this.getProgressBar());
            operazione.avvia();



        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Recupera un oggetto Dati con le RMP nel periodo di analisi.
     * <p/>
     * @return oggetto Dati RMP
     */
    private Dati getDatiRMP() {
        /* variabili e costanti locali di lavoro */
        Dati dati = null;
        Modulo modRMP = RMPModulo.get();
        Modulo modPiatto = PiattoModulo.get();
        Modulo modCategoria = CategoriaModulo.get();
        Query query;
        Filtro filtro;

        try {    // prova ad eseguire il codice

            query = new QuerySelezione(modRMP);
            query.addCampo(modRMP.getCampoChiave());
            query.addCampo(modRMP.getCampo(RMP.CAMPO_PIATTO));
            query.addCampo(modRMP.getCampo(RMP.CAMPO_MENU));
            query.addCampo(modPiatto.getCampo(Piatto.CAMPO_NOME_ITALIANO));
            query.addCampo(modCategoria.getCampo(Categoria.CAMPO_SIGLA));
            filtro = new Filtro();
            filtro.add(this.getFiltroMenu());
            filtro.add(this.getFiltroPiattiComandabili());
            query.setFiltro(filtro);
            dati = modRMP.query().querySelezione(query);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return dati;
    }


    /**
     * Aggiunge un nuovo piatto al modulo Risultati.
     * <p/>
     * Il piatto viene aggiunto solo se non già esistente
     *
     * @param codPiatto il codice del piatto
     * @param nomePiatto il nome del piatto
     * @param catPiatto la stringa categoria del piatto
     *
     * @return il codice della riga dei risultati relativa al piatto,
     *         -1 se non trovato e non aggiunto
     */
    private int addPiatto(int codPiatto, String nomePiatto, String catPiatto) {
        /* variabili e costanti locali di lavoro */
        int codRiga = -1;
        ModuloRisultati modRisultati = getModuloRisultati();
        ArrayList<CampoValore> valori;
        CampoValore cv;

        try {    // prova ad eseguire il codice

            /* cerca il piatto nei risultati */
            codRiga = modRisultati.query().valoreChiave(Campi.Ris.codicePiatto.getNome(),
                    codPiatto);

            /* se non trovato lo aggiunge ora */
            if (codRiga <= 0) {

                valori = new ArrayList<CampoValore>();

                cv = new CampoValore(Campi.Ris.codicePiatto.getNome(), codPiatto);
                valori.add(cv);
                cv = new CampoValore(Campi.Ris.nomePiatto.getNome(), nomePiatto);
                valori.add(cv);
                cv = new CampoValore(Campi.Ris.categoria.getNome(), catPiatto);
                valori.add(cv);

                codRiga = modRisultati.query().nuovoRecord(valori);

            }// fine del blocco if-else


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codRiga;

    }


    /**
     * Incrementa un campo di un record del modulo Risultati di una quantità data.
     * <p/>
     *
     * @param codice del record da incrementare
     * @param campo numerico da incrementare
     * @param quantita da aggiungere al valore esistente
     */
    private void incrementaCampo(int codice, Campi.Ris campo, int quantita) {
        /* variabili e costanti locali di lavoro */
        ModuloRisultati modRisultati = getModuloRisultati();
        int qtaPrec;
        int qtaCurr;

        try {    // prova ad eseguire il codice
            qtaPrec = modRisultati.query().valoreInt(campo.getCampo(), codice);
            qtaCurr = qtaPrec + quantita;
            modRisultati.query().registraRecordValore(codice, campo.getCampo(), qtaCurr);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Determina il numero di coperti presenti in un menu
     * <p/>
     *
     * @param codMenu da controllare
     *
     * @return il numero di coperti del menu
     */
    private int getQuantiCoperti(int codMenu) {
        /* variabili e costanti locali di lavoro */
        int totCoperti = 0;
        Number numero;
        Modulo modRMT = RMTModulo.get();
        Filtro filtro;

        try {    // prova ad eseguire il codice
            filtro = FiltroFactory.crea(modRMT.getCampo(RMT.Cam.menu), codMenu);
            numero = modRMT.query().somma(modRMT.getCampo(RMT.Cam.coperti), filtro);
            totCoperti = Libreria.getInt(numero);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return totCoperti;
    }


    /**
     * Determina il numero di comande effettuate per una data RMP
     * <p/>
     *
     * @param codRMP da controllare
     *
     * @return il numero di comande effettuate
     */
    private int getQuanteComande(int codRMP) {
        /* variabili e costanti locali di lavoro */
        int totComande = 0;
        Number numero;
        Modulo modRMO = RMOModulo.get();
        Modulo modRTO = RTOModulo.get();
        Filtro filtro;

        try {    // prova ad eseguire il codice
            filtro = FiltroFactory.crea(modRMO.getCampo(RMO.CAMPO_RIGA_MENU_PIATTO), codRMP);
            numero = modRTO.query().contaRecords(filtro);
            totComande = Libreria.getInt(numero);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return totComande;
    }


    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        boolean abilita;

        super.sincronizza();

        try { // prova ad eseguire il codice

            /* abilitazione bottone Esegui */
            abilita = false;
            if (!Lib.Data.isVuota(this.getDataInizio())) {
                if (!Lib.Data.isVuota(this.getDataFine())) {
                    if (Lib.Data.isSequenza(this.getDataInizio(), this.getDataFine())) {
                        abilita = true;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if
            this.getBottoneEsegui().setEnabled(abilita);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    protected void stampaDialogo() {
        J2TablePrinter tablePrinter;
        J2ComponentPrinter titolo;
        JLabel label;
        J2Printer printer;
        J2FlowPrinter flowPrinter;
        String sData1, sData2;

        try { // prova ad eseguire il codice

            sData1 = Lib.Data.getStringa(this.getDataInizio());
            sData2 = Lib.Data.getStringa(this.getDataFine());

            /* costruisce il Printer per il titolo della stampa */
            label = new JLabel(TITOLO_DIALOGO);
            label.setFont(FontFactory.creaPrinterFont(Font.BOLD, 14f));
            titolo = new J2ComponentPrinter(label);
            titolo.setHorizontalAlignment(J2Printer.LEFT);

            /* costruisce il Printer per la tavola */
            tablePrinter = this.getNavigatore().getLista().getPrinterTavolaDati();

            /* crea un FlowPrinter e aggiunge i vari printers */
            flowPrinter = new J2FlowPrinter();
            flowPrinter.addFlowable(new VerticalGap(0.2));
            flowPrinter.addFlowable(titolo);
            flowPrinter.addFlowable(new VerticalGap(0.1));
            flowPrinter.addFlowable(tablePrinter);

            /* crea e stampa il printer finale */
            printer = new J2Printer(Progetto.getPrintLicense());
            printer.setSeparatePrintThread(false);
            printer.setLeftMargin(.5);
            printer.setRightMargin(.5);
            printer.setPageable(flowPrinter);
            printer.setPageImagesMonochrome(true);
            printer.setLeftHeader("");
            printer.setCenterHeader("Periodo di analisi: dal " + sData1 + " al " + sData2);
            printer.setRightHeader("");
            printer.setCenterFooter("");
            printer.setRightFooter("Pagina ### di @@@");
            printer.setHeaderStyle(J2Printer.LINE);
            printer.setFooterStyle(J2Printer.LINE);
            printer.print();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Metodo eseguito quando un campo modificato perde il fuoco.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param campo interessato
     */
    protected void eventoUscitaCampoModificato(Campo campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        try { // prova ad eseguire il codice

            /* quando si modifica il campo data inizio modifica
             * il campo data fine */
            if (campo.equals(this.getCampo(nomeDataIni))) {
                unCampo = this.getCampo(nomeDataFine);
                unCampo.setValore(campo.getValore());
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna la data di inizio impostata nel dialogo.
     * <p/>
     *
     * @return la data di inizio analisi
     */
    private Date getDataInizio() {
        return (Date)this.getCampo(DialogoStatistiche.nomeDataIni).getValore();
    }


    /**
     * Ritorna la data di fine impostata nel dialogo.
     * <p/>
     *
     * @return la data di fine analisi
     */
    private Date getDataFine() {
        return (Date)this.getCampo(DialogoStatistiche.nomeDataFine).getValore();
    }


    private JButton getBottoneEsegui() {
        return bottoneEsegui;
    }


    private void setBottoneEsegui(JButton bottoneEsegui) {
        this.bottoneEsegui = bottoneEsegui;
    }


    private static ModuloRisultati getModuloRisultati() {
        return moduloRisultati;
    }


    private static void setModuloRisultati(ModuloRisultati modulo) {
        moduloRisultati = modulo;
    }


    private Pannello getPanNavigatore() {
        return panNavigatore;
    }


    private void setPanNavigatore(Pannello panNavigatore) {
        this.panNavigatore = panNavigatore;
    }


    private ProgressBar getProgressBar() {
        return progressBar;
    }


    private void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    private PanCoperti getPanCoperti() {
        return panCoperti;
    }

    private void setPanCoperti(PanCoperti panCoperti) {
        this.panCoperti = panCoperti;
    }

    /**
     * Pannello specializzato per la visualizzazione dei coperti
     * </p>
     */
    private class PanCoperti extends PannelloFlusso {

        private Campo campoPranzo;
        private Campo campoCena;
        private Campo campoTotale;

        /**
         * Costruttore completo con parametri. <br>
         */
        public PanCoperti() {
            /* rimanda al costruttore della superclasse */
            super(Layout.ORIENTAMENTO_ORIZZONTALE);


            try { // prova ad eseguire il codice

                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();

            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }// fine del metodo costruttore completo

        /**
         * Regolazioni immediate di riferimenti e variabili. <br>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            this.setAllineamento(Layout.ALLINEA_CENTRO);
            this.creaBordo("Coperti serviti");

            campoPranzo=CampoFactory.intero("a pranzo");
            campoPranzo.setLarghezza(60);
            campoPranzo.setModificabile(false);
            campoCena=CampoFactory.intero("a cena");
            campoCena.setLarghezza(60);
            campoCena.setModificabile(false);
            campoTotale=CampoFactory.intero("Totale");
            campoTotale.setLarghezza(60);                        
            campoTotale.setModificabile(false);

            this.add(campoPranzo);
            this.add(campoCena);
            this.add(campoTotale);
        }

        /**
         * Assegna il valore al campo n. coperti a pranzo.
         * <p/>
         * @param quanti numero di coperti
         */
        public void setNumCopertiPranzo(int quanti) {
            campoPranzo.setValore(quanti);
            this.syncNumCopertiTotali();
        }

        /**
         * Assegna il valore al campo n. coperti a cena.
         * <p/>
         * @param quanti numero di coperti
         */
        public void setNumCopertiCena(int quanti) {
            campoCena.setValore(quanti);
            this.syncNumCopertiTotali();            
        }

        /**
         * Sincronizza il valore del campo n. coperti totali.
         * <p/>
         */
        private void syncNumCopertiTotali() {
            int qPranzo = campoPranzo.getInt();
            int qCena = campoCena.getInt();
            campoTotale.setValore(qPranzo+qCena);
        }




    }


    /**
     * Azione per eseguire il ricalcolo del riepilogo
     * </p>
     */
    private final class AzioneEsegui implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            esegui();
        }
    } // fine della classe 'interna'


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Nomi dei campi del modulo Risultati
     */
    private enum Campi {

        ;


        /**
         * Nomi dei campi del modulo Risultati
         */
        private enum Ris {

            codicePiatto("codicePiatto"),
            nomePiatto("nomePiatto"),
            categoria("categoria"),
            quanteVolte("quanteVolte"),
            quantiCoperti("quantiCoperti"),
            quanteComande("quanteComande"),
            gradimento("gradimento"),;

            private String nome;


            /**
             * Costruttore completo senza parametri.<br>
             */
            Ris(String nome) {
                this.nome = nome;
            }// fine del metodo costruttore completo


            /**
             * Ritorna il nome del campo
             * <p/>
             *
             * @return il nome
             */
            public String getNome() {
                return this.nome;
            }


            /**
             * Ritorna il campo dal modulo
             * <p/>
             *
             * @return il campo
             */
            public Campo getCampo() {
                return getModuloRisultati().getCampo(this.getNome());
            }


        }// fine della classe


    }// fine della classe


    /**
     * Modulo interno con database in memoria.
     * <p/>
     */
    private final class ModuloRisultati extends ModuloMemoria {


        /**
         * Costruttore completo <br>
         *
         * @param campi campi specifici del modulo (oltre ai campi standard)
         */
        public ModuloRisultati(ArrayList<Campo> campi) {

            super("risultati", campi);

            /**
             * regolazioni iniziali di riferimenti e variabili
             */
            try { // prova ad eseguire il codice
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }/* fine del blocco try-catch */

        }/* fine del metodo costruttore completo */


        /**
         * Regolazioni immediate di riferimenti e variabili. <br>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            try { // prova ad eseguire il codice
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

        @Override
        public boolean prepara() {

            boolean riuscito;

            riuscito = super.prepara();

            return riuscito;

        }

        /**
         * Inizializza il Modulo
         * </p>
         *
         * @return true se il modulo e' stato inizializzato
         */
        public boolean inizializza() {

            boolean riuscito;

            riuscito = super.inizializza();

            return riuscito;

        } // fine del metodo

        /**
         * Avvia il Modulo
         * <p/>
         * Avvia il Navigatore Risultati
         */
        public void avvia() {
            super.avvia();
            this.getNavigatore(CHIAVE_NAV).avvia();
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
        protected void creaNavigatori() {
            /* variabili e costanti locali di lavoro */
            Navigatore nav;

            try { // prova ad eseguire il codice
                nav = new NavigatoreRisultati(this);
                this.addNavigatore(nav);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Navigatore per i risultati
         * </p>
         */
        public final class NavigatoreRisultati extends NavigatoreL {

            /**
             * Costruttore completo con parametri. <br>
             *
             * @param unModulo modulo di riferimento
             */
            public NavigatoreRisultati(Modulo unModulo) {
                /* rimanda al costruttore della superclasse */
                super(unModulo);

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
                this.setNomeChiave(CHIAVE_NAV);
                this.setUsaNuovo(false);
                this.setUsaElimina(false);
                this.setUsaRicerca(true);
                this.setUsaSelezione(true);
                this.setUsaStampaLista(false);
                this.setUsaProietta(false);
                this.setAggiornamentoTotaliContinuo(true);
            }

            @Override
            public void inizializza() {
                
//                Vista vista = this.getModulo().getVista(VISTA_RISULTATI);
//                this.setVista(vista);

                super.inizializza();
            }
        } // fine della classe 'interna'


    } // fine della classe 'interna'


    /**
     * Calcolo statistiche ristorante
     * </p>
     * Monitorata da ProgressBar
     */
    private final class OpAnalisi extends OperazioneMonitorabile {

        private Dati datiRMP;

        private int quanti;

        private int max;


        /**
         * Costruttore completo con parametri. <br>
         *
         * @param pb la progress bar
         */
        public OpAnalisi(ProgressBar pb) {
            /* rimanda al costruttore della superclasse */
            super(pb);

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
            Dati dati;

            try { // prova ad eseguire il codice

                this.setMessaggio("Analisi in corso");
                this.setBreakAbilitato(true);

                /* recupera e registra i dati delle RMP da elaborare */
                dati = getDatiRMP();
                this.setDati(dati);

                /* regola il fondo scala dell'operazione */
                this.setMax(dati.getRowCount());

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        public void start() {
            /* variabili e costanti locali di lavoro */
            boolean continua = true;
            Dati datiRMP;
            int codPiatto;
            int codRiga;
            int quantiCoperti;
            int quanteComande;
            String descPiatto;
            String catPiatto;
            Modulo modRMP = RMPModulo.get();
            Modulo modPiatto = PiattoModulo.get();
            Modulo modCategoria = CategoriaModulo.get();
            ModuloRisultati modRisultati = getModuloRisultati();
            Campo chiaveRMP;
            int codRMP;
            int codMenu;
            int[] codici;
            double qtaOff;
            double qtaCom;
            double gradimento = 0.0;
            PanCoperti panCoperti;

            try {    // prova ad eseguire il codice

                /* azzera il numero di coperti serviti */
                panCoperti = getPanCoperti();
                panCoperti.setNumCopertiPranzo(0);
                panCoperti.setNumCopertiCena(0);

                /* svuota i dati del modulo risultati */
                getModuloRisultati().query().eliminaRecords();

                datiRMP = this.getDati();
                chiaveRMP = modRMP.getCampoChiave();

                /* spazzola le RMP trovate */
                for (int k = 0; k < datiRMP.getRowCount(); k++) {

                    this.quanti++;

                    codRMP = datiRMP.getIntAt(k, chiaveRMP);
                    codPiatto = datiRMP.getIntAt(k, modRMP.getCampo(RMP.CAMPO_PIATTO));
                    descPiatto = datiRMP.getStringAt(k,
                            modPiatto.getCampo(Piatto.CAMPO_NOME_ITALIANO));
                    catPiatto = datiRMP.getStringAt(k,
                            modCategoria.getCampo(Categoria.CAMPO_SIGLA));

                    codRiga = addPiatto(codPiatto, descPiatto, catPiatto);

                    if (codRiga <= 0) {
                        continua = false;
                        break;
                    }// fine del blocco if

                    /* incrementa di 1 il numero di volte in cui il piatto è stato proposto
                     * per questa riga risultati */
                    incrementaCampo(codRiga, Campi.Ris.quanteVolte, 1);

                    /* determina il numero dei coperti presenti nel menu al
                     * quale questa RMP appartiene */
                    codMenu = datiRMP.getIntAt(k, modRMP.getCampo(RMP.CAMPO_MENU));
                    quantiCoperti = getQuantiCoperti(codMenu);

                    /* incrementa il numero di coperti potenziali per questa riga risultati */
                    incrementaCampo(codRiga, Campi.Ris.quantiCoperti, quantiCoperti);

                    /* determina il numero di comande effettuate
                     * per questa RMP */
                    quanteComande = getQuanteComande(codRMP);

                    /* incrementa il numero di comande effettuate per questa riga risultati */
                    incrementaCampo(codRiga, Campi.Ris.quanteComande, quanteComande);

                    /* interruzione nella superclasse */
                    if (super.isInterrompi()) {
                        continua = false;
                        break;
                    }// fine del blocco if

                } // fine del ciclo for

                /* spazzola le righe dei risultati per regolare il gradimento */
                if (continua) {
                    codici = modRisultati.query().valoriChiave();
                    for (int k = 0; k < codici.length; k++) {
                        codRiga = codici[k];
                        gradimento = 0.0;
                        qtaOff = modRisultati.query()
                                .valoreDouble(Campi.Ris.quantiCoperti.getCampo(), codRiga);
                        qtaCom = modRisultati.query()
                                .valoreDouble(Campi.Ris.quanteComande.getCampo(), codRiga);
                        if (qtaOff != 0) {
                            gradimento = qtaCom / qtaOff;
                            gradimento = Lib.Mat.arrotonda(gradimento, 4);
                        }// fine del blocco if
                        modRisultati.query().registraRecordValore(codRiga,
                                Campi.Ris.gradimento.getCampo(),
                                gradimento);
                    } // fine del ciclo for
                }// fine del blocco if

                datiRMP.close();

                getNavigatore().aggiornaLista();

                /* aggiorna il numero di coperti serviti */
                Filtro filtro;
                Number numero;
                Modulo moduloRMT = RMTModulo.get();
                Filtro filtroMenu = getFiltroMenu();
                Filtro filtroPranzo = FiltroFactory.crea(MenuModulo.get().getCampo(Menu.Cam.pasto), Ristorante.COD_DB_PRANZO);
                Filtro filtroCena = FiltroFactory.crea(MenuModulo.get().getCampo(Menu.Cam.pasto), Ristorante.COD_DB_CENA);
                filtro = new Filtro();
                filtro.add(filtroMenu);
                filtro.add(filtroPranzo);
                numero = moduloRMT.query().somma(RMT.Cam.coperti.get(), filtro);
                panCoperti.setNumCopertiPranzo(Libreria.getInt(numero));
                filtro = new Filtro();
                filtro.add(filtroMenu);
                filtro.add(filtroCena);
                numero = moduloRMT.query().somma(RMT.Cam.coperti.get(), filtro);
                panCoperti.setNumCopertiCena(Libreria.getInt(numero));

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        public int getMax() {
            return max;
        }


        private void setMax(int max) {
            this.max = max;
        }


        public int getCurrent() {
            return quanti;
        }


        private Dati getDati() {
            return datiRMP;
        }


        private void setDati(Dati dati) {
            this.datiRMP = dati;
        }


    } // fine della classe 'interna'


}// fine della classe
