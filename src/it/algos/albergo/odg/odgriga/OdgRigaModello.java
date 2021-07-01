package it.algos.albergo.odg.odgriga;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.camera.composizione.CompoCamera;
import it.algos.albergo.odg.odgaccessori.OdgAcc;
import it.algos.albergo.odg.odgzona.OdgZona;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;

/**
 * Modello dati del modulo Righe Ordine del Giorno
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 11-giu-2009 ore  16:46
 */
public final class OdgRigaModello extends ModelloAlgos implements OdgRiga {

    /**
     * Costruttore completo senza parametri.
     */
    public OdgRigaModello() {
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
        super.setTavolaArchivio(OdgRiga.NOME_TAVOLA);

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

            /* campo link a OdgZona */
            unCampo = CampoFactory.link(Cam.zona);
            unCampo.setNomeModuloLinkato(OdgZona.NOME_MODULO);
            unCampo.setAzioneDelete(Db.Azione.cascade);
            this.addCampo(unCampo);

            /* campo link a Camera */
            Campo campoCamera;
            campoCamera = CampoFactory.comboLinkSel(Cam.camera);
            campoCamera.setNomeModuloLinkato(Camera.NOME_MODULO);
            campoCamera.getCampoDB().setRelazionePreferita(true);
            campoCamera.setAzioneDelete(Db.Azione.setNull);
            campoCamera.setNomeCampoValoriLinkato(Camera.Cam.camera.get());
            campoCamera.setLarScheda(60);
            campoCamera.setUsaNuovo(false);
            campoCamera.decora().obbligatorio();
            this.addCampo(campoCamera);


            /**
             * Nota sui codici periodo (periodo1 e periodo2):
             * una riga di Odg può avere riferimento a nessuno, uno o due codici periodo.
             * - Non ha riferimenti se nel proprio giorno-camera la camera è libera
             * - Ha un solo riferimento se nel proprio giorno-camera c'è una entrata
             * o una uscita o una fermata.
             * - Ha due riferimenti se nel proprio giorno-camera ci sono sia una
             * uscita che una entrata. In tal caso il primo riferimento è quello
             * di uscita e il secondo è quello di entrata.
             *
             * Essendo riferimenti piuttosto volatili e gestiti programmaticamente
             * non si ritiene necessario usare campi di tipo link e instaurare dei
             * vincoli sul database.
             */

            /* campo codice primo o unico Periodo */
            unCampo = CampoFactory.intero(Cam.periodo1);
            this.addCampo(unCampo);

            /* campo codice eventuale secondo Periodo */
            unCampo = CampoFactory.intero(Cam.periodo2);
            this.addCampo(unCampo);

            /* campo booleano Fermata */
            unCampo = CampoFactory.checkBox(Cam.fermata);
            unCampo.setTestoComponente("Fermata");
            unCampo.setLarScheda(90);
            this.addCampo(unCampo);

            /* campo booleano Partenza */
            unCampo = CampoFactory.checkBox(Cam.partenza);
            unCampo.setTestoComponente("Partenza");
            unCampo.setLarScheda(90);
            this.addCampo(unCampo);

            /* campo booleano Arrivo */
            unCampo = CampoFactory.checkBox(Cam.arrivo);
            unCampo.setTestoComponente("Arrivo");
            unCampo.setLarScheda(90);
            this.addCampo(unCampo);

            /* campo booleano Cambio */
            unCampo = CampoFactory.checkBox(Cam.cambio);
            unCampo.setTestoComponente("Cambio");
            unCampo.setLarScheda(80);
            this.addCampo(unCampo);

            /* campo link Camera Provenienza */
            Campo campoCamProv;
            campoCamProv = CampoFactory.comboLinkSel(Cam.cambioDa);
            campoCamProv.setNomeModuloLinkato(Camera.NOME_MODULO);
            campoCamProv.setAzioneDelete(Db.Azione.setNull);
            campoCamProv.setRenderer(new RendererCameraLink(campoCamProv));
            campoCamProv.setNomeCampoValoriLinkato(Camera.Cam.camera.get());
            campoCamProv.setTitoloColonna("dalla");
            campoCamProv.setUsaNuovo(false);
            this.addCampo(campoCamProv);

            /* campo link Camera Destinazione */
            Campo campoCamDest;
            campoCamDest = CampoFactory.comboLinkSel(Cam.cambioA);
            campoCamDest.setNomeModuloLinkato(Camera.NOME_MODULO);
            campoCamDest.setAzioneDelete(Db.Azione.setNull);
            campoCamDest.setRenderer(new RendererCameraLink(campoCamDest));
            campoCamDest.setNomeCampoValoriLinkato(Camera.Cam.camera.get());
            campoCamDest.setTitoloColonna("alla");
            campoCamDest.setUsaNuovo(false);
            this.addCampo(campoCamDest);

            /* campo booleano Parte Domani */
            unCampo = CampoFactory.checkBox(Cam.parteDomani);
            unCampo.setTestoComponente("Parte domani");
            unCampo.setLarScheda(120);
            this.addCampo(unCampo);

            /* campo booleano Cambia Domani */
            unCampo = CampoFactory.checkBox(Cam.cambiaDomani);
            unCampo.setTestoComponente("Cambia domani");
            unCampo.setLarScheda(120);
            this.addCampo(unCampo);

            /* campo booleano Chiudere */
            unCampo = CampoFactory.checkBox(Cam.chiudere);
            unCampo.setTestoComponente("Chiudere");            
            unCampo.setLarScheda(90);
            this.addCampo(unCampo);

            /* campo booleano Da Fare */
            unCampo = CampoFactory.checkBox(Cam.dafare);
            unCampo.setTestoComponente("Preparare");
            unCampo.setLarScheda(90);
            this.addCampo(unCampo);

            /* campo composizione camera */
            unCampo = CampoFactory.comboLinkPop(Cam.composizione);
            unCampo.setNomeModuloLinkato(CompoCamera.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(CompoCamera.Cam.sigla.get());
            unCampo.setNomeCampoValoriLinkato(CompoCamera.Cam.sigla.get());
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.setLarScheda(100);
            this.addCampo(unCampo);

            /* campo composizione precedente (testo non modificabile) */
            unCampo = CampoFactory.testo(Cam.compoprecedente);
            unCampo.setAbilitato(false);
            unCampo.setLarScheda(60);
            this.addCampo(unCampo);

            /* campo note */
            unCampo = CampoFactory.testo(Cam.note);
            this.addCampo(unCampo);

            /* campo navigatore sub-lista accessori */
            unCampo = CampoFactory.navigatore(Cam.righeAccessori, OdgAcc.NOME_MODULO, OdgAcc.Nav.navInRigheOdg);
            this.addCampo(unCampo);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo creaCampi


    

    @Override
    protected void creaViste() {
        /* variabili e costanti locali di lavoro */
        Vista vista;
        VistaElemento elem;

        Campo campo;

        try { // prova ad eseguire il codice

            vista = new Vista(Vis.vistaRighe.get(), this.getModulo());

            campo = CameraModulo.get().getCampo(Camera.Cam.camera);
            elem = vista.addCampo(campo);
            elem.setTitoloColonna("cam");
            elem.setLarghezzaColonna(50);

            elem = vista.addCampo(Cam.fermata);
            elem.setTitoloColonna("ferm");
            elem.setLarghezzaColonna(40);

            elem =vista.addCampo(Cam.partenza);
            elem.setTitoloColonna("part");
            elem.setLarghezzaColonna(40);

            elem =vista.addCampo(Cam.arrivo);
            elem.setTitoloColonna("arr");
            elem.setLarghezzaColonna(40);

            elem =vista.addCampo(Cam.cambio);
            elem.setTitoloColonna("camb");
            elem.setLarghezzaColonna(40);

            /**
             * questo non lo espande, se no va in conflitto con linkcamera,
             * per la visualizzazione usa un renderer
             */
            elem = vista.addCampo(Cam.cambioDa);
            elem.setEspanso(true);
            elem.setTitoloColonna("dalla");
            elem.setLarghezzaColonna(40);

            /**
             * questo non lo espande, se no va in conflitto con linkcamera,
             * per la visualizzazione usa un renderer
             */
            elem = vista.addCampo(Cam.cambioA);
            elem.setEspanso(true);
            elem.setTitoloColonna("alla");
            elem.setLarghezzaColonna(40);

            elem=vista.addCampo(Cam.parteDomani);
            elem.setTitoloColonna("p.dom");
            elem.setLarghezzaColonna(40);

            elem=vista.addCampo(Cam.cambiaDomani);
            elem.setTitoloColonna("c.dom");
            elem.setLarghezzaColonna(40);

            elem=vista.addCampo(Cam.chiudere);
            elem.setTitoloColonna("chiu");
            elem.setLarghezzaColonna(40);
            
            elem=vista.addCampo(Cam.dafare);
            elem.setTitoloColonna("prep");
            elem.setLarghezzaColonna(40);

            elem = vista.addCampo(Cam.compoprecedente);
            elem.setTitoloColonna("prec");
            elem.setLarghezzaColonna(60);

            elem = vista.addCampo(Cam.composizione);
            elem.setTitoloColonna("comp");
            elem.setLarghezzaColonna(60);

            elem=vista.addCampo(Cam.note);
            elem.setLarghezzaColonna(120);

            this.addVista(vista);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


}// fine della classe