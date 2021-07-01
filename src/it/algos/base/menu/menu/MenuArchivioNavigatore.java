/**
 * Title:     MenuArchivioNavigatore
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-lug-2004
 */
package it.algos.base.menu.menu;

import it.algos.base.azione.Azione;
import it.algos.base.errore.Errore;
import it.algos.base.progetto.Progetto;

import javax.swing.*;
import java.util.HashMap;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA CLASSE .
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 21-lug-2004 ore 10.10.59
 */
public final class MenuArchivioNavigatore extends MenuBase {

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
    public MenuArchivioNavigatore() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param azioni collezione chiave-valore delle azioni
     */
    public MenuArchivioNavigatore(HashMap azioni) {
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
        /* variabili e costanti locali di lavoro */
        JMenu menuRegolazioni;
        JMenu menuDati;
        JMenu menuSelezione;

        try { // prova ad eseguire il codice
            super.aggiunge(Azione.NUOVO_RECORD);
            super.aggiunge(Azione.MODIFICA_RECORD);
            super.aggiunge(Azione.ELIMINA_RECORD);

            super.addSeparator();

            /* se aggiunge almeno una delle due righe di menu, mette il separatore */
            if ((super.aggiunge(Azione.CARICA_LISTA)) | (super.aggiunge(Azione.REGISTRA_LISTA))) {
                super.addSeparator();
            } /* fine del blocco if */

            super.aggiunge(Azione.PREFERENZE);

            /* blocco menuRegolazioni con submenu */
            menuRegolazioni = new JMenu("Regolazioni");
            if (Progetto.ModFissi.utenti.isUso()) {
                super.aggiunge(Azione.UTENTI, menuRegolazioni);
            }// fine del blocco if
            if (Progetto.ModFissi.contatori.isUso()) {
                super.aggiunge(Azione.CONTATORI, menuRegolazioni);
            }// fine del blocco if
            if (Progetto.ModFissi.semafori.isUso()) {
                super.aggiunge(Azione.SEMAFORI, menuRegolazioni);
            }// fine del blocco if
            /* aggiunge solo se c'è qualcosa */
            if (menuRegolazioni.getComponentCount() > 0) {
                this.add(menuRegolazioni);
            }// fine del blocco if

            /* blocco menuDati con submenu */
            menuDati = new JMenu("Dati");

            if (Progetto.isProgrammatore()) {
                super.aggiunge(Azione.REGISTRA_DATI_DEFAULT, menuDati);
            }// fine del blocco if
            super.aggiunge(Azione.IMPORT, menuDati);
            super.aggiunge(Azione.EXPORT, menuDati);
            if (Progetto.ModFissi.selezione.isUso()) {
                super.aggiunge(Azione.SALVA_SELEZIONE, menuDati);
                super.aggiunge(Azione.CARICA_SELEZIONE, menuDati);
            }// fine del blocco if
            super.aggiunge(Azione.BACKUP, menuDati);
            super.aggiunge(Azione.RESTORE, menuDati);
            this.add(menuDati);

            /* blocco menuSelezione con submenu */
            menuSelezione = new JMenu("Selezione");
            super.aggiunge(Azione.CARICA_TUTTI, menuSelezione);
            super.aggiunge(Azione.SOLO_SELEZIONATI, menuSelezione);
            super.aggiunge(Azione.NASCONDE_SELEZIONATI, menuSelezione);
            this.add(menuSelezione);

            super.aggiunge(Azione.STAMPA);
            super.aggiunge(Azione.CHIUDE_NAVIGATORE);
            super.aggiunge(Azione.CHIUDE_PROGRAMMA);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo inizia

}// fine della classe
