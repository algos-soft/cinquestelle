package it.algos.albergo.odg.odgzona;

import it.algos.albergo.odg.odgriga.OdgRiga;
import it.algos.albergo.odg.odgriga.OdgRigaModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.NavigatoreFactory;
import it.algos.base.progetto.Progetto;

/**
 * Modulo Zone di un ODG
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 11-giu-2009 ore  16:46
 */
public final class OdgZonaModulo extends ModuloBase implements OdgZona {

    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public OdgZonaModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(OdgZona.NOME_MODULO);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(OdgZona.TITOLO_FINESTRA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public OdgZonaModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(OdgZona.NOME_MODULO, unNodo);

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
        super.setModello(new OdgZonaModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(OdgZona.TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(OdgZona.TITOLO_MENU);

    }// fine del metodo inizia


    @Override
    public boolean inizializza() {
        return super.inizializza();
    }


    @Override
    protected void creaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        super.creaNavigatori();

        try { // prova ad eseguire il codice

            /* navigatore lista per le righe di zona */
            nav = new ZoneNavigatore(this);
            this.addNavigatore(nav, OdgZona.Nav.navZone.get());

            /* navigatore composto zone - righe ODG */
            nav = NavigatoreFactory.navigatoreNavigatore(this, OdgZona.Nav.navZone.get(), OdgRiga.NOME_MODULO, OdgRiga.Nav.navRighe.get());
            nav.setOrizzontale(false);
            this.addNavigatore(nav, OdgZona.Nav.navDoppio.get());

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
    public static OdgZonaModulo get() {
        return (OdgZonaModulo)ModuloBase.get(OdgZona.NOME_MODULO);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new OdgZonaModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main


}// fine della classe