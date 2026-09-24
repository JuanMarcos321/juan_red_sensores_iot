/**
 * Contiene los experimentos de la Semana 3.
 *
 * Esta clase NO tiene main().
 * Los experimentos son llamados desde IngestaSensores.
 */
public class BancoDePruebas {

    private static final int[] TAMANOS = {
            1_000,
            100_000,
            1_000_000
    };

    /**
     * Experimento 1:
     * búsqueda lineal en el peor caso.
     */
    public static void experimentoUno() {

        System.out.println(
                "=== EXPERIMENTO 1: BUSQUEDA LINEAL ==="
        );

        System.out.printf(
                "%12s %16s %14s%n",
                "lecturas",
                "comparaciones",
                "tiempo (ms)"
        );

        for (int n : TAMANOS) {

            LecturaSensor[] datos =
                    GeneradorDatos.generar(n);

            String objetivo =
                    GeneradorDatos.timestampEnPosicion(n - 1);

            long inicio = System.nanoTime();

            int posicion =
                    BuscadorLecturas
                            .busquedaLinealPorTimestamp(
                                    datos,
                                    objetivo
                            );

            long fin = System.nanoTime();

            System.out.printf(
                    "%12d %16d %14.3f%n",
                    n,
                    BuscadorLecturas.getComparaciones(),
                    (fin - inicio) / 1_000_000.0
            );

            if (posicion < 0) {
                System.out.println(
                        "ADVERTENCIA: no encontro una lectura existente."
                );
            }
        }

        System.out.println();
    }

    /**
     * Experimento 2:
     * compara búsqueda lineal y búsqueda binaria
     * (comparaciones y tiempo).
     */
    public static void experimentoDos() {

        System.out.println("=== EXPERIMENTO 2: LINEAL vs BINARIA ===");

        System.out.printf(
                "%12s %12s %12s %12s %16s %16s%n",
                "lecturas", "lineal", "binaria", "relacion",
                "t.lineal (us)", "t.binaria (us)"
        );

        for (int n : TAMANOS) {

            LecturaSensor[] datos = GeneradorDatos.generar(n);
            String objetivo = GeneradorDatos.timestampEnPosicion(n - 1);

            long t0 = System.nanoTime();
            BuscadorLecturas.busquedaLinealPorTimestamp(datos, objetivo);
            long t1 = System.nanoTime();
            int lineal = BuscadorLecturas.getComparaciones();

            long t2 = System.nanoTime();
            BuscadorLecturas.busquedaBinariaPorTimestamp(datos, objetivo);
            long t3 = System.nanoTime();
            int binaria = BuscadorLecturas.getComparaciones();

            System.out.printf(
                    "%12d %12d %12d %12.1f %16.1f %16.1f%n",
                    n, lineal, binaria,
                    (double) lineal / binaria,
                    (t1 - t0) / 1_000.0,
                    (t3 - t2) / 1_000.0
            );
        }

        System.out.println();
    }

    /**
     * Compara búsqueda de un timestamp inexistente.
     */
    public static void experimentoTres() {

        System.out.println(
                "=== EXPERIMENTO 3: DATO INEXISTENTE ==="
        );

        LecturaSensor[] datos =
                GeneradorDatos.generar(100_000);

        String objetivo =
                GeneradorDatos.timestampInexistente();

        BuscadorLecturas.busquedaLinealPorTimestamp(
                datos,
                objetivo
        );

        int lineal =
                BuscadorLecturas.getComparaciones();

        BuscadorLecturas.busquedaBinariaPorTimestamp(
                datos,
                objetivo
        );

        int binaria =
                BuscadorLecturas.getComparaciones();

        System.out.println(
                "Lineal  -> comparaciones: " + lineal
        );

        System.out.println(
                "Binaria -> comparaciones: " + binaria
        );

        System.out.println();
    }

    /**
     * Demuestra qué ocurre cuando la búsqueda binaria
     * se aplica sobre un campo que no está ordenado.
     */
    public static void experimentoCuatro() {

        System.out.println(
                "=== EXPERIMENTO 4: BINARIA POR PM2.5 ==="
        );

        LecturaSensor[] datos =
                GeneradorDatos.generar(10_000);

        int aciertosLineal = 0;
        int aciertosBinaria = 0;

        for (int i = 0; i < 20; i++) {

            double valor =
                    datos[i * 137].getPm25();

            int posLineal = -1;

            for (int j = 0; j < datos.length; j++) {

                if (datos[j].getPm25() == valor) {
                    posLineal = j;
                    break;
                }
            }

            int posBinaria =
                    BuscadorLecturas
                            .busquedaBinariaPorPm25(
                                    datos,
                                    valor
                            );

            if (posLineal >= 0) {
                aciertosLineal++;
            }

            if (posBinaria >= 0) {
                aciertosBinaria++;
            }
        }

        System.out.println(
                "Valores buscados que SI existen: 20"
        );

        System.out.println(
                "Encontrados por búsqueda lineal:  "
                        + aciertosLineal
        );

        System.out.println(
                "Encontrados por búsqueda binaria: "
                        + aciertosBinaria
        );

        System.out.println();
    }

    /**
     * Casos de prueba mínimos (paso 37).
     * Compara lo que devuelve cada búsqueda con la posición esperada.
     */
    public static void pruebasMinimas() {

        System.out.println("=== PRUEBAS MINIMAS DE BUSQUEDA ===");

        LecturaSensor[] pequeno = GeneradorDatos.generar(10);
        LecturaSensor[] grande  = GeneradorDatos.generar(1_000_000);

        verificar("pequeno - primero",     pequeno,
                GeneradorDatos.timestampEnPosicion(0), 0);
        verificar("pequeno - intermedio",  pequeno,
                GeneradorDatos.timestampEnPosicion(5), 5);
        verificar("pequeno - ultimo",      pequeno,
                GeneradorDatos.timestampEnPosicion(9), 9);
        verificar("pequeno - inexistente", pequeno,
                GeneradorDatos.timestampInexistente(), -1);

        verificar("grande - primero",      grande,
                GeneradorDatos.timestampEnPosicion(0), 0);
        verificar("grande - intermedio",   grande,
                GeneradorDatos.timestampEnPosicion(500_000), 500_000);
        verificar("grande - ultimo",       grande,
                GeneradorDatos.timestampEnPosicion(999_999), 999_999);
        verificar("grande - inexistente",  grande,
                GeneradorDatos.timestampInexistente(), -1);

        System.out.println();
    }

    /**
     * Ejecuta ambas búsquedas y muestra si coinciden con lo esperado.
     */
    private static void verificar(
            String nombre,
            LecturaSensor[] datos,
            String objetivo,
            int esperado) {

        int lineal = BuscadorLecturas
                .busquedaLinealPorTimestamp(datos, objetivo);
        int compLineal = BuscadorLecturas.getComparaciones();

        int binaria = BuscadorLecturas
                .busquedaBinariaPorTimestamp(datos, objetivo);
        int compBinaria = BuscadorLecturas.getComparaciones();

        boolean ok = (lineal == esperado && binaria == esperado);

        System.out.printf(
                "%-22s esperado=%7d | lineal=%7d (%7d comp) | binaria=%7d (%2d comp) | %s%n",
                nombre, esperado, lineal, compLineal,
                binaria, compBinaria, ok ? "OK" : "FALLO"
        );
    }

}
