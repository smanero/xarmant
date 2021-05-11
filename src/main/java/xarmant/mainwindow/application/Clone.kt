package xarmant.mainwindow.application

import xarmant.mainwindow.infraestructure.XarmantProgressMonitor
import xarmant.mainwindow.shared.GitContext
import xarmant.mainwindow.shared.XGit
// Esto va a desaoarecer:
/**
 * Era un intento de arquitectura hexagonal, per, sabes que?
 *
 * No voy a cambiar de JavaFX, lo unico que podria cambiar el JGit poor correr comandos y parsear respuestas y, para esp, esta lo de XGit
 */
class Clone {
    fun execute(context: GitContext, monitor: XarmantProgressMonitor): XGit {
        return XGit(context, monitor).clone()
    }
}
