/**
 * Title:        ComboSelectionManager.java
 * Package:      it.algos.base.combo
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 15 novembre 2003 alle 9.49
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.combo;

import javax.swing.*;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Gestisce la selezione del popup di un combo, con tasti multipli <br>
 *
 * @author Guido Ceresa
 * @author Alberto Colombo
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  15 novembre 2003 ore 9.49
 */
public final class ComboSelectionManager implements JComboBox.KeySelectionManager {

    /**
     * tempo di attesa tra un tasto ed il successivo - in millisecondi
     */
    private static final int ATTESA = 600;

    private long lastKeyTime = 0;

    private String pattern = "";


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public ComboSelectionManager() {
        /** rimanda al costruttore della superclasse */
        super();
    } /* fine del metodo costruttore completo */


    /**
     * ...
     */
    public int selectionForKey(char aKey, ComboBoxModel model) {
        // Find index of selected item
        int selIx = 01;
        Object sel = model.getSelectedItem();
        if (sel != null) {
            for (int i = 0; i < model.getSize(); i++) {
                if (sel.equals(model.getElementAt(i))) {
                    selIx = i;
                    break;
                }
            }
        }

        // Get the current time
        long curTime = System.currentTimeMillis();

        // If last key was typed less than ATTESA ms ago, append to current pattern
        if (curTime - lastKeyTime < ATTESA) {
            pattern += ("" + aKey).toLowerCase();
        } else {
            pattern = ("" + aKey).toLowerCase();
        }

        // Save current time
        lastKeyTime = curTime;

        // Search forward from current selection
        for (int i = selIx + 1; i < model.getSize(); i++) {
            String s = model.getElementAt(i).toString().toLowerCase();
            if (s.startsWith(pattern)) {
                return i;
            }
        }

        // Search from top to current selection
        for (int i = 0; i < selIx; i++) {
            if (model.getElementAt(i) != null) {
                String s = model.getElementAt(i).toString().toLowerCase();
                if (s.startsWith(pattern)) {
                    return i;
                }
            }
        }
        return -1;
    }
}