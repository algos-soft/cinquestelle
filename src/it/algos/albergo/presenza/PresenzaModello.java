package it.algos.albergo.presenza;

import it.algos.albergo.AlbergoLib;
import it.algos.albergo.AlbergoModulo;
import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.lib.RendererPasto;
import it.algos.albergo.listino.Listino;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.ristorante.tavolo.Tavolo;
import it.algos.albergo.tabelle.azienda.Azienda;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.logica.CampoLogica;
import it.algos.base.database.Db;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;
import it.algos.base.wrapper.SetValori;
import it.algos.base.wrapper.WrapFiltri;
import it.algos.gestione.anagrafica.Anagrafica;

import javax.swing.SwingConstants;
import java.util.Date;

/**
 * Modello dati del modulo Presenza
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 22-gen-2008 ore  16:03
 */
public final class PresenzaModello extends ModelloAlgos implements Presenza {

    /**
     * Costruttore completo senza parametri.
     */
    public PresenzaModello() {
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
        /* regola il nome della tavola dalla costante */
        super.setTavolaArchivio(Presenza.NOME_TAVOLA);

        /* attiva il trigger nuovo record */
        this.setTriggerNuovoAttivo(true);

        /* attiva la gestione trigger modifica record, con valori precedenti */
        this.setTriggerModificaAttivo(true, true);

        /* attiva il trigger di eliminazione, con valori precedenti */
        this.setTriggerEliminaAttivo(true, true);

    }// fine del metodo inizia


