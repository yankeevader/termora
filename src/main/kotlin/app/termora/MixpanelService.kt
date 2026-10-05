package app.termora

/**
 * OTM local-only build.
 *
 * Telemetry is intentionally disabled. Keeping this no-op service preserves
 * source compatibility for any code that still references MixpanelService
 * while guaranteeing that it never performs network I/O.
 */
internal class MixpanelService private constructor() {
    companion object {
        fun getInstance(): MixpanelService {
            return ApplicationScope.forApplicationScope()
                .getOrCreate(MixpanelService::class) { MixpanelService() }
        }
    }

    fun push(event: String, extras: Map<String, String> = emptyMap()) {
        // Intentionally disabled.
    }
}
