/**
 * Title:     ContoStampa
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      18-mag-2006
 */
package it.algos.albergo.conto;

import it.algos.albergo.Albergo;
import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.conto.addebito.Addebito;
import it.algos.albergo.conto.addebito.AddebitoModulo;
import it.algos.albergo.conto.pagamento.Pagamento;
import it.algos.albergo.conto.pagamento.PagamentoModulo;
import it.algos.albergo.listino.Listino;
import it.algos.albergo.listino.ListinoModulo;
import it.algos.albergo.pianodeicontialbergo.sottoconto.AlbSottocontoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.memoria.dati.DatiMemoria;
import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.stampa.carta.Carte;
import it.algos.base.stampa.impostazione.PaginaStampa;
import it.algos.base.stampa.stampabile.StampabileNumPagina;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.anagrafica.AnagraficaModulo;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Stampa di un conto.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 18-mag-2006 ore 14.39.46
 */
public final class ContoStampaOld extends StampabileNumPagina {

    /**
     * codice chiave del conto da stampare
     */
    private int codConto;

    /**
     * tipo di stampa
     */
    private Conto.TipiStampa tipoStampa;

    /**
     * flag per raggruppare gi addebiti di pensione giorno per giorno
     */
    private boolean raggruppaPens;

    /**
     * elenco degli addebiti da stampare
     */
    private Dati addebiti;

    /**
     * flag - indica se i dati sono stati caricati
     */
    private boolean datiCaricati;

    /**
     * Ordinata corrente della penna
     */
    private static int yPenna = 0;

    /**
     * riporto progressivo per pagina
     */
    private double riporto = 0;

    private double ultRiporto = 0;

    /* prima riga di addebito stampata sulla pagina corrente */
    private int primaRigaPagina;

    /* ultima riga di addebito stampata in assoluto */
    private int ultRigaStampata;

    /* nome del cliente stampato sul conto */
    private String nomeCliente;

    /* data di arrivo del cliente stampata sul conto */
    private Date dataArrivo;

    /* camera */
    private String camera;

    /* totale del conto */
    private double totaleConto;

    /* sconto */
    private double sconto;

    /* totale del conto dopo lo sconto */
    private double totaleDopoSconto;

    /* importo totale dei pagamenti effettuati */
    private double totalePagamenti;

    /* totale a pagare */
    private double totalePagare;

    /* totale pensione */
    private double totalePens;

    /* totale extra */
    private double totaleExtra;


    /**
     * moduli
     */
    private ContoModulo modConto;

    private AddebitoModulo modAddebito;

    private PagamentoModulo modPagamento;

    private AnagraficaModulo modCliente;

    private CameraModulo modCamera;

    private ListinoModulo modListino;

    private AlbSottocontoModulo modSottoconto;

    /**
     * campi
     */
    private Campo campoAddData;

    private Campo campoAddChiave;

    private Campo campoListinoDescr;

    private Campo campoListinoAmbito;

    private Campo campoSottocOrdine;

    private Campo campoAddQuantita;

    private Campo campoAddPrezzo;

    private Campo campoAddTotale;

    private Campo campoAddNote;

    private Campo campoPagConto;

    private Campo campoPagData;

    private Campo campoPagTitolo;

    private Campo campoPagRicevuta;

    private Campo campoPagImporto;

    private Campo campoPagNote;

    /* posizioni colonne dati addebito */
    private static final int xColData = 0;

    private static final int xColDescrizione = 60;

    private static final int xColQta = 210;

    private static final int xColPrezzo = 260;

    private static final int xColTotale = 310;

    private static final int xColProgr = 370;

    private static final int xColNote = 410;

    /* posizioni colonne dati pagamento */
    private static final int xColPData = 0;

    private static final int xColPTitolo = 60;

    private static final int xColPRicevuta = 110;

    private static final int xColPImporto = 160;

    private static final int xColPNote = 200;


    /**
     * Step di interlinea per le righe di addebito
     */
    private static int stepAddebito = 12;

    /**
     * Gap ulteriore tra gli addebiti al cambio di data
     */
    private static int gapCambioDataAddebito = 4;

    /**
     * Step di interlinea per le righe di intestazione
     */
    private static int stepHead = 13;

    /**
     * Step di interlinea per le righe di pagamento
     */
    private static int stepPagamento = 12;

    /**
     * Step di interlinea per le righe di riepilogo
     */
    private static int stepRiep = 13;

    /**
     * Font per il titolo del documento
     */
    private static Font fontTitoloDoc = FontFactory.creaPrinterFont(Font.BOLD, 14);

    /**
     * Font per il tipo documento
     */
    private static Font fontTipoDoc = FontFactory.creaPrinterFont(Font.PLAIN, 12);

    /**
     * Font per le label delle righe di intestazione
     */
    private static Font fontLblHead = FontFactory.creaPrinterFont(Font.PLAIN, 11);

    /**
     * Font per i dati delle righe di intestazione
     */
    private static Font fontDatiHead = FontFactory.creaPrinterFont(Font.BOLD, 11);

    /**
     * Font per le label delle righe di riepilogo
     */
    private static Font fontLblRiep = FontFactory.creaPrinterFont(Font.PLAIN, 11);

    /**
     * Font per i dati delle righe di riepilogo
     */
    private static Font fontDatiRiep = FontFactory.creaPrinterFont(Font.BOLD, 11);

