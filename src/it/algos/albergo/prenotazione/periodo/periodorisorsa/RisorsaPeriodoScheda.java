package it.algos.albergo.prenotazione.periodo.periodorisorsa;

import it.algos.albergo.risorse.Risorsa;
import it.algos.albergo.risorse.RisorsaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.scheda.SchedaDefault;

import java.util.Date;

public class RisorsaPeriodoScheda extends SchedaDefault implements RisorsaPeriodo{

    public RisorsaPeriodoScheda(Modulo modulo) {
        super(modulo);

        try {
            this.inizia();
        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        }
    }

    
    private void inizia() throws Exception {
        try {

        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        }

    }

    
    /**
     * Crea le pagine.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override
    protected void creaPagine() {
        /* variabili e costanti locali di lavoro */
        Pagina pagina;
        Pannello pan;

        try { // prova ad eseguire il codice

            /* aggiunge  una pagina al libro con il set di default */
            pagina = this.addPagina("generale");

            /* prima riga */
            pan = PannelloFactory.orizzontale(this);
            pan.setAllineamento(Layout.ALLINEA_BASSO);
            pan.add(RisorsaPeriodo.Cam.dataInizio);
            pan.add(RisorsaPeriodo.Cam.dataFine);
            pagina.add(pan);

            pagina.add(RisorsaPeriodo.Cam.tipoRisorsa);

            pagina.add(RisorsaPeriodo.Cam.risorsa);
            
            pagina.add(RisorsaPeriodo.Cam.note);


            
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }	
	


	@Override
	public void avvia() {
		super.avvia();
		regolaFiltroPopRisorse();		
        getPortale().getFinestra().setTitolo("Risorsa");
	}
	
	


	@Override
	public void avvia(int codice) {
		super.avvia(codice);
		regolaFiltroPopRisorse();		
	}


	@Override
	public void sincronizza() {
		super.sincronizza();
	}


	@Override
	public boolean isValida() {
		boolean valida = super.isValida();
		if (valida) {
			valida=(getCodImpegnoSovrapposto()==0);
		}
		return valida;
	}


	@Override
	public String getMotivoNonValida() {
		String motivo=super.getMotivoNonValida();
		int codSovrapposto = getCodImpegnoSovrapposto();
		if (codSovrapposto!=0) {
			motivo += "\n"+PeriodoRisorsaLogica.getMessaggioOccupato(codSovrapposto);
		}
		return motivo;
	}
	
	/**
	 * @return l'id dell'eventuale impegno sovrapposto a quello 
	 * correntemente specificato nella scheda, 0 se non ce ne sono 
	 * (o i dati non sono sufficienti per controllare).
	 */
	private int getCodImpegnoSovrapposto(){
		Date data1=getData(Cam.dataInizio.get());
		Date data2=getData(Cam.dataFine.get());
		int codTipoRisorsa = getInt(Cam.tipoRisorsa.get());
		int codRisorsa = getInt(Cam.risorsa.get());
		int codEscludi = getCodice();
		int idSovrapposto = PeriodoRisorsaLogica.getCodImpegnoSovrapposto(data1, data2, codTipoRisorsa, codRisorsa, codEscludi);
		return idSovrapposto;
	}
	
	/**
	 * Regola il filtro del popup risorse in base al tipo di risorsa correntemente selezionato
	 */
    private void regolaFiltroPopRisorse(){
    	int codTipoRisorsa = getInt(Cam.tipoRisorsa.get());
    	Modulo modRisorse = RisorsaModulo.get();
    	Campo camTipo = modRisorse.getCampo(Risorsa.Cam.tipo);
		Filtro filtro = FiltroFactory.crea(camTipo, codTipoRisorsa);
    	Campo camRisorsa = getCampo(Cam.risorsa);
		camRisorsa.getCampoDB().setFiltroCorrente(filtro);
		camRisorsa.avvia();
		//camRisorsa.getCampoDB().setOrdineElenco(campo);();

    }


	@Override
	protected void eventoMemoriaModificata(Campo campo) {
		if (campo.equals(getCampo(Cam.tipoRisorsa))) {
			regolaFiltroPopRisorse();
		}
	}
	
    
}
