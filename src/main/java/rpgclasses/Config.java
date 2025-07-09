package rpgclasses;

import necesse.engine.GlobalData;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Config {

    private float experienceMod = 1F;
    private int startingExperience = 200;
    private int firstExperienceReq = 200;
    private int experienceReqInc = 50;
    private int squareExperienceReqInc = 10;
    private int cubeExperienceReqInc = 0;

    private int damageTakenModifier = 40;
    private int knockbackGivenModifier = -20;
    private int enemySpawnRateModifier = 50;
    private int enemySpawnCapModifier = 50;
    private int raiderDamageModifier = 60;

    private int healthMobs = 40;

    private int healthForestMobs = 0;
    private int healthSnowMobs = 20;
    private int healthDungeonMobs = 40;
    private int healthPlainsMobs = 60;
    private int healthSwampMobs = 80;
    private int healthDesertMobs = 100;

    private int healthDeepForestMobs = 0;
    private int healthDeepPlainsMobs = 20;
    private int healthDeepSnowMobs = 40;
    private int healthDeepSwampMobs = 60;
    private int healthDeepDesertMobs = 80;
    private int healthTempleMobs = 100;

    private int healthForestIncursionMobs = 100;
    private int healthSnowIncursionMobs = 120;
    private int healthSwampIncursionMobs = 140;
    private int healthDesertIncursionMobs = 160;
    private int healthSlimeCaveIncursionMobs = 180;
    private int healthGraveyardCaveIncursionMobs = 200;
    private int healthSpiderCastleCaveIncursionMobs = 220;
    private int healthCrystalHollowCaveIncursionMobs = 240;

    private int healthUnstableGelSlime = 20;
    private int healthEvilsProtector = 20;
    private int healthQueenSpider = 40;
    private int healthVoidWizard = 60;
    private int healthChieftain = 80;
    private int healthSwampGuardian = 100;
    private int healthAncientVulture = 120;
    private int healthPirateCaptain = 140;
    private int healthReaper = 160;
    private int healthReaperIncursion = 160;
    private int healthTheCursedCrone = 180;
    private int healthCryoQueen = 200;
    private int healthCryoQueenIncursion = 200;
    private int healthPestWarden = 220;
    private int healthPestWardenIncursion = 220;
    private int healthSageAndGrit = 240;
    private int healthSageAndGritIncursion = 240;
    private int healthFallenWizard = 260;
    private int healthMotherSlime = 280;
    private int healthNightSwarmBat = 300;
    private int healthSpiderEmpress = 320;
    private int healthSunlightChampion = 340;
    private int healthMoonlightDancer = 360;
    private int healthCrystalDragon = 380;

    public static Config getConfig() {
        return RPG_Classes.getConfig();
    }

    public Config(String configFileName) {
        String filename = GlobalData.rootPath() + "settings\\rpgclasses\\" + configFileName;
        System.out.println("Loading RPG Classes settings");
        try {
            File file = new File(filename);
            if (!file.exists()) createNewFile(file);

            InputStreamReader isr = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            boolean createFile = loadConfig(br);
            br.close();
            if (createFile) recreateFile(file);

            System.out.println("Loaded file: " + file.toPath());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean loadConfig(BufferedReader br) throws IOException {
        String line;
        line = br.readLine();
        if (shouldUpdateFile(line)) {
            return true;
        }
        while ((line = br.readLine()) != null) {
            if (!line.isEmpty() && !line.startsWith("#")) {
                String[] temp = line.split("=");
                if (temp.length < 2) continue;
                String key = temp[0].trim();
                String value = temp[1].trim();
                switch (key) {
                    case "experienceMod":
                        this.setExperienceMod(Float.parseFloat(value));
                        break;
                    case "startingExperience":
                        this.setStartingExperience(Integer.parseInt(value));
                        break;
                    case "firstExperienceReq":
                        this.setFirstExperienceReq(Integer.parseInt(value));
                        break;
                    case "experienceReqInc":
                        this.setExperienceReqInc(Integer.parseInt(value));
                        break;
                    case "squareExperienceReqInc":
                        this.setSquareExperienceReqInc(Integer.parseInt(value));
                        break;
                    case "cubeExperienceReqInc":
                        this.setCubeExperienceReqInc(Integer.parseInt(value));
                        break;
                    case "damageTakenModifier":
                        this.setDamageTakenModifier(Integer.parseInt(value));
                        break;
                    case "knockbackGivenModifier":
                        this.setKnockbackGivenModifier(Integer.parseInt(value));
                        break;
                    case "enemySpawnRateModifier":
                        this.setEnemySpawnRateModifier(Integer.parseInt(value));
                        break;
                    case "enemySpawnCapModifier":
                        this.setEnemySpawnCapModifier(Integer.parseInt(value));
                        break;
                    case "raiderDamageModifier":
                        this.setRaiderDamageModifier(Integer.parseInt(value));
                        break;
                    case "healthMobs":
                        this.setHealthMobs(Integer.parseInt(value));
                        break;
                    case "healthForestMobs":
                        this.setHealthForestMobs(Integer.parseInt(value));
                        break;
                    case "healthSnowMobs":
                        this.setHealthSnowMobs(Integer.parseInt(value));
                        break;
                    case "healthDungeonMobs":
                        this.setHealthDungeonMobs(Integer.parseInt(value));
                        break;
                    case "healthPlainsMobs":
                        this.setHealthPlainsMobs(Integer.parseInt(value));
                        break;
                    case "healthSwampMobs":
                        this.setHealthSwampMobs(Integer.parseInt(value));
                        break;
                    case "healthDesertMobs":
                        this.setHealthDesertMobs(Integer.parseInt(value));
                        break;
                    case "healthDeepForestMobs":
                        this.setHealthDeepForestMobs(Integer.parseInt(value));
                        break;
                    case "healthDeepPlainsMobs":
                        this.setHealthDeepPlainsMobs(Integer.parseInt(value));
                        break;
                    case "healthDeepSnowMobs":
                        this.setHealthDeepSnowMobs(Integer.parseInt(value));
                        break;
                    case "healthDeepSwampMobs":
                        this.setHealthDeepSwampMobs(Integer.parseInt(value));
                        break;
                    case "healthDeepDesertMobs":
                        this.setHealthDeepDesertMobs(Integer.parseInt(value));
                        break;
                    case "healthTempleMobs":
                        this.setHealthTempleMobs(Integer.parseInt(value));
                        break;
                    case "healthForestIncursionMobs":
                        this.setHealthForestIncursionMobs(Integer.parseInt(value));
                        break;
                    case "healthSnowIncursionMobs":
                        this.setHealthSnowIncursionMobs(Integer.parseInt(value));
                        break;
                    case "healthSwampIncursionMobs":
                        this.setHealthSwampIncursionMobs(Integer.parseInt(value));
                        break;
                    case "healthDesertIncursionMobs":
                        this.setHealthDesertIncursionMobs(Integer.parseInt(value));
                        break;
                    case "healthSlimeCaveIncursionMobs":
                        this.setHealthSlimeCaveIncursionMobs(Integer.parseInt(value));
                        break;
                    case "healthGraveyardCaveIncursionMobs":
                        this.setHealthGraveyardCaveIncursionMobs(Integer.parseInt(value));
                        break;
                    case "healthSpiderCastleCaveIncursionMobs":
                        this.setHealthSpiderCastleCaveIncursionMobs(Integer.parseInt(value));
                        break;
                    case "healthCrystalHollowCaveIncursionMobs":
                        this.setHealthCrystalHollowCaveIncursionMobs(Integer.parseInt(value));
                        break;
                    case "healthUnstableGelSlime":
                        this.setHealthUnstableGelSlime(Integer.parseInt(value));
                        break;
                    case "healthEvilsProtector":
                        this.setHealthEvilsProtector(Integer.parseInt(value));
                        break;
                    case "healthQueenSpider":
                        this.setHealthQueenSpider(Integer.parseInt(value));
                        break;
                    case "healthVoidWizard":
                        this.setHealthVoidWizard(Integer.parseInt(value));
                        break;
                    case "healthChieftain":
                        this.setHealthChieftain(Integer.parseInt(value));
                        break;
                    case "healthSwampGuardian":
                        this.setHealthSwampGuardian(Integer.parseInt(value));
                        break;
                    case "healthAncientVulture":
                        this.setHealthAncientVulture(Integer.parseInt(value));
                        break;
                    case "healthPirateCaptain":
                        this.setHealthPirateCaptain(Integer.parseInt(value));
                        break;
                    case "healthReaper":
                        this.setHealthReaper(Integer.parseInt(value));
                        break;
                    case "healthReaperIncursion":
                        this.setHealthReaperIncursion(Integer.parseInt(value));
                        break;
                    case "healthTheCursedCrone":
                        this.setHealthTheCursedCrone(Integer.parseInt(value));
                        break;
                    case "healthCryoQueen":
                        this.setHealthCryoQueen(Integer.parseInt(value));
                        break;
                    case "healthCryoQueenIncursion":
                        this.setHealthCryoQueenIncursion(Integer.parseInt(value));
                        break;
                    case "healthPestWarden":
                        this.setHealthPestWarden(Integer.parseInt(value));
                        break;
                    case "healthPestWardenIncursion":
                        this.setHealthPestWardenIncursion(Integer.parseInt(value));
                        break;
                    case "healthSageAndGrit":
                        this.setHealthSageAndGrit(Integer.parseInt(value));
                        break;
                    case "healthSageAndGritIncursion":
                        this.setHealthSageAndGritIncursion(Integer.parseInt(value));
                        break;
                    case "healthFallenWizard":
                        this.setHealthFallenWizard(Integer.parseInt(value));
                        break;
                    case "healthMotherSlime":
                        this.setHealthMotherSlime(Integer.parseInt(value));
                        break;
                    case "healthNightSwarmBat":
                        this.setHealthNightSwarmBat(Integer.parseInt(value));
                        break;
                    case "healthSpiderEmpress":
                        this.setHealthSpiderEmpress(Integer.parseInt(value));
                        break;
                    case "healthSunlightChampion":
                        this.setHealthSunlightChampion(Integer.parseInt(value));
                        break;
                    case "healthMoonlightDancer":
                        this.setHealthMoonlightDancer(Integer.parseInt(value));
                        break;
                    case "healthCrystalDragon":
                        this.setHealthCrystalDragon(Integer.parseInt(value));
                        break;
                }
            }
        }
        return false;
    }

    private boolean shouldUpdateFile(String firstLine) {
        if (firstLine == null) return true;

        firstLine = firstLine.trim();

        if (firstLine.startsWith("#") && firstLine.toLowerCase().contains("no-update")) {
            return false;
        }

        if (firstLine.startsWith("# v")) {
            String versionStr = firstLine.substring(3).trim();
            return isOlderVersion(versionStr);
        }

        return true;
    }

    private boolean isOlderVersion(String current) {
        String[] currParts = current.split("\\.");
        String[] refParts = "1.2.0".split("\\.");

        for (int i = 0; i < Math.max(currParts.length, refParts.length); i++) {
            int curr = i < currParts.length ? Integer.parseInt(currParts[i]) : 0;
            int ref = i < refParts.length ? Integer.parseInt(refParts[i]) : 0;

            if (curr < ref) return true;
            if (curr > ref) return false;
        }

        return false;
    }

    public void recreateFile(File file) throws IOException {
        if (file.exists()) {
            if (!file.delete()) {
                throw new IOException("Could not delete existing file: " + file.toPath());
            }
        }

        createNewFile(file);
    }

    private void createNewFile(File file) throws IOException {
        if (!file.getParentFile().mkdirs() && !file.createNewFile()) {
            throw new IOException("Error creating file: " + file.toPath());
        }

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8))) {
            writer.write("# v1.2.0");
            writer.write("\n# If you want the file to not be updated when a version makes it obsolete, change the first version line to \"# no-update\"");
            writer.write("\n");
            writer.write("\n######################");
            writer.write("\n# EXPERIENCE OPTIONS #");
            writer.write("\n######################");
            writer.write("\n\n# Increase or decrease this number to adjust the amount of experience playerS receive. 2 means they will earn double experience, while 0.5 means they will earn half. Default: 1");
            writer.write("\nexperienceMod=1.0");
            writer.write("\n\n# The experience playerS start with. Default: 200");
            writer.write("\nstartingExperience=200");
            writer.write("\n\n# The experience required to level up from level 0 to level 1. Default: 200");
            writer.write("\nfirstExperienceReq=200");
            writer.write("\n\n# The experience requirement will increase by adding this number each time you level up. 0 to no changes. Default: 50");
            writer.write("\nexperienceReqInc=50");
            writer.write("\n\n# The experience requirement will increase by multiplying this number by the square of the level each time you level up. 0 to no changes. Default: 10");
            writer.write("\nsquareExperienceReqInc=10");
            writer.write("\n\n# The experience requirement will increase by multiplying this number by the cube of the level each time you level up. 0 to no changes. Default: 0");
            writer.write("\ncubeExperienceReqInc=0");
            writer.write("\n\n# The formula for the experience required to level up is:");
            writer.write("\n# firstExperienceReq + experienceReqInc * level + squareExperienceReqInc * Math.pow(level, 2) + cubeExperienceReqInc * Math.pow(level, 3)");
            writer.write("\n# Where 'level' is your actual level");
            writer.write("\n\n\n######################");
            writer.write("\n# DIFFICULTY OPTIONS #");
            writer.write("\n######################");
            writer.write("\n# The game becomes easier with the abilities, so the difficulty has been increased");
            writer.write("\n# If you want, you can change this in this file to make it harder or easier");
            writer.write("\n# Whatever you enter in this file will be ADDED to the current difficulty");
            writer.write("\n\n# Enter the values as percentages without the %. For example, 20 means +20%, and -10 means -10%");
            writer.write("\ndamageTakenModifier=" + getDamageTakenModifier());
            writer.write("\nknockbackGivenModifier=" + getKnockbackGivenModifier());
            writer.write("\nenemySpawnRateModifier=" + getEnemySpawnRateModifier());
            writer.write("\nenemySpawnCapModifier=" + getEnemySpawnCapModifier());
            writer.write("\nraiderDamageModifier=" + getRaiderDamageModifier());
            writer.write("\n\n# Custom difficulty settings");
            writer.write("\nhealthMobs=" + getHealthMobs());
            writer.write("\n\n# Custom biome difficulty settings");
            writer.write("\nhealthForestMobs=" + getHealthForestMobs());
            writer.write("\nhealthSnowMobs=" + getHealthSnowMobs());
            writer.write("\nhealthDungeonMobs=" + getHealthDungeonMobs());
            writer.write("\nhealthPlainsMobs=" + getHealthPlainsMobs());
            writer.write("\nhealthSwampMobs=" + getHealthSwampMobs());
            writer.write("\nhealthDesertMobs=" + getHealthDesertMobs());
            writer.write("\nhealthDeepForestMobs=" + getHealthForestMobs());
            writer.write("\nhealthDeepPlainsMobs=" + getHealthPlainsMobs());
            writer.write("\nhealthDeepSnowMobs=" + getHealthSnowMobs());
            writer.write("\nhealthDeepSwampMobs=" + getHealthSwampMobs());
            writer.write("\nhealthDeepDesertMobs=" + getHealthDesertMobs());
            writer.write("\nhealthTempleMobs=" + getHealthTempleMobs());
            writer.write("\n\n# Custom incursion difficulty settings");
            writer.write("\nhealthForestIncursionMobs=" + getHealthForestIncursionMobs());
            writer.write("\nhealthSnowIncursionMobs=" + getHealthSnowIncursionMobs());
            writer.write("\nhealthSwampIncursionMobs=" + getHealthSwampIncursionMobs());
            writer.write("\nhealthDesertIncursionMobs=" + getHealthDesertIncursionMobs());
            writer.write("\nhealthSlimeCaveIncursionMobs=" + getHealthSlimeCaveIncursionMobs());
            writer.write("\nhealthGraveyardCaveIncursionMobs=" + getHealthGraveyardCaveIncursionMobs());
            writer.write("\nhealthSpiderCastleCaveIncursionMobs=" + getHealthSpiderCastleCaveIncursionMobs());
            writer.write("\nhealthCrystalHollowCaveIncursionMobs=" + getHealthCrystalHollowCaveIncursionMobs());

            writer.write("\n\n# Custom bosses extra health settings");
            writer.write("\nhealthUnstableGelSlime=" + getHealthUnstableGelSlime());
            writer.write("\nhealthEvilsProtector=" + getHealthEvilsProtector());
            writer.write("\nhealthQueenSpider=" + getHealthQueenSpider());
            writer.write("\nhealthVoidWizard=" + getHealthVoidWizard());
            writer.write("\nhealthChieftain=" + getHealthChieftain());
            writer.write("\nhealthSwampGuardian=" + getHealthSwampGuardian());
            writer.write("\nhealthAncientVulture=" + getHealthAncientVulture());
            writer.write("\nhealthPirateCaptain=" + getHealthPirateCaptain());
            writer.write("\nhealthReaper=" + getHealthReaper());
            writer.write("\nhealthReaperIncursion=" + getHealthReaperIncursion());
            writer.write("\nhealthTheCursedCrone=" + getHealthTheCursedCrone());
            writer.write("\nhealthCryoQueen=" + getHealthCryoQueen());
            writer.write("\nhealthCryoQueenIncursion=" + getHealthCryoQueenIncursion());
            writer.write("\nhealthPestWarden=" + getHealthPestWarden());
            writer.write("\nhealthPestWardenIncursion=" + getHealthPestWardenIncursion());
            writer.write("\nhealthSageAndGrit=" + getHealthSageAndGrit());
            writer.write("\nhealthSageAndGritIncursion=" + getHealthSageAndGritIncursion());
            writer.write("\nhealthFallenWizard=" + getHealthFallenWizard());
            writer.write("\nhealthMotherSlime=" + getHealthMotherSlime());
            writer.write("\nhealthNightSwarmBat=" + getHealthNightSwarmBat());
            writer.write("\nhealthSpiderEmpress=" + getHealthSpiderEmpress());
            writer.write("\nhealthSunlightChampion=" + getHealthSunlightChampion());
            writer.write("\nhealthMoonlightDancer=" + getHealthMoonlightDancer());
            writer.write("\nhealthCrystalDragon=" + getHealthCrystalDragon());

            writer.write("\n\n# Delete this file and run the game to return to the default mod config");

            System.out.println("Created file: " + file.toPath());
        }
    }

    public float getExperienceMod() {
        return experienceMod;
    }

    public void setExperienceMod(float experienceMod) {
        this.experienceMod = experienceMod;
    }

    public int getStartingExperience() {
        return startingExperience;
    }

    public void setStartingExperience(int startingExperience) {
        this.startingExperience = startingExperience;
    }

    public int getFirstExperienceReq() {
        return firstExperienceReq;
    }

    public void setFirstExperienceReq(int firstExperienceReq) {
        this.firstExperienceReq = firstExperienceReq;
    }

    public int getExperienceReqInc() {
        return experienceReqInc;
    }

    public void setExperienceReqInc(int experienceReqInc) {
        this.experienceReqInc = experienceReqInc;
    }

    public int getSquareExperienceReqInc() {
        return squareExperienceReqInc;
    }

    public void setSquareExperienceReqInc(int squareExperienceReqInc) {
        this.squareExperienceReqInc = squareExperienceReqInc;
    }

    public int getCubeExperienceReqInc() {
        return cubeExperienceReqInc;
    }

    public void setCubeExperienceReqInc(int cubeExperienceReqInc) {
        this.cubeExperienceReqInc = cubeExperienceReqInc;
    }

    public int getDamageTakenModifier() {
        return damageTakenModifier;
    }

    public void setDamageTakenModifier(int damageTakenModifier) {
        this.damageTakenModifier = damageTakenModifier;
    }

    public int getKnockbackGivenModifier() {
        return knockbackGivenModifier;
    }

    public void setKnockbackGivenModifier(int knockbackGivenModifier) {
        this.knockbackGivenModifier = knockbackGivenModifier;
    }

    public int getEnemySpawnRateModifier() {
        return enemySpawnRateModifier;
    }

    public void setEnemySpawnRateModifier(int enemySpawnRateModifier) {
        this.enemySpawnRateModifier = enemySpawnRateModifier;
    }

    public int getEnemySpawnCapModifier() {
        return enemySpawnCapModifier;
    }

    public void setEnemySpawnCapModifier(int enemySpawnCapModifier) {
        this.enemySpawnCapModifier = enemySpawnCapModifier;
    }

    public int getRaiderDamageModifier() {
        return raiderDamageModifier;
    }

    public void setRaiderDamageModifier(int raiderDamageModifier) {
        this.raiderDamageModifier = raiderDamageModifier;
    }

    public int getHealthMobs() {
        return healthMobs;
    }

    public void setHealthMobs(int healthMobs) {
        this.healthMobs = healthMobs;
    }

    public int getHealthForestMobs() {
        return healthForestMobs;
    }

    public void setHealthForestMobs(int healthForestMobs) {
        this.healthForestMobs = healthForestMobs;
    }

    public int getHealthSnowMobs() {
        return healthSnowMobs;
    }

    public void setHealthSnowMobs(int healthSnowMobs) {
        this.healthSnowMobs = healthSnowMobs;
    }

    public int getHealthDungeonMobs() {
        return healthDungeonMobs;
    }

    public void setHealthDungeonMobs(int healthDungeonMobs) {
        this.healthDungeonMobs = healthDungeonMobs;
    }

    public int getHealthPlainsMobs() {
        return healthPlainsMobs;
    }

    public void setHealthPlainsMobs(int healthPlainsMobs) {
        this.healthPlainsMobs = healthPlainsMobs;
    }

    public int getHealthSwampMobs() {
        return healthSwampMobs;
    }

    public void setHealthSwampMobs(int healthSwampMobs) {
        this.healthSwampMobs = healthSwampMobs;
    }

    public int getHealthDesertMobs() {
        return healthDesertMobs;
    }

    public void setHealthDesertMobs(int healthDesertMobs) {
        this.healthDesertMobs = healthDesertMobs;
    }

    public int getHealthDeepForestMobs() {
        return healthDeepForestMobs;
    }

    public void setHealthDeepForestMobs(int healthDeepForestMobs) {
        this.healthDeepForestMobs = healthDeepForestMobs;
    }

    public int getHealthDeepSnowMobs() {
        return healthDeepSnowMobs;
    }

    public void setHealthDeepSnowMobs(int healthDeepSnowMobs) {
        this.healthDeepSnowMobs = healthDeepSnowMobs;
    }

    public int getHealthDeepPlainsMobs() {
        return healthDeepPlainsMobs;
    }

    public void setHealthDeepPlainsMobs(int healthDeepPlainsMobs) {
        this.healthDeepPlainsMobs = healthDeepPlainsMobs;
    }

    public int getHealthDeepSwampMobs() {
        return healthDeepSwampMobs;
    }

    public void setHealthDeepSwampMobs(int healthDeepSwampMobs) {
        this.healthDeepSwampMobs = healthDeepSwampMobs;
    }

    public int getHealthDeepDesertMobs() {
        return healthDeepDesertMobs;
    }

    public void setHealthDeepDesertMobs(int healthDeepDesertMobs) {
        this.healthDeepDesertMobs = healthDeepDesertMobs;
    }

    public int getHealthTempleMobs() {
        return healthTempleMobs;
    }

    public void setHealthTempleMobs(int healthTempleMobs) {
        this.healthTempleMobs = healthTempleMobs;
    }

    public int getHealthForestIncursionMobs() {
        return healthForestIncursionMobs;
    }

    public void setHealthForestIncursionMobs(int healthForestIncursionMobs) {
        this.healthForestIncursionMobs = healthForestIncursionMobs;
    }

    public int getHealthSnowIncursionMobs() {
        return healthSnowIncursionMobs;
    }

    public void setHealthSnowIncursionMobs(int healthSnowIncursionMobs) {
        this.healthSnowIncursionMobs = healthSnowIncursionMobs;
    }

    public int getHealthSwampIncursionMobs() {
        return healthSwampIncursionMobs;
    }

    public void setHealthSwampIncursionMobs(int healthSwampIncursionMobs) {
        this.healthSwampIncursionMobs = healthSwampIncursionMobs;
    }

    public int getHealthDesertIncursionMobs() {
        return healthDesertIncursionMobs;
    }

    public void setHealthDesertIncursionMobs(int healthDesertIncursionMobs) {
        this.healthDesertIncursionMobs = healthDesertIncursionMobs;
    }

    public int getHealthSlimeCaveIncursionMobs() {
        return healthSlimeCaveIncursionMobs;
    }

    public void setHealthSlimeCaveIncursionMobs(int healthSlimeCaveIncursionMobs) {
        this.healthSlimeCaveIncursionMobs = healthSlimeCaveIncursionMobs;
    }

    public int getHealthGraveyardCaveIncursionMobs() {
        return healthGraveyardCaveIncursionMobs;
    }

    public void setHealthGraveyardCaveIncursionMobs(int healthGraveyardCaveIncursionMobs) {
        this.healthGraveyardCaveIncursionMobs = healthGraveyardCaveIncursionMobs;
    }

    public int getHealthSpiderCastleCaveIncursionMobs() {
        return healthSpiderCastleCaveIncursionMobs;
    }

    public void setHealthSpiderCastleCaveIncursionMobs(int healthSpiderCastleCaveIncursionMobs) {
        this.healthSpiderCastleCaveIncursionMobs = healthSpiderCastleCaveIncursionMobs;
    }

    public int getHealthCrystalHollowCaveIncursionMobs() {
        return healthCrystalHollowCaveIncursionMobs;
    }

    public void setHealthCrystalHollowCaveIncursionMobs(int healthCrystalHollowCaveIncursionMobs) {
        this.healthCrystalHollowCaveIncursionMobs = healthCrystalHollowCaveIncursionMobs;
    }

    public int getHealthUnstableGelSlime() {
        return healthUnstableGelSlime;
    }

    public void setHealthUnstableGelSlime(int healthUnstableGelSlime) {
        this.healthUnstableGelSlime = healthUnstableGelSlime;
    }

    public int getHealthEvilsProtector() {
        return healthEvilsProtector;
    }

    public void setHealthEvilsProtector(int healthEvilsProtector) {
        this.healthEvilsProtector = healthEvilsProtector;
    }

    public int getHealthQueenSpider() {
        return healthQueenSpider;
    }

    public void setHealthQueenSpider(int healthQueenSpider) {
        this.healthQueenSpider = healthQueenSpider;
    }

    public int getHealthVoidWizard() {
        return healthVoidWizard;
    }

    public void setHealthVoidWizard(int healthVoidWizard) {
        this.healthVoidWizard = healthVoidWizard;
    }

    public int getHealthChieftain() {
        return healthChieftain;
    }

    public void setHealthChieftain(int healthChieftain) {
        this.healthChieftain = healthChieftain;
    }

    public int getHealthSwampGuardian() {
        return healthSwampGuardian;
    }

    public void setHealthSwampGuardian(int healthSwampGuardian) {
        this.healthSwampGuardian = healthSwampGuardian;
    }

    public int getHealthAncientVulture() {
        return healthAncientVulture;
    }

    public void setHealthAncientVulture(int healthAncientVulture) {
        this.healthAncientVulture = healthAncientVulture;
    }

    public int getHealthPirateCaptain() {
        return healthPirateCaptain;
    }

    public void setHealthPirateCaptain(int healthPirateCaptain) {
        this.healthPirateCaptain = healthPirateCaptain;
    }

    public int getHealthReaper() {
        return healthReaper;
    }

    public void setHealthReaper(int healthReaper) {
        this.healthReaper = healthReaper;
    }

    public int getHealthReaperIncursion() {
        return healthReaperIncursion;
    }

    public void setHealthReaperIncursion(int healthReaperIncursion) {
        this.healthReaperIncursion = healthReaperIncursion;
    }

    public int getHealthTheCursedCrone() {
        return healthTheCursedCrone;
    }

    public void setHealthTheCursedCrone(int healthTheCursedCrone) {
        this.healthTheCursedCrone = healthTheCursedCrone;
    }

    public int getHealthCryoQueen() {
        return healthCryoQueen;
    }

    public void setHealthCryoQueen(int healthCryoQueen) {
        this.healthCryoQueen = healthCryoQueen;
    }

    public int getHealthCryoQueenIncursion() {
        return healthCryoQueenIncursion;
    }

    public void setHealthCryoQueenIncursion(int healthCryoQueenIncursion) {
        this.healthCryoQueenIncursion = healthCryoQueenIncursion;
    }

    public int getHealthPestWarden() {
        return healthPestWarden;
    }

    public void setHealthPestWarden(int healthPestWarden) {
        this.healthPestWarden = healthPestWarden;
    }

    public int getHealthPestWardenIncursion() {
        return healthPestWardenIncursion;
    }

    public void setHealthPestWardenIncursion(int healthPestWardenIncursion) {
        this.healthPestWardenIncursion = healthPestWardenIncursion;
    }

    public int getHealthSageAndGrit() {
        return healthSageAndGrit;
    }

    public void setHealthSageAndGrit(int healthSageAndGrit) {
        this.healthSageAndGrit = healthSageAndGrit;
    }

    public int getHealthSageAndGritIncursion() {
        return healthSageAndGritIncursion;
    }

    public void setHealthSageAndGritIncursion(int healthSageAndGritIncursion) {
        this.healthSageAndGritIncursion = healthSageAndGritIncursion;
    }

    public int getHealthFallenWizard() {
        return healthFallenWizard;
    }

    public void setHealthFallenWizard(int healthFallenWizard) {
        this.healthFallenWizard = healthFallenWizard;
    }

    public int getHealthMotherSlime() {
        return healthMotherSlime;
    }

    public void setHealthMotherSlime(int healthMotherSlime) {
        this.healthMotherSlime = healthMotherSlime;
    }

    public int getHealthNightSwarmBat() {
        return healthNightSwarmBat;
    }

    public void setHealthNightSwarmBat(int healthNightSwarmBat) {
        this.healthNightSwarmBat = healthNightSwarmBat;
    }

    public int getHealthSpiderEmpress() {
        return healthSpiderEmpress;
    }

    public void setHealthSpiderEmpress(int healthSpiderEmpress) {
        this.healthSpiderEmpress = healthSpiderEmpress;
    }

    public int getHealthSunlightChampion() {
        return healthSunlightChampion;
    }

    public void setHealthSunlightChampion(int healthSunlightChampion) {
        this.healthSunlightChampion = healthSunlightChampion;
    }

    public int getHealthMoonlightDancer() {
        return healthMoonlightDancer;
    }

    public void setHealthMoonlightDancer(int healthMoonlightDancer) {
        this.healthMoonlightDancer = healthMoonlightDancer;
    }

    public int getHealthCrystalDragon() {
        return healthCrystalDragon;
    }

    public void setHealthCrystalDragon(int healthCrystalDragon) {
        this.healthCrystalDragon = healthCrystalDragon;
    }

    @Override
    public String toString() {
        return "RPGClassesConfig {" +
                "\n  experienceMod=" + experienceMod +
                "\n  startingExperience=" + startingExperience +
                "\n  firstExperienceReq=" + firstExperienceReq +
                "\n  experienceReqInc=" + experienceReqInc +
                "\n  squareExperienceReqInc=" + squareExperienceReqInc +
                "\n  cubeExperienceReqInc=" + cubeExperienceReqInc +
                "\n  damageTakenModifier=" + damageTakenModifier +
                "\n  knockbackGivenModifier=" + knockbackGivenModifier +
                "\n  enemySpawnRateModifier=" + enemySpawnRateModifier +
                "\n  enemySpawnCapModifier=" + enemySpawnCapModifier +
                "\n  raiderDamageModifier=" + raiderDamageModifier +
                "\n  healthMobs=" + healthMobs +
                "\n  healthForestMobs=" + healthForestMobs +
                "\n  healthSnowMobs=" + healthSnowMobs +
                "\n  healthDungeonMobs=" + healthDungeonMobs +
                "\n  healthPlainsMobs=" + healthPlainsMobs +
                "\n  healthSwampMobs=" + healthSwampMobs +
                "\n  healthDesertMobs=" + healthDesertMobs +
                "\n  healthDeepForestMobs=" + healthDeepForestMobs +
                "\n  healthDeepSnowMobs=" + healthDeepSnowMobs +
                "\n  healthDeepPlainsMobs=" + healthDeepPlainsMobs +
                "\n  healthDeepSwampMobs=" + healthDeepSwampMobs +
                "\n  healthDeepDesertMobs=" + healthDeepDesertMobs +
                "\n  healthTempleMobs=" + healthTempleMobs +
                "\n  healthForestIncursionMobs=" + healthForestIncursionMobs +
                "\n  healthSnowIncursionMobs=" + healthSnowIncursionMobs +
                "\n  healthSwampIncursionMobs=" + healthSwampIncursionMobs +
                "\n  healthDesertIncursionMobs=" + healthDesertIncursionMobs +
                "\n  healthSlimeCaveIncursionMobs=" + healthSlimeCaveIncursionMobs +
                "\n  healthGraveyardCaveIncursionMobs=" + healthGraveyardCaveIncursionMobs +
                "\n  healthSpiderCastleCaveIncursionMobs=" + healthSpiderCastleCaveIncursionMobs +
                "\n  healthCrystalHollowCaveIncursionMobs=" + healthCrystalHollowCaveIncursionMobs +
                "\n  healthUnstableGelSlime=" + healthUnstableGelSlime +
                "\n  healthEvilsProtector=" + healthEvilsProtector +
                "\n  healthQueenSpider=" + healthQueenSpider +
                "\n  healthVoidWizard=" + healthVoidWizard +
                "\n  healthChieftain=" + healthChieftain +
                "\n  healthSwampGuardian=" + healthSwampGuardian +
                "\n  healthAncientVulture=" + healthAncientVulture +
                "\n  healthPirateCaptain=" + healthPirateCaptain +
                "\n  healthReaper=" + healthReaper +
                "\n  healthReaperIncursion=" + healthReaperIncursion +
                "\n  healthTheCursedCrone=" + healthTheCursedCrone +
                "\n  healthCryoQueen=" + healthCryoQueen +
                "\n  healthCryoQueenIncursion=" + healthCryoQueenIncursion +
                "\n  healthPestWarden=" + healthPestWarden +
                "\n  healthPestWardenIncursion=" + healthPestWardenIncursion +
                "\n  healthSageAndGrit=" + healthSageAndGrit +
                "\n  healthSageAndGritIncursion=" + healthSageAndGritIncursion +
                "\n  healthFallenWizard=" + healthFallenWizard +
                "\n  healthMotherSlime=" + healthMotherSlime +
                "\n  healthNightSwarmBat=" + healthNightSwarmBat +
                "\n  healthSpiderEmpress=" + healthSpiderEmpress +
                "\n  healthSunlightChampion=" + healthSunlightChampion +
                "\n  healthMoonlightDancer=" + healthMoonlightDancer +
                "\n  healthCrystalDragon=" + healthCrystalDragon +
                "\n}";
    }
}
