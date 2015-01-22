package net.gvmtool.vendors

import net.gvmtool.vendors.model.ApiCredentials
import net.gvmtool.vendors.model.GvmExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class GvmVendorPlugin implements Plugin<Project> {
    @Override
    void apply(Project target) {
        def apis = target.container(ApiCredentials)
        target.configure(target) {
            extensions.create("gvm", GvmExtension, apis)
        }

        target.task 'gvmReleaseVersion', type: GvmReleaseVersionTask
        target.task 'gvmDefaultVersion', type: GvmDefaultVersionTask
    }
}
