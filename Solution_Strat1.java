import java.util.*;

/*
Paint houses that are available the earliest
 */

public class Solution_Strat1 {
    public static void main(String[] args) {

        // Input from user for line1
        Scanner sc = new Scanner(System.in);

        // assumption right now
        //String line1 = "6 10";
        System.out.println("Enter n<space>m value:");
        String line1 = sc.nextLine();
        //System.out.println("\nInput n<space>m are:\t"+line1);

        // preprocessing step
        String[] line1Split = line1.split("\\s+");
        int n = Integer.parseInt(line1Split[0]);
        int m = Integer.parseInt(line1Split[1]);

        // Input lineM
        System.out.println("Enter 'm' pair's i.e. (startDay<space>endDay):");
        String[] lineM = new String[m];
        //String[] lineM = new String[]{"3 4", "1 3", "2 3", "2 4", "1 2", "2 6", "3 4", "1 5", "3 4", "3 6"};
        for(int i=0; i<m; i++){
            lineM[i] = sc.nextLine();
        }

        ArrayList<ArrayList<Integer>> timelines = new ArrayList<>();
        for (int i = 0; i < lineM.length; i++) {
            String[] currentStr = lineM[i].split("\\s+");
            int startDate = Integer.parseInt(currentStr[0]);
            int endDate = Integer.parseInt(currentStr[1]);

            ArrayList<Integer> localList = new ArrayList<>();
            localList.add(startDate);
            localList.add(endDate);

            timelines.add(localList);
        }

        // sort the arrayList
        Collections.sort(timelines, new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                if (o1.get(0) == o2.get(0)) {
                    return (o1.get(1) - o2.get(1)); // increasing order of endDate
                } else {
                    return o1.get(0) - o2.get(0);
                }
            }
        });

        // chk if ArrayList is in SortedOrder
        /*
        System.out.println("Sorted ArrayList Print");
        for(int i=0; i<timelines.size(); i++){
            System.out.println(Arrays.toString(timelines.get(i).toArray()));
        }
         */

        Solution_Strat1 obj = new Solution_Strat1();

        // chk for strat1
        System.out.println("strat1 result = " + obj.strat1(timelines, n, m));
    }

    private String strat1(ArrayList<ArrayList<Integer>> timelines, int n, int m) {
        /*
        paint the house that started being available the earliest
         */
        /*
        Approach:
        1) Add all the houses inside the queue (pre-processing step).
        2) For the pDay, paint the head of the queue if its timelines are in the range (startDay<=pDay<=endDay).
        3) If the endDay of the house is breached (startDay<=pDay but pDay>endDay), remove the head of the queue & check the next pair.
        4) If the house isn't available (startDay>pDay), the painter will relax and move on to the next day.
         */

        StringBuilder housePaintString = new StringBuilder();

        // preprocessing step -- add all the houses inside the queue
        Queue<ArrayList<Integer>> houseQueue = new LinkedList<>();
        for (int i = 0; i < timelines.size(); i++) {

            // pair -- [startDay,endDay,houseNumber]
            ArrayList<Integer> pair = timelines.get(i);
            pair.add(i + 1); // house number
            houseQueue.add(pair);
        }

        // chk the painter's available day & paint the house conditionally
        for (int pDay = 1; pDay <= n; pDay++) {

            while (houseQueue.size() != 0) {
                ArrayList<Integer> peekHouse = houseQueue.peek();

                if (peekHouse.get(0) <= pDay && pDay <= peekHouse.get(1)) {
                    //paint the house
                    houseQueue.remove(); // head of the queue
                    housePaintString.append(peekHouse.get(2));
                    housePaintString.append(" ");
                    break; // paint 1 house in a day
                } else if (peekHouse.get(0) <= pDay && pDay > peekHouse.get(1)) {
                    // breech of endDay, just remove the pair
                    houseQueue.remove();
                    continue;
                } else if (peekHouse.get(0) > pDay) {
                    // this house isn't available for painting, painter relaxes
                    break;
                }
            }

            // myQueue is empty, no need to check further
            if (houseQueue.size() == 0) {
                break;
            }

        }
        // trim the white space
        return housePaintString.toString().trim();
    }
}
