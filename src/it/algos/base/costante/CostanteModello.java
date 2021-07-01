/**
 * Title:        CostanteModello.java
 * Package:      it.algos.base.costante
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 29 settembre 2002 alle 22.10
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa interfaccia e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.costante;

import it.algos.base.modello.Modello;

//-----------------------------------------------------------------------------
// Classe principale
//-----------------------------------------------------------------------------


/**
 * Interfaccia per le costanti di definizione dei campi<br>
 * <p/>
 * Ogni costante indica il tipo di dato registrato nel database ed il
 * tipo di dato per la rappresentazione grafica nella GUI
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  29 settembre 2002 ore 22.10
 */
public interface CostanteModello {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    // Costanti per le dimensioni dei campi video
    //--------------------------------------------------------------------------
    /**
     * larghezza di default dei campi (modificabile in ogni campo)
     */
    public static final int LARGHEZZA_CAMPO_DEFAULT = 150;

    /**
     * altezza di default dei campi (modificabile in ogni campo)
     */
    public static final int ALTEZZA_CAMPO_DEFAULT = 20;

    /**
     * larghezza di default della etichetta (modificabile in ogni campo)
     */
    public static final int LARGHEZZA_ETICHETTA_DEFAULT = 120;

    /**
     * altezza di default della etichetta (modificabile in ogni campo)
     */
    public static final int ALTEZZA_ETICHETTA_DEFAULT = 14;

    /**
     * altezza di default di una riga di testo
     */
    public static final int ALTEZZA_RIGA_DEFAULT = 19;

    //--------------------------------------------------------------------------
    // Diversi tipi tipoAlgos di oggetti ModelloCampo per il rapporto tavola/GUI
    //--------------------------------------------------------------------------
    /**
     * da varchar a testo JTextField
     */
    public static final int TESTO_TESTO = 1;

    /**
     * da varchar a testo JTextArea
     */
    public static final int TESTO_AREA_TESTO = 2;

    /**
     * da integer a testo JTextField
     */
    public static final int INTERO_TESTO = 3;

    /**
     * da float a testo JTextField
     */
    public static final int EURO_TESTO = 4;

    /**
     * da integer a lista di valori JList
     */
    public static final int INTERO_LISTA = 5;

    /**
     * da big integer a testo JTextField
     */
    public static final int BIG_INTERO_TESTO = 6;

    /**
     * da data a testo JTextField
     */
    public static final int DATA_TESTO = 7;

    /**
     * da ora a testo JTextField
     */
    public static final int ORA_TESTO = 8;

    /**
     * da data/ora a testo JTextField
     */
    public static final int TIMESTAMP_TESTO = 9;

    /**
     * da integer a lista di valori JComboBox
     */
    public static final int LINK_COMBO = 10;

    /**
     * da booleano a controllo JCheckBox
     */
    public static final int BOOLEAN_CHECK = 11;

    /**
     * da booleano a controllo JRadioButton
     */
    public static final int BOOLEAN_RADIO = 12;

    /**
     * da intero a controllo ButtonGroup
     */
    public static final int INTERO_GRUPPO_RADIO = 13;

    /**
     * da nullo a componente etichetta JLabel<br>
     * non esiste il campo nella tavola principale<br>
     * il riferimento e' ad un altro oggetto-campo<br>
     */
    public static final int NULL_LABEL = 14;

    /**
     * da nullo a componente JTable<br>
     * non esiste campo di legame nella tavola principale<br>
     * il campo di legame e' nella tavola della sub lista<br>
     */
    public static final int NULL_SUB_LISTA = 15;

    /**
     * da intero a componente blocco indirizzo<br>
     * il campo di legame punta ad un record della tavola indirizzi<br>
     * viene visualizzato un componente con tutti i campi di indirizzi<br>
     */
    public static final int INTERO_INDIRIZZO = 16;

    /**
     * da qualsiasi a nullo<br>
     * indipendente dal tipo di campo nel database<br>
     * il campo non e' visibile in scheda video<br>
     */
    public static final int QUALSIASI_NULL = 17;

