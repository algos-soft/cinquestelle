/**
 * Title:     ClienteScheda
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      18-mag-2006
 */
package it.algos.gestione.anagrafica.cliente;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.gestione.anagrafica.AnagraficaScheda;

/**
 * Scheda specifica di anagrafica Cliente.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-apr-2004 ore 10.31.58
 */
public class ClienteScheda extends AnagraficaScheda implements Cliente {


    /**
     * Costruttore completo senza parametri.
     */
    public ClienteScheda(Modulo modulo) {
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
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


}// fine della classe
