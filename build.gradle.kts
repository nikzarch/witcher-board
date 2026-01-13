import java.time.Instant
import java.io.ByteArrayOutputStream

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.9"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "1.9.25"
}

group = "ru.nikzarch"
version = "0.0.1-SNAPSHOT"
description = "milestone project"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.liquibase:liquibase-core")
	implementation("io.jsonwebtoken:jjwt-api:0.12.7")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.7")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.7")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
tasks.register("newMigration"){
	group = "liquibase"
	description = "создаёт новую миграцию"

	val migrationsDir = file("$rootDir/db/liquibase/changelog")
	val masterFile = migrationsDir.resolve("../changelog-master.yml")

	doLast {
		val migrationName = project.findProperty("migrationName")?.toString()
			?: throw GradleException("Укажи названии миграции в kebab-case. Пример: ./gradlew newMigration -PmigrationName=my-migration")

		val kebabCaseRegex = "^[a-z0-9]+(-[a-z0-9]+)*$".toRegex()
		if (!kebabCaseRegex.matches(migrationName)) {
			throw GradleException("'$migrationName' написано не в kebab-case! Надо вот так: add-users-table")
		}

		if (!migrationsDir.exists()) migrationsDir.mkdirs()

		val lastId = migrationsDir.listFiles()
			?.mapNotNull { it.name.split("-").firstOrNull()?.toIntOrNull() }
			?.maxOrNull() ?: 0

		val newId = String.format("%03d", lastId + 1)
		val fileName = "$newId-$migrationName.sql"
		val file = migrationsDir.resolve(fileName)
		val timestamp = Instant.now()
		val user = try {
			val stdout = ByteArrayOutputStream()
			exec {
				commandLine("git", "config", "user.name")
				standardOutput = stdout
			}
			stdout.toString().trim().ifEmpty { "unknown" }
		} catch (e: Exception) {
			"unknown"
		}
		file.writeText(
			"""
            -- migration: $fileName
            -- created by: $user
            -- timestamp: $timestamp
            """.trimIndent()
		)
		println("Миграция создана: $fileName в $migrationsDir")

		val includeEntry = """
            |  - include:
            |      file: changelog/$fileName
            |      relativeToChangelogFile: true
        """.trimMargin()
		val masterContent = masterFile.readText()
		val updatedContent = if (masterContent.contains(includeEntry)) {
			masterContent
		} else {
			masterContent.trimEnd() + "\n$includeEntry\n"
		}
		masterFile.writeText(updatedContent)
	}
}

