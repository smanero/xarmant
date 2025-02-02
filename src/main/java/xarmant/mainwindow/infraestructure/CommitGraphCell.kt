package xarmant.mainwindow.infraestructure

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.fxml.FXML
import javafx.scene.Group
import javafx.fxml.FXMLLoader
import javafx.scene.canvas.Canvas
import javafx.scene.control.TableCell
import javafx.scene.paint.Color
import javafx.scene.shape.Line
import xarmant.mainwindow.infraestructure.jgit.JavaFxPlotRenderer
import xarmant.mainwindow.model.Commit
import java.io.IOException

/**
 * Celdas de la grafica en el tableview
 */
class CommitGraphCell(val renderer : JavaFxPlotRenderer) : TableCell<Commit, Commit>() {
    @FXML
    var group: Group? = null
    @FXML
    var canvas: Canvas? = null

    var innerCommit : Commit? = null

    private var mLLoader = FXMLLoader(javaClass.getResource("/ListCell.fxml"))

    override fun updateItem(commit: Commit?, empty: Boolean) {
        super.updateItem(commit, empty);
        if (commit == null) {
            setGraphic(null);
            return;
        }

        mLLoader = FXMLLoader(javaClass.getResource("/ListCell.fxml"))
        mLLoader.setController(this)
        try {
            mLLoader.load<Any>()
            renderer.paint(this, commit.plotCommit, commit.type)
            setGraphic(canvas)
            val listener = InvalidationListener{
                if (innerCommit != null) {
                    updateItem(innerCommit, true)
                }
            }
            canvas!!.widthProperty().addListener(listener);
            canvas!!.heightProperty().addListener(listener);
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}
