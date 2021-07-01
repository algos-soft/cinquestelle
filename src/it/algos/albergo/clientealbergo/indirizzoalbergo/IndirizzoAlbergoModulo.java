/**
 * Title:     IndirizzoAlbergoModulo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25-ott-2007
 */
package it.algos.albergo.clientealbergo.indirizzoalbergo;

import it.algos.albergo.cittaalbergo.CittaAlbergoModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.scheda.Scheda;
import it.algos.gestione.indirizzo.IndirizzoModulo;
import it.algos.gestione.indirizzo.tabelle.citta.CittaModulo;
import it.algos.gestione.indirizzo.tabelle.via.ViaModulo;

/**
 * Modulo Indirizzi del Cliente Albergo.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 25-ott-2007 ore 09.04.02
 */
public final class IndirizzoAlbergoModulo extends IndirizzoModulo {

    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito) <br>
     */
    public IndirizzoAlbergoModulo() {

        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(IndirizzoAlbergo.NOME_MODULO);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(IndirizzoAlbergo.TITOLO_FINESTRA);

    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore <br>
     *
     * @param unNodo nodo dell'albero moduli
     */
    public IndirizzoAlbergoModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        this(IndirizzoAlbergo.NOME_MODULO, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Costruttore completo <br>
     *
     * @param unNomeModulo nome interno del modulo
     * @param unNodo nodo dell'albero moduli
     */
    public IndirizzoAlbergoModulo(String unNomeModulo, AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(unNomeModulo, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }/* fine del metodo costruttore completo */


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Scheda scheda;

        try { // prova ad eseguire il codice

            /* selezione del modello (obbligatorio) */
            super.setModello(new IndirizzoAlbergoModello());

            /* regola il titolo della finestra del navigatore */
            super.setTitoloFinestra(IndirizzoAlbergo.TITOLO_FINESTRA);

            /* regola il titolo di questo modulo nei menu di altri moduli */
            super.setTitoloMenu(IndirizzoAlbergo.TITOLO_MENU);

            /* sostituisce la scheda di default */
            scheda = new IndirizzoAlbergoScheda(this);
            this.addSchedaNavCorrente(scheda);

            /* crea e assegna una scheda pop */
            scheda = new SchedaPopIndirizzo(this);
            this.setSchedaPop(scheda);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo inizia


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Regola il Navigatore di default <br>
     * Crea altri Navigatori (oltre a quello standard) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void creaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try { // prova ad eseguire il codice

            /* regola il navigatore di default */
            nav = this.getNavigatoreDefault();
            nav.setUsaPannelloUnico(true);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    protected void creaModuli() {
        try { // prova ad eseguire il codice
            super.creaModulo(new ViaModulo());
            super.creaModulo(new CittaAlbergoModulo());
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
    public static IndirizzoAlbergoModulo get() {
        return (IndirizzoAlbergoModulo)ModuloBase.get(IndirizzoAlbergo.NOME_MODULO);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new IndirizzoAlbergoModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

}// fine della classe
