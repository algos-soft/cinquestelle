/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 16-5-2007
 */

package it.algos.albergo.prenotazione.periodo;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.camera.composizione.CompoCamera;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.lib.RendererPasto;
import it.algos.albergo.listino.Listino;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.prenotazione.periodo.periodoaddebito.AddebitoPeriodo;
import it.algos.albergo.prenotazione.periodo.periodoaddebito.AddebitoPeriodoModulo;
import it.algos.albergo.prenotazione.periodo.periodorisorsa.RisorsaPeriodo;
import it.algos.albergo.prenotazione.periodo.serviziospecifico.ServizioSpecifico;
import it.algos.albergo.prenotazione.periodo.serviziospecifico.ServizioSpecificoModulo;
import it.algos.albergo.prenotazione.periodo.serviziperiodo.ServizioPeriodo;
import it.algos.albergo.prenotazione.periodo.serviziperiodo.ServizioPeriodoModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.logica.CampoLogica;
import it.algos.base.database.Db;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.evento.db.DbTriggerAz;
import it.algos.base.evento.db.DbTriggerEve;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modello.Modello;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.tavola.renderer.RendererBase;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.SetValori;
import it.algos.base.wrapper.TestoAlgos;
import it.algos.gestione.anagrafica.Anagrafica;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.util.ArrayList;

/**
 * Tracciato record della tavola Periodi.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 16-5-2007
 */
public final class PeriodoModello extends ModelloAlgos implements Periodo {

    /**
     * Costruttore completo senza parametri.
     */
    public PeriodoModello() {
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
        super.setTavolaArchivio(Periodo.NOME_TAVOLA);

        /* attiva il trigger di modifica con uso dei valori precedenti */
        this.setTriggerModificaAttivo(true, true);

        /* attiva il trigger di eliminazione con uso dei valori precedenti */
        this.setTriggerEliminaAttivo(true, true);
    }


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
        boolean continua;
        Modulo modulo;
        Modello modello = null;

