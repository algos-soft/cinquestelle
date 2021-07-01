package it.algos.albergo.pensioni;

import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;

/**
 * Classe astratta per i moduli di creazione dei singoli files. </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-ott-2007 ore 19.21.46
 */
public class DialogoAddebiti extends DialogoAnnullaConferma {

    private PanAddebiti panPensioni;


    /**
     * Costruttore base con parametri.
     * <p/>
     *
     * @param panPensioni pannello pensioni da inserire nel dialogo
     */
    public DialogoAddebiti(PanAddebiti panPensioni) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setPanPensioni(panPensioni);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Costruttore base con parametri.
     * <p/>
     */
    public DialogoAddebiti() {
        this(null);
    }// fine del metodo costruttore completo


    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal
     * costruttore (init) <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        PanAddebiti panPensioni;

        try { // prova ad eseguire il codice

            this.setTitolo("Addebiti previsti");

            /* usa il pannello pensioni registrato, se manca lo crea ora */
            panPensioni = this.getPanPensioni();
            if (panPensioni == null) {
                panPensioni = new PanAddebiti();
            }// fine del blocco if

            /* aggiunge il componente al dialogo */
            this.addComponente(panPensioni);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    public PanAddebiti getPanPensioni() {
        return panPensioni;
    }


    private PanAddebiti setPanPensioni(PanAddebiti panPensioni) {
        this.panPensioni = panPensioni;
        return this.getPanPensioni();
    }
}// fine della classe