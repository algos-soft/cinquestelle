/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 20 lug 2006
 */

package it.algos.base.evento.portale;

import it.algos.base.evento.BaseListener;

/**
 * Listener per un evento di tipo PortaleListaPop.
 * </p>
 * Questa interfaccia: <ul>
 * <li> Riceve gli eventi </li>
 * <li> La classe che è interessata a ricevere un evento, implementa
 * questa interfaccia </li>
 * <li> L'oggetto della classe interessata, si deve registrare presso
 * la classe che genera l'evento </li>
 * <li> Per registrarsi, usa il metodo <code>addListener</code> </li>
 * <li> Quando l'evento viene lanciato, viene invocato il metodo portaleListaPopAz
 * nell'oggetto della classe che implementa questa interfaccia </li>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 20 lug 2006
 */
public interface PortaleListaPopLis extends BaseListener {

    /**
     * portaleListaPopAz, da PortaleListaPopLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void portaleListaPopAz(PortaleListaPopEve unEvento);
} // fine della classe
