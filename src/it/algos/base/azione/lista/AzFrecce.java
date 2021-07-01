/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      30-dic-2004
 */
package it.algos.base.azione.lista;

import it.algos.base.azione.Azione;
import it.algos.base.azione.adapter.AzAdapterKey;
import it.algos.base.errore.Errore;

import java.awt.event.KeyEvent;

/**
 * Si sposta avanti ed indietro di una riga nella lista.
 * <p/>
 * Questa classe azione concreta: <ul>
 * <li> Intercetta l'inserimento di un carattere freccia </li>
 * <li> Controlla la pressione dei tasti freccia s&ugrave; e freccia gi&ugrave;
 * in una lista </li>
 * <li> Implementa il metodo <code>keyReleased</code> della interfaccia
 * <code>KeyListener</code> </li>
 * <li> Viene usata nelle liste </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 30-dic-2004 ore 9.13.41
 */
public final class AzFrecce extends AzAdapterKey {

    /**
     * codifica dei caratteri da intercettare
     */
    private static final int alto = KeyEvent.VK_UP;

    private static final int basso = KeyEvent.VK_DOWN;

    /**
     * Testo della chiave per recuperare l'azione (obbligatorio)
     */
    private static final String CHIAVE = Azione.FRECCE;

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
    public AzFrecce() {
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
        super.addCarattere(alto);
        super.addCarattere(basso);

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
        int codice = 0;

        try { // prova ad eseguire il codice
            /* recupera il codice del tasto premuto */
            codice = unEvento.getKeyCode();

            /* filtra solo i caratteri previsti */
            if (super.isCarattere(codice)) {

                switch (codice) {
                    case alto:
                        /* invoca il metodo delegato della classe gestione eventi */
                        super.getGestore().frecciaAlto(unEvento, this);
                        break;
                    case basso:
                        /* invoca il metodo delegato della classe gestione eventi */
                        super.getGestore().frecciaBasso(unEvento, this);
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch

            } /* fine del blocco if */

//            new KeyEvent(
//                    (Component)unEvento.getSource(),
//                    unEvento.getID(),
//                    unEvento.getWhen()+25,
//                    unEvento.getModifiers(),
//                    unEvento.getKeyCode(),
//                    unEvento.getKeyChar());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

}// fine della classe
