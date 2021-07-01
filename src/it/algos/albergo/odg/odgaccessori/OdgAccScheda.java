package it.algos.albergo.odg.odgaccessori;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.scheda.SchedaBase;

/**
 * Scheda specifiche del modulo incrocio Riga Odg - Accessori
 * </p>
 * Usata dal navigatore degli accessori interno alla scheda Riga Odg
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author Alex
 * @version 1.0 / 26-giu-2009 ore 12:02
 */
public final class OdgAccScheda extends SchedaBase implements OdgAcc {

    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento per la scheda
     */
    public OdgAccScheda(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setUsaStatusBar(false);
    }// fine del metodo inizia


    /**
     * Crea le pagine della scheda.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.scheda.SchedaBase#add
     */
    @Override protected void creaPagine() {
        /* variabili e costanti locali di lavoro */
        Pagina pagina;

        try {    // prova ad eseguire il codice

            /* crea una pagina vuota col titolo */
            pagina = super.addPagina("generale");

            /* aggiunge i campi */
            pagina.add(Cam.accessorio);
            pagina.add(Cam.quantita);

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo creaPagine


}// fine della classe