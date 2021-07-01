package it.algos.albergo.tableau;

import it.algos.base.errore.Errore;

import java.util.Date;

/**
 * Cella di tipo Periodo di Camera.
 * </p>
 */
public class CellPeriodoCamera extends CellPeriodoAbs {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param cliente nome del cliente
     * @param agenzia nome agenzia
     * @param idCamera id camera prenotata
     * @param camProvenienza codice camera di provenienza se entra con cambio
     * @param camDestinazione codice camera di destinazione se esce con cambio
     * @param dataInizio inizio periodo
     * @param dataFine fine periodo
     * @param opzione prenotazione opzione
     * @param confermata prenotazione confermata
     * @param dataScadenza data di scadenza prenotazione
     * @param arrivato periodo arrivato
     * @param partito periodo partito
     * @param numAd numero di adulti prenotati
     * @param numBa numero di bambini prenotati
     * @param trattamento del cliente
     * @param preparazione preparazione della stanza
     * @param codPeriodo codice periodo di riferimento sul db
     */
    public CellPeriodoCamera(
            int idCamera,
            String cliente,
            String agenzia,
            int camProvenienza,
            int camDestinazione,
            Date dataInizio,
            Date dataFine,
            boolean opzione,
            boolean confermata,
            Date dataScadenza,
            boolean arrivato,
            boolean partito,
            int numAd,
            int numBa,
            String trattamento,
            String preparazione,
            int codPeriodo) {

        super(idCamera); // non ha ancora lo UserObject

        /* inserisce i dati specifici nelo UserObject */
        UserObjectPeriodo uo = getUO();
        uo.put(UserObjectPeriodo.KEY_CLIENTE, cliente);
        uo.put(UserObjectPeriodo.KEY_AGENZIA, agenzia);
        uo.put(UserObjectPeriodo.KEY_NOME_CAMERA, idCamera);
        uo.put(UserObjectPeriodo.KEY_PROVENIENZA, camProvenienza);
        uo.put(UserObjectPeriodo.KEY_DESTINAZIONE, camDestinazione);        
        uo.put(UserObjectPeriodo.KEY_DATAINIZIO, dataInizio);        
        uo.put(UserObjectPeriodo.KEY_DATAFINE, dataFine);                
        uo.put(UserObjectPeriodo.KEY_OPZIONE, opzione);                
        uo.put(UserObjectPeriodo.KEY_CONFERMATA, confermata);                
        uo.put(UserObjectPeriodo.KEY_DATASCADENZA, dataScadenza);                
        uo.put(UserObjectPeriodo.KEY_ARRIVATO, arrivato);                
        uo.put(UserObjectPeriodo.KEY_PARTITO, partito);                
        uo.put(UserObjectPeriodo.KEY_NUMAD, numAd);                
        uo.put(UserObjectPeriodo.KEY_NUMBA, numBa);                
        uo.put(UserObjectPeriodo.KEY_TRATTAMENTO, trattamento);                
        uo.put(UserObjectPeriodo.KEY_PREPARAZIONE, preparazione);                
        uo.put(UserObjectPeriodo.KEY_CODPERIODO, codPeriodo);        
        uo.put(UserObjectPeriodo.KEY_IDSORGENTE, codPeriodo);                


        this.inizia();

    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     */
    private void inizia() {
    }


    /**
     * Ritorna il codice periodo di questa cella.
     * <p/>
     *
     * @return il codice periodo
     */
    public int getCodicePeriodo() {
        return getUO().getInt(UserObjectPeriodo.KEY_CODPERIODO);
    }
    
    
	/**
	 * Crea il componente video (memorizzato staticamente)
	 */
	public CompPeriodoIF creaComponente(){
		return new CompPeriodoCamera();
	}
	
	
	
} // fine della classe
