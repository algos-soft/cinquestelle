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
import it.algos.base.query.filtro.Filtro;
import it.algos.gestione.anagrafica.Anagrafica;

import java.util.Date;

public final class PeriodoNavPartenze extends PeriodoNavDialogo {

    /**
     * Costruttore completo con parametri.
     *
     * @param dialogo di riferimento
     */
    public PeriodoNavPartenze(Dialogo dialogo) {
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
            this.setCampoFlagMovimentazione(Periodo.Cam.partito.get());

            /* testo specifico per il termine "movimentare */
            this.setTestoMovimentare("partire");

            /* assegna la vista */
            this.setVista(PeriodoModulo.get().getVista(Periodo.Vis.partenze.toString()));

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }// fine del metodo inizia


    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        super.inizializza();

        try { // prova ad eseguire il codice
            campo = ClienteAlbergoModulo.get().getCampo(Anagrafica.Cam.soggetto);
            this.getLista().setCampoOrdinamento(campo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizializza


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
                confermabile = !this.isPartito();
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
                annullabile = this.isPartito();
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
    private boolean isPartito() {
        /* variabili e costanti locali di lavoro */
        boolean partito = false;
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
                partito = mod.query().valoreBool(Periodo.Cam.partito.get(), cod);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return partito;
    }

    /**
     * Ritorna il filtro che seleziona le presenze relativamente a un periodo.
     * <p/>
     * @param codPeriodo il codice del periodo
     * @return il filtro che seleziona le presenze
     */
    protected Filtro getFiltroPresenze (int codPeriodo) {
        return PeriodoModulo.getFiltroPresenzePartenza(codPeriodo);
    }



    /**
     * Conferma il movimento selezionato.
     * <p/>
     *
     * @param codPeriodo codice del periodo selezionato da confermare
     */
    @Override protected void conferma(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        boolean continua;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(codPeriodo));

            /* esegue la conferma dell'arrivo */
            if (continua) {

                // presenta il dialogo ed esegue
                ArriviPartenzeLogica.confermaPartenza(codPeriodo);

                // aggiorna la lista anche se non ha confermato la partenza
                // potrebbe aver solo chiuso il conto
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
                continua = ArriviPartenzeLogica.annullaPartenza(codPeriodo);
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
     * Stampa il report delle partenze.
     * <p/>
     * Recupera le date di inizio e fine delle partenze <br>
     */
    @Override public void stampaLista() {
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
                StampaMovimenti.stampaPartenze(dataInizio, dataFine);
            } else {
                new MessaggioAvviso("Le date indicate non sono valide");
            }// fine del blocco if-else

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
        return PeriodoModulo.getNumPersonePartenza(codPeriodo);
    }


}
