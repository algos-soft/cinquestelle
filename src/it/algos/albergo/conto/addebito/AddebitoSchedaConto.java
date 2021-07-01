/**
 * Title:     AddebitoSchedaConto
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      21-apr-2006
 */
package it.algos.albergo.conto.addebito;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;

/**
 * Scheda specifica dell'addebito all'interno del conto.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 9-mar-2006 ore 14.39.46
 */
public class AddebitoSchedaConto extends AddebitoScheda implements Addebito {


    /**
     * Costruttore completo
     * <p/>
     *
     * @param modulo di riferimento
     */
    public AddebitoSchedaConto(Modulo modulo) {
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
     * Ritorna il pannello con Data e Conto.
     * <p/>
     * Sovrascive il metodo della superclasse.
     * Trattandosi della scheda di un conto specifico, non inserisce
     * il campo Conto nel pannello.
     *
     * @return il pannello contenente Data e Conto
     */
    protected Pannello getPanDataConto() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try {    // prova ad eseguire il codice
            pan = PannelloFactory.orizzontale(this);
            pan.add(Addebito.Cam.data.get());
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }

}// fine della classe
