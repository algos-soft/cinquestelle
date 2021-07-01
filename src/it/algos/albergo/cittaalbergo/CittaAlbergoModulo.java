package it.algos.albergo.cittaalbergo;

import it.algos.albergo.provinciaalbergo.ProvinciaAlbergoModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.gestione.indirizzo.tabelle.citta.Citta;
import it.algos.gestione.indirizzo.tabelle.citta.CittaModulo;
import it.algos.gestione.indirizzo.tabelle.provincia.ProvinciaModulo;

public class CittaAlbergoModulo extends CittaModulo {

    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito) <br>
     */
    public CittaAlbergoModulo() {
    	
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(Citta.NOME_MODULO);

    } /* fine del metodo costruttore provvisorio */

    /**
     * Costruttore <br>
     *
     * @param unNodo nodo dell'albero moduli
     */
    public CittaAlbergoModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        this(Citta.NOME_MODULO, unNodo);

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
    public CittaAlbergoModulo(String unNomeModulo, AlberoNodo unNodo) {
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

        try { // prova ad eseguire il codice

            /* selezione del modello (obbligatorio) */
            super.setModello(new CittaAlbergoModello());

            // navigatore custom
            this.addNavigatoreCorrente(new CittaAlbergoNavigatore(this));

            /* sostituisce la scheda nel navigatore di default */
            this.addSchedaNavCorrente(new CittaAlbergoScheda(this));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo inizia

    protected void creaModuli() {
        super.creaModulo(new ProvinciaAlbergoModulo());
    }

    
    
    /**
     * Main method.
     *
     * @param argomenti eventuali (quasi mai) parametri in ingresso
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new CittaAlbergoModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    }// fine del metodo main

}
