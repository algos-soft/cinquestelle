/**
 * Title:     NavStorico
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-mar-2009
 */
package it.algos.albergo.storico;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreL;

/**
 * Navigatore di base per le liste dello Storico
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-mar-2009 ore 17.11.43
 */
public class NavStorico extends NavigatoreL {

    /**
     * Storico proprietario di questo Navigatore
     * il riferimento viene assegnato dallo Storico
     * quando si impossessa del Navigatore.
     * Viene poi utilizzato dal Navigatore
     * per inviare messaggi allo Storico.
     */
    private Storico storico;

    /**
     * Costruttore completo con parametri.
     * <p>
     *
     * @param modulo di riferimento
     * @param nomeChiave del navigatore
     * @param nomeVista da utilizzare
     */
    public NavStorico(Modulo modulo, String nomeChiave, String nomeVista) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia(nomeChiave, nomeVista);
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo





    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     * @param nomeChiave del navigatore
     * @param nomeVista da utilizzare
     * @throws Exception unaEccezione
     */
    private void inizia(String nomeChiave, String nomeVista) throws Exception {

        try { // prova ad eseguire il codice

            this.setNomeChiave(nomeChiave);
            this.setNomeVista(nomeVista);
            this.setRigheLista(3);
            this.setUsaToolBarLista(false);
            this.setUsaStatusBarLista(false);
            this.setUsaFiltriPopLista(false);
            this.getLista().setUsaTotali(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    @Override
    protected boolean modificaRecord() {
        /* variabili e costanti locali di lavoro */
        Storico storico;
        Modulo mod;
        int codice;

        try { // prova ad eseguire il codice
            storico = this.getStorico();
            if (storico!=null) {
                mod = this.getModulo();
                codice = this.getLista().getChiaveSelezionata();
                storico.doppioClic(mod, codice);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return super.modificaRecord();
    }


    private Storico getStorico() {
        return storico;
    }


    public void setStorico(Storico storico) {
        this.storico = storico;
    }

}// fine della classe
