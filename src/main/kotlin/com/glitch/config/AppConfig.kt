package com.glitch.config

import java.nio.file.Path
import kotlin.io.path.Path


abstract class AppConfig(
    open val env: String = "none",
    open val name: String = System.getenv("APP_NAME") ?: "glitch",
    open val selfUrl: String = System.getenv("SELF_URL") ?: "",
    open val serverPort: Int = System.getenv("SERVER_PORT")?.toInt() ?: 5000,
    open val externPath: Path = System.getenv("EXTERN_PATH")?.let { Path(it) } ?: error("EXTERN_PATH required."),
    open val projectResources: Path = System.getenv("PROJECT_RESOURCES_PATH")?.let { Path(it) } ?: Path("."),
    open val jwtSecret: String = System.getenv("JWT_SECRET") ?: "secret",
    open val useMins: Boolean = System.getenv("USE_MINS")?.toBoolean() ?: false,
    open val dbUri: String = System.getenv("DATABASE_URI") ?: "",
    open val dbUser: String = System.getenv("DATABASE_USER") ?: "",
    open val dbPassword: String = System.getenv("DATABASE_PASSWORD") ?: "",
    open val dbAutoLoad: Boolean = false,
)


data class AppConfigProd(
    override val env: String = Environments.PROD,
    override val selfUrl: String = "https://glitch.paris",
    override val projectResources: Path = Path("/dev/null"),
    override val useMins: Boolean = true,
    override val dbAutoLoad: Boolean = false,
) : AppConfig()


data class AppConfigDev(
    override val env: String = Environments.DEV,
    override val selfUrl: String = "http://localhost",
    override val useMins: Boolean = false,
) : AppConfig()


data class AppConfigTest(
    override val env: String = Environments.TEST,
    override val selfUrl: String = "http://localhost",
    override val useMins: Boolean = false,
    override val dbUri: String = "jdbc:h2:mem:testdb",
    override val dbUser: String = "sa",
    override val dbPassword: String = "password",
) : AppConfig()


internal fun loadAppConfig(): AppConfig {
    return when (System.getenv("APP_ENV")?.lowercase() ?: "-") {
        Environments.PROD -> AppConfigProd()
        Environments.DEV -> AppConfigDev()
        Environments.TEST -> AppConfigTest()
        else -> error(
            "APP_ENV must be \"${Environments.TEST}\", "
                    + "\"${Environments.DEV}\", or "
                    + "\"${Environments.PROD}\""
        )
    }
}


val appConfig: AppConfig = loadAppConfig()