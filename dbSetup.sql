-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: ifarm
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activities`
--

DROP TABLE IF EXISTS `activities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activities` (
  `_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `farmId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `userId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `action` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `quantity` double NOT NULL,
  `field` int NOT NULL,
  `farm_row` int NOT NULL,
  PRIMARY KEY (`_id`),
  KEY `fk_user_idx` (`userId`),
  KEY `fk_farm_idx` (`farmId`),
  CONSTRAINT `fk_activity_farm` FOREIGN KEY (`farmId`) REFERENCES `farms` (`_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_activity_user` FOREIGN KEY (`userId`) REFERENCES `users` (`_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activities`
--

LOCK TABLES `activities` WRITE;
/*!40000 ALTER TABLE `activities` DISABLE KEYS */;
/*!40000 ALTER TABLE `activities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `farm_fertilizer`
--

DROP TABLE IF EXISTS `farm_fertilizer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `farm_fertilizer` (
  `fertilizer_id` varchar(255) NOT NULL,
  `farm_id` varchar(255) NOT NULL,
  PRIMARY KEY (`fertilizer_id`,`farm_id`),
  KEY `fk_farm_fertilizer_farm_idx` (`farm_id`),
  CONSTRAINT `fk_farm_fertilizer_farm` FOREIGN KEY (`farm_id`) REFERENCES `farms` (`_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_farm_fertilizer_fertilizer` FOREIGN KEY (`fertilizer_id`) REFERENCES `fertilizers` (`_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `farm_fertilizer`
--

LOCK TABLES `farm_fertilizer` WRITE;
/*!40000 ALTER TABLE `farm_fertilizer` DISABLE KEYS */;
/*!40000 ALTER TABLE `farm_fertilizer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `farm_pesticide`
--

DROP TABLE IF EXISTS `farm_pesticide`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `farm_pesticide` (
  `pesticide_id` varchar(255) NOT NULL,
  `farm_id` varchar(255) NOT NULL,
  PRIMARY KEY (`pesticide_id`,`farm_id`),
  KEY `fk_farm_pesticide_farm_idx` (`farm_id`),
  CONSTRAINT `fk_farm_pesticide_farm` FOREIGN KEY (`farm_id`) REFERENCES `farms` (`_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_farm_pesticide_pesticide` FOREIGN KEY (`pesticide_id`) REFERENCES `pesticides` (`_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `farm_pesticide`
--

LOCK TABLES `farm_pesticide` WRITE;
/*!40000 ALTER TABLE `farm_pesticide` DISABLE KEYS */;
/*!40000 ALTER TABLE `farm_pesticide` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `farm_plant`
--

DROP TABLE IF EXISTS `farm_plant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `farm_plant` (
  `farm_id` varchar(255) NOT NULL,
  `plant_id` varchar(255) NOT NULL,
  PRIMARY KEY (`farm_id`,`plant_id`),
  KEY `fk_farm_plant_idx` (`plant_id`),
  CONSTRAINT `fk_farm_plant_farm` FOREIGN KEY (`farm_id`) REFERENCES `farms` (`_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_farm_plant_plant` FOREIGN KEY (`plant_id`) REFERENCES `plants` (`_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `farm_plant`
--

LOCK TABLES `farm_plant` WRITE;
/*!40000 ALTER TABLE `farm_plant` DISABLE KEYS */;
/*!40000 ALTER TABLE `farm_plant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `farms`
--

DROP TABLE IF EXISTS `farms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `farms` (
  `_id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `farms`
--

LOCK TABLES `farms` WRITE;
/*!40000 ALTER TABLE `farms` DISABLE KEYS */;
INSERT INTO `farms` VALUES ('FM01','Badger Hill Farm','123A, Jalan Badger Hill, 46400 Petaling Jaya, Selangor.'),('FM02','Wits End Farm','321B, Jalan Wits End, 14200 Nibong Tebal, Perak.'),('FM03','Tropical Fruit Farm','Jalan Teluk Bahang, 11000 Pulau Pinang'),('FM04','Anglo Chermor Farm','468,Jalan Sungai Siput, 31200, 31200 Chemor, Perak'),('FM05','HengHuat Farm','No. 4292 Jln Kampong Benggali Kampong Benggali 12200 Butterworth Pulau Pinang'),('FM06','FaFaFa Farm','Jalan Pandak Mayah 6, Pusat Bandar Kuah, 07000, Kedah'),('FM07','Jordan Farm','No. 01 Jln Todak Pusat Bandar Seberang Jaya, 13700 Perai, Pulau Pinang'),('FM08','Allen Farm','471, Jln Bukit Bendera, Jln Stesen, 11500 George Town, Pulau Pinang'),('FM09','Jun Eng Farm','188C,Jalan Sungai Bakap, Nibong Tebal, Pulau Pinang.'),('FM10','Yi Xuan Farm','891A, Jalan Kerian, Bagan Samak, Kedah');
/*!40000 ALTER TABLE `farms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fertilizers`
--

DROP TABLE IF EXISTS `fertilizers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fertilizers` (
  `_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `unitType` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fertilizers`
--

LOCK TABLES `fertilizers` WRITE;
/*!40000 ALTER TABLE `fertilizers` DISABLE KEYS */;
INSERT INTO `fertilizers` VALUES ('F01','Anhydrous Ammonia','pack'),('F02','Aqua Ammonia','pack'),('F03','Ammonium Nitrate','pack'),('F04','Ammonium Nitrate Solution','pack'),('F05','Amm Nitrate-Limestone Mixtures','pack'),('F06','Ammonium Nitrate - Sulfate','pack'),('F07','Ammonium Polysulfide','pack'),('F08','Ammonium Sulfate','pack'),('F09','Ammonium Sulfate Solution','pack'),('F10','Ammonium Sulfate - Nitrate','pack'),('F100','Copper Oxide, Black','pack'),('F101','Copper Oxide, Red','pack'),('F102','Copper Sulfate','pack'),('F103','Copper Chelate','pack'),('F104','Copper Compound','pack'),('F105','Ferric Oxide','pack'),('F106','Ferric Sulfate','pack'),('F107','Ferrous Sulfate','pack'),('F108','Iron Chelate','pack'),('F109','Iron Compound','pack'),('F11','Ammonium Sulfate - Urea','pack'),('F110','Gypsum (Calcium Sulfate)','pack'),('F111','Calcium Chelate','pack'),('F112','Calcium Sulfate (Hydrous)','pack'),('F113','Lime Sulfur Solution','pack'),('F114','Magnesia (Magnesium Oxide)','pack'),('F115','Epsom Salt (Magnesium Sulfate)','pack'),('F116','Magnesium Chelate','pack'),('F117','Manganese Agstone','pack'),('F118','Manganese Chelate','pack'),('F119','Manganese Oxide','pack'),('F12','Ammonium Thiosulfate','pack'),('F120','Manganese Slag','pack'),('F121','Manganese Sulfate','pack'),('F122','Manganous Oxide','pack'),('F123','Sodium Molybdonate','pack'),('F124','Soil Amendment','pack'),('F125','Soil Additive','pack'),('F126','Soil Conditioner','pack'),('F127','Potting Soil','pack'),('F128','Sulfur','pack'),('F129','Calcium Chloride','pack'),('F13','Calcium Ammonium Nitrate','pack'),('F130','Sulfuric Acid','pack'),('F131','Zinc Oxide','pack'),('F132','Zinc Oxysulfate','pack'),('F133','Zinc Sulfate','pack'),('F134','Zinc Sulfate Solution','pack'),('F135','Zinc Chelate','pack'),('F136','Sec./Micro-Code Unknown','pack'),('F137','Sec./Micro-Code/Grade Unknown','pack'),('F138','Calcium Oxide (Burnt)','pack'),('F139','Calcium Hydroxide (Hydrate)','pack'),('F14','Calcium Cyanamide','pack'),('F140','Standard Dolomite','pack'),('F141','Dolomitic Lime (75% Neutral)','pack'),('F142','Standard Calcite','pack'),('F143','Calcitic Lime (75% Neutral)','pack'),('F144','Lime Product-Code Unknown','pack'),('F145','Lime Product-Code/Grade Unknown','pack'),('F146','Dolomitic & Calcitic Blend (Pelletized)','pack'),('F147','Lime Suspensions','pack'),('F148','Non-Lime Filler (Water, Sand, Etc.)','pack'),('F15','Calcium Nitrate','pack'),('F16','Calcium Nitrate - Urea','pack'),('F17','Ferrous Ammonium Sulfate','pack'),('F18','Magnesium Nitrate','pack'),('F19','Nitric Acid','pack'),('F20','Nitrogen Solution < 28%','pack'),('F21','Nitrogen Solution 28%','pack'),('F22','Nitrogen Solution 30%','pack'),('F23','Nitrogen Solution 32%','pack'),('F24','Nitrogen Solution > 32%','pack'),('F25','Sodium Nitrate','pack'),('F26','Sulfur Coated Urea','pack'),('F27','Urea','pack'),('F28','Urea Solution','pack'),('F29','Urea - Formaldehyde','pack'),('F30','Zinc Ammonium Sulfate Solution','pack'),('F31','Zinc Manganese Amm Sulfate','pack'),('F32','Nitrogen Product - Code Unknown','pack'),('F33','Nitrogen - Code/Grade Unknown','pack'),('F34','Ammonium Metaphosphate','pack'),('F35','Ammonium Phosphate','pack'),('F36','Diammonium Phosphate','pack'),('F37','Ammonium Polyphosphate','pack'),('F38','Basic Lime Phosphate','pack'),('F39','Ammonium Phosphate Nitrate','pack'),('F40','Ammonium Phosphate Sulfate','pack'),('F41','Basic Slag','pack'),('F42','Monoammonium Phosphate','pack'),('F43','Bone Black Spent','pack'),('F44','Bone Meal, Raw','pack'),('F45','Bone Meal, Steamed','pack'),('F46','Bone, Precipitated','pack'),('F47','Calcium Metaphosphate','pack'),('F48','Colloidal Phosphate (Soft)','pack'),('F49','Limestone, Phosphatic','pack'),('F50','Magnesium Phosphate','pack'),('F51','Nitric Phosphate','pack'),('F52','Phosphate Rock','pack'),('F53','Phosphoric Acid','pack'),('F54','Liquid Amm Polyphosphate','pack'),('F55','Precipitated Phosphate','pack'),('F56','Superphosphate, Normal','pack'),('F57','Superphosphate, Enriched','pack'),('F58','Superphosphate, Triple','pack'),('F59','Superphosphoric Acid','pack'),('F60','Phosphate Product - Code Unknown','pack'),('F61','Phosphate - Code/Grade Unknown','pack'),('F62','Lime-Potash Mixtures','pack'),('F63','Manure Salts','pack'),('F64','Potash Suspensions','pack'),('F65','Potassium Carbonate','pack'),('F66','Muriate of Potash 60%','pack'),('F67','Muriate of Potash 62%','pack'),('F68','Potassium - Magnesium Sulfate','pack'),('F69','Potassium - Metaphosphate','pack'),('F70','Potassium Nitrate','pack'),('F71','Potassium - Sodium Nitrate','pack'),('F72','Potassium Sulfate','pack'),('F73','Tobacco Stems','pack'),('F74','Potash Product - Code Unknown','pack'),('F75','Potash - Code/Grade Unknown','pack'),('F76','Blood, Dried','pack'),('F77','Castor Pomace','pack'),('F78','Cocoa Shell Meal','pack'),('F79','Cocoa Tankage','pack'),('F80','Compost','pack'),('F81','Cottonseed Meal','pack'),('F82','Fish Scrap','pack'),('F83','Guano','pack'),('F84','Manure','pack'),('F85','Peat','pack'),('F86','Sewage Sludge, Activated','pack'),('F87','Sewage Sludge, Heat-Dried','pack'),('F88','Sewage Sludge, Other','pack'),('F89','Soybean Meal','pack'),('F90','Tankage, Animal','pack'),('F91','Tankage, Process','pack'),('F92','Linseed Meal','pack'),('F93','Tung Pumace','pack'),('F94','Natural Org-Code Unknown','pack'),('F95','Natural Org-Code/Grade Unknown','pack'),('F96','Aluminum Sulfate','pack'),('F97','Borax','pack'),('F98','Brucite (Magnesium Hydroxide)','pack'),('F99','Cobalt Sulfate','pack');
/*!40000 ALTER TABLE `fertilizers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pesticides`
--

DROP TABLE IF EXISTS `pesticides`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pesticides` (
  `_id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `unitType` varchar(255) NOT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pesticides`
--

LOCK TABLES `pesticides` WRITE;
/*!40000 ALTER TABLE `pesticides` DISABLE KEYS */;
INSERT INTO `pesticides` VALUES ('PE1','Abamectin 1.8% EC','volume'),('PE10','Alazine','volume'),('PE100','Emulsamine BK','volume'),('PE101','Emulsamine E_3','volume'),('PE102','Estone','volume'),('PE103','Fernesta','volume'),('PE104','Fernimine','volume'),('PE105','Fernoxone','volume'),('PE106','Ferxone','volume'),('PE107','Lawn_Keep','volume'),('PE108','Macondray','volume'),('PE109','Pennamine D','volume'),('PE11','ALA','volume'),('PE110','Planotox','volume'),('PE111','Plantgard','volume'),('PE112','Tributon','volume'),('PE113','Weed_B_Gon','volume'),('PE114','Weedar','volume'),('PE115','Weedone','volume'),('PE116','Weedmaster','volume'),('PE117','Weed & Feed','volume'),('PE118','Weedatul','volume'),('PE119','Chipco Turf Herbicide D','volume'),('PE12','Lozo','volume'),('PE120','DMA_4','volume'),('PE121','Esterone 99','volume'),('PE122','Formula 40','volume'),('PE123','Spritz_Hormit','volume'),('PE124','Weed_Ag_Bar','volume'),('PE125','Weedez Wonder Bar','volume'),('PE126','Basagran','volume'),('PE127','Acme Super Brush Killer 875','volume'),('PE128','U 46 DP','volume'),('PE129','Duplosan DP_D','volume'),('PE13','Lariat','volume'),('PE130','Duplasan KV_ Combi','volume'),('PE131','Chipco Turf Kleen','volume'),('PE132','2 Plus 2','volume'),('PE133','Actril DS','volume'),('PE134','Mad','volume'),('PE135','Gordon’s Vegemec','volume'),('PE136','Vegetation Killer','volume'),('PE137','Lentemul','volume'),('PE14','Marksman','volume'),('PE15','Freedom','volume'),('PE16','Micro_Tech','volume'),('PE17','Nudor Extra','volume'),('PE18','Bronco','volume'),('PE19','Alanex','volume'),('PE2','Acephate 75% SP','volume'),('PE20','Bullet','volume'),('PE21','Stake','volume'),('PE22','Shroud','volume'),('PE23','Temik','volume'),('PE24','UC21149','volume'),('PE25','Aatrex','volume'),('PE26','Aktikon','volume'),('PE27','Atrazinax','volume'),('PE28','Atratol','volume'),('PE29','Fenamin','volume'),('PE3','Acephate 95% SG','volume'),('PE30','Aatrex','volume'),('PE31','Prozine','volume'),('PE32','Gesaprim','volume'),('PE33','Zeaphos','volume'),('PE34','Nudor Extra','volume'),('PE35','Atramet Combi','volume'),('PE36','Crisazin_Crisatrina','volume'),('PE37','Kombi','volume'),('PE38','Drexel','volume'),('PE39','Rhino','volume'),('PE4','Acetamiprid 20% SP','volume'),('PE40','Farmco Anizine','volume'),('PE41','Aaa Flowable','volume'),('PE42','Marksman','volume'),('PE43','Primextra','volume'),('PE44','Bicep','volume'),('PE45','Conquest','volume'),('PE46','Candex','volume'),('PE47','Extrazine','volume'),('PE48','Vestal','volume'),('PE49','Rapuzin','volume'),('PE5','Alpha-Cypermethrin 10%WP','volume'),('PE50','Pramatol','volume'),('PE51','Surpass','volume'),('PE52','Bullet','volume'),('PE53','Buctril','volume'),('PE54','Laddock','volume'),('PE55','Bay 70143','volume'),('PE56','Crisfuran','volume'),('PE57','Curaterr','volume'),('PE58','Yaltox','volume'),('PE59','Furadan','volume'),('PE6','Alphamethrin 10% EC','volume'),('PE60','Carbodan','volume'),('PE61','Carbosip','volume'),('PE62','Chinufur','volume'),('PE63','Kenofuran','volume'),('PE64','Niagara','volume'),('PE65','Forchlor','volume'),('PE66','Kill_Ko','volume'),('PE67','Sydane','volume'),('PE68','Belt','volume'),('PE69','Chlor Kil','volume'),('PE7','Lasso','volume'),('PE70','Chlorotox','volume'),('PE71','Corodane','volume'),('PE72','Gold Crest C_100','volume'),('PE73','Kilex Lindane','volume'),('PE74','Kypchlo','volume'),('PE75','Octachlor','volume'),('PE76','Synklor','volume'),('PE77','Termided','volume'),('PE78','Topiclor 20','volume'),('PE79','Velsicol 1068','volume'),('PE8','Pillarzo','volume'),('PE80','Aspon_chlordate','volume'),('PE81','Ortho_Klor','volume'),('PE82','Niran','volume'),('PE83','Termide','volume'),('PE84','Chlorohepton','volume'),('PE85','4_Dichlorophenoxy acetic acid','volume'),('PE86','Acme Main 4','volume'),('PE87','Acme Butyl Ester 4','volume'),('PE88','Acme LV 4','volume'),('PE89','Acme LV 6','volume'),('PE9','Alatox_480','volume'),('PE90','Agrotect','volume'),('PE91','Amoxone','volume'),('PE92','Aquakleen','volume'),('PE93','Chlorozxone','volume'),('PE94','Croprider','volume'),('PE95','Crossbow','volume'),('PE96','D50','volume'),('PE97','Dinoxol','volume'),('PE98','DMA_4','volume'),('PE99','Dormone','volume');
/*!40000 ALTER TABLE `pesticides` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plants`
--

DROP TABLE IF EXISTS `plants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plants` (
  `_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `unitType` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plants`
--

LOCK TABLES `plants` WRITE;
/*!40000 ALTER TABLE `plants` DISABLE KEYS */;
INSERT INTO `plants` VALUES ('P01','frican sheepbush _ Pentzia incana','mass'),('P02','Alder _ Alnus','mass'),('P03','Black alder _ Alnus glutinosa, Ilex verticillata','mass'),('P04','Common alder _ Alnus glutinosa','mass'),('P05','False alder _ Ilex verticillata','mass'),('P06','Gray alder _ Alnus incana','mass'),('P07','Speckled alder _ Alnus incana','mass'),('P08','White alder _ Alnus incana, Alnus rhombifolia, Ilex verticillata','mass'),('P09','Almond _ Prunus dulcis','mass'),('P10','Aloe vera _ Aloe vera','mass'),('P100','Buckeye (California buckeye) _ Aesculus californica','mass'),('P101','Buckeye _ Aesculus spp.','mass'),('P102','Buffalo weed _ Ambrosia trifida','mass'),('P103','Bugle _ Ajuga reptans','mass'),('P104','Butterfly flower _ Asclepias syriaca','mass'),('P105','Butterfly weed _ Asclepias tuberosa','mass'),('P11','Amaranth _ Amaranthus','mass'),('P12','Foxtail amaranth _ Amaranthus caudatus','mass'),('P13','Ambrosia','mass'),('P14','Tall ambrosia _ Ambrosia trifida','mass'),('P15','Amy root _ Apocynum cannabinum','mass'),('P16','Angel trumpet _ Brugmansia suaveolens','mass'),('P17','Apple _ Malus domestica','mass'),('P18','Apricot _ Prunus armeniaca','mass'),('P19','Arfaj _ Rhanterium epapposum','mass'),('P20','Arizona sycamore _ Platanus wrighitii','mass'),('P21','Arrowwood _ Cornus florida','mass'),('P22','Indian arrowwood _ Cornus florida','mass'),('P23','Ash _ Fraxinus spp.','mass'),('P24','Black ash _ Acer negundo, Fraxinus nigra','mass'),('P25','Blue ash _ Fraxinus quadrangulata','mass'),('P26','Cane ash _ Fraxinus americana','mass'),('P27','European ash _ Fraxinus excelsior[1]','mass'),('P28','Green ash _ Fraxinus pennsylvanica lanceolata','mass'),('P29','Maple ash _ Acer negundo','mass'),('P30','Red ash _ Fraxinus pennsylvanica lanceolata','mass'),('P31','River ash _ Fraxinus pennsylvanica','mass'),('P32','Swamp ash _ Fraxinus pennsylvanica','mass'),('P33','White ash _ Fraxinus americana','mass'),('P34','Water ash _ Acer negundo, Fraxinus pennsylvanica','mass'),('P35','Azolla _ Azolla','mass'),('P36','Carolina azolla _ Azolla caroliniana','mass'),('P37','Bamboo _ bamboosa ardinarifolia','mass'),('P38','Banana _ mainly Musa × paradisica','mass'),('P39','Baobab _ Adansonia','mass'),('P40','Bay _ Laurus spp. or Umbellularia spp.','mass'),('P41','Bay laurel _ Laurus nobilis (culinary)','mass'),('P42','California bay _ Umbellularia californica','mass'),('P43','Bean _ Fabaceae, specifically Phaseolus spp.','mass'),('P44','Bearberry _ Ilex decidua','mass'),('P45','Bear corn _ Veratrum viride','mass'),('P46','Beech _ Fagus','mass'),('P47','Bindweed','mass'),('P48','Blue bindweed _ Solanum dulcamara','mass'),('P49','Birds nest _ Daucus carota','mass'),('P50','Birds nest plant _ Daucus carota','mass'),('P51','Bird of paradise _ Strelitzia reginae','mass'),('P52','Birch _ Betula spp.','mass'),('P53','Black birch _ Betula lenta, Betula nigra','mass'),('P54','Bolean birch _ Betula papyrifera','mass'),('P55','Canoe birch _ Betula papyrifera','mass'),('P56','Cherry birch _ Betula lenta','mass'),('P57','European weeping birch _ Betula pendula','mass'),('P58','European white birch _ Betula pendula','mass'),('P59','Gray birch _ Betula alleghaniensis','mass'),('P60','Mahogany birch _ Betula lenta','mass'),('P61','Paper birch _ Betula papyrifera','mass'),('P62','Red birch _ Betula nigra','mass'),('P63','River birch _ Betula nigra, Betula lenta','mass'),('P64','Silver birch _ Betula papyrifera','mass'),('P65','Spice birch _ Betula lenta','mass'),('P66','Sweet birch _ Betula lenta','mass'),('P67','Water birch _ Betula nigra','mass'),('P68','Weeping birch _ Betula pendula','mass'),('P69','White birch _ Betula papyrifera, Betula pendula','mass'),('P70','Yellow birch _ Betula alleghaniensis','mass'),('P71','Bittercress _ Barbarea vulgaris, Cardamine bulbosa, Cardamine hirsuta','mass'),('P72','Hairy bittercress _ Cardamine hirsuta','mass'),('P73','Bittersweet _ Solanum dulcamara','mass'),('P74','Trailing bittersweet _ Solanum dulcamara','mass'),('P75','Bitterweed _ Helenium amarum','mass'),('P76','Blackberry _ Rubus spp., Rubus pensilvanicus, Rubus occidentalis','mass'),('P77','Hispid swamp blackberry _ Rubus hispidus','mass'),('P78','Pennsylvania blackberry _ Rubus pensilvanicus','mass'),('P79','Running swamp blackberry _ Rubus hispidus','mass'),('P80','Black cap _ Rubus occidentalis','mass'),('P81','Black_eyed Susan _ Rudbeckia hirta, Rudbeckia fulgida','mass'),('P82','Blackhaw _ Viburnum prunifolium','mass'),('P83','Blackiehead _ Rudbeckia hirta','mass'),('P84','Black_weed _ Ambrosia artemisiifolia','mass'),('P85','Blueberry _ Vaccinium (Cyanococcus) spp.','mass'),('P86','Bluebell _ Hyacinthoides non_scripta','mass'),('P87','Blue_of_the_heavens _ Allium caeruleum','mass'),('P88','Bow_wood _ Maclura pomifera','mass'),('P89','Box _ Buxus','mass'),('P90','False box _ Cornus florida','mass'),('P91','Boxelder _ Acer negundo','mass'),('P92','Boxwood _ Buxus, Cornus florida','mass'),('P93','False boxwood _ Cornus florida','mass'),('P94','Brier','mass'),('P95','Sand brier _ Solanum carolinense','mass'),('P96','Brittlebush _ Encelia farinosa','mass'),('P97','Broadleaf _ Plantago major','mass'),('P98','Brown Betty _ Rudbeckia hirta','mass'),('P99','Brown_eyed susan _ Rudbeckia hirta, Rudbeckia triloba','mass');
/*!40000 ALTER TABLE `plants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_farm`
--

DROP TABLE IF EXISTS `user_farm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_farm` (
  `user_id` varchar(255) NOT NULL,
  `farm_id` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`,`farm_id`),
  KEY `fk_farm_idx` (`farm_id`),
  CONSTRAINT `fk_farm` FOREIGN KEY (`farm_id`) REFERENCES `farms` (`_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_farm`
--

LOCK TABLES `user_farm` WRITE;
/*!40000 ALTER TABLE `user_farm` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_farm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `_id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phoneNumber` varchar(255) NOT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('FMR01','Stephen','stephen1@gmail.com','V$$yj/Sf7x[t$4hd','60121212121'),('FMR02','Curry','currycurry2@outlook.com',']@8yvMGTk$sbWr9','60122323232'),('FMR03','Lebron','lebron3@hotmail.com','NB/&%Z\"5/jtG:T7k','60175263542'),('FMR04','James','james12345@gmail.com','W>eu;67H!)bE`EK8','60125874596'),('FMR05','Kevin','kevin0987@hotmail.com','c_7e\"5_Y@X7Rkd8p','60176325963'),('FMR06','Durant','durant7463@gmai.com','6$NSwSG@4^Df$%2\"','60187452369'),('FMR07','Micheal','micheal231@outlook.com',']!vCgMRS2xnT;7M(','601125485632'),('FMR08','Jordan','jordan567@gmail.com','gB\"D:JK>>6)J.>vK','60148572369'),('FMR09','Dwyane','dwyane5643@gmail.com','Sk$(5@,^XQMLd','60198574859'),('FMR10','Wade','wade9809@outlook.com','>]8Zk3>5PGbzh^g!','60124785963'),('FMR100','Amara','Amara99@gmail.com','*gw_S7h*+?QmHtQa','601475900041'),('FMR101','Aatrox','Aatrox888@gmail.com','sfjHv]6`JLXr23ez`','601475900042'),('FMR102','Ahri','Ahri777@gmail.com','784{:@/3]rf~xYSe=','601475900043'),('FMR103','Akali','Akali456@gmail.com','6x`tfnBn.-m\"]h&Mv','601475900044'),('FMR104','Akshan','Akshand4444@gmail.com','Raar&2&P~a/aC~FPz','601475900045'),('FMR105','Alistar','Alistar447@gmail.com','>UN?,?b<sd,c53','601475900046'),('FMR106','Anivia','Anivia5555@gmail.com','m-xt6BkL6(gSakS<E','601475900047'),('FMR107','Aphelios','Aphelios4722@gmail.com','A5BrJV22_$X{n$yz}','601475900048'),('FMR108','Blitzcrank','Blitzcrank4561@gmail.com','*gw_S7h*+?QmHtQi','601475900049'),('FMR109','Diana','Diana4555@gmail.com','}b9Rk;q`B;b%6^qTB','601475900050'),('FMR11','Alice','aliceaaa@gmail.com','FmLpY~72Nhp:<(S,','60123636356'),('FMR110','Caitlyn','Caitlyn741852@gmail.com','MdhF4=qg\"Sr+<yNBm','601475900052'),('FMR111','Camille','Camille8222@gmail.com','4T&$r+9{>RT#B[H`}','601475900053'),('FMR112','Gangplank','Gangplank7845@gmail.com','[@EZb5@43{sCqpC<r','601475900054'),('FMR113','Garen','Garen74282@gmail.com','*sf_S7h*+?QmHtQa','601475900055'),('FMR114','Gwen','Gwen8522@gmail.com','*gy_S7h*+?QmAZEb','601475900056'),('FMR115','Jayce','Jayce82132@gmail.com','*ijjd_S7h*+?QHFDQc','601475900057'),('FMR116','Jean','jean@gmail.com','*gh_S7h*+?QSDFQd','601475900058'),('FMR117','Barbara','barbara@gmail.com','*yw_S7h*+?QxAsWe','601475900059'),('FMR118','Kamisato Ayaka','ayaka@outlook.com','*ow_S7h*+?QSStQf','601475900060'),('FMR119','Kamisato Ayato','ayato@outlook.com','*qw_S7h*+?QwetQg','601475900061'),('FMR12','Bob','bobbobbob@hotmail.com','W%3,;\"TyLHt#M=b>','60178932653'),('FMR120','Razor','razor@hotmail.com','*gw_S7h*+?QQWEQh','601475900062'),('FMR121','Hu Tao','hutao@gmail.com','*gw_S7h*+?QaaaQi','601475900063'),('FMR122','Ke Qing','keqing@gmail.com','*gw_S7h*+?QmHtQj','601475900064'),('FMR123','Raiden Shogun','raiden@hotmail.com','*gw_S7h*+?QmEWSk','601475900065'),('FMR124','Albedo','albedo@outlook.com','*gw_S7h*+?QJNHQl','601475900066'),('FMR125','Kokomi','kokomi@outlook.com','*gw_S7h*+?QASDQm','601475900067'),('FMR126','Itto','itto@gamil.com','*gw_S7h*+?QmHtQn','601475900068'),('FMR127','Kazuha','kazuha@gmail.com','*gw_S7h*+?QmSSo','601475900069'),('FMR128','Mona','mona@hotmail.com','*gw_S7h*+?OKHtQp','601475900070'),('FMR129','Sucrose','sucrose@gamil.com','*gw_S5h*+?asdtQq','601475900071'),('FMR13','Carl','carl879@outlook.com','K%MfBH/b_j7($eV','60127774859'),('FMR130','Venti','venti@gmail.com','*gw_S7h*+?QSStQr','601475900072'),('FMR131','Yi Heng','yiheng@hotmail.com','*gw_S7h*+?QSHtQ','601475900073'),('FMR132','Jun Ling','jl0000@gmail.com','*gw_S7h*+?QsHt','601475900074'),('FMR133','Wen Xuan','wxuan@hotmail.com','*gw_S7h*+?okn','601475900075'),('FMR134','Shao Zhuan','shaozhuan@gmail.com','*gw_S7h*+?EBNAS','601475900076'),('FMR135','Lee Quan','lee@gmail.com','*gw_S7h*+??HtQa','601475900077'),('FMR136','Tiam Keng','tiamkeng88@gmail.com','*gw_S7h*+?mHtQa','601475900078'),('FMR137','Ming Jun','mingjun@hotmail.com','*gw_S7h*+?????45','601475900079'),('FMR138','Chee King','theking77@gmail.com','*gw_S7h*?qqqqq45','601475900080'),('FMR139','Chee Sheng','cheesheng99@gmail.com','*gw_S7*+?QQQQQ','601475900081'),('FMR14','Emily','emilyabc@gmail.com','vxh]mUk)`>3:v=','60145252520'),('FMR140','Pei Heng','peiheng123@gmail.com','*gwh*+?DFFFF456','601475900082'),('FMR141','Pei Jie','peijie321@gmail.com','*gw_S7hQmHtQa','601475900083'),('FMR142','Wei Jie','weijie999@hotmail.com','*gw_S7h*+?QWE95','601475900084'),('FMR143','Ren Di','rendi@gmail.com','*gw_S7h*+?mHtQa','601475900085'),('FMR144','Choon Yuan','beng999@gmail.com','*gw_S7h*+?tQa11','601475900086'),('FMR145','Sheng Hao','engsh@hotmail.com','S7h*+?QmHtQa86','601475900087'),('FMR146','Wei Hong','weihong123@gmail.com','*gw_S7h*+?QmHt1s','601475900088'),('FMR147','Wei Hao','weihao333@gmail.com','*gw_S7h*+?1mHtQt','601475900089'),('FMR148','Kai Liang','kailiang3@gmail.com','*gw_S7h*+?2mHtQu','601475900090'),('FMR149','Yao Sheng','yaosheng111@gmail.com','*gw_S7h*+?3mHtQv','601475900091'),('FMR15','John','johnjohn@outlook.com','!N_pCR7!k35&N69%','60120302065'),('FMR150','Tryndamere','tryndamere788@gmail.com','*gw_S7h*+?4mHtQw','601475900092'),('FMR151','Wei Liang','weiliang23@gmail.com','*gw_S7h*+?5mHtQx','601475900093'),('FMR152','Zhi Jie','zhijie322@gmail.com','*gw_S7h*+?6mHtQy','601475900094'),('FMR153','Yi Xiang','yixiang166@gmail.com','*gw_S7h*+?7mHtQz','601475900095'),('FMR154','Yu Xiang','yuxiang223@gmail.com','*gw_S7h*+?8mHtQA','601475900096'),('FMR155','Yew Wei','yewwei99@gmail.com','*gw_S7h*+?9mHtQB','601475900097'),('FMR156','Min Shen','minshen00@gmail.com','*gw_S7h*+?Q1HtQC','601475900098'),('FMR157','Bo Hau','bohau122@gmail.com','*gw_S7h*+?Q2HtQD','601475900099'),('FMR158','Andy','andy77@gmail.com','*gw_S7h*+?Q3HtQE','601475900100'),('FMR159','Eden','eden9988@gmail.com','*gw_S7h*+?Q4HtQF','601475900101'),('FMR16','Johnson','johnson888@hotmail.com','N.:t:8EZ585;^V<p','60189696325'),('FMR160','Yan Hao','yanhao2235@gmail.com','*gw_S7h*+?Q5HtQG','601475900102'),('FMR17','Calvin','calvin333@gmail.com','ywxg>:7QJz&53-n]','601125232369'),('FMR18','Jackson','jackson321@gmail.com','2S9,vLbz~:K9::FZ','60171452036'),('FMR19','Bryan','bryanxyz@gmail.com','xHFADRANgX9.?G9<','60189998877'),('FMR20','Jason','jasonwhereareyou@gmail.com','@q7&Q];Ce<8:n!','60145785632'),('FMR21','Rachel','rachelwong@hotmail.com','J_bQ2<yBJmypXsyN','60123456789'),('FMR22','Elynn','elynnkee4@gmail.com','(u*dfR[/)vZxv89','601143658709'),('FMR23','Elaine','elaineng66@outlook.com','K5+e3J&aahQA<2{','60115599443'),('FMR24','Megan','megan_liew2@yahoo.com',':Q`5.(n^3QG^/t_7','60117894521'),('FMR25','Lucas','iamlucas@gmail.com','(}93e9e=.W~PUg%*','60128903477'),('FMR26','Cecelia','ceceliabulb@hotmail.com','%b{rpBMS4f(^g3Em','60129976653'),('FMR27','Carven','carvendummy123@hotmail.com',',z&Ppr2k^RUszs<','60175464886'),('FMR28','Kason','kasonwithyou@outlook.com','t/9Ftd_tWXn97&,m','601278786543'),('FMR29','Melvin','melvinewe90@gmail.com','d?EMz~;:F?fjnP9w','60195476985'),('FMR30','Felicia','feliciahuihui@gmail.com','%!e2tX]pD&xUuT@?','60186598743'),('FMR31','Stanley','stanley.22@gmail.com','~egpBRKB2m,M7/q','60174534889'),('FMR32','Brandon','brandonlone@hotmail.com','ckt~/8nfK[eC62/','60127893426'),('FMR33','Coby','cobyplane994@outlook.com','M?G}am9ZSE5ZtUJF','60147549353'),('FMR34','Frankie','frankietan_08@gmail.com','`;HaV6Mk^#^_dC@W','60185743954'),('FMR35','Ray','raydu@gmail.com','u5{E!*6h35MGKfn?','60128957329'),('FMR36','Crown','crown_official@gmail.com','kc3MVcm/Y_G-E7u','60119257494'),('FMR37','Lala','lalaport789@gmail.com','<2n`u(Jy&`PGY:p','60109341759'),('FMR38','Chickie','mynameischickie@outlook.com','!7R9f$at7tF@.!.x','60102039458'),('FMR39','Bellie','bel484lie@gmail.com','$C8~QWg74W#pH{e$','60173049583'),('FMR40','Oxford','oxford_best@hotmail.com','7(Yd}Uy5W7MDF~F[','601475937543'),('FMR41','Amber','amber41@gmail.com','8#[7W&#afxH8}]eX','60125891160'),('FMR42','Belle','belle24@gmail.com',']pp+(zRwM7_f<})','60156781160'),('FMR43','Lovell','lovell@yahoo.com','JRJy$w}wc!5yz5jp','60126110689'),('FMR44','Caven','caven3p@hotmail.com','Q@9C.p@}87~]a}_&','60125875129'),('FMR45','Danny','danny3422@gmail.com',')9Y\"CCxJxKBL>9H`','60187521923'),('FMR46','Pendy','pendyxlim@hotmail.com','{X>&c/@nG8,y5@6\"','60199672256'),('FMR47','Veron','veron6805@hotmail.com','%LKzk#y:>5K3Fqp','60134457688'),('FMR48','Owen','owen4884@gmail.com','8nGw/@:cF{b9wU7A','60198572310'),('FMR49','Eddie','eddie561@gmail,com','-bc]5/M~\"25W8mG?','60123343889'),('FMR50','Gladish','gladish891@hotmail.com',']su.U`/29}wS~Zv\"','60127812590'),('FMR51','Chris','chris789@yahoo.com','^.8`ZTxnV%}d%:s_','60128877552'),('FMR52','William','williamsim@gmail.com',';*b@P~d!/V5d)}KV','60125871180'),('FMR53','Smith','smith889@gmail.com','FGWBr>!sf9/q\".#','60125322109'),('FMR54','Charly','charly8898@hotmail.com','(L3U3GVqJ_:y.fEw','60126778879'),('FMR55','Angel','angel7881@yahoo.com','}J7:unbacgZS[?4`','60123321779'),('FMR56','Lawrence','lawrence1312@gmail.com','ekyS3{mBZu4Q3RwG','60128910343'),('FMR57','Charlie','charlie9412@hotmail.com','(9QP~C5MgS>[.Zr=','60174827910'),('FMR58','Sheeran','edsheeran88@gmail,com','RB<)76C)*GFc8u%t','60167251302'),('FMR59','Lisa','lalisalsa66@hotmail.com','j<]_wTzfa_ND2@\"','60166677890'),('FMR60','Jack','Jack@gmail.com','JD=wEp`j2X:y2a;','601475900001'),('FMR61','Grayson','Grayson22@gmail.com','_+T(<#LGAc3`u@ZLQ','601475900002'),('FMR62','Scarlett','Scarlett11@gmail.com','(FL3sRffAxR/]cDZ','601475900003'),('FMR63','Carter','Carter55@gmail.com','VXA:3]Y/z#8,c%9F=','601475900004'),('FMR64','Hazel','Hazel@gmail.com','sfjHv]6`JLXr23ez`','601475900005'),('FMR65','Willow','Willow@gmail.com','7dS{:@/3]rf~xYSe=','601475900006'),('FMR66','Hudson','Hudson@gmail.com','6$`tfnBn.-m\"]h&Mv','601475900007'),('FMR67','Madison','Madison22@gmail.com','Rbbr&2&P~a/aC~FPz','601475900008'),('FMR68','Maverick','Maverick520@gmail.com','>UN?,?b<sd,c53','601475900009'),('FMR69','Jaxon','Jaxon2000@gmail.com','m-xt6BkL6(gSakS<E','601475900010'),('FMR70','Waylon','Waylon52@gmail.com','A5BrJV22_$X{n$yz}','601475900011'),('FMR71','Addison','Addison88@gmail.com','BYJ`88^qh`:5x&+,3','601475900012'),('FMR72','Colton','Colton888@gmail.com','}b9Rk;q`B;b%6^qTB','601475900013'),('FMR73','Hailey','Hailey77@gmail.com','MdhF4=qg\"Sr+<yNBm','601475900014'),('FMR74','Landon','Landon@gmail.com','4T&$r+9{>RT#B[H`}','601475900015'),('FMR75','Audrey','Audreyyy@gmail.com','[@EZb5@43{sCqpC<r','601475900016'),('FMR76','Ella','Ella4545@gmail.com','3(Tb!f6AkKCz<\"7]J','601475900017'),('FMR77','Brooklyn','Brooklyn2323@gmail.com','zP(`3byG9UXABjq8?','601475900018'),('FMR78','Sadie','Sadie7878@gmail.com','(7?<c{@667WJVv]j','601475900019'),('FMR79','Piper','Piper555@gmail.com','q$3)k~A*qX6)/.>9w','601475900020'),('FMR80','Parker','Parker6565@gmail.com','4UX\"Sn4q9B,u4=`V.','601475900021'),('FMR81','Alyona','Alyona121@gmail.com','vV93%-*S!3P-NfcQ','601475900022'),('FMR82','Sextus','Sextus41@gmail.com','7=Df2=cBMt+K&Wxs','601475900023'),('FMR83','Anelia','Anelia32@gmail.com','VmN%*na6_fFWu3p4','601475900024'),('FMR84','Vayu','Vayu11@gmail.com','Fbk$&pFR$Y9@5J%L','601475900025'),('FMR85','Iqaluk','Iqaluk23@gmail.com','EqWyMa7MS@!2WDUY','601475900026'),('FMR86','Tlaloc','Tlaloc112@gmail.com','brtEtC9@3#QepS_@','601475900027'),('FMR87','Adrijan','Adrijan99@gmail.com','-k^3HYrL&F^%HAWt','601475900028'),('FMR88','Seda','Seda33@gmail.com','sA86&UgF=Tn#%GAc','601475900029'),('FMR89','Gabriel','Gabriel67@gmail.com','Gw6B&EBAW73u2$*H','601475900030'),('FMR90','Maria','Maria344@gmail.com','D?D#+8ePG2Sh7VsB','601475900031'),('FMR91','Vittoria','Vittoria332@gmail.com','NF!7W9D-CLv^yjNB','601475900032'),('FMR92','Lugaid','Lugaid544@gmail.com','7E^Zn_$B982nnY6!','601475900033'),('FMR93','Nila','Nila22@gmail.com','565M@y^HPEC&d$j@','601475900034'),('FMR94','Bithiah','Bithiah766@gmail.com','x2n&_DQfTKkaC#D4','601475900035'),('FMR95','Ayame','Ayame11@gmail.com','ExcSE9waBhzN=gzf','601475900036'),('FMR96','Sarita','Sarita36@gmail.com','8aKY99e&Humps*e$','601475900037'),('FMR97','Cornelia','Cornelia64@gmail.com','_CZ&TzA3ArmBrP3j','601475900038'),('FMR98','Talat','Talat88@gmail.com','5WZS$SCWWB7qreN9','601475900039'),('FMR99','Micha','Micha3@gmail.com','hxt^xPF2hcmU9Q6A','601475900040');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-16 13:11:19
