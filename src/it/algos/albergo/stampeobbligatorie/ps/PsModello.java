package it.algos.albergo.stampeobbligatorie.ps;

import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampe;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;

/**
 * Tracciato record della tavola Provincia.
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
 * @version 1.0 / 3-4-05
 */
public final class PsModello extends ModelloAlgos implements Ps {


    /**
     * Costruttore completo senza parametri.
     */
    public PsModello() {

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
            unCampo.setLarghezza(60);
            this.addCampo(unCampo);

            /* campo link al cliente */
            unCampo = CampoFactory.link(Cam.linkCliente);
            unCampo.setNomeModuloLinkato(ClienteAlbergo.NOME_MODULO);
            unCampo.setNomeVistaLinkata(ClienteAlbergo.Vis.ps.toString());
            unCampo.setAzioneDelete(Db.Azione.setNull);
            this.addCampo(unCampo);

            /* todo campo link alla presenza - PROVVISORIO, TOGLIERE APPENA POSSIBILE */
            unCampo = CampoFactory.link(Cam.linkPresenza);
            unCampo.setNomeModuloLinkato(Presenza.NOME_MODULO);
            unCampo.setAzioneDelete(Db.Azione.setNull);
            this.addCampo(unCampo);

            /* campo check di controllo validità dati */
            unCampo = CampoFactory.testo(Ps.Cam.check.get());
            unCampo.getCampoDB().setCampoFisico(false);
            unCampo.setVisibileVistaDefault(true);
            unCampo.setTitoloColonna("ok");
            unCampo.setLarLista(40);
            unCampo.setRenderer(new PSRendererInfo(unCampo));
            this.addCampo(unCampo);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }



} // fine della classe
