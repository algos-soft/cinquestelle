/**
 * Title:        Combo.java
 * Package:      it.algos.base.combo
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 17 novembre 2003 alle 16.08
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa interfaccia e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.combo;

import it.algos.base.portale.Portale;

import javax.swing.*;
import javax.swing.event.PopupMenuListener;
import java.awt.event.*;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa interfaccia e' responsabile di: <br>
 * A - Gestire una sottoclasse di JComboBox <br>
 * B - Implementa una selezione con i tasti (piu' di uno) premuti <br>
 * C - Gli elementi vengono raffigurati con diversi livello di indentazione <br>
 * D - Gli elementi possono essere non selezionabili (con un colore diverso) <br>
 * E - Ogni elemento puo' avere una icona associata (a sinistra) <br>
 * F - Ogni elemento puo' avere un'azione associata (Es. Nuovo valore...) <br>
 * G - Mantiene un elemento JSeparator() associabile ad una riga <br>
 *
 * @author Guido Ceresa
 * @author Alberto Colombo
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  17 novembre 2003 ore 16.08
 */
public interface Combo {

    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Invoca il metodo sovrascritto della superclasse <br>
     */
    public abstract void inizializza();


    /**
     * Avvio dell'oggetto.
     * <p/>
     */
    public abstract void avvia();


    /**
     * Aggiunge i Listeners al combo.
     * <p/>
     *
     * @param portale contenente le azioni
     */
    public abstract void aggiungeListener(Portale portale);


    public abstract void addItemListener(ItemListener unListener);


    public abstract void addPopupMenuListener(PopupMenuListener unListener);


    public abstract void addActionListener(ActionListener unListener);


    public abstract void addMouseListener(MouseListener unListener);


    public abstract void addKeyListener(KeyListener unListener);


    public abstract void removeItemListener(ItemListener unListener);


    public abstract void removePopupMenuListener(PopupMenuListener unListener);


    public abstract void removeActionListener(ActionListener unListener);


    public abstract void setValori(Object[] valori);


    /**
     * Aggiorna la GUI con il valore dal video.
     * <p/>
     */
    public abstract void aggiornaGUI();


    public abstract void setSelectedItem(Object unOggetto);


    public abstract void setSelectedIndex(int unIndice);


    /**
     * Ritorna il componente video completo
     * (JComboBox o pannello con testo e bottoni)
     * <p/>
     *
     * @return il componente completo del combo
     */
    public abstract JComponent getComponente();


    /**
     * Ritorna il componente video di selezione (JComboBox o JTextField)
     * <p/>
     *
     * @return il componente selezionabile o editabile del combo
     */
    public abstract JComponent getComponenteSelettore();


    public abstract ComboLista getComboLista();


    public abstract Object getSelectedItem();


    public abstract int getSelectedIndex();


    public abstract int getItemCount();


    /**
     * Abilita/disabilita il combo.
     * <p/>
     * Se disabilitato sbiadisce tutti gli elementi
     * e impedisce di modificare il valore
     *
     * @param flag true per abilitare, false per disabilitare
     */
    public abstract void setAbilitato(boolean flag);


    /**
     * Regola la larghezza del componente selezionabile
     * o editabile del combo
     * <p/>
     * In un ComboLista, assegna la larghezza al JComboBox <br>
     * In un ComboTavola, assegna la larghezza al componente editabile <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param larghezza da assegnare
     */
    public abstract void setLarghezza(int larghezza);


    /**
     * Regola l'altezza del componente selezionabile
     * o editabile del combo
     * <p/>
     * In un ComboLista, assegna l'altezza al JComboBox <br>
     * In un ComboTavola, assegna l'altezza al componente editabile <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param altezza da assegnare
     */
    public abstract void setAltezza(int altezza);


    /**
     * Invocato quando il combo ha
     * acquisito il fuoco in maniera permanente.
     * <p/>
     *
     * @param e l'evento fuoco
     */
    public abstract void focusGainedComponente(FocusEvent e);


    /**
     * Invocato quando il combo ha
     * perso il fuoco in maniera permanente.
     * <p/>
     *
     * @param e l'evento fuoco
     */
    public abstract void focusLost(FocusEvent e);


    /**
     * Richiede il fuoco sul componente editabile del combo.
     * <p/>
     */
    public abstract void requestFocus();


    /**
     * Controlla l'uso della funzione Nuovo Record.
     * <p/>
     *
     * @param flag per usare la funzione Nuovo Record
     */
    public abstract void setUsaNuovo(boolean flag);


    /**
     * Controlla l'uso della funzione Modifica Record.
     * <p/>
     *
     * @param flag per usare la funzione Modifica Record
     */
    public abstract void setUsaModifica(boolean flag);


    /**
     * Ritorna una copia profonda dell'oggetto (deep copy) col casting
     * Per fare una copia completa di questo oggetto occorre:
     * Prima copiare l'oggetto nel suo insieme, richiamando il metodo
     * sovrascritto che copia e regola tutte le variabili dell'oggetto con
     * gli stessi valori delle variabili originarie
     * Secondo copiare tutte le variabili che sono puntatori ad altri
     * oggetti, per evitare che nella copia ci sia il puntatore all'oggetto
     * originale (in genere tutti gli oggetti che vengono creati nella
     * classe col comando new)
     * Terzo in ogni sottoclasse occorre fare le copie dei puntatori
     * esistenti nelle sottoclassi stesse
     */
    public abstract Combo clona();

    //-------------------------------------------------------------------------
}// fine della interfaccia it.algos.base.combo.Combo.java
//-----------------------------------------------------------------------------

