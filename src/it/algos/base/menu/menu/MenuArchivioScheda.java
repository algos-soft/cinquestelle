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

import java.util.HashMap;

/**
 * Menu archivio valido per la scheda.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Costruisce il menu archivio valido solo per la scheda e le sue
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
 * @version 1.0    / 20-mar-2004 ore 9.48.03
 */
public final class MenuArchivioScheda extends MenuBase {

    /**
     * voce del menu come viene visualizzato nella finestra
     */
    private static final String TITOLO = Menu.TITOLO_ARCHIVIO;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public MenuArchivioScheda() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param azioni collezione chiave-valore delle azioni
     */
    public MenuArchivioScheda(HashMap azioni) {
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
        super.aggiunge(Azione.ANNULLA_MODIFICHE);
        super.aggiunge(Azione.REGISTRA_SCHEDA);

        this.addSeparator();

        super.aggiunge(Azione.CHIUDE_SCHEDA);

        super.addSeparator();

        super.aggiunge(Azione.STAMPA);
//        super.aggiunge(Azione.ESCE_PROGRAMMA);

    }// fine del metodo inizia

}// fine della classe
