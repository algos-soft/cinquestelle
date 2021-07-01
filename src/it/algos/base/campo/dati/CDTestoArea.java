/**
 * Title:     CDTestoArea
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      21-ago-2007
 */
package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;

/**
 * Classe concreta per implementare un oggetto da CDBase;
 * <p/>
 * Questa classe concreta e' responsabile di: <br>
 * A - Implementare i metodi astratti della superclasse per regolare le
 * conversioni tra i vari tipi di dati <br>
 */
public class CDTestoArea extends CDTesto {

    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDTestoArea(Campo unCampoParente) {
        super(unCampoParente);
    } /* fine del metodo costruttore completo */


    /**
     * Ritorna true se il campo e' di tipo testoArea.
     *
     * @return true se è campo testoArea
     */
    public boolean isTestoArea() {
        return true;
    }


}// fine della classe
