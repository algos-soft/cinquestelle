/**
 * Title:     ExportMethod
 * Copyright: Copyright (c) 2008
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      03-01-2008
 */
package it.algos.base.importExport.methods;

import it.algos.base.errore.Errore;

/**
 * Metodo di esportazione Tab Text.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 03-01-2008
 */
public class TabTextExportMethod extends TextExportMethod {


    /**
     * Costruttore completo con parametri. <br>
     */
    public TabTextExportMethod() {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */

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
        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


} // fine della classe