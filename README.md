# SDKMAN! Vendor Plugin

This Gradle plugin is used by SDK Vendors to publish their own releases on [SDKMAN!](http://sdkman.io). Even more, they are able to announce their releases through the Broadcast API.

## Vendors

A Vendor is an organisation/individual that owns a technology, and wishes to publish it's Versions on SDKMAN!

### Setup
It requires some configuration in the ```build.gradle``` file of the project.
```groovy
	buildscript {
	  repositories {
		maven {
		  url "https://plugins.gradle.org/m2/"
		}
	  }
	  dependencies {
		classpath "gradle.plugin.io.sdkman:gradle-sdkvendor-plugin:1.1.1"
	  }
	}

	apply plugin: "io.sdkman.vendors"
```
For users of Gradle 2.1 or greater:
```groovy
	plugins {
	  id "io.sdkman.vendors" version "1.1.0"
	}
```
You will also need some configuration to interact with the remote API in order to publish and broadcast:
```groovy
	sdkman {
		api = "https://vendors.sdkman.io/"
		consumerKey = "SOME_KEY"
		consumerToken = "SOME_TOKEN"
		candidate = "grails"
		version = "3.0.0"
		url = "http://dist.springframework.org.s3.amazonaws.com/release/GRAILS/grails-3.0.0.zip"
		hashtag = "#grailsfw"
	}

```
#### Credentials

The credentials for Vendors consisting of a security key and token can be obtained by raising an [Issue on Github](https://github.com/sdkman/sdkman-vendor-gradle-plugin).

#### Other Config

Configurations such as `candidate`, `version`, `url` and `hashtag` may be populated dynamically in the build.

## Gradle Tasks

The plugin provides the following tasks for releasing, defaulting and announcing:

 - `sdkAnnounceVersion` - Announce a Release on SDKMAN.
 - `sdkDefaultVersion` - Make an existing Candidate Version the new Default on SDKMAN.
 - `sdkReleaseVersion` - Release a new Candidate Version on SDKMAN.
 - `sdkMajorRelease` - Convenience task performs a Major Release consisting of Release, Default and Announce combined.
 - `sdkMinorRelease` - Convenience task performs a Minor Release consisting of Release and Announce combined.
