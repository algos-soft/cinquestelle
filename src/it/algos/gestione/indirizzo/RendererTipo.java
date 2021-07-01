/**
 * Title:     RendererTipo
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      8-mar-2007
 */
package it.algos.gestione.indirizzo;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.tavola.renderer.RendererBase;

import java.util.ArrayList;

/**
 * Renderer di colonna per il campo Tipo Sede
 */
public class RendererTipo extends RendererBase implements Indirizzo {


    /**
     * Costruttore completo
     * <p/>
     *
     * @param campo di riferimento
     */
    public RendererTipo(Campo campo) {
        /* rimanda al costruttore della superclasse */
        super(campo);
        /* regolazioni iniziali di riferimenti e variabili */
        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
//        setHorizontalAlignment(SwingConstants.CENTER);
    } /* fine del metodo */


    /**
     * Effettua il rendering di un valore.
     * <p/>
     *
     * @param objIn valore in ingresso
     *
     * @return valore in uscita
     */
    public Object rendValue(Object objIn) {
        /* variabili e costanti locali di lavoro */
        Object objOut = null;
        ArrayList lista;
        ArrayList<TipiSede> listaTipi;
        String stringa = "";


        try {    // prova ad eseguire il codice
            if (objIn != null) {
                if (objIn instanceof ArrayList) {
                    lista = (ArrayList)objIn;
                    listaTipi = TipiSede.getTipiSede(lista);
                    stringa = "";
                    for (TipiSede tipo : listaTipi) {
                        if (Lib.Testo.isValida(stringa)) {
                            stringa += ", ";
                        }// fine del blocco if
                        stringa += tipo.getDescBreve();
                    }
                }// fine del blocco if
            }// fine del blocco if

            objOut = stringa;

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return objOut;
    }


}
