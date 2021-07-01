/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-apr-2005
 */
package it.algos.base.dialogo;

import it.algos.base.errore.Errore;
import it.algos.base.pannello.Pannello;

/**
 * Dialogo di avviso per mostrare messaggi.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Sovrascrive vuoto un metodo della superclasse per non usare il
 * pannello centrale componenti</li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 21-apr-2005 ore 11.05.13
 */
public class DialogoAvviso extends DialogoBase {

    /**
     * Costruttore con parametri.
     * <p/>
     */
    public DialogoAvviso() {
        /* invoca il costruttore coi parametri */
        this("");
    } /* fine del metodo costruttore */


    /**
     * Costruttore completo senza parametri.
     * <p/>
     *
     * @param titolo della finestra del dialogo
     */
    public DialogoAvviso(String titolo) {
        /* rimanda al costruttore della superclasse */
        super(titolo);

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
    }// fine del metodo inizia


    /**
     * Crea il pannello centrale della finestra.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Deve essere invocato dopo aver creato il pannello contenitore <br>
     * Crea il pannello e lo aggiunge al pannello contenitore <br>
     * Sovrascritto nella classe specifica (dove non esegue niente)
     * per annullare la funzionalità <br>
     */
    protected Pannello creaPannelloContenuti() {
        return null;
    }

}// fine della classe
