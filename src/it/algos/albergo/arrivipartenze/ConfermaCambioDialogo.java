package it.algos.albergo.arrivipartenze;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.prenotazione.periodo.periodoaddebito.AddebitoPeriodo;
import it.algos.albergo.prenotazione.periodo.periodoaddebito.AddebitoPeriodoModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.dati.Dati;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.dialogo.DialogoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.NavigatoreL;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;

import javax.swing.BorderFactory;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import java.awt.Component;
import java.util.Date;

/**
 * Dialogo di conferma di un arrivo.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-ott-2007 ore 19.21.46
 */
public class ConfermaCambioDialogo extends DialogoAnnullaConferma {

    /**
     * codice del periodo (primo)
     */
    private int codPrimoPeriodo;

    /**
     * codice del periodo (secondo)
     */
    private int codSecondoPeriodo;

    /**
     * codice del conto sul quale trasferire gli addebiti previsti
     * (viene regolato dal dialogo prima della conferma)
     */
    private int codContoUpdate;

    private static final String NOME_CAMPO_DATA_CAMBIO = "data di cambio";

    private static final String NOME_CAMPO_CAMERA_PROVENIENZA = "camera provenienza";

    private static final String NOME_CAMPO_CAMERA_DESTINAZIONE = "camera destinazione";

    private static final String NOME_CAMPO_PERSONE = "persone";

