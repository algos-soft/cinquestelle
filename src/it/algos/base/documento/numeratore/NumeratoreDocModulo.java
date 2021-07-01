/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 20 lug 2006
 */

package it.algos.base.documento.numeratore;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;

import java.util.ArrayList;

/**
 * Modulo Numeratore Documenti.
 * <p/>
 * Fornisce la numerazione ai documenti.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 20 lug 2006
 */
public final class NumeratoreDocModulo extends ModuloBase {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = NumeratoreDoc.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = NumeratoreDoc.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = NumeratoreDoc.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public NumeratoreDocModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(NOME_PROGRAMMA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public NumeratoreDocModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(NOME_CHIAVE, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* selezione del modello (obbligatorio) */
        super.setModello(new NumeratoreDocModello());

        /* regola il voce della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il voce di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        /* regola il flag (le tabelle usano finestre piu' piccole) (facoltativo) */
        super.setTabella(true);
    }


    /**
     * Restituisce il prossimo numero di documento.
     * <p/>
     * Non incrementa il contatore
     *
     * @param chiave del tipo di documento
     *
     * @return prossimo numero
     */
    public int getNextNumero(String chiave) {
        /* variabili e costanti locali di lavoro */
        int numero = 0;
        Filtro filtro;
        ArrayList lista;

        try { // prova ad eseguire il codice
            filtro = FiltroFactory.crea(NumeratoreDoc.Cam.chiave.get(), chiave);
            lista = this.query().valoriCampo(NumeratoreDoc.Cam.ultimoNumero.get(), filtro);

            if (lista.size() == 1) {
                numero = (Integer)lista.get(0);
                numero++;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return numero;
    }


    /**
     * Regola il contatore con l'ultimo numero assegnato
     * a un dato tipo di documento.
     * <p/>
     *
     * @param chiave del tipo di documento
     * @param numero l'ultimo numero assegnato
     *
     * @return true se riuscito
     */
    public boolean setUltNumero(String chiave, int numero) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        int id;
        Campo campo;

        try {    // prova ad eseguire il codice

            campo = this.getCampo(NumeratoreDoc.Cam.ultimoNumero.get());
            id = this.getCodice(chiave);
            if (id > 0) {
                riuscito = this.query().registraRecordValore(id, campo, numero);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Ritorna il codice record per una data chiave.
     * <p/>
     *
     * @param chiave la chiave
     *
     * @return il codice
     */
    private int getCodice(String chiave) {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        Filtro filtro;

        try {    // prova ad eseguire il codice
            filtro = FiltroFactory.crea(NumeratoreDoc.Cam.chiave.get(), chiave);
            codice = this.query().valoreChiave(filtro);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static NumeratoreDocModulo get() {
        return (NumeratoreDocModulo)ModuloBase.get(NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new NumeratoreDocModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

} // fine della classe
