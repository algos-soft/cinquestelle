/**
 * Title:     RicercaDefault
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      2-mag-2006
 */
package it.algos.base.ricerca;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;

import java.util.ArrayList;

/**
 * Dialogo di ricerca di default.
 * </p>
 * Contiene tutti i campi definiti come ricercabili nel Modello o, in assenza
 * di questi, tutti i campi visibili nella lista del navigatore corrente.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 16-ago-2005 ore 14.36.34
 */
public class RicercaDefault extends RicercaBase {


    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento
     */
    public RicercaDefault(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

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


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> listaCampi = null;
        boolean range = false;

        try { // prova ad eseguire il codice

            /* aggiunge i campi di ricerca */
            listaCampi = this.getCampiRicerca();
            for (Campo campo : listaCampi) {
                range = campo.getCampoDati().isUsaRangeRicerca();
                this.addCampoRicerca(campo, range);
            }

            /* rimanda al metodo sovrascritto */
            super.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna l'elenco dei campi ricercabili.
     * <p/>
     * Se nel modello e' definito almeno un campo ricercabile,
     * ritorna i campi definiti come ricercabili nel Modello.
     * Altrimenti, ritorna l'elenco dei campi visibili
     * nella lista del navigatore corrente.
     *
     * @return l'elenco dei campi ricercabili
     */
    private ArrayList<Campo> getCampiRicerca() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> listaCampi = null;
        Modulo modulo = null;
        Navigatore nav;
        Lista lista;
        Vista vista;
        ArrayList<VistaElemento> elementi;
        Campo campo;


        try {    // prova ad eseguire il codice

            /* recupera i campi ricercabili dal modello */
            modulo = this.getModulo();
            listaCampi = modulo.getCampiRicercabili();

            /* se non ce ne sono usa i campi visibili della vista default */
            /* usa i campi originali (prima della espansione) */
            if (listaCampi.size() == 0) {
                nav = modulo.getNavigatoreCorrente();
                if (nav != null) {
                    lista = nav.getLista();
                    if (lista != null) {
                        vista = lista.getVista();
                        if (vista != null) {
                            listaCampi = new ArrayList<Campo>();
                            elementi = vista.getElementiVisibili();
                            for (VistaElemento elem : elementi) {
                                campo = elem.getCampoOriginale();
                                listaCampi.add(campo);
                            }
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaCampi;
    }


    public boolean isConfermabile() {
        return super.isConfermabile();    //To change body of overridden methods use File | Settings | File Templates.
    }

}
