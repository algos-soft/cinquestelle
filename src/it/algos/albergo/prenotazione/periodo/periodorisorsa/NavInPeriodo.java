package it.algos.albergo.prenotazione.periodo.periodorisorsa;

import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.form.Form;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;
import it.algos.base.scheda.Scheda;
import it.algos.base.toolbar.ToolBar;
import it.algos.base.wrapper.SetValori;

import java.util.Date;

public class NavInPeriodo extends NavigatoreLS {


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param modulo di riferimento
     */
    public NavInPeriodo(Modulo modulo) {
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
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice
            this.setNomeChiave(RisorsaPeriodo.Nav.navPeriodo.get());

            this.setUsaPannelloUnico(true);
            //this.setUsaToolBarLista(false);
            this.setUsaFinestraPop(true);
            setUsaRicerca(false);
            setUsaStampaLista(false);
            this.setRigheLista(4);
            this.getPortaleLista().setTipoIcona(ToolBar.ICONA_PICCOLA);
            this.setNomeVista(RisorsaPeriodo.Vis.inPeriodi.toString());
            
            this.addSchedaCorrente(new RisorsaPeriodoScheda(getModulo()));


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    public void inizializza() {

        super.inizializza();

        try { // prova ad eseguire il codice

            /* ordine fisso della lista */
            this.getLista().setOrdinabile(false);
            this.getLista().setOrdine(RisorsaPeriodoModulo.getOrdineRisorse());
            

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizializza


//    /**
//     * Sincronizza il Navigatore.
//     * <p/>
//     * Controlla lo stato di tutti i suoi componenti <br>
//     * Elabora la sua business logic <br>
//     * Regola la GUI di conseguenza <br>
//     */
//    @Override
//    public void sincronizza() {
//        /* variabili e costanti locali di lavoro */
//        Azione azione;
//        boolean abilita;
//
//
//        try { // prova ad eseguire il codice
//            super.sincronizza();
//
//            /* regola abilitazione modifica (sempre abilitato se la scheda è valida) */
//            azione = this.getPortaleLista().getAzione(Azione.MODIFICA_RECORD);
//            abilita = this.isModificaAbilitata();
//            azione.setEnabled(abilita);
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//    }


    /**
     * Verifica se si possono modificare le risorse.
     * <p/>
     *
     * @return true se si possono modificare le risorse
     */
    private boolean isModificaAbilitata() {
        /* variabili e costanti locali di lavoro */
        boolean abilitato = false;
        Scheda scheda;

        try { // prova ad eseguire il codice

            /* recupera la scheda parente */
            scheda = this.getSchedaParente();
            abilitato = scheda != null;

            /* verifica che sia attiva */
            if (abilitato) {
                abilitato = scheda.isModificabile();
            }// fine del blocco if

            /* verifica che sia valida (devono esserci inizio, fine, camera, persone) */
            if (abilitato) {
                abilitato = scheda.isValida();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
        /* valore di ritorno */
        return abilitato;
    }


//    /**
//     * Ritorna il codice di camera correntemente impostato nella scheda
//     * che contiene questo navigatore.
//     * <p/>
//     *
//     * @return il codice della camera
//     */
//    public int getCodCamera() {
//        /* variabili e costanti locali di lavoro */
//        int codCamera = 0;
//        Scheda scheda;
//
//        try {    // prova ad eseguire il codice
//            scheda = this.getSchedaParente();
//            if (scheda != null) {
//                codCamera = scheda.getInt(Periodo.Cam.camera.get());
//            }// fine del blocco if
//        } catch (Exception unErrore) {    // intercetta l'errore
//            new Errore(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return codCamera;
//    }


//    /**
//     * Ritorna il numero di persone correntemente impostato nella scheda
//     * che contiene questo navigatore.
//     * <p/>
//     *
//     * @return il numero di persone
//     */
//    public int getPersone() {
//        /* variabili e costanti locali di lavoro */
//        int persone = 0;
//        Scheda scheda;
//
//        try {    // prova ad eseguire il codice
//            scheda = this.getSchedaParente();
//            if (scheda != null) {
//                persone = scheda.getInt(Periodo.Cam.persone.get());
//            }// fine del blocco if
//        } catch (Exception unErrore) {    // intercetta l'errore
//            new Errore(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return persone;
//    }

    @Override
    /**
     * Assegna al nuovo record di impegno risorsa le date 
     * corrispondenti al periodo
     */
    protected boolean nuovoRecordAnte(SetValori set) {
        /* variabili e costanti locali di lavoro */
        try { // prova ad eseguire il codice
            Campo campo = this.getCampoPilota();
            if (campo!=null) {
                Scheda scheda = campo.getScheda();
                if (scheda!=null) {
                	Date data1 = scheda.getData(Periodo.Cam.arrivoPrevisto.get());
                	Date data2 = scheda.getData(Periodo.Cam.partenzaPrevista.get());
                    set.add(RisorsaPeriodo.Cam.dataInizio, data1);
                    set.add(RisorsaPeriodo.Cam.dataFine, data2);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return super.nuovoRecordAnte(set);
    }

    
    
	/**
     * Ritorna la data iniziale correntemente impostata nella scheda
     * che contiene questo navigatore.
     * <p/>
     *
     * @return la data iniziale
     */
    public Date getDataInizio() {
        /* variabili e costanti locali di lavoro */
        Date data = null;
        Scheda scheda;

        try {    // prova ad eseguire il codice
            scheda = this.getSchedaParente();
            if (scheda != null) {
                data = scheda.getData(Periodo.Cam.arrivoPrevisto.get());
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    /**
     * Ritorna la data finale correntemente impostata nella scheda
     * che contiene questo navigatore.
     * <p/>
     *
     * @return la data finale
     */
    public Date getDataFine() {
        /* variabili e costanti locali di lavoro */
        Date data = null;
        Scheda scheda;

        try {    // prova ad eseguire il codice
            scheda = this.getSchedaParente();
            if (scheda != null) {
                data = scheda.getData(Periodo.Cam.partenzaPrevista.get());
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    /**
     * Ritorna il codice chiave del periodo di riferimento
     * <p/>
     *
     * @return il codice del periodo
     */
    public int getCodPeriodo() {
        /* variabili e costanti locali di lavoro */
        int codPeriodo = 0;
        Scheda scheda;

        try {    // prova ad eseguire il codice
            scheda = this.getSchedaParente();
            if (scheda != null) {
                codPeriodo = scheda.getCodice();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codPeriodo;
    }


    /**
     * Ritorna la scheda che contiene questo navigatore.
     * <p/>
     */
    private Scheda getSchedaParente() {
        /* variabili e costanti locali di lavoro */
        Scheda scheda = null;
        boolean continua = true;
        Campo campo;
        Form form = null;


        try {    // prova ad eseguire il codice

            campo = this.getCampoPilota();
            continua = campo != null;

            if (continua) {
                form = campo.getForm();
                continua = form != null;
            }// fine del blocco if

            if (continua) {
                if (form instanceof Scheda) {
                    scheda = (Scheda)form;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return scheda;
    }


}
