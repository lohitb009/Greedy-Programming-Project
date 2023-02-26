import java.util.*;

/*
Paint houses that are available the earliest
 */

public class Solution_Strat3 {
    private String strat1(ArrayList<ArrayList<Integer>> timelines, int n, int m){
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
        for(int i=0; i<timelines.size(); i++){

            // pair -- [startDay,endDay,houseNumber]
            ArrayList<Integer> pair = timelines.get(i);
            pair.add(i+1); // house number
            houseQueue.add(pair);
        }

        // chk the painter's available day & paint the house conditionally
        for(int pDay=1; pDay<=n; pDay++){

            while(houseQueue.size()!=0){
                ArrayList<Integer> peekHouse = houseQueue.peek();

                if(peekHouse.get(0)<=pDay && pDay<=peekHouse.get(1)){
                    //paint the house
                    houseQueue.remove(); // head of the queue
                    housePaintString.append(peekHouse.get(2));
                    housePaintString.append(" ");
                    break; // paint 1 house in a day
                } else if (peekHouse.get(0)<=pDay && pDay>peekHouse.get(1)) {
                    // breech of endDay, just remove the pair
                    houseQueue.remove();
                    continue;
                } else if (peekHouse.get(0)>pDay) {
                    // this house isn't available for painting, painter relaxes
                    break;
                }
            }

            // myQueue is empty, no need to check further
            if(houseQueue.size() == 0){
                break;
            }

        }
        // trim the white space
        return housePaintString.toString().trim();
    }
    private String strat2(ArrayList<ArrayList<Integer>> timelines, int n, int m){
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

        PriorityQueue <ArrayList<Integer>> latestHouses = new PriorityQueue<>(new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                if(o1.get(0)==o2.get(0)){
                    return o1.get(1).compareTo(o2.get(1)); //ascending order of endDate for startDates conflict
                }else {
                    return o2.get(0).compareTo(o1.get(0)); // max-heap, new listing preference on pDay than the previous available listings
                }
            }
        });

        int housePtr = 0;
        for(int pDay=1; pDay<=n; pDay++){

            // add the houses that became available inside the priorityQueue on pDay
            while(housePtr < m){
                ArrayList<Integer> pair = timelines.get(housePtr);
                if(pair.get(0) == pDay){
                    pair.add(housePtr+1); // starting from 0th index
                    latestHouses.add(pair);
                    housePtr+=1;
                }else{
                    break;
                }
            }
            // extract head from the queue, paint-it and go ahead
            if(latestHouses.size()==0){
                continue;
            }

            // chk if you can paint the house or not
            while(latestHouses.size()!=0){

                // chk the head of the priorityQueue & decide if you can paint it or not
                ArrayList<Integer> peekHouse = latestHouses.peek();

                if(peekHouse.get(0) <= pDay &&  pDay <= peekHouse.get(1)){

                    // paint it, remove the house from the priorityQueue & move to the next day
                    // System.out.println("Peek House:\t"+ Arrays.toString(peekHouse.toArray()));

                    housePaintString.append(Integer.toString(peekHouse.get(2)));
                    housePaintString.append(" ");
                    latestHouses.remove();
                    break; // painted 1 house in a day

                } else if (peekHouse.get(0) <= pDay &&  pDay > peekHouse.get(1)) {
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
    private String strat3(ArrayList<ArrayList<Integer>> timelines, int n, int m){
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

        PriorityQueue <ArrayList<Integer>> latestHouses = new PriorityQueue<>(new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                if(o1.get(2)==o2.get(2)){
                    return o1.get(1).compareTo(o2.get(1)); //ascending order of endDate for duration conflict
                }else {
                    return o1.get(2).compareTo(o2.get(2)); // min-heap for duration
                }
            }
        });

        int housePtr = 0;
        for(int pDay=1; pDay<=n; pDay++){

            // add the houses that became available inside the priorityQueue on pDay
            while(housePtr < m){
                ArrayList<Integer> pair = timelines.get(housePtr);
                if(pair.get(0) == pDay){
                    pair.add(pair.get(1)-pair.get(0)); // difference of duration (endDay-startDay)
                    pair.add(housePtr+1); // starting from 0th index
                    latestHouses.add(pair);
                    housePtr+=1;
                }else{
                    break;
                }
            }
            // extract head from the queue, paint-it and go ahead
            if(latestHouses.size()==0){
                continue;
            }

            // chk if you can paint the house or not
            while(latestHouses.size()!=0){

                // chk the head of the priorityQueue & decide if you can paint it or not
                ArrayList<Integer> peekHouse = latestHouses.peek();

                if(peekHouse.get(0) <= pDay &&  pDay <= peekHouse.get(1)){

                    // paint it, remove the house from the priorityQueue & move to the next day
                    // System.out.println("Peek House:\t"+ Arrays.toString(peekHouse.toArray()));
                    housePaintString.append(Integer.toString(peekHouse.get(3)));
                    housePaintString.append(" ");
                    latestHouses.remove();
                    break; // painted 1 house in a day

                } else if (peekHouse.get(0) <= pDay &&  pDay > peekHouse.get(1)) {
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
    private String strat4(ArrayList<ArrayList<Integer>> timelines, int n, int m){
        /*
        paint the house that will stop being available the earliest
         */
        /*
        Approach:
        1) Take the house that became available in the pDay days and add to the MIN-HEAP on the basis of endDate
        2) If no new house is available, pick any previous availability & paint conditionally
        3) If new house is available, add the pair to the min-heap
        4) Use MIN-HEAP priority queue on the basis of "endDay"; in-case of same duration, prioritize in increasing order of startDay.
         */
        StringBuilder housePaintString = new StringBuilder();

        PriorityQueue <ArrayList<Integer>> latestHouses = new PriorityQueue<>(new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                if(o1.get(1)==o2.get(1)){
                    /*can add startDay same logic as well*/
                    return o1.get(0).compareTo(o2.get(0)); //ascending order of startDate for endDay conflict's
                }else {
                    return o1.get(1).compareTo(o2.get(1)); // min-heap for endDates
                }
            }
        });

        int housePtr = 0;
        for(int pDay=1; pDay<=n; pDay++){

            // add the houses that became available inside the priorityQueue on pDay
            while(housePtr < m){
                ArrayList<Integer> pair = timelines.get(housePtr);
                if(pair.get(0) == pDay){
                    pair.add(housePtr+1); // starting from 0th index
                    latestHouses.add(pair);
                    housePtr+=1;
                }else{
                    break;
                }
            }
            // extract head from the queue, paint-it and go ahead
            if(latestHouses.size()==0){
                continue;
            }

            // chk if you can paint the house or not
            while(latestHouses.size()!=0){

                // chk the head of the priorityQueue & decide if you can paint it or not
                ArrayList<Integer> peekHouse = latestHouses.peek();

                if(peekHouse.get(0) <= pDay &&  pDay <= peekHouse.get(1)){

                    // paint it, remove the house from the priorityQueue & move to the next day
                    // System.out.println("Peek House:\t"+ Arrays.toString(peekHouse.toArray()));

                    housePaintString.append(Integer.toString(peekHouse.get(2)));
                    housePaintString.append(" ");
                    latestHouses.remove();
                    break; // painted 1 house in a day

                } else if (peekHouse.get(0) <= pDay &&  pDay > peekHouse.get(1)) {
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

    public static void main(String[] args) {

        // assumption right now
        String line1 = "6 10";
        String[] lineM = new String[]{"3 4","1 3","2 3","2 4","1 2","2 6","3 4","1 5","3 4","3 6"};

        // preprocessing step
        String[] line1Split = line1.split("\\s+");
        int n = Integer.parseInt(line1Split[0]);
        int m = Integer.parseInt(line1Split[1]);

        ArrayList<ArrayList<Integer>> timelines = new ArrayList<>();
        for(int i=0; i<lineM.length; i++){
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
        System.out.println("strat3 result = "+obj.strat3(timelines,n,m));

    }
}
