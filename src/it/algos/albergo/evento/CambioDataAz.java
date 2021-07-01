/**
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25-lug-2009
 */
package it.algos.albergo.evento;

import it.algos.base.evento.BaseAzione;

/**
 * Azione specifica cambio data programma.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 24 mag 2006
 */
public abstract class CambioDataAz extends BaseAzione implements CambioDataLis {


    /**
     * Esegue l'azione
     * <p>
     *
     * @param unEvento evento che causa l'azione da eseguire
     */
    public abstract void cambioDataAz(CambioDataEve unEvento);

} // fine della classe