/**
 * Title:     WrapperPannello
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      29-feb-2004
 */
package it.algos.base.pannello;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;

import java.awt.*;

/**
 * Wrapper per inglobare il riferimento al Campo che crea questo pannello.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Costruisce un tipo di dati </li>
 * <li> Raggruppa questi dati in un wrapper </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 29-feb-2004 ore 15.12.45
 */
public final class PannelloComponenti extends PannelloBase implements Cloneable {

    /**
     * disegna uno sfondo colorato per debug
     */
    protected static final boolean DEBUG = false;

    /**
     * riferimento al PannelloCampo proprietario
     */
    private PannelloCampo pannelloCampo = null;


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param panCampo PannelloCampo proprietario
     */
    public PannelloComponenti(PannelloCampo panCampo) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice

            /* regola le variabili di istanza coi parametri */
            this.setPannelloCampo(panCampo);

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

        try { // prova ad eseguire il codice

            this.setLayout(new BorderLayout());
            this.setOpaque(false);

            /* se e' attivo il debug usa sfondo, bordi e colori */
            if (DEBUG) {
                this.setOpaque(true);
                this.setBackground(Color.pink);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna il campo proprietario di questo PannelloComponenti.
     * <p/>
     *
     * @return il campo proprietario
     */
    public Campo getCampo() {
        return this.getPannelloCampo().getCampo();
    }


    private PannelloCampo getPannelloCampo() {
        return pannelloCampo;
    }


    public void setPannelloCampo(PannelloCampo pannelloCampo) {
        this.pannelloCampo = pannelloCampo;
    }


}// fine della classe
