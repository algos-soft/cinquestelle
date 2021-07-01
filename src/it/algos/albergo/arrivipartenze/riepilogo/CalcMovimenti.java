/**
 * Copyright(c): 2009
 * Company: Algos s.r.l.
 * Author: alex
 */

package it.algos.albergo.arrivipartenze.riepilogo;

import it.algos.base.query.filtro.Filtro;

/**
 * Calcolatore dei movimenti su un periodo - interfaccia
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 16 lug 2009
 */
public interface CalcMovimenti {


    /**
     * Ritorna il numero di adulti presenti al giorno
     * precedente il periodo analizzato.
     * <p/>
     *
     * @return adulti al giorno precedente
     */
    public int getNumPrecAd();


    /**
     * Ritorna il numero di bambini presenti al giorno
     * precedente il periodo analizzato.
     * <p/>
     *
     * @return bambini al giorno precedente
     */
    public int getNumPrecBa();


    /**
     * Ritorna il numero di adulti arrivati o in arrivo
     * durante il periodo analizzato.
     * <p/>
     *
     * @return adulti arrivati o in arrivo nel periodo
     */
    public int getNumArriviAd();


    /**
     * Ritorna il numero di bambini arrivati o in arrivo
     * durante il periodo analizzato.
     * <p/>
     *
     * @return bambini arrivati o in arrivo nel periodo
     */
    public int getNumArriviBa();


    /**
     * Ritorna il numero di adulti partiti o in partenza
     * durante il periodo analizzato.
     * <p/>
     *
     * @return adulti partiti o in partenza nel periodo
     */
    public int getNumPartenzeAd();

    /**
     * Ritorna il numero di bambini partiti o in partenza
     * durante il periodo analizzato.
     * <p/>
     *
     * @return bambini partiti o in partenza nel periodo
     */
    public int getNumPartenzeBa();

    /**
     * Ritorna il numero di adulti presenti alla fine
     * del periodo analizzato.
     * <p/>
     *
     * @return adulti al giorno precedente
     */
    public int getNumFinaleAd();


    /**
     * Ritorna il numero di bambini presenti alla fine
     * del periodo analizzato.
     * <p/>
     *
     * @return bambini al giorno precedente
     */
    public int getNumFinaleBa();



    /**
     * Ritorna il filtro Precedenti Presenze
     * al giorno precedente il periodo analizzato.
     * <p/>
     *
     * @return il filtro Precedenti Presenze
     */
    public Filtro getFiltroPrecPres();

    /**
     * Ritorna il filtro Precedenti Periodi Positivo
     * per il giorno precedente il periodo analizzato.
     * <p/>
     * Le persone dei periodi selezionati da questo filtro
     * vengono aggiunte al totale
     *
     * @return il filtro Precedenti Periodi Positivo
     */
    public Filtro getFiltroPrecPeriPos();

    /**
     * Ritorna il filtro Precedenti Periodi Negativo
     * per il giorno precedente il periodo analizzato.
     * <p/>
     * Le persone dei periodi selezionati da questo filtro
     * vengono sottratte dal totale
     *
     * @return il filtro Precedenti Periodi Negativo
     */
    public Filtro getFiltroPrecPeriNeg();



    /**
     * Ritorna il filtro Arrivi Presenze
     * durante il periodo analizzato.
     * <p/>
     *
     * @return il filtro Arrivi Presenze
     */
    public Filtro getFiltroArriviPres();

    /**
     * Ritorna il filtro Arrivi Periodi
     * durante il periodo analizzato.
     * <p/>
     *
     * @return il filtro Arrivi Periodi
     */
    public Filtro getFiltroArriviPeri();


    /**
     * Ritorna il filtro Partenze Presenze
     * durante il periodo analizzato.
     * <p/>
     *
     * @return il filtro Partenze Presenze
     */
    public Filtro getFiltroPartenzePres();

    /**
     * Ritorna il filtro Partenze Periodi
     * durante il periodo analizzato.
     * <p/>
     *
     * @return il filtro Partenze Periodi
     */
    public Filtro getFiltroPartenzePeri();


    /**
     * Ritorna il filtro Finale Presenze
     * presenze alla fine del periodo analizzato.
     * <p/>
     *
     * @return il filtro Finale Presenze
     */
    public Filtro getFiltroFinalePres();


    /**
     * Ritorna il testo descrittivo per i presenti al giorno precedente
     * <p/>
     *
     * @return il testo
     */
    public String getTestoPrec();

    /**
     * Ritorna il testo descrittivo per gli arrivi nel periodo
     * <p/>
     *
     * @return il testo
     */
    public String getTestoArrivi();

    /**
     * Ritorna il testo descrittivo per le partenze nel periodo
     * <p/>
     *
     * @return il testo
     */
    public String getTestoPartenze();

    /**
     * Ritorna il testo descrittivo per i presenti a fine periodo
     * <p/>
     *
     * @return il testo
     */
    public String getTestoFinale();

    /**
     * Ritorna true se è sensato utilizzare il filtro presenze precedenti
     * per proiettare l'elenco a video.
     * <p/>
     * In genere è sensato se è l'unico filtro che compone il totale Precedenti
     *
     * @return true se ha senso utilizzare il filtro
     */
    public boolean isSensatoPresPrec();

    /**
     * Ritorna true se è sensato utilizzare il filtro presenze Arrivi
     * per proiettare l'elenco a video.
     * <p/>
     * In genere è sensato se è l'unico filtro che compone il totale Arrivi
     *
     * @return true se ha senso utilizzare il filtro
     */
    public boolean isSensatoPresArrivi();

    /**
     * Ritorna true se è sensato utilizzare il filtro presenze Partenze
     * per proiettare l'elenco a video.
     * <p/>
     * In genere è sensato se è l'unico filtro che compone il totale Partenze
     *
     * @return true se ha senso utilizzare il filtro
     */
    public boolean isSensatoPresPartenze();

    /**
     * Ritorna true se è sensato utilizzare il filtro presenze Finali
     * per proiettare l'elenco a video.
     * <p/>
     * In genere è sensato se è l'unico filtro che compone il totale Finali
     *
     * @return true se ha senso utilizzare il filtro
     */
    public boolean isSensatoPresFinali();





    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica degli intervalli temporali possibili
     */
    enum Tempi {

        passato,
        oggi,
        futuro,
        cavallo;

    }// fine della classe


} // fine della classe