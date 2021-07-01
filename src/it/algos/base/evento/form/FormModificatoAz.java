/**
 * Title:     FormModificatoAz
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      20-apr-2006
 */
package it.algos.base.evento.form;

import it.algos.base.evento.BaseAzione;

/**
 * Azione di un form che ha modificato il valore di un campo.
 * </p>
 * Questa classe adattatore astratta: <ul>
 * <li> Riceve tutti gli <strong>eventi</strong> che modificano
 * il valore di un campo di un form </li>
 * <li> I metodi della classe sono vuoti </li>
 * <li> Questa classe esiste come utilita' per creare oggetti di tipo listener </li>
 * <li> Implementa tutti i metodi della interfaccia <code>FormStatoLis</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 28-dic-2004 ore 16.13.23
 */
public abstract class FormModificatoAz extends BaseAzione implements FormModificatoLis {

    /**
     * FormModificatoAz, da FormModificatoLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void formModificatoAz(FormModificatoEve unEvento) {
    }

}// fine della classe
