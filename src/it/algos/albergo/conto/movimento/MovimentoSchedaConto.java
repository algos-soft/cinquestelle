/**
 * Title:     MovimentoSchedaConto
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      28-set-2007
 */
package it.algos.albergo.conto.movimento;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.scheda.SchedaBase;

/**
 * Scheda specifica dello sconto all'interno del conto.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 27-set-2007 ore 14.39.46
 */
public final class MovimentoSchedaConto extends SchedaBase implements Movimento {


    /**
     * Costruttore completo <br>
     */
    public MovimentoSchedaConto(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }


    /**
     * Crea le pagine della scheda.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaPagine() {
        Pagina pag;
        Pannello pan;

        try { // prova ad eseguire il codice

            /* crea la pagina e aggiunge campi e pannelli */
            pag = this.addPagina("generale");

            pan = PannelloFactory.orizzontale(this);
            pan.add(Cam.data);
            pan.add(Cam.importo.get());
            pan.add(Cam.note.get());
            pag.add(pan);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


}// fine della classe
