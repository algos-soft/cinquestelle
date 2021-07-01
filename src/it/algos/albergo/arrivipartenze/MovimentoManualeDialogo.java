package it.algos.albergo.arrivipartenze;

import it.algos.albergo.AlbergoLib;
import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.evento.BaseListener;
import it.algos.base.evento.lista.ListaSelAz;
import it.algos.base.evento.lista.ListaSelEve;
import it.algos.base.libreria.Lib;
import it.algos.base.lista.ListaBase;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.gestione.anagrafica.Anagrafica;

import javax.swing.ListSelectionModel;
import java.util.ArrayList;
import java.util.Date;

/**
 * Dialogo astratto di conferma di un movimento manuale.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 01-giu-2008 ore 19.21.46
 */
public abstract class MovimentoManualeDialogo extends DialogoAnnullaConferma {

    /* campo data del movimento */
    protected static final String NOME_CAMPO_DATA = "Data movimento";

    protected static final String NOME_CAMPO_CAMERA = "Camera destinazione";

    protected static final String NOME_CAMPO_CONTO = Presenza.Cam.conto.get();

    protected static final String NOME_CAMPO_PERSONE = "clienti presenti";

    /* azione invocata quando cambia la selezione nel navigatore presenze */
    private BaseListener azSelezione;

    /* elenco delle righe visualizzate nel combo camera */
    private RigaComboCamera[] righeComboCamera;

    /* legenda per il campo navigatore Persone */
    private String legendaCamPersone;

    /* etichetta del campo data movimento */
    private String labelCamData;


