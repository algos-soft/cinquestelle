/**
 * Title:     PannelloExport
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      2-3-2006
 */
package it.algos.base.importExport;

import it.algos.base.albero.Albero;
import it.algos.base.albero.AlberoCampi;
import it.algos.base.albero.AlberoModello;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.componente.bottone.Bottone;
import it.algos.base.componente.bottone.BottoneBase;
import it.algos.base.componente.bottone.BottoneDialogo;
import it.algos.base.costante.CostanteColore;
import it.algos.base.costante.CostanteFont;
import it.algos.base.dialogo.DialogoBase;
import it.algos.base.draganddrop.handlers.JListTransferHandler;
import it.algos.base.draganddrop.handlers.JTreeTransferHandler;
import it.algos.base.errore.Errore;
import it.algos.base.evento.albero.AlberoSelModAz;
import it.algos.base.evento.albero.AlberoSelModEve;
import it.algos.base.evento.listasingola.ListaDatiModAz;
import it.algos.base.evento.listasingola.ListaDatiModEve;
import it.algos.base.evento.listasingola.ListaSelModAz;
import it.algos.base.evento.listasingola.ListaSelModEve;
import it.algos.base.font.FontFactory;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibFile;
import it.algos.base.libreria.LibFont;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.listasingola.ListaSingola;
import it.algos.base.listasingola.ListaSingolaModello;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.portale.PortaleStandard;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.ordine.Ordine;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 * Dialogo per la preparazione dell'export.
 * </p>
 * Questa classe: <ul>
 * <li> E' un tipo di Dialogo </li>
 * <li> Visualizza un albero a sinistra e una lista a destra </li>
 * <li> La lista di destra e' ordinabile </li>
 * <li> Il pannello principale è diviso orizzontalmente in tre:
 * albero, comandi e lista </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 2-3-2006
 */
public final class DialogoExport extends DialogoBase {

    /**
     * true per attivare il debug (colora i vari pannelli)
     */
    private static final boolean DEBUG = false;

    /**
     * Colore di primo piano dei campi fissi in albero e lista
     */
    private static final Color COLORE_CAMPI_FISSI = Color.GRAY;

    /**
     * Font per i nomi dei campi in lista e albero
     */
    private static final Font FONT_CAMPI = CostanteFont.FONT_STANDARD;

    /**
     * Altezza fissa delle righe per lista e albero
     */
    private static final int ALTEZZA_RIGA = LibFont.getAltezzaFont(FONT_CAMPI) + 2;

    /**
     * Testo e chiave per il bottone Esporta
     */
    private static final String TESTO_BOTTONE_ESPORTA = "Esporta";

    /**
     * Modulo dal quale parte la gerarchia di export
     */
    private Modulo moduloRoot;

    /**
     * Albero con i campi nel portale di sinistra
     */
    private AlberoCampi albero;

    /**
     * Lista nel portale di destra
     */
    private ListaSingola lista;

    /**
     * campo combo lista moduli
     */
    private Campo campoModuli;

    /**
     * campo check nascondi campi fissi
     */
    private Campo campoChkFissi;

    /**
     * campo sorgente dei dati da esportare
     */
    private Campo campoSorgente;

    /**
     * campo titoli colonna
     */
    private Campo campoTitoliColonna;

    /**
     * campo numeri di di riga
     */
    private Campo campoNumeriRiga;

    /**
     * campo formato
     */
    private Campo campoFormato;

    /**
     * campo che mantiene il percorso del file di uscita
     */
    private Campo campoFile;

    /**
     * bottoni per lo spostamento dei campi tra albero e lista
     */
    private JButton botMoveCampi, botMoveAllCampi, botRemoveCampi, botRemoveAllCampi;

    /**
     * label informativa su quanti records si esportano
     */
    private JLabel infoRecords;

    /**
     * barra di progresso della esportazione
     */
    private JProgressBar progressBar;

    /**
     * oggetto esportatore dei dati
     */
    private Esporta esportatore;

    /**
     * timer per interrogare l'esportatore e acquisirne lo stato
     */
    private Timer timer;


