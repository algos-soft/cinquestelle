package it.algos.albergo.arrivipartenze;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;

/**
 * Dialogo di annullamento di un arrivo previsto.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 17-giu-2008 ore 14.21.46
 */
public class AnnullaArrivoDialogo extends DialogoAnnullaConferma {

    private static final String NOME_CAMPO_CAMERA = "camera";

    private static final String NOME_CAMPO_PERSONE = "clienti arrivati";

    /* codice del periodo da annullare */
    private int codPeriodo;


    /**
     * Costruttore base con parametri.
     * <p/>
     *
     * @param codPeriodo codice del periodo da annullare
     */
    public AnnullaArrivoDialogo(int codPeriodo) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setCodPeriodo(codPeriodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal
     * costruttore (init) <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            this.setTitolo("Annullamento arrivo");

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    @Override
    public void inizializza() {
        Campo campo;

        try { // prova ad eseguire il codice

            /* crea e registra i campi del dialogo */
            this.creaCampi();

            /* aggiunge graficamente i campi */
            campo = this.getCampo(NOME_CAMPO_CAMERA);
            this.addCampo(campo);
            campo = this.getCampo(NOME_CAMPO_PERSONE);
            this.addCampo(campo);

            /* rimanda alla superclasse */
            super.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */


    /**
     * Creazione dei campi.
     * </p>
     */
    private void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        Navigatore nav;

        try { // prova ad eseguire il codice

            /* camera */
            campo = CampoFactory.testo(NOME_CAMPO_CAMERA);
            campo.setAbilitato(false);
            campo.setLarghezza(60);
            campo.setValore(this.getNomeCamera());
            this.addCampoCollezione(campo);

            /* elenco delle persone presenti */
            nav = PresenzaModulo.get().getNavigatore(Presenza.Nav.annullaArrivo.get());
            nav.setFiltroBase(this.getFiltroPresenze());
            campo = CampoFactory.navigatore(NOME_CAMPO_PERSONE, nav);
            this.addCampoCollezione(campo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    public void eventoConferma() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        int[] codPresenze;
        int[] codiciConto;
        double totAddebiti;
        MessaggioDialogo messaggio;
        String testo;

        try { // prova ad eseguire il codice

            /**
             * controlla che tutti i conti non abbiano addebiti
             * se hanno degli addebiti avvisa che non verranno cancellati
             * e chiede conferma
             */
            codPresenze = this.getPresenze();
            codiciConto = PresenzaModulo.getContiAperti(codPresenze);

            for (int cod : codiciConto) {
                totAddebiti = ContoModulo.getTotAddebiti(cod, null, Progetto.getConnessione());
                if (totAddebiti != 0) {
                    continua = false;
                    testo = "Attenzione! Esistono degli addebiti su conti aperti!\n";
                    testo += "I conti che hanno degli addebiti non verranno cancellati.\n";
                    testo += "Vuoi continuare ugualmente?";
                    messaggio = new MessaggioDialogo(testo);
                    if (messaggio.isConfermato()) {
                        continua = true;
                    }// fine del blocco if
                    break;
                }// fine del blocco if
            }

            /* se ha passato i controli rimanda alla superclasse */
            if (continua) {
                super.eventoConferma();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna il filtro che isola le presenze delle quali annullare l'arrivo.
     * <p/>
     *
     * @return il filtro delle presenze
     */
    private Filtro getFiltroPresenze() {
        return PresenzaModulo.getFiltroAperte(this.getCodCamera());
    }


    /**
     * Ritorna l'elenco dei codici delle presenze delle quali annullare l'arrivo.
     * <p/>
     *
     * @return l'elenco presenze
     */
    public int[] getPresenze() {
        /* variabili e costanti locali di lavoro */
        int[] presenze = null;
        Filtro filtro;
        Modulo mod;

        try {    // prova ad eseguire il codice
            mod = PresenzaModulo.get();
            filtro = this.getFiltroPresenze();
            presenze = mod.query().valoriChiave(filtro);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return presenze;
    }


    /**
     * Ritorna il codice della camera.
     * <p/>
     *
     * @return il codice della camera
     */
    public int getCodCamera() {
        /* variabili e costanti locali di lavoro */
        int codCamera = 0;
        Modulo modPeriodo;

        try {    // prova ad eseguire il codice
            modPeriodo = PeriodoModulo.get();
            codCamera = modPeriodo.query()
                    .valoreInt(Periodo.Cam.camera.get(), this.getCodPeriodo());
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codCamera;
    }


    /**
     * Ritorna il nome della camera.
     * <p/>
     *
     * @return il nome della camera
     */
    private String getNomeCamera() {
        /* variabili e costanti locali di lavoro */
        String nomeCamera = "";
        int codCamera;
        Modulo modCamera;

        try {    // prova ad eseguire il codice
            modCamera = CameraModulo.get();
            codCamera = this.getCodCamera();
            nomeCamera = modCamera.query().valoreStringa(Camera.Cam.camera.get(), codCamera);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nomeCamera;
    }


    public int getCodPeriodo() {
        return codPeriodo;
    }


    private void setCodPeriodo(int codPeriodo) {
        this.codPeriodo = codPeriodo;
    }


}// fine della classe