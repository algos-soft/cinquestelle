/**
 * Title:        CostanteBase.java
 * Package:      it.algos.base.costante
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 29 settembre 2002 alle 21.57
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
 * Interfaccia che raggruppa tutte le interfaccie<br>
 * <p/>
 * Serve per implementare una solo interfaccia in ogni classe che necessiti
 * le costanti di una o piu interfacce.<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  29 settembre 2002 ore 21.57
 */
public interface CostanteBase extends CostanteAzione,
        CostanteCarattere,
        CostanteColore,
        CostanteComando,
        CostanteData,
        CostanteDB,
        CostanteFoglio,
        CostanteFont,
        CostanteGUI,
        CostanteLook,
        CostanteModello,
        CostanteModulo,
        CostanteTesto,
        CostanteGestore { // inizio del blocco (vuoto) della interfaccia
}// fine della interfaccia it.algos.base.costante.CostanteBase.java
//-----------------------------------------------------------------------------

