/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 16 ottobre 2003 alle 12.39
 */
package it.algos.base.campo.tipodati;

/**
 * Questa interfaccia e' responsabile di: <br>
 * A - Presentare all'esterno le classi che descrivono
 * i vari tipi dati
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  16 ottobre 2003 ore 16.39
 */
public interface TipoDati {

    /**
     * Ritorna la classe Java che rappresenta questo tipo
     *
     * @return la classe che rappresenta questo tipo
     */
    public Class getClasse();


    /**
     * Recupera il valore vuoto per questo tipo
     *
     * @return il valore vuoto per questo tipo
     */
    public Object getValoreVuoto();

}// fine della interfaccia