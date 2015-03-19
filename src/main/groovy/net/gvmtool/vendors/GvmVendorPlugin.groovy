package net.gvmtool.vendors

import net.gvmtool.vendors.model.GvmExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class GvmVendorPlugin implements Plugin<Project> {
    @Override
    void apply(Project target) {
        target.extensions.create("gvm", GvmExtension)

        target.task 'gvmReleaseVersion', type: GvmReleaseVersionTask
        target.task 'gvmDefaultVersion', type: GvmDefaultVersionTask
        target.task 'gvmAnnounceVersion', type: GvmAnnounceVersionTask

        target.task 'sdkMinorRelease', type: SdkMinorRelease
        target.task 'sdkMajorRelease', type: SdkMajorRelease
    }
}
