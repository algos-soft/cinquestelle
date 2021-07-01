package it.algos.albergo.stampeobbligatorie.notifica;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;

import it.algos.albergo.statistiche.StatElemento;
import it.algos.albergo.statistiche.StatDialogo.AzConti;
import it.algos.albergo.statistiche.StatDialogo.AzPrenotazioni;
import it.algos.albergo.statistiche.StatDialogo.AzRistorante;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;

/**
 * Dialogo di preparazione esportazione file testo notifiche 
 * in formato Servizio Alloggiati della PS
 */
public class DialogoEsporta extends JDialog {
	
	private WrapGruppoArrivato[] aGruppi;

	/**
	 * @param owner parent Frame
	 * @param aGruppi array di wrapper gruppi da esportare per visualizzare alcine informazioni
	 */
    public DialogoEsporta(Frame owner, WrapGruppoArrivato[] aGruppi) {
        /* rimanda al costruttore della superclasse */
        super(owner);

        this.aGruppi=aGruppi;
        
        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo
    
	/**
	 * @param owner parent Dialog
	 * @param aGruppi array di wrapper gruppi da esportare per visualizzare alcine informazioni
	 */
    public DialogoEsporta(Dialog owner, WrapGruppoArrivato[] aGruppi) {
        /* rimanda al costruttore della superclasse */
        super(owner);

        this.aGruppi=aGruppi;
        
        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo

    
    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore (init) <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

        	setTitle("Esporta per Servizio Alloggiati PS");
        	setModal(true);
        	
            this.add(createContent());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia

    /**
     * @return il contenuto del dialogo
     */
    private JComponent createContent(){
    	//Pannello pan = PannelloFactory.orizzontale(null);
    	JPanel pan = new JPanel();
    	
    	pan.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    	
        Campo campoPath = CampoFactory.testo("path");
        campoPath.setLarghezza(200);
        campoPath.setAbilitato(false);
        campoPath.avvia();
        
        pan.add(campoPath.getPannelloCampo());
        
        
        
        //add(campoPath.getComponenteVideo());
        return pan;
    }
    

}
