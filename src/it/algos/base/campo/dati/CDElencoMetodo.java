/**
 * Title:        CDElencoMetodo.java
 * Package:      it.algos.base.campo.dati
 * Description:
 * Copyright:    Copyright (c) 2004
 * Company:      Algos s.r.l.
 * @author Ceresa, Valbonesi
 * @version 1.0  /
 * Creato:       il 15 giu 2004 alle 15.19
 */
package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.db.CDBComboRadioMetodo;
import it.algos.base.elenco.Elenco;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.matrice.MatriceDoppia;
import it.algos.base.modulo.Modulo;
import it.algos.base.tavola.renderer.RendererElenco;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Classe concreta per implementare un oggetto da <code>CDElenco</code> <br>
 * <p/>
 * Questa classe concreta e' responsabile di: <br>
 * Acquisire i dati da un metodo esterno e regolare la <br>
 * lista valori del campo con i dati forniti dal metodo.
 *
 * @author Guido Andrea Ceresa e Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  15 giugno 2004 ore 15.23
 */
public final class CDElencoMetodo extends CDElenco {


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDElencoMetodo() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDElencoMetodo(Campo unCampoParente) {
        /** rimanda al costruttore della superclasse */
        super(unCampoParente);

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* regola il renderer dei dati nella lista */
        this.setRenderer(new RendererElenco(this.getCampoParente()));
    } /* fine del metodo inizia */


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     */
    public void inizializza() {

        /* esegue l'inizializzazione nella superclasse e ritorna il risultato  */
        super.inizializza();

        /* esegue l'inizializzazione in questa classe specifica */

        try {    // prova ad eseguire il codice
            super.creaElenco();
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Recupera il valore di elenco correntemente selezionato.
     * <p/>
     *
     * @return il valore corrente
     */
    public Object getValoreElenco() {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        Elenco elenco = null;

        try { // prova ad eseguire il codice
            elenco = this.getElenco();
            if (elenco != null) {
                valore = elenco.getValoreSelezionato();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * recupera la lista di valori col metodo accessore <br>
     * crea una lista di posizioni <br>
     *
     * @return una MatriceDoppia contenente posizioni e valori<br>
     *         Se ritorna nullo, significa che il metodo non e' ancora in
     *         grado di fornire i valori (es. modulo non inizializzato)
     */
    protected MatriceDoppia regolaValoriMatrice(MatriceDoppia unaMatriceDoppia) {
        /* variabili e costanti locali di lavoro */
        ArrayList listaCodici = null;
        ArrayList listaValori = null;
        CDBComboRadioMetodo campoDB = null;
        Modulo moduloFornitore = null;
        Method metodoFornitore = null;
        Object[] argomenti = null;
        Object[] valori = null;
        Object ritorno = null;
        boolean continua = true;

        try {    // prova ad eseguire il codice

            /* recupera il modulo e il metodo fornitori dei valori */
            if (continua) {
                campoDB = (CDBComboRadioMetodo)this.getCampoParente().getCampoDB();
                moduloFornitore = campoDB.getIstanzaModuloValori();
                metodoFornitore = campoDB.getIstanzaMetodoValori();
            }// fine del blocco if

            /* controlla che il metodo non sia nullo */
            if (continua) {
                if (metodoFornitore == null) {
                    throw new Exception("metodo fornitore dei valori nullo");
                }// fine del blocco if
            }// fine del blocco if

            /* invoca il metodo e recupera il risultato */
            if (continua) {
                argomenti = new Object[0];
                ritorno = metodoFornitore.invoke((Object)moduloFornitore, argomenti);
            }// fine del blocco if

            /* controlla che il risultato non sia nullo,
             * in tal caso il metodo non e' ancora in grado di
             * fornire i valori, non e' un errore */
            if (continua) {
                if (ritorno == null) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* recupera i valori come matrice di Object[]
             * crea un'istanza di matrice dati
             * crea una lista di posizioni
             * regola i valori della matrice */
            if (continua) {
                valori = (Object[])ritorno;
                listaValori = Libreria.objectArrayToArrayList(valori);
                unaMatriceDoppia.setValori(listaValori);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unaMatriceDoppia;
    } /* fine del metodo */


}// fine della classe