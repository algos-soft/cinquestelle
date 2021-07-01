package it.algos.albergo.prenotazione.periodo;

import it.algos.albergo.arrivipartenze.ArriviPartenzeDialogo;
import it.algos.albergo.arrivipartenze.ArriviPartenzeLogica;
import it.algos.albergo.arrivipartenze.StampaMovimenti;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.dialogo.Dialogo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.lista.Lista;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.gestione.anagrafica.Anagrafica;

import java.util.Date;

public final class PeriodoNavArrivi extends PeriodoNavDialogo {

    /**
     * Costruttore completo con parametri.
     *
     * @param dialogo di riferimento
     */
    public PeriodoNavArrivi(Dialogo dialogo) {
        /* rimanda al costruttore della superclasse */
        super(dialogo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try {    // prova ad eseguire il codice

            /* assegna il riferimento al campo flag di avvenuta movimentazione */
            this.setCampoFlagMovimentazione(Periodo.Cam.arrivato.get());

            /* testo specifico per il termine "movimentare */
            this.setTestoMovimentare("arrivare");

            /* assegna la vista */
            this.setVista(PeriodoModulo.get().getVista(Periodo.Vis.arrivi.toString()));

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }// fine del metodo inizia


    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice

            campo = ClienteAlbergoModulo.get().getCampo(Anagrafica.Cam.soggetto);
            this.getLista().setCampoOrdinamento(campo);

            super.inizializza();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizializza


    /**
     * Conferma il movimento selezionato.
     * <p/>
     *
     * @param codPeriodo codice del periodo selezionato da confermare
     */
    protected void conferma(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        boolean continua;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(codPeriodo));

            /* esegue la conferma dell'arrivo */
            if (continua) {
                continua = ArriviPartenzeLogica.confermaArrivo(codPeriodo);
            }// fine del blocco if

            /* aggiorna la lista del navigatore */
            if (continua) {
                this.aggiornaLista();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Annulla il movimento selezionato.
     * <p/>
     *
     * @param codPeriodo codice del periodo selezionato da annullare
     */
    protected void annulla(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        boolean continua;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(codPeriodo));

            /* esegue la conferma dell'arrivo */
            if (continua) {
                continua = ArriviPartenzeLogica.annullaArrivo(codPeriodo);
            }// fine del blocco if

            /* aggiorna la lista del navigatore */
            if (continua) {
                this.aggiornaLista();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Controlla l'abilitazione del pulsante conferma.
     * <p/>
     *
     * @return confermabile
     */
    protected boolean isConfermabile() {
        /* variabili e costanti locali di lavoro */
        boolean confermabile = false;

        try { // prova ad eseguire il codice
            confermabile = super.isConfermabile();
            if (confermabile) {
                confermabile = !this.isArrivato();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermabile;
    }


    /**
     * Controlla l'abilitazione del pulsante annulla.
     * <p/>
     *
     * @return annullabile
     */
    protected boolean isAnnullabile() {
        /* variabili e costanti locali di lavoro */
        boolean annullabile = false;

        try { // prova ad eseguire il codice
            annullabile = super.isAnnullabile();

            if (annullabile) {
                annullabile = this.isArrivato();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return annullabile;
    }


    /**
     * Controlla l'abilitazione del pulsante annulla.
     * <p/>
     *
     * @return annullabile
     */
    private boolean isArrivato() {
        /* variabili e costanti locali di lavoro */
        boolean arrivato = false;
        boolean continua;
        Lista lista;
        int cod = 0;
        Modulo mod = null;

        try { // prova ad eseguire il codice

            lista = this.getLista();
            continua = (lista != null);

            if (continua) {
                cod = lista.getChiaveSelezionata();
                continua = (cod > 0);
            }// fine del blocco if

            if (continua) {
                mod = this.getModulo();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                arrivato = mod.query().valoreBool(Periodo.Cam.arrivato.get(), cod);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return arrivato;
    }


    /**
     * Sincronizza il Navigatore.
     * <p/>
     * Controlla lo stato di tutti i suoi componenti <br>
     * Elabora la sua business logic <br>
     * Regola la GUI di conseguenza <br>
     */
    @Override
    public void sincronizza() {

        try { // prova ad eseguire il codice

            super.sincronizza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna il numero di persone relative a un periodo.
     * <p/>
     *
     * @param codPeriodo il codice del periodo
     *
     * @return il numero di persone relative al periodo
     */
    protected int getPersonePeriodo(int codPeriodo) {
        return PeriodoModulo.getNumPersoneArrivo(codPeriodo);
    }


    /**
     * Stampa il report degli arrivi.
     * <p/>
     * Recupera le date di inizio e fine degli arrivi <br>
     */
    @Override
    public void stampaLista() {
        /* variabili e costanti locali di lavoro */
        Dialogo dialogo;
        ArriviPartenzeDialogo dialogoAP;
        Date dataInizio = null;
        Date dataFine = null;

        try { // prova ad eseguire il codice
            dialogo = this.getDialogoRif();

            if (dialogo instanceof ArriviPartenzeDialogo) {
                dialogoAP = (ArriviPartenzeDialogo)dialogo;
                dataInizio = dialogoAP.getData1();
                dataFine = dialogoAP.getData2();

            }// fine del blocco if

            if (Lib.Data.isValida(dataInizio) && Lib.Data.isValida(dataFine)) {
                StampaMovimenti.stampaArrivi(dataInizio, dataFine);
            } else {
                new MessaggioAvviso("Le date indicate non sono valide");
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


}
