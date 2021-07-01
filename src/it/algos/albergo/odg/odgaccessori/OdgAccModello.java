package it.algos.albergo.odg.odgaccessori;

import it.algos.albergo.camera.accessori.Accessori;
import it.algos.albergo.camera.accessori.AccessoriModulo;
import it.algos.albergo.odg.odgriga.OdgRiga;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;

/**
 * Modello dati del modulo Accessori Righe ODG
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 26-giu-2009 ore  11:46
 */
public final class OdgAccModello extends ModelloAlgos implements OdgAcc {

    /**
     * Costruttore completo senza parametri.
     */
    public OdgAccModello() {
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
        super.setTavolaArchivio(OdgAcc.NOME_TAVOLA);

    }// fine del metodo inizia


    /**
     * Creazione dei campi.
     * <p/>
     */
    @Override
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo link a Riga Odg */
            unCampo = CampoFactory.link(Cam.rigaOdg);
            unCampo.setNomeModuloLinkato(OdgRiga.NOME_MODULO);
            unCampo.setAzioneDelete(Db.Azione.cascade);
            this.addCampo(unCampo);

            /* campo link a Accessorio Camera */
            unCampo = CampoFactory.comboLinkPop(Cam.accessorio);
            unCampo.setNomeModuloLinkato(Accessori.NOME_MODULO);
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.setUsaNonSpecificato(false);
            unCampo.decora().obbligatorio();
            unCampo.setLarScheda(170);
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

            /* crea la vista per la lista righe in Scheda Riga Odg */
            vista = this.creaVista(Vis.visInRigaOdg.get());

            elem = vista.addCampo(AccessoriModulo.get().getCampo(Accessori.Cam.descrizione));
            elem.setTitoloColonna("accessorio");
            elem.setLarghezzaColonna(150);

            elem = vista.addCampo(Cam.quantita);
            elem.setTitoloColonna("q.tà");

            this.addVista(vista);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


}// fine della classe