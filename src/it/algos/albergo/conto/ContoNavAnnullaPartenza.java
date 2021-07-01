/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      22-mar-2006
 */
package it.algos.albergo.conto;

import it.algos.base.errore.Errore;
import it.algos.base.navigatore.NavigatoreL;

/**
 * Navigatore dei conti dentro al dialogo di annulla partenza.
 * </p>
 * Creato al volo dal dialogo
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 19-08-2010
 */
public final class ContoNavAnnullaPartenza extends NavigatoreL {

    /**
     * Costruttore completo con parametri.
     */
    public ContoNavAnnullaPartenza() {
        /* rimanda al costruttore della superclasse */
        super(ContoModulo.get());

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
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            this.setNomeVista(Conto.Vis.partenza.get());
            this.setRigheLista(6);
            this.setUsaStatusBarLista(true);
            this.setUsaToolBarLista(false);
            this.setUsaFiltriPopLista(false);
            this.getLista().getTavola().setEnabled(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


}// fine della classe