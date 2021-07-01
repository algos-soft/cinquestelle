/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 16 ottobre 2003 alle 12.39
 */
package it.algos.base.campo.tipodati.tipoarchivio;

import it.algos.base.campo.tipodati.TipoDati;

/**
 * Questa interfaccia e' responsabile di: <br>
 * A - Presentare all'esterno le classi che descrivono
 * i vari tipi dati Archivio
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  16 ottobre 2003 ore 16.39
 */
public interface TipoArchivio extends TipoDati {

    public abstract int getChiaveTipoDatiDb();

}// fine della interfaccia