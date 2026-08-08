import com.hypherionmc.modpublisher.properties.ModLoader

plugins {
    id("com.gradleup.shadow")
}

architectury {
    platformSetupLoomIde()
    forge()
}

val minecraftVersion = providers.gradleProperty("minecraft_version").get()

configurations {
    val common = register("common")
    register("shadowCommon")
    compileClasspath.get().extendsFrom(common.get())
    runtimeClasspath.get().extendsFrom(common.get())
    named("developmentForge") { extendsFrom(common.get()) }
}

loom {
    accessWidenerPath.set(project(":Common").loom.accessWidenerPath)

    forge {
        convertAccessWideners.set(true)
        extraAccessWideners.add(loom.accessWidenerPath.get().asFile.name)

        mixinConfig("netherdescent-common.mixins.json")
    }
}

dependencies {
    forge("net.minecraftforge:forge:$minecraftVersion-${providers.gradleProperty("forge_version").get()}")

    "common"(project(":Common", "namedElements")) { isTransitive = false }
    "shadowCommon"(project(":Common", "transformProductionForge"))

    modLocalRuntime("me.djtheredstoner:DevAuth-forge-latest:${providers.gradleProperty("devauth_version").get()}")

    modApi("com.github.glitchfiend:TerraBlender-forge:$minecraftVersion-${providers.gradleProperty("terrablender_version").get()}")
    modApi("com.terraformersmc:biolith-forge:${providers.gradleProperty("biolith_version").get()}")
    modApi("dev.corgitaco:Oh-The-Trees-Youll-Grow-forge:$minecraftVersion-${providers.gradleProperty("ohthetreesyoullgrow_version").get()}")

    modLocalRuntime("mcp.mobius.waila:wthit:forge-${providers.gradleProperty("WTHIT").get()}")
    modLocalRuntime("lol.bai:badpackets:forge-${providers.gradleProperty("badPackets").get()}")
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("META-INF/mods.toml") {
            expand(mapOf("version" to project.version))
        }
    }

    shadowJar {
        exclude("architectury.common.json", ".cache/**", "data/neoforge/**")
        configurations = listOf(project.configurations.getByName("shadowCommon"))
        archiveClassifier.set("dev-shadow")
    }

    remapJar {
        inputFile.set(shadowJar.get().archiveFile)
        dependsOn(shadowJar)
    }
}

publisher {
    setLoaders(ModLoader.FORGE)
    val depends = mutableListOf("oh-the-trees-youll-grow")
    curseDepends.required.set(depends)
    modrinthDepends.required.set(depends)
    curseDepends.optional.set(mutableListOf("terrablender", "biolith", "wthit-forge"))
}