    /**
     * Regolazioni iniziali, dopo che sono stati regolati dalla sottoclasse
     * i parametri indispensabili (tra cui il riferimento al modulo)
     * Metodo chiamato dalla classe che crea questo oggetto
     * Viene eseguito una sola volta
     *
     * @param unModulo Abstract Data Types per le informazioni di un modulo
     */
    @Override
    public boolean inizializza(Modulo unModulo) {
        /* variabili e costanti locali di lavoro */
        boolean inizializzato = false;

        try { // prova ad eseguire il codice
            inizializzato = super.inizializza(unModulo);
            super.setCampoOrdineIniziale(Cam.entrata);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return inizializzato;
    } /* fine del metodo */


    /**
     * Creazione dei campi.
     * <p/>
     * Creazione dei campi base presenti in tutte le tavole <br>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Creazione dei campi record di questo modello <br>
     * I campi verranno visualizzati nell'ordine di inserimento <br>
     * Ogni campo viene creato con un costruttore semplice con solo le piu'
     * comuni informazioni; le altre vengono regolate con chiamate successive <br>
     * Invoca il metodo sovrascritto della superclasse <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.progetto.Progetto#preparaModuli
     * @see it.algos.base.modello.ModelloAlgos#creaCampi
     * @see it.algos.base.campo.base.CampoFactory
     * @see it.algos.base.campo.video.decorator.VideoFactory
     */
    @Override
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;
        Ordine ordine;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo ps */
            unCampo = CampoFactory.intero(Cam.ps);
            unCampo.setRicercabile(true);
            unCampo.addOrdinePrivato(this.getCampoChiave());
            this.addCampo(unCampo);

            /* campo data del primo arrivo */
            unCampo = CampoFactory.data(Cam.arrivo);
            unCampo.decora().obbligatorio();
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo cambio in entrata  */
            unCampo = CampoFactory.checkBox(Cam.cambioEntrata);
            unCampo.setLarLista(30);
            unCampo.setLarScheda(150);
            unCampo.setTestoComponente("cambio entrata");
            unCampo.setModificabileLista(false);
            unCampo.setRidimensionabile(false);
            this.addCampo(unCampo);

            /* campo data entrata (con arrivo o cambio) */
            unCampo = CampoFactory.data(Cam.entrata);
            unCampo.decora().obbligatorio();
            unCampo.setRenderer(new RendererEntrataUscita(unCampo, true));
            unCampo.setLarLista(90);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo data uscita (con partenza o cambio) */
            unCampo = CampoFactory.data(Cam.uscita);
            unCampo.setRenderer(new RendererEntrataUscita(unCampo, false));
            unCampo.setLarLista(90);
            unCampo.setInit(null);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo numero di presenze (calcolato, differenza date) */
            unCampo = CampoFactory.calcola(
                    Cam.presenze,
                    CampoLogica.Calcolo.differenzaDate,
                    Cam.entrata.get(),
                    Cam.uscita.get());
            unCampo.getCampoDB().setCampoFisico(true);
            unCampo.setLarLista(40);
            unCampo.setAllineamentoLista(SwingConstants.CENTER);
            this.addCampo(unCampo);

            /* campo cambio in uscita  */
            unCampo = CampoFactory.checkBox(Cam.cambioUscita);
            unCampo.setLarLista(30);
            unCampo.setLarScheda(150);
            unCampo.setTestoComponente("cambio uscita");
            unCampo.setModificabileLista(false);
            unCampo.setRidimensionabile(false);
            this.addCampo(unCampo);

            /* campo presenza chiusa */
            unCampo = CampoFactory.checkBox(Cam.chiuso);
            unCampo.setTestoComponente("partito");
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo cliente */
            unCampo = CampoFactory.comboLinkSel(Cam.cliente);
            unCampo.setNomeModuloLinkato(ClienteAlbergo.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(Anagrafica.Cam.soggetto.get());
            unCampo.setNomeCampoValoriLinkato(Anagrafica.Cam.soggetto.get());
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.decora().obbligatorio();
            unCampo.setLarghezza(150);
            unCampo.setRidimensionabile(false);
            unCampo.setUsaNuovo(false);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo camera */
            unCampo = CampoFactory.comboLinkSel(Cam.camera);
            unCampo.setNomeModuloLinkato(Camera.NOME_MODULO);
            unCampo.setNomeCampoValoriLinkato(Camera.Cam.camera.get());
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.decora().obbligatorio();
            unCampo.setRenderer(new RendererCamera(unCampo));
            unCampo.setUsaNuovo(false);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo tipo di pensione */
            unCampo = CampoFactory.comboInterno(Cam.pensione);
            unCampo.setValoriInterni(Listino.PensioniPeriodo.values());
            unCampo.setLarScheda(60);
            unCampo.setLarLista(40);
            unCampo.setAllineamentoLista(SwingConstants.CENTER);
            this.addCampo(unCampo);

            /* campo pasto in caso di mezza pensione */
            unCampo = CampoFactory.comboInterno(Cam.pasto);
            unCampo.setValoriInterni(TipiPasto.values());
            unCampo.setRenderer(new RendererPasto(unCampo));
            this.addCampo(unCampo);

            /* campo link al tavolo del ristorante */
            unCampo = CampoFactory.comboLinkPop(Cam.tavolo);
            unCampo.setNomeModuloLinkato(Tavolo.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(Tavolo.Cam.numtavolo.get());
            unCampo.setNomeCampoValoriLinkato(Tavolo.Cam.numtavolo.get());
            unCampo.setAzioneDelete(Db.Azione.setNull);
//            unCampo.setRenderer(new RendererCamera(unCampo));
//            unCampo.setUsaNuovo(false);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);


            /* campo check bambino */
            unCampo = CampoFactory.checkBox(Cam.bambino);
            unCampo.setTestoComponente("bambino");
            unCampo.setLarLista(35);
            this.addCampo(unCampo);

            /* campo presenza provvisoria */
            unCampo = CampoFactory.checkBox(Cam.provvisoria);
            unCampo.setTestoComponente("in partenza oggi");
            unCampo.setLarScheda(150);
            this.addCampo(unCampo);

            /* campo link conto */
            unCampo = CampoFactory.comboLinkSel(Cam.conto);
            unCampo.setNomeModuloLinkato(Conto.NOME_MODULO);
            unCampo.setNomeCampoValoriLinkato(Conto.Cam.sigla.get());
            unCampo.setAzioneDelete(Db.Azione.setNull);

            Campo c = ContoModulo.get().getCampo(Conto.Cam.chiuso);
            Filtro filtro = FiltroFactory.crea(c, false);
            unCampo.setFiltroCorrente(filtro);
            unCampo.setLarghezza(180);
            unCampo.setRidimensionabile(false);
            unCampo.setUsaNuovo(false);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo arrivo con */
            unCampo = CampoFactory.comboInterno(Conto.Cam.arrivoCon);
            unCampo.setValoriInterni(Periodo.TipiAP.getElementiArrivo());
            unCampo.setLarScheda(120);
            this.addCampo(unCampo);

            /* campo link periodo */
            unCampo = CampoFactory.link(Cam.periodo);
            unCampo.setNomeModuloLinkato(Periodo.NOME_MODULO);
            unCampo.setAzioneDelete(Db.Azione.setNull);
            this.addCampo(unCampo);

            /* campo azienda */
            unCampo = CampoFactory.comboLinkPop(Cam.azienda);
            unCampo.setNomeModuloLinkato(Azienda.NOME_MODULO);
            unCampo.setNomeCampoValoriLinkato(Azienda.CAMPO_SIGLA);
            unCampo.decora().obbligatorio();
            unCampo.setLarLista(60);
            unCampo.setLarScheda(80);
            unCampo.setUsaNonSpecificato(false);
            this.addCampo(unCampo);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo creaCampi


    /**
     * Crea la vista di default.
     * <p/>
     * Metodo invocato dal ciclo di preparazione dei moduli <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @return la vista creata
     */
    protected Vista creaVistaDefault() {
        /* variabili e costanti locali di lavoro */
        Vista vista = null;
        Campo campo;
        Modulo mod;
        VistaElemento elem;

        try { // prova ad eseguire il codice
            vista = new Vista(this.getModulo());
//            vista.addCampo(Cam.ps);
            vista.addCampo(Cam.entrata);
            vista.addCampo(Cam.uscita);
            vista.addCampo(Cam.cliente);

            /**
             * aggiungo direttamente un clone del campo camera
             * per utilizzare solo qui un renderer specifico
             */
            mod = CameraModulo.get();
            campo = mod.getCloneCampo(Camera.Cam.camera.get());
            campo.setRenderer(new RendererCamera(campo));
            elem = vista.addCampo(campo);
            elem.setLarghezzaColonna(50);
            elem.setAllineamentoLista(SwingConstants.CENTER);

            vista.addCampo(Cam.pensione);

            if (AlbergoModulo.isRistorante()) {
                vista.addCampo(Cam.pasto);
                vista.addCampo(Cam.tavolo);
            }// fine del blocco if

            vista.addCampo(Cam.bambino);
            vista.addCampo(Cam.conto);
            elem = vista.addCampo(Cam.azienda);
            elem.setLarghezzaColonna(60);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return vista;

    } /* fine del metodo */


    /**
     * Creazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Eventuale creazione di viste aggiuntive, oltre alla vista base di default <br>
     * Costruisce degli ArrayList di riferimenti ordinati (oggetti <code>Vista</code>) per
     * individuare i campi che voglio vedere nelle liste alternative ed
     * aggiuntive a quella standard (costruita in automatico nella superclasse) <br>
     * Gli array vengono creati coi campi di questo modello, oppure con
     * viste di altri moduli, oppure con campi di altri modelli <br>
     * Viene chiamato <strong>dopo</strong> che nella sottoclasse sono stati costruiti tutti i campi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.costante.CostanteModulo#VISTA_BASE_DEFAULT
     * @see it.algos.base.costante.CostanteModello#NOME_CAMPO_SIGLA
     */
    @Override
    protected void creaViste() {
        /* variabili e costanti locali di lavoro */
        Vista vista;
        Modulo mod;
        Campo campo;
        VistaElemento elem;

        try { // prova ad eseguire il codice

            /* crea la vista per il navigatore Presenze nel Conto */
            vista = this.creaVista(Presenza.Vis.conto.get());
            elem = vista.addCampo(Cam.cliente.get());
            elem.setLarghezzaColonna(130);
            mod = ClienteAlbergoModulo.get();
            campo = mod.getCampo(ClienteAlbergo.Cam.parentela.get());
            elem = vista.addCampo(campo);
            elem.setLarghezzaColonna(64);

            elem = vista.addCampo(Presenza.Cam.arrivoCon);
            elem.setLarghezzaColonna(70);
            elem.setAllineamentoLista(SwingConstants.CENTER);
            elem.setTitoloColonna("Arr. con");

            vista.addCampo(Presenza.Cam.entrata.get());
            vista.addCampo(Presenza.Cam.uscita.get());
            elem = vista.addCampo(Presenza.Cam.camera.get());
            elem.setLarghezzaColonna(50);
            campo = mod.getCampoPreferito();
            vista.addCampo(campo);
            this.addVista(vista);

            /* crea la vista con cliente e parentela */
            vista = this.creaVista(Presenza.Vis.clienteParentela.get());
            vista.addCampo(Cam.cliente.get());
            mod = ClienteAlbergoModulo.get();
            campo = mod.getCampo(ClienteAlbergo.Cam.parentela.get());
            vista.addCampo(campo);
            this.addVista(vista);

            /* crea la vista con data arrivo, camera, cliente e parentela */
            vista = this.creaVista(Presenza.Vis.partenzaCambioManuale.get());
            vista.addCampo(Cam.camera.get());
            vista.addCampo(Cam.cliente.get());
            mod = ClienteAlbergoModulo.get();
            campo = mod.getCampo(ClienteAlbergo.Cam.parentela.get());
            vista.addCampo(campo);
            vista.addCampo(Cam.arrivo.get());
            this.addVista(vista);

            /* crea la vista per il dialogo di gestione PS */
            vista = this.creaVista(Presenza.Vis.gestioneps.get());
            mod = CameraModulo.get();
            campo = mod.getCampo(Camera.Cam.camera.get());
            vista.addCampo(campo);
            elem = vista.addCampo(Cam.cliente.get());
            elem.setLarghezzaColonna(200);
            mod = ClienteAlbergoModulo.get();
            campo = mod.getCampo(ClienteAlbergo.Cam.parentela.get());
            vista.addCampo(campo);
            this.addVista(vista);

            /* crea la vista per il dialogo di annullamento arrivo */
            vista = this.creaVista(Presenza.Vis.annullaArrivo.get());
            elem = vista.addCampo(Cam.cliente.get());
            elem.setLarghezzaColonna(180);
            vista.addCampo(Cam.arrivo.get());
            elem = vista.addCampo(Cam.conto.get());
            elem.setLarghezzaColonna(180);
            campo = ContoModulo.get().getCampo(Conto.Cam.totNonPagato);
            vista.addCampo(campo);
            this.addVista(vista);

            /* crea la vista per il dialogo di annullamento partenze */
            vista = this.creaVista(Presenza.Vis.annullaPartenza.get());
            elem = vista.addCampo(Cam.cliente.get());
            elem.setLarghezzaColonna(180);
            elem = vista.addCampo(Cam.conto.get());
            elem.setLarghezzaColonna(180);
            this.addVista(vista);

            /* crea la vista per il dialogo di conferma cambio */
            vista = this.creaVista(Presenza.Vis.confermaCambio.get());
            vista.addCampo(Cam.cliente.get());
//            elem.setLarghezzaColonna(180);
            campo = ClienteAlbergoModulo.get().getCampo(ClienteAlbergo.Cam.parentela.get());
            vista.addCampo(campo);
            campo = ContoModulo.get().getCampo(Conto.Cam.sigla.get());
            vista.addCampo(campo);
            this.addVista(vista);


            /* crea la vista per lo Storico Presenze */
            vista = this.creaVista(Presenza.Vis.storico.get());
            vista.addCampo(Cam.entrata.get());
            vista.addCampo(Cam.uscita.get());
            vista.addCampo(Cam.cliente.get());
            elem = vista.addCampo(Cam.camera.get());
            elem.setTitoloColonna("cam");
            vista.addCampo(Cam.pensione.get());
            vista.addCampo(Cam.bambino.get());
//            vista.addCampo(Cam.azienda.get());
            this.addVista(vista);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Eventuale regolazione delle caratteristiche specifiche di ogni copia
     * dei campi delle viste; le variazioni modificano <strong>solo</strong> le copie <br>
     * Viene chiamato <strong>dopo</strong> che nella superclasse sono state
     * <strong>clonate</strong> tutte le viste <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.costante.CostanteModulo#VISTA_BASE_DEFAULT
     * @see it.algos.base.costante.CostanteModello#NOME_CAMPO_SIGLA
     */
    @Override
    protected void regolaViste() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Vista vista = null;
        Campo campo;
        Modulo modCliente;
        Modulo modCamera;
        Modulo modConto;

        try { // prova ad eseguire il codice
            modCliente = ClienteAlbergoModulo.get();
            modCamera = CameraModulo.get();
            modConto = ContoModulo.get();
            continua = (modCliente != null && modCamera != null && modConto != null);

            /* regola la vista di default */
            if (continua) {
                vista = this.getVistaDefault();
                continua = (vista != null);
            }// fine del blocco if

            /* campo cliente */
            if (continua) {
                campo = modCliente.getCampo(Anagrafica.Cam.soggetto.get());
                campo = vista.getCampo(campo);
                if (campo != null) {
                    campo.setLarghezza(150);
//                    campo.setRidimensionabile(false);
                }// fine del blocco if
            }// fine del blocco if

            /* campo camera */
            if (continua) {
                campo = modCamera.getCampo(Camera.Cam.camera.get());
                campo = vista.getCampo(campo);
                if (campo != null) {
                    campo.setTitoloColonna("camera");
                }// fine del blocco if
            }// fine del blocco if

            /* campo conto */
            if (continua) {
                campo = modConto.getCampo(Conto.Cam.sigla.get());
                campo = vista.getCampo(campo);
                if (campo != null) {
                    campo.setTitoloColonna("conto");
                    campo.setLarghezza(200);
//                    campo.setRidimensionabile(false);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazione dei filtri per i popup.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    @Override
    protected void regolaFiltriPop() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;
        WrapFiltri popFiltri;
        int codice;
        String sigla;
        Filtro filtroMezze;
        Filtro unFiltro;

        try { // prova ad eseguire il codice

            Date oggi = AlbergoLib.getDataProgramma();
            Date ieri = Lib.Data.add(oggi, -1);

            /* crea il popup presenze */
            popFiltri = super.addPopFiltro();
            popFiltri.setTitolo(Pop.presenti.get());

            filtro = FiltroFactory.creaFalso(Presenza.Cam.chiuso.get());
            popFiltri.add(filtro, "Presenti adesso");

//            this.filtroArrivatiOggi = PresenzaModulo.getFiltroPresenzeArrivate(oggi);
            filtro = PresenzaModulo.getFiltroPresenzeArrivate(oggi);
            popFiltri.add(filtro, "Arrivati oggi");
//            Method metodo = this.getClass().getMethod("getFiltroArrivatiOggi",null);

            filtro = PresenzaModulo.getFiltroPresenzePartite(oggi);
            popFiltri.add(filtro, "Partiti oggi");

            filtro = new Filtro();
            unFiltro = PresenzaModulo.getFiltroPresenzeUsciteCambio(oggi);
            filtro.add(unFiltro);
            unFiltro = PresenzaModulo.getFiltroPresenzeEntrateCambio(oggi);
            filtro.add(Filtro.Op.OR, unFiltro);
            popFiltri.add(filtro, "Cambiato oggi");

            filtro = PresenzaModulo.getFiltroPresenzeArrivate(ieri);
            popFiltri.add(filtro, "Arrivati ieri");

            filtro = PresenzaModulo.getFiltroPresenzePartite(ieri);
            popFiltri.add(filtro, "Partiti ieri");

            filtro = new Filtro();
            unFiltro = PresenzaModulo.getFiltroPresenzeUsciteCambio(ieri);
            filtro.add(unFiltro);
            unFiltro = PresenzaModulo.getFiltroPresenzeEntrateCambio(ieri);
            filtro.add(Filtro.Op.OR, unFiltro);
            popFiltri.add(filtro, "Cambiato ieri");

            filtro = FiltroFactory.creaVero(Presenza.Cam.chiuso.get());
            popFiltri.add(filtro, "Storico");

            /* crea il popup tipo pensione */
            popFiltri = super.addPopFiltro();
            popFiltri.setTitolo(Pop.pensioni.get());
            popFiltri.setTesto("Tutte");

            codice = Listino.PensioniPeriodo.pensioneCompleta.getCodice();
            sigla = Listino.PensioniPeriodo.pensioneCompleta.getSigla();
            filtro = FiltroFactory.crea(Presenza.Cam.pensione.get(), codice);
            popFiltri.add(filtro, sigla);

            codice = Listino.PensioniPeriodo.mezzaPensione.getCodice();
            sigla = Listino.PensioniPeriodo.mezzaPensione.getSigla();
            filtro = FiltroFactory.crea(Presenza.Cam.pensione.get(), codice);
            popFiltri.add(filtro, sigla);

            codice = Listino.PensioniPeriodo.pernottamento.getCodice();
            sigla = Listino.PensioniPeriodo.pernottamento.getSigla();
            filtro = FiltroFactory.crea(Presenza.Cam.pensione.get(), codice);
            popFiltri.add(filtro, sigla);

            /* crea il popup tipo pasto */
            popFiltri = super.addPopFiltro();
            popFiltri.setTitolo(Pop.pasti.get());

            filtroMezze = FiltroFactory.crea(
                    Presenza.Cam.pensione.get(),
                    Listino.PensioniPeriodo.mezzaPensione.getCodice());

            codice = TipiPasto.breakfast.getCodice();
            sigla = TipiPasto.breakfast.getSigla();
            filtro = FiltroFactory.crea(Presenza.Cam.pasto.get(), codice);
            filtro.add(filtroMezze);
            popFiltri.add(filtro, sigla);

            codice = TipiPasto.lunch.getCodice();
            sigla = TipiPasto.lunch.getSigla();
            filtro = FiltroFactory.crea(Presenza.Cam.pasto.get(), codice);
            filtro.add(filtroMezze);
            popFiltri.add(filtro, sigla);

            codice = TipiPasto.dinner.getCodice();
            sigla = TipiPasto.dinner.getSigla();
            filtro = FiltroFactory.crea(Presenza.Cam.pasto.get(), codice);
            filtro.add(filtroMezze);
            popFiltri.add(filtro, sigla);

            /* crea il popup adulti/bambini */
            popFiltri = super.addPopFiltro();
            popFiltri.setTitolo(Pop.adultibambini.get());

            filtro = FiltroFactory.creaFalso(Presenza.Cam.bambino.get());
            popFiltri.add(filtro, "Adulti");

            filtro = FiltroFactory.creaVero(Presenza.Cam.bambino.get());
            popFiltri.add(filtro, "Bambini");

//            /* crea il popup presenze registrate per la PS */
//            popFiltri = super.addPopFiltro();
//            popFiltri.setTitolo(Pop.registrati.get());
//
//            filtro = FiltroFactory.crea(Presenza.Cam.ps.get(), Filtro.Op.MAGGIORE, 0);
//            popFiltri.add(filtro, "Registrati");
//
//            filtro = FiltroFactory.crea(Presenza.Cam.ps.get(), 0);
//            popFiltri.add(filtro, "Non registrati");

//            /* crea il popup presenze arrivate esterno /cambi */
//            popFiltri = super.addPopFiltro();
//            popFiltri.setTitolo(Pop.arrivati.get());
//
//            filtro = FiltroFactory.creaFalso(Presenza.Cam.cambioEntrata.get());
//            popFiltri.add(filtro, "Nuovo arrivo");
//
//            filtro = FiltroFactory.creaVero(Presenza.Cam.cambioEntrata.get());
//            popFiltri.add(filtro, "Cambio camera");

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    @Override
    protected boolean nuovoRecordPost(int codice, SetValori set, Connessione conn) {
        return super.nuovoRecordPost( codice, set, conn);
    }


    @Override
    protected boolean registraRecordPost(int codice, SetValori set, SetValori setPre, Connessione conn) {
        return super.registraRecordPost(codice,set,setPre,conn);
    }


    /**
     * Sincronizza la data di ultimo soggiorno.
     * <p>
     */
    private void syncUltSoggiorno(SetValori sv) {
        try {    // prova ad eseguire il codice
            ;
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }




}// fine della classe