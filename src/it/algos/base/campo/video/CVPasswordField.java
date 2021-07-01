/**
 * Title:     CVPasswordField
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      22-giu-2007
 */
package it.algos.base.campo.video;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.util.Arrays;

/**
 * Componente video speciallizzato per l'editing delle password.
 * </p>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  3 luglio 2003 ore 23.53
 */
public class CVPasswordField extends CVTesto {

    /**
     * Costruttore completo con parametri.
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CVPasswordField(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

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

        try { // prova ad eseguire il codice

            /* creazione dei componenti */
            this.creaComponentiInterni();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea i componenti interni al pannelloComponenti.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     * Sovrascrive il metodo della superclasse <br>
     * E' responsabilita' di questo metodo:
     * - creare i componenti GUI interni al PannelloComponenti<br>
     * - eventualmente assegnare un layout al PannelloComponenti<br>
     * - aggiungere i componenti al pannelloComponenti<br>
     */
    public void creaComponentiInterni() {
        /* variabili e costanti locali di lavoro */
        JPasswordField comp;

        try { // prova ad eseguire il codice

            /* crea il componente principale di tipo JTextField */
            comp = new JPasswordField();

            /* registra il riferimento al componente principale */
            this.setComponente(comp);

            /* registra il riferimento al componente di testo accessibile
             * (in questo caso e' lo stesso)*/
            this.setComponenteTesto(comp);

            /* regola colore e font del componente */
            TestoAlgos.setField(comp);

            super.creaComponentiInterni();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Recupera dalla GUI il valore video.
     * <p/>
     * Security note: Although the JPasswordField class inherits
     * the getText method, you should use the getPassword method instead.
     * Not only is getText less secure, but in the future it might return
     * the visible string (for example, "******") instead of the typed string.
     * <p/>
     * To further enhance security, once you are finished with the character
     * array returned by the getPassword method, you should set each of its
     * elements to zero.
     */
    public Object recuperaGUI() {
        /* variabili e costanti locali di lavoro */
        String unTesto = "";
        JTextComponent compTesto;
        JPasswordField compPassword;
        char[] password;

        try {    // prova ad eseguire il codice

            /* recupera il componente di testo */
            compTesto = this.getComponenteTesto();

            if (compTesto instanceof JPasswordField) {
                compPassword = (JPasswordField)compTesto;
                password = compPassword.getPassword();
                unTesto = new String(password);

                //Zero out the password.
                Arrays.fill(password, '0');

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unTesto;
    }


}// fine della classe
