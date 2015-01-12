package net.gvmtool.vendors

import org.gradle.api.Plugin
import org.gradle.api.Project

class GvmVendorPlugin implements Plugin<Project> {
    @Override
    void apply(Project target) {
        target.extensions.create 'gvm', GvmExtension
        target.task 'gvmReleaseVersion', type: GvmReleaseVersionTask
        target.task 'gvmDefaultVersion', type: GvmDefaultVersionTask
    }
}
