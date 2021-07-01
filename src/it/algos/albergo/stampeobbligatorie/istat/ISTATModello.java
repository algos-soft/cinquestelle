package it.algos.albergo.stampeobbligatorie.istat;

import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampe;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;

/**
 * Tracciato record della tavola istat.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 01-Ott-2008
 */
public final class ISTATModello extends ModelloAlgos implements ISTAT {


    /**
     * Costruttore completo senza parametri.
     */
    public ISTATModello() {

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
            unCampo = CampoFactory.link(ISTAT.Cam.linkTesta);
            unCampo.setNomeModuloLinkato(TestaStampe.NOME_MODULO);
            unCampo.setAzioneDelete(Db.Azione.cascade);
            this.addCampo(unCampo);

            /* campo italiano o straniero o non specificato */
            unCampo = CampoFactory.radioInterno(ISTAT.Cam.tipoRiga);
            unCampo.setValoriInterni(TipoRiga.values());
//            unCampo.setRenderer(new RendererItaliano(unCampo));
            unCampo.setLarLista(50);
            this.addCampo(unCampo);

            /* campo codice della Nazione (stranieri) o Provincia (italiani) di residenza */
            unCampo = CampoFactory.intero(ISTAT.Cam.codResidenza);
            unCampo.setRenderer(new RendererResidenza(unCampo));
            unCampo.setLarLista(140);
            this.addCampo(unCampo);

            /* campo numero persone arrivate */
            unCampo = CampoFactory.intero(ISTAT.Cam.numArrivati);
            this.addCampo(unCampo);

            /* campo numero persone partite */
            unCampo = CampoFactory.intero(ISTAT.Cam.numPartiti);
            this.addCampo(unCampo);

            /* campo codici delle persone arrivate (link ad anagrafica, separati da virgola) */
            unCampo = CampoFactory.testo(ISTAT.Cam.codArrivati);
            this.addCampo(unCampo);

            /* campo codici delle persone partite (link ad anagrafica, separati da virgola) */
            unCampo = CampoFactory.testo(ISTAT.Cam.codPartiti);
            this.addCampo(unCampo);

            /* campo logico check di controllo validità dati anagrafici */
            unCampo = CampoFactory.testo(ISTAT.Cam.check.get());
            unCampo.getCampoDB().setCampoFisico(false);
            unCampo.setVisibileVistaDefault(true);
            unCampo.setTitoloColonna("ok");
            unCampo.setLarLista(40);
            unCampo.setRenderer(new ISTATRendererInfo(unCampo));
            this.addCampo(unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }



} // fine della classe