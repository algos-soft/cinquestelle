/**
 * Title:        CostanteLook.java
 * Package:      it.algos.base.costante
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 29 settembre 2002 alle 22.09
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
 * Interfaccia per le costanti di stringa del look-and-feel<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  29 settembre 2002 ore 22.09
 */
public interface CostanteLook {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * Look and feel mac
     */
    public static final String LOOK_MAC = "com.apple.mrj.swing.MacLookAndFeel";

    /**
     * Look and feel java
     */
    public static final String LOOK_JAVA = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";

    /**
     * Look and feel motif
     */
    public static final String LOOK_MOTIF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";

    /**
     * Look and feel metal
     */
    public static final String LOOK_METAL = "javax.swing.plaf.metal.MetalLookAndFeel";

    /**
     * Look and feel window
     */
    public static final String LOOK_WIN = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

    /**
     * Look and feel personalizzato (gac)
     */
    public static final String LOOK_GAC = LOOK_METAL;

    /**
     * Look and feel personalizzato (alex)
     */
    public static final String LOOK_ALEX = LOOK_METAL;

    //-------------------------------------------------------------------------
}// fine della interfaccia it.algos.base.costante.CostanteLook.java
//-----------------------------------------------------------------------------

