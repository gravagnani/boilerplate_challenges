import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Challenge implements Runnable {

    // NON VANNO TOCCATI
    private String id;
    private String fileName;
    private String prefix;

    // MODIFICARE DA QUA
    private int nPizzas;
    private int nT2;
    private int nT3;
    private int nT4;

    private List<Pizza> listPizzas;
    private List<Delivery> listDelivers;
    // A QUA

    // NON VA MODIFICATO
    public Challenge(String id, String fileName, String prefix) {
        this.id = id;
        this.fileName = fileName;
        this.prefix = prefix;
    }

    // ADEGUARE A PROBLEMA
    public void fromFile(String fileName) {
        String data = null;
        String[] splitData = null;
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            // first line
            System.out.println("Prima Linea");
            data = scanner.nextLine();
            splitData = data.split(" ");
            this.nPizzas = Integer.parseInt(splitData[0]);
            this.nT2 = Integer.parseInt(splitData[1]);
            this.nT3 = Integer.parseInt(splitData[2]);
            this.nT4 = Integer.parseInt(splitData[3]);

            // next 'till end
            this.listPizzas = new ArrayList<>();
            this.listDelivers = new ArrayList<>();
            int libIxd = 0;
            while (scanner.hasNextLine()) {
                System.out.println("Pizza " + libIxd);
                // first line
                data = scanner.nextLine();
                splitData = data.split(" ");
                int n = Integer.parseInt(splitData[0]);
                String[] ing = new String[splitData.length - 1];
                for (int i = 1; i < splitData.length; i++) {
                    ing[i - 1] = splitData[i];
                }
                Pizza p = new Pizza(libIxd, n, ing);
                listPizzas.add(p);
                libIxd++;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // ADEGUARE AL PROBLEMA
    public void toFile(String fileName) {

        try {
            FileWriter writer = new FileWriter(fileName);

            writer.write("" + this.listDelivers.size() + "\n");
            for (Delivery d : this.listDelivers) {
                writer.write("" + d.getNDeliveredPizas() + " ");
                String s = "";
                for (Pizza p : d.getDeliveredPizzas()) {
                    s += p.getId() + " ";
                }
                writer.write(s + "\n");
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public int getNextPizza() {
        return 0;
    }

    // ADEGUARE AL PROBLEMA
    public void process() {

        Collections.sort(this.listPizzas, new Comparator<Pizza>() {
            @Override
            public int compare(Pizza o1, Pizza o2) {
                return o2.getNDiff() - o1.getNDiff();
            }
        });

        // System.out.println(this.id + " - inizio processing");

        for (int t = 0; t < this.nT4; t++) {
            if (listPizzas.size() < 4) {
                break;
            }
            List<Pizza> lp = new ArrayList<>();
            for (int p = 0; p < 4; p++) {
                lp.add(listPizzas.remove(getNextPizza()));
            }
            Delivery d = new Delivery(4, lp);
            listDelivers.add(d);
        }

        for (int t = 0; t < this.nT3; t++) {
            if (listPizzas.size() < 3) {
                break;
            }
            List<Pizza> lp = new ArrayList<>();
            for (int p = 0; p < 3; p++) {
                lp.add(listPizzas.remove(getNextPizza()));
            }
            Delivery d = new Delivery(3, lp);
            listDelivers.add(d);
        }

        for (int t = 0; t < this.nT2; t++) {
            if (listPizzas.size() < 2) {
                break;
            }
            List<Pizza> lp = new ArrayList<>();
            for (int p = 0; p < 2; p++) {
                lp.add(listPizzas.remove(getNextPizza()));
            }
            Delivery d = new Delivery(2, lp);
            listDelivers.add(d);
        }

        System.out.println(this.id + " - fine processing");

    }

    @Override
    public void run() {
        System.out.println("### " + this.id + " Inizio ###");
        this.fromFile("input/" + this.fileName);
        this.process();
        this.toFile("output/" + this.prefix + "_" + this.fileName);
        System.out.println("### " + this.id + " Fine ###");
    }

    public int calcolaScore() {
        // calcola lo score del problema
        return 1;
    }

    /**
     * 
     */
    private class Pizza {
        private int id;
        private int nDiff;
        private String[] ingredients;

        public Pizza(int id, int nDiff, String[] ingredients) {
            this.id = id;
            this.nDiff = nDiff;
            this.ingredients = ingredients;
        }

        public int getId() {
            return this.id;
        }

        public String[] getIngredients() {
            return this.ingredients;
        }

        public int getNDiff() {
            return this.nDiff;
        }

    }

    /**
     * 
     */
    private class Delivery {
        private int nDeliveredPizzas;
        private List<Pizza> deliveredPizzas;

        public Delivery(int nDeliveredPizzas, List<Pizza> deliveredPizzas) {
            this.nDeliveredPizzas = nDeliveredPizzas;
            this.deliveredPizzas = deliveredPizzas;
        }

        public int getNDeliveredPizas() {
            return this.nDeliveredPizzas;
        }

        public List<Pizza> getDeliveredPizzas() {
            return this.deliveredPizzas;
        }
    }

    public static void main(String[] args) {

        String[] ids = { "A", "B", "C", "D", "E" };

        String[] inputs = { "a_example", "b_little_bit_of_everything.in", "c_many_ingredients.in", "d_many_pizzas.in",
                "e_many_teams.in" };

        String prefix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));

        for (int i = 0; i < inputs.length; i++) {
            Challenge h = new Challenge(ids[i], inputs[i], prefix);
            Thread t = new Thread(h);
            t.start();
        }

    }

}