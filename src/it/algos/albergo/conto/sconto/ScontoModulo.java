/**
 * Title:     ScontoModulo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      27-set-2007
 */
package it.algos.albergo.conto.sconto;

import it.algos.albergo.conto.movimento.MovimentoModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.progetto.Progetto;

/**
 * Modulo Sconto
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 27-set-2007
 */
public class ScontoModulo extends MovimentoModulo {


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public ScontoModulo() {

        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(Sconto.NOME_MODULO);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(Sconto.TITOLO_FINESTRA);

    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public ScontoModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(Sconto.NOME_MODULO, unNodo);

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
        super.setModello(new ScontoModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(Sconto.TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(Sconto.TITOLO_MENU);

    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static ScontoModulo get() {
        return (ScontoModulo)ModuloBase.get(Sconto.NOME_MODULO);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new ScontoModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

} // fine della classe
