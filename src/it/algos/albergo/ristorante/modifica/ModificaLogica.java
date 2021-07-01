/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      2-mar-2005
 */
package it.algos.albergo.ristorante.modifica;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.logica.LogicaBase;

/**
 * Repository di logiche specifiche del modulo RMP.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 2-mar-2005 ore 23.33.25
 */
public class ModificaLogica extends LogicaBase {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param modulo il modulo proprietario di questa logica.
     */
    public ModificaLogica(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
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
     * Controlla se una data modifica interessa la cucina.
     * <p/>
     *
     * @param codice il codice della modifica
     *
     * @return true se interessa la cucina
     */
    public boolean isInteressaCucina(int codice) {
        /* variabili e costanti locali di lavoro */
        boolean interessa = false;

        try {    // prova ad eseguire il codice
            interessa = this.getModulo().query().valoreBool(Modifica.CAMPO_INTERESSE_CUCINA,
                    codice);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return interessa;
    }

}// fine della classe
