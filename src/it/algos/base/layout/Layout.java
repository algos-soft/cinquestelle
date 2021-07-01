/*
 * Copyright:    Copyright (c) 2004
 * Company:      Algos s.r.l.
 * Author:       gac
 * Data:         21 dicembre 2003, 17.08
 */
package it.algos.base.layout;

import java.awt.*;

/**
 * Organizza graficamente un gruppo di oggetti in un contenitore.
 * <p/>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> <br>
 * <li> Fornisce un interfaccia unificata per tutte le chiamate alle classi di
 * questo package <br>
 * <li> Implementa il <i>design pattern</i> <b>Strategy Pattern</b> <br>
 * <li> Incapsula in una classe di questo package l'algoritmo che un
 * <CODE>Container</CODE> usa per organizzare i propri componenti <br>
 * <li> Estende le funzionalita' di <code>LayoutManager2</code> <br>
 * </ul>
 *
 * @author Alberto Colombo
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @version 1.0  /  21 dicembre 2003 ore 17.08
 */
public interface Layout extends LayoutManager2 {

    /**
     * Codifica dell'orientamento orizzontale
     */
    public static final int ORIENTAMENTO_ORIZZONTALE = 1;

    /**
     * Codifica dell'orientamento verticale
     */
    public static final int ORIENTAMENTO_VERTICALE = 2;

    /**
     * Codifica del tipo di allineamento
     * Allineamento in alto
     */
    public static final int ALLINEA_ALTO = 1;

    /**
     * Codifica del tipo di allineamento
     * Allineamento a sinistra
     * (sinonimo di ALLINEA_ALTO)
     */
    public static final int ALLINEA_SX = ALLINEA_ALTO;

    /**
     * Codifica del tipo di allineamento
     * Allineamento in basso
     */
    public static final int ALLINEA_BASSO = 2;

    /**
     * Codifica del tipo di allineamento
     * Allineamento a destra
     * (sinonimo di ALLINEA_BASSO)
     */
    public static final int ALLINEA_DX = ALLINEA_BASSO;

    /**
     * Codifica del tipo di allineamento
     * Allineamento centrato
     */
    public static final int ALLINEA_CENTRO = 3;

    /**
     * Codifica del tipo di allineamento
     * usa l'allineamento specifico impostato nel componente
     * (setAlignmentX(), setAlignmentY())
     */
    public static final int ALLINEA_DA_COMPONENTI = 4;

    /**
     * valore di default flag scorrevole abilitato
     */
    public static final boolean USA_SCORREVOLE = false;

    /**
     * valore di default per l'uso del bordo nello scorrevole
     */
    public static final boolean USA_BORDO_SCORREVOLE = false;

    /**
     * valore di default per l'uso del gap fisso
     */
    public static final boolean USA_GAP_FISSO = false;

    /**
     * distanza preferita tra gli oggetti (default)
     */
    public static final int GAP_PREFERITO = 20;

    /**
     * distanza minima tra gli oggetti (default)
     */
    public static final int GAP_MINIMO = 5;

    /**
     * distanza massima tra gli oggetti (default)
     */
    public static final int GAP_MASSIMO = 40;

    /**
     * flag - regola il ridimensionamento dei componenti
     * nel verso parallelo al layout.
     * true - ridimensiona i componenti rispettando il minimo e il massimo.
     * false - mantiene fissa la dimensione dei componenti (usa la dimensione preferita)
     */
    public boolean RIDIMENSIONA_PARALLELO = true;

    /**
     * flag - regola il ridimensionamento dei componenti
     * nel verso perpendicolare al layout.
     * true - ridimensiona i componenti rispettando il minimo e il massimo.
     * false - mantiene fissa la dimensione dei componenti (usa la dimensione preferita)
     */
    public boolean RIDIMENSIONA_PERPENDICOLARE = true;


    /**
     * codifica delle posizioni fisse per gli oggetti all'interno del pannelloCampo
     */
    public static final int CENTRO = 825;

    public static final int SINISTRA = 826;

    public static final int SOPRA = 827;

    public static final int DESTRA = 828;

    public static final int SOTTO = 829;

//    /**
//     * Regolazioni dinamiche dell'oggetto.
//     * <p/>
//     * Regola le caratteristiche dinamiche in base alla impostazione
//     * corrente delle caratteristiche statiche <br>
//     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
//     * Viene eseguito una sola volta <br>
//     * Sovrascritto nelle sottoclassi <br>
//     */
//    public abstract void inizializza() ;


    /**
     * Ritorna il layout di riferimento usato internamente
     * <p/>
     *
     * @return il layout di riferimento
     */
    public abstract LayoutManager2 getLayoutRef();


