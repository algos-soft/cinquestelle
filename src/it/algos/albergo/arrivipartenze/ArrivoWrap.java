package it.algos.albergo.arrivipartenze;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.camera.composizione.CompoCamera;
import it.algos.albergo.camera.composizione.CompoCameraModulo;
import it.algos.albergo.listino.Listino;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.base.componente.WrapTextArea;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.query.filtro.Filtro;

import javax.swing.JLabel;
import java.util.Date;

/**
 * Wrapper per la stampa di una prenotazione in arrivo.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 26-giu-2008
 */
public final class ArrivoWrap extends ArrivoPartenzaWrap {


    /**
     * Costruttore completo con parametri.
     *
     * @param wrapIn con prenotazione e periodi interessati
     */
    public ArrivoWrap(final PrenotazionePeriodiWrap wrapIn) {
        /* rimanda al costruttore della superclasse */
        super(wrapIn);
    } // fine del metodo costruttore completo


    /**
     * Aggiunge al pannello la eventuale caparra della prenotazione.
     *
     * @param pan pannello della singola prenotazione
     */
    @Override protected void addCaparra(final Pannello pan) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        double valCaparra = 0.0;
        JLabel label;
        String tagIni = "Caparra: ";
        String tagEnd = " €";
        String caparra;
        String ricevuta;
        String testo = "";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (pan != null);

            if (continua) {
                valCaparra = this.getCaparra();
                continua = (valCaparra > 0.0);
            } // fine del blocco if

            if (continua) {
                caparra = Lib.Testo.formatNumero(valCaparra);
                ricevuta = this.getRicevuta();
                testo = tagIni;
                testo += caparra;
                testo += tagEnd;

                if (Lib.Testo.isValida(ricevuta)) {
                    testo += " (r.f. n° " + ricevuta + ")";
                } // fine del blocco if

            } // fine del blocco if

