/**
 * Title:     NumberDocument
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      22-mag-2007
 */
package it.algos.base.campo.document;

import it.algos.base.libreria.Lib;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Estensione di PlainDocument per l'inserimento di numeri
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 22-mag-2007 ore 10.20.02
 */
public final class NumberDocument extends PlainDocument {

    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

        char[] source = str.toCharArray();
        char[] result = new char[source.length];
        int j = 0;

        for (int i = 0; i < result.length; i++) {
            if (Character.isDigit(source[i])) {
                result[j++] = source[i];
            } else {
                Lib.Sist.beep();
                System.err.println("insertString: " + source[i]);
            }
        }
        super.insertString(offs, new String(result, 0, j), a);
    }
}// fine della classe
