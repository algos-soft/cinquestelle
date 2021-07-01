/**
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      16-feb-2009
 */
package it.algos.albergo.sviluppopresenze;

import java.util.Date;

/**
 * Interfaccia per le tipologie di suddivisione dello sviluppo prenotazioni.
 * Nuove suddivisioni si possono implementare subclassando SuddivisioneBase
 * e implementando i metodi necessari
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 16-feb-2009 ore 8.29.49
 */
interface Suddivisione {

    /**
     * Crea una lista di elementi suddivisi per questo tipo di suddivisione.
     * <p/>
     * @param estremita eventuali estremità della suddivisione (non necessariamente utilizzato)
     * @return un array degli elementi suddivisi, ogni elemento con [chiave - sigla - descrizione]
     */
    public WrapSuddivisione[] creaSuddivisione(Object estremita);

    /**
     * Recupera la chiave di suddivisione relativa a un periodo.
     * <p/>
     * Indica in quale elemento di suddivisione il periodo ricade.
     * Significativo solo per suddivisioni di tipo non temporale.
     * @param codPeriodo il codice del periodo
     * @return la chiave di suddivisione
     */
    public int getChiavePeriodo(int codPeriodo);

    /**
     * Recupera la chiave di suddivisione nella quale un dato giorno rientra
     * <p>
     * Significativo solo per suddivisioni di tipo temporale
     * @param data la data da analizzare
     * @return la chiave di mappa
     */
    public int getChiaveGiorno(Date data);

    /**
     * Ritorna true se è una suddivisione di tipo temporale
     *(giornalera, settimanale, mensile...)
     * <p>
     * @return true se temporale
     */
    public boolean isTemporale();


    /**
     * Ritorna il titolo della suddivisione da usare
     * nelle testate di lista e nei grafici
     * <p>
     * @return il titolo della suddivisione
     */
    public String getLabel();



}// fine della interfaccia