/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      16-feb-2005
 */
package it.algos.albergo.stampeobbligatorie.testastampe;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreNN;

/**
 * Navigatore testa-righe inserito nei pannelli delle Stampa Obbligatorie.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 16-feb-2005 ore 18.05
 */
public class NavigatoreDoppio extends NavigatoreNN {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param moduloMaster modulo di riferimento
     * @param chiaveNavMaster nome chiave del navigatore Master (nel modulo Master)
     * @param moduloSlave modulo per il navigatore slave
     * @param chiaveNavSlave nome chiave del navigatore Slave (nel modulo Slave)
     */
    public NavigatoreDoppio(Modulo moduloMaster,
                        String chiaveNavMaster,
                        Modulo moduloSlave,
                        String chiaveNavSlave) {

        /* rimanda al costruttore della superclasse */
        super(moduloMaster, chiaveNavMaster, moduloSlave, chiaveNavSlave);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


}// fine della classe
