/**
 * Title:     TipoModulo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      28-apr-2004
 */
package it.algos.gestione.contatto.tabelle;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.progetto.Progetto;

/**
 * TipoModulo - Contenitore dei riferimenti agli oggetti del package.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Contiene tutti i riferimenti agli oggetti delle classi che servono
 * per gestire una parte del programma </li>
 * <li>  </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 28-apr-2004 ore 7.51.52
 */
public final class TipoContattoModulo extends ModuloBase {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = TipoContatto.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = TipoContatto.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = TipoContatto.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default) <br>
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito) <br>
     */
    public TipoContattoModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(NOME_PROGRAMMA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo <br>
     *
     * @param unNodo nodo dell'albero moduli
     */
    public TipoContattoModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(NOME_CHIAVE, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try {    // prova ad eseguire il codice
            /* selezione del modello (obbligatorio) */
            super.setModello(new TipoContattoModello());

            /* regola il voce della finestra del navigatore */
            super.setTitoloFinestra(TITOLO_FINESTRA);

            /* regola il voce di questo modulo nei menu di altri moduli */
            super.setTitoloMenu(TITOLO_MENU);

            /* regola il flag (le tabelle usano finestre piu' piccole) (facoltativo) */
            super.setTabella(true);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Prima istallazione.
     * <p/>
     * Metodo invocato da Progetto.lancia() <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * <p/>
     * Esegue il metodo iniziale di istallazione per ogni modulo <br>
     */
    @Override
    public void setup() {
        try { // prova ad eseguire il codice

            /* traverso tutta la collezione */
            for (TipoContatto.Setup contatto : TipoContatto.Setup.values()) {
                this.query().nuovoRecord("sigla", contatto.toString());
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static TipoContattoModulo get() {
        return (TipoContattoModulo)ModuloBase.get(NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new TipoContattoModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

}// fine della classe
