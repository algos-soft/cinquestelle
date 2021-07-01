package it.algos.base.matrice.test;
/**
 * Created by IntelliJ IDEA.
 * User: gac
 * Date: 30-giu-2006
 * Time: 15.40.58
 * To change this template use File | Settings | File Templates.
 */

import it.algos.base.matrice.MatriceDoppia;
import junit.framework.TestCase;

public class MatriceDoppiaTest extends TestCase {

    MatriceDoppia matriceDoppia;


    public void testAdd() throws Exception {
        matriceDoppia = new MatriceDoppia();
        int risultato;
        int richiesto;

        richiesto = 1;
        risultato = matriceDoppia.add(78, "pippo");

        if (risultato != richiesto) {
            fail("una riga");
        }// fine del blocco if

        richiesto = 2;
        risultato = matriceDoppia.add(78, "pippo");

        if (risultato != richiesto) {
            fail("due righe");
        }// fine del blocco if

    }
}