    private ExportSettings settings;


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param moduloRoot il modulo dal quale parte la gerarchia di export
     */
    public DialogoExport(Modulo moduloRoot) {
        this(new ExportSettings(moduloRoot));
    }// fine del metodo costruttore


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param setting impostazioni dell'export
     */
    public DialogoExport(ExportSettings setting) {

        super();

        this.settings = setting;

        try { // prova ad eseguire il codice

            this.inizia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Pannello pan;
        JProgressBar pb;

        try { // prova ad eseguire il codice

            /* questo dialogo e' ridimensionabile */
            this.setRidimensionabile(true);

            /* aggiunge il bottone chiudi (che dismette il dialogo) */
            this.addBottoneChiudi();

            /* aggiunge il bottone esporta (che non dismette il dialogo) */
            BottoneDialogo b = this.addBottone(TESTO_BOTTONE_ESPORTA, false, false);
            b.addActionListener(new AzioneEsporta());

            this.setTitolo("Esportazione dati");
            this.setMessaggio("Trasporta i campi da esportare nella lista di destra");

            /* crea il pannello Moduli e lo aggiunge */
            pan = this.creaPanModuli();
            this.addComponente(pan.getPanFisso());

            /* crea il pannello Contenuti completo e lo aggiunge */
            pan = this.creaPanContenuti();
            this.addComponente(pan.getPanFisso());

            /* crea il pannello comandi inferiore e lo aggiunge */
            pan = this.creaPanComandiGenerali();
            this.addComponente(pan.getPanFisso());

            /* crea il pannello comandi registra/carica impostazioni e lo aggiunge */
            pan = this.creaPanComandiImpostazioni();
            this.addComponenteComando(pan.getPanFisso());

            /* crea la progress bar e la aggiunge al pannello comandi */
            pb = new JProgressBar();
            this.setProgressBar(pb);
            pb.setMaximumSize(new Dimension(150, 20));
            pb.setValue(0);
            pb.setStringPainted(true);
            this.addComponenteComando(pb);


            if (DEBUG) {
                pan.setOpaque(true);
                pan.setBackground(Color.PINK);
            }// fine del blocco if

            this.regolaModelloAlbero();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alle impostazioni
     * correnti delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {

        try { // prova ad eseguire il codice

            /* invoca il metodo sovrascritto della superclasse */
            super.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * .
     * <p/>
     */
    public void avvia() {

        try { // prova ad eseguire il codice

            /* inizializza il dialogo (se non già inizializzato)*/
            if (!this.isInizializzato()) {
                this.inizializza();
            }// fine del blocco if

            if (this.settings != null) {
                this.usaSetting(settings);
            }// fine del blocco if

            /* rimuove eventualmente i campi fissi dall'albero se l'opzione è attiva */
            if (this.isNascondiCampiFissi()) {
                this.rimuoviCampiFissi();
            }// fine del blocco if

            /* regola il testo informativo sul numero di records */
            this.regolaInfoRecords();

            super.avvia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sincronizzazione della scheda/dialogo.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     * Sincronizza la status bar se esiste <br>
     */
    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        boolean abilita;
        int[] quanteSelezionate;
        int quante;
        AlberoModello modAlbero;
        ListaSingolaModello modLista;

        super.sincronizza();

        try { // prova ad eseguire il codice

            /* controllo abilitazione bottone Aggiungi Campi */
            quante = this.getAlbero().getTree().getSelectionCount();
            botMoveCampi.setEnabled(false);
            if (quante > 0) {
                botMoveCampi.setEnabled(true);
            }// fine del blocco if

            /* controllo abilitazione bottone Rimuovi Campi */
            quanteSelezionate = this.getLista().getLista().getSelectedIndices();
            botRemoveCampi.setEnabled(false);
            if (quanteSelezionate.length > 0) {
                botRemoveCampi.setEnabled(true);
            }// fine del blocco if

            /* controllo abilitazione bottone Aggiungi tutti i Campi */
            botMoveAllCampi.setEnabled(false);
            modAlbero = this.getModelloAlbero();
            if (modAlbero != null) {
                quante = modAlbero.getSize();
                if (quante > 0) {
                    botMoveAllCampi.setEnabled(true);
                }// fine del blocco if
            }// fine del blocco if

            /* controllo abilitazione bottone Rimuovi tutti i Campi */
            botRemoveAllCampi.setEnabled(false);
            modLista = this.getModelloLista();
            if (modLista != null) {
                quante = modLista.getSize();
                if (quante > 0) {
                    botRemoveAllCampi.setEnabled(true);
                }// fine del blocco if
            }// fine del blocco if

            /* controllo abilitazione bottone Esporta */
            abilita = this.isEsportabile();
            this.getBottoneEsporta().setEnabled(abilita);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea il pannello Moduli completo.
     * <p/>
     * E'il pannello di selezione del Modulo
     *
     * @return il pannello creato
     */
    private Pannello creaPanModuli() {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso pan = null;
        ArrayList<Modulo> moduli;
        Campo campo;

        try {    // prova ad eseguire il codice

            /* crea il pannello */
            pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(0);

            /* crea il campo combo e lo aggiunge */
            campo = CampoFactory.comboInterno("moduli");
            this.setCampoModuli(campo);
            campo.decora().eliminaEtichetta();
            campo.decora().etichettaSinistra("Modulo");
            moduli = Progetto.getListaModuli();
            campo.setUsaNonSpecificato(false);
            campo.setValoriInterni(moduli);
            campo.setValore(1);
            this.addCampo(campo);
            pan.add(campo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello Contenuti completo.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanContenuti() {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso pc = null;
        Pannello portaleAlbero;
        Pannello panComandi;
        Pannello portaleLista;

        try {    // prova ad eseguire il codice

            /* crea il pannello per la sezione contenuti */
            pc = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            pc.setUsaGapFisso(true);
            pc.setGapPreferito(0);

            /* crea il portale con albero e comandi e lo aggiunge */
            portaleAlbero = this.creaPortaleAlbero();
            pc.add(portaleAlbero);

            /* crea il pannello comandi centrale vuoto */
            panComandi = this.creaPanComandiCentro();
            pc.add(panComandi);

            /* crea il portale con lista e comandi e lo aggiunge */
            portaleLista = this.creaPortaleLista();
            if (DEBUG) {
                portaleLista.setOpaque(true);
                portaleLista.setBackground(Color.CYAN);
            }// fine del blocco if
            pc.add(portaleLista);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pc;
    }


    /**
     * Crea il pannello comandi generali.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanComandiGenerali() {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso pannello = null;
        Pannello pan;

        try {    // prova ad eseguire il codice

            /* crea il pannello */
            pannello = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            pannello.setUsaGapFisso(true);
            pannello.setGapPreferito(10);
            pannello.setRidimensionaComponenti(true);

            /* crea il pannello comandi setup e lo aggiunge */
            pan = this.creaPanComandiSetup();
            pannello.add(pan);

            /* crea il pannello comandi file e lo aggiunge */
            pan = this.creaPanComandiFile();
            pannello.add(pan);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pannello;
    }


    /**
     * Crea il pannello comandi setup.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanComandiSetup() {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso pannello = null;
        Pannello pan;

        try {    // prova ad eseguire il codice

            /* crea il pannello */
            pannello = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            pannello.setUsaGapFisso(true);
            pannello.setGapPreferito(10);
            pannello.setRidimensionaComponenti(true);

            /* crea il pannello dati sorgenti e lo aggiunge */
            pan = this.creaPanSorgenti();
            pannello.add(pan);

            /* crea il pannello header e lo aggiunge */
            pan = this.creaPanHeader();
            pannello.add(pan);

            /* crea il pannello formato e lo aggiunge */
            pan = this.creaPanFormato();
            pannello.add(pan);


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pannello;
    }


    /**
     * Crea il pannello comandi file.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanComandiFile() {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso pannello = null;
        Pannello pan;

        try {    // prova ad eseguire il codice

            /* crea il pannello */
            pannello = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            pannello.setUsaGapFisso(true);
            pannello.setGapPreferito(10);
            pannello.setRidimensionaComponenti(true);

            /* crea il pannello file e lo aggiunge */
            pan = this.creaPanFile();
            pannello.add(pan);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pannello;
    }


    /**
     * Crea il pannello comandi impostazioni.
     * <p/>
     * Registra / carica impostazioni
     *
     * @return il pannello creato
     */
    private Pannello creaPanComandiImpostazioni() {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso pannello = null;
        JButton bot;
        Dimension minBot = new Dimension(50, 20);

        try {    // prova ad eseguire il codice

            /* crea il pannello */
            pannello = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            pannello.setUsaGapFisso(true);
            pannello.setGapPreferito(10);
            pannello.setRidimensionaComponenti(true);

            /* crea i bottoni e li aggiunge */
            bot = new BottoneBase("Registra impostazioni...");
            bot.addActionListener(new AzRegistraImpostazioni());
            bot.setFont(FontFactory.creaScreenFont(9f));
            bot.setMinimumSize(minBot);
            pannello.add(bot);

            bot = new BottoneBase("Carica impostazioni...");
            bot.addActionListener(new AzCaricaImpostazioni());
            bot.setFont(FontFactory.creaScreenFont(9f));
            bot.setMinimumSize(minBot);
            pannello.add(bot);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pannello;
    }


    /**
     * Crea il pannello per i dati sorgente.
     * <p/>
     *
     * @return il pannello dati sorgente
     */
    private Pannello creaPanSorgenti() {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso pan = null;
        Campo campo;
        ArrayList<ImportExport.Sorgente> valori;
        JLabel label;

        try {    // prova ad eseguire il codice

            pan = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            pan.setGapPreferito(10);
            pan.setGapMassimo(15);
            pan.creaBordo("Records");

            valori = new ArrayList<ImportExport.Sorgente>();
            valori.add(ImportExport.Sorgente.tutti);
            valori.add(ImportExport.Sorgente.selezione);

            campo = CampoFactory.radioInterno("sorgenti");
            this.setCampoSorgente(campo);
            campo.setUsaNonSpecificato(false);
//            campo.getCampoDati().setNonSpecificatoIniziale(true);
//            campo.getCampoDati().setUsaSeparatore(true);
            campo.setValoriInterni(valori);
            campo.decora().eliminaEtichetta();
            this.addCampo(campo);
            pan.add(campo);

            label = new JLabel();
            infoRecords = label;
            label.setFont(CostanteFont.FONT_STANDARD_BOLD);
            label.setForeground(CostanteColore.ROSSO);
            this.setInfoRecordsText("records non disponibili");
            pan.add(label);

//            /* todo test */
//            Campo campo2 = CampoFactory.comboLink("linkato");
//            campo2.setNomeModuloLinkato(TestTabella.NOME_MODULO);
//            campo2.setNomeCampoValoriLinkato(TestTabella.CAMPO_SIGLA);
//            this.addCampo(campo2);
//            pan.add(campo2);
//            /* todo end test */

            /* dopo aver aggiunto i componenti, sblocco l'altezza */
            pan.sbloccaAltMax();


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello header.
     * <p/>
     *
     * @return il pannello header
     */
    private Pannello creaPanHeader() {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso pan = null;
        Campo campo;

        try {    // prova ad eseguire il codice

            pan = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            pan.setGapPreferito(10);
            pan.setGapMassimo(15);
            pan.creaBordo("Header");

            campo = CampoFactory.checkBox("titoli colonna");
            this.setCampoTitoliColonna(campo);
            this.addCampo(campo);
//            pan.add(campo.getPannelloVideo());
            pan.add(campo);

            campo = CampoFactory.checkBox("numeri di riga");
            this.setCampoNumeriRiga(campo);
            this.addCampo(campo);
//            pan.add(campo.getPannelloVideo());
            pan.add(campo);

            /* dopo aver aggiunto i componenti, sblocco l'altezza */
            pan.sbloccaAltMax();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello Formato.
     * <p/>
     *
     * @return il pannello Formato
     */
    private Pannello creaPanFormato() {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso pan = null;
        Campo campo;
        ArrayList<ImportExport.ExportFormats> valori;

        try {    // prova ad eseguire il codice

            pan = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            pan.setGapPreferito(10);
            pan.setGapMassimo(15);
            pan.creaBordo("Formato");

//            valori = new ArrayList<ImportExport.Formato>();

//            /* traverso tutta la collezione */
//            for (ImportExport.Formato form : ImportExport.Formato.values()) {
//                valori.add(form);
//            } // fine del ciclo for-each

            campo = CampoFactory.comboInterno("formato");
            this.setCampoFormato(campo);
            valori = ImportExport.ExportFormats.getElementi();
            campo.setValoriInterni(valori);
            campo.setUsaNonSpecificato(false);
            campo.decora().eliminaEtichetta();
            campo.setLarScheda(100);

            campo.setValore(1); //il primo valore
            this.addCampo(campo);
            pan.add(campo);

            /* questo e' l'ultimo della fila, sblocco anche la larghezza */
            pan.sbloccaDimMax();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello file.
     * <p/>
     *
     * @return il pannello file
     */
    private Pannello creaPanFile() {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso pan = null;
        Campo campo;
        Bottone bot;

        try {    // prova ad eseguire il codice

            pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            pan.setGapPreferito(10);
            pan.setGapMassimo(15);
            pan.setAllineamento(Layout.ALLINEA_CENTRO);
            pan.creaBordo("File destinazione");

            campo = CampoFactory.testo("percorso");
            this.setCampoFile(campo);
            campo.decora().eliminaEtichetta();
            campo.setLarScheda(380);
            this.addCampo(campo);
            campo.setAbilitato(false); // fatto dopo l'inizializzazione
            pan.add(campo);

            /* crea e aggiunge il bottone */
            bot = new BottoneBase("Seleziona...");
            bot.addActionListener(new AzSelezionaFile());
            pan.add(bot);

            /* dopo aver aggiunto i componenti, sblocco la dimensione */
            pan.sbloccaDimMax();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Azione del bottone 'seleziona output file' </p>
     */
    private class AzSelezionaFile implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            selezionaFile();
        }
    } // fine della classe 'interna'


    /**
     * Seleziona o crea un file per l'export.
     * <p/>
     */
    private void selezionaFile() {
        /* variabili e costanti locali di lavoro */
        File file = null;
        String path;
        boolean continua = true;

        try {    // prova ad eseguire il codice

            /* seleziona il file di uscita */
            if (continua) {
                file = LibFile.creaFile();
                continua = (file != null);
            }// fine del blocco if

            if (continua) {
                path = file.getPath();
                this.setFile(path);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Assegna un percorso per il file di uscita.
     * <p/>
     *
     * @param path il percorso
     */
    private void setFile(String path) {
        /* variabili e costanti locali di lavoro */
        Campo campoFile;

        try {    // prova ad eseguire il codice
            campoFile = this.getCampoFile();
            campoFile.setValore(path);
            campoFile.getCampoLogica().memoriaGui();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea il portale con l'albero dei campi esportabili.
     * <p/>
     *
     * @return il portale creato
     */
    private Pannello creaPortaleAlbero() {
        /* variabili e costanti locali di lavoro */
        Pannello pannelloComandi;
        PortaleStandard portale = null;

        try {    // prova ad eseguire il codice

            /* crea l'albero e il pannello comandi */
            this.creaAlbero();
            pannelloComandi = this.creaPanComandiAlbero();

            /* crea un portale standard con l'albero
             * aggiunge il pannello comandi in basso */
            portale = new PortaleStandard(this.getAlbero());
            portale.setTitolo("Campi esportabili");
            portale.setPanComandi(pannelloComandi);

            portale.setPreferredWidth(280);


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return portale;
    }


    /**
     * Crea il portale con la lista dei campi esportati.
     * <p/>
     *
     * @return il pannello creato
     */
    private PortaleStandard creaPortaleLista() {
        /* variabili e costanti locali di lavoro */
        PortaleStandard portale = null;
        ListaSingola lista;
        Pannello pannelloComandi;

        try {    // prova ad eseguire il codice

            /* crea la lista e il suo pannello comandi */
            lista = this.creaLista();
            pannelloComandi = this.creaPanComandiLista();

            /* crea un portale standard con la lista
             * aggiunge il pannello comandi in basso */
            portale = new PortaleStandard(lista);
            portale.setTitolo("Campi da esportare");
            portale.setPanComandi(pannelloComandi);

            /* limito la larghezza massima
             * ridimensionando la finestra e' piu' utile
             * avere spazio per l'albero */
            portale.setPreferredWidth(250);
            portale.setMaximumWidth(250);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return portale;
    }


    /**
     * Crea l'albero relativo alla tabella Root.
     * <p/>
     */
    private void creaAlbero() {
        /* variabili e costanti locali di lavoro */
        AlberoCampi albero;
        JTree tree;

        try {    // prova ad eseguire il codice

            albero = new AlberoCampi();
            this.setAlbero(albero);

            /* aggiunge il listener per la selezione */
            albero.addListener(new AzioneSelezioneAlberoModificata());

            /* attivazione DND */
            tree = albero.getTree();
            tree.setDragEnabled(true);
            tree.setTransferHandler(new AlberoTransferHandler());

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea la lista iniziale vuota.
     * <p/>
     *
     * @return la lista creata
     */
    private ListaSingola creaLista() {
        /* variabili e costanti locali di lavoro */
        ListaSingola lista = null;
        ListaSingolaModello modello;
        RendererRiga renderer;
        JList compLista;

        try {    // prova ad eseguire il codice

            /* crea e registra la lista */
            modello = new ListaSingolaModello();
            lista = new ListaSingola(modello);

            lista.addListener(new AzioneDatiListaModificati());
            lista.addListener(new AzioneSelezioneListaModificata());

            this.setLista(lista);

            compLista = lista.getLista();
            compLista.setFixedCellHeight(ALTEZZA_RIGA);

            compLista.setDragEnabled(true);
            compLista.setTransferHandler(new ListaTransferHandler());

            /* Assegna un renderer personalizzato per
             * disegnare le celle della lista. */
            renderer = new RendererRiga();
            lista.setCellRenderer(renderer);

            if (DEBUG) {
                lista.setOpaque(true);
                lista.setBackground(Color.YELLOW);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Crea il pannello comandi dell'albero.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanComandiAlbero() {
        /* variabili e costanti locali di lavoro */
        PannelloBase pannello = null;
        Campo campo;

        try {    // prova ad eseguire il codice

            /* creo un panello base con BorderLayout */
            pannello = new PannelloBase();
            pannello.setLayout(new BorderLayout());
            pannello.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

            /* creo il campo check */
            campo = CampoFactory.checkBox("Nascondi campi fissi");
            campo.setLarScheda(200);
            this.setCampoChkFissi(campo);
            this.addCampo(campo);

            /* aggiungo il campo al pannello sulla sinistra */
            pannello.add(campo, BorderLayout.LINE_START);

            /* blocco la dimensione del pannello cosi' che non si espanda */
            pannello.bloccaDim();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pannello;
    }


    /**
     * Crea il pannello comandi centrale.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanComandiCentro() {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso pannello = null;
        JToolBar toolbar;
        Icon icona;
        BottoneBase bot;

        try {    // prova ad eseguire il codice

            /* creo una toolbar con i 4 bottoni */
            toolbar = new JToolBar(JToolBar.VERTICAL);
            toolbar.setOpaque(false);
            toolbar.setFloatable(false);

            icona = Lib.Risorse.getIconaBase("freccia_blu_dx_1");
            bot = new BottoneBase(icona);
            bot.addActionListener(new AzMoveCampi());
            botMoveCampi = bot;
            toolbar.add(bot);

            icona = Lib.Risorse.getIconaBase("freccia_blu_dx_2");
            bot = new BottoneBase(icona);
            bot.addActionListener(new AzMoveAllCampi());
            botMoveAllCampi = bot;
            toolbar.add(bot);

            icona = Lib.Risorse.getIconaBase("freccia_red_sx_1");
            bot = new BottoneBase(icona);
            bot.addActionListener(new AzRemoveCampi());
            botRemoveCampi = bot;
            toolbar.add(bot);

            icona = Lib.Risorse.getIconaBase("freccia_red_sx_2");
            bot = new BottoneBase(icona);
            bot.addActionListener(new AzRemoveAllCampi());
            botRemoveAllCampi = bot;
            toolbar.add(bot);

            /* creo un panello base con BorderLayout */
            pannello = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            pannello.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

            /* aggiunge la toolbar al pannello
             * tra due vertical glues in modo
             * che sia centrata */
            pannello.add(Box.createVerticalGlue());
            pannello.add(toolbar);
            pannello.add(Box.createVerticalGlue());

            if (DEBUG) {
                pannello.setOpaque(true);
                pannello.setBackground(Color.BLUE);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pannello;
    }


    /**
     * Azione del bottone 'sposta i campi selezionati' </p>
     */
    private class AzMoveCampi implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            moveCampi();
        }
    } // fine della classe 'interna'


    /**
     * Azione del bottone 'sposta tutti i campi' </p>
     */
    private class AzMoveAllCampi implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            moveAllCampi();
        }
    } // fine della classe 'interna'


    /**
     * Azione del bottone 'rimuove i campi selezionati' </p>
     */
    private class AzRemoveCampi implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            removeCampi();
        }
    } // fine della classe 'interna'


    /**
     * Azione del bottone 'rimuove tutti i campi' </p>
     */
    private class AzRemoveAllCampi implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            removeAllCampi();
        }
    } // fine della classe 'interna'


    /**
     * Aggiunge alla lista i campi selezionati nell'albero.
     * <p/>
     */
    private void moveCampi() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campiDaAlbero;

        try { // prova ad eseguire il codice

            /* recupera la lista dei campi selezionati */
            campiDaAlbero = this.getAlbero().getCampiSelezionati();

            /* aggiunge i campi non gia' presenti in lista */
            for (Campo unCampo : campiDaAlbero) {
                this.addCampoToLista(unCampo);
            }

            /* se un solo nodo era selezionato,
             * seleziona automaticamente il prossimo campo nell'albero */
            if (campiDaAlbero.size() == 1) {
                this.getAlbero().selectNextNode();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Aggiunge alla lista tutti i campi "visibili" dell'albero.
     * <p/>
     * Sono tutti i campi attualmente espansi.
     */
    private void moveAllCampi() {
        /* variabili e costanti locali di lavoro */
        Albero albero;
        JTree tree;

        int quanteRighe;

        TreePath path;
        Object oggetto;
        Campo campo;

        try { // prova ad eseguire il codice

            albero = this.getAlbero();
            tree = albero.getTree();

            /* recupera i path dalle righe "visibili"
             * recupera i campi e li aggiunge */
            quanteRighe = tree.getRowCount();
            for (int k = 0; k < quanteRighe; k++) {
                path = tree.getPathForRow(k);
                if (tree.isVisible(path)) {
                    oggetto = albero.getOggetto(path);
                    if (oggetto != null) {
                        if (oggetto instanceof Campo) {
                            campo = (Campo)oggetto;
                            this.addCampoToLista(campo);
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Rimuove dalla lista tutti i campi selezionati in lista.
     * <p/>
     */
    private void removeCampi() {
        /* variabili e costanti locali di lavoro */
        JList lista;
        ListaSingolaModello modelloLista;
        int indice;
        boolean continua;

        try { // prova ad eseguire il codice

            /* devo fare cosi'; se prendo tutti gli indici selezionati
             * e faccio un ciclo, la cancellazione va in errore perche'
             * intanto gli indici della lista si spostano */
            lista = this.getLista().getLista();
            modelloLista = this.getModelloLista();
            continua = true;
            while (continua) {
                indice = lista.getSelectedIndex();
                if (indice != -1) {
                    modelloLista.remove(indice);
                } else {
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco while

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Rimuove dalla lista tutti i campi.
     * <p/>
     */
    private void removeAllCampi() {
        /* variabili e costanti locali di lavoro */
        ListaSingolaModello modello;

        try { // prova ad eseguire il codice

            /* rimuove tutti i dati della lista */
            modello = this.getModelloLista();
            if (modello != null) {
                this.getModelloLista().removeAllElements();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Aggiunge un campo alla lista se non gia' esistente.
     * <p/>
     *
     * @param campo da aggiungere
     */
    private void addCampoToLista(Campo campo) {

        try { // prova ad eseguire il codice
            if (!this.isEsisteCampo(campo)) {
                this.getModelloLista().addElement(campo);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Controlla se un campo e' gia' esistente nella lista.
     * <p/>
     *
     * @return true se esistente
     */
    private boolean isEsisteCampo(Campo campo) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;

        try { // prova ad eseguire il codice
            if (this.getModelloLista().contains(campo)) {
                esiste = true;
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Crea il pannello comandi della lista.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanComandiLista() {
        /* variabili e costanti locali di lavoro */
        PannelloBase pannello = null;

        try {    // prova ad eseguire il codice

            /* creo un panello base con BorderLayout */
            pannello = new PannelloBase();
            pannello.setLayout(new BorderLayout());
            pannello.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

            /* per adesso fisso l'altezza per allineare con l'albero*/
            pannello.setPreferredHeigth(20);

            /* blocco la dimensione del pannello cosi' che non si espanda */
            pannello.bloccaDim();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pannello;
    }


    /**
     * Rimuove i campi fissi dal modello dell'albero
     * <p/>
     */
    private void rimuoviCampiFissi() {
        this.getAlbero().rimuoviCampiFissi();
    }


    /**
     * Aggiunge i campi fissi al modello dell'albero
     * <p/>
     */
    private void aggiungiCampiFissi() {
        this.getAlbero().aggiungiCampiFissi();
    }


    /**
     * Esegue l'esportazione.
     * <p/>
     * Metodo invocato dal bottone Esporta.
     */
    private void esporta() {
        /* variabili e costanti locali di lavoro */
        ExportSettings setting;
        int quanti = 0;

        try {    // prova ad eseguire il codice

            /* crea un setting dallo stato attuale del dialogo */
            setting = this.creaSetting();

            /* crea un oggetto esportatore regolato con il setting */
            this.setEsportatore(new Esporta(setting));

            /* determina quanti record devono essere esportati */
            quanti = this.getEsportatore().getQuantiTotali();

            /* regola la progress bar */
            this.getProgressBar().setMaximum(quanti);
            this.getProgressBar().setString(null);

            /* disabilita il bottone esporta */
            this.getBottoneEsporta().setEnabled(false);

            /* crea un timer che interroga periodicamente lo stato dell'esportatore */
            this.setTimer(new Timer(100, new ListenerEsportazione()));

            /* avvia il timer */
            this.getTimer().start();

            /* avvia l'esportatore (esegue in un thread proprio) */
            this.getEsportatore().start();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Azione del bottone 'Registra impostazioni' </p>
     */
    private class AzRegistraImpostazioni implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            registraImpostazioni();
        }
    } // fine della classe 'interna'


    /**
     * Azione del bottone 'Carica impostazioni' </p>
     */
    private class AzCaricaImpostazioni implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            caricaImpostazioni();
        }
    } // fine della classe 'interna'


    /**
     * Registra le impostazioni correnti del dialogo.
     * <p/>
     */
    private void registraImpostazioni() {
        File file;
        ExportSettings setting;

        try {    // prova ad eseguire il codice

            file = LibFile.creaFile();
            if (file != null) {
                setting = this.creaSetting();
                setting.serializza(file);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Carica le impostazioni per il dialogo.
     * <p/>
     */
    private void caricaImpostazioni() {
        /* variabili e costanti locali di lavoro */
        File file;
        ExportSettings setting;

        try {    // prova ad eseguire il codice

            file = LibFile.getFile();
            if (file != null) {
                setting = ExportSettings.deserializza(file);
                this.usaSetting(setting);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Applica una impostazione al dialogo.
     * <p/>
     *
     * @param setting oggetto di tipo ExportSettings
     */
    private void usaSetting(ExportSettings setting) {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice

            this.setModuloRoot(setting.getModuloRoot());
            this.getCampoModuli().setValoreDaElenco(this.getModuloRoot());
            this.setCampiLista(setting.getCampi());
            this.getCampoChkFissi().setValore(setting.isNascondiCampiFissi());
            this.getCampoSorgente().setValoreDaElenco(setting.getSorgente());
            this.getCampoNumeriRiga().setValore(setting.isUsaNumeriRiga());
            this.getCampoTitoliColonna().setValore(setting.isUsaTitoliColonna());
            this.getCampoFormato().setValoreDaElenco(setting.getFormato());
            this.getCampoFile().setValore(setting.getPath());

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea un oggetto di impostazioni basato sullo stato del dialogo.
     * <p/>
     *
     * @return l'oggetto impostazioni
     */
    private ExportSettings creaSetting() {
        /* variabili e costanti locali di lavoro */
        ExportSettings setting = null;

        try {    // prova ad eseguire il codice

            setting = new ExportSettings();
            setting.setModuloRoot(this.getModuloRoot());
            setting.setCampi(this.getCampiLista());
            setting.setFiltro(this.recuperaFiltro());
            setting.setOrdine(this.recuperaOrdine());
            setting.setSorgente(this.getSorgente());
            setting.setUsaTitoliColonna(this.getUsaTitoliColonna());
            setting.setUsaNumeriRiga(this.getUsaNumeriRiga());
            setting.setFormato(this.getFormato());
            setting.setPath(this.getPath());

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return setting;
    }


    /**
     * Recupera il filtro di selezione.
     * <p/>
     *
     * @return il filtro
     */
    private Filtro recuperaFiltro() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        ImportExport.Sorgente sorgente;
        Navigatore nav;

        try {    // prova ad eseguire il codice

            sorgente = this.getSorgente();

            /* caso tutti i records, nessun filtro */
            if (sorgente.equals(ImportExport.Sorgente.tutti)) {
                filtro = null;
            }// fine del blocco if

            /* caso selezione, usa il filtro della lista */
            if (sorgente.equals(ImportExport.Sorgente.selezione)) {
                nav = this.getModuloRoot().getNavigatoreCorrente();
                if (nav != null) {
                    filtro = nav.getLista().getFiltro();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Recupera l'ordine di esportazione.
     * <p/>
     *
     * @return il filtro
     */
    private Ordine recuperaOrdine() {
        /* variabili e costanti locali di lavoro */
        Ordine ordine = null;
        ImportExport.Sorgente sorgente;
        Modulo modulo;
        Campo campo;
        Navigatore nav;

        try {    // prova ad eseguire il codice

            sorgente = this.getSorgente();
            modulo = this.getModuloRoot();

            /* caso tutti i records, ordina sul campo chiave */
            if (sorgente.equals(ImportExport.Sorgente.tutti)) {
                campo = modulo.getCampoChiave();
                ordine = new Ordine();
                ordine.add(campo);
            }// fine del blocco if

            /* caso selezione, usa l'ordine della lista */
            if (sorgente.equals(ImportExport.Sorgente.selezione)) {
                nav = modulo.getNavigatoreCorrente();
                if (nav != null) {
                    ordine = nav.getLista().getOrdine();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return ordine;
    }


    /**
     * Ritorna l'elenco dei campi selezionati per l'esportazione.
     * <p/>
     *
     * @return l'elenco dei campi contenuti nella lista
     */
    private ArrayList<Campo> getCampiLista() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> lista = null;
        ArrayList<Object> elementi;
        Campo campo;

        try {    // prova ad eseguire il codice
            lista = new ArrayList<Campo>();
            elementi = this.getModelloLista().getElementi();
            for (Object oggetto : elementi) {
                if (oggetto instanceof Campo) {
                    campo = (Campo)oggetto;
                    lista.add(campo);
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Carica un elenco di campi nella lista di esportazione.
     * <p/>
     *
     * @param campi da caricare
     */
    private void setCampiLista(ArrayList<Campo> campi) {
        try {    // prova ad eseguire il codice
            this.getModelloLista().removeAllElements();
            for (Campo campo : campi) {
                this.addCampoToLista(campo);
            }
        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna la sorgente dati di esportazione attualmente selezionata.
     * <p/>
     *
     * @return la sorgente dati di esportazione.
     */
    private ImportExport.Sorgente getSorgente() {
        /* variabili e costanti locali di lavoro */
        ImportExport.Sorgente sorgente = null;
        Object valoreElenco;
        Campo campo;

        try {    // prova ad eseguire il codice
            campo = this.getCampoSorgente();
            valoreElenco = campo.getValoreElenco();
            if (valoreElenco != null) {
                if (valoreElenco instanceof ImportExport.Sorgente) {
                    sorgente = (ImportExport.Sorgente)valoreElenco;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return sorgente;
    }


    /**
     * Ritorna l'uso dei titoli colonna.
     * <p/>
     *
     * @return l'uso dei titoli colonna.
     */
    private boolean getUsaTitoliColonna() {
        /* variabili e costanti locali di lavoro */
        boolean usa = false;
        Campo campo;

        try {    // prova ad eseguire il codice
            campo = this.getCampoTitoliColonna();
            usa = (Boolean)campo.getValore();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return usa;
    }


    /**
     * Ritorna l'uso dei numeri di riga.
     * <p/>
     *
     * @return l'uso dei numeri di riga.
     */
    private boolean getUsaNumeriRiga() {
        /* variabili e costanti locali di lavoro */
        boolean usa = false;
        Campo campo;

        try {    // prova ad eseguire il codice
            campo = this.getCampoNumeriRiga();
            usa = (Boolean)campo.getValore();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return usa;
    }


    /**
     * Ritorna il formato di esportazione.
     * <p/>
     *
     * @return il formato di esportazione.
     */
    private ImportExport.ExportFormats getFormato() {

        /* variabili e costanti locali di lavoro */
        ImportExport.ExportFormats formato = null;
        Object valoreElenco;
        Campo campo;

        try {    // prova ad eseguire il codice
            campo = this.getCampoFormato();
            valoreElenco = campo.getValoreElenco();
            if (valoreElenco != null) {
                if (valoreElenco instanceof ImportExport.ExportFormats) {
                    formato = (ImportExport.ExportFormats)valoreElenco;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return formato;
    }


    /**
     * Ritorna il percorso del file di esportazione.
     * <p/>
     *
     * @return il percorso del file di esportazione.
     */
    private String getPath() {
        /* variabili e costanti locali di lavoro */
        String path = null;
        Object valore;
        Campo campo;

        try {    // prova ad eseguire il codice
            campo = this.getCampoFile();
            valore = campo.getValore();
            if (valore != null) {
                if (valore instanceof String) {
                    path = (String)valore;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return path;

    }


    /**
     * Recupera il modulo Root
     * <p/>
     *
     * @return il modulo Root
     */
    private Modulo getModuloRoot() {
        return moduloRoot;
    }


    /**
     * Assegna il modulo Root
     * <p/>
     *
     * @param moduloRoot il modulo Root
     */
    private void setModuloRoot(Modulo moduloRoot) {

        try { // prova ad eseguire il codice

            /** aggiorna la variabile */
            this.moduloRoot = moduloRoot;

            /** aggiorna l'albero campi se esiste */
            this.regolaModelloAlbero();

            /** svuota la lista se esiste */
            this.removeAllCampi();

            /* regola il testo informativo sui records da esportare */
            this.regolaInfoRecords();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Metodo eseguito quando il valore di memoria un campo cambia.
     * <p/>
     *
     * @param campo cambiato
     */
    protected void eventoMemoriaModificata(Campo campo) {
        /* variabili e costanti locali di lavoro */
        Object valore;

        try { // prova ad eseguire il codice

            /* campo combo moduli */
            if (campo == this.getCampoModuli()) {
                valore = campo.getValoreElenco();
                if ((valore != null) && (valore instanceof Modulo)) {
                    Modulo modulo = (Modulo)valore;
                    this.setModuloRoot(modulo);
                }// fine del blocco if
            }// fine del blocco if

            /* campo check nascondi campi fissi */
            if (campo == this.getCampoChkFissi()) {
                valore = campo.getValore();
                boolean selezionato = (Boolean)valore;
                if (selezionato) {
                    rimuoviCampiFissi();
                } else {
                    aggiungiCampiFissi();
                }// fine del blocco if-else
            }// fine del blocco if

            /* campo sorgente */
            if (campo == this.getCampoSorgente()) {
                this.regolaInfoRecords();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        super.eventoMemoriaModificata(campo);

    }


    /**
     * Metodo eseguito quando i dati della lista campi vengono modifciati.
     * <p/>
     */
    private void datiListaModificati() {
        try { // prova ad eseguire il codice
            this.sincronizza();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Metodo eseguito quando la selezione nella lista viene modificata.
     * <p/>
     */
    private void selezioneListaModificata() {
        try { // prova ad eseguire il codice
            this.sincronizza();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Metodo eseguito quando la selezione nell'albero viene modificata.
     * <p/>
     */
    private void selezioneAlberoModificata() {
        try { // prova ad eseguire il codice
            this.sincronizza();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna il valore attuale dell'opzione Nascondi Campi Fissi.
     * <p/>
     *
     * @return true se nasconde i campi fissi false se li mostra
     */
    private boolean isNascondiCampiFissi() {
        /* variabili e costanti locali di lavoro */
        boolean nascondi = false;
        Campo campo;
        Object valore;

        try {    // prova ad eseguire il codice
            campo = this.getCampoChkFissi();
            valore = campo.getValore();
            nascondi = Libreria.getBool(valore);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nascondi;
    }


    /**
     * Determina se l'esportazione puo' essere eeguita.
     * <p/>
     * Controlla la congruita' dei valori inseriti nei campi del dialogo.
     *
     * @return true se e' esportabile
     */
    private boolean isEsportabile() {
        /* variabili e costanti locali di lavoro */
        boolean esportabile = false;
        boolean continua = true;
        ImportExport.ExportFormats formato;

        try {    // prova ad eseguire il codice

            /* ci deve essere il modulo root */
            if (continua) {
                if (this.getModuloRoot() == null) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* ci devono essere dei campi da esportare */
            if (continua) {
                if (this.getCampiLista().size() == 0) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* ci deve essere una sorgente selezionata */
            if (continua) {
                if (this.getSorgente() == null) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* ci deve essere un formato di esportazione */
            if (continua) {
                if (this.getFormato() == null) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* ci deve essere un file di destinazione */
            if (continua) {
                if (Lib.Testo.isVuota(this.getPath())) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if


            esportabile = continua;

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esportabile;
    }


    private AlberoModello getModelloAlbero() {
        return this.getAlbero().getModelloAlbero();
    }


    /**
     * Assegna all'albero il modello dati relativo
     * ai campi del modulo root corrente
     */
    private void regolaModelloAlbero() {
        /* variabili e costanti locali di lavoro */
        AlberoCampi albero;
        Modulo modulo;
        Campo campo;
        Object valore;
        boolean selezionato;

        try { // prova ad eseguire il codice

            albero = this.getAlbero();
            if (albero != null) {
                modulo = this.getModuloRoot();
                if (modulo != null) {
                    albero.caricaCampi(modulo);

                    /* controlla se rimuovere i campi fissi */
                    campo = this.getCampoChkFissi();
                    valore = campo.getValore();
                    selezionato = Libreria.getBool(valore);
                    if (selezionato) {
                        this.rimuoviCampiFissi();
                    }// fine del blocco if-else

                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Regola il testo informativo sul numero di record da esportare.
     */
    private void regolaInfoRecords() {
        Campo campo;
        Object valore;
        int quanti = 0;
        Navigatore nav;
        Filtro filtro;
        Lista lista;
        String info = "Nessun record"; //testo iniziale

        try { // prova ad eseguire il codice
            campo = this.getCampoSorgente();
            if (campo != null) {
                valore = campo.getValoreElenco();
                if (valore != null) {

                    /* caso Selezione */
                    if (valore.equals(ImportExport.Sorgente.selezione)) {
                        nav = this.getModuloRoot().getNavigatoreCorrente();
                        if (nav != null) {
                            lista = nav.getLista();
                            if (lista != null) {
                                filtro = lista.getFiltro();
                                if (filtro != null) {
                                    quanti = this.getModuloRoot()
                                            .query()
                                            .contaRecords(filtro);
                                }// fine del blocco if
                            }// fine del blocco if
                        }// fine del blocco if
                    }// fine del blocco if

                    /* caso Tutti */
                    if (valore.equals(ImportExport.Sorgente.tutti)) {
                        quanti = this.getModuloRoot().query().contaRecords();
                    }// fine del blocco if

                }// fine del blocco if

                /* regola il testo */
                if (quanti > 0) {
                    info = quanti + " records";
                }// fine del blocco if
                this.setInfoRecordsText(info);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    private ListaSingolaModello getModelloLista() {
        /* variabili e costanti locali di lavoro */
        ListaSingolaModello modello = null;
        ListaSingola lista;

        try { // prova ad eseguire il codice
            lista = this.getLista();
            if (lista != null) {
                modello = lista.getModello();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return modello;

    }


    /**
     * Regola il testo che visualizza il numero di records da esportare.
     * <p/>
     *
     * @param info il testo da visualizzare
     */
    private void setInfoRecordsText(String info) {
        /* variabili e costanti locali di lavoro */
        JLabel label;
        try {    // prova ad eseguire il codice
            label = this.infoRecords;
            if (label != null) {
                label.setText(info);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Restituisce il bottone Esporta del dialogo.
     * <p/>
     *
     * @return il bottone Esporta
     */
    private BottoneDialogo getBottoneEsporta() {
        /* variabili e costanti locali di lavoro */
        BottoneDialogo bottoneEsporta = null;

        try {    // prova ad eseguire il codice
            bottoneEsporta = this.getBottone(TESTO_BOTTONE_ESPORTA);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return bottoneEsporta;
    }


    private AlberoCampi getAlbero() {
        return albero;
    }


    private void setAlbero(AlberoCampi albero) {
        this.albero = albero;
    }


    private ListaSingola getLista() {
        return lista;
    }


    private void setLista(ListaSingola lista) {
        this.lista = lista;
    }


    private Campo getCampoModuli() {
        return campoModuli;
    }


    private void setCampoModuli(Campo campoModuli) {
        this.campoModuli = campoModuli;
    }


    private Campo getCampoChkFissi() {
        return campoChkFissi;
    }


    private void setCampoChkFissi(Campo campoChkFissi) {
        this.campoChkFissi = campoChkFissi;
    }


    private Campo getCampoSorgente() {
        return campoSorgente;
    }


    private void setCampoSorgente(Campo campoSorgente) {
        this.campoSorgente = campoSorgente;
    }


    private Campo getCampoTitoliColonna() {
        return campoTitoliColonna;
    }


    private void setCampoTitoliColonna(Campo campoTitoliColonna) {
        this.campoTitoliColonna = campoTitoliColonna;
    }


    private Campo getCampoNumeriRiga() {
        return campoNumeriRiga;
    }


    private void setCampoNumeriRiga(Campo campoNumeriRiga) {
        this.campoNumeriRiga = campoNumeriRiga;
    }


    private Campo getCampoFormato() {
        return campoFormato;
    }


    private void setCampoFormato(Campo campoFormato) {
        this.campoFormato = campoFormato;
    }


    private Campo getCampoFile() {
        return campoFile;
    }


    private void setCampoFile(Campo campoFile) {
        this.campoFile = campoFile;
    }


    private JProgressBar getProgressBar() {
        return progressBar;
    }


    private void setProgressBar(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }


    private Esporta getEsportatore() {
        return esportatore;
    }


    private void setEsportatore(Esporta esportatore) {
        this.esportatore = esportatore;
    }


    private Timer getTimer() {
        return timer;
    }


    private void setTimer(Timer timer) {
        this.timer = timer;
    }


    /**
     * Renderer specifico per gli elementi della lista
     */
    private class RendererRiga extends DefaultListCellRenderer {

        /**
         * Intercetto il metodo per ottenere informazioni
         * sull'elemento e regolare il testo del renderer
         */
        public Component getListCellRendererComponent(JList list,
                                                      Object value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus) {

            /* variabili e costanti locali di lavoro */
            Component comp;
            Campo campo;
            String testo = "";
            Icon icona = null;
            Font font = FONT_CAMPI;
            Color colore = Color.BLACK;

            comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value != null) {
                if (value instanceof Campo) {
                    campo = (Campo)value;
                    testo = campo.toString();
                    icona = campo.getIcona();
                    if (campo.getCampoDB().isFissoAlgos()) {
                        colore = COLORE_CAMPI_FISSI;
                    }// fine del blocco if-else
                } else {
                    testo = value.toString();
                }// fine del blocco if-else
            }// fine del blocco if

            /* regolo il testo per il renderer */
            this.setText(testo);
            this.setForeground(colore);
            this.setFont(font);
            this.setIcon(icona);

            /* valore di ritorno */
            return comp;

        }

    }


    /**
     * Listener invocato quando si clicca sul bottone Esporta.
     */
    private class AzioneEsporta implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            esporta();
        }

    }


    /**
     * Transfer Handler per il DND della lista.
     */
    private final class ListaTransferHandler extends JListTransferHandler {

        /**
         * Costruttore.
         * <p/>
         */
        private ListaTransferHandler() {
            /* rimanda al costruttore della superclasse */
            super(MOVE);
            this.setAccettaDuplicati(false);
        }// fine del metodo costruttore


        /**
         * Ritorna l'elenco degli oggetti attualmente selezionati.
         * <p/>
         *
         * @param source il JComponent interessato
         *
         * @return l'elenco degli oggetti selezionati nel componente interessato.
         */
        protected Object getDatiSelezionati(JComponent source) {
            /* variabili e costanti locali di lavoro */
            ArrayList values = null;

            try {    // prova ad eseguire il codice

                values = this.getCampiSelezionati(source);

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return values;
        }


        /**
         * Ritorna la lista dei campi attualmente selezionati
         * nel componente comp.
         * <p/>
         */
        private ArrayList<Campo> getCampiSelezionati(JComponent comp) {
            /* variabili e costanti locali di lavoro */
            ArrayList<Campo> campiSelezionati = null;
            JList list;
            Object[] values;
            Object val;
            Campo campo;

            try {    // prova ad eseguire il codice

                list = (JList)comp;
                values = list.getSelectedValues();

                campiSelezionati = new ArrayList<Campo>();
                for (int i = 0; i < values.length; i++) {
                    val = values[i];
                    if (val != null) {
                        if (val instanceof Campo) {
                            campo = (Campo)val;
                            campiSelezionati.add(campo);
                        }// fine del blocco if
                    }// fine del blocco if
                }
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return campiSelezionati;
        }


        /**
         * Chiamato al momento di importare i dati nell'oggetto.
         * <p/>
         * Intercetto l'importazione per eliminare evantuali oggetti
         * che non sono campi.
         *
         * @param oggettoDati da importare
         * @param dest il componente destinazione
         * @param source il componente sorgente
         *
         * @return true se riuscito
         */
        protected boolean importaDati(Object oggettoDati, JComponent dest, JComponent source) {
            /* variabili e costanti locali di lavoro */
            ArrayList listaIn;
            ArrayList<Campo> listaOut = null;
            Campo campo;

            try { // prova ad eseguire il codice
                listaIn = (ArrayList)oggettoDati;
                listaOut = new ArrayList<Campo>();
                for (Object oggetto : listaIn) {
                    if (oggetto instanceof Campo) {
                        campo = (Campo)oggetto;
                        listaOut.add(campo);
                    }// fine del blocco if
                }
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /** rimanda alla superclasse dopo aver pulito la lista */
            return super.importaDati(listaOut, dest, source);

        }

    } // fine della classe 'interna'


    /**
     * Transfer Handler per il DND dell'albero.
     */
    private final class AlberoTransferHandler extends JTreeTransferHandler {

        /**
         * Costruttore.
         * <p/>
         */
        private AlberoTransferHandler() {
            /* rimanda al costruttore della superclasse */
            super(COPY);
        }// fine del metodo costruttore


        /**
         * Ritorna l'elenco degli oggetti attualmente selezionati.
         * <p/>
         *
         * @param source il JComponent interessato
         *
         * @return l'elenco degli oggetti selezionati nel componente interessato.
         */
        protected Object getDatiSelezionati(JComponent source) {
            /* variabili e costanti locali di lavoro */
            ArrayList values = null;

            try {    // prova ad eseguire il codice

                values = getAlbero().getOggettiSelezionati();

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return values;
        }


    } // fine della classe 'interna'


    /**
     * Inner class per gestire l'azione.
     */
    private class AzioneDatiListaModificati extends ListaDatiModAz {

        /**
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void listaDatiModAz(ListaDatiModEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                datiListaModificati();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Inner class per gestire l'azione.
     */
    private class AzioneSelezioneListaModificata extends ListaSelModAz {

        /**
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void listaSelModAz(ListaSelModEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                selezioneListaModificata();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Inner class per gestire l'azione.
     */
    private class AzioneSelezioneAlberoModificata extends AlberoSelModAz {

        /**
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void alberoSelModAz(AlberoSelModEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                selezioneAlberoModificata();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Listener invocato quando si clicca sul bottone Esporta.
     */
    private class ListenerEsportazione implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            getProgressBar().setValue(getEsportatore().getQuantiEsportati());

            if (getEsportatore().isDone()) {
                Lib.Sist.beep();
                getTimer().stop();
                getBottoneEsporta().setEnabled(true);
                getProgressBar().setValue(getProgressBar().getMinimum());
                getProgressBar().setString("Terminato");
            }
        }

    }


} // fine della classe
