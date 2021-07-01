/**
 * Package:      it.algos.base.campo.dati
 * Description:
 * Copyright:    Copyright (c) 2005
 * Company:      Algos s.r.l.
 */
package it.algos.base.campo.dati;

import java.text.Format;

/**
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @version 1.0  /  2 dic 2005
 */
public interface CampoFormat {


    /**
     * Crea un oggetto Format da assegnare al campo.
     * <p/>
     * Invocato dal cliclo Inizia.
     * Implementato nelle sottoclassi concrete.
     * Il metodo deve creare e ritornare il Format adeguato.
     * Se il metodo ritorna null non viene usato il format
     * e la conversione viene effettuata secondo le regole della superclasse
     *
     * @return l'oggetto Format
     */
    public abstract Format createFormat();


}