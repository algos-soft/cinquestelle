package it.algos.albergo.arrivipartenze;

import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.clientealbergo.MonitorDatiCliente;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.base.azione.AzSpecifica;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreL;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.scheda.Scheda;
import it.algos.base.wrapper.CampoValore;
import it.algos.gestione.anagrafica.Anagrafica;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;

public class NavClienti extends NavigatoreL {

    /* Dialogo proprietario */
    private ConfermaArrivoDialogo dialogo;

    /* Monitor dei dati del cliente */
    private MonitorDatiCliente monitor;


    /**
     * Costruttore completo con parametri.
     */
    public NavClienti() {
        this(null, null);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento
     * @param dialogo di riferimento
     */
    public NavClienti(Modulo modulo, ConfermaArrivoDialogo dialogo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        try { // prova ad eseguire il codice
            /* regola le variabili di istanza coi parametri */
            this.setDialogo(dialogo);

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice

            this.setUsaFinestra(false);
            this.setUsaNuovo(true);
            this.setUsaModifica(true);
            this.setUsaElimina(false);
            this.setUsaSelezione(false);
            this.setUsaRicerca(false);
            this.setUsaStampaLista(false);
            this.setRigheLista(5);
            this.getLista().setOrdinabile(false);
            this.setUsaTotaliLista(false);
            this.setUsaStatusBarLista(false);

//            this.getLista().setCampoOrdinamento(this.getModulo().getCampoChiave());
//            Ordine ord = new Ordine();
//            ord.add(this.getModulo().getCampoChiave());
//            this.getLista().setOrdine(ord);

//            vista = new Vista(this.getModulo());
//            vista.addCampo(ConfermaArrivoDialogo.Nomi.valido.get());
//            vista.addCampo(ConfermaArrivoDialogo.Nomi.cliente.get());
//               vista.inizializza();
//            this.setVista(vista);

            this.addAzione(new AzAddCliente());

            /* crea e registra un monitor dati cliente */
            MonitorDatiCliente monitor = new MonitorDatiCliente(
                    MonitorDatiCliente.Visibilita.soloQuandoInvalido,
                    MonitorDatiCliente.TipoMonitor.tutto);
            this.setMonitor(monitor);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Inizializzazione dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche.<br>
     * Viene chiamato dall'inizializzazione del Modulo.<br>
     */
    @Override
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        Ordine ord;

        try { // prova ad eseguire il codice
            super.inizializza();

            this.getLista().setCampoOrdinamento(this.getModulo().getCampoChiave());
            ord = new Ordine();

            ord.add(this.getModulo().getCampoChiave());

            this.getLista().setOrdine(ord);

            /* aggiunge il monitor dati cliente al Portale Navigatore */
            this.getPortaleNavigatore().add(this.getMonitor());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo inizializza


    /**
     * Override di cellaModificata per non lanciare l'evento Navigatore Modificato
     * dopo la modifica di una cella in lista
     *
     * @param campo il campo da registrare
     * @param codice codice chiave del record da registrare
     *
     * @return true se riuscito
     */
    protected boolean cellaModificata(Campo campo, int codice) {
        return true;
    }


    /**
     * Azione nuovo record in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    @Override
    protected int nuovoRecord() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int codice = 0;
        int codIntestatario = 0;
        int codCapo = 0;
        boolean capo;
        Modulo modCliente;
        boolean confermato;
        ArrayList<CampoValore> valori;
        CampoValore cv;
        Scheda scheda;
        Connessione conn;

        try { // prova ad eseguire il codice
            modCliente = ClienteAlbergoModulo.get();
            continua = (modCliente != null);

            /**
             * identifica il capogruppo
             * è l'intestatario della prenotazione se è un capogruppo,
             * altrimenti è il capogruppo dell'intestatario
             */
            if (continua) {
                codIntestatario = this.getDialogo().getCodCliente();
                capo = modCliente.query().valoreBool(
                        ClienteAlbergo.Cam.capogruppo.get(),
                        codIntestatario);
                if (capo) {
                    codCapo = codIntestatario;
                } else {
                    codCapo = modCliente.query().valoreInt(
                            ClienteAlbergo.Cam.linkCapo.get(),
                            codIntestatario);
                }// fine del blocco if-else
            }// fine del blocco if

            /* crea il nuovo membro */
            if (continua) {
                codice = ClienteAlbergoModulo.nuovoMembro(codCapo);
                continua = (codice > 0);
            }// fine del blocco if

            /* presenta il nuovo record in scheda e se annullato lo elimina */
            if (continua) {

                /* presenta come nuovo record */
                scheda = modCliente.getSchedaPop();
                conn = Progetto.getConnessione();
                confermato = modCliente.presentaRecord(codice, scheda, true, conn);

                if (confermato) {
                    this.getDialogo().addCliente(codice, true);
                    this.aggiornaLista();
                    this.fireModificato();
                } else {
                    modCliente.query().eliminaRecord(codice);
                }// fine del blocco if-else
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return 0;
    }


    /**
     * Azione modifica record in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sincronizza lo stato della GUI <br>
     * (la sincronizzazione avviene DOPO, ma viene lanciata da qui) <br>
     * Controlla e chiude una eventuale scheda aperta <br>
     * Chiede la chiave alla lista master <br>
     * Avvia la scheda, passandogli il codice-chiave <br>
     *
     * @return true se eseguito correttamente
     */
    @Override
    protected boolean modificaRecord() {
        /* variabili e costanti locali di lavoro */
        boolean confermato = false;
        boolean continua;
        int codMem = 0;
        int codCliente = 0;
        Modulo modMem = null;
        Modulo modCliente;

        try { // prova ad eseguire il codice
            modCliente = ClienteAlbergoModulo.get();
            continua = (modCliente != null);

            if (continua) {
                codMem = this.getLista().getChiaveSelezionata();
                continua = (codMem > 0);
            }// fine del blocco if

            if (continua) {
                modMem = this.getDialogo().getMemoria();
                continua = (modMem != null);
            }// fine del blocco if

            if (continua) {
                codCliente = modMem.query().valoreInt(
                        ConfermaArrivoDialogo.Nomi.codCliente.get(),
                        codMem);
                continua = (codCliente > 0);
            }// fine del blocco if

            if (continua) {
                confermato = modCliente.presentaRecord(codCliente);
                if (confermato) {
                    this.reLoadLista();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermato;
    }


    /**
     * Ricarica tutta la lista.
     * <p/>
     */
    private void reLoadLista() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo modMem;
        ArrayList<Integer> codClienti = new ArrayList<Integer>();
        ArrayList<Boolean> checks = new ArrayList<Boolean>();
        int cod;
        int codCliente;
        boolean check;
        ConfermaArrivoDialogo dialogo = null;

        try { // prova ad eseguire il codice
            modMem = this.getDialogo().getMemoria();
            continua = (modMem != null);

            if (continua) {
                dialogo = this.getDialogo();
                continua = (dialogo != null);
            }// fine del blocco if

            if (continua) {
                int quanteRighe = this.getLista().getNumRecordsVisualizzati();
                for (int k = 0; k < quanteRighe; k++) {
                    cod = this.getLista().getChiave(k);
                    codCliente =
                            modMem.query().valoreInt(
                                    ConfermaArrivoDialogo.Nomi.codCliente.get(),
                                    cod);
                    check = modMem.query().valoreBool(ConfermaArrivoDialogo.Nomi.check.get(), cod);
                    codClienti.add(codCliente);
                    checks.add(check);
                } // fine del ciclo for
            }// fine del blocco if

            if (continua) {
                modMem.query().eliminaRecords();

                for (int k = 0; k < codClienti.size(); k++) {
                    cod = codClienti.get(k);
                    check = checks.get(k);
                    dialogo.addCliente(cod, check);
                } // fine del ciclo for

                this.aggiornaLista();

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * aggiunge un'anagrafica già esistente.
     * <p/>
     */
    private void addCliente() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo mod;
        int codice = 0;
        ConfermaArrivoDialogo dialogo = null;
        Filtro filtro;
        int[] presenti;
        ArrayList<Integer> lista;
        int[] gruppo;

        try {    // prova ad eseguire il codice
            mod = ClienteAlbergoModulo.get();
            continua = (mod != null);

            if (continua) {

                /* toglie i presenti */
                presenti = PresenzaModulo.getClientiPresenti();
                filtro = new Filtro();
                for (int cod : presenti) {
                    filtro.add(FiltroFactory.crea(mod.getCampoChiave(), Filtro.Op.DIVERSO, cod));
                } // fine del ciclo for-each

                /* toglie quelli che sono già in lista */
                lista =
                        this.getModulo()
                                .query()
                                .valoriCampo(ConfermaArrivoDialogo.Nomi.codCliente.get());
                for (int cod : lista) {
                    filtro.add(FiltroFactory.crea(mod.getCampoChiave(), Filtro.Op.DIVERSO, cod));
                } // fine del ciclo for-each

                codice = mod.getModuloBase().selezionaRecord(
                        Anagrafica.Cam.soggetto.get(),
                        filtro,
                        "",
                        null,
                        false,
                        null,
                        ClienteAlbergo.Cam.parentela.get(),
                        ClienteAlbergo.Cam.capogruppo.get());
                continua = (codice > 0);
            }// fine del blocco if

            if (continua) {
                dialogo = getDialogo();
                continua = (dialogo != null);
            }// fine del blocco if

            if (continua) {
                dialogo.addCliente(codice, true);
                this.aggiornaLista();
                this.fireModificato();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Sincronizza il Navigatore.
     * <p/>
     * Controlla lo stato di tutti i suoi componenti <br>
     * Elabora la sua business logic <br>
     * Regola la GUI di conseguenza <br>
     */
    @Override
    public void sincronizza() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            super.sincronizza();
            this.updateMonitorCliente();
            this.getDialogo().sincronizza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Aggiorna il monitor del cliente selezionato.
     * <p/>
     */
    private void updateMonitorCliente() {
        /* variabili e costanti locali di lavoro */
        Date data=null;
        int chiaveRiga;
        boolean spuntato;
        int codCliente = 0;
        boolean capo = false;

        try {    // prova ad eseguire il codice

            /**
             * controlla se la riga selezionata è spuntata
             * (se non lo è non deve controllare il cliente)
             */
            chiaveRiga = this.getLista().getChiaveSelezionata();
            spuntato = this.getModulo()
                    .query()
                    .valoreBool(ConfermaArrivoDialogo.Nomi.check.get(), chiaveRiga);


            if (spuntato) {

                /* recupera il codice del cliente */
                codCliente = this.getModulo()
                        .query()
                        .valoreInt(ConfermaArrivoDialogo.Nomi.codCliente.get(), chiaveRiga);

                /* controlla se è il Capogruppo della Scheda di Notifica */
                capo = this.getDialogo().isCapoNotifica(codCliente);

                /* recupera la data di arrivo */
                data = this.getDialogo().getDataArrivo();

            }// fine del blocco if

            /* avvia il monitor con i dati */
            this.getMonitor().avvia(codCliente, data, capo);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    private ConfermaArrivoDialogo getDialogo() {
        return dialogo;
    }


    private void setDialogo(ConfermaArrivoDialogo dialogo) {
        this.dialogo = dialogo;
    }


    private MonitorDatiCliente getMonitor() {
        return monitor;
    }


    private void setMonitor(MonitorDatiCliente monitor) {
        this.monitor = monitor;
    }


    /**
     * Azione aggiunge un'anagrafica già esistente.
     * </p>
     */
    private final class AzAddCliente extends AzSpecifica {

        /**
         * Costruttore senza parametri.
         */
        public AzAddCliente() {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili*/
            super.setChiave("aggiunge");
            super.setIconaMedia("addTondo16");
            super.setTooltip("aggiunge un'anagrafica già esistente");
            super.setAbilitataPartenza(true);
            super.setUsoLista(true);
        }// fine del metodo costruttore senza parametri


        /**
         * actionPerformed, da ActionListener.
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                addCliente();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    } // fine della classe 'azione interna'

}
