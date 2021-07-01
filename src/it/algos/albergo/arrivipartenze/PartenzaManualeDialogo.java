package it.algos.albergo.arrivipartenze;

import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.albergo.stampeobbligatorie.StampeObbLogica;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modulo.Modulo;

import java.util.Date;

/**
 * Dialogo di conferma di una partenza manuale.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 5-giu-2008 ore 10.21.46
 */
public class PartenzaManualeDialogo extends MovimentoManualeDialogo {


    /**
     * Costruttore base con parametri.
     * <p/>
     */
    public PartenzaManualeDialogo() {
        /* rimanda al costruttore della superclasse */
        super();

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

        try { // prova ad eseguire il codice

            this.setTitolo("Partenza manuale");

            /* etichetta del campo data movimento */
            this.setLabelCamData("Data di partenza");

            /* legenda del campo persone */
            this.setLegendaCamPersone("Selezionare la persona in partenza");

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

            /* aggiunge graficamente i campi */
            campo = this.getCampo(NOME_CAMPO_DATA);
            this.addCampo(campo);
            campo = this.getCampo(NOME_CAMPO_PERSONE);
            this.addCampo(campo);

            super.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */


    /**
     * Determina se il dialogo e' confermabile o registrabile.
     * <p/>
     *
     * @return true se confermabile / registrabile
     */
    @Override
    public boolean isConfermabile() {
        /* variabili e costanti locali di lavoro */
        boolean confermabile = false;

        try { // prova ad eseguire il codice

            /* controllo nella superclasse */
            confermabile = super.isConfermabile();

            /* controllo che ci sia una presenza selezionata */
            if (confermabile) {
                confermabile = this.getNavPresenze().getLista().isRigaSelezionata();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermabile;
    }


    /**
     * Invocato quando si preme il bottone conferma.
     * <p/>
     */
    public void eventoConferma() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        MessaggioDialogo messaggio;
        String testo;
        Modulo modPresenza;
        int codPresenza;
        Date dataArrivo=null;
        Date dataPartenza=null;
        boolean registrabile;

        try { // prova ad eseguire il codice


            /**
             * Controlla che la partenza sia confermabile
             */
            if (continua) {
                dataPartenza = this.getDataMovimento();
                int codAzienda = this.getCodAziendaPresenzaSelezionata();
                registrabile = StampeObbLogica.isPartenzaRegistrabile(dataPartenza, codAzienda);
                if (!registrabile) {
                    testo = ArriviPartenzeLogica.getMessaggioPartenzeNonConfermabili(codAzienda);
                    new MessaggioAvviso(testo);
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /**
            * Controlla che la data di partenza non sia precedente alla data di arrivo
            * in questo caso visualizza un messaggio e impedisce di confermare
            */
            if (continua) {
                codPresenza = this.getCodPresenzaSelezionata();
                modPresenza = PresenzaModulo.get();
                dataArrivo = modPresenza.query().valoreData(Presenza.Cam.arrivo.get(), codPresenza);
                dataPartenza = this.getDataMovimento();
                if (Lib.Data.isPrecedente(dataArrivo, dataPartenza)) {
                    testo = "La data di partenza non può essere anteriore alla data di arrivo.";
                    new MessaggioAvviso(testo);
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if


            /* se la data di partenza è uguale alla data di arrivo chiede conferma */
            if (continua) {
                if (dataArrivo.equals(dataPartenza)) {
                    testo =
                            "Attenzione! La data di partenza è uguale alla data di arrivo.\n" +
                                    "Vuoi continuare ugualmente?";
                    messaggio = new MessaggioDialogo(testo);
                    continua = messaggio.isConfermato();
                }// fine del blocco if
            }// fine del blocco if

            /**
             * controlla che la presenza che si fa partire non sia l'unica nella camera
             * in caso contrario avvisa e chiede conferma
             */
            if (continua) {
                if (this.isUnicaPresenza()) {
                    testo =
                            "Attenzione! Confermando la partenza di questo cliente\n" +
                                    "la camera rimarrà vuota.\nVuoi continuare ugualmente?";
                    messaggio = new MessaggioDialogo(testo);
                    continua = messaggio.isConfermato();
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                super.eventoConferma();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


}// fine della classe