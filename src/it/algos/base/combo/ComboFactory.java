/**
 * Title:        ComboFactory.java
 * Package:      it.algos.base.combo
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 17 novembre 2003 alle 16.30
 */
package it.algos.base.combo;

import it.algos.base.campo.video.CVCombo;
import it.algos.base.errore.Errore;

/**
 * Questa classe astratta factory e' responsabile di: <br>
 * A - ... <br>
 * B - ... <br>
 *
 * @author Guido Ceresa
 * @author Alberto Colombo
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  17 novembre 2003 ore 16.30
 */
public abstract class ComboFactory extends Object {

    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public ComboFactory() {
        /** rimanda al costruttore della superclasse */
        super();
    } /* fine del metodo costruttore completo */


    /**
     * Crea il combobox semplice per pochi valori (minore di tot)
     * <p/>
     *
     * @param cv campo video combo di riferimento
     *
     * @return l'oggetto Combo creato
     */
    public static Combo creaLista(CVCombo cv) {
        /* variabili e costanti locali di lavoro */
        Combo unComboBox = null;
        ComboLista unComboLista;
        ComboSelectionManager unSelettore;
        ComboRenderer unRenderer;

        try {    // prova ad eseguire il codice

            /* crea un'istanza della classe da ritornare */
            unComboBox = new ComboLista(cv);

            /* recupera l'oggetto combo concreto */
            unComboLista = unComboBox.getComboLista();

            /* crea un'istanza del selettore customizzato */
            unSelettore = new ComboSelectionManager();

            /* installa il selettore dei tasti customizzato */
            unComboLista.getComboBox().setKeySelectionManager(unSelettore);

            /* crea un'istanza del renderer */
            unRenderer = new ComboRenderer();

            /* installa il renderer delle celle customizzato */
            unComboLista.getComboBox().setRenderer(unRenderer);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unComboBox;
    } /* fine del metodo */


}// fine della classe