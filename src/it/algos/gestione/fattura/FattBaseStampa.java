/**
 * Title:     FatturaStampa
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      8-feb-2007
 */
package it.algos.gestione.fattura;

import com.wildcrest.j2printerworks.J2ComponentPrinter;
import com.wildcrest.j2printerworks.J2FlowPrinter;
import com.wildcrest.j2printerworks.J2Pageable;
import com.wildcrest.j2printerworks.J2Printer;
import com.wildcrest.j2printerworks.PageEject;
import com.wildcrest.j2printerworks.PrintingEventHandler;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.componente.WrapTextArea;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.memoria.dati.DatiMemoria;
import it.algos.base.documento.Documento;
import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.gestione.fattura.fatt.FattModulo;
import it.algos.gestione.fattura.riga.RigaFattura;
import it.algos.gestione.fattura.riga.RigaFatturaModulo;
import it.algos.gestione.tabelle.iva.Iva;
import it.algos.gestione.tabelle.iva.IvaModulo;
import it.algos.gestione.tabelle.tipopagamento.TipoPagamento;
import it.algos.gestione.tabelle.tipopagamento.TipoPagamentoModulo;
import it.algos.gestione.tabelle.um.UM;
import it.algos.gestione.tabelle.um.UMModulo;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.print.Pageable;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

/**
 * Classe per la stampa di una singola fattura.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 8-feb-2007
 */
public final class FattBaseStampa {

    /* flag per il debug grafico */
    private static final boolean DEBUG = false;

    /**
     * Margini desiderati per la pagina, in pixel.<br>
     * NOTA BENE: i margini reali vengono attribuiti rispettando
     * i margini non stampabili della stampante utilizzata.<br>
     * Per determinare l'area stampabile e i margini reali, usare
     * sempre i metodi getUsableHeigth / getUsableWidth
     * getTopMarg / getBottomMarg
     */
    private static final int MARG_TOP = 40;

    private static final int MARG_BOTTOM = 50;

    private static final int MARG_LEFT = 30;

    private static final int MARG_RIGHT = 30;

    /**
     * coordinata verticale alla quale vengono stampate le etichette, in pixel
     * intesa come distanza dall'inizio della carta all'inizio dell'etichetta
     * (esclusa la eventuale dicitura dell'etichetta)
     * (la coordinata è riferita all'inizio della carta per evitare che i margini
     * influenzino il posizionamento dell'etichetta nella busta a finestra)
     */
    private static final int Y_LABELS = 150;

    /**
     * coordinate orizzontali delle due etichette riferite all'inizio della carta
     * (sono riferite all'inizio della carta per evitare che i margini
     * influenzino il posizionamento dell'etichetta nella busta a finestra)
     */
    private static final int X_LABEL1 = 45;

    private static final int X_LABEL2 = 320;

    /* larghezza fissa di ogni etichetta
    * (l'altezza è determinata dal contenuto) */
    private static final int W_LABEL = 220;

    /**
     * altezza minima di ogni etichetta
     * relativa solo alla parte indirizzo, esclusa dicitura)
     */
    private static final int H_MIN_LABEL = 60;

    /* font per le etichette visibili nella finestra */
    private static final Font FONT_LABEL = FontFactory.creaPrinterFont(11.5f);

    /* font per le diciture "destinatario/luogo destinazione" dell'etichetta */
    private static final Font FONT_DICITURE_DEST = FontFactory.creaPrinterFont(Font.BOLD, 7f);

    /* distanza verticale tra l'inizio della dicitura e l'inizio dell'etichetta */
    private static final int DIST_DICITURE_DEST = 30;

    /* larghezza delle colonne fisse (la colonna descrizione
     * è automatica e occupa tutto lo spazio rimasto)*/
    private static final int LAR_COL_UM = 30;

    private static final int LAR_COL_QTA = 70;

    private static final int LAR_COL_UNITARIO = 70;

    private static final int LAR_COL_SCONTO = 25;

    private static final int LAR_COL_IVA = 25;

    private static final int LAR_COL_TOTALE = 70;

    /* font per l'intestazione del documento se creato dai testi di preferenze */
    private static final Font FONT_INTESTAZIONE_DOC = FontFactory.creaPrinterFont(Font.BOLD, 8f);

    /* font per il voce del documento */
    private static final Font FONT_TITOLO_DOC = FontFactory.creaPrinterFont(Font.BOLD, 16f);

    /* font per i titoli delle celle di testata */
    private static final Font FONT_TITOLI_CELLE_TESTATA = FontFactory.creaPrinterFont(Font.BOLD,
            8f);

    /* font per le celle di testata */
    private static final Font FONT_CELLE_TESTATA = FontFactory.creaPrinterFont(9f);

    /* font le righe di fattura */
    private static final Font FONT_RIGHE = FontFactory.creaPrinterFont(9f);

    /* font i titoli delle righe di fattura */
    private static final Font FONT_TITOLI_RIGHE = FontFactory.creaPrinterFont(Font.BOLD, 9f);

    /* font per la tabella dei totali */
    private static final Font FONT_TOTALI = FontFactory.creaPrinterFont(10f);

    /* font per il totale generale nella tabella dei totali */
    private static final Font FONT_TOTALE_GEN = FontFactory.creaPrinterFont(Font.BOLD, 10f);

    /* font per la tabella riepilogo IVA */
    private static final Font FONT_RIEPILOGO = FontFactory.creaPrinterFont(8f);

    /* font per il voce della tabella riepilogo IVA */
    private static final Font FONT_TIT_RIEPILOGO = FontFactory.creaPrinterFont(Font.BOLD, 9f);

//    /* altezza dell'header */
//    private static final int ALTEZZA_HEADER = 80;

    /* altezza del footer */

    private static final int ALTEZZA_FOOTER = 24;

    /* font per il testo del footer */
    private static final Font FONT_FOOTER = FontFactory.creaPrinterFont(9f);

    /* modulo fattura */
    private static final FattBaseModulo MOD_FAT = FattModulo.get();

    /* modulo righe fattura */
    private static final RigaFatturaModulo MOD_RF = RigaFatturaModulo.get();

    /* modulo unità di misura */
    private static final UMModulo MOD_UM = UMModulo.get();

    /* modulo codici IVA */
    private static final IvaModulo MOD_IVA = IvaModulo.get();

    /* modulo codici pagamento */
    private static final TipoPagamentoModulo MOD_PAG = TipoPagamentoModulo.get();

    /* campi di uso comune */
    private static final Campo CAMPO_R_DESCRIZIONE =
            MOD_RF.getCampo(RigaFattura.Cam.descrizione.get());

    private static final Campo CAMPO_R_SIGLA_UM = MOD_UM.getCampo(UM.Cam.sigla.get());

    private static final Campo CAMPO_R_QTA = MOD_RF.getCampo(RigaFattura.Cam.quantita.get());

    private static final Campo CAMPO_R_UNITARIO =
            MOD_RF.getCampo(RigaFattura.Cam.prezzoUnitario.get());

    private static final Campo CAMPO_R_SCONTO = MOD_RF.getCampo(RigaFattura.Cam.percSconto.get());

    private static final Campo CAMPO_R_IMPONIBILE =
            MOD_RF.getCampo(RigaFattura.Cam.imponibile.get());

    private static final Campo CAMPO_R_CODIVA = MOD_RF.getCampo(RigaFattura.Cam.codIva.get());

    private static final Campo CAMPO_R_ALIQUOTAIVA = MOD_RF.getCampo(RigaFattura.Cam.percIva.get());

    private static final Campo CAMPO_I_IVABREVE = MOD_IVA.getCampo(Iva.Cam.codbreve.get());

    private static final Campo CAMPO_I_DESCRIVA = MOD_IVA.getCampo(Iva.Cam.descrizione.get());

    private static final Campo CAMPO_I_FLAG_NON_IMPO = MOD_IVA.getCampo(Iva.Cam.fuoricampo.get());

    private static final Campo CAMPO_I_PERCIVA = MOD_IVA.getCampo(Iva.Cam.valore.get());

    private static final Campo CAMPO_R_IMPORTOIVA =
            MOD_RF.getCampo(RigaFattura.Cam.importoIva.get());

//    private static final Campo CAMPO_R_TOTALE
//            = MOD_RF.getCampo(RigaFattura.Cam.totale.get());

    private static final Campo CAMPO_F_TIPO = MOD_FAT.getCampo(FattBase.Cam.tipodoc.get());

    private static final Campo CAMPO_F_NUMERO = MOD_FAT.getCampo(FattBase.Cam.numeroDoc.get());

    private static final Campo CAMPO_F_DATA = MOD_FAT.getCampo(FattBase.Cam.dataDoc.get());

    private static final Campo CAMPO_F_DESTINATARIO =
            MOD_FAT.getCampo(FattBase.Cam.destinatario.get());

    private static final Campo CAMPO_F_DESTINAZIONE =
            MOD_FAT.getCampo(FattBase.Cam.destinazione.get());

    private static final Campo CAMPO_F_PICF_CLIENTE = MOD_FAT.getCampo(FattBase.Cam.picf.get());

    private static final Campo CAMPO_F_DATA_SCADENZA =
            MOD_FAT.getCampo(FattBase.Cam.dataScadenza.get());

    private static final Campo CAMPO_F_USA_BANCA = MOD_FAT.getCampo(FattBase.Cam.usaBanca.get());

    private static final Campo CAMPO_F_BANCA = MOD_FAT.getCampo(FattBase.Cam.coordBanca.get());

    private static final Campo CAMPO_F_RIF_CLIENTE =
            MOD_FAT.getCampo(FattBase.Cam.rifCliente.get());

    private static final Campo CAMPO_F_RIF_NOSTRI = MOD_FAT.getCampo(FattBase.Cam.rifNostri.get());

    private static final Campo CAMPO_F_IMPONIBILE_LORDO =
            MOD_FAT.getCampo(FattBase.Cam.imponibileLordo.get());

    private static final Campo CAMPO_F_PERC_RIVALSA =
            MOD_FAT.getCampo(FattBase.Cam.percRivalsa.get());

    private static final Campo CAMPO_F_IMPORTO_RIVALSA =
            MOD_FAT.getCampo(FattBase.Cam.importoRivalsa.get());

    private static final Campo CAMPO_F_IMPONIBILE_NETTO =
            MOD_FAT.getCampo(FattBase.Cam.imponibileNetto.get());

    private static final Campo CAMPO_F_IMPORTO_IVA_NETTO =
            MOD_FAT.getCampo(FattBase.Cam.importoIva.get());

    private static final Campo CAMPO_F_TOTALE_LORDO =
            MOD_FAT.getCampo(FattBase.Cam.totaleLordo.get());

    private static final Campo CAMPO_F_PERC_RA = MOD_FAT.getCampo(FattBase.Cam.percRA.get());

    private static final Campo CAMPO_F_IMPORTO_RA = MOD_FAT.getCampo(FattBase.Cam.importoRA.get());

    private static final Campo CAMPO_F_TOTALE_NETTO =
            MOD_FAT.getCampo(FattBase.Cam.totaleNetto.get());

    private static final Campo CAMPO_PAG_DECRIZIONE =
            MOD_PAG.getCampo(TipoPagamento.Cam.descrizione.get());


    /* codice della fattura corrente */
    private int codice;

    /* voce del documento corrente */
    private String titoloDoc;

    /* numero del documento corrente */
    private int numDoc;

    /* data del documento corrente */
    private Date dataDoc;

    /* etichetta destinatario */
    private String labelDestinatario;

    /* etichetta destinazione */
    private String labelDestinazione;

    /* partita iva o codice fiscale cliente */
    private String picfCliente;

    /* stringa condizioni di pagamento */
    private String pagamento;

    /* data di scadenza */
    private Date dataScadenza;

    /* banca d'appoggio */
    private String banca;

    /* riferimenti cliente */
    private String rifCliente;

    /* riferimenti nostri */
    private String rifNostri;

    /* Oggetto dati contenente le righe */
    private Dati datiRighe;

    /* Oggetto dati per la tabella totali */
    private Dati datiTotali;

    /* Oggetto dati per la tabella riepilogo iva */
    private Dati datiRiepilogo;

    /* Printer della fattura corrente */
    private PrinterFattura printer;

    /* Pageable della pagina corrente */
    private J2Pageable printerPagina;

    /* Numero della pagina correntemente in stampa */
    private int numPagina;

    /* Numero di pagine della farrura corrente */
    private int quantePagine;

    /* componente stampabile per l'header */
    private Header header;

    /* componente stampabile per il footer */
    private Footer footer;

    /* componente stampabile per la testata */
    private Testata testata;

    /* componente stampabile per i totali */
    private Totali totali;

    /* gap dopo header */
    private Gap gapAfterHeader;

    /* gap prima della testata */
    private Gap gapBeforeTestata;

    /* gap dopo header */
    private Gap gapAfterTestata;

    /* gap dopo header */
    private Gap gapBeforeTotali;

    /* gap dopo header */
    private Gap gapBeforeFooter;

    /* flag - se la fattura corrente contiene righe con codici IVA diversi */
    private boolean ivaMista;

    /* percentuale IVA nel caso che sia unica per tutta la fattura */
    private int percIvaUnica;


