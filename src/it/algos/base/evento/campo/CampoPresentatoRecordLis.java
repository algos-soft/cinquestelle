/**
 * Copyright(c): 2008
 * Company: Algos s.r.l.
 * Author: alex
 * Date: 28 mag 2008
 */

package it.algos.base.evento.campo;

import it.algos.base.evento.BaseListener;

/**
 * Listener per un evento di tipo: il campo ha presentato un record in scheda.
 * </p>
 * Questa interfaccia: <ul>
 * <li> Riceve gli eventi </li>
 * <li> La classe che è interessata a ricevere un evento, implementa
 * questa interfaccia </li>
 * <li> L'oggetto della classe interessata, si deve registrare presso
 * la classe che genera l'evento </li>
 * <li> Per registrarsi, usa il metodo <code>addListener</code> </li>
 * <li> Quando l'evento viene lanciato, viene invocato il metodo bottoneAz
 * nell'oggetto della classe che implementa questa interfaccia </li>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 20 lug 2006
 */
public interface CampoPresentatoRecordLis extends BaseListener {

    /**
     * bottoneAz, da BottoneLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void presentatoRecordAz(CampoPresentatoRecordEve unEvento);
} // fine della classe