    /**
     * Costruttore completo senza parametri.
     * <p/>
     */
    public MovimentoManualeDialogo() {
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

        try { // prova ad eseguire il codice

            this.setAzSelezione(new AzSelezione());

            /* valore di default */
            this.setLabelCamData("Data del movimento");

            /* valore di default */
            this.setLegendaCamPersone("Persone presenti");

            /* crea e registra i campi del dialogo */
            this.creaCampi();

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

        try { // prova ad eseguire il codice

            /* regola l'etichetta del campo data movimento */
            campo = this.getCampo(NOME_CAMPO_DATA);
            campo.decora().etichetta(this.getLabelCamData());

            /* aggiunge al campo persone la legenda */
            campo = this.getCampo(NOME_CAMPO_PERSONE);
            campo.decora().legenda(this.getLegendaCamPersone());

            /* inizializza nella superclasse */
            super.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */


    /**
     * Creazione dei campi.
     * </p>
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        Filtro filtro;
        Navigatore nav;

        try { // prova ad eseguire il codice

            /* data movimento */
            campo = CampoFactory.data(NOME_CAMPO_DATA);
            campo.setValoreIniziale(AlbergoLib.getDataProgramma());
            campo.decora().obbligatorio();
            this.addCampoCollezione(campo);

            /* campo combo interno camere occupate */
            campo = CampoFactory.comboInterno(NOME_CAMPO_CAMERA);
            campo.setLarScheda(340);
            campo.decora().legenda("camere occupate o a disposizione");
            this.addCampoCollezione(campo);

            /* campo combo link pop conti aperti */
            campo = CampoFactory.comboLinkPop(NOME_CAMPO_CONTO);
            campo.setNomeModuloLinkato(Conto.NOME_MODULO);
            campo.setNomeCampoValoriLinkato(Conto.Cam.sigla.get());
            Campo c = ContoModulo.get().getCampo(Conto.Cam.chiuso);
            filtro = FiltroFactory.crea(c, false);
//            filtro = ContoModulo.getFiltroContiAperti(AlbergoModulo.getCodAzienda());
            campo.setFiltroCorrente(filtro);
            campo.setOrdineElenco(ContoModulo.get().getCampo(Conto.Cam.sigla.get()));
            campo.setLarghezza(180);
            campo.decora().obbligatorio();
            campo.decora().etichetta("conto di competenza");
            campo.decora().legenda("conti aperti");
            campo.setUsaNuovo(false);
            this.addCampoCollezione(campo);

//            campo = PresenzaModulo.get().getCloneCampo(NOME_CAMPO_CONTO);
//            filtro = ContoModulo.getFiltroContiAperti(AlbergoModulo.getCodAzienda());
//            campo.setFiltroCorrente(filtro);
//            campo.setOrdineElenco(ContoModulo.get().getCampo(Conto.Cam.sigla.get()));
//            campo.decora().etichetta("conto di competenza");
//            campo.decora().legenda("conti aperti");
//            this.addCampoCollezione(campo);

            /* campo navigatore elenco delle persone presenti */
            nav = PresenzaModulo.get()
                    .getNavigatore(Presenza.Nav.arrivoCameraClienteParentela.get());
            nav.setFiltroBase(PresenzaModulo.getFiltroAperte());
            nav.getLista().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            nav.getLista().addListener(ListaBase.Evento.selezione, this.getAzSelezione());
            campo = CampoFactory.navigatore(NOME_CAMPO_PERSONE, nav);
            this.addCampoCollezione(campo);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Ritorna il navigatore delle presenze movimentabili
     * <p/>
     *
     * @return il navigatere delle presenze
     */
    protected Navigatore getNavPresenze() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;
        boolean continua;
        Campo campo;

        try { // prova ad eseguire il codice

            campo = this.getCampo(NOME_CAMPO_PERSONE);
            continua = (campo != null);

            if (continua) {
                nav = campo.getNavigatore();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


    /**
     * Ritorna il codice della presenza selezionata nel navigatore.
     * <p/>
     *
     * @return il codice della presenza
     */
    public int getCodPresenzaSelezionata() {
        /* variabili e costanti locali di lavoro */
        int codPresenza = 0;
        Navigatore nav;

        try {    // prova ad eseguire il codice
            nav = this.getNavPresenze();
            if (nav != null) {
                codPresenza = nav.getLista().getChiaveSelezionata();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codPresenza;
    }


    /**
     * Ritorna il codice azienda della presenza selezionata nel navigatore.
     * <p/>
     *
     * @return il codice azienda della presenza
     */
    protected int getCodAziendaPresenzaSelezionata() {
        /* variabili e costanti locali di lavoro */
        int codAzienda=0;
        int codPresenza = 0;
        Modulo modPresenza;

        try {    // prova ad eseguire il codice
            codPresenza = this.getCodPresenzaSelezionata();
            modPresenza = PresenzaModulo.get();
            codAzienda = modPresenza.query().valoreInt(Presenza.Cam.azienda.get(), codPresenza);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codAzienda;
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
        Date data;

        try { // prova ad eseguire il codice

            /* controllo nella superclasse */
            confermabile = super.isConfermabile();

            /* controllo che ci sia una data valida */
            if (confermabile) {
                data = this.getDataMovimento();
                confermabile = Lib.Data.isValida(data);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermabile;
    }


    /**
     * Invocato quando si preme il bottone conferma.
     * <p/>
     */
    public void eventoConferma() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        String testo;

        try { // prova ad eseguire il codice

            /**
             * Se la data del movimento è posteriore a oggi avvisa e impedisce di continuare
             */
            if (continua) {
                if (Lib.Data.isPosteriore(AlbergoLib.getDataProgramma(), this.getDataMovimento())) {
                    testo = "La data del movimento non può essere posteriore alla data di oggi.";
                    new MessaggioAvviso(testo);
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if


            if (continua) {
                super.eventoConferma();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Controola che una camera destinazione sia selezionata.
     * <p/>
     *
     * @return true se è selezionata
     */
    protected boolean isCameraSelezionata() {
        return (this.getCodCamera() != 0);
    }


    /**
     * Controola che una camera presenza sia selezionata.
     * <p/>
     *
     * @return true se è selezionata
     */
    protected boolean isPresenzaSelezionata() {
        return (this.getCodPresenzaSelezionata() != 0);
    }


    /**
     * Crea e registra i valori per il popup combo camere.
     * <p/>
     * Contiene tutte le camere verso le quali un cliente
     * può arrivare/cambiare manualmente.
     * <p/>
     * L'elenco comprende tutte le camere dei periodi (non disdetti)
     * che includono la data di riferimento (arrivo incluso, partenza esclusa)
     * e non sono ancora OUT
     * <p/>
     * - Se la camera è occupata visualizza a fianco di ogni camera
     * l'elenco delle persone attualmente presenti.
     * - Se la camera è libera visualizza l'intestatario della prenotazione.
     *
     * @param dataRif la data di riferimento
     */
    private void creaValoriCamere(Date dataRif) {
        /* variabili e costanti locali di lavoro */
        RigaComboCamera[] righe = new RigaComboCamera[0];
        Filtro filtro;
        Filtro unFiltro;
        Ordine ordine;
        Modulo modPeriodo;
        Modulo modPren;
        Modulo modClienti;
        Modulo modCamera;
        int[] codiciPeriodo;
        int codCamera;
        String nomeCamera;
        Campo campo;
//        int[] codCliPresenti;
        String testo;
        RigaComboCamera riga;
        ArrayList<RigaComboCamera> righeCombo = new ArrayList<RigaComboCamera>();
        String nome;
        int codCliente;
        int codPren;

        try {    // prova ad eseguire il codice

            modPeriodo = PeriodoModulo.get();
            modPren = PrenotazioneModulo.get();
            modClienti = ClienteAlbergoModulo.get();
            modCamera = CameraModulo.get();

            /* costruisce il filtro per i periodi */
            filtro = new Filtro();
            filtro.add(FiltroFactory.creaFalso(Periodo.Cam.partito.get()));
//            filtro.add(PeriodoModulo.getFiltroAperti());
//            filtro.add(PrenotazioneModulo.get().getFiltroAzienda());
            unFiltro = FiltroFactory.crea(Periodo.Cam.arrivoPrevisto.get(),
                    Filtro.Op.MINORE_UGUALE,
                    dataRif);
            filtro.add(unFiltro);
            unFiltro = FiltroFactory.crea(Periodo.Cam.partenzaPrevista.get(),
                    Filtro.Op.MAGGIORE,
                    dataRif);
            filtro.add(unFiltro);

            campo = PrenotazioneModulo.get().getCampo(Prenotazione.Cam.disdetta);
            unFiltro = FiltroFactory.crea(campo, false);
            filtro.add(unFiltro);

            /* costruisce l'ordine per nome camera */
            campo = CameraModulo.get().getCampo(Camera.Cam.camera);
            ordine = new Ordine();
            ordine.add(campo);


            codiciPeriodo = modPeriodo.query().valoriChiave(filtro, ordine);
            for (int codPeriodo : codiciPeriodo) {
                codCamera = modPeriodo.query().valoreInt(Periodo.Cam.camera.get(), codPeriodo);
                nomeCamera = modCamera.query().valoreStringa(Camera.Cam.camera.get(), codCamera);

//                codCliPresenti = PresenzaModulo.getClientiPresenti(codCamera);

                /* tutte le presenze aperte per il periodo */
                Filtro filtroPresenze=new Filtro();
                filtroPresenze.add(PresenzaModulo.getFiltroPresenzePeriodo(codPeriodo));
                filtroPresenze.add(FiltroFactory.creaFalso(Presenza.Cam.chiuso));
                int[] codiciPresenze=PresenzaModulo.get().query().valoriChiave(filtroPresenze);
                int[] codCliPresenti=new int[codiciPresenze.length];
                for (int k = 0; k < codiciPresenze.length; k++) {
                    int codPresenza = codiciPresenze[k];
                    int codCli = PresenzaModulo.get().query().valoreInt(Presenza.Cam.cliente.get(),codPresenza);
                    codCliPresenti[k] = codCli;
                } // fine del ciclo for


                testo = nomeCamera + " - ";
                if (codCliPresenti.length > 0) {
                    for (int k = 0; k < codCliPresenti.length; k++) {
                        codCliente = codCliPresenti[k];
                        nome = modClienti.query().valoreStringa(Anagrafica.Cam.soggetto.get(),
                                codCliente);
                        if (k > 0) {
                            testo += ", ";
                        }// fine del blocco if
                        testo += nome;
                    } // fine del ciclo for
                } else {
                    codPren = modPeriodo.query().valoreInt(Periodo.Cam.prenotazione.get(),
                            codPeriodo);
                    codCliente = modPren.query().valoreInt(Prenotazione.Cam.cliente.get(), codPren);
                    testo += "<libera> - a disposizione di ";
                    testo += modClienti.query().valoreStringa(Anagrafica.Cam.soggetto.get(),
                            codCliente);
                }// fine del blocco if-else

                riga = new RigaComboCamera(codCamera, testo, codPeriodo);
                righeCombo.add(riga);

            }

            /* crea l'array in uscita e lo registra */
            righe = righeCombo.toArray(righe);
            this.setRigheComboCamera(righe);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Aggiorna il popup delle camere in base alla data corrente.
     * <p/>
     */
    protected void updateComboCamere() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Date dataRif;
        RigaComboCamera[] valori;
        Campo campo;


        try {    // prova ad eseguire il codice

            dataRif = this.getDataMovimento();
            continua = Lib.Data.isValida(dataRif);

            if (continua) {
                this.creaValoriCamere(dataRif);
                valori = this.getRigheComboCamera();
                campo = this.getCampo(NOME_CAMPO_CAMERA);
                campo.setValoriInterni(valori);
                campo.avvia();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Data di movimento scelta.
     * <p/>
     *
     * @return la data di movimento inserita
     */
    public Date getDataMovimento() {
        /* variabili e costanti locali di lavoro */
        Date data = null;

        try { // prova ad eseguire il codice
            data = this.getData(NOME_CAMPO_DATA);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    /**
     * Camera selezionata.
     * <p/>
     *
     * @return il codice della camera selezionata
     */
    public int getCodCamera() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;

        try { // prova ad eseguire il codice
            codice = this.getInt(NOME_CAMPO_CAMERA);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Conto selezionato.
     * <p/>
     *
     * @return il codice del conto selezionato
     */
    public int getCodConto() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;

        try { // prova ad eseguire il codice
            codice = this.getInt(NOME_CAMPO_CONTO);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Ritorna true se la presenza da movimentare è un bambino
     * <p/>
     *
     * @return true se bambino
     */
    public boolean isBambino() {
        /* variabili e costanti locali di lavoro */
        boolean bambino=false;

        try { // prova ad eseguire il codice
            int codPres = this.getCodPresenzaSelezionata();
            if (codPres>0) {
                bambino = PresenzaModulo.get().query().valoreBool(Presenza.Cam.bambino.get(), codPres);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bambino;
    }

    /**
     * Ritorna il codice del periodo associato alla presenza da movimentare.
     * <p/>
     *
     * @return il codice del periodo
     */
    public int getCodPeriodoOrigine() {
        /* variabili e costanti locali di lavoro */
        int codPeri = 0;

        try { // prova ad eseguire il codice
            int codPres = this.getCodPresenzaSelezionata();
            if (codPres>0) {
                codPeri = PresenzaModulo.get().query().valoreInt(Presenza.Cam.periodo.get(), codPres);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codPeri;
    }

    /**
     * Ritorna il codice del periodo associato alla camera destinazione scelta.
     * <p/>
     *
     * @return il codice del periodo
     */
    public int getCodPeriodoDestinazione() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        RigaComboCamera riga;
        Campo campo;
        Object ogg;

        try { // prova ad eseguire il codice
            campo = this.getCampo(NOME_CAMPO_CAMERA);
            ogg = campo.getValoreElenco();
            if ((ogg != null) && (ogg instanceof RigaComboCamera)) {
                riga = (RigaComboCamera)ogg;
                codice = riga.getCodPeriodo();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Controlla se la presenza selezionata è l'unica aperta nella propria camera.
     * <p/>
     *
     * @return true se è l'unica
     */
    public boolean isUnicaPresenza() {
        /* variabili e costanti locali di lavoro */
        boolean unica = false;
        int codPresenza;
        int codCamera;
        int[] presenze;

        try {    // prova ad eseguire il codice
            codPresenza = this.getCodPresenzaSelezionata();
            codCamera = PresenzaModulo.getCamera(codPresenza);
            presenze = PresenzaModulo.getPresenzeAperte(codCamera);
            if (presenze.length < 2) {
                unica = true;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unica;
    }


    /**
     * Controlla se la data della movimentazione coincide con la data
     * di fine del periodo.
     * <p/>
     *
     * @return true se le date coincidono
     */
    public boolean isFinePeriodo() {
        /* variabili e costanti locali di lavoro */
        boolean fine=false;

        try {    // prova ad eseguire il codice
            int codPeriodo = this.getCodPeriodoOrigine();
            Modulo modPeriodo = PeriodoModulo.get();
            Date dataFine = modPeriodo.query().valoreData(Periodo.Cam.partenzaPrevista.get(), codPeriodo);
            Date dataMovi = this.getDataMovimento();
            fine=(dataMovi.equals(dataFine));
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return fine;
    }



    /**
     * Invocato quando si seleziona una riga nel navigatore Presenze.
     * <p/>
     */
    protected void selezionePresenze() {
        this.sincronizza();
    }


    private BaseListener getAzSelezione() {
        return azSelezione;
    }


    private void setAzSelezione(BaseListener azSelezione) {
        this.azSelezione = azSelezione;
    }


    private RigaComboCamera[] getRigheComboCamera() {
        return righeComboCamera;
    }


    private void setRigheComboCamera(RigaComboCamera[] righeComboCamera) {
        this.righeComboCamera = righeComboCamera;
    }


    private String getLegendaCamPersone() {
        return legendaCamPersone;
    }


    protected void setLegendaCamPersone(String legendaCamPersone) {
        this.legendaCamPersone = legendaCamPersone;
    }


    private String getLabelCamData() {
        return labelCamData;
    }


    protected void setLabelCamData(String labelCamData) {
        this.labelCamData = labelCamData;
    }


    /**
     * azione invocata quando cambia la selezione nel navigatore presenze
     */
    private class AzSelezione extends ListaSelAz {

        public void listaSelAz(ListaSelEve unEvento) {
            selezionePresenze();
        }
    } // fine della classe


    /**
     * Singolo elemento del combo Camere.
     * </p>
     */
    protected final class RigaComboCamera implements Campo.ElementiCombo {

        /* codice della camera */
        private int codCamera;

        /* testo da visualizzare nel combo */
        private String testoCombo;

        /* codice del periodo associato alla camera */
        private int codPeriodo;


        /**
         * Costruttore completo con parametri. <br>
         *
         * @param codCamera codice camera
         * @param testoCombo testo da visualizzare nel combo
         * @param codPeriodo di riferimento
         */
        public RigaComboCamera(int codCamera, String testoCombo, int codPeriodo) {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili di istanza coi parametri */
            this.codCamera = codCamera;
            this.testoCombo = testoCombo;
            this.codPeriodo = codPeriodo;

        }// fine del metodo costruttore completo


        public int getCodice() {
            return codCamera;
        }


        public String toString() {
            return testoCombo;
        }


        /**
         * Ritorna il codice del periodo associato.
         * <p/>
         *
         * @return il codice del periodo
         */
        public int getCodPeriodo() {
            return codPeriodo;
        }


    } // fine della classe 'interna'


}// fine della classe