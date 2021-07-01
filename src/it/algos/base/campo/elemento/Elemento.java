/**
 * Title:        Elemento.java
 * Package:      it.algos.base.campo.elemento
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 14 novembre 2003 alle 10.14
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa interfaccia e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.campo.elemento;

import javax.swing.*;
import java.awt.*;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa interfaccia e' responsabile di: <br>
 * A - Costruire un tipo di dati da usare nelle liste
 * (<CODE>JList</CODE> e <CODE>JComboBox</CODE>) <br>
 *
 * @author Guido Ceresa
 * @author Alberto Colombo
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  14 novembre 2003 ore 10.14
 */
public interface Elemento {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi pubblici degli oggetti (usati in altre classi)      (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    /**
     * componente che viene restituito per essere disegnato
     */
    public abstract Component getComponente();


    /**
     * valore del codice associato a questo elemento nella matriceDoppia
     */
    public abstract int getCodice();


    public abstract Icon getIcona();

    //-------------------------------------------------------------------------
}// fine della interfaccia it.algos.base.campo.elemento.Elemento.java
//-----------------------------------------------------------------------------

