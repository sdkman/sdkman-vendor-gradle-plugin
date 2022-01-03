# SDKMAN! Vendor Plugin

This Gradle plugin is used by SDK Vendors to publish their own releases on [SDKMAN!](http://sdkman.io). Even more, they are able to announce their releases through the Broadcast API.

## Vendors

A Vendor is an organisation/individual that owns a technology, and wishes to publish it's Versions on SDKMAN!

### Setup
It requires some configuration in the ```build.gradle``` file of the project.

```groovy
    plugins {
      id "io.sdkman.vendors" version "3.0.0"
    }
```

You will also need some configuration to interact with the remote API in order to publish and broadcast. Two types
of configuration are supported. The first is for UNIVERSAL binaries:

```groovy
    sdkman {
        candidate = "grails"
        version = "x.y.z"
        url = "https://host/grails-x.y.z.zip"
        hashtag = "grailsfw"
    }
```

The second type is for native platform-specific binaries:

```groovy
    sdkman {
        candidate = "micronaut"
        version = "x.y.z"
        platforms = [
            "MAC_OSX":"https://host/micronaut-x.y.z-macosx.zip",
            "WINDOWS_64":"https://host/micronaut-x.y.z-win.zip", 
            "LINUX_64":"https://host/micronaut-x.y.z-linux64.zip", 
        ]
        hashtag = "micronaut"
    }
```

We currently support the following platforms:
* `MAC_OSX`
* `MAC_ARM64`
* `WINDOWS_64`
* `LINUX_32`
* `LINUX_64`
* `LINUX_ARM32SF` (ARM Soft Float)
* `LINUX_ARM32HF` (ARM Hard Float)
* `LINUX_ARM64`

By default, the plugin communicates with the `https://vendors.sdkman.io` service.

#### Credentials

The credentials for Vendors consisting of a security key and token can be obtained by raising an [Issue on Github](https://github.com/sdkman/sdkman-vendor-gradle-plugin).

You can explicitly set your key and token from a Gradle property or environment variable.

```groovy
    sdkman {
        consumerKey = "SOME_KEY"
        consumerToken = "SOME_TOKEN"
    }
```

By default, the plugin looks for your credentials in the `SDKMAN_KEY` and `SDKMAN_TOKEN` environment variables.

#### Other Config

All configuration above such as credentials, `candidate`, `version`, `url` and `hashtag` may be populated dynamically in the build.

`candidate` and `version` are required. `url` is required if you do not set `platforms`.

## Gradle Tasks

The plugin provides the following tasks for releasing, defaulting and announcing:

 - `sdkAnnounceVersion` - Announce a Release on SDKMAN.
 - `sdkDefaultVersion` - Make an existing Candidate Version the new Default on SDKMAN.
 - `sdkReleaseVersion` - Release a new Candidate Version on SDKMAN.
 - `sdkMajorRelease` - Convenience task performs a Major Release consisting of Release, Default and Announce combined.
 - `sdkMinorRelease` - Convenience task performs a Minor Release consisting of Release and Announce combined.
