package it.algos.albergo.odg.odgrisorse;

public class WrapRiga {

	private int numRisorsa = 0;
	private String cameraFermata = "";
	private String clienteFermata = "";
	private String dettagliFermata = "";
	private String noteFermata = "";
	private String cameraPartenza = "";
	private String clientePartenza = "";
	private String dettagliPartenza = "";
	private String notePartenza = "";
	private String cameraArrivo = "";
	private String clienteArrivo = "";
	private String dettagliArrivo = "";
	private String noteArrivo = "";

	/**
	 * Costruttore.
	 * 
	 * @param numRisorsa
	 *            il numero della risorsa rappresentata (numero, non id!)
	 */
	public WrapRiga(int numRisorsa) {
		super();
		this.numRisorsa = numRisorsa;
	}

	public int getAltezza() {
		return 54;
	}
	
	public String getStringaNumero() {
		return "" + numRisorsa;
	}

	

	public void setCameraFermata(String cameraFermata) {
		this.cameraFermata = cameraFermata;
	}

	public void setClienteFermata(String clienteFermata) {
		this.clienteFermata = clienteFermata;
	}

	public void setDettagliFermata(String dettagliFermata) {
		this.dettagliFermata = dettagliFermata;
	}

	public void setNoteFermata(String noteFermata) {
		this.noteFermata = noteFermata;
	}

	
	public void setCameraPartenza(String cameraPartenza) {
		this.cameraPartenza = cameraPartenza;
	}

	public void setClientePartenza(String clientePartenza) {
		this.clientePartenza = clientePartenza;
	}
	
	public void setDettagliPartenza(String dettagliPartenza) {
		this.dettagliPartenza = dettagliPartenza;
	}

	public void setNotePartenza(String notePartenza) {
		this.notePartenza = notePartenza;
	}
	

	public void setCameraArrivo(String cameraArrivo) {
		this.cameraArrivo = cameraArrivo;
	}

	public void setClienteArrivo(String clienteArrivo) {
		this.clienteArrivo = clienteArrivo;
	}

	public void setDettagliArrivo(String dettagliArrivo) {
		this.dettagliArrivo = dettagliArrivo;
	}

	public void setNoteArrivo(String noteArrivo) {
		this.noteArrivo = noteArrivo;
	}
	
	
	

	public String getStringaArrivo() {
		String stringa = cameraArrivo + " " + clienteArrivo;
		return stringa;
	}
	
	public String getDettagliArrivo() {
		return dettagliArrivo;
	}
	
	public String getNoteArrivo() {
		return noteArrivo;
	}



	public String getStringaPartenza() {
		String stringa = cameraPartenza + " " + clientePartenza;
		return stringa;
	}
	
	public String getDettagliPartenza() {
		return dettagliPartenza;
	}
	
	public String getNotePartenza() {
		return notePartenza;
	}

	

	public String getStringaFermata() {
		String stringa = cameraFermata + " " + clienteFermata;
		return stringa;
	}
	
	public String getDettagliFermata() {
		return dettagliFermata;
	}
	
	public String getNoteFermata() {
		return noteFermata;
	}

	

}
