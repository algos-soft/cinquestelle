package it.algos.albergo.camera.compoaccessori;

import it.algos.albergo.camera.accessori.Accessori;
import it.algos.albergo.camera.accessori.AccessoriModulo;
import it.algos.albergo.camera.composizione.CompoCamera;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;

/**
 * Modello dati del modulo Incrocio Composizioni Camera - Accessori
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 11-giu-2009 ore  16:46
 */
public final class CompoAccessoriModello extends ModelloAlgos implements CompoAccessori {

    /**
     * Costruttore completo senza parametri.
     */
    public CompoAccessoriModello() {
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
        super.setTavolaArchivio(CompoAccessori.NOME_TAVOLA);

    }// fine del metodo inizia


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

        try { // prova ad eseguire il codice

            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo link composizione */
            unCampo = CampoFactory.comboLinkPop(Cam.composizione);
            unCampo.setNomeModuloLinkato(CompoCamera.NOME_MODULO);
            unCampo.setAzioneDelete(Db.Azione.cascade);
            this.addCampo(unCampo);

            /* campo link accessorio */
            unCampo = CampoFactory.comboLinkPop(Cam.accessorio);
            unCampo.setNomeModuloLinkato(Accessori.NOME_MODULO);
            unCampo.setAzioneDelete(Db.Azione.cascade);
            unCampo.setUsaNonSpecificato(false);
            unCampo.setUsaNuovo(true);
            unCampo.decora().obbligatorio();
            unCampo.setLarScheda(200);
            this.addCampo(unCampo);

            /* campo quantità */
            unCampo = CampoFactory.intero(Cam.quantita);
            unCampo.decora().obbligatorio();
            this.addCampo(unCampo);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo creaCampi


    /**
     * Creazione delle viste aggiuntive.
     * <p/>
     */
    @Override
    protected void creaViste() {
        /* variabili e costanti locali di lavoro */
        Vista vista;
        VistaElemento elem;

        try { // prova ad eseguire il codice

            /* crea la vista per la lista righe in Scheda Composizione Camera */
            vista = this.creaVista(Vis.visInComposizione.get());

            elem = vista.addCampo(AccessoriModulo.get().getCampo(Accessori.Cam.descrizione));
            elem.setTitoloColonna("accessorio");
            
            elem = vista.addCampo(Cam.quantita);
            elem.setTitoloColonna("q.tà");
            
            this.addVista(vista);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


}// fine della classe