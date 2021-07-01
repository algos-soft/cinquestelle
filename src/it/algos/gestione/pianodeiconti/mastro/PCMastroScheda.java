/**
 * Title:     ContoScheda
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      9-mar-2006
 */
package it.algos.gestione.pianodeiconti.mastro;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.scheda.SchedaBase;

/**
 * Scheda specifica del conto.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 9-mar-2006 ore 14.39.46
 */
public final class PCMastroScheda extends SchedaBase {


    /**
     * Costruttore completo <br>
     */
    public PCMastroScheda(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }


    /**
     * Crea le pagine della scheda.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaPagine() {
        Pagina pag;

        try { // prova ad eseguire il codice

            pag = this.addPagina("generale");
            pag.add(PCMastro.CAMPO_SIGLA);
            pag.add(PCMastro.CAMPO_DESCRIZIONE);

            pag = this.addPagina("conti");
            pag.add(PCMastro.CAMPO_CONTI);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

}// fine della classe
