/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      29-dic-2004
 */
package it.algos.base.azione.campo;

import it.algos.base.azione.Azione;
import it.algos.base.azione.adapter.AzAdapterKey;
import it.algos.base.errore.Errore;
import it.algos.base.form.Form;

import java.awt.event.KeyEvent;

/**
 * Carattere Enter.
 * <p/>
 * Questa classe azione concreta: <ul>
 * <li> Intercetta l'inserimento del carattere Enter in un campo testo </li>
 * <li> Implementa il metodo <code>keyReleased</code> della interfaccia
 * <code>KeyListener</code> </li>
 * <li> Viene usata nei campi edit della Scheda e del Dialogo </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 29-dic-2004 ore 9.26.39
 */
public final class AzEnter extends AzAdapterKey {

    /**
     * Testo della chiave per recuperare l'azione (obbligatorio)
     */
    private static final String CHIAVE = Azione.ENTER;

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
    public AzEnter() {
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

        /* registra i caratteri da filtrare */
        super.addCarattere(Azione.Carattere.enter.getCodice());

        /* regola le variabili*/
        super.setChiave(CHIAVE);
        super.setCarattereAcceleratore(ACCELERATORE);
        super.setCarattereMnemonico(MNEMONICO);
        super.setCarattereComando(COMANDO);
        super.setAttiva(ATTIVA);
        super.setAbilitataPartenza(ABILITATA);
    }// fine del metodo inizia


    /**
     * keyReleased, da KeyListener <br>
     * </p>
     * Esegue l'azione <br>
     * Filtra solo i caratteri previsti <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void keyReleased(KeyEvent unEvento) {
        /* variabili e costanti locali di lavoro */
        int codice;
        Form form = null;
        boolean continua;

        try { // prova ad eseguire il codice

            /* recupera il codice del tasto premuto
             * filtra solo i caratteri previsti */
            codice = unEvento.getKeyCode();
            continua = (super.isCarattere(codice));

            /* considera solo il return del tastierino numerico */
            /* ed enter (tastierino numerico) */
            if (continua) {
                continua = (unEvento.getKeyLocation() == KeyEvent.KEY_LOCATION_NUMPAD);
            }// fine del blocco if

            /* recupera il form del portale */
            if (continua) {
                form = this.getPortale().getForm();
                continua = (form != null);
            }// fine del blocco if

            /* controlla che il form sia registrabile */
            if (continua) {
                continua = (form.isRegistrabile());
            }// fine del blocco if

            /* invoca il metodo delegato della classe gestione eventi */
            if (continua) {
                super.getGestore().confermaForm(unEvento, this);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

}// fine della classe
