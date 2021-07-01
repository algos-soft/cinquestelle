/**
 * Title:     CampoListener
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      25-feb-2004
 */
package it.algos.base.evento.campo;

import it.algos.base.evento.BaseListener;

/**
 * Listener per un campo che ha perso il fuoco dopo modifica.
 * </p>
 * Questa interfaccia: <ul>
 * <li> Riceve gli eventi </li>
 * <li> La classe che è interessata a ricevere un evento, implementa
 * questa interfaccia </li>
 * <li> L'oggetto della classe interessata, si deve registrare presso
 * la classe CampoBase che genera l'evento </li>
 * <li> Per registrarsi, usa il metodo <code>addListener</code> </li>
 * <li> Quando l'evento viene lanciato unCampo.fireStatoModificato(),
 * viene invocato il metodo prendeFuocoAz o perdeFuocoAz nell'oggetto
 * della classe che implementa questa interfaccia </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-feb-2004 ore 13.55.03
 * @see java.awt.event.ActionEvent
 */
public interface CampoModificatoLis extends BaseListener {

    /**
     * campoModificatoAz, da CampoModificatoLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void campoModificatoAz(CampoModificatoEve unEvento);

}// fine della interfaccia
