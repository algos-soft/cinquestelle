/**
 * Title:     SchedaDefault
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      04-10-2004
 */
package it.algos.base.scheda;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;

/**
 * Scheda di default vuota.
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 04-10-2004 ore 14.11.08
 */
public class SchedaDefault extends SchedaBase {

    /**
     * nome del set per costruire  in automatico la scheda
     */
    private String nomeSet = "";


    /**
     * Costruttore con parametri.
     * <p/>
     *
     * @param modulo di riferimento
     */
    public SchedaDefault(Modulo modulo) {
        this(modulo, Modulo.SET_BASE_DEFAULT);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param modulo di riferimento
     */
    public SchedaDefault(Modulo modulo, String nomeSet) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        /* regola le variabili di istanza coi parametri */
        this.setNomeSet(nomeSet);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
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
        this.setNomeChiave(NOME_CHIAVE_DEFAULT);
    }// fine del metodo inizia


    /**
     * Crea le pagine.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void creaPagine() {
        try { // prova ad eseguire il codice
            /* aggiunge  una pagina al libro con il set di default */
            this.addPagina("generale", this.getNomeSet());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private String getNomeSet() {
        return nomeSet;
    }


    public void setNomeSet(String nomeSet) {
        this.nomeSet = nomeSet;
    }
}// fine della classe
