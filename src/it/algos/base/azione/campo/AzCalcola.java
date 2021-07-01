/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      30-gen-2005
 */
package it.algos.base.azione.campo;

import it.algos.base.azione.adapter.AzAdapterKey;
import it.algos.base.campo.base.Campo;

/**
 * Calcola un campo.
 * <p/>
 * Questa classe azione astratta: <ul>
 * <li> Sincronizza un componente di un campo calcolato che dipende dal componente a
 * cui &egrave; associata questa azione </li>
 * <li> Mantiene degli attributi comuni alle sottoclassi </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 30-gen-2005 ore 18.51.54
 */
public abstract class AzCalcola extends AzAdapterKey {

    /**
     * Riferimento al campo da sincronizzare
     */
    private Campo campoCalcolato = null;


    protected Campo getCampoCalcolato() {
        return campoCalcolato;
    }


    public void setCampoCalcolato(Campo campoCalcolato) {
        this.campoCalcolato = campoCalcolato;
    }

}// fine della classe