        try { // prova ad eseguire il codice
            inizializzato = super.inizializza(unModulo);

            /* per ricevere gli eventi trigger */

            modulo = AddebitoPeriodoModulo.get();
            continua = (modulo != null);

            if (continua) {
                modello = modulo.getModello();
                continua = (modello != null);
            }// fine del blocco if

            if (continua) {
                modello.addListener(Modello.Evento.trigger, new AzTriggerAddebito());
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return inizializzato;
    } /* fine del metodo */


    /**
     * Creazione dei campi.
     * <p/>
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
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;
        Modulo modulo;
        Navigatore nav;
        CampoLogica cl;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo link alla prenotazione */
            unCampo = CampoFactory.link(Cam.prenotazione);
            unCampo.setNomeModuloLinkato(Prenotazione.NOME_MODULO);
            unCampo.setAzioneDelete(Db.Azione.cascade);
            this.addCampo(unCampo);

            /* campo arrivo previsto */
            unCampo = CampoFactory.data(Cam.arrivoPrevisto);
            unCampo.decora().obbligatorio();
            unCampo.setInit(null);            
            this.addCampo(unCampo);

            /* campo arrivo effettivo */
            unCampo = CampoFactory.data(Cam.arrivoEffettivo);
            unCampo.setInit(null);
            this.addCampo(unCampo);

            /* campo partenza prevista */
            unCampo = CampoFactory.data(Cam.partenzaPrevista);
            unCampo.decora().obbligatorio();
            unCampo.setInit(null);
            this.addCampo(unCampo);

            /* campo partenza effettiva */
            unCampo = CampoFactory.data(Cam.partenzaEffettiva);
            unCampo.setInit(null);
            this.addCampo(unCampo);

            /* campo data fine addebiti (calcolato, partenzaPrevista -1 giorno) */
            unCampo = CampoFactory.data(Cam.dataFineAddebiti);
            cl = new CLFineAddebiti(unCampo, Periodo.Cam.partenzaPrevista);
            unCampo.setCampoLogica(cl);
            unCampo.setInit(null);
            this.addCampo(unCampo);

            /* campo giorni (differenza date) */
            unCampo = CampoFactory.calcola(Cam.giorni,
                    CampoLogica.Calcolo.differenzaDate,
                    Cam.arrivoPrevisto.get(),
                    Cam.partenzaPrevista.get());
            unCampo.getCampoDB().setCampoFisico(true);
            unCampo.setLarLista(40);
            unCampo.setLarScheda(30);
            unCampo.setAllineamentoLista(SwingConstants.CENTER);
            this.addCampo(unCampo);

            /* campo camera */
            unCampo = CampoFactory.comboLinkSel(Cam.camera);
            unCampo.setNomeModuloLinkato(Camera.NOME_MODULO);
            unCampo.setNomeCampoValoriLinkato(Camera.Cam.camera.get());
            unCampo.setUsaNuovo(false);
            unCampo.decora().obbligatorio();
            this.addCampo(unCampo);

            /* campo preparazione camera */
            unCampo = CampoFactory.comboLinkPop(Cam.preparazione);
            unCampo.setNomeModuloLinkato(CompoCamera.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(CompoCamera.Cam.sigla.get());
            unCampo.setNomeCampoValoriLinkato(CompoCamera.Cam.sigla.get());
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.setLarScheda(85);
            this.addCampo(unCampo);

            /* campo note preparazione */
            unCampo = CampoFactory.testo(Cam.noteprep);
            unCampo.setLarScheda(150);
            this.addCampo(unCampo);

            /* campo numero adulti */
            unCampo = CampoFactory.intero(Cam.adulti);
            unCampo.setLarghezza(30);
            this.addCampo(unCampo);

            /* campo numero bambini */
            unCampo = CampoFactory.intero(Cam.bambini);
            unCampo.setLarghezza(30);
            this.addCampo(unCampo);

            /* campo numero persone occupanti la camera */
            unCampo = CampoFactory.calcola(Cam.persone,
                    CampoLogica.Calcolo.sommaIntero,
                    Cam.adulti,
                    Cam.bambini);
            unCampo.getCampoDB().setCampoFisico(true);
            unCampo.setLarLista(55);
            unCampo.setLarScheda(30);
            unCampo.setAllineamentoLista(SwingConstants.CENTER);
//            unCampo.decora().obbligatorio();
            unCampo.setTotalizzabile(true);
            this.addCampo(unCampo);

            /* campo trattamento */
            unCampo = CampoFactory.comboInterno(Cam.trattamento);
            unCampo.setValoriInterni(Listino.PensioniPeriodo.values());
            unCampo.setLarScheda(60);
            unCampo.setLarLista(40);
            unCampo.decora().obbligatorio();
            unCampo.setUsaNonSpecificato(false);
            unCampo.setAllineamentoLista(SwingConstants.CENTER);
            this.addCampo(unCampo);

            /* campo pasto in caso di mezza pensione */
            unCampo = CampoFactory.comboInterno(Cam.pasto);
            unCampo.setValoriInterni(Presenza.TipiPasto.values());
            unCampo.setRenderer(new RendererPasto(unCampo));
            this.addCampo(unCampo);


            /* campo addebiti */
            unCampo = CampoFactory.navigatore(Cam.addebiti.get(),
                    AddebitoPeriodo.NOME_MODULO,
                    AddebitoPeriodo.Nav.navPeriodo.get());
            unCampo.decora().eliminaEtichetta();
            this.addCampo(unCampo);

            /* campo servizi standard */
            modulo = ServizioPeriodoModulo.get();
            nav = modulo.getNavigatore(ServizioPeriodo.Nav.periodo.get());
            unCampo = CampoFactory.navigatore(Cam.serviziStandard, nav);
            this.addCampo(unCampo);

            /* campo servizi specifici */
            modulo = ServizioSpecificoModulo.get();
            nav = modulo.getNavigatore(ServizioSpecifico.Nav.periodo.get());
            unCampo = CampoFactory.navigatore(Cam.serviziSpecifici, nav);
            this.addCampo(unCampo);

            /* campo arrivato */
            unCampo = CampoFactory.checkBox(Cam.arrivato);
            unCampo.setLarScheda(75);
            this.addCampo(unCampo);

            /* campo periodo chiuso */
            unCampo = CampoFactory.checkBox(Cam.partito);
            unCampo.setLarScheda(75);
            this.addCampo(unCampo);

            /* campo arrivo con */
            unCampo = CampoFactory.comboInterno(Cam.arrivoCon);
            unCampo.setValoriInterni(TipiAP.getElementiArrivo());
            unCampo.setLarScheda(120);
            this.addCampo(unCampo);

            /* campo arrivo confermato */
            unCampo = CampoFactory.checkBox(Cam.arrivoConfermato);
            unCampo.setTestoComponente("arrivo conf.");
            unCampo.setLarScheda(120);
            this.addCampo(unCampo);

            /* campo partenza con */
            unCampo = CampoFactory.comboInterno(Cam.partenzaCon);
            unCampo.setValoriInterni(TipiAP.getElementiPartenza());
            unCampo.setLarScheda(120);
            this.addCampo(unCampo);

            /* campo partenza confermata */
            unCampo = CampoFactory.checkBox(Cam.partenzaConfermata);
            unCampo.setTestoComponente("partenza conf.");
            unCampo.setLarScheda(120);
            this.addCampo(unCampo);

            /* campo causale arrivo  */
            unCampo = CampoFactory.comboInterno(Cam.causaleArrivo);
            unCampo.setValoriInterni(CausaleAP.values());
            unCampo.setModificabileLista(false);
            unCampo.setLarghezza(60);
            unCampo.setRidimensionabile(false);
            this.addCampo(unCampo);

            /* campo causale partenza  */
            unCampo = CampoFactory.comboInterno(Cam.causalePartenza);
            unCampo.setValoriInterni(CausaleAP.values());
            unCampo.setModificabileLista(false);
            unCampo.setLarghezza(60);
            unCampo.setRidimensionabile(false);
            this.addCampo(unCampo);

            /* campo link periodo provenienza (ha senso solo se la causaleArrivo è un cambio camera)  */
            unCampo = CampoFactory.intero(Cam.linkProvenienza);
            unCampo.setRenderer(new RendererCameraLink(unCampo, true));
            unCampo.setLarLista(70);
            this.addCampo(unCampo);

            /* campo link periodo destinazione (ha senso solo se la causalePartenza è un cambio camera)  */
            unCampo = CampoFactory.intero(Cam.linkDestinazione);
            unCampo.setRenderer(new RendererCameraLink(unCampo, false));
            unCampo.setLarLista(70);
            this.addCampo(unCampo);

            /* campo presenze adulti (n. adulti x n. giorni) */
            unCampo = CampoFactory.calcola(Cam.presenzeAdulti,
                    CampoLogica.Calcolo.prodottoIntero,
                    Cam.adulti,
                    Cam.giorni);
            unCampo.getCampoDB().setCampoFisico(true);
            this.addCampo(unCampo);

            /* campo presenze bambini (n. bambini x n. giorni) */
            unCampo = CampoFactory.calcola(Cam.presenzeBambini,
                    CampoLogica.Calcolo.prodottoIntero,
                    Cam.bambini,
                    Cam.giorni);
            unCampo.getCampoDB().setCampoFisico(true);
            this.addCampo(unCampo);

            /* campo presenze generate (presenze adulti + presenze bambini) */
            unCampo = CampoFactory.calcola(Cam.presenze,
                    CampoLogica.Calcolo.sommaIntero,
                    Cam.presenzeAdulti,
                    Cam.presenzeBambini);
            unCampo.getCampoDB().setCampoFisico(true);
            unCampo.setTotalizzabile(true);
            unCampo.setLarLista(40);
            unCampo.setLarScheda(30);
            this.addCampo(unCampo);

            /* campo valore del periodo */
            unCampo = CampoFactory.valuta(Cam.valore);
            unCampo.setLarghezza(70);
            unCampo.setRidimensionabile(false);
            unCampo.setTotalizzabile(true);
            this.addCampo(unCampo);

            /* campo note */
            unCampo = CampoFactory.testo(Cam.note);
            unCampo.setLarLista(100);
            unCampo.setLarScheda(150);
            unCampo.setRidimensionabile(true);
            this.addCampo(unCampo);

            /* campo info (non fisico) */
            unCampo = CampoFactory.testo(Cam.info);
            unCampo.getCampoDB().setCampoFisico(false);
            unCampo.setRenderer(new RendererInfo(unCampo));
            unCampo.setLarLista(50);
            this.addCampo(unCampo);

            /* campo risorse impegnate */
            unCampo = CampoFactory.navigatore(Cam.risorse.get(),
                    RisorsaPeriodo.NOME_MODULO,
                    RisorsaPeriodo.Nav.navPeriodo.get());
            unCampo.decora().eliminaEtichetta();
            this.addCampo(unCampo);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Eventuale creazione di viste aggiuntive, oltre alla vista base di default <br>
     * Costruisce degli ArrayList di riferimenti ordinati (oggetti <code>Vista</code>)
     * per individuare i campi che voglio vedere nelle liste alternative ed
     * aggiuntive a quella standard (costruita in automatico nella superclasse) <br>
     * Gli array vengono creati coi campi di questo modello, oppure con
     * viste di altri moduli, oppure con campi di altri modelli <br>
     * Viene chiamato <strong>dopo</strong> che nella sottoclasse sono stati
     * costruiti tutti i campi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.progetto.Progetto#preparaModuli()
     * @see #regolaViste
     */
    protected void creaViste() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo mod;
        Vista vista;
        VistaElemento elem;
        Campo campo;

        try { // prova ad eseguire il codice
            super.creaViste();

            /* crea la vista per il navigatore nella prenotazione */
            vista = this.creaVista(Periodo.Vis.prenotazione.toString());
            vista.addCampo(Periodo.Cam.linkProvenienza.get());
            vista.addCampo(Periodo.Cam.arrivoPrevisto.get());
            vista.addCampo(Periodo.Cam.partenzaPrevista.get());
            elem = vista.addCampo(Periodo.Cam.camera.get());
            elem.setLarghezzaColonna(60);
            elem.setRidimensionabile(false);
            elem.setTitoloColonna("camera");
            elem.setAllineamentoLista(SwingConstants.CENTER);
            elem = vista.addCampo(Periodo.Cam.preparazione.get());
            elem.setLarghezzaColonna(50);
            elem.setTitoloColonna("prep");
            elem.setAllineamentoLista(SwingConstants.CENTER);
            vista.addCampo(Periodo.Cam.persone.get());
            vista.addCampo(Periodo.Cam.trattamento.get());
            vista.addCampo(Periodo.Cam.arrivato.get());
            vista.addCampo(Periodo.Cam.partito.get());
            vista.addCampo(Periodo.Cam.giorni.get());
            vista.addCampo(Periodo.Cam.presenze.get());
            elem = vista.addCampo(Periodo.Cam.valore.get());
            elem.setTitoloColonna("spesa");
            vista.addCampo(Periodo.Cam.note.get());
            vista.addCampo(Periodo.Cam.linkDestinazione.get());
            this.addVista(vista);

            mod = ClienteAlbergoModulo.get();
            continua = (mod != null);

            /* crea la vista per il navigatore degli arrivi */
            if (continua) {
                vista = this.creaVista(Periodo.Vis.arrivi.toString());
                vista.addCampo(Cam.arrivato);
                campo = mod.getCampo(Anagrafica.Cam.soggetto);
                elem = vista.addCampo(campo);
                elem.setTitoloColonna("cliente");
                elem.setLarghezzaColonna(110);
                elem = vista.addCampo(Cam.arrivoPrevisto);
                elem.setTitoloColonna("arrivo");
                elem = vista.addCampo(Cam.partenzaPrevista);
                elem.setTitoloColonna("partenza");
                elem = vista.addCampo(Cam.camera);
                elem.setTitoloColonna("cam");
                elem.setLarghezzaColonna(40);
                elem = vista.addCampo(Cam.trattamento);
                elem.setTitoloColonna("trat");
                elem.setLarghezzaColonna(30);
                elem = vista.addCampo(Cam.persone);
                elem.setTitoloColonna("pers");
                elem.setLarghezzaColonna(40);
                elem = vista.addCampo(Cam.info);
                elem.setTitoloColonna("conf");
                elem.setLarghezzaColonna(30);
                this.addVista(vista);
            }// fine del blocco if

            /* crea la vista per il navigatore delle partenze */
            if (continua) {
                vista = this.creaVista(Periodo.Vis.partenze.toString());
                vista.addCampo(Cam.partito);
                campo = mod.getCampo(Anagrafica.Cam.soggetto);
                elem = vista.addCampo(campo);
                elem.setTitoloColonna("cliente");
                elem.setLarghezzaColonna(110);
                elem = vista.addCampo(Cam.partenzaPrevista);
                elem.setTitoloColonna("partenza");
                elem = vista.addCampo(Cam.camera);
                elem.setTitoloColonna("cam");
                elem.setLarghezzaColonna(40);
                vista.addCampo(Periodo.Cam.persone);

                /* campo non fisico per visualizzare lo stato dei conti */
                campo = CampoFactory.testo("conto");
                campo.getCampoDB().setCampoFisico(false);
                campo.setLarghezza(40);
                campo.setRenderer(new RendererContiChiusi(campo));
                vista.addCampo(campo);

                this.addVista(vista);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


//    protected void regolaViste() {
//        /* variabili e costanti locali di lavoro */
//        Vista vista;
//        Campo campo;
//
//        try { // prova ad eseguire il codice
//
//            /* regola la vista del navigatore Arrivi */
//            vista = this.getVista(Periodo.Vis.arrivi);
//            if (vista != null) {
//                campo = vista.getCampo(Cam.persone);
//                if (campo != null) {
//                    campo.setRenderer(new RendererNumArrivi(campo));
//                }// fine del blocco if
//            }// fine del blocco if
//
//            /* regola la vista del navigatore Partenze */
//            vista = this.getVista(Periodo.Vis.partenze);
//            if (vista != null) {
//                campo = vista.getCampo(Cam.persone);
//                if (campo != null) {
//                    campo.setRenderer(new RendererNumPartenze(campo));
//                }// fine del blocco if
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        super.regolaViste();
//    }


    /**
     * Metodo invocato prima della creazione di un nuovo record.
     * <p/>
     * Puo' modificare i valori che stanno per essere registrati<br>
     * Viene sovrascritto dalle classi specifiche <br>
     * Le eventuali modifiche vanno fatte sulla lista che viene
     * passata come parametro.
     *
     * @param lista array coppia campo-valore contenente i
     *              dati che stanno per essere registrati
     * @param conn  la connessione utilizzata per effettuare la query
     *
     * @return true per continuare il processo di registrazione,
     *         false per non effettuare la registrazione
     */
    @Override
    protected boolean nuovoRecordAnte(ArrayList<CampoValore> lista, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        CampoValore cv;
        Campo unCampo;

        try { // prova ad eseguire il codice

            /* causale arrivo di default normale */
            unCampo = this.getCampo(Cam.causaleArrivo.get());
            cv = Lib.Camp.getCampoValore(lista, unCampo);
            if (cv == null) {
                cv = new CampoValore(unCampo);
            }// fine del blocco if
            cv.setValore(CausaleAP.normale.getCodice());

            /* causale partenza di default normale */
            unCampo = this.getCampo(Cam.causalePartenza.get());
            cv = Lib.Camp.getCampoValore(lista, unCampo);
            if (cv == null) {
                cv = new CampoValore(unCampo);
            }// fine del blocco if
            cv.setValore(CausaleAP.normale.getCodice());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
        return super.nuovoRecordAnte(lista, conn);
    } // fine del metodo


    protected boolean nuovoRecordPost(int codice, SetValori set, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        int codPrenotazione;

        try { // prova ad eseguire il codice

            riuscito = super.nuovoRecordPost(codice, set, conn);

            /**
             * se la query conteneva il campo "chiuso",
             * sincronizza il flag chiuso della prenotazione
             */
            if (riuscito) {
                if (set.isContieneCampo(Cam.partito)) {
                    codPrenotazione = getCodPrenotazione(codice, conn);
                    riuscito = PrenotazioneModulo.syncStatoPrenotazione(codPrenotazione, conn);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;

    } // fine del metodo


    protected boolean registraRecordAnte(int codice,
                                         ArrayList<CampoValore> lista,
                                         Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;

        try { // prova ad eseguire il codice
            riuscito = super.registraRecordAnte(codice, lista, conn);
            if (riuscito) {
                int a = 87;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    } // fine del metodo


    protected boolean registraRecordPost(int codice,
                                         ArrayList<CampoValore> lista,
                                         ArrayList<CampoValore> listaPre,
                                         Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        int codPrenotazione;
        SetValori svCurr;
        SetValori svPre;

        try { // prova ad eseguire il codice

            riuscito = super.registraRecordPost(codice, lista, listaPre, conn);

            /**
             * Se è stato modificato il numero di adulti o bambini, e
             * si tratta di un cambio, garantisce che i periodi
             * linkati (se non già arrivati) abbiano lo stesso numero di adulti/bambini
             */
            if (riuscito) {
                svCurr = new SetValori(this.getModulo(), lista);
                svPre = new SetValori(this.getModulo(), listaPre);
                riuscito = this.checkABcambio(codice, svCurr, svPre, conn);
            }// fine del blocco if

            /**
             * se la query conteneva il campo "chiuso",
             * sincronizza il flag chiuso della prenotazione
             */
            if (riuscito) {
                if (isContieneCampoChiuso(lista)) {
                    codPrenotazione = getCodPrenotazione(codice, conn);
                    riuscito = PrenotazioneModulo.syncStatoPrenotazione(codPrenotazione, conn);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;

    } // fine del metodo


    /**
     * Metodo invocato prima della eliminazione di un record esistente.
     * <p/>
     * Viene sovrascritto dalle classi specifiche <br>
     *
     * @param codice del record da eliminare
     * @param conn   la connessione utilizzata per effettuare la query
     *
     * @return true per continuare il processo di eliminazione,
     *         false per non effettuare la eliminazione
     */
    protected boolean eliminaRecordAnte(int codice, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        int codCausale;
        int codCambio = CausaleAP.cambio.getCodice();
        int codNormale = CausaleAP.normale.getCodice();
        int codPeriLinkato;
        SetValori sv;
        ArrayList<CampoValore> listaCV;
        Modulo modPeriodo;

        try { // prova ad eseguire il codice

            continua = super.eliminaRecordAnte(codice, conn);

            if (continua) {
                modPeriodo = PeriodoModulo.get();

                /* se il periodo da eliminare è collegato in entrata,
                 * rimuove il collegamento dal periodo collegato */
                codCausale = this.query().valoreInt(Cam.causaleArrivo.get(), codice);
                if (codCausale == codCambio) {
                    codPeriLinkato = this.query().valoreInt(Cam.linkProvenienza.get(), codice);
                    sv = new SetValori(modPeriodo);
                    sv.add(Cam.causalePartenza.get(), codNormale);
                    sv.add(Cam.linkDestinazione.get(), 0);
                    listaCV = sv.getListaValori();
                    continua = this.query().registraRecordValori(codPeriLinkato, listaCV, conn);
                }// fine del blocco if

                /* se il periodo da eliminare è collegato in uscita,
                 * rimuove il collegamento dal periodo collegato */
                codCausale = this.query().valoreInt(Cam.causalePartenza.get(), codice);
                if (codCausale == codCambio) {
                    codPeriLinkato = this.query().valoreInt(Cam.linkDestinazione.get(), codice);
                    sv = new SetValori(modPeriodo);
                    sv.add(Cam.causaleArrivo.get(), codNormale);
                    sv.add(Cam.linkProvenienza.get(), 0);
                    listaCV = sv.getListaValori();
                    continua = this.query().registraRecordValori(codPeriLinkato, listaCV, conn);
                }// fine del blocco if

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;

    } // fine del metodo


    protected boolean eliminaRecordPost(int codice,
                                        ArrayList<CampoValore> listaPre,
                                        Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        int codPrenotazione;
        Campo campoLink;
        CampoValore cv;
        Object ogg;

        try { // prova ad eseguire il codice

            riuscito = super.eliminaRecordPost(codice, listaPre, conn);

            /**
             * sincronizza il flag chiuso della prenotazione
             */
            if (riuscito) {
                campoLink = this.getCampo(Cam.prenotazione.get());
                if (campoLink != null) {
                    cv = Lib.Camp.getCampoValore(listaPre, campoLink);
                    if (cv != null) {
                        ogg = cv.getValore();
                        codPrenotazione = Libreria.getInt(ogg);
                        if (codPrenotazione != 0) {
                            riuscito = PrenotazioneModulo.syncStatoPrenotazione(codPrenotazione,
                                    conn);
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;

    } // fine del metodo


    /**
     * Recupera il codice prenotazione relativo a un periodo.
     * <p/>
     * Invocato dopo creazione, modifica o cancellazione di un periodo
     * Se la prenotazione ha tutti i periodi chiusi la pone Chiusa
     * Se la prenotazione non ha tutti i periodi chiusi la pone Aperta
     * Se la prenotazione non periodi non la modifica
     *
     * @param codPeriodo codice del periodo
     * @param conn       la connessione da utilizzare per le query
     *
     * @return il codice della prenotazione
     */
    private static int getCodPrenotazione(int codPeriodo, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int codPrenotazione = 0;
        Modulo modPeriodo;

        try {    // prova ad eseguire il codice
            modPeriodo = PeriodoModulo.get();
            if (modPeriodo != null) {
                codPrenotazione = modPeriodo.query()
                        .valoreInt(Periodo.Cam.prenotazione.get(), codPeriodo, conn);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codPrenotazione;
    }


    /**
     * Sincronizza il periodo agganciato con cambio.
     * <p/>
     *
     * @param codPeriodo codice del periodo che è stato appena registrato
     * @param setPost    setValori dopo la modifica
     * @param setPre     setValori prima della modifica
     * @param conn       la connessione da utilizzare
     * @return true se riuscito
     */
    private boolean checkABcambio(int codPeriodo,
                                  SetValori setPost,
                                  SetValori setPre,
                                  Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        boolean continua;
        int causaleArrivo = 0;
        int causalePartenza = 0;
        int codCausaleCambio = 0;
        int adultiPost = 0;
        int adultiPre;
        int bambiniPost = 0;
        int bambiniPre;
        boolean syncAdulti = false;
        boolean syncBambini = false;
        ArrayList<Integer> periodiLink = null;
        int codice;


        try {    // prova ad eseguire il codice

            /* il set deve contenere adulti o bambini */
            continua = (setPost.isContieneCampo(Periodo.Cam.adulti) || setPost.isContieneCampo(
                    Periodo.Cam.bambini));

            /**
             * il periodo che è stato modificato deve avere
             * cambio in entrata o in uscita
             */
            if (continua) {
                causaleArrivo = this.query().valoreInt(Periodo.Cam.causaleArrivo.get(),
                        codPeriodo,
                        conn);
                causalePartenza = this.query().valoreInt(Periodo.Cam.causalePartenza.get(),
                        codPeriodo,
                        conn);
                codCausaleCambio = Periodo.CausaleAP.cambio.getCodice();
                continua =
                        ((causaleArrivo == codCausaleCambio) ||
                                (causalePartenza == codCausaleCambio));
            }// fine del blocco if

            /**
             * deve essere stato modificato il numero di adulti o di bambini
             * accende i flag per sincronizzazione di adulti e/o bambini
             */
            if (continua) {

                if (setPost.isContieneCampo(Periodo.Cam.adulti)) {
                    adultiPost = Libreria.getInt(setPost.getValore(Periodo.Cam.adulti));
                    adultiPre = Libreria.getInt(setPre.getValore(Periodo.Cam.adulti));
                    if (adultiPost != adultiPre) {
                        syncAdulti = true;
                    }// fine del blocco if
                }// fine del blocco if

                if (setPost.isContieneCampo(Periodo.Cam.bambini)) {
                    bambiniPost = Libreria.getInt(setPost.getValore(Periodo.Cam.bambini));
                    bambiniPre = Libreria.getInt(setPre.getValore(Periodo.Cam.bambini));
                    if (bambiniPost != bambiniPre) {
                        syncBambini = true;
                    }// fine del blocco if
                }// fine del blocco if

                continua = ((syncAdulti) || (syncBambini));
            }// fine del blocco if

            /* recupera i codici dei periodi collegati (possono essere 1 o 2) */
            if (continua) {
                periodiLink = new ArrayList<Integer>();
                if (causaleArrivo == codCausaleCambio) {
                    codice = this.query().valoreInt(Periodo.Cam.linkProvenienza.get(),
                            codPeriodo,
                            conn);
                    periodiLink.add(codice);
                }// fine del blocco if
                if (causalePartenza == codCausaleCambio) {
                    codice = this.query().valoreInt(Periodo.Cam.linkDestinazione.get(),
                            codPeriodo,
                            conn);
                    periodiLink.add(codice);
                }// fine del blocco if
                continua = (periodiLink.size() > 0);
            }// fine del blocco if


            /* modifica i periodi collegati (non già chiusi)*/
            if (continua) {
                for (int cod : periodiLink) {

                    boolean arrivato = this.query().valoreBool(Periodo.Cam.arrivato.get(), cod, conn);

                    if (!arrivato) {
                        if (syncAdulti) {
                            riuscito = this.query().registra(cod,
                                    Periodo.Cam.adulti.get(),
                                    adultiPost,
                                    conn);
                        }// fine del blocco if

                        if (syncBambini) {
                            riuscito = this.query().registra(cod,
                                    Periodo.Cam.bambini.get(),
                                    bambiniPost,
                                    conn);
                        }// fine del blocco if

                        if (!riuscito) {
                            break;
                        }// fine del blocco if
                    }// fine del blocco if


                }

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Determina se una lista di oggetti CampoValore
     * contiene il campo "chiuso" di Periodo.
     * <p/>
     *
     * @param lista da esaminare
     *
     * @return true se contiene il campo "chiuso"
     */
    private static boolean isContieneCampoChiuso(ArrayList<CampoValore> lista) {
        /* variabili e costanti locali di lavoro */
        boolean trovato = false;
        Campo campoChiuso;
        CampoValore cv;

        try {    // prova ad eseguire il codice
            campoChiuso = PeriodoModulo.get().getCampo(Cam.partito);
            cv = Lib.Camp.getCampoValore(lista, campoChiuso);
            trovato = (cv != null);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return trovato;
    }


    /**
     * Azione eseguita quando viene creata/modificata/cancellata una riga di addebito.
     * <p/>
     */
    private class AzTriggerAddebito extends DbTriggerAz {

        /**
         * dbTriggerAz, da DbTriggerLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param evento evento che causa l'azione da eseguire <br>
         */
        public void dbTriggerAz(DbTriggerEve evento) {
            /* variabili e costanti locali di lavoro */
            boolean continua;
            Campo campoValTesta;
            Campo campoValRiga = null;
            Modulo modRighe = null;
            Double totale;
            int codiceRiga;
            int codiceTesta = 0;
            Connessione conn;

            try { // prova ad eseguire il codice
                conn = evento.getConn();
                codiceRiga = evento.getCodice();
                continua = (codiceRiga > 0);

                if (continua) {
                    campoValTesta = getCampo(Cam.valore);
                    continua = (campoValTesta != null);
                }// fine del blocco if

                if (continua) {
                    modRighe = AddebitoPeriodoModulo.get();
                    continua = (modRighe != null);
                }// fine del blocco if

                if (continua) {
                    codiceTesta = modRighe.query()
                            .valoreInt(AddebitoPeriodo.Cam.periodo.get(), codiceRiga);
                    continua = (codiceTesta > 0);
                }// fine del blocco if

                if (continua) {
                    campoValRiga = modRighe.getCampo(AddebitoPeriodo.Cam.valore);
                    continua = (campoValRiga != null);
                }// fine del blocco if

                /* determina il nuovo totale imponibile lordo
                 * sommando gli imponibili delle righe */
                if (continua) {
                    totale = (Double)modRighe.query().somma(campoValRiga,
                            AddebitoPeriodo.Cam.periodo.get(),
                            codiceTesta,
                            conn);

                    query().registra(codiceTesta, Cam.valore.get(), totale);

                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    }


    /**
     * Classe 'interna'.<p>
     * Renderer del campo Camera Periodo linkato
     */
    private final class RendererCameraLink extends RendererBase {

        private Modulo modPeriodo;

        private Campo campoCamera;

        private Query query;

        private Icon iconaPartenza;

        private Icon iconaArrivo;

        private Icon iconaCambio;

        private JLabel label;

        private boolean inizio;


        /**
         * Costruttore completo con parametri. <br>
         *
         * @param campo  di riferimento
         * @param inizio true se è il link di provenienza false se destinazione
         */
        public RendererCameraLink(Campo campo, boolean inizio) {
            /* rimanda al costruttore della superclasse */
            super(campo);

            /* regola le variabili di istanza coi parametri */
            this.inizio = inizio;

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
            Modulo modCamera;

            try { // prova ad eseguire il codice
                modPeriodo = PeriodoModulo.get();
                modCamera = CameraModulo.get();
                campoCamera = modCamera.getCampo(Camera.Cam.camera);
                query = new QuerySelezione(modPeriodo);
                query.addCampo(campoCamera);

                iconaPartenza = modPeriodo.getIcona("bandierina");
                iconaArrivo = modPeriodo.getIcona("automobile");
                iconaCambio = modPeriodo.getIcona("frecciacambio");

                label = new JLabel();
                label.setOpaque(true);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);
                label.setVerticalTextPosition(JLabel.CENTER);
                TestoAlgos.setListaBold(label);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public Component getTableCellRendererComponent(JTable jTable,
                                                       Object o,
                                                       boolean isSelected,
                                                       boolean b1,
                                                       int i,
                                                       int i1) {
            /* variabili e costanti locali di lavoro */
            int codProvenienza;
            String testo;
            int pos;

            try { // prova ad eseguire il codice

                /* regola icona e testo */
                codProvenienza = Libreria.getInt(o);
                if (codProvenienza > 0) {
                    testo = this.getNomeCamera(codProvenienza);
                    label.setText(testo);
                    label.setIcon(iconaCambio);
                    if (inizio) {
                        pos = SwingConstants.LEADING;
                    } else {
                        pos = SwingConstants.TRAILING;
                    }// fine del blocco if-else
                    label.setHorizontalTextPosition(pos);

                } else {
                    label.setText("");
                    if (inizio) {
                        label.setIcon(iconaArrivo);
                    } else {
                        label.setIcon(iconaPartenza);
                    }// fine del blocco if-else
                }// fine del blocco if-else

                /** regola i colori della label
                 * in funzione del fatto che la riga sia
                 * selezionata o meno*/
                label.setOpaque(true); // devo rimetterlo se no non funziona
                if (isSelected) {
                    label.setBackground(jTable.getSelectionBackground());
                    label.setForeground(jTable.getSelectionForeground());
                } else {
                    label.setBackground(jTable.getBackground());
                    label.setForeground(jTable.getForeground());
                }

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return label;
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
            String nomeCamera = "";
            Filtro filtro;
            Dati dati;

            try {    // prova ad eseguire il codice
                filtro = FiltroFactory.codice(modPeriodo, codPeriodo);
                query.setFiltro(filtro);
                dati = modPeriodo.query().querySelezione(query);
                nomeCamera = dati.getStringAt(0, campoCamera);
                dati.close();
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return nomeCamera;
        }


    } // fine della classe 'interna'


    /**
     * Renderer per il campo Numero Persone In Arrivo / Arrivate
     * </p>
     * Se il periodo non è IN fa vedere il numero di persone previste
     * Se il periodo è IN fa vedere il numero di persone effettivamente arrivate
     */
    private final class RendererNumArrivi extends RendererBase {

        /**
         * Costruttore completo con parametri. <br>
         *
         * @param campo di riferimento
         */
        public RendererNumArrivi(Campo campo) {
            /* rimanda al costruttore della superclasse */
            super(campo);
        }// fine del metodo costruttore completo


        public Component getTableCellRendererComponent(JTable table,
                                                       Object valore,
                                                       boolean selezionata,
                                                       boolean hasFocus,
                                                       int riga,
                                                       int colonna) {

            /* variabili e costanti locali di lavoro */
            int quanti;
            Component comp = null;
            int codPeriodo;

            try { // prova ad eseguire il codice
                codPeriodo = this.getChiaveRiga(riga, table);
                quanti = PeriodoModulo.getNumPersoneArrivo(codPeriodo);
                comp = super.getTableCellRendererComponent(table,
                        quanti,
                        selezionata,
                        hasFocus,
                        riga,
                        colonna);
            } catch (Exception unErrore) {
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return comp;
        }
    } // fine della classe 'interna'


    /**
     * Renderer per il campo Numero Persone In Partenza / Partite
     * </p>
     * Se il periodo non è IN non fa nulla (lascia il numero di persone previste)
     * Se il periodo è IN ma non è OUT fa vedere il numero di persone
     * presenti nella camera e non ancora partite (presenze attuali)
     * Se il periodo è IN e anche OUT fa vedere il numero di persone
     * che erano presenti nella camera alla data della partenza effettiva
     */
    private final class RendererNumPartenze extends RendererBase {

        /**
         * Costruttore completo con parametri. <br>
         *
         * @param campo di riferimento
         */
        public RendererNumPartenze(Campo campo) {
            /* rimanda al costruttore della superclasse */
            super(campo);
        }// fine del metodo costruttore completo


        public Component getTableCellRendererComponent(JTable table,
                                                       Object valore,
                                                       boolean selezionata,
                                                       boolean hasFocus,
                                                       int riga,
                                                       int colonna) {

            /* variabili e costanti locali di lavoro */
            Component comp = null;
            int quanti;
            int codPeriodo;

            try { // prova ad eseguire il codice
                codPeriodo = this.getChiaveRiga(riga, table);
                quanti = PeriodoModulo.getNumPersonePartenza(codPeriodo);
                comp = super.getTableCellRendererComponent(table,
                        quanti,
                        selezionata,
                        hasFocus,
                        riga,
                        colonna);
            } catch (Exception unErrore) {
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return comp;
        }
    } // fine della classe 'interna'


} // fine della classe
