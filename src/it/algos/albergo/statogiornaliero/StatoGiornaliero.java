/**
 * Title:     StatoGiornaliero
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-mag-2009
 */
package it.algos.albergo.statogiornaliero;

import com.wildcrest.j2printerworks.J2ComponentPrinter;
import com.wildcrest.j2printerworks.J2FlowPrinter;
import com.wildcrest.j2printerworks.J2PanelPrinter;
import it.algos.albergo.AlbergoLib;
import it.algos.albergo.arrivipartenze.riepilogo.CalcFactory;
import it.algos.albergo.arrivipartenze.riepilogo.CalcMovimenti;
import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.camera.composizione.CompoCamera;
import it.algos.albergo.camera.composizione.CompoCameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.listino.Listino;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.base.azione.adapter.AzAdapterAction;
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
import it.algos.base.libreria.Libreria;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.stampa.Printer;
import it.algos.base.tavola.Tavola;
import it.algos.base.wrapper.SetValori;
import it.algos.gestione.anagrafica.Anagrafica;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;

/**
 * Funzione di reporting dello stato delle presenze per 1 giorno
 * in base ai dati di Periodo/Prenotazione.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-mag-2009
 */
public class StatoGiornaliero extends DialogoBase {

    private static final String NOME_BOT_ESPORTA = "Esporta";

    private static final String NOME_CAMPO_DATA = "data di analisi";

    private static final String NOME_CAMPO_CHECK_NASCONDI_LIBERE = "nascondi camere libere";

    private static final String KEY_PRESENTI_IERI = "Presenti ieri";

    private static final String KEY_ARRIVI_OGGI = "Arrivi di oggi";

    private static final String KEY_PARTENZE_OGGI = "Partenze di oggi";

    private static final String KEY_PRESENTI_OGGI = "Presenti nella notte";

    private static final String KEY_FB = Listino.PensioniPeriodo.pensioneCompleta.getSigla();

    private static final String KEY_HB = Listino.PensioniPeriodo.mezzaPensione.getSigla();

    private static final String KEY_BB = Listino.PensioniPeriodo.pernottamento.getSigla();

    private static final String KEY_LUNCH = Presenza.TipiPasto.lunch.getSigla();

    private static final String KEY_DINNER = Presenza.TipiPasto.dinner.getSigla();

    private static final String KEY_BREAKFAST = Presenza.TipiPasto.breakfast.getSigla()
            + " (di domani)";

    /* modulo che mantiene i dati */
    private StatoGiornalieroModulo moduloDati;

    /* variabili per i totali */
    private int totPresOggiA, totPresOggiB;

    /* tabelle dei totali */
    private TabTotali tabTotMovi, tabTotOggi, tabTotPasti;

    /* bottone esegui */
    private JButton botEsegui;

    /* label che riporta il giorno di analisi */
    private JLabel labelGiorno;

    /* pannello contenente le tabelle dei totali */
    private Pannello panTotali;

    /* data della situazione correntemente visualizzata */
    private Date dataCorrente;


