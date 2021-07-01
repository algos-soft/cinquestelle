package it.algos.gestione.indirizzo.tabelle.provincia;

import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.scheda.SchedaBase;

/**
 * Scheda specifiche del pacchetto - @todo Manca la descrizione della classe..
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 20-mar-2008 ore  08:20
 */
public final class ProvinciaScheda extends SchedaBase implements Provincia {

    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento per la scheda
     */
    public ProvinciaScheda(Modulo modulo) {
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
        Pagina pag;
        Pannello pan;
        Pannello panReg;

        try {    // prova ad eseguire il codice
            /* crea una pagina vuota col titolo */
            pag = super.addPagina("generale");

            /* aggiunge i campi */
            pag.add(Cam.nomeCorrente);
            pag.add(Cam.nomeCompleto);

            pan = PannelloFactory.orizzontale(this);
            pan.add(Cam.sigla);
            pag.add(pan);

            panReg = PannelloFactory.verticale(this);
            panReg.setGapMassimo(-5);
            panReg.add(Cam.regioneBreve);
            panReg.add(Cam.regioneCompleto);
            pag.add(panReg);

            pag.add(Cam.linkNazione);

            /* note */
            pag.add(ModelloAlgos.NOME_CAMPO_NOTE);

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo creaPagine
}// fine della classe