package com.dataVisualization;

import com.activity.Activity;
import com.farmer.Farmer;
import com.fertilizer.Fertilizer;
import com.main.Main;
import com.pesticide.Pesticide;
import com.plant.Plant;
import com.util.DAO;
import com.util.UnitConverter;

import java.util.*;

public class DataVisualizer {

    private int indexFarmId = 0;
    private Map<String, Map<String, Double>> activityTypeMap;
    private String[] farmsIdArr;
    private String[] farmerIdArr;
    private Map<String, Integer> farmIdMap;
    private Map<String, String> chosenMap = new HashMap<>();
    private UnitConverter unitConverter;
    private List<String> chosenIdList = new ArrayList<>();
    private String startDate = "";
    private String endDate = "";
    private List<Activity> firstLastActivity = new ArrayList<>();

    DAO dao = new DAO();
    Farmer[] farmers = dao.getFarmerData();

    public DataVisualizer() {
        this.activityTypeMap = new HashMap<>();
        dao = new DAO();
        farmsIdArr = new String[Main.farms.length];
        this.farmerIdArr = new String[farmers.length];
        this.farmIdMap = new HashMap<>();

        this.unitConverter = new UnitConverter();

        for (int i = 0; i < Main.farms.length; i++) {
            farmsIdArr[i] = Main.farms[i].get_id();
            farmIdMap.put(Main.farms[i].get_id(), i);
        }
        for (int i = 0; i < farmers.length; i++) {
            farmerIdArr[i] = farmers[i].get_id();
        }
    }

    public void startDataVisualizer() {

        Scanner sc = new Scanner(System.in);

        // let user choose which mode to display
        System.out.println("\nWelcome to ifarm: ");
        System.out.println("Below are the display mode:\n" +
                "1. Display all activity logs for a target farm.\n" +
                "2. Display all activity logs for a target farmer\n" +
                "3. Display all activity logs for a target farm and plant / fertilizer / pesticide\n" +
                "4. Display all activity logs for a target farm and plant / fertilizer / pesticide between date A and date B (inclusive)\n" +
                "5. Display summarized logs by plants, fertilizers and pesticides for a target farm and plant / fertilizer / pesticide between date A and date B (inclusive) for selected field and row number.\n" +
                "6. Exit Ifarm. ");

        System.out.print("\nPlease choose a mode: ");
        int mode = sc.nextInt();
        System.out.println();

        // will break when user click 6
        while (true) {

            // catch exception for input other than int
            try {
                switch (mode) {
                    case 1:
                        model1();
                        System.out.print("\nPlease choose a mode again: ");
                        mode = sc.nextInt();
                        System.out.println();
                        break;
                    case 2:
                        model2();
                        System.out.print("\nPlease choose a mode again: ");
                        mode = sc.nextInt();
                        System.out.println();
                        break;
                    case 3:
                        model3();
                        System.out.print("\nPlease choose a mode again: ");
                        mode = sc.nextInt();
                        System.out.println();
                        break;
                    case 4:
                        model4();
                        System.out.print("\nPlease choose a mode again: ");
                        mode = sc.nextInt();
                        System.out.println();
                        break;
                    case 5:
                        model5();
                        System.out.print("\nPlease choose a mode again: ");
                        mode = sc.nextInt();
                        System.out.println();
                        break;
                    case 6:
                        System.out.println("Thank you so much!!!");
                        System.exit(-1);
                    default:
                        System.out.println("\nPlease insert a number in the range 1 - 6");
                        break;
                }
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("Please input integer");
            }
        }
    }

    // Mode 1
    //1. Display all activity logs for a target farm.
    private void model1() {
        Scanner sc = new Scanner(System.in);

        // print farm id for user to view
        printFarm();

        // input farm id
        System.out.print("\nPlease choose a farm with farm id: ");
        String farmId = sc.nextLine().toUpperCase();
        System.out.println();

        // Check farm id exist or not, if not, re-input again
        while (!checkFarmId(farmId.replace(" ", ""))) {
            System.out.println("Sorry, please enter correct farm id");
            System.out.print("Please try again: ");
            farmId = sc.nextLine();
            System.out.println();
        }

        // retrieve records from db based on farm id inputted
        List<Activity> farmActivityList = dao.getActivityByFarmId(farmId);

        // print result
        if (farmActivityList.isEmpty()) {
            System.out.println("Sorry, not have such record.");
        } else {
            System.out.println("Activity log for farm " + farmId);
            printResult(farmActivityList);
        }
    }

