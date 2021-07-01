package it.algos.albergo.prenotazione.periodo;

import it.algos.albergo.arrivipartenze.ArriviPartenzeDialogo;
import it.algos.albergo.arrivipartenze.ArriviPartenzeLogica;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.base.dialogo.Dialogo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;

import java.util.ArrayList;
import java.util.Date;

public final class PeriodoNavCambi extends PeriodoNavDialogo {

    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento
     * @param dialogo di riferimento
     */
    public PeriodoNavCambi(Modulo modulo, Dialogo dialogo) {
        /* rimanda al costruttore della superclasse */
        super(modulo, dialogo);

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
            this.setCampoFlagMovimentazione(ArriviPartenzeDialogo.Nomi.effettuato.get());

            /* testo specifico per il termine "movimentare */
            this.setTestoMovimentare("cambiare");

            this.setRigheLista(6);
            this.setVista(this.getModulo().getVistaDefault());
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Ritorna il codice della prenotazione corrispondente
     * alla riga correntemente selezionata nella lista.
     * <p/>
     *
     * @return il codice della prenotazione
     */
    protected int getCodPrenSelezionata() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int codRiga;
        int codPrenotazione = 0;
        Modulo modulo;


        try {    // prova ad eseguire il codice

            codRiga = this.getLista().getChiaveSelezionata();
            continua = (codRiga > 0);
            if (continua) {
                modulo = this.getModulo();
                codPrenotazione =
                        modulo.query().valoreInt(ArriviPartenzeDialogo.Nomi.prenotazione.get(),
                                codRiga);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codPrenotazione;
    }


    /**
     * Ritorna il codice del periodo di provenienza corrispondente
     * alla riga correntemente selezionata nella lista.
     * <p/>
     *
     * @return il codice del periodo di provenienza
     */
    private int getCodPeriodoProvenienza() {
        /* variabili e costanti locali di lavoro */
        int codPeriodo = 0;
        boolean continua;
        int codRiga;
        Modulo modulo;


        try {    // prova ad eseguire il codice

            codRiga = this.getLista().getChiaveSelezionata();
            continua = (codRiga > 0);
            if (continua) {
                modulo = this.getModulo();
                codPeriodo =
                        modulo.query().valoreInt(ArriviPartenzeDialogo.Nomi.codPeriodo.get(),
                                codRiga);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codPeriodo;
    }

    /**
     * Ritorna il codice del periodo di destinazione corrispondente
     * alla riga correntemente selezionata nella lista.
     * <p/>
     *
     * @return il codice del periodo di destinazione
     */
    private int getCodPeriodoDestinazione() {
        /* variabili e costanti locali di lavoro */
        int codPeriodoDestinazione = 0;
        int codPeriodoProvenienza = 0;
        Modulo modPeriodo;

        try {    // prova ad eseguire il codice
            modPeriodo = PeriodoModulo.get();
            codPeriodoProvenienza = this.getCodPeriodoProvenienza();
            codPeriodoDestinazione = modPeriodo.query().valoreInt(Periodo.Cam.linkDestinazione.get(), codPeriodoProvenienza);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codPeriodoDestinazione;
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
        int codChiave;
        Modulo mod;
        boolean spuntato;

        try { // prova ad eseguire il codice
            confermabile = super.isConfermabile();
            if (confermabile) {
                codChiave = this.getLista().getChiaveSelezionata();
                mod = this.getModulo();
                spuntato = mod.query().valoreBool(ArriviPartenzeDialogo.Nomi.effettuato.get(),
                        codChiave);
                confermabile = !spuntato;
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermabile;
    }


    /**
     * Conferma il movimento selezionato.
     * <p/>
     *
     * @param codMemoria codice del periodo selezionato da confermare
     */
    @Override protected void conferma(int codMemoria) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int codPrimoPeriodo = 0;
        Modulo modMemoria = null;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(codMemoria));

            if (continua) {
                modMemoria = this.getModulo();
                continua = (modMemoria != null);
            }// fine del blocco if

            /* controlla che il cambio non sia già stato effettuato */
            if (continua) {
                if (modMemoria.query().valoreBool(ArriviPartenzeDialogo.Nomi.effettuato.get(),
                        codMemoria)) {
                    new MessaggioAvviso("Cambio già effettuato");
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* converte da memoria a codice periodo */
            if (continua) {
                codPrimoPeriodo =
                        modMemoria.query().valoreInt(ArriviPartenzeDialogo.Nomi.codPeriodo.get(),
                                codMemoria);
            }// fine del blocco if

            /* esegue la conferma dell'arrivo */
            if (continua) {
                continua = ArriviPartenzeLogica.confermaCambio(codPrimoPeriodo);
            }// fine del blocco if

            /* aggiorna la lista del navigatore */
            if (continua) {
                this.aggiornaLista();
            }// fine del blocco if

            if (continua) {
                new MessaggioAvviso("Cambio camera eseguito.");
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    protected void annulla(int codPeriodo) {
        new MessaggioAvviso("Funzione non ancora disponibile.");
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
        return PeriodoModulo.getNumPersoneCambio(codPeriodo, TipoPersona.persona);
    }


    /**
     * Ritorna il numero di camere non ancora movimentate.
     * <p/>
     *
     * @return il numero di camere non ancora movimentate
     */
    protected int getQuanteCamereNonMovimentate() {
        /* variabili e costanti locali di lavoro */
        int quanti = 0;
        Modulo modulo;
        Filtro filtro;

        try {    // prova ad eseguire il codice
            modulo = this.getModulo();
            filtro = FiltroFactory.creaFalso(ArriviPartenzeDialogo.Nomi.effettuato.get());
            quanti = modulo.query().contaRecords(filtro);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return quanti;
    }


    /**
     * Ritorna il numero di persone non ancora movimentate.
     * <p/>
     *
     * @return il numero di persone non ancora movimentate
     */
    protected int getQuantePersoneNonMovimentate() {
        /* variabili e costanti locali di lavoro */
        int quanti = 0;
        Modulo modulo;
        Filtro filtro;
        ArrayList<Integer> periodi;

        try {    // prova ad eseguire il codice
            modulo = this.getModulo();
            filtro = this.getFiltroPeriodiNonMovimentati();
            periodi = modulo.query().valoriCampo(ArriviPartenzeDialogo.Nomi.codPeriodo.get(),
                    filtro);
            for (int codPeriodo : periodi) {
                quanti += this.getPersonePeriodo(codPeriodo);
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return quanti;
    }

    /**
     * Ritorna true se l'azione Vai alle Presenze è abilitabile.
     * <p/>
     * @return true se abilitabile
     */
    protected boolean isVaiPresenzeAbilitabile () {
        /* variabili e costanti locali di lavoro */
        boolean continua=false;
        Modulo modPeriodo;
        int codProvenienza=0;

        try { // prova ad eseguire il codice

            modPeriodo = PeriodoModulo.get();

            /* ci deve essere 1 sola riga selezionata */
            continua = (this.getLista().getQuanteRigheSelezionate()==1);

            /* recupera il periodo di provenienza */
            if (continua) {
                codProvenienza = this.getCodPeriodoProvenienza();
                continua = (codProvenienza>0);
            }// fine del blocco if

            /* il periodo di provenienza deve essere arrivato */
            if (continua) {
                continua = modPeriodo.query().valoreBool(Periodo.Cam.arrivato.get(), codProvenienza);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Ritorna il filtro che seleziona le presenze relativamente a un periodo arrivato.
     * <p/>
     * ATTENZIONE IL CODICE PERIODO IN QUESTA CLASSE VIENE IGNORATO
     * IL FILTRO RITORNATO PUO' ESSERE RELATIVO A UNO O DUE PERIODI
     * @param codPeriodo il codice del periodo
     * @return il filtro che seleziona le presenze
     */
    protected Filtro getFiltroPresenze (int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro=null;
        Filtro filtroUscite;
        Filtro filtroEntrate;
        int codProvenienza=0;
        int codDestinazione=0;
        boolean uscito;
        int codCameraProv;
        int codCameraDest;
        Modulo modPeriodo;
        Date dataCambio;


        try { // prova ad eseguire il codice

            modPeriodo = PeriodoModulo.get();
            codProvenienza = this.getCodPeriodoProvenienza();
            uscito = modPeriodo.query().valoreBool(Periodo.Cam.partito.get(), codProvenienza);
            codCameraProv = modPeriodo.query().valoreInt(Periodo.Cam.camera.get(), codProvenienza);
            if (!uscito) { // non ancora cambiato
                filtro = PresenzaModulo.getFiltroAperteSmart(codCameraProv);
            } else {       // già cambiato
                codDestinazione = this.getCodPeriodoDestinazione();
                dataCambio = modPeriodo.query().valoreData(Periodo.Cam.partenzaEffettiva.get(), codProvenienza);
                codCameraDest = modPeriodo.query().valoreInt(Periodo.Cam.camera.get(), codDestinazione);
                filtroUscite = PresenzaModulo.getFiltroPresenzeUsciteCambio(dataCambio);;
                filtroUscite.add(FiltroFactory.crea(Presenza.Cam.camera.get(), codCameraProv));
                filtroEntrate = PresenzaModulo.getFiltroPresenzeEntrateCambio(dataCambio);
                filtroEntrate.add(FiltroFactory.crea(Presenza.Cam.camera.get(), codCameraDest));
                filtro = new Filtro();
                filtro.add(filtroUscite);
                filtro.add(Filtro.Op.OR, filtroEntrate);
            }// fine del blocco if-else


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }




}
