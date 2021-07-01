/**
 * Title:     Dati
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-ott-2004
 */
package it.algos.base.database.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.wrapper.CampoValore;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Interfaccia per il contenitore dei dati.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 21-ott-2004 ore 15.49.16
 */
public interface Dati extends TableModel {

    /**
     * codifica di posizione assoluta di un record nei dati
     * prima posizione
     */
    public static final int POSIZIONE_PRIMO = 1;

    /**
     * codifica di posizione assoluta di un record nei dati
     * - posizione intermedia
     */
    public static final int POSIZIONE_INTERMEDIO = 2;

    /**
     * codifica di posizione assoluta di un record nei dati
     * - ultima posizione
     */
    public static final int POSIZIONE_ULTIMO = 3;

    /**
     * codifica di posizione assoluta di un record nei dati
     * - non posizionato (assente)
     */
    public static final int POSIZIONE_ASSENTE = 4;


    /**
     * Metodi dichiarati in TableModel.
     * devono essere implementati obbligatoriamente
     * nella classe DatiBase o in tutte le sottoclassi concrete.<br>
     * Si potrebbe evitare di ridichiararli in questa interfaccia
     * ma lo facciamo per chiarezza.
     * <p/>
     * Adds a listener to the list that is notified each time
     * a change to the data model occurs.
     */

    /**
     * Adds a listener to the list that is notified each time
     * a change to the data model occurs.
     */
    void addTableModelListener(TableModelListener l);


    /**
     * Returns the most specific superclass for all the cell
     * values in the column.
     */
    Class<?> getColumnClass(int columnIndex);


    /**
     * Returns the number of columns in the model
     */
    int getColumnCount();


    /**
     * Returns the name of the column at columnIndex.
     */
    String getColumnName(int columnIndex);


    /**
     * Returns the number of rows in the model.
     */
    int getRowCount();


    /**
     * Returns the value for the cell at columnIndex and rowIndex.
     */
    Object getValueAt(int rowIndex, int columnIndex);


    /**
     * Returns true if the cell at rowIndex and columnIndex is editable.
     */
    boolean isCellEditable(int rowIndex, int columnIndex);


    /**
     * Removes a listener from the list that is notified each
     * time a change to the data model occurs.
     */
    void removeTableModelListener(TableModelListener l);


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract Object getValueAt(int riga, Campo campo);


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract Object getValueAt(int riga, String nome);


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract Object getValueAt(Campo campo);


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract Object getValueAt(String nome);


    /**
     * Ritorna il valore intero di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param colonna indice di colonna della cella richiesta (0 per la prima)
     *
     * @return il valore dell'intero nella cella specificata
     */
    public abstract int getIntAt(int riga, int colonna);


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract int getIntAt(int riga, Campo campo);


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract int getIntAt(int riga, String nome);


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract int getIntAt(Campo campo);


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract int getIntAt(String nome);


    /**
     * Ritorna il valore double di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param colonna indice di colonna della cella richiesta (0 per la prima)
     *
     * @return il valore dell'intero nella cella specificata
     */
    public abstract double getDoubleAt(int riga, int colonna);


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract double getDoubleAt(int riga, Campo campo);


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract double getDoubleAt(int riga, String nome);


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract double getDoubleAt(Campo campo);


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract double getDoubleAt(String nome);


    /**
     * Ritorna il valore data di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param colonna indice di colonna della cella richiesta (0 per la prima)
     *
     * @return il valore data nella cella specificata
     */
    public abstract Date getDataAt(int riga, int colonna);


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract Date getDataAt(int riga, Campo campo);


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract Date getDataAt(int riga, String nome);


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract Date getDataAt(Campo campo);


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract Date getDataAt(String nome);


    /**
     * Ritorna il valore stringa di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param colonna indice di colonna della cella richiesta (0 per la prima)
     *
     * @return il valore della stringa nella cella specificata
     */
    public abstract String getStringAt(int riga, int colonna);


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract String getStringAt(int riga, Campo campo);


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract String getStringAt(int riga, String nome);


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract String getStringAt(Campo campo);


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract String getStringAt(String nome);


    /**
     * Ritorna il valore booleano di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param colonna indice di colonna della cella richiesta (0 per la prima)
     *
     * @return il valore del booleano nella cella specificata
     */
    public abstract boolean getBoolAt(int riga, int colonna);


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract boolean getBoolAt(int riga, Campo campo);


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract boolean getBoolAt(int riga, String nome);


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract boolean getBoolAt(Campo campo);


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public abstract boolean getBoolAt(String nome);


    /**
     * Sets the value in the cell at columnIndex and rowIndex to aValue.
     */
    void setValueAt(Object aValue, int rowIndex, int columnIndex);


    /**
     * Assegna un valore a una cella.
     * <p/>
     *
     * @param riga l'indice della riga, 0 per la prima
     * @param campo corrispondente alla colonna
     * @param valore da assegnare
     */
    public abstract void setValueAt(int riga, Campo campo, Object valore);

//    /**
//     * Assegna un valore a una cella.
//     * <p/>
//     * @param valore da assegnare
//     * @param riga l'indice della riga, 0 per la prima
//     * @param colonna l'indice della colonna, 0 per la prima
//     */
//    public abstract void setValueAt(Object valore, int riga, int colonna);