    // Mode 2
    // 2.	Display all activity logs for a target farmer
    private void model2() {
        Scanner sc = new Scanner(System.in);

        // display farmer id to user
        System.out.println("Below is a list of farmer");
        for (int i = 0; i < farmers.length; i++) {
            System.out.print(farmers[i].get_id() + " " + farmers[i].getName() + "\n");
        }

        // input farmer id
        System.out.print("\nPlease choose a farmer with farmer id: ");
        String farmerId = sc.nextLine().toUpperCase();
        System.out.println();

        // check farmer id exist or not, if not re-input again
        while (!checkFarmerId(farmerId.replace(" ", ""))) {
            System.out.println("Sorry, please enter correct farmer id");
            System.out.print("Please try again: ");
            farmerId = sc.nextLine();
            System.out.println();
        }

        // retrieve records from database based on farmer id inputted
        List<Activity> farmerActivity = dao.getActivityByFarmerId(String.valueOf(farmerId));

        // print result
        if (farmerActivity.isEmpty()) {
            System.out.println("Sorry, not have such record.");
        } else {
            System.out.println("Activity log for farmer " + farmerId);
            printResult(farmerActivity);
        }
    }

    // Mode 3
    // 3.	Display all activity logs for a target farm and plant / fertilizer / pesticide
    private void model3() {
        Scanner sc = new Scanner(System.in);

        // print farm id for user to view
        printFarm();

        // input farm id
        System.out.print("\nPlease choose a farm with farm id: ");
        String farmId = sc.nextLine().toUpperCase();
        System.out.println();

        // check farm id exist or not, if not re-enter
        while (!checkFarmId(farmId.replace(" ", ""))) {
            System.out.println("Sorry, please enter correct farm id");
            System.out.print("Please try again: ");
            farmId = sc.nextLine().toUpperCase();
            System.out.println();
        }

        // let user input which type they want to display
        System.out.print("Choose one type to display(plant/fertilizer/pesticide): ");
        String displayType = sc.nextLine();
        System.out.println();

        // check input type equal to (plant/pesticide/fertilizer)
        while (!(displayType.equalsIgnoreCase("plant") || displayType.equalsIgnoreCase("fertilizer")
                || displayType.equalsIgnoreCase("pesticide"))) {
            System.out.println("Sorry, please enter correct display type (plant/fertilizer/pesticide)");
            System.out.print("Please enter again: ");
            displayType = sc.nextLine();
            System.out.println();
        }

        // if equal to those 3 type, then print respective id for inputted type
        // for the farm id
        printPlantFertilisePesticides(displayType, farmId);

        // input (plant/fertilizer/pesticide) id
        System.out.print("\nPlease input an id of the type chosen(plant/fertilizer/pesticide): ");
        String chosenId = sc.nextLine().toUpperCase();
        System.out.println();

        // check id exist or not, if not then re-input
        while (!checkChosenId(chosenId)) {
            System.out.println("Sorry, please input correct Id of related display type");
            System.out.print("Please enter again: ");
            chosenId = sc.nextLine();
            System.out.println();
        }

        // map chosen to respective (plant/fertilizer/pesticide) name
        String chosenName = this.chosenMap.get(chosenId);

        // retrieve record from db
        List<Activity> farmAndTypeList = dao.getActivityByFarmerIdAndType(farmId, chosenName);

        // print output
        if (farmAndTypeList.isEmpty()) {
            System.out.println("Sorry, not have such record.");
        } else {
            System.out.println("Activity log for farmer " + farmId);
            printResult(farmAndTypeList);
        }
    }

