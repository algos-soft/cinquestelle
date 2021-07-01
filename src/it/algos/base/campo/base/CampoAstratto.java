/**
 * Title:        CampoAstratto.java
 * Package:      prove.nuovocampo
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 3 luglio 2003 alle 20.30
 */

package it.algos.base.campo.base;

/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Mantiene dgli attributi comuni a tutte le classi del package campo <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  3 luglio 2003 ore 20.30
 */
public abstract class CampoAstratto {

    /**
     * riferimento al campo 'contenitore' dei vari oggetti che insieme
     * svolgono le funzioni del campo
     */
    protected Campo unCampoParente = null;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CampoAstratto() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CampoAstratto(Campo unCampoParente) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regola le variabili di istanza coi parametri */
        this.setCampoParente(unCampoParente);


    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     */
    public void inizializzaCampoAstratto() {
    } /* fine del metodo */


    /**
     * Regolazioni di avvio, ogni volta che questo oggetto deve 'ripartire',
     * per essere sicuri che sia 'pulito'
     * Metodo chiamato da altre classi
     * Viene eseguito tutte le volte che necessita
     */
    public void avviaCampoAstratto() {
    } /* fine del metodo */


    /**
     * riferimento al campo 'contenitore' dei vari oggetti che insieme
     * svolgono le funzioni del campo
     */
    public void setCampoParente(Campo unCampoParente) {
        this.unCampoParente = unCampoParente;
    } /* fine del metodo setter */


    /**
     * riferimento al campo 'contenitore' dei vari oggetti che insieme
     * svolgono le funzioni del campo
     */
    public Campo getCampoParente() {
        return unCampoParente;
    } /* fine del metodo getter */


}// fine della classe