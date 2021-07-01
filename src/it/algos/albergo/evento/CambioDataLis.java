/**
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      17-mag-2007
 */
package it.algos.albergo.evento;

import it.algos.base.evento.BaseListener;

/**
 * Listener per un evento di tipo cambio data.
 * </p>
 * Questa interfaccia: <ul>
 * <li> Riceve gli eventi </li>
 * <li> La classe che e' interessata a ricevere un evento, implementa
 * questa interfaccia </li>
 * <li> L'oggetto della classe interessata, si deve registrare presso
 * la classe che genera l'evento </li>
 * <li> Per registrarsi, usa il metodo <code>addListener</code> </li>
 * <li> Quando l'evento viene lanciato, viene invocato il metodo emergenzaAz
 * nell'oggetto della classe che implementa questa interfaccia </li>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 15 lug 2009
 */
public interface CambioDataLis extends BaseListener {

    /**
     * Esegue l'azione <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public abstract void cambioDataAz(CambioDataEve unEvento);
} // fine della classe