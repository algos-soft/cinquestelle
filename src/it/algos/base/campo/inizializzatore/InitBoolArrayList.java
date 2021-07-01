/**
 * Title:     InitBoolArray
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      18-mag-2006
 */
package it.algos.base.campo.inizializzatore;

import it.algos.base.errore.Errore;

import java.util.ArrayList;

/**
 * Inizializzatore con valore fisso lista di booleani.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 28-gen-2005 ore 10.09.49
 */
final class InitBoolArrayList extends InitBase {


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param valore il valore fisso da assegnare
     */
    public InitBoolArrayList(ArrayList<Boolean> valore) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.setValoreFisso(valore);
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    /**
     * Ritorna il valore di inizializzazione per il campo.
     * <p/>
     * Il valore ritornato e' a livello di Memoria
     * Il valore ritornato e' una ArrayList di booleani
     */
    public Object getValore() {
//        /* variabili e costanti locali di lavoro */
//        ArrayList<Boolean> lista=null;
//        boolean[] booleani;
//        Object oggetto;
//
//        try { // prova ad eseguire il codice
//            lista = new ArrayList<Boolean>();
//            oggetto = this.getValoreFisso();
//            if (oggetto instanceof boolean[]) {
//                booleani = (boolean[])oggetto;
//                for(boolean b : booleani){
//                   lista.add(b);
//                }
//            }// fine del blocco if
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return lista;
        return this.getValoreFisso();
    } /* fine del metodo */


}// fine della classe
