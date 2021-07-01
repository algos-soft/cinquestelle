/**
 * Title:     TAReale
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      30-mar-2006
 */
package it.algos.base.campo.tipodati.tipoarchivio;

import it.algos.base.database.tipodati.TipoDati;
import it.algos.base.errore.Errore;

/**
 * Archivio del campo di tipo intero.
 * <p/>
 * Numeri fino a 2.000.000.000 (circa) <br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 30-mar-2006 ore 12.24.56
 */
public final class TAReale extends TANumero {

    /**
     * istanza unica di questa classe, creata ad avvio del programma
     */
    private final static TAReale ISTANZA = new TAReale();


    /**
     * Costruttore completo senza parametri.
     */
    private TAReale() {
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
        this.setClasse(Double.class);
        super.setChiaveTipoDatiDb(TipoDati.TIPO_DOUBLE);
        super.setValoreVuoto(new Double(0));
    }// fine del metodo inizia


    public static TAReale getIstanza() {
        return TAReale.ISTANZA;
    } /* fine del metodo getter */

}// fine della classe
