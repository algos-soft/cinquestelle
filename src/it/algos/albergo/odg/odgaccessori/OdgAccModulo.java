package it.algos.albergo.odg.odgaccessori;

import it.algos.albergo.camera.accessori.AccessoriModulo;
import it.algos.albergo.odg.odgriga.OdgRigaModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;

/**
 * Modulo Accessori Righe ODG
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 26-giu-2009 ore  11:46
 */
public final class OdgAccModulo extends ModuloBase implements OdgAcc {

    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public OdgAccModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(OdgAcc.NOME_MODULO);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(OdgAcc.TITOLO_FINESTRA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public OdgAccModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(OdgAcc.NOME_MODULO, unNodo);

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
        super.setModello(new OdgAccModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(OdgAcc.TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(OdgAcc.TITOLO_MENU);

    }// fine del metodo inizia



    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Regola il Navigatore di default e crea altri Navigatori.
     */
    protected void creaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try { // prova ad eseguire il codice

            /**
             * Crea un Navigatore per la lista degli accessori
             * dentro alla scheda Riga Odg
             */
            nav = new NavInRigheOdg(this);
            this.addNavigatoreCorrente(nav);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * </p>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Metodo sovrascritto nelle classi specifiche <br>
     * <p/>
     * Aggiunge alla collezione moduli (del Progetto), i moduli necessari <br>
     */
    @Override
    protected void creaModuli() {
        try { // prova ad eseguire il codice
            super.creaModulo(new OdgRigaModulo());
            super.creaModulo(new AccessoriModulo());
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
    public static OdgAccModulo get() {
        return (OdgAccModulo)ModuloBase.get(OdgAcc.NOME_MODULO);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new OdgAccModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main


}// fine della classe