/**
 * Title:        DialogoBase.java
 * Package:      it.algos.base.dialogo
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 2 novembre 2003 alle 7.53
 */

package it.algos.base.dialogo;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;

import java.util.ArrayList;

/**
 * Dialogo con una lista di valori interni.
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  2 novembre 2003 ore 7.53
 */
public class DialogoListaInterna extends DialogoLista {

    private ArrayList valori;


    /**
     * Costruttore senza parametri.
     * <p/>
     */
    public DialogoListaInterna() {
        this(null);

    } /* fine del metodo costruttore completo */


    /**
     * Costruttore base con parametri.
     * <p/>
     */
    public DialogoListaInterna(ArrayList valori) {

        super("");

        /* regola le variabili di istanza coi parametri */
        this.setValori(valori);

        /* regolazioni iniziali di riferimenti e variabili */
        try {
            this.inizia();
        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione <br>
     */
    private void inizia() throws Exception {
        Campo campoLista;
        String nomeCampo = "lista";

        try { // prova ad eseguire il codice

            campoLista = CampoFactory.listaInterna(nomeCampo);
            campoLista.getCampoDati().setValoriInterni(this.getValori());
            campoLista.decora().obbligatorio();
            campoLista.decora().eliminaEtichetta();

            this.setCampoLista(campoLista);
            this.addCampo(campoLista);

            /* si registra presso il campo */
            campoLista.addListener(new AzioneDoppioClick());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo inizia */


    private ArrayList getValori() {
        return valori;
    }


    private void setValori(ArrayList valori) {
        this.valori = valori;
    }

}// fine della classe
