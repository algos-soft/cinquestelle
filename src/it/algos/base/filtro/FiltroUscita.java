/**
 * Title:        FiltroUscita.java
 * Package:      it.algos.base.filtroAlb
 * Description:
 * Copyright:    __COPY__
 * Company:      __COMPANY__
 * @author __AUTORI__  /  albi
 * @version 1.0  /
 * Creato:       il 18 dicembre 2003 alle 10.26
 */
//-----------------------------------------------------------------------------
// __COPY__  __COMPANY__ All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.filtro;

import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe astratta e' responsabile di: <br>
 * - formattazione di oggetti in stringhe
 * - parsing delle stringhe in oggetti
 * - gestione errori di parsing/dati non validi
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  albi
 * @version 1.0  /  18 dicembre 2003 ore 10.26
 * @todo la gestione degli errori non l'ho neanche pensata :-(
 * @todo incomatibile coi filtri ingresso, quando questi impongono un
 * formato differente da quello di scrittura del filtroOld uscita sullo
 * stesso componente.
 */
public class FiltroUscita extends Format {

    /**
     * il valore minimo che l'oggetto restituito da parseObject può assumere.
     */
    protected Comparable valoreMinimo = null;

    /**
     * il valore massimo che l'oggetto restituito da parseObject può assumere.
     */
    protected Comparable valoreMassimo = null;

    /**
     * il formato utilizzato per la conversione da stringa a oggetto. Viene
     * anche utilizzato per la conversione da oggetto a stringa se non si
     * &egrave; definito un formato di scrittura.
     *
     * @see FiltroUscita#formatoScrittura
     */
    protected Format formato = null;

    /**
     * il formato utilizzato per la conversione da oggetto a stringa. Se non
     * &egrave; definito, usa quello di lettura.
     *
     * @see FiltroUscita#formato
     */
    protected Format formatoScrittura = null;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public FiltroUscita() {
    }


    /**
     * controlla che il valore sia tra il minimo e il massimo
     *
     * @param unValore l'oggetto di cui controllare che il valore sia
     * contenuto tra valoreMinimo e valoreMassimo
     *
     * @throws ClassCastException se gli oggetti in esame non permettono di
     *                            essere confrontati
     * @returns <code>true</code> se valoreMinimo<=unValore<=valoreMassimo,
     * <code>false</code> altrimenti
     * @see java.lang.Comparable
     * @see valoreMinimo, valoreMassimo
     */
    protected boolean rispettaMargini(Comparable unValore) throws ClassCastException {
        boolean retval = true;
        if (valoreMinimo != null && unValore.compareTo(valoreMinimo) < 0) {
            retval = false;
        }
        if (valoreMassimo != null && unValore.compareTo(valoreMassimo) > 0) {
            retval = false;
        }
        return retval;
    }


    /**
     * @see FiltroUscita#valoreMinimo
     */
    public void setValoreMinimo(Comparable unValore) {
        valoreMinimo = unValore;
    }


    /**
     * @see FiltroUscita#valoreMinimo
     */
    public Comparable getValoreMinimo() {
        return valoreMinimo;
    }


    /**
     * @see FiltroUscita#valoreMassimo
     */
    public void setValoreMassimo(Comparable unValore) {
        valoreMassimo = unValore;
    }


    /**
     * @see FiltroUscita#valoreMassimo
     */
    public Comparable getValoreMassimo() {
        return valoreMassimo;
    }


    /**
     * @see FiltroUscita#formato
     */
    public void setFormato(Format unFormato) {
        formato = unFormato;
    }


    /**
     * @see FiltroUscita#formato
     */
    public Format getFormato() {
        return formato;
    }


    /**
     * le sottoclassi possono sovrascrivere questo metodo, definito in
     * <code>Format</code>.
     *
     * @see java.text.Format#formatToCharacterIterator
     */
    public AttributedCharacterIterator formatToCharacterIterator(Object o) throws
            NullPointerException,
            IllegalArgumentException {
        Format f = getFormatoScrittura();
        return f.formatToCharacterIterator(o);
    }


    /**
     * Parsa una stringa e la trasforma in oggetto usando prima il formato di
     * lettura e poi, se questo fallisce, quello di scrittura (se &egrave;
     * definito).
     * Le sottoclassi possono sovrascrivere questo metodo, definito in
     * <code>Format</code>.
     *
     * @see java.text.Format#parseObject
     */
    public Object parseObject(String source, ParsePosition pos) throws NullPointerException {
        Object parsed = null;
        int index = pos.getIndex();
        parsed = formato.parseObject(source, pos);
        if (parsed == null && formatoScrittura != null) {
            parsed = formatoScrittura.parseObject(source, pos);
        }
        if ((parsed != null) && (!this.rispettaMargini((Comparable)parsed))) {
            pos.setErrorIndex(source.length());
            pos.setIndex(index);
            parsed = null;
        }
        return parsed;
    }


    /**
     * le sottoclassi possono sovrascrivere questo metodo, definito in
     * <code>Format</code>.
     *
     * @see java.text.Format#format
     */
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) throws
            IllegalArgumentException {
        Format f = getFormatoScrittura();
        return f.format(obj, toAppendTo, pos);
    }


    /**
     * Getter for property formatoScrittura. Se vale null, restituisce il
     * formato lettura.
     *
     * @return Value of property formatoScrittura.
     */
    public java.text.Format getFormatoScrittura() {
        return (formatoScrittura == null) ? formato : formatoScrittura;
    }


    /**
     * Setter for property formatoScrittura.
     *
     * @param formatoScrittura New value of property formatoScrittura.
     */
    public void setFormatoScrittura(java.text.Format formatoScrittura) {
        this.formatoScrittura = formatoScrittura;
    }

}// fine della classe it.algos.base.filtroAlb.FiltroUscita.java
//-----------------------------------------------------------------------------
