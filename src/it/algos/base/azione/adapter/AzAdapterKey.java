/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      28-dic-2004
 */
package it.algos.base.azione.adapter;

import it.algos.base.azione.AzioneBase;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Eventi inserimento carattereri.
 * </p>
 * Questa classe adattatore astratta: <ul>
 * <li> An abstract adapter class for receiving <strong>key events</strong> </li>
 * <li> The methods in this class are empty </li>
 * <li> This class exists as convenience for creating listener objects </li>
 * <li> Implementa tutti i metodi della interfaccia <code>KeyListener</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 28-dic-2004 ore 16.14.57
 */
public abstract class AzAdapterKey extends AzioneBase implements KeyListener {

    /**
     * caratteri (uno o piu) filtrati dall'azione
     * arrayList di interi
     */
    private ArrayList<Integer> caratteri = null;


    /**
     * Costruttore completo senza parametri.<br>
     */
    protected AzAdapterKey() {
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
        /* creazione della variabile */
        this.caratteri = new ArrayList<Integer>();
    }


    /**
     * keyPressed, da KeyListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void keyPressed(KeyEvent unEvento) {
        int a = 87;
    }


    /**
     * keyReleased, da KeyListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void keyReleased(KeyEvent unEvento) {
        int a = 87;
    }


    /**
     * keyTyped, da KeyListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void keyTyped(KeyEvent unEvento) {
        int a = 87;
    }


    /**
     * Restituisce l'array di caratteri filtrati da questa azione.
     * <p/>
     * Metodo usato dalla classe <code>Tavola</code> <br>
     *
     * @return ArrayList di Integer
     */
    public ArrayList<Integer> getCaratteri() {
        return caratteri;
    }


    /**
     * Controlla se il carattere &egrave; compreso tra quelli della collezione.
     * <p/>
     * Metodo invocato dalla sottoclasse <br>
     *
     * @param codiceCarattere codifica del carattere
     *
     * @return vero se &egrave; nella collezione
     */
    protected boolean isCarattere(int codiceCarattere) {
        /* variabili e costanti locali di lavoro */
        boolean compreso = false;

        try { // prova ad eseguire il codice
            compreso = Lib.Mat.isEsiste(this.caratteri, codiceCarattere);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return compreso;
    }


    /**
     * Aggiunge un carattere alla collezione.
     * <p/>
     * Metodo invocato dalla sottoclasse <br>
     *
     * @param codiceCarattere codifica del carattere
     */
    protected void addCarattere(int codiceCarattere) {
        try { // prova ad eseguire il codice
            caratteri.add(codiceCarattere);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

}// fine della classe
