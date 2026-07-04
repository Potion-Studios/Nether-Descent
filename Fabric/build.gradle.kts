import com.hypherionmc.modpublisher.properties.ModLoader

plugins {
    id("com.gradleup.shadow")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

val minecraftVersion = providers.gradleProperty("minecraft_version").get()

configurations {
    val common = register("common")
    register("shadowCommon")
    compileClasspath.get().extendsFrom(common.get())
    runtimeClasspath.get().extendsFrom(common.get())
    named("developmentFabric") { extendsFrom(common.get()) }
}

loom {
    accessWidenerPath.set(project(":Common").loom.accessWidenerPath)
    injectAccessWidener(tasks.shadowJar)
}

dependencies {
    implementation("net.fabricmc:fabric-loader:${providers.gradleProperty("fabric_loader_version").get()}")
    api("net.fabricmc.fabric-api:fabric-api:${providers.gradleProperty("fabric_api_version").get()}+$minecraftVersion")

    "common"(project(":Common")) { isTransitive = false }
    "shadowCommon"(project(":Common", "transformProductionFabric"))

    localRuntime("me.djtheredstoner:DevAuth-fabric:${providers.gradleProperty("devauth_version").get()}")

    api("com.github.glitchfiend:TerraBlender-fabric:$minecraftVersion-${providers.gradleProperty("terrablender_version").get()}")
    compileOnly("com.terraformersmc:biolith-fabric:${providers.gradleProperty("biolith_version").get()}")
    compileOnly("maven.modrinth:lithostitched:${providers.gradleProperty("lithostitched_version").get()}-fabric-26.1")
    api("dev.corgitaco.ohthetreesyoullgrow:ohthetreesyoullgrow-fabric-$minecraftVersion:${providers.gradleProperty("ohthetreesyoullgrow_version").get()}")
    api("me.lucko:fabric-permissions-api:0.7.0")

    localRuntime("mcp.mobius.waila:wthit:fabric-${providers.gradleProperty("WTHIT").get()}")
    localRuntime("lol.bai:badpackets:fabric-${providers.gradleProperty("badPackets").get()}")
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand(mapOf("version" to project.version))
        }
    }

    jar.get().archiveClassifier.set("raw")

    shadowJar {
        dependsOn(jar)
        from(zipTree(jar.get().archiveFile))
        exclude("architectury.common.json", ".cache/**", "data/neoforge/**")
        configurations = listOf(project.configurations.getByName("shadowCommon"))
        archiveClassifier.set(null)
    }
}

publisher {
    setLoaders(ModLoader.FABRIC, ModLoader.QUILT)
    curseDepends.required.set(mutableListOf("fabric-api", "oh-the-trees-youll-grow"))
    modrinthDepends.required.set(mutableListOf("fabric-api", "oh-the-trees-youll-grow"))
    curseDepends.optional.set(mutableListOf("terrablender-fabric", "biolith", "lithostitched", "wthit"))
}
