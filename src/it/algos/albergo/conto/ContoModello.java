/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 2 feb 2006
 */

package it.algos.albergo.conto;

import it.algos.albergo.Albergo;
import it.algos.albergo.AlbergoLib;
import it.algos.albergo.camera.Camera;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.conto.addebito.AddebitoModulo;
import it.algos.albergo.conto.addebitofisso.AddebitoFisso;
import it.algos.albergo.conto.movimento.Movimento;
import it.algos.albergo.conto.pagamento.PagamentoModulo;
import it.algos.albergo.conto.sconto.ScontoModulo;
import it.algos.albergo.conto.sospeso.SospesoModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.albergo.tabelle.azienda.Azienda;
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
import it.algos.base.matrice.MatriceDoppia;
import it.algos.base.modello.Modello;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.base.wrapper.WrapFiltri;
import it.algos.gestione.anagrafica.Anagrafica;

import java.util.ArrayList;
import java.util.Date;

/**
 * Tracciato record della tavola Conto.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea il <strong>tracciato record</strong> (Abstract Data Types) di una
 * tavola </li>
 * <li> Crea i <strong>campi</strong> di questo modello (oltre a quelli base
 * della superclasse) nel metodo <code>creaCampi</code> </li>
 * <li> Ogni campo viene creato con un costruttore semplice con solo le piu'
 * comuni informazioni; le altre vengono regolate con chiamate successive </li>
 * <li> Crea eventuali <strong>viste</strong> della <code>Lista</code>
 * (oltre a quella base) nel metodo <code>creaViste</code> </li>
 * <li> Regola eventualmente i valori delle viste nel metodo <code>regolaViste</code> </li>
 * <li> Crea eventuali <strong>set</strong> della <code>Scheda</code>
 * (oltre a quello base) nel metodo <code>creaSet</code> </li>
 * <li> Regola eventualmente i valori dei set nel metodo <code>regolaSet</code> </li>
 * <li> Regola eventualmente i valori da inserire in un <code>nuovoRecord</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 2 feb 2006
 */
public final class ContoModello extends ModelloAlgos implements Conto {

    /**
     * nome della tavola di archivio collegata (facoltativo) <br>
     * i nomi delle tavole sono sempre minuscoli <br>
     * se vuoto usa il nome del modulo <br>
     */
    private static final String TAVOLA_ARCHIVIO = NOME_TAVOLA;

    /**
     * Testo della colonna della Lista come appare nella Vista
     */
    private static final String COLONNA_SIGLA = TITOLO_TABELLA;


    /**
     * Costruttore completo senza parametri.
     */
    public ContoModello() {
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
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* regola il nome della tavola dalla costante */
        super.setTavolaArchivio(TAVOLA_ARCHIVIO);
        this.setTriggerModificaAttivo(true, true);
    }