    // Mode 4
    // 4.	Display all activity logs for a target farm
    // and plant / fertilizer / pesticide between date A and date B (inclusive)
    private void model4() {
        Scanner sc = new Scanner(System.in);

        // print farm id for user to view
        printFarm();

        // input farm id
        System.out.print("\nPlease choose a farm with farm id: ");
        String farmId = sc.nextLine().toUpperCase();
        System.out.println();

        // check farm id exist or not, if not, re-input again
        while (!checkFarmId(farmId.replace(" ", ""))) {
            System.out.println("Sorry, please enter correct farm id");
            System.out.print("Please try again: ");
            farmId = sc.nextLine();
            System.out.println();
        }

        // retrieve first and last record of the activity based on farm id
        firstLastActivity = dao.getFirstAndLastRecord(farmId);

        // let user input which type they want to display
        System.out.print("Choose one type to display(plant/fertilizer/pesticide): ");
        String displayType = sc.nextLine();
        System.out.println();

        // check input type equal to (plant/pesticide/fertilizer)
        while (!(displayType.equalsIgnoreCase("plant") || displayType.equalsIgnoreCase("fertilizer")
                || displayType.equalsIgnoreCase("pesticide"))) {
            System.out.println("Sorry, please enter correct display type (plant/fertilizer/pesticide)");
            System.out.print("Please enter again: ");
            displayType = sc.nextLine();
            System.out.println();
        }

        // if equal to those 3 type, then print respective id for inputted type
        // for the farm id
        printPlantFertilisePesticides(displayType, farmId);

        // input (plant/fertilizer/pesticide) id
        System.out.print("\nPlease input an id of the type chosen(plant/fertilizer/pesticide): ");
        String chosenId = sc.nextLine().toUpperCase();
        System.out.println();

        // check id exist or not, if not then re-input
        while (!checkChosenId(chosenId)) {
            System.out.println("Sorry, please input correct Id of related display type");
            System.out.print("Please enter again: ");
            chosenId = sc.nextLine();
            System.out.println();
        }

        // map id to name of chosen type
        String chosenName = this.chosenMap.get(chosenId);

        //get date of first activity date
        this.startDate = firstLastActivity.get(0).getDate();

        // get date of last activity of the farm
        this.endDate = firstLastActivity.get(1).getDate();

        // input date A
        System.out.printf("Please input start date (year.month.day)(exp: %s): ", this.startDate);
        String dateStart = sc.nextLine();
        System.out.println();

        // input date B
        System.out.printf("Please input end date (year.month.day)(exp: %s): ", this.endDate);
        String dateEnd = sc.nextLine();
        System.out.println();

        // retrieve records from database
        List<Activity> farmAndTypeBetweenDateList = dao.getActivityByFarmerIdAndTypeBetweenDate(farmId, chosenName, dateStart, dateEnd);

        // print output
        if (farmAndTypeBetweenDateList.isEmpty()) {
            System.out.println("Sorry, not have such record.");
        } else {
            System.out.println("Activity log for farmer " + farmId);
            printResult(farmAndTypeBetweenDateList);
        }
    }

