/**
 * Title:     MenuComposizione
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      20-mar-2004
 */
package it.algos.base.menu.menu;

import it.algos.base.azione.Azione;
import it.algos.base.errore.Errore;
import it.algos.base.pref.Pref;

import java.util.HashMap;

/**
 * Menu strumenti valido per la scheda.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Costruisce il menu archivio strumenti solo per la scheda e le sue
 * righe di menu </li>
 * <li> Decide quali azioni utilizzare tra quelle disponibili </li>
 * <li> Decide in che ordine presentare le righe di menu </li>
 * <li> Decide l'interposizione logica delle righe di separazione </li>
 * <li> Un riferimento a questo oggetto viene reso disponibile per
 * modifiche, aggiunte o cancellazioni delle singole righe del menu </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 20-mar-2004 ore 9.24.15
 */
public final class MenuStrumentiScheda extends MenuBase {

    /**
     * voce del menu come viene visualizzato nella finestra
     */
    private static final String TITOLO = Menu.TITOLO_STRUMENTI;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public MenuStrumentiScheda() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param azioni collezione chiave-valore delle azioni
     */
    public MenuStrumentiScheda(HashMap azioni) {
        /* rimanda al costruttore della superclasse */
        super(azioni, TITOLO);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.<p>
     * Metodo chiamato direttamente dal costruttore <br>
     * <p/>
     * Costruisce il menu, decidendo quali azioni utilizzare tra quelle
     * rese disponibili dal pannello <br>
     * Le singole righe di menu vengono visualizzate nell'ordine di
     * inserimento deciso qui <br>
     * Costruisce anche i separatori logici tra gruppi di righe <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice
            if (Pref.GUI.spostamento.is()) {
//            super.aggiunge(Azione.RECORD_PRIMO);
                super.aggiunge(Azione.RECORD_PRECEDENTE);
                super.aggiunge(Azione.RECORD_SUCCESSIVO);
//            super.aggiunge(Azione.RECORD_ULTIMO);
            } /* fine del blocco if */
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia

}// fine della classe
