package it.algos.albergo.odg.odgzona;

import it.algos.albergo.camera.zona.Zona;
import it.algos.albergo.camera.zona.ZonaModulo;
import it.algos.albergo.odg.odgtesta.Odg;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;

/**
 * Modello dati del modulo Zone di un ODG
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 11-giu-2009 ore  16:46
 */
public final class OdgZonaModello extends ModelloAlgos implements OdgZona {

    /**
     * Costruttore completo senza parametri.
     */
    public OdgZonaModello() {
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
        super.setTavolaArchivio(OdgZona.NOME_TAVOLA);

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

            /* campo link a ODG */
            unCampo = CampoFactory.link(Cam.odg);
            unCampo.setNomeModuloLinkato(Odg.NOME_MODULO);
            unCampo.setAzioneDelete(Db.Azione.cascade);
            unCampo.setLarLista(200);
            this.addCampo(unCampo);

            /* campo link a Zona */
            unCampo = CampoFactory.comboLinkSel(Cam.zona);
            unCampo.setNomeModuloLinkato(Zona.NOME_MODULO);
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.setNomeCampoValoriLinkato(Zona.Cam.sigla.get());
            unCampo.setModificabile(false);
            unCampo.setUsaNuovo(false);
            unCampo.decora().obbligatorio();
            this.addCampo(unCampo);

            /* campo note */
            unCampo = CampoFactory.testoArea(Cam.note);
            unCampo.setNumeroRighe(2);
            unCampo.setLarScheda(580);
            unCampo.decora().etichettaSinistra();
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

            vista = new Vista(OdgZona.Vis.vistaZone.get(), this.getModulo());

            campo = ZonaModulo.get().getCampo(Zona.Cam.sigla);
            elem = vista.addCampo(campo);
            elem.setTitoloColonna("zona");
            elem.setLarghezzaColonna(120);
            elem.setRidimensionabile(false);

            elem = vista.addCampo(OdgZona.Cam.note);
            elem.setTitoloColonna("note");
            elem.setRidimensionabile(true);

            this.addVista(vista);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }



}// fine della classe