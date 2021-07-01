/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      22-mar-2005
 */
package it.algos.base.azione.componente;

import it.algos.base.azione.Azione;
import it.algos.base.azione.adapter.AzAdapterPopupMenu;
import it.algos.base.errore.Errore;

import javax.swing.event.PopupMenuEvent;

/**
 * Entrata in un campo popup.
 * <p/>
 * Questa classe azione concreta: <ul>
 * <li> Intercetta l'uscita da un popup di un ComboBox </li>
 * <li> Implementa il metodo <code>popupMenuWillBecomeVisible</code> della interfaccia
 * <code>PopupMenuListener</code> </li>
 * <li> Viene usata nei popup dei ComboBox </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 22-mar-2005 ore 16.40.29
 */
public final class AzEntrataPopup extends AzAdapterPopupMenu {

    /**
     * Testo della chiave per recuperare l'azione (obbligatorio)
     */
    private static final String CHIAVE = Azione.ENTRATA_POPUP;

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
    public AzEntrataPopup() {
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
     * popupMenuWillBecomeInvisible, da PopupMenuListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void popupMenuWillBecomeVisible(PopupMenuEvent unEvento) {
        try { // prova ad eseguire il codice
            if (super.getGestore() != null) {
                /* invoca il metodo delegato della classe gestione eventi */
                super.getGestore().entrataPopup(unEvento, this);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

}// fine della classe
