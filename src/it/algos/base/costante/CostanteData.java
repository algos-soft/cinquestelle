/**
 * Title:        CostanteData.java
 * Package:      it.algos.base.costante
 * Description:  Costanti per la gestione delle date
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 30 gennaio 2003 alle 11.17
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa interfaccia e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.costante;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa interfaccia e' responsabile di: <br>
 * A - Definire le costanti per la gestione delle date in diversi formati <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  30 gennaio 2003 ore 11.17
 */
public interface CostanteData {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * elenco dei caratteri validi come separatore nelle date
     */
    public static final String SEPARATORI_DATA_VALIDI = "-/";

    /**
     * separatore di default per le date
     */
    public static final String SEPARATORE_DATA_DEFAULT = "-";

    /**
     * costanti per i formati di data
     */
    public static final String FORMATO_DATA_GGMMAAAA = "GGMMAAAA";

    public static final String FORMATO_DATA_AAAAMMGG = "AAAAMMGG";

    /**
     * formato data di default
     */
    public static final String FORMATO_DATA_DEFAULT = FORMATO_DATA_GGMMAAAA;
    //-------------------------------------------------------------------------
}// fine della interfaccia it.algos.base.costante.CostanteData.java
//-----------------------------------------------------------------------------
