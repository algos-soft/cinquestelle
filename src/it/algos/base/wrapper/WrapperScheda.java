/**
 * Title:     WrapperScheda
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      5-feb-2004
 */
package it.algos.base.wrapper;

import it.algos.base.errore.Errore;

/**
 * Nome di una classe scheda e set di campi .
 * </p>
 * Questa classe concreta: <ul>
 * <li> Costruisce un tipo di dati </li>
 * <li> Raggruppa questi dati in un wrapper </li>
 * <li> Mantiene il nome di una classe scheda </li>
 * <li> Mantiene un flag per usare la classe scheda di default </li>
 * <li> Mantiene il nome di un set di campi (solo per la scheda di default) </li>
 * <li> Se viene usato il costruttore senza parametri, usa la classe standard
 * con tutti i campi visibili nella scheda </li>
 * <li> Se viene usato il costruttore con una stringa, usa la classe indicata
 * come parametro per costruire la scheda </li>
 * <li> Se si vuole usare la classe standard con un set particolare di campi,
 * usare il costruttore senza parametri e poi modificare i valori coi
 * metodi accessori </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 5-feb-2004 ore 19.26.47
 */
public final class WrapperScheda extends Object {

    /**
     * valore di default del flag - di solito usa la scheda di default
     */
    public static final boolean SCHEDA_DEFAULT = true;

    /**
     * nome della classe di default
     */
    private static final String NOME_CLASSE = "SchedaDefault";

    /**
     * flag per usare la classe scheda di default
     */
    private boolean isSchedaDefault = false;

    /**
     * nome della classe da usare per la scheda
     */
    private String nomeClasse = "";

    /**
     * nome del set di campi da mostrare nella scheda di default
     */
    private String nomeSet = "";


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public WrapperScheda() {
        /* rimanda al costruttore di questa classe */
        this("");
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param nomeClasse nome della classe non standard da usare per la scheda
     */
    public WrapperScheda(String nomeClasse) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setNomeClasse(nomeClasse);

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
        /* regolazioni di default */
        this.setSchedaDefault(SCHEDA_DEFAULT);
        this.setNomeClasse(NOME_CLASSE);
    }// fine del metodo inizia


    /**
     * nome della classe da usare per la scheda
     */

    public String getNomeClasse() {
        return nomeClasse;
    }


    /**
     * nome della classe da usare per la scheda
     */

    public void setNomeClasse(String nomeClasse) {
        this.nomeClasse = nomeClasse;
    }


    /**
     * nome del set di campi da mostrare nella scheda di default
     */

    public String getNomeSet() {
        return nomeSet;
    }


    /**
     * nome del set di campi da mostrare nella scheda di default
     */

    public void setNomeSet(String nomeSet) {
        this.nomeSet = nomeSet;
    }


    /**
     * flag per usare la classe scheda di default
     */

    public boolean isSchedaDefault() {
        return isSchedaDefault;
    }


    /**
     * flag per usare la classe scheda di default
     */

    public void setSchedaDefault(boolean schedaDefault) {
        isSchedaDefault = schedaDefault;
    }
}// fine della classe