    // Mode 5
    // 5.	Display summarized logs by plants, fertilizers and pesticides
    // for a target farm and plant / fertilizer / pesticide
    // between date A and date B (inclusive) for selected field and row number.
    private void model5() {
        Scanner sc = new Scanner(System.in);

        // print farm id for user to view
        printFarm();

        // input farm id
        System.out.print("\nPlease choose a farm with farm id: ");
        String farmId = sc.nextLine().toUpperCase();
        System.out.println();

        // check farmId exist or not, if not, re-enter again
        while (!checkFarmId(farmId.replace(" ", ""))) {
            System.out.println("Sorry, please enter correct farm id");
            System.out.print("Please try again: ");
            farmId = sc.nextLine().toUpperCase();
            System.out.println();
        }

        // retrieve first and last activity based on farm id
        firstLastActivity = dao.getFirstAndLastRecord(farmId);

        // let user input which type they want to display
        System.out.print("Choose one type to display(plant/fertilizer/pesticide): ");
        String displayType = sc.nextLine();
        System.out.println();

        // check input type equal to (plant/pesticide/fertilizer)
        while (!(displayType.equalsIgnoreCase("plant") || displayType.equalsIgnoreCase("fertilizer")
                || displayType.equalsIgnoreCase("pesticide"))) {
            System.out.println("Sorry, please enter correct display type (plant/fertilizer/pesticide)");
            System.out.print("Please enter again: ");
            displayType = sc.nextLine();
            System.out.println();
        }

        // if equal to those 3 type, then print respective id for inputted type
        // for the farm id
        printPlantFertilisePesticides(displayType, farmId);

        // input (plant/fertilizer/pesticide) id
        System.out.print("\nPlease input an id of the type chosen(plant/fertilizer/pesticide): ");
        String chosenId = sc.nextLine().toUpperCase();
        System.out.println();

        // check id exist or not, if not then re-input
        while (!checkChosenId(chosenId)) {
            System.out.println("Sorry, please input correct Id of related display type");
            System.out.print("Please enter again: ");
            chosenId = sc.nextLine();
            System.out.println();
        }

        // map id to name of chosen type
        String chosenName = this.chosenMap.get(chosenId);

        // print how many field and row of the farm to user
        printFieldAndRowOfFarm(farmId);

        // input field
        System.out.print("\nPlease select a field: ");
        int fieldSelected = sc.nextInt();
        System.out.println();

        // input row
        System.out.print("Please select a row: ");
        int rowSelected = sc.nextInt();
        System.out.println();

        sc.nextLine();

        // get date of first activity of the farm
        this.startDate = firstLastActivity.get(0).getDate();

        // get date of last activity of the farm
        this.endDate = firstLastActivity.get(1).getDate();

        // input date A
        System.out.printf("Please input start date (year.month.day)(exp: %s): ", this.startDate);
        String dateStart = sc.nextLine();
        System.out.println();

        // input date B
        System.out.printf("Please input end date (year.month.day)(exp: %s): ", this.endDate);
        String dateEnd = sc.nextLine();
        System.out.println();

        // retrieve record
        List<Activity> farmAndTypeBetweenDateListWithFieldRowList = dao.getActivityByFarmerIdAndTypeBetweenDateWithFieldRow(farmId,
                chosenName, dateStart, dateEnd, fieldSelected, rowSelected);

        // Summarize all the data by using map
        if (farmAndTypeBetweenDateListWithFieldRowList.isEmpty()) {
            System.out.println("Sorry, not have such record.");
        } else {
            for (Activity activity : farmAndTypeBetweenDateListWithFieldRowList) {
                if (activityTypeMap.containsKey(activity.getAction())) {
                    if (activityTypeMap.get(activity.getAction()).containsKey(activity.getType())) {
                        double totalUnit = activityTypeMap.get(activity.getAction()).get(activity.getType()) +
                                unitConverter.getConvertValue(activity.getQuantity(), activity.getUnit());
                        activityTypeMap.get(activity.getAction()).put(activity.getType(), totalUnit);
                    } else {
                        activityTypeMap.get(activity.getAction()).put(activity.getType(), activity.getQuantity());
                    }
                } else {
                    Map<String, Double> tempMap = new HashMap<>();
                    tempMap.put(activity.getType(), activity.getQuantity());
                    activityTypeMap.put(activity.getAction(), tempMap);
                }
            }

            // print result
            printSummaryMapMode5(activityTypeMap, fieldSelected, rowSelected);
        }
    }

    // print farm id
    private void printFarm() {
        System.out.println("Below is a list of farm");
        for (int i = 0; i < Main.farms.length; i++) {
            System.out.print(Main.farms[i].get_id() + " " + Main.farms[i].getName() + "\n");
        }
    }

    // check farm id, return true if exist
    private boolean checkFarmId(String farmId) {
        List<String> farmsIdList = new ArrayList<>(Arrays.asList(this.farmsIdArr));
        return farmsIdList.contains(farmId);
    }

    // check (plant/fertilizer/pesticide) id, return true if exist
    private boolean checkChosenId(String chosenId) {
        return this.chosenIdList.contains(chosenId);
    }

    // check farmer id, return true if exist
    private boolean checkFarmerId(String farmerId) {
        List<String> farmerIdList = new ArrayList<>(Arrays.asList(this.farmerIdArr));
        return farmerIdList.contains(farmerId);
    }

    // return index of the farm in map
    private int retrieveFarmIdIndex(String farmId) {
        return this.farmIdMap.get(farmId);
    }

    // print field and row of certain farm based on farmId
    private void printFieldAndRowOfFarm(String farmId) {
        int indexFarm = this.retrieveFarmIdIndex(farmId);
        System.out.println("Below is the field and row of the farm " + farmId);
        System.out.println("Field: " + Main.farms[indexFarm].getField() + " Row: " + Main.farms[indexFarm].getRow());
    }

