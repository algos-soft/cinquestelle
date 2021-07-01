/**
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      16-feb-2009
 */
package it.algos.albergo.tableau;

import java.util.ArrayList;
import java.util.Date;

/**
 * Interfaccia da implementare da parte di un oggetto in grado
 * di fornire una lista di celle di periodo al Tableau.
 * Una istanza di un oggetto di questa interfaccia è registrato
 * nel Tableau.
 * A tale oggetto viene richiesto di creare e fornire le celle.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 16-feb-2009 ore 8.29.49
 */
public interface ProviderCellePeriodo {

    /**
     * Ritorna un elenco di celle di periodo corrispondente
     * a un intervallo di date fornito
     * <p/>
     *
     * @param d1 data iniziale
     * @param d2 data finale
     *
     * @return la lista delle celle
     */
    public ArrayList<CellPeriodoIF> getCellePeriodo(Date d1, Date d2);


    /**
     * Ritorna la cella di periodo relativa a un dato periodo.
     * <p/>
     * @param codPeriodo il codice del periodo
     * @return la cella del periodo
     */
    public CellPeriodoIF getCellaPeriodo(int codPeriodo);


    /**
     * Ritorna l'elenco di tutti i codici di periodo contenuti
     * nella prenotazione proprietaria di un dato periodo.
     * <p/>
     * @param codPeriodo il codice periodo
     * @return l'array con tutti i codici periodo contenuti nella prenotazione
     */
    public int[] getPeriodiFratelli(int codPeriodo);


    /**
     * Apre per modifica la prenotazione relativa a un periodo.
     * <p/>
     * @param codPeriodo il codice del periodo
     * @return true se la prenotazione è stata modificata
     */
    public boolean apriPrenotazione(int codPeriodo);

    /**
     * Ritorna il codice cliente relativo a un periodo.
     * <p/>
     * @param codPeriodo il codice del periodo
     * @return il codice del relativo cliente
     */
    public int getCodCliente(int codPeriodo);



}// fine della interfaccia