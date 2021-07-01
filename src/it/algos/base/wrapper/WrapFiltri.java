/**
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      30-mar-2007
 */

package it.algos.base.wrapper;

import it.algos.base.errore.Errore;
import it.algos.base.query.filtro.Filtro;

import java.util.ArrayList;

/**
 * Wrapper per una lista di filtri.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Costruisce un tipo di dati </li>
 * <li> Raggruppa questi dati in un wrapper </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 30-mar-2007 ore 19.15.24
 */
public final class WrapFiltri extends ArrayList<Filtro> {

    /**
     * flag per usare il valore di filtro nullo (tutti i records)
     */
    private boolean usaTutti;

    /**
     * flag per posizionare il filtro nullo come ultimo valore del popup (altrimenti è il primo)
     */
    private boolean isTuttiFinale;

    /**
     * testo del valore tutti (di default maschile)
     */
    private String testo;

    /**
     * posizione iniziale selezionata nel popup (default -1)
     */
    private int pos;

    /**
     * voce del popup
     */
    private String titolo;


    /**
     * Costruttore base senza parametri.
     * <p/>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public WrapFiltri() {
        /* rimanda al costruttore di questa classe */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore base


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setUsaTutti(true);
        this.setTuttiFinale(true);
        this.setTesto("Tutti");
        this.setPos(-1);
    }


    public boolean add(Filtro filtro, String testo) {
        /* variabili e costanti locali di lavoro */
        boolean aggiunto = false;

        try { // prova ad eseguire il codice
            if (filtro != null) {
                filtro.setNome(testo);
            }// fine del blocco if

            aggiunto = super.add(filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return aggiunto;
    }


//    public boolean add(Filtro filtro, String testo) {
//        /* variabili e costanti locali di lavoro */
//        boolean aggiunto = false;
//
//        try { // prova ad eseguire il codice
//
//            WrapFiltro w = new WrapFiltro(filtro, testo);
//            aggiunto = super.add(w);
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return aggiunto;
//    }



//    public boolean addMethod(Method metodo, String testo) {
//        /* variabili e costanti locali di lavoro */
//        boolean aggiunto = false;
//
//        try { // prova ad eseguire il codice
////            if (metodo != null) {
////                filtro.setNome(testo);
////            }// fine del blocco if
////
////            aggiunto = super.add(filtro);
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return aggiunto;
//    }



    public boolean isUsaTutti() {
        return usaTutti;
    }


    public void setUsaTutti(boolean usaTutti) {
        this.usaTutti = usaTutti;
    }


    public boolean isTuttiFinale() {
        return isTuttiFinale;
    }


    public void setTuttiFinale(boolean tuttiFinale) {
        this.isTuttiFinale = tuttiFinale;
    }


    public String getTesto() {
        return testo;
    }


    public void setTesto(String testo) {
        this.testo = testo;
    }


    public String getTitolo() {
        return titolo;
    }


    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }


    public int getPos() {
        return pos;
    }


    /**
     * Parte da 1.
     */
    public void setPos(int pos) {
        this.pos = pos;
    }


    /**
     * todo per ora non utilizzata
     */
    private final class  WrapFiltro{

        private Object reference;
        private String titolo;

        /**
         * Costruttore completo con parametri. <br>
         *
         */
        WrapFiltro(Object reference, String titolo) {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili di istanza coi parametri */
            this.reference = reference;
            this.titolo = titolo;


            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
//                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }// fine del metodo costruttore completo



        ;
    } // fine della classe 'interna'

}// fine della classe
