/**
 * Title:        Elenco.java
 * Package:      it.algos.base.elenco
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 5 novembre 2003 alle 18.00
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
package it.algos.base.elenco;

import it.algos.base.matrice.MatriceDoppia;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Questa interfaccia e' responsabile di: <br>
 * A - Gestire un elenco di dati ed i codici relativi <br>
 * B - I dati sono mantenuti in una lista di valori <br>
 * C - Viene mantenuto inoltre un valore di selezione
 * relativo alla lista dei valori <br>
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  5 novembre 2003 ore 18.00
 */
public interface Elenco {

    public abstract void regolaPosizione(int codice);


    public abstract void regolaPosizione(Object codice, Object posizioneDefault);


    public abstract void addValoreCodice(Object unValore, Object unCodice, int unaPosizione);


    public abstract void addValoreCodice(Object unValore, int unCodice, int unaPosizione);


    public abstract void setMatriceDoppia(MatriceDoppia unaMatriceDoppia);


    public abstract void setPosizione(Integer posizione);


    public abstract void setPosizione(int posizione);


    public abstract void setSelezione(ArrayList selezione);


    public abstract MatriceDoppia getMatriceDoppia();


    public abstract ArrayList getListaValori();


    public abstract Vector getVettoreValori();


    public abstract Object[] getArrayValori();


    public abstract Object getValoreSelezionato();


    public abstract int getPosizione(Object unCodice);


    public abstract int getPosizione(int unCodice);


    public abstract ArrayList getSelezione();


    public abstract ArrayList getListaCodici();


    public abstract int getCodiceSelezionato();


    public abstract int getCodice(int unaPosizione);


    public abstract int getCodice(Object unaPosizione);


    /**
     * Ritorna il valore alla posizione specificata.
     * <p/>
     *
     * @return il valore alla posizione specificata
     *         (1 per la prima posizione)
     */
    public abstract Object getValore(int posizione);


}

