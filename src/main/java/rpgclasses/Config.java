package rpgclasses;

import necesse.engine.GlobalData;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

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

    private int mobsHealthModifier = 40;

    private int forestMobsHealthModifier = 0;
    private int snowMobsHealthModifier = 20;
    private int dungeonMobsHealthModifier = 40;
    private int swampMobsHealthModifier = 60;
    private int desertMobsHealthModifier = 80;
    private int templeMobsHealthModifier = 100;

    private int forestIncursionMobsHealthModifier = 100;
    private int snowIncursionMobsHealthModifier = 120;
    private int swampIncursionMobsHealthModifier = 140;
    private int desertIncursionMobsHealthModifier = 160;
    private int slimeCaveIncursionMobsHealthModifier = 180;
    private int graveyardCaveIncursionMobsHealthModifier = 200;
    private int spiderCastleCaveIncursionMobsHealthModifier = 220;
    private int crystalHollowCaveIncursionMobsHealthModifier = 240;

    private int healthUnstableGelSlime = 20;
    private int healthEvilsProtector = 20;
    private int healthQueenSpider = 40;
    private int healthVoidWizard = 60;
    private int healthSwampGuardian = 80;
    private int healthAncientVulture = 100;
    private int healthPirateCaptain = 120;
    private int healthReaper = 140;
    private int healthReaperIncursion = 140;
    private int healthCryoQueen = 160;
    private int healthCryoQueenIncursion = 160;
    private int healthPestWarden = 180;
    private int healthPestWardenIncursion = 180;
    private int healthSageAndGrit = 200;
    private int healthSageAndGritIncursion = 200;
    private int healthFallenWizard = 220;
    private int healthMotherSlime = 240;
    private int healthNightSwarmBat = 280;
    private int healthSpiderEmpress = 280;
    private int healthSunlightChampion = 300;
    private int healthMoonlightDancer = 320;
    private int healthCrystalDragon = 340;

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
            loadConfig(br);
            br.close();

            System.out.println("Loaded file: " + file.toPath());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadConfig(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.isEmpty() && !line.startsWith("#")) {
                String[] temp = line.split("=");
                if (temp.length < 2) continue;
                String key = temp[0].trim();
                String value = temp[1].trim();
                if (Objects.equals(key, "experienceMod")) {
                    this.setExperienceMod(Float.parseFloat(value));
                } else if (Objects.equals(key, "startingExperience")) {
                    this.setStartingExperience(Integer.parseInt(value));
                } else if (Objects.equals(key, "firstExperienceReq")) {
                    this.setFirstExperienceReq(Integer.parseInt(value));
                } else if (Objects.equals(key, "experienceReqInc")) {
                    this.setExperienceReqInc(Integer.parseInt(value));
                } else if (Objects.equals(key, "squareExperienceReqInc")) {
                    this.setSquareExperienceReqInc(Integer.parseInt(value));
                } else if (Objects.equals(key, "cubeExperienceReqInc")) {
                    this.setCubeExperienceReqInc(Integer.parseInt(value));
                } else if (Objects.equals(key, "damageTakenModifier")) {
                    this.setDamageTakenModifier(Integer.parseInt(value));
                } else if (Objects.equals(key, "knockbackGivenModifier")) {
                    this.setKnockbackGivenModifier(Integer.parseInt(value));
                } else if (Objects.equals(key, "enemySpawnRateModifier")) {
                    this.setEnemySpawnRateModifier(Integer.parseInt(value));
                } else if (Objects.equals(key, "enemySpawnCapModifier")) {
                    this.setEnemySpawnCapModifier(Integer.parseInt(value));
                } else if (Objects.equals(key, "raiderDamageModifier")) {
                    this.setRaiderDamageModifier(Integer.parseInt(value));
                } else if (Objects.equals(key, "mobsHealthModifier")) {
                    this.setMobsHealthModifier(Integer.parseInt(value));
                } else if (Objects.equals(key, "forestMobsHealthModifier")) {
                    this.setForestMobsHealthModifier(Integer.parseInt(value));
                } else if (Objects.equals(key, "snowMobsHealthModifier")) {
                    this.setSnowMobsHealthModifier(Integer.parseInt(value));
                } else if (Objects.equals(key, "dungeonMobsHealthModifier")) {
                    this.setDungeonMobsHealthModifier(Integer.parseInt(value));
                } else if (Objects.equals(key, "swampMobsHealthModifier")) {
                    this.setSwampMobsHealthModifier(Integer.parseInt(value));
                } else if (Objects.equals(key, "desertMobsHealthModifier")) {
                    this.setDesertMobsHealthModifier(Integer.parseInt(value));
                } else if (Objects.equals(key, "templeMobsHealthModifier")) {
                    this.setTempleMobsHealthModifier(Integer.parseInt(value));
                } else if (Objects.equals(key, "forestIncursionMobsHealthModifier")) {
                    this.setForestIncursionMobsHealthModifier(Integer.parseInt(value));
                } else if (Objects.equals(key, "snowIncursionMobsHealthModifier")) {
                    this.setSnowIncursionMobsHealthModifier(Integer.parseInt(value));
                } else if (Objects.equals(key, "swampIncursionMobsHealthModifier")) {
                    this.setSwampIncursionMobsHealthModifier(Integer.parseInt(value));
                } else if (Objects.equals(key, "desertIncursionMobsHealthModifier")) {
                    this.setDesertIncursionMobsHealthModifier(Integer.parseInt(value));
                } else if (Objects.equals(key, "slimeCaveIncursionMobsHealthModifier")) {
                    this.setSlimeCaveIncursionMobsHealthModifier(Integer.parseInt(value));
                } else if (Objects.equals(key, "graveyardCaveIncursionMobsHealthModifier")) {
                    this.setGraveyardCaveIncursionMobsHealthModifier(Integer.parseInt(value));
                } else if (Objects.equals(key, "spiderCastleCaveIncursionMobsHealthModifier")) {
                    this.setSpiderCastleCaveIncursionMobsHealthModifier(Integer.parseInt(value));
                } else if (Objects.equals(key, "crystalHollowCaveIncursionMobsHealthModifier")) {
                    this.setCrystalHollowCaveIncursionMobsHealthModifier(Integer.parseInt(value));
                } else if (Objects.equals(key, "healthUnstableGelSlime")) {
                    this.setHealthUnstableGelSlime(Integer.parseInt(value));
                } else if (Objects.equals(key, "healthEvilsProtector")) {
                    this.setHealthEvilsProtector(Integer.parseInt(value));
                } else if (Objects.equals(key, "healthQueenSpider")) {
                    this.setHealthQueenSpider(Integer.parseInt(value));
                } else if (Objects.equals(key, "healthVoidWizard")) {
                    this.setHealthVoidWizard(Integer.parseInt(value));
                } else if (Objects.equals(key, "healthSwampGuardian")) {
                    this.setHealthSwampGuardian(Integer.parseInt(value));
                } else if (Objects.equals(key, "healthAncientVulture")) {
                    this.setHealthAncientVulture(Integer.parseInt(value));
                } else if (Objects.equals(key, "healthPirateCaptain")) {
                    this.setHealthPirateCaptain(Integer.parseInt(value));
                } else if (Objects.equals(key, "healthReaper")) {
                    this.setHealthReaper(Integer.parseInt(value));
                } else if (Objects.equals(key, "healthReaperIncursion")) {
                    this.setHealthReaperIncursion(Integer.parseInt(value));
                } else if (Objects.equals(key, "healthCryoQueen")) {
                    this.setHealthCryoQueen(Integer.parseInt(value));
                } else if (Objects.equals(key, "healthCryoQueenIncursion")) {
                    this.setHealthCryoQueenIncursion(Integer.parseInt(value));
                } else if (Objects.equals(key, "healthPestWarden")) {
                    this.setHealthPestWarden(Integer.parseInt(value));
                } else if (Objects.equals(key, "healthPestWardenIncursion")) {
                    this.setHealthPestWardenIncursion(Integer.parseInt(value));
                } else if (Objects.equals(key, "healthSageAndGrit")) {
                    this.setHealthSageAndGrit(Integer.parseInt(value));
                } else if (Objects.equals(key, "healthSageAndGritIncursion")) {
                    this.setHealthSageAndGritIncursion(Integer.parseInt(value));
                } else if (Objects.equals(key, "healthFallenWizard")) {
                    this.setHealthFallenWizard(Integer.parseInt(value));
                } else if (Objects.equals(key, "healthMotherSlime")) {
                    this.setHealthMotherSlime(Integer.parseInt(value));
                } else if (Objects.equals(key, "healthNightSwarmBat")) {
                    this.setHealthNightSwarmBat(Integer.parseInt(value));
                } else if (Objects.equals(key, "healthSpiderEmpress")) {
                    this.setHealthSpiderEmpress(Integer.parseInt(value));
                } else if (Objects.equals(key, "healthSunlightChampion")) {
                    this.setHealthSunlightChampion(Integer.parseInt(value));
                } else if (Objects.equals(key, "healthMoonlightDancer")) {
                    this.setHealthMoonlightDancer(Integer.parseInt(value));
                } else if (Objects.equals(key, "healthCrystalDragon")) {
                    this.setHealthCrystalDragon(Integer.parseInt(value));
                }
            }
        }
    }


    private void createNewFile(File file) throws IOException {
        if (!file.getParentFile().mkdirs() && !file.createNewFile()) {
            throw new IOException("Error creating file: " + file.toPath());
        }

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8))) {
            writer.write("######################");
            writer.write("\n# EXPERIENCE OPTIONS #");
            writer.write("\n######################");
            writer.write("\n\n# Increase or decrease this number to adjust the amount of experience players receive. 2 means they will earn double experience, while 0.5 means they will earn half. Default: 1");
            writer.write("\nexperienceMod=1.0");
            writer.write("\n\n# The experience players start with. Default: 200");
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
            writer.write("\ndamageTakenModifier=40");
            writer.write("\nknockbackGivenModifier=-20");
            writer.write("\nenemySpawnRateModifier=50");
            writer.write("\nenemySpawnCapModifier=50");
            writer.write("\nraiderDamageModifier=60");
            writer.write("\n\n# Custom difficulty settings");
            writer.write("\nmobsHealthModifier=40");
            writer.write("\n\n# Custom biome difficulty settings");
            writer.write("\nforestMobsHealthModifier=0");
            writer.write("\nsnowMobsHealthModifier=20");
            writer.write("\ndungeonMobsHealthModifier=40");
            writer.write("\nswampMobsHealthModifier=60");
            writer.write("\ndesertMobsHealthModifier=80");
            writer.write("\ntempleMobsHealthModifier=100");
            writer.write("\n\n# Custom incursion difficulty settings");
            writer.write("\nforestIncursionMobsHealthModifier=100");
            writer.write("\nsnowIncursionMobsHealthModifier=120");
            writer.write("\nswampIncursionMobsHealthModifier=140");
            writer.write("\ndesertIncursionMobsHealthModifier=160");
            writer.write("\nslimeCaveIncursionMobsHealthModifier=180");
            writer.write("\ngraveyardCaveIncursionMobsHealthModifier=200");
            writer.write("\nspiderCastleCaveIncursionMobsHealthModifier=220");
            writer.write("\ncrystalHollowCaveIncursionMobsHealthModifier=240");

            writer.write("\n\n# Custom bosses extra health settings");
            writer.write("\nhealthUnstableGelSlime=20");
            writer.write("\nhealthEvilsProtector=20");
            writer.write("\nhealthQueenSpider=40");
            writer.write("\nhealthVoidWizard=60");
            writer.write("\nhealthSwampGuardian=80");
            writer.write("\nhealthAncientVulture=100");
            writer.write("\nhealthPirateCaptain=120");
            writer.write("\nhealthReaper=140");
            writer.write("\nhealthReaperIncursion=140");
            writer.write("\nhealthCryoQueen=160");
            writer.write("\nhealthCryoQueenIncursion=160");
            writer.write("\nhealthPestWarden=180");
            writer.write("\nhealthPestWardenIncursion=180");
            writer.write("\nhealthSageAndGrit=200");
            writer.write("\nhealthSageAndGritIncursion=200");
            writer.write("\nhealthFallenWizard=220");
            writer.write("\nhealthMotherSlime=240");
            writer.write("\nhealthNightSwarmBat=280");
            writer.write("\nhealthSpiderEmpress=280");
            writer.write("\nhealthSunlightChampion=300");
            writer.write("\nhealthMoonlightDancer=320");
            writer.write("\nhealthCrystalDragon=340");

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

    public int getMobsHealthModifier() {
        return mobsHealthModifier;
    }

    public void setMobsHealthModifier(int mobsHealthModifier) {
        this.mobsHealthModifier = mobsHealthModifier;
    }

    public int getForestMobsHealthModifier() {
        return forestMobsHealthModifier;
    }

    public void setForestMobsHealthModifier(int forestMobsHealthModifier) {
        this.forestMobsHealthModifier = forestMobsHealthModifier;
    }

    public int getSnowMobsHealthModifier() {
        return snowMobsHealthModifier;
    }

    public void setSnowMobsHealthModifier(int snowMobsHealthModifier) {
        this.snowMobsHealthModifier = snowMobsHealthModifier;
    }

    public int getDungeonMobsHealthModifier() {
        return dungeonMobsHealthModifier;
    }

    public void setDungeonMobsHealthModifier(int dungeonMobsHealthModifier) {
        this.dungeonMobsHealthModifier = dungeonMobsHealthModifier;
    }

    public int getSwampMobsHealthModifier() {
        return swampMobsHealthModifier;
    }

    public void setSwampMobsHealthModifier(int swampMobsHealthModifier) {
        this.swampMobsHealthModifier = swampMobsHealthModifier;
    }

    public int getDesertMobsHealthModifier() {
        return desertMobsHealthModifier;
    }

    public void setDesertMobsHealthModifier(int desertMobsHealthModifier) {
        this.desertMobsHealthModifier = desertMobsHealthModifier;
    }

    public int getTempleMobsHealthModifier() {
        return templeMobsHealthModifier;
    }

    public void setTempleMobsHealthModifier(int templeMobsHealthModifier) {
        this.templeMobsHealthModifier = templeMobsHealthModifier;
    }

    public int getForestIncursionMobsHealthModifier() {
        return forestIncursionMobsHealthModifier;
    }

    public void setForestIncursionMobsHealthModifier(int forestIncursionMobsHealthModifier) {
        this.forestIncursionMobsHealthModifier = forestIncursionMobsHealthModifier;
    }

    public int getSnowIncursionMobsHealthModifier() {
        return snowIncursionMobsHealthModifier;
    }

    public void setSnowIncursionMobsHealthModifier(int snowIncursionMobsHealthModifier) {
        this.snowIncursionMobsHealthModifier = snowIncursionMobsHealthModifier;
    }

    public int getSwampIncursionMobsHealthModifier() {
        return swampIncursionMobsHealthModifier;
    }

    public void setSwampIncursionMobsHealthModifier(int swampIncursionMobsHealthModifier) {
        this.swampIncursionMobsHealthModifier = swampIncursionMobsHealthModifier;
    }

    public int getDesertIncursionMobsHealthModifier() {
        return desertIncursionMobsHealthModifier;
    }

    public void setDesertIncursionMobsHealthModifier(int desertIncursionMobsHealthModifier) {
        this.desertIncursionMobsHealthModifier = desertIncursionMobsHealthModifier;
    }

    public int getSlimeCaveIncursionMobsHealthModifier() {
        return slimeCaveIncursionMobsHealthModifier;
    }

    public void setSlimeCaveIncursionMobsHealthModifier(int slimeCaveIncursionMobsHealthModifier) {
        this.slimeCaveIncursionMobsHealthModifier = slimeCaveIncursionMobsHealthModifier;
    }

    public int getGraveyardCaveIncursionMobsHealthModifier() {
        return graveyardCaveIncursionMobsHealthModifier;
    }

    public void setGraveyardCaveIncursionMobsHealthModifier(int graveyardCaveIncursionMobsHealthModifier) {
        this.graveyardCaveIncursionMobsHealthModifier = graveyardCaveIncursionMobsHealthModifier;
    }

    public int getSpiderCastleCaveIncursionMobsHealthModifier() {
        return spiderCastleCaveIncursionMobsHealthModifier;
    }

    public void setSpiderCastleCaveIncursionMobsHealthModifier(int spiderCastleCaveIncursionMobsHealthModifier) {
        this.spiderCastleCaveIncursionMobsHealthModifier = spiderCastleCaveIncursionMobsHealthModifier;
    }

    public int getCrystalHollowCaveIncursionMobsHealthModifier() {
        return crystalHollowCaveIncursionMobsHealthModifier;
    }

    public void setCrystalHollowCaveIncursionMobsHealthModifier(int crystalHollowCaveIncursionMobsHealthModifier) {
        this.crystalHollowCaveIncursionMobsHealthModifier = crystalHollowCaveIncursionMobsHealthModifier;
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
}
