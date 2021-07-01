package it.algos.albergo.stampeobbligatorie.notifica;

import java.util.Date;
import java.util.HashMap;


/**
 * Esportazione delle notifiche di 1 giorno 
 * in formato Servizio Alloggiati PS
 */
public class NotificaExport extends ExportBase {
	
	
	/**
	 * Costruttore
	 * @param aGruppi array contenente i wrapper con i gruppi arrivati
	 * @param path la cartella di esportazione
	 * @param filename il nome del file di esportazione
	 * */
	public NotificaExport(WrapGruppoArrivato[] aGruppi, Date data, String path,
			String filename) {
		super(aGruppi, data, path, filename);
	}


	
	
	/**
	 * Crea le linee per un singolo cliente arrivato
	 * @param codCliente il codice del cliente
	 * @param codPeriodo l'id del periodo di riferimento
	 * @param capo true se capogruppo
	 * @param codPosizione codice posizione per ISTAT
	 * @return la linea per il cliente
	 */
	@Override
	public String createLine(int codCliente, int codPeriodo, TipoAlloggiato tipo, String codPosizione){
		
		// crea una mappa con le parti di riga
		HashMap<Parti, String> mappa = creaMappa(codCliente, codPeriodo, tipo, codPosizione);

		// costruzione linea
		String line="";
		line = mappa.get(Parti.tipoAlloggiato);
		line += mappa.get(Parti.dataArrivo);
		line += mappa.get(Parti.ggPermanenza);
		line+=mappa.get(Parti.cognome);
		line+=mappa.get(Parti.nome);
		line+=mappa.get(Parti.sesso);
		line+=mappa.get(Parti.dataNascita);
		line+=mappa.get(Parti.comuneNascita);
		line+=mappa.get(Parti.provinciaNascita);
		line+=mappa.get(Parti.statoNascita);
		line+=mappa.get(Parti.cittadinanza);
		line+=mappa.get(Parti.tipoDoc);
		line+=mappa.get(Parti.numDoc);
		line+=mappa.get(Parti.luogoRilDoc);

		return line;
	}
	



	
}
