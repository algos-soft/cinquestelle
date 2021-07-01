/**
 * Title:     HotKeyDispatcher
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25.05.2006
 */
package it.algos.base.hotkey;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Dispatcher per le HotKeys
 * </p>
 * Una istanza di questo Dispatcher viene aggiunta al KeyboardFocusManager
 * all'avvio del Progetto.
 * Tutte le pressioni dei tasti arrivano qui prima di essere ulteriormente inoltrate.
 * Se si tratta di HotKeys, tutti i moduli vengono notificati.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 25.05.2006 ore 19:08:10
 */
public final class HotKeyDispatcher implements KeyEventDispatcher {

    /**
     * Chiamato dal KeyboardFocusManager per ogni KeyEvent
     * <p/>
     *
     * @param e il KeyEvent
     *
     * @return false se il KeyboardFocusManager deve continuare a trattare
     *         l'evento, true se lo deve ignorare.
     */
    public boolean dispatchKeyEvent(KeyEvent e) {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        String descrizione;
        ArrayList<Modulo> moduli;
        int chiaveHk = 0;

        /* recupera la descrizione dell'evento */
        descrizione = e.paramString();

        /* risponde solo agli eventi Key Released */
        if (!descrizione.contains("KEY_RELEASED")) {
            continua = false;
        }// fine del blocco if

        /* controlla se l'evento e' una HotKey e ne recupera la chiave */
        if (continua) {
            chiaveHk = this.getChiaveHotKey(e);
            continua = chiaveHk >= 0;
        }// fine del blocco if

        /* notifica tutti i moduli */
        if (continua) {
            moduli = Progetto.getListaModuli();
            for (Modulo modulo : moduli) {
                modulo.hotkey(chiaveHk);
            }
        }// fine del blocco if

        /*
         * valore di ritorno false
         * perche' il KeyboardFocusManager
         * deve continuare a trattare l'evento
         */
        return false;

    }


    /**
     * Ritorna la chiave della eventuale hotkey corrispondente
     * al tasto premuto
     * <p/>
     *
     * @return la chiave della HotKey, -1 se non e' una hotkey
     */
    private int getChiaveHotKey(KeyEvent e) {
        /* variabili e costanti locali di lavoro */
        int chiaveHk = -1;
        int evKeycode;
        int hkKeycode;
        HashMap<Integer, HotKey> hotkeys;
        HotKey hk;
        int evMask;
        int hkMask = 0;

        try {    // prova ad eseguire il codice

            /* recupera il keycode e la maschera dei modificatori dell'evento */
            evKeycode = e.getKeyCode();
            evMask = e.getModifiersEx();

            /* recupera le hotkeys di Progetto */
            hotkeys = Progetto.getHotKeys();

            /* spazzola le hotkeys e ne cerca
             * una uguale (setsso keycode e stessa maschera
             * dei modificatori) */
            for (int chiave : hotkeys.keySet()) {
                hk = hotkeys.get(chiave);
                hkKeycode = hk.getKeyCode();
                if (hkKeycode == evKeycode) {
                    hkMask = hk.getModifierMask();
                    if (hkMask == evMask) {
                        chiaveHk = chiave;
                        break;
                    }// fine del blocco if
                }// fine del blocco if
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return chiaveHk;
    }


}// fine della classe
