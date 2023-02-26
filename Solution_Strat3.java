import java.util.*;

/*
Paint houses that are available the earliest
 */

public class Solution_Strat3 {
    public static void main(String[] args) {

        // Input from user for line1
        Scanner sc = new Scanner(System.in);

        // assumption right now
        // String line1 = "6 10";
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

        Solution_Strat3 obj = new Solution_Strat3();

        // chk for strat3
        System.out.println("strat3 result = " + obj.strat3(timelines, n, m));

    }
    private String strat3(ArrayList<ArrayList<Integer>> timelines, int n, int m) {
        /*
        paint the house that is available for the shortest duration
         */
        /*
        Approach:
        1) Take the house that became available in the pDay days, calculate the duration and add the pair (inclusive of duration) to min-heap.
        2) If no new house is available, pick any previous availability & paint conditionally
        3) If new house is available, add the pair (inclusive of duration) to the min-heap
        4) Use MIN-HEAP priority queue on the basis of "duration"; in-case of same duration, prioritize in increasing order of end-day
         */

        StringBuilder housePaintString = new StringBuilder();

        PriorityQueue<ArrayList<Integer>> latestHouses = new PriorityQueue<>(new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                if (o1.get(2) == o2.get(2)) {
                    return o1.get(1).compareTo(o2.get(1)); //ascending order of endDate for duration conflict
                } else {
                    return o1.get(2).compareTo(o2.get(2)); // min-heap for duration
                }
            }
        });

        int housePtr = 0;
        for (int pDay = 1; pDay <= n; pDay++) {

            // before adding the new listing, update the duration of previous listings
            if(pDay>1){
                ArrayList<ArrayList<Integer>> previousBuffer = new ArrayList<>();
                while(latestHouses.size()!=0){
                    ArrayList<Integer> priorityQueueHead = latestHouses.remove();
                    priorityQueueHead.set(2,priorityQueueHead.get(2)-1); // updating the duration
                    if(priorityQueueHead.get(2)>=0){
                        // not an expired listing,add to the buffer
                        previousBuffer.add(priorityQueueHead);
                        continue;
                    }
                }

                // add the previousBuffer back to the priorityQueue
                for(int i=0; i<previousBuffer.size(); i++){
                    latestHouses.add(previousBuffer.get(i));
                }
            }

            // add the houses that became available inside the priorityQueue on pDay
            while (housePtr < m) {
                ArrayList<Integer> pair = timelines.get(housePtr);
                if (pair.get(0) == pDay) {
                    pair.add(pair.get(1) - pair.get(0)); // difference of duration (endDay-startDay)
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
                    housePaintString.append(Integer.toString(peekHouse.get(3)));
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

        // remove last added white space & return the string
        return housePaintString.toString().trim();
    }

}
