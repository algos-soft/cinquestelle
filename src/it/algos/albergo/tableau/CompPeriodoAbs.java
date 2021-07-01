package it.algos.albergo.tableau;

import it.algos.albergo.AlbergoLib;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;

import java.awt.Color;
import java.awt.Component;
import java.util.Date;
import java.util.HashMap;

import javax.swing.SwingConstants;

public abstract class CompPeriodoAbs extends PanGradientRounded implements CompPeriodoIF {

	HashMap<String, Object> values = new HashMap<String, Object>();

	public CompPeriodoAbs() {
		super(Tableau.COLORE_CONFERMATO, SwingConstants.VERTICAL);
		inizia();
	}
	
	private void inizia() {
		
		/* sfondo arrotondato */
		this.setRoundRadius(10);

	}
	
	@Override
	public void pack(GrafoPrenotazioni graph, UserObjectPeriodo uo) {

		/* regolazioni del colore in base ai vari flag */
		boolean confermato = Libreria
				.getBool(get(UserObjectPeriodo.KEY_CONFERMATA));
		boolean arrivato = Libreria
				.getBool(get(UserObjectPeriodo.KEY_ARRIVATO));
		boolean partito = Libreria.getBool(get(UserObjectPeriodo.KEY_PARTITO));
		Date dataScad = Libreria
				.getDate(get(UserObjectPeriodo.KEY_DATASCADENZA));
		boolean opzione = Libreria.getBool(get(UserObjectPeriodo.KEY_OPZIONE));
		regolaColore(confermato, arrivato, partito, dataScad, opzione);
		
		 /* tooltip */
		 setToolTipText(creaTooltipText(graph, uo));

	}

	
	/**
	 * @return the graphic component
	 */
	public Component getComponent(){
		return this;
	}

	/**
	 * Puts a value in the value map
	 * @param key the key
	 * @param value the value
	 */
	public void put(String key, Object value){
		values.put(key, value);
	}
	
	/**
	 * Returns a value from the value map
	 * @param key the key
	 * @return the value
	 */
	public Object get(String key){
		return values.get(key);
	}
	
	/**
	 * Assegna lo stato di presenza.
	 * <p/>
	 * 
	 * @param confermata
	 *            true se prenotazione confermata
	 * @param arrivato
	 *            true se già arrivato
	 * @param partito
	 *            true se già partito
	 * @param scadenza
	 *            data scadenza prenotazione
	 * @param opzione
	 *            se è una opzione
	 */
	public void regolaColore(boolean confermata, boolean arrivato,
			boolean partito, Date scadenza, boolean opzione) {
		/* variabili e costanti locali di lavoro */
		Color col;

		if (partito) {
			col = Tableau.COLORE_PARTITO;
		} else {
			if (arrivato) {
				col = Tableau.COLORE_PRESENTE;
			} else {
				if (confermata) {
					col = Tableau.COLORE_CONFERMATO;
				} else {
					if (opzione) {
						col = Tableau.COLORE_OPZIONE;
					} else {
						Date oggi = AlbergoLib.getDataProgramma();
						if (Lib.Data.isPosterioreUguale(oggi, scadenza)) { // non
																			// confermato
																			// e
																			// non
																			// scaduto
							col = Tableau.COLORE_NON_CONFERMATO;
						} else {
							col = Tableau.COLORE_SCADUTO; // non confermato e
															// scaduto
						}// fine del blocco if-else
					}// fine del blocco if-else
				}// fine del blocco if-else
			}// fine del blocco if-else
		}// fine del blocco if-else

		this.setColor(col);
	}




