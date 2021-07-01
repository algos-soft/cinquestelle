package it.algos.albergo.clientealbergo.compleanni;

import com.wildcrest.j2printerworks.J2Printer;
import com.wildcrest.j2printerworks.J2TablePrinter;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.clientealbergo.tabelle.parente.Parentela;
import it.algos.albergo.clientealbergo.tabelle.parente.ParentelaModulo;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.albergo.tabelle.lingua.Lingua;
import it.algos.albergo.tabelle.lingua.LinguaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.componente.bottone.BottoneDialogo;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.memoria.dati.DatiMemoria;
import it.algos.base.dialogo.DialogoBase;
import it.algos.base.errore.Errore;
import it.algos.base.importExport.Esporta;
import it.algos.base.importExport.ExportSettings;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.lista.TavolaModello;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.stampa.Printer;
import it.algos.base.tavola.Tavola;
import it.algos.base.validatore.ValidatoreFactory;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.TestoAlgos;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.anagrafica.tabelle.titolo.Titolo;
import it.algos.gestione.anagrafica.tabelle.titolo.TitoloModulo;
import it.algos.gestione.indirizzo.Indirizzo;
import it.algos.gestione.indirizzo.tabelle.nazione.Nazione;
import it.algos.gestione.indirizzo.tabelle.provincia.Provincia;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Gestione Compleanni
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 17-sett-2008 ore 19.21.46
 */
public class GestioneCompleanni extends DialogoBase {

    /* pannello per le condizioni di ricerca */
    private Pannello panCampi;

    /* pannello per l'esecuzione e il risultato della ricerca */
    private Pannello panCerca;

    /* navigatore di riferimento (per caricare eventualmente il risultato nella lista) */
    private Navigatore navRif;

    /* filtro corrente risultante dalla ultima esecuzione di una ricerca */
    private Filtro filtroCorrente;

    /* bottone Stampa */
    private JButton botStampa;

    /* bottone Esporta */
    private JButton botEsporta;

    /* bottone Mostra in Lista */
    private JButton botMostraLista;


