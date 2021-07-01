/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: alex
 * Date: 17 mar 2006
 */

package it.algos.base.evento.campo;

import it.algos.base.evento.BaseListener;

/**
 * Listener di un campo che ha modificato il suo valore di memoria.
 * </p>
 * Questa interfaccia: <ul>
 * <li> Riceve gli eventi </li>
 * <li> La classe che è interessata a ricevere un evento, implementa
 * questa interfaccia </li>
 * <li> L'oggetto della classe interessata, si deve registrare presso
 * la classe che genera l'evento </li>
 * <li> Per registrarsi, usa il metodo <code>addListener</code> </li>
 * <li> Quando l'evento viene lanciato, viene invocato il metodo campoMemoriaAz
 * nell'oggetto della classe che implementa questa interfaccia </li>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 17 mar 2006
 */
public interface CampoMemoriaLis extends BaseListener {

    /**
     * campoMemoriaAz, da CampoMemoriaLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void campoMemoriaAz(CampoMemoriaEve unEvento);

} // fine della classe
