package it.algos.albergo.tableau;

import java.util.Date;

/**
 * Cella di tipo Periodo di Risorsa.
 * </p>
 */
public class CellPeriodoRisorsa extends CellPeriodoAbs {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param idRisorsa id della risorsa impegnata
     * @param idSorgente id del record sorgente per questa cella
     * @param idPeriodo id del periodo di prenotazione
     * @param cliente nome del cliente
     * @param camera nome della camera relativa al periodo di riferimento
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
     */
    public CellPeriodoRisorsa(
            int idRisorsa,
            int idSorgente,
            int idPeriodo,
    		String cliente,
            String camera,
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
            String note) {

        super(idRisorsa); // non ha ancora lo UserObject

        /* inserisce i dati specifici nelo UserObject */
        UserObjectPeriodo uo = getUO();
        uo.put(UserObjectPeriodo.KEY_CLIENTE, cliente);
        uo.put(UserObjectPeriodo.KEY_IDSORGENTE, idSorgente);                
        uo.put(UserObjectPeriodo.KEY_CODPERIODO, idPeriodo);                
        uo.put(UserObjectPeriodo.KEY_NOME_CAMERA, camera);
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
        uo.put(UserObjectPeriodo.KEY_NOTE, note);                


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


	@Override
	public CompPeriodoIF creaComponente() {
		return new CompPeriodoRisorsa();
	}
	


	
}
