/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      27-set-2005
 */
package it.algos.base.validatore;

import it.algos.base.errore.Errore;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validatore astratto con Regular Expression .
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 27-set-2005 ore 10.30.18
 */

public abstract class ValidatoreRegexp extends ValidatoreBase {


    private String espressione;


    public ValidatoreRegexp() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }


    /**
     * Implementazione della logica di validazione.
     * <p/>
     * Implementare la logica di validazione in questo metodo.
     * Ritorna true se il dato e' valido, false se non e' valido.
     * All'interno della validazione e'anche possibile modificare
     * il messaggio in caso di dato non valido usando setMessaggio().
     *
     * @param testo da validare
     * @param comp componente di riferimento
     *
     * @return true se valido, false se non valido.
     */
    protected boolean validate(String testo, JComponent comp) {
        /* variabili e costanti locali di lavoro */
        boolean valido = true;
        String espressione;
        Matcher m;
        String car;

        try { // prova ad eseguire il codice
            /* recupera l'espressione */
            espressione = this.getEspressione();
            espressione = "[" + espressione + "]";

            for (int k = 0; k < testo.length(); k++) {
                car = testo.substring(k, k + 1);
                m = Pattern.compile(espressione).matcher(car);
                valido = m.find();
                if (!valido) {
                    this.setMessaggio("Testo non valido");
                    break;
                }// fine del blocco if
            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    private String getEspressione() {
        return espressione;
    }


    /**
     * Regular expression da soddisfare.
     *
     * @param espressione da soddisfare
     */
    public void setEspressione(String espressione) {
        this.espressione = espressione;
    }

}// fine della classe