    /**
     * Costruttore completo.
     */
    public FattBaseStampa() {

        /* rimanda al costruttore della superclasse */
        super();

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

        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea un oggetto Pageable per una fattura.
     * <p/>
     *
     * @param codice della fattura da stampare
     *
     * @return l'oggetto Pageable per la stampa
     */
    public Pageable creaPageable(int codice) {
        /* variabili e costanti locali di lavoro */
        Pageable pageable = null;
        boolean eseguito = false;
        int quantePag;

        try {    // prova ad eseguire il codice

            /* registra il codice */
            this.setCodice(codice);

            /* Carica e registra tutti i dati necessari */
            this.caricaDati();

            /* crea la stampa una prima volta per calcolare e
             * registrare il numero di pagine */
            quantePag = this.creaStampa();
            this.setQuantePagine(quantePag);

            /* crea la stampa definitiva */
            this.creaStampa();

            /* chiude i dati */
            this.getDatiRighe().close();
            this.getDatiRiepilogo().close();

//            PrinterJob pj = PrinterJob.getPrinterJob();
            pageable = this.getPrinter().getPageable();
//            pj.setPageable(pag);
//            pj.print();

            /* mostra il preview */
//            this.getPrinter().showPrintPreviewDialog();
//            this.getPrinter().print();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pageable;
    }


    /**
     * Crea la stampa della fattura corrente.
     * <p/>
     *
     * @return il numero totale di pagine create
     */
    private int creaStampa() {
        /* variabili e costanti locali di lavoro */
        int altDisponibileRighe;
        int altGruppo;
        int spazioRimasto;
        Gap gapVariabile;
        RigaDati riga;
        J2FlowPrinter flowPrinter;
        ArrayList<J2FlowPrinter> pagine = new ArrayList<J2FlowPrinter>();
        int numPagina;
        GruppoRighe gruppoRighe;

        try {    // prova ad eseguire il codice

            /* crea il printer della fattura e lo registra */
            PrinterFattura prifat = new PrinterFattura();
            this.setPrinter(prifat);

            int numRiga = 0;
            int quanteRigheTot = this.getQuanteRighe();

            /* ciclo fino all'accensione del flag fine */
            boolean fine = false;
            numPagina = 0;
            this.setNumPagina(numPagina);
            while (!fine) {

                numPagina++;
                this.setNumPagina(numPagina);

                /* crea e registra un nuovo FlowPrinter per la pagina */
                flowPrinter = new J2FlowPrinter();
                this.setPrinterPagina(flowPrinter);

                /* crea i componenti fissi per la pagina */
                this.creaCompFissi();

                /* dimensiona il gap variabile prima della testata
                * in modo che le etichette partano sempre alla
                * stessa distanza dall'inizio della carta */
                int mTop = this.getTopMarg();  // margine
                int hHead = this.getHeader().getPreferredSize().height;  // header
                int hGAH = this.getGapAfterHeader().getPreferredSize().height;  // gap
                int hGap = Y_LABELS - DIST_DICITURE_DEST - mTop - hHead - hGAH;

                /* se lo spazio non basta riduce automaticamente
                 * l'altezza dell'header quanto basta e pone il gap a zero */
                if (hGap < 0) {
                    hHead = hHead + hGap; // qui hGap è sempre negativo!
                    this.getHeader().setHeigth(hHead);
                    hGap = 0;
                }// fine del blocco if
                this.getGapBeforeTestata().setHeigth(hGap);

                /* recupera l'altezza disponibile per le righe */
                altDisponibileRighe = this.getAltDisponibileRighe();

                /* crea un nuovo gruppo di righe (include già la testatina) */
                gruppoRighe = new GruppoRighe();

                /* impostazione iniziale dei flag fineRighe e fine */
                boolean fineRighe;
                if (quanteRigheTot > 0) {
                    fineRighe = false;
                } else {
                    fineRighe = true;
                    fine = true;
                }// fine del blocco if-else

                /* ciclo fino all'accensione del flag fineRighe */
                while (!fineRighe) {

                    /* crea una nuova riga */
                    riga = new RigaDati(numRiga);

                    /* se la riga ci sta la aggiunge al gruppo
                     * se non ci sta, fine del gruppo */
                    if (gruppoRighe.ciStaRiga(riga, altDisponibileRighe)) {
                        gruppoRighe.addRiga(riga);
                        numRiga++;
                    } else {
                        fineRighe = true;
                    }// fine del blocco if-else

                    /* se siamo arrivati all'ultima riga, fine della stampa */
                    if (numRiga >= quanteRigheTot) {
                        fineRighe = true;
                        fine = true;
                    }// fine del blocco if

                }// fine del blocco while

                /* calcola lo spazio rimasto nella pagina
                 * e crea un gap di dimensioni pari a tale spazio */
                altGruppo = gruppoRighe.getPreferredSize().height;
                spazioRimasto = altDisponibileRighe - altGruppo;
                gapVariabile = new Gap(spazioRimasto);
                gapVariabile.setOpaque(false);
                if (DEBUG) {
                    gapVariabile.setOpaque(true);
                    gapVariabile.setBackground(Color.BLUE);
                }// fine del blocco if

                /**
                 * aggiunge i componenti alla stampa in funzione
                 * della pagina corrente
                 */
                flowPrinter.addFlowable(this.getHeader());
                flowPrinter.addFlowable(this.getGapAfterHeader());
                if (numPagina == 1) {
                    flowPrinter.addFlowable(this.getGapBeforeTestata());
                    flowPrinter.addFlowable(this.getTestata());
                    flowPrinter.addFlowable(this.getGapAfterTestata());
                }// fine del blocco if
                flowPrinter.addFlowable(gruppoRighe);
                flowPrinter.addFlowable(gapVariabile);
                flowPrinter.addFlowable(this.getGapBeforeTotali());
                flowPrinter.addFlowable(this.getTotali());
                flowPrinter.addFlowable(this.getGapBeforeFooter());
                flowPrinter.addFlowable(this.getFooter());
                pagine.add(flowPrinter);

            }// fine del blocco while

            /* crea la stampa completa */
            for (int k = 0; k < pagine.size(); k++) {
                J2FlowPrinter pag = pagine.get(k);
                if (k < pagine.size() - 1) {
                    pag.addFlowable(new PageEject());
                }// fine del blocco if

                /**
                 * Forza il pageable a starci su una sola pagina
                 * (eventualmente lo scala)
                 * Questo comando in teoria non è necessario poiché la
                 * pagina dovrebbe occupare esattamente tutta l'area
                 * stampabile. Se però ci sono degli errori di qualche
                 * pixel per arrotondamenti o altri motivi, la pagina
                 * viene comunque forzata a stampare su un solo foglio.
                 */
                pag.setMaximumPages(1, 1);

                this.getPrinter().addPageable(pag);
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pagine.size();
    }


    /**
     * Crea e registra i componenti fissi per una data pagina.
     * <p/>
     */
    private void creaCompFissi() {
        /* variabili e costanti locali di lavoro */
        Header header;
        Footer footer;
        Testata testata;
        Totali totali;
        Gap gap;

        try {    // prova ad eseguire il codice

            /* crea l'header */
            header = new Header();
            this.setHeader(header);

            /* crea il gap dopo l'header */
            gap = new Gap(10);
            gap.setBackground(Color.PINK);  // agisce solo in debug
            this.setGapAfterHeader(gap);

            /* crea il gap variabile prima della testata */
            gap = new Gap(10);
            gap.setBackground(Color.blue);  // agisce solo in debug
            this.setGapBeforeTestata(gap);

            /* crea l'area testata */
            testata = new Testata(this.getNumPagina());
            this.setTestata(testata);

            /* crea il gap dopo la testata */
            gap = new Gap(10);
            this.setGapAfterTestata(gap);

            /* crea il gap prima dei totali */
            gap = new Gap(10);
            this.setGapBeforeTotali(gap);

            /* crea l'area totali */
            totali = new Totali();
            this.setTotali(totali);

            /* crea il gap prima del footer */
            gap = new Gap(10, SwingConstants.BOTTOM);
            this.setGapBeforeFooter(gap);

            /* crea il footer */
            footer = new Footer();
            this.setFooter(footer);


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Carica e registra gli oggetti con i dati necessari.
     * <p/>
     */
    private void caricaDati() {

        try {    // prova ad eseguire il codice

            this.caricaDatiFattura();
            this.caricaDatiRighe();
            this.caricaDatiRiepilogo();
            this.caricaDatiTotali();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Carica e registra i dati generali della fattura.
     * <p/>
     */
    private void caricaDatiFattura() {
        /* variabili e costanti locali di lavoro */
        Dati dati = null;
        int codice;
        Query query;
        Filtro filtro;
        String titolo;
        String stringa;

        try {    // prova ad eseguire il codice

            codice = this.getCodice();

            filtro = FiltroFactory.codice(MOD_FAT, codice);

            query = new QuerySelezione(MOD_FAT);
            query.addCampo(CAMPO_F_TIPO);
            query.addCampo(CAMPO_F_NUMERO);
            query.addCampo(CAMPO_F_DATA);
            query.addCampo(CAMPO_F_DESTINATARIO);
            query.addCampo(CAMPO_F_DESTINAZIONE);
            query.addCampo(CAMPO_F_PICF_CLIENTE);
            query.addCampo(CAMPO_PAG_DECRIZIONE);
            query.addCampo(CAMPO_F_DATA_SCADENZA);
            query.addCampo(CAMPO_F_USA_BANCA);
            query.addCampo(CAMPO_F_BANCA);
            query.addCampo(CAMPO_F_RIF_CLIENTE);
            query.addCampo(CAMPO_F_RIF_NOSTRI);
            query.setFiltro(filtro);

            dati = MOD_FAT.query().querySelezione(query);

            /* recupera e registra voce documento */
            titolo = FattBaseModulo.getTitolo(dati.getIntAt(0, CAMPO_F_TIPO));
            this.setTitoloDoc(titolo);

            /* registra i dati di testa della fattura */
            this.setNumDoc(dati.getIntAt(0, CAMPO_F_NUMERO));
            this.setDataDoc(dati.getDataAt(0, CAMPO_F_DATA));
            this.setLabelDestinatario(dati.getStringAt(0, CAMPO_F_DESTINATARIO));
            this.setLabelDestinazione(dati.getStringAt(0, CAMPO_F_DESTINAZIONE));
            this.setPicfCliente(dati.getStringAt(0, CAMPO_F_PICF_CLIENTE));
            this.setPagamento(dati.getStringAt(0, CAMPO_PAG_DECRIZIONE));
            this.setDataScadenza(dati.getDataAt(0, CAMPO_F_DATA_SCADENZA));

            /* regola la banca se il pagamento lo prevede */
            stringa = "";
            if (dati.getBoolAt(0, CAMPO_F_USA_BANCA)) {
                stringa = dati.getStringAt(0, CAMPO_F_BANCA);
            }// fine del blocco if
            this.setBanca(stringa);

            this.setRifCliente(dati.getStringAt(0, CAMPO_F_RIF_CLIENTE));
            this.setRifNostri(dati.getStringAt(0, CAMPO_F_RIF_NOSTRI));

            dati.close();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Carica e registra l'oggetto dati con i valori delle righe.
     * <p/>
     */
    private void caricaDatiRighe() {
        /* variabili e costanti locali di lavoro */
        Dati dati = null;
        int codice;
        Modulo modRighe;
        Query query;
        Filtro filtro;
        Ordine ordine;
        Campo campoLink;
        Campo campoOrdine;

        try {    // prova ad eseguire il codice

            codice = this.getCodice();
            modRighe = MOD_RF;
            campoLink = modRighe.getCampo(RigaFattura.Cam.fattura.get());
            campoOrdine = modRighe.getCampoOrdine();
            filtro = FiltroFactory.crea(campoLink, codice);
            ordine = new Ordine();
            ordine.add(campoOrdine);

            query = new QuerySelezione(modRighe);
            query.addCampo(CAMPO_R_DESCRIZIONE);
            query.addCampo(CAMPO_R_SIGLA_UM);
            query.addCampo(CAMPO_R_QTA);
            query.addCampo(CAMPO_R_UNITARIO);
            query.addCampo(CAMPO_R_SCONTO);
            query.addCampo(CAMPO_R_IMPONIBILE);
            query.addCampo(CAMPO_R_CODIVA);
            query.addCampo(CAMPO_I_IVABREVE);
            query.addCampo(CAMPO_R_IMPORTOIVA);
            query.addCampo(CAMPO_I_FLAG_NON_IMPO);

            query.setFiltro(filtro);
            query.setOrdine(ordine);

            dati = modRighe.query().querySelezione(query);

            this.setDatiRighe(dati);

            /* Regola il flag indicatore di IVA mista e l'eventuale
             * percentuale di IVA unica */
            this.regolaIvaMista();


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Carica e registra l'oggetto dati con i valori dei totali.
     * <p/>
     */
    private void caricaDatiTotali() {
        /* variabili e costanti locali di lavoro */
        Dati dati = null;
        DatiMemoria datiTot = null;
        ArrayList<Campo> campi = new ArrayList<Campo>();
        ArrayList<Campo> campiTot = new ArrayList<Campo>();
        int codice;
        FattBaseModulo modFat;
        Query query;
        Filtro filtro;
        int indice;
        Double valore;
        String titIva;
        Campo campoDesc;
        Campo campoVal;
        double percDouble;
        int percInt;


        try {    // prova ad eseguire il codice

            /* carica i valori dei totali */
            codice = this.getCodice();
            modFat = MOD_FAT;

            campi.add(CAMPO_F_IMPONIBILE_LORDO);
            campi.add(CAMPO_F_PERC_RIVALSA);
            campi.add(CAMPO_F_IMPORTO_RIVALSA);
            campi.add(CAMPO_F_IMPONIBILE_NETTO);
            campi.add(CAMPO_F_IMPORTO_IVA_NETTO);
            campi.add(CAMPO_F_TOTALE_LORDO);
            campi.add(CAMPO_F_PERC_RA);
            campi.add(CAMPO_F_IMPORTO_RA);
            campi.add(CAMPO_F_TOTALE_NETTO);

            filtro = FiltroFactory.codice(modFat, codice);
            query = new QuerySelezione(modFat);
            query.setCampi(campi);
            query.setFiltro(filtro);
            dati = modFat.query().querySelezione(query);

            /* crea i dati per la tabella */
            campoDesc = CampoFactory.testo("descrizione");
            campoVal = CampoFactory.testo("valore");
            campiTot.add(campoDesc);
            campiTot.add(campoVal);
            datiTot = new DatiMemoria(campiTot, 0);

            /* aggiunge le righe alla tabella */

            /**
             * Righe imponibile:
             * - se c'è rivalsa INPS, aggiunge le righe
             * imponibile lordo, rivalsa, imponibile netto
             * - se non c'è rivalsa INPS, solo imponibile netto
             */
            valore = dati.getDoubleAt(0, CAMPO_F_IMPORTO_RIVALSA);
            if (valore != 0) {

                indice = datiTot.addRiga();
                valore = dati.getDoubleAt(0, CAMPO_F_IMPONIBILE_LORDO);
                datiTot.setValueAt(indice, campoDesc, "Imponibile");
                datiTot.setValueAt(indice, campoVal, valore);

                indice = datiTot.addRiga();
                valore = dati.getDoubleAt(0, CAMPO_F_IMPORTO_RIVALSA);
                percDouble = dati.getDoubleAt(0, CAMPO_F_PERC_RIVALSA);
                percInt = Libreria.getInt(percDouble * 100);
                datiTot.setValueAt(indice, campoDesc, "Rivalsa INPS " + percInt + "%");
                datiTot.setValueAt(indice, campoVal, valore);

                indice = datiTot.addRiga();
                valore = dati.getDoubleAt(0, CAMPO_F_IMPONIBILE_NETTO);
                datiTot.setValueAt(indice, campoDesc, "Totale imponibile");
                datiTot.setValueAt(indice, campoVal, valore);

            } else {

                /**
                 * non c'è rivalsa INPS, potrebbe essere un'azienda,
                 * suddivide per chiarezza l'imponibile in imponibile
                 * e non imponibile. Non sarebbe obbligatorio perché
                 * comunque c'è il riepilogo IVA, ma è più gradito da
                 * chi esamina la fattura.
                 */
                Dati datiRighe = this.getDatiRighe();
                double totNonImpo = 0;
                double totImpo = 0;
                int numDec = CAMPO_F_IMPONIBILE_NETTO.getCampoDati().getNumDecimali();
                for (int k = 0; k < datiRighe.getRowCount(); k++) {
                    double importo = datiRighe.getDoubleAt(k, CAMPO_R_IMPONIBILE);
                    boolean flag = datiRighe.getBoolAt(k, CAMPO_I_FLAG_NON_IMPO);
                    if (flag) {
                        totNonImpo += importo;
                        totNonImpo = Lib.Mat.arrotonda(totNonImpo, numDec);
                    } else {
                        totImpo += importo;
                        totImpo = Lib.Mat.arrotonda(totImpo, numDec);
                    }// fine del blocco if-else
                } // fine del ciclo for

                indice = datiTot.addRiga();
                datiTot.setValueAt(indice, campoDesc, "Imponibile");
                datiTot.setValueAt(indice, campoVal, totImpo);

                if (totNonImpo != 0) {
                    indice = datiTot.addRiga();
                    datiTot.setValueAt(indice, campoDesc, "Non imponibile");
                    datiTot.setValueAt(indice, campoVal, totNonImpo);
                }// fine del blocco if

            }// fine del blocco if-else

            /* IVA netto, sempre */
            titIva = "IVA";
            if (!isIvaMista()) {
                titIva += " " + getPercIvaUnica() + "%";
            }// fine del blocco if-else
            indice = datiTot.addRiga();
            valore = dati.getDoubleAt(0, CAMPO_F_IMPORTO_IVA_NETTO);
            datiTot.setValueAt(indice, campoDesc, titIva);
            datiTot.setValueAt(indice, campoVal, valore);

            /**
             * Righe totale:
             * - Se c'è ritenuta d'acconto, aggiumge le righe
             * totale lordo, ritenuta, totale netto
             * - Se non c'è ritenuta, solo totale netto
             */
            valore = dati.getDoubleAt(0, CAMPO_F_IMPORTO_RA);
            if (valore != 0) {

                indice = datiTot.addRiga();
                valore = dati.getDoubleAt(0, CAMPO_F_TOTALE_LORDO);
                datiTot.setValueAt(indice, campoDesc, "Totale " + this.getTitoloDoc());
                datiTot.setValueAt(indice, campoVal, valore);

                indice = datiTot.addRiga();
                valore = dati.getDoubleAt(0, CAMPO_F_IMPORTO_RA);
                percDouble = dati.getDoubleAt(0, CAMPO_F_PERC_RA);
                percInt = Libreria.getInt(percDouble * 100);
                datiTot.setValueAt(indice, campoDesc, "Ritenuta d'acconto " + percInt + "%");
                datiTot.setValueAt(indice, campoVal, -valore);

                indice = datiTot.addRiga();
                valore = dati.getDoubleAt(0, CAMPO_F_TOTALE_NETTO);
                datiTot.setValueAt(indice, campoDesc, "Totale a pagare");
                datiTot.setValueAt(indice, campoVal, valore);

            } else {

                indice = datiTot.addRiga();
                valore = dati.getDoubleAt(0, CAMPO_F_TOTALE_NETTO);
                datiTot.setValueAt(indice, campoDesc, "Totale");
                datiTot.setValueAt(indice, campoVal, valore);

            }// fine del blocco if-else

            dati.close();

            /* registra i dati */
            this.setDatiTotali(datiTot);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Carica e registra l'oggetto dati per la tabella riepilogo IVA.
     * <p/>
     */
    private void caricaDatiRiepilogo() {
        /* variabili e costanti locali di lavoro */
        Dati datiRighe = null;
        DatiMemoria datiTab = null;
        ArrayList<Campo> campiTab = new ArrayList<Campo>();
        int quanteRighe;
        int indice;
        int codIva;
        double impoRiga;
        double ivaRiga;
        double impoCorrente;
        double impoNuovo;
        double ivaCorrente;
        double ivaNuovo;
        int codIvaTab;
        int indiceTab;
        ArrayList oggetti;
        Object oggetto;

        try {    // prova ad eseguire il codice

            /* crea un oggetto Dati Memoria */
            campiTab.add(CAMPO_R_CODIVA);
            campiTab.add(CAMPO_I_IVABREVE);
            campiTab.add(CAMPO_R_IMPONIBILE);
            campiTab.add(CAMPO_I_PERCIVA);
            campiTab.add(CAMPO_R_IMPORTOIVA);
            campiTab.add(CAMPO_I_DESCRIVA);
            datiTab = new DatiMemoria(campiTab);

            datiRighe = this.getDatiRighe();
            quanteRighe = datiRighe.getRowCount();
            for (int k = 0; k < quanteRighe; k++) {
                codIva = datiRighe.getIntAt(k, CAMPO_R_CODIVA);
                impoRiga = datiRighe.getDoubleAt(k, CAMPO_R_IMPONIBILE);
                ivaRiga = datiRighe.getDoubleAt(k, CAMPO_R_IMPORTOIVA);

                /* ricerca l'indice della riga di tabella con
                 * questo codice iva, -1 se non trovato */
                indiceTab = -1;
                oggetti = datiTab.getValoriColonna(CAMPO_R_CODIVA);
                for (int j = 0; j < oggetti.size(); j++) {
                    oggetto = oggetti.get(j);
                    if (oggetto != null) {
                        if (oggetto instanceof Integer) {
                            codIvaTab = (Integer)oggetto;
                            if (codIvaTab == codIva) {
                                indiceTab = j;
                                break;
                            }// fine del blocco if
                        }// fine del blocco if
                    }// fine del blocco if
                } // fine del ciclo for

                /* se esiste, incrementa l'importo
        * se non esiste, aggiunge una nuova riga */
                if (indiceTab != -1) {
                    impoCorrente = datiTab.getDoubleAt(indiceTab, CAMPO_R_IMPONIBILE);
                    impoNuovo = impoCorrente + impoRiga;
                    ivaCorrente = datiTab.getDoubleAt(indiceTab, CAMPO_R_IMPORTOIVA);
                    ivaNuovo = ivaCorrente + ivaRiga;
                    datiTab.setValueAt(indiceTab, CAMPO_R_IMPONIBILE, impoNuovo);
                    datiTab.setValueAt(indiceTab, CAMPO_R_IMPORTOIVA, ivaNuovo);
                } else {

                    /* recupera i dati dalla tabella IVA */
                    Query query = new QuerySelezione(MOD_IVA);
                    query.addCampo(CAMPO_I_IVABREVE);
                    query.addCampo(CAMPO_I_PERCIVA);
                    query.addCampo(CAMPO_I_DESCRIVA);
                    Filtro filtro = FiltroFactory.codice(MOD_IVA, codIva);
                    query.setFiltro(filtro);
                    Dati dati = MOD_IVA.query().querySelezione(query);
                    String codBreve = dati.getStringAt(0, CAMPO_I_IVABREVE);
                    double percIva = dati.getDoubleAt(0, CAMPO_I_PERCIVA);
                    String descIva = dati.getStringAt(0, CAMPO_I_DESCRIVA);
                    dati.close();

                    /* crea la riga */
                    indice = datiTab.addRiga();
                    datiTab.setValueAt(indice, CAMPO_R_CODIVA, codIva);
                    datiTab.setValueAt(indice, CAMPO_I_IVABREVE, codBreve);
                    datiTab.setValueAt(indice, CAMPO_R_IMPONIBILE, impoRiga);
                    datiTab.setValueAt(indice, CAMPO_I_PERCIVA, Libreria.getInt(percIva * 100));
                    datiTab.setValueAt(indice, CAMPO_R_IMPORTOIVA, ivaRiga);
                    datiTab.setValueAt(indice, CAMPO_I_DESCRIVA, descIva);

                }// fine del blocco if-else
            } // fine del ciclo for

            /* registra l'oggetto dati */
            this.setDatiRiepilogo(datiTab);


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * controlla se le righe hanno codici IVA misti
     * e regola l'apposito flag
     * <p/>
     */
    private void regolaIvaMista() {
        /* variabili e costanti locali di lavoro */
        boolean mista = false;
        Dati dati;
        ArrayList valori;
        int codice = 0;
        int oldcodice = 0;
        Object unValore;
        Double valIva;
        int percIva;

        try {    // prova ad eseguire il codice
            dati = this.getDatiRighe();
            valori = dati.getValoriColonna(CAMPO_R_CODIVA);

            for (int k = 0; k < valori.size(); k++) {
                unValore = valori.get(k);
                if (unValore != null) {
                    if (unValore instanceof Integer) {
                        codice = (Integer)unValore;

                        /* la prima volta è uguale */
                        if (k == 0) {
                            oldcodice = codice;
                        }// fine del blocco if

                        if (codice != oldcodice) {
                            mista = true;
                            break;
                        }// fine del blocco if
                        oldcodice = codice;

                    }// fine del blocco if
                }// fine del blocco if

            } // fine del ciclo for

            /* regola il flag */
            this.setIvaMista(mista);

            /* se l'IVA non è mista, regola la percentuale di iva unica */
            this.setPercIvaUnica(0);
            if (!this.isIvaMista()) {
                valIva = MOD_IVA.query().valoreDouble(CAMPO_I_PERCIVA, codice);
                percIva = Libreria.getInt(valIva * 100);
                this.setPercIvaUnica(percIva);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Controlla se la fattura contiene righe con sconto.
     * <p/>
     *
     * @return true se contiene righe con sconto
     */
    private boolean isEsisteSconto() {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        Dati dati;
        ArrayList valori;
        double num;

        try {    // prova ad eseguire il codice
            dati = this.getDatiRighe();
            valori = dati.getValoriColonna(CAMPO_R_SCONTO);
            for (Object valore : valori) {
                if (valore != null) {
                    if (valore instanceof Double) {
                        num = (Double)valore;
                        if (num != 0) {
                            esiste = true;
                        }// fine del blocco if

                    }// fine del blocco if
                }// fine del blocco if
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Controlla se la fattura contiene righe con unità di misura.
     * <p/>
     *
     * @return true se contiene righe con unità di misura
     */
    private boolean isEsisteUM() {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        Dati dati;
        ArrayList valori;
        String testo;

        try {    // prova ad eseguire il codice
            dati = this.getDatiRighe();
            valori = dati.getValoriColonna(CAMPO_R_SIGLA_UM);
            for (Object valore : valori) {
                if (valore != null) {
                    if (valore instanceof String) {
                        testo = (String)valore;
                        if (Lib.Testo.isValida(testo)) {
                            esiste = true;
                        }// fine del blocco if

                    }// fine del blocco if
                }// fine del blocco if
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    private class PrintingStatusListener implements java.beans.PropertyChangeListener {

        J2Printer printer;


        /**
         * Costruttore base senza parametri. <br>
         * Indispensabile anche se non viene utilizzato
         * (anche solo per compilazione in sviluppo) <br>
         * Rimanda al costruttore completo <br>
         * Utilizza eventuali valori di default <br>
         */
        public PrintingStatusListener(J2Printer printer) {
            /* rimanda al costruttore di questa classe */
            this.printer = printer;
        }// fine del metodo costruttore base


        public void propertyChange(java.beans.PropertyChangeEvent evt) {

            int c = 87;
            if (evt.getPropertyName().equals("printingUnderway")) {
                if (printer.isPrintingUnderway()) {
                } // printing has started
                else {
                }                              // printing is done
            }

            if (evt.getPropertyName().equals("printDialogResponse")) {
                if (printer.isPrintDialogResponse()) {
                }  // if user said OK
                else {
                }                                  // if user said cancel
            }

            if (evt.getPropertyName().equals("pageBeingPrinted")) {
                int a = 87;
                int pag = printer.getPageBeingPrinted();
                // also get this event during printPreview, so test
                // isPrintingUnderway()==true to make sure we really are printing
                if (printer.isPrintingUnderway() && printer.getPageBeingPrinted() > 0) {
                    int b = 87;
                }
            }
        }
    }


    private class MyPrintingEventHandler extends PrintingEventHandler {

        int totalPages;

        J2Printer printer;


        /**
         * Costruttore base senza parametri. <br>
         */
        public MyPrintingEventHandler(J2Printer printer) {
            this.printer = printer;
        }// fine del metodo costruttore base


        public void printingStart() {
            System.out.println("About to print...");
            totalPages = printer.getPageable()
                    .getNumberOfPages(); // expensive so do only once
        }


        public void printingDone() {
            System.out.println("Printing done");
        }


        public void printDialogOK() {
            System.out.println("Start printing...");
        }


        public void printDialogCanceled() {
            System.out.println("Printing canceled");
        }


        public void pageStart(int pageNum) {
            System.out.println("Printing page " + pageNum + " of " + totalPages);
        }


        public void exceptionThrown(Exception e) {
            System.out.println("Got an exception: " + e);
        }

    }


    /**
     * Ritorna il margine superiore della pagina corrente.
     * <p/>
     * Non è mai inferiore al margine non stampabile
     *
     * @return il margine superiore
     */
    private int getTopMarg() {
        /* variabili e costanti locali di lavoro */
        int marg = 0;
        double margInch;
        J2Printer printer;

        try {    // prova ad eseguire il codice

            printer = this.getPrinter();
            if (printer != null) {
                margInch = printer.getTopMargin();
                marg = Libreria.getInt(margInch * 72);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return marg;
    }


    /**
     * Ritorna il margine inferiore della pagina corrente.
     * <p/>
     * Non è mai inferiore al margine non stampabile
     *
     * @return il margine inferiore
     */
    private int getBottomMarg() {
        /* variabili e costanti locali di lavoro */
        int marg = 0;
        double margInch;
        J2Printer printer;

        try {    // prova ad eseguire il codice

            printer = this.getPrinter();
            if (printer != null) {
                margInch = printer.getBottomMargin();
                marg = Libreria.getInt(margInch * 72);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return marg;
    }


    /**
     * Ritorna il margine sinistro della pagina corrente.
     * <p/>
     * Non è mai inferiore al margine non stampabile
     *
     * @return il margine sinistro
     */
    private int getLeftMarg() {
        /* variabili e costanti locali di lavoro */
        int marg = 0;
        double margInch;
        J2Printer printer;

        try {    // prova ad eseguire il codice

            printer = this.getPrinter();
            if (printer != null) {
                margInch = printer.getLeftMargin();
                marg = Libreria.getInt(margInch * 72);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return marg;
    }


    /**
     * Ritorna il margine destro della pagina corrente.
     * <p/>
     * Non è mai inferiore al margine non stampabile
     *
     * @return il margine destro
     */
    private int getRightMarg() {
        /* variabili e costanti locali di lavoro */
        int marg = 0;
        double margInch;
        J2Printer printer;

        try {    // prova ad eseguire il codice

            printer = this.getPrinter();
            if (printer != null) {
                margInch = printer.getRightMargin();
                marg = Libreria.getInt(margInch * 72);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return marg;
    }


    /**
     * Ritorna la larghezza stampabile della pagina corrente.
     * <p/>
     * E' la larghezza disponibile esclusi i margini
     *
     * @return la larghezza stampabile
     */
    private int getUsableWidth() {
        /* variabili e costanti locali di lavoro */
        int larghezza = 0;
        J2Pageable printerPagina;

        try {    // prova ad eseguire il codice

            printerPagina = this.getPrinterPagina();
            if (printerPagina != null) {
                larghezza = (int)printerPagina.getBodyWidth();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return larghezza;
    }


    /**
     * Ritorna l'altezza stampabile della pagina corrente.
     * <p/>
     * E' l'altezza disponibile esclusi i margini
     *
     * @return l'altezza stampabile
     */
    private int getUsableHeight() {
        /* variabili e costanti locali di lavoro */
        int altezza = 0;
        J2Pageable printerPagina;

        try {    // prova ad eseguire il codice

            printerPagina = this.getPrinterPagina();
            if (printerPagina != null) {
                altezza = (int)printerPagina.getBodyHeight(J2Pageable.FIRST);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return altezza;
    }


    /**
     * Ritorna l'altezza disponibile per le righe della fattura.
     * <p/>
     * L'altezza è calcolata sottraendo dall'altezza della pagina
     * l'altezza dei margini e di tutte le parti fisse.
     *
     * @return l'altezza disponibile per la stampa delle righe
     */
    private int getAltDisponibileRighe() {
        /* variabili e costanti locali di lavoro */
        int numPag;
        int altezzaRighe = 0;
        int altezzaSenzaMargini = 0;
        int altezzaHeader = 0;
        int altezzaFooter = 0;
        int altezzaTestata = 0;
        int altezzaTotali = 0;
        int gapAfterHeader = 0;
        int gapBeforeTestata = 0;
        int gapAfterTestata = 0;
        int gapBeforeTotali = 0;
        int gapBeforeFooter = 0;

        try {    // prova ad eseguire il codice

            numPag = getNumPagina();

            /* recupera l'altezza esclusi i margini */
            altezzaSenzaMargini = this.getUsableHeight();

            /* recupera l'altezza di header e footer */
            altezzaHeader = this.getHeader().getPreferredSize().height;
            altezzaFooter = this.getFooter().getPreferredSize().height;

            /* recupera l'altezza di testata e totali */
            altezzaTestata = this.getTestata().getPreferredSize().height;
            altezzaTotali = this.getTotali().getPreferredSize().height;

            /* recupera l'altezza dei gap */
            gapAfterHeader = this.getGapAfterHeader().getPreferredSize().height;
            gapBeforeTestata = this.getGapBeforeTestata().getPreferredSize().height;
            gapAfterTestata = this.getGapAfterTestata().getPreferredSize().height;
            gapBeforeTotali = this.getGapBeforeTotali().getPreferredSize().height;
            gapBeforeFooter = this.getGapBeforeFooter().getPreferredSize().height;

            /* effettua il calcolo */
            altezzaRighe = altezzaSenzaMargini;
            altezzaRighe -= altezzaHeader;
            altezzaRighe -= gapAfterHeader;

            if (numPag == 1) {
                altezzaRighe -= gapBeforeTestata;
                altezzaRighe -= altezzaTestata;
                altezzaRighe -= gapAfterTestata;
            }// fine del blocco if

            altezzaRighe -= gapBeforeTotali;
            altezzaRighe -= altezzaTotali;
            altezzaRighe -= gapBeforeFooter;
            altezzaRighe -= altezzaFooter;

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return altezzaRighe;
    }


    /**
     * Controlla se si sta stampando l'ultima pagina
     * della fattura.
     * <p/>
     *
     * @return true se è l'ultima pagina
     */
    private boolean isUltimaPagina() {
        /* variabili e costanti locali di lavoro */
        boolean ultima = false;
        int numPagina;
        int quantePagine;

        try {    // prova ad eseguire il codice
            numPagina = this.getNumPagina();
            quantePagine = this.getQuantePagine();
            if (numPagina > 0) {
                if (numPagina == quantePagine) {
                    ultima = true;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return ultima;
    }


    /**
     * Ritorna il numero di righe del documento corrente
     * <p/>
     *
     * @return il numero di righe
     */
    private int getQuanteRighe() {
        /* variabili e costanti locali di lavoro */
        int quante = 0;
        Dati dati;

        try {    // prova ad eseguire il codice
            dati = this.getDatiRighe();
            if (dati != null) {
                quante = dati.getRowCount();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return quante;
    }

    //    /**
//     * Recupera l'header di stampa
//     * <p/>
//     *
//     * @return l'header di stampa
//     */
//    private HeaderOld getHeaderOld() {
//        /* variabili e costanti locali di lavoro */
//        HeaderOld header = null;
//        PrinterFattura printer;
//
//        try { // prova ad eseguire il codice
//            printer = this.getPrinter();
//            if (printer != null) {
//                header = printer.getHeader();
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return header;
//    }

//    /**
//     * Recupera il footer di stampa
//     * <p/>
//     *
//     * @return il footer di stampa
//     */
//    private FooterOld getFooterOld() {
//        /* variabili e costanti locali di lavoro */
//        FooterOld footer = null;
//        PrinterFattura printer;
//
//        try { // prova ad eseguire il codice
//            printer = this.getPrinter();
//            if (printer != null) {
//                footer = printer.getFooter();
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return footer;
//    }


    private int getCodice() {
        return codice;
    }


    private void setCodice(int codice) {
        this.codice = codice;
    }


    private String getTitoloDoc() {
        return titoloDoc;
    }


    private void setTitoloDoc(String titoloDoc) {
        this.titoloDoc = titoloDoc;
    }


    private int getNumDoc() {
        return numDoc;
    }


    private void setNumDoc(int numDoc) {
        this.numDoc = numDoc;
    }


    private Date getDataDoc() {
        return dataDoc;
    }


    private void setDataDoc(Date dataDoc) {
        this.dataDoc = dataDoc;
    }


    private String getLabelDestinatario() {
        return labelDestinatario;
    }


    private void setLabelDestinatario(String labelDestinatario) {
        this.labelDestinatario = labelDestinatario;
    }


    private String getLabelDestinazione() {
        return labelDestinazione;
    }


    private void setLabelDestinazione(String labelDestinazione) {
        this.labelDestinazione = labelDestinazione;
    }


    private String getPicfCliente() {
        return picfCliente;
    }


    private void setPicfCliente(String picfCliente) {
        this.picfCliente = picfCliente;
    }


    private String getPagamento() {
        return pagamento;
    }


    private void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }


    private Date getDataScadenza() {
        return dataScadenza;
    }


    private void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }


    private String getBanca() {
        return banca;
    }


    private void setBanca(String banca) {
        this.banca = banca;
    }


    private String getRifCliente() {
        return rifCliente;
    }


    private void setRifCliente(String rifCliente) {
        this.rifCliente = rifCliente;
    }


    private String getRifNostri() {
        return rifNostri;
    }


    private void setRifNostri(String rifNostri) {
        this.rifNostri = rifNostri;
    }


    private Dati getDatiRighe() {
        return datiRighe;
    }


    private void setDatiRighe(Dati datiRighe) {
        this.datiRighe = datiRighe;
    }


    private Dati getDatiTotali() {
        return datiTotali;
    }


    private void setDatiTotali(Dati datiTotali) {
        this.datiTotali = datiTotali;
    }


    private Dati getDatiRiepilogo() {
        return datiRiepilogo;
    }


    private void setDatiRiepilogo(Dati datiRiepilogo) {
        this.datiRiepilogo = datiRiepilogo;
    }


    private PrinterFattura getPrinter() {
        return printer;
    }


    private void setPrinter(PrinterFattura printer) {
        this.printer = printer;
    }


    private J2Pageable getPrinterPagina() {
        return printerPagina;
    }


    private void setPrinterPagina(J2Pageable printerPagina) {
        this.printerPagina = printerPagina;
    }


    private int getNumPagina() {
        return numPagina;
    }


    private void setNumPagina(int numPagina) {
        this.numPagina = numPagina;
    }


    private int getQuantePagine() {
        return quantePagine;
    }


    private void setQuantePagine(int quantePagine) {
        this.quantePagine = quantePagine;
    }


    private Header getHeader() {
        return header;
    }


    private void setHeader(Header header) {
        this.header = header;
    }


    private Footer getFooter() {
        return footer;
    }


    private void setFooter(Footer footer) {
        this.footer = footer;
    }


    private Testata getTestata() {
        return testata;
    }


    private void setTestata(Testata testata) {
        this.testata = testata;
    }


    private Totali getTotali() {
        return totali;
    }


    private void setTotali(Totali totali) {
        this.totali = totali;
    }


    private Gap getGapAfterHeader() {
        return gapAfterHeader;
    }


    private void setGapAfterHeader(Gap gapAfterHeader) {
        this.gapAfterHeader = gapAfterHeader;
    }


    private Gap getGapBeforeTestata() {
        return gapBeforeTestata;
    }


    private void setGapBeforeTestata(Gap gapBeforeTestata) {
        this.gapBeforeTestata = gapBeforeTestata;
    }


    private Gap getGapAfterTestata() {
        return gapAfterTestata;
    }


    private void setGapAfterTestata(Gap gapAfterTestata) {
        this.gapAfterTestata = gapAfterTestata;
    }


    private Gap getGapBeforeTotali() {
        return gapBeforeTotali;
    }


    private void setGapBeforeTotali(Gap gapBeforeTotali) {
        this.gapBeforeTotali = gapBeforeTotali;
    }


    private Gap getGapBeforeFooter() {
        return gapBeforeFooter;
    }


    private void setGapBeforeFooter(Gap gapBeforeFooter) {
        this.gapBeforeFooter = gapBeforeFooter;
    }


    private boolean isIvaMista() {
        return ivaMista;
    }


    private void setIvaMista(boolean ivaMista) {
        this.ivaMista = ivaMista;
    }


    private int getPercIvaUnica() {
        return percIvaUnica;
    }


    private void setPercIvaUnica(int percIvaUnica) {
        this.percIvaUnica = percIvaUnica;
    }


    /**
     * Classe 'interna'. </p>
     */
    private final class PrinterFattura extends J2Printer {

//        FooterOld footer;
//
//        HeaderOld header;


        /**
         * Costruttore completo senza parametri.<br>
         */
        public PrinterFattura() {
            /* variabili e costanti locali di lavoro */

            /* rimanda al costruttore della superclasse */
            super(Progetto.getPrintLicense());

            try { // prova ad eseguire il codice

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

                /* margini della pagina, in pollici */
                double topPixels = Libreria.getDouble(MARG_TOP);
                double botPixels = Libreria.getDouble(MARG_BOTTOM);
                double topInches = topPixels / 72;
                double botInches = botPixels / 72;
                double leftPixels = Libreria.getDouble(MARG_LEFT);
                double rightPixels = Libreria.getDouble(MARG_RIGHT);
                double leftInches = leftPixels / 72;
                double rightInches = rightPixels / 72;
                this.setTopMargin(topInches);
                this.setBottomMargin(botInches);
                this.setLeftMargin(leftInches);
                this.setRightMargin(rightInches);

                this.setSeparatePrintThread(false);
                this.setPrintPreviewScale(1.0);

                this.setLeftHeader("");
                this.setCenterHeader("");
                this.setRightHeader("");
                this.setLeftFooter("");
                this.setCenterFooter("");
                this.setRightFooter("");

                /* gap dopo header e prima di footer, in pollici */
                this.setGapBelowHeader(0);
                this.setGapAboveFooter(0);

                double m = this.getTopMargin();
                int a = 87;

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


    } // fine della classe 'interna'


    /**
     * Componente stampabile generico.
     * </p>
     */
    private abstract class CompStampa extends J2ComponentPrinter {

        /**
         * Costruttore completo senza parametri.<br>
         */
        public CompStampa() {
            /* rimanda al costruttore della superclasse */
            super();

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

            /* variabili e costanti locali di lavoro */

            try { // prova ad eseguire il codice

                /* se il componente non ci sta orizzontalmente,
                 * non lo ridimensiona (eventualmente lo spezza
                 * su piu' pagine) */
                this.setHorizontalPageRule(J2ComponentPrinter.TILE);

                /* se il componente non ci sta verticalmente,
                 * non lo ridimensiona (eventualmente lo spezza
                 * su piu' pagine) */
                this.setVerticalPageRule(J2ComponentPrinter.TILE);

                /* allinea il componente a sinistra */
                this.setHorizontalAlignment(J2ComponentPrinter.LEFT);

                /** A value of 0.0 means always split content over page
                 * boundaries, never waste a pixel, whereas a value of
                 * (almost) 1.0 means OK to skip (almost) all of a page
                 * to keep content together on the page (default: 0.2) */
                this.setMaximumPaginationGap(0.0);

                /* rispetta il colore di sfondo del componente */
                this.setWhiteBackground(false);


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch


        }


        /* Ritorna la dimensione del componente */
        public Dimension getPreferredSize() {
            /* variabili e costanti locali di lavoro */
            Dimension dim = null;

            try { // prova ad eseguire il codice
                dim = this.getComponent().getPreferredSize();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return dim;
        }


        /**
         * Assegna la dimensione al componente
         */
        public void setPreferredSize(Dimension dimension) {
            this.getComponent().setPreferredSize(dimension);
        }

    } // fine della classe 'interna'


    /**
     * Header di stampa
     * </p>
     */
    private final class Header extends CompStampa {

        /**
         * Costruttore completo senza parametri.<br>
         */
        public Header() {
            /* rimanda al costruttore della superclasse */
            super();

            try { // prova ad eseguire il codice

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
            Pannello pannello;
            PannelloGap panGap;
            Component compDati;

            try { // prova ad eseguire il codice
                pannello = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
                pannello.setUsaGapFisso(true);
                pannello.setGapPreferito(0);
                this.setComponent(pannello.getPanFisso());

                pannello.getPanFisso().setOpaque(false);
                if (DEBUG) {
                    pannello.getPanFisso().setOpaque(true);
                    pannello.getPanFisso().setBackground(Color.GREEN);
                }// fine del blocco if

                /* aggiunge il componente dati intestazione (testo o immagine)*/
                compDati = this.getCompDatiHead();
                pannello.add(compDati);

                /* aggiunge un filetto */
                panGap = new PannelloGap(6, SwingConstants.BOTTOM);
                pannello.add(panGap);

                /* regola l'altezza */
                this.setHeigth(pannello.getPreferredSize().height);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Assegna un'altezza all'header.
         * <p/>
         *
         * @param h l'altezza da assegnare
         */
        public void setHeigth(int h) {
            int w = getUsableWidth();
            this.getComponent().setPreferredSize(new Dimension(w, h));
        }


        /**
         * Ritorna un pannello contenente i dati per intestazione.
         * <p/>
         * Il pannello contiene l'immagine inserita dal cliente
         * oppure con i dati registrati nelle preferenze
         *
         * @return il componente contenente i dati di intestazione
         */
        private JComponent getCompDatiHead() {
            /* variabili e costanti locali di lavoro */
            JComponent comp = null;
            Documento.SorgenteIntestazione sorgente;

            try {    // prova ad eseguire il codice
                sorgente = Documento.SorgenteIntestazione.testo;
                switch (sorgente) {
                    case testo:
                        comp = this.creaCompDaTestiPref();
                        break;
                    case immagine:
                        // todo qui fare qualcosa del genere...
//                        Image image = getImmagineDaPreferenze();
//                        ImagePanel pan = new ImagePanel(image);
//                        comp = pan;
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch

                Lib.Comp.bloccaDim(comp);

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return comp;
        }


        /**
         * Crea il componente di intestazione dai testi in Preferenze.
         * <p/>
         *
         * @return il componente di intestazione
         */
        private JComponent creaCompDaTestiPref() {
            /* variabili e costanti locali di lavoro */
            Pannello pan = null;
            JTextArea area;
            int w, h;
            String testo;

            try {    // prova ad eseguire il codice

                /* crea un pannello flusso orizzontale a gap variabile */
                pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
                pan.setGapMinimo(20);
                pan.setGapPreferito(40);
                pan.setGapMassimo(60);
                pan.setOpaque(false);

                testo = FattBasePref.Doc.bloccoUno.str();
                if (Lib.Testo.isValida(testo)) {
                    testo = Lib.Testo.trim(testo);
                    area = this.creaArea(testo);
                    pan.add(area);
                }// fine del blocco if

                testo = FattBasePref.Doc.bloccoDue.str();
                if (Lib.Testo.isValida(testo)) {
                    testo = Lib.Testo.trim(testo);
                    area = this.creaArea(testo);
                    pan.add(area);
                }// fine del blocco if

                testo = FattBasePref.Doc.bloccoTre.str();
                if (Lib.Testo.isValida(testo)) {
                    testo = Lib.Testo.trim(testo);
                    area = this.creaArea(testo);
                    pan.add(area);
                }// fine del blocco if

                testo = FattBasePref.Doc.bloccoQuattro.str();
                if (Lib.Testo.isValida(testo)) {
                    testo = Lib.Testo.trim(testo);
                    area = this.creaArea(testo);
                    pan.add(area);
                }// fine del blocco if

                /* congela la dimensione */
                w = getUsableWidth();
                h = pan.getPreferredSize().height;
                pan.setPreferredSize(w, h);

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return pan.getPanFisso();
        }


        /**
         * Crea una JTextArea per un blocchetto di testo.
         * <p/>
         *
         * @param testo da visualizzare
         *
         * @return l'area creata
         */
        private JTextArea creaArea(String testo) {
            /* variabili e costanti locali di lavoro */
            JTextArea area = null;
            int w, h;
            Dimension dim;

            try {    // prova ad eseguire il codice
                area = new JTextArea(testo);
                area.setFont(FONT_INTESTAZIONE_DOC);
                area.setLineWrap(false);    // no wrap

                /* aggiunge un po' di spazio sotto perché Java sbaglia leggermente l'altezza*/
                w = area.getPreferredSize().width;
                h = area.getPreferredSize().height;
                dim = new Dimension(w, h + 2);
                area.setPreferredSize(dim);
                Lib.Comp.bloccaDim(area);

                area.setOpaque(false);
                if (DEBUG) {
                    area.setOpaque(true);
                    area.setBackground(Color.YELLOW);
                }// fine del blocco if

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return area;
        }


    } // fine della classe 'interna'


    /**
     * Footer di stampa
     * </p>
     */
    private final class Footer extends CompStampa {

        /**
         * Costruttore completo senza parametri.<br>
         */
        public Footer() {
            /* rimanda al costruttore della superclasse */
            super();

            try { // prova ad eseguire il codice

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
            JPanel pannello;
            int pagCorrente;
            int quantePagine;
            String testoSx;
            String testoDx;
            Date data;
            JLabel labelSx;
            JLabel labelDx;

            try { // prova ad eseguire il codice
                pannello = new JPanel();
                pannello.setLayout(new BorderLayout());
                this.setComponent(pannello);

                pannello.setOpaque(false);
                if (DEBUG) {
                    pannello.setOpaque(true);
                    pannello.setBackground(Color.GREEN);
                }// fine del blocco if

                int w = getUsableWidth();
                pannello.setPreferredSize(new Dimension(w, ALTEZZA_FOOTER));

                /* crea il testo sinistro del footer */
                testoSx = " ";
                testoSx += getTitoloDoc();
                testoSx += " N. ";
                testoSx += getNumDoc();
                testoSx += " del ";
                data = getDataDoc();
                testoSx += Lib.Data.getStringa(data);

                /* crea il testo destro del footer */
                testoDx = "";
                pagCorrente = getNumPagina();
                quantePagine = getQuantePagine();
                if (quantePagine > 1) {
                    testoDx = "Pag. " + pagCorrente + " di " + quantePagine;
                    testoDx += " ";
                }// fine del blocco if

                /* crea una JLabel con il testo sinistro */
                labelSx = new JLabel(testoSx);
                labelSx.setFont(FONT_FOOTER);
                labelSx.setHorizontalAlignment(SwingConstants.LEFT);

                /* crea una JLabel con il testo destro */
                labelDx = new JLabel(testoDx);
                labelDx.setFont(FONT_FOOTER);
                labelDx.setHorizontalAlignment(SwingConstants.RIGHT);

                /* aggiunge le label al pannello */
                pannello.add(labelSx, BorderLayout.WEST);
                pannello.add(labelDx, BorderLayout.EAST);


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


    } // fine della classe 'interna'


    /**
     * Testata della fattura
     * </p>
     */
    private final class Testata extends CompStampa {

        private int numPag = 0;


        /**
         * Costruttore completo.
         * <p/>
         *
         * @param numPag il numero della pagina
         */
        public Testata(int numPag) {
            /* rimanda al costruttore della superclasse */
            super();

            this.numPag = numPag;

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
            Pannello pannello;
            GruppoRigheTesta gruppo;
            PanIndirizzi panInd;
            JLabel titolo;

            try { // prova ad eseguire il codice

                /* crea il componente */
                pannello = PannelloFactory.verticale(null);
                pannello.setUsaGapFisso(true);
                pannello.setGapPreferito(0);
                this.setComponent(pannello.getPanFisso());

                /* regola il componente */
                pannello.setOpaque(false);
                if (DEBUG) {
                    pannello.setOpaque(true);
                    pannello.setBackground(Color.YELLOW);
                }// fine del blocco if

                /* crea e aggiunge il pannello indirizzi */
                panInd = new PanIndirizzi();
                pannello.add(panInd);

                /* crea e aggiunge un gap verticale fisso */
                pannello.add(new PannelloGap(20));

                /* crea e aggiunge il voce del documento */
                titolo = new JLabel(getTitoloDoc());
                titolo.setFont(FONT_TITOLO_DOC);
                pannello.add(titolo);

                /* crea e aggiunge le righe di testata */
                gruppo = this.creaRighe();
                pannello.add(gruppo);

                /* fissa la dimensione */
                int w = getUsableWidth();
                int h = this.getPreferredSize().height;
                pannello.setPreferredSize(w, h);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Crea il gruppo delle righe con le celle di testata.
         * <p/>
         *
         * @return il gruppo di righe creato
         */
        private GruppoRigheTesta creaRighe() {
            /* variabili e costanti locali di lavoro */
            GruppoRigheTesta gruppo = null;
            String stringa;
            String rifCliente;
            String rifNostri;

            try {    // prova ad eseguire il codice

                gruppo = new GruppoRigheTesta();
                RigaTesta unaRiga;

                unaRiga = new RigaTesta();
                unaRiga.addCella("numero", 60, getNumDoc());
                unaRiga.addCella("data", 80, Lib.Data.getStringa(getDataDoc()));
                unaRiga.addCella("Partita IVA / C.F. cliente", 120, getPicfCliente());
                unaRiga.addCella("cond. pagamento", 0, getPagamento());
                unaRiga.addCella("scadenza", 80, Lib.Data.getStringa(getDataScadenza()));
                gruppo.addRiga(unaRiga);

                /* riga banca, se esiste */
                stringa = getBanca();
                if (Lib.Testo.isValida(stringa)) {
                    unaRiga = new RigaTesta();
                    unaRiga.addCella("banca", 0, stringa, SwingConstants.LEFT);
                    gruppo.addRiga(unaRiga);
                }// fine del blocco if

                /* riga riferimenti, se esistono */
                rifCliente = getRifCliente();
                rifNostri = getRifNostri();
                if ((Lib.Testo.isValida(rifCliente)) || (Lib.Testo.isValida(rifNostri))) {
                    unaRiga = new RigaTesta();
                    unaRiga.addCella("vostri riferimenti", 0, rifCliente, SwingConstants.LEFT);
                    unaRiga.addCella("nostri riferimenti", 0, rifNostri, SwingConstants.LEFT);
                    gruppo.addRiga(unaRiga);
                }// fine del blocco if

                /* inizializza il gruppo e lo aggiunge al pannello */
                gruppo.inizializza();

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return gruppo;
        }


    } // fine della classe 'interna'


    /**
     * Singola riga di stampa.
     * </p>
     */
    private abstract class RigaStampa extends PannelloFlusso {

        protected WrapTextArea areaDesc;

        protected JLabel lblUm;

        protected JLabel lblQta;

        protected JLabel lblUni;

        protected JLabel lblSconto;

        protected JLabel lblIva;

        protected JLabel lblTot;

        /* font di stampa della riga */
        protected Font font;


        /**
         * Costruttore completo.
         * <p/>
         */
        public RigaStampa() {
            /* rimanda al costruttore della superclasse */
            super(Layout.ORIENTAMENTO_ORIZZONTALE);

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

                /* regolazioni del pannelllo */
                this.setUsaGapFisso(true);
                this.setGapPreferito(10);

                this.font = FONT_RIGHE; // default

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Impaginazione della riga.
         * <p/>
         *
         * @throws Exception unaEccezione
         */
        protected void regolaPannello() throws Exception {
            /* variabili e costanti locali di lavoro */
            int wPag;
            int wPan;
            int wDiff;
            int wPrima;
            int wDopo;
            boolean opaco = false;

            try { // prova ad eseguire il codice

                /* regolazioni del pannelllo */
                this.setUsaGapFisso(true);
                this.setGapPreferito(10);

                /* assegna i font */
                areaDesc.setFont(this.font);
                lblUm.setFont(this.font);
                lblQta.setFont(this.font);
                lblUni.setFont(this.font);
                lblSconto.setFont(this.font);
                lblIva.setFont(this.font);
                lblTot.setFont(this.font);

                /* assegna gli allineamenti */
                lblUm.setHorizontalAlignment(SwingConstants.LEFT);
                lblQta.setHorizontalAlignment(SwingConstants.RIGHT);
                lblUni.setHorizontalAlignment(SwingConstants.RIGHT);
                lblSconto.setHorizontalAlignment(SwingConstants.CENTER);
                lblIva.setHorizontalAlignment(SwingConstants.CENTER);
                lblTot.setHorizontalAlignment(SwingConstants.RIGHT);

                /* assegna i colori */
                if (FattBaseStampa.DEBUG) {
                    opaco = true;
                }// fine del blocco if
                areaDesc.setOpaque(opaco);
                areaDesc.setBackground(Color.YELLOW);
                lblUm.setOpaque(opaco);
                lblUm.setBackground(Color.YELLOW);
                lblQta.setOpaque(opaco);
                lblQta.setBackground(Color.YELLOW);
                lblUni.setOpaque(opaco);
                lblUni.setBackground(Color.YELLOW);
                lblSconto.setOpaque(opaco);
                lblSconto.setBackground(Color.YELLOW);
                lblIva.setOpaque(opaco);
                lblIva.setBackground(Color.YELLOW);
                lblTot.setOpaque(opaco);
                lblTot.setBackground(Color.YELLOW);

                /* assegna le larghezze a tutte le colonne tranne la descrizione */
                Lib.Comp.setPreferredWidth(lblUm, LAR_COL_UM);
                Lib.Comp.bloccaLarghezza(lblUm);
                Lib.Comp.setPreferredWidth(lblQta, LAR_COL_QTA);
                Lib.Comp.bloccaLarghezza(lblQta);
                Lib.Comp.setPreferredWidth(lblUni, LAR_COL_UNITARIO);
                Lib.Comp.bloccaLarghezza(lblUni);
                Lib.Comp.setPreferredWidth(lblSconto, LAR_COL_SCONTO);
                Lib.Comp.bloccaLarghezza(lblSconto);
                Lib.Comp.setPreferredWidth(lblIva, LAR_COL_IVA);
                Lib.Comp.bloccaLarghezza(lblIva);
                Lib.Comp.setPreferredWidth(lblTot, LAR_COL_TOTALE);
                Lib.Comp.bloccaLarghezza(lblTot);

                /* aggiunge i componenti */
                this.add(areaDesc);

                /* colonna unità di misura solo se c'è */
                if (isEsisteUM()) {
                    this.add(lblUm);
                }// fine del blocco if

                this.add(lblQta);
                this.add(lblUni);

                /* colonna sconto solo se c'è */
                if (isEsisteSconto()) {
                    this.add(lblSconto);
                }// fine del blocco if

                /* colonna iva solo se è mista */
                if (isIvaMista()) {
                    this.add(lblIva);
                }// fine del blocco if

                this.add(lblTot);

                /* assegna la larghezza alla descrizione
                 * per occupare tutto lo spazio rimasto */
                wPag = getUsableWidth();
                wPan = this.getPreferredSize().width;
                wDiff = wPag - wPan;
                wPrima = areaDesc.getPreferredSize().width;
                wDopo = wPrima + wDiff;
                areaDesc.setWidth(wDopo);

                /* ricalcola la preferred size */
                this.invalidate();

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


    } // fine della classe 'interna'


    /**
     * Singola riga dati di stampa.
     * </p>
     */
    private final class RigaDati extends RigaStampa {

        /**
         * Costruttore completo.
         * <p/>
         *
         * @param numRiga numero della riga nei dati righe
         */
        public RigaDati(int numRiga) {
            /* rimanda al costruttore della superclasse */
            super();

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia(numRiga);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }// fine del metodo costruttore completo


        /**
         * Regolazioni immediate di riferimenti e variabili. <br>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @param numRiga numero della riga nei dati
         *
         * @throws Exception unaEccezione
         */
        private void inizia(int numRiga) throws Exception {
            /* variabili e costanti locali di lavoro */
            Dati dati;
            String testo;
            Double doppio;

            WrapTextArea areaDesc;
            JLabel lblUm;
            JLabel lblQta;
            JLabel lblUni;
            JLabel lblSconto;
            JLabel lblIva;
            JLabel lblTot;

            try { // prova ad eseguire il codice

                /* assegna il font*/
                super.font = FONT_RIGHE;

                /* recupera i dati */
                dati = getDatiRighe();

                /* area descrizione */
                testo = dati.getStringAt(numRiga, CAMPO_R_DESCRIZIONE);
                areaDesc = new WrapTextArea(testo);

                /* unità di misura */
                testo = dati.getStringAt(numRiga, CAMPO_R_SIGLA_UM);
                lblUm = new JLabel(testo);

                /* quantità */
                doppio = dati.getDoubleAt(numRiga, CAMPO_R_QTA);
                testo = CAMPO_R_QTA.format(doppio);
                lblQta = new JLabel(testo);

                /* prezzo unitario */
                doppio = dati.getDoubleAt(numRiga, CAMPO_R_UNITARIO);
                testo = CAMPO_R_UNITARIO.format(doppio);
                lblUni = new JLabel(testo);

                /* sconto */
                doppio = dati.getDoubleAt(numRiga, CAMPO_R_SCONTO);
                testo = CAMPO_R_SCONTO.format(doppio);
                lblSconto = new JLabel(testo);

                /* codice IVA breve */
                testo = dati.getStringAt(numRiga, CAMPO_I_IVABREVE);
                lblIva = new JLabel(testo);

                /* imponibile riga */
                doppio = dati.getDoubleAt(numRiga, CAMPO_R_IMPONIBILE);
                testo = CAMPO_R_IMPONIBILE.format(doppio);
                lblTot = new JLabel(testo);

                /* assegna i componenti */
                super.areaDesc = areaDesc;
                super.lblUm = lblUm;
                super.lblQta = lblQta;
                super.lblUni = lblUni;
                super.lblSconto = lblSconto;
                super.lblIva = lblIva;
                super.lblTot = lblTot;

                /* costruisce il pannello nella superclasse */
                super.regolaPannello();


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }

    } // fine della classe 'interna'


    /**
     * Riga testatina di stampa.
     * </p>
     */
    private final class RigaTestatina extends RigaStampa {

        /**
         * Costruttore completo.
         * <p/>
         */
        public RigaTestatina() {
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
            /* variabili e costanti locali di lavoro */
            WrapTextArea areaDesc;
            JLabel lblUm;
            JLabel lblQta;
            JLabel lblUni;
            JLabel lblSconto;
            JLabel lblIva;
            JLabel lblTot;

            try { // prova ad eseguire il codice

                /* assegna il font*/
                super.font = FONT_TITOLI_RIGHE;

                /* area descrizione */
                areaDesc = new WrapTextArea("Descrizione");

                /* unità di misura */
                lblUm = new JLabel("UM");

                /* quantità */
                lblQta = new JLabel("Q.tà");

                /* prezzo unitario */
                lblUni = new JLabel("Prezzo");

                /* Sconto % */
                lblSconto = new JLabel("sc%");

                /* Cod. IVA breve % */
                lblIva = new JLabel("Iva");

                /* totale riga */
                lblTot = new JLabel("Importo");

                /* assegna i componenti */
                super.areaDesc = areaDesc;
                super.lblUm = lblUm;
                super.lblQta = lblQta;
                super.lblUni = lblUni;
                super.lblSconto = lblSconto;
                super.lblIva = lblIva;
                super.lblTot = lblTot;

                /* costruisce il pannello nella superclasse */
                super.regolaPannello();


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }

    } // fine della classe 'interna'

//    /**
//     * Area di testo per la descrizione riga fattura.
//     * </p>
//     */
//    private final class DescArea extends JTextArea {
//
//        /**
//         * Costruttore base.
//         * <p/>
//         *
//         * @param testo da assegnare
//         */
//        public DescArea(String testo) {
//            /* rimanda al costruttore di questa classe */
//            super(testo);
//            this.setLineWrap(true);
//            this.setWrapStyleWord(true);
//        }// fine del metodo costruttore base
//
//
//        /**
//         * Assegna la larghezza in pixel all'area di testo.
//         * <p/>
//         * L'altezza viene calcolata automaticamente in base
//         * alla larghezza.<br>
//         * Per rimediare a un bug Java, occorre:
//         * - assegnare la larghezza
//         * - inserire l'area in un frame
//         * - eseguire il pack del frame
//         * - eliminare la preferred size
//         * - riassegnare la larghezza
//         * - bloccare larghezza e altezza
//         * <p/>
//         * ATTENZIONE! il componente viene tolto dal suo
//         * contenitore Parent, e poi reinserito automaticamente
//         * nella posizione di prima
//         *
//         * @param w la larghezza desiderata
//         */
//        public void setWidth(int w) {
//            /* variabili e costanti locali di lavoro */
//            JFrame frame;
//            Container cont;
//            int pos=-1;
//
//            try {    // prova ad eseguire il codice
//
//                /* determina il contenitore parente e l'indice
//                 * del componente nel contenitore */
//                cont = this.getParent();
//                if (cont != null) {
//                    Component[] componenti = cont.getComponents();
//                    for (int k = 0; k < componenti.length; k++) {
//                        Component comp = componenti[k];
//                        if (comp.equals(this)) {
//                            pos = k;
//                            break;
//                        }// fine del blocco if
//                    } // fine del ciclo for
//                }// fine del blocco if
//
//                /* assegna la larghezza */
//                Lib.Comp.setPreferredWidth(this, w);
//                frame = new JFrame();
//                frame.add(this);
//                frame.pack();
//                this.setPreferredSize(null);
//                Lib.Comp.setPreferredWidth(this, w);
//                Lib.Comp.bloccaDim(this);
//
//                /* reinserisce il componente dov'era prima */
//                if (pos >= 0) {
//                    cont.add(this, pos);
//                }// fine del blocco if
//
//
//            } catch (Exception unErrore) {    // intercetta l'errore
//                Errore.crea(unErrore);
//            } // fine del blocco try-catch
//        }
//
//
//    } // fine della classe 'interna'


    /**
     * Gruppo di righe di stampa
     * </p>
     */
    private final class GruppoRighe extends CompStampa {

        /* gap tra le righe */
        private int gap = 10;


        /**
         * Costruttore completo senza parametri.<br>
         */
        public GruppoRighe() {
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
            /* variabili e costanti locali di lavoro */
            PannelloFlusso pannello;

            try { // prova ad eseguire il codice

                /* crea il pannello generale */
                pannello = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
                pannello.setUsaGapFisso(true);
                pannello.setGapPreferito(this.gap);

                /* registra il componente */
                this.setComponent(pannello);

                /* aggiunge la testatina righe */
                this.addTestatina();


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch


        }


        /**
         * Aggiunge la testatina al gruppo.
         * <p/>
         */
        private void addTestatina() {
            /* variabili e costanti locali di lavoro */
            int spazioSopra = 10;   // margine sopra ai titoli
            JPanel panSpazio;
            RigaTestatina rigaTitoli;
            JPanel panTitoli;
            Filo filetto;

            try {    // prova ad eseguire il codice

                /* pannello titoli */
                panTitoli = new JPanel();
                panTitoli.setOpaque(false);
                if (DEBUG) {
                    panTitoli.setOpaque(true);
                    panTitoli.setBackground(Color.LIGHT_GRAY);
                }// fine del blocco if

                panTitoli.setLayout(new BorderLayout());

                /* pannello per spazio sopra */
                panSpazio = new JPanel();
                panSpazio.setOpaque(false);
                Lib.Comp.setPreferredHeigth(panSpazio, spazioSopra);

                /* riga dei titoli */
                rigaTitoli = new RigaTestatina();

                /* filo sotto */
                filetto = new Filo();

                /* aggiunge i componenti */
                panTitoli.add(panSpazio, BorderLayout.NORTH);
                panTitoli.add(rigaTitoli);
                panTitoli.add(filetto, BorderLayout.SOUTH);

                /* aggiunge al pannello visibile */
                this.getPannello().add(panTitoli);
                this.getPannello().invalidate();  // ricalcola la dimensione

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Aggiunge una riga al gruppo.
         * <p/>
         *
         * @param riga da aggiungere
         */
        public void addRiga(RigaDati riga) {
            try {    // prova ad eseguire il codice
                this.getPannello().add(riga);
                this.getPannello().invalidate();  // ricalcola la dimensione
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Determina se una riga può essere aggiunta al gruppo
         * senza superare una altezza massima.
         * <p/>
         *
         * @param riga da aggiungere
         * @param hMax altezza massima disponibile
         *
         * @return true se ci sta
         */
        public boolean ciStaRiga(RigaDati riga, int hMax) {
            /* variabili e costanti locali di lavoro */
            boolean ciStaRiga = false;
            int hRiga;
            int hPrima = 0;
            int hDopo = 0;

            try {    // prova ad eseguire il codice
                hRiga = riga.getPreferredSize().height;
                hPrima = this.getPreferredSize().height;
                hDopo = hPrima + this.gap + hRiga;
                if (hDopo <= hMax) {
                    ciStaRiga = true;
                }// fine del blocco if
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return ciStaRiga;
        }


        /**
         * Ritorna il componente Pannello.
         * <p/>
         *
         * @return il componente di tipo PannelloFlusso
         */
        private PannelloFlusso getPannello() {
            /* variabili e costanti locali di lavoro */
            PannelloFlusso panFlusso = null;
            Component comp;

            try {    // prova ad eseguire il codice
                comp = this.getComponent();
                if (comp != null) {
                    if (comp instanceof PannelloFlusso) {
                        panFlusso = (PannelloFlusso)comp;
                    }// fine del blocco if
                }// fine del blocco if

            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return panFlusso;
        }


    } // fine della classe 'interna'


    /**
     * Pannello totali fattura
     * </p>
     */
    private final class Totali extends CompStampa {

        /**
         * Costruttore completo senza parametri.<br>
         */
        public Totali() {
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
            /* variabili e costanti locali di lavoro */
            JPanel pannello;
            BoxLayout layout;
            TabTotali tabTot;
            TabRiepilogo tabRiep;
            PannelloFlusso panRiep;
            JTableHeader header;
            JLabel label;

            try { // prova ad eseguire il codice

                /* crea il pannello principale */
                pannello = new JPanel();
                this.setComponent(pannello);
                layout = new BoxLayout(pannello, BoxLayout.LINE_AXIS);
                pannello.setLayout(layout);
                pannello.setOpaque(false);
                if (DEBUG) {
                    pannello.setOpaque(true);
                    pannello.setBackground(Color.LIGHT_GRAY);
                }// fine del blocco if

                /* se l'IVA è mista, e se se è l'ultima pagina,
                 * crea e aggiunge il riepilogo IVA */
                if (isIvaMista()) {
                    if (isUltimaPagina()) {

                        /* crea un pannello per la tabella riepilogo iva
                         * contenente header e tabella  */
                        panRiep = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
                        panRiep.setUsaGapFisso(true);
                        panRiep.setGapPreferito(0);
                        panRiep.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                        /* crea la tabella riepilogo IVA */
                        tabRiep = new TabRiepilogo();

                        /* voce della tabella */
                        label = new JLabel("Riepilogo IVA");
                        label.setFont(FONT_TIT_RIEPILOGO);

                        /* recupera l'header */
                        header = tabRiep.getTableHeader();

                        /* aggiunge i componenti al pannello */
                        panRiep.add(label); // label
                        panRiep.add(header); // header
                        panRiep.add(tabRiep); // tabella

                        /* aggiunge il pannello riepilogo iva
                         * al pannello generale, in basso a sinistra */
                        panRiep.setAlignmentY(Component.BOTTOM_ALIGNMENT);
                        pannello.add(panRiep);

                    }// fine del blocco if
                }// fine del blocco if

                /* crea e aggiunge un filler espandibile */
                pannello.add(Box.createHorizontalGlue());

                /* crea e aggiunge la tabella totali a destra */
                tabTot = new TabTotali();
                tabTot.setAlignmentY(Component.BOTTOM_ALIGNMENT);
                pannello.add(tabTot);

                /* pone la larghezza del pannello pari alla larghezza pagina */
                int lar = getUsableWidth();
                int alt = this.getPreferredSize().height;
                this.setPreferredSize(new Dimension(lar, alt));

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch


        }


    } // fine della classe 'interna'


    /**
     * Gap di separazione verticale largo quanto la pagina
     * </p>
     * Può avere un filetto sopra, sotto o al centro dello spessore richiesto
     */
    private final class Gap extends CompStampa {

        private PannelloGap panGap;


        /**
         * Costruisce un gap senza filetto.
         * <p/>
         *
         * @param altezza del gap
         */
        public Gap(int altezza) {
            this(altezza, false, 0, 0);
        }// fine del metodo costruttore completo


        /**
         * Costruisce un gap con filetto.
         * <p/>
         *
         * @param altezza del gap
         * @param posFilo posizione del filetto
         * (può essere SwingConstants.TOP, SwingConstants.CENTER, SwingConstants.BOTTOM)
         */
        public Gap(int altezza, int posFilo) {
            this(altezza, posFilo, -1);
        }// fine del metodo costruttore completo


        /**
         * Costruisce un gap con filetto.
         * <p/>
         *
         * @param altezza del gap
         * @param posFilo posizione del filetto
         * (può essere SwingConstants.TOP, SwingConstants.CENTER, SwingConstants.BOTTOM)
         * @param spessFilo spessore del filetto
         */
        public Gap(int altezza, int posFilo, int spessFilo) {
            this(altezza, true, posFilo, spessFilo);
        }// fine del metodo costruttore completo


        /**
         * Costruisce un gap con o senza filetto.
         * <p/>
         *
         * @param altezza del gap
         * @param usaFilo true per usare il filetto
         * @param posFilo posizione del filetto
         * (può essere SwingConstants.TOP, SwingConstants.CENTER, SwingConstants.BOTTOM)
         * @param spessFilo spessore del filetto (-1 per lo spessore di default)
         */
        public Gap(int altezza, boolean usaFilo, int posFilo, int spessFilo) {
            /* rimanda al costruttore della superclasse */
            super();

            try { // prova ad eseguire il codice

                PannelloGap pan = new PannelloGap(altezza, usaFilo, posFilo, spessFilo);
                this.panGap = pan;
                this.setComponent(pan);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }// fine del metodo costruttore completo


        public void setOpaque(boolean flag) {
            this.getPannello().setOpaque(flag);
        }


        public void setBackground(Color color) {
            this.getPannello().setBackground(color);
        }


        /**
         * Assegna l'altezza al componente e ne blocca le dimensioni
         * <p/>
         *
         * @param heigth l'altezza desiderata
         */
        public void setHeigth(int heigth) {
            this.getPannello().setHeigth(heigth);
        }


        /**
         * Ritorna il pannello interno.
         * <p/>
         *
         * @return il pannello interno
         */
        private PannelloGap getPannello() {
            return panGap;
        }

    } // fine della classe 'interna'


    /**
     * Pannello Gap di separazione verticale largo quanto la pagina
     * </p>
     * Può avere un filetto sopra, sotto o al centro dello spessore richiesto
     */
    private final class PannelloGap extends JPanel {

        private int altezza;

        private boolean usaFilo;

        private int posFilo;

        private int spessFilo;


        /**
         * Costruisce un gap senza filetto.
         * <p/>
         *
         * @param altezza del gap
         */
        public PannelloGap(int altezza) {
            this(altezza, false, 0, 0);
        }// fine del metodo costruttore completo


        /**
         * Costruisce un gap con filetto.
         * <p/>
         *
         * @param altezza del gap
         * @param posFilo posizione del filetto
         * (può essere SwingConstants.TOP, SwingConstants.CENTER, SwingConstants.BOTTOM)
         */
        public PannelloGap(int altezza, int posFilo) {
            this(altezza, posFilo, -1);
        }// fine del metodo costruttore completo


        /**
         * Costruisce un gap con filetto.
         * <p/>
         *
         * @param altezza del gap
         * @param posFilo posizione del filetto
         * (può essere SwingConstants.TOP, SwingConstants.CENTER, SwingConstants.BOTTOM)
         * @param spessFilo spessore del filetto
         */
        public PannelloGap(int altezza, int posFilo, int spessFilo) {
            this(altezza, true, posFilo, spessFilo);
        }// fine del metodo costruttore completo


        /**
         * Costruisce un gap con o senza filetto.
         * <p/>
         *
         * @param altezza del gap
         * @param usaFilo true per usare il filetto
         * @param posFilo posizione del filetto
         * (può essere SwingConstants.TOP, SwingConstants.CENTER, SwingConstants.BOTTOM)
         * @param spessFilo spessore del filetto (-1 per lo spessore di default)
         */
        public PannelloGap(int altezza, boolean usaFilo, int posFilo, int spessFilo) {
            /* rimanda al costruttore della superclasse */
            super();

            this.altezza = altezza;
            this.usaFilo = usaFilo;
            this.posFilo = posFilo;
            this.spessFilo = spessFilo;

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
            Filo filo;

            try { // prova ad eseguire il codice

                /* crea il pannello principale */
                this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                this.setOpaque(false);
                if (DEBUG) {
                    this.setOpaque(true);
                    this.setBackground(Color.red);
                }// fine del blocco if


                if (this.usaFilo) {

                    /* crea il filo */
                    if (this.spessFilo != -1) {
                        filo = new Filo(this.spessFilo);
                    } else {
                        filo = new Filo();
                    }// fine del blocco if-else

                    /* aggiunge il filo */
                    /* determina la constraint per il layout */
                    switch (this.posFilo) {
                        case SwingConstants.TOP:
                            this.add(filo);
                            this.add(Box.createVerticalGlue());
                            break;
                        case SwingConstants.CENTER:
                            this.add(Box.createVerticalGlue());
                            this.add(filo);
                            this.add(Box.createVerticalGlue());
                            break;
                        case SwingConstants.BOTTOM:
                            this.add(Box.createVerticalGlue());
                            this.add(filo);
                            this.add(Box.createVerticalStrut(2));   // se non lo metto il filo sparisce!!
                            break;
                        default: // caso non definito
                            this.add(Box.createVerticalGlue());
                            this.add(filo);
                            this.add(Box.createVerticalGlue());
                            break;
                    } // fine del blocco switch

                } else {
                    this.add(Box.createVerticalGlue());
                }// fine del blocco if-else

                /* assegna l'altezza e blocca le dimensioni */
                this.setHeigth(this.altezza);
//                int w = getImageableWidth();
//                this.setPreferredSize(new Dimension(w, this.altezza));
//                Lib.Comp.bloccaDim(this);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Assegna l'altezza al componente e ne blocca le dimensioni
         * <p/>
         *
         * @param heigth l'altezza desiderata
         */
        public void setHeigth(int heigth) {
            int w = getUsableWidth();
            this.setPreferredSize(new Dimension(w, heigth));
            Lib.Comp.bloccaDim(this);
        }


    } // fine della classe 'interna'


    /**
     * Filetto nero a larghezza pagina.
     * </p>
     */
    private final class Filo extends JPanel {

        private static final int SPESSORE_DEFAULT = 1;

        private int spessore;


        /**
         * Costruttore completo senza parametri.<br>
         */
        public Filo() {
            this(SPESSORE_DEFAULT);
        }// fine del metodo costruttore completo


        /**
         * Costruttore completo
         * <p/>
         *
         * @param spessore del filo in pixel
         */
        public Filo(int spessore) {
            /* rimanda al costruttore della superclasse */
            super();

            this.setSpessore(spessore);

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
            /* variabili e costanti locali di lavoro */
            int larghezza;
            int spessore;

            try { // prova ad eseguire il codice

                larghezza = getUsableWidth();
                spessore = this.getSpessore();

                this.setPreferredSize(new Dimension(larghezza, spessore));
                this.setOpaque(true);
                this.setBackground(Color.BLACK);

                Lib.Comp.bloccaDim(this);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        private int getSpessore() {
            return spessore;
        }


        private void setSpessore(int spessore) {
            this.spessore = spessore;
        }
    } // fine della classe 'interna'


    /**
     * Tabella dei totali.
     * </p>
     */
    private final class TabTotali extends JTable {

        /**
         * Costruttore completo con parametri. <br>
         */
        public TabTotali() {
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
            /* variabili e costanti locali di lavoro */
            TableColumn col;
            int larDesc = 130;    // larghezza colonna descrizioni
            Enumeration<TableColumn> colonne;
            int hFont;
            double aria;

            try { // prova ad eseguire il codice

                /* assegna il modello */
                this.setModel(getDatiTotali());

                /* regola la JTable */
                this.setOpaque(false);
                this.setGridColor(Color.BLACK);
                this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                this.setIntercellSpacing(new Dimension(6, 0));

                /* regola l'altezza delle righe (altezza font + 40%)*/
                hFont = Lib.Fonte.getMaxAltezzaFont(FONT_TOTALI);
                aria = Libreria.getDouble(hFont) / 100 * 40;
                this.setRowHeight(hFont + (int)aria);

                /* installa un renderer specifico su tutte le colonne */
                colonne = this.getColumnModel().getColumns();
                while (colonne.hasMoreElements()) {
                    col = colonne.nextElement();
                    col.setCellRenderer(new RendererTot());
                }

                /* regola la larghezza della colonna descrizioni */
                col = this.getColumnModel().getColumn(0);
                col.setPreferredWidth(larDesc);
                col.setMinWidth(larDesc);
                col.setMaxWidth(larDesc);

                /* regola la larghezza della colonna valori */
                col = this.getColumnModel().getColumn(1);
                col.setPreferredWidth(LAR_COL_TOTALE);
                col.setMinWidth(LAR_COL_TOTALE);
                col.setMaxWidth(LAR_COL_TOTALE);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch


        }


    } // fine della classe 'interna'


    /**
     * Renderer per la Tabella dei totali.
     * </p>
     */
    private class RendererTot extends JLabel implements TableCellRenderer {

        private Format formatter = CAMPO_F_TOTALE_NETTO.getCampoDati().getFormat();

        private int numPag; // numero di pagina al momento della costruzione


        /**
         * Costruttore completo. <br>
         */
        public RendererTot() {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili di istanza coi parametri */
            this.numPag = getNumPagina();

        }// fine del metodo costruttore completo


        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int column) {

            /* variabili e costanti locali di lavoro */
            Font font;
            String testo = "";
            int allineamento = SwingConstants.LEFT;
            int quanteRighe;

            // Regola il font (diverso per l'ultima riga)
            quanteRighe = table.getRowCount();
            if (row == quanteRighe - 1) {
                font = FONT_TOTALE_GEN;
            } else {
                font = FONT_TOTALI;
            }

            /* regola il testo e l'allineamento */
            if (value != null) {
                if (value instanceof String) {
                    testo = ((String)value);
                    allineamento = SwingConstants.LEFT;
                }// fine del blocco if
                if (value instanceof Number) {
                    testo = formatter.format(value);
                    allineamento = SwingConstants.RIGHT;
                }// fine del blocco if
            }// fine del blocco if

            /* se non è l'ultima pagina scrive '%' al posto del numero */
            if (this.numPag < getQuantePagine()) {
                if (column == 1) {
                    testo = "%";
                }// fine del blocco if
            }// fine del blocco if-else

            /* assegna i valori alla JLabel */
            this.setHorizontalAlignment(allineamento);
            this.setText(testo);
            this.setFont(font);
//            this.setPreferredSize(new Dimension(200,25));
            return this;

        }


        // The following methods override the defaults for performance reasons
        public void validate() {
        }


        public void revalidate() {
        }


        protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        }


        public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
        }


    }


    /**
     * Tabella riepilogo IVA.
     * </p>
     */
    private final class TabRiepilogo extends JTable {

        /**
         * Costruttore completo con parametri. <br>
         */
        public TabRiepilogo() {
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

            /* variabili e costanti locali di lavoro */
            int larCodBreve = 26;    // larghezza colonna cod. breve
            int larImponibile = 60;    // larghezza colonna imponibile
            int larAliquota = 20;    // larghezza colonna aliquota
            int larImportoIva = 50;    // larghezza colonna importo iva
            int larDesc = 140;    // larghezza colonna descrizioni
            int hFont;
            double aria;

            try { // prova ad eseguire il codice

                /* assegna il modello dati */
                this.setModel(getDatiRiepilogo());

                /* regola la JTable */
                this.setOpaque(false);
                this.setGridColor(Color.BLACK);
                this.setFont(FONT_RIEPILOGO);
//                this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                this.setIntercellSpacing(new Dimension(6, 0));

                /* regola l'header della JTable */
                this.getTableHeader().setFont(FONT_RIEPILOGO);

                /* regola l'altezza delle righe (altezza font + 40%)*/
                hFont = Lib.Fonte.getMaxAltezzaFont(FONT_RIEPILOGO);
                aria = Libreria.getDouble(hFont) / 100 * 40;
                this.setRowHeight(hFont + (int)aria);

                /* rimuove la prima colonna (i codici IVA) */
                this.removeColumn(this.getColumnModel().getColumn(0));

                /* regola voce, larghezza e renderer della colonna codice breve */
                this.regolaColonna(0, larCodBreve, "cod", CAMPO_I_IVABREVE);

                /* regola voce, larghezza e renderer della colonna imponibile */
                this.regolaColonna(1, larImponibile, "imponibile", CAMPO_R_IMPONIBILE);

                /* regola voce, larghezza e renderer della colonna aliquota */
                this.regolaColonna(2, larAliquota, "%", CAMPO_R_ALIQUOTAIVA);

                /* regola voce, larghezza e renderer della colonna importo iva */
                this.regolaColonna(3, larImportoIva, "iva", CAMPO_R_IMPORTOIVA);

                /* regola voce, larghezza e renderer della colonna descrizione iva */
                this.regolaColonna(4, larDesc, "descrizione", CAMPO_R_DESCRIZIONE);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Regola larghezza, voce e renderer di una colonna.
         * <p/>
         *
         * @param indice della colonna
         * @param lar larghezza della colonna
         * @param titolo della colonna
         * @param campo di riferimento per la colonna
         */
        private void regolaColonna(int indice, int lar, String titolo, Campo campo) {
            /* variabili e costanti locali di lavoro */
            TableColumn col;
            Format format;

            try {    // prova ad eseguire il codice
                col = this.getColumnModel().getColumn(indice);
                this.getColumnModel().getColumn(indice).setHeaderValue(titolo);
                col.setPreferredWidth(lar);
                col.setMinWidth(lar);
                col.setMaxWidth(lar);

                /* installa un renderer specifico sulla le colonne */
                format = campo.getCampoDati().getFormat();
                col.setCellRenderer(new RendererRiep(format));

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

        }


    } // fine della classe 'interna'


    /**
     * Renderer per la Tabella del riepilogo IVA.
     * </p>
     */
    private class RendererRiep extends JLabel implements TableCellRenderer {

        private Format formatter;


        /**
         * Costruttore completo con parametri.
         * <p/>
         *
         * @param format il formatter da utilizzare
         */
        public RendererRiep(Format format) {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili di istanza coi parametri */
            this.formatter = format;
        }// fine del metodo costruttore completo


        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int column) {

            /* variabili e costanti locali di lavoro */
            String testo = "";
            int allineamento = SwingConstants.LEFT;

            /* regola il testo e l'allineamento */
            if (value != null) {
                if (value instanceof String) {
                    testo = ((String)value);
                    allineamento = SwingConstants.LEFT;
                }// fine del blocco if
                if (value instanceof Double) {
                    testo = formatter.format(value);
                    allineamento = SwingConstants.RIGHT;
                }// fine del blocco if
                if (value instanceof Integer) {
                    testo = value.toString();
                    allineamento = SwingConstants.CENTER;
                }// fine del blocco if
            }// fine del blocco if

            /* assegna i valori alla JLabel */
            this.setHorizontalAlignment(allineamento);
            this.setFont(FONT_RIEPILOGO);
            this.setText(testo);
            return this;

        }


        // The following methods override the defaults for performance reasons
        public void validate() {
        }


        public void revalidate() {
        }


        protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        }


        public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
        }


    }


    /**
     * Singola riga di intestazione, senza bordi.
     * </p>
     */
    private final class RigaTesta extends JPanel {

        private JTable tabella;

        private ArrayList<Integer> larghezze;

        private ArrayList<Integer> allineamenti;

        private boolean usaFiller;


        /**
         * Costruttore completo senza parametri.
         * <p/>
         * Non usa la cella filler
         */
        public RigaTesta() {
            /* rimanda al costruttore della superclasse */
            this(false);
        }// fine del metodo costruttore completo


        /**
         * Costruttore completo con parametri.
         * <p/>
         *
         * @param usaFiller true per aggiungere automaticamente una
         * cella filler a larghezza variabile a fine riga
         */
        public RigaTesta(boolean usaFiller) {
            /* rimanda al costruttore della superclasse */
            super(); // una riga, nessuna colonna
            this.usaFiller = usaFiller;
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
            JTable tab;


            try { // prova ad eseguire il codice

                /* regola il pannello */
                this.setLayout(new BorderLayout());
                this.setOpaque(false);

                /* crea l'array per mantenere le larghezze */
                this.larghezze = new ArrayList<Integer>();

                /* crea l'array per mantenere gli allineamenti */
                this.allineamenti = new ArrayList<Integer>();

                /* crea la JTable con il modello dati di default,
                 * 1 riga e inizialmente nessuna colonna */
                tab = new JTable(1, 0);
                this.tabella = tab;

                /* regola la JTable */
                tab.setOpaque(false);
                tab.setShowHorizontalLines(false);
                tab.setShowVerticalLines(true);
                tab.setGridColor(Color.BLACK);
                tab.setFont(FONT_CELLE_TESTATA);
                tab.getTableHeader().setFont(FONT_TITOLI_CELLE_TESTATA);
                tab.setIntercellSpacing(new Dimension(6, 3));
                tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                /* crea un renderer specifico per gestire gli allineamenti */
                tab.setDefaultRenderer(Object.class, new RendererCelle());

                /* aggiunge l'header alla parte sopra del pannello
                 * e la tabella alla parte centrale */
                this.add(tab.getTableHeader(), BorderLayout.NORTH);
                this.add(tab);


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Aggiunge una cella alla riga.
         * <p/>
         *
         * @param titolo della cella
         * @param larghezza della cella (0 per larghezza variabile)
         * @param valore da visualizzare nel contenuto
         * @param allineamento del contenuto (SwingConstants.LEFT/CENTER/RIGHT)
         */
        public void addCella(String titolo, int larghezza, Object valore, int allineamento) {
            /* variabili e costanti locali di lavoro */
            DefaultTableModel mod;
            Object[] oggetti;

            try {    // prova ad eseguire il codice

                /* aggiunge la larghezza all'array */
                this.larghezze.add(larghezza);

                /* aggiunge l'allianeamento all'array */
                this.allineamenti.add(allineamento);

                /* aggiunge la colonna con voce e valore al modello */
                mod = this.getModello();
                oggetti = new Object[1];
                oggetti[0] = valore;
                mod.addColumn(titolo, oggetti);

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Aggiunge una cella alla riga.
         * <p/>
         * Usa l'allineamento di default (al centro)
         *
         * @param titolo della cella
         * @param larghezza della cella (0 per larghezza variabile)
         * @param valore da visualizzare nel contenuto
         */
        public void addCella(String titolo, int larghezza, Object valore) {
            this.addCella(titolo, larghezza, valore, SwingConstants.CENTER);
        }


        /**
         * Inizializza la riga
         * <p/>
         * Aggiunge la cella finale a larghezza variabile.
         * Assegna le larghezze alle colonne fisse.
         */
        public void inizializza() {
            /* variabili e costanti locali di lavoro */
            TableColumn col;
            int lar;

            try {    // prova ad eseguire il codice

                /**
                 * Se richiesto, aggiunge la cella finale a
                 * larghezza variabile
                 */
                if (this.usaFiller) {
                    this.addCella("", 0, "");
                }// fine del blocco if-else

                /* assegna le larghezze alle colonne */
                int quante;
                quante = this.getModello().getColumnCount();
                for (int k = 0; k < quante; k++) {
                    col = this.tabella.getColumnModel().getColumn(k);
                    lar = this.larghezze.get(k);
                    if (lar != 0) {
                        col.setPreferredWidth(lar);
                        col.setMinWidth(lar);
                        col.setMaxWidth(lar);
                        col.setResizable(false);
                    } else {
                        col.setResizable(true);
                    }// fine del blocco if-else
                } // fine del ciclo for

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Restituisce il modello dati della JTable.
         * <p/>
         *
         * @return il modello dati della JTable
         */
        private DefaultTableModel getModello() {
            /* variabili e costanti locali di lavoro */
            DefaultTableModel mod = null;
            TableModel modello;

            try {    // prova ad eseguire il codice
                modello = this.tabella.getModel();
                if (modello != null) {
                    if (modello instanceof DefaultTableModel) {
                        mod = (DefaultTableModel)modello;
                    }// fine del blocco if
                }// fine del blocco if

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return mod;
        }


        /**
         * Renderer per tutte le celle.
         * <p/>
         */
        private class RendererCelle extends DefaultTableCellRenderer {

            public Component getTableCellRendererComponent(JTable table,
                                                           Object object,
                                                           boolean isSelected,
                                                           boolean hasFocus,
                                                           int row,
                                                           int col) {

                /* variabili e costanti locali di lavoro */
                int allineamento;

                try { // prova ad eseguire il codice

                    allineamento = allineamenti.get(col);
                    this.setHorizontalAlignment(allineamento);

                } catch (Exception unErrore) { // intercetta l'errore
                    Errore.crea(unErrore);
                }// fine del blocco try-catch

                /* valore di ritorno */
                return super.getTableCellRendererComponent(table,
                        object,
                        isSelected,
                        hasFocus,
                        row,
                        col);
            }

        }


    } // fine della classe 'interna'


    /**
     * Gruppo di righe di testata.
     * </p>
     */
    private final class GruppoRigheTesta extends PannelloFlusso {

        ArrayList<RigaTesta> righe;


        /**
         * Costruttore completo con parametri. <br>
         */
        public GruppoRigheTesta() {
            /* rimanda al costruttore della superclasse */
            super(Layout.ORIENTAMENTO_VERTICALE);

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

                this.righe = new ArrayList<RigaTesta>();

                /* regola il pannello */
                this.setUsaGapFisso(true);
                this.setGapPreferito(0);
                this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Inizializza il gruppo
         * <p/>
         * Inizializza le righe
         * Aggiunge graficamente le righe al pannello.
         * Aggiunge un filo sotto ad ogni riga tranne l'ultima.
         */
        public void inizializza() {
            /* variabili e costanti locali di lavoro */
            int quante;
            RigaTesta riga;

            try {    // prova ad eseguire il codice

                quante = this.righe.size();
                for (int k = 0; k < quante; k++) {
                    riga = this.righe.get(k);
                    riga.inizializza();
                    this.add(riga);
                    if (k < quante - 1) {
                        this.add(new Filo());
                    }// fine del blocco if
                } // fine del ciclo for

                int w = getUsableWidth();
                int h = this.getPreferredSize().height;
                this.setPreferredSize(w, h);
                Lib.Comp.bloccaDim(this);

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Aggiunge una riga.
         * <p/>
         *
         * @param riga la riga da aggiungere
         */
        public void addRiga(RigaTesta riga) {
            try {    // prova ad eseguire il codice
                this.righe.add(riga);
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


    } // fine della classe 'interna'


    /**
     * Pannello generale degli indirizzi.
     * </p>
     */
    private final class PanIndirizzi extends PannelloFlusso {

        /**
         * Costruttore completo con parametri. <br>
         */
        public PanIndirizzi() {
            /* rimanda al costruttore della superclasse */
            super(Layout.ORIENTAMENTO_ORIZZONTALE);

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
            PanIndirizzo pan1;
            PanIndirizzo pan2;

            try { // prova ad eseguire il codice

                this.setUsaGapFisso(true);
                this.setGapPreferito(0);
                this.setAllineamento(Layout.ALLINEA_ALTO);
                this.setOpaque(false);
                if (DEBUG) {
                    this.setOpaque(true);
                    this.setBackground(Color.CYAN);
                }// fine del blocco if

                /* costruisce i due pannelli di indirizzo */
                if (Lib.Testo.isValida(getLabelDestinazione())) {
                    pan1 = new PanIndirizzo(getLabelDestinatario(), "DESTINATARIO");
                    pan2 = new PanIndirizzo(getLabelDestinazione(), "LUOGO DESTINAZIONE");
                } else {
                    pan1 = new PanIndirizzo("", "");
                    pan2 = new PanIndirizzo(getLabelDestinatario(), "");
                }// fine del blocco if-else

                int margSx = getLeftMarg();
                int wLabel1 = pan1.getPreferredSize().width;

                int gap1 = X_LABEL1 - margSx;
                this.add(Box.createHorizontalStrut(gap1));
                this.add(pan1);

                int gap2 = X_LABEL2 - gap1 - wLabel1 - margSx;
                this.add(Box.createHorizontalStrut(gap2));
                this.add(pan2);

                Lib.Comp.setPreferredWidth(this, getUsableWidth());
                Lib.Comp.bloccaDim(this);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }

    } // fine della classe 'interna'


    /**
     * Pannello contenente un singolo indirizzo con una eventuale dicitura.
     * </p>
     */
    private final class PanIndirizzo extends PannelloFlusso {

        String testo;

        String dicitura;


        /**
         * Costruttore completo con parametri.
         * <p/>
         *
         * @param testo per l'etichetta
         * @param dicitura eventuale per l'etichetta
         */
        public PanIndirizzo(String testo, String dicitura) {
            /* rimanda al costruttore della superclasse */
            super(Layout.ORIENTAMENTO_VERTICALE);

            this.testo = testo;
            this.dicitura = dicitura;

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
            JLabel label;
            WrapTextArea area;


            try { // prova ad eseguire il codice

                this.setUsaGapFisso(true);
                this.setGapPreferito(0);

//                this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                this.setOpaque(false);
                if (DEBUG) {
                    this.setOpaque(true);
                    this.setBackground(Color.WHITE);
                }// fine del blocco if

                /* costruisce e aggiunge la dicitura */
                label = new JLabel(this.dicitura);
                label.setFont(FONT_DICITURE_DEST);
                label.setVerticalAlignment(SwingConstants.TOP);
                label.setPreferredSize(new Dimension(W_LABEL, DIST_DICITURE_DEST));
                Lib.Comp.bloccaDim(label);
//                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                this.add(label);

                /* costruisce e aggiunge l'etichetta */
                area = new WrapTextArea(this.testo);
                area.setLineWrap(true);
                area.setWrapStyleWord(true);
                area.setFont(FONT_LABEL);

                area.setPreferredSize(new Dimension(W_LABEL, 0)); // regola la larghezza
                area.setOptimalHeight(); // regola l'altezza di conseguenza
//                Lib.Comp.setAreaOptimalHeight(area);
                /* se l'alteza è minore del minimo la porta al minimo */
                if (area.getPreferredSize().height < H_MIN_LABEL) {
                    area.setPreferredSize(new Dimension(W_LABEL, H_MIN_LABEL));
                }// fine del blocco if
                Lib.Comp.bloccaDim(area);
//                area.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                this.add(area);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }

    } // fine della classe 'interna'


} // fine della classe