    /**
     * Attiva o disattiva l'uso del bordo nello scorrevole.
     * <p/>
     *
     * @param flag per attivare/disattivare l'uso del bordo.
     */
    public abstract void setScorrevoleBordato(boolean flag);


    /**
     * Abilita l'uso dello scorrevole.
     * <p/>
     *
     * @param flag true per abilitare l'uso dello scorrevole
     */
    public abstract void setUsaScorrevole(boolean flag);


    /**
     * Considera anche i componenti invisibili
     * disegnando il contenitore.
     * <p/>
     *
     * @param flag true per considerare anche i componenti invisibili
     */
    public abstract void setConsideraComponentiInvisibili(boolean flag);


    public abstract void setLarghezzaEsterna(boolean larghezzaEsterna);


    public abstract void setAltezzaEsterna(boolean altezzaEsterna);


    /**
     * Abilita l'uso del gap fisso.
     * <p/>
     *
     * @param usaGapFisso true per usare il gap fisso, false per il gap variabile
     */
    public abstract void setUsaGapFisso(boolean usaGapFisso);


    /**
     * Regola il gap preferito.
     * <p/>
     * Se si usa il gap fisso, questo e' il gap che viene usato. <br>
     *
     * @param gapPreferito il gap preferito (o fisso)
     */
    public abstract void setGapPreferito(int gapPreferito);


    /**
     * Regola il gap minimo.
     * <p/>
     * Significativo solo se si usa il gap variabile. <br>
     *
     * @param gapMinimo il gap minimo
     */
    public abstract void setGapMinimo(int gapMinimo);


    /**
     * Regola il gap massimo.
     * <p/>
     * Significativo solo se si usa il gap variabile. <br>
     *
     * @param gapMassimo il gap massimo
     */
    public abstract void setGapMassimo(int gapMassimo);


    /**
     * Attiva o disattiva il ridimensionamento dei componenti
     * in entrambi i versi del layout.
     * <p/>
     * Se il ridimensionamento e' attivo ridimensiona i componenti rispettando
     * la dimensione preferita, minima e massima. <br>
     * Se il ridimensionamento non e' attivo visualizza i componenti alla
     * loro dimensione preferita. <br>
     *
     * @param flag per attivare o disattivare il ridimensionamento
     */
    public abstract void setRidimensionaComponenti(boolean flag);


    /**
     * Attiva o disattiva il ridimensionamento dei componenti
     * nel verso parallelo al layout.
     * <p/>
     * Se il ridimensionamento e' attivo ridimensiona i componenti rispettando
     * la dimensione preferita, minima e massima. <br>
     * Se il ridimensionamento non e' attivo visualizza i componenti alla
     * loro dimensione preferita. <br>
     *
     * @param flag per attivare o disattivare il ridimensionamento parallelo
     */
    public abstract void setRidimensionaParallelo(boolean flag);


    /**
     * Attiva o disattiva il ridimensionamento dei componenti
     * nel verso perpendicolare al layout.
     * <p/>
     * Se il ridimensionamento e' attivo ridimensiona i componenti rispettando
     * la dimensione preferita, minima e massima. <br>
     * Se il ridimensionamento non e' attivo visualizza i componenti alla
     * loro dimensione preferita. <br>
     *
     * @param flag per attivare o disattivare il ridimensionamento perpendicolare
     */
    public abstract void setRidimensionaPerpendicolare(boolean flag);


    /**
     * Regola il tipo di allineamento dei componenti nel verso
     * perpendicolare a quello del layout.
     * <p/>
     *
     * @param allineamento il codice dell'allineamento
     * puo' essere ALLINEA_ALTO, ALLINEA_SX, ALLINEA_BASSO, ALLINEA_DX,
     * ALLINEA_CENTRO, ALLINEA_DA_COMPONENTI
     *
     * @see Layout
     */
    public abstract void setAllineamento(int allineamento);


    /**
     * Crea un nuovo filler in base alle impostazioni correnti del layout.
     * <p/>
     *
     * @return il filler appena creato
     */
    public abstract Component getNewFiller();


    /**
     * Ritorna lo stato del flag di regolazione dei componenti.
     * <p/>
     * Normalmente il layout regola automaticamente le dimensioni e l'allineamento
     * del componente quando viene aggiunto. <br>
     * Questa funzione puo' essere disattivata agendo sul flag <br>
     *
     * @return lo stato del flag di regolazione dei componenti in entrata
     */
    public abstract boolean isRegolaComponenti();


    /**
     * Flag - indica se regolare le carateristiche del componente
     * quando viene aggiunto al contenitore.
     * <p/>
     * Normalmente true, viene disattivato solo se si vuole aggiungere
     * un componente mantenendo le regolazioni originali.
     *
     * @param flag true per attivare la regolazione dei
     * componenti, false per disattivarla
     */
    public abstract void setRegolaComponenti(boolean flag);


}// fine della interfaccia