    // Print (plant/fertilizer/pesticide) id
    private void printPlantFertilisePesticides(String displayType, String farmId) {

        switch (displayType.toLowerCase()) {
            case "plant":
                printPlantId(farmId);
                break;
            case "fertilizer":
                printFertilizerId(farmId);
                break;
            case "pesticide":
                printPesticide(farmId);
                break;
            default:
                System.out.println("Error");
        }
    }

    // print all plant id of certain farm
    private void printPlantId(String farmId) {
        System.out.println("Below is the list of plant id planted inside the farm " + farmId);
        this.indexFarmId = retrieveFarmIdIndex(farmId);
        Plant[] plants = Main.farms[indexFarmId].getPlants();
        Arrays.sort(plants, Comparator.comparing(Plant::get_id));

        for (int i = 0; i < plants.length; i++) {
            System.out.print(plants[i].get_id() + ", ");
            this.chosenIdList.add(plants[i].get_id());
            this.chosenMap.put(plants[i].get_id(), plants[i].getName());
        }
        System.out.println();
    }

    // print all fertilizer id of certain farm
    private void printFertilizerId(String farmId) {
        System.out.println("Below is the list of fertilizer id used in the farm " + farmId);
        this.indexFarmId = retrieveFarmIdIndex(farmId);
        Fertilizer[] fertilizes = Main.farms[indexFarmId].getFertilizes();
        Arrays.sort(fertilizes, Comparator.comparing(Fertilizer::get_id));

        for (int i = 0; i < fertilizes.length; i++) {
            System.out.print(fertilizes[i].get_id() + ", ");
            this.chosenIdList.add(fertilizes[i].get_id());
            this.chosenMap.put(fertilizes[i].get_id(), fertilizes[i].getName());
        }
        System.out.println();
    }

    // print all pesticide id of certain farm
    private void printPesticide(String farmId) {
        System.out.println("Below is the list of pesticide id used in the farm " + farmId);
        this.indexFarmId = retrieveFarmIdIndex(farmId);
        Pesticide[] pesticides = Main.farms[indexFarmId].getPesticides();
        Arrays.sort(pesticides, Comparator.comparing(Pesticide::get_id));

        for (int i = 0; i < pesticides.length; i++) {
            System.out.print(pesticides[i].get_id() + ", ");
            this.chosenIdList.add(pesticides[i].get_id());
            this.chosenMap.put(pesticides[i].get_id(), pesticides[i].getName());
        }
        System.out.println();
    }

    // print result for mode 5
    private void printSummaryMapMode5(Map<String, Map<String, Double>> summaryMap, int field, int row) {
        for (Map.Entry<String, Map<String, Double>> entry : summaryMap.entrySet()) {
            String key1 = entry.getKey();
            Map<String, Double> value1 = entry.getValue();

            String mainUnit = "";

            if (key1.equalsIgnoreCase("sowing") || key1.equalsIgnoreCase("harvest") || key1.equalsIgnoreCase("sale")) {
                mainUnit = "kg";
            } else if (key1.equalsIgnoreCase("fertilizer")) {
                mainUnit = "pack";
            } else if (key1.equalsIgnoreCase("pesticide")) {
                mainUnit = "mass";
            }

            for (Map.Entry<String, Double> innerEntry : value1.entrySet()) {
                String innerKey = innerEntry.getKey();
                double innertValue = innerEntry.getValue();
                System.out.println(Character.toUpperCase(key1.charAt(0)) + key1.substring(1) + " " + innerKey + "Field " + field +
                        " Row " + row + " " + innertValue + " " + mainUnit);
            }
        }
    }

    private void printResult(List<Activity> farmActivityList) {
        System.out.printf("%-10s %-75s %-9s %-8s%-16s %-10s\n", "Activity", "Type", "Field", "Row", "Quantity&Unit", "Date");
        for (Activity activity : farmActivityList) {
            System.out.printf("%-10s %-75s Field %2d  Row %2d  %-6.2f%-10s %-10s\n",
                    activity.getAction(), activity.getType(), activity.getField(), activity.getRow(),
                    unitConverter.getConvertValue(activity.getQuantity(), activity.getUnit()), "kg", activity.getDate());
        }
    }
}