            if (continua) {
                label = new JLabel(testo);
                label.setFont(getFontStampa());
                pan.add(label);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


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
        boolean arrivato=false;
        String stato = "";
        Modulo modPeriodo = null;
        Modulo modCamera = null;
        Modulo modPrep = null;
        int codCamera;
        String camera = "";
        String riga = "";
        String tagRigaPeriodo = " * ";
        String sep = " - ";
        String testoAdBa = "";
        int numPersone;
        int numBambini;
        int quantiAdulti;
        int quantiBambini;
        int codPensione;
        int codPrep;
        String[] persone = new String[0];
        String pensione = "";
        String prep = "";
        String nota = "";
        Date dataPartenza;
        String partenza = "";
        Filtro filtroPresenze;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (codPeriodo > 0);

            if (continua) {
                modPeriodo = PeriodoModulo.get();
                modCamera = CameraModulo.get();
                modPrep = CompoCameraModulo.get();
                continua = (modPeriodo != null && modCamera != null);
            } // fine del blocco if

            /* camera (obbligatorio) */
            if (continua) {
                codCamera = modPeriodo.query().valoreInt(Periodo.Cam.camera.get(), codPeriodo);
                camera = modCamera.query().valoreStringa(Camera.Cam.camera.get(), codCamera);
                continua = (Lib.Testo.isValida(camera));
            } // fine del blocco if


            /* stato arrivato - non arrivato e partenza prevista */
            if (continua) {
                arrivato = modPeriodo.query().valoreBool(Periodo.Cam.arrivato.get(), codPeriodo);
                if (arrivato) {
                    stato = " (arrivato ";
                } else {
                    stato = " (non arrivato ";
                }// fine del blocco if-else
                dataPartenza = modPeriodo.query().valoreData(Periodo.Cam.partenzaPrevista.get(),codPeriodo);
                if (Lib.Data.isValida(dataPartenza)) {
                    partenza = Lib.Data.getDataBrevissima(dataPartenza);
                    stato += " - partenza prev. " + partenza;
                    stato += ")";
                } // fine del blocco if
            } // fine del blocco if


            /**
             * numero di adulti e di bambini
             */
            if (continua) {

                testoAdBa="";

                quantiAdulti = PeriodoModulo.getNumAdultiArrivo(codPeriodo);
                if (quantiAdulti>0) {
                    testoAdBa+=quantiAdulti;
                    if (quantiAdulti>1) {
                        testoAdBa += " adulti";
                    } else {
                        testoAdBa += " adulto";
                    }// fine del blocco if-else
                }// fine del blocco if

                quantiBambini = PeriodoModulo.getNumBambiniArrivo(codPeriodo);
                if (quantiBambini>0) {

                    if (quantiAdulti>0) {
                        testoAdBa+=" e ";
                    }// fine del blocco if

                    testoAdBa+=quantiBambini;
                    if (quantiBambini>1) {
                        testoAdBa += " bambini";
                    } else {
                        testoAdBa += " bambino";
                    }// fine del blocco if-else
                }// fine del blocco if

            }// fine del blocco if



            /* pensione (facoltativo) */
            if (continua) {
                codPensione = modPeriodo.query().valoreInt(Periodo.Cam.trattamento.get(), codPeriodo);
                pensione = Listino.AmbitoPrezzo.getSigla(codPensione);
            } // fine del blocco if

            /* preparazione (facoltativo) */
            if (continua) {
                codPrep = modPeriodo.query().valoreInt(Periodo.Cam.preparazione.get(), codPeriodo);
                if (codPrep > 0) {
                    prep = modPrep.query().valoreStringa(CompoCamera.Cam.sigla.get(), codPrep);
                } // fine del blocco if
            } // fine del blocco if

            /* nota periodo (facoltativa) */
            if (continua) {
                nota = modPeriodo.query().valoreStringa(Periodo.Cam.note.get(), codPeriodo);
            } // fine del blocco if

            /**
             * nomi delle persone se arrivati
             */
            if (continua) {
                if (arrivato) {
                    filtroPresenze = PeriodoModulo.getFiltroPresenzeArrivo(codPeriodo);
                    persone = this.getPersone(filtroPresenze);
                }// fine del blocco if
            }// fine del blocco if


            /* costruzione della riga */
            if (continua) {
                riga = tagRigaPeriodo;

                if (Lib.Testo.isValida(camera)) {
                    riga += " cam. " + camera;
                } // fine del blocco if

                if (Lib.Testo.isValida(stato)) {
                    riga += stato;
                } // fine del blocco if

                if (Lib.Testo.isValida(testoAdBa)) {
                    riga += sep + testoAdBa;
                } // fine del blocco if

                if (Lib.Testo.isValida(pensione)) {
                    riga += sep + pensione;
                } // fine del blocco if

                if (Lib.Testo.isValida(prep)) {
                    riga += sep + prep;
                } // fine del blocco if

                if (Lib.Testo.isValida(nota)) {
                    riga += sep + nota;
                } // fine del blocco if

                if (persone.length>0) {
                    riga += "\n";
                    riga += "(";
                    riga += Lib.Testo.getStringaVirgolaSpazio(persone);
                    riga += ")";
                } // fine del blocco if


            } // fine del blocco if

            if (continua) {
                area = new WrapTextArea(riga);
                area.setFont(this.getFontStampaBold());
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return area;
    }


    /**
     * Costruisce la stringa persone/adulti/bambini.
     * <p/>
     * Il totale di persone nella camera non può essere vuoto <br>
     * Il numero di bambini deve essere minore o uguale al numero totale di persone <br>
     *
     * @param numPersone - numero totale di persone nella camera
     * @param numBambini - numero di bambini nella camera
     *
     * @return stringa di presentazione
     */
    private String getPersone(int numPersone, int numBambini) {
        /* variabili e costanti locali di lavoro */
        String testoPersone = "";
        boolean continua;
        int numAdulti;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(numPersone));

            /* Il numero di bambini deve essere minore o uguale al numero totale di persone */
            if (continua) {
                continua = (numPersone >= numBambini);
            } // fine del blocco if

            if (continua) {
                if (numBambini > 0) {
                    numAdulti = (numPersone - numBambini);

                    if (numAdulti < 2) {
                        testoPersone = "1 adulto";
                    } else {
                        testoPersone = numAdulti + " adulti";
                    } // fine del blocco if-else

                    if (numBambini < 2) {
                        testoPersone += " e " + "1 bambino";
                    } else {
                        testoPersone += " e " + numBambini + " bambini";
                    } // fine del blocco if-else
                } else {
                    if (numPersone < 2) {
                        testoPersone = "1 persona";
                    } else {
                        testoPersone = numPersone + " persone";
                    } // fine del blocco if-else
                } // fine del blocco if-else

            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testoPersone;
    }


    /**
     * Recupera la caparra della prenotazione.
     *
     * @return importo della caparra (numero)
     */
    private double getCaparra() {
        /* variabili e costanti locali di lavoro */
        double caparra = 0.0;
        boolean continua;
        Modulo modPrenot = null;
        int codPrenot;

        try { // prova ad eseguire il codice
            codPrenot = this.getPrenotazione();
            continua = (codPrenot > 0);

            if (continua) {
                modPrenot = PrenotazioneModulo.get();
                continua = (modPrenot != null);
            } // fine del blocco if

            if (continua) {
                caparra = modPrenot.query().valoreDouble(Prenotazione.Cam.caparra.get(), codPrenot);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return caparra;
    }


    /**
     * Recupera la ricevuta fiscale della caparra.
     *
     * @return ricevuta (testo)
     */
    private String getRicevuta() {
        /* variabili e costanti locali di lavoro */
        String ricevuta = "";
        boolean continua;
        Modulo modPrenot = null;
        int codPrenot;

        try { // prova ad eseguire il codice
            codPrenot = this.getPrenotazione();
            continua = (codPrenot > 0);

            if (continua) {
                modPrenot = PrenotazioneModulo.get();
                continua = (modPrenot != null);
            } // fine del blocco if

            if (continua) {
                ricevuta = modPrenot.query().valoreStringa(Prenotazione.Cam.numRF.get(), codPrenot);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return ricevuta;
    }

}
