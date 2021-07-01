/**
 * Title:     ClienteAlbergoScheda
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      3-mag-2004
 */
package it.algos.albergo.clientealbergo;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.scheda.SchedaBase;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA CLASSE .
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 3-mag-2004 ore 13.19.37
 */
public final class GruppoScheda extends SchedaBase implements ClienteAlbergo {


    /**
     * Costruttore completo.
     *
     * @param unModulo di riferimento per la scheda
     */
    public GruppoScheda(Modulo unModulo) {
        /* rimanda al costruttore della superclasse */
        super(unModulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice
//            this.setPanCapo(new PanCapo(this));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Crea le pagine della scheda.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    @Override
    protected void creaPagine() {
        try {    // prova ad eseguire il codice
            this.creaPaginaGenerale();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */


    /**
     * Crea la pagina con i dati per la polizia.
     * <p/>
     */
    protected void creaPaginaGenerale() {
        /* variabili e costanti locali di lavoro */
        Pagina pag;

        try {    // prova ad eseguire il codice

            /* crea la pagina Generale */
            pag = super.addPagina("generale");

            pag.add(ClienteAlbergo.Cam.parentela);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

}// fine della classe
