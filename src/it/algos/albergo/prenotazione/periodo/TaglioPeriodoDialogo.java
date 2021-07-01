package it.algos.albergo.prenotazione.periodo;

import it.algos.albergo.camera.Camera;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;

import java.util.Date;

public class TaglioPeriodoDialogo extends DialogoAnnullaConferma {

    /**
     * codice del periodo che sto tagliando
     */
    private int codPeriodo;

    private int cam;

    private final static String INIZIO = "inizio";

    private final static String FINE = "fine";

    private final static String CAMBIO = "cambio";

    private final static String CAM_UNO = "camera iniziale";

    private final static String CAM_DUE = "camera finale";


    /**
     * Costruttore senza parametri.
     * <p/>
     */
    public TaglioPeriodoDialogo() {
        this(null);
    } /* fine del metodo costruttore completo */


    /**
     * Costruttore base con parametri.
     * <p/>
     */
    public TaglioPeriodoDialogo(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

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
     * Metodo invocato direttamente dal costruttore (init) <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice
            super.setTitolo("Divisione del periodo");
            this.creaCampi();
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

        try { // prova ad eseguire il codice
            super.inizializza();
            this.setCamUno(this.getCam());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */


    private void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        try { // prova ad eseguire il codice
            unCampo = CampoFactory.data(INIZIO);
            unCampo.setAbilitato(false);
            unCampo.decora().obbligatorio();
            this.addCampo(unCampo);

            unCampo = CampoFactory.data(CAMBIO);
            unCampo.decora().obbligatorio();
            this.addCampo(unCampo);

            unCampo = CampoFactory.data(FINE);
            unCampo.setAbilitato(false);
            unCampo.decora().obbligatorio();
            this.addCampo(unCampo);

            unCampo = CampoFactory.comboLinkSel(CAM_UNO);
            unCampo.setNomeModuloLinkato(Camera.NOME_MODULO);
            unCampo.setNomeCampoValoriLinkato(Camera.CAMPO_SIGLA);
            unCampo.setUsaNuovo(false);
            unCampo.decora().obbligatorio();
            this.addCampo(unCampo);

            unCampo = CampoFactory.comboLinkSel(CAM_DUE);
            unCampo.setNomeModuloLinkato(Camera.NOME_MODULO);
            unCampo.setNomeCampoValoriLinkato(Camera.CAMPO_SIGLA);
            unCampo.setUsaNuovo(false);
            unCampo.decora().obbligatorio();
            this.addCampo(unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Invocato quando si preme il bottone conferma o registra.
     * <p/>
     */
    @Override
    public void confermaRegistra() {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;


        try { // prova ad eseguire il codice
            continua = this.isConfermabile();

            /* controllo sequenza date */
            if (continua) {
                continua = this.checkDate();
                if (!continua) {
                    new MessaggioAvviso(
                            "La data di cambio non è compresa tra le date di inizio e fine");
                }// fine del blocco if
            }// fine del blocco if

            /* controllo camere uguali */
            if (continua) {
                continua = this.checkCamere();
                if (!continua) {
                    new MessaggioAvviso("Le camere devono essere diverse");
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                continua = this.checkPeriodo1();
            }// fine del blocco if

            if (continua) {
                continua = this.checkPeriodo2();
            }// fine del blocco if

            if (continua) {
                super.confermaRegistra();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Controlla la sequenza delle date inizio, cambio e fine.
     * <p/>
     *
     * @return true se la sequenza è corretta
     */
    private boolean checkDate() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        Date ini;
        Date cambio;
        Date end;

        try {    // prova ad eseguire il codice
            ini = this.getInizio();
            cambio = this.getCambio();
            end = this.getFine();

            riuscito = (Lib.Data.isPosteriore(ini, cambio) && Lib.Data.isPrecedente(end, cambio));

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Controlla che le due camere siano0 diverse.
     * <p/>
     *
     * @return true se le camere sono diverse
     */
    private boolean checkCamere() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        int cam1;
        int cam2;

        try {    // prova ad eseguire il codice
            cam1 = this.getCamUno();
            cam2 = this.getCamDue();
            riuscito = (cam1 != cam2);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Controlla la validità del primo periodo.
     * <p/>
     *
     * @return true se il periodo è libero
     */
    private boolean checkPeriodo1() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        Date ini;
        Date cambio;
        int camera;

        try {    // prova ad eseguire il codice
            ini = this.getInizio();
            cambio = this.getCambio();
            camera = this.getCamUno();
            riuscito = this.checkPeriodo(ini, cambio, camera);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Controlla la validità del secondo periodo.
     * <p/>
     *
     * @return true se il periodo è libero
     */
    private boolean checkPeriodo2() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        Date cambio;
        Date end;
        int camera;

        try {    // prova ad eseguire il codice
            cambio = this.getCambio();
            end = this.getFine();
            camera = this.getCamDue();
            riuscito = this.checkPeriodo(cambio, end, camera);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Controlla la validità di un periodo.
     * <p/>
     *
     * @return true se il periodo è libero
     */
    private boolean checkPeriodo(Date inizio, Date fine, int camera) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua;
        int codPeriodo;
        String messaggio;
        int codSovrapposto;

        try {    // prova ad eseguire il codice
            codPeriodo = this.getCodPeriodo();
            continua =
                    (codPeriodo > 0 &&
                            !Lib.Data.isVuota(inizio) &&
                            !Lib.Data.isVuota(fine) &&
                            camera > 0);

            if (continua) {
                codSovrapposto = PeriodoLogica.getCodPeriodoSovrapposto(inizio,
                        fine,
                        camera,
                        codPeriodo);
                if (codSovrapposto == 0) {
                    riuscito = true;
                }// fine del blocco if

                /* se esistono periodi sovrapposti, costruisco il messaggio
                 * specifico e lo assegno al controllo */
                if (!riuscito) {
                    messaggio = PeriodoLogica.getMessaggioOccupato(codSovrapposto);
                    new MessaggioAvviso(messaggio);
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    private int getCodPeriodo() {
        return codPeriodo;
    }


    public void setCodPeriodo(int codPeriodo) {
        this.codPeriodo = codPeriodo;
    }


    public Date getInizio() {
        return Libreria.getDate(this.getCampo(INIZIO).getValore());
    }


    public void setInizio(Date inizio) {
        this.getCampo(INIZIO).setValore(inizio);
    }


    public Date getFine() {
        return Libreria.getDate(this.getCampo(FINE).getValore());
    }


    public void setFine(Date fine) {
        this.getCampo(FINE).setValore(fine);
    }


    public Date getCambio() {
        return Libreria.getDate(this.getCampo(CAMBIO).getValore());
    }


    public void setCambio(Date cambio) {
        this.getCampo(CAMBIO).setValore(cambio);
    }


    public int getCam() {
        return cam;
    }


    public void setCam(int cam) {
        this.cam = cam;
    }


    public int getCamUno() {
        return Libreria.getInt(this.getCampo(CAM_UNO).getValore());
    }


    public void setCamUno(int camUno) {
        this.getCampo(CAM_UNO).setValore(camUno);
    }


    public int getCamDue() {
        return Libreria.getInt(this.getCampo(CAM_DUE).getValore());
    }


    public void setCamDue(int camDue) {
        this.getCampo(CAM_DUE).setValore(camDue);
    }
}