    /**
     * Crea il testo per il tooltip.
     * <p/>
     *
     * @param graph GrafoPrenotazioni di riferimento
     * @param uo lo UserObject con i dati
     *
     * @return il testo per il tooltip
     */
    public String creaTooltipText(GrafoPrenotazioni graph, UserObjectPeriodo uo) {
        /* variabili e costanti locali di lavoro */
        String text = "";
        String str;

        try {    // prova ad eseguire il codice
        			
            /* HTML Start */
            text = "<html>";

            /* riga cliente */
            str = creaRigaCliente(uo);
            if (Lib.Testo.isValida(str)) {
                text += str;
            }// fine del blocco if

            /* riga periodo */
            str = creaRigaPeriodo(graph, uo);
            if (Lib.Testo.isValida(str)) {
                text += "<br>" + str;
            }// fine del blocco if

            /* riga persone - trattamento - preparazione */
            str = creaRigaPersone(uo);
            if (Lib.Testo.isValida(str)) {
                text += "<br>" + str;
            }// fine del blocco if

            /* riga stato confermata - presente */
            str = creaRigaStato(uo);
            if (Lib.Testo.isValida(str)) {
                text += "<br>" + str;
            }// fine del blocco if

            /* HTML end */
            text += "</html>";

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return text;
    }


    /**
     * Crea la riga cliente-agenzia per il tooltip.
     * <p/>
     *
     * @param uo lo UserObject con i dati
     *
     * @return la riga creata
     */
    protected static String creaRigaCliente(UserObjectPeriodo uo) {
        return uo.getClienteAgenzia();
    }


    /**
     * Crea la riga periodo per il tooltip.
     * <p/>
     *
     * @param graph GrafoPrenotazioni di riferimento
     * @param uo lo UserObject con i dati
     *
     * @return la riga creata
     */
    protected static String creaRigaPeriodo(GrafoPrenotazioni graph, UserObjectPeriodo uo) {
        String text = "";
        text += graph.getPanGrafi().getNomeRisorsa(uo.getInt(UserObjectPeriodo.KEY_NOME_CAMERA));
        text += " dal ";
        text += Lib.Data.getDataBrevissima(uo.getDate(UserObjectPeriodo.KEY_DATAINIZIO));
        text += " al ";
        text += Lib.Data.getDataBrevissima(uo.getDate(UserObjectPeriodo.KEY_DATAFINE));
        return text;
    }


    /**
     * Crea la riga persone-trattamento-preparazione per il tooltip.
     * <p/>
     *
     * @param uo lo UserObject con i dati
     *
     * @return la riga creata
     */
    protected static String creaRigaPersone(UserObjectPeriodo uo) {
        String str = "";
        int ad = uo.getInt(UserObjectPeriodo.KEY_NUMAD);
        int ba = uo.getInt(UserObjectPeriodo.KEY_NUMBA);
        if (ad > 0) {
            str += ad + "ad";
        }
        if (ba > 0) {
            if (Lib.Testo.isValida(str)) {
                str += "+";
            }// fine del blocco if
            str += ba + "ba";
        }// fine del blocco if

        if (Lib.Testo.isValida(uo.getTrattamento())) {
            str += " - " + uo.getTrattamento();
        }// fine del blocco if

        if (Lib.Testo.isValida(uo.getPreparazione())) {
            str += " - " + uo.getPreparazione();
        }// fine del blocco if

        return str;
    }


    /**
     * Crea la riga stato confermata - presente per il tooltip.
     * <p/>
     *
     * @param uo lo UserObject con i dati
     *
     * @return la riga creata
     */
    protected static String creaRigaStato(UserObjectPeriodo uo) {
        String text = "";

        if (uo.getBool(UserObjectPeriodo.KEY_PARTITO)) {
            text += "Cliente già partito";
        } else {
            if (uo.isPresente()) {
                text += "Cliente attualmente presente";
            } else {
                if (uo.getBool(UserObjectPeriodo.KEY_CONFERMATA)) {
                    text += "Prenotazione confermata";
                } else {
                    if (uo.isOpzione()) {
                        text += "Opzione da confermare";
                    } else {
                        if (uo.isPrenScaduta()) {
                            text += "Prenotazione scaduta il "+Lib.Data.getStringa(uo.getDataScadenza());
                        }// fine del blocco if
                    }// fine del blocco if-else
                }// fine del blocco if-else
            }// fine del blocco if-else
        }// fine del blocco if-else

        /* valore di ritorno */
        return text;
    }


	

}
