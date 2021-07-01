/**
 * Title:     HotKey
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25.05.2006
 */
package it.algos.base.hotkey;

import it.algos.base.errore.Errore;

import java.util.ArrayList;

/**
 * Definizione di una singola HotKey di progetto
 * </p>
 * Le HotKeys sono tasti attivi per tutto il progetto.
 * E' possibile premere una HotKey in qualsiasi punto del programma.
 * Il progetto mantiene una collezione di HotKeys.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 25.05.2006 ore 19:26:58
 */
public final class HotKey {

    /**
     * KeyCode del tasto
     */
    private int keyCode;

    /**
     * primo eventuale modificatore
     */
    int mod1;

    /**
     * secondo eventuale modificatore
     */
    int mod2;

    /**
     * terzo eventuale modificatore
     */
    int mod3;

    /**
     * Elenco dei modificatori
     */
    private ArrayList<Integer> modificatori;


    /**
     * Costruttore completo con parametri
     * <p/>
     *
     * @param keycode il codice del tasto
     * @param mod1 il codice del primo modificatore
     * @param mod2 il codice del secondo modificatore
     * @param mod3 il codice del terzo modificatore
     *
     * @see java.awt.event.KeyEvent
     *      usare le costanti che cominciano con VK_
     * @see java.awt.event.InputEvent
     *      per i modificatori usare le costanti con la parola DOWN, es. ALT_DOWN_MASK
     */
    public HotKey(int keycode, int mod1, int mod2, int mod3) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setKeyCode(keycode);
        this.setMod1(mod1);
        this.setMod2(mod2);
        this.setMod3(mod3);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo con parametri
     * <p/>
     *
     * @param keycode il codice del tasto
     * @param mod1 il codice del primo modificatore
     * @param mod2 il codice del secondo modificatore
     *
     * @see java.awt.event.InputEvent
     *      per i modificatori usare le costanti con la parola DOWN, es. ALT_DOWN_MASK
     */
    public HotKey(int keycode, int mod1, int mod2) {
        /* rimanda al costruttore della superclasse */
        this(keycode, mod1, mod2, 0);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo con parametri
     * <p/>
     *
     * @param keycode il codice del tasto
     * @param mod1 il codice del modificatore
     *
     * @see java.awt.event.InputEvent
     *      per i modificatori usare le costanti con la parola DOWN, es. ALT_DOWN_MASK
     */
    public HotKey(int keycode, int mod1) {
        /* rimanda al costruttore della superclasse */
        this(keycode, mod1, 0);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo con parametri
     * <p/>
     *
     * @param keycode il codice del tasto
     */
    public HotKey(int keycode) {
        /* rimanda al costruttore della superclasse */
        this(keycode, 0);
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* crea la lista dei modificatori */
        this.setModificatori(new ArrayList<Integer>());

        /* aggiunge alla lista i modificatori diversi da zero */
        if (this.getMod1() != 0) {
            this.getModificatori().add(this.getMod1());
        }// fine del blocco if
        if (this.getMod2() != 0) {
            this.getModificatori().add(this.getMod2());
        }// fine del blocco if
        if (this.getMod3() != 0) {
            this.getModificatori().add(this.getMod3());
        }// fine del blocco if

    }// fine del metodo inizia


    /**
     * Ritorna la bitmask dei modificatori di questa hotkey.
     * <p/>
     *
     * @return la bitmask dei modificatori
     */
    public int getModifierMask() {
        /* variabili e costanti locali di lavoro */
        int mask = 0;

        try {    // prova ad eseguire il codice

            for (int mod : this.getModificatori()) {
                mask += mod;
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return mask;
    }


    public int getKeyCode() {
        return keyCode;
    }


    private void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }


    private ArrayList<Integer> getModificatori() {
        return modificatori;
    }


    private void setModificatori(ArrayList<Integer> modificatori) {
        this.modificatori = modificatori;
    }


    private int getMod1() {
        return mod1;
    }


    private void setMod1(int mod1) {
        this.mod1 = mod1;
    }


    private int getMod2() {
        return mod2;
    }


    private void setMod2(int mod2) {
        this.mod2 = mod2;
    }


    private int getMod3() {
        return mod3;
    }


    private void setMod3(int mod3) {
        this.mod3 = mod3;
    }

}// fine della classe
