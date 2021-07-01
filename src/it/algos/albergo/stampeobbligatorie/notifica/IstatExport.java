package it.algos.albergo.stampeobbligatorie.notifica;

import java.util.Date;
import java.util.HashMap;

public class IstatExport extends ExportBase {

	public IstatExport(WrapGruppoArrivato[] aGruppi, Date data, String path,
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
	public String createLine(int codCliente, int codPeriodo, TipoAlloggiato tipo, String codPosizione) {
		
		// crea una mappa con le parti di riga
		HashMap<Parti, String> mappa = creaMappa(codCliente, codPeriodo, tipo, codPosizione);

		// costruzione linea
		String line="";
		line = mappa.get(Parti.tipoAlloggiato);
		line += mappa.get(Parti.dataArrivo);
		line+=getBlank(Parti.cognome);
		line+=getBlank(Parti.nome);
		line+=mappa.get(Parti.sesso);
		line+=mappa.get(Parti.dataNascita);
		line+=mappa.get(Parti.comuneNascita);
		line+=mappa.get(Parti.provinciaNascita);
		line+=mappa.get(Parti.statoNascita);
		line+=mappa.get(Parti.cittadinanza);
		line+=mappa.get(Parti.comuneResidenza);
		line+=mappa.get(Parti.provinciaResidenza);
		line+=mappa.get(Parti.statoResidenza);
		line+=getBlank(Parti.indirizzo);
		line+=getBlank(Parti.tipoDoc);
		line+=getBlank(Parti.numDoc);
		line+=getBlank(Parti.luogoRilDoc);
		line+=getBlank(Parti.dataPartenza);
		line+=getBlank(Parti.tipoTurismo);
		line+=getBlank(Parti.mezzoTrasporto);
		line+=getBlank(Parti.camereOccupate);
		line+=getBlank(Parti.camereDisponibili);
		line+=getBlank(Parti.lettiDisponibili);
		line+=getBlank(Parti.tassaSoggiorno);
		line+=mappa.get(Parti.codiceIdentificativoPosizione);
		line+=mappa.get(Parti.modalita);

		return line;
	}

}
