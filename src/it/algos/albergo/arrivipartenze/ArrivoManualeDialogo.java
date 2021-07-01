package it.algos.albergo.arrivipartenze;

import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.clientealbergo.MonitorDatiCliente;
import it.algos.albergo.conto.WrapConto;
import it.algos.albergo.listino.Listino;
import it.algos.albergo.pensioni.PanAddebiti;
import it.algos.albergo.pensioni.WrapAddebitiConto;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.prenotazione.Prenotazione.Cam;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.albergo.stampeobbligatorie.StampeObbLogica;
import it.algos.albergo.tabelle.azienda.AziendaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;

import java.util.ArrayList;
import java.util.Date;

/**
 * Dialogo di conferma di un arrivo aggregato.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 01-giu-2008 ore 19.21.46
 */
public class ArrivoManualeDialogo extends MovimentoManualeDialogo {

    private static final String NOME_CAMPO_CLIENTE = Prenotazione.Cam.cliente.get();

    private static final String NOME_CAMPO_BAMBINO = Presenza.Cam.bambino.get();

    private static final String NOME_CAMPO_ARRIVO_CON = Presenza.Cam.arrivoCon.get();

    private static final String NOME_CAMPO_PENSIONE = Presenza.Cam.pensione.get();

    private static final String NOME_CAMPO_PASTO = Presenza.Cam.pasto.get();

    private static final String NOME_CAMPO_AZIENDA = Presenza.Cam.azienda.get();

    private static final String NOME_CAMPO_OPZ_CONTO = "opzioni conto";

    private static final String NOME_CAMPO_PART_PREVISTA = "partenza prevista";


    /* pannello switch contenente il Pannello Addebiti per editing del conto */
    private Pannello panEditConto;

    /**
     * pannello switch contenente alternativamente il campo popup di selezione
     * di un conto esistente o il campo data partenza prevista
     */
    private Pannello panSwapConto;

    /**
     * Pannello addebiti vuoto per impostazione nuovo conto
     */
    private PanAddebiti panAddebitiNuovoConto;

    /**
     * Pannello addebiti correntemente attivo
     */
    private PanAddebiti panAddebitiCorrente;

    /**
     * Oggetto grafico di monitoraggio dati cliente
     */
    private MonitorDatiCliente monitorCliente;