    /**
     * Font per le righe di addebito
     */
    private static Font fontAddebito = FontFactory.creaPrinterFont(Font.PLAIN, 9);

    /**
     * Font per la testatina addebiti
     */
    private static Font fontHeadAddebito = FontFactory.creaPrinterFont(Font.BOLD, 9);

    /**
     * Font per le righe di pagamento
     */
    private static Font fontPagamento = FontFactory.creaPrinterFont(Font.PLAIN, 9);

    /**
     * Font per la testatina pagamenti
     */
    private static Font fontHeadPagamento = FontFactory.creaPrinterFont(Font.BOLD, 9);

    /**
     * Font per il totale pagamenti
     */
    private static Font fontTotalePagamento = FontFactory.creaPrinterFont(Font.BOLD, 9);


    /**
     * Costruttore completo
     * <p/>
     *
     * @param codice del conto da stampare
     * @param tipo di stampa
     * @param raggruppa true per raggruppare le voci di pensione
     */
    public ContoStampaOld(int codice, Conto.TipiStampa tipo, boolean raggruppa) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setCodConto(codice);
        this.setTipoStampa(tipo);
        this.setRaggruppaPens(raggruppa);

        /* regolazioni iniziali di riferimenti e variabili */
        try {
            this.inizia();
        } catch (Exception unErrore) {
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * <p/>
     * Metodo chiamato direttamente dal costruttore
     */
    private void inizia() {

        /* regola le impostazioni di stampa */
        this.setMarginePagina(40, 40, 20, 40);
        this.setCodiceCarta(Carte.A4);
        this.setOrientamentoPagina(PaginaStampa.VERTICALE);

        /* Regola campi e moduli. */
        this.regolaCampiModuli();

    } /* fine del metodo inizia */


    /**
     * Regola campi e moduli.
     * <p/>
     */
    private void regolaCampiModuli() {
        try {    // prova ad eseguire il codice

            modConto = ContoModulo.get();
            modAddebito = AddebitoModulo.get();
            modPagamento = PagamentoModulo.get();
            modListino = ListinoModulo.get();
            modSottoconto = Albergo.Moduli.AlbSottoconto();
            modCliente = ((AnagraficaModulo)Progetto.getModulo(Anagrafica.NOME_MODULO));
            modCamera = Albergo.Moduli.Camera();

            campoAddData = modAddebito.getCampo(Addebito.Cam.data.get());
            campoListinoDescr = modListino.getCampo(Listino.Cam.descrizione.get());
            campoListinoAmbito = modListino.getCampo(Listino.Cam.ambitoPrezzo.get());
            campoSottocOrdine = modSottoconto.getCampoOrdine();
            campoAddChiave = modAddebito.getCampoChiave();
            campoAddQuantita = modAddebito.getCampo(Addebito.Cam.quantita.get());
            campoAddPrezzo = modAddebito.getCampo(Addebito.Cam.prezzo.get());
            campoAddTotale = modAddebito.getCampo(Addebito.Cam.importo.get());
            campoAddNote = modAddebito.getCampo(Addebito.Cam.note.get());
            campoPagConto = modPagamento.getCampo(Pagamento.Cam.conto.get());
            campoPagData = modPagamento.getCampo(Pagamento.Cam.data.get());
            campoPagTitolo = modPagamento.getCampo(Pagamento.Cam.titolo.get());
            campoPagRicevuta = modPagamento.getCampo(Pagamento.Cam.ricevuta.get());
            campoPagImporto = modPagamento.getCampo(Pagamento.Cam.importo.get());
            campoPagNote = modPagamento.getCampo(Pagamento.Cam.note.get());


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Disegna una pagina.
     * <p/>
     * Metodo da implementare obbligatoriamente nelle sottoclassi.
     * E' responsabilita' del metodo:
     * - disegnare la pagina richiesta nell'oggetto grafico g che viene passato.
     * - ritornare true se la stampa e' terminata o false se non e' terminata.
     *
     * @param pagina l'oggetto grafico nel quale disegnare
     * @param formato il formato della pagina che verra' stampata
     * @param numPagina il numero di pagina da stampare (1 per la prima)
     *
     * @return true se la stampa e' terminata
     */
    public boolean printPagina(Graphics2D pagina, PageFormat formato, int numPagina) {
        /* variabili e costanti locali di lavoro */
        boolean terminato = false;
        boolean addebitiEsauriti;
        boolean riepilogoStampato;

        try { // prova ad eseguire il codice

            /* carica i dati per la stampa solo prima volta */
            if (!this.datiCaricati) {
                this.caricaDati();
            }// fine del blocco if

            /* disegna il bounding box della pagina */
//            float lar = (float)formato.getImageableWidth();
//            float alt = (float)formato.getImageableHeight();
//            pagina.drawRect(0,0,(int)lar, (int)alt);

            /* prima di cominciare a stampare la pagina
             * resetta la posizione della penna */
            yPenna = 0;

            /* se e' la prima pagina stampa l'intestazione */
            if (numPagina == 1) {
                this.stampaIntestazione(pagina);
            }// fine del blocco if

            /* stampa gli addebiti */
            addebitiEsauriti = this.stampaAddebiti(pagina);

            /* se ha stampato tutti gli addebiti, stampa il riepilogo */
            if (addebitiEsauriti) {
                riepilogoStampato = this.stampaRiepilogo();
                if (riepilogoStampato) {
                    terminato = true;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return terminato;
    }


    /**
     * Stampa l'intestazione del conto.
     * <p/>
     *
     * @param pagina l'oggetto grafico nel quale disegnare
     */
    private void stampaIntestazione(Graphics2D pagina) {
        /* variabili e costanti locali di lavoro */
        int altezza;
        String label;
        String testo;
        int xCol2 = 90;

        try { // prova ad eseguire il codice

            /* titolo del documento */
            testo = "Conto cliente";
            pagina.setFont(fontTitoloDoc); // prima di misurare getAltezzaTesto()!
            altezza = this.getAltezzaTesto(testo);
            yPenna += altezza;
            pagina.drawString(testo, 0, yPenna);
            yPenna += stepHead;

            /* tipo di conto (solo se non e' quello completo)*/
            if (!this.getTipoStampa().equals(Conto.TipiStampa.completo)) {
                testo = "Tipo conto: " + this.getTipoStampa().toString();
                pagina.setFont(fontTipoDoc); // prima di misurare getAltezzaTesto()!
                altezza = this.getAltezzaTesto(testo);
                yPenna += altezza;
                pagina.drawString(testo, 0, yPenna);
                yPenna += stepHead;
            }// fine del blocco if

            /* nome del cliente */
            label = "Cliente:";
            testo = this.nomeCliente;
            yPenna += stepHead;
            pagina.setFont(fontLblHead);
            pagina.drawString(label, 0, yPenna);
            pagina.setFont(fontDatiHead);
            pagina.drawString(testo, xCol2, yPenna);

            /* camera */
            label = "Camera:";
            testo = this.camera;
            yPenna += stepHead;
            pagina.setFont(fontLblHead);
            pagina.drawString(label, 0, yPenna);
            pagina.setFont(fontDatiHead);
            pagina.drawString(testo, xCol2, yPenna);

            /* data di arrivo */
            label = "Data di arrivo:";
            testo = Lib.Data.getStringa(this.dataArrivo);
            yPenna += stepHead;
            pagina.setFont(fontLblHead);
            pagina.drawString(label, 0, yPenna);
            pagina.setFont(fontDatiHead);
            pagina.drawString(testo, xCol2, yPenna);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Stampa le righe di addebito.
     * <p/>
     * Stampa gli addebiti dalla ultima riga stampata fino
     * alla fine della lista addebiti o dello spazio disponibile
     * sulla pagina
     *
     * @param pagina l'oggetto grafico nel quale disegnare
     *
     * @return true se ha esaurito gli addebiti da stampare
     */
    private boolean stampaAddebiti(Graphics2D pagina) {
        /* variabili e costanti locali di lavoro */
        boolean esauriti = false;
        Dati addebiti;
        boolean fine = false;
        boolean continua;
        int riga = 0;

        final int larQta = 20;    // larghezza colonna qta, x all. dx
        final int larImporti = 30;    // larghezza colonne importo, x all. dx

        Date ultimaData = null;
        Date data;
        String descrizione;
        int quantita;
        double prezzo;
        double totale;
        double progr = 0;
        String note;
        String strData;
        String strDescrizione;
        String strQuantita;
        String strPrezzo;
        String strTotale;
        String strProgr;
        String strNote;

        int xData = 0;
        int xDescrizione = 0;
        int xQta = 0;
        int xPrezzo = 0;
        int xTotale = 0;
        int xProgr = 0;
        int xNote = 0;


        try { // prova ad eseguire il codice

            /* stampa la testatina degli addebiti */
            this.stampaTestataAddebiti();

            /* recupera i dati degli addebiti */
            addebiti = this.getAddebiti();

            /*uscita di emergenza */
            if (addebiti == null) {
                return true;
            }// fine del blocco if

            /* regola la riga dalla quale iniziare */
            if (this.isPaginaNuova()) {
                if (this.ultRigaStampata > 0) {
                    riga = this.ultRigaStampata + 1;
                } else {
                    riga = 0;
                }// fine del blocco if-else
            } else {
                riga = primaRigaPagina;
            }// fine del blocco if-else

            /* memorizza il numero della prima riga
       * stampata sulla pagina corrente */
            if (this.isPaginaNuova()) {
                primaRigaPagina = riga;
            }// fine del blocco if

            /* regola il riporto dal quale iniziare i progressivi */
            if (this.isPaginaNuova()) {
                progr = riporto;
                ultRiporto = riporto;
            } else {
                progr = ultRiporto;
            }// fine del blocco if-else

            /* ciclo di stampa addebiti */
            while (!fine) {

                /* controlla che non sia esaurito l'elenco
                 * degli addebiti da stampare */
                continua = true;
                if (continua) {
                    if (riga >= addebiti.getRowCount()) {
                        esauriti = true;
                        fine = true;
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if

                /* avanza la penna per la stampa del prossimo addebito */
                if (continua) {
                    yPenna += stepAddebito;

                    /* al cambio data aggiunge uno spazio ulteriore */
                    data = (Date)addebiti.getValueAt(riga, campoAddData);
                    if (ultimaData != null) {
                        if (!data.equals(ultimaData)) {
                            yPenna += gapCambioDataAddebito;
                        }// fine del blocco if
                    }// fine del blocco if

                }// fine del blocco if

                /* controllo cambio pagina
                 * controlla che la penna non sia avanzata
                 * oltre la fine dell'area stampabile */
                if (continua) {
                    if (yPenna > this.getyMax()) {
                        fine = true;
                        continua = false;
                        riporto = progr;
                    }// fine del blocco if
                }// fine del blocco if

                /* stampa la singola riga di addebito */
                if (continua) {

                    /* regola il font corrente (prima dei comandi getLunghezza()) */
                    pagina.setFont(fontAddebito);


                    data = (Date)addebiti.getValueAt(riga, campoAddData);
                    strData = campoAddData.format(data);
                    xData = xColData;

                    descrizione = addebiti.getStringAt(riga, campoListinoDescr);
                    strDescrizione = descrizione;
                    xDescrizione = xColDescrizione;

                    quantita = addebiti.getIntAt(riga, campoAddQuantita);
                    strQuantita = campoAddQuantita.format(quantita);
                    xQta = xColQta + larQta - this.getLunghezzaTesto(strQuantita);

                    prezzo = addebiti.getDoubleAt(riga, campoAddPrezzo);
                    strPrezzo = campoAddPrezzo.format(prezzo);
                    xPrezzo = xColPrezzo + larImporti - this.getLunghezzaTesto(strPrezzo);

                    totale = quantita * prezzo;
                    strTotale = campoAddTotale.format(totale);
                    xTotale = xColTotale + larImporti - this.getLunghezzaTesto(strTotale);

                    progr += totale;
                    strProgr = campoAddTotale.format(progr);
                    xProgr = xColProgr + larImporti - this.getLunghezzaTesto(strProgr);

                    note = addebiti.getStringAt(riga, campoAddNote);
                    strNote = note;
                    xNote = xColNote;

                    pagina.drawString(strData, xData, yPenna);
                    pagina.drawString(strDescrizione, xDescrizione, yPenna);
                    pagina.drawString(strQuantita, xQta, yPenna);
                    pagina.drawString(strPrezzo, xPrezzo, yPenna);
                    pagina.drawString(strTotale, xTotale, yPenna);
                    pagina.drawString(strProgr, xProgr, yPenna);
                    pagina.drawString(strNote, xNote, yPenna);

                    /* se e' una nuova pagina, memorizza l'indice
                     * dell'ultimo addebito stampato */
                    if (this.isPaginaNuova()) {
                        this.ultRigaStampata = riga;
                    }// fine del blocco if

                    /* memorizza l'ultima data stampata */
                    ultimaData = data;

                    /* incrementa il contatore di riga */
                    riga++;

                }// fine del blocco if

            }// fine del blocco while


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esauriti;

    }


    /**
     * Stampa la testata degli addebiti.
     * <p/>
     */
    private void stampaTestataAddebiti() {
        /* variabili e costanti locali di lavoro */
        Graphics2D pagina;

        try {    // prova ad eseguire il codice

            yPenna += 20;
            pagina = this.getPagina();
            pagina.setFont(fontHeadAddebito);

            pagina.drawString("Data", xColData, yPenna);
            pagina.drawString("Causale", xColDescrizione, yPenna);
            pagina.drawString("Quantità", xColQta, yPenna);
            pagina.drawString("Prezzo", xColPrezzo, yPenna);
            pagina.drawString("Totale", xColTotale, yPenna);
            pagina.drawString("Progr.", xColProgr, yPenna);
            pagina.drawString("Note", xColNote, yPenna);

            yPenna += 4;
            this.drawLine(yPenna);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Stampa lo schema riepilogativo.
     * <p/>
     *
     * @return true se il riepilogo e' stato effettivamente stampato
     */
    private boolean stampaRiepilogo() {
        /* variabili e costanti locali di lavoro */
        boolean stampato = false;
        int oldyPenna;
        int hRiepilogo;

        try {    // prova ad eseguire il codice

            /* memorizza la posizione della penna prima di cominciare */
            oldyPenna = yPenna;

            /* esegue una stampa simulata per vedere se
             * ci sta nella pagina */
            this.stampaRiepilogo(false);
            hRiepilogo = yPenna - oldyPenna;

            /* se la penna non e andata oltre al margine inferiore,
      * ripristina la posizione della penna, esegue effettivamente
      * la stampa e ritorna true */
            if (yPenna <= this.getyMax()) {
                yPenna = (int)this.getyMax() - hRiepilogo;
                this.stampaRiepilogo(true);
                stampato = true;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stampato;
    }


    /**
     * Stampa lo schema riepilogativo.
     * <p/>
     *
     * @param flag true per disegnare effettivamente nella pagina,
     * false per simulare la stampa e spostare solo la penna verticalmente.
     */
    private void stampaRiepilogo(boolean flag) {
        /* variabili e costanti locali di lavoro */
        int yPennaIniziale;
        int yDopoPagamenti;
        int yDopoTotali;
        int yPennaMax;


        try { // prova ad eseguire il codice

            yPennaIniziale = yPenna;
            /* riepilogo pagamenti (solo completa) */
            if (this.getTipoStampa().equals(Conto.TipiStampa.completo)) {
                this.stampaRiepilogoPagamenti(flag);
            }// fine del blocco if
            yDopoPagamenti = yPenna;

            yPenna = yPennaIniziale;
            this.stampaRiepilogoTotali(flag);
            yDopoTotali = yPenna;

            yPennaMax = Math.max(yDopoPagamenti, yDopoTotali);
            yPenna = yPennaMax;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Stampa il riepilogo pagamenti a sinistra.
     * <p/>
     *
     * @param flag true per disegnare effettivamente nella pagina,
     * false per simulare la stampa e spostare solo la penna verticalmente.
     */
    private void stampaRiepilogoPagamenti(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Graphics2D pagina;
        Query query;
        Filtro filtro;
        Ordine ordine;
        Dati dati;

        Date data;
        int codTitolo;
        int numRicevuta;
        double importo = 0;

        String sData;
        String sTitolo;
        String sRicevuta;
        String sImporto;
        String sNote;
        String sTotale;

        final int larImporti = 30;    // larghezza colonne importo, x all. dx
        int xImporto;
        final int lunFilo = 310;  // lunghezza del filetto

        String testo;
        int lunTesto;

        try {    // prova ad eseguire il codice

            /* recupera i dati dei pagamenti */
            query = new QuerySelezione(modPagamento);
            query.addCampo(campoPagData);
            query.addCampo(campoPagTitolo);
            query.addCampo(campoPagRicevuta);
            query.addCampo(campoPagImporto);
            query.addCampo(campoPagNote);
            codConto = this.getCodConto();

            /* filtro per tutti i pagamenti del conto tranne sconti e sospesi*/
            filtro = this.getFiltroPagamenti();

            query.setFiltro(filtro);
            ordine = new Ordine();
            ordine.add(campoPagData);
            ordine.add(modPagamento.getCampoOrdine());
            query.setOrdine(ordine);
            dati = modPagamento.query().querySelezione(query);

            /* procede solo se ci sono dei pagamenti */
            if (dati.getRowCount() > 0) {

                /* recupera la pagina */
                pagina = this.getPagina();

                /* stampa il titolo */
                yPenna += 16;
                if (flag) {
                    pagina.setFont(fontHeadPagamento);
                    pagina.drawString("Pagamenti effettuati", 0, yPenna);
                }// fine del blocco if

                /* stampa la testatina */
                yPenna += 16;
                if (flag) {
                    pagina.setFont(fontHeadPagamento);
                    pagina.drawString("Data", xColPData, yPenna);
                    pagina.drawString("Titolo", xColPTitolo, yPenna);
                    pagina.drawString("Ricevuta", xColPRicevuta, yPenna);
                    pagina.drawString("Importo", xColPImporto, yPenna);
                    pagina.drawString("Note", xColPNote, yPenna);
                }// fine del blocco if
                yPenna += 4;
                if (flag) {
                    this.drawLine(yPenna, lunFilo);
                }// fine del blocco if

                /* spazzola e stampa le righe */
                pagina.setFont(fontPagamento);
                for (int k = 0; k < dati.getRowCount(); k++) {

                    yPenna += stepPagamento;

                    if (flag) {

                        /* data */
                        data = dati.getDataAt(k, campoPagData);
                        sData = Lib.Data.getStringa(data);
                        pagina.drawString(sData, xColPData, yPenna);

                        /* titolo */
                        codTitolo = dati.getIntAt(k, campoPagTitolo);
                        sTitolo = Pagamento.TitoloPagamento.getDescrizione(codTitolo);
                        pagina.drawString(sTitolo, xColPTitolo, yPenna);

                        /* ricevuta */
                        numRicevuta = dati.getIntAt(k, campoPagRicevuta);
                        sRicevuta = "";
                        if (numRicevuta != 0) {
                            sRicevuta = Lib.Testo.getStringa(numRicevuta);
                        }// fine del blocco if
                        pagina.drawString(sRicevuta, xColPRicevuta, yPenna);

                        /* importo */
                        importo = dati.getDoubleAt(k, campoPagImporto);
                        sImporto = campoPagImporto.format(importo);
                        xImporto = xColPImporto + larImporti - this.getLunghezzaTesto(sImporto);
                        pagina.drawString(sImporto, xImporto, yPenna);

                        /* note */
                        sNote = dati.getStringAt(k, campoPagNote);
                        pagina.drawString(sNote, xColPNote, yPenna);

                    }// fine del blocco if

                } // fine del ciclo for

                /* filetto di chiusura */
                yPenna += 4;
                if (flag) {
                    this.drawLine(yPenna, lunFilo);
                }// fine del blocco if

                /* stampa totale pagamenti */
                yPenna += stepPagamento;
                if (flag) {
                    sTotale = campoPagImporto.format(totalePagamenti);
                    xImporto = xColPImporto + larImporti - this.getLunghezzaTesto(sTotale);
                    pagina.setFont(fontTotalePagamento);
                    testo = "Totale pagamenti";
                    lunTesto = this.getLunghezzaTesto(testo);
                    pagina.drawString(testo, xImporto - lunTesto - 20, yPenna);
                    pagina.drawString(sTotale, xImporto, yPenna);
                }// fine del blocco if

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Stampa il riepilogo totali a destra.
     * <p/>
     *
     * @param flag true per disegnare effettivamente nella pagina,
     * false per simulare la stampa e spostare solo la penna verticalmente.
     */
    private void stampaRiepilogoTotali(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Graphics2D pagina;
        String label;
        String testo;
        int margSx;
        int xCol2 = 165;
        int lun;
        boolean stampaPensione;
        boolean stampaExtra;

        try { // prova ad eseguire il codice

            /* regola i flag di stampa Pensione e Extra */
            switch (this.getTipoStampa()) {
                case completo:
                    stampaPensione = true;
                    stampaExtra = true;
                    break;
                case pensione:
                    stampaPensione = true;
                    stampaExtra = false;
                    break;
                case extra:
                    stampaPensione = false;
                    stampaExtra = true;
                    break;
                default: // caso non definito
                    stampaPensione = true;
                    stampaExtra = true;
                    break;
            } // fine del blocco switch

            /* recupera la pagina */
            pagina = this.getPagina();

            /* determina il margine sinistro per ottenere
             * l'allineamento a destra del riepilogo */
            margSx = (int)this.getxMax() - (xCol2);

            /* filetto a larghezza pagina */
            yPenna += 4;
            if (flag) {
                this.drawLine(yPenna);
            }// fine del blocco if

            /* totale pensione */
            if (stampaPensione) {
                label = "Totale pensione:";
                testo = this.campoAddTotale.format(this.totalePens);
                lun = this.getLunghezzaTesto(testo, fontDatiRiep);
                yPenna += stepRiep;
                if (flag) {
                    pagina.setFont(fontLblRiep);
                    pagina.drawString(label, margSx, yPenna);
                    pagina.setFont(fontDatiRiep);
                    pagina.drawString(testo, margSx + xCol2 - lun, yPenna);
                }// fine del blocco if
            }// fine del blocco if

            /* totale extra */
            if (stampaExtra) {
                label = "Totale extra:";
                testo = this.campoAddTotale.format(this.totaleExtra);
                lun = this.getLunghezzaTesto(testo, fontDatiRiep);
                yPenna += stepRiep;
                if (flag) {
                    pagina.setFont(fontLblRiep);
                    pagina.drawString(label, margSx, yPenna);
                    pagina.setFont(fontDatiRiep);
                    pagina.drawString(testo, margSx + xCol2 - lun, yPenna);
                }// fine del blocco if
            }// fine del blocco if

            /* totali (solo x stampa completa) */
            if (this.getTipoStampa().equals(Conto.TipiStampa.completo)) {

                /* filetto a larghezza colonna */
                yPenna += 4;
                if (flag) {
                    pagina.drawLine(margSx, yPenna, margSx + xCol2, yPenna);
                }// fine del blocco if

                /* totale del conto */
                label = "Totale conto:";
                testo = this.campoAddTotale.format(this.totaleConto);
                lun = this.getLunghezzaTesto(testo, fontDatiRiep);
                yPenna += stepRiep;
                if (flag) {
                    pagina.setFont(fontLblRiep);
                    pagina.drawString(label, margSx, yPenna);
                    pagina.setFont(fontDatiRiep);
                    pagina.drawString(testo, margSx + xCol2 - lun, yPenna);
                }// fine del blocco if

                /* sconto e nuovo totale conto (se c'è) */
                if (sconto != 0) {

                    label = "Sconto:";
                    testo = "-" + this.campoAddTotale.format(this.sconto);
                    lun = this.getLunghezzaTesto(testo, fontDatiRiep);
                    yPenna += stepRiep;
                    if (flag) {
                        pagina.setFont(fontLblRiep);
                        pagina.drawString(label, margSx, yPenna);
                        pagina.setFont(fontDatiRiep);
                        pagina.drawString(testo, margSx + xCol2 - lun, yPenna);
                    }// fine del blocco if

                    label = "Totale netto:";
                    testo = this.campoAddTotale.format(this.totaleDopoSconto);
                    lun = this.getLunghezzaTesto(testo, fontDatiRiep);
                    yPenna += stepRiep;
                    if (flag) {
                        pagina.setFont(fontLblRiep);
                        pagina.drawString(label, margSx, yPenna);
                        pagina.setFont(fontDatiRiep);
                        pagina.drawString(testo, margSx + xCol2 - lun, yPenna);
                    }// fine del blocco if

                    /* filetto */
                    yPenna += 4;
                    if (flag) {
                        pagina.drawLine(margSx, yPenna, margSx + xCol2, yPenna);
                    }// fine del blocco if


                }// fine del blocco if

                /* meno pagamenti effettuati (se ci sono)*/
                if (this.totalePagamenti != 0) {
                    label = "Pagamenti effettuati:";
                    testo = this.campoAddTotale.format(-this.totalePagamenti);
                    lun = this.getLunghezzaTesto(testo, fontDatiRiep);
                    yPenna += stepRiep;
                    if (flag) {
                        pagina.setFont(fontLblRiep);
                        pagina.drawString(label, margSx, yPenna);
                        pagina.setFont(fontDatiRiep);
                        pagina.drawString(testo, margSx + xCol2 - lun, yPenna);
                    }// fine del blocco if

                }// fine del blocco if

                /* filetto */
                yPenna += 4;
                if (flag) {
                    pagina.drawLine(margSx, yPenna, margSx + xCol2, yPenna);
                }// fine del blocco if

                /* totale a pagare */
                label = "Totale a pagare:";
                testo = this.campoAddTotale.format(this.totalePagare);
                lun = this.getLunghezzaTesto(testo, fontDatiRiep);
                yPenna += stepRiep;
                if (flag) {
                    pagina.setFont(fontLblRiep);
                    pagina.drawString(label, margSx, yPenna);
                    pagina.setFont(fontDatiRiep);
                    pagina.drawString(testo, margSx + xCol2 - lun, yPenna);
                }// fine del blocco if
            }// fine del blocco if

            /* spazio sotto */
            yPenna += 2;


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Carica i dati necessari pr la stampa.
     * <p/>
     */
    private void caricaDati() {
        try {    // prova ad eseguire il codice
            this.caricaDatiIntestazione();
            this.caricaAddebiti();
            this.caricaDatiRiepilogo();
            this.datiCaricati = true;
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Carica i dati generali pr la stampa.
     * <p/>
     */
    private void caricaDatiIntestazione() {
        /* variabili e costanti locali di lavoro */
        int codCliente = 0;
        EstrattoBase estCliente;
        int codCamera;

        try { // prova ad eseguire il codice

            /* recupera il nome del cliente (privato o societa') */
            codCliente = modConto.query().valoreInt(Conto.Cam.pagante.get(), this.getCodConto());
            estCliente = modCliente.getEstratto(Anagrafica.Estratto.descrizione, codCliente);
            this.nomeCliente = estCliente.getStringa();

            /* data di arrivo */
            this.dataArrivo = (Date)modConto.query().valoreCampo(Conto.Cam.validoDal.get(),
                    this.getCodConto());

            /* camera */
            codCamera = modConto.query().valoreInt(Conto.Cam.camera.get(), this.getCodConto());
            this.camera = modCamera.query().valoreStringa(Camera.Cam.camera.get(), codCamera);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Carica la lista degli addebiti.
     * <p/>
     * Carica tutti gli addebiti del conto e registra
     * l'oggetto Dati corrispondente.
     * Gli addebiti sono ordinati per data, ordine di sottoconto e
     * ordine di inserimento
     */
    private void caricaAddebiti() {
        /* variabili e costanti locali di lavoro */
        Campo campoChiaveConto;
        Filtro filtro;
        Filtro filtroConto;
        Filtro filtroTipo = null;
        Ordine ordine;
        Dati dati;
        Query query;
        Conto.TipiStampa tipoStampa;

        try {    // prova ad eseguire il codice

            /* creo la query sugli addebiti */
            query = new QuerySelezione(modAddebito);

            /* campi */
            query.addCampo(campoAddData);
            query.addCampo(campoListinoDescr);
            query.addCampo(campoAddQuantita);
            query.addCampo(campoAddPrezzo);
            query.addCampo(campoAddNote);
            query.addCampo(campoListinoAmbito);

            /* filtro per gli addebiti del conto */
            campoChiaveConto = modConto.getCampoChiave();
            filtroConto = new Filtro(campoChiaveConto, Filtro.Op.UGUALE, this.getCodConto());

            /* eventuale filtro per gli addebiti di un certo tipo */
            tipoStampa = this.getTipoStampa();
            if (tipoStampa != null) {
                switch (tipoStampa) {
                    case completo:
                        break;
                    case pensione:
                        filtroTipo = Listino.AmbitoPrezzo.getFiltroListino(true);
                        break;
                    case extra:
                        filtroTipo = Listino.AmbitoPrezzo.getFiltroListino(false);
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch
            }// fine del blocco if

            /* regola il filtro della query */
            filtro = new Filtro();
            filtro.add(filtroConto);
            if (filtroTipo != null) {
                filtro.add(filtroTipo);
            }// fine del blocco if
            query.setFiltro(filtro);

            /* ordine per ordine di sottoconto e poi per sequenza di inserimento addebito */
            ordine = new Ordine();
            ordine.add(campoAddData);
            ordine.add(campoSottocOrdine);
            ordine.add(campoAddChiave);
            query.setOrdine(ordine);

            /* esecuzione */
            dati = modAddebito.query().querySelezione(query);
            this.setAddebiti(dati);

            /* raggruppa le voci di pensione */
            if (this.isRaggruppaPens()) {
                this.raggruppaPensioni();
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Raggruppa gli addebiti di pensione giorno per giorno.
     * <p/>
     * Registra un nuovo oggetto Dati con le pensioni raggruppate
     */
    private void raggruppaPensioni() {
        /* variabili e costanti locali di lavoro */
        Dati dati;
        Date data;
        String descr;
        int qta;
        double prezzo;
        String note;
        int ambito;

        ArrayList<Campo> campi;
        DatiMemoria dati2;
        int riga;

        Date oldData = Lib.Data.getVuota();
        int ultRigaPens = -1;
        double totPens = 0;


        try {    // prova ad eseguire il codice

            /* crea un nuovo oggetto dati
             * per contenere i dati raggruppati */
            campi = new ArrayList<Campo>();
            campi.add(campoAddData);
            campi.add(campoListinoDescr);
            campi.add(campoAddQuantita);
            campi.add(campoAddPrezzo);
            campi.add(campoAddNote);
            campi.add(campoListinoAmbito);
            dati2 = new DatiMemoria(campi);

            /* recupera l'oggetto dati corrente, lo spazzola e lo copia
             * nel nuovo oggetto dati effettuando il raggruppamento */
            dati = this.getAddebiti();
            dati.getRowCount();
            for (int k = 0; k < dati.getRowCount(); k++) {

                /* dati della riga originale */
                data = Libreria.getDate(dati.getValueAt(k, campoAddData));
                descr = Lib.Testo.getStringa(dati.getValueAt(k, campoListinoDescr));
                qta = Libreria.getInt(dati.getValueAt(k, campoAddQuantita));
                prezzo = Libreria.getDouble(dati.getValueAt(k, campoAddPrezzo));
                note = Lib.Testo.getStringa(dati.getValueAt(k, campoAddNote));
                ambito = Libreria.getInt(dati.getValueAt(k, campoListinoAmbito));

                /* cambio della data addebito
                 * - riporta il totale accumulato sulla precedente
                 * riga di pensione
                 * - crea una nuova riga di pensione
                 */
                if (!data.equals(oldData)) {

                    if (ultRigaPens > -1) {
                        dati2.setValueAt(ultRigaPens, campoAddQuantita, 1);
                        dati2.setValueAt(ultRigaPens, campoAddPrezzo, totPens);
                        totPens = 0;
                    }// fine del blocco if

                    riga = dati2.addRiga();
                    dati2.setValueAt(riga, campoAddData, data);
                    dati2.setValueAt(riga, campoListinoDescr, "Pensione");
                    dati2.setValueAt(riga,
                            campoListinoAmbito,
                            Listino.AmbitoPrezzo.pensioniComplete.getCodice());
                    ultRigaPens = riga;

                    oldData = data;

                }// fine del blocco if

                /* se riga extra, copia la riga nei dati nuovi
                 * se riga pensione, accumula il totale */
                if (Listino.AmbitoPrezzo.isCodPensione(ambito)) {  // pensione
                    totPens += qta * prezzo;
                } else {  // extra
                    riga = dati2.addRiga();
                    dati2.setValueAt(riga, campoAddData, data);
                    dati2.setValueAt(riga, campoListinoDescr, descr);
                    dati2.setValueAt(riga, campoAddQuantita, qta);
                    dati2.setValueAt(riga, campoAddPrezzo, prezzo);
                    dati2.setValueAt(riga, campoAddNote, note);
                    dati2.setValueAt(riga, campoListinoAmbito, ambito);
                }// fine del blocco if-else

            } // fine del ciclo for

            /* scarica la pensione accumulata per l'ultimo giorno */
            if (ultRigaPens > -1) {
                dati2.setValueAt(ultRigaPens, campoAddQuantita, 1);
                dati2.setValueAt(ultRigaPens, campoAddPrezzo, totPens);
                totPens = 0;
            }// fine del blocco if

            /* registra i nuovi dati addebiti */
            this.setAddebiti(dati2);


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Carica i dati per il riepilogo (totali).
     * <p/>
     */
    private void caricaDatiRiepilogo() {
        /* variabili e costanti locali di lavoro */
        Dati addebiti;
        int qta;
        double prezzo;
        double totRiga;
        int codAmbito;


        try {    // prova ad eseguire il codice

            addebiti = this.getAddebiti();
            for (int k = 0; k < addebiti.getRowCount(); k++) {
                qta = addebiti.getIntAt(k, this.campoAddQuantita);
                prezzo = addebiti.getDoubleAt(k, this.campoAddPrezzo);
                codAmbito = addebiti.getIntAt(k, this.campoListinoAmbito);
                totRiga = qta * prezzo;

                /* attribuisce l'importo al totale pensione o extra
                 * a seconda dell'ambito prezzo della voce di listino */
                if (Listino.AmbitoPrezzo.isCodPensione(codAmbito)) {
                    this.totalePens += totRiga;
                } else {
                    this.totaleExtra += totRiga;
                }// fine del blocco if-else
            } // fine del ciclo for

            this.totaleConto = this.totalePens + this.totaleExtra;
            this.sconto = this.getTotaleSconti();
            this.totaleDopoSconto = this.totaleConto - this.sconto;

            /* importo totale dei pagamenti effettuati */
            totalePagamenti = this.getTotalePagamenti();

            /**
             * totale a pagare
             */
            totalePagare = totaleDopoSconto - totalePagamenti;

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il filtro per isolare i pagamenti del conto.
     * <p/>
     * Ritorna tutti i pagamenti a qualsiasi titolo
     *
     * @return il filtro per isolare i pagamenti del conto
     */
    private Filtro getFiltroPagamenti() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        int codConto;

        try {    // prova ad eseguire il codice

            codConto = this.getCodConto();
            filtro = FiltroFactory.crea(campoPagConto, codConto);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Recupera l'importo totale dei pagamenti effettuati.
     * <p/>
     *
     * @return il totale pagamenti
     */
    private double getTotalePagamenti() {
        /* variabili e costanti locali di lavoro */
        double totale = 0;
        Filtro filtro;
        Number numero;

        try {    // prova ad eseguire il codice

            codConto = this.getCodConto();
            filtro = this.getFiltroPagamenti();
            numero = modPagamento.query().somma(campoPagImporto, filtro);
            totale = Libreria.getDouble(numero);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return totale;
    }


    /**
     * Recupera l'importo totale degli sconti effettuati.
     * <p/>
     *
     * @return il totale sconti
     */
    private double getTotaleSconti() {
        /* variabili e costanti locali di lavoro */
        double totale = 0;

        try {    // prova ad eseguire il codice
            totale = ContoModulo.getTotSconti(this.getCodConto(), null);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return totale;
    }


    private int getCodConto() {
        return codConto;
    }


    private void setCodConto(int codConto) {
        this.codConto = codConto;
    }


    private Conto.TipiStampa getTipoStampa() {
        return tipoStampa;
    }


    private void setTipoStampa(Conto.TipiStampa tipoStampa) {
        this.tipoStampa = tipoStampa;
    }


    private boolean isRaggruppaPens() {
        return raggruppaPens;
    }


    private void setRaggruppaPens(boolean raggruppaPens) {
        this.raggruppaPens = raggruppaPens;
    }


    private Dati getAddebiti() {
        return addebiti;
    }


    private void setAddebiti(Dati addebiti) {
        this.addebiti = addebiti;
    }

//     public int getNumberOfPages(){
//         return 2;
//     }

}// fine della classe