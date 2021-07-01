/**
 * Title:        FiltroAlgos.java
 * Package:      it.algos.base.filtroOld
 * Description:  Superclasse per i filtri
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 31 gennaio 2003 alle 11.20
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002, 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.filtro;

import it.algos.base.costante.CostanteCarattere;

import javax.swing.text.PlainDocument;
import java.awt.*;

/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Fungere da superclasse per i filtri <br>
 * B - Mantiene alcuni attributi ed i loro metodi setter <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  31 gennaio 2003 ore 11.20
 */
public abstract class FiltroAlgos extends PlainDocument implements CostanteCarattere {

    /**
     * nome di questa classe (per i messaggi di errore)
     */
    private static final String NOME_CLASSE = "FiltroAlgos";

    /**
     * kit di utilita' per il suono di avviso
     */
    private static Toolkit toolkit = Toolkit.getDefaultToolkit();

    /**
     * numero minimo di caratteri ammessi nel campo
     */
    protected int caratteriMinimi = 0;

    /**
     * numero massimo di caratteri ammessi nel campo
     */
    protected int caratteriMassimi = MAX;


    /**
     * Costruttore senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public FiltroAlgos() {
        /** rimanda al costruttore della superclasse */
        super();
    } /* fine del metodo costruttore completo */


    /**
     * numero minimo di caratteri ammessi nel campo
     */
    public void setCaratteriMinimi(int caratteriMinimi) {
        this.caratteriMinimi = caratteriMinimi;
    } /* fine del metodo setter */


    /**
     * numero massimo di caratteri ammessi nel campo
     */
    public void setCaratteriMassimi(int caratteriMassimi) {
        this.caratteriMassimi = caratteriMassimi;
    } /* fine del metodo setter */


}