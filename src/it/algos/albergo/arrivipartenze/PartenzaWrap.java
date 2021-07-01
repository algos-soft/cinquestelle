package it.algos.albergo.arrivipartenze;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.base.componente.WrapTextArea;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;

import java.util.ArrayList;
import java.util.Date;

/**
 * Wrapper per la stampa di una prenotazione in partenza.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 26-giu-2008
 */
public final class PartenzaWrap extends ArrivoPartenzaWrap {

    /**
     * Costruttore completo con parametri.
     *
     * @param wrapIn con prenotazione e periodi interessati
     */
    public PartenzaWrap(final PrenotazionePeriodiWrap wrapIn) {
        /* rimanda al costruttore della superclasse */
        super(wrapIn);
    } // fine del metodo costruttore completo


    /**
     * Aggiunge al pannello il singolo periodo.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param codPeriodo interessato
     *
     * @return area di testo flessibile
     */
    @Override protected WrapTextArea addPeriodo(final int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        WrapTextArea area = null;
        boolean continua;
        boolean arrivato = false;
        boolean partito = false;
        String stato;
        Modulo modPeriodo = null;
        Modulo modCamera = null;
        int codCamera = 0;
        String camera = "";
        String riga = "";
        String tagRigaPeriodo = " * ";
        String sep = " - ";
        String[] persone;
        int quantiAdulti;
        int quantiBambini;
        Filtro filtroPresenze;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (codPeriodo > 0);

            if (continua) {
                modPeriodo = PeriodoModulo.get();
                modCamera = CameraModulo.get();
                continua = (modPeriodo != null && modCamera != null);
            } // fine del blocco if

            /* camera (obbligatorio) */
            if (continua) {
                codCamera = modPeriodo.query().valoreInt(Periodo.Cam.camera.get(), codPeriodo);
                camera = modCamera.query().valoreStringa(Camera.Cam.camera.get(), codCamera);
                continua = (Lib.Testo.isValida(camera));
            } // fine del blocco if

            if (continua) {
                riga = tagRigaPeriodo;
                riga += " cam. " + camera;
            } // fine del blocco if

            /* stato arrivato - partito */
            if (continua) {
                arrivato = modPeriodo.query().valoreBool(Periodo.Cam.arrivato.get(), codPeriodo);
                partito = modPeriodo.query().valoreBool(Periodo.Cam.partito.get(), codPeriodo);
                if (arrivato) {
                    if (partito) {
                        stato = " (partito) ";
                    } else {
                        stato = " (in partenza) ";
                    } // fine del blocco if-else
                } else {
                    stato = " (non arrivato) ";
                }// fine del blocco if-else

                riga += stato;
            } // fine del blocco if

            /**
             * numero di adulti e di bambini
             */
            if (continua) {

                riga += sep;

                quantiAdulti = PeriodoModulo.getNumAdultiPartenza(codPeriodo);
                if (quantiAdulti>0) {
                    riga+=quantiAdulti;
                    if (quantiAdulti>1) {
                        riga += " adulti";
                    } else {
                        riga += " adulto";
                    }// fine del blocco if-else
                }// fine del blocco if

                quantiBambini = PeriodoModulo.getNumBambiniPartenza(codPeriodo);
                if (quantiBambini>0) {

                    if (quantiAdulti>0) {
                        riga+=" e ";
                    }// fine del blocco if

                    riga+=quantiBambini;
                    if (quantiBambini>1) {
                        riga += " bambini";
                    } else {
                        riga += " bambino";
                    }// fine del blocco if-else
                }// fine del blocco if

            }// fine del blocco if

            /**
             * nomi delle persone se arrivati
             */
            if (continua) {
                if (arrivato) {
                    filtroPresenze = PeriodoModulo.getFiltroPresenzePartenza(codPeriodo);
                    persone = this.getPersone(filtroPresenze);
                    riga += "\n";
                    riga += "(";
                    riga += Lib.Testo.getStringaVirgolaSpazio(persone);
                    riga += ")";
                }// fine del blocco if
            }// fine del blocco if


            if (continua) {
                area = new WrapTextArea(riga);
                area.setFont(getFontStampaBold());
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return area;
    }







    /**
     * Ritorna l'elenco delle presenze nella camera alla data.
     *
     * @param codCamera interessata
     * @param data di partenza (prevista o effettiva)
     * @param partito true se il periodo è chiuso
     *
     * @return matrice di nomi delle persone
     */
    public int[] getPresenze(final int codCamera, Date data, boolean partito) {
        /* variabili e costanti locali di lavoro */
        int[] presenze = null;
        boolean continua;
        Filtro filtro = null;
        PresenzaModulo mod;
        boolean esistono;

        try { // prova ad eseguire il codice
            mod = PresenzaModulo.get();
            continua = (mod != null);

            if (continua) {
//                filtro = PresenzaModulo.getFiltroCamera(codCamera);

                filtro = PresenzaModulo.getFiltroPresenze(codCamera, data);
                filtro.add(FiltroFactory.creaVero(Presenza.Cam.provvisoria));
            } // fine del blocco if

            if (continua) {
                esistono = mod.query().isEsisteRecord(filtro);

                /* se non esistono presenze "provvisorie" seleziona tutte */
                if (!esistono) {
                    filtro = PresenzaModulo.getFiltroPresenze(codCamera, data);
                }// fine del blocco if

            } // fine del blocco if

            // se esistono records con partenza vuota, rifare il filtro eliminando la partenza


            if (continua) {
                presenze = mod.query().valoriChiave(filtro);
                ArrayList<Integer> valide = new ArrayList<Integer>();
                for (int cod : presenze) {
                    if (this.isValida(cod, partito)) {
                        valide.add(cod);
                    } // fine del blocco if
                } // fine del ciclo for-each

                presenze = Lib.Array.creaIntArray(valide);

            } // fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return presenze;
    }


    /**
     * Validità della presenza.
     * <p/>
     *
     * @param codPresenza interessata
     * @param partito true se il periodo è chiuso
     */
    private boolean isValida(int codPresenza, boolean partito) {
        /* variabili e costanti locali di lavoro */
        boolean valida = false;
        Date dataChiusura;
        Modulo modPresenza;

        try {    // prova ad eseguire il codice
            modPresenza = PresenzaModulo.get();
            dataChiusura = modPresenza.query().valoreData(Presenza.Cam.uscita.get(), codPresenza);
            if (partito) {
                valida = Lib.Data.isValida(dataChiusura);
            } else {
                valida = Lib.Data.isVuota(dataChiusura);
            } // fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valida;
    }


}