    /**
     * Inizializza il Modello.<br>
     * Crea i componenti del Modello, li inizializza ed effettua le
     * regolazioni ulteriori.
     *
     * @param unModulo
     *
     * @return true se il modello e' stato inizializzato correttamente
     */
    public boolean inizializza(Modulo unModulo) {
        /* variabili e costanti locali di lavoro */
        Modulo modulo;
        Modello modello;

        try { // prova ad eseguire il codice

            /* si registra presso il modello delle Presenze */
            /* per ricevere gli eventi trigger */
            modulo = PresenzaModulo.get();
            modello = modulo.getModello();
            modello.addListener(Modello.Evento.trigger, new AzTriggerPresenza());

            super.inizializza(unModulo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;

    } // fine del metodo


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
     * @see it.algos.base.campo.video.decorator.VideoFactory
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;
        Modulo modulo;
        Navigatore nav;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo data apertura conto */
            unCampo = CampoFactory.data(Conto.Cam.dataApertura);
            unCampo.decora().obbligatorio();
            unCampo.setVisibileVistaDefault(true);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo link alla camera */
            unCampo = CampoFactory.comboLinkPop(Conto.Cam.camera);
            unCampo.setNomeModuloLinkato(Camera.NOME_MODULO);
            unCampo.setNomeCampoValoriLinkato(Camera.CAMPO_SIGLA);
            unCampo.setUsaNuovo(false);
            unCampo.setLarghezza(80);
            unCampo.setRidimensionabile(false);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo sigla del conto */
            unCampo = CampoFactory.testo(Conto.Cam.sigla);
            unCampo.decora().obbligatorio();
            unCampo.getCampoLista().setRidimensionabile(true);
            unCampo.setLarLista(200);
            unCampo.setLarScheda(200);
            this.addCampo(unCampo);

            /* campo azienda */
            unCampo = CampoFactory.comboLinkPop(Conto.Cam.azienda);
            unCampo.setNomeModuloLinkato(Azienda.NOME_MODULO);
            unCampo.setNomeCampoValoriLinkato(Azienda.CAMPO_SIGLA);
            unCampo.decora().obbligatorio();
            unCampo.setLarScheda(80);
            unCampo.setUsaNonSpecificato(false);
            this.addCampo(unCampo);

            /* campo arrivo con */
            unCampo = CampoFactory.comboInterno(Conto.Cam.arrivoCon);
            unCampo.setValoriInterni(Periodo.TipiAP.getElementiArrivo());
            unCampo.setLarScheda(120);
            this.addCampo(unCampo);

            /* campo link al cliente pagante */
            unCampo = CampoFactory.comboLinkSel(Conto.Cam.pagante);
            unCampo.decora().obbligatorio();
            unCampo.setNomeModuloLinkato(ClienteAlbergo.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(Anagrafica.Cam.soggetto.get());
            unCampo.setNomeCampoValoriLinkato(Anagrafica.Cam.soggetto.get());
            unCampo.addColonnaCombo(ClienteAlbergo.Cam.parentela.get());
            unCampo.addColonnaCombo(ModelloAlgos.NOME_CAMPO_PREFERITO);
            unCampo.setUsaNuovo(false);
            unCampo.setLarghezza(180);
            unCampo.decora().estrattoSotto(Anagrafica.Estratto.descrizione);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo link al periodo di prenotazione al quale è riferito il conto */
            unCampo = CampoFactory.link(Conto.Cam.periodo);
            unCampo.setNomeModuloLinkato(Periodo.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(Periodo.Cam.arrivoPrevisto.get());
            unCampo.setNomeCampoValoriLinkato(Periodo.Cam.arrivoPrevisto.get());
            unCampo.setAbilitato(false);
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.setUsaNuovo(false);
            this.addCampo(unCampo);

            /* campo numero di persone in carico */
            unCampo = CampoFactory.intero(Conto.Cam.numPersone);
            unCampo.setLarLista(30);
            unCampo.setAbilitato(false);
            unCampo.setTotalizzabile(true);
            this.addCampo(unCampo);

//            /* campo link a documento fattura */
//            unCampo = CampoFactory.comboLink(Conto.CAMPO_DOCUMENTO);
//            unCampoDB = unCampo.getCampoDB();
//            unCampo.setNomeModuloLinkato(Fattura.MODULO_FATTURA);
//            unCampoDB.setNomeColonnaListaLinkata(Fattura.CAMPO_NUMERO);
//            unCampoDB.setNomeCampoSchedaLinkato(Fattura.CAMPO_NUMERO);
//            this.addCampo(unCampo);

            /* campo caparra */
            unCampo = CampoFactory.valuta(Conto.Cam.caparra);
            unCampo.setAbilitato(false);
            unCampo.setLarLista(60);
            unCampo.setLarScheda(60);
            this.addCampo(unCampo);

            /* campo ricevuta */
            unCampo = CampoFactory.intero(Conto.Cam.ricFiscale);
            unCampo.setAbilitato(false);
            this.addCampo(unCampo);

            /* campo data inizio validità */
            unCampo = CampoFactory.data(Conto.Cam.validoDal);
            unCampo.setInit(null);
            this.addCampo(unCampo);

            /* campo data fine validità */
            unCampo = CampoFactory.data(Conto.Cam.validoAl);
            unCampo.setInit(null);
            this.addCampo(unCampo);

            /* campo data partenza cliente (effettiva) */
            unCampo = CampoFactory.data(Conto.Cam.partEffettiva);
            unCampo.setInit(null);
            unCampo.setVisibileVistaDefault(false);
            this.addCampo(unCampo);

            /* campo importo totale del conto (somma degli addebiti) */
            unCampo = CampoFactory.valuta(Conto.Cam.totImporto);
            unCampo.setLarLista(60);
            unCampo.setTotalizzabile(true);
            unCampo.setToolTipLista("importo lordo del conto (totale degli addebiti)");
            this.addCampo(unCampo);

            /* campo totale sconto (somma degli sconti) */
            unCampo = CampoFactory.valuta(Conto.Cam.totSconto);
            unCampo.setLarLista(60);
            unCampo.setToolTipLista("importo sconti");
            unCampo.setTotalizzabile(true);
            this.addCampo(unCampo);

            /* campo totale netto (calcolato, importo - sconto) */
            unCampo = CampoFactory.calcola(Conto.Cam.totNetto,
                    CampoLogica.Calcolo.differenzaValuta,
                    Conto.Cam.totImporto,
                    Conto.Cam.totSconto);
            unCampo.setLarLista(60);
            unCampo.getCampoDB().setCampoFisico(true);
            unCampo.setToolTipLista("importo netto a pagare (importo lordo - sconti)");
            unCampo.setTotalizzabile(true);
            this.addCampo(unCampo);

            /* campo totale pagato (somma dei pagamenti) */
            unCampo = CampoFactory.valuta(Conto.Cam.totPagato);
            unCampo.setLarLista(60);
            unCampo.setToolTipLista("importo già pagato (caparre, acconti, saldi)");
            unCampo.setTotalizzabile(true);
            this.addCampo(unCampo);

            /* campo totale non pagato (calcolato, netto a pagare - pagato) */
            unCampo = CampoFactory.calcola(Conto.Cam.totNonPagato,
                    CampoLogica.Calcolo.differenzaValuta,
                    Conto.Cam.totNetto,
                    Conto.Cam.totPagato);
            unCampo.getCampoDB().setCampoFisico(true);
            unCampo.setLarLista(60);
            unCampo.setToolTipLista("importo ancora da pagare (netto a pagare - pagato)");
            unCampo.setTotalizzabile(true);
            this.addCampo(unCampo);

            /* campo totale sospeso (somma dei sospesi) */
            unCampo = CampoFactory.valuta(Conto.Cam.totSospeso);
            unCampo.setLarLista(60);
            unCampo.setToolTipLista("pagamenti sospesi");
            unCampo.setTotalizzabile(true);
            this.addCampo(unCampo);

            /* campo stato del conto (aperto/chiuso) */
            unCampo = CampoFactory.checkBox(Conto.Cam.chiuso);
            unCampo.setLarLista(45);
            unCampo.setTestoComponente("conto chiuso");
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo data chiusura conto */
            unCampo = CampoFactory.data(Conto.Cam.dataChiusura);
            unCampo.setInit(null);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo navigatore addebiti fissi */
            modulo = Progetto.getModulo(AddebitoFisso.NOME_MODULO);
            nav = modulo.getNavigatore(AddebitoFisso.NAVIGATORE_CONTO);
            unCampo = CampoFactory.navigatore(Conto.Cam.addebitifissi, nav);
            unCampo.decora().eliminaEtichetta();
            this.addCampo(unCampo);

            /* campo navigatore addebiti */
            modulo = AddebitoModulo.get();
            nav = modulo.getNavigatore(Movimento.Nav.movimentiConto.get());
            unCampo = CampoFactory.navigatore(Conto.Cam.addebiti, nav);
            unCampo.decora().eliminaEtichetta();
            this.addCampo(unCampo);

            /* campo navigatore pagamenti */
            modulo = PagamentoModulo.get();
            nav = modulo.getNavigatore(Movimento.Nav.movimentiConto.get());
            unCampo = CampoFactory.navigatore(Conto.Cam.pagamenti, nav);
            this.addCampo(unCampo);

            /* campo navigatore sconti */
            modulo = ScontoModulo.get();
            nav = modulo.getNavigatore(Movimento.Nav.movimentiConto.get());
            unCampo = CampoFactory.navigatore(Conto.Cam.sconti, nav);
            this.addCampo(unCampo);

            /* campo navigatore sospesi */
            modulo = SospesoModulo.get();
            nav = modulo.getNavigatore(Movimento.Nav.movimentiConto.get());
            unCampo = CampoFactory.navigatore(Conto.Cam.sospesi, nav);
            this.addCampo(unCampo);

            /* campo navigatore presenze */
            modulo = PresenzaModulo.get();
            nav = modulo.getNavigatore(Presenza.Nav.conto.get());
            unCampo = CampoFactory.navigatore(Conto.Cam.presenze, nav);
            this.addCampo(unCampo);

            /* campo note in basso */
            unCampo = CampoFactory.testoArea(Conto.Cam.note);
//            unCampo.decora().eliminaEtichetta();
            unCampo.setLarScheda(360);
            unCampo.setNumeroRighe(8);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    protected Vista creaVistaDefault() {
        /* variabili e costanti locali di lavoro */
        Vista vista = null;

        try { // prova ad eseguire il codice
            vista = new Vista(this.getModulo());
            vista.addCampo(Cam.camera);
            vista.addCampo(Cam.pagante);
            vista.addCampo(Cam.dataApertura);
            vista.addCampo(Cam.numPersone);
            vista.addCampo(Cam.totImporto);
            vista.addCampo(Cam.totSconto);
            vista.addCampo(Cam.totNetto);
            vista.addCampo(Cam.totPagato);
            vista.addCampo(Cam.totNonPagato);
            vista.addCampo(Cam.totSospeso);
            vista.addCampo(Cam.chiuso);

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
        Vista vista;
        VistaElemento elem;


        try { // prova ad eseguire il codice
            /* crea la vista specifica (un solo campo) */
            super.addVista(VISTA_SIGLA, Conto.Cam.sigla.get());

            /* crea la vista per il navigatore nel dialogo di conferma partenze */
            vista = this.creaVista(Conto.Vis.partenza.get());
            vista.addCampo(Conto.Cam.sigla.get());
            elem = vista.addCampo(Conto.Cam.totNonPagato.get());
            elem.setTitoloColonna("a pagare");
            vista.addCampo(Conto.Cam.chiuso.get());
            this.addVista(vista);

            /* crea la vista per lo Storico Cliente */
            vista = this.creaVista(Conto.Vis.storico.get());
            elem = vista.addCampo(Conto.Cam.camera.get());
            elem.setTitoloColonna("cam");
            elem.setLarghezzaColonna(40);
            elem = vista.addCampo(Conto.Cam.pagante.get());
            elem.setTitoloColonna("cliente");
            elem.setLarghezzaColonna(120);
            vista.addCampo(Conto.Cam.dataApertura.get());
            elem = vista.addCampo(Conto.Cam.totImporto.get());
            elem.setLarghezzaColonna(65);
            elem = vista.addCampo(Conto.Cam.totSconto.get());
            elem.setLarghezzaColonna(65);
            elem = vista.addCampo(Conto.Cam.totPagato.get());
            elem.setLarghezzaColonna(65);
            this.addVista(vista);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Eventuale regolazione delle caratteristiche specifiche di ogni copia dei
     * campi delle viste; le variazioni modificano <strong>solo</strong> le copie <br>
     * Viene chiamato <strong>dopo</strong> che nella superclasse sono state
     * <strong>clonate</strong> tutte le viste <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see #creaViste
     */
    protected void regolaViste() {
        /* variabili e costanti locali di lavoro */
        Vista vista;
        Campo campo;
        Modulo modCliente;
        Modulo modCamera;

        try { // prova ad eseguire il codice

            modCliente = Albergo.Moduli.Cliente();
            modCamera = Albergo.Moduli.Camera();

            /* regola la vista default */
            vista = this.getVistaDefault();

            campo = vista.getCampo(modCamera.getCampo(Camera.Cam.camera));
            if (campo != null) {
                campo.setLarLista(50);
                campo.setRidimensionabile(true);
            }// fine del blocco if

            campo = vista.getCampo(modCliente.getCampo(Anagrafica.Cam.soggetto.get()));
            if (campo != null) {
                campo.setRidimensionabile(true);
            }// fine del blocco if

            /* regola la vista sigla */
            vista = this.getVista(VISTA_SIGLA);
            campo = vista.getCampo(Conto.Cam.sigla.get());
            if (campo != null) {
                campo.getCampoLista().setRidimensionabile(false);
                campo.setTitoloColonna(COLONNA_SIGLA);
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
        Date dataGennaioCorrente;
        Date dataGennaioPrecedente;
        Date dataDicembrePrecedente;
        int annoPrecedente;
        Filtro filtro1;
        Filtro filtro2;

        try { // prova ad eseguire il codice

            /* crea il popup conti aperti / chiusi / sospesi */
            popFiltri = super.addPopFiltro();
            filtro = FiltroFactory.crea(Conto.Cam.chiuso.get(), false);
            popFiltri.add(filtro, "Conti aperti");
            filtro = FiltroFactory.crea(Conto.Cam.chiuso.get(), true);
            popFiltri.add(filtro, "Conti chiusi");
            filtro = FiltroFactory.crea(Conto.Cam.totSospeso.get(), Filtro.Op.DIVERSO, 0.0);
            popFiltri.add(filtro, "Conti sospesi");
            popFiltri.setTitolo(Conto.Pop.tipi.get());

            /* crea il popup degli anni passati */
            annoPrecedente = Lib.Data.getAnnoCorrente() - 1;
            dataGennaioCorrente = Lib.Data.getPrimoGennaio();
            dataGennaioPrecedente = Lib.Data.getPrimoGennaio(annoPrecedente);
            dataDicembrePrecedente = Lib.Data.getTrentunoDicembre(annoPrecedente);

            popFiltri = super.addPopFiltro();
            filtro = FiltroFactory.crea(Conto.Cam.dataApertura.get(),
                    Filtro.Op.MAGGIORE_UGUALE,
                    dataGennaioCorrente);
            popFiltri.add(filtro, "Anno corrente");

            filtro1 = FiltroFactory.crea(Conto.Cam.dataApertura.get(),
                    Filtro.Op.MAGGIORE_UGUALE,
                    dataGennaioPrecedente);
            filtro2 = FiltroFactory.crea(Conto.Cam.dataApertura.get(),
                    Filtro.Op.MINORE_UGUALE,
                    dataDicembrePrecedente);

            filtro = new Filtro();
            filtro.add(filtro1);
            filtro.add(filtro2);
            popFiltri.add(filtro, "Anno precedente");

            popFiltri.setTitolo(Conto.Pop.anni.get());


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Regola i valori dei campi per un nuovo record.
     * <p/>
     * Invocato prima della registrazione.
     * Permette di modificare i campi e i valori che stanno per essere registrati<br>
     * Viene sovrascritto dalle classi specifiche <br>
     * Le eventuali modifiche vanno fatte sulla lista che viene
     * passata come parametro.
     *
     * @param lista array coppia campo-valore contenente i
     * dati che stanno per essere registrati
     *
     * @return true per continuare il processo di registrazione,
     *         false per non effettuare la registrazione
     */
    @Override
    public boolean nuovoRecordAnte(ArrayList<CampoValore> lista, Connessione conn) {

        try { // prova ad eseguire il codice

            /* regola la data di prenotazione pari alla data programma */
            Campo campo = this.getCampo(Conto.Cam.dataApertura.get());
            CampoValore cv = Lib.Camp.getCampoValore(lista, campo);
            if (cv != null) {
                cv.setValore(AlbergoLib.getDataProgramma());
            }// fine del blocco if

            super.nuovoRecordAnte(lista, conn);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;
    } // fine del metodo


    /**
     * Metodo invocato dopo la registrazione di un record esistente.
     * <p/>
     * Viene sovrascritto dalle classi specifiche <br>
     *
     * @param codice del record
     * @param lista array coppia campo-valore contenente
     * i dati appena registrati
     * @param listaPre array coppia campo-valore contenente
     * i dati precedenti la registrazione
     *
     * @return true se riuscito
     */
    @Override protected boolean registraRecordPost(int codice,
                                                   ArrayList<CampoValore> lista,
                                                   ArrayList<CampoValore> listaPre,
                                                   Connessione conn) {

        /* variabili e costanti locali di lavoro */
        boolean continua = false;

        try { // prova ad eseguire il codice

            /* regolo la sigla del conto */
            continua = this.regolaSigla(codice, lista, listaPre, conn);

            /* rimando alla superclasse */
            if (continua) {
                continua = super.registraRecordPost(codice, lista, listaPre, conn);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;

    } // fine del metodo


    /**
     * Elimina un record.
     * <p/>
     * Invocato prima della registrazione.
     * Modifica eventuali valori del singolo campo <br>
     * Viene sovrascritto dalle classi specifiche <br>
     *
     * @param codice del record
     *
     * @return true se riuscito
     */
    @Override
    public boolean eliminaRecordAnte(int codice, Connessione conn) {
    	
    	// prima di eliminare il conto rimuove il flag caparra accreditata dalla prenotazione
    	ContoLogica.RemoveFlagCaparraAccreditata(codice, conn);
    	
        return super.eliminaRecordAnte(codice, conn);
    }
    

	/**
     * Regola il valore del campo sigla.
     * <p/>
     * Aggiunge o sostituisce il valore da registrare  <br>
     *
     * @param codice del record
     * @param lista array coppia campo-valore dei dati da registrare
     * @param listaPre array coppia campo-valore dei dati precedenti
     * @param conn connessione da utilizzare
     *
     * @return true se riuscito
     */
    private boolean regolaSigla(int codice,
                                ArrayList<CampoValore> lista,
                                ArrayList<CampoValore> listaPre,
                                Connessione conn) {

        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        Campo campo;
        String sigla;
        CampoValore cvCameraNew;
        CampoValore cvClienteNew;
        CampoValore cvCameraPre;
        CampoValore cvClientePre;
        int cameraNew = 0;
        int cameraPre;
        int clienteNew = 0;
        int clientePre;
        boolean cambiato;

        try { // prova ad eseguire il codice


            campo = this.getCampo(Cam.camera);
            cvCameraNew = Lib.Camp.getCampoValore(lista, campo);

            campo = this.getCampo(Cam.pagante);
            cvClienteNew = Lib.Camp.getCampoValore(lista, campo);

            if ((cvCameraNew != null) || (cvClienteNew != null)) {

                campo = this.getCampo(Cam.camera);
                cvCameraPre = Lib.Camp.getCampoValore(listaPre, campo);
                cameraPre = Libreria.getInt(cvCameraPre.getValore());

                campo = this.getCampo(Cam.pagante);
                cvClientePre = Lib.Camp.getCampoValore(listaPre, campo);
                clientePre = Libreria.getInt(cvClientePre.getValore());


                if (cvCameraNew != null) {
                    cameraNew = Libreria.getInt(cvCameraNew.getValore());
                }// fine del blocco if

                if (cvClienteNew != null) {
                    clienteNew = Libreria.getInt(cvClienteNew.getValore());
                }// fine del blocco if

                cambiato = (cameraNew != cameraPre);
                if (!cambiato) {
                    cambiato = (clienteNew != clientePre);
                }// fine del blocco if

                if (cambiato) {

                    if (cameraNew == 0) {
                        cameraNew = cameraPre;
                    }// fine del blocco if

                    if (clienteNew == 0) {
                        clienteNew = clientePre;
                    }// fine del blocco if

                    sigla = ContoModulo.creaSigla(cameraNew, clienteNew);
                    this.query().registra(codice, Conto.Cam.sigla.get(), sigla, conn);
                }// fine del blocco if

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    } // fine del metodo


    /**
     * Restituisce una matrice doppia per l'estratto
     * </p>
     * Controlla che il tipo di estratto richiesto sia una matrice <br>
     * Crea un estratto con una MatriceDoppia di codici e valori <br>
     * Il valore è la camera ed il cliente <br>
     * Filtra tutti record visibili <br>
     * Filtra tutti i record dell'azienda attiva <br>
     * Filtra tutti i record dei conti aperti <br>
     * Per ogni record presenta un valore = camera + cliente <br>
     * Il tipo di selezione inverte l'ordine di presentazione <br>
     *
     * @param tipo codifica dell'estratto desiderato
     * @param selezione di matrice (per camera, per cliente)
     *
     * @return la matrice doppia
     */
    private EstrattoBase getMatriceConti(Estratti tipo, Conto.Selezione selezione) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;
        MatriceDoppia matrice = null;
        boolean continua;
        EstrattoBase.Tipo tipoEst;
        ContoModulo modConto = null;
        Modulo modCamera = null;
        Modulo modCliente = null;
        Campo campoChiaveConto = null;
        Campo campoCamera = null;
        Campo campoCliente = null;
        Dati dati;
        Filtro filtroAzienda;
        Filtro filtroAperti;
        Filtro filtro;
        Ordine ordine;
        Query query;
        int codice;
        String camera;
        String cliente;
        String composta = "";


        try {    // prova ad eseguire il codice
            /* tipo di estratto codificato */
            tipoEst = tipo.getTipo();
            continua = (tipoEst == EstrattoBase.Tipo.matrice);

            /* moduli */
            if (continua) {
                modConto = Albergo.Moduli.Conto();
                modCamera = Albergo.Moduli.Camera();
                modCliente = Albergo.Moduli.Cliente();
            }// fine del blocco if

            /* campi */
            if (continua) {
                campoChiaveConto = modConto.getCampoChiave();
                campoCamera = modCamera.getCampo(Camera.Cam.camera);
                campoCliente = modCliente.getCampo(Anagrafica.Cam.soggetto.get());
            }// fine del blocco if

            /* filtro per isolare i conti aperti dell'azienda attiva */
            filtroAzienda = modConto.getFiltroAzienda();
            filtroAperti = FiltroFactory.crea(Conto.Cam.chiuso.get(), false);
            filtro = new Filtro();
            filtro.add(filtroAzienda);
            filtro.add(filtroAperti);

            /* ordine */
            ordine = new Ordine();
            switch (selezione) {
                case camera:
                    ordine.add(campoCamera);
                    break;
                case cliente:
                    ordine.add(campoCliente);
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

            /* query */
            query = new QuerySelezione(modConto);
            query.addCampo(campoChiaveConto);
            query.addCampo(campoCamera);
            query.addCampo(campoCliente);
            query.setFiltro(filtro);
            query.setOrdine(ordine);

            /* esegue la query e recupera i dati */
            dati = modConto.query().querySelezione(query);

            /* spazzola i dati e crea la matrice */
            matrice = new MatriceDoppia();
            for (int k = 0; k < dati.getRowCount(); k++) {
                codice = dati.getIntAt(k, campoChiaveConto);
                camera = dati.getStringAt(k, campoCamera);
                cliente = dati.getStringAt(k, campoCliente);

                /* crea la descrizione composta */
                switch (selezione) {
                    case camera:
                        composta = camera + " - " + cliente;
                        break;
                    case cliente:
                        composta = cliente + " - " + camera;
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch

                matrice.add(codice, composta);

            } // fine del ciclo for

            /* chiude i dati */
            if (dati != null) {
                dati.close();
            }// fine del blocco if

            unEstratto = new EstrattoBase(matrice, EstrattoBase.Tipo.matrice);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
    }


    /**
     * Restituisce un estratto.
     * </p>
     * Metodo invocato dal modulo <br>
     * Restituisce un estratto conforme al tipo ed al record richiesto <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param estratto codifica dell'estratto desiderato
     *
     * @return l'estratto costruito
     */
    public EstrattoBase getEstratto(Estratti estratto) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;

        try { // prova ad eseguire il codice

            /* selettore della variabile */
            switch ((Conto.Estratto)estratto) {
                case popCamera:
                    unEstratto = this.getMatriceConti(estratto, Conto.Selezione.camera);
                    break;
                case popCliente:
                    unEstratto = this.getMatriceConti(estratto, Conto.Selezione.cliente);
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
    }


    /**
     * Restituisce un estratto.
     * </p>
     * Restituisce un estratto conforme al tipo ed al record richiesto <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param tipo codifica dell'estratto desiderato
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito
     */
    public EstrattoBase getEstratto(Estratti tipo, Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;

        try { // prova ad eseguire il codice

            /* selettore della variabile */
            switch ((Conto.Estratto)tipo) {
                case descrizione:
                    unEstratto = this.getEstratto(tipo, chiave, Conto.Cam.sigla.get());
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
    }


    /**
     * Azione eseguita quando viene creata/modificata/cancellata
     * una riga di Presenza.
     * <p/>
     */
    private class AzTriggerPresenza extends DbTriggerAz {

        /**
         * Metodo invocato dal trigger.
         * <p/>
         *
         * @param evento evento che causa l'azione da eseguire <br>
         */
        public void dbTriggerAz(DbTriggerEve evento) {
            /* variabili e costanti locali di lavoro */
            Db.TipoOp tipo;
            int codiceRiga;
            int codConto = 0;
            int codContoOld = 0;
            Connessione conn;
            ArrayList<CampoValore> valoriOld;

            Modulo modRighe;
            Campo campoLinkConto;
            CampoValore cv;
            boolean aggiorna = false;


            try { // prova ad eseguire il codice

                /* recupera le informazioni dall'evento */
                tipo = evento.getTipo();
                valoriOld = evento.getValoriOld();
                codiceRiga = evento.getCodice();
                conn = evento.getConn();

                /**
                 * Decide se deve aggiornare i totali delle persone nel conto.
                 * I totali vanno aggionati se:
                 * - è stato creato un nuovo record di Presenza
                 * - è stato cancellato un record di Presenza
                 * - è stato modificato il campo LinkConto su un record di Presenza
                 */
                modRighe = PresenzaModulo.get();
                campoLinkConto = modRighe.getCampo(Presenza.Cam.conto.get());

                /* determina i valori di variazione di imponibile e iva */
                switch (tipo) {

                    case nuovo:

                        codConto = getCodConto(codiceRiga, conn);
                        aggiorna = true;
                        break;

                    case modifica:

                        /* recupero il vecchio valore e il nuovo valore del campo link conto
                        * perché se sono diversi vanno modificati entrambi i conti */
                        cv = Lib.Camp.getCampoValore(valoriOld, campoLinkConto);
                        if (cv != null) {
                            codContoOld = Libreria.getInt(cv.getValore());
                        }// fine del blocco if
                        codConto = getCodConto(codiceRiga, conn);

                        /* se il vecchio valore è uguale al nuovo il campo link non è
                        * stato modificato. azzero il vecchio valore per non
                        * sincronizzare due volte */
                        if (codContoOld != 0) {
                            if (codContoOld == codConto) {
                                codContoOld = 0;
                            }// fine del blocco if
                        }// fine del blocco if

                        aggiorna = true;
                        break;

                    case elimina:

                        /* la riga non esiste più, devo recuperala dai valori old  */
                        cv = Lib.Camp.getCampoValore(valoriOld, campoLinkConto);
                        if (cv != null) {
                            codConto = Libreria.getInt(cv.getValore());
                            aggiorna = true;
                        }// fine del blocco if

                        break;

                    default: // caso non definito
                        break;
                } // fine del blocco switch

                /* aggiorna i totali se necessario */
                if (aggiorna) {
                    if (codContoOld != 0) {
                        syncTotPresenze(codContoOld, conn);
                    }// fine del blocco if
                    syncTotPresenze(codConto, conn);
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    }


    /**
     * Recupera il codice del conto di riferimento di una Presenza.
     * <p/>
     *
     * @param codPresenza codice della riga di Presenza
     * @param conn la connessione da utilizzare
     *
     * @return il codice del conto di riferimento
     */
    private int getCodConto(int codPresenza, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int codConto = 0;
        Modulo modRighe;

        try {    // prova ad eseguire il codice
            modRighe = PresenzaModulo.get();
            codConto = modRighe.query().valoreInt(Presenza.Cam.conto.get(), codPresenza, conn);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codConto;
    }


    /**
     * Sincronizza i totali delle presenze in un conto.
     * <p/>
     * Il totale presenze è pari al numero di righe di Presenza aperte
     * che fanno riferimento al conto.
     *
     * @param codConto codice del conto da sincronizzare
     * @param conn connessione da utilizzare
     */
    private void syncTotPresenze(int codConto, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo modConto;
        Modulo modPresenze;
        Filtro filtro;
        Filtro filtroConto;
        Filtro filtroAperte;
        int totPresenze;

        try {    // prova ad eseguire il codice

            /* esegue solo se il codice del conto è valido */
            continua = (codConto > 0);

            if (continua) {

                /* recupera il numero di presenze */
                modPresenze = PresenzaModulo.get();
                filtroConto = FiltroFactory.crea(Presenza.Cam.conto.get(), codConto);
                filtroAperte = FiltroFactory.creaFalso(Presenza.Cam.chiuso.get());
                filtro = new Filtro();
                filtro.add(filtroConto);
                filtro.add(filtroAperte);
                totPresenze = modPresenze.query().contaRecords(filtro, conn);

                /* scrive il numero di presenze nel conto */
                modConto = this.getModulo();
                modConto.query().registraRecordValore(codConto,
                        Conto.Cam.numPersone.get(),
                        totPresenze,
                        conn);

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


} // fine della classe


