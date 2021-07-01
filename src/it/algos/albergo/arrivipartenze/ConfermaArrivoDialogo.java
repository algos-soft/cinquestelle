package it.algos.albergo.arrivipartenze;

import it.algos.albergo.AlbergoLib;
import it.algos.albergo.camera.Camera;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.clientealbergo.tabelle.parente.Parentela;
import it.algos.albergo.clientealbergo.tabelle.parente.ParentelaModulo;
import it.algos.albergo.conto.WrapConto;
import it.algos.albergo.listino.Listino;
import it.algos.albergo.pensioni.PanAddebiti;
import it.algos.albergo.pensioni.WrapAddebiti;
import it.algos.albergo.pensioni.WrapAddebitiConto;
import it.algos.albergo.pensioni.WrapAddebitiPeriodo;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.albergo.stampeobbligatorie.StampeObbLogica;
import it.algos.albergo.stampeobbligatorie.notifica.NotificaLogica;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.evento.navigatore.NavStatoAz;
import it.algos.base.evento.navigatore.NavStatoEve;
import it.algos.base.evento.pannello.PanModificatoAz;
import it.algos.base.evento.pannello.PanModificatoEve;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.lista.TavolaModello;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloMemoria;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.NavigatoreBase;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.tavola.Tavola;
import it.algos.base.wrapper.IntBool;
import it.algos.base.wrapper.TestoAlgos;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.tabelle.mezzopagamento.MezzoPagamento;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

/**
 * Dialogo di conferma di un arrivo.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-ott-2007 ore 19.21.46
 */
public class ConfermaArrivoDialogo extends DialogoAnnullaConferma {

    /* codice del periodo da confermare */
    private int codPeriodo;

    private ModuloMemoria memoria;

    private static final String NOME_CAMPO_DATA_ARRIVO = "data di arrivo";

    private static final String NOME_CAMPO_CAMERA = "camera";

    private static final String NOME_CAMPO_ADULTI = "adulti";

    private static final String NOME_CAMPO_BAMBINI = "bambini";

    private static final String NOME_CAMPO_PERSONE = "persone";

    private static final String NOME_CAMPO_CAPARRA = "caparra";

    private static final String NOME_CAMPO_MEZZO_CAPARRA = "mezzocaparra";

    private static final String NOME_CAMPO_RICEVUTA_CAPARRA = "ricevutacaparra";

    private static final String NOME_CAMPO_DATA_CAPARRA = "datacaparra";

    private static final String NOME_CAMPO_CLIENTE = "cliente";

    private static final String NOME_CAMPO_DATA_PARTENZA = "partenza prevista";

    private static final String NOME_CAMPO_ARRIVO_CON = "arrivo con";

    private static final String NOME_CAMPO_PENSIONE = "pensione";

    private static final String NOME_CAMPO_PASTO = "pasto";

    private static final String NOME_CAMPO_AZIENDA = Presenza.Cam.azienda.get();

    /* pannello gestione addebiti */
    private PanAddebiti panPensioni;

    /* flag - true se nella prenotazione c'era una caparra ed è stata utilizzata */
    private boolean caparraUtilizzata;

    /* pannello opzioni conto */
    private PanOpzioniConto panOpzioniConto;


    /**
     * Costruttore senza parametri.
     * <p/>
     */
    public ConfermaArrivoDialogo() {
        this(0);
    } /* fine del metodo costruttore completo */


    /**
     * Costruttore base con parametri.
     * <p/>
     *
     * @param codice del periodo da confermare
     */
    public ConfermaArrivoDialogo(int codice) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.setCodPeriodo(codice);
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
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
        /* variabili e costanti locali di lavoro */
        PanOpzioniConto panOpzioniConto;

