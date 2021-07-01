package it.algos.albergo.camera.accessori;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;

/**
 * Modulo Accessori Camera
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 11-giu-2009 ore  16:46
 */
public final class AccessoriModulo extends ModuloBase implements Accessori {


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public AccessoriModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(Accessori.NOME_MODULO);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(Accessori.TITOLO_FINESTRA);

    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public AccessoriModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(Accessori.NOME_MODULO, unNodo);

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
        super.setModello(new AccessoriModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(Accessori.TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(Accessori.TITOLO_MENU);

        this.setTabella(true);

    }// fine del metodo inizia


    @Override
    protected void creaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try { // prova ad eseguire il codice

            /* abilita le frecce di spostamento ordine */
            this.getNavigatoreDefault().setUsaFrecceSpostaOrdineLista(true);
            
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
    public static AccessoriModulo get() {
        return (AccessoriModulo)ModuloBase.get(Accessori.NOME_MODULO);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new AccessoriModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main


}// fine della classe