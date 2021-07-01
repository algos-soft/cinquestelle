/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.base.wrapper;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA CLASSE .
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 3-4-05
 */
public final class ListaSelezione extends Object {

    private int[] dati = null;

    private String nomeModulo = "";

    private String nomeSelezione = "";

    private boolean privata = false;


    /**
     * Costruttore completo senza parametri.
     */
    public ListaSelezione() {
        /* invoca il metodo delegato della classe */
        this("", "", null, false);
    }


    /**
     * Costruttore completo con parametri.
     */
    public ListaSelezione(String modulo, String nome, int[] dati, boolean privata) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setNomeModulo(modulo);
        this.setNomeSelezione(nome);
        this.setDati(dati);
        this.setPrivata(privata);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }


    public String getNomeModulo() {
        return nomeModulo;
    }


    private void setNomeModulo(String modulo) {
        this.nomeModulo = modulo;
    }


    public String getNomeSelezione() {
        return nomeSelezione;
    }


    private void setNomeSelezione(String nome) {
        this.nomeSelezione = nome;
    }


    public String getDati() {
        return Lib.Testo.getStringaVirgola(this.dati);
    }


    private void setDati(int[] dati) {
        this.dati = dati;
    }


    public boolean isPrivata() {
        return privata;
    }


    private void setPrivata(boolean privata) {
        this.privata = privata;
    }
} // fine della classe
