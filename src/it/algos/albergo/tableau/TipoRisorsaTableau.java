package it.algos.albergo.tableau;

import java.util.ArrayList;

import it.algos.albergo.tabelle.tipirisorsa.TipoRisorsa;
import it.algos.albergo.tabelle.tipirisorsa.TipoRisorsaModulo;
import it.algos.base.query.ordine.Ordine;

/**
 * Descrive i tipi di risorsa disponibili per il tableau (oltre alle camere)
 */
public class TipoRisorsaTableau {
	
	private int id;
	private String descrizione;

	public TipoRisorsaTableau(int id, String descrizione) {
		super();
		this.id = id;
		this.descrizione = descrizione;
	}

	/**
	 * @return array of DAOs in the default order
	 */
	public static TipoRisorsaTableau[] getAll(){
		ArrayList<TipoRisorsaTableau> lista = new ArrayList<TipoRisorsaTableau>();
		Ordine ordine = new Ordine(TipoRisorsaModulo.get().getCampoOrdine());
		int[] chiavi = TipoRisorsaModulo.get().query().valoriChiave(ordine);
		for(int chiave : chiavi){
			String desc = TipoRisorsaModulo.get().query().valoreStringa(TipoRisorsa.Cam.settore.get(), chiave);
			TipoRisorsaTableau dao = new TipoRisorsaTableau(chiave, desc);
			lista.add(dao);
		}
		return lista.toArray(new TipoRisorsaTableau[0]);
	}
	
	public int getId() {
		return id;
	}

	public String getDescrizione() {
		return descrizione;
	}


	@Override
	public String toString() {
		return getDescrizione();
	}
	

}
