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

    neoForge.convertAccessWideners(tasks.shadowJar, "netherdescent.accessWidener")
}

dependencies {
    neoForge("net.neoforged:neoforge:${providers.gradleProperty("neoforge_version").get()}")

    "common"(project(":Common")) { isTransitive = false }
    "shadowCommon"(project(":Common", "transformProductionNeoForge"))

    localRuntime("me.djtheredstoner:DevAuth-neoforge:${providers.gradleProperty("devauth_version").get()}")

    compileOnly("com.github.glitchfiend:TerraBlender-neoforge:$minecraftVersion-${providers.gradleProperty("terrablender_version").get()}")
    compileOnly("com.terraformersmc:biolith-neoforge:${providers.gradleProperty("biolith_version").get()}")
    api("maven.modrinth:lithostitched:${providers.gradleProperty("lithostitched_version").get()}-neoforge-26.1")
    api("dev.corgitaco.ohthetreesyoullgrow:ohthetreesyoullgrow-neoforge-$minecraftVersion:${providers.gradleProperty("ohthetreesyoullgrow_version").get()}")

    localRuntime("mcp.mobius.waila:wthit:neo-${providers.gradleProperty("WTHIT").get()}")
    localRuntime("lol.bai:badpackets:neo-${providers.gradleProperty("badPackets").get()}")

    localRuntime("maven.modrinth:worldedit:7.4.4")
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("META-INF/neoforge.mods.toml") {
            expand(mapOf("version" to project.version))
        }
    }

    jar.get().archiveClassifier.set("raw")

    shadowJar {
        dependsOn(jar)
        from(zipTree(jar.get().archiveFile))
        exclude("architectury.common.json", "net/potionstudios/netherdescent/neoforge/datagen/**", ".cache/**")
        configurations = listOf(project.configurations.getByName("shadowCommon"))
        archiveClassifier.set(null)
    }
}

publisher {
    setLoaders(ModLoader.NEOFORGE)
    curseDepends.required.set(mutableListOf("oh-the-trees-youll-grow"))
    modrinthDepends.required.set(mutableListOf("oh-the-trees-youll-grow"))
    curseDepends.optional.set(mutableListOf("terrablender-neoforge", "biolith", "lithostitched", "wthit-forge"))
}