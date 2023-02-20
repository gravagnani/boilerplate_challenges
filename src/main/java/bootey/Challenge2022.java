package bootey;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class Challenge2022 implements Runnable {

    // NON VANNO TOCCATI
    private String id;
    private String fileName;
    private String prefix;

    // MODIFICARE DA QUA
    private Integer Si;
    private Integer Smax;
    private Integer T;
    private Integer D;

    private List<Demon> listDemons;

    private Demon[] solution;
    private Integer[] stamina;
    // A QUA

    // NON VA MODIFICATO
    public Challenge2022(String id, String fileName, String prefix) {
        this.id = id;
        this.fileName = fileName;
        this.prefix = prefix;
    }

    public static void main(String[] args) {

        String[] ids = {"00", "01", "02", "03", "04", "05"};

        String[] inputs = {"00-example.txt", "01-the-cloud-abyss.txt",
                "02-iot-island-of-terror.txt",
                "03-etheryum.txt", "04-the-desert-of-autonomous-machines.txt",
                "05-androids-armageddon.txt"};

        String prefix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));

        for (int i = 0; i < inputs.length; i++) {
            Challenge2022 h = new Challenge2022(ids[i], inputs[i], prefix);
            Thread t = new Thread(h);
            t.start();
        }

    }

    // ADEGUARE A PROBLEMA
    public void fromFile(String fileName) {
        String data;
        String[] splitData;
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            // first line
            // System.out.println("Prima Linea");
            data = scanner.nextLine();
            splitData = data.split(" ");
            this.Si = Integer.parseInt(splitData[0]);
            this.Smax = Integer.parseInt(splitData[1]);
            this.T = Integer.parseInt(splitData[2]);
            this.D = Integer.parseInt(splitData[3]);

            // System.out.println("Si " + this.Si);
            // System.out.println("Smax " + this.Smax);
            // System.out.println("T " + this.T);
            // System.out.println("D " + this.D);

            this.listDemons = new ArrayList<>();
            int libIxd = 0;
            while (scanner.hasNextLine()) {
                System.out.println("Demon " + libIxd);
                // first line
                data = scanner.nextLine();
                splitData = data.split(" ");

                Integer Id = libIxd;
                Integer Sc = Integer.parseInt(splitData[0]);
                Integer Tr = Integer.parseInt(splitData[1]);
                Integer Sr = Integer.parseInt(splitData[2]);
                Integer Na = Integer.parseInt(splitData[3]);
                List<Integer> fragments = new ArrayList<>();

                for (int i = 4; i < 4 + Na; i++) {
                    fragments.add(Integer.parseInt(splitData[i]));
                }

                Demon demon = new Demon(Id, Sc, Tr, Sr, Na, fragments);
                listDemons.add(demon);
                // System.out.println("Id " + demon.getId());
                // System.out.println("Sc " + demon.getSc());
                // System.out.println("Tr " + demon.getTr());
                // System.out.println("Sr " + demon.getSr());
                // System.out.println("Na " + demon.getNa());
                // System.out.println("Fragments " + demon.getFragments());
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

            for (Demon d : this.solution) {
                if (d != null) {
                    writer.write(d.getId() + "\n");
                }
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public int getNextDemon() {
        return 0;
    }

    // ADEGUARE AL PROBLEMA
    public void process() {

        solution = new Demon[T];
        stamina = new Integer[T];
        for (int i = 0; i < T; i++) {
            solution[i] = null;
            stamina[i] = 0;
        }

        this.listDemons.sort((d1, d2) -> {
            if (d2.getWeight() > d1.getWeight()) {
                return 1;
            } else if (d2.getWeight() < d1.getWeight()) {
                return -1;
            }
            return 0;
        });

        System.out.println(this.id + " - inizio processing");

        for (int t = 0; t < solution.length; t++) {

            if (t % 100 == 0) {
                System.out.println(this.id + " " + t + " / " + this.T);
            }

            // aggiorno lista demon
            for (Demon d : listDemons) {
                // d.computeWeight2(T - t);
                d.computeWeight3(T - t, this.Si);
            }

            Collections.sort(this.listDemons, new Comparator<Demon>() {
                @Override
                public int compare(Demon d1, Demon d2) {
                    if (d2.getWeight() > d1.getWeight()) {
                        return 1;
                    } else if (d2.getWeight() < d1.getWeight()) {
                        return -1;
                    }
                    return 0;
                }
            });

            // recupero stamina
            this.Si = Math.min(this.Si + stamina[t], this.Smax);

            // scelgo demone
            for (int i = 0; i < listDemons.size(); i++) {
                if (this.Si >= listDemons.get(i).getSc()) {
                    solution[t] = listDemons.remove(i);
                    break;
                }
            }

            // rimozione stamina
            if (solution[t] == null) {
                continue;
            }

            this.Si -= solution[t].getSc();

            try {
                stamina[t + solution[t].getTr()] += solution[t].getSr();
            } catch (Exception e) {
                ;
            }

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
    public class Demon {
        private Integer Id;

        private Integer Sc;

        private Integer Tr;

        private Integer Sr;

        private Integer Na;

        private List<Integer> fragments;

        private Double weight;

        public Demon(Integer id, Integer sc, Integer tr, Integer sr, Integer na, List<Integer> fragments) {
            this.Id = id;
            this.Sc = sc;
            this.Tr = tr;
            this.Sr = sr;
            this.Na = na;
            this.fragments = fragments;

            this.computeWeight();

        }

        public Double getWeight() {
            return weight;
        }

        public void setWeight(Double weight) {
            this.weight = weight;
        }

        public Integer getId() {
            return Id;
        }

        public void setId(Integer id) {
            Id = id;
        }

        public Integer getSc() {
            return Sc;
        }

        public void setSc(Integer sc) {
            Sc = sc;
        }

        public Integer getTr() {
            return Tr;
        }

        public void setTr(Integer tr) {
            Tr = tr;
        }

        public Integer getSr() {
            return Sr;
        }

        public void setSr(Integer sr) {
            Sr = sr;
        }

        public Integer getNa() {
            return Na;
        }

        public void setNa(Integer na) {
            Na = na;
        }

        public List<Integer> getFragments() {
            return fragments;
        }

        public void setFragments(List<Integer> fragments) {
            this.fragments = fragments;
        }

        public void computeWeight() {
            Integer sumFragments = 0;
            // calcola sum fragments
            for (Integer i : this.fragments) {
                sumFragments += i;
            }
            // calcola weight
            this.weight = 1.0 * this.Sr * sumFragments;// / this.Tr;
        }

        public void computeWeight2(int n) {
            Integer sumFragments = 0;
            Integer sumFragmentsTot = 0;
            // calcola sum fragments
            for (int i = 0; i < fragments.size(); i++) {
                if (i < n) {
                    sumFragments += fragments.get(i);
                }
                sumFragmentsTot += fragments.get(i);
            }
            // calcola weight

            this.weight = 1.0 * sumFragments;// / this.Tr;
        }

        public void computeWeight3(int n, int S) {
            Integer sumFragments = 0;
            // calcola sum fragments
            for (int i = 0; i < fragments.size(); i++) {
                if (i < n) {
                    sumFragments += fragments.get(i);
                }
            }
            // calcola weight
            if (S < 15) {
                this.weight = 1.0 * (this.Sr - this.Sc) / this.Sc;
            } else {
                this.weight = 1.0 * sumFragments;
            }
        }

    }

}