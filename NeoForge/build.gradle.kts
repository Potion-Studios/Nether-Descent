import com.hypherionmc.modpublisher.properties.ModLoader

plugins {
    id("com.gradleup.shadow")
}

architectury {
    platformSetupLoomIde()
    neoForge()
}

val minecraftVersion = project.properties["minecraft_version"] as String

configurations {
    create("common")
    "common" {
        isCanBeResolved = true
        isCanBeConsumed = false
    }
    create("shadowBundle")
    compileClasspath.get().extendsFrom(configurations["common"])
    runtimeClasspath.get().extendsFrom(configurations["common"])
    getByName("developmentNeoForge").extendsFrom(configurations["common"])
    "shadowBundle" {
        isCanBeResolved = true
        isCanBeConsumed = false
    }
}

loom {
    accessWidenerPath.set(project(":Common").loom.accessWidenerPath)

    runs.create("datagen") {
        data()
        programArgs("--all", "--mod", "netherdescent")
        programArgs("--output", project(":Common").file("src/main/generated/resources").absolutePath)
        programArgs("--existing", project(":Common").file("src/main/resources").absolutePath)
    }
}

dependencies {
    neoForge("net.neoforged:neoforge:${project.properties["neoforge_version"]}")

    "common"(project(":Common", "namedElements")) { isTransitive = false }
    "shadowBundle"(project(":Common", "transformProductionNeoForge"))

    modLocalRuntime("me.djtheredstoner:DevAuth-neoforge:${project.properties["devauth_version"]}")

    modApi("com.github.glitchfiend:TerraBlender-neoforge:$minecraftVersion-${project.properties["terrablender_version"]}")
    modApi("com.terraformersmc:biolith-neoforge:${project.properties["biolith_version"]}")
    modApi("dev.corgitaco:Oh-The-Trees-Youll-Grow-neoforge:$minecraftVersion-${project.properties["ohthetreesyoullgrow_version"]}")

    modLocalRuntime("mcp.mobius.waila:wthit:neo-${project.properties["WTHIT"]}")
    modLocalRuntime("lol.bai:badpackets:neo-${project.properties["badPackets"]}")

    modLocalRuntime("com.github.Jab125.architectury-data-generation-fix:architectury-data-generation-fix-neoforge:21.0.3")
    modLocalRuntime("maven.modrinth:worldedit:7.3.8")
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("META-INF/neoforge.mods.toml") {
            expand(mapOf("version" to project.version))
        }
    }

    shadowJar {
        exclude("architectury.common.json", "net/potionstudios/netherdescent/neoforge/datagen/**", ".cache/**")
        configurations = listOf(project.configurations.getByName("shadowBundle"))
        archiveClassifier.set("dev-shadow")
    }

    remapJar {
        inputFile.set(shadowJar.get().archiveFile)
        dependsOn(shadowJar)
        atAccessWideners.add("netherdescent.accesswidener")
    }
}

publisher {
    setLoaders(ModLoader.NEOFORGE)
    curseDepends.required.set(mutableListOf("oh-the-trees-youll-grow"))
    modrinthDepends.required.set(mutableListOf("oh-the-trees-youll-grow"))
    curseDepends.optional.set(mutableListOf("terrablender-neoforge", "biolith", "wthit-forge"))
}