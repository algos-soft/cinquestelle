/**
 * Title:     PortaleEliminaRecLis
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      24-nov-2006
 */
package it.algos.base.evento.portale;

import it.algos.base.evento.BaseListener;

/**
 * Listener per un evento di tipo PortaleEliminaRecEve.
 * </p>
 * Questa interfaccia: <ul>
 * <li> Riceve gli eventi </li>
 * <li> La classe che è interessata a ricevere un evento, implementa
 * questa interfaccia </li>
 * <li> L'oggetto della classe interessata, si deve registrare presso
 * la classe che genera l'evento </li>
 * <li> Per registrarsi, usa il metodo <code>addListener</code> </li>
 * <li> Quando l'evento viene lanciato, viene invocato il metodo portaleEliminaRecAz
 * nell'oggetto della classe che implementa questa interfaccia </li>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 2 feb 2006
 */
public interface PortaleEliminaRecLis extends BaseListener {

    /**
     * PortaleEliminaRecAz, da PortaleEliminaRecLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void portaleEliminaRecAz(PortaleEliminaRecEve unEvento);
} // fine della classe
