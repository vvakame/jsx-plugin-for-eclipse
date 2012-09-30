package net.vvakame.ide.jsx.editors

import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IProjectNature

object JsxNature {
  val NATURE_ID = "net.vvakame.jsx.eclipse.plugin.core.jsxnature"
}

class JsxNature extends IProjectNature {

  var project: IProject = null

  override def configure() {
    var desc = project.getDescription
    var list = desc.getBuildSpec().toList
    if (list.exists(_.getBuilderName() == JsxBuilder.BUILDER_ID)) return

    var command = desc.newCommand()
    command.setBuilderName(JsxBuilder.BUILDER_ID)
    list ::= command
    desc.setBuildSpec(list.toArray)
    project.setDescription(desc, null)
  }

  override def deconfigure() {
    var desc = project.getDescription
    var list = desc.getBuildSpec().toList
    list = list.filter(_.getBuilderName() != JsxBuilder.BUILDER_ID)
    desc.setBuildSpec(list.toArray)
    project.setDescription(desc, null)
  }

  override def getProject(): IProject = this.project

  override def setProject(project: IProject) {
    this.project = project;
  }
}