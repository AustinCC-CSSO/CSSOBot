rootProject.name = "CSSOBot"

sourceControl {
    gitRepository(java.net.URI.create("https://github.com/camdenorrb/CrescentLang.git")) {
        producesModule("dev.twelveoclock.lang:CrescentLang")
    }
}