    /**
     * Costruttore base con parametri.
     * <p/>
     *
     * @param codice del primo periodo del cambio da confermare
     */
    public ConfermaCambioDialogo(int codice) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.setCodPrimoPeriodo(codice);
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
        try { // prova ad eseguire il codice
            this.setTitolo("Conferma cambio");
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
        Pannello pan;

        try { // prova ad eseguire il codice

            /* crea e registra i campi del dialogo */
            this.creaCampi();

            /* regola i valori iniziali dei campi del dialogo */
            this.regolaValoriIniziali();

            pan = PannelloFactory.verticale(this);
            campo = this.getCampo(NOME_CAMPO_DATA_CAMBIO);
            pan.add(campo);

            campo = this.getCampo(NOME_CAMPO_CAMERA_PROVENIENZA);
            pan.add(campo);

            campo = this.getCampo(NOME_CAMPO_CAMERA_DESTINAZIONE);
            pan.add(campo);

            campo = this.getCampo(NOME_CAMPO_PERSONE);
            pan.add(campo);

            this.addComponente(pan.getPanFisso());

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
        int codCameraProvenienza = 0;
        int codCameraDestinazione = 0;
        int codSecondoPeriodo = 0;
        Date dataCambio = null;
        boolean continua;
        Modulo modPeriodo = null;
        Query query;
        Filtro filtro;
        Dati dati;
        Campo campo;
        int codPrimoPeriodo;
        Navigatore nav;

        try {    // prova ad eseguire il codice

            codPrimoPeriodo = this.getCodPrimoPeriodo();
            continua = (codPrimoPeriodo > 0);

            /* recupera modulo Periodo */
            if (continua) {
                modPeriodo = PeriodoModulo.get();
                continua = (modPeriodo != null);
            }// fine del blocco if

            /* costruisce la query sul periodo e recupera i dati */
            /* recupera il secondo periodo */
            if (continua) {
                query = new QuerySelezione(modPeriodo);
                filtro = FiltroFactory.codice(modPeriodo, codPrimoPeriodo);
                query.addCampo(Periodo.Cam.camera.get());
                query.addCampo(Periodo.Cam.partenzaPrevista.get());
                query.addCampo(Periodo.Cam.linkDestinazione.get());
                query.setFiltro(filtro);

                dati = modPeriodo.query().querySelezione(query);

                codCameraProvenienza = dati.getIntAt(Periodo.Cam.camera.get());
                dataCambio = dati.getDataAt(Periodo.Cam.partenzaPrevista.get());
                codSecondoPeriodo = dati.getIntAt(Periodo.Cam.linkDestinazione.get());

                dati.close();

                codCameraDestinazione = modPeriodo.query().valoreInt(
                        Periodo.Cam.camera.get(),
                        codSecondoPeriodo);
            }// fine del blocco if

            /* registra i dati nel dialogo */
            if (continua) {
                this.setDataCambio(dataCambio);
                this.setCameraProvenienza(codCameraProvenienza);
                this.setCameraDestinazione(codCameraDestinazione);
                this.setCodSecondoPeriodo(codSecondoPeriodo);
                campo = this.getCampo(NOME_CAMPO_PERSONE);
                nav = campo.getNavigatore();
                nav.setFiltroBase(null);
                nav.setFiltroCorrente(this.getFiltroPresenze());
//                nav.setFiltroBase(PresenzaModulo.getFiltroAperte(codCameraProvenienza));
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }

    /**
     * Creazione dei campi.
     * </p>
     */
    private void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        Navigatore nav;

        try { // prova ad eseguire il codice

            /* data cambio */
            campo = CampoFactory.data(NOME_CAMPO_DATA_CAMBIO);
            this.addCampoCollezione(campo);

            /* camera provenienza */
            campo = CampoFactory.comboLinkSel(NOME_CAMPO_CAMERA_PROVENIENZA);
            campo.setNomeModuloLinkato(Camera.NOME_MODULO);
            campo.setNomeCampoValoriLinkato(Camera.Cam.camera.get());
            campo.setUsaNuovo(false);
            campo.setLarScheda(70);
            campo.setAbilitato(false);
            this.addCampoCollezione(campo);

            /* camera destinazione */
            campo = CampoFactory.comboLinkSel(NOME_CAMPO_CAMERA_DESTINAZIONE);
            campo.setNomeModuloLinkato(Camera.NOME_MODULO);
            campo.setNomeCampoValoriLinkato(Camera.Cam.camera.get());
            campo.setUsaNuovo(false);
            campo.setLarScheda(70);
            campo.setAbilitato(false);
            this.addCampoCollezione(campo);

            /* elenco delle persone presenti nella camera */
            nav = PresenzaModulo.get().getNavigatore(Presenza.Nav.confermaCambio.get());
            campo = CampoFactory.navigatore(NOME_CAMPO_PERSONE, nav);
            campo.setAbilitato(false);
            nav.getLista().getTavola().setEnabled(false);
            this.addCampoCollezione(campo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia

    /**
     * Ritorna il filtro per le presenze da mostrare nel navigatore.
     * <p/>
     * Controlla se nella camera ci sono presenze "provvisorie" (con flag partenzaOggi acceso).
     * Se ce ne sono, seleziona solo quelle
     * Altrimenti, seleziona tutte le presenze nella camera
     *
     * @return il filtro per le presenze da visualizzare
     */
    public Filtro getFiltroPresenze() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;

        try { // prova ad eseguire il codice

//            int codCamera = this.getCameraProvenienza();
//            filtro = PresenzaModulo.getFiltroAperteSmart(codCamera);

            int codPeriodo = this.getCodPrimoPeriodo();
            filtro = new Filtro();
            filtro.add(PresenzaModulo.getFiltroPresenzePeriodo(codPeriodo));
            filtro.add(FiltroFactory.creaFalso(Presenza.Cam.chiuso));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }

    /**
     * Identifica il conto sul quale vanno trasferiti
     * gli addebiti previsti del periodo destinazione.
     * <p/>
     * Se ce n'è più di uno chiede all'utente di sceglierne uno
     *
     * @return il codice del conto,
     *         0 se non si devono trasferire gli addebiti,
     *         -1 se l'utente ha annullato la procedura
     */
    private int identificaContoUpdate() {
        /* variabili e costanti locali di lavoro */
        int codConto = -1;

        try {    // prova ad eseguire il codice

            Filtro filtro = new Filtro();
            // tutti i conti del periodo
            filtro.add(ContoModulo.getFiltroContiPeriodo(this.getCodPrimoPeriodo()));
            // che sono attualmente aperti
            filtro.add(FiltroFactory.crea(Conto.Cam.chiuso.get(), false));
            Modulo mod = ContoModulo.get();
            int quanti = mod.query().contaRecords(filtro);

            /* cosa fare */
            switch (quanti) {
                case 0:    // nessun conto aperto trovato
                    boolean risposta = DialogoFactory.getConferma(
                            "Attenzione!\nNon ci sono conti!\nGli addebiti previsti in prenotazione non saranno trasferiti.");
                    if (risposta) {
                        codConto = 0;
                    } else {
                        codConto = -1;
                    }// fine del blocco if-else
                    break;

                case 1:    // un conto aperto trovato
                    codConto = mod.query().valoreChiave(filtro);
                    break;

                default: // piu' conti aperti trovati, deve scegliere

                    DialogoSceltaConto dialogo = new DialogoSceltaConto(filtro);
                    dialogo.avvia();
                    if (dialogo.isConfermato()) {
                        codConto = dialogo.getCodConto();
                    } else {
                        codConto = -1;
                    }// fine del blocco if-else
                    break;


            } // fine del blocco switch

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codConto;
    }

    /**
     * Determina se il periodo destinazione ha degli addebiti previsti
     * da trasferire su un conto.
     * <p/>
     *
     * @return true se ha addebiti false se non ne ha
     */
    private boolean isAddebitiDaTrasferire() {
        /* variabili e costanti locali di lavoro */
        boolean isAddebiti = false;

        try {    // prova ad eseguire il codice

            int codPeriodo = this.getCodSecondoPeriodo();
            Modulo modAddebiti = AddebitoPeriodoModulo.get();
            Filtro filtro = FiltroFactory.crea(AddebitoPeriodo.Cam.periodo.get(), codPeriodo);
            int quanti = modAddebiti.query().contaRecords(filtro);
            isAddebiti = (quanti > 0);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return isAddebiti;
    }

    @Override
    public void eventoConferma() {
        /* variabili e costanti locali di lavoro */
        int codice;

        try { // prova ad eseguire il codice

            if (this.isAddebitiDaTrasferire()) {    //ci sono addebiti da trasferire

                //se ci sono addebiti da trasferire, identifica il conto di destinazione
                codice = this.identificaContoUpdate();

                switch (codice) {
                    case -1:  // annullato
                        break;
                    case 0:   // nessun conto
                        this.setCodContoUpdate(0);
                        break;
                    default: // conto trovato
                        this.setCodContoUpdate(codice);
                        break;
                } // fine del blocco switch

                // se non annullato dall'utente, procedi
                if (codice != -1) {
                    super.eventoConferma();
                }// fine del blocco if

            } else {                    //non ci sono addebiti da trasferire
                super.eventoConferma();
            }// fine del blocco if-else


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }

    /**
     * Assegna la data di cambio.
     * <p/>
     *
     * @param data di cambio
     */
    private void setDataCambio(Date data) {
        this.setValore(NOME_CAMPO_DATA_CAMBIO, data);
    }

    /**
     * Assegna la data di cambio.
     * <p/>
     *
     * @return data di cambio
     */
    public Date getDataCambio() {
        return this.getData(NOME_CAMPO_DATA_CAMBIO);
    }

    /**
     * Assegna la camera di provenienza.
     * <p/>
     *
     * @param camera di provenienza
     */
    private void setCameraProvenienza(int camera) {
        this.setValore(NOME_CAMPO_CAMERA_PROVENIENZA, camera);
    }

    /**
     * Ritorna la camera di provenienza.
     * <p/>
     *
     * @return camera di provenienza
     */
    public int getCameraProvenienza() {
        return this.getInt(NOME_CAMPO_CAMERA_PROVENIENZA);
    }

    /**
     * Assegna la camera di destinazione.
     * <p/>
     *
     * @param camera di destinazione
     */
    private void setCameraDestinazione(int camera) {
        this.setValore(NOME_CAMPO_CAMERA_DESTINAZIONE, camera);
    }

    /**
     * Ritorna la camera di destinazione.
     * <p/>
     *
     * @return camera di destinazione
     */
    public int getCameraDestinazione() {
        return this.getInt(NOME_CAMPO_CAMERA_DESTINAZIONE);
    }

    public int getCodPrimoPeriodo() {
        return codPrimoPeriodo;
    }

    private void setCodPrimoPeriodo(int codPrimoPeriodo) {
        this.codPrimoPeriodo = codPrimoPeriodo;
    }

    public int getCodSecondoPeriodo() {
        return codSecondoPeriodo;
    }

    private void setCodSecondoPeriodo(int codSecondoPeriodo) {
        this.codSecondoPeriodo = codSecondoPeriodo;
    }

    /**
     * L'eventuale codice del conto sul quale trasferire gli
     * addebiti previsti del periodo destinazione del cambio
     * <p/>
     *
     * @return il codice conto
     */
    public int getCodContoUpdate() {
        return codContoUpdate;
    }

    private void setCodContoUpdate(int codContoUpdate) {
        this.codContoUpdate = codContoUpdate;
    }

    /**
     * Dialogo di selezione di un conto.
     * (Classe interna)
     * </p>
     */
    class DialogoSceltaConto extends DialogoAnnullaConferma {

        private Navigatore navConti;
        private Filtro filtroConti;

        /**
         * Costruttore completo con parametri.
         * <p>
         * @param filtroConti filtro per isolare i conti tra i quali scegliere
         */
        public DialogoSceltaConto(Filtro filtroConti) {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili di istanza coi parametri */
            this.setFiltroConti(filtroConti);

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

            try { // prova ad eseguire il codice

                this.setTitolo("Seleziona Conto");

                this.setMessaggio("Nella camera ci sono più conti aperti.\n\n"
                        + "Selezionare il conto sul quale trasferire il piano addebiti "
                        + "previsti in prenotazione.");

                
                // crea il navigatore
//                Modulo modConto = ContoModulo.get();
//                Navigatore nav = new NavigatoreL(modConto);
//                this.setNavConti(nav);
//                Vista vista = new Vista();
//                vista.addCampo(modConto.getCampo(Conto.Cam.sigla));
//                nav.setVista(vista);
//                nav.setFiltroBase(this.getFiltroConti());
//                nav.setRigheLista(6);
//                nav.setUsaToolBarLista(false);
//                nav.setUsaStatusBarLista(false);
//                nav.inizializza();
//                nav.avvia();

                // crea il navigatore
                Modulo modConto = ContoModulo.get();
                Navigatore nav = new NavConti(modConto, this);
                this.setNavConti(nav);

                /* aggiunge il navigatore al dialogo */
                Component comp = this.getNavConti().getPortaleNavigatore();
                this.addComponente(comp);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch


        }

        /**
         * Ritorna il codice del conto selezionato.
         * <p/>
         * @return il codice del conto
         */
        public int getCodConto() {
            /* variabili e costanti locali di lavoro */
            int cod=0;

            Navigatore nav = this.getNavConti();
            if (nav!=null) {
                cod = nav.getLista().getChiaveSelezionata();
            }// fine del blocco if

            /* valore di ritorno */
            return cod;
        }

        @Override
        public boolean isConfermabile() {

            boolean confermabile=super.isConfermabile();

            if (confermabile) {
                int cod= this.getCodConto();
                confermabile = (cod>0);
            }// fine del blocco if

            return confermabile;
        }

        private Navigatore getNavConti() {
            return navConti;
        }

        private void setNavConti(Navigatore navConti) {
            this.navConti = navConti;
        }

        private Filtro getFiltroConti() {
            return filtroConti;
        }

        private void setFiltroConti(Filtro filtroConti) {
            this.filtroConti = filtroConti;
        }
    } // fine della classe 'interna'


    /**
     * Navigatore per la selezione del conto
     * (Classe interna)
     */
    class NavConti extends NavigatoreL {

        private DialogoSceltaConto dialogo;

        /**
         * Costruttore completo con parametri. <br>
         *
         * @param modulo di riferimento
         * @param dialogo di riferimento
         *
         */
        public NavConti(Modulo modulo, DialogoSceltaConto dialogo) {
            /* rimanda al costruttore della superclasse */
            super(modulo);

            /* regola le variabili di istanza coi parametri */
            this.dialogo=dialogo;

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
            Modulo modConto = ContoModulo.get();
            Vista vista = new Vista();
            vista.addCampo(modConto.getCampo(Conto.Cam.sigla));
            this.setVista(vista);
            this.setFiltroBase(dialogo.getFiltroConti());
            this.setRigheLista(6);
            this.setUsaToolBarLista(false);
            this.setUsaStatusBarLista(false);
            this.getLista().getTavola().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            this.inizializza();
            this.avvia();
        }

        @Override
        public void sincronizza() {
            super.sincronizza();
            dialogo.sincronizza();
        }
    } // fine della classe 'interna'


}// fine della classe