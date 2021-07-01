/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      15-gen-2005
 */
package it.algos.base.database.util;


/**
 * FunzioneBase.
 * </p>
 * Questa classe astratta: <ul>
 * Descrive il tipo di dati per una funzione del database.
 * </ul>
 * <p/>
 * Tipo di dati in uscita:<br>
 * una Funzione puo' avere un tipo di dati in uscita diverso dal tipo del
 * campo al quale e' applicata.<br>
 * Per esempio, la funzione COUNT su un campo TEXT produce un numero.<br>
 * In generale, una funzione puo' produrre dati:
 * - dello stesso tipo del campo al quale e' applicata.<br>
 * - di un tipo specifico.<br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 15-gen-2005 ore 18.14.07
 */
public abstract class FunzioneBase extends Object implements Funzione {


    /**
     * Costruttore completo senza parametri.
     * <p/>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public FunzioneBase() {
        /* rimanda al costruttore della superclasse */
        super();
    }// fine del metodo costruttore completo


}// fine della classe