    //--------------------------------------------------------------------------
    // Costanti per i tipi di tabelle
    //--------------------------------------------------------------------------
    /**
     * tabella testo
     */
    public static final int TABELLA_SEMPLICE = 1;

    /**
     * tabella testo + testo
     */
    public static final int TABELLA_DOPPIA = 2;

    /**
     * tabella testo + link
     */
    public static final int TABELLA_LINK = 3;

    /**
     * tabella testo + testo + link
     */
    public static final int TABELLA_DOPPIA_LINK = 4;

    /**
     * tabella testo + reale
     */
    public static final int TABELLA_VALORE = 5;

    /**
     * tabella testo + booleano
     */
    public static final int TABELLA_LOGICA = 6;

    /**
     * tabella testo + booleano + link
     */
    public static final int TABELLA_LOGICA_LINK = 7;

    /**
     * tabella testo + testo + booleano + link
     */
    public static final int TABELLA_DOPPIA_LOGICA_LINK = 8;


    //--------------------------------------------------------------------------
    // Costanti per i nomi dei campi delle tabelle
    //--------------------------------------------------------------------------
    /**
     * nome del campo note presente in tutte le anagrafiche
     */
    public static final String NOME_CAMPO_NOTE = Modello.NOME_CAMPO_NOTE;


    //--------------------------------------------------------------------------
    // Costanti per i nomi dei campi delle tabelle
    //--------------------------------------------------------------------------
    /**
     * nome del campo sigla sempre presente nelle tabelle
     */
    public static final String NOME_CAMPO_SIGLA = Modello.NOME_CAMPO_SIGLA;

    /**
     * nome del campo descrizione sempre presente nelle tabelle
     */
    public static final String NOME_CAMPO_DESCRIZIONE = Modello.NOME_CAMPO_DESCRIZIONE;

    /**
     * nome del campo legame sempre presente nelle tabelle
     */
    public static final String NOME_CAMPO_LINK = Modello.NOME_CAMPO_LINK;

    /**
     * nome del campo valore sempre presente nelle tabelle
     */
    public static final String NOME_CAMPO_VALORE = Modello.NOME_CAMPO_VALORE;

    /**
     * nome del campo logico sempre presente nelle tabelle
     */
    public static final String NOME_CAMPO_LOGICO = Modello.NOME_CAMPO_LOGICO;

    /**
     * nome del campo logico sempre presente nelle tabelle
     */
    public static final String NOME_CAMPO_TESTO = Modello.NOME_CAMPO_TESTO;

    //--------------------------------------------------------------------------
    // Costanti per la posizione della etichetta rispetto al relativo campo
    //--------------------------------------------------------------------------
    /**
     * etichetta posizionata sopra il relativo campo a sinistra
     */
    public static final int ETICHETTA_SOPRA_SINISTRA = 1;

    /**
     * etichetta posizionata sopra il relativo campo a destra
     */
    public static final int ETICHETTA_SOPRA_DESTRA = 2;

    /**
     * etichetta posizionata a sinistra del campo, giustificata a sinistra
     */
    public static final int ETICHETTA_SINISTRA_GIUSTIFICATA_SINISTRA = 3;

    /**
     * etichetta posizionata a sinistra del campo, giustificata al centro
     */
    public static final int ETICHETTA_SINISTRA_GIUSTIFICATA_CENTRO = 4;

    /**
     * etichetta posizionata a sinistra del campo, giustificata a destra
     */
    public static final int ETICHETTA_SINISTRA_GIUSTIFICATA_DESTRA = 5;

    /**
     * etichetta posizionata a destra del campo, giustificata a sinistra
     */
    public static final int ETICHETTA_DESTRA_GIUSTIFICATA_SINISTRA = 6;
    //-------------------------------------------------------------------------
}// fine della interfaccia it.algos.base.costante.CostanteModello.java

//-----------------------------------------------------------------------------

