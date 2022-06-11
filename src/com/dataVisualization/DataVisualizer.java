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

        while (true) {
            try {
                System.out.println("Below are the display mode:\n" +
                        "1. Display all activity logs for a target farm.\n" +
                        "2. Display all activity logs for a target farmer\n" +
                        "3. Display all activity logs for a target farm and plant / fertilizer / pesticide\n" +
                        "4. Display all activity logs for a target farm and plant / fertilizer / pesticide between date A and date B (inclusive)\n" +
                        "5. Display summarized logs by plants, fertilizers and pesticides for a target farm and plant / fertilizer / pesticide between date A and date B (inclusive) for selected field and row number.\n" +
                        "6. Exit Ifarm. ");
                System.out.print("\nPlease choose a mode: ");
                mode = sc.nextInt();
                System.out.println();
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

    private void model1() {
        Scanner sc = new Scanner(System.in);

        printFarm();

        System.out.print("\nPlease choose a farm with farm id: ");
        String farmId = sc.nextLine().toUpperCase();
        System.out.println();

        while (!checkFarmId(farmId.replace(" ", ""))) {
            System.out.println("Sorry, please enter correct farm id");
            System.out.print("Please try again: ");
            farmId = sc.nextLine();
            System.out.println();
        }

        List<Activity> farmActivityList = dao.getActivityByFarmId(farmId);
        if (farmActivityList.isEmpty()) {
            System.out.println("Sorry, not have such record.");
        } else {
            System.out.println("Activity log for farm " + farmId);
            printResult(farmActivityList);
        }
    }

    private void model2() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Below is a list of farmer");
        for (int i = 0; i < farmers.length; i++) {
            System.out.print(farmers[i].get_id() + " " + farmers[i].getName() + "\n");
        }

        System.out.print("\nPlease choose a farmer with farmer id: ");
        String farmerId = sc.nextLine().toUpperCase();
        System.out.println();

        while (!checkFarmerId(farmerId.replace(" ", ""))) {
            System.out.println("Sorry, please enter correct farmer id");
            System.out.print("Please try again: ");
            farmerId = sc.nextLine();
            System.out.println();
        }

        List<Activity> farmerActivity = dao.getActivityByFarmerId(String.valueOf(farmerId));

        if (farmerActivity.isEmpty()) {
            System.out.println("Sorry, not have such record.");
        } else {
            System.out.println("Activity log for farmer " + farmerId);
            printResult(farmerActivity);
        }
    }

    private void model3() {
        Scanner sc = new Scanner(System.in);

        printFarm();

        System.out.print("\nPlease choose a farm with farm id: ");
        String farmId = sc.nextLine().toUpperCase();
        System.out.println();

        while (!checkFarmId(farmId.replace(" ", ""))) {
            System.out.println("Sorry, please enter correct farm id");
            System.out.print("Please try again: ");
            farmId = sc.nextLine().toUpperCase();
            System.out.println();
        }

        System.out.print("Choose one type to display(plant/fertilizer/pesticide): ");
        String displayType = sc.nextLine();
        System.out.println();

        while (!(displayType.equalsIgnoreCase("plant") || displayType.equalsIgnoreCase("fertilizer")
                || displayType.equalsIgnoreCase("pesticide"))) {
            System.out.println("Sorry, please enter correct display type (plant/fertilizer/pesticide)");
            System.out.print("Please enter again: ");
            displayType = sc.nextLine();
            System.out.println();
        }

        printPlantFertilisePesticides(displayType, farmId);

        System.out.print("\nPlease input an id of the type chosen(plant/fertilizer/pesticide): ");
        String chosenId = sc.nextLine().toUpperCase();
        System.out.println();

        while (!checkChosenId(chosenId)) {
            System.out.println("Sorry, please input correct Id of related display type");
            System.out.print("Please enter again: ");
            chosenId = sc.nextLine();
            System.out.println();
        }

        String chosenName = this.chosenMap.get(chosenId);

        List<Activity> farmAndTypeList = dao.getActivityByFarmerIdAndType(farmId, chosenName);

        if (farmAndTypeList.isEmpty()) {
            System.out.println("Sorry, not have such record.");
        } else {
            System.out.println("Activity log for farmer " + farmId);
            printResult(farmAndTypeList);
        }
    }

    private void model4() {
        Scanner sc = new Scanner(System.in);

        printFarm();

        System.out.print("\nPlease choose a farm with farm id: ");
        String farmId = sc.nextLine().toUpperCase();
        System.out.println();

        while (!checkFarmId(farmId.replace(" ", ""))) {
            System.out.println("Sorry, please enter correct farm id");
            System.out.print("Please try again: ");
            farmId = sc.nextLine();
            System.out.println();
        }

        firstLastActivity = dao.getFirstAndLastRecord(farmId);

        System.out.print("Choose one type to display(plant/fertilizer/pesticide): ");
        String displayType = sc.nextLine();
        System.out.println();

        while (!(displayType.equalsIgnoreCase("plant") || displayType.equalsIgnoreCase("fertilizer")
                || displayType.equalsIgnoreCase("pesticide"))) {
            System.out.println("Sorry, please enter correct display type (plant/fertilizer/pesticide)");
            System.out.print("Please enter again: ");
            displayType = sc.nextLine();
            System.out.println();
        }

        printPlantFertilisePesticides(displayType, farmId);

        System.out.print("\nPlease input an id of the type chosen(plant/fertilizer/pesticide): ");
        String chosenId = sc.nextLine().toUpperCase();
        System.out.println();

        while (!checkChosenId(chosenId)) {
            System.out.println("Sorry, please input correct Id of related display type");
            System.out.print("Please enter again: ");
            chosenId = sc.nextLine();
            System.out.println();
        }

        String chosenName = this.chosenMap.get(chosenId);

        this.startDate = firstLastActivity.get(0).getDate();
        this.endDate = firstLastActivity.get(1).getDate();

        System.out.printf("Please input start date (year.month.date.hour.min.ss)(exp: %s): ", this.startDate);
        String dateStart = sc.nextLine();
        System.out.println();

        System.out.printf("Please input end date (year.month.date.hour.min.ss)(exp: %s): ", this.endDate);
        String dateEnd = sc.nextLine();
        System.out.println();

        List<Activity> farmAndTypeBetweenDateList = dao.getActivityByFarmerIdAndTypeBetweenDate(farmId, chosenName, dateStart, dateEnd);

        if (farmAndTypeBetweenDateList.isEmpty()) {
            System.out.println("Sorry, not have such record.");
        } else {
            System.out.println("Activity log for farmer " + farmId);
            printResult(farmAndTypeBetweenDateList);
        }
    }

    private void model5() {
        Scanner sc = new Scanner(System.in);

        printFarm();

        System.out.print("\nPlease choose a farm with farm id: ");
        String farmId = sc.nextLine().toUpperCase();
        System.out.println();

        while (!checkFarmId(farmId.replace(" ", ""))) {
            System.out.println("Sorry, please enter correct farm id");
            System.out.print("Please try again: ");
            farmId = sc.nextLine().toUpperCase();
            System.out.println();
        }

        firstLastActivity = dao.getFirstAndLastRecord(farmId);

        System.out.print("Choose one type to display(plant/fertilizer/pesticide): ");
        String displayType = sc.nextLine();
        System.out.println();

        while (!(displayType.equalsIgnoreCase("plant") || displayType.equalsIgnoreCase("fertilizer")
                || displayType.equalsIgnoreCase("pesticide"))) {
            System.out.println("Sorry, please enter correct display type (plant/fertilizer/pesticide)");
            System.out.print("Please enter again: ");
            displayType = sc.nextLine();
            System.out.println();
        }

        printPlantFertilisePesticides(displayType, farmId);

        System.out.print("\nPlease input an id of the type chosen(plant/fertilizer/pesticide): ");
        String chosenId = sc.nextLine().toUpperCase();
        System.out.println();

        while (!checkChosenId(chosenId)) {
            System.out.println("Sorry, please input correct Id of related display type");
            System.out.print("Please enter again: ");
            chosenId = sc.nextLine();
            System.out.println();
        }

        String chosenName = this.chosenMap.get(chosenId);

        printFieldAndRowOfFarm(farmId);

        System.out.print("Please select a field: ");
        int fieldSelected = sc.nextInt();
        System.out.println();

        System.out.print("Please select a row: ");
        int rowSelected = sc.nextInt();
        System.out.println();

        sc.nextLine();

        this.startDate = firstLastActivity.get(0).getDate();
        this.endDate = firstLastActivity.get(1).getDate();

        System.out.printf("Please input start date (year.month.date.hour.min.ss)(exp: %s): ", this.startDate);
        String dateStart = sc.nextLine();
        System.out.println();

        System.out.printf("Please input end date (year.month.date.hour.min.ss)(exp: %s): ", this.endDate);
        String dateEnd = sc.nextLine();
        System.out.println();

        List<Activity> farmAndTypeBetweenDateListWithFieldRowList = dao.getActivityByFarmerIdAndTypeBetweenDateWithFieldRow(farmId,
                displayType, dateStart, dateEnd, fieldSelected, rowSelected);

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
            printSummaryMapMode5(activityTypeMap, fieldSelected, rowSelected);
        }
    }

    private void printFarm() {
        System.out.println("Below is a list of farm");
        for (int i = 0; i < Main.farms.length; i++) {
            System.out.print(Main.farms[i].get_id() + " " + Main.farms[i].getName() + "\n");
        }
    }

    private boolean checkFarmId(String farmId) {
        List<String> farmsIdList = new ArrayList<>(Arrays.asList(this.farmsIdArr));
        return farmsIdList.contains(farmId);
    }

    private boolean checkChosenId(String chosenId) {
        return this.chosenIdList.contains(chosenId);
    }

    private boolean checkFarmerId(String farmerId) {
        List<String> farmerIdList = new ArrayList<>(Arrays.asList(this.farmerIdArr));
        return farmerIdList.contains(farmerId);
    }

    private int retrieveFarmIdIndex(String farmId) {
        return this.farmIdMap.get(farmId);
    }

    private void printFieldAndRowOfFarm(String farmId) {
        int indexFarm = this.retrieveFarmIdIndex(farmId);
        System.out.println("Below is the field and row of the farm " + farmId);
        System.out.println("Field: " + Main.farms[indexFarm].getField() + " Row: " + Main.farms[indexFarm].getRow());
    }

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

