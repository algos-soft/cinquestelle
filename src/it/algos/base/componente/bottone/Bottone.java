/**
 * Title:        Bottone.java
 * Package:      it.algos.base.componente.bottone
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 31 ottobre 2003 alle 16.27
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa interfaccia e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.componente.bottone;

import it.algos.base.azione.Azione;

import java.awt.event.ActionListener;

//-----------------------------------------------------------------------------
// Classe principale
//-----------------------------------------------------------------------------


/**
 * Questa interfaccia e' responsabile di: <br>
 * A - Gestire un bottone <br>
 *
 * @author Guido Andrea Ceresa
 * @author Alberto Colombo
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  31 ottobre 2003 ore 16.27
 */
public interface Bottone {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------

    //-------------------------------------------------------------------------
    // Metodi pubblici degli oggetti (usati in altre classi)      (instance m.)
    //-------------------------------------------------------------------------

    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------


    public abstract void setAction(Azione unAzione);


    public abstract void setLarghezza(int larghezza);


    public abstract void setSize(int larghezza, int altezza);


    public abstract void setLocation(int ascissa, int ordinata);


    /**
     * abilitazione del bottone.
     * <p/>
     *
     * @param enable true per abilitare false per disabilitare
     */
    public abstract void setEnabled(boolean enable);


    /**
     * Adds an <code>ActionListener</code> to the button.
     *
     * @param l the <code>ActionListener</code> to be added
     */
    public void addActionListener(ActionListener l);


    public abstract BottoneBase getBottone();

    //-------------------------------------------------------------------------
}// fine della interfaccia it.algos.base.componente.bottone.Bottone.java

//-----------------------------------------------------------------------------

