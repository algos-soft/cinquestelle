package it.algos.albergo.prenotazione.periodo;
/**
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      24-ott-2007
 */

import it.algos.albergo.AlbergoLib;
import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraLib;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.camera.composizione.CompoCameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.listino.Listino;
import it.algos.albergo.pensioni.DialogoAddebiti;
import it.algos.albergo.pensioni.PanAddebiti;
import it.algos.albergo.pensioni.WrapAddebiti;
import it.algos.albergo.pensioni.WrapAddebitiConto;
import it.algos.albergo.pensioni.WrapAddebitiPeriodo;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.prenotazione.PrenotazionePref;
import it.algos.albergo.prenotazione.periodo.periodoaddebito.AddebitoPeriodo;
import it.algos.albergo.prenotazione.periodo.periodoaddebito.AddebitoPeriodoModulo;
import it.algos.albergo.prenotazione.periodo.periodorisorsa.PeriodoRisorsaLogica;
import it.algos.albergo.prenotazione.periodo.periodorisorsa.RisorsaPeriodo;
import it.algos.albergo.prenotazione.periodo.periodorisorsa.RisorsaPeriodoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.dati.CDElencoInterno;
import it.algos.base.campo.dati.CampoDati;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.listasingola.ListaSingola;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.Filtro.Op;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.modifica.QueryModifica;
import it.algos.base.query.modifica.QueryUpdate;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.scheda.Scheda;
import it.algos.base.scheda.SchedaDefault;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.TestoAlgos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Scheda specifica di un Periodo
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 24-ott-2007 ore 16.42.46
 */
public final class PeriodoScheda extends SchedaDefault implements Periodo {

    /* pannello visualizzatore del tipo di entrata */

    private PanInOut panIn;

    /* pannello visualizzatore del tipo di uscita */

    private PanInOut panOut;

    /* bottone per eseguire l'editing degli addebiti */

    private JButton botAddebiti;

    /**
     * riferimento al pannello di editing addebiti
     */
    private PanAddebiti panAddebiti;
    
    // memorizza la data corrente all'entrata nel campo data arrivo
	private Date dataArrivoAllEntrata;
    // memorizza la data corrente all'entrata nel campo data partenza
	private Date dataPartenzaAllEntrata;


