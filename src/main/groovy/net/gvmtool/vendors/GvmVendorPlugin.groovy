package net.gvmtool.vendors

import org.gradle.api.Plugin
import org.gradle.api.Project

class GvmVendorPlugin implements Plugin<Project> {
    @Override
    void apply(Project target) {
        target.task('gvmPublish', type: GvmPublishTask)
    }
}
