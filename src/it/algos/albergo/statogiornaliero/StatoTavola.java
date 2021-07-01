/**
 * Title:     StatoTavola
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      30-giu-2009
 */
package it.algos.albergo.statogiornaliero;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.tavola.Tavola;

/**
 * Tavola del navigatore StatoGiornaliero
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 30-giu-2009 ore 22.05.10
 */
public final class StatoTavola extends Tavola {



    /**
     * Costruttore completo con parametri. <br>
     */
    public StatoTavola() {
        super();
    }// fine del metodo costruttore completo


    @Override
    public int getSelectedRow() {
        int row = super.getSelectedRow();
        return row;
    }


    @Override
    /**
     * Determina se la cella è editabile
     * <p>
     * E' editabile se appartiene a una riga di partenza
     *
     */
    public boolean isCellEditable(int row, int col) {
        /* variabili e costanti locali di lavoro */
        boolean editable=false;

        try { // prova ad eseguire il codice

            Campo campo = this.getLista().getCampo(col);
            if (campo.isModificabileLista()) {
                int chiave = this.getLista().getChiave(row);
                Modulo mod = this.getLista().getPortale().getNavigatore().getModulo();
                int partenza = mod.query().valoreInt(Cam.partenza.get(), chiave);
                int arrivo = mod.query().valoreInt(Cam.arrivo.get(), chiave);
                editable = ((partenza>0) || (arrivo>0));
            }// fine del blocco if

            if ((campo.getNomeInterno()).equals(Cam.codArrPar.get())) {
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return editable;

    }
}// fine della classe