    /**
     * Costruttore completo senza parametri.
     * <p/>
     *
     * @param modulo di riferimento
     */
    public PeriodoScheda(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

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

            JButton botAddebiti = new JButton("Modifica addebiti...");
            botAddebiti.addActionListener(new AzModAddebiti());
            this.setBotAddebiti(botAddebiti);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia

    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Recupera il db <br>
     * Chiede al db i dati del record <br>
     * Regola i dati della scheda <br>
     */
    @Override
    public void avvia(int codice) {
        try { // prova ad eseguire il codice
            super.avvia(codice);
            this.regolaPopupPreparazione();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

    /**
     * Crea le pagine.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override
    protected void creaPagine() {
        /* variabili e costanti locali di lavoro */
        Pagina pagina;
        Pannello pan;
        Pannello pan1;
        Pannello pan2;
        PanInOut panIO;

        try { // prova ad eseguire il codice

            /* crea i pannellini visualizzatori tipo entrata e uscita */
            panIO = new PanInOut(this, true);
            this.setPanIn(panIO);
            panIO = new PanInOut(this, false);
            this.setPanOut(panIO);

            /* aggiunge  una pagina al libro con il set di default */
            pagina = this.addPagina("generale");

            /* prima riga */
            pan1 = PannelloFactory.orizzontale(this);
            pan1.setAllineamento(Layout.ALLINEA_BASSO);
            pan1.add(this.getPanIn());
//            pan1.add(Periodo.Cam.causaleArrivo);
            pan1.add(Periodo.Cam.arrivoPrevisto);
            pan1.add(Periodo.Cam.partenzaPrevista);
            pan1.add(Periodo.Cam.camera);
            pan1.add(Periodo.Cam.trattamento);
            pan1.add(Periodo.Cam.pasto);
            pan1.add(this.getPanOut());

            /* seconda riga */
            pan2 = PannelloFactory.orizzontale(this);
            pan2.add(Periodo.Cam.preparazione);
            pan2.add(Periodo.Cam.noteprep);
            pan2.add(Periodo.Cam.adulti);
            pan2.add(Periodo.Cam.bambini);
            pan2.add(Periodo.Cam.persone);
            pan2.add(Periodo.Cam.giorni);
            pan2.add(Periodo.Cam.presenze);
            pan2.add(Periodo.Cam.note);

            /* terza riga */
            Pannello panArrivo = PannelloFactory.verticale(this);
            panArrivo.setGapFisso(0);
            panArrivo.add(Periodo.Cam.arrivoCon);
            panArrivo.add(Periodo.Cam.arrivoConfermato);

            Pannello panPartenza = PannelloFactory.verticale(this);
            panPartenza.setGapFisso(0);
            panPartenza.add(Periodo.Cam.partenzaCon);
            panPartenza.add(Periodo.Cam.partenzaConfermata);

            Pannello panArrivato = PannelloFactory.verticale(this);
            panArrivato.setGapFisso(0);
            panArrivato.add(Periodo.Cam.arrivato);
            panArrivato.add(Periodo.Cam.arrivoEffettivo);

            Pannello panPartito = PannelloFactory.verticale(this);
            panPartito.setGapFisso(0);
            panPartito.add(Periodo.Cam.partito);
            panPartito.add(Periodo.Cam.partenzaEffettiva);

            Pannello pan3 = PannelloFactory.orizzontale(this);
            pan3.add(panArrivo);
            pan3.add(panPartenza);
            pan3.add(Box.createHorizontalGlue());
            pan3.add(panArrivato);
            pan3.add(panPartito);

            /* pannello con le 3 righe */
            pan = PannelloFactory.verticale(this);
            pan.add(pan1);
            pan.add(pan2);
            pan.add(pan3);

            pagina.add(pan);

            pan = PannelloFactory.verticale(this);
            pan.setGapFisso(2);
            pan.add(this.getBotAddebiti());
            pan.add(Periodo.Cam.addebiti);
            pagina.add(pan);
            
            pan = PannelloFactory.verticale(this);
            pan.setGapFisso(2);
            pan.add(new JLabel("Risorse"));
            pan.add(Periodo.Cam.risorse);
            pagina.add(pan);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Sovrascritto dalle sottoclassi
     */
    @Override
    protected void eventoMemoriaModificata(Campo campo) {
        /* variabili e costanti locali di lavoro */
        Campo campoNav;
        Navigatore nav;

        try { // prova ad eseguire il codice

            /**
             * se modifico la camera assegna la composizione (se diversa),
             * senza chiedere conferma
             */
            if (this.isCampo(campo, Cam.camera)) {
                int codCamera = this.getInt(Cam.camera.get());
                int compoPrevista = CameraModulo.getComposizioneStandard(codCamera);
                int compoEsistente = this.getInt(Cam.preparazione.get());
                if (compoPrevista != compoEsistente) {
                    this.setValore(Cam.preparazione.get(), compoPrevista);
                }// fine del blocco if
            }// fine del blocco if

            /**
             * se modifico la composizione assegna le persone
             * se sono già presenti e diverse, chiede conferma
             */
            if (this.isCampo(campo, Cam.preparazione)) {
                this.composizioneModificata();
            }// fine del blocco if

            /**
             * se modifico la camera, regola il popup delle preparazioni possibili
             */
            if (this.isCampo(campo, Cam.camera)) {
                this.regolaPopupPreparazione();
            }// fine del blocco if

            /* se modifico camera, arrivo, partenza o persone sincronizza il nav addebiti */
            if (this.isCampo(
                    campo,
                    Cam.camera,
                    Cam.arrivoPrevisto,
                    Cam.partenzaPrevista,
                    Cam.persone)) {
                campoNav = this.getCampo(Cam.addebiti);
                nav = campoNav.getNavigatore();
                nav.sincronizza();
            }// fine del blocco if

            /**
             * se modifico il trattamento, e il cliente non è ancora arrivato,
             * regola arrivo con e partenza con
             */
            if (this.isCampo(campo, Cam.trattamento)) {
                if (!this.getBool(Cam.arrivato.get())) {
                    this.regolaArrivoPartenza();
                }// fine del blocco if
            }// fine del blocco if

            /**
             * se modifico l'arrivo con, la metto automaticamente
             * come confermato (se non è nuovo record)
             */
            if (this.isCampo(campo, Cam.arrivoCon)) {
                if (!this.isNuovoRecord()) {
                    this.setValore(Cam.arrivoConfermato.get(), true);
                }// fine del blocco if
            }// fine del blocco if

            /**
             * se modifico la partenza con, la metto automaticamente
             * come confermata (se non è nuovo record)
             */
            if (this.isCampo(campo, Cam.partenzaCon)) {
                if (!this.isNuovoRecord()) {
                    this.setValore(Cam.partenzaConfermata.get(), true);
                }// fine del blocco if
            }// fine del blocco if


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
    @Override
    protected void eventoUscitaCampoModificato(Campo campo) {
        /* variabili e costanti locali di lavoro */
        Date dataIni;
        Date dataEnd;
        int delta;
        int codPeriodo;
        String messaggio;
        Date data;

        try { // prova ad eseguire il codice

            /* se modifico la data arrivo regola la data partenza da default (se non è già presente) */
            if (this.isCampo(campo, Cam.arrivoPrevisto)) {
                dataEnd = this.getData(Cam.partenzaPrevista.get());
                if (Lib.Data.isVuota(dataEnd)) {
                    delta = PrenotazionePref.Prenotazione.soggiorno.intero();
                    dataIni = Libreria.getDate(campo.getValore());
                    dataEnd = Lib.Data.add(dataIni, delta);
                    this.setValore(Cam.partenzaPrevista.get(), dataEnd);
                }// fine del blocco if
            }// fine del blocco if

            /**
             * Se modifico data inizio, data fine o camera controlla la disponibilità.
             * se non disponibile visualizza un messaggio ma non ripristina il valore del campo.
             * Il valore non viene ripristinato perché il dato potrebbe non essere definitivo
             * (la occupazione è data da arrivo-partenza-camera e l'utente non ha finito di inserirli)
             * perciò devo lasciare la possibilità all'utente di completare le modifiche.
             * La disponibilità viene comunque controllata ad uscita scheda, dove è
             * obbligatorio che la camera sia disponibile per poter confermare.
             */
            if (this.isCampo(campo, Cam.arrivoPrevisto, Cam.partenzaPrevista, Cam.camera)) {
                if (!this.isPeriodoValido()) {
                    codPeriodo = this.getCodPeriodoSovrapposto();
                    messaggio = PeriodoLogica.getMessaggioOccupato(codPeriodo);
                    new MessaggioAvviso("Periodo non disponibile\n" + messaggio);
                    campo.setValore(campo.getCampoDati().getBackup());
                }// fine del blocco if
            }// fine del blocco if


            /* se modifico data di arrivo, la data di partenza o il numero di persone
             * ed esistono già degli addebiti previsti, avvisa, chiede conferma e li modifica */
            if (this.isCampo(
                    campo,
                    Cam.arrivoPrevisto,
                    Cam.partenzaPrevista,
                    Cam.adulti,
                    Cam.bambini)) {

                if (this.isEsistonoAddebiti()) {

                    String testo = "";

                    if ((this.isCampo(campo, Cam.arrivoPrevisto)) || (this.isCampo(
                            campo, Cam.partenzaPrevista))) {
                        testo = "Il periodo di soggiorno";
                    }// fine del blocco if

                    if ((this.isCampo(campo, Cam.adulti)) || (this.isCampo(campo, Cam.bambini))) {
                        testo = "Il numero di persone";
                    }// fine del blocco if

                    MessaggioDialogo dialogo = new MessaggioDialogo(
                            "Attenzione! "
                                    + testo
                                    + " è stato modificato.\nModifico anche gli addebiti?");
                    if (dialogo.isConfermato()) {
                        this.syncAddebiti();
                    }// fine del blocco if
                }// fine del blocco if

//                this.checkAddebiti("pippo");
            }// fine del blocco if

            /* se modifico la data di arrivo, invoca il controllo cambi in arrivo */
            if (this.isCampo(campo, Cam.arrivoPrevisto)) {
                data = this.getData(Cam.arrivoPrevisto.get());
                this.checkCambi(true, data);
            }// fine del blocco if

            /* se modifico la data di partenza, invoca il controllo cambi in partenza */
            if (this.isCampo(campo, Cam.partenzaPrevista)) {
                data = this.getData(Cam.partenzaPrevista.get());
                this.checkCambi(false, data);
            }// fine del blocco if

            /**
             * se modifico le note preparazione chiede se memorizzarle
             * nel cliente per le prenotazioni successive
             */
            if (this.isCampo(campo, Cam.noteprep)) {
                this.notePrepModificate();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

    /**
     * Invocato quando viene modificata la Composizione.
     * <p/>
     * Assegna il numero di persone in base alla composizione scelta.
     */
    private void composizioneModificata() {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice

//            /* verifica se si tratta di cambio */
//            /* (in caso di cambio le persone sono regolate pari al periodo agganciato) */
//            boolean cambio = false;
//            int codCausaleIn = this.getInt(Cam.causaleArrivo.get());
//            int codCausaleOut = this.getInt(Cam.causalePartenza.get());
//            int codCambio = CausaleAP.cambio.getCodice();
//            if ((codCausaleIn == codCambio) || (codCausaleOut == codCambio)) {
//                cambio = true;
//            }// fine del blocco if

            /**
             * - se le persone non sono state inserite le inserisce,
             * - se sono già state inserite, e sono diverse da quanto previsto
             * per la nuova composizione, chiede conferma
             */
            if (true) {

                MessaggioDialogo dialogo;
                boolean modifica = false;

                /* acquisisce i dati */
                int codCompo = this.getInt(Cam.preparazione.get());
                int numAdultiPrevisti = CompoCameraModulo.getNumLettiAdulti(codCompo);
                int numBambiniPrevisti = CompoCameraModulo.getNumLettiBambini(codCompo);
                int numAdultiEsistenti = this.getInt(Cam.adulti.get());
                int numBambiniEsistenti = this.getInt(Cam.bambini.get());

                if ((numAdultiEsistenti == 0) && (numBambiniEsistenti == 0)) {
                    modifica = true;
                } else {
                    if ((numAdultiEsistenti != numAdultiPrevisti) || (numBambiniEsistenti
                            != numBambiniPrevisti)) {

                        String newPers;
                        if (numBambiniPrevisti > 0) {
                            newPers = "" + numAdultiPrevisti + "+" + numBambiniPrevisti;
                        } else {
                            newPers = "" + numAdultiPrevisti;
                        }// fine del blocco if-else

                        String testo = "Attenzione! La composizione è stata modificata.\n";
                        testo += "Modifico anche le persone (" + newPers + ") ?";
                        dialogo = new MessaggioDialogo(testo);
                        modifica = dialogo.isConfermato();
                    }// fine del blocco if
                }// fine del blocco if-else

                /* esegue la modifica */
                if (modifica) {

                    this.setValore(Cam.adulti.get(), numAdultiPrevisti);
                    this.setValore(Cam.bambini.get(), numBambiniPrevisti);

                    /**
                     * se non erano entrambi a zero, e se ci sono addebiti,
                     * propone la modifica degli addebiti
                     */
                    Campo campo = this.getCampo(Periodo.Cam.addebiti);
                    Navigatore nav = campo.getNavigatore();
                    int quantiAddebiti = nav.getLista().getNumRecordsVisualizzati();

                    if (quantiAddebiti > 0) {
                        if ((numAdultiEsistenti != 0) || (numBambiniEsistenti != 0)) {
                            dialogo = new MessaggioDialogo(
                                    "Attenzione! Il numero di persone è stato modificato.\nModifico anche gli addebiti?");
                            if (dialogo.isConfermato()) {
                                this.syncAddebiti();
                            }// fine del blocco if
                        }// fine del blocco if
                    }// fine del blocco if

                }// fine del blocco if


            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }

    /**
     * Regola il popup delle preparazioni per la camera.
     * <p/>
     * Metodo chiamato da avvio ed ogni volta che cambia il valore del campo camera <br>
     */
    private void regolaPopupPreparazione() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Campo campoPreparaz;
        int cod = 0;

        try { // prova ad eseguire il codice

            campoPreparaz = this.getCampo(Cam.preparazione);
            continua = (campoPreparaz != null);

            /* se la camera non è selezionata, il popup delle preparazioni è disabilitato */
            /* altrimenti il popup riporta solo i valori possibili per quella camera */
            if (continua) {
                cod = this.getInt(Cam.camera.get());
                if (cod > 0) {
                    campoPreparaz.setModificabile(true);
                    CameraLib.ricaricaCompo(cod, campoPreparaz);
                } else {
                    campoPreparaz.setModificabile(false);
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

    /**
     * Regola i campi Arrivo Con e Partenza Con in base al trattamento.
     * <p/>
     */
    private void regolaArrivoPartenza() {
        /* variabili e costanti locali di lavoro */
        int codTrat;
        Listino.PensioniPeriodo trat;
        TipiAP tipo;
        Campo campo;
        CampoDati cd;
        CDElencoInterno cdi;

        try {    // prova ad eseguire il codice

            codTrat = this.getInt(Periodo.Cam.trattamento.get());
            trat = Listino.PensioniPeriodo.get(codTrat);
            if (trat != null) {

                /* arrivo */
                tipo = TipiAP.getTipoArrivoDefault(trat);
                campo = this.getCampo(Periodo.Cam.arrivoCon);
                cd = campo.getCampoDati();
                if (cd instanceof CDElencoInterno) {
                    cdi = (CDElencoInterno)cd;
                    cdi.setValoreDaElencoNew(tipo);
                }// fine del blocco if

                /* partenza */
                tipo = TipiAP.getTipoPartenzaDefault(trat);
                campo = this.getCampo(Periodo.Cam.partenzaCon);
                cd = campo.getCampoDati();
                if (cd instanceof CDElencoInterno) {
                    cdi = (CDElencoInterno)cd;
                    cdi.setValoreDaElencoNew(tipo);
                }// fine del blocco if

            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }

    /**
     * Ho modificato le note di preparazione.
     * <p/>
     * Confronta con quelle registrate nel cliente ed eventualmente
     * chiede se si vogliono memorizzare nel cliente.
     */
    private void notePrepModificate() {
        /* variabili e costanti locali di lavoro */
        String noteCli;
        String notePeri;
        MessaggioDialogo d;
        String s;

        try {    // prova ad eseguire il codice

            /* recupera le note del cliente e le note del periodo */
            int codCli = this.getCodCliente();
            noteCli = ClienteAlbergoModulo.get()
                    .query()
                    .valoreStringa(ClienteAlbergo.Cam.noteprep.get(), codCli);
            notePeri = this.getString(Periodo.Cam.noteprep.get());

            if (Lib.Testo.isValida(notePeri)) { // ha inserito una stringa valida

                if (Lib.Testo.isValida(noteCli)) {   // esiste già una nota valida sul cliente

                    if (!notePeri.equals(noteCli)) {      // se diverso, chiede
                        s
                                =
                                "Vuoi memorizzare la nota sul cliente per le future prenotazioni?\n(attualmente la nota cliente è \""
                                        + noteCli
                                        + "\")";
                        d = new MessaggioDialogo(s);
                        if (d.isConfermato()) {
                            this.setNotePrepCli(notePeri);
                        }// fine del blocco if
                    }// fine del blocco if

                } else {          // il cliente non ha note

                    s = "Vuoi memorizzare la nota sul cliente per le future prenotazioni?";
                    d = new MessaggioDialogo(s);
                    if (d.isConfermato()) {
                        this.setNotePrepCli(notePeri);
                    }// fine del blocco if

                }// fine del blocco if-else

            } else {     // ha cancellato il valore esistente

                if (Lib.Testo.isValida(noteCli)) {   // il cliente ha una nota valida

                    s
                            = "Vuoi eliminare la nota anche sul cliente?\n(non verrà più riproposta sulle future prenotazioni)";
                    d = new MessaggioDialogo(s);
                    if (d.isConfermato()) {
                        this.setNotePrepCli("");
                    }// fine del blocco if

                }// fine del blocco if

            }// fine del blocco if-else


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }

    /**
     * Assegna un valore alle Note Preparazione del cliente.
     * <p/>
     *
     * @param testo da assegnare alle Note Preparazione
     */
    private void setNotePrepCli(String testo) {
        try {    // prova ad eseguire il codice
            int codCli = this.getCodCliente();
            if (codCli > 0) {
                ClienteAlbergoModulo.get()
                        .query()
                        .registra(codCli, ClienteAlbergo.Cam.noteprep.get(), testo);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }

    /**
     * Ritorna il codice prenotazione relativo al periodo corrente.
     * <p/>
     *
     * @return il codice prenotazione
     */
    private int getCodPrenotazione() {
        /* variabili e costanti locali di lavoro */
        int codPren = 0;

        try {    // prova ad eseguire il codice
            int codPeri = this.getCodice();
            if (codPeri > 0) {
                codPren = PeriodoModulo.get()
                        .query()
                        .valoreInt(Periodo.Cam.prenotazione.get(), this.getCodice());
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codPren;
    }

    /**
     * Ritorna il codice cliente relativo al periodo corrente.
     * <p/>
     *
     * @return il codice cliente
     */
    private int getCodCliente() {
        /* variabili e costanti locali di lavoro */
        int codCli = 0;

        try {    // prova ad eseguire il codice
            int codPren = this.getCodPrenotazione();
            if (codPren > 0) {
                codCli = PrenotazioneModulo.get()
                        .query()
                        .valoreInt(Prenotazione.Cam.cliente.get(), codPren);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codCli;
    }

    /**
     * Intercetta la registrazione del record.
     * <p/>
     * Se il n. persone è zero, prima di registrare chiede conferma.
     *
     * @return true se ha registrato.
     */
    protected boolean registraRecord() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;

        try { // prova ad eseguire il codice

            /**
             * se non ci sono persone avvisa ma consente di registrare
             */
            if (this.getInt(Cam.persone.get()) == 0) {
                String testo
                        = "Attenzione! Il numero di persone è zero.\nVuoi registrare comunque?";
                MessaggioDialogo mess = new MessaggioDialogo(testo);
                continua = mess.isConfermato();
            }// fine del blocco if
            
            // Sincronizza gli eventuali conti aperti con la data di partenza del periodo
            if (continua) {
            	int[] contiModificati = syncConti();
            	if (contiModificati.length>0) {
                	if (contiModificati.length==1) {
                        String testo = "Hai variato la partenza e il conto aperto è stato automaticamente modificato.\nVuoi esaminarlo?";
                        MessaggioDialogo mess = new MessaggioDialogo(testo);
                        if (mess.isConfermato()) {
    						ContoModulo.get().presentaRecord(contiModificati[0], getConnessione());
    					}
    				}
    				else {
                        String testo = "Hai variato la partenza e "+contiModificati.length+" conti aperti sono stati automaticamente modificati.";
                		new MessaggioAvviso(testo);
    				}
				}
			}

            if (continua) {
                continua = super.registraRecord();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }
    
    /**
     * Sincronizza gli eventuali conti aperti con la data di partenza del periodo
     * - cambia la data di fine validità dei conti
     * - aggiorna la tabella degli addebiti continuativi
     * @return i codici dei conti modificati
     */
    private int[] syncConti(){
    	
    	ArrayList<Integer> contiModificati=new ArrayList<Integer>();
    	boolean cont = true;
    	
    	// calcola la data di fine validità per i conti
    	Date dataFineConto = Lib.Data.add(getData(Periodo.Cam.partenzaPrevista.getNome()), -1);

    	
    	// filtro conti aperti relativi al periodo
    	// con data fine validità diversa da quella del periodo
    	Filtro filtroConti = new Filtro();
    	filtroConti.add(FiltroFactory.crea(Conto.Cam.periodo.getNome(), getCodice()));
    	filtroConti.add(FiltroFactory.crea(Conto.Cam.chiuso.getNome(), false));
    	filtroConti.add(FiltroFactory.crea(Conto.Cam.validoAl.getNome(), Op.DIVERSO, dataFineConto));

    	// recupera i codici dei conti da sincronizzare
    	Modulo modConto = ContoModulo.get();
    	int[] codiciConti = modConto.query().valoriChiave(filtroConti, getConnessione());
    	if(codiciConti.length<=0){
        	cont = false;
    	}
    	
    	// tenta di sincronizzare automaticamente tutti i conti trovati
    	if (cont) {
        	for(int codConto : codiciConti){
        		
            	// cambia la data di fine validità del conto
        		modConto.query().registra(codConto, Conto.Cam.validoAl.getNome(), dataFineConto, getConnessione());
        		
            	// modifica l'elenco degli addebiti continuativi
            	if (cont) {
            		Date dataIni = modConto.query().valoreData(Conto.Cam.validoDal.get(), codConto, getConnessione());
            		int persone = modConto.query().valoreInt(Conto.Cam.numPersone.get(), codConto, getConnessione());
            		WrapAddebitiConto wrapAddebiti = new WrapAddebitiConto(codConto, getConnessione());
                    boolean riuscito = wrapAddebiti.modifica(dataIni, dataFineConto, persone, getConnessione());
                    if (riuscito) {
                    	contiModificati.add(codConto);
                    }else{
                    	cont=false;
                    	break;
        			}
        		}

        	}
        	
        	// se la procedura è fallita avvisa di controllare manuamlente
        	if (!cont) {
        		String testo = "Impossibile aggiornare i conti relativi al periodo. Controlla i conti manualmente.";
        		new MessaggioAvviso(testo);
    		}

        	
		}
    	
    	
    	// converti a int[]
        int[] ret = new int[contiModificati.size()];
        for (int i=0; i < ret.length; i++)
        {ret[i] = contiModificati.get(i).intValue();}
        return ret;
    }

    /**
     * Controlla se per il periodo corrente esistono già degli
     * addebiti previsti.
     * <p/>
     *
     * @return true se esistono
     */
    private boolean isEsistonoAddebiti() {
        /* variabili e costanti locali di lavoro */
        boolean esistono = false;
        Modulo modulo;
        Filtro filtro;
        int quanti;

        try {    // prova ad eseguire il codice
            modulo = AddebitoPeriodoModulo.get();
            if (modulo != null) {
                filtro = FiltroFactory.crea(AddebitoPeriodo.Cam.periodo.get(), this.getCodice());
                quanti = modulo.query().contaRecords(filtro, this.getConnessione());
                esistono = (quanti > 0);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esistono;
    }

    /**
     * Controllo cambi.
     * <p/>
     * Invocato quando si modifica la data di arrivo
     *
     * @param arrivo true se ho modificato la data di arrivo false quella di partenza
     * @param data la data di controllo
     */
    private void checkCambi(boolean arrivo, Date data) {
        /* variabili e costanti locali di lavoro */
        int[] periodiAgganciabili;
        int quanti;
        SelPeriodoLink selettore;
        int codPeriodoQuesto;
        int codPeriodoAltro;
        Modulo modPeriodo;
        Modulo modCamera;
        int codCamera;
        String camera;
        String testo;
        MessaggioDialogo messaggio;
        String parola;

        try {    // prova ad eseguire il codice

            /* recupera la lista dei periodi agganciabili */
            periodiAgganciabili = this.getPeriodiAgganciabili(arrivo, data);
            quanti = periodiAgganciabili.length;
            if (quanti > 0) {

                modPeriodo = PeriodoModulo.get();

                if (quanti == 1) {   // un solo periodo agganciabile

                    codPeriodoAltro = periodiAgganciabili[0];
                    codCamera = modPeriodo.query().valoreInt(Cam.camera.get(), codPeriodoAltro);
                    modCamera = CameraModulo.get();
                    camera = modCamera.query().valoreStringa(Camera.Cam.camera.get(), codCamera);
                    if (arrivo) {
                        parola = "dalla";
                    } else {
                        parola = "alla";
                    }// fine del blocco if-else
                    testo = "Possibile cambio ";
                    testo += parola + " " + camera + ".";
                    testo += "\nVuoi effettuare il cambio?";
                    messaggio = new MessaggioDialogo(testo);
                    if (messaggio.isConfermato()) {
                        codPeriodoQuesto = this.getCodice();
                        this.agganciaPeriodi(codPeriodoQuesto, codPeriodoAltro, arrivo);
                    }// fine del blocco if

                } else {  // molti periodi agganciabili

                    selettore = new SelPeriodoLink(periodiAgganciabili, arrivo);
                    selettore.avvia();
                    if (selettore.isConfermato()) {
                        codPeriodoQuesto = this.getCodice();
                        codPeriodoAltro = selettore.getCodPeriodoSelezionato();
                        this.agganciaPeriodi(codPeriodoQuesto, codPeriodoAltro, arrivo);
                    }// fine del blocco if

                }// fine del blocco if-else

            }// fine del blocco if-else


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }

    /**
     * Recupera i codici dei periodi agganciabili in arrivo o partenza
     * <p/>
     *
     * @param arrivo true recupera i periodi agganciabili in arrivo
     * false i periodi agganciabili in partenza
     * @param data data di controllo
     *
     * @return la lista dei codici dei periodi agganciabili
     */
    private int[] getPeriodiAgganciabili(boolean arrivo, Date data) {
        /* variabili e costanti locali di lavoro */
        int[] periodi = new int[0];
        Filtro filtroTot;
        Filtro filtro;
        Modulo modPeri;
        int codPrenotazione;
        int codQuestoPeriodo;
        Cam camDataCheck;
        Cam camCausaleCheck;

        try {    // prova ad eseguire il codice

            modPeri = PeriodoModulo.get();
            codQuestoPeriodo = this.getCodice();
            codPrenotazione = modPeri.query().valoreInt(Cam.prenotazione.get(), codQuestoPeriodo);

            if (arrivo) {   // cerca cambi agganciabili in arrivo
                camCausaleCheck = Cam.causalePartenza;
                camDataCheck = Cam.partenzaPrevista;
            } else {   // cerca cambi agganciabili in partenza
                camCausaleCheck = Cam.causaleArrivo;
                camDataCheck = Cam.arrivoPrevisto;
            }// fine del blocco if-else

            filtroTot = new Filtro();

            // solo i periodi di questa prenotazione
            filtro = FiltroFactory.crea(Cam.prenotazione.get(), codPrenotazione);
            filtroTot.add(filtro);

            // solo i periodi che non terminano/iniziano con un cambio
            filtro = FiltroFactory.crea(
                    camCausaleCheck.get(),
                    Filtro.Op.DIVERSO,
                    CausaleAP.cambio.getCodice());
            filtroTot.add(filtro);

            // solo i periodi che terminano/iniziano nella data di controllo specificata
            filtro = FiltroFactory.crea(camDataCheck.get(), data);
            filtroTot.add(filtro);

            /* non il periodo che sto editando adesso in scheda */
            filtro = FiltroFactory.crea(
                    modPeri.getCampoChiave(), Filtro.Op.DIVERSO, this.getCodice());
            filtroTot.add(filtro);

            /* esegue la query e recupera il risultato */
            periodi = modPeri.query().valoriChiave(filtroTot);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return periodi;
    }

    /**
     * Aggancia due periodi con cambio.
     * <p/>
     * Aggancia la fine del primo periodo all'inizio del secondo periodo.
     * <p/>
     * Utilizza una transazione
     * Se fallito mostra un messaggio con la motivazione
     *
     * @param periodoInt codice del periodo attualmente modificato in scheda
     * @param periodoEst codice del periodo esterno
     * @param arrivo true aggancia la fine del periodo esterno all'inizio del periodo interno
     * false aggancia l'inizio del periodo esterno alla fine del periodo interno
     */
    private void agganciaPeriodi(int periodoInt, int periodoEst, boolean arrivo) {
        /* variabili e costanti locali di lavoro */
        Connessione conn;
        String testo;
        String messaggio;
        boolean riuscito = false;
        Modulo modPeriodo;
        int periodo1;
        int periodo2;
        Campi campoData;
        Campi campoCausale;
        Date data;

        try {    // prova ad eseguire il codice

            modPeriodo = PeriodoModulo.get();

            if (arrivo) {
                periodo1 = periodoEst;
                periodo2 = periodoInt;
                campoData = Cam.arrivoPrevisto;
                campoCausale = Cam.causaleArrivo;
            } else {
                periodo1 = periodoInt;
                periodo2 = periodoEst;
                campoData = Cam.partenzaPrevista;
                campoCausale = Cam.causalePartenza;
            }// fine del blocco if-else

            /* recupera la data dal campo */
            data = this.getData(campoData.get());

            /* registra la data modificata sul db prima dell'aggancio periodi */
            modPeriodo.query().registra(periodoInt, campoData.get(), data);

            /* esegue l'aggancio sotto transazione */
            conn = this.getConnessione();
            conn.startTransaction();
            testo = AlbergoLib.agganciaPeriodi(periodo1, periodo2, conn);
            riuscito = Lib.Testo.isVuota(testo);
            if (riuscito) {
                conn.commit();
            } else {
                conn.rollback();
            }// fine del blocco if-else

            /**
             * aggiornamento scheda se riuscito,
             * messaggio con motivazione se fallito
             */
            if (riuscito) {

                /* aggiorna il valore del campo causale arrivo/partenza*/
                this.setValore(campoCausale.get(), CausaleAP.cambio.getCodice());

                /**
                 * aggiorna alcuni dati del periodo attualmente modificato in scheda
                 * copiandoli dal perdiodo esterno
                 */
                Query q = new QuerySelezione(modPeriodo);
//                q.addCampo(Cam.adulti);
//                q.addCampo(Cam.bambini);
                q.addCampo(Cam.trattamento);
                q.setFiltro(FiltroFactory.codice(modPeriodo, periodoEst));
                Dati d = modPeriodo.query().querySelezione(q);
//                this.setValore(Cam.adulti.get(), d.getIntAt(Cam.adulti.get()));
//                this.setValore(Cam.bambini.get(), d.getIntAt(Cam.bambini.get()));
                this.setValore(Cam.trattamento.get(), d.getIntAt(Cam.trattamento.get()));
                d.close();

                /* sincronizza la scheda */
                this.sincronizza();

                /* aggiorna la lista sottostante */
                this.getNavigatore().aggiornaLista();

            } else {
                messaggio = "Impossibile effettuare il cambio.\n";
                messaggio += testo;
                new MessaggioAvviso(messaggio);
            }// fine del blocco if-else


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch


    }

    /**
     * Rimuove il cambio in entrata o in uscita.
     * <p/>
     *
     * @param entrata true per entrata false per uscita
     */
    private void eliminaCambio(boolean entrata) {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        int periodo1;
        int periodo2;
        Connessione conn;
        String testo;
        boolean riuscito;
        String messaggio;
        Campi campoCausale;

        try {    // prova ad eseguire il codice

            /* recupera i codici dei due periodi */
            if (continua) {
                if (entrata) {
                    periodo1 = this.getInt(Cam.linkProvenienza.get());
                    periodo2 = this.getCodice();
                    campoCausale = Cam.causaleArrivo;
                } else {
                    periodo1 = this.getCodice();
                    periodo2 = this.getInt(Cam.linkDestinazione.get());
                    campoCausale = Cam.causalePartenza;
                }// fine del blocco if-else

                /* esegue lo sgancio sotto transazione */
                conn = this.getConnessione();
                conn.startTransaction();
                testo = AlbergoLib.sganciaPeriodi(periodo1, periodo2, conn);
                riuscito = Lib.Testo.isVuota(testo);
                if (riuscito) {
                    conn.commit();
                } else {
                    conn.rollback();
                }// fine del blocco if-else

                /**
                 * aggiornamento scheda se riuscito,
                 * messaggio con motivazione se fallito
                 */
                if (riuscito) {

                    /* aggiorna il valore del campo causale */
                    this.setValore(campoCausale.get(), CausaleAP.normale.ordinal() + 1);

                    /* sincronizza la scheda */
                    this.sincronizza();

                    /* aggiorna la lista sottostante */
                    this.getNavigatore().aggiornaLista();

                } else {
                    messaggio = "Impossibile eliminare il cambio.\n";
                    messaggio += testo;
                    new MessaggioAvviso(messaggio);
                }// fine del blocco if-else
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }

//    /**
//     * Controlla la sincronizzazione addebiti.
//     * <p/>
//     * Controlla se esistono addebiti previsti
//     * Chiede conferma per la sincronizzazione
//     * Esegue la sincronizzazione
//     * @param messaggio da visualizzare per chiedere conferma all'utente
//     */
//    private void checkAddebiti(String messaggio) {
//        /* variabili e costanti locali di lavoro */
//        boolean continua;
//        MessaggioDialogo dialogo;
//
//        try {    // prova ad eseguire il codice
//
//            /* controlla se esistono addebiti */
//            continua = this.isEsistonoAddebiti();
//
//            /* chiede conferma */
//            if (continua) {
//                dialogo = new MessaggioDialogo(
//                        "Attenzione! Il periodo è stato modificato.\nModifico anche gli addebiti?");
//                continua = dialogo.isConfermato();
//            }
//
//            /* esegue */
//            if (continua) {
//                this.syncAddebiti();
//            }// fine del blocco if
//
//            /* esegue */
//        } catch (Exception unErrore) {    // intercetta l'errore
//            new Errore(unErrore);
//        } // fine del blocco try-catch
//
//    }

    /**
     * Sincronizza gli Addebiti Previsti con date e persone.
     * <p/>
     */
    private void syncAddebiti() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int codPeriodo;
        WrapAddebiti wPens;
        Date dataIni;
        Date dataFinePeriodo;
        Date dataFineAddebiti;
        int persone;
        boolean riuscito;

        try { // prova ad eseguire il codice
            codPeriodo = this.getCodice();
            continua = (codPeriodo > 0);

            /* esegue */
            if (continua) {
                dataIni = this.getData(Periodo.Cam.arrivoPrevisto.get());
                dataFinePeriodo = this.getData(Periodo.Cam.partenzaPrevista.get());
                dataFineAddebiti = Lib.Data.add(dataFinePeriodo, -1);
                persone = this.getInt(Periodo.Cam.persone.get());

                wPens = new WrapAddebitiPeriodo(codPeriodo, this.getConnessione());
                riuscito = wPens.modifica(
                        dataIni,
                        dataFineAddebiti,
                        persone,
                        this.getConnessione());

                /* ricarica la lista */
                if (riuscito) {
                    Campo campo = this.getCampo(Periodo.Cam.addebiti);
                    Navigatore nav = campo.getNavigatore();
                    nav.aggiornaLista();
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

    /**
     * Controlla che il periodo corrente non si sovrapponga
     * (come camera e date) ad un altro periodo.
     * <p/>
     *
     * @return periodo valido
     */
    private boolean isPeriodoValido() {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;
        Date dataIni;
        Date dataEnd;
        int codCam;
        int codPeriodo;

        try { // prova ad eseguire il codice
            dataIni = Libreria.getDate(this.getValore(Cam.arrivoPrevisto.get()));
            dataEnd = Libreria.getDate(this.getValore(Cam.partenzaPrevista.get()));
            codCam = Libreria.getInt(this.getValore(Cam.camera.get()));
            codPeriodo = this.getCodice();

            valido = PeriodoScheda.isPeriodoValido(dataIni, dataEnd, codCam, codPeriodo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }

//    /**
//     * Controlla che le date di arrivo e partenza siano in sequenza corretta
//     * <p/>
//     *
//     * @return true se in sequenza
//     */
//    private boolean isDateSequenza() {
//        /* variabili e costanti locali di lavoro */
//        boolean valido = false;
//        Date dataIni;
//        Date dataEnd;
//        int codCam;
//        int codPeriodo;
//
//        try { // prova ad eseguire il codice
//            dataIni = Libreria.getDate(this.getValore(Cam.arrivoPrevisto.get()));
//            dataEnd = Libreria.getDate(this.getValore(Cam.partenzaPrevista.get()));
//            valido = Lib.Data.isPosteriore(dataIni, dataEnd);
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return valido;
//    }

    /**
     * Ritorna il codice dell'eventuale periodo che si sovrappone a quello corrente.
     * <p/>
     * Se ci fosse più di 1 periodo sovrapposto (in teoria non dovrebbe essere possibile),
     * torna il codice del primo periodo sovrapposto trovato.
     * Se non ci sono periodi sovrapposti, ritorna 0.
     *
     * @return il codice dell'eventuale primo periodo sovrapposto a quello corrente,
     *         0 se non ce ne sono
     */
    private int getCodPeriodoSovrapposto() {
        /* variabili e costanti locali di lavoro */
        int codSovrapposto = 0;
        Date dataIni;
        Date dataEnd;
        int codCam;
        int codPeriodo;

        try { // prova ad eseguire il codice
            dataIni = Libreria.getDate(this.getValore(Cam.arrivoPrevisto.get()));
            dataEnd = Libreria.getDate(this.getValore(Cam.partenzaPrevista.get()));
            codCam = Libreria.getInt(this.getValore(Cam.camera.get()));
            codPeriodo = this.getCodice();
            codSovrapposto = PeriodoLogica.getCodPeriodoSovrapposto(
                    dataIni,
                    dataEnd,
                    codCam,
                    codPeriodo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codSovrapposto;

    }

    /**
     * Controlla che un periodo non si sovrapponga (come camera e date) ad un altro periodo.
     * <p/>
     *
     * @param dataIni data di inizio periodo
     * @param dataEnd data di fine periodo
     * @param codCam codice della camera
     * @param codEscludi codice dell'eventuale periodo da escludere dalla ricerca
     *
     * @return periodo valido
     */
    private static boolean isPeriodoValido(Date dataIni, Date dataEnd, int codCam, int codEscludi) {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;
        String messaggio;
        int codSovrapposto;

        try { // prova ad eseguire il codice

            codSovrapposto = PeriodoLogica.getCodPeriodoSovrapposto(
                    dataIni,
                    dataEnd,
                    codCam,
                    codEscludi);
            if (codSovrapposto == 0) {
                valido = true;
            }// fine del blocco if

            /* se esistono periodi sovrapposti, costruisco il messaggio
             * specifico e lo assegno al controllo */
            if (!valido) {
                messaggio = PeriodoLogica.getMessaggioOccupato(codSovrapposto);
                Controllo.periodo.setMessaggio(messaggio);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }

    /**
     * Edita gli addebiti previsti in un dialogo specializzato.
     * <p/>
     */
    private void editAddebiti() {
        boolean continua;
        DialogoAddebiti dialogo;
        PanAddebiti panPens = null;
        int codPeriodo;
        WrapAddebiti wrapIn = null;
        WrapAddebitiPeriodo wrapOut;
        int quanti;
        Lista lista;
        Filtro filtro;
        Date dataIni;
        Date dataEnd;
        int persone;
        Navigatore nav;

        try { // prova ad eseguire il codice

            /* recupera il Navigatore Addebiti */
            nav = this.getNavAddebiti();

            /* recupera il codice del periodo */
            codPeriodo = this.getCodice();
            continua = (codPeriodo > 0);

            /**
             * Controlla se ci sono già delle righe di addebito relative al periodo.
             * - se ce ne sono, crea un wrapper precaricato in base agli addebiti esistenti
             * - se non ce ne sono, crea un wrapper vuoto precaricato con date e persone
             */
            if (continua) {

                dataIni = this.getData(Periodo.Cam.arrivoPrevisto.get());
                dataEnd = this.getData(Periodo.Cam.partenzaPrevista.get());
                dataEnd = Lib.Data.add(dataEnd, -1);
                persone = this.getInt(Periodo.Cam.persone.get());

                wrapIn = new WrapAddebitiPeriodo(
                        dataIni, dataEnd, persone, codPeriodo, this.getConnessione());
            }// fine del blocco if

            /**
             * crea un oggetto grafico PanPensioni regolato con il precedente wrapper
             * crea un dialogo per modificare le informazioni
             * presenta il dialogo
             */
            if (continua) {

                panPens = this.getPanAddebiti();
                panPens.inizializza(wrapIn);

                dialogo = new DialogoAddebiti(panPens);
                dialogo.avvia();
                continua = dialogo.isConfermato();

            }// fine del blocco if

            /* aggiorna gli addebiti esistenti in base a quanto impostato nel dialogo */
            if (continua) {
                wrapOut = panPens.creaWrapAddebitiPeriodo();
                wrapOut.write(codPeriodo, this.getConnessione());
                nav.aggiornaLista();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

    /**
     * Ritorna il Navigatore che gestisce gli addebiti.
     * <p/>
     *
     * @return il navigatore
     */
    private Navigatore getNavAddebiti() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;

        try {    // prova ad eseguire il codice
            Campo campo = this.getCampo(Periodo.Cam.addebiti);
            nav = campo.getNavigatore();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }

    
    
    @Override
	protected void eventoEntrataCampo(Campo campo) {
    	if (campo.equals(getCampo(Periodo.Cam.arrivoPrevisto))) {
    		dataArrivoAllEntrata=campo.getData();
		}
    	if (campo.equals(getCampo(Periodo.Cam.partenzaPrevista))) {
    		dataPartenzaAllEntrata=campo.getData();
		}
	}

	@Override
	protected void eventoUscitaCampo(Campo campo) {
		
		// in uscita dal campo arrivo, se cambiato tento di sincronizzare gli impegni risorsa
    	if (campo.equals(getCampo(Periodo.Cam.arrivoPrevisto))) {
    		Date dataArrivoAllUscita = campo.getData();
			if (!dataArrivoAllUscita.equals(dataArrivoAllEntrata)) {
				EsitoSyncRisorse esito = syncRisorse(true, dataArrivoAllEntrata, dataArrivoAllUscita);
				if (!esito.isPositivo()) {
					campo.setValore(dataArrivoAllEntrata);
					new MessaggioAvviso(esito.getMessaggioFalliti());
				}else{
					esito.commit();
					getCampo(Periodo.Cam.risorse).getNavigatore().aggiornaLista();
				}
			}
		}

		// in uscita dal campo partenza, se cambiato tento di sincronizzare gli impegni risorsa
    	if (campo.equals(getCampo(Periodo.Cam.partenzaPrevista))) {
    		Date dataPartenzaAllUscita = campo.getData();
			if (!dataPartenzaAllUscita.equals(dataPartenzaAllEntrata)) {
				EsitoSyncRisorse esito = syncRisorse(false, dataPartenzaAllEntrata, dataPartenzaAllUscita);
				if (!esito.isPositivo()) {
					campo.setValore(dataPartenzaAllEntrata);	
					new MessaggioAvviso(esito.getMessaggioFalliti());
				}else{
					esito.commit();
					getCampo(Periodo.Cam.risorse).getNavigatore().aggiornaLista();
				}			
			}
		}
	}
    
    /**
     * Tenta di sincronizzare tutte le risorse del periodo in conseguenza di una variazione 
     * della data di arrivo o partenza del periodo.
     * @param arrivo true se è variato l'arrivo, false se è variata la partenza
     * @param vecchiaData la data precedente
     * @param nuovaData la data nuova
     * @return un oggetto con le informazioni sull'esito della operazione
     */
    private EsitoSyncRisorse syncRisorse(boolean arrivo, Date vecchiaData, Date nuovaData){
    	EsitoSyncRisorse esito = new EsitoSyncRisorse();
    	Modulo modRisorsaPeriodo = RisorsaPeriodoModulo.get();
    	Query query;
    	Filtro filtro;
    	Dati dati;
    	
    	// trova tutte le righe di risorsa del periodo corrente che iniziano/finiscono nella data vecchia
    	int idPeriodo = getCodice();
    	filtro = FiltroFactory.crea(RisorsaPeriodo.Cam.periodo.get(), idPeriodo);
    	int[] chiaviPeriodiRisorse = RisorsaPeriodoModulo.get().query().valoriChiave(filtro);
    	
    	// spazzola una per una
    	for(int chiave : chiaviPeriodiRisorse){
    		
    		if (arrivo) {	// arrivo
    			Date dataIni=RisorsaPeriodoModulo.get().query().valoreData(RisorsaPeriodo.Cam.dataInizio.get(), chiave);
				if (dataIni.equals(vecchiaData)) {
					Campo campoDataFine = modRisorsaPeriodo.getCampo(RisorsaPeriodo.Cam.dataFine);
					Campo campoTipoRisorsa = modRisorsaPeriodo.getCampo(RisorsaPeriodo.Cam.tipoRisorsa);
					Campo campoCodRisorsa = modRisorsaPeriodo.getCampo(RisorsaPeriodo.Cam.risorsa);
					query = new QuerySelezione(modRisorsaPeriodo);
					query.addCampo(campoDataFine);
					query.addCampo(campoTipoRisorsa);
					query.addCampo(campoCodRisorsa);
					filtro = FiltroFactory.codice(modRisorsaPeriodo, chiave);
					query.setFiltro(filtro);
					dati=modRisorsaPeriodo.query().querySelezione(query);
					Date dataFine = dati.getDataAt(campoDataFine);
					int idTipoRisorsa = dati.getIntAt(campoTipoRisorsa);
					int idRisorsa = dati.getIntAt(campoCodRisorsa);
					dati.close();
					int codSovrapposto = PeriodoRisorsaLogica.getCodImpegnoSovrapposto(nuovaData, dataFine, idTipoRisorsa, idRisorsa, chiave);
					if (codSovrapposto!=0) {
						esito.addSovrapposto(codSovrapposto);
					}else{
						esito.schedulaMovimento(arrivo, chiave, nuovaData);
					}
				}
			} else {	// partenza
    			Date dataFine=RisorsaPeriodoModulo.get().query().valoreData(RisorsaPeriodo.Cam.dataFine.get(), chiave);
				if (dataFine.equals(vecchiaData)) {
					Campo campoDataInizio = modRisorsaPeriodo.getCampo(RisorsaPeriodo.Cam.dataInizio);
					Campo campoTipoRisorsa = modRisorsaPeriodo.getCampo(RisorsaPeriodo.Cam.tipoRisorsa);
					Campo campoCodRisorsa = modRisorsaPeriodo.getCampo(RisorsaPeriodo.Cam.risorsa);
					query = new QuerySelezione(modRisorsaPeriodo);
					query.addCampo(campoDataInizio);
					query.addCampo(campoTipoRisorsa);
					query.addCampo(campoCodRisorsa);
					filtro = FiltroFactory.codice(modRisorsaPeriodo, chiave);
					query.setFiltro(filtro);
					dati=modRisorsaPeriodo.query().querySelezione(query);
					Date dataInizio = dati.getDataAt(campoDataInizio);
					int idTipoRisorsa = dati.getIntAt(campoTipoRisorsa);
					int idRisorsa = dati.getIntAt(campoCodRisorsa);
					dati.close();
					int codSovrapposto = PeriodoRisorsaLogica.getCodImpegnoSovrapposto(dataInizio, nuovaData, idTipoRisorsa, idRisorsa, chiave);
					if (codSovrapposto!=0) {
						esito.addSovrapposto(codSovrapposto);
					}else{
						esito.schedulaMovimento(arrivo, chiave, nuovaData);
					}
				}

				

			}
    	}
    	
    	return esito;
    }
    
    
    private class EsitoSyncRisorse{
    	ArrayList<Integer> sovrapposti = new ArrayList<Integer>();
    	ArrayList<ScheduleSyncRisorse> schedules= new ArrayList<ScheduleSyncRisorse>();

		public void addSovrapposto(int chiave){
			sovrapposti.add(chiave);
		}

		public void schedulaMovimento(boolean arrivo, int chiave, Date data){
			schedules.add(new ScheduleSyncRisorse(arrivo,  chiave,  data));
		}
		
		public boolean isPositivo(){
			return sovrapposti.size()==0;
		}
		
		/**
		 * Persiste i cambiamenti
		 */
		public void commit(){
			for(ScheduleSyncRisorse schedule : schedules){
				schedule.commit();
			}
		}

		/**
		 * Ritorna un messaggio che dettaglia quali sono i periodi sorapposti
		 */
		public String getMessaggioFalliti(){
			String messaggio = "";
			for(int cod : sovrapposti){
				if (!messaggio.equals("")) {
					messaggio+="\n";
				}
				messaggio += PeriodoRisorsaLogica.getMessaggioOccupato(cod);
			}
			return messaggio;
		}
    	
    }
    
    private class ScheduleSyncRisorse{
    	boolean arrivo;
    	int chiave;
    	Date data;
    	
		public ScheduleSyncRisorse(boolean arrivo, int chiave, Date data) {
			super();
			this.arrivo = arrivo;
			this.chiave = chiave;
			this.data = data;
		}
		
		// persiste la variazione nel database
		public void commit(){
			Modulo mod = RisorsaPeriodoModulo.get();
			Query query = new QueryUpdate(mod);
			if (arrivo) {
				query.addCampo(RisorsaPeriodo.Cam.dataInizio.get(), data);
			} else {
				query.addCampo(RisorsaPeriodo.Cam.dataFine.get(), data);
			}
			
			Filtro filtro = FiltroFactory.codice(mod, this.chiave);
			query.setFiltro(filtro);
			mod.query().queryModifica(query);

		}
		
    }
    


    
    
    
    private void spostaRisorse(Date vecchiaData, Date nuovaData){
    	
    }
    
    

	public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        int codCambio = CausaleAP.cambio.getCodice();
        int cod;
        Campo campo;

        super.sincronizza();

        try { // prova ad eseguire il codice

            /* se la causale di entrata è un cambio la data di arrivo non è modificabile */
            cod = this.getInt(Cam.causaleArrivo.get());
            campo = this.getCampo(Cam.arrivoPrevisto.get());
            campo.setModificabile(cod != codCambio);

            /* se la causale di uscita è un cambio la data di partenza non è modificabile */
            cod = this.getInt(Cam.causalePartenza.get());
            campo = this.getCampo(Cam.partenzaPrevista.get());
            campo.setModificabile(cod != codCambio);

            /* se c'è il flag Partito la scheda non è abilitata */
            this.setModificabile(!this.getBool(Cam.partito.get()));
            campo = this.getCampo(Cam.partito.get());
            campo.setModificabile(true);

            /* se manca il flag Arrivato il flag Partito non è modificabile */
            campo = this.getCampo(Cam.partito.get());
            campo.setModificabile(this.getBool(Cam.arrivato.get()));

            /* se c'è il flag Arrivato alcuni campi non sono più modificabili */
            if (this.getBool(Cam.arrivato.get())) {
                campo = this.getCampo(Cam.arrivoPrevisto.get());
                campo.setModificabile(false);
                campo = this.getCampo(Cam.camera.get());
                campo.setModificabile(false);
                campo = this.getCampo(Cam.preparazione.get());
                campo.setModificabile(false);
                campo = this.getCampo(Cam.adulti.get());
                campo.setModificabile(false);
                campo = this.getCampo(Cam.bambini.get());
                campo.setModificabile(false);
                campo = this.getCampo(Cam.arrivoEffettivo.get());
                campo.setModificabile(false);
            }// fine del blocco if

            /* il campo Pasto è visibile solo se pensione è HB */
            boolean visibile = this.getInt(Cam.trattamento.get()) == Listino.PensioniPeriodo
                    .mezzaPensione
                    .getCodice();
            Campo campoPasto = this.getCampo(Cam.pasto.get());
            campoPasto.setVisibile(visibile);

            /**
             * Abilitazione del botton Edita Addebiti
             * verifica che la scheda sia modificabile e sia valida
             * (devono esserci inizio, fine, camera, persone)
             */
            boolean abilita = false;
            if (this.isModificabile()) {
                if (this.isValida()) {
                    abilita = true;
                }// fine del blocco if
            }// fine del blocco if
            this.getBotAddebiti().setEnabled(abilita);

            /* sincronizza i pannellini visualizzatori del tipo di entrata e uscita */
            this.getPanIn().sync();
            this.getPanOut().sync();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }

    private PanInOut getPanIn() {
        return panIn;
    }

    private void setPanIn(PanInOut panIn) {
        this.panIn = panIn;
    }

    private PanInOut getPanOut() {
        return panOut;
    }

    private void setPanOut(PanInOut panOut) {
        this.panOut = panOut;
    }

    private JButton getBotAddebiti() {
        return botAddebiti;
    }

    private void setBotAddebiti(JButton botAddebiti) {
        this.botAddebiti = botAddebiti;
    }

    private PanAddebiti getPanAddebiti() {
        /* variabili e costanti locali di lavoro */
        PanAddebiti pan;

        try { // prova ad eseguire il codice
            pan = this.panAddebiti;
            if (pan == null) {
                pan = new PanAddebiti();
                this.setPanAddebiti(pan);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return panAddebiti;
    }

    private void setPanAddebiti(PanAddebiti panAddebiti) {
        this.panAddebiti = panAddebiti;
    }

    /**
     * Azione modifica edita addebiti previsti.
     * </p>
     */
    private final class AzModAddebiti implements ActionListener {

        public void actionPerformed(ActionEvent unEvento) {
            editAddebiti();
        }
    } // fine della classe 'interna'

    /**
     * Pannello per visualizzazione grafica del tipo
     * di entrata e uscita nella scheda
     * </p>
     */
    private final class PanInOut extends PannelloFlusso {

        private Scheda scheda;

        private boolean entrata;

        private Modulo modPeriodo;

        private Icon iconaPartenza;

        private Icon iconaArrivo;

        private Icon iconaCambio;

        private JLabel label;

        private Campi campoCausale;

        private Campi campoLinkPeriodo;

        private JButton botRemoveCambio;

        private JPanel spacer;

        /**
         * Costruttore completo con parametri. <br>
         *
         * @param scheda di riferimento
         * @param entrata true per entrata false per uscita
         */
        public PanInOut(Scheda scheda, boolean entrata) {
            /* rimanda al costruttore della superclasse */
            super(Layout.ORIENTAMENTO_VERTICALE);

            /* regola le variabili di istanza coi parametri */
            this.scheda = scheda;
            this.entrata = entrata;

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

                /* regola i moduli */
                modPeriodo = PeriodoModulo.get();

                /* regola il campo causale */
                if (entrata) {
                    campoCausale = Periodo.Cam.causaleArrivo;
                    campoLinkPeriodo = Periodo.Cam.linkProvenienza;
                } else {
                    campoCausale = Periodo.Cam.causalePartenza;
                    campoLinkPeriodo = Periodo.Cam.linkDestinazione;
                }// fine del blocco if-else

                /* recupera le icone */
                iconaPartenza = modPeriodo.getIcona("bandierina");
                iconaArrivo = modPeriodo.getIcona("automobile");
                iconaCambio = modPeriodo.getIcona("frecciacambio");

                /* regolazioni grafiche di questo pannello */
                this.setUsaGapFisso(true);
                this.setGapPreferito(0);
                this.setAllineamento(Layout.ALLINEA_SX);
                this.setOpaque(false);

                /* creazione e aggiunta dei componenti */
                this.add(this.creaLabelTitolo());
                this.add(this.creaPanContenuti());

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch


        }

        /**
         * Crea la JLabel del titolo.
         * <p/>
         *
         * @return la JLabel del titolo
         */
        private JLabel creaLabelTitolo() {
            /* variabili e costanti locali di lavoro */
            JLabel label = null;
            String testo = "";

            try {    // prova ad eseguire il codice
                if (entrata) {
                    testo = "entrata";
                } else {
                    testo = "uscita";
                }// fine del blocco if-else
                label = new JLabel(testo);
                TestoAlgos.setEtichetta(label);
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return label;
        }

        /**
         * Crea il Pannello Contenuti.
         * <p/>
         *
         * @return il pannello Contenuti
         */
        private Pannello creaPanContenuti() {
            /* variabili e costanti locali di lavoro */
            Pannello pan = null;

            try {    // prova ad eseguire il codice
                pan = PannelloFactory.orizzontale(null);

                /* questo pannello ha una dimensione fissa */
                pan.setDimFissa(60, 22);

                /* gap fisso a zero */
                pan.setUsaGapFisso(true);
                pan.setGapPreferito(0);

                pan.setAllineamento(Layout.ALLINEA_CENTRO);
                pan.getPanFisso().setBorder(BorderFactory.createEtchedBorder(0));

                pan.setOpaque(true);
                pan.setBackground(new Color(249, 221, 90));

                /* crea la JLabel per icone e camera */
                label = new JLabel();
                label.setOpaque(false);
                label.setOpaque(true);
                label.setBackground(Color.yellow);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);
                label.setVerticalTextPosition(JLabel.CENTER);
                if (entrata) {
                    label.setHorizontalTextPosition(SwingConstants.LEADING);
                } else {
                    label.setHorizontalTextPosition(SwingConstants.TRAILING);
                }// fine del blocco if-else
                TestoAlgos.setListaBold(label);

                /* crea il bottone rimuovi cambio */
                botRemoveCambio = this.creaBottoneRemoveCambio();

                /* crea lo spaziatore */
                spacer = new JPanel();
                spacer.setOpaque(false);
                spacer.setPreferredSize(new Dimension(5, 1));
                Lib.Comp.bloccaDim(spacer);

                /* aggiunge i componenti */
                if (entrata) {
                    pan.add(Box.createHorizontalGlue());
                    pan.add(label);
                    pan.add(spacer);
                    pan.add(botRemoveCambio);
                    pan.add(Box.createHorizontalGlue());
                } else {
                    pan.add(Box.createHorizontalGlue());
                    pan.add(label);
                    pan.add(spacer);
                    pan.add(botRemoveCambio);
                    pan.add(Box.createHorizontalGlue());
                }// fine del blocco if-else


            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return pan;
        }

        /**
         * Crea il bottone per rimuovere il cambio
         * già collegato all'azione.
         * <p/>
         *
         * @return il bottone creato
         */
        private JButton creaBottoneRemoveCambio() {
            /* variabili e costanti locali di lavoro */
            JButton bottone = null;
            Icon icona;

            try {    // prova ad eseguire il codice

                /* crea il bottone con l'icona */
                icona = modPeriodo.getIcona("removecambio");
                bottone = new JButton(icona);

                /* assegna il tooltip */
                bottone.setToolTipText("Elimina il cambio");

                /* regola graficamente il bottone */
                bottone.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

                /* aggiunge il listener */
                bottone.addActionListener(new AzRemoveCambio());

            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return bottone;
        }

        /**
         * Sincronizza il contenuto dell'oggetto
         * in base ai valori della scheda.
         * <p/>
         */
        private void sync() {
            /* variabili e costanti locali di lavoro */
            boolean cambio;
            String camera;
            int periodo;
            int periodoLink;

            try {    // prova ad eseguire il codice

                cambio = this.isCambio();

                if (cambio) {
                    periodo = scheda.getCodice();
                    periodoLink = modPeriodo.query().valoreInt(campoLinkPeriodo.get(), periodo);
                    camera = this.getNomeCamera(periodoLink);
                    label.setText(camera);
                    label.setIcon(iconaCambio);
                } else {
                    label.setText("");
                    if (entrata) {
                        label.setIcon(iconaArrivo);
                    } else {
                        label.setIcon(iconaPartenza);
                    }// fine del blocco if-else
                }// fine del blocco if-else

                botRemoveCambio.setVisible(cambio);
                spacer.setVisible(cambio);

            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

        }

        /**
         * Controlla se è un cambio.
         * <p/>
         *
         * @return true se è un cambio
         */
        private boolean isCambio() {
            /* variabili e costanti locali di lavoro */
            boolean cambio = false;
            int codCambio = CausaleAP.cambio.getCodice();
            int cod;

            try {    // prova ad eseguire il codice

                cod = scheda.getInt(campoCausale.get());
                cambio = (cod == codCambio);

            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return cambio;
        }

        /**
         * Ritorna il nome della camera di un dato periodo.
         * <p/>
         *
         * @param codPeriodo codice del periodo
         *
         * @return nome della camera
         */
        private String getNomeCamera(int codPeriodo) {
            /* variabili e costanti locali di lavoro */
            Modulo modCamera;
            String nomeCamera = "";
            int codCamera;

            try {    // prova ad eseguire il codice
                modCamera = CameraModulo.get();
                codCamera = modPeriodo.query().valoreInt(Periodo.Cam.camera.get(), codPeriodo);
                nomeCamera = modCamera.query().valoreStringa(Camera.Cam.camera.get(), codCamera);
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return nomeCamera;
        }

        /**
         * Metodo chiamato dall'azione Elimina Cambio.
         * <p/>
         * Chiede conferma e rimanda alla scheda
         */
        private void removeCambio() {
            /* variabili e costanti locali di lavoro */

            /* variabili e costanti locali di lavoro */
            MessaggioDialogo dialogo;
            boolean continua;
            int questoPeriodo;
            int altroPeriodo;
            String cameraQuesto;
            String cameraAltro;
            String testo;

            try { // prova ad eseguire il codice

                questoPeriodo = scheda.getCodice();
                altroPeriodo = modPeriodo.query().valoreInt(campoLinkPeriodo.get(), questoPeriodo);
                cameraQuesto = getNomeCamera(questoPeriodo);
                cameraAltro = getNomeCamera(altroPeriodo);
                if (entrata) {
                    testo = cameraAltro + " -> " + cameraQuesto;
                } else {
                    testo = cameraQuesto + " -> " + cameraAltro;
                }// fine del blocco if-else

                /* chiede conferma */
                dialogo = new MessaggioDialogo(
                        "Sei sicuro di voler eliminare il cambio " +
                                testo +
                                "?");
                continua = dialogo.isConfermato();

                /* esegue */
                if (continua) {
                    eliminaCambio(entrata);
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

        /**
         * Azione bottone elimina cambio premuto
         * <p/>
         */
        private final class AzRemoveCambio implements ActionListener {

            public void actionPerformed(ActionEvent event) {
                removeCambio();
            }
        } // fine della classe 'interna'


    } // fine della classe 'interna'

    /**
     * Selettore del periodo di provenienza/destinazione per creare un cambio
     * <p/>
     */
    private final class SelPeriodoLink extends DialogoAnnullaConferma {

        private int[] periodiAgganciabili;

        private boolean arrivo;

        private ListaSingola lista;

        /**
         * Costruttore completo con parametri. <br>
         *
         * @param periodiAgganciabili elenco ordinato dei codici dei periodi agganciabili
         * @param arrivo true per cambio in arrivo false per bambio in partenza
         */
        public SelPeriodoLink(int[] periodiAgganciabili, boolean arrivo) {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili di istanza coi parametri */
            this.setPeriodiAgganciabili(periodiAgganciabili);
            this.setArrivo(arrivo);

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
            ListaSingola lista;
            String[] camere;
            String testo;

            try { // prova ad eseguire il codice

                this.setTitolo("Possibilità di cambio");
                if (this.isArrivo()) {
                    testo = "provenienza";
                } else {
                    testo = "destinazione";
                }// fine del blocco if-else

                this.setMessaggio(
                        "Selezionare la camera di " + testo +
                                "\n(premere Annulla se non è un cambio)");

                camere = this.getCamere();
                lista = new ListaSingola();
                this.setLista(lista);
                lista.getLista().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                lista.getLista().addListSelectionListener(new AzSelModificata());
                lista.setValori(camere);
                this.addComponente(lista);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }

        /**
         * Ritorna le camere relative ai periodi collegabili.
         * <p/>
         *
         * @return un array di stringhe con i nomi delle camere
         *         nello stesso ordine dell'elenco periodi
         */
        private String[] getCamere() {
            /* variabili e costanti locali di lavoro */
            String[] camere = null;
            int[] periodi;
            int quanti;
            Modulo modCamera;
            Modulo modPeriodo;
            Campo campoCamera;
            int codPeriodo;
            String stringa;
            Query query;
            Filtro filtro;
            Dati dati;

            try {    // prova ad eseguire il codice

                modCamera = CameraModulo.get();
                modPeriodo = PeriodoModulo.get();
                campoCamera = modCamera.getCampo(Camera.Cam.camera);

                periodi = this.getPeriodiAgganciabili();
                quanti = periodi.length;
                camere = new String[quanti];

                for (int k = 0; k < periodi.length; k++) {
                    codPeriodo = periodi[k];
                    query = new QuerySelezione(modPeriodo);
                    filtro = FiltroFactory.codice(modPeriodo, codPeriodo);
                    query.addCampo(campoCamera);
                    query.setFiltro(filtro);
                    dati = modPeriodo.query().querySelezione(query);
                    stringa = dati.getStringAt(campoCamera);
                    camere[k] = stringa;
                    dati.close();
                } // fine del ciclo for


            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return camere;
        }

        /**
         * Ritorna il codice del periodo selezionato.
         * <p/>
         *
         * @return il codice del periodo
         */
        public int getCodPeriodoSelezionato() {
            /* variabili e costanti locali di lavoro */
            int codice = 0;
            ListaSingola lista;
            int indice;

            try {    // prova ad eseguire il codice
                lista = this.getListaSingola();
                indice = lista.getLista().getSelectedIndex();
                codice = this.getPeriodiAgganciabili()[indice];
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return codice;
        }

        public boolean isConfermabile() {
            /* variabili e costanti locali di lavoro */
            boolean confermabile = false;
            ListaSingola lista;
            int indice;

            try { // prova ad eseguire il codice
                confermabile = super.isConfermabile();
                if (confermabile) {
                    lista = this.getListaSingola();
                    indice = lista.getLista().getSelectedIndex();
                    confermabile = (indice >= 0);
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return confermabile;
        }

        private int[] getPeriodiAgganciabili() {
            return periodiAgganciabili;
        }

        private void setPeriodiAgganciabili(int[] periodiAgganciabili) {
            this.periodiAgganciabili = periodiAgganciabili;
        }

        private boolean isArrivo() {
            return arrivo;
        }

        private void setArrivo(boolean arrivo) {
            this.arrivo = arrivo;
        }

        private ListaSingola getListaSingola() {
            return lista;
        }

        private void setLista(ListaSingola lista) {
            this.lista = lista;
        }

        /**
         * Listener di modifica selezione nella JList.
         */
        private class AzSelModificata implements ListSelectionListener {

            public void valueChanged(ListSelectionEvent event) {
                sincronizza();
            }
        } // fine della classe interna

    } // fine della classe 'interna'

    /**
     * Richiama i controlli della scheda specifica.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override
    protected Controlli[] getControlli() {
        return Controllo.values();
    }

    /**
     * Classe interna Enumerazione.
     */
    public enum Controllo implements Controlli {

        /* date inizio e fine in sequenza */
        date("le date indicate non sono nella corretta sequenza") {
            public boolean isValido(Scheda scheda) {
                /* variabili e costanti locali di lavoro */
                boolean valido = true;
                Date dataIni = null;
                Date dataEnd = null;

                try { // prova ad eseguire il codice
                    dataIni = Libreria.getDate(scheda.getValore(Cam.arrivoPrevisto.get()));
                    dataEnd = Libreria.getDate(scheda.getValore(Cam.partenzaPrevista.get()));
                    if (!Lib.Data.isVuota(dataIni) && (!Lib.Data.isVuota(dataEnd))) {
                        valido = Lib.Data.isPosteriore(dataIni, dataEnd);
                    }// fine del blocco if
                } catch (Exception unErrore) { // intercetta l'errore
                    Errore.crea(unErrore);
                }// fine del blocco try-catch

                /* valore di ritorno */
                return valido;
            }
        },

        /* sovrapposizione a un periodo già prenotato */
        periodo("la camera è già occupata nel periodo indicato") {
            public boolean isValido(Scheda scheda) {
                return ((PeriodoScheda)scheda).isPeriodoValido();
            }
        };

        /**
         * messaggio di avviso
         */
        private String messaggio;

        /**
         * Costruttore completo con parametri.
         *
         * @param messaggio di avviso
         */
        Controllo(String messaggio) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setMessaggio(messaggio);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

        public abstract boolean isValido(Scheda scheda);

        public String getMessaggio(Scheda scheda) {
            return messaggio;
        }

        private void setMessaggio(String messaggio) {
            this.messaggio = messaggio;
        }


    }// fine della classe


}// fine della classe
