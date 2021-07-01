/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      7-gen-2005
 */
package it.algos.base.azione.mouse;

import it.algos.base.azione.Azione;
import it.algos.base.azione.adapter.AzAdapterMouse;
import it.algos.base.errore.Errore;

import java.awt.event.MouseEvent;

/**
 * Mouse cliccato due volte.
 * <p/>
 * Questa classe azione concreta: <ul>
 * <li> Intercetta il generico doppio click del mouse </li>
 * <li> Implementa il metodo <code>mouseReleased</code> della interfaccia
 * <code>MouseListener</code> </li>
 * <li> Viene usata nei menu, nelle toolbar e nei bottoni di comando </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 7-gen-2005 ore 8.57.35
 */
public final class AzMouseDoppioClick extends AzAdapterMouse {

    /**
     * Testo della chiave per recuperare l'azione (obbligatorio)
     */
    private static final String CHIAVE = Azione.MOUSE_DOPPIO_CLICK;

    /**
     * Carattere del tasto acceleratore (facoltativo)
     */
    private static final char ACCELERATORE = ' ';

    /**
     * codice carattere di default per il tasto mnemonico (facoltativo)
     */
    private static final int MNEMONICO = 0;

    /**
     * Lettera di default per il tasto comando (facoltativo)
     */
    private static final String COMANDO = null;

    /**
     * Costante per l'azione attiva (booleana)
     */
    private static final boolean ATTIVA = true;

    /**
     * Costante per l'azione abilitata alla partenza (booleana)
     */
    private static final boolean ABILITATA = true;


    /**
     * Costruttore completo senza parametri.
     */
    public AzMouseDoppioClick() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* regola le variabili*/
        super.setChiave(CHIAVE);
        super.setCarattereAcceleratore(ACCELERATORE);
        super.setCarattereMnemonico(MNEMONICO);
        super.setCarattereComando(COMANDO);
        super.setAttiva(ATTIVA);
        super.setAbilitataPartenza(ABILITATA);
    }// fine del metodo inizia


    /**
     * mouseReleased, da MouseListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void mouseReleased(MouseEvent unEvento) {
        /* controlla che sia stato cliccato due volte */
        if (unEvento.getClickCount() == 2) {
            /* invoca il metodo delegato della classe gestione eventi */
            super.getGestore().mouseDoppioClick(unEvento, this);
        } /* fine del blocco if */
    }

}// fine della classe
