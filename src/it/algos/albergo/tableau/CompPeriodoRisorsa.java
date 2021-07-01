package it.algos.albergo.tableau;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Date;

import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.test.GetUiDefaults;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class CompPeriodoRisorsa extends CompPeriodoAbs {

	private JLabel labelCentro;

	
	public CompPeriodoRisorsa() {
		super();
		inizia();
	}
	
	private void inizia(){
		
		/* pannello generale */
		Pannello pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);

		/* molla */
		pan.add(Box.createHorizontalGlue());

		/* componente centrale */
		labelCentro = new JLabel();
		labelCentro.setHorizontalAlignment(SwingConstants.CENTER);
		labelCentro.setFont(Tableau.FONT_NOMI_CLIENTI);
		labelCentro.setOpaque(false);
		labelCentro.setForeground(Tableau.COLORE_NOME_CLIENTE);
		labelCentro.setMinimumSize(new Dimension(10, 20));
		pan.add(labelCentro);

		/* molla */
		pan.add(Box.createHorizontalGlue());

		/* aggiunge il pannello completo */
		this.add(pan.getPanFisso());

	}


	
	@Override
	public void pack(GrafoPrenotazioni graph, UserObjectPeriodo uo) {

		super.pack(graph, uo);
		
		/* nome cliente */
		String nome = Libreria.getString(get(UserObjectPeriodo.KEY_CLIENTE));
		String camera = Libreria.getString(get(UserObjectPeriodo.KEY_NOME_CAMERA));
		labelCentro.setText(camera+" "+nome);

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
	@Override
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
            
            /* riga note */
            String note = uo.getString(UserObjectPeriodo.KEY_NOTE);
            if (Lib.Testo.isValida(note)) {
                text += "<br>" + note;
            }// fine del blocco if

            /* HTML end */
            text += "</html>";

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return text;
    }



}
