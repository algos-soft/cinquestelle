/**
 * Title:     PagamentoModulo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      18-lug-2006
 */
package it.algos.albergo.conto.pagamento;

import it.algos.albergo.conto.movimento.MovimentoModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.gestione.tabelle.mezzopagamento.MezzoPagamentoModulo;

/**
 * Modulo Pagamento
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 18-lug-2006
 */
public class PagamentoModulo extends MovimentoModulo {


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public PagamentoModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(Pagamento.NOME_MODULO);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(Pagamento.TITOLO_FINESTRA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public PagamentoModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(Pagamento.NOME_MODULO, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* selezione del modello (obbligatorio) */
        super.setModello(new PagamentoModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(Pagamento.TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(Pagamento.TITOLO_MENU);

    }


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * </p>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Metodo sovrascritto nelle classi specifiche <br>
     */
    protected void creaModuli() {
        try { // prova ad eseguire il codice
            super.creaModulo(new MezzoPagamentoModulo());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea il Navigatore da visualizzare all'interno
     * della scheda del conto.
     * <p/>
     *
     * @return il Navigatore creato
     */
    protected Navigatore creaNavConto() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;

        try { // prova ad eseguire il codice

            /* crea un navigatore per il conto */
            nav = new PagamentoNavConto(this);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }
    

    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static PagamentoModulo get() {
        return (PagamentoModulo)ModuloBase.get(Pagamento.NOME_MODULO);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new PagamentoModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

} // fine della classe
