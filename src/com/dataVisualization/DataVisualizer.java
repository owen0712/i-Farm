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
    DAO dao = new DAO();
    String[] farmsIdArr;
    String[] farmerIdArr;
    Integer[] farmFieldArr;
    Integer[] farmRowArr;
    Farmer[] farmers = dao.getFarmerData();
    Map<String, Integer> farmIdMap;

    private UnitConverter unitConverter;

    public DataVisualizer() {
        this.activityTypeMap = new HashMap<>();
        dao = new DAO();
        farmsIdArr = new String[Main.farms.length];
        this.farmerIdArr = new String[farmers.length];
        this.farmFieldArr = new Integer[Main.farms.length];
        this.farmRowArr = new Integer[Main.farms.length];
        this.farmIdMap = new HashMap<>();

        this.unitConverter = new UnitConverter();

        for (int i = 0; i < Main.farms.length; i++) {
            farmsIdArr[i] = Main.farms[i].get_id();
            farmFieldArr[i] = Main.farms[i].getField();
            farmRowArr[i] = Main.farms[i].getRow();
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
        while (1 <= mode && mode <= 6) {
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
        }

    }

    private void model1() {
        Scanner sc = new Scanner(System.in);

        printFarm();

        System.out.print("\nPlease choose a farm with farm id: ");
        String farmId = sc.nextLine();
        System.out.println();

        while (!checkFarmId(farmId.replace(" ", ""))) {
            System.out.println("Sorry, please enter correct farm id");
            System.out.print("Please try again: ");
            farmId = sc.nextLine();
            System.out.println();
        }
        List<Activity> farmActivityList = dao.getActivityByFarmId(farmId);

        System.out.println("Activity log for farm " + farmId);
        printResult(farmActivityList);
    }

    private void model2() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Below is a list of farmer");
        for (int i = 0; i < farmers.length; i++) {
            System.out.print(farmers[i].get_id() + " " + farmers[i].getName() + "\n");
        }

        System.out.print("\nPlease choose a farmer with farmer id: ");
        String farmerId = sc.nextLine();
        System.out.println();

        while (!checkFarmerId(farmerId)) {
            System.out.println("Sorry, please enter correct farmer id");
            System.out.print("Please try again: ");
            farmerId = sc.nextLine();
            System.out.println();
        }

        List<Activity> farmerActivity = dao.getActivityByFarmerId(String.valueOf(farmerId));

        System.out.println("Activity log for farmer " + farmerId);
        printResult(farmerActivity);
    }

    private void model3() {
        Scanner sc = new Scanner(System.in);

        printFarm();

        System.out.print("\nPlease choose a farm with farm id: ");
        String farmId = sc.nextLine();
        System.out.println();

        while (!checkFarmId(farmId)) {
            System.out.println("Sorry, please enter correct farm id");
            System.out.print("Please try again: ");
            farmId = sc.nextLine();
            System.out.println();
        }

        printPlantFertilisePesticides(farmId);

        System.out.print("\n\nChoose one type to display(plant/fertilizer/pesticide) by entering their name: ");
        String displayType = sc.nextLine();
        System.out.println();

        List<Activity> farmAndTypeList = dao.getActivityByFarmerIdAndType(farmId, displayType);

        if (farmAndTypeList.isEmpty()) {
            System.out.println("Sorry, not have such record.");
        }

        System.out.println("Activity log for farmer " + farmId);
        printResult(farmAndTypeList);
    }

    private void model4() {
        Scanner sc = new Scanner(System.in);

        printFarm();

        System.out.print("\nPlease choose a farm with farm id: ");
        String farmId = sc.nextLine();
        System.out.println();

        while (!checkFarmId(farmId)) {
            System.out.println("Sorry, please enter correct farm id");
            System.out.println("Please try again: ");
            farmId = sc.nextLine();
            System.out.println();
        }

        printPlantFertilisePesticides(farmId);

        System.out.print("\n\nChoose one type to display(plant/fertilizer/pesticide) by entering their name: ");
        String displayType = sc.nextLine();
        System.out.println();

        System.out.print("Please input start date (year.month.date.hour.min.ss)(exp: 2022.06.02.11.12.32): ");
        String dateStart = sc.nextLine();
        System.out.println();

        System.out.print("Please input end date (year.month.date.hour.min.ss)(exp: 2022.06.02.11.33.24): ");
        String dateEnd = sc.nextLine();
        System.out.println();

        List<Activity> farmAndTypeBetweenDateList = dao.getActivityByFarmerIdAndTypeBetweenDate(farmId, displayType, dateStart, dateEnd);

        if (farmAndTypeBetweenDateList.isEmpty()) {
            System.out.println("Sorry, not have such record.");
        }

        System.out.println("Activity log for farmer " + farmId);
        printResult(farmAndTypeBetweenDateList);
    }

    private void model5() {
        Scanner sc = new Scanner(System.in);

        printFarm();

        System.out.print("\nPlease choose a farm with farm id: ");
        String farmId = sc.nextLine();
        System.out.println();

        while (!checkFarmId(farmId)) {
            System.out.println("Sorry, please enter correct farm id");
            System.out.print("Please try again: ");
            farmId = sc.nextLine();
            System.out.println();
        }

        printPlantFertilisePesticides(farmId);

        System.out.print("\n\nChoose one type to display(plant/fertilizer/pesticide) by entering their name: ");
        String displayType = sc.nextLine();
        System.out.println();

        printFieldAndRowOfFarm(farmId);

        System.out.print("Please select a field: ");
        int fieldSelected = sc.nextInt();
        System.out.println();

        System.out.print("Please select a row: ");
        int rowSelected = sc.nextInt();
        System.out.println();

        sc.nextLine();
        System.out.print("Please input start date (year.month.date.hour.min.ss)(exp: 2022.06.02.11.12.32): ");
        String dateStart = sc.nextLine();
        System.out.println();

        System.out.print("Please input end date (year.month.date.hour.min.ss)(exp: 2022.06.02.11.33.24): ");
        String dateEnd = sc.nextLine();
        System.out.println();

        List<Activity> farmAndTypeBetweenDateListWithFieldRowList = dao.getActivityByFarmerIdAndTypeBetweenDateWithFieldRow(farmId,
                displayType, dateStart, dateEnd, fieldSelected, rowSelected);

        if (farmAndTypeBetweenDateListWithFieldRowList.isEmpty()) {
            System.out.println("Sorry, not have such record.");
        }

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

    private boolean checkFarmerId(String farmerId) {
        List<String> farmerIdList = new ArrayList<>(Arrays.asList(this.farmerIdArr));
        return farmerIdList.contains(farmerId);
    }

    private boolean checkFarmField(int farmField) {
        List<Integer> farmFieldList = new ArrayList<>(Arrays.asList(this.farmFieldArr));
        return farmFieldList.contains(farmField);
    }

    private boolean checkFarmRow(int farmRow) {
        List<Integer> farmRowList = new ArrayList<>(Arrays.asList(this.farmRowArr));
        return farmRowList.contains(farmRow);
    }

    private int retrieveFarmIdIndex(String farmId) {
        return this.farmIdMap.get(farmId);
    }

    private void printFieldAndRowOfFarm(String farmId) {
        int indexFarm = this.retrieveFarmIdIndex(farmId);
        System.out.println("Below is the field and row of the farm " + farmId);
        System.out.println("Field: " + Main.farms[indexFarm].getField() + " Row: " + Main.farms[indexFarm].getRow());
    }

    private void printPlantFertilisePesticides(String farmId) {
        System.out.println("Below is the list of plant planted inside the farm" + farmId);
        this.indexFarmId = retrieveFarmIdIndex(farmId);

        Plant[] plants = Main.farms[indexFarmId].getPlants();
        for (int i = 0; i < plants.length; i++) {
            System.out.print(plants[i].getName() + ", ");
        }

        System.out.println("\n\nBelow is the list of fertilizer used in the farm" + farmId);
        Fertilizer[] fertilizes = Main.farms[indexFarmId].getFertilizes();
        for (int i = 0; i < fertilizes.length; i++) {
            System.out.print(fertilizes[i].getName() + ", ");
        }

        System.out.println("\n\nBelow is the list of pesticide used in the farm: " + farmId);
        Pesticide[] pesticides = Main.farms[indexFarmId].getPesticides();
        for (int i = 0; i < pesticides.length; i++) {
            System.out.print(pesticides[i].getName() + ", ");
        }
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

    private void printResult(List<Activity> farmActivityList){
        System.out.printf("%-10s %-50s %-8s %-5s %-22s %-10s\n", "Activity", "Type", "Field", "Row", "Quantity&Unit", "Date");
        for (Activity activity : farmActivityList) {
            System.out.printf("%-10s %-50s Field %2d Row %2d %6.2f%-15s %-10s\n",
                    activity.getAction(), activity.getType(), activity.getField(), activity.getRow(),
                    activity.getQuantity(), activity.getUnit(), activity.getDate());
        }
    }
}

