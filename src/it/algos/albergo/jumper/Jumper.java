/**
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      2 feb 2009
 */
package it.algos.albergo.jumper;

import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.PannelloFlusso;

import javax.swing.Icon;
import java.awt.Color;

/**
 * Oggetto grafico che implementa l'apertura di moduli relazionati.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 2 feb 2009
 */
public class Jumper extends PannelloFlusso {

    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public Jumper(int orientamento) {

        super(orientamento);

        try { // prova ad eseguire il codice
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
        /* variabili e costanti locali di lavoro */
        Modulo mod;
        Icon icona;

        try { // prova ad eseguire il codice
            mod = ClienteAlbergoModulo.get();
            if (mod != null) {
                icona = mod.getIcona("clientealbergo24");
                this.add(icona);
            }// fine del blocco if

            this.setBackground(Color.YELLOW);
            this.setOpaque(true);
            this.setPreferredSize(100,20);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


} // fine della classe