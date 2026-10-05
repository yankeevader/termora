package app.termora

import app.termora.Application.ohMyJson
import kotlinx.serialization.json.*
import okhttp3.Request
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.time.DateFormatUtils
import org.commonmark.node.BulletList
import org.commonmark.node.Heading
import org.commonmark.node.Paragraph
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.AttributeProvider
import org.commonmark.renderer.html.HtmlRenderer
import org.semver4j.Semver
import org.slf4j.LoggerFactory
import java.time.Instant
import java.util.*


class UpdaterManager private constructor() {
    companion object {
        private val log = LoggerFactory.getLogger(UpdaterManager::class.java)
        fun getInstance(): UpdaterManager {
            return ApplicationScope.forApplicationScope().getOrCreate(UpdaterManager::class) { UpdaterManager() }
        }
    }

    data class Asset(
        val name: String,
        val url: String,
        val downloadUrl: String,
        val size: Long
    )

    data class LatestVersion(
        // tag name
        val version: String,
        val prerelease: Boolean,
        val draft: Boolean,
        val name: String,
        val createdDate: Date,
        val publishedDate: Date,
        val body: String,
        val htmlBody: String,
        val assets: List<Asset>
    ) {
        companion object {
            val self = LatestVersion(
                version = Application.getVersion(),
                prerelease = false,
                draft = false,
                name = StringUtils.EMPTY,
                createdDate = Date(),
                publishedDate = Date(),
                body = StringUtils.EMPTY,
                htmlBody = StringUtils.EMPTY,
                assets = emptyList()
            )
        }

        val isSelf get() = this == self
    }

    var lastVersion = LatestVersion.self

    fun fetchLatestVersion(): LatestVersion {
        // OTM local-only build: never contact GitHub/Termora for update checks.
        lastVersion = LatestVersion.self
        return LatestVersion.self
    }

    private fun getLatestBetaRelease(text: String): JsonObject? {
        val releases = parseReleases(text)
        if (releases.isEmpty()) return null
        return releases.maxByOrNull { it.first }?.second
    }

    private fun parseReleases(text: String): List<Pair<Semver, JsonObject>> {
        val array = ohMyJson.parseToJsonElement(text).jsonArray
        val releases = mutableListOf<Pair<Semver, JsonObject>>()
        for (e in array) {
            val version = e.jsonObject.getValue("tag_name").jsonPrimitive.content
            val prerelease = e.jsonObject.getValue("prerelease").jsonPrimitive.boolean
            if (prerelease.not()) continue
            val semver = Semver.parse(version) ?: continue
            releases.add(semver to e.jsonObject)
        }
        return releases
    }
}