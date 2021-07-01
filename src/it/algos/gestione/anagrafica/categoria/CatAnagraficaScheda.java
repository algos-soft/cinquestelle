package it.algos.gestione.anagrafica.categoria;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.scheda.SchedaDefault;

/**
 * Scheda specifiche del pacchetto - @todo Manca la descrizione della classe..
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @version 1.0 / 2-apr-2009
 */
public final class CatAnagraficaScheda extends SchedaDefault implements CatAnagrafica {

    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento per la scheda
     */
    public CatAnagraficaScheda(Modulo modulo) {
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
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Recupera il db <br>
     * Chiede al db i dati del record <br>
     * Regola i dati della scheda <br>
     */
    @Override
    public void avvia(int codice) {

        try { // prova ad eseguire il codice
            super.avvia(codice);

            if (codice == 1 || codice == 2 || codice == 3) {
                this.setModificabile(false);
            } else {
                this.setModificabile(true);
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

}// fine della classe}