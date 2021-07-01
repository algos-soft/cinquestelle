package it.algos.albergo.tableau;

import it.algos.base.modulo.OnEditingFinished;

import java.util.ArrayList;
import java.util.Date;

/**
 * Interfaccia da implementare da parte di un 
 * oggetto in grado di fornire dati a un Tableau.
 * A questo oggetto viene richiesto di fornire le 
 * celle dei periodi e le celle delle risorse.
 *
 * @author alex
 * @date mag-2014
 */
public interface TableauDatamodel {


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
    public ArrayList<CellPeriodoIF> getCellePeriodo(int idTipoRisorsa, Date d1, Date d2);


    /**
     * Ritorna la cella di periodo relativa a un dato periodo.
     * <p/>
     * @param idTipoRisorsa id del tipo di risorsa
     * @param idRecordSorgente id del record sorgente
     * @return la cella del periodo
     */
    public CellPeriodoIF getCellaPeriodo(int idTipoRisorsa, int idRecordSorgente);


    /**
     * Ritorna l'elenco di tutti gli id di record sorgenti di cella contenuti
     * in una data prenotazione, relativi a un dato tipo di risorse.
     * <p/>
     * Nel caso di prenotazione di camere, ritorna gli id di periodo
     * Nel caso di prenotazione di risorse, ritorna gli id di RisorsaPeriodo di tutti i periodi
     * ecc...
     *
     * @param idPrenotazione l'id della prenotazione
     * @param tipoRisorse l'id del tipo di risorse correntemente selezionato
     * @return l'array con tutti gli id di record sorgenti contenuti nella prenotazione
     */
    public int[] getIdRecordSorgenti(int idPrenotazione, int tipoRisorse);


    /**
     * Apre per modificare una prenotazione in scheda.
     * <p/>
     * @param codPrenotazione il codice della prenotazione
     * @param listener listener da notificare alla chiusura della prenotazione
     */
    public void apriPrenotazione(int codPrenotazione, OnEditingFinished listener);

    /**
     * Ritorna il codice cliente relativo a un periodo.
     * <p/>
     * @param codPeriodo il codice del periodo
     * @return il codice del relativo cliente
     */
    public int getCodCliente(int codPeriodo);


    /**
     * Ritorna la lista ordinata delle celle di risorsa 
     * @param codTipo il tipo di celle da creare
     * @return la lista delle celle
     */
	public ArrayList<CellRisorsaIF> getCelleRisorsa(int codTipo);

    /**
     * Ritorna un elenco ordinato dei tipi di risorsa supportati.
     * <br>Questi tipi vengono mostrati nel popup.
     * <br>Quando cambia il tipo, le callback al modello riportano l'id del tipo selezionato. 
     * @return l'elenco dei tipi di risorsa supportati
     */
	public TipoRisorsaTableau[] getTipiRisorsa();

}
