import java.util.*;

public class Solution_Strat2 {
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

        Solution_Strat2 obj = new Solution_Strat2();

        // chk for strat2
        System.out.println("strat2 result = " + obj.strat2(timelines, n, m));
    }

    private String strat2(ArrayList<ArrayList<Integer>> timelines, int n, int m) {
		/*
		   paint the house that started being available the latest
		 */
		/*
        Approach:
        1) Take the house that became available in the ith days, paint any house and move to the next day.
        2) If no new house is available, pick any previous availability.
        3) If new house is available, pick that house & paint it
        4) Use MAX-HEAP priority queue on the basis of "startDay"; in-case of same startDay, prioritize with minimum endDate
		 */

        StringBuilder housePaintString = new StringBuilder();

        PriorityQueue<ArrayList<Integer>> latestHouses = new PriorityQueue<>(new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                if (o1.get(0) == o2.get(0)) {
                    return o1.get(1).compareTo(o2.get(1)); //ascending order of endDate for startDates conflict
                } else {
                    return o2.get(0).compareTo(o1.get(0)); // max-heap, new listing preference on pDay than the previous available listings
                }
            }
        });

        int housePtr = 0;
        for (int pDay = 1; pDay <= n; pDay++) {

            // add the houses that became available inside the priorityQueue on pDay
            while (housePtr < m) {
                ArrayList<Integer> pair = timelines.get(housePtr);
                if (pair.get(0) == pDay) {
                    pair.add(housePtr + 1); // starting from 0th index
                    latestHouses.add(pair);
                    housePtr += 1;
                } else {
                    break;
                }
            }
            // extract head from the queue, paint-it and go ahead
            if (latestHouses.size() == 0) {
                continue;
            }

            // chk if you can paint the house or not
            while (latestHouses.size() != 0) {

                // chk the head of the priorityQueue & decide if you can paint it or not
                ArrayList<Integer> peekHouse = latestHouses.peek();

                if (peekHouse.get(0) <= pDay && pDay <= peekHouse.get(1)) {

                    // paint it, remove the house from the priorityQueue & move to the next day
                    // System.out.println("Peek House:\t"+ Arrays.toString(peekHouse.toArray()));

                    housePaintString.append(Integer.toString(peekHouse.get(2)));
                    housePaintString.append(" ");
                    latestHouses.remove();
                    break; // painted 1 house in a day

                } else if (peekHouse.get(0) <= pDay && pDay > peekHouse.get(1)) {
                    // breech of endDay
                    // just remove the house
                    latestHouses.remove();
                    continue;

                } else if (pDay < peekHouse.get(0)) {
                    // start day hasn't arrived, no need to check further
                    break;
                }
            }
        }
        return housePaintString.toString().trim();
    }
}
