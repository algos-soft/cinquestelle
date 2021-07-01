package it.algos.albergo.stampeobbligatorie.notifica;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampe;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.vista.Vista;
import it.algos.gestione.anagrafica.Anagrafica;

import javax.swing.SwingConstants;

/**
 * Modello della tavola Notifica.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 3-4-05
 */
public final class NotificaModello extends ModelloAlgos implements Notifica {


    /**
     * Costruttore completo senza parametri.
     */
    public NotificaModello() {

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
    }


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

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo link al record di testa */
            unCampo = CampoFactory.link(Cam.linkTesta);
            unCampo.setNomeModuloLinkato(TestaStampe.NOME_MODULO);
            unCampo.setAzioneDelete(Db.Azione.cascade);
            this.addCampo(unCampo);

            /* campo progressivo da inizio anno */
            unCampo = CampoFactory.intero(Cam.progressivo);
            unCampo.setLarghezza(30);
            this.addCampo(unCampo);

            /* campo link camera */
            unCampo = CampoFactory.comboLinkSel(Cam.camera);
            unCampo.setNomeModuloLinkato(Camera.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(Camera.Cam.camera.get());
            unCampo.setAzioneDelete(Db.Azione.setNull);
            this.addCampo(unCampo);

            /* campo link cliente intestatario della scheda di notifica */
            unCampo = CampoFactory.comboLinkSel(Cam.linkCliente);
            unCampo.setNomeModuloLinkato(ClienteAlbergo.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(Anagrafica.Cam.soggetto.get());
            unCampo.setNomeCampoValoriLinkato(Anagrafica.Cam.soggetto.get());
            unCampo.setAzioneDelete(Db.Azione.setNull);
            this.addCampo(unCampo);
            
            /* campo link periodo di riferimento */
            unCampo = CampoFactory.comboLinkSel(Cam.linkPeriodo);
            unCampo.setNomeModuloLinkato(PeriodoModulo.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(PeriodoModulo.get().getCampoChiave().getNomeInterno());
            unCampo.setAzioneDelete(Db.Azione.setNull);
            this.addCampo(unCampo);

            /* campo numerico per il totale delle persone del gruppo */
            unCampo = CampoFactory.intero(Cam.numPersone);
            unCampo.setLarghezza(30);
            unCampo.setAllineamentoLista(SwingConstants.CENTER);
            unCampo.setTotalizzabile(true);
            this.addCampo(unCampo);

            /* campo persone del gruppo (link ad anagrafica, separati da virgola) */
            /* contiene anche il capogruppo (che potrebbe anche NON essere arrivato) */
            unCampo = CampoFactory.testo(Cam.codPersone);
            this.addCampo(unCampo);

            /* campo logico check di controllo validità dati anagrafici */
            unCampo = CampoFactory.testo(Cam.check.get());
            unCampo.getCampoDB().setCampoFisico(false);
            unCampo.setVisibileVistaDefault(true);
            unCampo.setTitoloColonna("ok");
            unCampo.setLarLista(40);
            unCampo.setRenderer(new NotificaRendererInfoMultiplo(unCampo));
            this.addCampo(unCampo);

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
    @Override protected void regolaViste() {
        /* variabili e costanti locali di lavoro */
        Vista unaVista;
        Campo unCampo;

        try { // prova ad eseguire il codice
            unaVista = this.getVistaDefault();

            unCampo = unaVista.getCampo(Camera.Cam.camera.get());
            unCampo.setTitoloColonna("camera");
            unCampo.setLarLista(50);
            unCampo.getCampoLista().setRidimensionabile(false);

            unCampo = unaVista.getCampo(Anagrafica.Cam.soggetto.get());
            unCampo.setTitoloColonna("intestatario");
            unCampo.setLarLista(200);
            unCampo.getCampoLista().setRidimensionabile(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

} // fine della classe
