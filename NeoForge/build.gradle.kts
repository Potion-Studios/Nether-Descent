import com.hypherionmc.modpublisher.properties.ModLoader

plugins {
    id("com.gradleup.shadow")
}

architectury {
    platformSetupLoomIde()
    neoForge()
}

val minecraftVersion = providers.gradleProperty("minecraft_version").get()

configurations {
    val common = register("common")
    register("shadowCommon")
    compileClasspath.get().extendsFrom(common.get())
    runtimeClasspath.get().extendsFrom(common.get())
    named("developmentNeoForge") { extendsFrom(common.get()) }
}

loom {
    accessWidenerPath.set(project(":Common").loom.accessWidenerPath)

    runs.create("datagen") {
        clientData()
        programArguments.addAll(
            "--all", "--mod", "netherdescent",
            "--output", project(":Common").file("src/main/generated/resources").absolutePath,
            "--existing", project(":Common").file("src/main/resources").absolutePath
        )
    }
}

dependencies {
    neoForge("net.neoforged:neoforge:${providers.gradleProperty("neoforge_version").get()}")

    "common"(project(":Common", "namedElements")) { isTransitive = false }
    "shadowCommon"(project(":Common", "transformProductionNeoForge"))

    modLocalRuntime("me.djtheredstoner:DevAuth-neoforge:${providers.gradleProperty("devauth_version").get()}")

    modApi("com.github.glitchfiend:TerraBlender-neoforge:$minecraftVersion-${providers.gradleProperty("terrablender_version").get()}")
    modCompileOnly("com.terraformersmc:biolith-neoforge:${providers.gradleProperty("biolith_version").get()}")
    modApi("maven.modrinth:lithostitched:${providers.gradleProperty("lithostitched_version").get()}-neoforge-21.11")
    modApi("dev.corgitaco:Oh-The-Trees-Youll-Grow-neoforge:$minecraftVersion-${providers.gradleProperty("ohthetreesyoullgrow_version").get()}")

    modLocalRuntime("mcp.mobius.waila:wthit:neo-${providers.gradleProperty("WTHIT").get()}")
    modLocalRuntime("lol.bai:badpackets:neo-${providers.gradleProperty("badPackets").get()}")

//    modLocalRuntime("maven.modrinth:worldedit:7.4.2")
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
        configurations = listOf(project.configurations.getByName("shadowCommon"))
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