/**
 * Package:      it.algos.base.campo.dati
 * Description:
 * Copyright:    Copyright (c) 2005
 * Company:      Algos s.r.l.
 * @author Ceresa, Valbonesi
 * @version 1.0  /
 * Creato:       il 28 geb 2005 alle 19.22
 */

package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.selezione.QuerySelezione;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Campo dati per un campo Navigatore.
 * <p/>
 */
public final class CDNavMemoria extends CDNavigatore {


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDNavMemoria() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDNavMemoria(Campo unCampoParente) {
        /** rimanda al costruttore della superclasse */
        super(unCampoParente);

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    } /* fine del metodo inizia */


    /**
     * Controlla se il contenuto del campo e' valido.
     * <p/>
     * Un Campo Navigatore è valido se la sua scheda corrente è valida
     * (o se non ha una scheda aperta)
     *
     * @return true se valido, false se non valido.
     */
    public boolean isValido() {
        /* variabili e costanti locali di lavoro */
        boolean valido = true;
        Navigatore nav;
        int[] codici;
        LinkedHashMap<String, Campo> mappa;
        ArrayList<Campo> campiObb;
        Modulo modulo;
        Dati dati;
        Query query;
        Filtro filtro;
        Object valore;
        boolean continua;

        try { // prova ad eseguire il codice

            nav = this.getNavigatore();
            modulo = nav.getModulo();
            codici = nav.getLista().getChiaviVisualizzate();
            mappa = modulo.getModello().getCampiModello();

            /* traverso tutta la collezione */
            campiObb = new ArrayList<Campo>();
            for (Campo unCampo : mappa.values()) {
                if (unCampo.isObbligatorio()) {
                    campiObb.add(unCampo);
                }// fine del blocco if
            } // fine del ciclo for-each
            continua = (campiObb.size() > 0);


            if (continua) {

                /* traverso tutta la collezione */
                for (int cod : codici) {
                    query = new QuerySelezione(modulo);
                    /* traverso tutta la collezione */
                    for (Campo campo : campiObb) {
                        query.addCampo(campo);
                    } // fine del ciclo for-each
                    filtro = FiltroFactory.codice(modulo, cod);
                    query.setFiltro(filtro);
                    dati = nav.query().querySelezione(query);

                    for (Campo campo : campiObb) {
                        valore = dati.getValueAt(0, campo);
                        campo.setValore(valore);
                        if (!campo.isValido()) {
                            valido = false;
                            break;
                        }// fine del blocco if
                    } // fine del ciclo for-each

                    if (!valido) {
                        break;
                    }// fine del blocco if

                } // fine del ciclo for-each

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


}// fine della classe