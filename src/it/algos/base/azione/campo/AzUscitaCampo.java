/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      29-dic-2004
 */
package it.algos.base.azione.campo;

import it.algos.base.azione.Azione;
import it.algos.base.azione.adapter.AzAdapterFocus;
import it.algos.base.errore.Errore;
import it.algos.base.gestore.Gestore;

import java.awt.event.FocusEvent;

/**
 * Uscita da un campo.
 * <p/>
 * Questa classe azione concreta: <ul>
 * <li> Intercetta l'uscita (focusLost) in un campo testo </li>
 * <li> Implementa il metodo <code>focusLost</code> della interfaccia
 * <code>FocusListener</code> </li>
 * <li> Viene usata nei campi edit della Scheda e del Dialogo </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 29-dic-2004 ore 9.28.10
 */
public final class AzUscitaCampo extends AzAdapterFocus {

    /**
     * Testo della chiave per recuperare l'azione (obbligatorio)
     */
    private static final String CHIAVE = Azione.USCITA_CAMPO;

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
    public AzUscitaCampo() {
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
     * focusLost, da FocusListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void focusLost(FocusEvent unEvento) {
        /* variabili e costanti locali di lavoro */
        Gestore gestore;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe gestione eventi */
            gestore = super.getGestore();
            if (gestore != null) {
                gestore.uscitaCampo(unEvento, this);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

}// fine della classe
