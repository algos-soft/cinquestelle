/**
 * Title:     ProiezioneBase
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25-set-2006
 */
package it.algos.base.proiezione;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.componente.bottone.BottoneDialogo;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.ricerca.CampoRicerca;

import java.util.Collection;

/**
 * Gestione di un dialogo di proiezione dati.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 16-ago-2005 ore 14.36.34
 */
public class ProiezioneBase extends DialogoAnnullaConferma implements Proiezione {

    /* campo opzioni di ricerca */
    private Campo campoOpzioni;


    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento
     */
    public ProiezioneBase(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

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
        String titolo;
        String messaggio;
        Campo campoOpzioni;

        try { // prova ad eseguire il codice

            /* costanti letterali */
            titolo = "Proiezione";
            messaggio = "Seleziona il modulo dal quale proiettare i dati";

            this.setTitolo(titolo);
            this.setMessaggio(messaggio);

            /* crea l'albero dei moduli */
//            Albero albero = this.creaAlberoModuli();
//            this.addComponente(albero);

            /* crea e registra il campo opzioni di ricerca */
            this.creaCampoOpzioni();

            /* aggiunge il campo opzioni all'area comandi
             * e alla collezione del dialogo */
            campoOpzioni = this.getCampoOpzioni();
            this.getPannelloComandi().add(campoOpzioni);
            this.addCampoCollezione(campoOpzioni);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Avvia concretamente il dialogo.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     */
    public void avvia() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            /* resetta i campi */
            this.resetCampi();

            /* visualizza il dialogo */
            super.avvia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Presenta il dialogo di ricerca.
     * </p>
     */
    public void presentaDialogo() {
        this.avvia();
    }


    /**
     * Crea e registra il campo opzioni di ricerca.
     * <p/>
     */
    private Campo creaCampoOpzioni() {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try {    // prova ad eseguire il codice
            campo = CampoFactory.radioInterno("opzioniRicerca");
            this.setCampoOpzioni(campo);
            campo.setValoriInterni(Opzioni.getLista());
            campo.setUsaNonSpecificato(false);
            campo.decora().eliminaEtichetta();
            campo.inizializza();
            campo.avvia();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Ritorna l'opzione di ricerca correntemente selezionata.
     * <p/>
     *
     * @return l'opzione selezionata
     *
     * @see it.algos.base.ricerca.Ricerca.Opzioni
     */
    public Opzioni getOpzioneRicerca() {
        /* variabili e costanti locali di lavoro */
        Opzioni opzione = null;
        Campo campo;
        Object oggetto;

        try {    // prova ad eseguire il codice
            campo = this.getCampoOpzioni();
            oggetto = campo.getValoreElenco();
            if (oggetto instanceof Opzioni) {
                opzione = (Opzioni)oggetto;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return opzione;
    }


    /**
     * Recupera il filtro corrispondente a questa Ricerca.
     * <p/>
     *
     * @return il filtro corrispondente alla ricerca
     *         (null se non sono state impostate condizioni di ricerca)
     */
    public Filtro getFiltro() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroCampo = null;
        Collection<CampoRicerca> lista = null;
        String unione;
        int i = 0;

        try {    // prova ad eseguire il codice

//            /* allinea le variabili da GUI a memoria */
//            this.guiMemoria();
//
//            filtro = new Filtro();
//            lista = this.getCampiRicerca().values();
//            for (CampoRicerca cr : lista) {
//                filtroCampo = cr.getFiltro();
//                if (filtroCampo != null) {
//                    if (i == 0) {
//                        filtro.add(filtroCampo);
//                    } else {
//                        unione = cr.getUnione();
//                        filtro.add(unione, filtroCampo);
//                    }// fine del blocco if-else
//                }// fine del blocco if
//                i++;
//            }
//
//            /* se non ha aggiunto nulla, annulla il filtro */
//            if (filtro.getSize() == 0) {
//                filtro = null;
//            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    protected void bottonePremuto(BottoneDialogo bottone) {
    }


    private Campo getCampoOpzioni() {
        return campoOpzioni;
    }


    private void setCampoOpzioni(Campo campoOpzioni) {
        this.campoOpzioni = campoOpzioni;
    }


}
