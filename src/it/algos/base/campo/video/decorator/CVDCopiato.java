/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 15 agosto 2003 alle 21.04
 */
package it.algos.base.campo.video.decorator;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.dati.CampoDati;
import it.algos.base.campo.tipodati.tipomemoria.TipoMemoria;
import it.algos.base.campo.tipodati.tipovideo.TipoVideo;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.evento.campo.CampoMemoriaAz;
import it.algos.base.evento.campo.CampoMemoriaEve;

import java.util.ArrayList;

/**
 * Decoratore copiato della classe CampoVideo.
 * <p/>
 * Questa classe concreta: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Decorator</b> </li>
 * <li> Disegna una legenda (descrizione) nel pannello campo del CampoVideo,
 * dopo il pannello componenti </li>
 * <li> Il calcolato può essere posizionato sotto o a destra </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  15 agosto 2003 ore 21.04
 */
public class CVDCopiato extends CVDecoratoreBase {


    /**
     * riferimento ai campi da riempire
     */
    private ArrayList<String> listaCampi;


    /**
     * Costruttore completo con parametri.
     *
     * @param campoVideoDecorato oggetto da decorare
     */
    public CVDCopiato(CampoVideo campoVideoDecorato) {
        /* rimanda al costruttore della superclasse */
        super(campoVideoDecorato);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    } /* fine del metodo inizia */


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alle impostazioni
     * correnti delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.inizializza();

            /* regola l'azione */
            this.getCampoParente().addListener(new AzioneCopia());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Esegue l'azione generata dall'evento.
     * <p/>
     * Metodo invocato dalla classe interna <br>
     *
     * @param campo da regolare
     */
    protected void esegui(Campo campo) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Campo unCampo;
        Object ogg = null;
        ArrayList<String> lista = null;

        try { // prova ad eseguire il codice
            continua = (campo != null);

            if (continua) {
                lista = this.getListaCampi();
                continua = (lista != null && lista.size() > 0);
            }// fine del blocco if

            if (continua) {
                ogg = campo.getValore();
                continua = (ogg != null);
            }// fine del blocco if

            if (continua) {
                /* traverso tutta la collezione */
                for (String nomeCampo : lista) {
                    unCampo = this.getCampoForm(nomeCampo);
                    if (unCampo != null) {
                        if (isTipoUguale(campo, unCampo)) {
//                            if (Lib.Testo.isVuota(unCampo.getValore())) {
                            unCampo.setValore(ogg);
//                            }// fine del blocco if
                        }// fine del blocco if
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    protected boolean isTipoUguale(Campo campoUno, Campo campoDue) {
        /* variabili e costanti locali di lavoro */
        boolean uguale = false;
        boolean continua;
        CampoDati datiUno = null;
        CampoDati datiDue = null;
        TipoMemoria memUno = null;
        TipoMemoria memDue = null;
        TipoVideo vidUno = null;
        TipoVideo vidDue = null;

        try { // prova ad eseguire il codice
            continua = (campoUno != null && campoDue != null);

            if (continua) {
                datiUno = campoUno.getCampoDati();
                continua = (datiUno != null);
            }// fine del blocco if

            if (continua) {
                datiDue = campoDue.getCampoDati();
                continua = (datiDue != null);
            }// fine del blocco if

            if (continua) {
                memUno = datiUno.getTipoMemoria();
                continua = (memUno != null);
            }// fine del blocco if

            if (continua) {
                memDue = datiDue.getTipoMemoria();
                continua = (memDue != null);
            }// fine del blocco if

            if (continua) {
                vidUno = datiUno.getTipoVideo();
                continua = (vidUno != null);
            }// fine del blocco if

            if (continua) {
                vidDue = datiDue.getTipoVideo();
                continua = (vidDue != null);
            }// fine del blocco if

            if (continua) {
                uguale = ((memUno == memDue) && (vidUno == vidDue));
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return uguale;
    }


    public ArrayList<String> getListaCampi() {
        return listaCampi;
    }


    public void setListaCampi(ArrayList<String> listaCampi) {
        this.listaCampi = listaCampi;
    }


    /**
     * Inner class per gestire l'azione.
     */
    private class AzioneCopia extends CampoMemoriaAz {

        /**
         * campoAz, da CampoLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void campoMemoriaAz(CampoMemoriaEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                esegui(unEvento.getCampo());
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


}// fine della classe