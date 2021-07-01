package it.algos.albergo.arrivipartenze;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.camera.composizione.CompoCamera;
import it.algos.albergo.camera.composizione.CompoCameraModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.base.componente.WrapTextArea;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.filtro.Filtro;

/**
 * Wrapper per la stampa di una prenotazione in cambio.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 09-set-2008
 */
public final class CambioWrap extends ArrivoPartenzaWrap {

    /**
     * Costruttore completo con parametri.
     *
     * @param wrapIn con prenotazione e periodi interessati
     */
    public CambioWrap(final PrenotazionePeriodiWrap wrapIn) {
        /* rimanda al costruttore della superclasse */
        super(wrapIn);
    } // fine del metodo costruttore completo


    /**
     * Aggiunge al pannello il singolo periodo.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param codPeriodoOrigine interessato (con cambio in uscita)
     *
     * @return area di testo flessibile
     */
    @Override protected WrapTextArea addPeriodo(final int codPeriodoOrigine) {
        /* variabili e costanti locali di lavoro */
        WrapTextArea area = null;
        boolean continua;
        boolean cambiato = false;
        String stato;
        Modulo modPeriodo = null;
        Modulo modCamera = null;
        int codCameraOrigine;
        int codCameraDest;
        int codPeriodoDest =0;
        boolean entrato=false;
        String strCameraOrigine;
        String strCameraDest;
        String riga = "";
        String sep = " - ";
        String[] persone;
        int quantiAdulti;
        int quantiBambini;
        Filtro filtroPresenze;
        int codPrep;
        String strCamere="";
        String strStato="";
        String strPrep="";
        String strNumPersone="";
        String strNomiPersone="";
        String strNota="";

        try { // prova ad eseguire il codice
            
            /* controllo di congruità */
            continua = (codPeriodoOrigine > 0);

            if (continua) {
                modPeriodo = PeriodoModulo.get();
                modCamera = CameraModulo.get();
                continua = (modPeriodo != null && modCamera != null);
            } // fine del blocco if

            /* recupera i dati fondamentali */
            if (continua) {
                codPeriodoDest = modPeriodo.query().valoreInt(Periodo.Cam.linkDestinazione.get(), codPeriodoOrigine);
                entrato = modPeriodo.query().valoreBool(Periodo.Cam.arrivato.get(), codPeriodoOrigine);
                cambiato = modPeriodo.query().valoreBool(Periodo.Cam.partito.get(), codPeriodoOrigine);
            }// fine del blocco if

            /* camera di origine e di destinazione dai 2 periodi */
            if (continua) {
                codCameraOrigine = modPeriodo.query().valoreInt(Periodo.Cam.camera.get(), codPeriodoOrigine);
                codCameraDest = modPeriodo.query().valoreInt(Periodo.Cam.camera.get(), codPeriodoDest);
                strCameraOrigine = modCamera.query().valoreStringa(Camera.Cam.camera.get(), codCameraOrigine);
                strCameraDest = modCamera.query().valoreStringa(Camera.Cam.camera.get(), codCameraDest);
                strCamere = "da " + strCameraOrigine+" a "+strCameraDest;
            } // fine del blocco if

            /* stato cambiato o no, dal periodo origine */
            if (continua) {
                if (cambiato) {
                    stato = "(cambiato)";
                } else {
                    stato = "(in cambio)";
                }// fine del blocco if-else
                strStato = stato;
            } // fine del blocco if

            /**
             * numero di adulti e di bambini
             */
            if (continua) {

                riga = "";

                quantiAdulti = PeriodoModulo.getNumPersoneCambio(codPeriodoOrigine, Periodo.TipoPersona.adulto);
                if (quantiAdulti>0) {
                    riga+=quantiAdulti;
                    if (quantiAdulti>1) {
                        riga += " adulti";
                    } else {
                        riga += " adulto";
                    }// fine del blocco if-else
                }// fine del blocco if

                quantiBambini = PeriodoModulo.getNumPersoneCambio(codPeriodoOrigine, Periodo.TipoPersona.bambino);
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

                strNumPersone=riga;

            }// fine del blocco if

            /* preparazione (facoltativo) */
            if (continua) {
                codPrep = modPeriodo.query().valoreInt(Periodo.Cam.preparazione.get(), codPeriodoDest);
                if (codPrep > 0) {
                    Modulo modPrep = CompoCameraModulo.get();
                    strPrep = modPrep.query().valoreStringa(CompoCamera.Cam.sigla.get(), codPrep);
                } // fine del blocco if
            } // fine del blocco if

            /* nota periodo (facoltativa) */
            if (continua) {
                strNota = modPeriodo.query().valoreStringa(Periodo.Cam.note.get(), codPeriodoDest);
            } // fine del blocco if


            /**
             * se già entrato, nomi delle persone in uscita o già uscite
             */
            if (continua) {
                if (entrato) {
                    riga="";
                    filtroPresenze = PeriodoModulo.getFiltroPresenzeCambio(codPeriodoOrigine);
                    persone = this.getPersone(filtroPresenze);
                    riga += "(";
                    riga += Lib.Testo.getStringaVirgolaSpazio(persone);
                    riga += ")";
                    strNomiPersone=riga;
                }// fine del blocco if
            }// fine del blocco if


            /* costruzione della riga */
            if (continua) {

                riga="";
                
                if (Lib.Testo.isValida(strCamere)) {
                    riga += strCamere;
                } // fine del blocco if

                if (Lib.Testo.isValida(strStato)) {
                    riga += sep + strStato;
                } // fine del blocco if

                if (Lib.Testo.isValida(strNumPersone)) {
                    riga += sep + strNumPersone;
                } // fine del blocco if

                if (Lib.Testo.isValida(strPrep)) {
                    riga += sep + strPrep;
                } // fine del blocco if

                if (Lib.Testo.isValida(strNota)) {
                    riga += sep + strNota;
                } // fine del blocco if

                if (Lib.Testo.isValida(strNomiPersone)) {
                    riga += "\n";                    
                    riga += strNomiPersone;
                } // fine del blocco if


            } // fine del blocco if
            

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



}