    /**
     * Costruttore base con parametri.
     * <p/>
     */
    public GestioneCompleanni() {
        this(null);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param nav Navigatore di riferimento
     */
    public GestioneCompleanni(Navigatore nav) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setNavRif(nav);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal
     * costruttore (init) <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice

            this.getDialogo().setModal(false);
            this.setTitolo("Ricerca compleanni");
            this.addBottoni();
            this.creaCampi();
            this.creaPannelli();
            this.impagina();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Crea i campi per il dialogo e li aggiunge alla collezione.
     * <p/>
     */
    private void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        int lar = 30;

        try {    // prova ad eseguire il codice

            campo = CampoFactory.intero(Campi.ggStart.get());
            campo.setTestoEtichetta("giorno");
            campo.setLarghezza(lar);
            campo.setValidatore(ValidatoreFactory.numIntPos());
            campo.getValidatore().setValoreMassimo(31);
            campo.getValidatore().setLunghezzaMassima(2);
            this.addCampoCollezione(campo);

            campo = CampoFactory.intero(Campi.mmStart.get());
            campo.setTestoEtichetta("mese");
            campo.setLarghezza(lar);
            campo.setValidatore(ValidatoreFactory.numIntPos());
            campo.getValidatore().setValoreMassimo(12);
            campo.getValidatore().setLunghezzaMassima(2);
            this.addCampoCollezione(campo);

            campo = CampoFactory.intero(Campi.ggEnd.get());
            campo.setTestoEtichetta("giorno");
            campo.setLarghezza(lar);
            campo.setValidatore(ValidatoreFactory.numIntPos());
            campo.getValidatore().setValoreMassimo(31);
            campo.getValidatore().setLunghezzaMassima(2);
            this.addCampoCollezione(campo);

            campo = CampoFactory.intero(Campi.mmEnd.get());
            campo.setTestoEtichetta("mese");
            campo.setLarghezza(lar);
            campo.setValidatore(ValidatoreFactory.numIntPos());
            campo.getValidatore().setValoreMassimo(12);
            campo.getValidatore().setLunghezzaMassima(2);
            this.addCampoCollezione(campo);

            campo = CampoFactory.intero(Campi.annoStart.get());
            campo.decora().eliminaEtichetta();
            campo.setValidatore(ValidatoreFactory.numIntPos());
            campo.getValidatore().setValoreMassimo(9999);
            this.addCampoCollezione(campo);

            campo = CampoFactory.checkBox(Campi.soloCorrispondenza.get());
            campo.setTestoComponente("solo con corrispondenza");
            campo.setLarghezza(200);
            campo.setValore(true);
            this.addCampoCollezione(campo);

            campo = CampoFactory.intero(Campi.risultato.get());
            campo.decora().eliminaEtichetta();
            campo.decora().etichettaSinistra("n. di compleanni trovati");
            campo.setLarghezza(60);
            campo.setAbilitato(false);
            this.addCampoCollezione(campo);


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Crea e registra i pannelli da inserire nel dialogo.
     * <p/>
     */
    private void creaPannelli() {
        /* variabili e costanti locali di lavoro */
        Pannello pan;

        try {    // prova ad eseguire il codice

            pan = this.creaPanCampi();
            this.setPanCampi(pan);

            pan = this.creaPanCerca();
            this.setPanCerca(pan);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea, registra e aggiunge i bottoni al dialogo.
     * <p/>
     */
    private void addBottoni() {
        /* variabili e costanti locali di lavoro */
        BottoneDialogo bot;

        try {    // prova ad eseguire il codice

            bot = this.addBottone("Stampa", false, false);
            bot.setIcon(Lib.Risorse.getIconaBase("Print24"));
            Lib.Comp.setPreferredWidth(bot, 100);
            bot.addActionListener(new AzStampa());
            this.setBotStampa(bot);

            bot = this.addBottone("Esporta", false, false);
            bot.setIcon(Lib.Risorse.getIconaBase("Export24"));
            Lib.Comp.setPreferredWidth(bot, 100);
            bot.addActionListener(new AzEsporta());
            this.setBotEsporta(bot);

            if (this.getNavRif() != null) {
                bot = this.addBottone("Mostra in lista", true, true);
                bot.setIcon(Lib.Risorse.getIconaBase("CaricaTutti24"));
                Lib.Comp.setPreferredWidth(bot, 130);
                bot.addActionListener(new AzMostraLista());
                this.setBotMostraLista(bot);
            }// fine del blocco if

            this.addBottoneChiudi();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Impagina il dialogo.
     * <p/>
     * Aggiunge i componenti
     */
    private void impagina() {
        /* variabili e costanti locali di lavoro */
        Pannello pan;

        try {    // prova ad eseguire il codice

            pan = this.getPanCampi();
            this.addPannello(pan);

            pan = this.getPanCerca();
            this.addPannello(pan);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Crea il pannello con le condizioni di ricerca.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanCampi() {
        /* variabili e costanti locali di lavoro */
        Pannello panTot = null;
        Pannello pan = null;

        try {    // prova ad eseguire il codice

            panTot = PannelloFactory.verticale(null);

            pan = this.creaPanRange();
            panTot.add(pan);

            pan = this.creaPanAnno();
            panTot.add(pan);

            pan = this.creaPanOpzioni();
            panTot.add(pan);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return panTot;
    }


    /**
     * Crea il pannello contenente il range di ricerca (giorno/mese).
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanRange() {
        /* variabili e costanti locali di lavoro */
        Pannello panTot = null;
        Pannello pan = null;
        JComponent spiega;
        String testo;

        try {    // prova ad eseguire il codice

            panTot = PannelloFactory.verticale(null);
            panTot.creaBordo("Cerca nel periodo");

            pan = PannelloFactory.orizzontale(this);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(10);
            pan.setAllineamento(Layout.ALLINEA_BASSO);

            pan.add(new JLabel("dal"));
            pan.add(Campi.ggStart.get());
            pan.add(Campi.mmStart.get());
            pan.add(new JLabel("al"));
            pan.add(Campi.ggEnd.get());
            pan.add(Campi.mmEnd.get());
            panTot.add(pan);

            testo = "considera solo i compleanni che accadono nel perdiodo indicato.\n"
                    + "se non specificato considera i compleanni di tutti i giorni dell'anno.";
            spiega = this.creaSpiega(testo);
            panTot.add(spiega);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return panTot;
    }


    /**
     * Crea il pannello contenente l'anno di inizio.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanAnno() {
        /* variabili e costanti locali di lavoro */
        Pannello panTot = null;
        Pannello pan = null;
        JComponent spiega;
        String testo;

        try {    // prova ad eseguire il codice

            panTot = PannelloFactory.verticale(null);
            panTot.creaBordo("Cerca dall'anno di soggiorno");

            pan = PannelloFactory.orizzontale(this);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(10);
            pan.setAllineamento(Layout.ALLINEA_BASSO);

//            pan.add(new JLabel("dal"));
            pan.add(Campi.annoStart.get());
            panTot.add(pan);

            testo = "considera solo i clienti che hanno soggiornato dall'anno indicato in poi.\n"
                    + "se non specificato considera tutti i clienti.";
            spiega = this.creaSpiega(testo);
            panTot.add(spiega);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return panTot;
    }


    /**
     * Crea il pannello contenente le altre opzioni.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanOpzioni() {
        /* variabili e costanti locali di lavoro */
        Pannello panTot = null;
        Pannello pan = null;
        JComponent spiega;
        String testo;

        try {    // prova ad eseguire il codice

            panTot = PannelloFactory.verticale(null);
            panTot.creaBordo("Opzioni");

            pan = PannelloFactory.orizzontale(this);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(10);
            pan.setAllineamento(Layout.ALLINEA_BASSO);

            pan.add(Campi.soloCorrispondenza.get());
            panTot.add(pan);

            testo
                    = "se spuntato, considera solo i clienti che hanno il flag \"corrispondenza\" abilitato.";
            spiega = this.creaSpiega(testo);
            panTot.add(spiega);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return panTot;
    }


    /**
     * Crea il pannello per l'esecuzione e il risultato della ricerca.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanCerca() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        JButton bottone;
        Icon icona;

        try {    // prova ad eseguire il codice

            icona = Lib.Risorse.getIconaBase("ricerca24");
            bottone = new JButton("Esegui ricerca", icona);
            bottone.setOpaque(false);
            Lib.Comp.setPreferredWidth(bottone, 140);
            bottone.addActionListener(new AzRicerca());

            pan = PannelloFactory.orizzontale(this);
            pan.creaBordo("Ricerca");
            pan.setAllineamento(Layout.ALLINEA_CENTRO);
            pan.add(Box.createHorizontalGlue());
            pan.add(bottone);
            pan.add(Campi.risultato.get());
            pan.add(Box.createHorizontalGlue());

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea un componente grafico di spiegazione.
     * <p/>
     *
     * @param testo per la label
     *
     * @return il componente creato
     */
    private JComponent creaSpiega(String testo) {
        /* variabili e costanti locali di lavoro */
        JTextArea area = null;

        try {    // prova ad eseguire il codice

            area = new JTextArea();
            area.setFocusable(false);
            area.setEditable(false);
            TestoAlgos.setLegenda(area);
            area.setText(testo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return area;
    }


    /**
     * Esegue la ricerca in base alle condizioni specificate.
     * <p/>
     * Controlla che le condizioni siano valide
     * Esegue la ricerca
     * Visualizza il risultato
     * Memorizza il filtro
     */
    private void eseguiRicerca() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int g1, m1, g2, m2;
        int annoMin;
        boolean soloCorr;
        Filtro filtro = null;
        int quanti;
        Modulo modCliente;

        try {    // prova ad eseguire il codice

            /* controlla che le condizioni siano valide */
            continua = this.chkCondizioni();

            /* effettua la ricerca e ottiene il filtro */
            if (continua) {
                g1 = this.getGStart();
                m1 = this.getMStart();
                g2 = this.getGEnd();
                m2 = this.getMEnd();
                annoMin = this.getAnnoStart();
                soloCorr = this.getSoloCorrispondenza();
                filtro = ClienteAlbergoModulo.getFiltroCompleanni(
                        g1, m1, g2, m2, annoMin, soloCorr);
            }// fine del blocco if

            /* registra il filtro */
            if (continua) {
                this.setFiltroCorrente(filtro);
            }// fine del blocco if

            /* aggiorna e visualizza il risultato */
            if (continua) {
                modCliente = ClienteAlbergoModulo.get();
                quanti = modCliente.query().contaRecords(filtro);
                this.setRisultato(quanti);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Stampa un report dei compleanni trovati.
     * <p/>
     */
    private void stampa() {
        /* variabili e costanti locali di lavoro */
        Printer printer;
        J2TablePrinter tablePrinter;

        try {    // prova ad eseguire il codice
            
            /* crea e regola il Printer generale */
            printer = Lib.Stampa.getDefaultPrinter();
            printer.setOrientation(J2Printer.LANDSCAPE);
            printer.setCenterHeader("Elenco compleanni");

            tablePrinter = this.creaPrinterCompleanni();
            printer.addPageable(tablePrinter);
            printer.showPrintPreviewDialog();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea un printer con l'elenco dei compleanni.
     * <p/>
     * @return il printer creato
     */
    private J2TablePrinter creaPrinterCompleanni() {
        /* variabili e costanti locali di lavoro */
        J2TablePrinter printer=null;
        Dati dati;
        Vista vista;
        ArrayList<Campo> campi;
        Tavola tavola;
        TavolaModello modello;

        try {    // prova ad eseguire il codice

            /* recupera i dati */
            dati = this.creaDatiMemoria();

            /* crea una vista con i campi dei dati */
            vista = new Vista();
            campi = dati.getCampi();
            for(Campo campo : campi){
                vista.addCampo(campo);
            }

            /* crea una tavola con il modello */
            tavola = new Tavola();
            modello = new TavolaModello(tavola, vista);
            modello.setDati(dati);
            tavola.setModello(modello);

            /* crea un printer della tavola */
            printer = tavola.getTablePrinter();


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return printer;
    }


//    /**
//     * Crea un pannello stampabile con i dati di un compleanno.
//     * <p/>
//     * @param dati oggetto contenente i dati
//     * @param indice della riga da stampare
//     * @return il pannello stampabile creato
//     */
//    private J2PanelPrinter creaPanRiga(Dati dati, int indice) {
//        /* variabili e costanti locali di lavoro */
//        J2PanelPrinter panPrinter = null;
//        JPanel pan;
//        boolean continua;
////        Pannello pannello = null;
//        Riepilogo riepilogo;
//
//        try { // prova ad eseguire il codice
//            /* crea il Printer completo */
//            panPrinter = new J2PanelPrinter();
//            panPrinter.setPanel(riepilogo.getPanFisso());
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return panPrinter;
//    }




    /**
     * Stampa l'elenco dei compleanni trovati.
     * <p/>
     */
    private void esporta() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        DialogoEsporta dialogo;
        ExportSettings settings;
        Esporta exporter;
        Dati dati;

        try {    // prova ad eseguire il codice

            /* presenta il dialogo di esportazione */
            dialogo = new DialogoEsporta();
            dialogo.avvia();
            continua = dialogo.isConfermato();

            /* crea l'oggetto da esportare ed esporta */
            if (continua) {
                dati = this.creaDatiMemoria();
                settings = new ExportSettings();
                settings.setFormato(dialogo.getFormatoExport());
                settings.setPath(dialogo.getDestinazione());
                settings.setUsaTitoliColonna(true);
                exporter = new Esporta(dati, settings);
                exporter.run();
                dati.close();
                new MessaggioAvviso("Terminato.");
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea un oggetto Dati in memoria contenente le informazioni
     * da esportare / stampare.
     * <p/>
     * @return l'oggetto Dati da esportare o stampare
     */
    private Dati creaDatiMemoria () {
        /* variabili e costanti locali di lavoro */
        Dati datiMem  = null;
        Dati datiDb;
        ArrayList<Campo> campi;
        Modulo modCliente;
        Query query;
        Ordine ordine;
        Campo campoTitolo;
        Campo campoLingua;
        Campo campoParentela;
        HashMap<it.algos.base.wrapper.Campi,String> mappa;

        try {    // prova ad eseguire il codice

            /* recupera i dati dal database */
            modCliente = ClienteAlbergoModulo.get();
            ordine = new Ordine();
            ordine.add(modCliente.getCampo(ClienteAlbergo.Cam.indiceGiornoNato.get()));
            ordine.add(modCliente.getCampo(Anagrafica.Cam.cognome.get()));
            ordine.add(modCliente.getCampo(Anagrafica.Cam.nome.get()));
            query = new QuerySelezione(modCliente);
            query.addCampo(modCliente.getCampoChiave());
            query.addCampo(Anagrafica.Cam.cognome);
            query.addCampo(Anagrafica.Cam.nome);
            campoTitolo = TitoloModulo.get().getCampo(Titolo.Cam.sigla);
            query.addCampo(campoTitolo);
            query.addCampo(ClienteAlbergo.Cam.dataNato);
            query.addCampo(Anagrafica.Cam.telefono);
            query.addCampo(Anagrafica.Cam.email);
            campoLingua = LinguaModulo.get().getCampo(Lingua.Cam.descrizione);
            query.addCampo(campoLingua);
            campoParentela = ParentelaModulo.get().getCampo(Parentela.Cam.descrizione);
            query.addCampo(campoParentela);

            query.setFiltro(this.getFiltroCorrente());
            query.setOrdine(ordine);
            datiDb = modCliente.query().querySelezione(query);


            /* crea i dati memoria vuoti */
            campi = new ArrayList<Campo>();
            Campo memGiornoNascita = CampoFactory.intero("gg");
            memGiornoNascita.setLarLista(20);
            campi.add(memGiornoNascita);
            Campo memMeseNascita = CampoFactory.intero("mm");
            memMeseNascita.setLarLista(20);
            campi.add(memMeseNascita);
            Campo memAnnoNascita = CampoFactory.intero("aa");
            memAnnoNascita.setLarLista(30);
            campi.add(memAnnoNascita);
            Campo memTitolo = CampoFactory.testo("titolo");
            memTitolo.setLarLista(40);
            campi.add(memTitolo);
            Campo memCognome = CampoFactory.testo("cognome");
            memCognome.setLarLista(110);
            campi.add(memCognome);
            Campo memNome = CampoFactory.testo("nome");
            memNome.setLarLista(70);
            campi.add(memNome);
            Campo memIndirizzo = CampoFactory.testo("indirizzo");
            memIndirizzo.setLarLista(160);
            campi.add(memIndirizzo);
            Campo memIndirizzo2 = CampoFactory.testo("indirizzo2");
            memIndirizzo2.setLarLista(100);
            campi.add(memIndirizzo2);
            Campo memCAP = CampoFactory.testo("CAP");
            memCAP.setLarLista(45);
            campi.add(memCAP);
            Campo memCitta = CampoFactory.testo("località");
            memCitta.setLarLista(120);
            campi.add(memCitta);
            Campo memProvincia = CampoFactory.testo("provincia");
            memProvincia.setLarLista(30);
            campi.add(memProvincia);
            Campo memNazione = CampoFactory.testo("nazione");
            memNazione.setLarLista(60);
            campi.add(memNazione);
            Campo memTelefono = CampoFactory.testo("telefono");
            memTelefono.setLarLista(100);
            campi.add(memTelefono);
            Campo memEmail = CampoFactory.testo("email");
            memEmail.setLarLista(200);
            campi.add(memEmail);
            Campo memLingua = CampoFactory.testo("lingua");
            memLingua.setLarLista(40);
            campi.add(memLingua);
            Campo memParentela = CampoFactory.testo("par.");
            memParentela.setLarLista(40);
            campi.add(memParentela);
            Campo memAnnoUltSogg = CampoFactory.intero("ult.sogg.");
            memAnnoUltSogg.setLarLista(30);
            campi.add(memAnnoUltSogg);
            
            datiMem = new DatiMemoria(campi, datiDb.getRowCount());

            /* legge i dati db e riempie i dati memoria */
            for (int k = 0; k < datiDb.getRowCount(); k++) {
                int codice = datiDb.getIntAt(k,modCliente.getCampoChiave());
                String cognome = datiDb.getStringAt(k,Anagrafica.Cam.cognome.get());
                String nome = datiDb.getStringAt(k,Anagrafica.Cam.nome.get());
                String titolo = datiDb.getStringAt(k,campoTitolo);
                Date dataUlt = PresenzaModulo.getDataUltimoSoggiorno(codice);
                Date dataNascita = datiDb.getDataAt(k,ClienteAlbergo.Cam.dataNato.get());
                mappa = ClienteAlbergoModulo.getIndirizzo(codice);
                String indirizzo = mappa.get(Indirizzo.Cam.indirizzo);
                String indirizzo2 = mappa.get(Indirizzo.Cam.indirizzo2);
                String cap = mappa.get(Indirizzo.Cam.cap);
                String citta = mappa.get(Indirizzo.Cam.citta);
                String provincia = mappa.get(Provincia.Cam.sigla);
                String nazione = mappa.get(Nazione.Cam.nazione);
                String telefono = datiDb.getStringAt(k,Anagrafica.Cam.telefono.get());
                String email = datiDb.getStringAt(k,Anagrafica.Cam.email.get());
                String lingua = datiDb.getStringAt(k,campoLingua);
                String parentela = datiDb.getStringAt(k,campoParentela);

                /* scrive i valori nella riga dei dati */
                datiMem.setValueAt(k, memGiornoNascita, Lib.Data.getNumeroGiorno(dataNascita));
                datiMem.setValueAt(k, memMeseNascita, Lib.Data.getNumeroMese(dataNascita));
                datiMem.setValueAt(k, memAnnoNascita, Lib.Data.getAnno(dataNascita));
                datiMem.setValueAt(k, memTitolo, titolo);
                datiMem.setValueAt(k, memCognome, cognome);
                datiMem.setValueAt(k, memNome, nome);
                datiMem.setValueAt(k, memIndirizzo, indirizzo);
                datiMem.setValueAt(k, memIndirizzo2, indirizzo2);
                datiMem.setValueAt(k, memCAP, cap);
                datiMem.setValueAt(k, memCitta, citta);
                datiMem.setValueAt(k, memProvincia, provincia);
                datiMem.setValueAt(k, memNazione, nazione);
                datiMem.setValueAt(k, memTelefono, telefono);
                datiMem.setValueAt(k, memEmail, email);
                datiMem.setValueAt(k, memLingua, lingua);
                datiMem.setValueAt(k, memParentela, parentela);
                datiMem.setValueAt(k, memAnnoUltSogg, Lib.Data.getAnno(dataUlt));

            } // fine del ciclo for

            datiDb.close();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return datiMem;
    }





    /**
     * Mostra il risultato della ricerca nella lista del navigatore di riferimento.
     * <p/>
     */
    private void mostraLista() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        Filtro filtro;

        try {    // prova ad eseguire il codice
            nav = this.getNavRif();
            if (nav != null) {
                filtro = this.getFiltroCorrente();
                nav.setFiltroCorrente(filtro);
                nav.aggiornaLista();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        JButton bot;

        super.sincronizza();

        try { // prova ad eseguire il codice

            /* abilitazione bottone stampa*/
            bot = this.getBotStampa();
            bot.setEnabled(this.getRisultato() > 0);

            /* abilitazione bottone esporta*/
            bot = this.getBotEsporta();
            bot.setEnabled(this.getRisultato() > 0);

            /* abilitazione bottone mostra in lista*/
            bot = this.getBotMostraLista();
            bot.setEnabled(this.getRisultato() > 0);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Controlla che le condizioni di ricerca siano valide.
     * <p/>
     * Se non lo sono visualizza un messaggio di spiegazione
     *
     * @return true se sono valide
     */
    private boolean chkCondizioni() {
        /* variabili e costanti locali di lavoro */
        boolean valide = true;
        String testo;
        String messaggio = "";

        try {    // prova ad eseguire il codice

            /* controllo range */
            testo = this.isRangeCongruo();
            if (Lib.Testo.isValida(testo)) {
                messaggio += testo;
            }// fine del blocco if

            /* se ha creato il messaggio lo visualizza e ritorna false */
            if (Lib.Testo.isValida(messaggio)) {
                new MessaggioAvviso(messaggio);
                valide = false;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valide;
    }


    /**
     * Controlla se gli attuali valori di range sono congrui.
     * <p/>
     * - se è specificato un valore devono essere specificati anche tutti gli altri
     *
     * @return stringa vuota se congruo, testo con spiegazione se non congruo
     */
    private String isRangeCongruo() {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        int g1, m1, g2, m2;

        try {    // prova ad eseguire il codice
            g1 = this.getGStart();
            m1 = this.getMStart();
            g2 = this.getGEnd();
            m2 = this.getMEnd();

            if ((g1 != 0) | (m1 != 0) | (g2 != 0) | (m2 != 0)) {
                if ((g1 == 0) | (m1 == 0) | (g2 == 0) | (m2 == 0)) {
                    testo
                            = "Se si usa il periodo di ricerca bisogna inserire un valore in tutti i campi.";
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Ritorna il giorno di inizio range.
     * <p/>
     *
     * @return il giorno di inizio range
     */
    private int getGStart() {
        return this.getInt(Campi.ggStart.get());
    }


    /**
     * Ritorna il mese di inizio range.
     * <p/>
     *
     * @return il mese di inizio range
     */
    private int getMStart() {
        return this.getInt(Campi.mmStart.get());
    }


    /**
     * Ritorna il giorno di fine range.
     * <p/>
     *
     * @return il giorno di fine range
     */
    private int getGEnd() {
        return this.getInt(Campi.ggEnd.get());
    }


    /**
     * Ritorna il mese di fine range.
     * <p/>
     *
     * @return il mese di fine range
     */
    private int getMEnd() {
        return this.getInt(Campi.mmEnd.get());
    }


    /**
     * Ritorna l'anno di soggiorno minimo.
     * <p/>
     *
     * @return l'anno di soggiorno minimo
     */
    private int getAnnoStart() {
        return this.getInt(Campi.annoStart.get());
    }


    /**
     * Ritorna l'opzione solo corrispondenza.
     * <p/>
     *
     * @return l'opzione solo corrispondenza
     */
    private boolean getSoloCorrispondenza() {
        return this.getBool(Campi.soloCorrispondenza.get());
    }


    /**
     * Ritorna il numero di risultati.
     * <p/>
     *
     * @return il numero di risultati
     */
    private int getRisultato() {
        return this.getInt(Campi.risultato.get());
    }


    /**
     * Assegna il numero di risultati.
     * <p/>
     *
     * @param quanti il numero di risultati
     */
    private void setRisultato(int quanti) {
        this.setValore(Campi.risultato.get(), quanti);
    }


    private Pannello getPanCampi() {
        return panCampi;
    }


    private void setPanCampi(Pannello panCampi) {
        this.panCampi = panCampi;
    }


    private Pannello getPanCerca() {
        return panCerca;
    }


    private void setPanCerca(Pannello panCerca) {
        this.panCerca = panCerca;
    }


    private Navigatore getNavRif() {
        return navRif;
    }


    private void setNavRif(Navigatore navRif) {
        this.navRif = navRif;
    }


    private Filtro getFiltroCorrente() {
        return filtroCorrente;
    }


    private void setFiltroCorrente(Filtro filtroCorrente) {
        this.filtroCorrente = filtroCorrente;
    }


    private JButton getBotStampa() {
        return botStampa;
    }


    private void setBotStampa(JButton botStampa) {
        this.botStampa = botStampa;
    }


    private JButton getBotEsporta() {
        return botEsporta;
    }


    private void setBotEsporta(JButton botEsporta) {
        this.botEsporta = botEsporta;
    }


    private JButton getBotMostraLista() {
        return botMostraLista;
    }


    private void setBotMostraLista(JButton botMostraLista) {
        this.botMostraLista = botMostraLista;
    }


    /**
     * Listener bottone Esegui Ricerca
     * </p>
     */
    public final class AzRicerca implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            eseguiRicerca();
        }
    } // fine della classe 'interna'

    /**
     * Listener bottone Stampa
     * </p>
     */
    public final class AzStampa implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            stampa();
        }
    } // fine della classe 'interna'

    /**
     * Listener bottone Esporta
     * </p>
     */
    public final class AzEsporta implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            esporta();
        }
    } // fine della classe 'interna'

    /**
     * Listener bottone Mostra nella Lista
     * </p>
     */
    public final class AzMostraLista implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            mostraLista();
        }
    } // fine della classe 'interna'

    /**
     * Codifica dei campi del dialogo.
     */
    private enum Campi {

        ggStart("giornoinizio"), // giorno inizio
        mmStart("meseinizio"), // mese inizio
        ggEnd("giornofine"), // giorno fine
        mmEnd("mesefine"), // mese fine
        annoStart("annoinizio"), // anno inizio
        soloCorrispondenza("corrispondenza"), // flag corrispondenza
        risultato("risultato"); // numero di compleanni trovati

        /**
         * titolo del popup.
         */
        private String nome;


        /**
         * Costruttore completo con parametri.
         *
         * @param titolo del popup utilizzato per il tooltiptext
         */
        Campi(String titolo) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setNome(titolo);
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }


        private String getNome() {
            return nome;
        }


        private void setNome(String nome) {
            this.nome = nome;
        }


        public String get() {
            return getNome();
        }
    }// fine della classe



}// fine della classe