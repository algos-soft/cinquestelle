/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 14 giu 2006
 */

package it.algos.albergo.pianodeicontialbergo.sottoconto;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.scheda.SchedaDefault;

/**
 * Presentazione grafica di un singolo record di AlbSottoconto.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea le pagine del <code>Libro</code> che vengono visualizzate nel
 * PortaleScheda del Navigatore </li>
 * <li> Ogni pagina viene creata con un set di campi o aggiungendo i singoli campi </li>
 * <li> I campi vengono posizionati in automatico oppure singolarmente </li>
 * <li> Se uno stesso campo viene posizionato su pi&ugrave; pagine, risulter&agrave;
 * visibile solo nell'ultima pagina in cui viene posizionato </li>
 * <li> Se il <code>Modello>/code> prevede il campo note, crea la pagina note </li>
 * <li> Se il flag programma &egrave; attivo, crea la pagina programmatore </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 14 giu 2006
 */
public final class AlbSottocontoScheda extends SchedaDefault {

    /**
     * Costruttore completo.
     *
     * @param unModulo di riferimento per la scheda
     */
    public AlbSottocontoScheda(Modulo unModulo) {
        /* rimanda al costruttore della superclasse */
        super(unModulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Sovrascritto dalle sottoclassi
     */
    @Override protected void eventoMemoriaModificata(Campo campo) {
        Campo tipoPrezzo;
        boolean acceso;

        try { // prova ad eseguire il codice
//            if (campo.getNomeInterno().equals(AlbSottoconto.CAMPO_FISSO)) {
//                tipoPrezzo = this.getCampo(AlbSottoconto.CAMPO_TIPO_PREZZO);
//                acceso = (Boolean)campo.getValore();
//                tipoPrezzo.setVisibile(acceso);
//                if (!acceso) {
//                    tipoPrezzo.setValore(0);
//                }// fine del blocco if
//            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }

} // fine della classe
