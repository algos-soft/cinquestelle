/**
 * Title:     NodoModuloOggetto
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      19-feb-2004
 */
package it.algos.base.modulo;

import it.algos.base.errore.Errore;

/**
 * Contenuto di un nodo per l'albero dei moduli.
 * </p>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 07-giu-2004 ore 13.33.00
 */
public final class NodoModuloOggetto extends Object {

    /**
     * percorso completo (path) per costruire il modulo
     */
    private String percorso = "";

    /**
     * nome interno (chiave) del modulo
     */
    private String nomeChiave = "";

    /**
     * riferimento al modulo
     */
    private Modulo modulo = null;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public NodoModuloOggetto() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param unPercorso path completo per la creazione dell'istanza
     */
    public NodoModuloOggetto(String unPercorso) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setPercorso(unPercorso);
    }// fine del metodo costruttore completo


    public void setPercorso(String percorso) {
        this.percorso = percorso;
    }


    public void setNomeChiave(String nomeChiave) {
        this.nomeChiave = nomeChiave;
    }


    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }


    public String getPercorso() {
        return percorso;
    }


    public String getNomeChiave() {
        return nomeChiave;
    }


    public Modulo getModulo() {
        return modulo;
    }


    public String getNomeModulo() {
        /* variabili e costanti locali di lavoro */
        String nome = "";

        try { // prova ad eseguire il codice
            if (this.getModulo() != null) {
                nome = this.getModulo().getNomeChiave();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nome;
    }


    public String toString() {
        return this.getPercorso();
    }
}// fine della classe
