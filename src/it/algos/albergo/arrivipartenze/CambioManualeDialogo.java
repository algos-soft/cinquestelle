package it.algos.albergo.arrivipartenze;

import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;

import java.util.ArrayList;
import java.util.Date;

/**
 * Dialogo di conferma di un cambio manuale.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 5-giu-2008 ore 10.21.46
 */
public class CambioManualeDialogo extends MovimentoManualeDialogo {


    /**
     * Costruttore base con parametri.
     * <p/>
     */
    public CambioManualeDialogo() {
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

            this.setTitolo("Cambio manuale");

            /* etichetta del campo data movimento */
            this.setLabelCamData("Data del cambio");

            /* legenda del campo persone */
            this.setLegendaCamPersone("Selezionare la persona che cambia camera");

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
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice

            /* aggiunge graficamente i campi */
            campo = this.getCampo(NOME_CAMPO_DATA);
            this.addCampo(campo);
            campo = this.getCampo(NOME_CAMPO_PERSONE);
            this.addCampo(campo);
            campo = this.getCampo(NOME_CAMPO_CAMERA);
            this.addCampo(campo);
            campo = this.getCampo(NOME_CAMPO_CONTO);
            this.addCampo(campo);

            /* aggiorna il campo combo delle camere disponibili */
            this.updateComboCamere();

            super.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */


    protected void eventoMemoriaModificata(Campo campo) {

        try { // prova ad eseguire il codice
            super.eventoMemoriaModificata(campo);

            /* se modifico il campo popup camera destinazione
            * ricarica il popup dei conti disponibili*/
            if (campo.equals(this.getCampo(NOME_CAMPO_CAMERA))) {
                this.syncPopConti();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Invocato quando si seleziona una riga nel navigatore Presenze.
     * <p/>
     */
    protected void selezionePresenze() {
        /* variabili e costanti locali di lavoro */
        try { // prova ad eseguire il codice

            /* ricarica il popup conti */
            this.syncPopConti();

            /* seleziona il conto originale della presenza che cambia */
            this.setValore(NOME_CAMPO_CONTO, this.getCodContoPresenzaSelezionata());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Sincronizza il campo poupup conti.
     * <p/>
     */
    private void syncPopConti() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        Filtro filtro;

        try {    // prova ad eseguire il codice

            campo = this.getCampo(NOME_CAMPO_CONTO);
            filtro = this.getFiltroConti();
            campo.setFiltroCorrente(filtro);
            campo.avvia();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna un filtro che seleziona i conti disponibili
     * per il cambio correntemente impostato.
     * <p/>
     * Se non sono ancora state specificate la presenza che cambia e la camera
     * di destinazione, il filtro non seleziona alcon conto.
     * Se sono stati specificati uno o l'altro, il filtro seleziona il conto della
     * presenza di provenienza più tutti i conti aperti sulla camera di destinazione.
     *
     * @return il filtro per selezionare i conti disponibili
     */
    private Filtro getFiltroConti() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Modulo modPresenza;
        Modulo modConto;
        int codPresenza;
        int codCamera;
        boolean datiDisponibili;
        ArrayList<Integer> codiciConto = null;
        int codContoOriginale = 0;
        int[] presenzeDest;
        int codConto;
        Filtro unFiltro;

        try {    // prova ad eseguire il codice

            /* recupera i moduli necessari */
            modPresenza = PresenzaModulo.get();
            modConto = ContoModulo.get();

            /* crea inizialmente un filtro che non seleziona alcun conto */
            filtro = FiltroFactory.codice(modConto, -1);

            /* determina se entrambi i dati sono disponibili */
            codPresenza = this.getCodPresenzaSelezionata();
            codCamera = this.getCodCamera();

            datiDisponibili = ((codPresenza > 0) || (codCamera > 0));

            /* se i dati sono disponibili crea il filtro conti */
            if (datiDisponibili) {

                /* codice del conto originale */
                if (codPresenza > 0) {
                    codContoOriginale = this.getCodContoPresenzaSelezionata();
                }// fine del blocco if

                /* codici dei conti aperti relativi alle presenze aperte
                 * nella camera destinazione */
                if (codCamera > 0) {

                    /* tutte le presenze aperte nella camera destinazione */
                    unFiltro = PresenzaModulo.getFiltroAperte(codCamera);
                    presenzeDest = modPresenza.query().valoriChiave(unFiltro);

                    codiciConto = new ArrayList<Integer>();
                    for (int cod : presenzeDest) {
                        codConto = modPresenza.query().valoreInt(Presenza.Cam.conto.get(), cod);
                        codiciConto.add(codConto);
                    }
                }// fine del blocco if

                /* filtro completo */
                filtro = new Filtro();

                if (codContoOriginale > 0) {
                    unFiltro = FiltroFactory.codice(modConto, codContoOriginale);
                    filtro.add(Filtro.Op.OR, unFiltro);
                }// fine del blocco if

                if (codiciConto != null) {
                    for (int cod : codiciConto) {
                        unFiltro = FiltroFactory.codice(modConto, cod);
                        filtro.add(Filtro.Op.OR, unFiltro);
                    }
                }// fine del blocco if


            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Ritorna il codice del conto associato alla presenza selezionata.
     * <p/>
     *
     * @return il codice del conto
     */
    private int getCodContoPresenzaSelezionata() {
        /* variabili e costanti locali di lavoro */
        int codConto = 0;
        int codPresenza;
        Modulo modPresenza;

        try {    // prova ad eseguire il codice
            modPresenza = PresenzaModulo.get();
            codPresenza = this.getCodPresenzaSelezionata();
            if (codPresenza > 0) {
                codConto = modPresenza.query().valoreInt(Presenza.Cam.conto.get(), codPresenza);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codConto;
    }


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
                confermabile = this.isPresenzaSelezionata();
            }// fine del blocco if

            /* controllo che ci sia una camera destinazione selezionata */
            if (confermabile) {
                confermabile = this.isCameraSelezionata();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermabile;
    }


    protected void eventoUscitaCampoModificato(Campo campo) {

        try { // prova ad eseguire il codice

            super.eventoUscitaCampoModificato(campo);

            /**
             * se modifico la data di arrivo regolo il flag bambino
             * e ricarico la lista delle camere
             */
            if (campo.equals(this.getCampo(NOME_CAMPO_DATA))) {
                this.updateComboCamere();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

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
        Date dataArrivo;
        Date dataCambio;
        int codCamOrigine;
        int codCamDestinazione;


        try { // prova ad eseguire il codice

            /**
             * Controlla che la data di cambio non sia precedente alla data di arrivo
             * in questo caso visualizza un messaggio e impedisce di confermare
             */
            codPresenza = this.getCodPresenzaSelezionata();
            modPresenza = PresenzaModulo.get();
            dataArrivo = modPresenza.query().valoreData(Presenza.Cam.arrivo.get(), codPresenza);
            dataCambio = this.getDataMovimento();
            if (Lib.Data.isPrecedente(dataArrivo, dataCambio)) {
                testo = "La data del cambio non può essere anteriore alla data dell'arrivo.";
                new MessaggioAvviso(testo);
                continua = false;
            }// fine del blocco if

            /**
             * controlla che la camera di origine sia diversa dalla camera destinazione
             */
            if (continua) {
                codPresenza = this.getCodPresenzaSelezionata();
                modPresenza = PresenzaModulo.get();
                codCamOrigine = modPresenza.query().valoreInt(Presenza.Cam.camera.get(),
                        codPresenza);
                codCamDestinazione = this.getCodCamera();

                if (codCamOrigine == codCamDestinazione) {
                    testo =
                            "La camera di origine deve essere diversa dalla camera di destinazione.";
                    new MessaggioAvviso(testo);
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* se la data di cambio è uguale alla data di arrivo chiede conferma */
            if (continua) {
                if (dataArrivo.equals(dataCambio)) {
                    testo =
                            "Attenzione! La data di cambio è uguale alla data di arrivo.\n" +
                                    "Vuoi continuare ugualmente?";
                    messaggio = new MessaggioDialogo(testo);
                    continua = messaggio.isConfermato();
                }// fine del blocco if
            }// fine del blocco if

            /**
             * controlla che la presenza che si fa cambiare non sia l'unica nella camera
             * in caso contrario avvisa e chiede conferma
             */
            if (continua) {
                if (this.isUnicaPresenza()) {
                    if (this.isFinePeriodo()) {
                        continua=true;
                        int a = 87;
                    } else {
                        testo =
                        "Attenzione! Confermando il cambio di questo cliente\n" +
                                "la camera rimarrà vuota.\nVuoi continuare ugualmente?";
                        messaggio = new MessaggioDialogo(testo);
                        continua = messaggio.isConfermato();
                    }// fine del blocco if-else


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