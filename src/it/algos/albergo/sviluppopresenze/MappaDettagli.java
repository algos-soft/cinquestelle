/**
 * Title:     MappaDettagli
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      13-feb-2009
 */
package it.algos.albergo.sviluppopresenze;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import java.util.LinkedHashMap;

/**
 * Mappa delle righe della tabella Dettagli
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 13-feb-2009 ore 9.22.52
 */
final class MappaDettagli extends LinkedHashMap<Integer, RigaDettaglio> {

    /**
     * Costruttore base.
     * <p/>
     */
    public MappaDettagli() {
        super();
    }// fine del metodo costruttore base


    /**
     * Rimuove falla mappa tutti gli elementi che hanno
     * tutti i valori vuoti
     * <p/>
     */
    public void rimuoviElementiVuoti() {
        /* variabili e costanti locali di lavoro */
        int key;
        RigaDettaglio value;

        try { // prova ad eseguire il codice

            /* crea una nuova mappa vuota */
            MappaDettagli mappaNew = new MappaDettagli();

            /**
             * spazzola questa mappa e copia nella nuova
             * solo gli elementi che non sono vuoti
             */
            for (java.util.Map.Entry<Integer, RigaDettaglio> elem : this.entrySet()) {
                key = elem.getKey();
                value = elem.getValue();
                if (!value.isValoriVuoti()) {
                    mappaNew.put(key, value);
                }// fine del blocco if
            }


            /**
             * Svuota questa mappa e la riempie con gli
             * elementi della mappa nuova
             */
            this.clear();
            for (java.util.Map.Entry<Integer, RigaDettaglio> elem : mappaNew.entrySet()) {
                key = elem.getKey();
                value = elem.getValue();
                this.put(key, value);
            }

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Rimuove falla mappa l'elemento "Altro" (chiave = 0)
     * solo se ha tutti i valori vuoti
     * <p/>
     */
    public void rimuoviAltroSeVuoto() {
        /* variabili e costanti locali di lavoro */
        RigaDettaglio value;

        try { // prova ad eseguire il codice
            value = this.get(0);
            if (value.isValoriVuoti()) {
                this.remove(0);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Calcola le percentuali di ogni riga e le consolida nella riga stessa.
     * <p/>
     */
    public void calcPercentuali () {
        /* variabili e costanti locali di lavoro */
        int totPresenze=0;
        double totValore=0;

        try {    // prova ad eseguire il codice

            /* spazzola la mappa e calcola i totali Presenze e Valore */
            for(RigaDettaglio riga : this.values()){
                totPresenze += riga.getPresTotali();
                totValore += riga.getValore();
            }

            /* spazzola nuovamente e attribuisce i valori percentuali ad ogni riga */
            double presRiga;
            double percPres;
            double valRiga;
            double percVal;
            for(RigaDettaglio riga : this.values()){

                presRiga = riga.getPresTotali();
                percPres=0;
                if (totPresenze!=0) {
                    percPres = presRiga/totPresenze;
                    percPres = Lib.Mat.arrotonda(percPres, 3);
                }// fine del blocco if
                riga.setPercPresenze(percPres);

                valRiga = riga.getValore();
                percVal=0;
                if (totValore!=0) {
                    percVal = valRiga/totValore;
                    percVal = Lib.Mat.arrotonda(percVal, 3);
                }// fine del blocco if
                riga.setPercValore(percVal);
                
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }





}// fine della classe
