/**
 *
 * @author Julius Lukas Jedzinskas, programu sistemos, 2 gr.
 * Metoduose paklaidos siek tiek padidintos pagal poreikius,
 * kad galutiniam atsakyme butu tiksliai suapvalinta.
 */

import java.math.RoundingMode;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.apfloat.FixedPrecisionApfloatHelper;

public class Metodai {
    
    // pi skaiciavimas Gauso-Lagranzo metodu.
    static Apfloat GLpi (int paklaida) {
        int pakl = paklaida + 2;
        FixedPrecisionApfloatHelper fpah = new FixedPrecisionApfloatHelper(5);
        Apfloat a = new Apfloat(1, pakl),
                du = new Apfloat(2, pakl),
                keturi = new Apfloat(4, pakl),
                b = ApfloatMath.inverseRoot(du, 2),
                t = a.divide(keturi),
                p = a,
                log = fpah.log(new Apfloat(pakl), new Apfloat(2)).truncate(),
                laik;
        int iteracijos = log.intValue(), i = 0;
        do {
            i++;
            laik = a;
            a = a.add(b).divide(du);
            b = laik.multiply(b);
            b = ApfloatMath.sqrt(b);
            t = t.subtract(p.multiply(ApfloatMath.pow(laik.subtract(a), 2)));
            p = du.multiply(p);
        } while(i < iteracijos);
        a = a.add(b);
        t = keturi.multiply(t);
        Apfloat pi = ApfloatMath.round(ApfloatMath.pow(a, 2).divide(t), pakl - 1, RoundingMode.HALF_UP); 
        return pi;
    }
    
    /* pi skaiciavimas. Naudojamas Apfloat paketo (Chudnovsky binary splitting)
       metodas (ApfloatMath.pi). */
    static Apfloat Cpi(int paklaida) {
        int pakl = paklaida + 1;
        Apfloat pi = ApfloatMath.round(ApfloatMath.pi(pakl + 1), pakl, RoundingMode.HALF_UP);
        return pi;
    }
    
    // e skaiciavimas Oilerio eilutes metodu.
    static Apfloat Oe(int paklaida) {
        int pakl = paklaida + 3, cikloSkaitliukas = pakl + 3;
        Apfloat e = Apfloat.ONE,
                faktorialas = e,
                vienas = e,
                faktorialoDaugiklis = e,
                skaitiklioDaugiklis = e,
                skaitiklioSumatoriausSkaitliukas = new Apfloat(cikloSkaitliukas - 1),
                skaitiklioSumatorius = Apfloat.ZERO;
        FixedPrecisionApfloatHelper fpah = new FixedPrecisionApfloatHelper(pakl);
        for(int i = 1; i < cikloSkaitliukas; i++) {
            faktorialas = faktorialas.multiply(faktorialoDaugiklis);
            faktorialoDaugiklis = faktorialoDaugiklis.add(vienas);
            skaitiklioDaugiklis = skaitiklioDaugiklis.multiply(skaitiklioSumatoriausSkaitliukas);
            skaitiklioSumatorius = skaitiklioSumatorius.add(skaitiklioDaugiklis);
            skaitiklioSumatoriausSkaitliukas = skaitiklioSumatoriausSkaitliukas.subtract(vienas);        
        }
        e = ApfloatMath.round(fpah.divide(skaitiklioSumatorius, faktorialas), pakl - 2, RoundingMode.HALF_UP);
        return e;
    }
    
    /* e skaiciavimas. 
     * Naudojamas Apfloat paketo (Niutono iteraciju atvirksciam logaritmui)
     * metodas (ApfloatMath.exp). */
    static Apfloat Ne(int paklaida) {
        int pakl = paklaida  + 2;
        FixedPrecisionApfloatHelper fpah = new FixedPrecisionApfloatHelper(pakl);
        Apfloat e = ApfloatMath.round(fpah.exp(Apfloat.ONE), pakl - 1, RoundingMode.HALF_UP);
        return e;
    }
    
    // e^pi skaiciavimas. Naudojamas Apfloat paketo metodas (ApfloatMath.pow).
    static Apfloat epi(Apfloat e, Apfloat pi, int paklaida) {
        Apfloat epi = ApfloatMath.round(ApfloatMath.pow(e, pi), paklaida, RoundingMode.HALF_UP);
        return epi;
    }
}