        try { // prova ad eseguire il codice
            this.setTitolo("Conferma arrivo");

            /* creazione del modulo memoria per l'elenco dei clienti */
            this.creaMemoria();

            /* creazione del pannello OpzioniConto */
            panOpzioniConto = new PanOpzioniConto();
            panOpzioniConto.addListener(PannelloBase.Evento.modifica, new AzPanOpzioniModificato());
            this.setPanOpzioniConto(panOpzioniConto);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


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
    @Override
    public void inizializza() {
        Campo campo;
        Pannello panDialogo;
        Pannello panSinistra;
        Pannello panDestra;
        Pannello panTesta;
        Pannello pan;
        PanAddebiti panPensione;
        WrapAddebiti wpens;
        Modulo moduloMem;
        JButton bot;
        Icon icona;
        Connessione conn;

        try { // prova ad eseguire il codice

            /* crea e registra i campi del dialogo */
            this.creaCampi();

            /* regola i valori iniziali dei campi del dialogo */
            this.regolaValoriIniziali();

            /* aggiunge i clienti al modulo memoria */
            moduloMem = this.getMemoria();
            if (moduloMem != null) {
                moduloMem.inizializza();
                this.addGruppo();
                moduloMem.avvia();
            }// fine del blocco if

            /* pannello generale e laterali (contenuti) */
            panDialogo = PannelloFactory.orizzontale(this);
            panSinistra = PannelloFactory.verticale(this);
            panDestra = PannelloFactory.verticale(this);

            /* pannello dati di riferimento */
            panTesta = PannelloFactory.verticale(this);

            /* prima riga */
            pan = PannelloFactory.orizzontale(this);
            campo = this.getCampo(NOME_CAMPO_DATA_ARRIVO);
            pan.add(campo);
            campo = this.getCampo(NOME_CAMPO_CLIENTE);
            pan.add(campo);
            campo = this.getCampo(NOME_CAMPO_CAMERA);
            pan.add(campo);
            panTesta.add(pan);

            /* seconda riga */
            pan = PannelloFactory.orizzontale(this);
            campo = this.getCampo(NOME_CAMPO_DATA_PARTENZA);
            pan.add(campo);
            campo = this.getCampo(NOME_CAMPO_ADULTI);
            pan.add(campo);
            campo = this.getCampo(NOME_CAMPO_BAMBINI);
            pan.add(campo);
            campo = this.getCampo(NOME_CAMPO_PERSONE);
            pan.add(campo);
            campo = this.getCampo(NOME_CAMPO_CAPARRA);
            pan.add(campo);
            panTesta.add(pan);

            /* terza riga */
            pan = PannelloFactory.orizzontale(this);
            pan.setAllineamento(Layout.ALLINEA_BASSO);
            campo = this.getCampo(NOME_CAMPO_ARRIVO_CON);
            pan.add(campo);
            campo = this.getCampo(NOME_CAMPO_PENSIONE);
            pan.add(campo);
            campo = this.getCampo(NOME_CAMPO_PASTO);
            pan.add(campo);
            campo = this.getCampo(NOME_CAMPO_AZIENDA);
            pan.add(campo);
            
            // GESTIONE MULTIAZIENDA DISABLED 10-2017 ALEX
            campo.setValore(1);
            campo.setVisibile(false);
            // END DISABLED

            panTesta.add(pan);

            /* quarta riga */
            pan = PannelloFactory.orizzontale(this);
            bot = new JButton("Vai a prenotazione");
            icona = PrenotazioneModulo.get().getIcona("prenotazione24");
            bot.setIcon(icona);
            bot.setOpaque(false);
            bot.setFocusable(false);
            bot.addActionListener(new AzVaiPrenotazione());
            pan.add(bot);
            panTesta.add(pan);

            panSinistra.add(panTesta);

            /* pannello persone arrivate */
            Pannello panClienti = new PanClienti();
            panSinistra.add(panClienti);

            /* pannello opzioni conto */
            panSinistra.add(this.getPanOpzioniConto());

            /* pannello addebiti previsti*/
            conn = Progetto.getConnessione();
            wpens = new WrapAddebitiPeriodo(this.getCodPeriodo(), conn);
            panPensione = new PanAddebiti(wpens);
            this.setPanPensioni(panPensione);
            panDestra.add(panPensione);

            /* aggiunge il pannello-contenitore al dialogo */
            panDialogo.add(panSinistra);
            panDialogo.add(panDestra);
            this.addComponente(panDialogo.getPanFisso());


            super.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */


    /**
     * Regola i valori iniziali dei campi del dialogo.
     * <p/>
     */
    private void regolaValoriIniziali() {
        /* variabili e costanti locali di lavoro */
        int codCliente = 0;
        int codCamera = 0;
        int codAzienda = 0;
        int adulti = 0;
        int bambini = 0;
        int persone = 0;
        double caparra = 0;
        Date dataCaparra = null;
        int mezzoCaparra = 0;
        int ricevutaCaparra = 0;
        boolean capAccreditata;
        int arrivoCon = 0;
        int pensione = 0;
        Date dataArrivoPrevista = null;
        Date dataPartenza = null;

        boolean continua;
        int codPeriodo;
        int codPrenotazione = 0;
        Modulo modPeriodo = null;
        Modulo modPren = null;
        Query query;
        Filtro filtro;
        Dati dati;
        Campo campoCodCliente = null;
        Campo campoCodAzienda = null;

        PanOpzioniConto panOpzioni;

        try {    // prova ad eseguire il codice

            codPeriodo = this.getCodPeriodo();
            continua = (codPeriodo > 0);

            /* recupera modulo Periodo */
            if (continua) {
                modPeriodo = PeriodoModulo.get();
                continua = (modPeriodo != null);
            }// fine del blocco if

            /* recupera modulo Prenotazione */
            if (continua) {
                modPren = PrenotazioneModulo.get();
                continua = (modPren != null);
            }// fine del blocco if

            /* recupera i campi dei moduli diversi da periodo da aggiungere alla query */
            if (continua) {
                campoCodCliente = modPren.getCampo(Prenotazione.Cam.cliente);
                campoCodAzienda = modPren.getCampo(Prenotazione.Cam.azienda);
            }// fine del blocco if

            /* costruisce la query sul periodo e recupera i dati */
            if (continua) {
                query = new QuerySelezione(modPeriodo);
                filtro = FiltroFactory.codice(modPeriodo, codPeriodo);
                query.addCampo(campoCodCliente);
                query.addCampo(campoCodAzienda);
                query.addCampo(Periodo.Cam.prenotazione.get());
                query.addCampo(Periodo.Cam.camera.get());
                query.addCampo(Periodo.Cam.adulti.get());
                query.addCampo(Periodo.Cam.bambini.get());
                query.addCampo(Periodo.Cam.persone.get());
                query.addCampo(Periodo.Cam.arrivoPrevisto.get());
                query.addCampo(Periodo.Cam.partenzaPrevista.get());
                query.addCampo(Periodo.Cam.arrivoCon.get());
                query.addCampo(Periodo.Cam.trattamento.get());
                query.setFiltro(filtro);
                dati = modPeriodo.query().querySelezione(query);
                codCliente = dati.getIntAt(campoCodCliente);
                codAzienda = dati.getIntAt(campoCodAzienda);
                codPrenotazione = dati.getIntAt(Periodo.Cam.prenotazione.get());
                codCamera = dati.getIntAt(Periodo.Cam.camera.get());
                adulti = dati.getIntAt(Periodo.Cam.adulti.get());
                bambini = dati.getIntAt(Periodo.Cam.bambini.get());
                persone = dati.getIntAt(Periodo.Cam.persone.get());
                dataArrivoPrevista = dati.getDataAt(Periodo.Cam.arrivoPrevisto.get());
                dataPartenza = dati.getDataAt(Periodo.Cam.partenzaPrevista.get());
                arrivoCon = dati.getIntAt(Periodo.Cam.arrivoCon.get());
                pensione = dati.getIntAt(Periodo.Cam.trattamento.get());
                dati.close();
            }// fine del blocco if

            /* recupera la caparra dalla prenotazione */
            if (continua) {
                query = new QuerySelezione(modPren);
                filtro = FiltroFactory.codice(modPren, codPrenotazione);
                query.addCampo(Prenotazione.Cam.caparra.get());
                query.addCampo(Prenotazione.Cam.mezzoCaparra.get());
                query.addCampo(Prenotazione.Cam.numRF.get());
                query.addCampo(Prenotazione.Cam.dataCaparra.get());
                query.addCampo(Prenotazione.Cam.caparraAccreditata.get());
                query.setFiltro(filtro);
                dati = modPren.query().querySelezione(query);
                caparra = dati.getDoubleAt(Prenotazione.Cam.caparra.get());
                mezzoCaparra = dati.getIntAt(Prenotazione.Cam.mezzoCaparra.get());
                ricevutaCaparra = dati.getIntAt(Prenotazione.Cam.numRF.get());
                dataCaparra = dati.getDataAt(Prenotazione.Cam.dataCaparra.get());
                capAccreditata = dati.getBoolAt(Prenotazione.Cam.caparraAccreditata.get());
                dati.close();

                /* se la caparra è già stata accreditata, non la ripropone */
                if (capAccreditata) {
                    caparra = 0;
                }// fine del blocco if

            }// fine del blocco if

            /* registra i dati nel dialogo */
            if (continua) {
                this.setDataArrivo(dataArrivoPrevista);
                this.setCodCliente(codCliente);
                this.setCodCamera(codCamera);
                this.setCodAzienda(codAzienda);
                this.setAdultiPrevisti(adulti);
                this.setBambiniPrevisti(bambini);
                this.setPersonePreviste(persone);
                this.setCaparra(caparra);
                if (caparra != 0) {
                    this.setCaparraUtilizzata(true);
                }// fine del blocco if
                this.setMezzoCaparra(mezzoCaparra);
                this.setRicevutaCaparra(ricevutaCaparra);
                this.setDataCaparra(dataCaparra);
                this.setDataPartenza(dataPartenza);
                this.setArrivoCon(arrivoCon);
                this.setPensione(pensione);

            }// fine del blocco if

            /**
             * assegna il cliente di default (l'intestatario della prenotazione)
             * al pannello opzioni conto
             */
            if (continua) {
                panOpzioni = this.getPanOpzioniConto();
                if (panOpzioni != null) {
                    panOpzioni.setCodClienteDefault(this.getCodCliente());
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Creazione del modulo memoria per l'elenco dei clienti.
     * </p>
     */
    private void creaMemoria() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        ArrayList<Campo> lista;
        ModuloMemoria mem;
        Navigatore nav;


        try { // prova ad eseguire il codice
            lista = new ArrayList<Campo>();

            campo = CampoFactory.checkBox(Nomi.check.get());
            campo.setVisibileVistaDefault(true);
            campo.setModificabileLista(true);
            lista.add(campo);

            campo = CampoFactory.intero(Nomi.codCliente.get());
            campo.setVisibileVistaDefault(false);
            campo.getCampoLista().setPresenteVistaDefault(true);
            lista.add(campo);

            campo = CampoFactory.testo(Nomi.cliente.get());
            campo.setLarLista(150);
            campo.setVisibileVistaDefault(true);
            lista.add(campo);

            campo = CampoFactory.testo(Nomi.parentela.get());
            campo.setVisibileVistaDefault(true);
            campo.setTitoloColonna("par.");
            campo.setLarLista(50);
            lista.add(campo);

            campo = CampoFactory.checkBox(Nomi.bambino.get());
            campo.setVisibileVistaDefault(true);
            campo.setModificabileLista(true);
            campo.setAllineamentoLista(SwingConstants.CENTER);
//            campo.setEditor(new EditorBambino());
            campo.setRenderer(new RendererBambino(campo));
            campo.setTitoloColonna("bam");
            campo.setLarLista(35);
            lista.add(campo);

            campo = CampoFactory.checkBox(Nomi.datiValidi.get());
            campo.setRenderer(new RendererDatiCliente(campo, this));
            campo.setVisibileVistaDefault(true);
            lista.add(campo);

            campo = CampoFactory.checkBox(Nomi.nuovoconto.get());
            campo.getCampoLista().setPresenteVistaDefault(true);
            campo.setVisibileVistaDefault(false);
            lista.add(campo);

            campo = CampoFactory.intero(Nomi.linkcontocliente.get());
            campo.getCampoLista().setPresenteVistaDefault(true);
            campo.setVisibileVistaDefault(false);
            lista.add(campo);

            campo = CampoFactory.testo(Nomi.infoconto.get());
            campo.setModificabileLista(true);

            campo.setRenderer(new RendererConto(campo));
            campo.setEditor(new EditorConto(this));
            campo.setLarLista(120);
            campo.setVisibileVistaDefault(true);

            // todo per ora reso non visibile - alex  13-05-08
            campo.setVisibileVistaDefault(false);

            lista.add(campo);

            mem = this.setMemoria(new ModuloMemoria(lista, false));

            nav = new NavClienti(mem, this);
            nav.addListener(NavigatoreBase.Evento.statoModificato, new AzNavClientiModificato());
            mem.addNavigatoreCorrente(nav);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Creazione dei campi.
     * </p>
     */
    private void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice

            /* data arrivo */
            campo = CampoFactory.data(NOME_CAMPO_DATA_ARRIVO);
            this.addCampoCollezione(campo);

            /* cliente */
            campo = CampoFactory.comboLinkSel(NOME_CAMPO_CLIENTE);
            campo.setNomeModuloLinkato(ClienteAlbergo.NOME_MODULO);
            campo.setNomeCampoValoriLinkato(Anagrafica.Cam.soggetto.get());
            campo.setUsaNuovo(false);
            campo.setUsaModifica(false);
            campo.setLarScheda(160);
            campo.setAbilitato(false);
            this.addCampoCollezione(campo);

            /* camera */
            campo = CampoFactory.comboLinkSel(NOME_CAMPO_CAMERA);
            campo.setNomeModuloLinkato(Camera.NOME_MODULO);
            campo.setNomeCampoValoriLinkato(Camera.Cam.camera.get());
            campo.setUsaNuovo(false);
            campo.setLarScheda(70);
            campo.setAbilitato(false);
            this.addCampoCollezione(campo);

            /* adulti */
            campo = CampoFactory.intero(NOME_CAMPO_ADULTI);
            campo.setLarScheda(50);
            campo.setAbilitato(false);
            this.addCampoCollezione(campo);

            /* bambini */
            campo = CampoFactory.intero(NOME_CAMPO_BAMBINI);
            campo.setLarScheda(50);
            campo.setAbilitato(false);
            this.addCampoCollezione(campo);

            /* persone */
            campo = CampoFactory.intero(NOME_CAMPO_PERSONE);
            campo.setLarScheda(50);
            campo.setAbilitato(false);
            this.addCampoCollezione(campo);

            /* importo caparra */
            campo = CampoFactory.valuta(NOME_CAMPO_CAPARRA);
            campo.setLarScheda(60);
            campo.setAbilitato(false);
            this.addCampoCollezione(campo);

            /* mezzo caparra */
            campo = CampoFactory.comboLinkPop(NOME_CAMPO_MEZZO_CAPARRA);
            campo.setNomeModuloLinkato(MezzoPagamento.NOME_MODULO);
            campo.setAbilitato(false);
            this.addCampoCollezione(campo);

            /* n. ricevuta caparra */
            campo = CampoFactory.intero(NOME_CAMPO_RICEVUTA_CAPARRA);
            campo.setAbilitato(false);
            this.addCampoCollezione(campo);

            /* data di pagamento della caparra */
            campo = CampoFactory.data(NOME_CAMPO_DATA_CAPARRA);
            campo.setAbilitato(false);
            this.addCampoCollezione(campo);

            /* data partenza prevista */
            campo = CampoFactory.data(NOME_CAMPO_DATA_PARTENZA);
            campo.setAbilitato(false);
            this.addCampoCollezione(campo);

            /* arrivo con */
            campo = CampoFactory.comboInterno(NOME_CAMPO_ARRIVO_CON);
            campo.setValoriInterni(Periodo.TipiAP.getElementiArrivo());
            this.addCampoCollezione(campo);

            /* pensione */
            campo = PeriodoModulo.get().getCloneCampo(Periodo.Cam.trattamento.get());
            campo.setNomeInternoCampo(NOME_CAMPO_PENSIONE);
            this.addCampoCollezione(campo);

            /* pasto */
            campo = PresenzaModulo.get().getCloneCampo(Presenza.Cam.pasto.get());
            campo.setNomeInternoCampo(NOME_CAMPO_PASTO);
            this.addCampoCollezione(campo);

            /* campo azienda */
            campo = PresenzaModulo.get().getCloneCampo(NOME_CAMPO_AZIENDA);
            this.addCampoCollezione(campo);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Crea i record dei clienti appartenenti al gruppo.
     * </p>
     * Il gruppo è quello di appartenenza del cliente che ha effettuato la prenotazione <br>
     */
    private void addGruppo() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int codPeriodo;
        int codPrenotazione = 0;
        int codCliente = 0;
        int codCapo = 0;
        Modulo modPeriodo = null;
        Modulo modPrenotazione = null;
        Modulo modCliente = null;
        Filtro filtro;
        Ordine ordine;
        int[] codici = null;

        try { // prova ad eseguire il codice
            codPeriodo = this.getCodPeriodo();
            continua = (codPeriodo > 0);

            if (continua) {
                modPeriodo = PeriodoModulo.get();
                continua = (modPeriodo != null);
            }// fine del blocco if

            if (continua) {
                codPrenotazione = modPeriodo.query()
                        .valoreInt(Periodo.Cam.prenotazione.get(), codPeriodo);
                continua = (codPrenotazione > 0);
            }// fine del blocco if

            if (continua) {
                modPrenotazione = PrenotazioneModulo.get();
                continua = (modPrenotazione != null);
            }// fine del blocco if

            if (continua) {
                codCliente = modPrenotazione.query()
                        .valoreInt(Prenotazione.Cam.cliente.get(), codPrenotazione);
                continua = (codCliente > 0);
            }// fine del blocco if


            if (continua) {
                modCliente = ClienteAlbergoModulo.get();
                continua = (modCliente != null);
            }// fine del blocco if

            if (continua) {
                codCapo = modCliente.query().valoreInt(ClienteAlbergo.Cam.linkCapo.get(),
                        codCliente);
                continua = codCapo > 0;
            }// fine del blocco if

            if (continua) {
                ordine = AlbergoLib.getOrdineGruppo();
                filtro = FiltroFactory.crea(ClienteAlbergo.Cam.linkCapo.get(), codCapo);
                codici = modCliente.query().valoriChiave(filtro, ordine);
                continua = (codici != null && codici.length > 0);
            }// fine del blocco if

            if (continua) {
                for (int cod : codici) {
                    if (!PresenzaModulo.isPresente(cod)) {
                        this.addCliente(cod, false);
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Aggiunge un record di un cliente dell'albergo.
     * </p>
     *
     * @param cod codice di anagrafica
     * @param check true per avere il check già selezionato (aggiunte successive)
     */
    public void addCliente(int cod, boolean check) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        ModuloMemoria mem;
        Modulo modCliente = null;
        Modulo modParentela = null;
        int codNew;
        String soggetto = "";
        String parentela = "";
        Campo campoParentela = null;
        Date dataArrivo;
        boolean bambino = false;
        Query query;
        Dati dati;


        try { // prova ad eseguire il codice
            mem = this.getMemoria();
            continua = (mem != null);

            if (continua) {
                modCliente = ClienteAlbergoModulo.get();
                continua = (modCliente != null);
            }// fine del blocco if

            if (continua) {
                modParentela = ParentelaModulo.get();
                continua = (modParentela != null);
            }// fine del blocco if

            if (continua) {
                campoParentela = modParentela.getCampo(Parentela.Cam.sigla);
                continua = (campoParentela != null);
            }// fine del blocco if

            if (continua) {

                soggetto = modCliente.query().valoreStringa(Anagrafica.Cam.soggetto.get(), cod);

                query = new QuerySelezione(modCliente);
                query.addCampo(campoParentela);
                query.setFiltro(FiltroFactory.codice(modCliente, cod));
                dati = modCliente.query().querySelezione(query);
                parentela = dati.getStringAt(campoParentela);
                dati.close();
            }// fine del blocco if

            /* determina se bambino al momento dell'arrivo */
            if (continua) {
                dataArrivo = getDataArrivo();
                bambino = ClienteAlbergoModulo.isBambino(cod, dataArrivo);
            }// fine del blocco if


            if (continua) {
                codNew = mem.query().nuovoRecord();
                mem.query().registra(codNew, Nomi.check.get(), check);
                mem.query().registra(codNew, Nomi.codCliente.get(), cod);
                mem.query().registra(codNew, Nomi.cliente.get(), soggetto);
                mem.query().registra(codNew, Nomi.parentela.get(), parentela);
                mem.query().registra(codNew, Nomi.bambino.get(), bambino);
                mem.query().registra(codNew, Nomi.nuovoconto.get(), true);
                mem.query().registra(codNew, Nomi.linkcontocliente.get(), this.getCodCliente());
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Presenta il dialogo opzioni conto.
     * <p/>
     *
     * @param codRiga del record del db memoria
     */
    void editConto(int codRiga) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        OpzioniContoDialogo dialogoOpzioni;
        boolean nuovoConto;
        int codContoCliente;
        Modulo modMemoria;
        ArrayList<Integer> codClientiDisponibili;

        try {    // prova ad eseguire il codice

            modMemoria = this.getMemoria();
            continua = (modMemoria != null);

            if (continua) {

                nuovoConto = modMemoria.query().valoreBool(Nomi.nuovoconto.get(), codRiga);
                codContoCliente = modMemoria.query().valoreInt(Nomi.linkcontocliente.get(),
                        codRiga);

                codClientiDisponibili = this.getClientiDisponibiliConto();
                dialogoOpzioni = new OpzioniContoDialogo(nuovoConto,
                        codClientiDisponibili,
                        codContoCliente);
                dialogoOpzioni.inizializza();
                dialogoOpzioni.avvia();

                if (dialogoOpzioni.isConfermato()) {
                    nuovoConto = dialogoOpzioni.isNuovoConto();
                    codContoCliente = dialogoOpzioni.getCodContoCliente();

                    modMemoria.query().registra(codRiga, Nomi.nuovoconto.get(), nuovoConto);
                    modMemoria.query().registra(codRiga,
                            Nomi.linkcontocliente.get(),
                            codContoCliente);
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    private Navigatore getNavClienti() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;
        ModuloMemoria mem;

        try { // prova ad eseguire il codice
            mem = this.getMemoria();

            if (mem != null) {
                nav = mem.getNavigatoreCorrente();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


    /**
     * Determina se il dialogo e' confermabile o registrabile.
     * <p/>
     *
     * @return true se confermabile / registrabile
     */
    @Override
    public boolean isConfermabile() {
        /* variabili e costanti locali di lavoro */
        boolean confermabile = false;
        int quantiCheck;
        Filtro filtro;
        Date data;

        try { // prova ad eseguire il codice

            confermabile = super.isConfermabile();

            /* deve essere flaggato almeno un record */
            if (confermabile) {
                filtro = FiltroFactory.creaVero(ConfermaArrivoDialogo.Nomi.check.get());
                quantiCheck = this.getMemoria().query().contaRecords(filtro);
                confermabile = quantiCheck > 0;
            }// fine del blocco if

            /* ci deve essere la data di arrivo */
            if (confermabile) {
                data = this.getDataArrivo();
                confermabile = !Lib.Data.isVuota(data);
            }// fine del blocco if

            /* il pannello opzioni conto deve essere valido */
            if (confermabile) {
                confermabile = this.getPanOpzioniConto().isValido();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermabile;
    }


    protected void eventoMemoriaModificata(Campo campo) {
        /* variabili e costanti locali di lavoro */
        try { // prova ad eseguire il codice

            /* se cambio la data di arrivo aggiorna il check bambini */
            if (campo.getNomeInterno().equals(NOME_CAMPO_DATA_ARRIVO)) {
                this.syncFlagBambini();
            }// fine del blocco if

            /* se cambio il tipo di pensione avvisa di controllare gli addebiti */
            if (campo.getNomeInterno().equals(NOME_CAMPO_PENSIONE)) {
                new MessaggioAvviso("Hai modificato il tipo di pensione.\nControlla gli addebiti!");
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
    @Override protected void eventoUscitaCampoModificato(Campo campo) {
        /* variabili e costanti locali di lavoro */
        Campo campoArrivo;
        Date dataPrevista;
        Date dataEffettiva;
        String messaggio;

        try { // prova ad eseguire il codice

            /* se cambio la data di arrivo ed è diversa da quella
            * prevista in prenotazione, avvisa */
            campoArrivo = this.getCampo(NOME_CAMPO_DATA_ARRIVO);
            if (campo.equals(campoArrivo)) {
                dataPrevista = this.getDataArrivoPrevista();
                dataEffettiva = this.getDataArrivo();
                if (!dataPrevista.equals(dataEffettiva)) {
                    messaggio = "La data di arrivo è diversa da quella prevista nella prenotazione";
                    messaggio += "\nControlla il conto";
                    new MessaggioAvviso(messaggio);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Invocato quando si preme il bottone conferma.
     * <p/>
     * Controlla che tutti i clienti in arrivo abbiano un documento valido
     * Se non ce l'hanno, avvisa e chiede ulteriore conferma
     */
    public void eventoConferma() {
        /* variabili e costanti locali di lavoro */
        boolean passed = true;
        Date dataArrivo;
        int codAzienda;
        boolean registrabile;
        String testo;

        try { // prova ad eseguire il codice

            /**
             * Controlla che si possano ancora registrare arrivi
             * per la data e l'azienda in oggetto
             */
            if (passed) {
                dataArrivo = this.getDataArrivo();
                codAzienda = this.getCodAzienda();
                registrabile = StampeObbLogica.isArrivoRegistrabile(dataArrivo, codAzienda);
                if (!registrabile) {
                    testo = ArriviPartenzeLogica.getMessaggioArriviNonConfermabili(codAzienda);
                    new MessaggioAvviso(testo);
                    passed = false;
                }// fine del blocco if
            }// fine del blocco if

            /* controlla che il tipo di arrivo sia specificato */
            if (passed) {
                passed = this.chkTipoArrivo();
            }// fine del blocco if

            /* controlla che il tipo di pensione sia specificato */
            if (passed) {
                passed = this.chkPensione();
            }// fine del blocco if

            /* se mezza pensione controlla che il tipo di pasto sia specificato */
            if (passed) {
                if (this.isMezzaPensione()) {
                    passed = this.chkPasto();
                }// fine del blocco if
            }// fine del blocco if

            /* controlla la validità dei dati dei clienti in arrivo */
            if (passed) {
                passed = this.chkDatiClienti();
            }// fine del blocco if

            /* controlla il numero totale delle persone in arrivo rispetto al previsto */
            if (passed) {
                passed = this.chkNumPersone();
            }// fine del blocco if

            /* controlla il rapporto adulti/bambini rispetto al previsto */
            if (passed) {
                passed = this.chkAdultiBambini();
            }// fine del blocco if

            /* se il controllo è passato rimanda alla superclasse */
            if (passed) {
                super.eventoConferma();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Controlla che tutti i clienti in  arrivo abbiano dati validi.
     * <p/>
     * In caso contrario avvisa e chiede conferma
     *
     * @return true se tutti hanno doc valido o l'avviso è stato confermato
     */
    private boolean chkDatiClienti() {
        /* variabili e costanti locali di lavoro */
        boolean passed = true;
        ArrayList<Integer> selezionati;
        MessaggioDialogo messaggio;
        Date data;
        boolean capo;

        try {    // prova ad eseguire il codice

            /* controlla la validità dei documenti */
            selezionati = this.getClientiSelezionati();
            data = this.getDataArrivo();
            for (int cod : selezionati) {
                capo = this.isCapoNotifica(cod);
                if (!ClienteAlbergoModulo.isValidoArrivo(cod, data, capo)) {
                    passed = false;
                    break;
                }// fine del blocco if
            }

            /* chiede conferma */
            if (!passed) {
                messaggio = new MessaggioDialogo(
                        "I dati di alcuni clienti non sono validi!\nVuoi continuare?");
                passed = messaggio.isConfermato();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return passed;
    }


    /**
     * Controlla il numero delle persone in arrivo rispetto al previsto.
     * <p/>
     * In caso di differenza avvisa e chiede conferma
     *
     * @return true se le persone corrispondono o l'avviso è stato confermato
     */
    private boolean chkNumPersone() {
        /* variabili e costanti locali di lavoro */
        boolean passed = true;
        int quantiPrevisti;
        int quantiEffettivi;
        MessaggioDialogo messaggio;


        try { // prova ad eseguire il codice

            /* recupera */
            quantiPrevisti = this.getPersonePreviste();
            quantiEffettivi = this.getClientiSelezionati().size();

            /* controlla */
            if (quantiPrevisti != quantiEffettivi) {
                passed = false;
            }// fine del blocco if

            /* chiede conferma */
            if (!passed) {
                messaggio = new MessaggioDialogo(
                        "Il numero delle persone in arrivo è diverso dal previsto!\nVuoi continuare?");
                passed = messaggio.isConfermato();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return passed;

    }


    /**
     * Controlla rapporto adulti/bambini rispetto al previsto.
     * <p/>
     * In caso di differenza avvisa e chiede conferma
     *
     * @return true se adulti e bambini corrispondono o l'avviso è stato confermato
     */
    private boolean chkAdultiBambini() {
        /* variabili e costanti locali di lavoro */
        boolean passed = true;
        int adultiPrevisti;
        int bambiniPrevisti;
        int adultiEffettivi;
        int bambiniEffettivi;
        MessaggioDialogo messaggio;


        try { // prova ad eseguire il codice

            /* recupera i previsti */
            adultiPrevisti = this.getAdultiPrevisti();
            bambiniPrevisti = this.getBambiniPrevisti();

            /* recupera gli effettivi */
            adultiEffettivi = this.getAdultiEffettivi();
            bambiniEffettivi = this.getBambiniEffettivi();

            /* controlla */
            if (adultiPrevisti != adultiEffettivi) {
                passed = false;
            }// fine del blocco if

            /* controlla */
            if (bambiniPrevisti != bambiniEffettivi) {
                passed = false;
            }// fine del blocco if

            /* chiede conferma */
            if (!passed) {
                messaggio = new MessaggioDialogo(
                        "Il numero di adulti o bambini è diverso dal previsto!\nVuoi continuare?");
                passed = messaggio.isConfermato();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return passed;

    }


    /**
     * Controlla che il campo Tipo di arrivo sia specificato.
     * <p/>
     * Se non lo è avvisa e chiede conferma
     *
     * @return true il tipo di arrivo è specificato o l'avviso è stato confermato
     */
    private boolean chkTipoArrivo() {
        /* variabili e costanti locali di lavoro */
        boolean passed = true;
        int tipoArrivo;
        MessaggioDialogo messaggio;


        try { // prova ad eseguire il codice

            /* recupera */
            tipoArrivo = this.getArrivoCon();

            /* controlla */
            if (tipoArrivo == 0) {
                passed = false;
            }// fine del blocco if

            /* chiede conferma */
            if (!passed) {
                messaggio = new MessaggioDialogo(
                        "Il tipo di arrivo non è specificato!\nVuoi continuare?");
                passed = messaggio.isConfermato();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return passed;

    }


    /**
     * Controlla che il campo Pensione sia valorizzato.
     * <p/>
     * Se non lo è avvisa e chiede conferma
     *
     * @return true se la pensione è specificata o l'avviso è stato confermato
     */
    private boolean chkPensione() {
        /* variabili e costanti locali di lavoro */
        boolean passed = true;
        int pensione;
        MessaggioDialogo messaggio;


        try { // prova ad eseguire il codice

            /* recupera */
            pensione = this.getCodPensione();

            /* controlla */
            if (pensione == 0) {
                passed = false;
            }// fine del blocco if

            /* chiede conferma */
            if (!passed) {
                messaggio = new MessaggioDialogo(
                        "Il tipo di pensione non è specificato!\nVuoi continuare?");
                passed = messaggio.isConfermato();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return passed;

    }


    /**
     * Controlla che il campo Pasto sia valorizzato.
     * <p/>
     * Se non lo è avvisa e chiede conferma
     *
     * @return true se il pasto è specificato o l'avviso è stato confermato
     */
    private boolean chkPasto() {
        /* variabili e costanti locali di lavoro */
        boolean passed = true;
        int pasto;
        MessaggioDialogo messaggio;


        try { // prova ad eseguire il codice

            /* recupera */
            pasto = this.getCodPasto();

            /* controlla */
            if (pasto == 0) {
                passed = false;
            }// fine del blocco if

            /* chiede conferma */
            if (!passed) {
                messaggio = new MessaggioDialogo(
                        "Il tipo di pasto non è specificato!\nVuoi continuare?");
                passed = messaggio.isConfermato();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return passed;

    }


    /**
     * Ritorna la lista dei codici dei clienti in arrivo (spuntati).
     * <p/>
     *
     * @return lista dei codici clienti
     *         (oggetti IntBool contenenti codice cliente e flag bambino)
     */
    public ArrayList<IntBool> getClientiInArrivo() {
        /* variabili e costanti locali di lavoro */
        ArrayList<IntBool> lista = null;
        boolean continua;
        Modulo mod;
        Filtro filtro;
        int[] codiciRecord;
        int codCli;
        boolean flagBambino;
        IntBool wrapper;

        try {    // prova ad eseguire il codice
            lista = new ArrayList<IntBool>();

            mod = this.getMemoria();
            continua = (mod != null);

            if (continua) {
                filtro = this.getFiltroSelezionati();
                codiciRecord = mod.query().valoriChiave(filtro);
                for (int codRec : codiciRecord) {
                    codCli = mod.query().valoreInt(Nomi.codCliente.get(), codRec);
                    flagBambino = mod.query().valoreBool(Nomi.bambino.get(), codRec);
                    wrapper = new IntBool(codCli, flagBambino);
                    lista.add(wrapper);
                }
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Ritorna la lista dei codici dei clienti selezionati.
     * <p/>
     *
     * @return lista dei codici clienti
     */
    private ArrayList<Integer> getClientiSelezionati() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Integer> listaCod = null;
        boolean continua;
        Modulo mod;
        Filtro filtro;

        try {    // prova ad eseguire il codice
            listaCod = new ArrayList<Integer>();

            mod = this.getMemoria();
            continua = (mod != null);

            if (continua) {
                filtro = this.getFiltroSelezionati();
                listaCod = mod.query().valoriCampo(Nomi.codCliente.get(), filtro);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaCod;
    }


    /**
     * Ritorna la lista dei codici dei clienti adulti selezionati.
     * <p/>
     *
     * @return lista dei codici clienti adulti
     */
    public ArrayList<Integer> getAdultiSelezionati() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Integer> listaCod = null;
        boolean continua;
        Modulo mod;
        Filtro filtro;

        try {    // prova ad eseguire il codice
            listaCod = new ArrayList<Integer>();

            mod = this.getMemoria();
            continua = (mod != null);

            if (continua) {
                filtro = this.getFiltroSelezionati();
                filtro.add(FiltroFactory.creaFalso(Nomi.bambino.get()));
                listaCod = mod.query().valoriCampo(Nomi.codCliente.get(), filtro);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaCod;
    }


    /**
     * Ritorna la lista dei codici dei clienti adulti selezionati.
     * <p/>
     *
     * @return lista dei codici clienti adulti
     */
    public ArrayList<Integer> getBambiniSelezionati() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Integer> listaCod = null;
        boolean continua;
        Modulo mod;
        Filtro filtro;

        try {    // prova ad eseguire il codice
            listaCod = new ArrayList<Integer>();

            mod = this.getMemoria();
            continua = (mod != null);

            if (continua) {
                filtro = this.getFiltroSelezionati();
                filtro.add(FiltroFactory.creaVero(Nomi.bambino.get()));
                listaCod = mod.query().valoriCampo(Nomi.codCliente.get(), filtro);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaCod;
    }


    /**
     * Ritorna il filtro corrispondente ai clienti attualmente
     * selezionati con il check box in Arrivo.
     * <p/>
     *
     * @return il filtro selezionati
     */
    private Filtro getFiltroSelezionati() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;

        try {    // prova ad eseguire il codice
            filtro = FiltroFactory.creaVero(Nomi.check.get());
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Sincronizza il flag bambino su tutti i clienti in lista
     * in base alla data di arrivo.
     * <p/>
     */
    private void syncFlagBambini() {
        /* variabili e costanti locali di lavoro */
        Modulo mem;
        int[] codiciRecord;
        int codCli;
        Date dataArrivo;
        boolean bambino;

        try {    // prova ad eseguire il codice
            dataArrivo = this.getDataArrivo();
            mem = this.getMemoria();
            codiciRecord = mem.query().valoriChiave();
            for (int codRecord : codiciRecord) {
                codCli = mem.query().valoreInt(Nomi.codCliente.get(), codRecord);
                bambino = ClienteAlbergoModulo.isBambino(codCli, dataArrivo);
                mem.query().registra(codRecord, Nomi.bambino.get(), bambino);
            }
            this.getNavClienti().aggiornaLista();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Determina se un dato cliente è il capogruppo della scheda di notifica
     * che verrà emessa relativamente agli arrivi selezionati.
     * <p/>
     * @param codCliente cliente da controllare
     * @return true se è il capogruppo della scheda
     */
    public boolean isCapoNotifica(int codCliente) {
        /* variabili e costanti locali di lavoro */
        boolean capo = false;
        ArrayList<IntBool> lista;
        IntBool elemento;
        int cod;
        int[] inArrivo;
        int codCapo;

        try {    // prova ad eseguire il codice

            /* recupera la lista dei codici cliente in arrivo (spuntati) */
            lista = this.getClientiInArrivo();
            inArrivo = new int[lista.size()];
            for (int k = 0; k < lista.size(); k++) {
                elemento = lista.get(k);
                cod = elemento.getInt();
                inArrivo[k] = cod;
            } // fine del ciclo for

            /* elegge un cliente a Capogruppo Scheda di Notifica */
            codCapo = NotificaLogica.eleggiCapoScheda(inArrivo);

            /* se è il cliente stesso che sta controllando accende il flag */
            capo = (codCapo == codCliente);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return capo;
    }


    /**
     * Ritorna la lista di tutti i clienti disponibili ai quali si
     * può intestare il conto.
     * <p/>
     * Sono tutti quelli visualizzati nell'elenco persone in arrivo
     * più l'intestatario della prenotazione (se non già compreso nei clienti in arrivo)
     *
     * @return lista dei codici clienti disponibili
     */
    public ArrayList<Integer> getClientiDisponibiliConto() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Integer> listaCod = null;
        boolean continua;
        Modulo mod;
        int codPrenotazione;
        int codIntestatario;

        try {    // prova ad eseguire il codice
            listaCod = new ArrayList<Integer>();

            mod = this.getMemoria();
            continua = (mod != null);

            if (continua) {
                listaCod = mod.query().valoriCampo(Nomi.codCliente.get());
            }// fine del blocco if

            /**
             * aggiunge sempre l'intestatario della prenotazione se mancante.
             * in tal caso viene aggiunto come primo elemento
             */
            if (continua) {
                codPrenotazione = this.getCodPrenotazione();
                mod = PrenotazioneModulo.get();
                codIntestatario = mod.query().valoreInt(Prenotazione.Cam.cliente.get(),
                        codPrenotazione);
                if (!listaCod.contains(codIntestatario)) {
                    listaCod.add(0, codIntestatario);
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaCod;
    }


    /**
     * Ritorna il codice del cliente intestatario del conto.
     * <p/>
     * Significativo solo per nuovo conto
     *
     * @return il codice
     */
    public int getCodClienteConto() {
        /* variabili e costanti locali di lavoro */
        int cod = 0;
        PanOpzioniConto panOpzioniConto;

        try {    // prova ad eseguire il codice
            panOpzioniConto = this.getPanOpzioniConto();
            if (panOpzioniConto != null) {
                cod = panOpzioniConto.getCodCliente();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cod;
    }


    /**
     * Ritorna il codice del conto associato.
     * <p/>
     * Significativo solo per usa conto esistente
     *
     * @return il codice
     */
    public int getCodConto() {
        /* variabili e costanti locali di lavoro */
        int cod = 0;
        PanOpzioniConto panOpzioniConto;

        try {    // prova ad eseguire il codice
            panOpzioniConto = this.getPanOpzioniConto();
            if (panOpzioniConto != null) {
                cod = panOpzioniConto.getCodConto();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cod;
    }


    /**
     * Controlla se è selezionata l'opzione crea conto.
     * <p/>
     *
     * @return true se selezionata
     */
    private boolean isCreaConto() {
        return this.getPanOpzioniConto().isNuovoConto();
    }


    /**
     * Apre la finestra della prenotazione relativa a questo periodo.
     * <p/>
     */
    private void vaiPrenotazione() {
        /* variabili e costanti locali di lavoro */
        int codPrenotazione;
        boolean confermato;

        try { // prova ad eseguire il codice
            codPrenotazione = this.getCodPrenotazione();
            if (codPrenotazione > 0) {
                confermato = PrenotazioneModulo.get().presentaRecord(codPrenotazione);
                if (confermato) {
                    this.regolaValoriIniziali();
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Sincronizzazione della scheda/dialogo.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override
    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        PanOpzioniConto panOpzioniConto;
        Campo campo;

        try { // prova ad eseguire il codice

            /* aggiorna il popup dei clienti disponibili al nuovo conto */
            panOpzioniConto = this.getPanOpzioniConto();
            panOpzioniConto.setCodClientiDisponibili(this.getClientiDisponibiliConto());

            /* il campo pasto è visibile solo se a mezza pensione */
            campo = this.getCampo(NOME_CAMPO_PASTO);
            campo.setVisibile(this.isMezzaPensione());

            super.sincronizza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Controlla se l'arrivo è a mezza pensione.
     * <p/>
     *
     * @return true se a mezza pensione
     */
    private boolean isMezzaPensione() {
        /* variabili e costanti locali di lavoro */
        boolean mezzaPens = false;
        int codPens;

        try {    // prova ad eseguire il codice
            codPens = this.getCodPensione();
            mezzaPens = (codPens == Listino.PensioniPeriodo.mezzaPensione.getCodice());
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return mezzaPens;
    }


    /**
     * Ritorna il codice della prenotazione.
     * <p/>
     *
     * @return il codice della prenotazione
     */
    public int getCodPrenotazione() {
        /* variabili e costanti locali di lavoro */
        int codPrenotazione = 0;
        int codPeriodo = 0;
        Modulo mod;

        try {    // prova ad eseguire il codice
            mod = PeriodoModulo.get();
            codPeriodo = this.getCodPeriodo();
            codPrenotazione = mod.query().valoreInt(Periodo.Cam.prenotazione.get(), codPeriodo);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codPrenotazione;
    }


    /**
     * Ritorna la data di arrivo prevista nel Periodo.
     * <p/>
     *
     * @return il codice della prenotazione
     */
    private Date getDataArrivoPrevista() {
        /* variabili e costanti locali di lavoro */
        Date data = null;
        int codPeriodo;
        Modulo mod;

        try {    // prova ad eseguire il codice
            mod = PeriodoModulo.get();
            codPeriodo = this.getCodPeriodo();
            data = mod.query().valoreData(Periodo.Cam.arrivoPrevisto.get(), codPeriodo);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    /**
     * Ritorna la data di arrivo.
     * <p/>
     *
     * @return data di arrivo
     */
    public Date getDataArrivo() {
        return this.getData(NOME_CAMPO_DATA_ARRIVO);
    }


    /**
     * Assegna la data di arrivo.
     * <p/>
     *
     * @param data di arrivo
     */
    private void setDataArrivo(Date data) {
        this.setValore(NOME_CAMPO_DATA_ARRIVO, data);
    }


    /**
     * Ritorna il codice del cliente intestatario della prenotazione.
     * <p/>
     *
     * @return il codice del cliente
     */
    public int getCodCliente() {
        return this.getInt(NOME_CAMPO_CLIENTE);
    }


    /**
     * Assegna il codice cliente.
     * <p/>
     *
     * @param codCliente codice del cliente
     */
    private void setCodCliente(int codCliente) {
        this.setValore(NOME_CAMPO_CLIENTE, codCliente);
    }


    /**
     * Ritorna il codice della camera.
     * <p/>
     *
     * @return codice camera
     */
    public int getCodCamera() {
        return this.getInt(NOME_CAMPO_CAMERA);
    }


    /**
     * Assegna il codice camera.
     * <p/>
     *
     * @param codCamera codice della camera
     */
    private void setCodCamera(int codCamera) {
        this.setValore(NOME_CAMPO_CAMERA, codCamera);
    }


    /**
     * Ritorna il codice azienda.
     * <p/>
     *
     * @return codice azienda
     */
    public int getCodAzienda() {
        return this.getInt(NOME_CAMPO_AZIENDA);
    }


    /**
     * Assegna il codice azienda.
     * <p/>
     *
     * @param codAzienda codice della camera
     */
    private void setCodAzienda(int codAzienda) {
        this.setValore(NOME_CAMPO_AZIENDA, codAzienda);
    }


    /**
     * Recupera il numero di adulti previsti.
     * <p/>
     *
     * @return il numero di adulti previsti
     */
    private int getAdultiPrevisti() {
        return this.getInt(NOME_CAMPO_ADULTI);
    }


    /**
     * Assegna il numero di adulti previsti.
     * <p/>
     *
     * @param persone numero di adulti previsti
     */
    private void setAdultiPrevisti(int persone) {
        this.setValore(NOME_CAMPO_ADULTI, persone);
    }


    /**
     * Recupera il numero di bambini previsti.
     * <p/>
     *
     * @return il numero di bambini previsti
     */
    private int getBambiniPrevisti() {
        return this.getInt(NOME_CAMPO_BAMBINI);
    }


    /**
     * Assegna il numero di bambini previsti.
     * <p/>
     *
     * @param persone numero di bambini previsti
     */
    private void setBambiniPrevisti(int persone) {
        this.setValore(NOME_CAMPO_BAMBINI, persone);
    }


    /**
     * Recupera il numero di persone previste.
     * <p/>
     *
     * @return il numero di persone previste
     */
    private int getPersonePreviste() {
        return this.getInt(NOME_CAMPO_PERSONE);
    }


    /**
     * Assegna il numero di persone previste.
     * <p/>
     *
     * @param persone numero di persone previste
     */
    private void setPersonePreviste(int persone) {
        this.setValore(NOME_CAMPO_PERSONE, persone);
    }


    /**
     * Recupera il numero di adulti effettivamente in arrivo.
     * <p/>
     *
     * @return il numero di adulti effettivamente in arrivo
     */
    private int getAdultiEffettivi() {
        return this.getAdultiSelezionati().size();
    }


    /**
     * Recupera il numero di bambini effettivamente in arrivo.
     * <p/>
     *
     * @return il numero di bambini effettivamente in arrivo
     */
    private int getBambiniEffettivi() {
        return this.getBambiniSelezionati().size();
    }


    /**
     * Ritorna l'importo della caparra.
     * <p/>
     *
     * @return l'importo della caparra
     */
    public double getCaparra() {
        return this.getDouble(NOME_CAMPO_CAPARRA);
    }


    /**
     * Assegna l'importo della caparra.
     * <p/>
     *
     * @param caparra numero di persone
     */
    private void setCaparra(double caparra) {
        this.setValore(NOME_CAMPO_CAPARRA, caparra);
    }


    /**
     * Ritorna il codice del mezzo di pagamento della caparra.
     * <p/>
     *
     * @return il codice del mezzo di pagamento della caparra
     */
    public int getMezzoCaparra() {
        return this.getInt(NOME_CAMPO_MEZZO_CAPARRA);
    }


    /**
     * Assegna il codice del mezzo di pagamento della caparra.
     * <p/>
     *
     * @param codice del mezzo di pagamento della caparra
     */
    private void setMezzoCaparra(int codice) {
        this.setValore(NOME_CAMPO_MEZZO_CAPARRA, codice);
    }


    /**
     * Ritorna il numero della ricevuta della caparra.
     * <p/>
     *
     * @return il numero della ricevuta della caparra
     */
    public int getRicevutaCaparra() {
        return this.getInt(NOME_CAMPO_RICEVUTA_CAPARRA);
    }


    /**
     * Assegna il numero della ricevuta della caparra.
     * <p/>
     *
     * @param numero della ricevuta della caparra
     */
    private void setRicevutaCaparra(int numero) {
        this.setValore(NOME_CAMPO_RICEVUTA_CAPARRA, numero);
    }


    /**
     * Ritorna la data di pagamento della caparra.
     * <p/>
     *
     * @return la data di pagamento della caparra
     */
    public Date getDataCaparra() {
        return this.getData(NOME_CAMPO_DATA_CAPARRA);
    }


    /**
     * Assegna la data di pagamento della caparra.
     * <p/>
     *
     * @param data di pagamento della caparra
     */
    private void setDataCaparra(Date data) {
        this.setValore(NOME_CAMPO_DATA_CAPARRA, data);
    }


    /**
     * Ritorna la data partenza prevista.
     * <p/>
     *
     * @return data partenza prevista
     */
    public Date getDataPartenza() {
        return this.getData(NOME_CAMPO_DATA_PARTENZA);
    }


    /**
     * Assegna la data di partenza prevista.
     * <p/>
     *
     * @param data di partenza prevista
     */
    private void setDataPartenza(Date data) {
        this.setValore(NOME_CAMPO_DATA_PARTENZA, data);
    }


    /**
     * Ritorna l'arrivo con.
     * <p/>
     *
     * @return l'arrivo con
     */
    public int getArrivoCon() {
        return this.getInt(NOME_CAMPO_ARRIVO_CON);
    }


    /**
     * Assegna il tipo di arrivo con.
     * <p/>
     *
     * @param codArrivo codice di arrivo con
     */
    private void setArrivoCon(int codArrivo) {
        this.setValore(NOME_CAMPO_ARRIVO_CON, codArrivo);
    }


    /**
     * Ritorna il tipo di pensione.
     * <p/>
     *
     * @return il tipo di pensione
     */
    public int getCodPensione() {
        return this.getInt(NOME_CAMPO_PENSIONE);
    }


    /**
     * Assegna il tipo di pensione.
     * <p/>
     *
     * @param codTipo codice del tipo di pensione
     */
    private void setPensione(int codTipo) {
        this.setValore(NOME_CAMPO_PENSIONE, codTipo);
    }


    /**
     * Ritorna il tipo di pasto.
     * <p/>
     *
     * @return il tipo di pasto
     */
    public int getCodPasto() {
        return this.getInt(NOME_CAMPO_PASTO);
    }


    /**
     * Assegna il tipo di pasto.
     * <p/>
     *
     * @param codTipo codice del tipo di pasto
     */
    private void setPasto(int codTipo) {
        this.setValore(NOME_CAMPO_PASTO, codTipo);
    }


    public int getCodPeriodo() {
        return codPeriodo;
    }


    private void setCodPeriodo(int codPeriodo) {
        this.codPeriodo = codPeriodo;
    }


    public ModuloMemoria getMemoria() {
        return memoria;
    }


    private ModuloMemoria setMemoria(ModuloMemoria memoria) {
        this.memoria = memoria;
        return this.getMemoria();
    }


    private PanAddebiti getPanPensioni() {
        return panPensioni;
    }


    private void setPanPensioni(PanAddebiti panPensioni) {
        this.panPensioni = panPensioni;
    }


    public boolean isCaparraUtilizzata() {
        return caparraUtilizzata;
    }


    private void setCaparraUtilizzata(boolean caparraUtilizzata) {
        this.caparraUtilizzata = caparraUtilizzata;
    }


    private PanOpzioniConto getPanOpzioniConto() {
        return panOpzioniConto;
    }


    private void setPanOpzioniConto(PanOpzioniConto panOpzioniConto) {
        this.panOpzioniConto = panOpzioniConto;
    }


    /**
     * Restituisce una lista di informazioni per i conti da creare/modificare.
     * <p/>
     * Per ora contiene un solo elemento.
     *
     * @return una lista di wrapper per i conti da creare/modificare
     */
    public ArrayList<WrapConto> getWrapConti() {
        /* variabili e costanti locali di lavoro */
        ArrayList<WrapConto> conti = null;

        WrapConto wrapConto;
        Date data;
        int codCamera;
        int codCliente;
        int codAzienda;
        WrapAddebitiConto wAddebiti;

        try { // prova ad eseguire il codice

            conti = new ArrayList<WrapConto>();

            data = this.getDataArrivo();
            codCamera = this.getCodCamera();
            codCliente = this.getCodClienteConto();
            codAzienda = this.getCodAzienda();

            wrapConto = new WrapConto(data, codCamera, codCliente, codAzienda);

            wrapConto.setDataInizioValidita(data);
            wrapConto.setDataFineValidita(Lib.Data.add(this.getDataPartenza(), -1));
            wrapConto.setArrivoCon(this.getArrivoCon());
            wrapConto.setCaparra(this.getCaparra());
            wrapConto.setDataCaparra(this.getDataCaparra());
            wrapConto.setMezzoCaparra(this.getMezzoCaparra());
            wrapConto.setRicevutaCaparra(this.getRicevutaCaparra());
            wrapConto.setCodPeriodo(this.getCodPeriodo());

            /* crea il wrapper addebiti per il conto e lo aggiunge al wrapConto*/
            wAddebiti = this.creaWrapAddebitiConto();
            wrapConto.setWrapAddebiti(wAddebiti);

            /* se usa conto esistente, inserisce il codice del conto nel wrapper
             * per modificare un conto esistente invece che crearne uno nuovo */
            if (!this.isCreaConto()) {
                wrapConto.setCodConto(this.getCodConto());
            }// fine del blocco if

            conti.add(wrapConto);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return conti;
    }


    /**
     * Crea un WrapAddebiti per il conto in base a quanto impostato nel pannello.
     * <p/>
     *
     * @return il wrapper contenente i dati
     */
    private WrapAddebitiConto creaWrapAddebitiConto() {
        /* variabili e costanti locali di lavoro */
        WrapAddebitiConto wrapper = null;
        boolean continua;
        PanAddebiti panPens;

        try {    // prova ad eseguire il codice
            panPens = this.getPanPensioni();
            continua = panPens != null;

            if (continua) {
                wrapper = panPens.creaWrapAddebitiConto();

                /**
                 * rimuove il riferimento alla riga di origine che si riferisce
                 * agli addebiti del periodo e non è più valido
                 */
                wrapper.cleanOrigine();

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return wrapper;
    }


    /**
     * Ritorna true se una riga è spuntata col check di arrivo.
     * <p/>
     *
     * @param table la jtable
     * @param riga da esaminare
     *
     * @return true se spuntata
     */
    static boolean isRigaSpuntata(JTable table, int riga) {
        /* variabili e costanti locali di lavoro */
        boolean spuntata = false;
        boolean continua = true;
        Tavola tavola = null;
        TavolaModello modelloDati;
        Dati dati;

        try {    // prova ad eseguire il codice

            if (table == null) {
                continua = false;
            }// fine del blocco if

            if (continua) {
                if (table instanceof Tavola) {
                    tavola = (Tavola)table;
                } else {
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            if (continua) {
                modelloDati = tavola.getModello();
                dati = modelloDati.getDati();
                spuntata = dati.getBoolAt(riga, ConfermaArrivoDialogo.Nomi.check.get());
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return spuntata;
    }


    /**
     * Ritorna un bottone.
     * <p/>
     *
     * @return bottone da visualizzare
     */
    static JButton getBottoneLista() {
        /* variabili e costanti locali di lavoro */
        JButton bottone = null;

        try {    // prova ad eseguire il codice

            bottone = new JButton();
            bottone.setOpaque(true);
            bottone.setMargin(new Insets(0, 0, 0, 0));
            bottone.setBorder(null);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return bottone;
    }


    /**
     * Azione navigatore clienti modificato
     */
    private class AzNavClientiModificato extends NavStatoAz {

        /**
         * navStatoAz, da NavStatoLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void navStatoAz(NavStatoEve unEvento) {
            int a = 87;
        }

    } // fine della classe interna


    /**
     * Azione vai alla prenotazione
     */
    private class AzVaiPrenotazione implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            vaiPrenotazione();
            //To change body of implemented methods use File | Settings | File Templates.
        }
    } // fine della classe interna


    /**
     * Classe 'interna'.
     * <p/>
     * Pannello clienti in arrivo
     */
    private final class PanClienti extends PannelloFlusso {

        /**
         * Costruttore completo senza parametri.<br>
         */
        public PanClienti() {
            /* rimanda al costruttore della superclasse */
            super(Layout.ORIENTAMENTO_VERTICALE);

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
            JLabel label;
            Navigatore nav;

            try { // prova ad eseguire il codice

                this.setUsaGapFisso(true);
                this.setGapPreferito(2);

                label = new JLabel("Seleziona le persone arrivate");
                TestoAlgos.setEtichetta(label);
                nav = getNavClienti();
                this.add(label);
                this.add(nav.getPortaleNavigatore());

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }
    } // fine della classe 'interna'


    /**
     * Azione pannello opzioni conto modificato
     */
    private class AzPanOpzioniModificato extends PanModificatoAz {

        public void panModificatoAz(PanModificatoEve unEvento) {
            sincronizza();
        }

    } // fine della classe interna


    /**
     * Nomi dei campi del modulo memoria.
     */
    public enum Nomi {

        check("ok"),
        codCliente("cod"),
        cliente("cliente"),
        parentela("parentela"),
        bambino("bambino"),
        datiValidi("doc"),
        nuovoconto("nuovoconto"),
        linkcontocliente("linkcontocliente"),
        infoconto("conto"),;

        /**
         * titolo da utilizzare
         */
        private String titolo;


        /**
         * Costruttore completo con parametri.
         *
         * @param titolo utilizzato nei popup
         */
        Nomi(String titolo) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setTitolo(titolo);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public String get() {
            return titolo;
        }


        private void setTitolo(String titolo) {
            this.titolo = titolo;
        }

    }// fine della classe

}// fine della classe