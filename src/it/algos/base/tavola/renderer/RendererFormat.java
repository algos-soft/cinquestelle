/**
 * Title:     RendererFormat
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      23-mag-2006
 */
package it.algos.base.tavola.renderer;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;

import javax.swing.table.TableCellRenderer;

/**
 * Renderer di colonna per campi che usano un Format<br>
 * Converte i valori tramite il Format del campo.
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  23-mag-2006 ore 17.24
 */
public class RendererFormat extends RendererBase implements TableCellRenderer {

    /**
     * Costruttore completo
     * <p>
     * @param campo campo di riferimento
     */
    public RendererFormat(Campo campo) {
        /* rimanda al costruttore della superclasse */
        super(campo);
        /* regolazioni iniziali di riferimenti e variabili */
        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    } /* fine del metodo */


    /**
     * Effettua il rendering di un valore.
     * <p/>
     *
     * @param objIn valore in ingresso
     *
     * @return valore in uscita
     */
    public Object rendValue(Object objIn) {
        /* variabili e costanti locali di lavoro */
        Object objOut = null;
        Campo campo;
        String stringa = "";

        try {    // prova ad eseguire il codice

            if (objIn != null) {
                campo = this.getCampo();
                stringa = campo.format(objIn);
            }// fine del blocco if

            objOut = stringa;

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return objOut;
    }


}