    /**
     * Costruttore completo senza parametri.
     * <p/>
     */
    public ArrivoManualeDialogo() {
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
     * Regolazioni iniziali di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal
     * costruttore (init) <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Pannello pan;
        Connessione conn;
        WrapAddebitiConto wrapper;
        Date dataIniConto;
        Date dataEndConto;
        PanAddebiti panAddebiti;

        try { // prova ad eseguire il codice

            this.setTitolo("Arrivo manuale");

            /* etichetta del campo data movimento */
            this.setLabelCamData("Data di arrivo");

            /* crea il pannello switch contenitore del pannello edit conto */
            pan = PannelloFactory.verticale(this);
            this.setPanEditConto(pan);

            /**
             * crea il pannello switch contenitore del popup conto esistente o
             * della data di fine validità nuovo conto
             */
            pan = PannelloFactory.orizzontale(this);
            this.setPanSwapConto(pan);

            /**
             * crea un pannello addebiti vuoto da usare in caso di nuovo conto
             */
            conn = Progetto.getConnessione();
            dataIniConto = this.getDataMovimento();
            dataEndConto = dataIniConto;
            wrapper = new WrapAddebitiConto(dataIniConto, dataEndConto, 1, 0, conn);
            panAddebiti = new PanAddebiti(wrapper);
            this.setPanAddebitiNuovoConto(panAddebiti);

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
        /* variabili e costanti locali di lavoro */
        Campo campo;
        Pannello pan;
        Pannello panSx;
        Pannello panTot;
        MonitorDatiCliente monitor;

        try { // prova ad eseguire il codice

            /* regola il valore iniziale del campo Opzioni Conto */
            this.setValore(NOME_CAMPO_OPZ_CONTO, 1);

            /* pannello di sinistra con i campi */
            panSx = PannelloFactory.verticale(this);
            panSx.setGapMassimo(20);

            /* pannello completo da aggiungere al dialogo */
            panTot = PannelloFactory.orizzontale(this);
            panTot.add(panSx);
            panTot.add(this.getPanEditConto());

            /* aggiunge graficamente i campi */
            campo = this.getCampo(NOME_CAMPO_DATA);
            panSx.add(campo);

            campo = this.getCampo(NOME_CAMPO_CLIENTE);
            panSx.add(campo);

            /* monitor dati cliente */
            monitor = new MonitorDatiCliente(MonitorDatiCliente.TipoMonitor.tutto);
            this.setMonitorCliente(monitor);
            panSx.add(monitor);

            campo = this.getCampo(NOME_CAMPO_BAMBINO);
            panSx.add(campo);

            campo = this.getCampo(NOME_CAMPO_CAMERA);
            panSx.add(campo);

            campo = this.getCampo(NOME_CAMPO_OPZ_CONTO);
            panSx.add(campo);

            pan = PannelloFactory.orizzontale(this);
            pan.add(this.getPanSwapConto());
            campo = this.getCampo(NOME_CAMPO_ARRIVO_CON);
            pan.add(campo);
            panSx.add(pan);

            pan = PannelloFactory.orizzontale(this);
            campo = this.getCampo(NOME_CAMPO_PENSIONE);
            pan.add(campo);
            campo = this.getCampo(NOME_CAMPO_PASTO);
            pan.add(campo);
            campo = this.getCampo(NOME_CAMPO_AZIENDA);
            
            // GESTIONE MULTIAZIENDA DISABLED 10-2017 ALEX
            campo.setValore(1);
            campo.setVisibile(false);
            // END DISABLED

            pan.add(campo);
            panSx.add(pan);

            this.addPannello(panTot);

            /* aggiorna il campo combo delle camere disponibili */
            this.updateComboCamere();

            super.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */


    public void avvia() {
        try { // prova ad eseguire il codice
            this.syncPanConto();
            this.syncPanSwapConto();
            super.avvia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo avvia


    /**
     * Creazione dei campi.
     * </p>
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice

            /* crea i campi nella superclasse */
            super.creaCampi();

            /* link al cliente */
            campo = PrenotazioneModulo.get().getCloneCampo(NOME_CAMPO_CLIENTE);
            campo.setFiltroBase(this.getFiltroClienti());
            campo.setLarScheda(180);
            campo.decora().obbligatorio();
            campo.decora().etichetta("cliente in arrivo");
            campo.decora().legenda("clienti non già presenti in albergo");
            this.addCampoCollezione(campo);

            /* check bambino */
            campo = PresenzaModulo.get().getCloneCampo(NOME_CAMPO_BAMBINO);
            this.addCampoCollezione(campo);

            /* tipo di arrivo */
            campo = PresenzaModulo.get().getCloneCampo(NOME_CAMPO_ARRIVO_CON);
            campo.decora().obbligatorio();
            this.addCampoCollezione(campo);

            /* campo tipo di pensione */
            campo = PresenzaModulo.get().getCloneCampo(NOME_CAMPO_PENSIONE);
            campo.decora().obbligatorio();
            this.addCampoCollezione(campo);

            /* campo tipo di pasto */
            campo = PresenzaModulo.get().getCloneCampo(NOME_CAMPO_PASTO);
            this.addCampoCollezione(campo);


            /* campo azienda */
            campo = PresenzaModulo.get().getCloneCampo(NOME_CAMPO_AZIENDA);
            campo.decora().obbligatorio();
            this.addCampoCollezione(campo);
            
            /* campo opzioni conto */
            campo = CampoFactory.radioInterno(NOME_CAMPO_OPZ_CONTO);
            campo.setValoriInterni("Associa a conto esistente,Apri nuovo conto");
            this.addCampoCollezione(campo);

            /* campo data partenza prevista */
            campo = CampoFactory.data(NOME_CAMPO_PART_PREVISTA);
            campo.decora().obbligatorio();
            campo.decora().legenda("per validità conto");
            this.addCampoCollezione(campo);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Sincronizzazione della scheda/dialogo.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override
    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice

            /* superclasse */
            super.sincronizza();

            /* il campo pasto è visibile solo se a mezza pensione */
            campo = this.getCampo(NOME_CAMPO_PASTO);
            campo.setVisibile(this.isMezzaPensione());

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
     * Filtro per i clienti non attualmente presenti in albergo.
     * </p>
     *
     * @return il filtro clienti non presenti
     */
    private Filtro getFiltroClienti() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroCli;
        boolean continua;
        int[] clientiPresenti;

        try { // prova ad eseguire il codice
            clientiPresenti = PresenzaModulo.getClientiPresenti();
            continua = (clientiPresenti != null) && (clientiPresenti.length > 0);

            if (continua) {
                filtro = new Filtro();
                for (int cod : clientiPresenti) {
                    filtroCli = FiltroFactory.codice(ClienteAlbergoModulo.get(), cod);
                    filtro.add(Filtro.Op.OR, filtroCli);
                } // fine del ciclo for-each
                filtro.setInverso(true);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Cliente selezionato.
     * <p/>
     *
     * @return il codice del cliente selezionato
     */
    public int getCodCliente() {
        return this.getInt(NOME_CAMPO_CLIENTE);
    }


    /**
     * Arrivo Con.
     * <p/>
     *
     * @return il codice di Arrivo Con
     */
    public int getArrivoCon() {
        return this.getInt(NOME_CAMPO_ARRIVO_CON);
    }


    /**
     * Check Bambino.
     * <p/>
     *
     * @return il flag Bambino
     */
    public boolean isBambino() {
        return this.getBool(NOME_CAMPO_BAMBINO);
    }


    /**
     * Tipo di pensione.
     * <p/>
     *
     * @return il codice del tipo di pensione
     */
    public int getCodPensione() {
        return this.getInt(NOME_CAMPO_PENSIONE);
    }


    /**
     * Tipo di pasto.
     * <p/>
     *
     * @return il codice del tipo di pasto
     */
    public int getCodPasto() {
        return this.getInt(NOME_CAMPO_PASTO);
    }


    /**
     * Azienda selezionata.
     * <p/>
     *
     * @return il codice della azienda selezionata
     */
    public int getCodAzienda() {
        return this.getInt(NOME_CAMPO_AZIENDA);
    }


    /**
     * Ritorna l'impostazione dell'opzione Nuovo Conto.
     * <p/>
     *
     * @return true se deve aprire nuovo conto, false se deve usare conto esistente
     */
    public boolean isNuovoConto() {
        /* variabili e costanti locali di lavoro */
        boolean nuovoConto = false;
        int opzione;

        try {    // prova ad eseguire il codice
            opzione = this.getInt(NOME_CAMPO_OPZ_CONTO);
            nuovoConto = (opzione == 2);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nuovoConto;
    }


    /**
     * Ritorna la data di partenza prevista.
     * <p/>
     *
     * @return la data di partenza prevista
     */
    private Date getDataPartenzaPrevista() {
        return this.getData(NOME_CAMPO_PART_PREVISTA);
    }


    /**
     * Ritorna la data di fine validità del nuovo conto.
     * <p/>
     *
     * @return la data di fine validità
     */
    public Date getDataFineValiditaNuovoConto() {
        /* variabili e costanti locali di lavoro */
        Date fineValidita = Lib.Data.getVuota();
        Date partPrevista;

        try { // prova ad eseguire il codice
            partPrevista = getDataPartenzaPrevista();
            if (!Lib.Data.isVuota(partPrevista)) {
                fineValidita = Lib.Data.add(partPrevista, -1);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return fineValidita;
    }


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @param campo cambiato
     */
    protected void eventoMemoriaModificata(Campo campo) {
        /* variabili e costanti locali di lavoro */
        int codAzienda;
        int codConto;
        int codPensione;
        int codPasto;

        try { // prova ad eseguire il codice

            super.eventoMemoriaModificata(campo);

            /**
             * se modifico il cliente regolo il flag bambino
             */
            if (campo.equals(this.getCampo(NOME_CAMPO_CLIENTE))) {
                this.syncBambino();
            }// fine del blocco if

            /**
             * se modifico il cliente aggiorno il monitor di stato
             */
            if (campo.equals(this.getCampo(NOME_CAMPO_CLIENTE))) {
                this.updateMonitorStatoCliente();
            }// fine del blocco if

            /**
             * se modifico la data di arrivo regolo il flag bambino
             * e ricarico la lista delle camere
             */
            if (campo.equals(this.getCampo(NOME_CAMPO_DATA))) {
                this.syncBambino();
            }// fine del blocco if

            /**
             * se modifico la camera regolo di default alcuni valori
             * in base alla impostazione dei clienti che già stanno
             * occupando la camera (se ce ne sono)
             */
            if (campo.equals(this.getCampo(NOME_CAMPO_CAMERA))) {

                /* assegno l'azienda */
                codAzienda = this.getCodAziendaSuggerita();
                this.setValore(NOME_CAMPO_AZIENDA, codAzienda);

                /* assegno il conto */
                codConto = this.getCodContoSuggerito();
                this.setValore(NOME_CAMPO_CONTO, codConto);

                /* assegno il tipo di pensione */
                codPensione = this.getPensioneSuggerita();
                this.setValore(NOME_CAMPO_PENSIONE, codPensione);

                /* assegno il tipo di pasto */
                codPasto = this.getPastoSuggerito();
                this.setValore(NOME_CAMPO_PASTO, codPasto);

                /* sincronizzo la data partenza prevista per nuovo conto */
                this.syncDataPartenzaPrevista();

            }// fine del blocco if

            /**
             * se modifico il conto aggiorno il pannello conto
             */
            if (campo.equals(this.getCampo(NOME_CAMPO_CONTO))) {
                this.syncPanConto();
            }// fine del blocco if

            /**
             * se modifico il campo Opzioni Conto:
             * - aggiorno il pannello conto
             * - aggiorno il pannello swap conto
             * - se nuovo conto assegno la data proposta al campo partenza prevista
             */
            if (campo.equals(this.getCampo(NOME_CAMPO_OPZ_CONTO))) {
                this.syncPanConto();
                this.syncPanSwapConto();
                this.syncDataPartenzaPrevista();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
    }


    protected void eventoUscitaCampoModificato(Campo campo) {
        /* variabili e costanti locali di lavoro */
        PanAddebiti panAddebiti;
        Date data;

        try { // prova ad eseguire il codice

            super.eventoUscitaCampoModificato(campo);

            /**
             * se modifico la data di arrivo:
             * - regolo il flag bambino
             * - ricarico la lista delle camere
             * - aggiorno il monitor di stato dati cliente
             */
            if (campo.equals(this.getCampo(NOME_CAMPO_DATA))) {
                this.updateComboCamere();
                this.updateMonitorStatoCliente();
            }// fine del blocco if

            /**
             * se modifico la data di fine validità nuovo conto:
             * - aggiorno il pannello conto
             * - aggiorno il pannello swap conto
             */
            if (campo.equals(this.getCampo(NOME_CAMPO_PART_PREVISTA))) {
                data = this.getDataFineValiditaNuovoConto();
                panAddebiti = this.getPanAddebitiNuovoConto();
                panAddebiti.setDataFineGiorni(data);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    protected void eventoCampoPresentatoRecord(Campo campo) {

        try { // prova ad eseguire il codice

            /**
             * se ho presentato la scheda cliente controllo il flag bambino
             */
            if (campo.equals(this.getCampo(NOME_CAMPO_CLIENTE))) {
                this.syncBambino();
                this.updateMonitorStatoCliente();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Aggiorna il monitor di stato validità dati cliente.
     * <p/>
     */
    private void updateMonitorStatoCliente() {
        /* variabili e costanti locali di lavoro */
        Date dataArrivo;
        int codCliente;
        MonitorDatiCliente monitor;

        try {    // prova ad eseguire il codice
            dataArrivo = this.getDataMovimento();
            codCliente = this.getCodCliente();
            monitor = this.getMonitorCliente();
            monitor.avvia(codCliente, dataArrivo);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Sincronizza il flag Bambino in funzione del cliente
     * e della data di arrivo.
     * <p/>
     */
    private void syncBambino() {
        /* variabili e costanti locali di lavoro */
        int codCliente;
        Date dataArrivo;
        boolean bambino;

        try {    // prova ad eseguire il codice
            codCliente = this.getCodCliente();
            dataArrivo = this.getDataMovimento();
            bambino = ClienteAlbergoModulo.isBambino(codCliente, dataArrivo);
            this.setValore(NOME_CAMPO_BAMBINO, bambino);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Inserisce nel Pannello Edit Conto in Pannello Addebiti appropriato
     * in base alle impostazioni correnti del dialogo
     * <p/>
     */
    private void syncPanConto() {
        /* variabili e costanti locali di lavoro */
        Pannello panConto;
        int codConto;
        PanAddebiti panAddebiti;
        WrapAddebitiConto wrapper;
        Connessione conn;

        try {    // prova ad eseguire il codice

            panConto = this.getPanEditConto();
            panConto.removeAll();

            if (this.isNuovoConto()) {
                panAddebiti = this.getPanAddebitiNuovoConto();
            } else {
                codConto = this.getCodConto();
                if (codConto > 0) {
                    conn = Progetto.getConnessione();
                    wrapper = new WrapAddebitiConto(codConto, conn);
                    panAddebiti = new PanAddebiti(wrapper);
                } else {
                    panAddebiti = this.getPanAddebitiNuovoConto();
                }// fine del blocco if-else
            }// fine del blocco if-else

            panConto.add(panAddebiti);
            panConto.getPanFisso().repaint();

            /* registra come corrente il pannello addebiti appena inserito */
            this.setPanAddebitiCorrente(panAddebiti);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Assegna il contenuto al pannello swap conto
     * in base alle impostazioni correnti del dialogo.
     * <p/>
     */
    private void syncPanSwapConto() {
        /* variabili e costanti locali di lavoro */
        Pannello panSwap;

        try {    // prova ad eseguire il codice
            panSwap = this.getPanSwapConto();
            panSwap.removeAll();

            if (this.isNuovoConto()) {
                panSwap.add(this.getCampo(NOME_CAMPO_PART_PREVISTA));
            } else {
                panSwap.add(this.getCampo(NOME_CAMPO_CONTO));
            }// fine del blocco if-else
            panSwap.getPanFisso().repaint();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Sincronizza il valore del campo data partenza prevista
     * in base ai valori correnti del dialogo.
     * <p/>
     */
    private void syncDataPartenzaPrevista() {
        /* variabili e costanti locali di lavoro */
        int codPeriodo;
        Modulo modPeriodo;
        Campo campo;
        Date data;

        try {    // prova ad eseguire il codice

            data = Lib.Data.getVuota();
            campo = this.getCampo(NOME_CAMPO_PART_PREVISTA);

            if (this.isNuovoConto()) {
                codPeriodo = this.getCodPeriodoDestinazione();
                if (codPeriodo != 0) {
                    modPeriodo = PeriodoModulo.get();
                    data = modPeriodo.query().valoreData(
                            Periodo.Cam.partenzaPrevista.get(),
                            codPeriodo);
                    this.setValore(campo, data);
                    this.eventoUscitaCampoModificato(campo); // forzo aggiornamento
                }// fine del blocco if
            }// fine del blocco if

            this.setValore(campo, data);
            this.eventoUscitaCampoModificato(campo); // forzo aggiornamento

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna il codice conto suggerito.
     * <p/>
     * E' il codice conto della prima presenza nella camera
     * Se la camera è vuota è zero
     *
     * @return il codice del conto suggerito
     */
    private int getCodContoSuggerito() {
        /* variabili e costanti locali di lavoro */
        int codConto = 0;
        int codPresenza;
        Modulo modPres;

        try { // prova ad eseguire il codice

            /* recupera il codice della prima presenza (non provvisoria) nella camera selezionata */
            codPresenza = this.getCodPrimaPresenza();

            /**
             * se c'è una presenza recupera il conto dalla presenza
             */
            if (codPresenza > 0) {
                modPres = PresenzaModulo.get();
                codConto = modPres.query().valoreInt(Presenza.Cam.conto.get(), codPresenza);
            }// fine del blocco


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codConto;
    }


    /**
     * Ritorna il codice pensione suggerita.
     * <p/>
     * Controlla tutti i presenti nella camera
     * Se hanno tutti lo stesso tipo di pensione, suggerisce quello
     * Se la camera è vuota è zero
     *
     * @return il codice tipo di pensione suggerita
     */
    private int getPensioneSuggerita() {
        /* variabili e costanti locali di lavoro */
        int codPensione = 0;
        int codCamera;
        int codPres;
        int codPens;
        int firstCod = 0;
        boolean omogenei = false;
        int[] presenze;
        Modulo modPres;

        try { // prova ad eseguire il codice

            modPres = PresenzaModulo.get();
            codCamera = this.getCodCamera();
            presenze = PresenzaModulo.getPresenzeAperte(codCamera);
            for (int k = 0; k < presenze.length; k++) {
                codPres = presenze[k];
                codPens = modPres.query().valoreInt(Presenza.Cam.pensione.get(), codPres);
                if (k == 0) {
                    firstCod = codPens;
                    omogenei = true;
                }// fine del blocco if
                if (codPens != firstCod) {
                    omogenei = false;
                    break;
                }// fine del blocco if
            } // fine del ciclo for

            if (omogenei) {
                codPensione = firstCod;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codPensione;
    }


    /**
     * Ritorna il codice pasto suggerito.
     * <p/>
     * Controlla tutti i presenti nella camera
     * Se hanno tutti lo stesso tipo di pasto, suggerisce quello
     * Se la camera è vuota è zero
     *
     * @return il codice del tipo di pasto suggerito
     */
    private int getPastoSuggerito() {
        /* variabili e costanti locali di lavoro */
        int codPasto = 0;
        int codCamera;
        int codPres;
        int unCodPasto;
        int firstCod = 0;
        boolean omogenei = false;
        int[] presenze;
        Modulo modPres;

        try { // prova ad eseguire il codice

            modPres = PresenzaModulo.get();
            codCamera = this.getCodCamera();
            presenze = PresenzaModulo.getPresenzeAperte(codCamera);
            for (int k = 0; k < presenze.length; k++) {
                codPres = presenze[k];
                unCodPasto = modPres.query().valoreInt(Presenza.Cam.pasto.get(), codPres);
                if (k == 0) {
                    firstCod = unCodPasto;
                    omogenei = true;
                }// fine del blocco if
                if (unCodPasto != firstCod) {
                    omogenei = false;
                    break;
                }// fine del blocco if
            } // fine del ciclo for

            if (omogenei) {
                codPasto = firstCod;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codPasto;
    }


    /**
     * Ritorna il codice azienda suggerito.
     * <p/>
     * E' il codice azienda della prima presenza nella camera
     * Se la camera è vuota è l'azienda di default
     *
     * @return il codice dell'azienda suggerita
     */
    private int getCodAziendaSuggerita() {
        /* variabili e costanti locali di lavoro */
        int codAzienda = 0;
        int codPresenza;
        Modulo modPres;

        try { // prova ad eseguire il codice

            /* recupera il codice della prima presenza nella camera selezionata */
            codPresenza = this.getCodPrimaPresenza();

            /**
             * se c'è una presenza recupera l'azienda dalla presenza
             * altrimenti recupera l'azienda principale
             */
            modPres = PresenzaModulo.get();
            if (codPresenza > 0) {
                codAzienda = modPres.query().valoreInt(Presenza.Cam.azienda.get(), codPresenza);
            } else {
                codAzienda = AziendaModulo.get().getRecordPreferito();
            }// fine del blocco if-else


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codAzienda;
    }


    /**
     * Recupera il codice della prima presenza aperta e non provvisoria associata
     * alla camera selezionata
     * <p/>
     *
     * @return il codice della prima presenza, 0 se non ce ne sono
     */
    private int getCodPrimaPresenza() {
        /* variabili e costanti locali di lavoro */
        int codPresenza = 0;
        Modulo modPres;
        int codCamera;
        Filtro filtro;
        Filtro filtroCamera;
        Filtro filtroAperto;
        Filtro filtroDefinitive;

        try {    // prova ad eseguire il codice
            modPres = PresenzaModulo.get();
            codCamera = this.getCodCamera();
            filtro = new Filtro();
            filtroCamera = FiltroFactory.crea(Presenza.Cam.camera.get(), codCamera);
            filtroAperto = FiltroFactory.creaFalso(Presenza.Cam.chiuso.get());
            filtroDefinitive = FiltroFactory.creaFalso(Presenza.Cam.provvisoria.get());
            filtro.add(filtroCamera);
            filtro.add(filtroAperto);
            filtro.add(filtroDefinitive);
            codPresenza = modPres.query().valoreChiave(filtro);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codPresenza;
    }


    /**
     * Ritorna il wrapper addebiti correntemente attivo.
     * <p/>
     *
     * @return il wrapper addebiti
     */
    private WrapAddebitiConto getWrapAddebiti() {
        /* variabili e costanti locali di lavoro */
        WrapAddebitiConto wrapper = null;
        PanAddebiti panAddebiti;

        try {    // prova ad eseguire il codice
            panAddebiti = this.getPanAddebitiCorrente();
            wrapper = panAddebiti.creaWrapAddebitiConto();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return wrapper;
    }


    /**
     * Restituisce una lista di informazioni per il conto da creare/modificare.
     * <p/>
     *
     * @return il wrapper del conto
     */
    public WrapConto getWrapConto() {
        /* variabili e costanti locali di lavoro */
        WrapConto wrapConto = null;
        Date data;
        int codCamera;
        int codCliente;
        int codAzienda;
        WrapAddebitiConto wAddebiti;

        try { // prova ad eseguire il codice

            /* crea il wrapper per il conto */
            data = this.getDataMovimento();
            codCamera = this.getCodCamera();
            codCliente = this.getCodCliente();
            codAzienda = this.getCodAzienda();
            wrapConto = new WrapConto(data, codCamera, codCliente, codAzienda);

            /* crea il wrapper addebiti per il conto e lo aggiunge al wrapConto */
            wAddebiti = this.getWrapAddebiti();
            wrapConto.setWrapAddebiti(wAddebiti);

            /**
             * - se deve creare un nuovo conto inserisce le informazioni relative al nuovo conto
             * - se usa conto esistente, inserisce il codice del conto nel wrapper
             * per modificare un conto esistente invece che crearne uno nuovo
             */
            if (this.isNuovoConto()) {
                wrapConto.setDataInizioValidita(data);
                wrapConto.setDataFineValidita(this.getDataFineValiditaNuovoConto());
//                wrapConto.setTipoArrivo(this.getTipoArrivo());
                wrapConto.setCodPeriodo(this.getCodPeriodoDestinazione());
            } else {
                wrapConto.setCodConto(this.getCodConto());
            }// fine del blocco if-else


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return wrapConto;
    }


    /**
     * Invocato quando si preme il bottone conferma.
     * <p/>
     * Effettua i controlli specifici
     * Prima quelli bloccanti poi quelli superabili
     */
    @Override
    public void eventoConferma() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Date oggi;
        int codCliente;
        int codPensione;
        int codPasto;
        boolean validi;
        MessaggioDialogo messaggio;
        boolean registrabile;
        Date dataArrivo;
        Date dataPartPrevista;
        int codAzienda;
        String testo;
        int quante;
        boolean valido;

        try { // prova ad eseguire il codice

            /**
             * Controlla che si possano ancora registrare arrivi
             * per la data e l'azienda in oggetto
             */
            if (continua) {
                dataArrivo = this.getDataMovimento();
                codAzienda = this.getCodAzienda();
                registrabile = StampeObbLogica.isArrivoRegistrabile(dataArrivo, codAzienda);
                if (!registrabile) {
                    testo = ArriviPartenzeLogica.getMessaggioArriviNonConfermabili(codAzienda);
                    new MessaggioAvviso(testo);
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /**
             * se nuovo conto, la data di partenza prevista deve
             * essere successiva alla data di arrivo
             */
            if (continua) {
                if (this.isNuovoConto()) {
                    dataArrivo = this.getDataMovimento();
                    dataPartPrevista = this.getDataPartenzaPrevista();
                    if (!Lib.Data.isPosteriore(dataArrivo, dataPartPrevista)) {
                        testo =
                                "La data di partenza prevista deve essere successiva alla data di arrivo.";
                        new MessaggioAvviso(testo);
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /**
             * controlla che il cliente in arrivo abbia dati validi
             * se non li ha chiede conferma
             * Essendo un arrivo singolo, ai fini del controllo assume che
             * la scheda di notifica venga stampata in qualità di Capogruppo Scheda.
             */
            if (continua) {
                codCliente = this.getCodCliente();
                dataArrivo = this.getDataMovimento();
                boolean capoScheda = true;
                valido = ClienteAlbergoModulo.isValidoArrivo(codCliente, dataArrivo, capoScheda);
                if (!valido) {
                    ArrayList<ClienteAlbergo.ErrDatiAnag> errori = ClienteAlbergoModulo.checkValidoArrivo(codCliente, dataArrivo, capoScheda);
                    testo = ClienteAlbergo.ErrDatiAnag.getTesto(errori);
                    continua = false;
                    messaggio = new MessaggioDialogo("Attenzione! I dati del cliente non sono validi.\n"+testo+"\nVuoi continuare?");
                    if (messaggio.isConfermato()) {
                        continua = true;
                    }// fine del blocco if
                }// fine del blocco if


//                        validi = ClienteAlbergoModulo.isDocValido(codCliente);
//                if (!validi) {
//                    continua = false;
//                    messaggio = new MessaggioDialogo(
//                            "Il cliente in arrivo non ha un documento valido!\nVuoi continuare?");
//                    if (messaggio.isConfermato()) {
//                        continua = true;
//                    }// fine del blocco if
//                }// fine del blocco if
            }// fine del blocco if

            /**
             * controlla che sia specificato un tipo di pensione
             * in caso contrario chiede conferma
             */
            if (continua) {
                codPensione = this.getCodPensione();
                if (codPensione <= 0) {
                    continua = false;
                    messaggio = new MessaggioDialogo(
                            "Il tipo di pensione non è specificato!\nVuoi continuare?");
                    if (messaggio.isConfermato()) {
                        continua = true;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /**
             * se mezza pensione controlla che sia specificato un tipo di pasto
             * in caso contrario chiede conferma
             */
            if (continua) {
                if (this.isMezzaPensione()) {
                    codPasto = this.getCodPasto();
                    if (codPasto <= 0) {
                        continua = false;
                        messaggio = new MessaggioDialogo(
                                "Il tipo di pasto non è specificato!\nVuoi continuare?");
                        if (messaggio.isConfermato()) {
                            continua = true;
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /**
             * Se nel pannello addebiti non ci sono addebiti, avvisa e chiede conferma
             */
            if (continua) {
                quante = getPanAddebitiCorrente().getNumRigheSelezionate();
                if (quante == 0) {
                    messaggio = new MessaggioDialogo(
                            "Non hai inserito addebiti nel il conto!\nVuoi continuare?");
                    continua = (messaggio.isConfermato());
                }// fine del blocco if
            }// fine del blocco if

            /* se ha passato i controlli rimanda alla superclasse */
            if (continua) {
                super.eventoConferma();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }



    private Pannello getPanEditConto() {
        return panEditConto;
    }


    private void setPanEditConto(Pannello panEditConto) {
        this.panEditConto = panEditConto;
    }


    private Pannello getPanSwapConto() {
        return panSwapConto;
    }


    private void setPanSwapConto(Pannello panSwapConto) {
        this.panSwapConto = panSwapConto;
    }


    private PanAddebiti getPanAddebitiNuovoConto() {
        return panAddebitiNuovoConto;
    }


    private void setPanAddebitiNuovoConto(PanAddebiti panAddebitiNuovoConto) {
        this.panAddebitiNuovoConto = panAddebitiNuovoConto;
    }


    private PanAddebiti getPanAddebitiCorrente() {
        return panAddebitiCorrente;
    }


    private void setPanAddebitiCorrente(PanAddebiti panAddebitiCorrente) {
        this.panAddebitiCorrente = panAddebitiCorrente;
    }


    private MonitorDatiCliente getMonitorCliente() {
        return monitorCliente;
    }


    private void setMonitorCliente(MonitorDatiCliente monitorCliente) {
        this.monitorCliente = monitorCliente;
    }
}// fine della classe