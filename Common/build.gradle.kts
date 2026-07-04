architectury {
    common("forge", "fabric", "neoforge")
    platformSetupLoomIde()
}

val minecraftVersion = providers.gradleProperty("minecraft_version").get()

loom.accessWidenerPath.set(file("src/main/resources/netherdescent.accessWidener"))

sourceSets.main.get().resources.srcDir("src/main/generated/resources")

dependencies {
    implementation("net.fabricmc:fabric-loader:${providers.gradleProperty("fabric_loader_version").get()}")

    compileOnly("com.github.glitchfiend:TerraBlender-common:$minecraftVersion-${providers.gradleProperty("terrablender_version").get()}")
    compileOnly("com.terraformersmc:biolith-fabric:${providers.gradleProperty("biolith_version").get()}")
    compileOnly("maven.modrinth:lithostitched:${providers.gradleProperty("lithostitched_version").get()}-fabric-26.1")
    implementation("dev.corgitaco.ohthetreesyoullgrow:ohthetreesyoullgrow-common-$minecraftVersion:${providers.gradleProperty("ohthetreesyoullgrow_version").get()}")

    compileOnly("mcp.mobius.waila:wthit-api:fabric-${providers.gradleProperty("WTHIT").get()}")
}
