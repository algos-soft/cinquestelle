/**
 * Title:        CostanteFoglio.java
 * Package:      it.algos.base.costante
 * Description:
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 26 maggio 2003 alle 13.33
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002, 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.costante;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Definire le costanti per i fogli di stampa <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  26 maggio 2003 ore 13.33
 */
public interface CostanteFoglio {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    //----------------------------------------------------------------
    //  Costanti 'chiave' per individuare un oggetto di classe Foglio
    //----------------------------------------------------------------
    public static final int A4 = 1; // foglio A4 verticale

    public static final int A4_ORIZZONTALE = 2; // foglio A4 orizzontale

    public static final int A3 = 3; // foglio A3 verticale

    public static final int A3_ORIZZONTALE = 4; // foglio A3 orizzontale

    public static final int US_LETTER = 5; // foglio US Letter verticale

    public static final int US_LETTER_ORIZZONTALE = 6; // foglio US Letter orizzontale
    //-------------------------------------------------------------------------
}// fine della interfaccia it.algos.base.costante.CostanteFoglio.java
//-----------------------------------------------------------------------------

