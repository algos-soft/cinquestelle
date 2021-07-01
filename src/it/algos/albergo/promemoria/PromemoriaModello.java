package it.algos.albergo.promemoria;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.promemoria.tipo.TipoPro;
import it.algos.albergo.promemoria.tipo.TipoProModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.inizializzatore.InitFactory;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.WrapFiltri;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.anagrafica.AnagraficaModulo;

import java.util.ArrayList;

/**
 * Tracciato record della tavola Promemoria.
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
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @version 1.0 / 12-mar-2009
 */
public final class PromemoriaModello extends ModelloAlgos implements Promemoria {

    /**
     * Costruttore completo senza parametri.
     */
    public PromemoriaModello() {
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
        super.setTavolaArchivio(Promemoria.NOME_TAVOLA);
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
            super.setCampoOrdineIniziale(Cam.dataScadenza);

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
        int larL = 40;
        int larS = 45;
        int larCameraLista = 70;
        int larClienteLista = 70;
        int larData = 80;
        int larCliente = 165; //per allineare il bordo destro
        int larTesto = 370;  //per allineare il bordo destro

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo eseguito */
            unCampo = CampoFactory.checkBox(Cam.eseguito);
            this.addCampo(unCampo);

            /* campo tipo */
            unCampo = CampoFactory.comboLinkPop(Cam.linkTipo);
            unCampo.setNomeModuloLinkato(TipoPro.NOME_MODULO);
            unCampo.setUsaNuovo(true);
            this.addCampo(unCampo);

            /* campo testo */
            unCampo = CampoFactory.testoArea(Cam.testo);
            unCampo.setNumeroRighe(5);
            unCampo.setLarScheda(larTesto);
            unCampo.decora().obbligatorio();
            this.addCampo(unCampo);

            /* campo ora scadenza */
            unCampo = CampoFactory.ora(Cam.oraScadenza);
            unCampo.setInit(InitFactory.intero(MEZZANOTTE));
            unCampo.setLarLista(larL);
            unCampo.setLarScheda(larS);
            unCampo.decora().eliminaEtichetta();
            this.addCampo(unCampo);

            /* campo data scadenza */
            unCampo = CampoFactory.data(Cam.dataScadenza);
            unCampo.setInit(InitFactory.standard(unCampo));
            Ordine ordine = new Ordine();
            ordine.add(Cam.dataScadenza.get());
            ordine.add(Cam.oraScadenza.get());
            unCampo.setOrdine(ordine);
            unCampo.setLarScheda(larData);
            unCampo.decora().obbligatorio();
            unCampo.decora().eliminaEtichetta();
            this.addCampo(unCampo);

            /* campo ora visione */
            unCampo = CampoFactory.ora(Cam.oraVisione);
            unCampo.setLarScheda(larS);
            unCampo.decora().eliminaEtichetta();
            this.addCampo(unCampo);

            /* campo data visione */
            unCampo = CampoFactory.data(Cam.dataVisione);
            unCampo.setInit(InitFactory.standard(unCampo));
            unCampo.setLarScheda(larData);
            unCampo.decora().obbligatorio();
            unCampo.decora().eliminaEtichetta();
            this.addCampo(unCampo);

            /* campo rifCamera */
            unCampo = CampoFactory.comboLinkSel(Cam.rifCamera);
            unCampo.setNomeModuloLinkato(Camera.NOME_MODULO);
            unCampo.setLarLista(larCameraLista);
            unCampo.setModificabileLista(false);
            unCampo.setUsaNuovo(false);
            this.addCampo(unCampo);

            /* campo rifCliente */
            unCampo = CampoFactory.comboLinkSel(Cam.rifCliente);
            unCampo.setNomeModuloLinkato(ClienteAlbergo.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(Anagrafica.Cam.soggetto.get());
            unCampo.setNomeCampoValoriLinkato(Anagrafica.Cam.soggetto.get());
            unCampo.setLarLista(larClienteLista);
            unCampo.setLarScheda(larCliente);
            unCampo.setModificabileLista(false);
            unCampo.setUsaNuovo(false);
            this.addCampo(unCampo);


            /* campo urgenza */
//            unCampo = CampoFactory.intero(Cam.urgenza);
//            this.addCampo(unCampo);

//            /* campo idUtenteCrea */
//            unCampo = CampoFactory.link(Cam.idUtenteCrea);
//            this.addCampo(unCampo);
//
//            /* campo idUtenteChiude */
//            unCampo = CampoFactory.link(Cam.idUtenteChiude);
//            this.addCampo(unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo creaCampi


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
        boolean continua;
        Modulo modTipo;
        Modulo modCamera;
        Modulo modCliente;
        Vista unaVista = null;
        Campo unCampo;

        try { // prova ad eseguire il codice

            modTipo = TipoProModulo.get();
            modCamera = CameraModulo.get();
            modCliente = AnagraficaModulo.get();
            continua = (modTipo != null && modCamera != null && modCliente != null);

            /* regola la vista di default */
            if (continua) {
                unaVista = this.getVistaDefault();
                continua = (unaVista != null);
            }// fine del blocco if

            /* campo tipo */
            if (continua) {
                unCampo = modTipo.getCampo(TipoPro.Cam.sigla.get());
                unCampo = unaVista.getCampo(unCampo);
                if (unCampo != null) {
                    unCampo.setTitoloColonna("tipo");
                }// fine del blocco if
            }// fine del blocco if

            /* campo camera */
            if (continua) {
                unCampo = modCamera.getCampo(Camera.Cam.camera.get());
                unCampo = unaVista.getCampo(unCampo);
                if (unCampo != null) {
                    unCampo.setTitoloColonna("camera");
                    unCampo.setLarghezza(70);
                    unCampo.setRidimensionabile(false);
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
        Modulo mod;
        Campo linkTipo;
        ArrayList<Integer> lista;
        String sigla;

        try { // prova ad eseguire il codice

            /* crea il popup promemoria aperti / chiusi */
            popFiltri = super.addPopFiltro();
            popFiltri.setTitolo(Promemoria.Pop.eseguiti.get());

            filtro = FiltroFactory.creaFalso(Cam.eseguito.get());
            popFiltri.add(filtro, "Da fare");

            filtro = FiltroFactory.creaVero(Cam.eseguito.get());
            popFiltri.add(filtro, "Eseguiti");


            /* crea il popup sul tipo di promemoria */
            mod = TipoProModulo.get();

            popFiltri = super.addPopFiltro();
            popFiltri.setTitolo(Promemoria.Pop.tipi.get());
            popFiltri.setTesto("Tutti");
            linkTipo = this.getCampo(Cam.linkTipo.get());

            /* recupera i valori dal campo link */
            lista = this.query().valoriDistintiInt(linkTipo.getNomeInterno());

            /* crea una lista di filtri */
            for (int cod : lista) {
                sigla = mod.query().valoreStringa(TipoPro.Cam.sigla.get(),cod);
                filtro = FiltroFactory.crea(linkTipo, cod);
                popFiltri.add(filtro, sigla);
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


} // fine dell'interfaccia