    /**
     * Ritorna il valore di una cella.
     * <p/>
     * Il valore e' rappresentato a livello Archivio.
     * Implementato in tutte le sottoclassi concrete.<br>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param colonna indice di colonna della cella richiesta (0 per la prima)
     *
     * @return il valore dell'oggetto nella cella specificata
     */
    public abstract Object getDbValueAt(int riga, int colonna);


    /**
     * Resituisce la posizione assoluta di una chiave all'interno dei dati.
     * <p/>
     *
     * @param codice il codice del record del quale individuare la posizione
     *
     * @return la posizione assoluta della chiave (0 per la prima, -1 se non trovato)
     */
    public abstract int getRigaChiave(int codice);


    /**
     * Resituisce la posizione di una chiave all'interno dei dati.
     * Implementato in tutte le sottoclassi concrete.<br>
     *
     * @param codice
     *
     * @return la posizione della chiave (vedi codifica in Dati)
     */
    public abstract int posizioneRelativa(int codice);


    /**
     * Restituisce la codifica di posizione del record corrente nell'ambito del result set.
     *
     * @return la posizione (vedi codifica in Dati)
     */
    public abstract int posizione();


    /**
     * Restituisce il codice-chiave di un record relativo ad un altro record.
     *
     * @param codice record di riferimento
     * @param spostamento codifica della posizione (vedi codifica in PortaleScheda)
     *
     * @return codice del record nella posizione relativa richiesta
     */
    public abstract int getCodiceRelativo(int codice, Dati.Spostamento spostamento);


    /**
     * Restituisce il campo corrispondente a una colonna.
     * <p/>
     *
     * @param colonna l'indice della colonna (0 per la prima)
     *
     * @return il campo corrispondente
     */
    public abstract Campo getCampo(int colonna);


    /**
     * Restituisce l'elenco ordinato dei campi corrispondenti alle colonne.
     * <p/>
     *
     * @return l'elenco ordinato dei campi corrispondenti alle colonne
     */
    public abstract ArrayList<Campo> getCampi();


    /**
     * Restituisce la colonna corrispondente a un campo.
     * <p/>
     *
     * @param campo il campo da cercare
     *
     * @return l'indice della colonna corrispondente
     *         (0 per la prima, -1 se non trovata)
     */
    public abstract int getColonna(Campo campo);


    /**
     * Restituisce i valori per una singola colonna dei dati.
     * <p/>
     *
     * @param indice l'indice della colonna (0 per la prima)
     *
     * @return una ArrayList contenente gli oggetti valore
     */
    public abstract ArrayList getValoriColonna(int indice);


    /**
     * Restituisce la colonna dei valori per una singolo campo.
     * <p/>
     *
     * @param campo il campo da cercare
     *
     * @return una ArrayList contenente gli oggetti valore
     */
    public abstract ArrayList getValoriColonna(Campo campo);


    /**
     * Restituisce i valori per una singola riga dei dati.
     * <p/>
     *
     * @param indice l'indice della riga (0 per la prima)
     *
     * @return una ArrayList contenente gli oggetti valore
     */
    public abstract ArrayList<Object> getValoriRiga(int indice);


    /**
     * Restituisce i CampiValore per una singola riga dei dati.
     * <p/>
     *
     * @param indice l'indice della riga (0 per la prima)
     *
     * @return una ArrayList contenente gli oggetti CampoValore
     */
    public abstract ArrayList<CampoValore> getCampiValore(int indice);


    /**
     * Restituisce i valori stringa per una riga dei dati.
     * <p/>
     *
     * @param indice l'indice della riga (0 per la prima)
     *
     * @return una ArrayList contenente gli oggetti stringa
     */
    public abstract ArrayList<String> getStringheRiga(int indice);


    /**
     * Restituisce la mappa di un record.
     * <p/>
     *
     * @return una ArrayList contenente gli oggetti stringa
     */
    public abstract LinkedHashMap<String, String> getStrRecord();


    /**
     * Restituisce la mappa di un record.
     * <p/>
     *
     * @param riga del result set
     *
     * @return una ArrayList contenente gli oggetti stringa
     */
    public abstract LinkedHashMap<String, String> getStrRecord(int riga);


    /**
     * Restituisce una stringa di una singola riga dei dati.
     * <p/>
     *
     * @param indice l'indice della riga (0 per la prima)
     * @param delim il carattere delimitatore di campo
     *
     * @return una stringa per la singola riga
     */
    public abstract String getStringaRiga(int indice, char delim);


    /**
     * Restituisce una stringa di una singola riga dei dati.
     * <p/>
     *
     * @param indice l'indice della riga (0 per la prima)
     * @param delim la stringa delimitatore di campo
     *
     * @return una stringa per la singola riga
     */
    public abstract String getStringaRiga(int indice, String delim);


    /**
     * Ritorna la posizione della colonna contenente le chiavi.
     * <p/>
     *
     * @return la posizione della colonna delle chiavi (0 per la prima)
     */
    public abstract int getColonnaChiavi();


    /**
     * Chiude l'oggetto dati e libera le risorse allocate.<p>
     * <p/>
     */
    public abstract void close();


    /**
     * Codifica per lo spostamento verso un record nei dati.
     */
    public enum Spostamento {

        primo(),       //spostamento al primo record
        precedente(),  //spostamento al record precedente (relativamente a un altro record)
        successivo(),  //spostamento al record successivo (relativamente a un altro record)
        ultimo()       //spostamento all'ultimo record

    }// fine della classe

}// fine della classe
