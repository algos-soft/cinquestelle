/**
 * Title:        InitContatore.java
 * Package:      it.algos.base.campo.inizializzatore
 * Description:  Inizializzatore da contatore
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       28-dic-2006 ore 12.04
 */

package it.algos.base.campo.inizializzatore;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;

/**
 * Inizializzatore da contatore.
 * Ritorna un valore progressivo basato sul contatore del modulo.
 * Ad ogni rilascio aggiorna il contatore.
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  28-dic-2006 ore 12.04
 */
class InitContatore extends InitBase {

    /**
     * Modulo di riferimento
     */
    private Modulo modulo = null;


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param modulo il modulo di riferimento
     */
    public InitContatore(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setModulo(modulo);

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
     */
    public Object getValore() {
        /* variabili e costanti locali di lavoro */
        int num = 0;

        try { // prova ad eseguire il codice

            num = this.getModulo().releaseID();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return num;
    }


    /**
     * Controlla se questo inizializzatore supporta l'utilizzo delle transazioni.
     * <p/>
     * Significativo solo per gli inizializzatori del campo chiave
     *
     * @return true se supporta le transazioni
     */
    public boolean isSupportaTransazioni() {
        return true;
    }


    /**
     * Controlla se questo inizializzatore necessita del modulo Contatori.
     * <p/>
     *
     * @return true se necessita del modulo Contatori
     */
    public boolean isNecessitaContatori() {
        return true;
    }


    private Modulo getModulo() {
        return modulo;
    }


    private void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }
}