    /**
     * Costruttore completo con parametri
     * <p/>
     */
    public StatoGiornaliero() {
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
        Component comp;
        Pannello pan;

        try { // prova ad eseguire il codice

            /* crea il modulo per i dati */
            this.creaModuloDati();

            /* regola il dialogo */
            this.getDialogo().setModal(false);
            this.setTitolo("Situazione giornaliera");

            /* aggiunge il pannello comandi in alto */
            pan = this.creaPanComandi();
            this.addPannello(pan);

            /* aggiunge il navigatore al dialogo */
            comp = this.getNavDati().getPortaleNavigatore();
            this.addComponente(comp);

            /* aggiunge il pannello con le tabelle totali */
            pan = this.creaPannelloTotali();
            this.setPanTotali(pan);
            this.addPannello(pan);

            /* aggiunge i bottoni in fondo al dialogo */
            this.addBottoneStampa();
            BottoneDialogo bottone = this.addBottoneBase(
                    NOME_BOT_ESPORTA, "Export24", false, false, new AzioneEsporta());
            bottone.setOpaque(false);
            this.addBottone(bottone);
            this.addBottoneChiudi();

            /* mette la data odierna e lancia una prima analisi */
            this.setValore(NOME_CAMPO_DATA, AlbergoLib.getDataProgramma());
            this.setValore(NOME_CAMPO_CHECK_NASCONDI_LIBERE, true);
            this.eventoEsegui();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea il pannello con i comandi.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanComandi() {
        /* variabili e costanti locali di lavoro */
        Pannello panComandi = null;
        Pannello panSx;
        Pannello panDx;
        Campo campo;

        try {    // prova ad eseguire il codice

            /**
             * pannello di sx con data e bottone
             */
            panSx = PannelloFactory.orizzontale(null);
            panSx.setAllineamento(Layout.ALLINEA_BASSO);
            campo = CampoFactory.data(NOME_CAMPO_DATA);
            panSx.add(campo);
            JButton bot = new JButton("Aggiorna");
            this.setBotEsegui(bot);
            bot.setOpaque(false);
            bot.addActionListener(new AzEsegui());
            panSx.add(bot);

            /**
             * label del giorno di analisi
             */
            JLabel label = new JLabel("ciao");
            this.setLabelGiorno(label);
            label.setForeground(Color.red);


            /**
             * pannello di dx con opzioni e storico
             */
            panDx = PannelloFactory.orizzontale(null);
            panDx.setAllineamento(Layout.ALLINEA_CENTRO);
            campo = CampoFactory.checkBox(NOME_CAMPO_CHECK_NASCONDI_LIBERE);
            campo.setLarScheda(180);
            panDx.add(campo);
            bot = AlbergoLib.creaBotStorico();
            bot.addActionListener(new AzStorico());
            panDx.add(bot);

            /**
             * pannello completo
             */
            panComandi = PannelloFactory.orizzontale(null);
            panComandi.creaBordo(4,4,4,4);

            panDx.setAllineamento(Layout.ALLINEA_CENTRO);
            panComandi.add(panSx);
            panComandi.add(Box.createHorizontalGlue());
            panComandi.add(this.getLabelGiorno());
            panComandi.add(Box.createHorizontalGlue());
            panComandi.add(panDx);


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return panComandi;
    }


    /**
     * Crea il modulo per mantenere i dati risultanti.
     * <p/>
     */
    private void creaModuloDati() {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try {    // prova ad eseguire il codice

            ArrayList<Campo> campi = new ArrayList<Campo>();

            campo = CampoFactory.intero(Cam.codperiodo);
            campi.add(campo);

            campo = CampoFactory.intero(Cam.codcamera);
            campi.add(campo);

            campo = CampoFactory.testo(Cam.camera);
            campo.setLarghezza(40);
            campi.add(campo);

            campo = CampoFactory.checkBox(Cam.libera);
            campi.add(campo);

            campo = CampoFactory.testo(Cam.cliente);
            campo.setLarghezza(120);
            campi.add(campo);

            campo = CampoFactory.intero(Cam.adulti);
            campo.setLarghezza(36);
            campo.setAllineamentoLista(SwingConstants.CENTER);
            campi.add(campo);

            campo = CampoFactory.intero(Cam.bambini);
            campo.setLarghezza(36);
            campo.setAllineamentoLista(SwingConstants.CENTER);
            campi.add(campo);

            campo = CampoFactory.testo(Cam.trattamento);
            campo.setLarghezza(40);
            campo.setAllineamentoLista(SwingConstants.CENTER);
            campi.add(campo);

            Campo campoLunchBoh = CampoFactory.checkBox(Cam.lunchBoh);
            campi.add(campoLunchBoh);

            campo = CampoFactory.intero(Cam.lunchA);
            campo.setLarghezza(44);
            campo.setAllineamentoLista(SwingConstants.CENTER);
            campo.setRenderer(new RendererPasto(campo, campoLunchBoh));
            campo.setTotalizzabile(true);
            campi.add(campo);

            campo = CampoFactory.intero(Cam.lunchB);
            campo.setLarghezza(44);
            campo.setAllineamentoLista(SwingConstants.CENTER);
            campo.setRenderer(new RendererPasto(campo, campoLunchBoh));
            campo.setTotalizzabile(true);
            campi.add(campo);

            Campo campoDinnerBoh = CampoFactory.checkBox(Cam.dinnerBoh);
            campi.add(campoDinnerBoh);

            campo = CampoFactory.intero(Cam.dinnerA);
            campo.setLarghezza(44);
            campo.setAllineamentoLista(SwingConstants.CENTER);
            campo.setRenderer(new RendererPasto(campo, campoDinnerBoh));
            campo.setTotalizzabile(true);
            campi.add(campo);

            campo = CampoFactory.intero(Cam.dinnerB);
            campo.setLarghezza(44);
            campo.setAllineamentoLista(SwingConstants.CENTER);
            campo.setRenderer(new RendererPasto(campo, campoDinnerBoh));
            campo.setTotalizzabile(true);
            campi.add(campo);

            campo = CampoFactory.intero(Cam.arrivo);
            campo.setLarghezza(36);
            campo.setAllineamentoLista(SwingConstants.CENTER);
            campo.setTotalizzabile(true);
            campi.add(campo);

            campo = CampoFactory.intero(Cam.cambio);
            campo.setLarghezza(36);
            campo.setAllineamentoLista(SwingConstants.CENTER);
            campo.setTotalizzabile(true);
            campi.add(campo);

            campo = CampoFactory.intero(Cam.partenza);
            campo.setLarghezza(36);
            campo.setAllineamentoLista(SwingConstants.CENTER);
            campo.setTotalizzabile(true);
            campi.add(campo);

            campo = CampoFactory.intero(Cam.codArrPar);
            campo.setModificabileLista(true);
            campo.setEditor(new APConEditor(this));
            campo.setRenderer(new APConRenderer(campo, this));
            campo.setLarghezza(100);
            campi.add(campo);

            campo = CampoFactory.checkBox(Cam.apConfermata);
            campo.setModificabileLista(true);
            campo.setEditor(new ConfermatoEditor(this));
            campo.setRenderer(new ConfermatoRenderer(campo, this));
            campo.setLarghezza(40);
            campi.add(campo);

            campo = CampoFactory.testo(Cam.cambio_da_a);
            campo.setLarghezza(70);
            campi.add(campo);

            campo = CampoFactory.testo(Cam.preparazione);
            campo.setLarghezza(50);
            campi.add(campo);

            campo = CampoFactory.testo(Cam.note);
            campo.setLarghezza(100);
            campi.add(campo);

            StatoGiornalieroModulo mod = new StatoGiornalieroModulo(campi);
            this.setModuloDati(mod);

            /* sostituisce il navigatore di default con uno a sola lista */
            Navigatore nav = new NavDati(mod, this);
            mod.setNavigatoreDefault(nav);

            /* avvia il modulo */
            mod.avvia();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Crea il pannello per i totali.
     * <p/>
     * Crea e registra le singole tabelle
     * Assembla le tabelle in un pannello
     * Restituisce il pannello
     *
     * @return il pannello creato
     */
    private Pannello creaPannelloTotali() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try {    // prova ad eseguire il codice

            this.setTabTotMovi(
                    new TabTotali(
                            "Movimento del giorno",
                            KEY_PRESENTI_IERI,
                            KEY_ARRIVI_OGGI,
                            KEY_PARTENZE_OGGI,
                            KEY_PRESENTI_OGGI));

            this.setTabTotOggi(new TabTotali("Presenti oggi", KEY_FB, KEY_HB, KEY_BB));

            this.setTabTotPasti(
                    new TabTotali(
                            "Presenti ai pasti", KEY_LUNCH, KEY_DINNER, KEY_BREAKFAST));

            pan = PannelloFactory.orizzontale(null);
            pan.add(this.getTabTotMovi());
            pan.add(Box.createHorizontalStrut(20));
            pan.add(this.getTabTotOggi());
            pan.add(Box.createHorizontalStrut(20));
            pan.add(this.getTabTotPasti());

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Aggiorna i totali.
     * <p/>
     * Aggiorna i totali della lista e rigenera le tabelle dei totali
     */
    private void aggiornaTotali() {

        try {    // prova ad eseguire il codice

            this.getNavDati().getLista().aggiornaTotali();

            this.aggiornaTotMovimenti();
            this.aggiornaTotPresOggi();
            this.aggiornaTotPresPasti();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiorna la tabella totali Movimenti del Giorno.
     * <p/>
     */
    private void aggiornaTotMovimenti() {
        /* variabili e costanti locali di lavoro */
        TabTotali tab;
        int numArrAdulti;
        int numArrBambini;
        int numParAdulti;
        int numParBambini;
        int numPresAdulti;
        int numPresBambini;

        int numArrivi;
        int numPartenze;
        int numPresenti;

        try { // prova ad eseguire il codice

            Date dataCurr = this.getDataCorrente();

            /* presenze a ieri */
//            Date dataPrima = Lib.Data.add(this.getDataCorrente(), -1);
//            int adultiArr = this.getNumArriviPartenze(null, dataPrima, true, false);
//            int bambiniArr = this.getNumArriviPartenze(null, dataPrima, true, true);
//            int adultiPar = this.getNumArriviPartenze(null, dataPrima, false, false);
//            int bambiniPar = this.getNumArriviPartenze(null, dataPrima, false, true);
//            int presentiPrimaAd = adultiArr - adultiPar;
//            int presentiPrimaBam = bambiniArr - bambiniPar;
//            int presentiPrima = presentiPrimaAd + presentiPrimaBam;
//            String stringaPrima = "(" + presentiPrimaAd + "+" + presentiPrimaBam + ")";

//            /* arrivi di oggi */
//            numArrAdulti = this.getNumArriviPartenze(dataCurr, dataCurr, true, false);
//            numArrBambini = this.getNumArriviPartenze(dataCurr, dataCurr, true, true);
//            numArrivi = numArrAdulti + numArrBambini;
//            String stringaArrivi = "(" + numArrAdulti + "+" + numArrBambini + ")";

//            /* partenze di oggi */
//            numParAdulti = this.getNumArriviPartenze(dataCurr, dataCurr, false, false);
//            numParBambini = this.getNumArriviPartenze(dataCurr, dataCurr, false, true);
//            numPartenze = numParAdulti + numParBambini;
//            String stringaPartenze = "(" + numParAdulti + "+" + numParBambini + ")";

//            /* presenti di oggi */
//            numPresAdulti = presentiPrimaAd + numArrAdulti - numParAdulti;
//            numPresBambini = presentiPrimaBam + numArrBambini - numParBambini;
//            numPresenti = numPresAdulti + numPresBambini;
//            String stringaPresenti = "(" + numPresAdulti + "+" + numPresBambini + ")";

            /* crea un Calcolatore Movimenti del giorno */
            CalcMovimenti calc = CalcFactory.creaCalcolatore(dataCurr, dataCurr);

            /* presenze al giorno prima */
            int presentiPrimaAd = calc.getNumPrecAd();
            int presentiPrimaBam = calc.getNumPrecBa();
            int presentiPrima = presentiPrimaAd + presentiPrimaBam;
            String stringaPrima = "(" + presentiPrimaAd + "+" + presentiPrimaBam + ")";

            /* arrivi del giorno */
            numArrAdulti = calc.getNumArriviAd();
            numArrBambini = calc.getNumArriviBa();
            numArrivi = numArrAdulti + numArrBambini;
            String stringaArrivi = "(" + numArrAdulti + "+" + numArrBambini + ")";

            /* partenze del giorno */
            numParAdulti = calc.getNumPartenzeAd();
            numParBambini = calc.getNumPartenzeBa();
            numPartenze = numParAdulti + numParBambini;
            String stringaPartenze = "(" + numParAdulti + "+" + numParBambini + ")";

            /* presenti di oggi */
            numPresAdulti = calc.getNumFinaleAd();
            numPresBambini = calc.getNumFinaleBa();
            numPresenti = numPresAdulti + numPresBambini;
            String stringaPresenti = "(" + numPresAdulti + "+" + numPresBambini + ")";

            /* aggiorna la tabella movimenti del giorno */
            tab = this.getTabTotMovi();
            tab.setValore(KEY_PRESENTI_IERI, presentiPrima, stringaPrima);
            tab.setValore(KEY_ARRIVI_OGGI, numArrivi, stringaArrivi);
            tab.setValore(KEY_PARTENZE_OGGI, numPartenze, stringaPartenze);
            tab.setValore(KEY_PRESENTI_OGGI, numPresenti, stringaPresenti);

            /* memorizza questi totali per uso successivo (breakfast) */
            this.totPresOggiA = numPresAdulti;
            this.totPresOggiB = numPresBambini;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Aggiorna la tabella totali Presenti di Oggi.
     * <p/>
     * Sono tutti quelli che non partono oggi
     * Valori suddivisi per Trattamento
     */
    private void aggiornaTotPresOggi() {
        /* variabili e costanti locali di lavoro */
        TabTotali tab;
        Modulo mod = this.getModuloDati();
        Filtro filtro;
        Filtro filtroNoPartenze;
        Filtro filtroTrat;
        int totFBA, totFBB;
        int totHBA, totHBB;
        int totBBA, totBBB;
        String strFB, strHB, strBB;

        try { // prova ad eseguire il codice

            filtroNoPartenze = FiltroFactory.crea(Cam.partenza.get(), 0);

            filtroTrat = FiltroFactory.crea(
                    Cam.trattamento.get(), Listino.PensioniPeriodo.pensioneCompleta.getSigla());
            filtro = new Filtro();
            filtro.add(filtroNoPartenze);
            filtro.add(filtroTrat);
            totFBA = Libreria.getInt(mod.query().somma(Cam.adulti.get(), filtro));
            totFBB = Libreria.getInt(mod.query().somma(Cam.bambini.get(), filtro));
            strFB = "(" + totFBA + "+" + totFBB + ")";

            filtroTrat = FiltroFactory.crea(
                    Cam.trattamento.get(), Listino.PensioniPeriodo.mezzaPensione.getSigla());
            filtro = new Filtro();
            filtro.add(filtroNoPartenze);
            filtro.add(filtroTrat);
            totHBA = Libreria.getInt(mod.query().somma(Cam.adulti.get(), filtro));
            totHBB = Libreria.getInt(mod.query().somma(Cam.bambini.get(), filtro));
            strHB = "(" + totHBA + "+" + totHBB + ")";

            filtroTrat = FiltroFactory.crea(
                    Cam.trattamento.get(), Listino.PensioniPeriodo.pernottamento.getSigla());
            filtro = new Filtro();
            filtro.add(filtroNoPartenze);
            filtro.add(filtroTrat);
            totBBA = Libreria.getInt(mod.query().somma(Cam.adulti.get(), filtro));
            totBBB = Libreria.getInt(mod.query().somma(Cam.bambini.get(), filtro));
            strBB = "(" + totBBA + "+" + totBBB + ")";

            /* aggiorna la tabella movimenti del giorno */
            tab = this.getTabTotOggi();
            tab.setValore(KEY_FB, totFBA + totFBB, strFB);
            tab.setValore(KEY_HB, totHBA + totHBB, strHB);
            tab.setValore(KEY_BB, totBBA + totBBB, strBB);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiorna la tabella totali Presenti ai Pasti.
     * <p/>
     */
    private void aggiornaTotPresPasti() {
        /* variabili e costanti locali di lavoro */
        TabTotali tab;
        int totLunchA, totLunchB;
        int totDinnerA, totDinnerB;
        int totBreakfastA, totBreakfastB;
        String strLunch, strDinner, strBreakfast;
        Modulo mod = this.getModuloDati();
        Number num;

        try { // prova ad eseguire il codice

            /* lunch */
            num = mod.query().somma(Cam.lunchA.get(), null);
            totLunchA = Libreria.getInt(num);
            num = mod.query().somma(Cam.lunchB.get(), null);
            totLunchB = Libreria.getInt(num);
            strLunch = "(" + totLunchA + "+" + totLunchB + ")";

            /* dinner */
            num = mod.query().somma(Cam.dinnerA.get(), null);
            totDinnerA = Libreria.getInt(num);
            num = mod.query().somma(Cam.dinnerB.get(), null);
            totDinnerB = Libreria.getInt(num);
            strDinner = "(" + totDinnerA + "+" + totDinnerB + ")";

            /* breakfast di domani */
            totBreakfastA = totPresOggiA;
            totBreakfastB = totPresOggiB;
            strBreakfast = "(" + totBreakfastA + "+" + totBreakfastB + ")";

            /* aggiorna la tabella movimenti del giorno */
            tab = this.getTabTotPasti();
            tab.setValore(KEY_LUNCH, totLunchA + totLunchB, strLunch);
            tab.setValore(KEY_DINNER, totDinnerA + totDinnerB, strDinner);
            tab.setValore(KEY_BREAKFAST, totBreakfastA + totBreakfastB, strBreakfast);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna il numero di adulti/bambini in arrivo/partenza su un periodo.
     * <p/>
     *
     * @param dataIni data iniziale
     * @param dataEnd data finale
     * @param arrivi true per arrivi false per partenze
     * @param bambini true per bambini false per adulti
     *
     * @return il numero di arrivi richiesto
     */
    private int getNumArriviPartenze(
            Date dataIni,
            Date dataEnd,
            boolean arrivi,
            boolean bambini) {
        /* variabili e costanti locali di lavoro */
        int quanti = 0;
        Number numero;
        Filtro filtro;
        String nomeCampoSomma;
        Modulo modPeriodo;

        try {    // prova ad eseguire il codice
            modPeriodo = PeriodoModulo.get();
            if (arrivi) {
                filtro = PeriodoModulo.getFiltroArrivi(dataIni, dataEnd);
            } else {
                filtro = PeriodoModulo.getFiltroPartenze(dataIni, dataEnd);
            }// fine del blocco if-else
            if (bambini) {  // bambini
                nomeCampoSomma = Periodo.Cam.bambini.get();
            } else {        // adulti
                nomeCampoSomma = Periodo.Cam.adulti.get();
            }// fine del blocco if-else
            numero = modPeriodo.query().somma(nomeCampoSomma, filtro);
            quanti = Libreria.getInt(numero);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return quanti;
    }


    /**
     * Esegue l'analisi.
     * <p/>
     */
    protected void eventoEsegui() {

        /* aggiorna la data corrente in base a quanto inserito */
        this.setDataCorrente(this.getDataCampo());

        this.getModuloDati().query().eliminaRecords();
        this.caricaPeriodi(this.creaFiltroPeriodiGiorno());
        this.syncFiltroLibere();
        this.getNavDati().aggiornaLista();
        this.aggiornaTotali();

        /* aggiorna la data di analisi a video */
        String nomegiorno = Lib.Data.getGiorno(this.getDataCorrente());
        String testo = Lib.Testo.primaMaiuscola(nomegiorno) + " " + Lib.Data.getDataEstesa(this.getDataCorrente());
        this.getLabelGiorno().setText(testo);

        this.sincronizza();
    }


    /**
     * Crea un filtro per i Periodi che isola i periodi interessati
     * per la data correntemente impostata.
     * <p/>
     * Tutti i periodi che intersecano la data di analisi
     * la cui Prenotazione è valida.
     *
     * @return il filtro creato
     */
    private Filtro creaFiltroPeriodiGiorno() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        PrenotazioneModulo modPren = PrenotazioneModulo.get();

        try {    // prova ad eseguire il codice
            /* crea il filtro */
            Filtro filtroData = PeriodoModulo.getFiltroInteressati(this.getDataCorrente(), this.getDataCorrente());
            Filtro filtroValidi = PrenotazioneModulo.getFiltroValide();
            Filtro filtroAzienda = modPren.getFiltroAzienda();
            filtro = new Filtro();
            filtro.add(filtroData);
            filtro.add(filtroValidi);
            filtro.add(filtroAzienda);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Esegue l'analisi e la stampa.
     * <p/>
     */
    protected void eventoStampa() {
        this.stampa();
    }


    /**
     * Esegue l'analisi e la esporta.
     * <p/>
     */
    private void eventoEsporta() {
        this.esporta();
    }


    /**
     * Fatto doppio clic su una riga del navigatore.
     * <p/>
     * Se possibile apre la prenotazione in dialogo modale
     * Al ritorno aggiorna i dati (tutta la tabella)
     */
    void doppioClic() {
        /* variabili e costanti locali di lavoro */
        int codPrenotazione;
        boolean confermato;

        try {    // prova ad eseguire il codice
            codPrenotazione = this.getCodPrenSelezionata();
            if (codPrenotazione > 0) {
                Modulo mod = PrenotazioneModulo.get();
                confermato = mod.presentaRecord(codPrenotazione);
                if (confermato) {
                    int codPeri = this.getCodPeriSelezionato();
                    this.refreshPeriodo(codPeri);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Apre lo storico del cliente.
     * <p/>
     */
    private void showStorico() {
        /* variabili e costanti locali di lavoro */
        int codPrenotazione;
        int codCliente;
        Modulo mod;

        try { // prova ad eseguire il codice
            codPrenotazione = this.getCodPrenSelezionata();
            if (codPrenotazione > 0) {
                mod = PrenotazioneModulo.get();
                codCliente = mod.query().valoreInt(Prenotazione.Cam.cliente.get(), codPrenotazione);
                if (codCliente > 0) {
                    ClienteAlbergoModulo.showStorico(codCliente);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna il codice del periodo corrispondente
     * alla riga selezionata nel navigatore.
     * <p/>
     *
     * @return il codice del periodo, 0 se non disponibile
     */
    private int getCodPeriSelezionato() {
        /* variabili e costanti locali di lavoro */
        int codPeriodo = 0;
        Modulo mod;

        try { // prova ad eseguire il codice
            int cod = this.getNavDati().getLista().getChiaveSelezionata();
            if (cod > 0) {
                mod = this.getModuloDati();
                codPeriodo = mod.query().valoreInt(Cam.codperiodo.get(), cod);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codPeriodo;

    }


    /**
     * Ritorna il codice della prenotazione corrispondente
     * alla riga selezionata nel navigatore.
     * <p/>
     *
     * @return il codice della prenotazione, 0 se non disponibile
     */
    private int getCodPrenSelezionata() {
        /* variabili e costanti locali di lavoro */
        int codPrenotazione = 0;
        int codPeriodo;
        Modulo mod;

        try { // prova ad eseguire il codice
            codPeriodo = this.getCodPeriSelezionato();
            if (codPeriodo > 0) {
                mod = PeriodoModulo.get();
                codPrenotazione = mod.query()
                        .valoreInt(Periodo.Cam.prenotazione.get(), codPeriodo);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codPrenotazione;

    }


    /**
     * Stampa.
     * <p/>
     * Invocato dal bottone Stampa del dialogo
     */
    public void stampa() {
        /* variabili e costanti locali di lavoro */
        Printer printer;
        J2FlowPrinter fpTot;
        Tavola tavola;

        try { // prova ad eseguire il codice

            /* crea un printer orizzontale */
            printer = Lib.Stampa.getDefaultPrinter();
            printer.setOrientation(SwingConstants.HORIZONTAL);
            String testo = "Situazione giornaliera di " + this.getLabelGiorno().getText();
            printer.setCenterHeader(testo);

            /* aggiunge un flow printer dalla tavola dati */
            fpTot = new J2FlowPrinter();
            tavola = this.getNavDati().getLista().getTavola();
            fpTot.addFlowable(tavola.getTablePrinter());

            /* aggiunge un gap */
            fpTot.addFlowable(new J2ComponentPrinter(Lib.Comp.createVerticalFiller(10, 10, 10)));

            /* aggiunge un flow printer dalla tavola totali */
            tavola = this.getNavDati().getLista().getTavolaTotali();
            fpTot.addFlowable(tavola.getTablePrinter(false));

            /* aggiunge un gap */
            fpTot.addFlowable(new J2ComponentPrinter(Lib.Comp.createVerticalFiller(10, 10, 10)));

            /* aggiunge il pannello totali */
            Pannello pan = this.getPanTotali();
            fpTot.addFlowable(new J2PanelPrinter(pan.getPanFisso()));

            /* aggiunge il flow printer completo */
            printer.addPageable(fpTot);

            /* stampa */
            printer.showPrintPreviewDialog();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }



    /**
     * Esporta.
     * <p/>
     * Invocato dal bottone del dialogo
     */
    public void esporta() {
        /* variabili e costanti locali di lavoro */
        Dati dati;
        Dati dati2 = null;
        ExportSettings settings;
        Esporta exporter;
        boolean continua = true;
        boolean dati2creato = false;

        try { // prova ad eseguire il codice

            /* recupera i dati e controlla che esistano */
            dati = this.getNavDati().getLista().getTavola().getModello().getDati();
            if (dati == null) {
                new MessaggioAvviso("Non ci sono dati da esportare");
                continua = false;
            }// fine del blocco if-else

            /* crea un nuovo Dati2 con le solo colonne esportabili */
            if (continua) {

                Modulo mod = this.getModuloDati();
                Cam[] esportabili = Cam.getCampiEsportabili();
                ArrayList<Campo> campi = new ArrayList<Campo>();

                for(Cam cam :esportabili){
                    Campo campo = mod.getCampo(cam);
                    campi.add(campo);
                }

                dati2 = new DatiMemoria(campi, dati.getRowCount());
                dati2creato = true;

                /* copia i valori */
                Object val;
                for (int row = 0; row < dati.getRowCount(); row++) {
                    for (Campo campo : campi) {
                        val = dati.getValueAt(row, campo);
                        dati2.setValueAt(row, campo, val);
                    }
                } // fine del ciclo for
            }// fine del blocco if

            /* presenta i settings e se confermato esporta */
            if (continua) {
                settings = new ExportSettings();
                if (settings.edit()) {
                    exporter = new Esporta(dati2, settings);
                    exporter.run();
                    Lib.Sist.beep();
                    new MessaggioAvviso("Terminato.");
                }// fine del blocco if
            }// fine del blocco if

            /* se ha creato dati2 lo deve chiudere */
            if (dati2creato) {
                dati2.close();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Aggiorna i dati di un periodo dal database fino al video.
     * <p/>
     * Aggiorna anche la lista visualizzata e i totali
     *
     * @param codice del periodo da aggiornare
     */
    void refreshPeriodo(int codice) {
        try {    // prova ad eseguire il codice
            this.caricaPeriodo(codice);
            this.getNavDati().aggiornaLista();
            this.aggiornaTotali();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Carica i dati di un singolo periodo specificato.
     * <p/>
     * Se la riga esiste già nei dati la modifica se no la aggiunge
     *
     * @param codPeriodo codice del periodo da caricare
     */
    private void caricaPeriodo(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;
        Modulo modPeri = PeriodoModulo.get();

        try {    // prova ad eseguire il codice
            filtro = FiltroFactory.codice(modPeri, codPeriodo);
            this.caricaPeriodi(filtro);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Carica i dati nel modulo memoria.
     * <p/>
     * Tutti i periodi che intersecano la data di analisi
     * la cui Prenotazione è valida.
     *
     * @param filtro per isolare i periodi da caricare
     */
    private void caricaPeriodi(Filtro filtro) {
        /* variabili e costanti locali di lavoro */
        Dati datiDb;
        PeriodoModulo modPeri = PeriodoModulo.get();
        CameraModulo modCamera = CameraModulo.get();
        Modulo modCliente = ClienteAlbergoModulo.get();

        try { // prova ad eseguire il codice

            /* crea l'ordine */
            Ordine ordine = new Ordine();
            ordine.add(modCamera.getCampo(Camera.Cam.camera));

            /* crea la query completa */
            Query q = new QuerySelezione(modPeri);
            q.setFiltro(filtro);
            q.setOrdine(ordine);

            /* aggiunge i campi alla query */
            q.addCampo(modPeri.getCampoChiave());

            Campo cdbNomeCli = modCliente.getCampo(Anagrafica.Cam.soggetto);
            q.addCampo(cdbNomeCli);

            Campo cdbNomeCam = modCamera.getCampo(Camera.Cam.camera);
            q.addCampo(cdbNomeCam);

            q.addCampo(Periodo.Cam.camera);
            q.addCampo(Periodo.Cam.adulti);
            q.addCampo(Periodo.Cam.bambini);
            q.addCampo(Periodo.Cam.trattamento);
            q.addCampo(Periodo.Cam.pasto);
            q.addCampo(Periodo.Cam.causaleArrivo);
            q.addCampo(Periodo.Cam.arrivoPrevisto);
            q.addCampo(Periodo.Cam.causalePartenza);
            q.addCampo(Periodo.Cam.partenzaPrevista);
            q.addCampo(Periodo.Cam.arrivoCon);
            q.addCampo(Periodo.Cam.arrivoConfermato);
            q.addCampo(Periodo.Cam.partenzaCon);
            q.addCampo(Periodo.Cam.partenzaConfermata);
            q.addCampo(Periodo.Cam.preparazione);
            q.addCampo(Periodo.Cam.linkProvenienza);
            q.addCampo(Periodo.Cam.linkDestinazione);
            q.addCampo(Periodo.Cam.note);

            datiDb = modPeri.query().querySelezione(q);

            /* spazzola i dati, li elabora e riempie il modulo memoria */
            for (int k = 0; k < datiDb.getRowCount(); k++) {
                this.elaboraPeriodo(k, datiDb);
            } // fine del ciclo for

            /* chiude i dati del db */
            datiDb.close();

            /* aggiunge alla tabella le camere libere */
            this.addCamereLibere();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Elabora un periodo e crea o ricrea la
     * riga nella tabella memoria.
     * <p/>
     * Se non esiste crea la riga, se esiste già prima la
     * cancella e poi la ricrea
     * (chiave di confronto = cod Periodo)
     *
     * @param k indice della riga da elaborare nei dati forniti
     * @param datiDb dati forniti
     */
    private void elaboraPeriodo(int k, Dati datiDb) {
        /* variabili e costanti locali di lavoro */
        Date data;
        PeriodoModulo modPeri = PeriodoModulo.get();
        CameraModulo modCamera = CameraModulo.get();
        Modulo modCliente = ClienteAlbergoModulo.get();
        Modulo modCompoCamera = CompoCameraModulo.get();
        boolean arrivo, partenza, fermata, cambioentrata, cambiouscita;
        int codAP;
        Periodo.CausaleAP causaleAP;
        Periodo.TipiAP arrivoCon = null;
        Periodo.TipiAP partenzaCon = null;
        boolean arrConfermato = false;
        boolean partConfermata = false;
        Presenza.TipiPasto pastoMezzapens;
        int codLunch, codDinner;
        int codPasto;
        int codCam;
        int codPeri;

        try {    // prova ad eseguire il codice

            /**
             * Controlla se è un arrivo
             * accende il flag arrivo o cambioentrata
             * recupera il valore di arrivo con
             */
            arrivo = false;
            cambioentrata = false;
            data = datiDb.getDataAt(k, Periodo.Cam.arrivoPrevisto.get());
            if (data.equals(this.getDataCorrente())) {
                codAP = datiDb.getIntAt(k, Periodo.Cam.causaleArrivo.get());
                causaleAP = Periodo.CausaleAP.getCausale(codAP);
                switch (causaleAP) {
                    case normale:
                        arrivo = true;
                        int cod = datiDb.getIntAt(k, Periodo.Cam.arrivoCon.get());
                        arrivoCon = Periodo.TipiAP.get(cod);
                        arrConfermato = datiDb.getBoolAt(k, Periodo.Cam.arrivoConfermato.get());
                        break;
                    case cambio:
                        cambioentrata = true;
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch

            }// fine del blocco if

            /**
             * Controlla se è una partenza
             * accende il flag partenza o cambiouscita
             * recupera il valore di partenza con
             */
            partenza = false;
            cambiouscita = false;
            data = datiDb.getDataAt(k, Periodo.Cam.partenzaPrevista.get());
            if (data.equals(this.getDataCorrente())) {
                codAP = datiDb.getIntAt(k, Periodo.Cam.causalePartenza.get());
                causaleAP = Periodo.CausaleAP.getCausale(codAP);
                switch (causaleAP) {
                    case normale:
                        partenza = true;
                        int cod = datiDb.getIntAt(k, Periodo.Cam.partenzaCon.get());
                        partenzaCon = Periodo.TipiAP.get(cod);
                        partConfermata = datiDb.getBoolAt(k, Periodo.Cam.partenzaConfermata.get());
                        break;
                    case cambio:
                        cambiouscita = true;
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch

            }// fine del blocco if

            /**
             * Controlla se è una fermata
             * è una fermata se non è né arrivo né partenza
             */
            fermata = ((!arrivo) && (!partenza));

            /**
             * Recupera il pasto di mezza pensione
             */
            codPasto = datiDb.getIntAt(k, Periodo.Cam.pasto.get());
            pastoMezzapens = Presenza.TipiPasto.get(codPasto);

            SetValori sv = new SetValori(this.getModuloDati());

            codPeri = datiDb.getIntAt(k, modPeri.getCampoChiave());
            sv.add(Cam.codperiodo, codPeri);

            codCam = datiDb.getIntAt(k, Periodo.Cam.camera.get());
            sv.add(Cam.codcamera, codCam);

            Campo cdbNomeCam = modCamera.getCampo(Camera.Cam.camera);
            String nomeCam = datiDb.getStringAt(k, cdbNomeCam);
            sv.add(Cam.camera, nomeCam);

            Campo cdbNomeCli = modCliente.getCampo(Anagrafica.Cam.soggetto);
            String nomeCli = datiDb.getStringAt(k, cdbNomeCli);
            sv.add(Cam.cliente, nomeCli);

            int adulti = datiDb.getIntAt(k, Periodo.Cam.adulti.get());
            sv.add(Cam.adulti, adulti);

            int bambini = datiDb.getIntAt(k, Periodo.Cam.bambini.get());
            sv.add(Cam.bambini, bambini);

            int codTratt = datiDb.getIntAt(k, Periodo.Cam.trattamento.get());
            Listino.PensioniPeriodo trattamento = Listino.PensioniPeriodo.get(codTratt);
            if (trattamento != null) {
                sv.add(Cam.trattamento, trattamento.getSigla());
            } else {
                sv.add(Cam.trattamento, "?");
            }// fine del blocco if-else


            /* arrivo o partenza confermati */
            if (arrivo) {
                sv.add(Cam.apConfermata, arrConfermato);
            }// fine del blocco if
            if (partenza) {
                sv.add(Cam.apConfermata, partConfermata);
            }// fine del blocco if


            /**
             * Colonne pasti:
             * se HB, controlla se pranzo o cena
             * se BB nessun pasto
             * se FB tutti i pasti
             * se in arrivo o in partenza considera il pasto di arrivo e partenza
             */
            codLunch = 2;    // 0=no, 1=si, 2=boh - si parte con boh e si cerca
            codDinner = 2;      // 0=no, 1=si, 2=boh - si parte con boh e si cerca

            if (trattamento != null) {   // se manca il trattamento resta boh

                switch (trattamento) {

                    case mezzaPensione:

                        // mezza pensione - in arrivo
                        if (arrivo) {
                            if (arrivoCon != null) {
                                switch (arrivoCon) {
                                    case breakfast:     // arrivo a 1/2 pens con 1a col
                                        if (pastoMezzapens != null) {
                                            switch (pastoMezzapens) {
                                                case breakfast:  // arriva con 1a colaz e fa 1a colaz (?)
                                                    codLunch = 0;
                                                    codDinner = 0;
                                                    break;
                                                case lunch: // arriva con 1a colaz e fa 2a colaz
                                                    codLunch = 1;
                                                    codDinner = 0;
                                                    break;
                                                case dinner: // arriva con 1a colaz e fa pranzo
                                                    codLunch = 0;
                                                    codDinner = 1;
                                                    break;
                                                default: // caso non definito
                                                    break;
                                            } // fine del blocco switch
                                        }// fine del blocco if
                                        break;
                                    case lunch:     // arrivo a 1/2 pens con 2a colaz
                                        if (pastoMezzapens != null) {
                                            switch (pastoMezzapens) {
                                                case breakfast:  // arriva con 2a colaz e fa 1a colaz (?)
                                                    codLunch = 0;
                                                    codDinner = 0;
                                                    break;
                                                case lunch: // arriva con 2a colaz e fa 2a colaz
                                                    codLunch = 1;
                                                    codDinner = 0;
                                                    break;
                                                case dinner: // arriva con 2a colaz e fa pranzo
                                                    codLunch = 0;
                                                    codDinner = 1;
                                                    break;
                                                default: // caso non definito
                                                    break;
                                            } // fine del blocco switch
                                        }// fine del blocco if
                                        break;
                                    case dinner:  // arrivo a 1/2 pens con pranzo
                                        codLunch
                                                = 0; // visto che arriva con cena il pranzo lo escludo subito
                                        if (pastoMezzapens != null) {
                                            switch (pastoMezzapens) {
                                                case breakfast:  // arriva con pranzo e fa 1a colaz (?)
                                                    codLunch = 0;
                                                    codDinner = 0;
                                                    break;
                                                case lunch: // arriva con pranzo e fa 2a colaz
                                                    codLunch = 0;
                                                    codDinner = 0;
                                                    break;
                                                case dinner: // arriva con pranzo e fa pranzo
                                                    codLunch = 0;
                                                    codDinner = 1;
                                                    break;
                                                default: // caso non definito
                                                    break;
                                            } // fine del blocco switch
                                        }// fine del blocco if
                                        break;
                                    case room:  // arrivo a 1/2 pens con camera
                                        codLunch = 0;
                                        codDinner = 0;
                                        break;
                                    default: // caso non definito
                                        break;
                                } // fine del blocco switch
                            } else {  // in arrivo a 1/2 pens ma non so quando
                                // se ha come pasto di 1/2 pens il pranzo, posso escludere per certo la 2a colaz
                                if (pastoMezzapens != null) {
                                    if (pastoMezzapens.equals(Presenza.TipiPasto.dinner)) {
                                        codLunch = 0;
                                    }// fine del blocco if
                                }// fine del blocco if-else
                            }// fine del blocco if-else
                        }

                        // mezza pensione - in partenza
                        if (partenza) {
                            if (partenzaCon != null) {
                                switch (partenzaCon) {
                                    case breakfast:      // partenza a 1/2 pens con 1a col
                                        codLunch = 0;
                                        codDinner = 0;
                                        break;
                                    case lunch:      // partenza a 1/2 pens con 2a colaz
                                        codDinner
                                                = 0;  // partendo con 2a colaz, posso escludere per certo il pranzo
                                        if (pastoMezzapens != null) {
                                            switch (pastoMezzapens) {
                                                case breakfast: //(?)
                                                    codLunch = 0;
                                                    codDinner = 0;
                                                    break;
                                                case lunch: // parte con 2a colaz e fa 2a colaz
                                                    codLunch = 1;
                                                    codDinner = 0;
                                                    break;
                                                case dinner:// parte con 2a colaz e fa pranzo
                                                    codLunch = 0;
                                                    codDinner = 0;
                                                    break;
                                                default: // caso non definito
                                                    break;
                                            } // fine del blocco switch
                                        }// fine del blocco if
                                        break;
                                    case dinner:     // partenza a 1/2 pens con pranzo
                                        if (pastoMezzapens != null) {
                                            switch (pastoMezzapens) {
                                                case breakfast: //(?)
                                                    codLunch = 0;
                                                    codDinner = 0;
                                                    break;
                                                case lunch: // parte con pranzo e fa 2a colaz
                                                    codLunch = 1;
                                                    codDinner = 0;
                                                    break;
                                                case dinner: // parte con pranzo e fa pranzo
                                                    codLunch = 0;
                                                    codDinner = 1;
                                                    break;
                                                default: // caso non definito
                                                    break;
                                            } // fine del blocco switch
                                        }// fine del blocco if
                                        break;
                                    case room:    // partenza a 1/2 pens con camera, parte prima di colazione, non mangia
                                        codLunch = 0;
                                        codDinner = 0;
//                                        if (pastoMezzapens != null) {
//                                            switch (pastoMezzapens) {
//                                                codLunch = 0;
//                                                codDinner = 0;
//
//                                                case breakfast:  //(?)
//                                                    codLunch = 0;
//                                                    codDinner = 0;
//                                                    break;
//                                                case lunch:
//                                                    codLunch = 1;
//                                                    codDinner = 0;
//                                                    break;
//                                                case dinner:
//                                                    codLunch = 0;
//                                                    codDinner = 1;
//                                                    break;
//                                                default: // caso non definito
//                                                    break;
//                                            } // fine del blocco switch
//                                        }// fine del blocco if
                                        break;
                                    default: // caso non definito
                                        break;
                                } // fine del blocco switch

                            } else {  // in partenza a 1/2 pens ma non so quando

                                // se ha come pasto di 1/2 pens il pranzo, posso escludere per certo la 2a colaz
                                if (pastoMezzapens != null) {
                                    if (pastoMezzapens.equals(Presenza.TipiPasto.dinner)) {
                                        codLunch = 0;
                                    }// fine del blocco if
                                }// fine del blocco if-else
                            }// fine del blocco if-else
                        }// fine del blocco if

                        // mezza pensione - in permanenza
                        if (fermata) {
                            if (pastoMezzapens != null) {
                                switch (pastoMezzapens) {
                                    case breakfast:
                                        codLunch = 0;
                                        codDinner = 0;
                                        break;
                                    case lunch:
                                        codLunch = 1;
                                        codDinner = 0;
                                        break;
                                    case dinner:
                                        codLunch = 0;
                                        codDinner = 1;
                                        break;
                                    default: // caso non definito
                                        break;
                                } // fine del blocco switch
                            }// fine del blocco if
                        }// fine del blocco if

                        break;

                    case pensioneCompleta:

                        // pensione completa - in arrivo
                        if (arrivo) {
                            if (arrivoCon != null) {    // se non si sa come arrivano è boh
                                switch (arrivoCon) {
                                    case breakfast:
                                        codLunch = 1;
                                        codDinner = 1;
                                        break;
                                    case lunch:
                                        codLunch = 1;
                                        codDinner = 1;
                                        break;
                                    case dinner:
                                        codLunch = 0;
                                        codDinner = 1;
                                        break;
                                    case room:
                                        codLunch = 0;
                                        codDinner = 0;
                                        break;
                                    default: // caso non definito
                                        break;
                                } // fine del blocco switch
                            }// fine del blocco if
                        }// fine del blocco if

                        // pensione completa - in partenza
                        if (partenza) {
                            if (partenzaCon != null) {       // se non si sa come partono è boh
                                switch (partenzaCon) {
                                    case breakfast:
                                        codLunch = 0;
                                        codDinner = 0;
                                        break;
                                    case lunch:
                                        codLunch = 1;
                                        codDinner = 0;
                                        break;
                                    case dinner:
                                        codLunch = 1;
                                        codDinner = 1;
                                        break;
                                    case room:    // parte prima di colazione, non mangia
                                        codLunch = 0;
                                        codDinner = 0;
                                        break;
                                    default: // caso non definito
                                        break;
                                } // fine del blocco switch
                            }// fine del blocco if
                        }// fine del blocco if

                        // pensione completa - in permanenza
                        if (fermata) {
                            codLunch = 1;
                            codDinner = 1;
                        }// fine del blocco if

                        break;

                    // pernottamento - non mangiano mai
                    case pernottamento:
                        codLunch = 0;
                        codDinner = 0;
                        break;

                    default: // tipo di trattamento non previsto
                        break;
                } // fine del blocco switch

            }// fine del blocco if-else

            /* scrive il valore dei campi pranzo e cena in base ai codici 0,1,2
            * e aggiorna di conseguenza i totali */
            switch (codLunch) {
                case 0:
                    break;
                case 1:
                    sv.add(Cam.lunchA, adulti);
                    sv.add(Cam.lunchB, bambini);
                    break;
                case 2:
                    sv.add(Cam.lunchBoh, true);
                    break;
                default: // caso non definito
            } // fine del blocco switch

            switch (codDinner) {
                case 0:
                    break;
                case 1:
                    sv.add(Cam.dinnerA, adulti);
                    sv.add(Cam.dinnerB, bambini);
                    break;
                case 2:
                    sv.add(Cam.dinnerBoh, true);
                    break;
                default: // caso non definito
            } // fine del blocco switch

            /* colonna arrivo */
            if (arrivo) {
                sv.add(Cam.arrivo, adulti + bambini);
            }// fine del blocco if

            /* colonna cambio */
            if (cambioentrata || cambiouscita) {
                sv.add(Cam.cambio, adulti + bambini);
            }// fine del blocco if

            /* colonna partenza */
            if (partenza) {
                sv.add(Cam.partenza, adulti + bambini);
            }// fine del blocco if

            /* colonna codice arrivo con o partenza con */
            if (arrivo || partenza) {
                Periodo.TipiAP ap = null;
                if (arrivo) {
                    ap = arrivoCon;
                }// fine del blocco if
                if (partenza) {
                    ap = partenzaCon;
                }// fine del blocco if
                if (ap != null) {
                    sv.add(Cam.codArrPar, ap.getCodice());
                }// fine del blocco if
            }// fine del blocco if

            /* colonna dettagli cambio */
            if (cambioentrata || cambiouscita) {
                codPeri = 0;
                String prep = "";

                if (cambioentrata) {
                    codPeri = datiDb.getIntAt(k, Periodo.Cam.linkProvenienza.get());
                    prep = "dalla";
                }// fine del blocco if

                if (cambiouscita) {
                    codPeri = datiDb.getIntAt(k, Periodo.Cam.linkDestinazione.get());
                    prep = "alla";
                }// fine del blocco if

                codCam = modPeri.query().valoreInt(Periodo.Cam.camera.get(), codPeri);
                String nome = modCamera.query().valoreStringa(Camera.Cam.camera.get(), codCam);
                String stringa = prep + " " + nome;
                sv.add(Cam.cambio_da_a, stringa);
            }// fine del blocco if

            /* colonna Preparazione */
            if (arrivo || cambioentrata) {
                int codPrep = datiDb.getIntAt(k, Periodo.Cam.preparazione.get());
                String stringa = modCompoCamera.query()
                        .valoreStringa(CompoCamera.Cam.sigla.get(), codPrep);
                sv.add(Cam.preparazione, stringa);
            }// fine del blocco if

            /* colonna Note */
            String stringa = datiDb.getStringAt(k, Periodo.Cam.note.get());
            sv.add(Cam.note, stringa);


            /**
             * Aggiorna il record del periodo se esistente
             * Lo crea se non esistente
             */
            Modulo modDati = this.getModuloDati();
            Filtro filtro = FiltroFactory.crea(Cam.codperiodo.get(), codPeri);
            int codRiga = modDati.query().valoreChiave(filtro);
            if (codRiga > 0) {
                sv.ensureAllFields();  // aggiunge eventuali campi non contenuti nel setvalori
                modDati.query().registraRecordValori(codRiga, sv.getListaValori());
            } else {
                modDati.query().nuovoRecord(sv.getListaValori());
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Aggiunge alla tabella le camere libere (tutte quelle che non ci sono già).
     * <p/>
     */
    private void addCamereLibere() {
        /* variabili e costanti locali di lavoro */
        Query query;
        Dati datiCamere;

        try {    // prova ad eseguire il codice

            Modulo modCamera = CameraModulo.get();
            Modulo modDati = this.getModuloDati();

            query = new QuerySelezione(modCamera);
            query.addCampo(modCamera.getCampoChiave());
            query.addCampo(Camera.Cam.camera);
            datiCamere = modCamera.query().querySelezione(query);

            for (int k = 0; k < datiCamere.getRowCount(); k++) {
                int codCamera = datiCamere.getIntAt(k, modCamera.getCampoChiave());
                String nomeCamera = datiCamere.getStringAt(k, Camera.Cam.camera.get());

                Filtro filtro = FiltroFactory.crea(Cam.codcamera.get(), codCamera);
                if (!modDati.query().isEsisteRecord(filtro)) {
                    SetValori sv = new SetValori(modDati);
                    sv.add(Cam.codcamera, codCamera);
                    sv.add(Cam.camera, nomeCamera);
                    sv.add(Cam.libera, true);
                    sv.add(Cam.cliente, "-- Libera --");
                    modDati.query().nuovoRecord(sv.getListaValori());
                }// fine del blocco if

            } // fine del ciclo for

            datiCamere.close();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Sincronizza il filtro base del modulo dati
     * per far vedere o meno le camere libere.
     * <p/>
     */
    private void syncFiltroLibere() {
        /* variabili e costanti locali di lavoro */
        boolean flag;
        Filtro filtro;

        try {    // prova ad eseguire il codice
            flag = this.getBool(NOME_CAMPO_CHECK_NASCONDI_LIBERE);
            if (flag) {
                filtro = FiltroFactory.crea(Cam.libera.get(), false);
            } else {
                filtro = null;
            }// fine del blocco if-else
            this.getNavDati().setFiltroBase(filtro);
            this.getNavDati().aggiornaLista();
            this.aggiornaTotali();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * @return la data correntemente presente nel campo di inserimento data.
     */
    private Date getDataCampo() {
        return this.getData(NOME_CAMPO_DATA);
    }


    @Override
    protected void eventoMemoriaModificata(Campo campo) {
        super.eventoMemoriaModificata(campo);

        if (this.isCampo(campo, NOME_CAMPO_CHECK_NASCONDI_LIBERE)) {
            this.syncFiltroLibere();
        }// fine del blocco if


    }


    @Override
    protected void eventoUscitaCampoModificato(Campo campo) {
        super.eventoUscitaCampoModificato(campo);

        if (this.isCampo(campo, NOME_CAMPO_DATA)) {
            Date dataCampo = this.getData(NOME_CAMPO_DATA);
            Date dataCurr = this.getDataCorrente();
            if (!dataCampo.equals(dataCurr)) {
                this.eventoEsegui();
            }// fine del blocco if
        }// fine del blocco if
    }


    @Override
    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        BottoneDialogo botStampa, botEsporta;

        super.sincronizza();

        /* abilitazione del bottone Stampa ed Esporta - ci deve essere la data corrente */
        botStampa = this.getBottoneStampa();
        botEsporta = this.getBottone(NOME_BOT_ESPORTA);
        JButton botEsegui = this.getBotEsegui();
        boolean valida = Lib.Data.isValida(this.getDataCorrente());
        botStampa.setEnabled(valida);
        botEsporta.setEnabled(valida);

        /**
         * abilitazione del bottone Esegui:
         * - nel campo ci deve essere una data valida
         * - deve essere diversa dalla data di analisi corrente
         */
        boolean abilita=false;
        Date dataCurr = this.getDataCorrente();
        Date dataCampo = this.getDataCampo();
        if (Lib.Data.isValida(dataCampo)) {
            if (!dataCampo.equals(dataCurr)) {
                abilita=true;
            }// fine del blocco if
        }// fine del blocco if
        botEsegui.setEnabled(abilita);

    }


    /**
     * Determina se un record del modulo dati è un Arrivo.
     * <p/>
     *
     * @param chiave del record
     *
     * @return true se arrivo
     */
    boolean isArrivo(int chiave) {
        int quanti = this.getModuloDati().query().valoreInt(Cam.arrivo.get(), chiave);
        return quanti > 0;
    }


    /**
     * Determina se un record del modulo dati è una Partenza.
     * <p/>
     *
     * @param chiave del record
     *
     * @return true se partenza
     */
    boolean isPartenza(int chiave) {
        int quanti = this.getModuloDati().query().valoreInt(Cam.partenza.get(), chiave);
        return quanti > 0;
    }


//    /**
//     * Determina se un record del modulo dati ha l'arrivo/partenza confermato.
//     * <p/>
//     *
//     * @param chiave del record
//     *
//     * @return true se è confermato
//     */
//    boolean isConfermato(int chiave) {
//        /* variabili e costanti locali di lavoro */
//        Campi campo;
//
//        try { // prova ad eseguire il codice
//            ;
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        if (this.isArrivo(chiave)) {
//            campo = ;
//        }// fine del blocco if
//        if (this.isPartenza(chiave)) {
//            codice;
//        }// fine del blocco if
//
//        int quanti = this.getModuloDati().query().valoreInt(Cam.partenza.get(), chiave);
//        return quanti > 0;
//    }



    /**
     * Ritorna il codice periodo relativo a un record.
     * <p/>
     *
     * @param chiave del record
     *
     * @return codice del periodo
     */
    int getPeriodo(int chiave) {
        return this.getModuloDati().query().valoreInt(Cam.codperiodo.get(), chiave);
    }


    /**
     * Invocato quando si clicca sul bottone Esegui.
     */
    private class AzEsegui extends AzAdapterAction {

        public void actionPerformed(ActionEvent e) {
            eventoEsegui();
        }
    }

    /**
     * Invocato quando si clicca sul bottone Esporta.
     */
    private class AzioneEsporta extends AzAdapterAction {

        public void actionPerformed(ActionEvent e) {
            eventoEsporta();
        }
    }

    /**
     * Invocato quando si clicca sul bottone Storico.
     */
    private class AzStorico extends AzAdapterAction {

        public void actionPerformed(ActionEvent e) {
            showStorico();
        }
    }


    /**
     * Recupera il navigatore del modulo dati.
     * <p/>
     *
     * @return il navigatore
     */
    private Navigatore getNavDati() {
        return this.getModuloDati().getNavigatoreDefault();
    }


    private StatoGiornalieroModulo getModuloDati() {
        return moduloDati;
    }


    private void setModuloDati(StatoGiornalieroModulo moduloDati) {
        this.moduloDati = moduloDati;
    }


    private TabTotali getTabTotMovi() {
        return tabTotMovi;
    }


    private void setTabTotMovi(TabTotali tabTotMovi) {
        this.tabTotMovi = tabTotMovi;
    }


    private TabTotali getTabTotOggi() {
        return tabTotOggi;
    }


    private void setTabTotOggi(TabTotali tabTotOggi) {
        this.tabTotOggi = tabTotOggi;
    }


    private TabTotali getTabTotPasti() {
        return tabTotPasti;
    }


    private void setTabTotPasti(TabTotali tabTotPasti) {
        this.tabTotPasti = tabTotPasti;
    }


    private JButton getBotEsegui() {
        return botEsegui;
    }


    private void setBotEsegui(JButton botEsegui) {
        this.botEsegui = botEsegui;
    }


    private JLabel getLabelGiorno() {
        return labelGiorno;
    }


    private void setLabelGiorno(JLabel labelGiorno) {
        this.labelGiorno = labelGiorno;
    }


    private Pannello getPanTotali() {
        return panTotali;
    }


    private void setPanTotali(Pannello panTotali) {
        this.panTotali = panTotali;
    }


    private Date getDataCorrente() {
        return dataCorrente;
    }


    private void setDataCorrente(Date dataCorrente) {
        this.dataCorrente = dataCorrente;
    }


    /**
     * Classe 'interna'.</p>
     */
    private final class RendererCelle extends DefaultTableCellRenderer {

        int alignment;


        /**
         * Costruttore base senza parametri. <br>
         * Indispensabile anche se non viene utilizzato
         * (anche solo per compilazione in sviluppo) <br>
         * Rimanda al costruttore completo <br>
         * Utilizza eventuali valori di default <br>
         *
         * @param align allineamento da SwingConstants
         */
        public RendererCelle(int align) {
            this.alignment = align;
        }// fine del metodo costruttore base


        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {
            JLabel renderedLabel = (JLabel)super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
            renderedLabel.setHorizontalAlignment(alignment);
            return renderedLabel;
        }
    } // fine della classe 'interna'


}// fine della classe