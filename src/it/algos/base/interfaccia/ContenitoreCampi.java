/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      6-ago-2005
 */
package it.algos.base.interfaccia;

import it.algos.base.campo.base.Campo;
import it.algos.base.modulo.Modulo;

/**
 * Interfaccia Contenitore Campi.
 * </p>
 * Implementata dalle classi che contengono delle collezioni di campi.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 6-ago-2005 ore 14.19.34
 */
public interface ContenitoreCampi {

    /**
     * Recupera un campo dal contenitore.
     * <p/>
     *
     * @param chiave chiave per recuperare il campo dalla collezione
     *
     * @return il campo
     */
    public abstract Campo getCampo(String chiave);


    /**
     * Controlla l'esistenza di un campo.
     * <br>
     *
     * @param chiave chiave per recuperare il campo dalla collezione
     *
     * @return vero se il campo esiste nella collezione campi
     */
    public abstract boolean isEsisteCampo(String chiave);


    /**
     * Recupera il modulo di riferimento di questo contenitore di campi.
     * <br>
     *
     * @return il modulo di riferimento
     */
    public abstract Modulo getModulo();


}// fine della interfaccia
