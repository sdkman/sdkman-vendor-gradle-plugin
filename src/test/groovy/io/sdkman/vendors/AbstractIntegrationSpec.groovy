package io.sdkman.vendors

import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options

abstract class AbstractIntegrationSpec extends Specification {
    @Rule
    WireMockRule api = new WireMockRule(options().dynamicPort())

    @Rule
    TemporaryFolder projectDir = new TemporaryFolder()

    File settingsFile
    File buildFile
    BuildResult result

    def setup() {
        settingsFile = projectDir.newFile('settings.gradle')
        settingsFile << "rootProject.name = 'release-test'"

        if (dsl == Dsl.GROOVY) {
            buildFile = projectDir.newFile('build.gradle')
        } else {
            buildFile = projectDir.newFile('build.gradle.kts')
        }

        // This needs to be written so that it works in both DSLs
        buildFile << """
            plugins {
                id("io.sdkman.vendors")
            }
            
            sdkman {
                api.set("${apiUrl}")
            }
        """
    }

    BuildResult succeeds(String... arguments) {
        result = createRunner(arguments).build()
    }

    BuildResult fails(String... arguments) {
        result = createRunner(arguments).buildAndFail()
    }

    private GradleRunner createRunner(String... arguments) {
        GradleRunner runner = GradleRunner.create()
                .withProjectDir(projectDir.root)
                .withPluginClasspath()
                .forwardOutput()
                .withEnvironment([
                        "SDKMAN_KEY": "SOME_KEY",
                        "SDKMAN_TOKEN": "SOME_TOKEN"])
                .withArguments(arguments)
        String gradleVersionForTest = System.getProperty("gradle.testing.version")
        if (gradleVersionForTest) {
            runner.withGradleVersion(gradleVersionForTest)
        }
        return runner
    }

    protected String getApiUrl() {
        api.baseUrl()
    }

    enum Dsl {
        KOTLIN, GROOVY
    }

    protected Dsl getDsl() {
        return Dsl.GROOVY
    }
}
