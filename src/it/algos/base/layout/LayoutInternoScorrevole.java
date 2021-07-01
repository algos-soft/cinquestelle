/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      19-giu-2005
 */
package it.algos.base.layout;

import it.algos.base.errore.Errore;

import javax.swing.*;
import java.awt.*;

/**
 * Layout interno allo scorrevole.
 * <p/>
 * Creato da LayoutScorrevole.<br>
 * Mantiene i componenti quando lo scorrevole e' in uso.<br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 19-giu-2005 ore 11.09.23
 */
public final class LayoutInternoScorrevole extends LayoutBase {

    /**
     * contenitore di riferimento
     */
    private Container contenitore = null;

    /**
     * orientamento del layout
     *
     * @see Layout.ORIENTAMENTO_VERTICALE
     * @see Layout.ORIENTAMENTO_ORIZZONTALE
     */
    private int orientamento = 0;


    /**
     * Costruttore completo con parametri.
     * <p/>
     * (senza modificatore, cos� non pu� essere invocato fuori dal package) <br>
     *
     * @param cont il contenitore gestito da questo layout
     * @param orientamento codifica per l'orientamento (in interfaccia Layout)
     *
     * @see Layout.ORIENTAMENTO_VERTICALE
     * @see Layout.ORIENTAMENTO_ORIZZONTALE
     */
    public LayoutInternoScorrevole(Container cont, int orientamento) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regola le variabili di istanza coi parametri */
            this.setContenitore(cont);
            this.setOrientamento(orientamento);

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }/* fine del blocco try-catch */

    }/* fine del metodo costruttore completo */


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        LayoutManager2 layout = null;
        int orientamento = 0;

        try { // prova ad eseguire il codice
            orientamento = this.getCodOrientamentoBoxLayout(this.getOrientamento());
            layout = new BoxLayout(this.getContenitore(), orientamento);
            this.setLayoutRef(layout);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }/* fine del metodo inizia */


    public void removeLayoutComponent(Component comp) {
        super.removeLayoutComponent(comp);

    }


    private Container getContenitore() {
        return contenitore;
    }


    private void setContenitore(Container contenitore) {
        this.contenitore = contenitore;
    }


    private int getOrientamento() {
        return orientamento;
    }


    private void setOrientamento(int orientamento) {
        this.orientamento = orientamento;
    }


}// fine della classe
